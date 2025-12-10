/*     */ package com.funcom.server.common.util;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LongDeltaDecoder
/*     */ {
/*     */   public static final int TYPE_STOP_SIGNAL = 1;
/*     */   private OpenByteArray byteArr;
/*     */   private ByteArrayBitSet bitSet;
/*     */   
/*     */   public LongDeltaDecoder(byte[] data) {
/*  13 */     this.byteArr = new OpenByteArray(data);
/*  14 */     int bitSetDataSize = this.byteArr.readInt();
/*  15 */     this.byteArr.skip(bitSetDataSize);
/*     */     
/*  17 */     this.bitSet = new ByteArrayBitSet(data, 4, bitSetDataSize);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long decode(long original) {
/*  27 */     int typeId = decodeType();
/*     */     
/*  29 */     return decodeValue(original, typeId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long decodeValue(long original, int typeId) {
/*     */     int i;
/*     */     long value;
/*  42 */     switch (typeId) {
/*     */       
/*     */       case 0:
/*  45 */         i = this.byteArr.get();
/*  46 */         if (this.bitSet.get()) {
/*  47 */           return original - i;
/*     */         }
/*  49 */         return original + i;
/*     */ 
/*     */ 
/*     */       
/*     */       case 1:
/*  54 */         return original;
/*     */ 
/*     */       
/*     */       case 2:
/*  58 */         i = this.byteArr.readShort();
/*  59 */         if (this.bitSet.get()) {
/*  60 */           return original - i;
/*     */         }
/*  62 */         return original + i;
/*     */ 
/*     */       
/*     */       case 3:
/*  66 */         value = this.byteArr.readInt() & 0xFFFFFFFFL;
/*  67 */         if (this.bitSet.get()) {
/*  68 */           return original - value;
/*     */         }
/*  70 */         return original + value;
/*     */     } 
/*     */ 
/*     */     
/*  74 */     return this.byteArr.readLong();
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
/*  85 */     if (this.bitSet.get()) {
/*  86 */       if (this.bitSet.get()) {
/*  87 */         if (this.bitSet.get()) {
/*  88 */           if (this.bitSet.get()) {
/*  89 */             return 4;
/*     */           }
/*  91 */           return 3;
/*     */         } 
/*     */         
/*  94 */         return 2;
/*     */       } 
/*     */       
/*  97 */       return 1;
/*     */     } 
/*     */     
/* 100 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\LongDeltaDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */