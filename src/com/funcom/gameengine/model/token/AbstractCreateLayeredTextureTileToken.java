/*     */ package com.funcom.gameengine.model.token;
/*     */ 
/*     */ import com.funcom.gameengine.jme.TileQuad;
/*     */ import com.funcom.gameengine.model.ResourceGetter;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.jme.image.Texture;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.scene.state.RenderState;
/*     */ import com.jme.scene.state.TextureState;
/*     */ import com.jme.scene.state.ZBufferState;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import java.awt.Point;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractCreateLayeredTextureTileToken
/*     */   implements Token
/*     */ {
/*     */   protected int x;
/*     */   protected int y;
/*  24 */   protected String[] resourceNames = new String[4];
/*     */   protected TokenTargetNode tokenTargetNode;
/*     */   protected Point tileCoord;
/*     */   protected ResourceGetter resourceGetter;
/*     */   
/*     */   public AbstractCreateLayeredTextureTileToken(int x, int y, String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/*  30 */     this.x = x;
/*  31 */     this.y = y;
/*  32 */     this.resourceNames[0] = backgroundResourceName;
/*  33 */     this.resourceNames[1] = layer2ResourceName;
/*  34 */     this.resourceNames[2] = layer3ResourceName;
/*  35 */     this.resourceNames[3] = layer4ResourceName;
/*  36 */     this.tokenTargetNode = tokenTargetNode;
/*  37 */     this.tileCoord = tileCoord;
/*  38 */     this.resourceGetter = resourceGetter;
/*     */   }
/*     */   
/*     */   public Token.TokenType getTokenType() {
/*  42 */     return Token.TokenType.GAME_THREAD;
/*     */   }
/*     */   
/*     */   public TileQuad createTileQuad(int x, int y) {
/*  46 */     TileQuad tileQuad = new TileQuad();
/*  47 */     SpatialUtils.setupTileQuad(tileQuad, x, y);
/*     */     
/*  49 */     initByLoadingData(tileQuad);
/*     */     
/*  51 */     return tileQuad;
/*     */   }
/*     */   
/*     */   protected void initByLoadingData(TileQuad tileQuad) {
/*  55 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/*  56 */     ts.setEnabled(true);
/*     */     
/*  58 */     tileQuad.setRenderState((RenderState)ts);
/*     */     
/*  60 */     boolean hasAlpha = false;
/*  61 */     for (int textureNumber = 0; textureNumber < 4; textureNumber++) {
/*     */       
/*  63 */       if (this.resourceNames[textureNumber].isEmpty()) {
/*     */         break;
/*     */       }
/*  66 */       Texture texture = this.resourceGetter.getTexture(this.resourceNames[textureNumber], CacheType.CACHE_TEMPORARILY);
/*  67 */       if (textureNumber == 0) {
/*  68 */         configureBackgroundTileLayer(texture);
/*     */       } else {
/*  70 */         configureOtherTileLayer(texture);
/*  71 */         tileQuad.addTextureCoordinates(SpatialUtils.getTileTextureCoords());
/*     */       } 
/*     */       
/*  74 */       if (isTransparentFile(this.resourceNames[textureNumber])) {
/*  75 */         hasAlpha = true;
/*     */       }
/*     */       
/*  78 */       ts.setTexture(texture, textureNumber);
/*     */     } 
/*     */     
/*  81 */     if (hasAlpha) {
/*  82 */       ZBufferState zBufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/*  83 */       zBufferState.setWritable(false);
/*  84 */       tileQuad.setRenderState((RenderState)zBufferState);
/*  85 */       tileQuad.setRenderQueueMode(3);
/*     */     } else {
/*  87 */       tileQuad.setRenderQueueMode(1);
/*     */     } 
/*  89 */     tileQuad.setCullHint(Spatial.CullHint.Dynamic);
/*     */   }
/*     */   
/*     */   public static void configureBackgroundTileLayer(Texture texture) {
/*  93 */     texture.setApply(Texture.ApplyMode.Replace);
/*     */   }
/*     */   
/*     */   public static void configureOtherTileLayer(Texture texture) {
/*  97 */     texture.setApply(Texture.ApplyMode.Combine);
/*  98 */     texture.setCombineFuncRGB(Texture.CombinerFunctionRGB.Interpolate);
/*  99 */     texture.setCombineFuncAlpha(Texture.CombinerFunctionAlpha.Add);
/*     */     
/* 101 */     texture.setCombineSrc0RGB(Texture.CombinerSource.CurrentTexture);
/* 102 */     texture.setCombineOp0RGB(Texture.CombinerOperandRGB.SourceColor);
/*     */     
/* 104 */     texture.setCombineSrc1RGB(Texture.CombinerSource.Previous);
/* 105 */     texture.setCombineOp1RGB(Texture.CombinerOperandRGB.SourceColor);
/*     */     
/* 107 */     texture.setCombineSrc2RGB(Texture.CombinerSource.CurrentTexture);
/* 108 */     texture.setCombineOp2RGB(Texture.CombinerOperandRGB.SourceAlpha);
/*     */   }
/*     */   
/*     */   protected abstract boolean isTransparentFile(String paramString);
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\AbstractCreateLayeredTextureTileToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */