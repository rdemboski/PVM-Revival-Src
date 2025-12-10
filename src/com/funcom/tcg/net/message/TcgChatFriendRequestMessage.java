/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class TcgChatFriendRequestMessage
/*    */   implements Message {
/*    */   private int sourceId;
/*    */   private String clientName;
/*    */   
/*    */   public TcgChatFriendRequestMessage(int sourceId, String clientName) {
/* 13 */     this.sourceId = sourceId;
/* 14 */     this.clientName = clientName;
/*    */   }
/*    */ 
/*    */   
/*    */   public TcgChatFriendRequestMessage() {}
/*    */   
/*    */   public TcgChatFriendRequestMessage(ByteBuffer buffer) {
/* 21 */     this.sourceId = MessageUtils.readInt(buffer);
/* 22 */     this.clientName = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 27 */     return 58;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 32 */     return new TcgChatFriendRequestMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 37 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.clientName);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 42 */     MessageUtils.writeInt(buffer, this.sourceId);
/* 43 */     MessageUtils.writeStr(buffer, this.clientName);
/* 44 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getSourceId() {
/* 48 */     return this.sourceId;
/*    */   }
/*    */   
/*    */   public String getClientName() {
/* 52 */     return this.clientName;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TcgChatFriendRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */