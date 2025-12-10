/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ 
/*    */ 
/*    */ public class ObjectiveArrowDecal
/*    */   extends DecalQuad
/*    */ {
/*    */   public ObjectiveArrowDecal(String name) {
/* 10 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(Renderer renderer) {
/* 15 */     renderer.setPolygonOffset(-5.0F, -5.0F);
/* 16 */     super.draw(renderer);
/* 17 */     renderer.clearPolygonOffset();
/*    */   }
/*    */   
/*    */   protected void updateWorldRotation() {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\ObjectiveArrowDecal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */