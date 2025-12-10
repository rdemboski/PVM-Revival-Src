/*    */ package com.funcom.commons;
/*    */ 
/*    */ import javax.swing.SwingUtilities;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class SwingInvoker
/*    */   implements ThreadInvoker
/*    */ {
/* 10 */   private static final Logger LOGGER = Logger.getLogger(SwingInvoker.class);
/*    */   
/*    */   public void invoke(Runnable runnable) {
/* 13 */     SwingUtilities.invokeLater(runnable);
/* 14 */     LOGGER.debug("Task placed on Swing queue");
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\SwingInvoker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */