/*    */ package com.funcom.commons.jme;
/*    */ 
/*    */ import com.funcom.commons.utils.TimeSystem;
/*    */ import com.jme.util.Timer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class JMETimerSystem
/*    */   implements TimeSystem
/*    */ {
/* 11 */   private Timer timer = Timer.getTimer();
/*    */   
/*    */   private long time;
/*    */   
/*    */   public long getCurrentTime() {
/* 16 */     return this.time;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getRealTime() {
/* 21 */     return this.timer.getTime() / this.timer.getResolution() / 1000L;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 26 */     this.timer.update();
/* 27 */     this.time = this.timer.getTime() / this.timer.getResolution() / 1000L;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\JMETimerSystem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */