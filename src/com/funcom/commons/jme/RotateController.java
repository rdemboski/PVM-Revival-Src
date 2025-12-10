/*    */ package com.funcom.commons.jme;
/*    */ 
/*    */ import com.jme.math.Quaternion;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ public class RotateController
/*    */   extends Controller
/*    */ {
/*    */   private Spatial spatial;
/*    */   private Vector3f axis;
/*    */   private Quaternion quat;
/*    */   private float angle;
/*    */   
/*    */   public RotateController(Spatial spatial, Vector3f axis) {
/* 17 */     this.spatial = spatial;
/* 18 */     this.axis = axis;
/*    */     
/* 20 */     this.quat = new Quaternion();
/* 21 */     this.angle = 0.0F;
/*    */   }
/*    */   
/*    */   public RotateController(Spatial spatial, Vector3f axis, float speed) {
/* 25 */     setSpeed(speed);
/* 26 */     this.spatial = spatial;
/* 27 */     this.axis = axis;
/*    */     
/* 29 */     this.quat = new Quaternion();
/* 30 */     this.angle = 0.0F;
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 34 */     this.angle += time * getSpeed();
/* 35 */     if (this.angle > 6.2831855F) {
/* 36 */       this.angle -= 6.2831855F;
/*    */     }
/*    */     
/* 39 */     this.quat.fromAngleNormalAxis(this.angle, this.axis);
/* 40 */     this.spatial.setLocalRotation(this.quat);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\RotateController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */