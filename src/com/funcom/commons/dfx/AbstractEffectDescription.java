/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ public abstract class AbstractEffectDescription
/*    */   implements EffectDescription {
/*    */   protected double startTime;
/*    */   protected double endTime;
/*    */   protected String resource;
/*    */   protected EffectHandlerFactory handlerFactory;
/*    */   private DireEffectResourceLoader resourceFetcher;
/*    */   
/*    */   public void setStartTime(double startTime) {
/* 12 */     this.startTime = startTime;
/*    */   }
/*    */   
/*    */   public double getStartTime() {
/* 16 */     return this.startTime;
/*    */   }
/*    */   
/*    */   public void setEndTime(double endTime) {
/* 20 */     this.endTime = endTime;
/*    */   }
/*    */   
/*    */   public double getEndTime() {
/* 24 */     return this.endTime;
/*    */   }
/*    */   
/*    */   public void setResource(String resource) {
/* 28 */     this.resource = resource;
/*    */   }
/*    */   
/*    */   public String getResource() {
/* 32 */     return this.resource;
/*    */   }
/*    */   
/*    */   public EffectHandlerFactory getHandlerFactory() {
/* 36 */     return this.handlerFactory;
/*    */   }
/*    */   
/*    */   public void setHandlerFactory(EffectHandlerFactory handlerFactory) {
/* 40 */     this.handlerFactory = handlerFactory;
/*    */   }
/*    */   
/*    */   public Effect createInstance(Object instanceSource, Object instanceData) {
/* 44 */     EffectHandler handler = getHandlerFactory().createHandler(instanceSource, instanceData);
/*    */     
/* 46 */     return new Effect(this, handler);
/*    */   }
/*    */   
/*    */   public void setResourceFetcher(DireEffectResourceLoader resourceFetcher) {
/* 50 */     this.resourceFetcher = resourceFetcher;
/*    */   }
/*    */   
/*    */   public DireEffectResourceLoader getResourceFetcher() {
/* 54 */     return this.resourceFetcher;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\AbstractEffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */