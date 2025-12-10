/*     */ package com.funcom.gameengine.model;
/*     */ 
/*     */ import com.funcom.commons.jme.md5importer.JointAnimation;
/*     */ import com.funcom.commons.jme.md5importer.ModelNode;
/*     */ import com.funcom.gameengine.jme.TileQuadCached;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.TileInfo;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.TextureAtlasDescription;
/*     */ import com.funcom.gameengine.view.MaxNode;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.scene.Node;
/*     */ import com.jme.scene.state.GLSLShaderObjectsState;
/*     */ import com.jmex.bui.BCursor;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.icon.ImageIcon;
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ResourceGetterImpl
/*     */   implements ResourceGetter
/*     */ {
/*     */   private ResourceManager resourceManager;
/*  33 */   private static ArrayList<TileInfo> tileCache = new ArrayList<TileInfo>(5000);
/*     */   
/*     */   public ResourceGetterImpl(ResourceManager resourceManager) {
/*  36 */     this.resourceManager = resourceManager;
/*     */   }
/*     */   
/*     */   public ResourceManager getResourceManager() {
/*  40 */     return this.resourceManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public Document getDocument(String resourceName, CacheType cached) {
/*  45 */     return (Document)this.resourceManager.getResource(Document.class, resourceName, cached);
/*     */   }
/*     */ 
/*     */   
/*     */   public GraphicsConfig getGraphicsConfig(String resourceName) {
/*  50 */     return (GraphicsConfig)this.resourceManager.getResource(GraphicsConfig.class, resourceName, CacheType.CACHE_TEMPORARILY);
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture getTexture(String resourceName, CacheType cacheType) {
/*  55 */     return (Texture)this.resourceManager.getResource(Texture.class, resourceName, cacheType);
/*     */   }
/*     */ 
/*     */   
/*     */   public Texture getTextureCopy(String resourceName) {
/*  60 */     return (Texture)this.resourceManager.getResource(Texture.class, resourceName);
/*     */   }
/*     */ 
/*     */   
/*     */   public ModelNode getModelNode(String resourceName) {
/*  65 */     return ((ModelNode)this.resourceManager.getResource(ModelNode.class, resourceName, CacheType.CACHE_TEMPORARILY)).clone();
/*     */   }
/*     */ 
/*     */   
/*     */   public JointAnimation getModelAnimation(String resourceName) {
/*  70 */     JointAnimation jointAnimation = (JointAnimation)this.resourceManager.getResource(JointAnimation.class, resourceName, CacheType.CACHE_TEMPORARILY);
/*  71 */     jointAnimation = jointAnimation.clone();
/*  72 */     return jointAnimation;
/*     */   }
/*     */ 
/*     */   
/*     */   public GLSLShaderObjectsState getShader(String resourceName) {
/*  77 */     return (GLSLShaderObjectsState)this.resourceManager.getResource(GLSLShaderObjectsState.class, resourceName, CacheType.CACHE_PERMANENTLY);
/*     */   }
/*     */ 
/*     */   
/*     */   public BImage getBImage(String resourceName) {
/*  82 */     return (BImage)this.resourceManager.getResource(BImage.class, resourceName, CacheType.NOT_CACHED);
/*     */   }
/*     */ 
/*     */   
/*     */   public BIcon getBIcon(String resourceName) {
/*  87 */     return (BIcon)new ImageIcon(getBImage(resourceName));
/*     */   }
/*     */ 
/*     */   
/*     */   public BCursor getBCursor(String name) {
/*  92 */     throw new IllegalStateException("WAITING TO BE IMPLEMENTED!");
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getBlob(String name) {
/*  97 */     return getBlob(name, CacheType.NOT_CACHED);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuffer getBlob(String name, CacheType cacheType) {
/* 102 */     return (ByteBuffer)this.resourceManager.getResource(ByteBuffer.class, name, cacheType);
/*     */   }
/*     */ 
/*     */   
/*     */   public Node getStaticModel(String resource) {
/* 107 */     MaxNode maxNode = (MaxNode)this.resourceManager.getResource(MaxNode.class, resource, CacheType.NOT_CACHED);
/* 108 */     return (Node)maxNode.getChild(0);
/*     */   }
/*     */   
/*     */   public boolean exists(String resource) {
/* 112 */     return this.resourceManager.exists(resource);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileQuadCached.CachedTileData getCachedTileData(String tileKey) {
/* 117 */     return (TileQuadCached.CachedTileData)this.resourceManager.getResource(TileQuadCached.CachedTileData.class, tileKey, CacheType.CACHE_TEMPORARILY);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> listFiles(String path, String file) {
/* 122 */     return this.resourceManager.listFiles(path, file);
/*     */   }
/*     */ 
/*     */   
/*     */   public TextureAtlasDescription getTextureAtlasDescription(String resourceName, CacheType cached) {
/* 127 */     return (TextureAtlasDescription)this.resourceManager.getResource(TextureAtlasDescription.class, resourceName, cached);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTileCache(ArrayList<TileInfo> tileCache) {
/* 132 */     this; ResourceGetterImpl.tileCache = tileCache;
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearTileCache() {
/* 137 */     tileCache.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public TileInfo getTile(int index) {
/* 142 */     if (index >= 0 && index < tileCache.size()) {
/* 143 */       return tileCache.get(index);
/*     */     }
/* 145 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public TileInfo getTile(String background, String layer2, String layer3, String layer4) {
/* 150 */     TileInfo tile = new TileInfo(background, layer2, layer3, layer4, this);
/* 151 */     tileCache.add(tile);
/* 152 */     return tile;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\ResourceGetterImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */