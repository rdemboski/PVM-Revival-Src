/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.CameraShakeEffectDescription;
/*    */ import com.funcom.commons.utils.GlobalTime;
/*    */ 
/*    */ public class CameraShakeEffect {
/*    */   private CameraShakeEffectDescription cameraShakeEffectDescription;
/*    */   private long startTime;
/*    */   private float xOffset;
/*    */   private float yOffset;
/*    */   
/*    */   public CameraShakeEffect(CameraShakeEffectDescription cameraShakeEffectDescription) {
/* 13 */     this.cameraShakeEffectDescription = cameraShakeEffectDescription;
/* 14 */     this.startTime = GlobalTime.getInstance().getCurrentTime();
/*    */   }
/*    */   
/*    */   public CameraShakeEffectDescription getCameraShakeEffectDescription() {
/* 18 */     return this.cameraShakeEffectDescription;
/*    */   }
/*    */   
/*    */   public void update() {
/* 22 */     double dT = ((float)(GlobalTime.getInstance().getCurrentTime() - this.startTime) / 1000.0F);
/* 23 */     this.xOffset = (float)(Math.sin(6.283185307179586D * dT * this.cameraShakeEffectDescription.getSpeedX()) * this.cameraShakeEffectDescription.getAmplitudeX());
/* 24 */     this.yOffset = (float)(Math.sin(6.283185307179586D * dT * this.cameraShakeEffectDescription.getSpeedY()) * this.cameraShakeEffectDescription.getAmplitudeY());
/*    */   }
/*    */   
/*    */   public float getXOffset() {
/* 28 */     return this.xOffset;
/*    */   }
/*    */   
/*    */   public float getYOffset() {
/* 32 */     return this.yOffset;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CameraShakeEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */