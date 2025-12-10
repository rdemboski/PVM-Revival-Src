/*    */ package com.funcom.gameengine.controller;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.bounding.BoundingBox;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ public class SquareShadowController extends ShadowController {
/*    */   public SquareShadowController(Spatial shadow, PropNode propNode, float scale) {
/* 10 */     super(shadow, propNode, scale);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void setShadowScale(BoundingBox worldBound, Vector3f shadowScale, float scale) {
/* 15 */     float size = worldBound.xExtent;
/* 16 */     if (worldBound.zExtent > size) {
/* 17 */       size = worldBound.zExtent;
/*    */     }
/*    */     
/* 20 */     size *= 2.0F * scale;
/*    */     
/* 22 */     shadowScale.set(size, 1.0F, size);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\controller\SquareShadowController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */