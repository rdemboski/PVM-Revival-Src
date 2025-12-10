/*    */ package com.funcom.rpgengine2.vendor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VendorItem
/*    */ {
/*    */   private String itemId;
/*    */   private int itemAmount;
/*    */   private String itemDescriptionText;
/*    */   private int tier;
/*    */   private int fromLevel;
/*    */   private int toLevel;
/*    */   private String itemCostId;
/*    */   private int itemCostAmount;
/*    */   
/*    */   public VendorItem(String itemId, int itemAmount, String itemDescriptionText, int tier, int fromLevel, int toLevel, String itemCostId, int itemCostAmount) {
/* 20 */     this.itemId = itemId;
/* 21 */     this.itemAmount = itemAmount;
/* 22 */     this.itemDescriptionText = itemDescriptionText;
/* 23 */     this.tier = tier;
/* 24 */     this.fromLevel = fromLevel;
/* 25 */     this.toLevel = toLevel;
/* 26 */     this.itemCostId = itemCostId;
/* 27 */     this.itemCostAmount = itemCostAmount;
/*    */   }
/*    */   
/*    */   public String getItemId() {
/* 31 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getItemAmount() {
/* 35 */     return this.itemAmount;
/*    */   }
/*    */   
/*    */   public String getItemDescriptionText() {
/* 39 */     return this.itemDescriptionText;
/*    */   }
/*    */   
/*    */   public int getFromLevel() {
/* 43 */     return this.fromLevel;
/*    */   }
/*    */   
/*    */   public int getToLevel() {
/* 47 */     return this.toLevel;
/*    */   }
/*    */   
/*    */   public String getItemCostId() {
/* 51 */     return this.itemCostId;
/*    */   }
/*    */   
/*    */   public int getItemCostAmount() {
/* 55 */     return this.itemCostAmount;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 59 */     StringBuffer sb = new StringBuffer();
/* 60 */     sb.append("[itemId=").append(this.itemId).append(",itemDescriptionText=").append(this.itemDescriptionText).append(",itemAmount=").append(this.itemAmount).append(",tier=").append(this.tier).append(",fromLevel=").append(this.fromLevel).append(",toLevel=").append(this.toLevel).append(",itemCostId=").append(this.itemCostId).append(",itemCostAmount=").append(this.itemCostAmount).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 69 */     return sb.toString();
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 73 */     return this.tier;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\vendor\VendorItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */