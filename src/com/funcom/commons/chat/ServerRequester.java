/*    */ package com.funcom.commons.chat;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net2.message.chat.JoinChannelRequest;
/*    */ import com.funcom.tcg.net2.message.chat.JoinChannelResponse;
/*    */ import com.funcom.tcg.net2.message.chat.LeaveChannelRequest;
/*    */ import com.funcom.tcg.net2.message.chat.LeaveChannelResponse;
/*    */ import com.funcom.tcg.net2.message.chat.ListChannelsRequest;
/*    */ import com.funcom.tcg.net2.message.chat.ListChannelsResponse;
/*    */ import java.util.Arrays;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class ServerRequester
/*    */ {
/*    */   private ChatCommunicator communicator;
/*    */   
/*    */   public ServerRequester(ChatCommunicator communicator) {
/* 20 */     this.communicator = communicator;
/*    */   }
/*    */   
/*    */   public void listChannels(int requesterId, final Listener listener) {
/* 24 */     this.communicator.addMessageListener(new ChatCommunicator.MessageListener(116) {
/*    */           public boolean messageReceived(Message message) {
/* 26 */             ChatChannel[] chatChannels = ((ListChannelsResponse)message).getChatChannels();
/* 27 */             listener.listChannelsResponse(new HashSet<ChatChannel>(Arrays.asList(chatChannels)));
/* 28 */             return false;
/*    */           }
/*    */         });
/* 31 */     this.communicator.sendMessage((Message)new ListChannelsRequest(requesterId));
/*    */   }
/*    */   
/*    */   public void joinChannel(int requesterId, String channelName, final Listener listener) {
/* 35 */     this.communicator.addMessageListener(new ChatCommunicator.MessageListener(112) {
/*    */           public boolean messageReceived(Message message) {
/* 37 */             ChatChannel chatChannel = ((JoinChannelResponse)message).getChatChannel();
/* 38 */             listener.joinChannelResponse(chatChannel);
/* 39 */             return false;
/*    */           }
/*    */         });
/* 42 */     this.communicator.sendMessage((Message)new JoinChannelRequest(requesterId, channelName));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void leaveChannel(int requesterId, String channelName, final Listener listener) {
/* 49 */     this.communicator.addMessageListener(new ChatCommunicator.MessageListener(114) {
/*    */           public boolean messageReceived(Message message) {
/* 51 */             int chatChannelId = ((LeaveChannelResponse)message).getChannelId();
/* 52 */             listener.leaveChannelResponse(chatChannelId);
/* 53 */             return false;
/*    */           }
/*    */         });
/* 56 */     this.communicator.sendMessage((Message)new LeaveChannelRequest(requesterId, channelName));
/*    */   }
/*    */   
/*    */   public static interface Listener {
/*    */     void listChannelsResponse(Set<ChatChannel> param1Set);
/*    */     
/*    */     void joinChannelResponse(ChatChannel param1ChatChannel);
/*    */     
/*    */     void leaveChannelResponse(int param1Int);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\ServerRequester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */