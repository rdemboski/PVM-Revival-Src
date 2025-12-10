/*    */ package com.funcom.commons.jme.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.util.Timer;
/*    */ import com.jmex.bui.BButton;
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HighlightedRegularButton
/*    */   extends BButton
/*    */ {
/* 16 */   private static final Logger LOGGER = Logger.getLogger(HighlightedRegularButton.class);
/*    */   private static final String DEFAULT_STYLE_CLASS = "highlightedbutton";
/* 18 */   private static final String[] STATES = new String[] { "hover", "down", "highlight", "highlighthover", "highlightdown" };
/*    */ 
/*    */   
/*    */   private boolean highlighted;
/*    */   
/*    */   private boolean fakeHover;
/*    */   
/*    */   private float elapsed;
/*    */   
/*    */   private float switchTime;
/*    */   
/*    */   private boolean state;
/*    */ 
/*    */   
/*    */   public HighlightedRegularButton() {
/* 33 */     super("");
/* 34 */     this.highlighted = false;
/* 35 */     this.fakeHover = false;
/* 36 */     this.elapsed = 0.0F;
/* 37 */     this.switchTime = 0.5F;
/* 38 */     this.state = false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isHighlighted() {
/* 43 */     return this.highlighted;
/*    */   }
/*    */   
/*    */   public void setHighlighted(boolean highlighted) {
/* 47 */     this.highlighted = highlighted;
/*    */   }
/*    */   
/*    */   public boolean isFakeHover() {
/* 51 */     return this.fakeHover;
/*    */   }
/*    */   
/*    */   public void setFakeHover(boolean fakeHover) {
/* 55 */     this.fakeHover = fakeHover;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configureStyle(BStyleSheet style) {
/* 60 */     super.configureStyle(style);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer) {
/* 68 */     this.elapsed += Timer.getTimer().getTimePerFrame();
/*    */     
/* 70 */     if (this.highlighted && this.elapsed >= this.switchTime && getState() == 0) {
/* 71 */       this.elapsed = 0.0F;
/* 72 */       this.state = !this.state;
/*    */     } 
/*    */     
/* 75 */     super.render(renderer);
/*    */   }
/*    */ 
/*    */   
/*    */   public BBackground getBackground() {
/* 80 */     if (this.fakeHover) {
/* 81 */       return this._backgrounds[1];
/*    */     }
/*    */     
/* 84 */     if (!this.highlighted) {
/* 85 */       return super.getBackground();
/*    */     }
/* 87 */     if (getState() == 0 && this.state)
/* 88 */       return this._backgrounds[0]; 
/* 89 */     if (getState() == 0 && !this.state) {
/* 90 */       return this._backgrounds[1];
/*    */     }
/* 92 */     return super.getBackground();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\HighlightedRegularButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */