/*    */ package com.funcom.commons.chat;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface ChatCommunicator
/*    */ {
/*    */   void addMessageListener(MessageListener paramMessageListener);
/*    */   
/*    */   void removeMessageListener(MessageListener paramMessageListener);
/*    */   
/*    */   void sendMessage(Message paramMessage);
/*    */   
/*    */   public static abstract class MessageListener
/*    */   {
/*    */     private int messageType;
/*    */     
/*    */     public MessageListener(int messageType) {
/* 22 */       this.messageType = messageType;
/*    */     }
/*    */     
/*    */     public int getMessageType() {
/* 26 */       return this.messageType;
/*    */     }
/*    */     
/*    */     public abstract boolean messageReceived(Message param1Message);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\chat\ChatCommunicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */