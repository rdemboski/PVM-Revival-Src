/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BStyleSheet;
/*    */ import com.jmex.bui.background.ImageBackground;
/*    */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ 
/*    */ public abstract class OverlayedContainer extends BContainer {
/*    */   private static final String PROP_IMAGE = "image";
/*    */   private ImageBackground overlay;
/*    */   private ImageBackground overlaySelect;
/*    */   private static final String FIXED_SELECTION_SC = "fixed--framed-container-select";
/*    */   
/*    */   public OverlayedContainer() {
/* 19 */     setLayoutManager((BLayoutManager)new FreeAbsoluteLayout());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void configureStyle(BStyleSheet style) {
/* 24 */     super.configureStyle(style);
/*    */     
/* 26 */     BImage overlayImage = (BImage)style.findProperty((BComponent)this, null, "image", true);
/*    */     
/* 28 */     if (overlayImage != null) {
/* 29 */       this.overlay = new ImageBackground(ImageBackgroundMode.FRAME_XY, overlayImage);
/* 30 */       if (isAdded()) {
/* 31 */         this.overlay.wasAdded();
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 36 */     BComponent dummy = new BComponent();
/* 37 */     dummy.setStyleClass("fixed--framed-container-select");
/* 38 */     BImage selectImage = (BImage)style.findProperty(dummy, null, "image", true);
/*    */     
/* 40 */     if (selectImage != null) {
/* 41 */       this.overlaySelect = new ImageBackground(ImageBackgroundMode.FRAME_XY, selectImage);
/* 42 */       if (isAdded()) {
/* 43 */         this.overlaySelect.wasAdded();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void renderOverlay(Renderer renderer, boolean selected) {
/* 49 */     renderOverlay(renderer);
/*    */     
/* 51 */     if (selected) {
/* 52 */       renderSelected(renderer);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void renderSelected(Renderer renderer) {
/* 57 */     if (this.overlaySelect != null) {
/* 58 */       this.overlaySelect.render(renderer, 0, 0, getWidth(), getHeight(), getAlpha());
/*    */     } else {
/* 60 */       throw new IllegalStateException("missing property: styleclass=fixed--framed-container-select prop=image");
/*    */     } 
/*    */   }
/*    */   
/*    */   protected void renderOverlay(Renderer renderer) {
/* 65 */     if (this.overlay != null) {
/* 66 */       this.overlay.render(renderer, 0, 0, getWidth(), getHeight(), getAlpha());
/*    */     } else {
/* 68 */       throw new IllegalStateException("missing property: styleclass=" + getStyleClass() + " prop=" + "image");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void wasAdded() {
/* 74 */     super.wasAdded();
/* 75 */     if (this.overlay != null) {
/* 76 */       this.overlay.wasAdded();
/*    */     }
/* 78 */     if (this.overlaySelect != null) {
/* 79 */       this.overlaySelect.wasAdded();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void wasRemoved() {
/* 85 */     if (this.overlaySelect != null) {
/* 86 */       this.overlaySelect.wasRemoved();
/*    */     }
/* 88 */     if (this.overlay != null) {
/* 89 */       this.overlay.wasRemoved();
/*    */     }
/* 91 */     super.wasRemoved();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\OverlayedContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */