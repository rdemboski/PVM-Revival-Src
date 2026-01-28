/*    */ package com.funcom.gameengine.resourcemanager.loaders;
/*    */ 
/*    */ import com.funcom.commons.Reducio;
/*    */ import com.funcom.gameengine.resourcemanager.LoadException;
/*    */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*    */ import com.funcom.gameengine.resourcemanager.loaders.texture.AsyncTexture2D;
/*    */ import com.jme.image.Image;
/*    */ import com.jme.image.Texture;
/*    */ import com.jmex.bui.BImage;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import javax.imageio.ImageIO;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuiImageLoader
/*    */   extends ReducioEnabledLoader
/*    */ {
/*    */   public BuiImageLoader() {
/* 22 */     super(BImage.class);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   @SuppressWarnings("unchecked")
/*    */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*    */     try {
/* 31 */       ManagedResource<Texture> managedResourceT = this.resourceManager.getManagedResource(Texture.class, managedResource.getName());
/* 32 */       Texture texture = (Texture)managedResourceT.getResource();
/* 33 */       if (texture != null) {
/* 34 */         if (texture.getImage().getFormat() == Image.Format.NativeDXT5 || texture.getImage().getFormat() == Image.Format.NativeDXT1) {
/*    */           
/* 36 */           ((ManagedResource<BImage>)managedResource).setResource(new BImage(texture));
/*    */         } else {
/* 38 */           int width = (texture instanceof AsyncTexture2D) ? ((AsyncTexture2D)texture).getOriginalWidth() : texture.getImage().getWidth();
/* 39 */           int height = (texture instanceof AsyncTexture2D) ? ((AsyncTexture2D)texture).getOriginalHeight() : texture.getImage().getHeight();
/* 40 */           ((ManagedResource<BImage>)managedResource).setResource(new BImage(width, height, texture.getImage()));
/*    */         }
/*    */       
/*    */       }
/* 44 */     } catch (Exception e) {
/*    */       
/* 46 */       ((ManagedResource<BImage>)managedResource).setResource(null);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 51 */     if (managedResource.getResource() == null) {
/*    */       BufferedImage image;
/*    */       try {
/* 54 */         image = tryReducioImage(managedResource, this);
/* 55 */       } catch (LoadException e) {
/* 56 */         image = loadNormal(managedResource);
/*    */       } 
/*    */       
/* 59 */       ((ManagedResource<BImage>)managedResource).setResource(new BImage(image));
/*    */     } 
/*    */   }
/*    */   
/*    */   private BufferedImage tryReducioImage(ManagedResource<?> managedResource, AbstractLoader loader) throws LoadException {
/* 64 */     String relPath = managedResource.getName();
/* 65 */     String jpegPath = relPath.substring(0, relPath.lastIndexOf('.')) + ".jpeg";
/*    */     
/* 67 */     BufferedImage colorImage = loadColorImage(loader, jpegPath, managedResource);
/* 68 */     BufferedImage alphaImage = loadAlphaImage(loader, relPath, managedResource);
/* 69 */     if (alphaImage == null) {
/* 70 */       return colorImage;
/*    */     }
/* 72 */     return Reducio.load(colorImage, alphaImage);
/*    */   }
/*    */   
/*    */   private BufferedImage loadNormal(ManagedResource<?> managedResource) throws LoadException {
/* 76 */     InputStream inputStream = null;
/*    */     try {
/* 78 */       inputStream = getFileInputStream(managedResource.getName());
/* 79 */       return ImageIO.read(inputStream);
/* 80 */     } catch (IOException e) {
/* 81 */       throw new LoadException(getResourceManager(), managedResource, e);
/*    */     } finally {
/* 83 */       closeSafely(inputStream, managedResource);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\BuiImageLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */