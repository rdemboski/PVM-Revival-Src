/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.view.TransparentAlphaState;
/*    */ import com.funcom.gameengine.view.water.SinusShoreController;
/*    */ import com.funcom.gameengine.view.water.WaterFactory;
/*    */ import com.funcom.gameengine.view.water.WaterMesh;
/*    */ import com.funcom.gameengine.view.water.WaterOffsetController;
/*    */ import com.funcom.gameengine.view.water.WaterPond;
/*    */ import com.funcom.gameengine.view.water.WaterPondCoordinateSet;
/*    */ import com.jme.bounding.BoundingBox;
/*    */ import com.jme.bounding.BoundingVolume;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.state.RenderState;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import java.awt.Point;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CreateWaterPondToken
/*    */   extends CreateWaterToken
/*    */ {
/*    */   protected WaterPond waterPond;
/*    */   private float height;
/*    */   private float transparency;
/*    */   private boolean useShoreTexture;
/*    */   private float baseTextureScale;
/*    */   private float overlayTextureScale;
/*    */   private float shoreTextureScale;
/*    */   private float shoreTextureOffset;
/*    */   private List<WaterPondCoordinateSet> pondPoints;
/*    */   private float baseSpeedX;
/*    */   private float baseSpeedY;
/*    */   private float overlaySpeedX;
/*    */   private float overlaySpeedY;
/*    */   private float shoreSpeed;
/*    */   private String baseTexture;
/*    */   private String overlayTexture;
/*    */   private String shoreTexture;
/*    */   protected TokenTargetNode tokenTargetNode;
/*    */   private Point tileCoord;
/*    */   
/*    */   protected CreateWaterPondToken(float height, float transparency, boolean useShoreTexture, float baseTextureScale, float overlayTextureScale, float shoreTextureScale, float shoreTextureOffset, List<WaterPondCoordinateSet> pondPoints, float baseSpeedX, float baseSpeedY, float overlaySpeedX, float overlaySpeedY, float shoreSpeed, String baseTexture, String overlayTexture, String shoreTexture, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 46 */     super(resourceGetter);
/*    */     
/* 48 */     this.height = height;
/* 49 */     this.transparency = transparency;
/* 50 */     this.useShoreTexture = useShoreTexture;
/* 51 */     this.baseTextureScale = baseTextureScale;
/* 52 */     this.overlayTextureScale = overlayTextureScale;
/* 53 */     this.shoreTextureScale = shoreTextureScale;
/* 54 */     this.shoreTextureOffset = shoreTextureOffset;
/* 55 */     this.pondPoints = pondPoints;
/* 56 */     this.baseSpeedX = baseSpeedX;
/* 57 */     this.baseSpeedY = baseSpeedY;
/* 58 */     this.overlaySpeedX = overlaySpeedX;
/* 59 */     this.overlaySpeedY = overlaySpeedY;
/* 60 */     this.shoreSpeed = shoreSpeed;
/* 61 */     this.baseTexture = baseTexture;
/* 62 */     this.overlayTexture = overlayTexture;
/* 63 */     this.shoreTexture = shoreTexture;
/* 64 */     this.tokenTargetNode = tokenTargetNode;
/* 65 */     this.tileCoord = tileCoord;
/*    */   }
/*    */   
/*    */   public Token.TokenType getTokenType() {
/* 69 */     return Token.TokenType.GAME_THREAD;
/*    */   }
/*    */   
/*    */   protected WaterPond createWaterPond() {
/* 73 */     TextureState textureState = WaterFactory.createTextureState(this.resourceGetter, this.baseTexture, this.overlayTexture, this.shoreTexture);
/* 74 */     WaterPond waterPond = new WaterPond(this.height, this.useShoreTexture, this.baseTextureScale, 0.0F, 0.0F, this.overlayTextureScale, 0.0F, 0.0F, this.shoreTextureScale, this.shoreTextureOffset, this.pondPoints);
/*    */ 
/*    */ 
/*    */     
/* 78 */     waterPond.setRenderState((RenderState)textureState);
/* 79 */     waterPond.setRenderState((RenderState)TransparentAlphaState.get());
/* 80 */     waterPond.setRenderQueueMode(3);
/*    */     
/* 82 */     waterPond.setModelBound((BoundingVolume)new BoundingBox());
/* 83 */     waterPond.updateModelBound();
/*    */     
/* 85 */     WaterFactory.assignWaterShaders((Spatial)waterPond, this.resourceGetter);
/* 86 */     WaterFactory.setWaterTextureTransparency((Spatial)waterPond, this.transparency);
/*    */     
/* 88 */     waterPond.addController((Controller)new SinusShoreController((WaterMesh)waterPond, this.shoreSpeed));
/* 89 */     waterPond.addController((Controller)new WaterOffsetController((WaterMesh)waterPond, this.baseSpeedX, this.baseSpeedY, this.overlaySpeedX, this.overlaySpeedY));
/*    */     
/* 91 */     waterPond.updateWorldVectors();
/*    */     
/* 93 */     return waterPond;
/*    */   }
/*    */   
/*    */   public void process() {
/* 97 */     this.waterPond = createWaterPond();
/* 98 */     this.tokenTargetNode.attachAnimatedChild((Spatial)this.waterPond);
/* 99 */     this.tokenTargetNode.updateRenderState();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\CreateWaterPondToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */