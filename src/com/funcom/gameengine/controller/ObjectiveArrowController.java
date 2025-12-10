/*    */ package com.funcom.gameengine.controller;
/*    */ 
/*    */ import com.funcom.gameengine.jme.DecalQuad;
/*    */ import com.funcom.gameengine.model.props.MultipleTargetsModel;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.jme.math.Quaternion;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class ObjectiveArrowController
/*    */   extends Controller
/*    */ {
/*    */   private boolean isDetached = false;
/*    */   private DecalQuad objectiveArrowQuad;
/*    */   private PropNode propNode;
/*    */   private MultipleTargetsModel multipleTargetsModel;
/*    */   
/*    */   public ObjectiveArrowController(DecalQuad objectiveArrowQuad, PropNode propNode, MultipleTargetsModel multipleTargetsModel) {
/* 24 */     this.objectiveArrowQuad = objectiveArrowQuad;
/* 25 */     this.propNode = propNode;
/* 26 */     this.multipleTargetsModel = multipleTargetsModel;
/*    */ 
/*    */     
/* 29 */     this.objectiveArrowQuad.setLocalTranslation(0.0F, 0.01F, 0.0F);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void update(float v) {
/* 35 */     MultipleTargetsModel.TargetData targetData = this.multipleTargetsModel.getClosestToPlayer();
/*    */ 
/*    */     
/* 38 */     if (targetData != null && !targetData.isWithinMinimumRange(this.propNode.getPosition())) {
/* 39 */       if (this.isDetached) addQuad(); 
/* 40 */       float angle = (float)-targetData.getPosition().angleTo(this.propNode.getPosition());
/* 41 */       Quaternion quaternion = this.objectiveArrowQuad.getLocalRotation().fromAngleAxis(angle, Vector3f.UNIT_Y);
/* 42 */       this.objectiveArrowQuad.setLocalRotation(quaternion);
/* 43 */     } else if (!this.isDetached) {
/*    */       
/* 45 */       removeQuad();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void addQuad() {
/* 52 */     float angleX = -3.1415927F;
/* 53 */     Quaternion quaternion = this.objectiveArrowQuad.getLocalRotation().fromAngleAxis(angleX, Vector3f.UNIT_X);
/* 54 */     this.objectiveArrowQuad.setLocalRotation(quaternion);
/* 55 */     this.isDetached = false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void removeQuad() {
/* 61 */     float angleY = 0.0F;
/* 62 */     Quaternion quaternionY = this.objectiveArrowQuad.getLocalRotation().fromAngleAxis(angleY, Vector3f.UNIT_Y);
/* 63 */     this.objectiveArrowQuad.setLocalRotation(quaternionY);
/*    */     
/* 65 */     float angleX = 3.1415927F;
/* 66 */     Quaternion quaternion = this.objectiveArrowQuad.getLocalRotation().fromAngleAxis(angleX, Vector3f.UNIT_X);
/* 67 */     this.objectiveArrowQuad.setLocalRotation(quaternion);
/*    */     
/* 69 */     this.isDetached = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\controller\ObjectiveArrowController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */