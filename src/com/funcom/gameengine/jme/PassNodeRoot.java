/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.Node;
/*    */ 
/*    */ 
/*    */ public class PassNodeRoot
/*    */   extends Node
/*    */ {
/*    */   public PassNodeRoot(String name) {
/* 11 */     super(name);
/*    */   }
/*    */ 
/*    */   
/*    */   public void draw(Renderer r) {
/* 16 */     DrawPassState passState = new DrawPassState();
/*    */     
/* 18 */     passState.onDrawComplete(r, this.children);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\PassNodeRoot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */