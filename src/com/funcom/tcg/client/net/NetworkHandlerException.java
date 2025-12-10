/*    */ package com.funcom.tcg.client.net;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NetworkHandlerException
/*    */   extends RuntimeException
/*    */ {
/*    */   public NetworkHandlerException(String message, Throwable cause) {
/* 15 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public static NetworkHandlerException ioHandlerInitializationError(Exception cause) {
/* 19 */     return new NetworkHandlerException("Failed to initialize ioHandler!", cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\NetworkHandlerException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */