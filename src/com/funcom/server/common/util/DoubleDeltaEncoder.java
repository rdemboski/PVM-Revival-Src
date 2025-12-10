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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleDeltaEncoder
/*     */ {
/*     */   public static final int MAX_ENTRY_BYTES = 9;
/*  24 */   private ByteArrayBitSet bitSet = new ByteArrayBitSet();
/*  25 */   private OpenByteArray bout = new OpenByteArray();
/*     */   
/*     */   private float errorTolerance;
/*     */   
/*     */   private double byteMaxLen;
/*     */   
/*     */   private double shortMaxLen;
/*     */   
/*     */   private double intMaxLen;
/*     */   private double integerSegment;
/*     */   private boolean finalized;
/*     */   
/*     */   public DoubleDeltaEncoder(float errorTolerance) {
/*  38 */     this.errorTolerance = errorTolerance;
/*     */     
/*  40 */     this.integerSegment = (errorTolerance / 2.0F);
/*  41 */     this.byteMaxLen = this.integerSegment * 255.0D;
/*  42 */     this.shortMaxLen = this.integerSegment * 65535.0D;
/*  43 */     this.intMaxLen = this.integerSegment * 4.294967295E9D;
/*     */   }
/*     */   
/*     */   public float getErrorTolerance() {
/*  47 */     return this.errorTolerance;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double encode(double from, double to) {
/*  59 */     if (this.finalized) {
/*  60 */       throw new IllegalStateException("Data is already finalized, illegal to append data");
/*     */     }
/*     */     
/*  63 */     double diff = to - from;
/*  64 */     double diffLen = (diff >= 0.0D) ? diff : -diff;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  74 */     if (diffLen < this.byteMaxLen) {
/*     */       
/*  76 */       this.bitSet.skip();
/*     */       
/*  78 */       writeSign((diff > 0.0D));
/*     */       
/*  80 */       int value = (int)((diffLen + this.integerSegment / 2.0D) / this.integerSegment);
/*  81 */       if (value > 255) {
/*  82 */         value = 255;
/*     */       }
/*  84 */       this.bout.write(value);
/*     */       
/*  86 */       if (diff < 0.0D) {
/*  87 */         return from - value * this.integerSegment;
/*     */       }
/*  89 */       return from + value * this.integerSegment;
/*  90 */     }  if (diffLen < this.shortMaxLen) {
/*     */       
/*  92 */       this.bitSet.set();
/*  93 */       this.bitSet.set();
/*  94 */       this.bitSet.skip();
/*     */       
/*  96 */       writeSign((diff > 0.0D));
/*     */       
/*  98 */       int value = (int)((diffLen + this.integerSegment / 2.0D) / this.integerSegment);
/*  99 */       if (value > 65535) {
/* 100 */         value = 65535;
/*     */       }
/* 102 */       this.bout.writeShort(value);
/*     */       
/* 104 */       if (diff < 0.0D) {
/* 105 */         return from - value * this.integerSegment;
/*     */       }
/* 107 */       return from + value * this.integerSegment;
/* 108 */     }  if (diffLen < this.intMaxLen) {
/*     */       
/* 110 */       this.bitSet.set();
/* 111 */       this.bitSet.set();
/* 112 */       this.bitSet.set();
/* 113 */       this.bitSet.skip();
/*     */       
/* 115 */       writeSign((diff > 0.0D));
/*     */       
/* 117 */       long value = (long)((diffLen + this.integerSegment / 2.0D) / this.integerSegment);
/* 118 */       if (value > 4294967295L) {
/* 119 */         value = 4294967295L;
/*     */       }
/* 121 */       this.bout.writeInt((int)value);
/*     */       
/* 123 */       if (diff < 0.0D) {
/* 124 */         return from - value * this.integerSegment;
/*     */       }
/* 126 */       return from + value * this.integerSegment;
/*     */     } 
/*     */     
/* 129 */     this.bitSet.set();
/* 130 */     this.bitSet.set();
/* 131 */     this.bitSet.set();
/* 132 */     this.bitSet.set();
/*     */     
/* 134 */     this.bout.writeDouble(to);
/* 135 */     return to;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte[] finalizeData() {
/* 145 */     byte[] data = this.bitSet.getData();
/* 146 */     int headerBytes = this.bitSet.getByteSize();
/*     */     
/* 148 */     this.bout.prepend(headerBytes + 4);
/* 149 */     this.bout.writeInt(headerBytes, 0);
/*     */     
/* 151 */     this.bout.write(data, 0, headerBytes, 4);
/*     */     
/* 153 */     this.finalized = true;
/*     */     
/* 155 */     return this.bout.getBuffer();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getFinalizedDataSize() {
/* 162 */     if (this.finalized) {
/* 163 */       return this.bout.size();
/*     */     }
/*     */     
/* 166 */     return this.bitSet.getByteSize() + 4 + this.bout.size();
/*     */   }
/*     */   
/*     */   private void writeSign(boolean isPositive) {
/* 170 */     if (isPositive) {
/* 171 */       this.bitSet.skip();
/*     */     } else {
/*     */       
/* 174 */       this.bitSet.set();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void encodeStopped() {
/* 182 */     this.bitSet.set();
/* 183 */     this.bitSet.skip();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\DoubleDeltaEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */