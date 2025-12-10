/*     */ package com.funcom.server.common.util;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import java.awt.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoordDecoder
/*     */ {
/*     */   private OpenByteArray bin;
/*     */   private ByteArrayBitSet bitArray;
/*     */   private double integerSegment;
/*     */   
/*     */   public CoordDecoder(byte[] data, int dataSize, float errorThreshold) {
/*  19 */     this.bin = new OpenByteArray(data);
/*  20 */     int bitSetDataSize = this.bin.readInt();
/*  21 */     this.bin.skip(bitSetDataSize);
/*     */     
/*  23 */     this.bitArray = new ByteArrayBitSet(data, 4, bitSetDataSize);
/*     */     
/*  25 */     this.integerSegment = (errorThreshold / 2.0F);
/*     */   }
/*     */   
/*     */   public boolean decode(Point tilePos, Vector2d tileOffset) {
/*  29 */     boolean hasIntegerData = this.bitArray.get();
/*  30 */     if (hasIntegerData)
/*     */     {
/*  32 */       if (!this.bitArray.get()) {
/*     */ 
/*     */         
/*  35 */         int data = this.bin.get();
/*  36 */         if (this.bitArray.get()) {
/*  37 */           tilePos.x -= data >>> 4;
/*     */         } else {
/*  39 */           tilePos.x += data >>> 4;
/*     */         } 
/*     */         
/*  42 */         if (this.bitArray.get()) {
/*  43 */           tilePos.y -= data & 0xF;
/*     */         } else {
/*  45 */           tilePos.y += data & 0xF;
/*     */         } 
/*     */       } else {
/*  48 */         tilePos.x += decodeInt();
/*  49 */         tilePos.y += decodeInt();
/*     */       } 
/*     */     }
/*  52 */     if (this.bitArray.get()) {
/*     */       
/*  54 */       tileOffset.setX(decodeDouble2(tileOffset.getX()));
/*  55 */       tileOffset.setY(decodeDouble2(tileOffset.getY()));
/*  56 */       return true;
/*     */     } 
/*  58 */     return hasIntegerData;
/*     */   }
/*     */ 
/*     */   
/*     */   private double decodeDouble2(double oldValue) {
/*  63 */     double resolution = this.integerSegment;
/*  64 */     if (this.bitArray.get()) {
/*  65 */       resolution /= 255.0D;
/*     */     }
/*     */     
/*  68 */     if (!this.bitArray.get()) {
/*     */       
/*  70 */       if (this.bitArray.get()) {
/*  71 */         return oldValue - this.bin.get() * resolution;
/*     */       }
/*  73 */       return oldValue + this.bin.get() * resolution;
/*     */     } 
/*  75 */     if (!this.bitArray.get()) {
/*     */       
/*  77 */       if (this.bitArray.get()) {
/*  78 */         return oldValue - this.bin.readShort() * resolution;
/*     */       }
/*  80 */       return oldValue + this.bin.readShort() * resolution;
/*     */     } 
/*  82 */     if (!this.bitArray.get()) {
/*     */       
/*  84 */       if (this.bitArray.get()) {
/*  85 */         return oldValue - this.bin.readInt() * resolution;
/*     */       }
/*  87 */       return oldValue + this.bin.readInt() * resolution;
/*     */     } 
/*     */     
/*  90 */     return this.bin.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   private double decodeDouble(double oldValue) {
/*  95 */     if (!this.bitArray.get()) {
/*     */       
/*  97 */       if (this.bitArray.get()) {
/*  98 */         return oldValue - this.bin.get() * this.integerSegment;
/*     */       }
/* 100 */       return oldValue + this.bin.get() * this.integerSegment;
/*     */     } 
/* 102 */     if (!this.bitArray.get()) {
/*     */       
/* 104 */       if (this.bitArray.get()) {
/* 105 */         return oldValue - this.bin.readShort() * this.integerSegment;
/*     */       }
/* 107 */       return oldValue + this.bin.readShort() * this.integerSegment;
/*     */     } 
/* 109 */     if (!this.bitArray.get()) {
/*     */       
/* 111 */       if (this.bitArray.get()) {
/* 112 */         return oldValue - this.bin.readInt() * this.integerSegment;
/*     */       }
/* 114 */       return oldValue + this.bin.readInt() * this.integerSegment;
/*     */     } 
/*     */     
/* 117 */     return this.bin.readDouble();
/*     */   }
/*     */ 
/*     */   
/*     */   private int decodeInt() {
/* 122 */     if (!this.bitArray.get()) {
/*     */       
/* 124 */       if (this.bitArray.get()) {
/* 125 */         return -this.bin.get();
/*     */       }
/* 127 */       return this.bin.get();
/*     */     } 
/* 129 */     if (!this.bitArray.get()) {
/*     */       
/* 131 */       if (this.bitArray.get()) {
/* 132 */         return -this.bin.readShort();
/*     */       }
/* 134 */       return this.bin.readShort();
/*     */     } 
/*     */ 
/*     */     
/* 138 */     if (this.bitArray.get()) {
/* 139 */       return -this.bin.readInt();
/*     */     }
/* 141 */     return this.bin.readInt();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\CoordDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */