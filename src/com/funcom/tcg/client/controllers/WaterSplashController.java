/*    */ package com.funcom.tcg.client.controllers;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.water.WaterMesh;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.intersection.CollisionData;
/*    */ import com.jme.intersection.CollisionResults;
/*    */ import com.jme.intersection.TriangleCollisionResults;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.state.TextureState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ 
/*    */ public class WaterSplashController
/*    */   extends Controller
/*    */ {
/*    */   public static final float SPLASH_DELAY = 0.5F;
/*    */   public static final float SPLASH_FADEOUT = 1.0F;
/*    */   private WaterMesh waterMesh;
/*    */   private PropNode playerNode;
/*    */   private CollisionResults collisionResults;
/*    */   private TextureState splashTextureState;
/*    */   private Vector3f[] vertices;
/*    */   private float delayRemaining;
/*    */   private WorldCoordinate lastPosition;
/*    */   
/*    */   public WaterSplashController(WaterMesh waterMesh, PropNode playerNode) {
/* 30 */     this.waterMesh = waterMesh;
/* 31 */     this.playerNode = playerNode;
/*    */     
/* 33 */     this.collisionResults = (CollisionResults)new TriangleCollisionResults();
/* 34 */     this.vertices = new Vector3f[3];
/* 35 */     this.splashTextureState = DisplaySystem.getDisplaySystem().getRenderer().createTextureState();
/* 36 */     Texture splashTexture = (Texture)TcgGame.getResourceManager().getResource(Texture.class, "splash2.png");
/* 37 */     splashTexture.setWrap(Texture.WrapMode.BorderClamp);
/* 38 */     this.splashTextureState.setTexture(splashTexture);
/* 39 */     this.delayRemaining = 0.0F;
/* 40 */     this.lastPosition = new WorldCoordinate(playerNode.getPosition());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(float time) {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private float maxHeight(CollisionResults collisionResult) {
/* 80 */     float maxHeight = 0.0F;
/* 81 */     for (int i = 0; i < collisionResult.getNumber(); i++) {
/* 82 */       CollisionData collisionData = this.collisionResults.getCollisionData(i);
/* 83 */       for (Integer triIndex : collisionData.getSourceTris()) {
/* 84 */         this.waterMesh.getTriangle(triIndex.intValue(), this.vertices);
/* 85 */         for (Vector3f vertex : this.vertices) {
/* 86 */           maxHeight = Math.max(maxHeight, vertex.y);
/*    */         }
/*    */       } 
/*    */     } 
/*    */     
/* 91 */     return maxHeight;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\controllers\WaterSplashController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */