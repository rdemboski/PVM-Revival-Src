/*    */ package com.funcom.commons.jme.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jme.util.Timer;
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.BToggleButton;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HighlightedRegularToggleButton
/*    */   extends BToggleButton
/*    */ {
/* 19 */   private static final Logger LOGGER = Logger.getLogger(HighlightedButton.class);
/*    */   private static final String DEFAULT_STYLE_CLASS = "highlightedbutton";
/* 21 */   private static final String[] STATES = new String[] { "hover", "down", "highlight", "highlighthover", "highlightdown" };
/*    */ 
/*    */   
/*    */   private boolean highlighted;
/*    */   
/*    */   private float elapsed;
/*    */   
/*    */   private float switchTime;
/*    */   
/*    */   private boolean state;
/*    */ 
/*    */   
/*    */   public HighlightedRegularToggleButton() {
/* 34 */     super("");
/* 35 */     this.highlighted = false;
/* 36 */     this.elapsed = 0.0F;
/* 37 */     this.switchTime = 0.5F;
/* 38 */     this.state = false;
/*    */   }
/*    */   
/*    */   public boolean isHighlighted() {
/* 42 */     return this.highlighted;
/*    */   }
/*    */   
/*    */   public void setHighlighted(boolean highlighted) {
/* 46 */     this.highlighted = highlighted;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configureStyle(BStyleSheet style) {
/* 51 */     super.configureStyle(style);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer) {
/* 59 */     this.elapsed += Timer.getTimer().getTimePerFrame();
/*    */     
/* 61 */     if (this.highlighted && this.elapsed >= this.switchTime && getState() == 0) {
/* 62 */       this.elapsed = 0.0F;
/* 63 */       this.state = !this.state;
/*    */     } 
/*    */     
/* 66 */     super.render(renderer);
/*    */   }
/*    */ 
/*    */   
/*    */   public BBackground getBackground() {
/* 71 */     if (!this.highlighted) {
/* 72 */       return super.getBackground();
/*    */     }
/* 74 */     if (getState() == 0 && this.state)
/* 75 */       return this._backgrounds[0]; 
/* 76 */     if (getState() == 0 && !this.state) {
/* 77 */       return this._backgrounds[1];
/*    */     }
/* 79 */     return super.getBackground();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\bui\HighlightedRegularToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */