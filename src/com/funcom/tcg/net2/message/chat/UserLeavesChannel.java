/*    */ package com.funcom.tcg.net2.message.chat;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserLeavesChannel
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 104;
/*    */   private int channelId;
/*    */   private int leavingUserId;
/*    */   
/*    */   public UserLeavesChannel() {}
/*    */   
/*    */   public UserLeavesChannel(int channelId, int leavingUserId) {
/* 19 */     this.channelId = channelId;
/* 20 */     this.leavingUserId = leavingUserId;
/*    */   }
/*    */   
/*    */   public UserLeavesChannel(ByteBuffer buffer) {
/* 24 */     this(buffer.getInt(), buffer.getInt());
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 28 */     return new UserLeavesChannel(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 104;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 36 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 41 */     buffer.putInt(getChannelId());
/* 42 */     buffer.putInt(getLeavingUserId());
/* 43 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getChannelId() {
/* 47 */     return this.channelId;
/*    */   }
/*    */   
/*    */   public int getLeavingUserId() {
/* 51 */     return this.leavingUserId;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 55 */     if (this == o) return true; 
/* 56 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 58 */     UserLeavesChannel that = (UserLeavesChannel)o;
/*    */     
/* 60 */     if (this.channelId != that.channelId) return false; 
/* 61 */     if (this.leavingUserId != that.leavingUserId) return false;
/*    */     
/* 63 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 68 */     int result = this.channelId;
/* 69 */     result = 31 * result + this.leavingUserId;
/* 70 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\UserLeavesChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */