/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.BTextComponent;
/*    */ import com.jmex.bui.Label;
/*    */ import com.jmex.bui.TcgLabel;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ 
/*    */ public class RightAlignedBLabel
/*    */   extends BLabel {
/*    */   private int currentX;
/*    */   private int currentWidth;
/*    */   
/*    */   public RightAlignedBLabel(String text) {
/* 16 */     super(text);
/* 17 */     this._label = (Label)new RightAlignedLabel((BTextComponent)this);
/* 18 */     this._label.setText(text);
/*    */   }
/*    */   
/*    */   public RightAlignedBLabel(String text, String styleClass) {
/* 22 */     super(text, styleClass);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderBackground(Renderer renderer) {
/* 27 */     BBackground background = getBackground();
/* 28 */     if (background != null) {
/* 29 */       background.render(renderer, this.currentX, 0, this.currentWidth, this._height, this._alpha);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void layout() {
/* 35 */     super.layout();
/* 36 */     this.currentX = ((RightAlignedLabel)this._label).getX();
/* 37 */     this.currentWidth = ((RightAlignedLabel)this._label).getWidth();
/*    */   }
/*    */   
/*    */   private class RightAlignedLabel extends TcgLabel {
/*    */     private RightAlignedLabel(BTextComponent container) {
/* 42 */       super(container);
/*    */     }
/*    */     
/*    */     public int getX() {
/* 46 */       return (this._config.glyphs == null) ? 0 : (this._container.getWidth() - this._config.glyphs.size.width - (RightAlignedBLabel.this.getInsets()).right - (RightAlignedBLabel.this.getInsets()).left);
/*    */     }
/*    */     
/*    */     public int getWidth() {
/* 50 */       return (this._config.glyphs == null) ? 0 : (this._config.glyphs.size.width + (RightAlignedBLabel.this.getInsets()).right + (RightAlignedBLabel.this.getInsets()).left);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\RightAlignedBLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */