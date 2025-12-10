/*     */ package com.funcom.gameengine.resourcemanager.loaders;
/*     */ 
/*     */ import com.funcom.gameengine.model.token.GameTokenProcessor;
/*     */ import com.funcom.gameengine.resourcemanager.DrawableContextManager;
/*     */ import com.funcom.gameengine.resourcemanager.LoadException;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.AsyncTexture2D;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.ImageWrapper;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.ImageWrapperFactory;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.TextureData;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.texture.TextureDataSimple;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.util.DebugManager;
/*     */ import com.jme.image.Image;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.image.Texture2D;
/*     */ import com.jme.image.util.DDSLoader;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.lwjgl.LWJGLTextureState2;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.net.MalformedURLException;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.ArrayBlockingQueue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.lwjgl.opengl.GLContext;
/*     */ 
/*     */ public class TextureLoader extends SynchronizedTextureLoader {
/*  37 */   private static final boolean LOAD_QUALITY = "true".equalsIgnoreCase(System.getProperty("tcg.qualityinitload"));
/*  38 */   private Boolean _supportsNonPowerOfTwo = null;
/*     */   
/*  40 */   private static final Runnable TEXTURE_DATA_LOADER = new TextureDataLoader();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String FALLBACK_TEXTURE = "environment/global/no_mesh/blank.png";
/*     */ 
/*     */ 
/*     */   
/*  49 */   private static BlockingQueue<TextureData> loadDataQueue = new ArrayBlockingQueue<TextureData>(4096);
/*  50 */   private static AtomicReference<Thread> asyncLoadThread = new AtomicReference<Thread>();
/*     */ 
/*     */   
/*     */   private ByteBuffer tempPixelBuffer;
/*     */   
/*     */   private static final int THREAD_PRIORITY = 5;
/*     */   
/*  57 */   private static TextureState ts = null;
/*     */   public static void setTextureState(TextureState ts) {
/*  59 */     TextureLoader.ts = ts;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadData(ManagedResource<?> managedResource) throws LoadException {
/*  64 */     if (this._supportsNonPowerOfTwo == null) {
/*  65 */       this._supportsNonPowerOfTwo = Boolean.valueOf((GLContext.getCapabilities()).GL_ARB_texture_non_power_of_two);
/*     */     }
/*     */     
/*  68 */     if (LoadingManager.USE) {
/*  69 */       String resourceName = managedResource.getName();
/*  70 */       boolean bCompressed = true;
/*  71 */       InputStream fis = null;
/*     */       try {
/*  73 */         if (LoadingManager.USE_DDS_FORMAT) {
/*     */           
/*     */           try {
/*  76 */             if (resourceName.length() > 4 && resourceName.substring(0, 4).compareTo("gui/") == 0) {
/*  77 */               throw new FileNotFoundException();
/*     */             }
/*     */             
/*  80 */             if (resourceName.length() > 4) {
/*  81 */               String strFilenameDDS = resourceName.substring(0, resourceName.length() - 4) + ".dds";
/*  82 */               fis = getFileInputStream(strFilenameDDS);
/*     */             }
/*     */           
/*     */           }
/*  86 */           catch (FileNotFoundException e) {
/*  87 */             bCompressed = false;
/*  88 */             fis = getFileInputStream(resourceName);
/*     */           } 
/*     */         } else {
/*     */           
/*  92 */           bCompressed = false;
/*  93 */           fis = getFileInputStream(resourceName);
/*     */         } 
/*     */         
/*  96 */         boolean bMainThread = (Thread.currentThread().getName().compareTo("main") == 0);
/*     */ 
/*     */         
/*  99 */         if (DrawableContextManager.THREADED_OPENGL && (bMainThread || DrawableContextManager.set(-1L))) {
/* 100 */           if (bCompressed) {
/* 101 */             Texture2D texture2D = new Texture2D();
/* 102 */             texture2D.setImage(DDSLoader.loadImage(fis, false));
/* 103 */             texture2D.getImage().setData(texture2D.getImage().getData());
/* 104 */             managedResource.setResource(texture2D);
/*     */             
/* 106 */             LWJGLTextureState2 ts2 = new LWJGLTextureState2();
/* 107 */             ts2.setTexture((Texture)texture2D);
/* 108 */             ts2.load();
/* 109 */             if (!bMainThread) {
/* 110 */               DrawableContextManager.unset();
/*     */             }
/* 112 */             managedResource.setResource(texture2D);
/*     */           } else {
/*     */             
/* 115 */             ImageWrapper wrapper = ImageWrapperFactory.createWrapper(resourceName, fis, managedResource.getParameters());
/*     */             
/* 117 */             wrapper.setTargetBuffer(this.tempPixelBuffer);
/* 118 */             Texture texture = wrapper.loadTexture(!this._supportsNonPowerOfTwo.booleanValue());
/* 119 */             this.tempPixelBuffer = wrapper.getTargetBuffer();
/* 120 */             AsyncTexture2D texture2D = new AsyncTexture2D(wrapper.getOriginalWidth(), wrapper.getOriginalHeight());
/* 121 */             texture2D.copyRealProperties(texture);
/*     */             
/* 123 */             LWJGLTextureState2 ts2 = new LWJGLTextureState2();
/* 124 */             ts2.setTexture((Texture)texture2D);
/* 125 */             ts2.load();
/* 126 */             if (!bMainThread) {
/* 127 */               DrawableContextManager.unset();
/*     */             }
/* 129 */             texture.getImage().setData(new ArrayList());
/* 130 */             managedResource.setResource(texture2D);
/*     */           }
/*     */         
/*     */         }
/* 134 */         else if (bCompressed) {
/* 135 */           Texture2D texture2D = new Texture2D();
/* 136 */           texture2D.setImage(DDSLoader.loadImage(fis, false));
/* 137 */           texture2D.getImage().setData(texture2D.getImage().getData());
/* 138 */           texture2D.setMagnificationFilter(Texture.MagnificationFilter.Bilinear);
/* 139 */           texture2D.setMinificationFilter(Texture.MinificationFilter.BilinearNoMipMaps);
/* 140 */           managedResource.setResource(texture2D);
/*     */         } else {
/*     */           
/* 143 */           ImageWrapper wrapper = ImageWrapperFactory.createWrapper(resourceName, fis, managedResource.getParameters());
/* 144 */           Texture texture = wrapper.loadTexture(!this._supportsNonPowerOfTwo.booleanValue());
/* 145 */           texture.setMagnificationFilter(Texture.MagnificationFilter.Bilinear);
/* 146 */           texture.setMinificationFilter(Texture.MinificationFilter.BilinearNoMipMaps);
/* 147 */           AsyncTexture2D texture2D = new AsyncTexture2D(wrapper.getOriginalWidth(), wrapper.getOriginalHeight());
/* 148 */           texture2D.copyRealProperties(texture);
/* 149 */           texture.getImage().setData(texture.getImage().getData());
/* 150 */           managedResource.setResource(texture2D);
/*     */         }
/*     */       
/* 153 */       } catch (IOException e) {
/* 154 */         throw new LoadException(getResourceManager(), managedResource, e);
/* 155 */       } catch (InterruptedException e) {
/* 156 */         throw new LoadException(getResourceManager(), managedResource, e);
/*     */       }
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 162 */       boolean synchronizedLoad = getResourceManager().isMapLoadingHint();
/* 163 */       if (synchronizedLoad) {
/* 164 */         if (LOAD_QUALITY) {
/*     */           
/* 166 */           super.loadData(managedResource);
/*     */ 
/*     */         
/*     */         }
/*     */         else {
/*     */ 
/*     */ 
/*     */           
/* 174 */           String resourceName = managedResource.getName();
/*     */           try {
/* 176 */             ImageWrapper wrapper = ImageWrapperFactory.createWrapper(resourceName, getFileInputStream(resourceName), managedResource.getParameters());
/*     */             
/* 178 */             wrapper.setTargetBuffer(this.tempPixelBuffer);
/* 179 */             Texture texture = wrapper.loadTexture(!this._supportsNonPowerOfTwo.booleanValue());
/* 180 */             this.tempPixelBuffer = wrapper.getTargetBuffer();
/* 181 */             AsyncTexture2D texture2D = new AsyncTexture2D(wrapper.getOriginalWidth(), wrapper.getOriginalHeight());
/* 182 */             texture2D.copyRealProperties(texture);
/*     */             
/* 184 */             TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 185 */             ts.setTexture((Texture)texture2D);
/* 186 */             ts.load();
/*     */             
/* 188 */             texture.getImage().setData(new ArrayList());
/* 189 */             managedResource.setResource(texture2D);
/* 190 */           } catch (IOException e) {
/* 191 */             throw new LoadException(getResourceManager(), managedResource, e);
/* 192 */           } catch (InterruptedException e) {
/* 193 */             throw new LoadException(getResourceManager(), managedResource, e);
/*     */           } 
/*     */         } 
/*     */       } else {
/* 197 */         loadData_Async(managedResource);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void loadData_Async(ManagedResource<?> managedResource) throws LoadException {
/*     */     try {
/*     */       ImageWrapper imageWrapper;
/* 205 */       String resourceName = managedResource.getName();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 211 */         imageWrapper = ImageWrapperFactory.createWrapper(resourceName, getFileInputStream(resourceName), managedResource.getParameters());
/* 212 */       } catch (FileNotFoundException e) {
/* 213 */         DebugManager.getInstance().warn("Missing texture: " + resourceName, e);
/* 214 */         String fallbackName = "environment/global/no_mesh/blank.png";
/* 215 */         imageWrapper = ImageWrapperFactory.createWrapper(fallbackName, getFileInputStream(fallbackName), managedResource.getParameters());
/*     */       } 
/*     */ 
/*     */ 
/*     */       
/* 220 */       AsyncTexture2D substituteTexture = makeSmallSubstituteTexture(imageWrapper);
/*     */       
/* 222 */       managedResource.setResource(substituteTexture);
/*     */       
/* 224 */       TextureDataSimple textureDataSimple = new TextureDataSimple(resourceName, imageWrapper, substituteTexture);
/* 225 */       textureDataSimple.init();
/* 226 */       addToQueue((TextureData)textureDataSimple);
/*     */       
/* 228 */       ensureAsyncThread();
/* 229 */     } catch (MalformedURLException e) {
/* 230 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 231 */     } catch (IOException e) {
/* 232 */       throw new LoadException(getResourceManager(), managedResource, e);
/* 233 */     } catch (InterruptedException e) {
/* 234 */       throw new LoadException(getResourceManager(), managedResource, e);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void addToQueue(TextureData loadData) {
/* 239 */     if (LoadingManager.USE) {
/* 240 */       System.out.printf("TextureLoader Error - addtoqueue is disabled.\n", new Object[0]);
/*     */     } else {
/*     */       
/* 243 */       loadDataQueue.add(loadData);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void ensureAsyncThread() {
/* 248 */     if (asyncLoadThread.get() == null) {
/* 249 */       Thread newThread = new Thread(TEXTURE_DATA_LOADER, "Texture Data Loader");
/* 250 */       boolean success = asyncLoadThread.compareAndSet(null, newThread);
/* 251 */       if (success) {
/* 252 */         newThread.setDaemon(true);
/* 253 */         newThread.setPriority(5);
/* 254 */         newThread.start();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private AsyncTexture2D makeSmallSubstituteTexture(ImageWrapper wrapper) {
/* 260 */     AsyncTexture2D substituteTexture = new AsyncTexture2D(wrapper.getOriginalWidth(), wrapper.getOriginalHeight());
/* 261 */     ByteBuffer data = BufferUtils.createByteBuffer(4);
/* 262 */     IntBuffer buffer = data.asIntBuffer();
/* 263 */     while (buffer.hasRemaining())
/*     */     {
/* 265 */       buffer.put(0);
/*     */     }
/* 267 */     Image tmpImage = new Image(Image.Format.RGBA8, 1, 1, data);
/* 268 */     substituteTexture.setImage(tmpImage);
/* 269 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 270 */     ts.setTexture((Texture)substituteTexture);
/* 271 */     ts.load();
/* 272 */     return substituteTexture;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAsyncLoading() {
/* 277 */     if (!LoadingManager.USE) {
/* 278 */       ensureAsyncThread();
/* 279 */       return !loadDataQueue.isEmpty();
/*     */     } 
/* 281 */     return false;
/*     */   }
/*     */   
/*     */   private static class TextureDataLoader implements Runnable { private TextureDataLoader() {}
/*     */     
/*     */     public void run() {
/* 287 */       Thread myself = Thread.currentThread();
/* 288 */       TextureData data = null;
/*     */       
/*     */       try {
/* 291 */         while (myself == TextureLoader.asyncLoadThread.get() && !Thread.interrupted())
/*     */         {
/* 293 */           data = loadNextTexture();
/*     */         }
/* 295 */       } catch (InterruptedException ignore) {
/*     */       
/* 297 */       } catch (Exception e) {
/* 298 */         if (data != null) {
/* 299 */           System.err.println(data.getName());
/*     */         }
/* 301 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     private TextureData loadNextTexture() throws InterruptedException {
/* 307 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("(Async)...waiting for jobs");
/* 308 */       TextureData data = TextureLoader.loadDataQueue.take();
/* 309 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("(Async)...loading");
/* 310 */       data.load();
/* 311 */       GameTokenProcessor.STEPPEDTOKEN_LOG.info("(Async)...finishing");
/* 312 */       data.finish();
/* 313 */       if (GameTokenProcessor.STEPPEDTOKEN_LOG.isEnabledFor((Priority)Level.INFO)) {
/* 314 */         GameTokenProcessor.STEPPEDTOKEN_LOG.info("Async Load Queue: " + TextureLoader.loadDataQueue.size());
/*     */       }
/*     */       
/* 317 */       return data;
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\TextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */