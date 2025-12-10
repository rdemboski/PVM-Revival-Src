/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Insets;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class StretchedImageButton
/*     */   extends BButton
/*     */ {
/*     */   private boolean wasAdded;
/*     */   private BImage image;
/*     */   private boolean clickable = true;
/*     */   
/*     */   public StretchedImageButton() {
/*  21 */     super("");
/*     */   }
/*     */   
/*     */   public StretchedImageButton(BImage image) {
/*  25 */     super("");
/*  26 */     setImage(image);
/*     */   }
/*     */   
/*     */   public void setImage(BImage image) {
/*  30 */     if (isAdded() && this.wasAdded) {
/*  31 */       BImage oldImage = this.image;
/*  32 */       if (oldImage != null) {
/*  33 */         oldImage.release();
/*     */       }
/*  35 */       if (image != null) {
/*  36 */         image.reference();
/*     */       }
/*     */     } 
/*  39 */     this.image = image;
/*     */   }
/*     */   
/*     */   public BImage getImage() {
/*  43 */     return this.image;
/*     */   }
/*     */ 
/*     */   
/*     */   public Dimension getPreferredSize(int whint, int hhint) {
/*  48 */     if (this.image == null) {
/*  49 */       return super.getPreferredSize(whint, hhint);
/*     */     }
/*  51 */     return new Dimension(this.image.getImageWidth(), this.image.getImageHeight());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/*  56 */     super.renderComponent(renderer);
/*     */     
/*  58 */     if (this.image != null) {
/*  59 */       Insets insets = getInsets();
/*  60 */       this.image.render(renderer, insets.left, insets.bottom, getWidth() - insets.getHorizontal(), getHeight() - insets.getVertical(), getAlpha());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void wasAdded() {
/*  68 */     super.wasAdded();
/*     */     
/*  70 */     if (this.image != null) {
/*  71 */       this.image.reference();
/*     */     }
/*     */     
/*  74 */     this.wasAdded = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasRemoved() {
/*  79 */     this.wasAdded = false;
/*     */     
/*  81 */     super.wasRemoved();
/*  82 */     if (this.image != null) {
/*  83 */       this.image.release();
/*     */     }
/*     */   }
/*     */   
/*     */   public void setClickable(boolean clickable) {
/*  88 */     this.clickable = clickable;
/*     */   }
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  93 */     if (this.clickable) {
/*  94 */       return super.getHitComponent(mx, my);
/*     */     }
/*  96 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/* 101 */     super.configureStyle(style);
/*     */     
/* 103 */     if (this.image == null)
/* 104 */       this.image = (BImage)style.findProperty((BComponent)this, null, "image", true); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\StretchedImageButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */