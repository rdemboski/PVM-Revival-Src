/*    */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*    */ 
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.util.TextureManager;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.nio.ByteBuffer;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultImageWrapper
/*    */   implements ImageWrapper
/*    */ {
/*    */   private final BufferedImage image;
/*    */   protected boolean grayscale;
/*    */   private boolean hasAlpha;
/*    */   private int origWidth;
/*    */   private int origHeight;
/*    */   private final TextureSetup setup;
/*    */   
/*    */   public DefaultImageWrapper(BufferedInputStream bufferedInputStream, TextureSetup setup) throws IOException {
/* 28 */     this.setup = setup;
/* 29 */     BufferedImage origImage = ImageIO.read(bufferedInputStream);
/*    */     try {
/* 31 */       bufferedInputStream.close();
/* 32 */     } catch (IOException ignore) {}
/*    */     
/* 34 */     this.origWidth = origImage.getWidth();
/* 35 */     this.origHeight = origImage.getHeight();
/* 36 */     this.image = origImage;
/*    */     
/* 38 */     this.grayscale = TextureManager.isGreyscale(origImage);
/* 39 */     this.hasAlpha = TextureManager.hasAlpha(origImage);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPower2Width() {
/* 44 */     return this.image.getWidth();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPower2Height() {
/* 49 */     return this.image.getHeight();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOriginalWidth() {
/* 54 */     return this.origWidth;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getOriginalHeight() {
/* 59 */     return this.origHeight;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBytesPerPixel() {
/*    */     int bytesPerPixel;
/* 65 */     if (this.grayscale) {
/* 66 */       bytesPerPixel = 1;
/* 67 */     } else if (this.hasAlpha) {
/* 68 */       bytesPerPixel = 4;
/*    */     } else {
/* 70 */       bytesPerPixel = 3;
/*    */     } 
/* 72 */     return bytesPerPixel;
/*    */   }
/*    */ 
/*    */   
/*    */   public Texture loadTexture(boolean toPowerOf2) {
/* 77 */     BufferedImage tempImage = this.image;
/* 78 */     if (toPowerOf2) {
/* 79 */       tempImage = ImageWrapperFactory.makeSmallestPower2(this.image);
/*    */     }
/* 81 */     Texture texture = TextureManager.loadTexture(tempImage, Texture.MinificationFilter.BilinearNearestMipMap, Texture.MagnificationFilter.Bilinear, 0.0F, true);
/*    */ 
/*    */     
/* 84 */     this.setup.setup(texture);
/* 85 */     return texture;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void setTargetBuffer(ByteBuffer pixelBuffer) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer getTargetBuffer() {
/* 95 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\DefaultImageWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */