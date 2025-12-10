/*    */ package com.funcom.gameengine.view.particles;
/*    */ 
/*    */ import com.jme.math.Vector2f;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GuiParticleRelPos
/*    */ {
/*    */   RelPosX xpos;
/*    */   RelPosY ypos;
/*    */   float x;
/*    */   float y;
/*    */   
/*    */   public GuiParticleRelPos(RelPosX xpos, RelPosY ypos, float x, float y) {
/* 15 */     this.xpos = xpos;
/* 16 */     this.ypos = ypos;
/* 17 */     this.x = x;
/* 18 */     this.y = y;
/*    */   }
/*    */   
/*    */   public Vector2f getPos(Vector2f currentResolutionVector) {
/* 22 */     return new Vector2f(this.xpos.getPosValue() * currentResolutionVector.getX() + this.x, this.ypos.getPosValue() * currentResolutionVector.getY() + this.y);
/*    */   }
/*    */   
/*    */   public enum RelPosX {
/* 26 */     LEFT, CENTER, RIGHT;
/*    */     
/*    */     public float getPosValue() {
/* 29 */       if (this == LEFT)
/* 30 */         return 0.0F; 
/* 31 */       if (this == CENTER) {
/* 32 */         return 0.5F;
/*    */       }
/* 34 */       return 1.0F;
/*    */     }
/*    */   }
/*    */   
/*    */   public enum RelPosY
/*    */   {
/* 40 */     TOP, CENTER, BOTTOM;
/*    */     
/*    */     public float getPosValue() {
/* 43 */       if (this == BOTTOM)
/* 44 */         return 0.0F; 
/* 45 */       if (this == CENTER) {
/* 46 */         return 0.5F;
/*    */       }
/* 48 */       return 1.0F;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\particles\GuiParticleRelPos.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */