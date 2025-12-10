/*    */ package com.funcom.gameengine.model;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.renderer.pass.Pass;
/*    */ import com.jme.scene.Node;
/*    */ 
/*    */ 
/*    */ public class DfxTextRenderPass
/*    */   extends Pass
/*    */ {
/*    */   private Node textNode;
/*    */   
/*    */   public DfxTextRenderPass(Node node) {
/* 14 */     if (node == null)
/* 15 */       throw new IllegalArgumentException("textNode = null"); 
/* 16 */     this.textNode = node;
/*    */   }
/*    */   
/*    */   protected void doUpdate(float v) {
/* 20 */     this.textNode.updateGeometricState(v, true);
/*    */   }
/*    */   
/*    */   public void doRender(Renderer renderer) {
/* 24 */     this.textNode.draw(renderer);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\DfxTextRenderPass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */