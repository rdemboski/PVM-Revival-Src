/*    */ package com.funcom.commons.chat;
/*    */ 
/*    */ import com.funcom.tcg.net2.message.chat.ChatMessage;
/*    */ import java.util.HashSet;
/*    */ import java.util.Iterator;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class ChannelDemultiplexer
/*    */   implements Iterable<ChatChannel>
/*    */ {
/* 14 */   private Set<ChatChannel> channels = new HashSet<ChatChannel>();
/* 15 */   private DemultiplexerChatMessageListener listener = new DemultiplexerChatMessageListener();
/*    */   private List<ChatMessageListener> listeners;
/*    */   
/*    */   public void addMessageListener(ChatMessageListener listener) {
/* 19 */     if (this.listeners == null)
/* 20 */       this.listeners = new LinkedList<ChatMessageListener>(); 
/* 21 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void removeMessageListener(ChatMessageListener listener) {
/* 25 */     if (this.listeners == null)
/*    */       return; 
/* 27 */     this.listeners.remove(listener);
/*    */   }
/*    */   
/*    */   private void fireMessageReceived(ChatChannel chatChannel, ChatMessage message) {
/* 31 */     if (this.listeners != null)
/* 32 */       for (ChatMessageListener listener : this.listeners)
/* 33 */         listener.messageReceived(chatChannel, message);  
/*    */   }
/*    */   
/*    */   public void registerChannel(ChatChannel chatChannel) {
/* 37 */     this.channels.add(chatChannel);
/* 38 */     chatChannel.addMessageListener(this.listener);
/*    */   }
/*    */   
/*    */   public void deregisterChannel(ChatChannel chatChannel) {
/* 42 */     this.channels.remove(chatChannel);
/* 43 */     chatChannel.removeMessageListener(this.listener);
/*    */   }
/*    */   
/*    */   public ChatChannel getById(int id) {
/* 47 */     for (ChatChannel channel : this.channels) {
/* 48 */       if (channel.getId() == id)
/* 49 */         return channel; 
/* 50 */     }  return null;
/*    */   }
/*    */   
/*    */   public int count() {
/* 54 */     return this.channels.size();
/*    */   }
/*    */   
/*    */   public void pushMessage(ChatMessage message) {
/* 58 */     for (ChatChannel channel : this.channels) {
/* 59 */       if (channel.isUserRegistered(message.getUserId()))
/* 60 */         channel.messageReceived(message); 
/*    */     } 
/*    */   }
/*    */   public Iterator<ChatChannel> iterator() {
/* 64 */     return this.channels.iterator();
/*    */   }
/*    */   
/*    */   private class DemultiplexerChatMessageListener implements ChatMessageListener {
/*    */     public void messageReceived(ChatChannel channel, ChatMessage message) {
/* 69 */       ChannelDemultiplexer.this.fireMessageReceived(channel, message);
/*    */     }
/*    */     
/*    */     private DemultiplexerChatMessageListener() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\ChannelDemultiplexer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */