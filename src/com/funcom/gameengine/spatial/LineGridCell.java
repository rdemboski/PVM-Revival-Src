/*    */ package com.funcom.gameengine.spatial;
/*    */ 
/*    */ import com.funcom.commons.geom.LineWCHeight;
/*    */ 
/*    */ class LineGridCell {
/*  6 */   private LineWCHeight[] lines = new LineWCHeight[0];
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void put(LineWCHeight newLine) {
/* 13 */     for (LineWCHeight line : this.lines) {
/* 14 */       if (line == newLine) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */     
/* 19 */     LineWCHeight[] tmp = new LineWCHeight[this.lines.length + 1];
/* 20 */     System.arraycopy(this.lines, 0, tmp, 0, this.lines.length);
/* 21 */     tmp[tmp.length - 1] = newLine;
/* 22 */     this.lines = tmp;
/*    */   }
/*    */   
/*    */   LineWCHeight[] getLines() {
/* 26 */     return this.lines;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\spatial\LineGridCell.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */