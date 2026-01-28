/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ import com.funcom.gameengine.resourcemanager.DrawableContextManager;
/*     */ import com.funcom.gameengine.resourcemanager.LoadException;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.AsyncTexture2D;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.ImageWrapper;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.ImageWrapperFactory;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.ReducioInputStream;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.ReducioWrapper;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.TextureData;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.TextureDataSimple;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.jme.image.Image;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ public class ReducioProxyTextureLoader extends ReducioEnabledLoader {
/*  28 */   private Boolean _supportsNonPowerOfTwo = null;
/*     */   private TextureLoader delegate;
/*     */   
/*     */   public ReducioProxyTextureLoader(TextureLoader delegate) {
/*  32 */     super(Texture.class);
/*  33 */     this.delegate = delegate;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*  38 */     if (this._supportsNonPowerOfTwo == null) {
/*  39 */       this._supportsNonPowerOfTwo = Boolean.valueOf((GLContext.getCapabilities()).GL_ARB_texture_non_power_of_two);
/*     */     }
/*     */     try {
/*  42 */       tryReducioImage(managedResource, this);
/*  43 */     } catch (LoadException e) {
/*  44 */       LOGGER.log((Priority)Level.INFO, "Trying to load Reducio pair, but failed: " + managedResource, (Throwable)e);
/*  45 */       loadNormalPngTexture(managedResource);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void tryReducioImage(ManagedResource<?> managedResource, AbstractLoader loader) throws LoadException {
/*     */     try {
/*  51 */       String relPath = managedResource.getName();
/*  52 */       String jpegPath = relPath.substring(0, relPath.lastIndexOf('.')) + ".jpeg";
/*     */       
/*  54 */       ReducioInputStream alphaStream = getAlphaStream(loader, relPath);
/*  55 */       if (alphaStream == null) {
/*  56 */         ImageWrapper wrapper = ImageWrapperFactory.createWrapper(jpegPath, getFileInputStream(jpegPath), managedResource.getParameters());
/*  57 */         loadReducio(managedResource, wrapper);
/*     */       } else {
/*  59 */         ReducioWrapper reducioWrapper = new ReducioWrapper(new ReducioInputStream("jpg", loader.getFileInputStream(jpegPath)), alphaStream);
/*  60 */         loadReducio(managedResource, (ImageWrapper)reducioWrapper);
/*     */       } 
/*  62 */     } catch (IOException e) {
/*  63 */       throw new LoadException(getResourceManager(), managedResource, e);
/*  64 */     } catch (InterruptedException e) {
/*  65 */       throw new LoadException(getResourceManager(), managedResource, e);
/*     */     } 
/*     */   }
/*     */   
            @SuppressWarnings("unchecked")
/*     */   private void loadReducio(ManagedResource<?> managedResource, ImageWrapper wrapper) throws LoadException {
/*  70 */     if (LoadingManager.USE) {
/*     */       
/*     */       try {
/*  73 */         boolean bCompressed = false;
/*  74 */         if (LoadingManager.USE_DDS_FORMAT) {
/*  75 */           bCompressed = true;
/*     */           
/*     */           try {
/*  78 */             String resourceName = managedResource.getName();
/*  79 */             if (resourceName.length() > 4) {
/*  80 */               String strFilenameDDS = resourceName.substring(0, resourceName.length() - 4) + ".dds";
/*  81 */               getFileInputStream(strFilenameDDS);
/*     */             }
/*     */             else {
/*     */               
/*  85 */               bCompressed = false;
/*     */             } 
/*  87 */           } catch (FileNotFoundException e) {
/*     */             
/*  89 */             bCompressed = false;
/*  90 */           } catch (IOException e) {
/*     */             
/*  92 */             bCompressed = false;
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/*  97 */         if (bCompressed) {
/*  98 */           throw new LoadException(getResourceManager(), managedResource, new Exception("Compressed texture available."));
/*     */         }
/* 100 */         boolean bMainThread = (Thread.currentThread().getName().compareTo("main") == 0);
/*     */ 
/*     */         
/* 103 */         if (DrawableContextManager.THREADED_OPENGL && (bMainThread || DrawableContextManager.set(-1L))) {
/* 104 */           Texture texture = wrapper.loadTexture(!this._supportsNonPowerOfTwo.booleanValue());
/*     */           
/* 106 */           AsyncTexture2D texture2D = new AsyncTexture2D(wrapper.getOriginalWidth(), wrapper.getOriginalHeight());
/* 107 */           texture2D.copyRealProperties(texture);
/*     */           
/* 109 */           TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 110 */           ts.setTexture((Texture)texture2D);
/* 111 */           ts.load();
/* 112 */           if (!bMainThread)
/* 113 */             DrawableContextManager.unset(); 
/* 114 */           texture.getImage().setData(new ArrayList());
/* 115 */           ((ManagedResource<AsyncTexture2D>)managedResource).setResource(texture2D);
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 124 */           Texture texture = wrapper.loadTexture(!this._supportsNonPowerOfTwo.booleanValue());
/*     */ 
/*     */           
/* 127 */           AsyncTexture2D texture2D = new AsyncTexture2D(wrapper.getOriginalWidth(), wrapper.getOriginalHeight());
/* 128 */           texture2D.copyRealProperties(texture);
/* 129 */           texture.getImage().setData(texture.getImage().getData());
/* 130 */           ((ManagedResource<AsyncTexture2D>)managedResource).setResource(texture2D);
/*     */         } 
/* 132 */       } catch (InterruptedException e) {
/* 133 */         throw new LoadException(getResourceManager(), managedResource, e);
/*     */       } 
/*     */     } else {
/*     */       
/* 137 */       boolean synchronizedLoad = getResourceManager().isMapLoadingHint();
/* 138 */       if (synchronizedLoad) {
/*     */         
/*     */         try {
/* 141 */           Texture texture = wrapper.loadTexture(!this._supportsNonPowerOfTwo.booleanValue());
/* 142 */           AsyncTexture2D texture2D = new AsyncTexture2D(wrapper.getOriginalWidth(), wrapper.getOriginalHeight());
/* 143 */           texture2D.copyRealProperties(texture);
/* 144 */           TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 145 */           ts.setTexture((Texture)texture2D);
/* 146 */           ts.load();
/* 147 */           texture.getImage().setData(new ArrayList());
/* 148 */           ((ManagedResource<AsyncTexture2D>)managedResource).setResource(texture2D);
/* 149 */         } catch (InterruptedException e) {
/* 150 */           throw new LoadException(getResourceManager(), managedResource, e);
/*     */         } 
/*     */       } else {
/* 153 */         loadData_Async(managedResource, wrapper);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
            @SuppressWarnings("unchecked")
/*     */   public void loadData_Async(ManagedResource<?> managedResource, ImageWrapper wrapper) throws LoadException {
/* 159 */     String resourceName = managedResource.getName();
/*     */ 
/*     */ 
/*     */     
/* 163 */     AsyncTexture2D substituteTexture = makeSmallSubstituteTexture(wrapper);
/*     */     
/* 165 */     ((ManagedResource<AsyncTexture2D>)managedResource).setResource(substituteTexture);
/*     */     
/* 167 */     TextureDataSimple textureDataSimple = new TextureDataSimple(resourceName, wrapper, substituteTexture);
/* 168 */     textureDataSimple.init();
/* 169 */     this.delegate.addToQueue((TextureData)textureDataSimple);
/*     */     
/* 171 */     this.delegate.ensureAsyncThread();
/*     */   }
/*     */   
/*     */   private AsyncTexture2D makeSmallSubstituteTexture(ImageWrapper wrapper) {
/* 175 */     AsyncTexture2D substituteTexture = new AsyncTexture2D(wrapper.getOriginalWidth(), wrapper.getOriginalHeight());
/* 176 */     ByteBuffer data = BufferUtils.createByteBuffer(4);
/* 177 */     IntBuffer buffer = data.asIntBuffer();
/* 178 */     while (buffer.hasRemaining())
/*     */     {
/* 180 */       buffer.put(0);
/*     */     }
/* 182 */     Image tmpImage = new Image(Image.Format.RGBA8, 1, 1, data);
/* 183 */     substituteTexture.setImage(tmpImage);
/* 184 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 185 */     ts.setTexture((Texture)substituteTexture);
/* 186 */     ts.load();
/* 187 */     return substituteTexture;
/*     */   }
/*     */   
/*     */   private void loadNormalPngTexture(ManagedResource<?> managedResource) throws LoadException {
/* 191 */     this.delegate.setResourceManager(getResourceManager());
/* 192 */     this.delegate.loadData(managedResource);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAsyncLoading() {
/* 197 */     return this.delegate.isAsyncLoading();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unloadData(ManagedResource<?> managedResource, Object unloadInfo) {
/* 202 */     this.delegate.unloadData(managedResource, unloadInfo);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getUnloadInfo(ManagedResource<?> managedResource) {
/* 207 */     return this.delegate.getUnloadInfo(managedResource);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\ReducioProxyTextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */