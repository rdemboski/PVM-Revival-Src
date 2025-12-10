/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ class TCGLevelingEvaluator
/*    */   implements LevelingEvaluator
/*    */ {
/*  7 */   static final TCGLevelingEvaluator INSTANCE = new TCGLevelingEvaluator();
/*    */   
/*    */   private static final int XP_FACTOR = 22;
/*    */   private static final double XP_EXPONENT = 3.0D;
/*    */   
/*    */   public int getLevelStartByXp(int xp) {
/* 13 */     int level = levelOf(xp);
/* 14 */     return xpForLevel(level);
/*    */   }
/*    */   
/*    */   public int getLevelEndByXp(int xp) {
/* 18 */     int level = levelOf(xp);
/* 19 */     return xpForLevel(level + 1);
/*    */   }
/*    */   
/*    */   public int levelOf(int xp) {
/* 23 */     if (xp < 0)
/* 24 */       throw new IllegalArgumentException("No negative XP!"); 
/* 25 */     return (int)(Math.pow(xp / 22.0D, 0.3333333333333333D) + 1.0D);
/*    */   }
/*    */   
/*    */   public int xpForLevel(int level) {
/* 29 */     if (level <= 0)
/* 30 */       throw new IllegalArgumentException("No negative or zero levels!"); 
/* 31 */     level--;
/* 32 */     return (int)(22.0D * Math.pow(level, 3.0D));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGLevelingEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */