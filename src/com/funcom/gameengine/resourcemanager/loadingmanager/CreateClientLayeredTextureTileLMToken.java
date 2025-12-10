/*     */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.jme.TileQuad;
/*     */ import com.funcom.gameengine.jme.TileQuadCached;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.TileInfo;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TexCoords;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jme.util.geom.BufferUtils;
/*     */ import java.awt.Point;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.nio.IntBuffer;
/*     */ import java.util.ArrayList;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.Future;
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
/*     */ public class CreateClientLayeredTextureTileLMToken
/*     */   extends AbstractCreateLayeredTextureTileLMToken
/*     */ {
/*  43 */   private static FloatBuffer tileVertexBuffer = null;
/*     */   private static FloatBuffer tileNormalBuffer;
/*     */   private static FloatBuffer tileColorBuffer;
/*     */   private static FloatBuffer tileTextureMappingBuffer;
/*     */   private static IntBuffer tileIndicesBuffer;
/*     */   private static BoundingBox tileBounds;
/*     */   public static final float TILE_CUBE_SIZE = 1.0F;
/*  50 */   private static final ColorRGBA[] colors = null;
/*  51 */   private static int[] indexes = new int[] { 0, 1, 2, 2, 1, 3 };
/*     */ 
/*     */ 
/*     */   
/*  55 */   private int id = -1;
/*  56 */   private Quad tileQuad = null;
/*  57 */   private Future<TileQuadCached.CachedTileData> LoadTexturesFuture = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public CreateClientLayeredTextureTileLMToken(int x, int y, String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter, int id) {
/*  62 */     super(x, y, backgroundResourceName, layer2ResourceName, layer3ResourceName, layer4ResourceName, tokenTargetNode, tileCoord, resourceGetter);
/*     */     
/*  64 */     this.id = id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isTransparentFile(String fileName) {
/*  70 */     return (fileName.indexOf("transparent_base") >= 0);
/*     */   }
/*     */ 
/*     */   
/*     */   public TileQuadCached.CachedTileData loadCachedTile(TextureState ts, ZBufferState zBufferState) {
/*  75 */     LoadTexturesCallable c = new LoadTexturesCallable(this.resourceNames, ts, zBufferState);
/*  76 */     return c.call();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean processRequestAssets() {
/*  81 */     if (this.id == -1) {
/*     */       
/*  83 */       String tileKey = this.resourceNames[0] + "_" + this.resourceNames[1] + "_" + this.resourceNames[2] + "_" + this.resourceNames[3];
/*  84 */       TileQuadCached.CachedTileData cachedTileData = this.resourceGetter.getCachedTileData(tileKey);
/*  85 */       if (cachedTileData.isInitialized()) {
/*  86 */         return true;
/*     */       }
/*  88 */       TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  89 */       ZBufferState zBufferState = null;
/*  90 */       if (isTransparentFile(this.resourceNames[0]))
/*  91 */         zBufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState(); 
/*  92 */       Callable<Integer> callable = new LoadTexturesCallable(this.resourceNames, ts, zBufferState);
/*  93 */       this.LoadTexturesFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*     */     } 
/*     */     
/*  96 */     return true;
/*     */   }
/*     */   
/*     */   public boolean processWaitingAssets() {
/* 100 */     return (this.LoadTexturesFuture == null || this.LoadTexturesFuture.isDone());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean processGame() throws Exception {
/* 106 */     this.tileQuad = (Quad)createTileQuad(this.x, this.y);
/*     */     
/* 108 */     TextureState textureState = (TextureState)this.tileQuad.getRenderState(5);
/* 109 */     RenderState zBufferState = this.tileQuad.getRenderState(7);
/*     */     
/* 111 */     if (textureState.getNumberOfSetTextures() > 1 || zBufferState == null) {
/* 112 */       this.tokenTargetNode.attachStaticChild((Spatial)this.tileQuad);
/* 113 */       this.tileQuad.updateRenderState();
/* 114 */       this.tileQuad.propagateBoundToRoot();
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 120 */     return true;
/*     */   }
/*     */   
/*     */   public TileQuad createTileQuad(int x, int y) {
/* 124 */     TileQuadCached tileQuad = new TileQuadCached();
/* 125 */     setupTileQuad((TileQuad)tileQuad, x, y);
/*     */     
/* 127 */     TileQuadCached.CachedTileData cachedTileData = null;
/* 128 */     if (this.LoadTexturesFuture != null && this.LoadTexturesFuture.isDone() && !this.LoadTexturesFuture.isCancelled()) {
/*     */       
/*     */       try {
/* 131 */         cachedTileData = this.LoadTexturesFuture.get();
/*     */       }
/* 133 */       catch (Exception e) {
/* 134 */         LoadingManager.INSTANCE.sendCrash(e);
/*     */       } 
/* 136 */       this.LoadTexturesFuture = null;
/*     */     } 
/*     */     
/* 139 */     if (cachedTileData == null && this.id != -1) {
/* 140 */       TileInfo t = this.resourceGetter.getTile(this.id);
/* 141 */       if (t != null) {
/* 142 */         cachedTileData = t.cachedTile;
/*     */       }
/*     */     } 
/* 145 */     if (cachedTileData == null) {
/* 146 */       String tileKey = this.resourceNames[0] + "_" + this.resourceNames[1] + "_" + this.resourceNames[2] + "_" + this.resourceNames[3];
/* 147 */       cachedTileData = this.resourceGetter.getCachedTileData(tileKey);
/*     */     } 
/*     */     
/* 150 */     if (cachedTileData.isInitialized()) {
/* 151 */       initByCachedData(tileQuad, cachedTileData);
/*     */     } else {
/* 153 */       if (LoadingManager.TILE_TEXTURE_ATLAS) {
/* 154 */         initByLoadingDataAtlas((TileQuad)tileQuad);
/*     */       } else {
/*     */         
/* 157 */         initByLoadingData((TileQuad)tileQuad);
/*     */       } 
/* 159 */       prepareCacheData(tileQuad, cachedTileData);
/*     */     } 
/*     */     
/* 162 */     return (TileQuad)tileQuad;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void setupTileQuad(TileQuad tileQuad, int x, int y) {
/* 168 */     if (tileVertexBuffer == null) {
/* 169 */       Vector3f[] tileNormals = { new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f(0.0F, 1.0F, 0.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 175 */       Vector3f[] tileVertexes = { new Vector3f(0.0F, 0.0F, 0.0F), new Vector3f(0.0F, 0.0F, 1.0F), new Vector3f(1.0F, 0.0F, 0.0F), new Vector3f(1.0F, 0.0F, 1.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 181 */       Vector2f[] texCoords = { new Vector2f(0.0F, 1.0F), new Vector2f(0.0F, 0.0F), new Vector2f(1.0F, 1.0F), new Vector2f(1.0F, 0.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 187 */       tileVertexBuffer = BufferUtils.createFloatBuffer(tileVertexes);
/* 188 */       tileNormalBuffer = BufferUtils.createFloatBuffer(tileNormals);
/* 189 */       tileColorBuffer = BufferUtils.createFloatBuffer(colors);
/* 190 */       tileTextureMappingBuffer = BufferUtils.createFloatBuffer(texCoords);
/* 191 */       tileIndicesBuffer = BufferUtils.createIntBuffer(indexes);
/* 192 */       tileBounds = new BoundingBox(new Vector3f(0.5F, 0.0F, 0.5F), 0.5F, 0.0F, 0.5F);
/*     */     } 
/*     */ 
/*     */     
/* 196 */     tileQuad.setName("Tile_" + x + "_" + y);
/* 197 */     tileQuad.reconstruct(tileVertexBuffer, tileNormalBuffer, tileColorBuffer, new TexCoords(tileTextureMappingBuffer), tileIndicesBuffer);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     tileQuad.setModelBound((BoundingVolume)tileBounds);
/* 204 */     tileQuad.setLocalTranslation(x, 0.0F, y);
/* 205 */     tileQuad.setCullHint(Spatial.CullHint.Dynamic);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void initByLoadingDataAtlas(TileQuad tileQuad) {
/* 212 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 213 */     ts.setEnabled(true);
/*     */     
/* 215 */     tileQuad.setRenderState((RenderState)ts);
/*     */     
/* 217 */     boolean hasAlpha = false;
/* 218 */     for (int textureNumber = 0; textureNumber < 4; textureNumber++) {
/*     */       
/* 220 */       if (this.resourceNames[textureNumber].isEmpty()) {
/*     */         break;
/*     */       }
/*     */       
/* 224 */       TextureAtlasDescription.TextureDescription def = null;
/* 225 */       if (LoadingManager.TILE_TEXTURE_ATLAS) {
/* 226 */         def = TextureAtlasDescription.getTextureDescription(this.resourceNames[textureNumber], this.resourceGetter);
/*     */       }
/* 228 */       Texture texture = null;
/* 229 */       if (def == null) {
/* 230 */         texture = this.resourceGetter.getTexture(this.resourceNames[textureNumber], CacheType.CACHE_TEMPORARILY);
/*     */       } else {
/* 232 */         texture = new AtlasTexture(this.resourceGetter.getTexture(def.atlasfilename, CacheType.CACHE_TEMPORARILY));
/*     */       } 
/* 234 */       if (textureNumber == 0) {
/* 235 */         configureBackgroundTileLayer(texture);
/*     */       } else {
/* 237 */         configureOtherTileLayer(texture);
/* 238 */         if (def == null) {
/* 239 */           tileQuad.addTextureCoordinates(SpatialUtils.getTileTextureCoords());
/*     */         }
/*     */         else {
/*     */           
/* 243 */           Vector2f[] texCoords = { new Vector2f(0.0F, 1.0F), new Vector2f(0.0F, 0.0F), new Vector2f(1.0F, 1.0F), new Vector2f(1.0F, 0.0F) };
/*     */           
/* 245 */           tileQuad.addTextureCoordinates(new TexCoords(BufferUtils.createFloatBuffer(texCoords)));
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 250 */       if (def != null) {
/*     */         
/* 252 */         TexCoords coords = tileQuad.getTextureCoords(textureNumber);
/* 253 */         coords.coords.put(0, def.woffset);
/* 254 */         coords.coords.put(1, 1.0F - 0.0F + def.hoffset);
/* 255 */         coords.coords.put(2, def.woffset);
/* 256 */         coords.coords.put(3, 1.0F - def.height + def.hoffset);
/* 257 */         coords.coords.put(4, def.width + def.woffset);
/* 258 */         coords.coords.put(5, 1.0F - 0.0F + def.hoffset);
/* 259 */         coords.coords.put(6, def.width + def.woffset);
/* 260 */         coords.coords.put(7, 1.0F - def.height + def.hoffset);
/*     */       } 
/*     */       
/* 263 */       if (isTransparentFile(this.resourceNames[textureNumber])) {
/* 264 */         hasAlpha = true;
/*     */       }
/*     */       
/* 267 */       ts.setTexture(texture, textureNumber);
/*     */     } 
/*     */     
/* 270 */     if (hasAlpha) {
/* 271 */       ZBufferState zBufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/* 272 */       zBufferState.setWritable(false);
/* 273 */       tileQuad.setRenderState((RenderState)zBufferState);
/* 274 */       tileQuad.setRenderQueueMode(3);
/*     */     } else {
/* 276 */       tileQuad.setRenderQueueMode(1);
/*     */     } 
/* 278 */     tileQuad.setCullHint(Spatial.CullHint.Dynamic);
/*     */   }
/*     */   private void initByCachedData(TileQuadCached tileQuad, TileQuadCached.CachedTileData cachedTileData) {
/* 281 */     tileQuad.setDisplayListID(cachedTileData.getDisplayListId());
/* 282 */     tileQuad.setRenderState(cachedTileData.getTextureState());
/* 283 */     tileQuad.setRenderState(cachedTileData.getZBufferState());
/* 284 */     tileQuad.setTileConfigData(cachedTileData);
/* 285 */     int numberOfSetTextures = ((TextureState)cachedTileData.getTextureState()).getNumberOfSetTextures();
/*     */     
/* 287 */     if (LoadingManager.TILE_TEXTURE_ATLAS) {
/*     */       
/* 289 */       TexCoords t = cachedTileData.getTextureCoords(0);
/* 290 */       ((TexCoords)tileQuad.getTextureCoords().get(0)).coords.put(0, t.coords.get(0));
/* 291 */       ((TexCoords)tileQuad.getTextureCoords().get(0)).coords.put(1, t.coords.get(1));
/* 292 */       ((TexCoords)tileQuad.getTextureCoords().get(0)).coords.put(2, t.coords.get(2));
/* 293 */       ((TexCoords)tileQuad.getTextureCoords().get(0)).coords.put(3, t.coords.get(3));
/* 294 */       ((TexCoords)tileQuad.getTextureCoords().get(0)).coords.put(4, t.coords.get(4));
/* 295 */       ((TexCoords)tileQuad.getTextureCoords().get(0)).coords.put(5, t.coords.get(5));
/* 296 */       ((TexCoords)tileQuad.getTextureCoords().get(0)).coords.put(6, t.coords.get(6));
/* 297 */       ((TexCoords)tileQuad.getTextureCoords().get(0)).coords.put(7, t.coords.get(7));
/* 298 */       for (int i = 1; i < numberOfSetTextures; i++) {
/* 299 */         tileQuad.addTextureCoordinates(cachedTileData.getTextureCoords(i));
/*     */       }
/*     */     } else {
/*     */       
/* 303 */       for (int i = 0; i < numberOfSetTextures - 1; i++)
/* 304 */         tileQuad.addTextureCoordinates(SpatialUtils.getTileTextureCoords()); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void prepareCacheData(TileQuadCached tileQuad, TileQuadCached.CachedTileData cachedTileData) {
/* 309 */     cachedTileData.init((TextureState)tileQuad.getRenderState(RenderState.StateType.Texture), (ZBufferState)tileQuad.getRenderState(RenderState.StateType.ZBuffer), tileQuad.getTextureCoords());
/*     */ 
/*     */     
/* 312 */     tileQuad.setTileConfigData(cachedTileData);
/*     */   }
/*     */   
/*     */   public class LoadTexturesCallable
/*     */     implements Callable {
/* 317 */     String[] resourcesName = null;
/*     */     private TextureState ts;
/*     */     private ZBufferState zBufferState;
/*     */     
/*     */     public LoadTexturesCallable(String[] resourcesName, TextureState ts, ZBufferState zBufferState) {
/* 322 */       this.resourcesName = resourcesName;
/* 323 */       this.ts = ts;
/* 324 */       this.zBufferState = zBufferState;
/*     */     }
/*     */     
/*     */     public TileQuadCached.CachedTileData call() {
/* 328 */       String tileKey = CreateClientLayeredTextureTileLMToken.this.resourceNames[0] + "_" + CreateClientLayeredTextureTileLMToken.this.resourceNames[1] + "_" + CreateClientLayeredTextureTileLMToken.this.resourceNames[2] + "_" + CreateClientLayeredTextureTileLMToken.this.resourceNames[3];
/* 329 */       TileQuadCached.CachedTileData cachedTileData = CreateClientLayeredTextureTileLMToken.this.resourceGetter.getCachedTileData(tileKey);
/*     */       
/* 331 */       ArrayList<TexCoords> texCoords = null;
/* 332 */       if (LoadingManager.TILE_TEXTURE_ATLAS) {
/* 333 */         texCoords = new ArrayList<TexCoords>();
/* 334 */         Vector2f[] t = { new Vector2f(0.0F, 1.0F), new Vector2f(0.0F, 0.0F), new Vector2f(1.0F, 1.0F), new Vector2f(1.0F, 0.0F) };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 340 */         texCoords.add(new TexCoords(BufferUtils.createFloatBuffer(t)));
/*     */       } 
/*     */       
/* 343 */       if (!cachedTileData.isInitialized()) {
/*     */         
/* 345 */         int nMax = this.resourcesName.length;
/* 346 */         for (int textureNumber = 0; textureNumber < nMax; textureNumber++) {
/* 347 */           if (!this.resourcesName[textureNumber].isEmpty() && this.resourcesName[textureNumber].compareTo("") != 0) {
/*     */             
/* 349 */             TextureAtlasDescription.TextureDescription def = null;
/* 350 */             if (LoadingManager.TILE_TEXTURE_ATLAS) {
/* 351 */               def = TextureAtlasDescription.getTextureDescription(CreateClientLayeredTextureTileLMToken.this.resourceNames[textureNumber], CreateClientLayeredTextureTileLMToken.this.resourceGetter);
/*     */             }
/* 353 */             Texture texture = null;
/* 354 */             if (def == null) {
/* 355 */               texture = CreateClientLayeredTextureTileLMToken.this.resourceGetter.getTexture(CreateClientLayeredTextureTileLMToken.this.resourceNames[textureNumber], CacheType.CACHE_TEMPORARILY);
/*     */             } else {
/* 357 */               texture = new AtlasTexture(CreateClientLayeredTextureTileLMToken.this.resourceGetter.getTexture(def.atlasfilename, CacheType.CACHE_TEMPORARILY));
/*     */             } 
/* 359 */             if (textureNumber == 0) {
/* 360 */               AbstractCreateLayeredTextureTileLMToken.configureBackgroundTileLayer(texture);
/*     */             } else {
/* 362 */               AbstractCreateLayeredTextureTileLMToken.configureOtherTileLayer(texture);
/* 363 */               if (def != null) {
/*     */                 
/* 365 */                 Vector2f[] texCoord = { new Vector2f(0.0F, 1.0F), new Vector2f(0.0F, 0.0F), new Vector2f(1.0F, 1.0F), new Vector2f(1.0F, 0.0F) };
/*     */                 
/* 367 */                 texCoords.add(new TexCoords(BufferUtils.createFloatBuffer(texCoord)));
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/* 372 */             if (def != null) {
/* 373 */               TexCoords coords = texCoords.get(textureNumber);
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
/* 384 */               coords.coords.put(0, def.woffset);
/* 385 */               coords.coords.put(1, 0.0F - def.hoffset);
/* 386 */               coords.coords.put(2, def.woffset);
/* 387 */               coords.coords.put(3, 0.0F - def.height + def.hoffset);
/* 388 */               coords.coords.put(4, def.width + def.woffset);
/* 389 */               coords.coords.put(5, 0.0F - def.hoffset);
/* 390 */               coords.coords.put(6, def.width + def.woffset);
/* 391 */               coords.coords.put(7, 0.0F - def.height + def.hoffset);
/*     */             } 
/* 393 */             this.ts.setTexture(texture, textureNumber);
/*     */           } 
/*     */         } 
/*     */         
/* 397 */         if (this.zBufferState != null) {
/* 398 */           this.zBufferState.setWritable(false);
/*     */         }
/*     */         
/* 401 */         cachedTileData.init(this.ts, this.zBufferState, texCoords);
/*     */       } 
/*     */       
/* 404 */       return cachedTileData;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\CreateClientLayeredTextureTileLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */