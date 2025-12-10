/*    */ package com.funcom.server.common.util;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ByteArrayBitSet
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
/*    */   public ByteArrayBitSet() {
/* 23 */     this.data = new byte[8];
/*    */   }
/*    */   
/*    */   public ByteArrayBitSet(byte[] data, int readOffset, int length) {
/* 27 */     this.data = data;
/* 28 */     this.readOffset = readOffset;
/* 29 */     this.maxIndex = readOffset + length;
/*    */   }
/*    */   
/*    */   public void set(int index) {
/* 33 */     int byteIndex = index >> 3;
/* 34 */     ensureSize(byteIndex);
/* 35 */     this.data[byteIndex] = (byte)(this.data[byteIndex] | 1 << (index & 0x7));
/*    */     
/* 37 */     if (byteIndex > this.maxIndex) {
/* 38 */       this.maxIndex = byteIndex;
/*    */     }
/*    */   }
/*    */   
/*    */   private void ensureSize(int byteIndex) {
/* 43 */     if (byteIndex >= this.data.length) {
/* 44 */       this.data = Arrays.copyOf(this.data, this.data.length * 2);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean get() {
/* 49 */     int byteIndex = (this.index >> 3) + this.readOffset; return 
/* 50 */       ((this.data[byteIndex] & 0xFF & 1 << (this.index++ & 0x7)) != 0);
/*    */   }
/*    */   
/*    */   public byte[] getData() {
/* 54 */     return this.data;
/*    */   }
/*    */   
/*    */   public int getByteSize() {
/* 58 */     return this.maxIndex + 1;
/*    */   }
/*    */   
/*    */   public void skip(int bitsToSkip) {
/* 62 */     this.index += bitsToSkip;
/*    */   }
/*    */   
/*    */   public void set() {
/* 66 */     set(this.index++);
/*    */   }
/*    */   
/*    */   public void skip() {
/* 70 */     this.index++;
/*    */     
/* 72 */     int byteIndex = this.index >> 3;
/* 73 */     ensureSize(byteIndex);
/* 74 */     if (byteIndex > this.maxIndex)
/* 75 */       this.maxIndex = byteIndex; 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\ByteArrayBitSet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */