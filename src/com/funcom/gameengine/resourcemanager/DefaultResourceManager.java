/*     */ package com.funcom.gameengine.resourcemanager;
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.AWTImageLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.BlobLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.BuiImageLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CSVLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CachedTileDataLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.ExtPropertiesLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.GraphicsConfigLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.HtmlLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.JmeImageLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.MaxModelLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.Md5AnimationLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.Md5ModelLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.PublicKeyLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.ReducioProxyTextureLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.TextLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.TextureAtlasDescriptionLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.TextureLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.XmlLoader;
/*     */ import com.jme.util.NanoTimer;
/*     */ import java.io.File;
/*     */ import java.io.FileFilter;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.MissingResourceException;
/*     */ import java.util.ResourceBundle;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.CopyOnWriteArraySet;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.jar.JarFile;
/*     */ import java.util.zip.ZipEntry;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class DefaultResourceManager implements ResourceManager {
/*  43 */   private static final Logger LOGGER = Logger.getLogger(DefaultResourceManager.class.getName());
/*     */ 
/*     */ 
/*     */   
/*  47 */   protected static NanoTimer timer = new NanoTimer();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  61 */   private Map<ResourceTypeKey, ManagedResource<?>> resourceDictionary = new HashMap<ResourceTypeKey, ManagedResource<?>>();
/*  62 */   private Map<Class<?>, ResourceLoader> loaders = new HashMap<Class<?>, ResourceLoader>();
/*  63 */   private ExecutorService executorService = Executors.newSingleThreadExecutor();
/*  64 */   private Set<String> resourceRoots = new CopyOnWriteArraySet<String>();
/*     */ 
/*     */   
/*     */   protected boolean mapLoadingHint = true;
/*     */   
/*  69 */   private boolean useJars = Boolean.getBoolean("usejars");
/*     */   private static final long MAX_CACHE_AGE = 10000L;
/*     */   private static final String ARG_USE_JARS = "usejars";
/*     */   
/*     */   public void initDefaultLoaders() {
/*  74 */     addTypeLoader((ResourceLoader)new XmlLoader());
/*  75 */     addTypeLoader((ResourceLoader)new BlobLoader());
/*  76 */     addTypeLoader((ResourceLoader)new BuiImageLoader());
/*  77 */     addTypeLoader((ResourceLoader)new JmeImageLoader());
/*  78 */     addTypeLoader((ResourceLoader)new CSVLoader());
/*  79 */     addTypeLoader((ResourceLoader)new ExtPropertiesLoader());
/*  80 */     addTypeLoader((ResourceLoader)new Md5ModelLoader());
/*  81 */     addTypeLoader((ResourceLoader)new Md5AnimationLoader());
/*  82 */     addTypeLoader((ResourceLoader)new MaxModelLoader());
/*  83 */     addTypeLoader((ResourceLoader)new GraphicsConfigLoader());
/*  84 */     addTypeLoader((ResourceLoader)new HtmlLoader());
/*  85 */     addTypeLoader((ResourceLoader)new ReducioProxyTextureLoader(new TextureLoader()));
/*  86 */     addTypeLoader((ResourceLoader)new AWTImageLoader());
/*  87 */     addTypeLoader((ResourceLoader)new TextLoader());
/*  88 */     addTypeLoader((ResourceLoader)new CachedTileDataLoader());
/*  89 */     addTypeLoader((ResourceLoader)new PublicKeyLoader());
/*  90 */     addTypeLoader((ResourceLoader)new TextureAtlasDescriptionLoader());
/*  91 */     for (ResourceLoader loader : this.loaders.values())
/*  92 */       loader.init(); 
/*     */   }
/*     */   private static final double NANO_TO_MS = 1.0E-6D;
/*     */   private DummyResourceFactory dummyResourceFactory;
/*     */   
/*     */   public void addTypeLoader(ResourceLoader resourceLoader) {
/*  98 */     Class<?> type = resourceLoader.getResourceType();
/*  99 */     if (this.loaders.containsKey(type))
/* 100 */       this.loaders.remove(type); 
/* 101 */     this.loaders.put(type, resourceLoader);
/* 102 */     resourceLoader.setResourceManager(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeTypeLoader(Class<?> type) {
/* 107 */     this.loaders.remove(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getDummyResource(Class<?> type) {
/* 112 */     return this.dummyResourceFactory.getResourceForType(type);
/*     */   }
/*     */ 
/*     */   
/*     */   public DummyResourceFactory getDummyResourceFactory() {
/* 117 */     return this.dummyResourceFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDummyResourceFactory(DummyResourceFactory dummyResourceFactory) {
/* 122 */     this.dummyResourceFactory = dummyResourceFactory;
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getResource(Class<T> resourceType, String name) throws ResourceManagerException {
/* 127 */     return getManagedResource(resourceType, name, CacheType.CACHE_TEMPORARILY).getResource();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> ManagedResource<T> getManagedResource(Class<T> resourceType, String name) throws ResourceManagerException {
/* 132 */     return getManagedResource(resourceType, name, CacheType.CACHE_PERMANENTLY);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getResource(Class<T> resourceType, String name, CacheType cacheInMemory) throws ResourceManagerException {
/* 137 */     return getManagedResource(name, cacheInMemory, null, resourceType).getResource();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getResourceByLocale(Class<T> resourceType, String name, CacheType cacheInMemory) throws ResourceManagerException {
/*     */     ResourceBundle resourceBundle;
/*     */     try {
/* 145 */       resourceBundle = ResourceBundle.getBundle(name, Locale.getDefault(), new ResourceBundleLoadControl(this, resourceType, cacheInMemory));
/* 146 */     } catch (MissingResourceException e) {
/* 147 */       throw new ResourceManagerException("Cannot load locale resource: resourceName=" + name + "resourceRoots=" + this.resourceRoots);
/*     */     } 
/*     */     
/* 150 */     return (T)resourceBundle.getObject(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> ManagedResource<T> getManagedResource(Class<T> resourceType, String name, CacheType cacheInMemory) throws ResourceManagerException {
/* 155 */     return getManagedResource(name, cacheInMemory, null, resourceType);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getResource(Class<T> resourceType, String name, CacheType cacheInMemory, Map<Object, Object> properties) throws ResourceManagerException {
/* 160 */     return getResourceOmnipowered(resourceType, name, null, cacheInMemory, properties).getResource();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> ManagedResource<T> getManagedResource(String name, CacheType cacheInMemory, Map<Object, Object> properties, Class<T> resourceType) throws ResourceManagerException {
/* 165 */     return getResourceOmnipowered(resourceType, name, null, cacheInMemory, properties);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getResourceCopy(Class<T> resourceType, String name) throws ResourceManagerException {
/* 170 */     return getManagedResourceCopy(resourceType, name, null).getResource();
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> ManagedResource<T> getManagedResourceCopy(Class<T> resourceType, String name) throws ResourceManagerException {
/* 175 */     return getManagedResourceCopy(resourceType, name, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T getResourceCopy(Class<T> resourceType, String name, Map<Object, Object> properties) throws ResourceManagerException {
/* 180 */     return getManagedResourceCopy(resourceType, name, properties).getResource();
/*     */   }
/*     */ 
/*     */   
/*     */   public synchronized <T> ManagedResource<T> getManagedResourceCopy(Class<T> resourceType, String name, Map<Object, Object> properties) throws ResourceManagerException {
/* 185 */     ManagedResource<T> resource = new ManagedResource<T>(name, resourceType, CacheType.NOT_CACHED);
/* 186 */     if (properties != null) {
/* 187 */       fillProperties(resource, properties);
/*     */     }
/* 189 */     ResourceLoader resourceLoader = this.loaders.get(resource.getResourceType());
/* 190 */     resourceLoader.setResourceManager(this);
/* 191 */     resource.setLoader(resourceLoader);
/* 192 */     LoadTask<T> lt = new LoadTask<T>(resource);
/*     */     try {
/* 194 */       return lt.call();
/* 195 */     } catch (LoadException e) {
/* 196 */       throw new ResourceManagerException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void getManagedResourceAsync(Class<T> resourceType, String name, ResourceListener<T> listener) throws ResourceManagerException {
/* 202 */     getManagedResourceAsync(resourceType, name, listener, CacheType.CACHE_PERMANENTLY);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void getManagedResourceAsync(Class<T> resourceType, String name, ResourceListener<T> listener, CacheType cacheInMemory) throws ResourceManagerException {
/* 207 */     getManagedResourceAsync(resourceType, name, listener, cacheInMemory, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> void getManagedResourceAsync(Class<T> resourceType, String name, ResourceListener<T> listener, CacheType cacheInMemory, Map<Object, Object> properties) throws ResourceManagerException {
/* 212 */     if (listener == null)
/* 213 */       throw new IllegalArgumentException("Listener cannot be null for asynchronous loading."); 
/* 214 */     getResourceOmnipowered(resourceType, name, listener, cacheInMemory, properties);
/*     */   }
/*     */   
/*     */   protected <T> ManagedResource<T> getResourceOmnipowered(Class<T> resourceType, String name, ResourceListener<T> listener, CacheType cacheInMemory, Map<Object, Object> properties) throws ResourceManagerException {
/* 218 */     ManagedResource<T> resource = null;
/* 219 */     synchronized (this.resourceDictionary) {
/* 220 */       resource = (ManagedResource<T>)this.resourceDictionary.get(new ResourceTypeKey(name, resourceType));
/*     */       
/* 222 */       if (resource == null) {
/* 223 */         resource = new ManagedResource<T>(name, resourceType, cacheInMemory);
/*     */       }
/*     */       
/* 226 */       if (properties != null) {
/* 227 */         fillProperties(resource, properties);
/*     */       }
/*     */     } 
/*     */     
/* 231 */     if (!resource.isLoaded() && !resource.isLoading()) {
/*     */       
/* 233 */       resource.setLoading(true);
/* 234 */       startLoading(listener, resource);
/* 235 */     } else if (listener != null && resource.isLoaded()) {
/*     */       
/* 237 */       listener.resourceLoaded(resource);
/* 238 */     } else if (!resource.isLoaded() && listener != null) {
/*     */       
/* 240 */       resource.addListener(listener);
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 245 */     if (cacheInMemory == CacheType.CACHE_PERMANENTLY || cacheInMemory == CacheType.CACHE_TEMPORARILY)
/*     */     {
/* 247 */       this.resourceDictionary.put(new ResourceTypeKey(name, resourceType), resource);
/*     */     }
/*     */     
/* 250 */     return resource;
/*     */   }
/*     */   
/*     */   private <T> void startLoading(ResourceListener<T> listener, ManagedResource<T> resource) {
/* 254 */     ResourceLoader resourceLoader = null;
/* 255 */     synchronized (this.loaders) {
/* 256 */       resourceLoader = this.loaders.get(resource.getResourceType());
/*     */     } 
/*     */     
/* 259 */     if (resourceLoader == null) {
/* 260 */       throw new ResourceManagerException(resource);
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 276 */     synchronized (resourceLoader) {
/* 277 */       resourceLoader.setResourceManager(this);
/* 278 */       fillAdditionalLoaders(resourceLoader);
/*     */       
/*     */       try {
/* 281 */         resource.setLoader(resourceLoader);
/* 282 */         if (listener == null) {
/* 283 */           (new LoadTask(resource)).call();
/*     */         } else {
/*     */           
/* 286 */           synchronized (this.executorService) {
/* 287 */             this.executorService.submit(new LoadTask<T>(resource, listener));
/*     */           }
/*     */         
/*     */         } 
/* 291 */       } catch (LoadException e) {
/* 292 */         throw new ResourceManagerException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fillAdditionalLoaders(ResourceLoader targetLoader) {
/* 298 */     if (targetLoader.getNeededLoaderTypes() != null)
/* 299 */       for (Class<?> type : targetLoader.getNeededLoaderTypes()) {
/* 300 */         ResourceLoader neededLoader = this.loaders.get(type);
/* 301 */         neededLoader.setResourceManager(this);
/* 302 */         targetLoader.addAdditionalLoader(neededLoader);
/*     */       }  
/*     */   }
/*     */   
/*     */   private void fillProperties(ManagedResource<?> resource, Map<Object, Object> properties) {
/* 307 */     for (Map.Entry<Object, Object> entry : properties.entrySet()) {
/* 308 */       resource.setParameter(entry.getKey(), entry.getValue());
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isResourceLoaded(ResourceTypeKey resourceTypeKey) {
/* 313 */     ManagedResource<?> resource = this.resourceDictionary.get(resourceTypeKey);
/* 314 */     return (resource != null && resource.isLoaded());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update() {
/* 320 */     for (ResourceTypeKey resourceTypeKey : this.resourceDictionary.keySet()) {
/* 321 */       ManagedResource<?> resource = this.resourceDictionary.get(resourceTypeKey);
/* 322 */       if (shouldClear(resource)) {
/* 323 */         LOGGER.debug("Unloading resource: " + resource);
/* 324 */         resource.unload();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearUnusedResources(CacheType cacheType) {
/* 332 */     for (ManagedResource<?> managedResource : this.resourceDictionary.values()) {
/* 333 */       if (cacheType == null || cacheType == managedResource.getCacheType())
/*     */       {
/* 335 */         managedResource.unloadPrepare();
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 340 */     doGC();
/*     */ 
/*     */     
/* 343 */     for (Iterator<ManagedResource<?>> iterator = this.resourceDictionary.values().iterator(); iterator.hasNext(); ) {
/* 344 */       ManagedResource managedResource = iterator.next();
/* 345 */       boolean unloaded = managedResource.unload();
/* 346 */       if (unloaded) {
/* 347 */         LOGGER.debug("Unloaded resource: " + managedResource.getName());
/* 348 */         iterator.remove();
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void doGC() {
/* 354 */     for (int i = 0; i < 2; i++) {
/* 355 */       System.gc();
/*     */       try {
/* 357 */         Thread.sleep(100L);
/* 358 */       } catch (InterruptedException ignore) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearAllUnusedResources() {
/* 365 */     clearUnusedResources(null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean shouldClear(ManagedResource<?> managedResource) {
/* 370 */     long ageLimit = GlobalTime.getInstance().getCurrentTime() - 10000L;
/* 371 */     boolean shouldClear = (managedResource.isLoaded() && (managedResource.getLastAccessed() < ageLimit || managedResource.isDirty()));
/*     */     
/* 373 */     if (shouldClear)
/* 374 */       LOGGER.debug("Should clear!"); 
/* 375 */     return shouldClear;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearResource(String managedResource, Class type) {
/* 380 */     ResourceTypeKey typeKey = new ResourceTypeKey(managedResource, type);
/* 381 */     if (this.resourceDictionary.containsKey(typeKey)) {
/* 382 */       this.resourceDictionary.remove(typeKey);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMapLoadingHint() {
/* 388 */     return this.mapLoadingHint;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAsyncLoading() {
/* 393 */     for (ResourceLoader resourceLoader : this.loaders.values()) {
/* 394 */       if (resourceLoader.isAsyncLoading()) {
/* 395 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 399 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean addResourceRoot(String resourceRoot) {
/* 404 */     if (resourceRoot == null)
/* 405 */       throw new IllegalArgumentException("resourceRoots = null"); 
/* 406 */     return this.resourceRoots.add(resourceRoot);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean remove(String resourceRoot) {
/* 411 */     if (resourceRoot == null)
/* 412 */       throw new IllegalArgumentException("resourceRoot = null"); 
/* 413 */     return this.resourceRoots.remove(resourceRoot);
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getResourceRoots() {
/* 418 */     return this.resourceRoots;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyLoadingScreenStarted(String toLoadMapName) {
/* 423 */     this.mapLoadingHint = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyLoadingScreenFinished(String loadedMapName) {
/* 428 */     this.mapLoadingHint = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUseJars() {
/* 433 */     return this.useJars;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean exists(String resource) {
/* 438 */     if (isUseJars()) {
/* 439 */       String absResourcePath = FileUtils.makeAbsolute(resource);
/* 440 */       for (String resourceRoot : this.resourceRoots) {
/* 441 */         if (resourceRoot.contains(".jar")) {
/* 442 */           JarFile jarFile = null;
/*     */           try {
/* 444 */             jarFile = JarOpener.instance().getJarFile(resourceRoot.substring(0, resourceRoot.indexOf(".jar") + 4));
/* 445 */           } catch (IOException e) {
/*     */             continue;
/*     */           } 
/* 448 */           String pathFromRoot = resourceRoot.substring(resourceRoot.indexOf(".jar") + 5);
/* 449 */           pathFromRoot = pathFromRoot.isEmpty() ? pathFromRoot : (pathFromRoot + "/");
/* 450 */           ZipEntry entry = jarFile.getEntry(pathFromRoot + absResourcePath);
/* 451 */           if (entry != null) {
/* 452 */             return true;
/*     */           }
/*     */         } 
/*     */       } 
/*     */     } else {
/* 457 */       for (String resourceRoot : this.resourceRoots) {
/* 458 */         File file = new File(resourceRoot, resource);
/* 459 */         if (file.exists())
/* 460 */           return true; 
/*     */       } 
/*     */     } 
/* 463 */     return false;
/*     */   }
/*     */   
/*     */   public void shutdown() {
/* 467 */     this.executorService.shutdown();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> listFiles(String path, String file) {
/* 475 */     final String finalfile = file;
/* 476 */     String finalpath = path;
/*     */     
/* 478 */     FileFilter fileFilter = new FileFilter()
/*     */       {
/*     */         public boolean accept(File pathname) {
/* 481 */           return ((pathname.isFile() && pathname.getPath().contains(finalfile)) || pathname.isDirectory());
/*     */         }
/*     */       };
/*     */ 
/*     */     
/* 486 */     List<String> retNames = new ArrayList<String>();
/* 487 */     Set<String> resourceRoots = getResourceRoots();
/* 488 */     for (String resourceRoot : resourceRoots) {
/* 489 */       File tmp = new File(resourceRoot + "\\" + path);
/* 490 */       File[] searchResults = tmp.listFiles(fileFilter);
/* 491 */       searchFiles(retNames, searchResults, fileFilter, resourceRoot.length());
/*     */     } 
/*     */     
/* 494 */     return retNames;
/*     */   }
/*     */   
/*     */   private void searchFiles(List<String> retNames, File[] searchResults, FileFilter fileFilter, int ignore) {
/* 498 */     if (searchResults != null)
/* 499 */       for (int n = 0; n < searchResults.length; n++) {
/* 500 */         File file = searchResults[n];
/* 501 */         if (file.isFile()) {
/*     */           
/* 503 */           retNames.add(searchResults[n].getPath().substring(ignore));
/*     */         }
/* 505 */         else if (file.isDirectory()) {
/* 506 */           File[] newsearchResults = file.listFiles(fileFilter);
/* 507 */           searchFiles(retNames, newsearchResults, fileFilter, ignore);
/*     */         } 
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\DefaultResourceManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */