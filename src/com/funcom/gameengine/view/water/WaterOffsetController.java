/*    */ package com.funcom.gameengine.view.water;
/*    */ 
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ public class WaterOffsetController
/*    */   extends Controller
/*    */ {
/*    */   private WaterMesh waterMesh;
/*    */   private float baseSpeedX;
/*    */   private float baseSpeedY;
/*    */   private float overlaySpeedX;
/*    */   private float overlaySpeedY;
/*    */   private float wrap;
/*    */   private float basePosX;
/*    */   private float basePosY;
/*    */   private float overlayPosX;
/*    */   private float overlayPosY;
/*    */   
/*    */   public WaterOffsetController(WaterMesh waterMesh, float baseSpeedX, float baseSpeedY, float overlaySpeedX, float overlaySpeedY) {
/* 21 */     this.waterMesh = waterMesh;
/* 22 */     this.baseSpeedX = baseSpeedX;
/* 23 */     this.baseSpeedY = baseSpeedY;
/* 24 */     this.overlaySpeedX = overlaySpeedX;
/* 25 */     this.overlaySpeedY = overlaySpeedY;
/* 26 */     this.wrap = waterMesh.getBaseTextureScale();
/* 27 */     this.basePosX = 0.0F;
/* 28 */     this.basePosY = 0.0F;
/* 29 */     this.overlayPosX = 0.0F;
/* 30 */     this.overlayPosY = 0.0F;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 34 */     this.basePosX += this.baseSpeedX * time;
/* 35 */     this.basePosY += this.baseSpeedY * time;
/* 36 */     this.overlayPosX += this.overlaySpeedX * time;
/* 37 */     this.overlayPosY += this.overlaySpeedY * time;
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
/* 48 */     this.waterMesh.setBaseTextureOffset(this.basePosX, this.basePosY);
/* 49 */     this.waterMesh.setOverlayTextureOffset(this.overlayPosX, this.overlayPosY);
/*    */   }
/*    */   
/*    */   public float getBaseSpeedX() {
/* 53 */     return this.baseSpeedX;
/*    */   }
/*    */   
/*    */   public void setBaseSpeedX(float speed) {
/* 57 */     this.baseSpeedX = speed;
/*    */   }
/*    */   
/*    */   public float getBaseSpeedY() {
/* 61 */     return this.baseSpeedY;
/*    */   }
/*    */   
/*    */   public void setBaseSpeedY(float speed) {
/* 65 */     this.baseSpeedY = speed;
/*    */   }
/*    */   
/*    */   public float getOverlaySpeedX() {
/* 69 */     return this.overlaySpeedX;
/*    */   }
/*    */   
/*    */   public void setOverlaySpeedX(float speed) {
/* 73 */     this.overlaySpeedX = speed;
/*    */   }
/*    */   
/*    */   public float getOverlaySpeedY() {
/* 77 */     return this.overlaySpeedY;
/*    */   }
/*    */   
/*    */   public void setOverlaySpeedY(float speed) {
/* 81 */     this.overlaySpeedY = speed;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\water\WaterOffsetController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */