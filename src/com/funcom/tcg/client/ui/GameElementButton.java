/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import com.jmex.bui.background.ImageBackground;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ 
/*    */ public abstract class GameElementButton extends SelectableButton {
/*    */   private static final String PROP_FILLED_STAR = "filled_star";
/*    */   private BImage filledStar;
/*    */   private BBackground selectionOverlay;
/*    */   
/*    */   public GameElementButton(ResourceManager resourceManager, SelectableButtonModel model) {
/* 18 */     super(resourceManager, model);
/*    */   }
/*    */   
/*    */   protected void renderComponentOverlay(Renderer renderer) {
/* 22 */     this.selectionOverlay.render(renderer, 0, 0, this._width, this._height, this._alpha);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configureStyle(BStyleSheet style) {
/* 27 */     super.configureStyle(style);
/*    */     
/* 29 */     this.filledStar = (BImage)style.findProperty((BComponent)this, null, "filled_star", true);
/* 30 */     if (this.filledStar == null) {
/* 31 */       throw new IllegalStateException("missing filled star: styleclass=" + getStyleClass() + " prop=" + "filled_star");
/*    */     }
/*    */     
/* 34 */     BImage selectionImage = (BImage)style.findProperty((BComponent)this, null, "image", true);
/* 35 */     if (selectionImage != null) {
/* 36 */       this.selectionOverlay = (BBackground)new ImageBackground(ImageBackgroundMode.FRAME_XY, selectionImage);
/*    */     } else {
/* 38 */       throw new IllegalStateException("missing filled star: styleclass=" + getStyleClass() + " prop=" + "image");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void wasAdded() {
/* 44 */     super.wasAdded();
/* 45 */     this.filledStar.reference();
/* 46 */     this.selectionOverlay.wasAdded();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void wasRemoved() {
/* 51 */     this.selectionOverlay.wasRemoved();
/* 52 */     this.filledStar.release();
/* 53 */     super.wasRemoved();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderStar(Renderer renderer, int starX, int starY, int starSize) {
/* 58 */     int flippedY = getHeight() - 1 - starSize - starY;
/* 59 */     this.filledStar.render(renderer, starX, flippedY, starSize, starSize, getAlpha());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\GameElementButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */