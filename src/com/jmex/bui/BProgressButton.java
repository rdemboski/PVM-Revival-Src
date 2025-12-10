/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.StackedLayout;
/*     */ import com.jmex.bui.util.Insets;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BProgressButton
/*     */   extends BCustomButton
/*     */   implements ProgressComponent
/*     */ {
/*     */   private BActiveProgressBar progressBar;
/*     */   
/*     */   public BProgressButton() {
/*  19 */     this(BProgressBar.Direction.WEST, 0L, 0.0F);
/*     */   }
/*     */   
/*     */   public BProgressButton(BProgressBar.Direction direction, long initialDelay, float catchupSpeed) {
/*  23 */     setLayoutManager((BLayoutManager)new StackedLayout());
/*  24 */     this.progressBar = new BActiveProgressBar(direction, initialDelay, catchupSpeed);
/*  25 */     add((BComponent)this.progressBar);
/*  26 */     setDirection(direction);
/*     */   }
/*     */   
/*     */   public void addBackgroundImage(BImage backImage) {
/*  30 */     this.progressBar.addBackgroundImage(backImage);
/*     */   }
/*     */   
/*     */   public void addBackgroundImage(BImage backImage, ImageBackgroundMode mode) {
/*  34 */     this.progressBar.addBackgroundImage(backImage, mode);
/*     */   }
/*     */   
/*     */   public void addBackgroundImage(BImage backImage, ImageBackgroundMode mode, Insets frame) {
/*  38 */     this.progressBar.addBackgroundImage(backImage, mode, frame);
/*     */   }
/*     */   
/*     */   public void addProgressImage(BImage progressImage) {
/*  42 */     this.progressBar.addProgressImage(progressImage);
/*     */   }
/*     */   
/*     */   public void addProgressImage(BImage progressImage, ImageBackgroundMode mode) {
/*  46 */     this.progressBar.addProgressImage(progressImage, mode);
/*     */   }
/*     */   
/*     */   public void addProgressImage(BImage progressImage, ImageBackgroundMode mode, Insets frame) {
/*  50 */     this.progressBar.addProgressImage(progressImage, mode, frame);
/*     */   }
/*     */   
/*     */   public void addOverlayImage(BImage overlayImage) {
/*  54 */     this.progressBar.addOverlayImage(overlayImage);
/*     */   }
/*     */   
/*     */   public void setDirection(BProgressBar.Direction direction) {
/*  58 */     this.progressBar.setDirection(direction);
/*     */   }
/*     */   
/*     */   public BProgressBar.Direction getDirection() {
/*  62 */     return this.progressBar.getDirection();
/*     */   }
/*     */   
/*     */   public void setProgress(float progress) {
/*  66 */     this.progressBar.setProgress(progress);
/*     */   }
/*     */   
/*     */   public void setProgressInstant(float progress) {
/*  70 */     this.progressBar.setProgressInstant(progress);
/*     */   }
/*     */   
/*     */   public float getProgress() {
/*  74 */     return this.progressBar.getProgress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  79 */     return "progressbutton";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/*  84 */     boolean processed = super.dispatchEvent(event);
/*     */     
/*  86 */     if (event instanceof MouseEvent) {
/*  87 */       MouseEvent mev = (MouseEvent)event;
/*  88 */       if (mev.getType() == 4 || mev.getType() == 6)
/*     */       {
/*  90 */         return true;
/*     */       }
/*     */     } 
/*     */     
/*  94 */     return processed;
/*     */   }
/*     */   
/*     */   public void removeAllBackgroundImages() {
/*  98 */     this.progressBar.removeAllBackgroundImages();
/*     */   }
/*     */   
/*     */   public void removeAllProgressImages() {
/* 102 */     this.progressBar.removeAllProgressImages();
/*     */   }
/*     */   
/*     */   public void addImmediateProgressImage(BImage resource) {
/* 106 */     this.progressBar.addInstantProgressImage(resource, ImageBackgroundMode.SCALE_XY);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BProgressButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */