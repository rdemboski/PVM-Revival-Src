/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ public class TextEffectDescription
/*    */   extends AbstractEffectDescription {
/*    */   private float scale;
/*    */   private float r;
/*    */   private float g;
/*    */   private float b;
/*    */   private String window;
/*    */   private boolean broadcast;
/*    */   
/*    */   public void setScale(float scale) {
/* 13 */     this.scale = scale;
/*    */   }
/*    */   
/*    */   public void setColor(float r, float g, float b) {
/* 17 */     this.r = r;
/* 18 */     this.g = g;
/* 19 */     this.b = b;
/*    */   }
/*    */   
/*    */   public boolean isBroadcast() {
/* 23 */     return this.broadcast;
/*    */   }
/*    */   
/*    */   public void setBroadcast(boolean broadcast) {
/* 27 */     this.broadcast = broadcast;
/*    */   }
/*    */   
/*    */   public void setWindow(String window) {
/* 31 */     this.window = window;
/*    */   }
/*    */   
/*    */   public String getWindow() {
/* 35 */     return this.window;
/*    */   }
/*    */   
/*    */   public float getScale() {
/* 39 */     return this.scale;
/*    */   }
/*    */   
/*    */   public float getR() {
/* 43 */     return this.r;
/*    */   }
/*    */   
/*    */   public float getG() {
/* 47 */     return this.g;
/*    */   }
/*    */   
/*    */   public float getB() {
/* 51 */     return this.b;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\TextEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */