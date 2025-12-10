/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.commons.chat.ChatServer;
/*    */ import com.funcom.tcg.net2.message.chat.ChatMessage;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import java.text.MessageFormat;
/*    */ import org.apache.log4j.Level;
/*    */ 
/*    */ 
/*    */ public class ServerChatMessageHandler
/*    */   extends ServerRequestsChainedHandler
/*    */ {
/*    */   public ServerChatMessageHandler(ServerRequestsChainedHandler follower) {
/* 16 */     super(follower);
/*    */   }
/*    */   
/*    */   protected void youreTheOne(short messageType, SelectionKey selectionKey, ByteBuffer buffer, ChatServer server) {
/* 20 */     ChatMessage chatMessage = new ChatMessage(buffer);
/* 21 */     ChatChannel channel = server.channelById(chatMessage.getChannelId());
/* 22 */     if (channel == null) {
/* 23 */       server.log(Level.WARN, "Nonexistent channel!");
/*    */     } else {
/* 25 */       server.log(Level.DEBUG, MessageFormat.format("[{0}][{1}]: {2}", new Object[] { server.userById(chatMessage.getUserId()).getNick(), channel.getName(), chatMessage.getData() }));
/* 26 */       server.chat(chatMessage);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected short getType() {
/* 31 */     return 102;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\ServerChatMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */