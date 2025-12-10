/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchPlayerByNicknameResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private String friendNickname;
/*    */   private Integer friendId;
/*    */   
/*    */   public SearchPlayerByNicknameResponseMessage() {}
/*    */   
/*    */   public SearchPlayerByNicknameResponseMessage(String friendNickname, Integer friendId) {
/* 18 */     this.friendNickname = friendNickname;
/* 19 */     this.friendId = friendId;
/*    */   }
/*    */   
/*    */   public SearchPlayerByNicknameResponseMessage(ByteBuffer buffer) {
/* 23 */     this.friendNickname = MessageUtils.readStr(buffer);
/* 24 */     this.friendId = Integer.valueOf(MessageUtils.readInt(buffer));
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 29 */     return 76;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 34 */     return new SearchPlayerByNicknameResponseMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 39 */     return MessageUtils.getSizeStr(this.friendNickname) + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 44 */     MessageUtils.writeStr(buffer, this.friendNickname);
/* 45 */     MessageUtils.writeInt(buffer, this.friendId.intValue());
/* 46 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getFriendNickname() {
/* 50 */     return this.friendNickname;
/*    */   }
/*    */   
/*    */   public Integer getFriendId() {
/* 54 */     return this.friendId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SearchPlayerByNicknameResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */