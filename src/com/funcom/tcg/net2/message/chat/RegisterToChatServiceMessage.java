/*    */ package com.funcom.tcg.net2.message.chat;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegisterToChatServiceMessage
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 101;
/*    */   private int userId;
/*    */   private String userNick;
/*    */   
/*    */   public RegisterToChatServiceMessage() {}
/*    */   
/*    */   public RegisterToChatServiceMessage(int userId, String userNick) {
/* 25 */     this.userId = userId;
/* 26 */     this.userNick = userNick;
/*    */   }
/*    */   
/*    */   public RegisterToChatServiceMessage(ByteBuffer buffer) {
/* 30 */     this(buffer.getInt(), MessageUtils.readStr(buffer));
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 34 */     return new RegisterToChatServiceMessage(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 101;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 42 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(getUserNick());
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 47 */     buffer.putInt(getUserId());
/* 48 */     MessageUtils.writeStr(buffer, getUserNick());
/* 49 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getUserId() {
/* 53 */     return this.userId;
/*    */   }
/*    */   
/*    */   public String getUserNick() {
/* 57 */     return this.userNick;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 61 */     if (this == o) return true; 
/* 62 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 64 */     RegisterToChatServiceMessage that = (RegisterToChatServiceMessage)o;
/*    */     
/* 66 */     if (this.userId != that.userId) return false; 
/* 67 */     if (!this.userNick.equals(that.userNick)) return false;
/*    */     
/* 69 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 74 */     int result = this.userId;
/* 75 */     result = 31 * result + this.userNick.hashCode();
/* 76 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\RegisterToChatServiceMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */