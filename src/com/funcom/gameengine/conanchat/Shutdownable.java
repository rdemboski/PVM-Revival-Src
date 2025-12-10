/*    */ package com.funcom.gameengine.conanchat;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Shutdownable
/*    */ {
/*    */   private boolean running = true;
/*    */   
/*    */   public void shutdown() {
/* 14 */     cleanup();
/* 15 */     this.running = false;
/*    */   }
/*    */   
/*    */   public boolean isShutdown() {
/* 19 */     return !this.running;
/*    */   }
/*    */   
/*    */   protected void checkState() {
/* 23 */     if (isShutdown())
/* 24 */       throw new IllegalStateException("The object is shut down!"); 
/*    */   }
/*    */   
/*    */   protected void cleanup() {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\Shutdownable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */