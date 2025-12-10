/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LevelRangeManager
/*    */ {
/* 10 */   private List<LevelRange> levelRanges = new LinkedList<LevelRange>();
/*    */   
/*    */   public List<LevelRange> getLevelRanges() {
/* 13 */     return this.levelRanges;
/*    */   }
/*    */   
/*    */   public void addLevelRange(LevelRange range) {
/* 17 */     this.levelRanges.add(range);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\LevelRangeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */