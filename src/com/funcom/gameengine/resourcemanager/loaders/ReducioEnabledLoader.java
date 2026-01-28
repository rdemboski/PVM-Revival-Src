/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.funcom.gameengine.resourcemanager.loaders.texture.ReducioInputStream;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.imageio.ImageIO;
/*    */ import javax.imageio.ImageReadParam;
/*    */ import javax.imageio.ImageReader;
/*    */ 
/*    */ public abstract class ReducioEnabledLoader
/*    */   extends AbstractLoader {
/*    */   protected ReducioEnabledLoader(Class<?> resourceType) {
/* 16 */     super(resourceType);
/*    */   }
/*    */ 
/*    */   
/*    */   protected BufferedImage loadAlphaImage(AbstractLoader loader, String relPath, ManagedResource<?> managedResource) {
/* 21 */     InputStream alphaStream = null;
/*    */     
/*    */     try {
/* 24 */       String alphaPath = relPath.substring(0, relPath.lastIndexOf('.')) + ".alpha.jpeg";
/* 25 */       alphaStream = loader.getFileInputStream(alphaPath);
/* 26 */       return readImage(alphaStream, "jpeg");
/* 27 */     } catch (IOException e) {
/*    */       
/* 29 */       closeSafely(alphaStream, managedResource);
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     }
/*    */     finally {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 40 */       closeSafely(alphaStream, managedResource);
/*    */     }

/*    */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ReducioInputStream getAlphaStream(AbstractLoader loader, String relPath) {
/*    */     try {
/* 47 */       String jpgAlphaPath = relPath.substring(0, relPath.lastIndexOf('.')) + ".alpha.jpeg";
/* 48 */       return new ReducioInputStream("jpeg", loader.getFileInputStream(jpgAlphaPath));
/* 49 */     } catch (IOException e) {
/*    */       
/*    */       try {
/* 52 */         String pngAlphaPath = relPath.substring(0, relPath.lastIndexOf('.')) + ".alpha.png";
/* 53 */         return new ReducioInputStream("png", loader.getFileInputStream(pngAlphaPath));
/* 54 */       } catch (IOException e1) {
/* 55 */         return null;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   protected BufferedImage loadColorImage(AbstractLoader loader, String jpegPath, ManagedResource<?> managedResource) throws LoadException {
/* 61 */     InputStream colorStream = null;
/*    */     try {
/* 63 */       colorStream = loader.getFileInputStream(jpegPath);
/* 64 */       return readImage(colorStream, "jpeg");
/* 65 */     } catch (IOException e) {
/* 66 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 68 */       closeSafely(colorStream, managedResource);
/*    */     } 
/*    */   }
/*    */   
/*    */   private BufferedImage readImage(InputStream alphaStream, String readerName) throws IOException {
/* 73 */     ImageReader imageReader = ImageIO.getImageReadersByFormatName(readerName).next();
/* 74 */     ImageReadParam param = imageReader.getDefaultReadParam();
/* 75 */     imageReader.setInput(ImageIO.createImageInputStream(alphaStream), true, true);
/*    */     try {
/* 77 */       return imageReader.read(0, param);
/*    */     } finally {
/* 79 */       imageReader.dispose();
/* 80 */       alphaStream.close();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\ReducioEnabledLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */