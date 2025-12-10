/*    */ package com.funcom.tcg.client.token;
/*    */ 
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ import com.funcom.gameengine.model.token.CreateWaterPondToken;
/*    */ import com.funcom.gameengine.model.token.TokenTargetNode;
/*    */ import com.funcom.gameengine.view.water.WaterMesh;
/*    */ import com.funcom.gameengine.view.water.WaterPondCoordinateSet;
/*    */ import com.funcom.tcg.client.controllers.WaterSplashController;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.jme.scene.Controller;
/*    */ import java.awt.Point;
/*    */ import java.util.List;
/*    */ 
/*    */ public class CreateClientWaterPondToken extends CreateWaterPondToken {
/*    */   protected CreateClientWaterPondToken(float height, float transparency, boolean useShoreTexture, float baseTextureScale, float overlayTextureScale, float shoreTextureScale, float shoreTextureOffset, List<WaterPondCoordinateSet> pondPoints, float baseSpeedX, float baseSpeedY, float overlaySpeedX, float overlaySpeedY, float shoreSpeed, String baseTexture, String overlayTexture, String shoreTexture, TokenTargetNode tokenTargetNode, Point tileCoord, ResourceGetter resourceGetter) {
/* 16 */     super(height, transparency, useShoreTexture, baseTextureScale, overlayTextureScale, shoreTextureScale, shoreTextureOffset, pondPoints, baseSpeedX, baseSpeedY, overlaySpeedX, overlaySpeedY, shoreSpeed, baseTexture, overlayTexture, shoreTexture, tokenTargetNode, tileCoord, resourceGetter);
/*    */   }
/*    */ 
/*    */   
/*    */   public void process() {
/* 21 */     super.process();
/* 22 */     this.waterPond.addController((Controller)new WaterSplashController((WaterMesh)this.waterPond, MainGameState.getPlayerNode()));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\token\CreateClientWaterPondToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */