/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jmex.bui.event.BEvent;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BHoverOnlyLabel
/*    */   extends BLabel2
/*    */ {
/*    */   public BHoverOnlyLabel() {
/* 12 */     super("");
/*    */   }
/*    */   
/*    */   public BHoverOnlyLabel(String text) {
/* 16 */     super(text);
/*    */   }
/*    */   
/*    */   public BHoverOnlyLabel(String text, String styleClass) {
/* 20 */     super(text, styleClass);
/*    */   }
/*    */   
/*    */   public BHoverOnlyLabel(BIcon icon) {
/* 24 */     super(icon);
/*    */   }
/*    */   
/*    */   public BHoverOnlyLabel(BIcon icon, String styleClass) {
/* 28 */     super(icon, styleClass);
/*    */   }
/*    */   
/*    */   public BHoverOnlyLabel(BIcon icon, String text, String styleClass) {
/* 32 */     super(icon, text, styleClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean dispatchEvent(BEvent event) {
/* 37 */     if (event instanceof com.jmex.bui.event.MouseEvent) {
/* 38 */       return getParent().dispatchEvent(event);
/*    */     }
/* 40 */     return super.dispatchEvent(event);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BHoverOnlyLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */