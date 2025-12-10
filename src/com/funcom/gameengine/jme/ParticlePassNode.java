/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.WorldOrigin;
/*    */ import com.funcom.gameengine.WorldUtils;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ public class ParticlePassNode
/*    */   extends PassNodeSpecificPass
/*    */ {
/* 12 */   private static final WorldCoordinate coord = new WorldCoordinate();
/*    */   public ParticlePassNode(String name, DrawPassState triggerState) {
/* 14 */     super(name, triggerState);
/* 15 */     addController(new OriginUpdateController(this));
/*    */   }
/*    */   
/*    */   protected void updatePropVectors(int offsetx, int offsety) {
/* 19 */     float x = WorldUtils.getScreenX(coord, offsetx);
/* 20 */     float y = WorldUtils.getScreenY(coord, offsety);
/*    */     
/* 22 */     this.localTranslation.set(x, this.localTranslation.getY(), y);
/*    */     
/* 24 */     if (getParent() != null) {
/* 25 */       this.localTranslation.subtractLocal(getParent().getWorldTranslation());
/*    */     }
/*    */     
/* 28 */     updateWorldTranslation();
/*    */   }
/*    */   
/*    */   private static class OriginUpdateController extends Controller {
/*    */     private ParticlePassNode node;
/*    */     
/*    */     private OriginUpdateController(ParticlePassNode node) {
/* 35 */       this.node = node;
/*    */     }
/*    */     
/*    */     public void update(float time) {
/* 39 */       this.node.updatePropVectors(WorldOrigin.instance().getX(), WorldOrigin.instance().getY());
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\ParticlePassNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */