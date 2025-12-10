/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.EffectHandler;
/*    */ import com.funcom.commons.dfx.PositionalEffectDescription;
/*    */ import com.jme.math.Quaternion;
/*    */ import com.jme.math.Vector3f;
/*    */ 
/*    */ public abstract class PositionalEffectHandler
/*    */   implements EffectHandler
/*    */ {
/*    */   private RepresentationalNode target;
/*    */   
/*    */   public PositionalEffectHandler(RepresentationalNode target) {
/* 14 */     this.target = target;
/*    */   }
/*    */   
/*    */   protected Quaternion getOffsetAngle(float[] offsetAngle) {
/* 18 */     Quaternion offsetAngleQuat = new Quaternion();
/* 19 */     if (offsetAngle != null) {
/* 20 */       Quaternion pitch = new Quaternion();
/* 21 */       Quaternion roll = new Quaternion();
/* 22 */       Quaternion yaw = new Quaternion();
/* 23 */       pitch.fromAngleAxis((float)Math.toRadians(offsetAngle[0]), new Vector3f(0.0F, 0.0F, 1.0F));
/* 24 */       roll.fromAngleAxis((float)Math.toRadians(offsetAngle[1]), new Vector3f(0.0F, 1.0F, 0.0F));
/* 25 */       yaw.fromAngleAxis((float)Math.toRadians(offsetAngle[2]), new Vector3f(1.0F, 0.0F, 0.0F));
/* 26 */       offsetAngleQuat = pitch.mult(roll).mult(yaw);
/*    */     } 
/* 28 */     return offsetAngleQuat;
/*    */   }
/*    */   
/*    */   protected Vector3f getOffsetPos(PositionalEffectDescription particleEffectDescription) {
/* 32 */     float[] offsetPos = particleEffectDescription.getOffsetPos();
/* 33 */     Vector3f offset = new Vector3f();
/* 34 */     if (offsetPos != null) {
/* 35 */       offset.set(offsetPos[0], offsetPos[1], offsetPos[2]);
/*    */     }
/* 37 */     return offset;
/*    */   }
/*    */   
/*    */   protected RepresentationalNode getTarget() {
/* 41 */     return this.target;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 46 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\PositionalEffectHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */