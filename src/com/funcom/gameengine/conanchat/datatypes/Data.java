/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Data
/*    */   extends AbstractDatatype
/*    */ {
/*    */   private byte[] value;
/*    */   
/*    */   public Data() {}
/*    */   
/*    */   public Data(byte[] value) {
/* 18 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Data(ByteBuffer byteBuffer, Endianess endianess) {
/* 22 */     setEndianess(endianess);
/* 23 */     readValue(byteBuffer);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 34 */     (new Integer16(this.value.length)).toByteBuffer(byteBuffer);
/* 35 */     for (byte b : this.value)
/* 36 */       byteBuffer.put(b); 
/* 37 */     return byteBuffer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/* 51 */     int numBytes = (new Integer16(byteBuffer, getEndianess())).getIntValue();
/* 52 */     this.value = new byte[numBytes];
/* 53 */     byteBuffer.get(this.value);
/*    */   }
/*    */   
/*    */   public int getSizeInBytes() {
/* 57 */     if (this.value.length == 0) {
/* 58 */       return 2;
/*    */     }
/*    */     
/* 61 */     return 2 + this.value.length;
/*    */   }
/*    */   
/*    */   public byte[] getValue() {
/* 65 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\Data.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */