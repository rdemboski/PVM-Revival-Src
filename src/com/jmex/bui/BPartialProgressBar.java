/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.border.BBorder;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.parser.ProgressBackgroundProperty;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BPartialProgressBar
/*     */   extends BLabel
/*     */   implements ProgressComponent
/*     */ {
/*     */   protected static final int STATE_COUNT = 4;
/*  21 */   protected static final String[] STATE_PCLASSES = new String[] { null, "hover", "disabled", "down" };
/*     */ 
/*     */   
/*     */   private float progress;
/*     */ 
/*     */   
/*     */   private float secondProgress;
/*     */   
/*     */   private BProgressBar.Direction direction;
/*     */   
/*     */   private BBackgroundList backgroundList;
/*     */   
/*     */   private Set<BPartialProgressBackground> progressLayers;
/*     */ 
/*     */   
/*     */   public BPartialProgressBar(BProgressBar.Direction direction) {
/*  37 */     super("");
/*  38 */     this.direction = direction;
/*     */     
/*  40 */     this.backgroundList = new BBackgroundList();
/*  41 */     this.progressLayers = new HashSet<BPartialProgressBackground>();
/*     */     
/*  43 */     setBackground(0, this.backgroundList);
/*  44 */     setBackground(1, this.backgroundList);
/*  45 */     setBackground(2, this.backgroundList);
/*  46 */     setBackground(3, this.backgroundList);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  51 */     return "progressbar";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBackgroundImage(BImage backImage) {
/*  60 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, backImage);
/*  61 */     this.backgroundList.addBackground((BBackground)imageBackground);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageSetter addProgressImage(BImage progressImage) {
/*  71 */     BPartialProgressBackground progressBackground = new BPartialProgressBackground(ImageBackgroundMode.SCALE_XY, progressImage, (BComponent)this, this.direction);
/*     */     
/*  73 */     this.progressLayers.add(progressBackground);
/*  74 */     this.backgroundList.addBackground((BBackground)progressBackground);
/*  75 */     return progressBackground;
/*     */   }
/*     */   
/*     */   public void setProgress(float progress) {
/*  79 */     this.progress = progress;
/*     */     
/*  81 */     for (ProgressComponent progressLayer : this.progressLayers) {
/*  82 */       progressLayer.setProgress(progress);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getProgress() {
/*  87 */     return this.progress;
/*     */   }
/*     */   
/*     */   public float getSecondProgress() {
/*  91 */     return this.secondProgress;
/*     */   }
/*     */   
/*     */   public void setSecondProgress(float secondProgress) {
/*  95 */     this.secondProgress = secondProgress;
/*     */     
/*  97 */     for (BPartialProgressBackground progressLayer : this.progressLayers) {
/*  98 */       progressLayer.setSecondProgress(secondProgress);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/* 105 */     super.wasAdded();
/*     */ 
/*     */     
/* 108 */     for (BBackground _background : this._backgrounds) {
/* 109 */       if (_background != null) {
/* 110 */         _background.wasAdded();
/*     */       }
/*     */     } 
/* 113 */     for (BBorder _border : this._borders) {
/* 114 */       if (_border != null) {
/* 115 */         _border.wasAdded();
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   protected int getStateCount() {
/* 121 */     return 4;
/*     */   }
/*     */   
/*     */   protected String getStatePseudoClass(int state) {
/* 125 */     return STATE_PCLASSES[state];
/*     */   }
/*     */   
/*     */   public void removeAllBackgroundImages() {
/* 129 */     this.backgroundList.removeAllBackgrounds();
/*     */   }
/*     */   
/*     */   public void removeAllProgressImages() {
/* 133 */     for (ProgressComponent progressLayer : this.progressLayers) {
/* 134 */       this.backgroundList.removeBackground((BBackground)progressLayer);
/*     */     }
/* 136 */     this.progressLayers.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/* 141 */     super.configureStyle(style);
/*     */     
/* 143 */     ArrayList<BBackground> backgrounds = (ArrayList<BBackground>)style.findProperty((BComponent)this, null, "background_list", true);
/* 144 */     if (backgrounds != null) {
/* 145 */       for (BBackground background : backgrounds) {
/* 146 */         this.backgroundList.addBackground(background);
/*     */       }
/*     */     }
/* 149 */     ArrayList<ProgressBackgroundProperty> progressBackgrounds = (ArrayList<ProgressBackgroundProperty>)style.findProperty((BComponent)this, null, "progress_list", true);
/* 150 */     if (progressBackgrounds != null)
/* 151 */       for (ProgressBackgroundProperty progressProperty : progressBackgrounds) {
/*     */         BImage image;
/*     */         try {
/* 154 */           image = style._rsrcprov.loadImage(progressProperty.ipath);
/* 155 */         } catch (IOException e) {
/* 156 */           throw new RuntimeException(e);
/*     */         } 
/* 158 */         BPartialProgressBackground progressBackground = new BPartialProgressBackground(progressProperty.scaleMode, image, (BComponent)this, this.direction, progressProperty.frame);
/* 159 */         this.progressLayers.add(progressBackground);
/* 160 */         this.backgroundList.addBackground((BBackground)progressBackground);
/*     */       }  
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BPartialProgressBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */