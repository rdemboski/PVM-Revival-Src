/*    */ package com.funcom.server.common;
/*    */ 
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class UnknownMessageTypeException extends RuntimeException {
/*    */   private short msgID;
/*    */   private ByteBuffer buffer;
/*    */   
/*    */   public UnknownMessageTypeException(short msgID, ByteBuffer buffer) {
/* 10 */     super("Unknown message type, missing code in message factory or hack attempt: msgID=" + msgID + " buffer.limit=" + buffer.limit());
/* 11 */     this.msgID = msgID;
/* 12 */     this.buffer = buffer;
/*    */   }
/*    */   
/*    */   public short getMsgID() {
/* 16 */     return this.msgID;
/*    */   }
/*    */   
/*    */   public ByteBuffer getBuffer() {
/* 20 */     return this.buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\server\common\UnknownMessageTypeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */