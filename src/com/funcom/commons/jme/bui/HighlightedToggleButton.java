/*     */ package com.funcom.commons.jme.bui;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.util.Timer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BHoverToggleButton;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HighlightedToggleButton
/*     */   extends BHoverToggleButton
/*     */ {
/*  19 */   private static final Logger LOGGER = Logger.getLogger(HighlightedButton.class);
/*     */   private static final String DEFAULT_STYLE_CLASS = "highlightedbutton";
/*  21 */   private static final String[] STATES = new String[] { "hover", "down", "highlight", "highlighthover", "highlightdown" };
/*     */   
/*     */   private boolean highlighted;
/*     */   
/*     */   private boolean fakeHover;
/*     */   
/*     */   private float elapsed;
/*     */   
/*     */   private float switchTime;
/*     */   
/*     */   private boolean state;
/*     */   
/*     */   private boolean clickthrough = false;
/*     */ 
/*     */   
/*     */   public HighlightedToggleButton() {
/*  37 */     super("");
/*  38 */     this.highlighted = false;
/*  39 */     this.fakeHover = false;
/*  40 */     this.elapsed = 0.0F;
/*  41 */     this.switchTime = 0.5F;
/*  42 */     this.state = false;
/*     */   }
/*     */   
/*     */   public HighlightedToggleButton(String text) {
/*  46 */     super(text);
/*  47 */     this.highlighted = false;
/*  48 */     this.fakeHover = false;
/*  49 */     this.elapsed = 0.0F;
/*  50 */     this.switchTime = 0.5F;
/*  51 */     this.state = false;
/*     */   }
/*     */   
/*     */   public boolean isHighlighted() {
/*  55 */     return this.highlighted;
/*     */   }
/*     */   
/*     */   public void setHighlighted(boolean highlighted) {
/*  59 */     this.highlighted = highlighted;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/*  64 */     super.configureStyle(style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFakeHover() {
/*  71 */     return this.fakeHover;
/*     */   }
/*     */   
/*     */   public void setFakeHover(boolean fakeHover) {
/*  75 */     this.fakeHover = fakeHover;
/*     */   }
/*     */   
/*     */   public boolean isClickthrough() {
/*  79 */     return this.clickthrough;
/*     */   }
/*     */   
/*     */   public void setClickthrough(boolean clickthrough) {
/*  83 */     this.clickthrough = clickthrough;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/*  88 */     this.elapsed += Timer.getTimer().getTimePerFrame();
/*     */     
/*  90 */     if (this.highlighted && this.elapsed >= this.switchTime && getState() == 0) {
/*  91 */       this.elapsed = 0.0F;
/*  92 */       this.state = !this.state;
/*     */     } 
/*     */     
/*  95 */     super.render(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/* 100 */     if (this.clickthrough) {
/* 101 */       return null;
/*     */     }
/*     */     
/* 104 */     return super.getHitComponent(mx, my);
/*     */   }
/*     */ 
/*     */   
/*     */   public BBackground getBackground() {
/* 109 */     if (this.fakeHover) {
/* 110 */       return this._backgrounds[1];
/*     */     }
/* 112 */     if (!this.highlighted) {
/* 113 */       return super.getBackground();
/*     */     }
/* 115 */     if (getState() == 0 && this.state)
/* 116 */       return this._backgrounds[0]; 
/* 117 */     if (getState() == 0 && !this.state) {
/* 118 */       return this._backgrounds[1];
/*     */     }
/* 120 */     return super.getBackground();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\HighlightedToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */