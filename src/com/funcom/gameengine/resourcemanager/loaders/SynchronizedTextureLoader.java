/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.LoadException;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.AsyncTexture2D;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.ImageWrapperFactory;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.TextureSetup;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.TextureKey;
/*     */ import com.jme.util.TextureManager;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import javax.imageio.ImageIO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SynchronizedTextureLoader
/*     */   extends AbstractLoader
/*     */ {
/*     */   private static final int SYNCLOADED_MIN_MIPMAPPED_SIZE = 128;
/*     */   static final boolean FLIPPED = true;
/*  31 */   public static final boolean SMALL_IMAGE = "true".equalsIgnoreCase(System.getProperty("tcg.smalltexture"));
/*     */   
/*     */   public SynchronizedTextureLoader() {
/*  34 */     super(Texture.class);
/*     */   }
/*     */ 
/*     */   @SuppressWarnings("unchecked")
/*     */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*  39 */     InputStream inputStream = null;
/*     */     try {
/*  41 */       String resourceName = managedResource.getName();
/*  42 */       inputStream = getFileInputStream(resourceName);
/*  43 */       BufferedImage image = ImageIO.read(inputStream);
/*  44 */       int width = image.getWidth();
/*  45 */       int height = image.getHeight();
/*  46 */       if (SMALL_IMAGE && !resourceName.startsWith("pointer_")) {
/*     */ 
/*     */         
/*  49 */         int newW = ImageWrapperFactory.smallestPower2(width);
/*  50 */         int newH = ImageWrapperFactory.smallestPower2(height);
/*     */         
/*  52 */         if (newW > 256) {
/*  53 */           newW /= 4;
/*  54 */         } else if (newW > 4) {
/*  55 */           newW /= 2;
/*     */         } 
/*  57 */         if (newH > 256) {
/*  58 */           newH /= 4;
/*  59 */         } else if (newW > 4) {
/*  60 */           newH /= 2;
/*     */         } 
/*     */         
/*  63 */         image = ImageWrapperFactory.resizeImage(image, newW, newH);
/*     */       } 
/*     */       
/*  66 */       int maxDimension = Math.max(width, height);
/*  67 */       if (maxDimension < 128)
/*     */       {
/*  69 */         managedResource.setParameter(Texture.MinificationFilter.class, Texture.MinificationFilter.BilinearNoMipMaps);
/*     */       }
/*     */ 
/*     */       
/*  73 */       TextureSetup setup = new TextureSetup(managedResource.getParameters());
/*     */       
/*  75 */       Texture texture = TextureManager.loadTexture(image, setup.getMinFilter(), setup.getMagFilter(), 0.0F, true);
/*  76 */       setup.setup(texture);
/*  77 */       AsyncTexture2D asyncTexture2D = new AsyncTexture2D(width, height);
/*  78 */       texture.createSimpleClone((Texture)asyncTexture2D);
/*     */ 
/*     */ 
/*     */       
/*  82 */       TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  83 */       ts.setTexture(texture);
/*  84 */       ts.load();
/*     */       
/*  86 */       ((ManagedResource<AsyncTexture2D>)managedResource).setResource(asyncTexture2D);
/*  87 */     } catch (MalformedURLException e) {
/*  88 */       throw new LoadException(getResourceManager(), managedResource, e);
/*  89 */     } catch (IOException e) {
/*  90 */       throw new LoadException(getResourceManager(), managedResource, e);
/*     */     } finally {
/*  92 */       closeSafely(inputStream, managedResource);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloadData(ManagedResource<?> managedResource, Object unloadInfo) {
/*  98 */     TextureUnloadInfo textureUnloadInfo = (TextureUnloadInfo)unloadInfo;
/*  99 */     if (textureUnloadInfo != null) {
/* 100 */       textureUnloadInfo.unload();
/*     */     }
/*     */   }
/*     */ 
/*     */   @SuppressWarnings("unchecked")
/*     */   public Object getUnloadInfo(ManagedResource<?> managedResource) {
/* 106 */     Texture texture = (Texture)((ManagedResource<Texture>)managedResource).getResource();
/* 107 */     if (texture != null && texture.getTextureId() != 0) {
/* 108 */       return new TextureUnloadInfo(texture.getTextureKey(), texture.getTextureId());
/*     */     }
/*     */     
/* 111 */     return null;
/*     */   }
/*     */   
/*     */   private static class TextureUnloadInfo {
/*     */     private final TextureKey textureKey;
/*     */     private final int textureId;
/*     */     
/*     */     public TextureUnloadInfo(TextureKey textureKey, int textureId) {
/* 119 */       this.textureKey = textureKey;
/* 120 */       this.textureId = textureId;
/*     */     }
/*     */     
/*     */     private void unload() {
/* 124 */       TextureManager.releaseTexture(this.textureKey);
/*     */ 
/*     */       
/* 127 */       TextureState ts = (TextureState)Renderer.defaultStateList[5];
/*     */       try {
/* 129 */         ts.deleteTextureId(this.textureId);
/* 130 */       } catch (Exception ignore) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\SynchronizedTextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */