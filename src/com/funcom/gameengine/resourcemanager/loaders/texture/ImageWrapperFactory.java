/*    */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*    */ 
/*    */ import com.funcom.util.DebugManager;
/*    */ import com.jme.util.TextureManager;
/*    */ import java.awt.Graphics;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.BufferedInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.IntBuffer;
/*    */ import java.util.Map;
/*    */ import org.lwjgl.opengl.GL11;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ImageWrapperFactory
/*    */ {
/* 21 */   private static int DEV_MAX_TEX_SIZE = 2048;
/*    */   private static int maxTextureSize;
/*    */   
/*    */   static {
/* 25 */     IntBuffer ints = ByteBuffer.allocateDirect(64).asIntBuffer();
/* 26 */     GL11.glGetInteger(3379, ints);
/* 27 */     maxTextureSize = ints.get(0);
/*    */   }
/*    */ 
/*    */   
/*    */   public static ImageWrapper createWrapper(String id, InputStream inputStream, Map<Object, Object> params) throws IOException, InterruptedException {
/* 32 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream, 8192);
/*    */     
/* 34 */     TextureSetup textureSetup = new TextureSetup(params);
/*    */     
/* 36 */     if (PngWrapper.isPng(bufferedInputStream)) {
/* 37 */       bufferedInputStream.mark(8192);
/* 38 */       PngWrapper ret = new PngWrapper(id, new BufferedInputStream(bufferedInputStream), textureSetup);
/* 39 */       ret.init();
/* 40 */       return ret;
/*    */     } 
/*    */     
/* 43 */     return new DefaultImageWrapper(bufferedInputStream, textureSetup);
/*    */   }
/*    */   
/*    */   public static BufferedImage makeSmallestPower2(BufferedImage image) {
/* 47 */     if (DebugManager.getInstance().isDebugEnabled())
/*    */     {
/* 49 */       if (image.getWidth() > DEV_MAX_TEX_SIZE || image.getHeight() > DEV_MAX_TEX_SIZE) {
/* 50 */         DebugManager.showMessage("Texture too big! " + image.getWidth() + "x" + image.getHeight());
/*    */       }
/*    */     }
/*    */     
/* 54 */     int power2Width = smallestPower2(image.getWidth());
/* 55 */     int power2Height = smallestPower2(image.getHeight());
/*    */ 
/*    */     
/* 58 */     if (power2Width > maxTextureSize) {
/* 59 */       power2Width = maxTextureSize;
/*    */     }
/* 61 */     if (power2Height > maxTextureSize) {
/* 62 */       power2Height = maxTextureSize;
/*    */     }
/* 64 */     if (power2Width != image.getWidth() || power2Height != image.getHeight())
/*    */     {
/* 66 */       image = resizeImage(image, power2Width, power2Height);
/*    */     }
/*    */     
/* 69 */     return image;
/*    */   }
/*    */   
/*    */   public static BufferedImage resizeImage(BufferedImage image, int width, int height) {
/*    */     int type;
/* 74 */     if (TextureManager.isGreyscale(image)) {
/* 75 */       type = 10;
/* 76 */     } else if (TextureManager.hasAlpha(image)) {
/* 77 */       type = 6;
/*    */     } else {
/* 79 */       type = 5;
/*    */     } 
/*    */     
/* 82 */     BufferedImage tmp = new BufferedImage(width, height, type);
/* 83 */     Graphics g = tmp.getGraphics();
/* 84 */     g.drawImage(image, 0, 0, width, height, null);
/* 85 */     g.dispose();
/* 86 */     image = tmp;
/* 87 */     return image;
/*    */   }
/*    */   
/*    */   public static int smallestPower2(int value) {
/* 91 */     int ret = 1;
/* 92 */     while ((value >>= 1) > 0) {
/* 93 */       ret <<= 1;
/*    */     }
/* 95 */     return ret;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\ImageWrapperFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */