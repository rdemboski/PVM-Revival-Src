/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.jme.image.Image;
/*    */ import com.jme.image.Texture;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class AtlasTexture
/*    */   extends Texture
/*    */ {
/* 17 */   private Texture atlasTexture = null;
/*    */ 
/*    */   
/*    */   public void configureBackgroundTileLayer() {
/* 21 */     setApply(Texture.ApplyMode.Replace);
/*    */   }
/*    */   
/*    */   public void configureOtherTileLayer() {
/* 25 */     setApply(Texture.ApplyMode.Combine);
/* 26 */     setCombineFuncRGB(Texture.CombinerFunctionRGB.Interpolate);
/* 27 */     setCombineFuncAlpha(Texture.CombinerFunctionAlpha.Add);
/*    */     
/* 29 */     setCombineSrc0RGB(Texture.CombinerSource.CurrentTexture);
/* 30 */     setCombineOp0RGB(Texture.CombinerOperandRGB.SourceColor);
/*    */     
/* 32 */     setCombineSrc1RGB(Texture.CombinerSource.Previous);
/* 33 */     setCombineOp1RGB(Texture.CombinerOperandRGB.SourceColor);
/*    */     
/* 35 */     setCombineSrc2RGB(Texture.CombinerSource.CurrentTexture);
/* 36 */     setCombineOp2RGB(Texture.CombinerOperandRGB.SourceAlpha);
/*    */   }
/*    */   
/*    */   public AtlasTexture(Texture tex) {
/* 40 */     this.atlasTexture = tex;
/*    */   }
/*    */   
/*    */   public int getTextureId() {
/* 44 */     if (this.atlasTexture != null) {
/* 45 */       return this.atlasTexture.getTextureId();
/*    */     }
/* 47 */     return -1;
/*    */   }
/*    */   
/*    */   public void setTextureId(int textureId) {
/* 51 */     if (this.atlasTexture != null) {
/* 52 */       this.atlasTexture.setTextureId(textureId);
/*    */     } else {
/* 54 */       super.setTextureId(textureId);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void setWrap(Texture.WrapAxis axis, Texture.WrapMode mode) {
/* 59 */     if (this.atlasTexture != null)
/* 60 */       this.atlasTexture.setWrap(axis, mode); 
/*    */   }
/*    */   
/*    */   public void setWrap(Texture.WrapMode mode) {
/* 64 */     if (this.atlasTexture != null)
/* 65 */       this.atlasTexture.setWrap(mode); 
/*    */   }
/*    */   
/*    */   public Texture.Type getType() {
/* 69 */     if (this.atlasTexture != null) {
/* 70 */       return this.atlasTexture.getType();
/*    */     }
/* 72 */     return Texture.Type.TwoDimensional;
/*    */   }
/*    */   
/*    */   public Texture createSimpleClone() {
/* 76 */     if (this.atlasTexture != null) {
/* 77 */       return this.atlasTexture.createSimpleClone();
/*    */     }
/* 79 */     return null;
/*    */   }
/*    */   
/*    */   public Texture.WrapMode getWrap(Texture.WrapAxis axis) {
/* 83 */     if (this.atlasTexture != null) {
/* 84 */       return this.atlasTexture.getWrap(axis);
/*    */     }
/* 86 */     return null;
/*    */   }
/*    */   
/*    */   public Image getImage() {
/* 90 */     if (this.atlasTexture != null) {
/* 91 */       return this.atlasTexture.getImage();
/*    */     }
/* 93 */     return super.getImage();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\AtlasTexture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */