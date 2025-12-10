/*    */ package com.funcom.commons.jme;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.renderer.pass.RenderPass;
/*    */ import com.jme.scene.Spatial;
/*    */ import com.jme.system.DisplaySystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LayeredRenderPass
/*    */   extends RenderPass
/*    */ {
/*    */   public LayeredRenderPass() {}
/*    */   
/*    */   public LayeredRenderPass(Spatial spatial) {
/* 18 */     add(spatial);
/*    */   }
/*    */   
/*    */   public void doRender(Renderer renderer) {
/* 22 */     DisplaySystem.getDisplaySystem().getRenderer().clearZBuffer();
/* 23 */     super.doRender(renderer);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\LayeredRenderPass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */