/*    */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*    */ 
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.imageio.ImageReadParam;
/*    */ import javax.imageio.ImageReader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReducioInputStream
/*    */ {
/*    */   private final InputStream stream;
/*    */   private final ImageReader imageReader;
/*    */   
/*    */   public ReducioInputStream(String formatName, InputStream stream) {
/* 20 */     this.stream = stream;
/*    */     
/*    */     try {
/* 23 */       this.imageReader = ImageIO.getImageReadersByFormatName(formatName).next();
/* 24 */       this.imageReader.setInput(ImageIO.createImageInputStream(stream), true, true);
/* 25 */     } catch (IOException e) {
/* 26 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public BufferedImage loadImage() throws IOException {
/*    */     try {
/* 32 */       ImageReadParam param = this.imageReader.getDefaultReadParam();
/* 33 */       return this.imageReader.read(0, param);
/*    */     } finally {
/* 35 */       this.imageReader.dispose();
/* 36 */       this.stream.close();
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getWidth() {
/*    */     try {
/* 42 */       return this.imageReader.getWidth(0);
/* 43 */     } catch (IOException e) {
/* 44 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public int getHeight() {
/*    */     try {
/* 50 */       return this.imageReader.getHeight(0);
/* 51 */     } catch (IOException e) {
/* 52 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\ReducioInputStream.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */