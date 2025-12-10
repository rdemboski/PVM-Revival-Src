/*    */ package com.funcom.tcg.client.controllers;
/*    */ 
/*    */ import com.funcom.gameengine.view.CameraConfig;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ScreenCenterController
/*    */   extends Controller
/*    */ {
/*    */   private PropNode propNode;
/*    */   
/*    */   public void setPropNode(PropNode propNode) {
/* 15 */     this.propNode = propNode;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 19 */     CameraConfig.instance().moveCameraTo(this.propNode.getPosition());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\controllers\ScreenCenterController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */