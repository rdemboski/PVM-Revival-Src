/*    */ package com.funcom.gameengine.controller;
/*    */ 
/*    */ import com.funcom.gameengine.view.ModularNode;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.bounding.BoundingBox;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ public class ShadowController extends Controller {
/*    */   protected Spatial shadow;
/*    */   protected PropNode propNode;
/*    */   private float scale;
/*    */   
/*    */   public ShadowController(Spatial shadow, PropNode propNode) {
/* 16 */     this(shadow, propNode, 1.25F);
/*    */   }
/*    */   
/*    */   public ShadowController(Spatial shadow, PropNode propNode, float scale) {
/* 20 */     this.shadow = shadow;
/* 21 */     this.propNode = propNode;
/* 22 */     this.scale = scale;
/*    */     
/* 24 */     if (!(propNode.getRepresentation() instanceof ModularNode)) {
/* 25 */       throw new IllegalArgumentException("Can only use with modular node representations");
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void update(float v) {
/* 31 */     ModularNode modularNode = (ModularNode)this.propNode.getRepresentation();
/*    */     
/* 33 */     Vector3f shadowScale = this.shadow.getLocalScale();
/*    */     
/* 35 */     if (modularNode.getParent() != null) {
/* 36 */       BoundingBox worldBound = (BoundingBox)modularNode.getModelBounds();
/*    */       
/* 38 */       setShadowScale(worldBound, shadowScale, this.scale);
/*    */       
/* 40 */       Vector3f boundCenter = worldBound.getCenter();
/*    */       
/* 42 */       this.shadow.setLocalTranslation(boundCenter.x, 0.0F, boundCenter.z);
/*    */     } else {
/* 44 */       shadowScale.set(0.0F, 0.0F, 0.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void setShadowScale(BoundingBox worldBound, Vector3f shadowScale, float scale) {
/* 49 */     shadowScale.set(worldBound.xExtent * 2.0F * scale, 1.0F, worldBound.zExtent * 2.0F * scale);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\controller\ShadowController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */