/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.CameraShakeEffectDescription;
/*    */ import com.funcom.commons.dfx.Effect;
/*    */ import com.funcom.commons.dfx.EffectHandler;
/*    */ 
/*    */ public class CameraShakeEffectHandler implements EffectHandler {
/*    */   private RepresentationalNode source;
/*    */   
/*    */   public CameraShakeEffectHandler(RepresentationalNode source) {
/* 11 */     this.source = source;
/*    */   }
/*    */ 
/*    */   
/*    */   public void startEffect(Effect sourceEffect) {
/* 16 */     CameraShakeEffectDescription cameraShakeEffectDescription = (CameraShakeEffectDescription)sourceEffect.getDescription();
/* 17 */     if (!cameraShakeEffectDescription.isBroadcast() && !DfxTextWindowManager.instance().isUser(this.source))
/*    */       return; 
/* 19 */     if (cameraShakeEffectDescription.getRange() < DfxTextWindowManager.instance().distanceToUser(this.source))
/*    */       return; 
/* 21 */     CameraConfig.instance().startShakeEffect(new CameraShakeEffect(cameraShakeEffectDescription));
/*    */   }
/*    */ 
/*    */   
/*    */   public void endEffect(Effect sourceEffect) {
/* 26 */     CameraConfig.instance().endShakeEffect();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isFinished() {
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CameraShakeEffectHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */