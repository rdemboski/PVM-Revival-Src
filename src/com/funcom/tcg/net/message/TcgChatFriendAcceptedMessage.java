/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class TcgChatFriendAcceptedMessage
/*    */   implements Message {
/*    */   private int friendId;
/*    */   
/*    */   public TcgChatFriendAcceptedMessage(int friendId) {
/* 12 */     this.friendId = friendId;
/*    */   }
/*    */ 
/*    */   
/*    */   public TcgChatFriendAcceptedMessage() {}
/*    */   
/*    */   public TcgChatFriendAcceptedMessage(ByteBuffer buffer) {
/* 19 */     this.friendId = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 24 */     return 60;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 29 */     return new TcgChatFriendAcceptedMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 34 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 39 */     MessageUtils.writeInt(buffer, this.friendId);
/* 40 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getFriendId() {
/* 44 */     return this.friendId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TcgChatFriendAcceptedMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */