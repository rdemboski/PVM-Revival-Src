/*    */ package com.funcom.gameengine.view.particles;
/*    */ 
/*    */ import com.funcom.gameengine.view.FollowJoint;
/*    */ import com.jme.math.Quaternion;
/*    */ import com.jme.math.TransformMatrix;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Md5JointFollowController
/*    */   extends Controller
/*    */ {
/*    */   private Spatial spatial;
/*    */   private Vector3f translationDisplacement;
/*    */   private Quaternion rotationDisplacement;
/* 19 */   private Vector3f translateScale = new Vector3f(1.0F, 1.0F, 1.0F);
/*    */   
/*    */   private Quaternion rotation;
/*    */   
/*    */   private Vector3f translation;
/*    */   private FollowJoint targetJoint;
/* 25 */   private static final Quaternion coordinateSystemCorrection = (new Quaternion()).fromAngleAxis(-1.5707964F, (new Vector3f(1.0F, 0.0F, 0.0F)).normalize());
/*    */   
/*    */   public void setTranslateScale(Vector3f translateScale) {
/* 28 */     this.translateScale = translateScale;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setSwitchCoordSystem(boolean switchCoordSystem) {
/* 37 */     this.switchCoordSystem = switchCoordSystem;
/*    */   }
/*    */   
/*    */   private boolean switchCoordSystem = false;
/*    */   
/*    */   public Md5JointFollowController(Spatial controlledSpatial, FollowJoint targetJoint) {
/* 43 */     this.targetJoint = targetJoint;
/*    */     
/* 45 */     if (targetJoint == null)
/* 46 */       throw new IllegalArgumentException("joint = null"); 
/* 47 */     if (controlledSpatial == null) {
/* 48 */       throw new IllegalArgumentException("spatial = null");
/*    */     }
/* 50 */     this.spatial = controlledSpatial;
/* 51 */     this.rotation = new Quaternion();
/* 52 */     this.translation = new Vector3f();
/* 53 */     this.translationDisplacement = new Vector3f();
/* 54 */     this.rotationDisplacement = new Quaternion();
/*    */   }
/*    */ 
/*    */   
/*    */   public Vector3f getTranslationDisplacement() {
/* 59 */     return this.translationDisplacement;
/*    */   }
/*    */   
/*    */   public void setTranslationDisplacement(Vector3f translationDisplacement) {
/* 63 */     if (translationDisplacement == null) {
/* 64 */       this.translationDisplacement = new Vector3f(0.0F, 0.0F, 0.0F);
/*    */     } else {
/* 66 */       this.translationDisplacement = translationDisplacement;
/*    */     } 
/*    */   }
/*    */   public Quaternion getRotationDisplacement() {
/* 70 */     return this.rotationDisplacement;
/*    */   }
/*    */   
/*    */   public void setRotationDisplacement(Quaternion rotationDisplacement) {
/* 74 */     if (rotationDisplacement == null) {
/* 75 */       this.rotationDisplacement = new Quaternion(0.0F, 0.0F, 0.0F, 0.0F);
/*    */     } else {
/* 77 */       this.rotationDisplacement = rotationDisplacement;
/*    */     } 
/*    */   }
/*    */   
/*    */   public void update(float v) {
/* 82 */     TransformMatrix tm = this.targetJoint.getTransform();
/* 83 */     tm.getTranslation(this.translation);
/* 84 */     tm.getRotation(this.rotation);
/* 85 */     Quaternion parentRot = this.targetJoint.getParentRotation();
/* 86 */     if (this.switchCoordSystem) {
/* 87 */       this.rotation = coordinateSystemCorrection.mult(this.rotation);
/* 88 */       this.translation = parentRot.multLocal(this.translation);
/*    */     } 
/*    */     
/* 91 */     Vector3f translationDisplacementtmp = this.rotation.mult(this.translationDisplacement);
/* 92 */     this.translation.addLocal(translationDisplacementtmp);
/* 93 */     this.rotation.multLocal(parentRot);
/* 94 */     this.rotation.multLocal(this.rotationDisplacement);
/* 95 */     this.translation.multLocal(this.translateScale);
/* 96 */     this.spatial.setLocalTranslation(this.translation);
/* 97 */     this.spatial.setLocalRotation(this.rotation);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\particles\Md5JointFollowController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */