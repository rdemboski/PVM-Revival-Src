/*    */ package com.funcom.gameengine.conanchat.handlers;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*    */ 
/*    */ 
/*    */ public abstract class AbstractConanChatMessageHandler
/*    */   implements ConanChatMessageHandler
/*    */ {
/*    */   private short myType;
/*    */   private ConanChatMessageHandler next;
/*    */   
/*    */   protected AbstractConanChatMessageHandler(short myType) {
/* 13 */     this.myType = myType;
/*    */   }
/*    */   
/*    */   protected AbstractConanChatMessageHandler(short myType, ConanChatMessageHandler next) {
/* 17 */     this.myType = myType;
/* 18 */     this.next = next;
/*    */   }
/*    */ 
/*    */   
/*    */   public void handle(ChatMessage message) throws NotHandledException {
/* 23 */     if (message.getMessageType() == this.myType)
/* 24 */     { process(message); }
/* 25 */     else { if (iAmLast()) {
/* 26 */         throw new NotHandledException(message);
/*    */       }
/* 28 */       this.next.handle(message); }
/*    */   
/*    */   }
/*    */   private boolean iAmLast() {
/* 32 */     return (this.next == null);
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract void process(ChatMessage paramChatMessage);
/*    */   
/*    */   public void setNext(ConanChatMessageHandler next) {
/* 39 */     this.next = next;
/*    */   }
/*    */ 
/*    */   
/*    */   public ConanChatMessageHandler getNext(ConanChatMessageHandler successiveHandler) {
/* 44 */     return this.next;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\handlers\AbstractConanChatMessageHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */