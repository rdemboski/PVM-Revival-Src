/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InventoryItem
/*    */ {
/*    */   private String itemId;
/*    */   private int slotId;
/*    */   private int amount;
/*    */   private String costId;
/*    */   private int costAmount;
/*    */   private int tier;
/*    */   
/*    */   public InventoryItem(String itemId, int tier, int slotId, int amount, String costId, int costAmount) {
/* 21 */     this.itemId = itemId;
/* 22 */     this.tier = tier;
/* 23 */     this.slotId = slotId;
/* 24 */     this.amount = amount;
/* 25 */     this.costId = costId;
/* 26 */     this.costAmount = costAmount;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 30 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 34 */     return this.slotId;
/*    */   }
/*    */   
/*    */   public int getAmount() {
/* 38 */     return this.amount;
/*    */   }
/*    */   
/*    */   public String getCostId() {
/* 42 */     return this.costId;
/*    */   }
/*    */   
/*    */   public int getCostAmount() {
/* 46 */     return this.costAmount;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 50 */     StringBuffer sb = new StringBuffer();
/* 51 */     sb.append("[").append("itemId=").append(this.itemId).append(",slotId=").append(this.slotId).append(",amount=").append(this.amount).append(",costId=").append(this.costId).append(",costAmount=").append(this.costAmount).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 58 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 62 */     return this.tier;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\InventoryItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */