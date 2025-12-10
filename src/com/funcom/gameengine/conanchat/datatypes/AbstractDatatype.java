/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractDatatype
/*    */   implements Datatype
/*    */ {
/* 10 */   private Endianess endianess = Endianess.BIG_ENDIAN;
/*    */   
/*    */   public Endianess getEndianess() {
/* 13 */     return this.endianess;
/*    */   }
/*    */   
/*    */   public void setEndianess(Endianess endianess) {
/* 17 */     this.endianess = endianess;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\AbstractDatatype.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */