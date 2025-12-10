/*    */ package com.funcom.commons;
/*    */ 
/*    */ import com.jme.util.GameTaskQueueManager;
/*    */ import java.util.concurrent.Executors;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class JMEQueueInvoker
/*    */   implements ThreadInvoker
/*    */ {
/* 11 */   private static final Logger LOGGER = Logger.getLogger(JMEQueueInvoker.class);
/*    */   
/*    */   public void invoke(Runnable runnable) {
/* 14 */     GameTaskQueueManager.getManager().update(Executors.callable(runnable));
/* 15 */     LOGGER.debug("Task placed in update queue");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\JMEQueueInvoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */