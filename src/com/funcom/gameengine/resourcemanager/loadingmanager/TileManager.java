/*     */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.jme.TileQuad;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.TriMesh;
/*     */ import com.jme.scene.shape.Quad;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Hashtable;
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
/*     */ 
/*     */ public enum TileManager
/*     */ {
/*  34 */   INSTANCE;
/*     */   
/*     */   public Hashtable<String, TileRenderer> TileCache;
/*     */   
/*     */   TileManager() {
/*  39 */     this.TileCache = new Hashtable<String, TileRenderer>();
/*     */   }
/*     */   
/*     */   public void remove(TileRenderer tile) {
/*  43 */     if (tile != null && 
/*  44 */       tile.release()) {
/*  45 */       this.TileCache.remove(tile);
/*  46 */       tile = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Tile getTile(String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, ResourceGetter resourceGetter, TokenTargetNode tokenTargetNode) {
/*  54 */     TileRenderer tr = getTileRenderer(backgroundResourceName, layer2ResourceName, layer3ResourceName, layer4ResourceName, resourceGetter, tokenTargetNode);
/*     */     
/*  56 */     Tile tile = new Tile(tr);
/*     */     
/*  58 */     return tile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TileRenderer getTileRenderer(String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, ResourceGetter resourceGetter, TokenTargetNode tokenTargetNode) {
/*  66 */     String tileKey = backgroundResourceName + "_" + layer2ResourceName + "_" + layer3ResourceName + "_" + layer4ResourceName;
/*  67 */     TileRenderer tile = this.TileCache.get(tileKey);
/*     */ 
/*     */     
/*  70 */     if (tile == null) {
/*  71 */       tile = new TileRenderer(backgroundResourceName, layer2ResourceName, layer3ResourceName, layer4ResourceName, resourceGetter, tokenTargetNode);
/*     */       
/*  73 */       this.TileCache.put(tileKey, tile);
/*  74 */       tile.load();
/*     */     } 
/*     */     
/*  77 */     tile.addRef();
/*  78 */     return tile;
/*     */   }
/*     */   
/*     */   class Tile
/*     */     extends Quad
/*     */   {
/*  84 */     private TileManager.TileRenderer mTileRenderer = null;
/*     */     
/*     */     public Tile(TileManager.TileRenderer tr) {
/*  87 */       this.mTileRenderer = tr;
/*     */     }
/*     */ 
/*     */     
/*     */     public void draw(Renderer r) {
/*  92 */       if (this.mTileRenderer != null)
/*  93 */         if (!r.isProcessingQueue()) {
/*  94 */           super.draw(r);
/*     */         } else {
/*  96 */           this.mTileRenderer.draw(r, getCenter().getX(), getCenter().getY(), getCenter().getZ());
/*     */         }  
/*     */     }
/*     */   }
/*     */   
/*     */   class TileRenderer
/*     */     extends TriMesh
/*     */   {
/* 104 */     private int mUsers = 0;
/*     */     private boolean mLoaded = false;
/* 106 */     private TileQuad mTile = null;
/*     */     
/*     */     private String mBackgroundResourceName;
/*     */     private String mLayer2ResourceName;
/*     */     private String mLayer3ResourceName;
/*     */     private String mLayer4ResourceName;
/* 112 */     private ResourceGetter mResourceGetter = null;
/*     */     
/* 114 */     private ArrayList<String> mListResourceNames = null;
/* 115 */     private Future<TileQuad> mLoadTileFuture = null;
/* 116 */     private TokenTargetNode mTokenTargetNode = null;
/*     */ 
/*     */     
/*     */     public TileRenderer(String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, ResourceGetter resourceGetter, TokenTargetNode tokenTargetNode) {
/* 120 */       this.mBackgroundResourceName = backgroundResourceName;
/* 121 */       this.mLayer2ResourceName = layer2ResourceName;
/* 122 */       this.mLayer3ResourceName = layer3ResourceName;
/* 123 */       this.mLayer4ResourceName = layer4ResourceName;
/* 124 */       this.mResourceGetter = resourceGetter;
/* 125 */       this.mTokenTargetNode = tokenTargetNode;
/*     */     }
/*     */     
/*     */     public void draw(Renderer r, float x, float y, float z) {
/* 129 */       if (this.mLoaded) {
/*     */         
/* 131 */         for (int n = 0; n < (RenderState.StateType.values()).length; n++);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 137 */         this.mTile.draw(r);
/*     */       } else {
/*     */         
/* 140 */         load();
/*     */       } 
/*     */     }
/*     */     
/*     */     public void load() {
/* 145 */       if (!this.mLoaded) {
/* 146 */         if (this.mLoadTileFuture == null) {
/*     */           
/* 148 */           this.mListResourceNames = new ArrayList<String>();
/* 149 */           this.mListResourceNames.add(this.mBackgroundResourceName);
/* 150 */           this.mListResourceNames.add(this.mLayer2ResourceName);
/* 151 */           this.mListResourceNames.add(this.mLayer3ResourceName);
/* 152 */           this.mListResourceNames.add(this.mLayer4ResourceName);
/* 153 */           Callable<Integer> callable = new TileManager.LoadTileCallable(this.mListResourceNames, this.mResourceGetter);
/* 154 */           this.mLoadTileFuture = (Future)LoadingManager.INSTANCE.submitCallable(callable);
/*     */         
/*     */         }
/* 157 */         else if (this.mLoadTileFuture.isDone()) {
/* 158 */           if (!this.mLoadTileFuture.isCancelled()) {
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */ 
/*     */               
/* 165 */               this.mTile = this.mLoadTileFuture.get();
/*     */               
/* 167 */               boolean hasAlpha = false;
/* 168 */               for (int textureNumber = 0; textureNumber < 4 && 
/* 169 */                 !((String)this.mListResourceNames.get(textureNumber)).isEmpty(); textureNumber++) {
/*     */ 
/*     */                 
/* 172 */                 if (isTransparentFile(this.mListResourceNames.get(textureNumber))) {
/* 173 */                   hasAlpha = true;
/*     */                 }
/*     */               } 
/*     */               
/* 177 */               if (hasAlpha) {
/* 178 */                 ZBufferState zBufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/* 179 */                 zBufferState.setWritable(false);
/* 180 */                 setRenderState((RenderState)zBufferState);
/* 181 */                 setRenderQueueMode(3);
/*     */               } else {
/* 183 */                 setRenderQueueMode(1);
/*     */               } 
/* 185 */               setCullHint(Spatial.CullHint.Never);
/* 186 */               updateRenderState();
/*     */ 
/*     */ 
/*     */             
/*     */             }
/* 191 */             catch (Exception e) {
/*     */               
/* 193 */               System.out.printf("TileManager error while loading a tile.\n", new Object[0]);
/* 194 */               this.mTile = null;
/*     */             } 
/*     */           }
/* 197 */           this.mLoadTileFuture = null;
/* 198 */           this.mListResourceNames = null;
/* 199 */           this.mLoaded = true;
/*     */         } 
/*     */       }
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isTransparentFile(String fileName) {
/* 206 */       return (fileName.indexOf("transparent_base") >= 0);
/*     */     }
/*     */ 
/*     */     
/*     */     public void addRef() {
/* 211 */       this.mUsers++;
/*     */     }
/*     */     
/*     */     public boolean release() {
/* 215 */       this.mUsers--;
/* 216 */       return (this.mUsers == 0);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public class LoadTileCallable
/*     */     implements Callable
/*     */   {
/* 224 */     ArrayList<String> resourcesName = null;
/* 225 */     ResourceGetter mResourceGetter = null;
/*     */     
/*     */     public LoadTileCallable(ArrayList<String> resourcesName, ResourceGetter resourceGetter) {
/* 228 */       this.resourcesName = resourcesName;
/* 229 */       this.mResourceGetter = resourceGetter;
/*     */     }
/*     */ 
/*     */     
/*     */     public TileQuad call() {
/* 234 */       TileQuad tile = new TileQuad();
/*     */       
/* 236 */       SpatialUtils.setupTileQuad(tile, 0, 0);
/* 237 */       initByLoadingData(tile);
/* 238 */       return tile;
/*     */     }
/*     */     
/*     */     protected void initByLoadingData(TileQuad tileQuad) {
/* 242 */       TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 243 */       ts.setEnabled(true);
/*     */       
/* 245 */       tileQuad.setRenderState((RenderState)ts);
/*     */ 
/*     */       
/* 248 */       for (int textureNumber = 0; textureNumber < 4; textureNumber++) {
/*     */         
/* 250 */         if (((String)this.resourcesName.get(textureNumber)).isEmpty()) {
/*     */           break;
/*     */         }
/* 253 */         Texture texture = this.mResourceGetter.getTexture(this.resourcesName.get(textureNumber), CacheType.CACHE_TEMPORARILY);
/* 254 */         if (textureNumber == 0) {
/* 255 */           configureBackgroundTileLayer(texture);
/*     */         } else {
/* 257 */           configureOtherTileLayer(texture);
/* 258 */           tileQuad.addTextureCoordinates(SpatialUtils.getTileTextureCoords());
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 265 */         ts.setTexture(texture, textureNumber);
/*     */       } 
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
/*     */     public void configureBackgroundTileLayer(Texture texture) {
/* 282 */       texture.setApply(Texture.ApplyMode.Replace);
/*     */     }
/*     */     
/*     */     public void configureOtherTileLayer(Texture texture) {
/* 286 */       texture.setApply(Texture.ApplyMode.Combine);
/* 287 */       texture.setCombineFuncRGB(Texture.CombinerFunctionRGB.Interpolate);
/* 288 */       texture.setCombineFuncAlpha(Texture.CombinerFunctionAlpha.Add);
/*     */       
/* 290 */       texture.setCombineSrc0RGB(Texture.CombinerSource.CurrentTexture);
/* 291 */       texture.setCombineOp0RGB(Texture.CombinerOperandRGB.SourceColor);
/*     */       
/* 293 */       texture.setCombineSrc1RGB(Texture.CombinerSource.Previous);
/* 294 */       texture.setCombineOp1RGB(Texture.CombinerOperandRGB.SourceColor);
/*     */       
/* 296 */       texture.setCombineSrc2RGB(Texture.CombinerSource.CurrentTexture);
/* 297 */       texture.setCombineOp2RGB(Texture.CombinerOperandRGB.SourceAlpha);
/*     */     }
/*     */ 
/*     */     
/*     */     protected boolean isTransparentFile(String fileName) {
/* 302 */       return (fileName.indexOf("transparent_base") >= 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\TileManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */