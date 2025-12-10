/*    */ package com.funcom.gameengine.jme;
/*    */ 
/*    */ import com.funcom.gameengine.view.AdditiveBlendingAlphaState;
/*    */ import com.funcom.gameengine.view.TransparentAlphaState;
/*    */ import com.jme.scene.state.RenderState;
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum BlendMode
/*    */ {
/* 11 */   NORMAL {
/*    */     public RenderState getRenderState() {
/* 13 */       return (RenderState)TransparentAlphaState.get();
/*    */     } },
/* 15 */   ADDITIVE {
/*    */     public RenderState getRenderState() {
/* 17 */       return (RenderState)AdditiveBlendingAlphaState.get();
/*    */     }
/*    */   };
/*    */   
/*    */   public abstract RenderState getRenderState();
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\jme\BlendMode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */