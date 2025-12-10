/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ public class TrackPropNodeController
/*    */   extends Controller
/*    */ {
/*    */   protected Vector3f displacer;
/*    */   protected PropNode nodeToTrack;
/*    */   protected Spatial objectThatFollows;
/*    */   
/*    */   public TrackPropNodeController(PropNode nodeToTrack, Spatial objectThatFollows) {
/* 15 */     this.nodeToTrack = nodeToTrack;
/* 16 */     this.objectThatFollows = objectThatFollows;
/* 17 */     this.displacer = new Vector3f(0.0F, 0.0F, 0.0F);
/*    */   }
/*    */   
/*    */   public void update(float v) {
/* 21 */     this.objectThatFollows.setLocalTranslation(calculateTranslation(this.displacer));
/*    */   }
/*    */   
/*    */   protected Vector3f calculateTranslation(Vector3f additionalOffset) {
/* 25 */     Vector3f lt = this.objectThatFollows.getLocalTranslation();
/* 26 */     lt.set(this.nodeToTrack.getLocalTranslation());
/* 27 */     lt.addLocal(additionalOffset);
/* 28 */     return lt;
/*    */   }
/*    */   
/*    */   public void setDisplacer(Vector3f displacer) {
/* 32 */     this.displacer.set(displacer);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TrackPropNodeController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */