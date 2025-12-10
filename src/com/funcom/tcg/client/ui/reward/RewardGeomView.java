/*    */ package com.funcom.tcg.client.ui.reward;
/*    */ 
/*    */ import com.funcom.gameengine.view.Effects;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.tcg.client.ui.AbstactAngledGeomView;
/*    */ import com.jme.renderer.ColorRGBA;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RewardGeomView
/*    */   extends AbstactAngledGeomView
/*    */ {
/* 13 */   private float rotationSpeed = 0.0F;
/*    */   
/*    */   protected void updateCharacterAngle(float timePerFrame) {
/* 16 */     PropNode geometry = getGeometry();
/* 17 */     if (geometry == null) {
/*    */       return;
/*    */     }
/* 20 */     float angle = geometry.getAngle();
/*    */     
/* 22 */     angle += this.rotationSpeed * timePerFrame;
/*    */     
/* 24 */     angle %= 6.2831855F;
/* 25 */     geometry.setAngle(angle);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAlpha(float v) {
/* 30 */     super.setAlpha(v);
/* 31 */     PropNode node = getGeometry();
/* 32 */     if (node != null) {
/* 33 */       ColorRGBA color = node.getEffects().getTintRgba();
/* 34 */       node.getEffects().setTintRgba(color.r, color.g, color.b, v);
/* 35 */       node.getEffects().tint(Effects.TintMode.MODULATE);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setRotationSpeed(float rotationSpeed) {
/* 40 */     this.rotationSpeed = rotationSpeed;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\reward\RewardGeomView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */