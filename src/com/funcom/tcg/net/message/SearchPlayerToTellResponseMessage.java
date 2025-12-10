/*    */ package com.funcom.tcg.net.message;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchPlayerToTellResponseMessage
/*    */   implements Message
/*    */ {
/*    */   private String friendNickname;
/*    */   private Integer friendId;
/*    */   private String message;
/*    */   private String name;
/*    */   
/*    */   public SearchPlayerToTellResponseMessage() {}
/*    */   
/*    */   public SearchPlayerToTellResponseMessage(String friendNickname, Integer friendId, String message, String name) {
/* 20 */     this.friendNickname = friendNickname;
/* 21 */     this.friendId = friendId;
/* 22 */     this.message = message;
/* 23 */     this.name = name;
/*    */   }
/*    */   
/*    */   public SearchPlayerToTellResponseMessage(ByteBuffer buffer) {
/* 27 */     this.friendNickname = MessageUtils.readStr(buffer);
/* 28 */     this.friendId = Integer.valueOf(MessageUtils.readInt(buffer));
/* 29 */     this.message = MessageUtils.readStr(buffer);
/* 30 */     this.name = MessageUtils.readStr(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 35 */     return 78;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 40 */     return new SearchPlayerToTellResponseMessage(buffer);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getSerializedSize() {
/* 45 */     return MessageUtils.getSizeStr(this.friendNickname) + MessageUtils.getSizeInt() + MessageUtils.getSizeStr(this.message) + MessageUtils.getSizeStr(this.name);
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 50 */     MessageUtils.writeStr(buffer, this.friendNickname);
/* 51 */     MessageUtils.writeInt(buffer, this.friendId.intValue());
/* 52 */     MessageUtils.writeStr(buffer, this.message);
/* 53 */     MessageUtils.writeStr(buffer, this.name);
/* 54 */     return buffer;
/*    */   }
/*    */   
/*    */   public String getFriendNickname() {
/* 58 */     return this.friendNickname;
/*    */   }
/*    */   
/*    */   public Integer getFriendId() {
/* 62 */     return this.friendId;
/*    */   }
/*    */   
/*    */   public String getMessageToTell() {
/* 66 */     return this.message;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 70 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\message\SearchPlayerToTellResponseMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */