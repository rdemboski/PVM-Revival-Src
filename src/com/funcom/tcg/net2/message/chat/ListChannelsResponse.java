/*    */ package com.funcom.tcg.net2.message.chat;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.server.common.MessageUtils;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ListChannelsResponse
/*    */   implements Message
/*    */ {
/*    */   private static final short TYPE_ID = 116;
/*    */   private ChatChannel[] chatChannels;
/*    */   
/*    */   public ListChannelsResponse() {}
/*    */   
/*    */   public ListChannelsResponse(ChatChannel[] chatChannels) {
/* 24 */     this.chatChannels = chatChannels;
/*    */   }
/*    */   
/*    */   public ListChannelsResponse(ByteBuffer buffer) {
/* 28 */     this.chatChannels = new ChatChannel[buffer.getInt()];
/* 29 */     for (int i = 0; i < this.chatChannels.length; i++)
/* 30 */       this.chatChannels[i] = new ChatChannel(buffer.getInt(), MessageUtils.readStr(buffer)); 
/*    */   }
/*    */   
/*    */   public Message toMessage(ByteBuffer buffer) {
/* 34 */     return new ListChannelsResponse(buffer);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 38 */     return 116;
/*    */   }
/*    */   
/*    */   public int getSerializedSize() {
/* 42 */     int chatChannelsDataSize = 0;
/* 43 */     for (ChatChannel chatChannel : getChatChannels()) {
/* 44 */       chatChannelsDataSize += MessageUtils.getSizeInt();
/* 45 */       chatChannelsDataSize += MessageUtils.getSizeStr(chatChannel.getName());
/*    */     } 
/*    */     
/* 48 */     return MessageUtils.getSizeInt() + chatChannelsDataSize;
/*    */   }
/*    */ 
/*    */   
/*    */   public ByteBuffer serialize(ByteBuffer buffer) {
/* 53 */     buffer.putInt((getChatChannels()).length);
/* 54 */     for (ChatChannel chatChannel : getChatChannels()) {
/* 55 */       buffer.putInt(chatChannel.getId());
/* 56 */       MessageUtils.writeStr(buffer, chatChannel.getName());
/*    */     } 
/* 58 */     return buffer;
/*    */   }
/*    */   
/*    */   public ChatChannel[] getChatChannels() {
/* 62 */     return this.chatChannels;
/*    */   }
/*    */   
/*    */   public boolean equals(Object o) {
/* 66 */     if (this == o) return true; 
/* 67 */     if (o == null || getClass() != o.getClass()) return false;
/*    */     
/* 69 */     ListChannelsResponse that = (ListChannelsResponse)o;
/*    */     
/* 71 */     if (!Arrays.equals((Object[])this.chatChannels, (Object[])that.chatChannels)) return false;
/*    */     
/* 73 */     return true;
/*    */   }
/*    */   
/*    */   public int hashCode() {
/* 77 */     return Arrays.hashCode((Object[])this.chatChannels);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\ListChannelsResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */