/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatServer;
/*    */ import com.funcom.commons.chat.ChatUser;
/*    */ import com.funcom.tcg.net2.message.chat.RegisterToChatServiceMessage;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ public class RegisterRequestHandler
/*    */   extends ServerRequestsChainedHandler
/*    */ {
/* 15 */   private static final Logger LOG = Logger.getLogger(RegisterRequestHandler.class.getName());
/*    */   
/*    */   public RegisterRequestHandler(ServerRequestsChainedHandler follower) {
/* 18 */     super(follower);
/*    */   }
/*    */   
/*    */   protected void youreTheOne(short messageType, SelectionKey selectionKey, ByteBuffer buffer, ChatServer server) {
/* 22 */     RegisterToChatServiceMessage message = new RegisterToChatServiceMessage(buffer);
/* 23 */     ChatUser chatUser = new ChatUser(message.getUserId(), message.getUserNick());
/* 24 */     server.registerUser(chatUser, selectionKey);
/* 25 */     server.log(Level.INFO, "New user registered: " + chatUser);
/*    */   }
/*    */   
/*    */   protected short getType() {
/* 29 */     return 101;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\RegisterRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */