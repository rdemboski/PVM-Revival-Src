/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TimedCommand
/*    */   extends Command
/*    */ {
/*    */   protected boolean executed;
/*    */   protected float cummulativeUpdateTime;
/*    */   protected float initialDelay;
/*    */   protected float endDelay;
/*    */   
/*    */   protected TimedCommand(float initialDelay, float endDelay) {
/* 19 */     this.initialDelay = initialDelay;
/* 20 */     this.endDelay = endDelay;
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 24 */     return (this.cummulativeUpdateTime >= this.initialDelay + this.endDelay);
/*    */   }
/*    */   
/*    */   protected abstract void execute();
/*    */   
/*    */   public void update(float time) {
/* 30 */     this.cummulativeUpdateTime += time;
/*    */     
/* 32 */     if (!this.executed && this.cummulativeUpdateTime >= this.initialDelay) {
/*    */       
/* 34 */       execute();
/* 35 */       this.executed = true;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\TimedCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */