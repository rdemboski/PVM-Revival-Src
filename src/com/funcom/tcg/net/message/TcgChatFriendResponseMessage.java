/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ public class TcgChatFriendResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private int playerId;
/*    */   
/*    */   public TcgChatFriendResponseMessage() {}
/*    */   
/*    */   public TcgChatFriendResponseMessage(int playerId) {
/* 15 */     this.playerId = playerId;
/*    */   }
/*    */   
/*    */   public TcgChatFriendResponseMessage(ByteBuffer buffer) {
/* 19 */     this.playerId = MessageUtils.readInt(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 24 */     return 59;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 29 */     return new TcgChatFriendResponseMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 34 */     return MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 39 */     MessageUtils.writeInt(buffer, this.playerId);
/* 40 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getPlayerId() {
/* 44 */     return this.playerId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\TcgChatFriendResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */