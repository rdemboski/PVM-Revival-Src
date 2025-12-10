/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ public class LevelRange
/*    */ {
/*    */   int min;
/*    */   int max;
/*    */   
/*    */   public LevelRange(int min, int max) {
/* 10 */     this.min = min;
/* 11 */     this.max = max;
/*    */   }
/*    */   
/*    */   public boolean contains(int val) {
/* 15 */     return (this.min <= val && val <= this.max);
/*    */   }
/*    */   
/*    */   public int getMin() {
/* 19 */     return this.min;
/*    */   }
/*    */   
/*    */   public int getMax() {
/* 23 */     return this.max;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\LevelRange.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */