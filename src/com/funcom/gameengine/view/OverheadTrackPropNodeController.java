/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.bounding.BoundingBox;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Spatial;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OverheadTrackPropNodeController
/*    */   extends TrackPropNodeController
/*    */ {
/*    */   public OverheadTrackPropNodeController(PropNode nodeToTrack, Spatial objectThatFollows) {
/* 13 */     super(nodeToTrack, objectThatFollows);
/*    */   }
/*    */ 
/*    */   
/*    */   protected Vector3f calculateTranslation(Vector3f additionalOffset) {
/* 18 */     if (this.nodeToTrack.getRepresentation() == null)
/* 19 */       return additionalOffset.clone(); 
/* 20 */     BoundingBox bounds = (BoundingBox)this.nodeToTrack.getRepresentation().getWorldBound();
/* 21 */     float y = (bounds == null) ? 0.0F : bounds.getExtent(new Vector3f()).getY();
/* 22 */     Vector3f offset = additionalOffset.clone().addLocal(new Vector3f(0.0F, y * 2.0F, 0.0F));
/* 23 */     return super.calculateTranslation(offset);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\OverheadTrackPropNodeController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */