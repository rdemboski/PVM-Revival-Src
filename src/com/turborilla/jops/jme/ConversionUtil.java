/*    */ package com.turborilla.jops.jme;
/*    */ 
/*    */ import com.jme.scene.state.BlendState;
/*    */ 
/*    */ public final class ConversionUtil
/*    */ {
/*    */   public static BlendState.SourceFunction toMonkeyBlendFactorSourceFunction(int glBlendFactorInteger) {
/*  8 */     switch (glBlendFactorInteger) {
/*    */       case 1:
/* 10 */         return BlendState.SourceFunction.One;
/*    */       case 0:
/* 12 */         return BlendState.SourceFunction.Zero;
/*    */       case 772:
/* 14 */         return BlendState.SourceFunction.DestinationAlpha;
/*    */       case 774:
/* 16 */         return BlendState.SourceFunction.DestinationColor;
/*    */       case 770:
/* 18 */         return BlendState.SourceFunction.SourceAlpha;
/*    */       case 768:
/* 20 */         return BlendState.SourceFunction.DestinationColor;
/*    */       case 773:
/* 22 */         return BlendState.SourceFunction.OneMinusDestinationAlpha;
/*    */       case 775:
/* 24 */         return BlendState.SourceFunction.OneMinusDestinationColor;
/*    */       case 771:
/* 26 */         return BlendState.SourceFunction.OneMinusSourceAlpha;
/*    */       case 776:
/* 28 */         return BlendState.SourceFunction.SourceAlphaSaturate;
/*    */       case 769:
/* 30 */         return BlendState.SourceFunction.OneMinusDestinationColor;
/*    */     } 
/* 32 */     throw new RuntimeException("GL source blend mode not recognized : " + glBlendFactorInteger);
/*    */   }
/*    */ 
/*    */   
/*    */   public static BlendState.DestinationFunction toMonkeyBlendFactorDestinationFunction(int glBlendFactorInteger) {
/* 37 */     switch (glBlendFactorInteger) {
/*    */       case 1:
/* 39 */         return BlendState.DestinationFunction.One;
/*    */       case 0:
/* 41 */         return BlendState.DestinationFunction.Zero;
/*    */       case 772:
/* 43 */         return BlendState.DestinationFunction.DestinationAlpha;
/*    */       case 774:
/* 45 */         return BlendState.DestinationFunction.SourceColor;
/*    */       case 770:
/* 47 */         return BlendState.DestinationFunction.SourceAlpha;
/*    */       case 768:
/* 49 */         return BlendState.DestinationFunction.SourceColor;
/*    */       case 773:
/* 51 */         return BlendState.DestinationFunction.OneMinusDestinationAlpha;
/*    */       case 775:
/* 53 */         return BlendState.DestinationFunction.OneMinusSourceColor;
/*    */       case 771:
/* 55 */         return BlendState.DestinationFunction.OneMinusSourceAlpha;
/*    */       case 776:
/* 57 */         return BlendState.DestinationFunction.SourceAlpha;
/*    */       case 769:
/* 59 */         return BlendState.DestinationFunction.OneMinusDestinationAlpha;
/*    */     } 
/* 61 */     throw new RuntimeException("GL source blend mode not recognized : " + glBlendFactorInteger);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\turborilla\jops\jme\ConversionUtil.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */