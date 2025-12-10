/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.commons.chat.ChatServer;
/*    */ import com.funcom.commons.chat.ChatUser;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.JoinChannelRequest;
/*    */ import com.funcom.tcg.net2.message.chat.JoinChannelResponse;
/*    */ import com.funcom.tcg.net2.message.chat.UserJoinsChannel;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import java.nio.channels.SocketChannel;
/*    */ import java.text.MessageFormat;
/*    */ import org.apache.log4j.Level;
/*    */ 
/*    */ public class JoinChannelRequestHandler
/*    */   extends ServerRequestsChainedHandler
/*    */ {
/*    */   public JoinChannelRequestHandler(ServerRequestsChainedHandler follower) {
/* 20 */     super(follower);
/*    */   }
/*    */   
/*    */   protected void youreTheOne(short messageType, SelectionKey selectionKey, ByteBuffer buffer, ChatServer server) {
/* 24 */     JoinChannelRequest jcm = new JoinChannelRequest(buffer);
/* 25 */     ChatChannel channel = server.channelByName(jcm.getChannelName());
/* 26 */     if (channel == null) {
/* 27 */       server.log(Level.WARN, MessageFormat.format("Client tries to join inexistant channel: {0}/{1}", new Object[] { ((SocketChannel)selectionKey.channel()).socket().getInetAddress(), jcm.getChannelName() }));
/*    */       
/*    */       return;
/*    */     } 
/*    */     
/* 32 */     ChatUser chatUser = server.userByKey(selectionKey);
/* 33 */     if (chatUser == null) {
/* 34 */       server.log(Level.ERROR, "UNREGISTERED USER SENDS COMMAND: " + selectionKey);
/*    */       
/*    */       return;
/*    */     } 
/* 38 */     if (!channel.isUserRegistered(chatUser)) {
/* 39 */       channel.registerUser(chatUser);
/* 40 */       server.sendMessage(chatUser, (Message)new JoinChannelResponse(chatUser.getId(), channel));
/* 41 */       notifyOtherUsers(channel, chatUser, server);
/* 42 */       server.log(Level.INFO, chatUser.getNick() + " registers to " + channel.getName());
/*    */     } else {
/* 44 */       server.log(Level.WARN, MessageFormat.format("User already registered to channel: {0}/{1}", new Object[] { chatUser, channel }));
/*    */     } 
/*    */   }
/*    */   private void notifyOtherUsers(ChatChannel channel, ChatUser chatUser, ChatServer server) {
/* 48 */     for (ChatUser channelUser : channel) {
/* 49 */       if (channelUser.getId() != chatUser.getId())
/* 50 */         server.sendMessage(channelUser, (Message)new UserJoinsChannel(channel.getId(), chatUser)); 
/*    */     } 
/*    */   }
/*    */   protected short getType() {
/* 54 */     return 111;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\JoinChannelRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */