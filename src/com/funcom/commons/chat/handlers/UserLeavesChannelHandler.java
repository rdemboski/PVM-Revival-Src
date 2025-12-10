/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChannelDemultiplexer;
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.commons.chat.ChatCommunicator;
/*    */ import com.funcom.commons.chat.ChatUser;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.ChatMessage;
/*    */ import com.funcom.tcg.net2.message.chat.UserLeavesChannel;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ public class UserLeavesChannelHandler
/*    */   extends ChatCommunicator.MessageListener {
/* 16 */   private static final Logger LOG = Logger.getLogger(UserLeavesChannelHandler.class.getName());
/*    */   private ChannelDemultiplexer demultiplexer;
/*    */   
/*    */   public UserLeavesChannelHandler(ChannelDemultiplexer demultiplexer) {
/* 20 */     super(104);
/* 21 */     this.demultiplexer = demultiplexer;
/*    */   }
/*    */   
/*    */   public boolean messageReceived(Message message) {
/* 25 */     UserLeavesChannel ulc = (UserLeavesChannel)message;
/* 26 */     int leavingUserId = ulc.getLeavingUserId();
/*    */     
/* 28 */     ChatChannel chatChannel = this.demultiplexer.getById(ulc.getChannelId());
/* 29 */     if (chatChannel == null) {
/* 30 */       LOG.log((Priority)Level.ERROR, "Leaving information about nonregistered channel: " + ulc.getChannelId());
/* 31 */       return true;
/*    */     } 
/*    */     
/* 34 */     ChatUser leavingUser = chatChannel.getUserForId(leavingUserId);
/* 35 */     if (leavingUser == null) {
/* 36 */       LOG.log((Priority)Level.ERROR, "Leaving information about nonregistered user: " + ulc.getChannelId() + "/" + ulc.getLeavingUserId());
/* 37 */       return true;
/*    */     } 
/*    */     
/* 40 */     this.demultiplexer.getById(chatChannel.getId()).deregisterUser(leavingUser);
/* 41 */     this.demultiplexer.pushMessage(ChatMessage.systemMessage(chatChannel.getId(), "User " + leavingUser.getNick() + " left channel."));
/* 42 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\UserLeavesChannelHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */