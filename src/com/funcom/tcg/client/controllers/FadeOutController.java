/*    */ package com.funcom.tcg.client.controllers;
/*    */ 
/*    */ import com.funcom.gameengine.view.TransparentMaterialState;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.scene.state.RenderState;
/*    */ 
/*    */ public class FadeOutController extends Controller {
/*    */   private Spatial spatial;
/*    */   private float totalTime;
/*    */   private float currentTime;
/*    */   
/*    */   public FadeOutController(Spatial spatial, float totalTime) {
/* 14 */     this.spatial = spatial;
/* 15 */     this.totalTime = totalTime;
/* 16 */     this.currentTime = 0.0F;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 20 */     this.currentTime += time;
/* 21 */     if (this.currentTime >= this.totalTime) {
/* 22 */       this.spatial.getParent().detachChild(this.spatial);
/*    */     } else {
/* 24 */       float alpha = 1.0F - this.currentTime / this.totalTime;
/* 25 */       this.spatial.setRenderState((RenderState)TransparentMaterialState.get(alpha));
/* 26 */       this.spatial.updateRenderState();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\controllers\FadeOutController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */