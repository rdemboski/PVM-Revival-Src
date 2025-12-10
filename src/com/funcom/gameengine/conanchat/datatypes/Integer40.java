/*     */ package com.funcom.gameengine.conanchat.datatypes;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ public class Integer40
/*     */   extends AbstractDatatype
/*     */ {
/*   9 */   public static final long MAXVALUE = Math.round(Math.pow(2.0D, 40.0D) - 1.0D);
/*     */   
/*     */   public static final long MINVALUE = 0L;
/*     */   
/*     */   public static final int SIZE_IN_BYTES = 5;
/*     */   
/*     */   private long value;
/*     */ 
/*     */   
/*     */   public Integer40() {}
/*     */ 
/*     */   
/*     */   public Integer40(long value) {
/*  22 */     this.value = value;
/*     */   }
/*     */   
/*     */   public Integer40(long value, Endianess endianess) {
/*  26 */     this(value);
/*  27 */     setEndianess(endianess);
/*     */   }
/*     */   
/*     */   public Integer40(ByteBuffer buffer, Endianess endianess) {
/*  31 */     setEndianess(endianess);
/*  32 */     readValue(buffer);
/*     */   }
/*     */   
/*     */   public long getValue() {
/*  36 */     return this.value;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer toByteBuffer(ByteBuffer byteBuffer) {
/*  41 */     switch (getEndianess()) {
/*     */       case BIG_ENDIAN:
/*  43 */         writeBigEndian(byteBuffer);
/*     */         break;
/*     */       case LITTLE_ENDIAN:
/*  46 */         writeLittleEndian(byteBuffer);
/*     */         break;
/*     */     } 
/*  49 */     return byteBuffer;
/*     */   }
/*     */ 
/*     */   
/*     */   public void readValue(ByteBuffer byteBuffer) {
/*  54 */     switch (getEndianess()) {
/*     */       case BIG_ENDIAN:
/*  56 */         readBigEndian(byteBuffer);
/*     */         break;
/*     */       case LITTLE_ENDIAN:
/*  59 */         readLittleEndian(byteBuffer);
/*     */         break;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void writeLittleEndian(ByteBuffer byteBuffer) {
/*  65 */     byteBuffer.put((byte)(int)(this.value & 0xFFL)).put((byte)(int)(this.value >> 8L & 0xFFL)).put((byte)(int)(this.value >> 16L & 0xFFL)).put((byte)(int)(this.value >> 24L & 0xFFL)).put((byte)(int)(this.value >> 32L & 0xFFL));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readLittleEndian(ByteBuffer byteBuffer) {
/*  74 */     this.value = 0L;
/*  75 */     this.value += byteBuffer.get() & 0xFFL;
/*  76 */     this.value += byteBuffer.get() << 8L & 0xFFFFL;
/*  77 */     this.value += byteBuffer.get() << 16L & 0xFFFFFFL;
/*  78 */     this.value += byteBuffer.get() << 24L & 0xFFFFFFFFL;
/*  79 */     this.value += byteBuffer.get() << 32L & 0xFFFFFFFFFFL;
/*     */   }
/*     */   
/*     */   private void writeBigEndian(ByteBuffer byteBuffer) {
/*  83 */     byteBuffer.put((byte)(int)(this.value >> 32L & 0xFFL)).put((byte)(int)(this.value >> 24L & 0xFFL)).put((byte)(int)(this.value >> 16L & 0xFFL)).put((byte)(int)(this.value >> 8L & 0xFFL)).put((byte)(int)(this.value & 0xFFL));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readBigEndian(ByteBuffer byteBuffer) {
/*  92 */     this.value = 0L;
/*  93 */     this.value += byteBuffer.get() << 32L & 0xFFFFFFFFFFL;
/*  94 */     this.value += byteBuffer.get() << 24L & 0xFFFFFFFFL;
/*  95 */     this.value += byteBuffer.get() << 16L & 0xFFFFFFL;
/*  96 */     this.value += byteBuffer.get() << 8L & 0xFFFFL;
/*  97 */     this.value += byteBuffer.get() & 0xFFL;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSizeInBytes() {
/* 102 */     return 5;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\datatypes\Integer40.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */