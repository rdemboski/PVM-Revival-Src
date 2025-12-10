/*    */ package com.funcom.gameengine.utils.parameterizer;
/*    */ 
/*    */ public abstract class IntegerParameter
/*    */   extends BasicParameter {
/*    */   public IntegerParameter() {
/*  6 */     this(0, 100);
/*    */   }
/*    */   
/*    */   public IntegerParameter(int min, int max) {
/* 10 */     super(min, max);
/*    */   }
/*    */   
/*    */   public void setAsIntegerValue(int value) throws IllegalArgumentException {
/* 14 */     checkRange(value);
/* 15 */     set(value);
/*    */   }
/*    */   
/*    */   public int getAsIntegerValue() {
/* 19 */     int i = get();
/* 20 */     checkRange(i);
/* 21 */     return i;
/*    */   }
/*    */   
/*    */   protected abstract int get();
/*    */   
/*    */   protected abstract void set(int paramInt);
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\parameterizer\IntegerParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */