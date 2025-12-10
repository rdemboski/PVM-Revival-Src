/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ import java.util.concurrent.ExecutionException;
/*    */ import java.util.concurrent.Future;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import java.util.concurrent.TimeoutException;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TFuture<T>
/*    */   implements Future<T>
/*    */ {
/*    */   private volatile boolean mDone = false;
/*    */   volatile boolean mCanceled = false;
/* 16 */   private volatile Callable<T> mTask = null;
/* 17 */   private volatile T mResult = null;
/*    */   
/*    */   public TFuture(Callable<T> task) {
/* 20 */     this.mTask = task;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean cancel(boolean mayInterruptIfRunning) {
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 30 */     return this.mCanceled;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDone() {
/* 35 */     return this.mDone;
/*    */   }
/*    */ 
/*    */   
/*    */   public T get() throws InterruptedException, ExecutionException {
/* 40 */     if (isDone()) {
/* 41 */       return this.mResult;
/*    */     }
/* 43 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   public T get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
/* 48 */     return null;
/*    */   }
/*    */   
/*    */   public void set(T result) {
/* 52 */     this.mResult = result;
/*    */   }
/*    */   
/*    */   public void run() throws Exception {
/* 56 */     this.mCanceled = true;
/* 57 */     set(this.mTask.call());
/* 58 */     this.mCanceled = false;
/* 59 */     this.mDone = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\TFuture.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */