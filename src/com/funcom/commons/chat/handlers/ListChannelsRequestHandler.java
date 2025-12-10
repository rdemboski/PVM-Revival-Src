/*    */ package com.funcom.commons.chat.handlers;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.commons.chat.ChatServer;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.ListChannelsResponse;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.nio.channels.SelectionKey;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Level;
/*    */ 
/*    */ public class ListChannelsRequestHandler
/*    */   extends ServerRequestsChainedHandler
/*    */ {
/*    */   public ListChannelsRequestHandler(ServerRequestsChainedHandler follower) {
/* 16 */     super(follower);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void youreTheOne(short messageType, SelectionKey selectionKey, ByteBuffer buffer, ChatServer server) {
/* 21 */     Set<ChatChannel> channels = server.channels();
/* 22 */     server.sendMessage(selectionKey, (Message)new ListChannelsResponse(channels.<ChatChannel>toArray(new ChatChannel[channels.size()])));
/* 23 */     server.log(Level.INFO, "Sending channel list to: " + server.userByKey(selectionKey));
/*    */   }
/*    */   
/*    */   protected short getType() {
/* 27 */     return 115;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\handlers\ListChannelsRequestHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */