/*    */ package com.funcom.rpgengine2.loot;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LootDescription
/*    */ {
/*    */   private String itemName;
/*    */   private int numberOfPicks;
/*    */   private int amount;
/*    */   private LootType lootType;
/*    */   private int tier;
/*    */   
/*    */   public LootDescription(String itemName, int tier, int amount, int numberOfPicks, LootType lootType) {
/* 16 */     this.itemName = itemName;
/* 17 */     this.tier = tier;
/* 18 */     this.amount = amount;
/* 19 */     this.numberOfPicks = numberOfPicks;
/* 20 */     this.lootType = lootType;
/*    */   }
/*    */   
/*    */   public String getItemName() {
/* 24 */     return this.itemName;
/*    */   }
/*    */   
/*    */   public int getAmount() {
/* 28 */     return this.amount;
/*    */   }
/*    */   
/*    */   public int getNumberOfPicks() {
/* 32 */     return this.numberOfPicks;
/*    */   }
/*    */   
/*    */   public boolean isPickUp() {
/* 36 */     return (this.lootType == LootType.PICKUP);
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 40 */     return this.tier;
/*    */   }
/*    */   
/*    */   public boolean isPortalDrop() {
/* 44 */     return (this.lootType == LootType.PORTAL);
/*    */   }
/*    */   
/*    */   public boolean isLootGroup() {
/* 48 */     return (this.lootType == LootType.LOOTGROUP);
/*    */   }
/*    */   
/*    */   public boolean isPet() {
/* 52 */     return (this.lootType == LootType.PET);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loot\LootDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */