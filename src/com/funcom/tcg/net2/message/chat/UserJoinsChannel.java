/*    */ package com.funcom.tcg.net2.message.chat;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatUser;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserJoinsChannel
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 103;
/*    */   private int channelId;
/*    */   private ChatUser joinedUser;
/*    */   
/*    */   public UserJoinsChannel() {}
/*    */   
/*    */   public UserJoinsChannel(int channelId, ChatUser joinedUser) {
/* 20 */     this.channelId = channelId;
/* 21 */     this.joinedUser = joinedUser;
/*    */   }
/*    */   
/*    */   public UserJoinsChannel(ByteBuffer buffer) {
/* 25 */     this(buffer.getInt(), new ChatUser(buffer.getInt(), MessageUtils.readStr(buffer)));
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 29 */     return new UserJoinsChannel(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 103;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 37 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeStr(getJoinedUser().getNick());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 43 */     buffer.putInt(getChannelId());
/* 44 */     buffer.putInt(getJoinedUser().getId());
/* 45 */     MessageUtils.writeStr(buffer, getJoinedUser().getNick());
/* 46 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getChannelId() {
/* 50 */     return this.channelId;
/*    */   }
/*    */   
/*    */   public ChatUser getJoinedUser() {
/* 54 */     return this.joinedUser;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 58 */     if (this == o) return true; 
/* 59 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 61 */     UserJoinsChannel that = (UserJoinsChannel)o;
/*    */     
/* 63 */     if (this.channelId != that.channelId) return false; 
/* 64 */     if (!this.joinedUser.equals(that.joinedUser)) return false;
/*    */     
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 71 */     int result = this.channelId;
/* 72 */     result = 31 * result + this.joinedUser.hashCode();
/* 73 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\UserJoinsChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */