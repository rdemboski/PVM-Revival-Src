/*     */ package com.funcom.server.common.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DoubleDeltaDecoder
/*     */ {
/*     */   public static final int TYPE_STOP_SIGNAL = 1;
/*     */   private OpenByteArray byteArr;
/*     */   private ByteArrayBitSet bitSet;
/*     */   private double integerSegment;
/*     */   
/*     */   public DoubleDeltaDecoder(byte[] data, float errorTolerance) {
/*  14 */     this.byteArr = new OpenByteArray(data);
/*  15 */     int bitSetDataSize = this.byteArr.readInt();
/*  16 */     this.byteArr.skip(bitSetDataSize);
/*     */     
/*  18 */     this.bitSet = new ByteArrayBitSet(data, 4, bitSetDataSize);
/*     */     
/*  20 */     this.integerSegment = (errorTolerance / 2.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double decode(double original) {
/*  30 */     int typeId = decodeType();
/*     */     
/*  32 */     return decodeValue(original, typeId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double decodeValue(double original, int typeId) {
/*     */     int i;
/*     */     long value;
/*  45 */     switch (typeId) {
/*     */       
/*     */       case 0:
/*  48 */         i = this.byteArr.get();
/*  49 */         if (this.bitSet.get()) {
/*  50 */           return original - this.integerSegment * i;
/*     */         }
/*  52 */         return original + this.integerSegment * i;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*  57 */         return original;
/*     */ 
/*     */       
/*     */       case 2:
/*  61 */         i = this.byteArr.readShort();
/*  62 */         if (this.bitSet.get()) {
/*  63 */           return original - this.integerSegment * i;
/*     */         }
/*  65 */         return original + this.integerSegment * i;
/*     */ 
/*     */       
/*     */       case 3:
/*  69 */         value = this.byteArr.readInt() & 0xFFFFFFFFL;
/*  70 */         if (this.bitSet.get()) {
/*  71 */           return original - this.integerSegment * value;
/*     */         }
/*  73 */         return original + this.integerSegment * value;
/*     */     } 
/*     */ 
/*     */     
/*  77 */     return this.byteArr.readDouble();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int decodeType() {
/*  88 */     if (this.bitSet.get()) {
/*  89 */       if (this.bitSet.get()) {
/*  90 */         if (this.bitSet.get()) {
/*  91 */           if (this.bitSet.get()) {
/*  92 */             return 4;
/*     */           }
/*  94 */           return 3;
/*     */         } 
/*     */         
/*  97 */         return 2;
/*     */       } 
/*     */       
/* 100 */       return 1;
/*     */     } 
/*     */     
/* 103 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\DoubleDeltaDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */