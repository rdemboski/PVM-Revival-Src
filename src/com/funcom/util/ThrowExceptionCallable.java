/*    */ package com.funcom.util;
/*    */ 
/*    */ import java.util.concurrent.Callable;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class ThrowExceptionCallable
/*    */   implements Callable<Object>, Runnable
/*    */ {
/* 10 */   private static final Logger LOGGER = Logger.getLogger(ThrowExceptionCallable.class);
/*    */   private Exception ex;
/*    */   
/*    */   public ThrowExceptionCallable(Exception ex) {
/* 14 */     if (ex == null)
/* 15 */       throw new IllegalArgumentException("ex = null"); 
/* 16 */     this.ex = ex;
/*    */   }
/*    */   
/*    */   public void run() {
/* 20 */     if (this.ex instanceof RuntimeException) {
/* 21 */       throw (RuntimeException)this.ex;
/*    */     }
/* 23 */     throw new RuntimeException(this.ex);
/*    */   }
/*    */   
/*    */   public Object call() throws Exception {
/* 27 */     throw this.ex;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\ThrowExceptionCallable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */