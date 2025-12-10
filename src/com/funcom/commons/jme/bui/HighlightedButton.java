/*     */ package com.funcom.commons.jme.bui;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.util.Timer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HighlightedButton
/*     */   extends IrregularButton
/*     */ {
/*  16 */   private static final Logger LOGGER = Logger.getLogger(HighlightedButton.class);
/*     */   private static final String DEFAULT_STYLE_CLASS = "highlightedbutton";
/*  18 */   private static final String[] STATES = new String[] { "hover", "down", "highlight", "highlighthover", "highlightdown" };
/*     */ 
/*     */   
/*     */   private boolean highlighted;
/*     */   
/*     */   private boolean fakeHover;
/*     */   
/*     */   private boolean clickthrough = false;
/*     */   
/*     */   private float elapsed;
/*     */   
/*     */   private float switchTime;
/*     */   
/*     */   private boolean state;
/*     */ 
/*     */   
/*     */   public HighlightedButton() {
/*  35 */     super("");
/*  36 */     this.highlighted = false;
/*  37 */     this.fakeHover = false;
/*  38 */     this.elapsed = 0.0F;
/*  39 */     this.switchTime = 0.5F;
/*  40 */     this.state = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public HighlightedButton(String text) {
/*  45 */     super(text);
/*  46 */     this.highlighted = false;
/*  47 */     this.fakeHover = false;
/*  48 */     this.elapsed = 0.0F;
/*  49 */     this.switchTime = 0.5F;
/*  50 */     this.state = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClickthrough() {
/*  55 */     return this.clickthrough;
/*     */   }
/*     */   
/*     */   public void setClickthrough(boolean clickthrough) {
/*  59 */     this.clickthrough = clickthrough;
/*     */   }
/*     */   
/*     */   public boolean isHighlighted() {
/*  63 */     return this.highlighted;
/*     */   }
/*     */   
/*     */   public void setHighlighted(boolean highlighted) {
/*  67 */     this.highlighted = highlighted;
/*     */   }
/*     */   
/*     */   public boolean isFakeHover() {
/*  71 */     return this.fakeHover;
/*     */   }
/*     */   
/*     */   public void setFakeHover(boolean fakeHover) {
/*  75 */     this.fakeHover = fakeHover;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/*  80 */     super.configureStyle(style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  88 */     if (this.clickthrough) return null; 
/*  89 */     return super.getHitComponent(mx, my);
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
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 134 */     this.elapsed += Timer.getTimer().getTimePerFrame();
/*     */     
/* 136 */     if (this.highlighted && this.elapsed >= this.switchTime && getState() == 0) {
/* 137 */       this.elapsed = 0.0F;
/* 138 */       this.state = !this.state;
/*     */     } 
/*     */     
/* 141 */     super.render(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public BBackground getBackground() {
/* 146 */     if (this.fakeHover) {
/* 147 */       return this._backgrounds[1];
/*     */     }
/*     */     
/* 150 */     if (!this.highlighted) {
/* 151 */       return super.getBackground();
/*     */     }
/* 153 */     if (getState() == 0 && this.state)
/* 154 */       return this._backgrounds[0]; 
/* 155 */     if (getState() == 0 && !this.state) {
/* 156 */       return this._backgrounds[1];
/*     */     }
/* 158 */     return super.getBackground();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\HighlightedButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */