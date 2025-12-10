/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import com.funcom.gameengine.jme.TileQuadCached;
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.CreateClientLayeredTextureTileLMToken;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.scene.state.ZBufferState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TileInfo
/*    */ {
/* 18 */   public int index = 0;
/* 19 */   public String background = "";
/* 20 */   public String layer2 = "";
/* 21 */   public String layer3 = "";
/* 22 */   public String layer4 = "";
/* 23 */   ResourceGetter resourceGetter = null;
/* 24 */   public TileQuadCached.CachedTileData cachedTile = null;
/*    */   
/*    */   public TileInfo(String bg, String l2, String l3, String l4, ResourceGetter resourceGetter) {
/* 27 */     this.background = bg;
/* 28 */     this.layer2 = l2;
/* 29 */     this.layer3 = l3;
/* 30 */     this.layer4 = l4;
/* 31 */     this.resourceGetter = resourceGetter;
/*    */     
/* 33 */     loadCachedTile();
/*    */   }
/*    */   
/*    */   public void loadCachedTile() {
/* 37 */     String tileKey = this.background + "_" + this.layer2 + "_" + this.layer3 + "_" + this.layer4;
/* 38 */     this.cachedTile = this.resourceGetter.getCachedTileData(tileKey);
/* 39 */     if (this.cachedTile.isInitialized()) {
/*    */       return;
/*    */     }
/*    */     
/* 43 */     TextureState ts = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 44 */     ZBufferState zBufferState = null;
/* 45 */     if (isTransparentFile(this.background)) {
/* 46 */       zBufferState = DisplaySystem.getDisplaySystem().getRenderer().createZBufferState();
/*    */     }
/* 48 */     CreateClientLayeredTextureTileLMToken token = new CreateClientLayeredTextureTileLMToken(0, 0, this.background, this.layer2, this.layer3, this.layer4, null, null, this.resourceGetter, -1);
/*    */     
/* 50 */     this.cachedTile = token.loadCachedTile(ts, zBufferState);
/*    */   }
/*    */   
/*    */   protected boolean isTransparentFile(String fileName) {
/* 54 */     return (fileName.indexOf("transparent_base") >= 0);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\TileInfo.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */