/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Integer32Array
/*    */   extends AbstractDatatype
/*    */ {
/*    */   private Integer32[] array;
/*    */   
/*    */   public Integer32Array() {}
/*    */   
/*    */   public Integer32Array(Integer32[] array, Endianess endianess) {
/* 16 */     setEndianess(endianess);
/* 17 */     this.array = new Integer32[array.length];
/* 18 */     System.arraycopy(array, 0, this.array, 0, array.length);
/* 19 */     for (Integer32 integer32 : array) {
/* 20 */       integer32.setEndianess(endianess);
/*    */     }
/*    */   }
/*    */   
/*    */   public Integer32Array(ByteBuffer byteBuffer, Endianess endianess) {
/* 25 */     setEndianess(endianess);
/* 26 */     readValue(byteBuffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 31 */     Integer16 length = new Integer16(this.array.length, getEndianess());
/* 32 */     length.toByteBuffer(byteBuffer);
/*    */     
/* 34 */     for (Integer32 int32 : this.array)
/* 35 */       int32.toByteBuffer(byteBuffer); 
/* 36 */     return byteBuffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/* 41 */     Integer16 length = new Integer16(byteBuffer, getEndianess());
/* 42 */     this.array = new Integer32[length.getIntValue()];
/*    */     
/* 44 */     for (int i = 0; i < this.array.length; i++) {
/* 45 */       this.array[i] = new Integer32(byteBuffer, getEndianess());
/*    */     }
/*    */   }
/*    */   
/*    */   public int getSizeInBytes() {
/* 50 */     return 2 + this.array.length * 4;
/*    */   }
/*    */   
/*    */   public Integer32[] getArray() {
/* 54 */     return this.array;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\Integer32Array.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */