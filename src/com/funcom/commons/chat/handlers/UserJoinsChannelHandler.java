/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChannelDemultiplexer;
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.commons.chat.ChatCommunicator;
/*    */ import com.funcom.commons.chat.ChatUser;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.ChatMessage;
/*    */ import com.funcom.tcg.net2.message.chat.UserJoinsChannel;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ public class UserJoinsChannelHandler
/*    */   extends ChatCommunicator.MessageListener {
/* 16 */   private static final Logger LOG = Logger.getLogger(UserJoinsChannelHandler.class.getName());
/*    */   private ChannelDemultiplexer demultiplexer;
/*    */   
/*    */   public UserJoinsChannelHandler(ChannelDemultiplexer demultiplexer) {
/* 20 */     super(103);
/* 21 */     this.demultiplexer = demultiplexer;
/*    */   }
/*    */   
/*    */   public boolean messageReceived(Message message) {
/* 25 */     UserJoinsChannel ujc = (UserJoinsChannel)message;
/* 26 */     ChatUser joiningUser = ujc.getJoinedUser();
/*    */     
/* 28 */     ChatChannel chatChannel = this.demultiplexer.getById(ujc.getChannelId());
/* 29 */     if (chatChannel == null) {
/* 30 */       LOG.log((Priority)Level.FATAL, "Join information about nonregistered channel: " + ujc.getChannelId());
/* 31 */       return true;
/*    */     } 
/*    */     
/* 34 */     this.demultiplexer.getById(chatChannel.getId()).registerUser(joiningUser);
/* 35 */     this.demultiplexer.pushMessage(ChatMessage.systemMessage(chatChannel.getId(), "User " + joiningUser.getNick() + " joins channel"));
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\UserJoinsChannelHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */