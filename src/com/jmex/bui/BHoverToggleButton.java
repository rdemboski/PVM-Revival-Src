/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.funcom.commons.jme.bui.IrregularToggleButton;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BHoverToggleButton
/*    */   extends IrregularToggleButton
/*    */ {
/*    */   public static final int SELECTED_HOVER = 6;
/*    */   protected static final int STATE_COUNT = 7;
/*    */   
/*    */   public BHoverToggleButton(String text) {
/* 16 */     super(text);
/*    */   }
/*    */   
/*    */   public BHoverToggleButton(String text, String action) {
/* 20 */     super(text, action);
/*    */   }
/*    */   
/*    */   public BHoverToggleButton(BIcon icon, String action) {
/* 24 */     super(icon, action);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getState() {
/* 30 */     int state = super.getState();
/* 31 */     return (this._selected && this._hover) ? 6 : state;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected int getStateCount() {
/* 37 */     return 7;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getStatePseudoClass(int state) {
/* 43 */     if (state >= 6) {
/* 44 */       return STATE_PCLASSES[state - 6];
/*    */     }
/* 46 */     return super.getStatePseudoClass(state);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 51 */   protected static final String[] STATE_PCLASSES = new String[] { "selected_hover" };
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BHoverToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */