/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.funcom.gameengine.resourcemanager.loaders.texture.ImageWrapperFactory;
/*    */ import com.jme.image.Image;
/*    */ import com.jme.util.TextureManager;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.MalformedURLException;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ public class JmeImageLoader
/*    */   extends AbstractLoader {
/*    */   static final boolean FLIPPED = true;
/* 17 */   public static final boolean SMALL_IMAGE = "true".equalsIgnoreCase(System.getProperty("tcg.smalltexture"));
/*    */   
/*    */   public JmeImageLoader() {
/* 20 */     super(Image.class);
/*    */   }
/*    */ 
/*    */   @SuppressWarnings("unchecked")
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/* 25 */     InputStream inputStream = null;
/*    */     try {
/* 27 */       String resourceName = managedResource.getName();
/* 28 */       inputStream = getFileInputStream(resourceName);
/* 29 */       BufferedImage image = ImageIO.read(inputStream);
/* 30 */       int width = image.getWidth();
/* 31 */       int height = image.getHeight();
/* 32 */       if (SMALL_IMAGE && !resourceName.startsWith("pointer_")) {
/*    */ 
/*    */         
/* 35 */         int newW = ImageWrapperFactory.smallestPower2(width);
/* 36 */         int newH = ImageWrapperFactory.smallestPower2(height);
/*    */         
/* 38 */         if (newW > 256) {
/* 39 */           newW /= 4;
/* 40 */         } else if (newW > 4) {
/* 41 */           newW /= 2;
/*    */         } 
/* 43 */         if (newH > 256) {
/* 44 */           newH /= 4;
/* 45 */         } else if (newW > 4) {
/* 46 */           newH /= 2;
/*    */         } 
/*    */         
/* 49 */         image = ImageWrapperFactory.resizeImage(image, newW, newH);
/*    */       } 
/*    */       
/* 52 */       Image jmeImage = TextureManager.loadImage(image, true);
/*    */       
/* 54 */       ((ManagedResource<Image>)managedResource).setResource(jmeImage);
/* 55 */     } catch (MalformedURLException e) {
/* 56 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 57 */     } catch (IOException e) {
/* 58 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 60 */       closeSafely(inputStream, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\JmeImageLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */