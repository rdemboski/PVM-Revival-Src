/*    */ package com.funcom.commons.chat.commands;
/*    */ 
/*    */ import com.funcom.commons.chat.ChatChannel;
/*    */ import com.funcom.commons.chat.ChatCommunicator;
/*    */ import com.funcom.commons.chat.ChatUser;
/*    */ import com.funcom.server.common.Message;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ public abstract class ChatCommand {
/* 12 */   private static final Logger LOG = Logger.getLogger(ChatCommand.class.getName());
/*    */   private ChatCommunicator communicator;
/*    */   private ChatUser myself;
/*    */   private Iterable<ChatChannel> chatChannels;
/*    */   private ChatChannel selectedChannel;
/*    */   
/*    */   public abstract String getCommand();
/*    */   
/*    */   public abstract void execute(String[] paramArrayOfString);
/*    */   
/*    */   public void setMyself(ChatUser myself) {
/* 23 */     this.myself = myself;
/*    */   }
/*    */   
/*    */   public void setChatChannels(Iterable<ChatChannel> chatChannels) {
/* 27 */     this.chatChannels = chatChannels;
/*    */   }
/*    */   
/*    */   public void setCommunicator(ChatCommunicator communicator) {
/* 31 */     this.communicator = communicator;
/*    */   }
/*    */   
/*    */   public void setSelectedChannel(ChatChannel selectedChannel) {
/* 35 */     this.selectedChannel = selectedChannel;
/*    */   }
/*    */   
/*    */   protected final void sendMessage(Message message) {
/* 39 */     this.communicator.sendMessage(message);
/*    */   }
/*    */   
/*    */   protected final void log(Level level, String log) {
/* 43 */     LOG.log((Priority)level, log);
/*    */   }
/*    */   
/*    */   protected Iterable<ChatChannel> getChannels() {
/* 47 */     return this.chatChannels;
/*    */   }
/*    */   
/*    */   protected ChatUser getMyself() {
/* 51 */     return this.myself;
/*    */   }
/*    */   
/*    */   protected ChatChannel getSelectedChannel() {
/* 55 */     return this.selectedChannel;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\commands\ChatCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */