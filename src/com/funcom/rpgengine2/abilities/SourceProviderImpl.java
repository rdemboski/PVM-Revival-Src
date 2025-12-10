/*    */ package com.funcom.rpgengine2.abilities;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ 
/*    */ 
/*    */ public class SourceProviderImpl
/*    */   implements SourceProvider
/*    */ {
/*    */   private RpgEntity sourceObject;
/*    */   private RpgEntity resultHandler;
/*    */   
/*    */   public SourceProviderImpl(RpgEntity sourceObject, RpgEntity resultHandler) {
/* 13 */     this.sourceObject = sourceObject;
/* 14 */     this.resultHandler = resultHandler;
/*    */   }
/*    */   
/*    */   public RpgEntity getSourceObject() {
/* 18 */     return this.sourceObject;
/*    */   }
/*    */   
/*    */   public RpgEntity getSourceHandler() {
/* 22 */     return this.resultHandler;
/*    */   }
/*    */   
/*    */   public void notifyHit(TargetProvider targetProvider) {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\SourceProviderImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */