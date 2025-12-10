/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.scene.state.BlendState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AdditiveBlendingAlphaState
/*    */ {
/*    */   private static BlendState blendState;
/*    */   
/*    */   public static BlendState get() {
/* 13 */     if (blendState == null) {
/* 14 */       blendState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/* 15 */       blendState.setBlendEnabled(true);
/* 16 */       blendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
/* 17 */       blendState.setDestinationFunction(BlendState.DestinationFunction.One);
/* 18 */       blendState.setTestEnabled(true);
/* 19 */       blendState.setTestFunction(BlendState.TestFunction.GreaterThan);
/*    */     } 
/* 21 */     return blendState;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\AdditiveBlendingAlphaState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */