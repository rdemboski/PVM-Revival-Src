/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ public class CameraShakeEffectDescription extends AbstractEffectDescription {
/*    */   private double speedX;
/*    */   private double amplitudeX;
/*    */   private double speedY;
/*    */   private double amplitudeY;
/*    */   private boolean broadcast;
/*    */   private double range;
/*    */   
/*    */   public double getAmplitudeX() {
/* 12 */     return this.amplitudeX;
/*    */   }
/*    */   public double getAmplitudeY() {
/* 15 */     return this.amplitudeY;
/*    */   }
/*    */   
/*    */   public void setAmplitudeX(double amplitude) {
/* 19 */     this.amplitudeX = amplitude;
/*    */   }
/*    */   public void setAmplitudeY(double amplitude) {
/* 22 */     this.amplitudeY = amplitude;
/*    */   }
/*    */   
/*    */   public double getSpeedX() {
/* 26 */     return this.speedX;
/*    */   }
/*    */   public double getSpeedY() {
/* 29 */     return this.speedY;
/*    */   }
/*    */   
/*    */   public boolean isBroadcast() {
/* 33 */     return this.broadcast;
/*    */   }
/*    */   
/*    */   public void setBroadcast(boolean broadcast) {
/* 37 */     this.broadcast = broadcast;
/*    */   }
/*    */   
/*    */   public double getRange() {
/* 41 */     return this.range;
/*    */   }
/*    */   
/*    */   public void setRange(double range) {
/* 45 */     this.range = range;
/*    */   }
/*    */   
/*    */   public void setCyclesX(double cycles) {
/* 49 */     this.speedX = cycles / (this.endTime - this.startTime);
/*    */   }
/*    */   
/*    */   public void setCyclesY(double cycles) {
/* 53 */     this.speedY = cycles / (this.endTime - this.startTime);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\CameraShakeEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */