/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DamageCrutchMult
/*    */ {
/*  7 */   private static final double[] EXPONENTS = new double[600];
/*    */   
/*    */   static {
/* 10 */     for (int i = 0; i < EXPONENTS.length; i++) {
/* 11 */       EXPONENTS[i] = calcExponent(-300 + i);
/*    */     }
/*    */   }
/*    */   
/*    */   public static double getDamageMultiplier(int targetLevel, int sourceLevel) {
/* 16 */     return 1.0D / (1.0D + getExponent(300 + targetLevel - sourceLevel));
/*    */   }
/*    */   
/*    */   private static double getExponent(int sourceLevel) {
/* 20 */     if (sourceLevel < 0 || sourceLevel >= EXPONENTS.length) {
/* 21 */       return calcExponent(sourceLevel);
/*    */     }
/* 23 */     return EXPONENTS[sourceLevel];
/*    */   }
/*    */   
/*    */   private static double calcExponent(int levelDiff) {
/* 27 */     return Math.exp((-1 * (-1 * levelDiff + 6)));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\DamageCrutchMult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */