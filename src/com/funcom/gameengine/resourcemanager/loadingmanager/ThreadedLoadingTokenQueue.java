/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import com.jme.util.NanoTimer;
/*    */ import com.jme.util.Timer;
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ThreadedLoadingTokenQueue
/*    */   implements Runnable
/*    */ {
/* 17 */   private Timer timer = (Timer)new NanoTimer();
/* 18 */   private long ReservedTicks = 0L;
/*    */   
/*    */   class CallFuture<V>
/*    */   {
/*    */     Callable ToCall;
/*    */     public volatile boolean Loaded;
/*    */     public volatile V Out;
/*    */     
/* 26 */     public CallFuture(Callable ToCall) { this.ToCall = null;
/* 27 */       this.Loaded = false;
/* 28 */       this.Out = null;
/*    */       this.ToCall = ToCall;
/*    */       this.Loaded = false;
/* 31 */       this.Out = null; } } private final LinkedBlockingQueue<TFuture<?>> mWorkQueue = new LinkedBlockingQueue<TFuture<?>>();
/*    */   private volatile boolean mRunning = true;
/*    */   private volatile boolean mProcessing = false;
/*    */   
/*    */   public void stop() {
/* 36 */     this.mRunning = false;
/*    */   }
/*    */   
/*    */   public boolean isProcessing() {
/* 40 */     return this.mProcessing;
/*    */   }
/*    */   
/*    */   public synchronized <T> Future<T> submit(Callable<T> task) {
/* 44 */     TFuture<T> t = new TFuture<T>(task);
/* 45 */     this.mWorkQueue.add(t);
/* 46 */     return t;
/*    */   }
/*    */   
/*    */   public void setTargetTime(Double dSec) {
/* 50 */     if (dSec.doubleValue() >= 0.0D) {
/* 51 */       Double d = Double.valueOf(this.timer.getResolution() * dSec.doubleValue());
/* 52 */       this.ReservedTicks = d.longValue();
/*    */     } 
/*    */   }
/*    */   
/*    */   public long getReservedTicks() {
/* 57 */     return this.ReservedTicks;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 62 */     while (this.mRunning) {
/* 63 */       TFuture<?> f = null;
/* 64 */       long nStartTick = this.timer.getTime();
/* 65 */       long nEndTick = nStartTick + this.ReservedTicks;
/*    */       do {
/*    */         try {
/* 68 */           f = this.mWorkQueue.take();
/* 69 */           f.run();
/* 70 */           if (LoadingManager.DEBUG_INFO) {
/* 71 */             System.out.printf("ThreadedLoadingTokenQueue - Done running task (%d left).\n", new Object[] { Integer.valueOf(this.mWorkQueue.size()) });
/*    */           }
/*    */         }
/* 74 */         catch (Exception e) {
/* 75 */           if (f != null) {
/* 76 */             f.mCanceled = true;
/*    */           }
/* 78 */           LoadingManager.INSTANCE.sendCrash(e);
/*    */         } 
/* 80 */         f = null;
/* 81 */       } while (this.timer.getTime() < nEndTick);
/*    */       
/* 83 */       if (LoadingManager.INSTANCE.getAvailableProcessors() <= 1) {
/* 84 */         Thread.yield();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 90 */     return this.mWorkQueue.isEmpty();
/*    */   }
/*    */   
/*    */   public long size() {
/* 94 */     return this.mWorkQueue.size();
/*    */   }
/*    */   
/*    */   public void clear() {
/* 98 */     this.mWorkQueue.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\ThreadedLoadingTokenQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */