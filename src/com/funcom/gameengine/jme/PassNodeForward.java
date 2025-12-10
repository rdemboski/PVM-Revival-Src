/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.Node;
/*    */ 
/*    */ public class PassNodeForward
/*    */   extends Node
/*    */   implements PassDrawable
/*    */ {
/*    */   public PassNodeForward(String name) {
/* 11 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(Renderer r) {
/* 16 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDrawByState(Renderer r, DrawPassState drawPassState) {
/* 21 */     drawPassState.onDrawCurrentState(r, this.children);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\PassNodeForward.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */