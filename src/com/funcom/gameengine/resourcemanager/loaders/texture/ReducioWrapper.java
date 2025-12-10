/*    */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*    */ 
/*    */ import com.funcom.commons.Reducio;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.util.TextureManager;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class ReducioWrapper
/*    */   implements ImageWrapper {
/*    */   private int origWidth;
/*    */   private int origHeight;
/*    */   private int power2Width;
/*    */   private int power2Height;
/*    */   private final ReducioInputStream colorStream;
/*    */   private final ReducioInputStream alphaStream;
/*    */   
/*    */   public ReducioWrapper(ReducioInputStream colorStream, ReducioInputStream alphaStream) throws IOException {
/* 20 */     this.colorStream = colorStream;
/* 21 */     this.alphaStream = alphaStream;
/*    */     
/* 23 */     this.origWidth = alphaStream.getWidth();
/* 24 */     this.origHeight = alphaStream.getHeight();
/* 25 */     this.power2Width = ImageWrapperFactory.smallestPower2(this.origWidth);
/* 26 */     this.power2Height = ImageWrapperFactory.smallestPower2(this.origHeight);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPower2Width() {
/* 31 */     return this.power2Width;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPower2Height() {
/* 36 */     return this.power2Height;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOriginalWidth() {
/* 41 */     return this.origWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOriginalHeight() {
/* 46 */     return this.origHeight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBytesPerPixel() {
/* 51 */     return 4;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Texture loadTexture(boolean toPowerOf2) {
/*    */     BufferedImage colorImage, alphaImage;
/*    */     try {
/* 59 */       colorImage = this.colorStream.loadImage();
/* 60 */     } catch (IOException e) {
/* 61 */       throw new RuntimeException("cannot load color stream", e);
/*    */     } 
/*    */     try {
/* 64 */       alphaImage = this.alphaStream.loadImage();
/* 65 */     } catch (IOException e) {
/* 66 */       throw new RuntimeException("cannot load alpha stream", e);
/*    */     } 
/*    */     
/* 69 */     BufferedImage image = Reducio.load(colorImage, alphaImage);
/* 70 */     image = ImageWrapperFactory.makeSmallestPower2(image);
/* 71 */     return TextureManager.loadTexture(image, Texture.MinificationFilter.BilinearNearestMipMap, Texture.MagnificationFilter.Bilinear, 0.0F, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTargetBuffer(ByteBuffer pixelBuffer) {}
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer getTargetBuffer() {
/* 83 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\ReducioWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */