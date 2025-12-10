/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchPlayerToTellRequestMessage
/*    */   implements Message
/*    */ {
/*    */   private String friendNickname;
/*    */   private String message;
/*    */   private String name;
/*    */   
/*    */   public SearchPlayerToTellRequestMessage() {}
/*    */   
/*    */   public SearchPlayerToTellRequestMessage(String friendNickname, String message, String name) {
/* 19 */     this.friendNickname = friendNickname;
/* 20 */     this.message = message;
/* 21 */     this.name = name;
/*    */   }
/*    */   
/*    */   public SearchPlayerToTellRequestMessage(ByteBuffer buffer) {
/* 25 */     this.friendNickname = MessageUtils.readStr(buffer);
/* 26 */     this.message = MessageUtils.readStr(buffer);
/* 27 */     this.name = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 77;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 37 */     return new SearchPlayerToTellRequestMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 42 */     return MessageUtils.getSizeStr(this.friendNickname) + MessageUtils.getSizeStr(this.message) + MessageUtils.getSizeStr(this.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 47 */     MessageUtils.writeStr(buffer, this.friendNickname);
/* 48 */     MessageUtils.writeStr(buffer, this.message);
/* 49 */     MessageUtils.writeStr(buffer, this.name);
/* 50 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getFriendNickname() {
/* 54 */     return this.friendNickname;
/*    */   }
/*    */   
/*    */   public String getMessageToTell() {
/* 58 */     return this.message;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 62 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SearchPlayerToTellRequestMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */