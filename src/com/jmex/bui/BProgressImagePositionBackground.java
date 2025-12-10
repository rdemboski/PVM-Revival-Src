/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ 
/*    */ public class BProgressImagePositionBackground
/*    */   extends BImagePositionBackground implements ProgressComponent {
/*    */   private BComponent component;
/*    */   private BProgressBar.Direction direction;
/*    */   private float progress;
/*    */   
/*    */   public BProgressImagePositionBackground(BImage image, BComponent component, BProgressBar.Direction direction) {
/* 12 */     super(image);
/*    */     
/* 14 */     this.component = component;
/* 15 */     this.direction = direction;
/*    */     
/* 17 */     setProgress(0.0F);
/*    */   }
/*    */   
/*    */   public void setComponent(BComponent component) {
/* 21 */     this.component = component;
/*    */   }
/*    */   
/*    */   public BComponent getComponent() {
/* 25 */     return this.component;
/*    */   }
/*    */   
/*    */   public void setDirection(BProgressBar.Direction direction) {
/* 29 */     this.direction = direction;
/*    */   }
/*    */   
/*    */   public BProgressBar.Direction getDirection() {
/* 33 */     return this.direction;
/*    */   }
/*    */   
/*    */   public void setProgress(float progress) {
/* 37 */     if (progress < 0.0F || progress > 1.0F) {
/* 38 */       throw new IllegalArgumentException("Progress parameter needs to be 0 < x < 1.");
/*    */     }
/*    */     
/* 41 */     this.progress = progress;
/*    */     
/* 43 */     updateProgressPositioning();
/*    */   }
/*    */   
/*    */   public float getProgress() {
/* 47 */     return this.progress;
/*    */   }
/*    */   
/*    */   private void updateProgressPositioning() {
/* 51 */     switch (this.direction) {
/*    */       case PROGRESSDIR_NORTH:
/* 53 */         setPosition((int)(getComponent().getWidth() - getImage().getWidth()) / 2, (int)(getComponent().getHeight() * this.progress - getImage().getHeight() / 2.0F));
/*    */         break;
/*    */ 
/*    */ 
/*    */       
/*    */       case PROGRESSDIR_SOUTH:
/* 59 */         setPosition((int)(getComponent().getWidth() - getImage().getWidth()) / 2, (int)(getComponent().getHeight() * (1.0F - this.progress) - getImage().getHeight() / 2.0F));
/*    */         break;
/*    */ 
/*    */ 
/*    */       
/*    */       case WEST:
/* 65 */         setPosition((int)(getComponent().getWidth() * (1.0F - this.progress) - getImage().getWidth() / 2.0F), (int)(getComponent().getHeight() - getImage().getHeight()) / 2);
/*    */         break;
/*    */ 
/*    */ 
/*    */       
/*    */       case PROGRESSDIR_EAST:
/* 71 */         setPosition((int)(getComponent().getWidth() * this.progress - getImage().getWidth() / 2.0F), (int)(getComponent().getHeight() - getImage().getHeight()) / 2);
/*    */         break;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer, int x, int y, int width, int height, float alpha) {
/* 81 */     updateProgressPositioning();
/* 82 */     super.render(renderer, x, y, width, height, alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BProgressImagePositionBackground.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */