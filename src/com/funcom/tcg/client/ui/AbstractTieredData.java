/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractTieredData
/*    */   implements TieredData
/*    */ {
/*    */   public String getPathForIconReplacingTierStars() {
/* 11 */     return null;
/*    */   }
/*    */   
/*    */   public boolean isUsingCustomBackground() {
/* 15 */     return false;
/*    */   }
/*    */   
/*    */   public String getCustomImageBackgroundPath() {
/* 19 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtml() {
/* 24 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\AbstractTieredData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */