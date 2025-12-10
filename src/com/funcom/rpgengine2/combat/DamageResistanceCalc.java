/*   */ package com.funcom.rpgengine2.combat;
/*   */ 
/*   */ public class DamageResistanceCalc {
/*   */   private static final int LEVEL_MULT = 15;
/*   */   
/*   */   public static double getDamageMultiplier(int targetResistance, int sourceLevel) {
/* 7 */     return 1.0D - targetResistance / (targetResistance + sourceLevel * 15);
/*   */   }
/*   */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\DamageResistanceCalc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */