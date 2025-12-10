/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.commons.chat.ChatServer;
/*    */ import com.funcom.commons.chat.ChatUser;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.LeaveChannelRequest;
/*    */ import com.funcom.tcg.net2.message.chat.LeaveChannelResponse;
/*    */ import com.funcom.tcg.net2.message.chat.UserLeavesChannel;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import java.text.MessageFormat;
/*    */ import org.apache.log4j.Level;
/*    */ 
/*    */ public class LeaveChannelRequestHandler
/*    */   extends ServerRequestsChainedHandler
/*    */ {
/*    */   public LeaveChannelRequestHandler(ServerRequestsChainedHandler follower) {
/* 19 */     super(follower);
/*    */   }
/*    */   
/*    */   protected void youreTheOne(short messageType, SelectionKey selectionKey, ByteBuffer buffer, ChatServer server) {
/* 23 */     LeaveChannelRequest lcr = new LeaveChannelRequest(buffer);
/* 24 */     ChatChannel channel = server.channelByName(lcr.getChannelName());
/* 25 */     if (channel == null) {
/* 26 */       server.log(Level.WARN, "User tries to leave nonexistant channel: " + lcr.getChannelName());
/*    */       return;
/*    */     } 
/* 29 */     ChatUser chatUser = server.userByKey(selectionKey);
/* 30 */     if (chatUser == null) {
/* 31 */       server.log(Level.ERROR, "UNREGISTERED USER SENDS COMMAND: " + selectionKey);
/*    */       
/*    */       return;
/*    */     } 
/* 35 */     if (channel.isUserRegistered(chatUser)) {
/* 36 */       channel.deregisterUser(chatUser);
/* 37 */       server.sendMessage(chatUser, (Message)new LeaveChannelResponse(chatUser.getId(), channel.getId()));
/* 38 */       notifyOtherUsers(channel, chatUser, server);
/* 39 */       server.log(Level.INFO, chatUser.getNick() + " leaves " + channel.getName());
/*    */     } else {
/* 41 */       server.log(Level.WARN, MessageFormat.format("User tries to leave channel he''s not registered to: {0}/{1}", new Object[] { chatUser, channel }));
/*    */     } 
/*    */   }
/*    */   private void notifyOtherUsers(ChatChannel channel, ChatUser chatUser, ChatServer server) {
/* 45 */     for (ChatUser channelUser : channel) {
/* 46 */       if (channelUser.getId() != chatUser.getId())
/* 47 */         server.sendMessage(channelUser, (Message)new UserLeavesChannel(channel.getId(), chatUser.getId())); 
/*    */     } 
/*    */   }
/*    */   protected short getType() {
/* 51 */     return 113;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\LeaveChannelRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */