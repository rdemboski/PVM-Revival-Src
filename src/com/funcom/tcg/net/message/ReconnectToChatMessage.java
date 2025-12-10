/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class ReconnectToChatMessage
/*    */   implements Message
/*    */ {
/*    */   private long chatClientId;
/*    */   private long chatClientCookie;
/*    */   
/*    */   public ReconnectToChatMessage() {}
/*    */   
/*    */   public ReconnectToChatMessage(long chatClientId, long chatClientCookie) {
/* 16 */     this.chatClientId = chatClientId;
/* 17 */     this.chatClientCookie = chatClientCookie;
/*    */   }
/*    */   
/*    */   public ReconnectToChatMessage(ByteBuffer buffer) {
/* 21 */     this.chatClientId = MessageUtils.readLong(buffer);
/* 22 */     this.chatClientCookie = MessageUtils.readLong(buffer);
/*    */   }
/*    */   
/*    */   public long getChatClientId() {
/* 26 */     return this.chatClientId;
/*    */   }
/*    */   
/*    */   public long getChatClientCookie() {
/* 30 */     return this.chatClientCookie;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 35 */     return 55;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 40 */     return new ReconnectToChatMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 45 */     return MessageUtils.getSizeLong() + MessageUtils.getSizeLong();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 51 */     MessageUtils.writeLong(buffer, this.chatClientId);
/* 52 */     MessageUtils.writeLong(buffer, this.chatClientCookie);
/* 53 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\ReconnectToChatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */