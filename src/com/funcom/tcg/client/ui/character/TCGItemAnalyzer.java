/*     */ package com.funcom.tcg.client.ui.character;
/*     */ import com.funcom.rpgengine2.Persistence;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.StatCollection;
/*     */ import com.funcom.rpgengine2.StatEffect;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.EffectFilter;
/*     */ import com.funcom.rpgengine2.abilities.StatModifier;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.rpg.StatId;
/*     */ import java.util.HashMap;
import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class TCGItemAnalyzer {
/*     */   public Map<Object, Integer> getItemStats(List<ClientItem> alreadyEquippedItems, InventoryItem newItemToCheck, ClientPlayer clientPlayer) {
/*  22 */     Map<Object, Integer> statChangesByItem = new HashMap<Object, Integer>();
/*     */     
/*  24 */     evalStatModifierStats(alreadyEquippedItems, newItemToCheck, clientPlayer, statChangesByItem);
/*  25 */     int power = evalPower(newItemToCheck);
/*  26 */     statChangesByItem.put("power", Integer.valueOf(power));
/*     */     
/*  28 */     return statChangesByItem;
/*     */   }
/*     */   
/*     */   private void evalStatModifierStats(List<ClientItem> alreadyEquippedItems, InventoryItem newItemToCheck, ClientPlayer clientPlayer, Map<Object, Integer> statChangesByItem) {
/*  32 */     StatCollection realStatCollection = clientPlayer.getStatSupport().getStatCollection();
/*  33 */     StatCollection statsBeforeItem = makeCopyWithOnlyBase(realStatCollection);
/*     */     
/*  35 */     for (ClientItem equippedItem : alreadyEquippedItems) {
/*  36 */       if (equippedItem != null) {
/*  37 */         applyAllStatModifiers((InventoryItem)equippedItem, statsBeforeItem, clientPlayer);
/*     */       }
/*     */     } 
/*  40 */     StatCollection statsAfterItem = makeCopy(statsBeforeItem);
/*  41 */     applyAllStatModifiers(newItemToCheck, statsAfterItem, clientPlayer);
/*     */     
/*  43 */     calcStatsDiff(statsAfterItem, statsBeforeItem, statChangesByItem);
/*     */   }
/*     */   
/*     */   private StatCollection makeCopyWithOnlyBase(StatCollection realStatCollection) {
/*  47 */     StatCollection ret = makeCopy(realStatCollection);
/*  48 */     for (Stat stat : ret.getStats()) {
/*  49 */       stat.setModifier(0);
/*     */     }
/*  51 */     return ret;
/*     */   }
/*     */   
/*     */   private void applyAllStatModifiers(InventoryItem item, StatCollection statsLogger, ClientPlayer clientPlayer) {
/*  55 */     List<Ability> abilities = item.getAbilities();
/*  56 */     for (Ability ability : abilities) {
/*  57 */       if (ability instanceof StatModifier) {
/*  58 */         StatModifier statModifier = (StatModifier)ability;
/*  59 */         statModifier.modifyStats(statsLogger, (RpgEntity)clientPlayer);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private StatCollection makeCopy(StatCollection realStatCollection) {
/*  65 */     StatCollection statsBeforeItem = new StatCollection();
/*  66 */     for (Stat stat : realStatCollection.getStats()) {
/*  67 */       statsBeforeItem.put(new Stat(stat));
/*     */     }
/*  69 */     return statsBeforeItem;
/*     */   }
/*     */   
/*     */   private void calcStatsDiff(StatCollection statsAfterItem, StatCollection statsBeforeItem, Map<Object, Integer> result) {
/*  73 */     Set<Short> allIds = new HashSet<Short>();
/*  74 */     allIds.addAll(statsAfterItem.getStatIds());
/*  75 */     allIds.addAll(statsBeforeItem.getStatIds());
/*     */     
/*  77 */     for (Short id : allIds) {
/*  78 */       int before = getStatSum(id, statsBeforeItem);
/*  79 */       int after = getStatSum(id, statsAfterItem);
/*     */       
/*  81 */       if (after != before) {
/*  82 */         result.put(id, Integer.valueOf(after - before));
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private int getStatSum(Short id, StatCollection statsBeforeItem) {
/*  88 */     Stat beforeStat = statsBeforeItem.get(id);
/*  89 */     int before = (beforeStat != null) ? beforeStat.getSum() : 0;
/*  90 */     return before;
/*     */   }
/*     */   
/*     */   private int evalPower(InventoryItem inventoryItem) {
/*  94 */     int power = 0;
/*     */     
/*  96 */     for (Ability ability : inventoryItem.getAbilities()) {
/*  97 */       if (ability instanceof EffectFilter) {
/*  98 */         EffectFilter filter = (EffectFilter)ability;
/*  99 */         if ("power".equals(filter.getId())) {
/* 100 */           StatEffect effectLogger = new StatEffect(null, null, null, Short.valueOf((short)12), Persistence.BASE, -1, Element.PHYSICAL);
/* 101 */           effectLogger.setAbilityValue("powerFactor", 1.0F);
/* 102 */           filter.filter(effectLogger);
/*     */           
/* 104 */           power += -effectLogger.getBonusValue();
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 109 */     return power;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int extractArmourSum(Map<Object, Integer> mutableSimpleStats) {
/* 116 */     int armour = 0;
/* 117 */     for (Element element : Element.values()) {
/* 118 */       Short resistanceId = StatId.getId(element);
/* 119 */       Integer resistance = mutableSimpleStats.get(resistanceId);
/* 120 */       if (resistance != null) {
/* 121 */         armour += resistance.intValue();
/*     */       }
/*     */     } 
/* 124 */     return armour;
/*     */   }
/*     */   
/*     */   public int getArmourSum(StatCollection statCollection) {
/* 128 */     Map<Short, Integer> simplifiedStats = statCollection.getSimplifiedStats();
/* 129 */     Map<Object, Integer> convertedDueToGenericLimitation = new HashMap<Object, Integer>();
/* 130 */     convertedDueToGenericLimitation.putAll(simplifiedStats);
/* 131 */     return extractArmourSum(convertedDueToGenericLimitation);
/*     */   }
/*     */   
/*     */   public int getPowerSum(List<ClientItem> items) {
/* 135 */     int powerSum = 0;
/*     */     
/* 137 */     for (ClientItem item : items) {
/* 138 */       if (item != null) {
/* 139 */         powerSum += evalPower((InventoryItem)item);
/*     */       }
/*     */     } 
/*     */     
/* 143 */     return powerSum;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\TCGItemAnalyzer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */