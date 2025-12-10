/*    */ package com.funcom.server.common.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ByteArrayBitSet2
/*    */ {
/*    */   private byte[] data;
/*    */   private int readOffset;
/* 11 */   private int maxIndex = -1;
/*    */   private int index;
/*    */   
/*    */   public void reset() {
/* 15 */     this.readOffset = 0;
/* 16 */     this.maxIndex = -1;
/* 17 */     this.index = 0;
/*    */     
/* 19 */     Arrays.fill(this.data, (byte)0);
/*    */   }
/*    */   
/*    */   public ByteArrayBitSet2() {
/* 23 */     this.data = new byte[8];
/*    */   }
/*    */   
/*    */   public ByteArrayBitSet2(byte[] data, int readOffset, int length) {
/* 27 */     this.data = data;
/* 28 */     this.readOffset = readOffset;
/* 29 */     this.maxIndex = readOffset + length;
/*    */   }
/*    */   
/*    */   public void setOn() {
/* 33 */     int byteIndex = this.index >> 3;
/* 34 */     ensureSize(byteIndex);
/* 35 */     this.data[byteIndex] = (byte)(this.data[byteIndex] | 1 << (this.index & 0x7));
/*    */     
/* 37 */     if (byteIndex > this.maxIndex) {
/* 38 */       this.maxIndex = byteIndex;
/*    */     }
/*    */     
/* 41 */     this.index++;
/*    */   }
/*    */   
/*    */   public void setOn(int count) {
/* 45 */     while (count-- > 0)
/*    */     {
/* 47 */       setOn();
/*    */     }
/*    */   }
/*    */   
/*    */   private void ensureSize(int byteIndex) {
/* 52 */     if (byteIndex >= this.data.length) {
/* 53 */       this.data = Arrays.copyOf(this.data, this.data.length * 2);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean get() {
/* 58 */     int byteIndex = (this.index >> 3) + this.readOffset; return 
/* 59 */       ((this.data[byteIndex] & 0xFF & 1 << (this.index++ & 0x7)) != 0);
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/* 63 */     return this.data;
/*    */   }
/*    */   
/*    */   public int getByteSize() {
/* 67 */     return this.maxIndex + 1;
/*    */   }
/*    */   
/*    */   public void skip(int bitsToSkip) {
/* 71 */     this.index += bitsToSkip;
/*    */     
/* 73 */     int byteIndex = this.index >> 3;
/* 74 */     ensureSize(byteIndex);
/* 75 */     if (byteIndex > this.maxIndex) {
/* 76 */       this.maxIndex = byteIndex;
/*    */     }
/*    */   }
/*    */   
/*    */   public void setOff() {
/* 81 */     this.index++;
/*    */     
/* 83 */     int byteIndex = this.index >> 3;
/* 84 */     ensureSize(byteIndex);
/* 85 */     if (byteIndex > this.maxIndex)
/* 86 */       this.maxIndex = byteIndex; 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\ByteArrayBitSet2.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */