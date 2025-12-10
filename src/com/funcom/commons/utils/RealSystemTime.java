/*    */ package com.funcom.commons.utils;
/*    */ 
/*    */ public class RealSystemTime
/*    */   implements TimeSystem, Runnable {
/*    */   private long currentTime;
/*    */   
/*    */   public long getCurrentTime() {
/*  8 */     return this.currentTime;
/*    */   }
/*    */   
/*    */   public long getRealTime() {
/* 12 */     return System.currentTimeMillis();
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 17 */     this.currentTime = System.currentTimeMillis();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\RealSystemTime.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */