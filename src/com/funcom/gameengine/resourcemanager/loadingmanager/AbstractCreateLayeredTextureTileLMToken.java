/*     */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.jme.TileQuad;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.awt.Point;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCreateLayeredTextureTileLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*     */   protected int x;
/*     */   protected int y;
/*  29 */   protected String[] resourceNames = new String[4];
/*     */   
/*     */   protected TokenTargetNode tokenTargetNode;
/*     */   
/*     */   protected Point tileCoord;
/*     */   protected ResourceGetter resourceGetter;
/*     */   
/*     */   public AbstractCreateLayeredTextureTileLMToken(int x, int y, String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  37 */     this.x = x;
/*  38 */     this.y = y;
/*  39 */     this.resourceNames[0] = backgroundResourceName;
/*  40 */     this.resourceNames[1] = layer2ResourceName;
/*  41 */     this.resourceNames[2] = layer3ResourceName;
/*  42 */     this.resourceNames[3] = layer4ResourceName;
/*  43 */     this.tokenTargetNode = tokenTargetNode;
/*  44 */     this.tileCoord = tileCoord;
/*  45 */     this.resourceGetter = resourceGetter;
/*     */   }
/*     */   
/*     */   public TileQuad createTileQuad(int x, int y) {
/*  49 */     TileQuad tileQuad = new TileQuad();
/*  50 */     SpatialUtils.setupTileQuad(tileQuad, x, y);
/*     */     
/*  52 */     initByLoadingData(tileQuad);
/*     */     
/*  54 */     return tileQuad;
/*     */   }
/*     */   
/*     */   protected void initByLoadingData(TileQuad tileQuad) {
/*  58 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  59 */     ts.setEnabled(true);
/*     */     
/*  61 */     tileQuad.setRenderState((RenderState)ts);
/*     */     
/*  63 */     boolean hasAlpha = false;
/*  64 */     for (int textureNumber = 0; textureNumber < 4; textureNumber++) {
/*     */       
/*  66 */       if (this.resourceNames[textureNumber].isEmpty()) {
/*     */         break;
/*     */       }
/*  69 */       Texture texture = this.resourceGetter.getTexture(this.resourceNames[textureNumber], CacheType.CACHE_TEMPORARILY);
/*  70 */       if (textureNumber == 0) {
/*  71 */         configureBackgroundTileLayer(texture);
/*     */       } else {
/*  73 */         configureOtherTileLayer(texture);
/*  74 */         tileQuad.addTextureCoordinates(SpatialUtils.getTileTextureCoords());
/*     */       } 
/*     */       
/*  77 */       if (isTransparentFile(this.resourceNames[textureNumber])) {
/*  78 */         hasAlpha = true;
/*     */       }
/*     */       
/*  81 */       ts.setTexture(texture, textureNumber);
/*     */     } 
/*     */     
/*  84 */     if (hasAlpha) {
/*  85 */       ZBufferState zBufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/*  86 */       zBufferState.setWritable(false);
/*  87 */       tileQuad.setRenderState((RenderState)zBufferState);
/*  88 */       tileQuad.setRenderQueueMode(3);
/*     */     } else {
/*  90 */       tileQuad.setRenderQueueMode(1);
/*     */     } 
/*  92 */     tileQuad.setCullHint(Spatial.CullHint.Dynamic);
/*     */   }
/*     */   
/*     */   protected void initByLoadingDataAsynch(TileQuad tileQuad, ArrayList<Texture> textures) {
/*  96 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  97 */     ts.setEnabled(true);
/*     */     
/*  99 */     tileQuad.setRenderState((RenderState)ts);
/*     */     
/* 101 */     boolean hasAlpha = false;
/* 102 */     for (int textureNumber = 0; textureNumber < 4; textureNumber++) {
/*     */       
/* 104 */       if (this.resourceNames[textureNumber].isEmpty()) {
/*     */         break;
/*     */       }
/*     */       
/* 108 */       Texture texture = textures.get(textureNumber);
/*     */       
/* 110 */       if (textureNumber == 0) {
/* 111 */         configureBackgroundTileLayer(texture);
/*     */       } else {
/* 113 */         configureOtherTileLayer(texture);
/* 114 */         tileQuad.addTextureCoordinates(SpatialUtils.getTileTextureCoords());
/*     */       } 
/*     */       
/* 117 */       if (isTransparentFile(this.resourceNames[textureNumber])) {
/* 118 */         hasAlpha = true;
/*     */       }
/*     */       
/* 121 */       ts.setTexture(texture, textureNumber);
/*     */     } 
/*     */     
/* 124 */     if (hasAlpha) {
/* 125 */       ZBufferState zBufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/* 126 */       zBufferState.setWritable(false);
/* 127 */       tileQuad.setRenderState((RenderState)zBufferState);
/* 128 */       tileQuad.setRenderQueueMode(3);
/*     */     } else {
/* 130 */       tileQuad.setRenderQueueMode(1);
/*     */     } 
/* 132 */     tileQuad.setCullHint(Spatial.CullHint.Dynamic);
/*     */   }
/*     */   
/*     */   public static void configureBackgroundTileLayer(Texture texture) {
/* 136 */     texture.setApply(Texture.ApplyMode.Replace);
/*     */   }
/*     */   
/*     */   public static void configureOtherTileLayer(Texture texture) {
/* 140 */     texture.setApply(Texture.ApplyMode.Combine);
/* 141 */     texture.setCombineFuncRGB(Texture.CombinerFunctionRGB.Interpolate);
/* 142 */     texture.setCombineFuncAlpha(Texture.CombinerFunctionAlpha.Add);
/*     */     
/* 144 */     texture.setCombineSrc0RGB(Texture.CombinerSource.CurrentTexture);
/* 145 */     texture.setCombineOp0RGB(Texture.CombinerOperandRGB.SourceColor);
/*     */     
/* 147 */     texture.setCombineSrc1RGB(Texture.CombinerSource.Previous);
/* 148 */     texture.setCombineOp1RGB(Texture.CombinerOperandRGB.SourceColor);
/*     */     
/* 150 */     texture.setCombineSrc2RGB(Texture.CombinerSource.CurrentTexture);
/* 151 */     texture.setCombineOp2RGB(Texture.CombinerOperandRGB.SourceAlpha);
/*     */   }
/*     */   
/*     */   protected abstract boolean isTransparentFile(String paramString);
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\AbstractCreateLayeredTextureTileLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */