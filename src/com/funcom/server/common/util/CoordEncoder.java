/*     */ package com.funcom.server.common.util;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import java.awt.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CoordEncoder
/*     */ {
/*  12 */   private ByteArrayBitSet2 bitArray = new ByteArrayBitSet2();
/*  13 */   private OpenByteArray bout = new OpenByteArray();
/*     */   
/*     */   private double intMaxLen;
/*     */   
/*     */   private double integerSegment;
/*     */   private boolean finalized;
/*     */   public static final int HIGH_RESOLUTION_DIV = 255;
/*     */   
/*     */   public CoordEncoder(float errorThreshold) {
/*  22 */     this.integerSegment = (errorThreshold / 2.0F);
/*  23 */     this.intMaxLen = this.integerSegment * 4.294967295E9D;
/*     */   }
/*     */   
/*     */   public void reset() {
/*  27 */     this.bitArray.reset();
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
/*     */ 
/*     */   
/*     */   public void encode(Point oldPos, Vector2d oldOffset, Point pos, Vector2d offset) {
/*  41 */     if (this.finalized) {
/*  42 */       throw new IllegalStateException("Data is already finalized, illegal to append data");
/*     */     }
/*     */ 
/*     */     
/*  46 */     int diffX = pos.x - oldPos.x;
/*  47 */     int diffY = pos.y - oldPos.y;
/*     */     
/*  49 */     if (diffX == 0 && diffY == 0) {
/*     */       
/*  51 */       this.bitArray.setOff();
/*     */     } else {
/*     */       
/*  54 */       this.bitArray.setOn();
/*  55 */       int lenX = (diffX >= 0) ? diffX : -diffX;
/*  56 */       int lenY = (diffY >= 0) ? diffY : -diffY;
/*     */       
/*  58 */       if (lenX < 16 && lenY < 16) {
/*     */         
/*  60 */         this.bitArray.setOff();
/*     */ 
/*     */         
/*  63 */         if (diffX < 0) {
/*  64 */           this.bitArray.setOn();
/*     */         } else {
/*  66 */           this.bitArray.setOff();
/*     */         } 
/*  68 */         if (diffY < 0) {
/*  69 */           this.bitArray.setOn();
/*     */         } else {
/*  71 */           this.bitArray.setOff();
/*     */         } 
/*     */         
/*  74 */         this.bout.write(lenX << 4 | lenY);
/*     */       } else {
/*  76 */         this.bitArray.setOn();
/*  77 */         encode((diffX >= 0), lenX);
/*  78 */         encode((diffY >= 0), lenY);
/*     */       } 
/*     */       
/*  81 */       oldPos.x = pos.x;
/*  82 */       oldPos.y = pos.y;
/*     */     } 
/*     */ 
/*     */     
/*  86 */     double offsetDiffX = offset.getX() - oldOffset.getX();
/*  87 */     double offsetDiffY = offset.getY() - oldOffset.getY();
/*     */     
/*  89 */     if (offsetDiffX == 0.0D && offsetDiffY == 0.0D) {
/*     */       
/*  91 */       this.bitArray.setOff();
/*     */     } else {
/*     */       
/*  94 */       this.bitArray.setOn();
/*  95 */       double offsetLenX = (offsetDiffX >= 0.0D) ? offsetDiffX : -offsetDiffX;
/*  96 */       double offsetLenY = (offsetDiffY >= 0.0D) ? offsetDiffY : -offsetDiffY;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 105 */       if (offsetLenX <= this.intMaxLen) {
/* 106 */         oldOffset.setX(oldOffset.getX() + encodeDouble(offsetDiffX));
/*     */       }
/*     */       else {
/*     */         
/* 110 */         this.bitArray.setOff();
/*     */         
/* 112 */         this.bitArray.setOn();
/* 113 */         this.bitArray.setOn();
/* 114 */         this.bitArray.setOn();
/*     */         
/* 116 */         this.bout.writeDouble(offset.getX());
/* 117 */         oldOffset.setX(offset.getX());
/*     */       } 
/* 119 */       if (offsetLenY <= this.intMaxLen) {
/* 120 */         oldOffset.setY(oldOffset.getY() + encodeDouble(offsetDiffY));
/*     */       }
/*     */       else {
/*     */         
/* 124 */         this.bitArray.setOff();
/*     */         
/* 126 */         this.bitArray.setOn();
/* 127 */         this.bitArray.setOn();
/* 128 */         this.bitArray.setOn();
/*     */         
/* 130 */         this.bout.writeDouble(offset.getY());
/* 131 */         oldOffset.setY(offset.getY());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private double encodeDouble(double val) {
/* 137 */     double len = (val >= 0.0D) ? val : -val;
/* 138 */     double resolution = this.integerSegment;
/*     */ 
/*     */     
/* 141 */     if (len >= resolution) {
/*     */       
/* 143 */       this.bitArray.setOff();
/*     */     } else {
/*     */       
/* 146 */       this.bitArray.setOn();
/* 147 */       resolution /= 255.0D;
/*     */     } 
/*     */     
/* 150 */     long writeValue = (long)(len / resolution);
/* 151 */     if (writeValue <= 255L) {
/*     */       
/* 153 */       this.bitArray.setOff();
/* 154 */       if (val >= 0.0D) {
/* 155 */         this.bitArray.setOff();
/*     */       } else {
/* 157 */         this.bitArray.setOn();
/*     */       } 
/* 159 */       this.bout.write((int)writeValue);
/* 160 */     } else if (writeValue <= 65535L) {
/*     */       
/* 162 */       this.bitArray.setOn();
/* 163 */       this.bitArray.setOff();
/* 164 */       if (val >= 0.0D) {
/* 165 */         this.bitArray.setOff();
/*     */       } else {
/* 167 */         this.bitArray.setOn();
/*     */       } 
/* 169 */       this.bout.writeShort((int)writeValue);
/*     */     } else {
/*     */       
/* 172 */       if (writeValue > 4294967295L) {
/* 173 */         writeValue = 4294967295L;
/*     */       }
/* 175 */       this.bitArray.setOn();
/* 176 */       this.bitArray.setOn();
/* 177 */       this.bitArray.setOff();
/* 178 */       if (val >= 0.0D) {
/* 179 */         this.bitArray.setOff();
/*     */       } else {
/* 181 */         this.bitArray.setOn();
/*     */       } 
/* 183 */       this.bout.writeInt((int)writeValue);
/*     */     } 
/*     */     
/* 186 */     if (val >= 0.0D) {
/* 187 */       return writeValue * resolution;
/*     */     }
/* 189 */     return -writeValue * resolution;
/*     */   }
/*     */ 
/*     */   
/*     */   private void encode(boolean isPositive, int len) {
/* 194 */     if (len <= 255) {
/*     */       
/* 196 */       this.bitArray.setOff();
/*     */       
/* 198 */       if (isPositive) {
/* 199 */         this.bitArray.setOff();
/*     */       } else {
/* 201 */         this.bitArray.setOn();
/*     */       } 
/*     */       
/* 204 */       this.bout.write(len);
/* 205 */     } else if (len <= 65535) {
/*     */       
/* 207 */       this.bitArray.setOn();
/* 208 */       this.bitArray.setOff();
/*     */       
/* 210 */       if (isPositive) {
/* 211 */         this.bitArray.setOff();
/*     */       } else {
/* 213 */         this.bitArray.setOn();
/*     */       } 
/*     */       
/* 216 */       this.bout.writeShort(len);
/*     */     } else {
/*     */       
/* 219 */       this.bitArray.setOn();
/* 220 */       this.bitArray.setOn();
/*     */       
/* 222 */       if (isPositive) {
/* 223 */         this.bitArray.setOff();
/*     */       } else {
/* 225 */         this.bitArray.setOn();
/*     */       } 
/*     */       
/* 228 */       this.bout.writeInt(len);
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getFinalizedDataSize() {
/* 233 */     if (this.finalized) {
/* 234 */       return this.bout.size();
/*     */     }
/*     */     
/* 237 */     return this.bitArray.getByteSize() + 4 + this.bout.size();
/*     */   }
/*     */   
/*     */   public byte[] finalizeData() {
/* 241 */     byte[] data = this.bitArray.getData();
/* 242 */     int headerBytes = this.bitArray.getByteSize();
/*     */     
/* 244 */     this.bout.prepend(headerBytes + 4);
/* 245 */     this.bout.writeInt(headerBytes, 0);
/*     */     
/* 247 */     this.bout.write(data, 0, headerBytes, 4);
/*     */     
/* 249 */     this.finalized = true;
/*     */     
/* 251 */     return this.bout.getBuffer();
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/* 255 */     long old = 0L;
/* 256 */     for (int i = 1; i <= 50; i++) {
/* 257 */       double d = 1.0D / i;
/*     */       
/* 259 */       long l = Double.doubleToLongBits(d);
/* 260 */       long diff = l ^ old;
/* 261 */       System.out.println(sameLength(diff) + "\t" + d);
/* 262 */       old = l;
/*     */     } 
/*     */   }
/*     */   
/*     */   private static String sameLength(long diff) {
/* 267 */     String s = Long.toBinaryString(diff);
/* 268 */     return "0000000000000000000000000000000000000000000000000000000000000000".substring(s.length()) + s;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\commo\\util\CoordEncoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */