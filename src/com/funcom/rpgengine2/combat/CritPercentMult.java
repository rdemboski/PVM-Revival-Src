/*    */ package com.funcom.rpgengine2.combat;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CritPercentMult
/*    */ {
/*    */   private static final float Base = 52.23F;
/*    */   private static final float K = 54.40625F;
/*    */   
/*    */   public static float getDamageMultiplier(float critRating, float sourceLevel) {
/* 11 */     return critRating * 54.40625F / 52.23F * sourceLevel * 100.0F;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\CritPercentMult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */