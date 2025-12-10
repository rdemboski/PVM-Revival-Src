/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.creatures.StatSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LevelStat
/*    */   extends Stat
/*    */ {
/*    */   private final StatSupport parent;
/*    */   private final LevelingEvaluator levelingEvaluator;
/*    */   
/*    */   public LevelStat(Short id, StatSupport parent, LevelingEvaluator levelingEvaluator) {
/* 19 */     super(id, -1);
/* 20 */     this.parent = parent;
/* 21 */     this.levelingEvaluator = levelingEvaluator;
/*    */   }
/*    */   
/*    */   public int getBase() {
/* 25 */     return this.levelingEvaluator.levelOf(getXPStat().getSum());
/*    */   }
/*    */   
/*    */   public void setBase(int permanentValue) {
/* 29 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   public void addBase(int toAdd) {
/* 33 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   private Stat getXPStat() {
/* 37 */     return this.parent.getStatById((short)0);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\LevelStat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */