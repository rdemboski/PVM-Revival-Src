/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGAggroRangeEvaluator
/*    */ {
/*    */   private final double maxRangeFactor;
/*    */   private final double exponent;
/*    */   private final double offset;
/*    */   
/*    */   public TCGAggroRangeEvaluator(double maxRangeFactor, double exponent) {
/* 16 */     this.maxRangeFactor = maxRangeFactor;
/* 17 */     this.exponent = exponent;
/* 18 */     this.offset = -Math.log(maxRangeFactor - 1.0D) / exponent;
/*    */   }
/*    */   
/*    */   public double getAggroRangeFactor(int levelDifference) {
/* 22 */     return this.maxRangeFactor / (1.0D + Math.exp(-(levelDifference + this.offset) * this.exponent));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGAggroRangeEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */