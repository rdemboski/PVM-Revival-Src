/*    */ package com.funcom.tcg.net2.message.chat;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LeaveChannelResponse
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 114;
/*    */   private int userId;
/*    */   private int channelId;
/*    */   
/*    */   public LeaveChannelResponse() {}
/*    */   
/*    */   public LeaveChannelResponse(int userId, int channelId) {
/* 19 */     this.userId = userId;
/* 20 */     this.channelId = channelId;
/*    */   }
/*    */   
/*    */   public LeaveChannelResponse(ByteBuffer buffer) {
/* 24 */     this(buffer.getInt(), buffer.getInt());
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 28 */     return new LeaveChannelResponse(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 32 */     return 114;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 36 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt();
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 41 */     buffer.putInt(getUserId());
/* 42 */     buffer.putInt(getChannelId());
/* 43 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getUserId() {
/* 47 */     return this.userId;
/*    */   }
/*    */   
/*    */   public int getChannelId() {
/* 51 */     return this.channelId;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 55 */     if (this == o) return true; 
/* 56 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 58 */     LeaveChannelResponse that = (LeaveChannelResponse)o;
/*    */     
/* 60 */     if (this.channelId != that.channelId) return false; 
/* 61 */     if (this.userId != that.userId) return false;
/*    */     
/* 63 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 68 */     int result = this.userId;
/* 69 */     result = 31 * result + this.channelId;
/* 70 */     return result;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\LeaveChannelResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */