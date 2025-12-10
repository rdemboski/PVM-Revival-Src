/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.creatures.StatSupport;
/*    */ 
/*    */ 
/*    */ public class LevelingLimitStat
/*    */   extends Stat
/*    */ {
/*    */   private final Limit limit;
/*    */   private final StatSupport parent;
/*    */   private final LevelingEvaluator levelingEvaluator;
/*    */   
/*    */   public LevelingLimitStat(Short id, StatSupport parent, Limit limit, LevelingEvaluator levelingEvaluator) {
/* 15 */     super(id, -1);
/*    */     
/* 17 */     if (limit == null) {
/* 18 */       throw new NullPointerException("limit is missing");
/*    */     }
/*    */     
/* 21 */     this.limit = limit;
/* 22 */     this.parent = parent;
/* 23 */     this.levelingEvaluator = levelingEvaluator;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBase() {
/* 28 */     Stat xp = getXPStat();
/* 29 */     if (this.limit == Limit.START) {
/* 30 */       return this.levelingEvaluator.getLevelStartByXp(xp.getSum());
/*    */     }
/* 32 */     return this.levelingEvaluator.getLevelEndByXp(xp.getSum());
/*    */   }
/*    */   
/*    */   public void setBase(int permanentValue) {
/* 36 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void addBase(int toAdd) {
/* 40 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   private Stat getXPStat() {
/* 44 */     return this.parent.getStatById((short)0);
/*    */   }
/*    */   
/*    */   public enum Limit {
/* 48 */     START, END;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\LevelingLimitStat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */