/*     */ package com.funcom.commons.jme.bui;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.util.Timer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HighlightedContainer
/*     */   extends BContainer
/*     */ {
/*  18 */   private static final Logger LOGGER = Logger.getLogger(HighlightedContainer.class);
/*     */   private static final String DEFAULT_STYLE_CLASS = "highlightedbutton";
/*  20 */   private static final String[] STATES = new String[] { "hover", "down", "highlight", "highlighthover", "highlightdown" };
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
/*     */   public HighlightedContainer() {
/*  37 */     super("");
/*  38 */     this.highlighted = false;
/*  39 */     this.fakeHover = false;
/*  40 */     this.elapsed = 0.0F;
/*  41 */     this.switchTime = 0.5F;
/*  42 */     this.state = false;
/*     */   }
/*     */   
/*     */   public HighlightedContainer(BLayoutManager manager) {
/*  46 */     super(manager);
/*  47 */     this.highlighted = false;
/*  48 */     this.fakeHover = false;
/*  49 */     this.elapsed = 0.0F;
/*  50 */     this.switchTime = 0.5F;
/*  51 */     this.state = false;
/*     */   }
/*     */   
/*     */   public HighlightedContainer(String text) {
/*  55 */     super(text);
/*  56 */     this.highlighted = false;
/*  57 */     this.fakeHover = false;
/*  58 */     this.elapsed = 0.0F;
/*  59 */     this.switchTime = 0.5F;
/*  60 */     this.state = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClickthrough() {
/*  65 */     return this.clickthrough;
/*     */   }
/*     */   
/*     */   public void setClickthrough(boolean clickthrough) {
/*  69 */     this.clickthrough = clickthrough;
/*     */   }
/*     */   
/*     */   public boolean isHighlighted() {
/*  73 */     return this.highlighted;
/*     */   }
/*     */   
/*     */   public void setHighlighted(boolean highlighted) {
/*  77 */     this.highlighted = highlighted;
/*     */   }
/*     */   
/*     */   public boolean isFakeHover() {
/*  81 */     return this.fakeHover;
/*     */   }
/*     */   
/*     */   public void setFakeHover(boolean fakeHover) {
/*  85 */     this.fakeHover = fakeHover;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/*  90 */     super.configureStyle(style);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  98 */     if (this.clickthrough) return null; 
/*  99 */     return super.getHitComponent(mx, my);
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
/* 144 */     this.elapsed += Timer.getTimer().getTimePerFrame();
/*     */     
/* 146 */     if (this.highlighted && this.elapsed >= this.switchTime && getState() == 0) {
/* 147 */       this.elapsed = 0.0F;
/* 148 */       this.state = !this.state;
/*     */     } 
/*     */     
/* 151 */     super.render(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public BBackground getBackground() {
/* 156 */     if (this.fakeHover) {
/* 157 */       return this._backgrounds[1];
/*     */     }
/*     */     
/* 160 */     if (!this.highlighted) {
/* 161 */       return super.getBackground();
/*     */     }
/* 163 */     if (getState() == 0 && this.state)
/* 164 */       return this._backgrounds[0]; 
/* 165 */     if (getState() == 0 && !this.state) {
/* 166 */       return this._backgrounds[1];
/*     */     }
/* 168 */     return super.getBackground();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\HighlightedContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */