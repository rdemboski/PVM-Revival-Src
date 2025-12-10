/*    */ package com.funcom.tcg.client.ui.hud;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BImage;
/*    */ import com.jmex.bui.BLabel;
/*    */ 
/*    */ 
/*    */ public abstract class FlashIconLabel
/*    */   extends BLabel
/*    */ {
/*    */   private BImage image;
/*    */   
/*    */   public FlashIconLabel(BImage imageToFlash) {
/* 14 */     super("");
/* 15 */     this.image = imageToFlash;
/* 16 */     this.image.reference();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void renderComponent(Renderer renderer) {
/* 21 */     super.renderComponent(renderer);
/*    */     
/* 23 */     float alpha = getAlphaPercent();
/*    */     
/* 25 */     if (alpha >= 0.0F) {
/* 26 */       int width = Math.round(this.image.getImageWidth());
/* 27 */       int height = Math.round(this.image.getImageHeight());
/*    */       
/* 29 */       this.image.render(renderer, getWidth() / 2 - width / 2, getHeight() / 2 - height / 2, alpha);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract float getAlphaPercent();
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\FlashIconLabel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */