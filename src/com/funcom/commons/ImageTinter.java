/*     */ package com.funcom.commons;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.RenderingHints;
/*     */ import java.awt.geom.Point2D;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.awt.image.BufferedImageOp;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.awt.image.DirectColorModel;
/*     */ 
/*     */ public class ImageTinter
/*     */   implements BufferedImageOp
/*     */ {
/*     */   private final Color mixColor;
/*     */   private final float mixValue;
/*     */   
/*     */   public Rectangle2D getBounds2D(BufferedImage src) {
/*  20 */     return new Rectangle(0, 0, src.getWidth(), src.getHeight());
/*     */   }
/*     */   private int[] preMultipliedRed; private int[] preMultipliedGreen; private int[] preMultipliedBlue;
/*     */   
/*     */   public BufferedImage createCompatibleDestImage(BufferedImage src, ColorModel destCM) {
/*  25 */     if (destCM == null) {
/*  26 */       destCM = src.getColorModel();
/*     */     }
/*     */     
/*  29 */     return new BufferedImage(destCM, destCM.createCompatibleWritableRaster(src.getWidth(), src.getHeight()), destCM.isAlphaPremultiplied(), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Point2D getPoint2D(Point2D srcPt, Point2D dstPt) {
/*  36 */     return (Point2D)srcPt.clone();
/*     */   }
/*     */   
/*     */   public RenderingHints getRenderingHints() {
/*  40 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageTinter(Color mixColor, float mixValue) {
/*  51 */     if (mixColor == null) {
/*  52 */       throw new IllegalArgumentException("mixColor cannot be null");
/*     */     }
/*     */     
/*  55 */     this.mixColor = mixColor;
/*  56 */     if (mixValue < 0.0F) {
/*  57 */       mixValue = 0.0F;
/*  58 */     } else if (mixValue > 1.0F) {
/*  59 */       mixValue = 1.0F;
/*     */     } 
/*  61 */     this.mixValue = mixValue;
/*     */     
/*  63 */     int mix_r = (int)(mixColor.getRed() * mixValue);
/*  64 */     int mix_g = (int)(mixColor.getGreen() * mixValue);
/*  65 */     int mix_b = (int)(mixColor.getBlue() * mixValue);
/*     */     
/*  67 */     float factor = 1.0F - mixValue;
/*  68 */     this.preMultipliedRed = new int[256];
/*  69 */     this.preMultipliedGreen = new int[256];
/*  70 */     this.preMultipliedBlue = new int[256];
/*     */     
/*  72 */     for (int i = 0; i < 256; i++) {
/*  73 */       int value = (int)(i * factor);
/*  74 */       this.preMultipliedRed[i] = value + mix_r;
/*  75 */       this.preMultipliedGreen[i] = value + mix_g;
/*  76 */       this.preMultipliedBlue[i] = value + mix_b;
/*     */     } 
/*     */   }
/*     */   
/*     */   public float getMixValue() {
/*  81 */     return this.mixValue;
/*     */   }
/*     */   
/*     */   public Color getMixColor() {
/*  85 */     return this.mixColor;
/*     */   }
/*     */   
/*     */   public BufferedImage filter(BufferedImage src, BufferedImage dst) {
/*  89 */     if (dst == null) {
/*  90 */       DirectColorModel directCM = new DirectColorModel(32, 16711680, 65280, 255, -16777216);
/*     */       
/*  92 */       dst = createCompatibleDestImage(src, directCM);
/*     */     } 
/*     */     
/*  95 */     int width = src.getWidth();
/*  96 */     int height = src.getHeight();
/*     */     
/*  98 */     int[] pixels = new int[width * height];
/*  99 */     GraphicsUtilities.getPixels(src, 0, 0, width, height, pixels);
/* 100 */     mixColor(pixels);
/* 101 */     GraphicsUtilities.setPixels(dst, 0, 0, width, height, pixels);
/*     */     
/* 103 */     return dst;
/*     */   }
/*     */   
/*     */   private void mixColor(int[] pixels) {
/* 107 */     for (int i = 0; i < pixels.length; i++) {
/* 108 */       int argb = pixels[i];
/* 109 */       pixels[i] = argb & 0xFF000000 | this.preMultipliedRed[argb >> 16 & 0xFF] << 16 | this.preMultipliedGreen[argb >> 8 & 0xFF] << 8 | this.preMultipliedBlue[argb & 0xFF];
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\ImageTinter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */