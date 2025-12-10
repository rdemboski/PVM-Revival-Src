/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.scene.Node;
/*    */ 
/*    */ 
/*    */ public class PassNodeSpecificPass
/*    */   extends Node
/*    */   implements PassDrawable
/*    */ {
/*    */   private DrawPassState triggerState;
/*    */   
/*    */   public PassNodeSpecificPass(String name, DrawPassState triggerState) {
/* 14 */     super(name);
/*    */     
/* 16 */     if (triggerState == null) {
/* 17 */       throw new NullPointerException("trigger pass state cannot be null");
/*    */     }
/* 19 */     this.triggerState = triggerState;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDrawByState(Renderer r, DrawPassState drawPassState) {
/* 24 */     if (this.triggerState.equals(drawPassState))
/* 25 */       onDraw(r); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\PassNodeSpecificPass.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */