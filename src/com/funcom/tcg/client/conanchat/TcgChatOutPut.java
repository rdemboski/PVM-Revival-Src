/*    */ package com.funcom.tcg.client.conanchat;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.DefaultChatUser;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.chat.ChatOutput;
/*    */ import com.funcom.tcg.net.message.TcgChatFriendResponseMessage;
/*    */ import com.funcom.tcg.net.message.TcgChatMessage;
/*    */ import com.funcom.tcg.net.message.TcgChatRequestFriendMessage;
/*    */ 
/*    */ public class TcgChatOutPut implements ChatOutput {
/*    */   private TcgDummyChatUser tcgDummyChatUser;
/*    */   
/*    */   public TcgChatOutPut(TcgDummyChatUser tcgDummyChatUser) {
/* 16 */     this.tcgDummyChatUser = tcgDummyChatUser;
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendMessage(int playerSourceID, String message) {
/*    */     try {
/* 22 */       NetworkHandler.instance().getIOHandler().send((Message)new TcgChatMessage(playerSourceID, message));
/* 23 */     } catch (InterruptedException e) {
/* 24 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void sendFriendRequest(int id) {
/*    */     try {
/* 31 */       NetworkHandler.instance().getIOHandler().send((Message)new TcgChatRequestFriendMessage(id));
/* 32 */     } catch (InterruptedException e) {
/* 33 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendFriendRemove(int id) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void sendFriendResponse(int id) {
/*    */     try {
/* 45 */       NetworkHandler.instance().getIOHandler().send((Message)new TcgChatFriendResponseMessage(id));
/* 46 */     } catch (InterruptedException e) {
/* 47 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void addFriend(int id) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeFriend(int id) {
/* 58 */     MainGameState.getFriendModel().removeFriend(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public DefaultChatUser getChatUser() {
/* 63 */     return this.tcgDummyChatUser;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\conanchat\TcgChatOutPut.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */