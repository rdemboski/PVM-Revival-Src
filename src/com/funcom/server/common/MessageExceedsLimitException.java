/*    */ package com.funcom.server.common;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageExceedsLimitException
/*    */   extends IOException
/*    */ {
/*    */   public MessageExceedsLimitException(String message) {
/* 13 */     super(message);
/*    */   }
/*    */   
/*    */   public MessageExceedsLimitException(String message, Throwable cause) {
/* 17 */     super(message, cause);
/*    */   }
/*    */   
/*    */   public MessageExceedsLimitException(Throwable cause) {
/* 21 */     super(cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\MessageExceedsLimitException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */