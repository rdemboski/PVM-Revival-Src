/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class TcgChatRequestFriendMessage
/*    */   implements Message
/*    */ {
/*    */   private int requestedFriendId;
/*    */   
/*    */   public TcgChatRequestFriendMessage() {}
/*    */   
/*    */   public TcgChatRequestFriendMessage(int requestedFriendId) {
/* 15 */     this.requestedFriendId = requestedFriendId;
/*    */   }
/*    */   
/*    */   public TcgChatRequestFriendMessage(ByteBuffer buffer) {
/* 19 */     this.requestedFriendId = MessageUtils.readInt(buffer);
/*    */   }
/*    */   
/*    */   public int getRequestedFriendId() {
/* 23 */     return this.requestedFriendId;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 28 */     return 57;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 33 */     return new TcgChatRequestFriendMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 38 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     MessageUtils.writeInt(buffer, this.requestedFriendId);
/* 44 */     return buffer;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TcgChatRequestFriendMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */