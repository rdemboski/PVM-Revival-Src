/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Integer16
/*    */   extends AbstractDatatype
/*    */ {
/*    */   public static final int SIZE_IN_BYTES = 2;
/* 13 */   public static final long MAXVALUE = Math.round(Math.pow(2.0D, 16.0D) - 1.0D);
/*    */   
/*    */   public static final long MINVALUE = 0L;
/*    */   
/*    */   private int value;
/*    */   
/*    */   public Integer16() {}
/*    */   
/*    */   public Integer16(int value) {
/* 22 */     if (value > MAXVALUE)
/* 23 */       throw new IllegalArgumentException("Max value is " + MAXVALUE + ", value=" + value + " is too large."); 
/* 24 */     if (value < 0L)
/* 25 */       throw new IllegalArgumentException("Min value is 0, value=" + value + " is too small."); 
/* 26 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Integer16(int value, Endianess endianess) {
/* 30 */     this(value);
/* 31 */     setEndianess(endianess);
/*    */   }
/*    */   
/*    */   public Integer16(ByteBuffer buffer, Endianess endianess) {
/* 35 */     setEndianess(endianess);
/* 36 */     readValue(buffer);
/*    */   }
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 40 */     byte highByte = (byte)(this.value >> 8 & 0xFF);
/* 41 */     byte lowByte = (byte)(this.value & 0xFF);
/*    */     
/* 43 */     if (getEndianess().equals(Endianess.BIG_ENDIAN)) {
/* 44 */       byteBuffer.put(highByte);
/* 45 */       byteBuffer.put(lowByte);
/*    */     } else {
/* 47 */       byteBuffer.put(lowByte);
/* 48 */       byteBuffer.put(highByte);
/*    */     } 
/*    */     
/* 51 */     return byteBuffer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/*    */     byte highByte, lowByte;
/* 58 */     if (getEndianess().equals(Endianess.BIG_ENDIAN)) {
/* 59 */       highByte = byteBuffer.get();
/* 60 */       lowByte = byteBuffer.get();
/*    */     } else {
/* 62 */       lowByte = byteBuffer.get();
/* 63 */       highByte = byteBuffer.get();
/*    */     } 
/*    */     
/* 66 */     this.value = 0;
/* 67 */     this.value += (highByte & 0xFF) << 8;
/* 68 */     this.value += lowByte & 0xFF;
/*    */   }
/*    */   
/*    */   public int getSizeInBytes() {
/* 72 */     return 2;
/*    */   }
/*    */   
/*    */   public int getIntValue() {
/* 76 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\Integer16.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */