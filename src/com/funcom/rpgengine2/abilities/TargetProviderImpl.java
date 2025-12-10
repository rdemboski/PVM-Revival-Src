/*    */ package com.funcom.rpgengine2.abilities;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ 
/*    */ public class TargetProviderImpl
/*    */   implements TargetProvider {
/*    */   private RpgEntity resultListener;
/*    */   private RpgEntity targetObject;
/*    */   
/*    */   public TargetProviderImpl(RpgEntity targetObject, RpgEntity resultListener) {
/* 11 */     this.targetObject = targetObject;
/* 12 */     this.resultListener = resultListener;
/*    */   }
/*    */   
/*    */   public RpgEntity getTargetHandler() {
/* 16 */     return this.resultListener;
/*    */   }
/*    */   
/*    */   public RpgEntity getTargetObject() {
/* 20 */     return this.targetObject;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\TargetProviderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */