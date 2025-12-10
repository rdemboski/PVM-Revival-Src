/*    */ package com.funcom.gameengine.utils.parameterizer;
/*    */ 
/*    */ public abstract class FloatParameter
/*    */   extends BasicParameter {
/*    */   private static final float PERC = 100.0F;
/*    */   
/*    */   protected FloatParameter() {
/*  8 */     super(0, 100);
/*    */   }
/*    */   
/*    */   public void setAsIntegerValue(int value) throws ParameterRangeException {
/* 12 */     checkRange(value);
/* 13 */     set(value / 100.0F);
/*    */   }
/*    */   
/*    */   public int getAsIntegerValue() {
/* 17 */     int i = Math.round(get() * 100.0F);
/* 18 */     checkRange(i);
/* 19 */     return i;
/*    */   }
/*    */   
/*    */   protected abstract float get();
/*    */   
/*    */   protected abstract void set(float paramFloat);
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\parameterizer\FloatParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */