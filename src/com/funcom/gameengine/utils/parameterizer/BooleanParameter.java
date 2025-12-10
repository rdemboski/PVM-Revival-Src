/*    */ package com.funcom.gameengine.utils.parameterizer;
/*    */ 
/*    */ public abstract class BooleanParameter
/*    */   extends BasicParameter {
/*    */   protected BooleanParameter() {
/*  6 */     super(0, 1);
/*    */   }
/*    */   
/*    */   public void setAsIntegerValue(int value) throws ParameterRangeException {
/* 10 */     checkRange(value);
/* 11 */     set((value != 0));
/*    */   }
/*    */   
/*    */   public int getAsIntegerValue() {
/* 15 */     return get() ? 1 : 0;
/*    */   }
/*    */   
/*    */   protected abstract boolean get();
/*    */   
/*    */   protected abstract void set(boolean paramBoolean);
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\parameterizer\BooleanParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */