/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ import com.jmex.bui.util.Insets;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BProgressBackground
/*    */   extends BPartialImageBackground
/*    */   implements ProgressComponent
/*    */ {
/*    */   private BProgressBar.Direction direction;
/*    */   private float progress;
/*    */   
/*    */   public BProgressBackground(ImageBackgroundMode mode, BImage image, BComponent component, BProgressBar.Direction direction) {
/* 26 */     this(mode, image, component, direction, (Insets)null);
/*    */   }
/*    */   
/*    */   public BProgressBackground(ImageBackgroundMode mode, BImage image, BComponent component, BProgressBar.Direction direction, Insets frame) {
/* 30 */     super(mode, image, component, frame);
/* 31 */     setDirection(direction);
/*    */   }
/*    */   
/*    */   public void setDirection(BProgressBar.Direction direction) {
/* 35 */     this.direction = direction;
/*    */   }
/*    */   
/*    */   public BProgressBar.Direction getDirection() {
/* 39 */     return this.direction;
/*    */   }
/*    */   
/*    */   public void setProgress(float progress) {
/* 43 */     if (progress < 0.0F || progress > 1.0F) {
/* 44 */       throw new IllegalArgumentException("Progress parameter needs to be 0 <= x <= 1. progress = " + progress);
/*    */     }
/*    */     
/* 47 */     this.progress = progress;
/* 48 */     updateProgressClipping();
/*    */   }
/*    */   
/*    */   public float getProgress() {
/* 52 */     return this.progress; } protected void updateProgressClipping() { int compHeight;
/*    */     int progHeight;
/*    */     int compWidth;
/*    */     int progWidth;
/* 56 */     switch (this.direction) {
/*    */       case PROGRESSDIR_NORTH:
/* 58 */         setDrawFrame(0, 0, getComponent().getWidth(), (int)(getComponent().getHeight() * this.progress));
/*    */         break;
/*    */       case PROGRESSDIR_SOUTH:
/* 61 */         compHeight = getComponent().getHeight();
/* 62 */         progHeight = (int)(compHeight * this.progress);
/* 63 */         setDrawFrame(0, compHeight - progHeight, getComponent().getWidth(), progHeight);
/*    */         break;
/*    */       case WEST:
/* 66 */         compWidth = getComponent().getWidth();
/* 67 */         progWidth = (int)(compWidth * this.progress);
/* 68 */         setDrawFrame(compWidth - progWidth, 0, progWidth, getComponent().getHeight());
/*    */         break;
/*    */       case PROGRESSDIR_EAST:
/* 71 */         setDrawFrame(0, 0, (int)(getComponent().getWidth() * this.progress), getComponent().getHeight());
/*    */         break;
/*    */     }  }
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer, int x, int y, int width, int height, float alpha) {
/* 78 */     updateProgressClipping();
/* 79 */     super.render(renderer, x, y, width, height, alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BProgressBackground.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */