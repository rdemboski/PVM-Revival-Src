/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChannelDemultiplexer;
/*    */ import com.funcom.commons.chat.ChatCommunicator;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.ChatMessage;
/*    */ 
/*    */ public class ClientChatMessageHandler
/*    */   extends ChatCommunicator.MessageListener
/*    */ {
/*    */   private ChannelDemultiplexer demultiplexer;
/*    */   
/*    */   public ClientChatMessageHandler(ChannelDemultiplexer demultiplexer) {
/* 14 */     super(102);
/* 15 */     this.demultiplexer = demultiplexer;
/*    */   }
/*    */   
/*    */   public boolean messageReceived(Message message) {
/* 19 */     this.demultiplexer.pushMessage((ChatMessage)message);
/* 20 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\ClientChatMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */