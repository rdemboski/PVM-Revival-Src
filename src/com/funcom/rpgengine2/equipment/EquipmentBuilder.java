/*     */ package com.funcom.rpgengine2.equipment;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.commons.localization.LocalizationException;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*     */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemFactory;
/*     */ import com.funcom.rpgengine2.items.ItemManager;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.rpgengine2.loader.AbilityFactory;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.EquipmentBuilderFields;
/*     */ import com.funcom.rpgengine2.loader.FieldMap;
/*     */ import com.funcom.rpgengine2.loader.LoaderUtils;
/*     */ import com.funcom.rpgengine2.loader.RawData;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class EquipmentBuilder {
/*     */   private static final double STACKING_PENALTY = 3.0D;
/*     */   private ArchtypeManager archtypeManager;
/*     */   private ItemManager itemManager;
/*     */   
/*     */   public ItemDescription createEquipment(Map<String, RawData> rawDataMap, FieldMap fieldMap) {
/*     */     Integer itemLevel;
/*     */     ItemDescription itemDescription;
/*     */     DireEffectDescription dfxDescription;
/*     */     Integer itemValueAmount;
/*  33 */     String itemId = fieldMap.get(EquipmentBuilderFields.ID);
/*  34 */     if (itemId.isEmpty()) {
/*  35 */       throw new RuntimeException("Item without id! Empty line?");
/*     */     }
/*  37 */     String archtypeId = fieldMap.get(EquipmentBuilderFields.ARCHTYPE);
/*  38 */     ArchType archtype = this.archtypeManager.getArchtypeForId(archtypeId);
/*     */     
/*  40 */     if (archtype == null) {
/*  41 */       throw new RuntimeException("Archtype: " + archtypeId + " does not exist!");
/*     */     }
/*     */     
/*  44 */     String tierStr = fieldMap.get(EquipmentBuilderFields.TIER);
/*  45 */     int tier = 0;
/*  46 */     if (!tierStr.isEmpty())
/*  47 */       tier = Integer.parseInt(tierStr); 
/*  48 */     String ref = fieldMap.get(EquipmentBuilderFields.REFERENCE_ID);
/*  49 */     String name = fieldMap.get(EquipmentBuilderFields.NAME);
/*  50 */     String icon = fieldMap.get(EquipmentBuilderFields.ICON);
/*  51 */     String rarity = fieldMap.get(EquipmentBuilderFields.RARITY);
/*  52 */     String setid = fieldMap.get(EquipmentBuilderFields.SETID);
/*  53 */     String itemText = fieldMap.get(EquipmentBuilderFields.ITEM_TEXT);
/*  54 */     String sType = fieldMap.get(EquipmentBuilderFields.ITEM_TYPE);
/*  55 */     boolean subscriberOnly = Boolean.parseBoolean(fieldMap.get(EquipmentBuilderFields.SUBSCIBER_ONLY));
/*  56 */     String visualId = fieldMap.get(EquipmentBuilderFields.VISUAL_ID);
/*  57 */     String sTypeText = fieldMap.get(EquipmentBuilderFields.ITEM_TYPE_TEXT);
/*  58 */     ItemType itemType = ItemType.getItemType(sType);
/*  59 */     if (itemType == null) {
/*  60 */       throw new RuntimeException("Could not parse item type: \"" + sType + "\"");
/*     */     }
/*     */     
/*     */     try {
/*  64 */       itemLevel = Integer.valueOf(fieldMap.get(EquipmentBuilderFields.ITEM_LEVEL));
/*  65 */     } catch (NumberFormatException e) {
/*  66 */       throw new RuntimeException("Cannot parse item level: levelText =\"" + fieldMap.get(EquipmentBuilderFields.ITEM_LEVEL) + "\"\n" + "ItemID=\"" + itemId + "\"");
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/*  71 */     if (!this.itemManager.hasDescription(itemId, Integer.valueOf(tier), false)) {
/*  72 */       itemDescription = new ItemDescription(itemId, Integer.valueOf(tier), this.itemFactory);
/*  73 */       itemDescription.setArchType(archtype);
/*  74 */       this.itemManager.putOnce(itemId, ref, itemDescription);
/*     */     } else {
/*  76 */       itemDescription = this.itemManager.getDescription(itemId, Integer.valueOf(tier), false);
/*     */     } 
/*  78 */     String itemValue = fieldMap.get(EquipmentBuilderFields.ITEM_VALUE);
/*  79 */     itemDescription.setItemValue(itemValue);
/*     */     
/*  81 */     if (itemType != null) {
/*  82 */       itemDescription.setItemType(itemType);
/*     */     } else {
/*  84 */       throw new RuntimeException("No item type set for item: " + itemId + " tier: " + tier);
/*     */     } 
/*  86 */     itemDescription.setName(name);
/*  87 */     itemDescription.setItemText(itemText);
/*  88 */     itemDescription.setItemTypeText(sTypeText);
/*  89 */     itemDescription.setSubscriberOnly(subscriberOnly);
/*  90 */     itemDescription.setVisualId(visualId);
/*  91 */     if (setid != null) {
/*  92 */       itemDescription.setSetId(setid);
/*     */     }
/*  94 */     itemDescription.setLevel(itemLevel.intValue());
/*  95 */     if (!icon.trim().isEmpty())
/*  96 */       itemDescription.setIcon(icon); 
/*  97 */     itemDescription.setRarity(rarity);
/*     */ 
/*     */     
/*     */     try {
/* 101 */       dfxDescription = this.loader.getDfxDescriptionFactory().getDireEffectDescription("", false);
/* 102 */     } catch (NoSuchDFXException e) {
/* 103 */       throw new RuntimeException(e);
/*     */     } 
/* 105 */     itemDescription.setDfxDescription(dfxDescription);
/*     */ 
/*     */     
/* 108 */     DireEffectDescription impactDfxDescription = DireEffectDescriptionFactory.EMPTY_DFX;
/* 109 */     itemDescription.setImpactDfxDescription(impactDfxDescription);
/*     */     
/* 111 */     if (itemDescription.isInitialized()) {
/*     */       
/* 113 */       itemDescription.checkInstanceValues(tier, 0, 1);
/*     */     } else {
/* 115 */       itemDescription.init(tier, 0, 1, 0.0D);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 120 */       itemValueAmount = Integer.valueOf(fieldMap.get(EquipmentBuilderFields.ITEM_VALUE_AMOUNT));
/* 121 */     } catch (NumberFormatException e) {
/* 122 */       throw new RuntimeException("Cannot parse item level: itemValueAmount=" + fieldMap.get(EquipmentBuilderFields.ITEM_LEVEL));
/*     */     } 
/*     */     
/* 125 */     int requireLevelFrom = Integer.parseInt(fieldMap.get(EquipmentBuilderFields.REQIREMENT_LEVEL_FROM));
/* 126 */     int requireLevelTo = LoaderUtils.parseLevel(fieldMap.get(EquipmentBuilderFields.REQUIREMENT_LEVEL_TO));
/*     */     
/* 128 */     itemDescription.setItemValueAmount(itemValueAmount.intValue());
/*     */     
/* 130 */     loadAbilities(rawDataMap, archtype, itemDescription, requireLevelFrom, requireLevelTo);
/*     */     
/* 132 */     return itemDescription;
/*     */   }
/*     */   private ItemFactory itemFactory; private ValueAccumulatorFactory valueAccumulatorFactory; private ShapeDataEvaluator evaluator; private RpgLoader loader;
/*     */   private void loadAbilities(Map<String, RawData> rawDataMap, ArchType archtype, ItemDescription itemDescription, int requireLevelFrom, int requireLevelTo) {
/* 136 */     int numStats = archtype.count();
/* 137 */     int baseLevel = itemDescription.getLevel();
/* 138 */     double level = baseLevel;
/* 139 */     double gear_slots = 5.0D;
/* 140 */     if (archtype.getHealth() > 0.0D) {
/* 141 */       double health_a = 6.0D;
/* 142 */       double health_b = 8.0D;
/* 143 */       double health_c = 80.0D;
/*     */       
/* 145 */       double max_hp_goal = 1.0D;
/* 146 */       double config_gear_hp = 0.4D;
/*     */       
/* 148 */       double hp = (6.0D * Math.pow(level, 2.0D) + 8.0D * level + 80.0D) * 1.0D;
/*     */       
/* 150 */       double amount = Math.round(hp * 0.4D / gear_slots);
/* 151 */       amount *= archtype.getHealth();
/*     */       
/* 153 */       addAbility(rawDataMap, "mod-add-health", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/* 155 */     if (archtype.getMana() > 0.0D) {
/* 156 */       double mana_a = 3.0D;
/* 157 */       double mana_b = 4.0D;
/* 158 */       double mana_c = 40.0D;
/*     */       
/* 160 */       double max_mp_goal = 1.0D;
/* 161 */       double config_gear_mp = 0.4D;
/*     */       
/* 163 */       double mp = (3.0D * Math.pow(level, 2.0D) + 4.0D * level + 40.0D) * 1.0D;
/*     */       
/* 165 */       double amount = Math.round(mp * 0.4D / gear_slots);
/* 166 */       amount *= archtype.getMana();
/* 167 */       addAbility(rawDataMap, "mod-add-mana", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/* 169 */     if (archtype.getDefense() > 0.0D) {
/* 170 */       int armor_a = 15;
/* 171 */       double mitigation = 0.7D;
/* 172 */       double c = (15 * baseLevel);
/*     */       
/* 174 */       double config_gear_armor = 0.5D;
/*     */       
/* 176 */       double amount = Math.round(c * 0.7D / 0.30000000000000004D / gear_slots);
/* 177 */       amount *= 0.5D;
/*     */       
/* 179 */       amount *= archtype.getDefense();
/*     */       
/* 181 */       addAbility(rawDataMap, "mod-add-resist-monster", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/* 182 */       addAbility(rawDataMap, "mod-add-resist-player", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/* 184 */     if (archtype.getAttack() > 0.0D) {
/* 185 */       double k = 20.0D;
/* 186 */       double max_attack_goal = 0.8D;
/* 187 */       double attack_constant = 16.0D;
/*     */       
/* 189 */       double attack_for_level = 16.0D * level / 20.0D;
/*     */       
/* 191 */       double config_gear_attack = 0.5D;
/*     */       
/* 193 */       double amount = Math.round(attack_for_level * 0.5D * 100.0D / gear_slots);
/* 194 */       amount *= archtype.getAttack();
/* 195 */       addAbility(rawDataMap, "mod-add-attack", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/* 197 */     if (archtype.getCrit() > 0.0D) {
/* 198 */       double k = 20.0D;
/* 199 */       double max_crit_goal = 0.6D;
/* 200 */       double crit_constant = 12.0D;
/*     */       
/* 202 */       double crit_for_level = 12.0D * level / 20.0D;
/* 203 */       double config_gear_crit = 0.5D;
/*     */       
/* 205 */       double amount = Math.round(crit_for_level * 0.5D * 100.0D / gear_slots);
/* 206 */       amount *= archtype.getCrit();
/*     */       
/* 208 */       addAbility(rawDataMap, "mod-add-max-crit", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addAbility(Map<String, RawData> rawDataMap, String abilityId, String x, ItemDescription itemDescription, int requireLevelFrom, int requireLevelTo) {
/* 214 */     RawData data = rawDataMap.get(abilityId);
/*     */     
/* 216 */     if (data == null) {
/* 217 */       if (!abilityId.isEmpty()) {
/* 218 */         this.loader.notifyMissingAbility(itemDescription, abilityId);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 224 */     data.assertAbilityType("This ability is not supported by Item", ItemDescription.SUPPORTED_ABILITIES);
/*     */     
/* 226 */     AbilityFactory abilityFactory = data.getAbilityFactory();
/* 227 */     AbilityParams params = abilityFactory.createParams(data.getFieldsList());
/* 228 */     params.setCommon(AbilityParams.ParamName.REQ_LEVEL_FROM, Integer.valueOf(requireLevelFrom));
/* 229 */     params.setCommon(AbilityParams.ParamName.REQ_LEVEL_TO, Integer.valueOf(requireLevelTo));
/* 230 */     params.setCommon(AbilityParams.ParamName.X, x);
/*     */     
/* 232 */     AbilityParams realVariables = new AbilityParams();
/* 233 */     realVariables.setCommon(AbilityParams.ParamName.X, x);
/*     */     try {
/* 235 */       params.resolveVariables(realVariables);
/* 236 */     } catch (LocalizationException e) {
/* 237 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 240 */     Ability ability = abilityFactory.create((AbilityContainer)itemDescription, this.evaluator, params, this.loader, this.valueAccumulatorFactory);
/*     */     
/* 242 */     if (ability == null) {
/* 243 */       throw new NullPointerException("Cannot create ability: factoryClass=" + abilityFactory.getClass());
/*     */     }
/*     */     
/* 246 */     itemDescription.getAbilities().add(ability);
/*     */   }
/*     */   
/*     */   public void setArchtypeManager(ArchtypeManager archtypeManager) {
/* 250 */     this.archtypeManager = archtypeManager;
/*     */   }
/*     */   
/*     */   public void setItemManager(ItemManager itemManager) {
/* 254 */     this.itemManager = itemManager;
/*     */   }
/*     */   
/*     */   public void setItemFactory(ItemFactory itemFactory) {
/* 258 */     this.itemFactory = itemFactory;
/*     */   }
/*     */   
/*     */   public void setValueAccumulatorFactory(ValueAccumulatorFactory valueAccumulatorFactory) {
/* 262 */     this.valueAccumulatorFactory = valueAccumulatorFactory;
/*     */   }
/*     */   
/*     */   public void setEvaluator(ShapeDataEvaluator evaluator) {
/* 266 */     this.evaluator = evaluator;
/*     */   }
/*     */   
/*     */   public void setLoader(RpgLoader loader) {
/* 270 */     this.loader = loader;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\equipment\EquipmentBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */