/*    */ package com.funcom.tcg.net2.message.chat;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ChatMessage
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 102;
/*    */   private int userId;
/*    */   private int channelId;
/*    */   private String data;
/*    */   
/*    */   public ChatMessage() {}
/*    */   
/*    */   public ChatMessage(int userId, int channelId, String data) {
/* 20 */     this.userId = userId;
/* 21 */     this.channelId = channelId;
/* 22 */     this.data = data;
/*    */   }
/*    */   
/*    */   public ChatMessage(ByteBuffer buffer) {
/* 26 */     this(buffer.getInt(), buffer.getInt(), MessageUtils.readStr(buffer));
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 30 */     return new ChatMessage(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 34 */     return 102;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 38 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeStr(getData());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 44 */     buffer.putInt(getUserId());
/* 45 */     buffer.putInt(getChannelId());
/* 46 */     MessageUtils.writeStr(buffer, getData());
/* 47 */     return buffer;
/*    */   }
/*    */   
/*    */   public int getUserId() {
/* 51 */     return this.userId;
/*    */   }
/*    */   
/*    */   public int getChannelId() {
/* 55 */     return this.channelId;
/*    */   }
/*    */   
/*    */   public String getData() {
/* 59 */     return this.data;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 63 */     if (this == o) return true; 
/* 64 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 66 */     ChatMessage that = (ChatMessage)o;
/*    */     
/* 68 */     if (this.channelId != that.channelId) return false; 
/* 69 */     if (this.userId != that.userId) return false; 
/* 70 */     if (!this.data.equals(that.data)) return false;
/*    */     
/* 72 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 77 */     int result = this.userId;
/* 78 */     result = 31 * result + this.channelId;
/* 79 */     result = 31 * result + this.data.hashCode();
/* 80 */     return result;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static ChatMessage systemMessage(int channelId, String message) {
/* 91 */     return new ChatMessage(-1, channelId, message);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\ChatMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */