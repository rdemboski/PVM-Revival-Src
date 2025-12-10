/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ import com.jmex.bui.util.Insets;
/*    */ 
/*    */ 
/*    */ public class BPartialProgressBackground
/*    */   extends BProgressBackground
/*    */ {
/* 10 */   private float secondProgress = 0.0F;
/*    */   
/*    */   public BPartialProgressBackground(ImageBackgroundMode mode, BImage image, BComponent component, BProgressBar.Direction direction) {
/* 13 */     super(mode, image, component, direction);
/*    */   }
/*    */   
/*    */   public BPartialProgressBackground(ImageBackgroundMode mode, BImage image, BComponent component, BProgressBar.Direction direction, Insets frame) {
/* 17 */     super(mode, image, component, direction, frame);
/*    */   }
/*    */   
/*    */   public float getSecondProgress() {
/* 21 */     return this.secondProgress;
/*    */   }
/*    */   
/*    */   public void setSecondProgress(float secondProgress) {
/* 25 */     this.secondProgress = secondProgress;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setProgress(float progress) {
/* 30 */     super.setProgress(progress);
/* 31 */     setSecondProgress(Math.max(0.0F, Math.min(1.0F, progress - 0.1F))); } protected void updateProgressClipping() { int compHeight; int progHeight; int height;
/*    */     int compWidth;
/*    */     int progWidth;
/*    */     int width;
/* 35 */     switch (getDirection()) {
/*    */       case PROGRESSDIR_NORTH:
/* 37 */         setDrawFrame(0, (int)(getComponent().getHeight() * getSecondProgress()), getComponent().getWidth(), (int)(getComponent().getHeight() * (getProgress() - getSecondProgress())));
/*    */         break;
/*    */       case PROGRESSDIR_SOUTH:
/* 40 */         compHeight = getComponent().getHeight();
/* 41 */         progHeight = compHeight - (int)(compHeight * getProgress());
/* 42 */         height = (int)(compHeight * (getProgress() - getSecondProgress()));
/* 43 */         setDrawFrame(0, progHeight, getComponent().getWidth(), height);
/*    */         break;
/*    */       case WEST:
/* 46 */         compWidth = getComponent().getWidth();
/* 47 */         progWidth = compWidth - (int)(compWidth * getProgress());
/* 48 */         width = (int)(compWidth * (getProgress() - getSecondProgress()));
/* 49 */         setDrawFrame(progWidth, 0, width, getComponent().getHeight());
/*    */         break;
/*    */       case PROGRESSDIR_EAST:
/* 52 */         setDrawFrame((int)(getComponent().getWidth() * getSecondProgress()), 0, (int)(getComponent().getWidth() * (getProgress() - getSecondProgress())), getComponent().getHeight());
/*    */         break;
/*    */     }  }
/*    */ 
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BPartialProgressBackground.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */