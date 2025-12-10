/*    */ package com.funcom.tcg.client;
/*    */ 
/*    */ import com.funcom.gameengine.view.CameraConfig;
/*    */ 
/*    */ public class PvMCameraConfig
/*    */   extends CameraConfig {
/*    */   public static void createInstance() {
/*  8 */     INSTANCE = new PvMCameraConfig();
/*    */   }
/*    */   
/*    */   protected void updateCameraRotation() {
/* 12 */     this.cameraRotation.fromAngles(-0.017453292F * this.cameraAngle, 0.017453292F * this.offsetAngle, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\PvMCameraConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */