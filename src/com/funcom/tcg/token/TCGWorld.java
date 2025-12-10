/*     */ package com.funcom.tcg.token;
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.gameengine.collisiondetection.Area;
/*     */ import com.funcom.gameengine.jme.CollisionOnlyPassNode;
/*     */ import com.funcom.gameengine.jme.PassNodeRoot;
/*     */ import com.funcom.gameengine.jme.RuntimeContentPassNode;
/*     */ import com.funcom.gameengine.model.ParticleSurface;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.WorldDebugFlags;
/*     */ import com.funcom.gameengine.model.chunks.ChunkWorldNode;
/*     */ import com.funcom.gameengine.model.factories.MapObjectBuilder;
/*     */ import com.funcom.gameengine.model.factories.PathGraphLoader;
/*     */ import com.funcom.gameengine.model.token.ChunkBuilder;
/*     */ import com.funcom.gameengine.model.token.ChunkFetcherStrategy;
/*     */ import com.funcom.gameengine.model.token.ChunkLoaderToken;
/*     */ import com.funcom.gameengine.model.token.ChunkTokenFactory;
/*     */ import com.funcom.gameengine.model.token.FactoryChunkBuilder;
/*     */ import com.funcom.gameengine.model.token.ManagedChunkLoaderStrategy;
/*     */ import com.funcom.gameengine.model.token.TCGParticleSurface;
/*     */ import com.funcom.gameengine.model.token.Token;
/*     */ import com.funcom.gameengine.model.token.TokenRegister;
/*     */ import com.funcom.gameengine.pathfinding2.MapPathGraph;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceDownloader;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.BinaryLoader;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CSVData;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.TextureAtlasDescription;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AbstractProjectile;
/*     */ import com.funcom.gameengine.view.DfxTextWindow;
/*     */ import com.funcom.gameengine.view.MapObject;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.RepresentationalNode;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.token.ClientChunkTokenFactory;
/*     */ import com.funcom.tcg.client.token.XmlActionFactory;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.LoadingWindow;
/*     */ import com.funcom.tcg.client.ui.maps.MapModel;
/*     */ import com.jme.intersection.PickResults;
/*     */ import com.jme.math.Plane;
/*     */ import com.jme.math.Ray;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.util.TextureManager;
/*     */ import com.jme.util.geom.Debugger;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.effects.glsl.BloomRenderPass;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Field;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.InvalidPropertiesFormatException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Properties;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.jdom.DataConversionException;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ import org.lwjgl.opengl.Display;
/*     */ 
/*     */ public class TCGWorld implements SpatializedWorld {
/*  77 */   public static final Logger LOGGER = Logger.getLogger(TCGWorld.class);
/*  78 */   public static final Plane WORLD_PLANE = new Plane(new Vector3f(0.0F, 1.0F, 0.0F), 0.0F);
/*     */   private ResourceManager resourceManager;
/*     */   private ResourceGetter resourceGetter;
/*     */   private ResourceDownloader resourceDownloader;
/*     */   private ChunkWorldNode chunkWorldNode;
/*     */   private PassNodeRoot worldNode;
/*     */   private PropNode playerNode;
/*     */   private WorldDebugFlags worldDebugFlags;
/*     */   private Node particleNode;
/*     */   private RuntimeContentPassNode runtimeContentNode;
/*  88 */   private RuntimeContentPassNode collisionOnlyContentNode = (RuntimeContentPassNode)new CollisionOnlyPassNode();
/*     */   
/*     */   private Node chuckWorldRoot;
/*     */   private boolean fullLoading;
/*     */   private TCGParticleSurface particleSurface;
/*     */   private PassNodeRoot textNode;
/*     */   
/*     */   public TCGWorld(ResourceManager resourceManager, ResourceGetter resourceGetter, ResourceDownloader resourceDownloader) {
/*  96 */     this.resourceManager = resourceManager;
/*  97 */     this.resourceGetter = resourceGetter;
/*  98 */     this.resourceDownloader = resourceDownloader;
/*  99 */     this.worldDebugFlags = new WorldDebugFlags();
/*     */   }
/*     */   
/*     */   public ResourceManager getResourceManager() {
/* 103 */     return this.resourceManager;
/*     */   }
/*     */   
/*     */   public ResourceGetter getResourceGetter() {
/* 107 */     return this.resourceGetter;
/*     */   }
/*     */   
/*     */   public ChunkWorldNode getChunkWorldNode() {
/* 111 */     return this.chunkWorldNode;
/*     */   }
/*     */   
/*     */   public void setChunkWorldNode(ChunkWorldNode chunkWorldNode) {
/* 115 */     this.chunkWorldNode = chunkWorldNode;
/* 116 */     this.chuckWorldRoot.attachChild((Spatial)chunkWorldNode);
/*     */   }
/*     */   
/*     */   public void setParticleNode(Node particleNode) {
/* 120 */     this.particleNode = particleNode;
/* 121 */     this.particleSurface = new TCGParticleSurface(particleNode);
/* 122 */     attachObject((Spatial)particleNode);
/*     */   }
/*     */   
/*     */   public void setRuntimeContentNode(RuntimeContentPassNode runtimeContentNode) {
/* 126 */     this.runtimeContentNode = runtimeContentNode;
/* 127 */     attachObject((Spatial)runtimeContentNode);
/*     */   }
/*     */   
/*     */   public void setChuckWorldRoot(Node chuckWorldRoot) {
/* 131 */     this.chuckWorldRoot = chuckWorldRoot;
/* 132 */     attachObject((Spatial)chuckWorldRoot);
/*     */   }
/*     */   
/*     */   public Node getWorldNode() {
/* 136 */     return (Node)this.worldNode;
/*     */   }
/*     */   
/*     */   public void findPick(Ray mouseRay, PickResults pickResult) {
/* 140 */     this.runtimeContentNode.findPick(mouseRay, pickResult);
/* 141 */     this.collisionOnlyContentNode.findPick(mouseRay, pickResult);
/*     */   }
/*     */   
/*     */   public void setWorldNode(PassNodeRoot worldNode) {
/* 145 */     this.worldNode = worldNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addArea(Area area) {
/* 150 */     attachObject((Spatial)area);
/*     */   }
/*     */   
/*     */   public void setPlayerNode(PropNode playerNode) {
/* 154 */     this.playerNode = playerNode;
/* 155 */     this.runtimeContentNode.attachChild((Spatial)playerNode);
/*     */   }
/*     */   
/*     */   public void updateGeometricState(float t, boolean propagate) {
/* 159 */     this.worldNode.updateGeometricState(t, propagate);
/*     */   }
/*     */   
/*     */   public void updateRenderState() {
/* 163 */     this.worldNode.updateRenderState();
/*     */   }
/*     */   
/*     */   public void render(Renderer r) {
/* 167 */     r.draw((Spatial)this.worldNode);
/*     */ 
/*     */     
/* 170 */     if (this.worldDebugFlags.showBounds) {
/* 171 */       Debugger.drawBounds((Spatial)this.worldNode, r, true);
/*     */     }
/*     */     
/* 174 */     if (this.worldDebugFlags.showNormals) {
/* 175 */       Debugger.drawNormals((Spatial)this.worldNode, r);
/* 176 */       Debugger.drawTangents((Spatial)this.worldNode, r);
/*     */     } 
/*     */   }
/*     */   
/*     */   public PropNode getPlayerNode() {
/* 181 */     return this.playerNode;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMapName() {
/* 186 */     String toStripSlash = this.chunkWorldNode.getBasePath();
/* 187 */     return toStripSlash.replace('/', ' ').trim();
/*     */   }
/*     */ 
/*     */   
/*     */   public ParticleSurface getParticleSurface() {
/* 192 */     return (ParticleSurface)this.particleSurface;
/*     */   }
/*     */   
/*     */   public void addObject(RepresentationalNode representationalNode) {
/* 196 */     this.runtimeContentNode.attachChild((Spatial)representationalNode);
/*     */   }
/*     */   
/*     */   public void addCollisionObject(RepresentationalNode representationalNode) {
/* 200 */     this.collisionOnlyContentNode.attachChild((Spatial)representationalNode);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeObject(RepresentationalNode representationalNode) {
/* 205 */     this.runtimeContentNode.detachChild((Spatial)representationalNode);
/*     */   }
/*     */   
/*     */   public void addTile(Quad tile) {
/* 209 */     attachObject((Spatial)tile);
/*     */   }
/*     */   
/*     */   public void loadMap(String mapName, GameIOHandler ioHandler) {
/* 213 */     boolean sameMap = isCurrentMap(mapName);
/*     */     
/* 215 */     this.chunkWorldNode.invalidateCurrentChunkPos();
/*     */     
/* 217 */     cleanupRuntimeContent(sameMap);
/*     */     
/* 219 */     reattachMainNodes();
/*     */     
/* 221 */     loadWorldNode(mapName);
/*     */     
/* 223 */     ResourceDownloader downloader = TcgGame.getResourceDownloader();
/* 224 */     if (downloader != null) {
/*     */       LoadingWindow loadingWindow;
/* 226 */       if (TcgUI.isWindowOpen(LoadingWindow.class)) {
/* 227 */         loadingWindow = (LoadingWindow)TcgUI.getWindowFromClass(LoadingWindow.class);
/* 228 */         if (!loadingWindow.isValid()) {
/*     */ 
/*     */           
/* 231 */           BuiSystem.removeWindow((BWindow)loadingWindow);
/* 232 */           BuiSystem.addWindow((BWindow)loadingWindow);
/* 233 */           loadingWindow.validate();
/*     */         } 
/*     */       } else {
/*     */         
/* 237 */         loadingWindow = MainGameState.getLoadingWindow();
/* 238 */         BuiSystem.addWindow((BWindow)loadingWindow);
/*     */       } 
/*     */       
/* 241 */       String jarNeeded = getMapName() + ".jar";
/*     */       
/* 243 */       downloader.downloadMapAndDependencies(jarNeeded);
/* 244 */       TcgGame.getResourceDownloaderThread().setPriority(10);
/*     */       
/* 246 */       loadingWindow.setVisible(true);
/* 247 */       loadingWindow.setMinDownloadFiles(downloader.getJarIndex(jarNeeded) - 1);
/* 248 */       loadingWindow.setTotalDownloadFiles(downloader.getNumberOfDownloadRequestsQueued());
/*     */       
/* 250 */       while (!downloader.isCompletelyDownloaded(jarNeeded)) {
/* 251 */         loadingWindow.updateDownloaderProgress(downloader);
/* 252 */         float RESOURCE_LOADING_TIMER = 0.7F;
/* 253 */         GameStateManager.getInstance().render(RESOURCE_LOADING_TIMER);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 258 */         if (Display.isCreated()) {
/* 259 */           Display.update();
/*     */         }
/*     */         
/* 262 */         if (Display.isCloseRequested()) {
/* 263 */           System.exit(0);
/*     */         }
/*     */       } 
/* 266 */       loadingWindow.updateDownloaderProgress(downloader);
/* 267 */       TcgGame.getResourceDownloaderThread().setPriority(1);
/*     */     } 
/*     */     
/* 270 */     continueLoading();
/*     */     
/* 272 */     this.fullLoading = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean isCurrentMap(String mapName) {
/* 278 */     String currentMapId = this.chunkWorldNode.getChunkWorldInfo().getMapId();
/*     */     
/* 280 */     currentMapId = FileUtils.trimTailingSlashes(currentMapId);
/* 281 */     mapName = FileUtils.trimTailingSlashes(mapName);
/*     */     
/* 283 */     return (currentMapId != null && mapName.startsWith(currentMapId.substring(0, 3)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void cleanupRuntimeContent(boolean sameMap) {
/* 289 */     DfxTextWindowManager.instance().clearAllText();
/* 290 */     MainGameState.getGuiWindowsController().closeAll();
/* 291 */     NetworkHandler.instance().unregisterAllForInventoryUpdate();
/* 292 */     MainGameState.getMapWindow().clear();
/* 293 */     TcgGame.getPropNodeRegister().removeAllFromParents();
/* 294 */     TcgGame.getMonsterRegister().removeAllFromParents();
/* 295 */     TcgGame.getTownPortalRegister().removeAllFromParents();
/* 296 */     TcgGame.getCustomPortalRegister().removeAllFromParents();
/* 297 */     TcgGame.getReturnPointRegister().removeAllFromParents();
/* 298 */     TcgGame.getWaypointDestinationRegister().removeAllFromParents();
/* 299 */     TcgGame.getWaypointRegister().removeAllFromParents();
/* 300 */     TcgGame.getVendorRegister().removeAllFromParents();
/* 301 */     this.chunkWorldNode.clearWorldChunks();
/* 302 */     this.chunkWorldNode.resetWorldLineNode();
/* 303 */     this.chunkWorldNode.getManagedChunkNode().setLineRoot(null);
/* 304 */     this.runtimeContentNode.detachAllContent();
/* 305 */     this.collisionOnlyContentNode.detachAllContent();
/* 306 */     SpatialUtils.clearDecalConfigs();
/* 307 */     MainGameState.resetCurrentChunkFactory();
/* 308 */     MainGameState.getTcgGameControlsController().clearPickedData();
/* 309 */     TcgGame.getParticleProcessor().purge();
/*     */     
/* 311 */     TcgGame.getDireEffectDescriptionFactory().clearRuntimeDescriptions();
/* 312 */     ParticleTextureManager.clearCache();
/* 313 */     TextureManager.clearCache();
/*     */     
/*     */     try {
/* 316 */       Field cleanupStoreField = TextureManager.class.getDeclaredField("cleanupStore");
/* 317 */       cleanupStoreField.setAccessible(true);
/* 318 */       List cleanupStore = (List)cleanupStoreField.get((Object)null);
/* 319 */       cleanupStore.clear();
/* 320 */     } catch (NoSuchFieldException e) {
/* 321 */       e.printStackTrace();
/* 322 */     } catch (IllegalAccessException e) {
/* 323 */       e.printStackTrace();
/*     */     } 
/*     */     
/* 326 */     cleanupResources(sameMap);
/*     */     
/* 328 */     TokenRegister.instance().clear();
/* 329 */     this.chunkWorldNode.clearQueuedChunks();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void reattachMainNodes() {
/* 341 */     TcgGame.getPropNodeRegister().addPropNode(this.playerNode);
/* 342 */     this.runtimeContentNode.attachChild((Spatial)this.playerNode);
/*     */   }
/*     */   
/*     */   private void cleanupResources(boolean sameMap) {
/* 346 */     finishAsyncLoading();
/* 347 */     if (!sameMap) {
/* 348 */       this.resourceManager.clearUnusedResources(CacheType.CACHE_TEMPORARILY);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void finishAsyncLoading() {
/* 356 */     while (this.resourceManager.isAsyncLoading()) {
/* 357 */       TokenRegister.instance().processAllSteppedTokens();
/*     */       try {
/* 359 */         Thread.sleep(50L);
/* 360 */       } catch (InterruptedException ignore) {}
/*     */     } 
/*     */     
/* 363 */     TokenRegister.instance().processAllSteppedTokens();
/*     */   }
/*     */   
/*     */   private void loadWorldNode(String mapName) {
/* 367 */     this.chunkWorldNode.getChunkWorldInfo().setMapId(mapName);
/*     */     
/* 369 */     this.chunkWorldNode.setBasePath(FileUtils.fixTailingSlashes(mapName));
/*     */   }
/*     */   
/*     */   private void loadConfig() {
/* 373 */     String mapConfigName = this.chunkWorldNode.getBasePath() + "config.bunk";
/* 374 */     ByteBuffer blob = (ByteBuffer)this.resourceManager.getResource(ByteBuffer.class, (new File("binary", mapConfigName)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/*     */     
/* 376 */     Document mapConfig = BinaryLoader.convertBlobToMap(blob, this.resourceGetter);
/*     */     
/* 378 */     this.chunkWorldNode.getChunkWorldInfo().loadConfig(mapConfig);
/*     */     
/* 380 */     Element rootElement = mapConfig.getRootElement();
/* 381 */     updateBackgroundColor(rootElement);
/* 382 */     startDownloadingNextMaps(rootElement);
/*     */   }
/*     */   
/*     */   private void loadTileCache() {
/* 386 */     String mapConfigName = this.chunkWorldNode.getBasePath() + "index.bunk";
/*     */     
/*     */     try {
/* 389 */       ByteBuffer blob = (ByteBuffer)this.resourceManager.getResource(ByteBuffer.class, (new File("binary", mapConfigName)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/*     */       
/* 391 */       Document tileIndex = BinaryLoader.convertBlobToMap(blob, this.resourceGetter);
/*     */       
/* 393 */       this.chunkWorldNode.getChunkWorldInfo().loadTileCache(tileIndex, this.resourceGetter);
/*     */     }
/* 395 */     catch (Exception e) {
/* 396 */       System.out.printf("Index file %s is not available. Reverting to old tile loading scheme.\n", new Object[] { mapConfigName });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void startDownloadingNextMaps(Element rootElement) {
/* 403 */     Element jarsElement = rootElement.getChild("jars_to_download");
/* 404 */     if (jarsElement != null) {
/* 405 */       List<Element> jarsList = jarsElement.getChildren("jar");
/* 406 */       for (Element jar : jarsList) {
/* 407 */         String fileName = jar.getValue();
/* 408 */         if (this.resourceDownloader != null)
/* 409 */           this.resourceDownloader.addRequest(fileName); 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadExtra() {
/* 415 */     String mapConfigName = this.chunkWorldNode.getBasePath() + "resources.csv";
/* 416 */     String fileName = (new File("xml", mapConfigName)).getPath().replace('\\', '/');
/* 417 */     if (this.resourceManager.exists(fileName)) {
/* 418 */       String blob = (String)this.resourceManager.getResource(String.class, fileName, CacheType.NOT_CACHED);
/* 419 */       String[] vals = blob.split(",");
/* 420 */       for (String val : vals) {
/* 421 */         if (!val.endsWith(".alpha.png"))
/*     */         {
/* 423 */           if (!val.endsWith(".png") || !this.resourceManager.exists(val))
/*     */           {
/* 425 */             if (val.endsWith(".md5mesh")) {
/* 426 */               this.resourceManager.getResource(ModelNode.class, val, CacheType.CACHE_TEMPORARILY);
/* 427 */             } else if (val.endsWith(".md5anim")) {
/* 428 */               this.resourceManager.getResource(JointAnimation.class, val, CacheType.CACHE_TEMPORARILY);
/* 429 */             } else if (val.endsWith(".xml")) {
/* 430 */               this.resourceManager.getResource(Document.class, val, CacheType.CACHE_TEMPORARILY);
/*     */             }  }  } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void loadTileTextureAtlas() {
/* 437 */     if (LoadingManager.TILE_TEXTURE_ATLAS) {
/* 438 */       String atlasName = "tiles/atlases/" + this.chunkWorldNode.getBasePath();
/* 439 */       atlasName = atlasName.substring(0, atlasName.length() - 1);
/* 440 */       atlasName = atlasName + ".tai";
/*     */       
/* 442 */       TextureAtlasDescription.getAtlasDescription(atlasName.replace('\\', '/'), this.resourceGetter);
/*     */ 
/*     */       
/*     */       try {
/* 446 */         int cpt = 0;
/*     */         while (true) {
/* 448 */           String strTexture = "tiles/atlases/" + this.chunkWorldNode.getBasePath();
/* 449 */           strTexture = strTexture.substring(0, strTexture.length() - 1);
/* 450 */           strTexture = strTexture + cpt + ".png";
/* 451 */           this.resourceGetter.getTexture(strTexture, CacheType.CACHE_TEMPORARILY);
/* 452 */           cpt++;
/*     */         }
/*     */       
/* 455 */       } catch (ResourceManagerException e) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void continueLoading() {
/* 462 */     loadConfig();
/* 463 */     loadImages();
/* 464 */     loadTileTextureAtlas();
/* 465 */     loadTileCache();
/* 466 */     loadRegions();
/* 467 */     addChunkLoaderToken(NetworkHandler.instance().getIOHandler());
/*     */     
/* 469 */     resetMapWindow();
/*     */     
/* 471 */     loadPathGraph();
/* 472 */     MainGameState.getBreadcrumbManager().updateGraphs();
/* 473 */     MainGameState.getBreadcrumbManager().mapLoaded();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadImages() {
/* 479 */     String fileName = this.chunkWorldNode.getBasePath() + "imageList.txt";
/* 480 */     if (this.resourceManager.exists("xml/" + fileName)) {
/* 481 */       CSVData data = (CSVData)this.resourceManager.getResource(CSVData.class, "xml/" + fileName, CacheType.NOT_CACHED);
/* 482 */       Iterator<String[]> iterator = data.iterator();
/* 483 */       while (iterator.hasNext()) {
/* 484 */         String[] vals = iterator.next();
/* 485 */         for (String val : vals) {
/* 486 */           this.resourceManager.getResource(Texture.class, val, CacheType.CACHE_TEMPORARILY);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void loadPathGraph() {
/* 496 */     String pathGraphFileName = this.chunkWorldNode.getBasePath() + "pathgraph.pthgph";
/* 497 */     if (this.resourceManager.exists(FileUtils.fixTailingSlashes("binary") + pathGraphFileName)) {
/*     */       try {
/* 499 */         ByteBuffer blob = (ByteBuffer)this.resourceManager.getResource(ByteBuffer.class, (new File("binary", pathGraphFileName)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/*     */ 
/*     */         
/* 502 */         PathGraphLoader pathGraphLoader = new PathGraphLoader();
/* 503 */         MapPathGraph mapPathGraph = new MapPathGraph(pathGraphLoader.load(blob, this.chunkWorldNode.getChunkWorldInfo().getMapId()));
/*     */         
/* 505 */         this.chunkWorldNode.setPathGraph(mapPathGraph);
/* 506 */       } catch (IOException e) {
/* 507 */         LOGGER.error("Failed to laod pathgraph file! ", e);
/*     */       } 
/*     */     } else {
/* 510 */       LOGGER.error("Couldn't find pathgraph file - pathfinding won't work.");
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadBloomSettings() {
/* 515 */     BloomRenderPass bloomRenderPass = MainGameState.getBloomPass();
/* 516 */     bloomRenderPass.setEnabled(false);
/*     */ 
/*     */     
/* 519 */     String bloomSettingsFile = this.chunkWorldNode.getBasePath() + "bloom.settings";
/* 520 */     if (this.resourceManager.exists(FileUtils.fixTailingSlashes("binary") + bloomSettingsFile)) {
/*     */       try {
/* 522 */         ByteBuffer propertiesBlob = (ByteBuffer)this.resourceManager.getResource(ByteBuffer.class, (new File("binary", bloomSettingsFile)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/*     */ 
/*     */         
/* 525 */         Properties p = new Properties();
/* 526 */         ByteArrayInputStream stream = null;
/*     */         try {
/* 528 */           stream = new ByteArrayInputStream(propertiesBlob.array());
/* 529 */           p.loadFromXML(stream);
/*     */ 
/*     */           
/* 532 */           float cutoff = Float.parseFloat(p.getProperty("cutoff", "0.0"));
/* 533 */           float intensity = Float.parseFloat(p.getProperty("intensity", "1.3"));
/* 534 */           int passes = Integer.parseInt(p.getProperty("passes", "2"));
/* 535 */           int throttleMS = Integer.parseInt(p.getProperty("throttleMS", "20"));
/* 536 */           float power = Float.parseFloat(p.getProperty("power", "3.0"));
/* 537 */           float size = Float.parseFloat(p.getProperty("size", "0.02"));
/* 538 */           boolean useCurrent = Boolean.parseBoolean(p.getProperty("useCurrent", "true"));
/*     */           
/* 540 */           bloomRenderPass.setEnabled(true);
/* 541 */           bloomRenderPass.setExposureCutoff(cutoff);
/* 542 */           bloomRenderPass.setBlurIntensityMultiplier(intensity);
/* 543 */           bloomRenderPass.setNrBlurPasses(passes);
/* 544 */           bloomRenderPass.setThrottle(throttleMS * 0.001F);
/* 545 */           bloomRenderPass.setExposurePow(power);
/* 546 */           bloomRenderPass.setBlurSize(size);
/* 547 */           bloomRenderPass.setUseCurrentScene(useCurrent);
/*     */           
/* 549 */           if (LOGGER.isEnabledFor((Priority)Level.INFO)) {
/* 550 */             LOGGER.info("Bloom setup OK, enabled.");
/*     */           }
/* 552 */         } catch (InvalidPropertiesFormatException e) {
/* 553 */           LOGGER.error("Failure in reading bloom.settings file, giving up!", e);
/* 554 */         } catch (IOException e) {
/* 555 */           LOGGER.error("Failure in reading bloom.settings file, giving up!", e);
/*     */         } finally {
/* 557 */           if (stream != null)
/* 558 */             stream.close(); 
/*     */         } 
/* 560 */       } catch (IOException e) {
/* 561 */         LOGGER.error("Failed to laod pathgraph file! ", e);
/*     */       } 
/*     */     } else {
/* 564 */       LOGGER.warn("Couldn't find bloom settings file - bloom will be disabled for this level.");
/*     */     } 
/*     */   }
/*     */   private void loadRegions() {
/* 568 */     String regionFileName = this.chunkWorldNode.getBasePath() + "region.bunk";
/* 569 */     if (this.resourceManager.exists("binary/" + regionFileName)) {
/* 570 */       ByteBuffer blob = (ByteBuffer)this.resourceManager.getResource(ByteBuffer.class, (new File("binary", regionFileName)).getPath().replace('\\', '/'), CacheType.NOT_CACHED);
/*     */       
/* 572 */       Document regionDocument = BinaryLoader.convertBlobToMap(blob, this.resourceGetter);
/*     */       try {
/* 574 */         this.chunkWorldNode.getChunkWorldInfo().loadRegions(regionDocument.getRootElement());
/* 575 */       } catch (DataConversionException e) {
/* 576 */         throw new RuntimeException("Could not load regions! Exception: " + e.toString());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addChunkLoaderToken(GameIOHandler ioHandler) {
/* 582 */     FactoryChunkBuilder factoryChunkBuilder = new FactoryChunkBuilder((ChunkTokenFactory)new ClientChunkTokenFactory(TcgGame.getDireEffectDescriptionFactory(), TcgGame.getResourceManager()));
/*     */     
/* 584 */     ManagedChunkLoaderStrategy managedChunkLoaderStrategy = new ManagedChunkLoaderStrategy(this.chunkWorldNode, new ChunkFetcherStrategy(this.resourceGetter), "managed.bunk");
/*     */ 
/*     */     
/* 587 */     ChunkLoaderToken chunkLoaderToken = new ChunkLoaderToken((ChunkBuilder)factoryChunkBuilder, (ChunkLoaderStrategy)managedChunkLoaderStrategy, this.playerNode.getPosition(), this.resourceGetter, ioHandler, (ActionFactory)new XmlActionFactory(this.resourceGetter, ioHandler));
/*     */     
/* 589 */     TokenRegister.instance().addToken((Token)chunkLoaderToken);
/*     */   }
/*     */   
/*     */   private void resetMapWindow() {
/* 593 */     List<MapObject> mapObjects = (new MapObjectBuilder(this.resourceGetter)).getMapObjectList(this.chunkWorldNode.getChunkWorldInfo());
/*     */     
/* 595 */     MapModel mapModel = new MapModel(this.chunkWorldNode.getChunkWorldInfo(), mapObjects);
/* 596 */     MainGameState.getMapWindow().reset(mapModel);
/*     */   }
/*     */   
/*     */   private static void sleep(int millis) {
/*     */     try {
/* 601 */       Thread.sleep(millis);
/* 602 */     } catch (InterruptedException ignore) {}
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateBackgroundColor(Element rootElement) {
/* 607 */     Element backgroundColorElement = rootElement.getChild("background_color");
/* 608 */     if (backgroundColorElement != null) {
/*     */       try {
/* 610 */         float[] color = SpatialUtils.getColor(backgroundColorElement);
/* 611 */         TcgGame.setBackgroundColor(new ColorRGBA(color[0], color[1], color[2], color[3]));
/* 612 */       } catch (DataConversionException e) {
/* 613 */         e.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   private void attachObject(Spatial spatial) {
/* 624 */     this.worldNode.attachChild(spatial);
/*     */   }
/*     */   
/*     */   public WorldDebugFlags getDebugRenderFlags() {
/* 628 */     return this.worldDebugFlags;
/*     */   }
/*     */ 
/*     */   
/*     */   public void addProjectile(AbstractProjectile projectile) {
/* 633 */     this.particleNode.attachChild((Spatial)projectile);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeProjectile(AbstractProjectile projectile) {
/* 638 */     this.particleNode.detachChild((Spatial)projectile);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFullLoading() {
/* 643 */     return this.fullLoading;
/*     */   }
/*     */   
/*     */   public void resetLoadingScreenNeeded() {
/* 647 */     this.fullLoading = false;
/*     */   }
/*     */   
/*     */   public String getLocalizedName() {
/* 651 */     return TcgGame.getLocalizedText(this.chunkWorldNode.getChunkWorldInfo().getMapNameKey(), new String[0]);
/*     */   }
/*     */   
/*     */   public void addDfxTextFactories(List<DfxTextWindow> dfxTextWindows) {
/* 655 */     for (DfxTextWindow dfxTextWindow : dfxTextWindows) {
/* 656 */       this.textNode.attachChild((Spatial)dfxTextWindow);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setTextNode(PassNodeRoot textNode) {
/* 661 */     this.textNode = textNode;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\token\TCGWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */