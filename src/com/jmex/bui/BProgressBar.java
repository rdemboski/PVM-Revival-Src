/*     */ package com.jmex.bui;
/*     */ 
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.parser.ProgressBackgroundProperty;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class BProgressBar
/*     */   extends BLabel
/*     */   implements ProgressComponent
/*     */ {
/*     */   protected static final int STATE_COUNT = 4;
/*  23 */   protected static final String[] STATE_PCLASSES = new String[] { null, "hover", "disabled", "down" };
/*     */   
/*     */   private float progress;
/*     */   
/*     */   private Direction direction;
/*     */   
/*     */   protected BBackgroundList backgroundList;
/*     */   private Set<ProgressComponent> progressLayers;
/*     */   private boolean clickthrough = false;
/*     */   
/*     */   public BProgressBar() {
/*  34 */     this(Direction.PROGRESSDIR_EAST);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BProgressBar(Direction direction) {
/*  45 */     super("");
/*  46 */     this.direction = direction;
/*     */     
/*  48 */     this.backgroundList = new BBackgroundList();
/*  49 */     this.progressLayers = new HashSet<ProgressComponent>();
/*     */     
/*  51 */     setBackground(0, this.backgroundList);
/*  52 */     setBackground(1, this.backgroundList);
/*  53 */     setBackground(2, this.backgroundList);
/*  54 */     setBackground(3, this.backgroundList);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  59 */     return "progressbar";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBackgroundImage(BImage backImage) {
/*  68 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, backImage);
/*  69 */     this.backgroundList.addBackground((BBackground)imageBackground);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBackgroundImage(BImage backImage, ImageBackgroundMode mode) {
/*  79 */     ImageBackground imageBackground = new ImageBackground(mode, backImage);
/*  80 */     this.backgroundList.addBackground((BBackground)imageBackground);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBackgroundImage(BImage backImage, ImageBackgroundMode mode, Insets frame) {
/*  91 */     ImageBackground imageBackground = new ImageBackground(mode, backImage, frame);
/*  92 */     this.backgroundList.addBackground((BBackground)imageBackground);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageSetter addProgressImage(BImage progressImage) {
/* 102 */     BProgressBackground progressBackground = new BProgressBackground(ImageBackgroundMode.SCALE_XY, progressImage, (BComponent)this, this.direction);
/*     */     
/* 104 */     this.progressLayers.add(progressBackground);
/* 105 */     this.backgroundList.addBackground((BBackground)progressBackground);
/* 106 */     return progressBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageSetter addProgressImage(BImage progressImage, ImageBackgroundMode mode) {
/* 117 */     BProgressBackground progressBackground = new BProgressBackground(mode, progressImage, (BComponent)this, this.direction);
/*     */     
/* 119 */     this.progressLayers.add(progressBackground);
/* 120 */     this.backgroundList.addBackground((BBackground)progressBackground);
/* 121 */     return progressBackground;
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
/*     */   public ImageSetter addProgressImage(BImage progressImage, ImageBackgroundMode mode, Insets frame) {
/* 133 */     BProgressBackground progressBackground = new BProgressBackground(mode, progressImage, (BComponent)this, this.direction, frame);
/*     */     
/* 135 */     this.progressLayers.add(progressBackground);
/* 136 */     this.backgroundList.addBackground((BBackground)progressBackground);
/* 137 */     return progressBackground;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImageSetter addOverlayImage(BImage overlayImage) {
/* 147 */     BProgressImagePositionBackground progressBackground = new BProgressImagePositionBackground(overlayImage, (BComponent)this, this.direction);
/*     */     
/* 149 */     this.progressLayers.add(progressBackground);
/* 150 */     this.backgroundList.addBackground(progressBackground);
/* 151 */     return progressBackground;
/*     */   }
/*     */   
/*     */   public void setDirection(Direction direction) {
/* 155 */     this.direction = direction;
/*     */   }
/*     */   
/*     */   public Direction getDirection() {
/* 159 */     return this.direction;
/*     */   }
/*     */   
/*     */   public void setProgress(float progress) {
/* 163 */     this.progress = progress;
/*     */     
/* 165 */     for (ProgressComponent progressLayer : this.progressLayers) {
/* 166 */       progressLayer.setProgress(progress);
/*     */     }
/*     */   }
/*     */   
/*     */   public float getProgress() {
/* 171 */     return this.progress;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/* 177 */     configureStyle(getWindow().getStyleSheet());
/* 178 */     this._label.wasAdded();
/*     */     
/*     */     int ii;
/* 181 */     for (ii = 0; ii < this._backgrounds.length; ii++) {
/* 182 */       if (this._backgrounds[ii] != null) {
/* 183 */         this._backgrounds[ii].wasAdded();
/*     */       }
/*     */     } 
/* 186 */     for (ii = 0; ii < this._borders.length; ii++) {
/* 187 */       if (this._borders[ii] != null) {
/* 188 */         this._borders[ii].wasAdded();
/*     */       }
/*     */     } 
/* 191 */     setProgress(this.progress);
/*     */   }
/*     */   
/*     */   protected int getStateCount() {
/* 195 */     return 4;
/*     */   }
/*     */   
/*     */   protected String getStatePseudoClass(int state) {
/* 199 */     return STATE_PCLASSES[state];
/*     */   }
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/* 203 */     super.configureStyle(style);
/*     */     
/* 205 */     ArrayList<BBackground> backgrounds = (ArrayList<BBackground>)style.findProperty((BComponent)this, null, "background_list", true);
/* 206 */     if (backgrounds != null) {
/* 207 */       for (BBackground background : backgrounds) {
/* 208 */         this.backgroundList.addBackground(background);
/*     */       }
/*     */     }
/* 211 */     ArrayList<ProgressBackgroundProperty> progressBackgrounds = (ArrayList<ProgressBackgroundProperty>)style.findProperty((BComponent)this, null, "progress_list", true);
/* 212 */     if (progressBackgrounds != null) {
/* 213 */       for (ProgressBackgroundProperty progressProperty : progressBackgrounds) {
/*     */         BImage image;
/*     */         try {
/* 216 */           image = style._rsrcprov.loadImage(progressProperty.ipath);
/* 217 */         } catch (IOException e) {
/* 218 */           throw new RuntimeException(e);
/*     */         } 
/* 220 */         BProgressBackground progressBackground = new BProgressBackground(progressProperty.scaleMode, image, (BComponent)this, this.direction, progressProperty.frame);
/* 221 */         this.progressLayers.add(progressBackground);
/* 222 */         this.backgroundList.addBackground((BBackground)progressBackground);
/*     */       } 
/*     */     }
/* 225 */     ArrayList<BImage> overlays = (ArrayList<BImage>)style.findProperty((BComponent)this, null, "overlay_list", true);
/* 226 */     if (overlays != null)
/* 227 */       for (BImage overlay : overlays) {
/* 228 */         addOverlayImage(overlay);
/*     */       } 
/*     */   }
/*     */   
/*     */   public void removeAllBackgroundImages() {
/* 233 */     this.backgroundList.removeAllBackgrounds();
/*     */   }
/*     */   
/*     */   public void removeAllProgressImages() {
/* 237 */     for (ProgressComponent progressLayer : this.progressLayers) {
/* 238 */       this.backgroundList.removeBackground((BBackground)progressLayer);
/*     */     }
/* 240 */     this.progressLayers.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/* 245 */     if (this.clickthrough) return null; 
/* 246 */     return super.getHitComponent(mx, my);
/*     */   }
/*     */   
/*     */   public boolean isClickthrough() {
/* 250 */     return this.clickthrough;
/*     */   }
/*     */   
/*     */   public void setClickthrough(boolean clickthrough) {
/* 254 */     this.clickthrough = clickthrough;
/*     */   }
/*     */   
/*     */   public enum Direction {
/* 258 */     PROGRESSDIR_NORTH,
/* 259 */     PROGRESSDIR_SOUTH,
/* 260 */     WEST,
/* 261 */     PROGRESSDIR_EAST;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BProgressBar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */