/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ 
/*    */ 
/*    */ public class ShadowDecal
/*    */   extends DecalQuad
/*    */ {
/*    */   public ShadowDecal(String name) {
/* 10 */     super(name);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void draw(Renderer renderer) {
/* 16 */     renderer.setPolygonOffset(-5.0F, -5.0F);
/* 17 */     super.draw(renderer);
/* 18 */     renderer.clearPolygonOffset();
/*    */   }
/*    */   
/*    */   public void blah() {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\ShadowDecal.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */