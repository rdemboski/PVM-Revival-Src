/*    */ package com.funcom.tcg.net2.message.chat;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LeaveChannelRequest
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 113;
/*    */   private int userId;
/*    */   private String channelName;
/*    */   
/*    */   public LeaveChannelRequest() {}
/*    */   
/*    */   public LeaveChannelRequest(int userId, String channelId) {
/* 19 */     this.userId = userId;
/* 20 */     this.channelName = channelId;
/*    */   }
/*    */   
/*    */   public LeaveChannelRequest(ByteBuffer buffer) {
/* 24 */     this(buffer.getInt(), MessageUtils.readStr(buffer));
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 28 */     return new LeaveChannelRequest(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 113;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 36 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeStr(getChannelName());
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 41 */     buffer.putInt(getUserId());
/* 42 */     MessageUtils.writeStr(buffer, getChannelName());
/* 43 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getUserId() {
/* 47 */     return this.userId;
/*    */   }
/*    */   
/*    */   public String getChannelName() {
/* 51 */     return this.channelName;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 55 */     if (this == o) return true; 
/* 56 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 58 */     LeaveChannelRequest that = (LeaveChannelRequest)o;
/*    */     
/* 60 */     if (this.userId != that.userId) return false; 
/* 61 */     if (!this.channelName.equals(that.channelName)) return false;
/*    */     
/* 63 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 68 */     int result = this.userId;
/* 69 */     result = 31 * result + this.channelName.hashCode();
/* 70 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\LeaveChannelRequest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */