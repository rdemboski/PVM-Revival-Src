/*    */ package com.funcom.rpgengine2.loot;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LevelLootDescription
/*    */   extends LootDescription
/*    */ {
/*    */   private int fromLevel;
/*    */   private int toLevel;
/*    */   
/*    */   public LevelLootDescription(String itemName, int tier, int amount, int fromLevel, int toLevel, int numberOfPicks, LootType lootType) {
/* 12 */     super(itemName, tier, amount, numberOfPicks, lootType);
/* 13 */     this.fromLevel = fromLevel;
/* 14 */     this.toLevel = toLevel;
/*    */   }
/*    */   
/*    */   public int getFromLevel() {
/* 18 */     return this.fromLevel;
/*    */   }
/*    */   
/*    */   public int getToLevel() {
/* 22 */     return this.toLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loot\LevelLootDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */