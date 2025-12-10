/*    */ package com.funcom.gameengine.conanchat.datatypes;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ public class Integer32
/*    */   extends AbstractDatatype
/*    */ {
/*  9 */   public static final long MAXVALUE = Math.round(Math.pow(2.0D, 32.0D) - 1.0D);
/*    */   
/*    */   public static final long MINVALUE = 0L;
/*    */   
/*    */   public static final int SIZE_IN_BYTES = 4;
/*    */   
/*    */   private long value;
/*    */ 
/*    */   
/*    */   public Integer32() {}
/*    */ 
/*    */   
/*    */   public Integer32(long value) {
/* 22 */     this.value = value;
/*    */   }
/*    */   
/*    */   public Integer32(long value, Endianess endianess) {
/* 26 */     this(value);
/* 27 */     setEndianess(endianess);
/*    */   }
/*    */   
/*    */   public Integer32(ByteBuffer buffer, Endianess endianess) {
/* 31 */     setEndianess(endianess);
/* 32 */     readValue(buffer);
/*    */   }
/*    */   
/*    */   public long getValue() {
/* 36 */     return this.value;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/* 41 */     switch (getEndianess()) {
/*    */       case BIG_ENDIAN:
/* 43 */         writeBigEndian(byteBuffer);
/*    */         break;
/*    */       case LITTLE_ENDIAN:
/* 46 */         writeLittleEndian(byteBuffer);
/*    */         break;
/*    */     } 
/* 49 */     return byteBuffer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void readValue(ByteBuffer byteBuffer) {
/* 54 */     switch (getEndianess()) {
/*    */       case BIG_ENDIAN:
/* 56 */         readBigEndian(byteBuffer);
/*    */         break;
/*    */       case LITTLE_ENDIAN:
/* 59 */         readLittleEndian(byteBuffer);
/*    */         break;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void writeLittleEndian(ByteBuffer byteBuffer) {
/* 65 */     byteBuffer.put((byte)(int)(this.value & 0xFFL)).put((byte)(int)(this.value >> 8L & 0xFFL)).put((byte)(int)(this.value >> 16L & 0xFFL)).put((byte)(int)(this.value >> 24L & 0xFFL));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void readLittleEndian(ByteBuffer byteBuffer) {
/* 73 */     this.value = 0L;
/* 74 */     this.value += byteBuffer.get() & 0xFFL;
/* 75 */     this.value += byteBuffer.get() << 8L & 0xFFFFL;
/* 76 */     this.value += byteBuffer.get() << 16L & 0xFFFFFFL;
/* 77 */     this.value += byteBuffer.get() << 24L & 0xFFFFFFFFL;
/*    */   }
/*    */   
/*    */   private void writeBigEndian(ByteBuffer byteBuffer) {
/* 81 */     byteBuffer.put((byte)(int)(this.value >> 24L & 0xFFL)).put((byte)(int)(this.value >> 16L & 0xFFL)).put((byte)(int)(this.value >> 8L & 0xFFL)).put((byte)(int)(this.value & 0xFFL));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void readBigEndian(ByteBuffer byteBuffer) {
/* 89 */     this.value = 0L;
/* 90 */     this.value += byteBuffer.get() << 24L & 0xFFFFFFFFL;
/* 91 */     this.value += byteBuffer.get() << 16L & 0xFFFFFFL;
/* 92 */     this.value += byteBuffer.get() << 8L & 0xFFFFL;
/* 93 */     this.value += byteBuffer.get() & 0xFFL;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSizeInBytes() {
/* 98 */     return 4;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\Integer32.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */