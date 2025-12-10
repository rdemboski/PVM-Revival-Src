/*     */ package com.funcom.server.common.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongDeltaEncoder
/*     */ {
/*     */   public static final int MAX_ENTRY_BYTES = 9;
/*  21 */   private ByteArrayBitSet bitSet = new ByteArrayBitSet();
/*  22 */   private OpenByteArray bout = new OpenByteArray();
/*     */   
/*     */   private boolean finalized;
/*     */   
/*     */   public void reset() {
/*  27 */     this.bitSet.reset();
/*  28 */     this.bout.reset();
/*  29 */     this.finalized = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encode(long from, long to) {
/*  39 */     if (this.finalized) {
/*  40 */       throw new IllegalStateException("Data is already finalized, illegal to append data");
/*     */     }
/*     */     
/*  43 */     long diff = to - from;
/*  44 */     long diffLen = (diff >= 0L) ? diff : -diff;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  54 */     if (diffLen <= 255L) {
/*     */       
/*  56 */       this.bitSet.skip();
/*     */       
/*  58 */       writeSign((diff > 0L));
/*     */       
/*  60 */       this.bout.write((int)diffLen);
/*  61 */     } else if (diffLen <= 65535L) {
/*     */       
/*  63 */       this.bitSet.set();
/*  64 */       this.bitSet.set();
/*  65 */       this.bitSet.skip();
/*     */       
/*  67 */       writeSign((diff > 0L));
/*     */       
/*  69 */       this.bout.writeShort((int)diffLen);
/*  70 */     } else if (diffLen <= 4294967295L) {
/*     */       
/*  72 */       this.bitSet.set();
/*  73 */       this.bitSet.set();
/*  74 */       this.bitSet.set();
/*  75 */       this.bitSet.skip();
/*     */       
/*  77 */       writeSign((diff > 0L));
/*     */       
/*  79 */       this.bout.writeInt((int)diffLen);
/*     */     } else {
/*     */       
/*  82 */       this.bitSet.set();
/*  83 */       this.bitSet.set();
/*  84 */       this.bitSet.set();
/*  85 */       this.bitSet.set();
/*     */       
/*  87 */       this.bout.writeLong(to);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] finalizeData() {
/*  97 */     byte[] data = this.bitSet.getData();
/*  98 */     int headerBytes = this.bitSet.getByteSize();
/*     */     
/* 100 */     this.bout.prepend(headerBytes + 4);
/* 101 */     this.bout.writeInt(headerBytes, 0);
/*     */     
/* 103 */     this.bout.write(data, 0, headerBytes, 4);
/*     */     
/* 105 */     this.finalized = true;
/*     */     
/* 107 */     return this.bout.getBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFinalizedDataSize() {
/* 114 */     if (this.finalized) {
/* 115 */       return this.bout.size();
/*     */     }
/*     */     
/* 118 */     return this.bitSet.getByteSize() + 4 + this.bout.size();
/*     */   }
/*     */   
/*     */   private void writeSign(boolean isPositive) {
/* 122 */     if (isPositive) {
/* 123 */       this.bitSet.skip();
/*     */     } else {
/*     */       
/* 126 */       this.bitSet.set();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encodeStopped() {
/* 134 */     this.bitSet.set();
/* 135 */     this.bitSet.skip();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\LongDeltaEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */