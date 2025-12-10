/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AnimationEffectDescription
/*    */   extends AbstractEffectDescription
/*    */ {
/*    */   private boolean looped;
/*    */   private float playSpeed;
/*    */   
/*    */   public void setPlaySpeed(float playSpeed) {
/* 13 */     this.playSpeed = playSpeed;
/*    */   }
/*    */   
/*    */   public void setLooped(boolean looped) {
/* 17 */     this.looped = looped;
/*    */   }
/*    */   
/*    */   public boolean isLooped() {
/* 21 */     return this.looped;
/*    */   }
/*    */   
/*    */   public float getPlaySpeed() {
/* 25 */     return this.playSpeed;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\AnimationEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */