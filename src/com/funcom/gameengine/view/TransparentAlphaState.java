/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.jme.scene.state.BlendState;
/*    */ import com.jme.system.DisplaySystem;
/*    */ 
/*    */ public class TransparentAlphaState
/*    */ {
/*    */   private static BlendState blendState;
/*    */   
/*    */   public static BlendState get() {
/* 11 */     if (blendState == null) {
/* 12 */       blendState = DisplaySystem.getDisplaySystem().getRenderer().createBlendState();
/* 13 */       blendState.setBlendEnabled(true);
/* 14 */       blendState.setSourceFunction(BlendState.SourceFunction.SourceAlpha);
/* 15 */       blendState.setDestinationFunction(BlendState.DestinationFunction.OneMinusSourceAlpha);
/* 16 */       blendState.setTestEnabled(true);
/* 17 */       blendState.setTestFunction(BlendState.TestFunction.GreaterThan);
/*    */     } 
/* 19 */     return blendState;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\TransparentAlphaState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */