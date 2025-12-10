/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.background.ImageBackground;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ import com.jmex.bui.util.Insets;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ public class BPartialImageBackground
/*    */   extends ImageBackground
/*    */   implements ImageSetter
/*    */ {
/*    */   private Rectangle drawRect;
/*    */   private BComponent component;
/*    */   
/*    */   public BPartialImageBackground(ImageBackgroundMode mode, BImage image, BComponent component) {
/* 17 */     this(mode, image, component, null);
/*    */   }
/*    */   
/*    */   public BPartialImageBackground(ImageBackgroundMode mode, BImage image, BComponent component, Insets frame) {
/* 21 */     super(mode, image, frame);
/* 22 */     this.component = component;
/*    */     
/* 24 */     this.drawRect = new Rectangle(0, 0, (int)image.getWidth(), (int)image.getHeight());
/*    */   }
/*    */   
/*    */   public void setDrawFrame(int x, int y, int width, int height) {
/* 28 */     this.drawRect.set(x, y, width, height);
/*    */   }
/*    */   
/*    */   public void setDrawFrameLeft(int x) {
/* 32 */     this.drawRect.x = x;
/*    */   }
/*    */   
/*    */   public void setDrawFrameTop(int y) {
/* 36 */     this.drawRect.y = y;
/*    */   }
/*    */   
/*    */   public void setDrawFrameWidth(int width) {
/* 40 */     this.drawRect.width = width;
/*    */   }
/*    */   
/*    */   public void setDrawFrameHeight(int height) {
/* 44 */     this.drawRect.height = height;
/*    */   }
/*    */   
/*    */   public void setComponent(BComponent component) {
/* 48 */     this.component = component;
/*    */   }
/*    */   
/*    */   public BComponent getComponent() {
/* 52 */     return this.component;
/*    */   }
/*    */ 
/*    */   
/*    */   public void wasAdded() {
/* 57 */     super.wasAdded();
/* 58 */     setDrawFrame(0, 0, this.component.getWidth(), this.component.getHeight());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer, int x, int y, int width, int height, float alpha) {
/* 68 */     if (this.drawRect.width > this._image._width || this.drawRect.height > this._image._height) {
/* 69 */       super.render(renderer, x, y, this.drawRect.width, this.drawRect.height, alpha);
/*    */     } else {
/* 71 */       this._image.render(renderer, this.drawRect.x, this.drawRect.y, this.drawRect.width, this.drawRect.height, this.drawRect.x, this.drawRect.y, this.drawRect.width, this.drawRect.height, alpha);
/*    */     } 
/*    */   }
/*    */   public void setImage(BImage image) {
/* 75 */     if (image != this._image) {
/* 76 */       this._image.release();
/* 77 */       image.reference();
/*    */     } 
/* 79 */     this._image = image;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BPartialImageBackground.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */