/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BImagePositionBackground
/*    */   extends BBackground
/*    */   implements ImageSetter
/*    */ {
/*    */   private BImage image;
/*    */   private int x;
/*    */   private int y;
/*    */   
/*    */   public BImagePositionBackground(BImage image) {
/* 17 */     this(image, 0, 0);
/*    */   }
/*    */   
/*    */   public BImagePositionBackground(BImage image, int x, int y) {
/* 21 */     this.image = image;
/* 22 */     this.x = x;
/* 23 */     this.y = y;
/*    */   }
/*    */   
/*    */   public void setImage(BImage image) {
/* 27 */     if (image != this.image) {
/* 28 */       this.image.release();
/* 29 */       image.reference();
/*    */     } 
/* 31 */     this.image = image;
/*    */   }
/*    */   
/*    */   public BImage getImage() {
/* 35 */     return this.image;
/*    */   }
/*    */   
/*    */   public void setPosition(int x, int y) {
/* 39 */     this.x = x;
/* 40 */     this.y = y;
/*    */   }
/*    */   
/*    */   public int getX() {
/* 44 */     return this.x;
/*    */   }
/*    */   
/*    */   public int getY() {
/* 48 */     return this.y;
/*    */   }
/*    */ 
/*    */   
/*    */   public void wasAdded() {
/* 53 */     super.wasAdded();
/* 54 */     this.image.reference();
/*    */   }
/*    */ 
/*    */   
/*    */   public void wasRemoved() {
/* 59 */     super.wasRemoved();
/* 60 */     this.image.release();
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer, int x, int y, int width, int height, float alpha) {
/* 65 */     super.render(renderer, x, y, width, height, alpha);
/* 66 */     this.image.render(renderer, x + this.x, y + this.y, (int)Math.floor(this.image.getWidth()), (int)Math.floor(this.image.getHeight()), alpha);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BImagePositionBackground.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */