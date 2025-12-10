/*    */ package com.jmex.bui;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BCustomToggleButton
/*    */   extends BCustomButton
/*    */ {
/*    */   public static final int SELECTED = 4;
/*    */   public static final int DISSELECTED = 5;
/*    */   protected boolean _selected;
/*    */   protected ButtonGroup buttonGroup;
/*    */   protected static final int STATE_COUNT = 6;
/* 17 */   protected static final String[] STATE_PCLASSES = new String[] { "selected", "disselected" };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSelected() {
/* 25 */     return this._selected;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 34 */     if (this._selected != selected) {
/* 35 */       this._selected = selected;
/* 36 */       stateDidChange();
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setButtonGroup(ButtonGroup buttonGroup) {
/* 41 */     this.buttonGroup = buttonGroup;
/*    */   }
/*    */   
/*    */   public ButtonGroup getButtonGroup() {
/* 45 */     return this.buttonGroup;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 51 */     return "tab";
/*    */   }
/*    */ 
/*    */   
/*    */   public int getState() {
/* 56 */     int state = super.getState();
/* 57 */     return this._selected ? ((state == 2) ? 5 : 4) : state;
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getStateCount() {
/* 62 */     return 6;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getStatePseudoClass(int state) {
/* 67 */     if (state >= 4) {
/* 68 */       return STATE_PCLASSES[state - 4];
/*    */     }
/* 70 */     return super.getStatePseudoClass(state);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void fireAction(long when, int modifiers) {
/* 78 */     this._selected = !this._selected;
/* 79 */     if (this.buttonGroup != null) {
/* 80 */       this.buttonGroup.setSelected(this, this._selected);
/*    */     }
/* 82 */     super.fireAction(when, modifiers);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BCustomToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */