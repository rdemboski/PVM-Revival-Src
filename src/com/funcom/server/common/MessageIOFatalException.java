/*    */ package com.funcom.server.common;
/*    */ 
/*    */ 
/*    */ public class MessageIOFatalException
/*    */   extends RuntimeException
/*    */ {
/*    */   public MessageIOFatalException(String message) {
/*  8 */     super(message);
/*    */   }
/*    */   
/*    */   public MessageIOFatalException(String message, Throwable cause) {
/* 12 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public MessageIOFatalException(Throwable cause) {
/* 16 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\MessageIOFatalException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */