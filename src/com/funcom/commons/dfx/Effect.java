/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ 
/*    */ public class Effect
/*    */ {
/*    */   protected boolean started;
/*    */   protected boolean ended;
/*    */   private final EffectDescription description;
/*    */   private final EffectHandler handler;
/*    */   
/*    */   public Effect(EffectDescription description, EffectHandler handler) {
/* 12 */     this.description = description;
/* 13 */     this.handler = handler;
/*    */   }
/*    */   
/*    */   public boolean update(double time) {
/* 17 */     if (!this.started && time >= this.description.getStartTime()) {
/* 18 */       start();
/*    */     }
/* 20 */     if (!this.ended && time >= this.description.getEndTime()) {
/* 21 */       kill();
/*    */     }
/*    */     
/* 24 */     return isAlive();
/*    */   }
/*    */   
/*    */   public void kill() {
/* 28 */     if (!this.ended) {
/* 29 */       if (this.started) {
/* 30 */         this.handler.endEffect(this);
/*    */       }
/* 32 */       this.ended = true;
/*    */     } 
/*    */   }
/*    */   
/*    */   private void start() {
/* 37 */     this.handler.startEffect(this);
/* 38 */     this.started = true;
/*    */   }
/*    */   
/*    */   public EffectDescription getDescription() {
/* 42 */     return this.description;
/*    */   }
/*    */   
/*    */   public boolean isAlive() {
/* 46 */     boolean isHandlerFinished = (this.started && this.handler.isFinished());
/*    */     
/* 48 */     return (!this.ended && !isHandlerFinished);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\Effect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */