/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoModel;
/*    */ 
/*    */ 
/*    */ 
/*    */ class HudInfoModelImpl
/*    */   implements HudInfoModel
/*    */ {
/*    */   private int priority;
/*    */   private String icon;
/*    */   private String text;
/*    */   
/*    */   public HudInfoModelImpl(int priority, String icon, String text) {
/* 15 */     this.priority = priority;
/* 16 */     this.icon = icon;
/* 17 */     this.text = text;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPriority() {
/* 22 */     return this.priority;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIconPath() {
/* 27 */     return this.icon;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 32 */     return this.text;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\HudInfoModelImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */