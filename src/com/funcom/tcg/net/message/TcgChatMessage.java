/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class TcgChatMessage
/*    */   implements Message
/*    */ {
/*    */   private long clientChatId;
/*    */   private String message;
/*    */   
/*    */   public TcgChatMessage() {}
/*    */   
/*    */   public TcgChatMessage(long clientChatId, String message) {
/* 16 */     this.clientChatId = clientChatId;
/* 17 */     this.message = message;
/*    */   }
/*    */   
/*    */   public TcgChatMessage(ByteBuffer buffer) {
/* 21 */     this.clientChatId = MessageUtils.readLong(buffer);
/* 22 */     this.message = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 27 */     return 56;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 32 */     return new TcgChatMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 37 */     return MessageUtils.getSizeLong() + MessageUtils.getSizeStr(this.message);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 42 */     MessageUtils.writeLong(buffer, this.clientChatId);
/* 43 */     MessageUtils.writeStr(buffer, this.message);
/* 44 */     return buffer;
/*    */   }
/*    */   
/*    */   public long getClientChatId() {
/* 48 */     return this.clientChatId;
/*    */   }
/*    */   
/*    */   public String getMessage() {
/* 52 */     return this.message;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TcgChatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */