/*    */ package com.funcom.tcg.client.ui.hud;
/*    */ 
/*    */ import com.funcom.commons.utils.GlobalTime;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BLabel;
/*    */ 
/*    */ class FadeIconLabel
/*    */   extends BLabel {
/* 10 */   private final Object fadeImageLock = new Object();
/*    */   
/*    */   private long start;
/*    */   private BImage fadeImage;
/*    */   private int showDurationMillis;
/*    */   
/*    */   public FadeIconLabel(int showDurationMillis) {
/* 17 */     super("");
/* 18 */     this.showDurationMillis = showDurationMillis;
/*    */   }
/*    */   
/*    */   public void setFadeImage(BImage fadeImage) {
/* 22 */     synchronized (this.fadeImageLock) {
/* 23 */       if (this.fadeImage != null) {
/* 24 */         this.fadeImage.release();
/*    */       }
/* 26 */       this.fadeImage = fadeImage;
/* 27 */       this.fadeImage.reference();
/*    */     } 
/* 29 */     this.start = GlobalTime.getInstance().getCurrentTime();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderComponent(Renderer renderer) {
/* 34 */     super.renderComponent(renderer);
/*    */     
/* 36 */     if (this.fadeImage != null && this.start != 0L) {
/* 37 */       float sizePercent, alphaPercent, rawPercent = (float)(GlobalTime.getInstance().getCurrentTime() - this.start) / this.showDurationMillis;
/*    */ 
/*    */       
/* 40 */       float FADE_IN_PERCENT = 0.05F;
/* 41 */       float BIG_PERCENT = 0.8F;
/* 42 */       if (rawPercent < 0.05F) {
/* 43 */         sizePercent = 1.0F + (float)Math.sin(rawPercent * Math.PI / 0.05000000074505806D) / 4.0F;
/* 44 */         alphaPercent = (float)Math.sin(rawPercent * Math.PI / 2.0D / 0.05000000074505806D);
/* 45 */       } else if (rawPercent < 0.8F) {
/* 46 */         sizePercent = 1.0F;
/* 47 */         alphaPercent = 1.0F;
/*    */       } else {
/* 49 */         sizePercent = (float)Math.cos((rawPercent - 0.8F) * Math.PI);
/* 50 */         alphaPercent = sizePercent;
/*    */       } 
/*    */       
/* 53 */       if (sizePercent <= 0.0F) {
/* 54 */         synchronized (this.fadeImageLock) {
/* 55 */           this.fadeImage.release();
/* 56 */           this.fadeImage = null;
/*    */         } 
/*    */       } else {
/* 59 */         int width = Math.round(this.fadeImage.getImageWidth());
/* 60 */         int height = Math.round(this.fadeImage.getImageHeight());
/*    */         
/* 62 */         int w = (int)(width * sizePercent);
/* 63 */         int h = (int)(height * sizePercent);
/*    */         
/* 65 */         this.fadeImage.render(renderer, getWidth() / 2 - w / 2, getHeight() / 2 - h / 2, w, h, alphaPercent);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\FadeIconLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */