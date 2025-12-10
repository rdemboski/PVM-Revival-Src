/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.math.Quaternion;
/*    */ import com.jme.math.TransformMatrix;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FollowJoint
/*    */ {
/*    */   private TransformMatrix transform;
/*    */   private Quaternion parentRotation;
/*    */   
/*    */   public FollowJoint(TransformMatrix transform, Quaternion parentRotation) {
/* 14 */     this.transform = transform;
/* 15 */     this.parentRotation = parentRotation;
/*    */   }
/*    */   
/*    */   public TransformMatrix getTransform() {
/* 19 */     return this.transform;
/*    */   }
/*    */   
/*    */   public Quaternion getParentRotation() {
/* 23 */     return this.parentRotation;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\FollowJoint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */