/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RpgKeepAliveEffectDescription
/*    */   implements EffectDescription
/*    */ {
/*    */   private double endTime;
/*    */   private DireEffectResourceLoader resourceFetcher;
/*    */   
/*    */   public RpgKeepAliveEffectDescription(double endTime) {
/* 13 */     this.endTime = endTime;
/*    */   }
/*    */   
/*    */   public double getStartTime() {
/* 17 */     return 0.0D;
/*    */   }
/*    */   
/*    */   public double getEndTime() {
/* 21 */     return this.endTime;
/*    */   }
/*    */   
/*    */   public String getResource() {
/* 25 */     return "";
/*    */   }
/*    */   
/*    */   public Effect createInstance(Object instanceSource, Object instanceData) {
/* 29 */     return new Effect(this, EmptyHandler.INSTANCE);
/*    */   }
/*    */   
/*    */   public void setResourceFetcher(DireEffectResourceLoader resourceFetcher) {
/* 33 */     this.resourceFetcher = resourceFetcher;
/*    */   }
/*    */   
/*    */   public DireEffectResourceLoader getResourceFetcher() {
/* 37 */     return this.resourceFetcher;
/*    */   }
/*    */   
/*    */   public void merge(RpgKeepAliveEffectDescription other) {
/* 41 */     this.endTime = Math.max(this.endTime, other.endTime);
/*    */   }
/*    */   
/*    */   private static class EmptyHandler implements EffectHandler {
/* 45 */     static final EffectHandler INSTANCE = new EmptyHandler();
/*    */ 
/*    */ 
/*    */     
/*    */     public void startEffect(Effect sourceEffect) {}
/*    */ 
/*    */ 
/*    */     
/*    */     public void endEffect(Effect sourceEffect) {}
/*    */ 
/*    */     
/*    */     public boolean isFinished() {
/* 57 */       return false;
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\RpgKeepAliveEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */