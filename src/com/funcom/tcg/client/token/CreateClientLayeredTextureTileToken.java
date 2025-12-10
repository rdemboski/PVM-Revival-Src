/*    */ package com.funcom.tcg.client.token;
/*    */ 
/*    */ import com.funcom.gameengine.jme.TileQuad;
/*    */ import com.funcom.gameengine.jme.TileQuadCached;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.token.AbstractCreateLayeredTextureTileToken;
/*    */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.CreateClientLayeredTextureTileLMToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.utils.SpatialUtils;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.scene.state.ZBufferState;
/*    */ import java.awt.Point;
/*    */ 
/*    */ 
/*    */ public class CreateClientLayeredTextureTileToken
/*    */   extends AbstractCreateLayeredTextureTileToken
/*    */ {
/*    */   public CreateClientLayeredTextureTileToken(int x, int y, String backgroundResourceName, String layer2ResourceName, String layer3ResourceName, String layer4ResourceName, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 23 */     super(x, y, backgroundResourceName, layer2ResourceName, layer3ResourceName, layer4ResourceName, tokenTargetNode, tileCoord, resourceGetter);
/*    */   }
/*    */ 
/*    */   
/*    */   protected boolean isTransparentFile(String fileName) {
/* 28 */     return (fileName.indexOf("transparent_base") >= 0);
/*    */   }
/*    */ 
/*    */   
/*    */   public void process() {
/* 33 */     if (LoadingManager.USE) {
/* 34 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new CreateClientLayeredTextureTileLMToken(this.x, this.y, this.resourceNames[0], this.resourceNames[1], this.resourceNames[2], this.resourceNames[3], this.tokenTargetNode, this.tileCoord, this.resourceGetter, -1));
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 39 */       TileQuad tileQuad = createTileQuad(this.x, this.y);
/*    */       
/* 41 */       TextureState textureState = (TextureState)tileQuad.getRenderState(5);
/* 42 */       RenderState zBufferState = tileQuad.getRenderState(7);
/*    */       
/* 44 */       if (textureState.getNumberOfSetTextures() > 1 || zBufferState == null) {
/* 45 */         this.tokenTargetNode.attachStaticChild((Spatial)tileQuad);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public TileQuad createTileQuad(int x, int y) {
/* 56 */     TileQuadCached tileQuad = new TileQuadCached();
/*    */     
/* 58 */     SpatialUtils.setupTileQuad((TileQuad)tileQuad, x, y);
/*    */     
/* 60 */     String tileKey = this.resourceNames[0] + this.resourceNames[1] + this.resourceNames[2] + this.resourceNames[3];
/*    */     
/* 62 */     TileQuadCached.CachedTileData cachedTileData = this.resourceGetter.getCachedTileData(tileKey);
/*    */     
/* 64 */     if (cachedTileData.isInitialized()) {
/* 65 */       initByCachedData(tileQuad, cachedTileData);
/*    */     } else {
/* 67 */       initByLoadingData((TileQuad)tileQuad);
/*    */       
/* 69 */       prepareCacheData(tileQuad, cachedTileData);
/*    */     } 
/*    */     
/* 72 */     return (TileQuad)tileQuad;
/*    */   }
/*    */   
/*    */   private void initByCachedData(TileQuadCached tileQuad, TileQuadCached.CachedTileData cachedTileData) {
/* 76 */     tileQuad.setDisplayListID(cachedTileData.getDisplayListId());
/* 77 */     tileQuad.setRenderState(cachedTileData.getTextureState());
/* 78 */     tileQuad.setRenderState(cachedTileData.getZBufferState());
/* 79 */     tileQuad.setTileConfigData(cachedTileData);
/* 80 */     int numberOfSetTextures = ((TextureState)cachedTileData.getTextureState()).getNumberOfSetTextures();
/* 81 */     for (int i = 0; i < numberOfSetTextures - 1; i++)
/* 82 */       tileQuad.addTextureCoordinates(SpatialUtils.getTileTextureCoords()); 
/*    */   }
/*    */   
/*    */   private void prepareCacheData(TileQuadCached tileQuad, TileQuadCached.CachedTileData cachedTileData) {
/* 86 */     cachedTileData.init((TextureState)tileQuad.getRenderState(RenderState.StateType.Texture), (ZBufferState)tileQuad.getRenderState(RenderState.StateType.ZBuffer), tileQuad.getTextureCoords());
/*    */ 
/*    */     
/* 89 */     tileQuad.setTileConfigData(cachedTileData);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\token\CreateClientLayeredTextureTileToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */