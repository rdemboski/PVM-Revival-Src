/*    */ package com.funcom.gameengine.resourcemanager.loaders.texture;
/*    */ 
/*    */ import com.jme.image.Texture;
/*    */ import com.jme.image.Texture2D;
/*    */ 
/*    */ 
/*    */ public class AsyncTexture2D
/*    */   extends Texture2D
/*    */ {
/*    */   private int originalWidth;
/*    */   private int originalHeight;
/*    */   
/*    */   public AsyncTexture2D(int originalWidth, int originalHeight) {
/* 14 */     this.originalWidth = originalWidth;
/* 15 */     this.originalHeight = originalHeight;
/*    */   }
/*    */   
/*    */   public int getOriginalWidth() {
/* 19 */     return this.originalWidth;
/*    */   }
/*    */   
/*    */   public int getOriginalHeight() {
/* 23 */     return this.originalHeight;
/*    */   }
/*    */   
/*    */   public void copyRealProperties(Texture realTexture) {
/* 27 */     setEnvironmentalMapMode(realTexture.getEnvironmentalMapMode());
/* 28 */     setMinificationFilter(realTexture.getMinificationFilter());
/* 29 */     setMagnificationFilter(realTexture.getMagnificationFilter());
/* 30 */     setHasBorder(realTexture.hasBorder());
/* 31 */     setAnisotropicFilterPercent(realTexture.getAnisotropicFilterPercent());
/* 32 */     setImageLocation(realTexture.getImageLocation());
/* 33 */     setBlendColor(realTexture.getBlendColor());
/* 34 */     setScale(realTexture.getScale());
/* 35 */     setTranslation(realTexture.getTranslation());
/* 36 */     setRotation(realTexture.getRotation());
/* 37 */     setMatrix(realTexture.getMatrix());
/* 38 */     setTextureKey(realTexture.getTextureKey());
/* 39 */     setImage(realTexture.getImage());
/* 40 */     setWrap(Texture.WrapAxis.S, realTexture.getWrap(Texture.WrapAxis.S));
/* 41 */     setWrap(Texture.WrapAxis.T, realTexture.getWrap(Texture.WrapAxis.T));
/* 42 */     updateMemoryReq();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loaders\texture\AsyncTexture2D.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */