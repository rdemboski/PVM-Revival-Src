/*    */ package com.funcom.tcg.rpg.items;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VendorItem
/*    */ {
/*    */   private String itemId;
/*    */   private String costId;
/*    */   private int costAmount;
/*    */   private String sellId;
/*    */   private int sellAmount;
/*    */   private int tier;
/*    */   
/*    */   public VendorItem(String itemId, int tier, String costId, int costAmount, String sellId, int sellAmount) {
/* 17 */     this.itemId = itemId;
/* 18 */     this.tier = tier;
/* 19 */     this.costId = costId;
/* 20 */     this.costAmount = costAmount;
/* 21 */     this.sellId = sellId;
/* 22 */     this.sellAmount = sellAmount;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 26 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public String getCostId() {
/* 30 */     return this.costId;
/*    */   }
/*    */   
/*    */   public int getCostAmount() {
/* 34 */     return this.costAmount;
/*    */   }
/*    */   
/*    */   public String getSellId() {
/* 38 */     return this.sellId;
/*    */   }
/*    */   
/*    */   public int getSellAmount() {
/* 42 */     return this.sellAmount;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 46 */     StringBuffer sb = new StringBuffer();
/* 47 */     sb.append("[").append("itemId=").append(this.itemId).append(",costId=").append(this.costId).append(",costAmount=").append(this.costAmount).append(",sellId=").append(this.sellId).append(",sellAmount=").append(this.sellAmount).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 54 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 58 */     return this.tier;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\items\VendorItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */