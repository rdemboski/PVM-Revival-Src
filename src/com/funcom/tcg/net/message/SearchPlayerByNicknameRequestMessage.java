/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchPlayerByNicknameRequestMessage
/*    */   implements Message
/*    */ {
/*    */   private String friendNickname;
/*    */   
/*    */   public SearchPlayerByNicknameRequestMessage() {}
/*    */   
/*    */   public SearchPlayerByNicknameRequestMessage(String friendNickname) {
/* 17 */     this.friendNickname = friendNickname;
/*    */   }
/*    */   
/*    */   public SearchPlayerByNicknameRequestMessage(ByteBuffer buffer) {
/* 21 */     this.friendNickname = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 26 */     return 75;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 31 */     return new SearchPlayerByNicknameRequestMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 36 */     return MessageUtils.getSizeStr(this.friendNickname);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 41 */     MessageUtils.writeStr(buffer, this.friendNickname);
/* 42 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getFriendNickname() {
/* 46 */     return this.friendNickname;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SearchPlayerByNicknameRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */