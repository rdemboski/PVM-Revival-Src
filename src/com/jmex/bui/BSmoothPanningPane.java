/*    */ package com.jmex.bui;
/*    */ 
/*    */ import com.jme.renderer.Renderer;
/*    */ 
/*    */ public class BSmoothPanningPane
/*    */   extends BPanningPane {
/*    */   private static final float OFFSET_INTERPOLATE_SPEED = 7.0F;
/*    */   private boolean inited;
/*    */   private float currentX;
/*    */   private float currentY;
/*    */   private int targetY;
/*    */   private int targetX;
/*    */   private long lastRenderAt;
/*    */   
/*    */   public BSmoothPanningPane(BComponent target) {
/* 16 */     super(target);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setYOffset(int newOffsetY) {
/* 21 */     this.targetY = clampY(newOffsetY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setXOffset(int newOffsetX) {
/* 26 */     this.targetX = clampX(newOffsetX);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getYOffset() {
/* 31 */     return this.targetY;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getXOffset() {
/* 36 */     return this.targetX;
/*    */   }
/*    */ 
/*    */   
/*    */   public void render(Renderer renderer) {
/* 41 */     long now = System.currentTimeMillis();
/* 42 */     if (this.inited) {
/* 43 */       long timeDelta = now - this.lastRenderAt;
/* 44 */       if (timeDelta <= 0L) {
/* 45 */         timeDelta = 1L;
/*    */       }
/* 47 */       this.currentX = interpolate(this.currentX, this.targetX, timeDelta, 7.0F);
/* 48 */       this.currentY = interpolate(this.currentY, this.targetY, timeDelta, 7.0F);
/*    */     } else {
/* 50 */       this.currentX = this.targetX;
/* 51 */       this.currentY = this.targetY;
/* 52 */       this.inited = true;
/*    */     } 
/* 54 */     super.setXOffset((int)this.currentX);
/* 55 */     super.setYOffset((int)this.currentY);
/* 56 */     this.lastRenderAt = now;
/*    */     
/* 58 */     super.render(renderer);
/*    */   }
/*    */   
/*    */   public static float interpolate(float from, float to, long millisDelta, float speed) {
/* 62 */     float directSetLimit = 0.01F;
/* 63 */     float millisPerSecond = 1000.0F;
/*    */     
/* 65 */     float diff = to - from;
/*    */     
/* 67 */     if (Math.abs(diff) < 0.01F) {
/* 68 */       return to;
/*    */     }
/* 70 */     float toAdd = diff * speed * (float)millisDelta / 1000.0F;
/*    */     
/* 72 */     if (Math.abs(toAdd) > Math.abs(diff)) {
/* 73 */       toAdd = diff;
/*    */     }
/*    */     
/* 76 */     return from + toAdd;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\BSmoothPanningPane.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */