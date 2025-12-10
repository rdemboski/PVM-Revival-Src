/*     */ package com.funcom.rpgengine2.equipment;
/*     */ 
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
/*     */ import com.funcom.rpgengine2.loader.RawData;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class PetEquipmentBuilder {
/*     */   private ItemManager itemManager;
/*     */   private ItemFactory itemFactory;
/*     */   private ValueAccumulatorFactory valueAccumulatorFactory;
/*     */   private ShapeDataEvaluator evaluator;
/*     */   private RpgLoader loader;
/*     */   
/*     */   public ItemDescription createEquipment(Map<String, RawData> rawDataMap, PetArchType archtype, int level) {
/*     */     Integer itemLevel;
/*     */     DireEffectDescription dfxDescription;
/*     */     Integer itemValueAmount;
/*  32 */     String itemId = archtype.getArchtypeId() + "-" + level;
/*  33 */     if (itemId.isEmpty()) {
/*  34 */       throw new RuntimeException("Item without id! Empty line?");
/*     */     }
/*  36 */     String archtypeId = archtype.getArchtypeId();
/*     */     
/*  38 */     if (archtype == null) {
/*  39 */       throw new RuntimeException("Archtype: " + archtypeId + " does not exist!");
/*     */     }
/*     */     
/*  42 */     String tierStr = "0";
/*  43 */     int tier = 0;
/*  44 */     if (!tierStr.isEmpty())
/*  45 */       tier = Integer.parseInt(tierStr); 
/*  46 */     String ref = "";
/*  47 */     String name = "";
/*  48 */     String icon = "";
/*  49 */     String rarity = "";
/*  50 */     String setid = "";
/*  51 */     String itemText = "";
/*  52 */     String sType = "";
/*  53 */     boolean subscriberOnly = false;
/*  54 */     String visualId = "";
/*  55 */     String sTypeText = "";
/*  56 */     ItemType itemType = ItemType.BONUS;
/*  57 */     if (itemType == null) {
/*  58 */       throw new RuntimeException("Could not parse item type: \"" + sType + "\"");
/*     */     }
/*     */     
/*     */     try {
/*  62 */       itemLevel = Integer.valueOf(level);
/*  63 */     } catch (NumberFormatException e) {
/*  64 */       throw new RuntimeException("Cannot parse item level: levelText =\"0\"\nItemID=\"" + itemId + "\"");
/*     */     } 
/*     */ 
/*     */     
/*  68 */     ItemDescription itemDescription = new ItemDescription(itemId, Integer.valueOf(tier), this.itemFactory);
/*  69 */     String itemValue = "";
/*  70 */     itemDescription.setItemValue(itemValue);
/*     */     
/*  72 */     if (itemType != null) {
/*  73 */       itemDescription.setItemType(itemType);
/*     */     } else {
/*  75 */       throw new RuntimeException("No item type set for item: " + itemId + " tier: " + tier);
/*     */     } 
/*  77 */     itemDescription.setName(name);
/*  78 */     itemDescription.setItemText(itemText);
/*  79 */     itemDescription.setItemTypeText(sTypeText);
/*  80 */     itemDescription.setSubscriberOnly(subscriberOnly);
/*  81 */     itemDescription.setVisualId(visualId);
/*  82 */     if (setid != null) {
/*  83 */       itemDescription.setSetId(setid);
/*     */     }
/*  85 */     itemDescription.setLevel(itemLevel.intValue());
/*  86 */     if (!icon.trim().isEmpty())
/*  87 */       itemDescription.setIcon(icon); 
/*  88 */     itemDescription.setRarity(rarity);
/*     */ 
/*     */     
/*     */     try {
/*  92 */       dfxDescription = this.loader.getDfxDescriptionFactory().getDireEffectDescription("", false);
/*  93 */     } catch (NoSuchDFXException e) {
/*  94 */       throw new RuntimeException(e);
/*     */     } 
/*  96 */     itemDescription.setDfxDescription(dfxDescription);
/*     */ 
/*     */     
/*  99 */     DireEffectDescription impactDfxDescription = DireEffectDescriptionFactory.EMPTY_DFX;
/* 100 */     itemDescription.setImpactDfxDescription(impactDfxDescription);
/*     */     
/* 102 */     if (itemDescription.isInitialized()) {
/*     */       
/* 104 */       itemDescription.checkInstanceValues(tier, 0, 1);
/*     */     } else {
/* 106 */       itemDescription.init(tier, 0, 1, 0.0D);
/*     */     } 
/*     */ 
/*     */     
/*     */     try {
/* 111 */       itemValueAmount = Integer.valueOf(0);
/* 112 */     } catch (NumberFormatException e) {
/* 113 */       throw new RuntimeException("Cannot parse item level: itemValueAmount=0");
/*     */     } 
/* 115 */     itemDescription.setItemValueAmount(itemValueAmount.intValue());
/*     */     
/* 117 */     loadAbilities(rawDataMap, archtype, itemDescription, 1, 2147483647);
/*     */     
/* 119 */     this.itemManager.setPetStatItem(itemId, itemDescription);
/* 120 */     return itemDescription;
/*     */   }
/*     */   
/*     */   private void loadAbilities(Map<String, RawData> rawDataMap, PetArchType archtype, ItemDescription itemDescription, int requireLevelFrom, int requireLevelTo) {
/* 124 */     int numStats = archtype.count();
/* 125 */     int baseLevel = itemDescription.getLevel();
/* 126 */     double level = baseLevel;
/* 127 */     double gear_slots = 1.0D;
/*     */     
/* 129 */     if (archtype.getHealth() > 0.0D) {
/* 130 */       double health_a = 6.0D;
/* 131 */       double health_b = 8.0D;
/* 132 */       double health_c = 80.0D;
/*     */       
/* 134 */       double max_hp_goal = 1.0D;
/* 135 */       double config_gear_hp = 0.4D;
/*     */       
/* 137 */       double hp = (6.0D * Math.pow(level, 2.0D) + 8.0D * level + 80.0D) * 1.0D;
/*     */       
/* 139 */       double amount = Math.round(hp * 0.4D / gear_slots);
/* 140 */       amount *= archtype.getHealth();
/*     */       
/* 142 */       addAbility(rawDataMap, "mod-add-health", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/* 144 */     if (archtype.getMana() > 0.0D) {
/* 145 */       double mana_a = 3.0D;
/* 146 */       double mana_b = 4.0D;
/* 147 */       double mana_c = 40.0D;
/*     */       
/* 149 */       double max_mp_goal = 1.0D;
/* 150 */       double config_gear_mp = 0.4D;
/*     */       
/* 152 */       double mp = (3.0D * Math.pow(level, 2.0D) + 4.0D * level + 40.0D) * 1.0D;
/*     */       
/* 154 */       double amount = Math.round(mp * 0.4D / gear_slots);
/* 155 */       amount *= archtype.getMana();
/* 156 */       addAbility(rawDataMap, "mod-add-mana", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/* 158 */     if (archtype.getDefense() > 0.0D) {
/* 159 */       int armor_a = 15;
/* 160 */       double mitigation = 0.7D;
/* 161 */       double c = (15 * baseLevel);
/*     */       
/* 163 */       double config_gear_armor = 0.5D;
/*     */       
/* 165 */       double amount = Math.round(c * 0.7D / 0.30000000000000004D / gear_slots);
/* 166 */       amount *= 0.5D;
/*     */       
/* 168 */       amount *= archtype.getDefense();
/*     */       
/* 170 */       addAbility(rawDataMap, "mod-add-resist-monster", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/* 171 */       addAbility(rawDataMap, "mod-add-resist-player", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/* 173 */     if (archtype.getAttack() > 0.0D) {
/* 174 */       double k = 20.0D;
/* 175 */       double max_attack_goal = 0.8D;
/* 176 */       double attack_constant = 16.0D;
/*     */       
/* 178 */       double attack_for_level = 16.0D * level / 20.0D;
/*     */       
/* 180 */       double config_gear_attack = 0.5D;
/*     */       
/* 182 */       double amount = Math.round(attack_for_level * 0.5D * 100.0D / gear_slots);
/* 183 */       amount *= archtype.getAttack();
/* 184 */       addAbility(rawDataMap, "mod-add-attack", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/* 186 */     if (archtype.getCrit() > 0.0D) {
/* 187 */       double k = 20.0D;
/* 188 */       double max_crit_goal = 0.6D;
/* 189 */       double crit_constant = 12.0D;
/*     */       
/* 191 */       double crit_for_level = 12.0D * level / 20.0D;
/* 192 */       double config_gear_crit = 0.5D;
/*     */       
/* 194 */       double amount = Math.round(crit_for_level * 0.5D * 100.0D / gear_slots);
/* 195 */       amount *= archtype.getCrit();
/*     */       
/* 197 */       addAbility(rawDataMap, "mod-add-max-crit", String.valueOf((int)amount), itemDescription, requireLevelFrom, requireLevelTo);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addAbility(Map<String, RawData> rawDataMap, String abilityId, String x, ItemDescription itemDescription, int requireLevelFrom, int requireLevelTo) {
/* 203 */     RawData data = rawDataMap.get(abilityId);
/*     */     
/* 205 */     if (data == null) {
/* 206 */       if (!abilityId.isEmpty()) {
/* 207 */         this.loader.notifyMissingAbility(itemDescription, abilityId);
/*     */       }
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/* 213 */     data.assertAbilityType("This ability is not supported by Item", ItemDescription.SUPPORTED_ABILITIES);
/*     */     
/* 215 */     AbilityFactory abilityFactory = data.getAbilityFactory();
/* 216 */     AbilityParams params = abilityFactory.createParams(data.getFieldsList());
/* 217 */     params.setCommon(AbilityParams.ParamName.REQ_LEVEL_FROM, Integer.valueOf(requireLevelFrom));
/* 218 */     params.setCommon(AbilityParams.ParamName.REQ_LEVEL_TO, Integer.valueOf(requireLevelTo));
/* 219 */     params.setCommon(AbilityParams.ParamName.X, x);
/*     */     
/* 221 */     AbilityParams realVariables = new AbilityParams();
/* 222 */     realVariables.setCommon(AbilityParams.ParamName.X, x);
/*     */     try {
/* 224 */       params.resolveVariables(realVariables);
/* 225 */     } catch (LocalizationException e) {
/* 226 */       throw new RuntimeException(e);
/*     */     } 
/*     */     
/* 229 */     Ability ability = abilityFactory.create((AbilityContainer)itemDescription, this.evaluator, params, this.loader, this.valueAccumulatorFactory);
/*     */     
/* 231 */     if (ability == null) {
/* 232 */       throw new NullPointerException("Cannot create ability: factoryClass=" + abilityFactory.getClass());
/*     */     }
/*     */     
/* 235 */     itemDescription.getAbilities().add(ability);
/*     */   }
/*     */   
/*     */   public void setItemManager(ItemManager itemManager) {
/* 239 */     this.itemManager = itemManager;
/*     */   }
/*     */   
/*     */   public void setItemFactory(ItemFactory itemFactory) {
/* 243 */     this.itemFactory = itemFactory;
/*     */   }
/*     */   
/*     */   public void setValueAccumulatorFactory(ValueAccumulatorFactory valueAccumulatorFactory) {
/* 247 */     this.valueAccumulatorFactory = valueAccumulatorFactory;
/*     */   }
/*     */   
/*     */   public void setEvaluator(ShapeDataEvaluator evaluator) {
/* 251 */     this.evaluator = evaluator;
/*     */   }
/*     */   
/*     */   public void setLoader(RpgLoader loader) {
/* 255 */     this.loader = loader;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\equipment\PetEquipmentBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */