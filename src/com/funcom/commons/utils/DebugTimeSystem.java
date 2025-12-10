/*    */ package com.funcom.commons.utils;
/*    */ 
/*    */ public class DebugTimeSystem
/*    */   implements TimeSystem {
/*    */   private long currentTime;
/*    */   
/*    */   public long getCurrentTime() {
/*  8 */     return this.currentTime;
/*    */   }
/*    */   
/*    */   public long getRealTime() {
/* 12 */     return this.currentTime;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {}
/*    */ 
/*    */   
/*    */   public void setMillis(long currentTime) {
/* 20 */     this.currentTime = currentTime;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\DebugTimeSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */