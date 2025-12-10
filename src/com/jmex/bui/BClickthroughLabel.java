/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ 
/*    */ public class BClickthroughLabel
/*    */   extends BLabel2
/*    */ {
/*    */   public BClickthroughLabel() {
/*  9 */     super("");
/*    */   }
/*    */   
/*    */   public BClickthroughLabel(String text) {
/* 13 */     super(text);
/*    */   }
/*    */   
/*    */   public BClickthroughLabel(String text, String styleClass) {
/* 17 */     super(text, styleClass);
/*    */   }
/*    */   
/*    */   public BClickthroughLabel(BIcon icon) {
/* 21 */     super(icon);
/*    */   }
/*    */   
/*    */   public BClickthroughLabel(BIcon icon, String styleClass) {
/* 25 */     super(icon, styleClass);
/*    */   }
/*    */   
/*    */   public BClickthroughLabel(BIcon icon, String text, String styleClass) {
/* 29 */     super(icon, text, styleClass);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public BComponent getHitComponent(int mx, int my) {
/* 39 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BClickthroughLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */