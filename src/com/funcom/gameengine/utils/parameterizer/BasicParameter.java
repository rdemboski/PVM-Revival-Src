/*    */ package com.funcom.gameengine.utils.parameterizer;
/*    */ 
/*    */ public abstract class BasicParameter
/*    */   implements Parameter {
/*    */   private int max;
/*    */   private int min;
/*    */   
/*    */   public BasicParameter(int min, int max) {
/*  9 */     this.max = max;
/* 10 */     this.min = min;
/*    */   }
/*    */   
/*    */   protected void checkRange(int value) {
/* 14 */     if (value < this.min || value > this.max)
/* 15 */       throw new ParameterRangeException(value, this.min, this.max); 
/*    */   }
/*    */   
/*    */   public int getMinimum() {
/* 19 */     return this.min;
/*    */   }
/*    */   
/*    */   public int getMaximum() {
/* 23 */     return this.max;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\parameterizer\BasicParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */