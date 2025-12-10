/*     */ package com.funcom.server.common.util;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OpenByteArray
/*     */   extends ByteArrayOutputStream
/*     */ {
/*  14 */   private byte[] tmp = new byte[8];
/*     */ 
/*     */   
/*     */   public OpenByteArray() {}
/*     */   
/*     */   public OpenByteArray(byte[] data) {
/*  20 */     this.buf = data;
/*     */   }
/*     */   
/*     */   public byte[] getBuffer() {
/*  24 */     return this.buf;
/*     */   }
/*     */   
/*     */   public void prepend(int byteCount) {
/*  28 */     byte[] tmp = this.buf;
/*     */     
/*  30 */     if (byteCount + this.count >= this.buf.length) {
/*  31 */       tmp = new byte[this.count + byteCount];
/*     */     }
/*     */     
/*  34 */     System.arraycopy(this.buf, 0, tmp, byteCount, this.count);
/*  35 */     Arrays.fill(tmp, 0, byteCount, (byte)0);
/*     */     
/*  37 */     this.buf = tmp;
/*  38 */     this.count += byteCount;
/*     */   }
/*     */   
/*     */   public void writeDouble(double doubleValue) {
/*  42 */     long value = Double.doubleToLongBits(doubleValue);
/*  43 */     writeLong(value);
/*     */   }
/*     */   
/*     */   public double readDouble() {
/*  47 */     long bits = readLong();
/*  48 */     return Double.longBitsToDouble(bits);
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeLong(long value) {
/*  53 */     this.tmp[0] = (byte)(int)(value >>> 56L);
/*  54 */     this.tmp[1] = (byte)(int)(value >>> 48L);
/*  55 */     this.tmp[2] = (byte)(int)(value >>> 40L);
/*  56 */     this.tmp[3] = (byte)(int)(value >>> 32L);
/*  57 */     this.tmp[4] = (byte)(int)(value >>> 24L);
/*  58 */     this.tmp[5] = (byte)(int)(value >>> 16L);
/*  59 */     this.tmp[6] = (byte)(int)(value >>> 8L);
/*  60 */     this.tmp[7] = (byte)(int)value;
/*  61 */     write(this.tmp, 0, 8);
/*     */   }
/*     */   
/*     */   public long readLong() {
/*  65 */     return (this.buf[this.count++] & 0xFFL) << 56L | (this.buf[this.count++] & 0xFFL) << 48L | (this.buf[this.count++] & 0xFFL) << 40L | (this.buf[this.count++] & 0xFFL) << 32L | (this.buf[this.count++] & 0xFFL) << 24L | (this.buf[this.count++] & 0xFFL) << 16L | (this.buf[this.count++] & 0xFFL) << 8L | this.buf[this.count++] & 0xFFL;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeShort(int value) {
/*  76 */     write(value >>> 8 & 0xFF);
/*  77 */     write(value & 0xFF);
/*     */   }
/*     */   
/*     */   public int readShort() {
/*  81 */     return (this.buf[this.count++] & 0xFF) << 8 | this.buf[this.count++] & 0xFF;
/*     */   }
/*     */ 
/*     */   
/*     */   public void writeFloat(float floatValue) {
/*  86 */     int value = Float.floatToIntBits(floatValue);
/*  87 */     write(value >>> 24 & 0xFF);
/*  88 */     write(value >>> 16 & 0xFF);
/*  89 */     write(value >>> 8 & 0xFF);
/*  90 */     write(value & 0xFF);
/*     */   }
/*     */   
/*     */   public void writeInt(int value) {
/*  94 */     write(value >>> 24 & 0xFF);
/*  95 */     write(value >>> 16 & 0xFF);
/*  96 */     write(value >>> 8 & 0xFF);
/*  97 */     write(value & 0xFF);
/*     */   }
/*     */   
/*     */   public float readFloat() {
/* 101 */     int value = readInt();
/* 102 */     return Float.intBitsToFloat(value);
/*     */   }
/*     */   
/*     */   public int readInt() {
/* 106 */     return (this.buf[this.count++] & 0xFF) << 24 | (this.buf[this.count++] & 0xFF) << 16 | (this.buf[this.count++] & 0xFF) << 8 | this.buf[this.count++] & 0xFF;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeInt(int value, int index) {
/* 113 */     this.buf[index++] = (byte)(value >>> 24 & 0xFF);
/* 114 */     this.buf[index++] = (byte)(value >>> 16 & 0xFF);
/* 115 */     this.buf[index++] = (byte)(value >>> 8 & 0xFF);
/* 116 */     this.buf[index] = (byte)(value & 0xFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public void write(byte[] data, int dataOffset, int dataLen, int targetOffset) {
/* 121 */     System.arraycopy(data, dataOffset, this.buf, targetOffset, dataLen);
/*     */   }
/*     */   
/*     */   public void skip(int bytesToSkip) {
/* 125 */     this.count += bytesToSkip;
/*     */   }
/*     */   
/*     */   public int get() {
/* 129 */     return this.buf[this.count++] & 0xFF;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\OpenByteArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */