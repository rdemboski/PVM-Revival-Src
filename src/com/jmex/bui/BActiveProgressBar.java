/*     */ package com.jmex.bui;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.parser.ProgressBackgroundProperty;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.ListIterator;
/*     */ import java.util.Set;
/*     */ import java.util.Vector;
/*     */ 
/*     */ public class BActiveProgressBar extends BProgressBar {
/*  14 */   private Set<ProgressComponent> instantProgressLayers = new HashSet<ProgressComponent>();
/*     */   private long time;
/*  16 */   private Vector<ProgressUpdate> progressUpdateList = new Vector<ProgressUpdate>(20);
/*     */   
/*  18 */   private long initialDelay = 0L;
/*  19 */   private float catchUpSpeed = 0.1F;
/*     */ 
/*     */ 
/*     */   
/*     */   private float intstantProgress;
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean clickthrough = false;
/*     */ 
/*     */ 
/*     */   
/*     */   public BActiveProgressBar(BProgressBar.Direction progressdir, long initialDelay, float catchUpSpeed) {
/*  32 */     super(progressdir);
/*  33 */     this.initialDelay = initialDelay;
/*  34 */     this.catchUpSpeed = catchUpSpeed;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/*  39 */     long newTime = System.currentTimeMillis();
/*  40 */     long dt = newTime - this.time;
/*  41 */     this.time = newTime;
/*  42 */     update(dt);
/*  43 */     super.renderComponent(renderer);
/*     */   }
/*     */   
/*     */   private void update(long dt) {
/*  47 */     ListIterator<ProgressUpdate> updateIterator = this.progressUpdateList.listIterator();
/*  48 */     while (updateIterator.hasNext()) {
/*  49 */       ProgressUpdate progressUpdate = updateIterator.next();
/*     */ 
/*     */ 
/*     */       
/*  53 */       if (progressUpdate.getStartTime() <= this.time) {
/*  54 */         float newProgress = 0.0F;
/*  55 */         if (progressUpdate.isPositive()) {
/*  56 */           newProgress = progressUpdate.getOldProgress() + (float)dt / 1000.0F * this.catchUpSpeed;
/*  57 */           newProgress = Math.min(newProgress, progressUpdate.getNewProgress());
/*  58 */           super.setProgress(newProgress);
/*     */         } else {
/*  60 */           for (ProgressComponent progressLayer : this.instantProgressLayers) {
/*  61 */             newProgress = progressUpdate.getOldProgress() - (float)dt / 1000.0F * this.catchUpSpeed;
/*  62 */             newProgress = Math.max(newProgress, progressUpdate.getNewProgress());
/*  63 */             progressLayer.setProgress(newProgress);
/*     */           } 
/*     */         } 
/*  66 */         progressUpdate.setOldProgress(newProgress);
/*  67 */         if (newProgress == progressUpdate.getNewProgress()) {
/*  68 */           updateIterator.remove();
/*     */         }
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setProgress(float progress) {
/*  77 */     if (progress == getProgressInstant()) {
/*     */       return;
/*     */     }
/*  80 */     ProgressUpdate update = new ProgressUpdate(progress, this.intstantProgress, this.time + this.initialDelay);
/*  81 */     if (!this.progressUpdateList.isEmpty()) {
/*  82 */       boolean exsistingIsPositive = ((ProgressUpdate)this.progressUpdateList.firstElement()).isPositive();
/*     */       
/*  84 */       if ((update.isPositive() && !exsistingIsPositive) || (!update.isPositive() && exsistingIsPositive)) {
/*     */         
/*  86 */         this.progressUpdateList.clear();
/*     */       } else {
/*  88 */         update.setOldProgress(getProgressInstant());
/*     */       } 
/*     */     } 
/*     */     
/*  92 */     this.progressUpdateList.add(update);
/*  93 */     if (update.isPositive()) {
/*  94 */       for (ProgressComponent progressLayer : this.instantProgressLayers) {
/*  95 */         progressLayer.setProgress(progress);
/*     */       }
/*     */     }
/*  98 */     this.intstantProgress = progress;
/*  99 */     super.setProgress(progress);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageSetter addInstantProgressImage(BImage progressImage, ImageBackgroundMode mode) {
/* 112 */     BProgressBackground progressBackground = new BProgressBackground(mode, progressImage, (BComponent)this, getDirection());
/*     */     
/* 114 */     this.instantProgressLayers.add(progressBackground);
/* 115 */     this.backgroundList.addBackgroundFirst((BBackground)progressBackground);
/* 116 */     return progressBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setProgressInstant(float fraction) {
/* 125 */     this.progressUpdateList.clear();
/* 126 */     super.setProgress(fraction);
/* 127 */     for (ProgressComponent progressLayer : this.instantProgressLayers) {
/* 128 */       progressLayer.setProgress(fraction);
/*     */     }
/* 130 */     this.intstantProgress = fraction;
/*     */   }
/*     */   
/*     */   public float getProgressInstant() {
/* 134 */     if (this.progressUpdateList.isEmpty())
/* 135 */       return this.intstantProgress; 
/* 136 */     return ((ProgressUpdate)this.progressUpdateList.get(this.progressUpdateList.size() - 1)).getNewProgress();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/* 141 */     super.wasAdded();
/* 142 */     setProgressInstant(this.intstantProgress);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/* 147 */     ArrayList<ProgressBackgroundProperty> progressBackgrounds = (ArrayList<ProgressBackgroundProperty>)style.findProperty((BComponent)this, null, "active_progress_list", true);
/* 148 */     if (progressBackgrounds != null) {
/* 149 */       for (ProgressBackgroundProperty progressProperty : progressBackgrounds) {
/*     */         BImage image;
/*     */         try {
/* 152 */           image = style._rsrcprov.loadImage(progressProperty.ipath);
/* 153 */         } catch (IOException e) {
/* 154 */           throw new RuntimeException(e);
/*     */         } 
/* 156 */         BProgressBackground progressBackground = new BProgressBackground(progressProperty.scaleMode, image, (BComponent)this, getDirection(), progressProperty.frame);
/* 157 */         this.instantProgressLayers.add(progressBackground);
/* 158 */         this.backgroundList.addBackground((BBackground)progressBackground);
/*     */       } 
/*     */     }
/* 161 */     super.configureStyle(style);
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/* 166 */     if (this.clickthrough) return null; 
/* 167 */     return super.getHitComponent(mx, my);
/*     */   }
/*     */   
/*     */   public boolean isClickthrough() {
/* 171 */     return this.clickthrough;
/*     */   }
/*     */   
/*     */   public void setClickthrough(boolean clickthrough) {
/* 175 */     this.clickthrough = clickthrough;
/*     */   }
/*     */   
/*     */   private class ProgressUpdate {
/*     */     private float newProgress;
/*     */     private float oldProgress;
/*     */     private long startTime;
/*     */     
/*     */     public ProgressUpdate(float newProgress, float oldProgress, long startTime) {
/* 184 */       this.newProgress = newProgress;
/* 185 */       this.oldProgress = oldProgress;
/* 186 */       this.startTime = startTime;
/*     */     }
/*     */     
/*     */     public long getStartTime() {
/* 190 */       return this.startTime;
/*     */     }
/*     */     
/*     */     public float getNewProgress() {
/* 194 */       return this.newProgress;
/*     */     }
/*     */     
/*     */     public float getOldProgress() {
/* 198 */       return this.oldProgress;
/*     */     }
/*     */     
/*     */     public void setOldProgress(float oldProgress) {
/* 202 */       this.oldProgress = oldProgress;
/*     */     }
/*     */     
/*     */     public boolean isPositive() {
/* 206 */       return (this.newProgress > this.oldProgress);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BActiveProgressBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */