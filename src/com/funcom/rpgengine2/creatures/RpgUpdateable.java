/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ public interface RpgUpdateable
/*    */ {
/*    */   public static final int PRIORITY_MIN = 0;
/*    */   public static final int PRIORITY_CASTTIME = 499998;
/*    */   public static final int PRIORITY_DFX = 499999;
/*    */   public static final int PRIORITY_NORMAL = 500000;
/*    */   public static final int PRIORITY_MAX = 1000000;
/* 10 */   public static final int[] NORMAL_SINGLE_UPDATE = new int[] { 500000 };
/*    */   
/*    */   int[] getUpdatePriorities();
/*    */   
/*    */   void update(int paramInt, long paramLong);
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\RpgUpdateable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */