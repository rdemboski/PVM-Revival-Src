/*    */ package com.funcom.rpgengine2.loot;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class LootManager {
/*  9 */   private Map<String, Double> picksPerMobTypes = new HashMap<String, Double>(); public static final boolean MakeAllCoinsAndXpMagnetic = true;
/* 10 */   private Map<String, String> lootModelPerMobTypes = new HashMap<String, String>();
/* 11 */   private Map<String, List<LootDescription>> groupLootMap = new HashMap<String, List<LootDescription>>();
/* 12 */   private Map<Integer, List<LootDescription>> levelLootMap = new HashMap<Integer, List<LootDescription>>();
/*    */   
/*    */   public void clearData() {
/* 15 */     this.picksPerMobTypes.clear();
/* 16 */     this.lootModelPerMobTypes.clear();
/* 17 */     this.groupLootMap.clear();
/* 18 */     this.levelLootMap.clear();
/*    */   }
/*    */   
/*    */   public void addGroupLootCard(String groupId, LootDescription lootDescription) {
/* 22 */     List<LootDescription> lootDeck = this.groupLootMap.get(groupId);
/* 23 */     if (lootDeck == null) {
/* 24 */       lootDeck = new ArrayList<LootDescription>();
/* 25 */       this.groupLootMap.put(groupId, lootDeck);
/*    */     } 
/*    */     
/* 28 */     expandIntoDeck(lootDescription, lootDeck);
/*    */   }
/*    */   
/*    */   private void expandIntoDeck(LootDescription descriptionToExpand, List<LootDescription> lootDeck) {
/* 32 */     int expandCount = descriptionToExpand.getNumberOfPicks();
/*    */ 
/*    */     
/* 35 */     boolean isNoLootDrop = descriptionToExpand.getItemName().equalsIgnoreCase("no-loot-drop");
/* 36 */     if (isNoLootDrop) {
/* 37 */       descriptionToExpand = null;
/*    */     }
/*    */ 
/*    */     
/* 41 */     for (int i = 0; i < expandCount; i++) {
/* 42 */       lootDeck.add(descriptionToExpand);
/*    */     }
/*    */   }
/*    */   
/*    */   public List<LootDescription> getGroupLootDeck(String id) {
/* 47 */     return this.groupLootMap.get(id);
/*    */   }
/*    */   
/*    */   public List<LootDescription> getLevelLootDeck(Integer level) {
/* 51 */     return this.levelLootMap.get(level);
/*    */   }
/*    */   
/*    */   public void addLootCard(LevelLootDescription lootDescription) {
/* 55 */     for (int i = lootDescription.getFromLevel(); i <= lootDescription.getToLevel(); i++) {
/* 56 */       List<LootDescription> levelLoot = this.levelLootMap.get(Integer.valueOf(i));
/* 57 */       if (levelLoot == null) {
/* 58 */         levelLoot = new ArrayList<LootDescription>();
/* 59 */         this.levelLootMap.put(Integer.valueOf(i), levelLoot);
/*    */       } 
/* 61 */       boolean isNoLootDrop = lootDescription.getItemName().equalsIgnoreCase("no-loot-drop");
/* 62 */       for (int j = 0; j < lootDescription.getNumberOfPicks(); j++) {
/* 63 */         if (isNoLootDrop) {
/* 64 */           levelLoot.add(null);
/*    */         } else {
/* 66 */           levelLoot.add(lootDescription);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public void addMobType(String mobType, double picks) {
/* 73 */     this.picksPerMobTypes.put(mobType, Double.valueOf(picks));
/*    */   }
/*    */   
/*    */   public Map<String, Double> getPicksPerMobTypes() {
/* 77 */     return this.picksPerMobTypes;
/*    */   }
/*    */   
/*    */   public void addMobTypeLootModel(String mobType, String model) {
/* 81 */     this.lootModelPerMobTypes.put(mobType, model);
/*    */   }
/*    */   
/*    */   public String getLootModel(String mobType) {
/* 85 */     return this.lootModelPerMobTypes.get(mobType);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loot\LootManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */