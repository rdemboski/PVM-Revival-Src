/*     */ package com.funcom.tcg.net2.message.chat;
/*     */ 
/*     */ import com.funcom.commons.chat.ChatChannel;
/*     */ import com.funcom.commons.chat.ChatUser;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.server.common.MessageUtils;
/*     */ import java.nio.ByteBuffer;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class JoinChannelResponse
/*     */   implements Message
/*     */ {
/*     */   private static final short TYPE_ID = 112;
/*     */   private int userId;
/*     */   private ChatChannel chatChannel;
/*     */   
/*     */   public JoinChannelResponse() {}
/*     */   
/*     */   public JoinChannelResponse(int userId, ChatChannel chatChannel) {
/*  21 */     this.userId = userId;
/*  22 */     this.chatChannel = chatChannel;
/*     */   }
/*     */   
/*     */   public JoinChannelResponse(ByteBuffer buffer) {
/*  26 */     this.userId = buffer.getInt();
/*  27 */     this.chatChannel = createChannel(buffer);
/*  28 */     registerUsers(buffer);
/*     */   }
/*     */   
/*     */   public Message toMessage(ByteBuffer buffer) {
/*  32 */     return new JoinChannelResponse(buffer);
/*     */   }
/*     */   
/*     */   private void registerUsers(ByteBuffer buffer) {
/*  36 */     int userCount = buffer.getInt();
/*  37 */     for (int i = 0; i < userCount; i++) {
/*  38 */       int userId = buffer.getInt();
/*  39 */       String userNick = MessageUtils.readStr(buffer);
/*  40 */       ChatUser user = new ChatUser(userId, userNick);
/*  41 */       this.chatChannel.registerUser(user);
/*     */     } 
/*     */   }
/*     */   
/*     */   private ChatChannel createChannel(ByteBuffer buffer) {
/*  46 */     int channelId = buffer.getInt();
/*  47 */     String channelName = MessageUtils.readStr(buffer);
/*  48 */     return new ChatChannel(channelId, channelName);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/*  53 */     return 112;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSerializedSize() {
/*  58 */     int userDataSize = 0;
/*  59 */     for (ChatUser chatUser : getChatChannel()) {
/*  60 */       userDataSize += MessageUtils.getSizeInt();
/*  61 */       userDataSize += MessageUtils.getSizeStr(chatUser.getNick());
/*     */     } 
/*     */ 
/*     */     
/*  65 */     return MessageUtils.getSizeInt() + MessageUtils.getSizeInt() + MessageUtils.getSizeStr(getChatChannel().getName()) + MessageUtils.getSizeInt() + userDataSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ByteBuffer serialize(ByteBuffer buffer) {
/*  73 */     buffer.putInt(getUserId());
/*     */ 
/*     */     
/*  76 */     buffer.putInt(getChatChannel().getId());
/*  77 */     MessageUtils.writeStr(buffer, getChatChannel().getName());
/*     */ 
/*     */     
/*  80 */     buffer.putInt(getChatChannel().userCount());
/*  81 */     for (ChatUser chatUser : getChatChannel()) {
/*  82 */       buffer.putInt(chatUser.getId());
/*  83 */       MessageUtils.writeStr(buffer, chatUser.getNick());
/*     */     } 
/*  85 */     return buffer;
/*     */   }
/*     */   
/*     */   public int getUserId() {
/*  89 */     return this.userId;
/*     */   }
/*     */   
/*     */   public ChatChannel getChatChannel() {
/*  93 */     return this.chatChannel;
/*     */   }
/*     */   
/*     */   public boolean equals(Object o) {
/*  97 */     if (this == o) return true; 
/*  98 */     if (o == null || getClass() != o.getClass()) return false;
/*     */     
/* 100 */     JoinChannelResponse that = (JoinChannelResponse)o;
/*     */     
/* 102 */     if (this.userId != that.userId) return false; 
/* 103 */     if (!this.chatChannel.equals(that.chatChannel)) return false;
/*     */     
/* 105 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 110 */     int result = this.userId;
/* 111 */     result = 31 * result + this.chatChannel.hashCode();
/* 112 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\tcg\net2\message\chat\JoinChannelResponse.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */