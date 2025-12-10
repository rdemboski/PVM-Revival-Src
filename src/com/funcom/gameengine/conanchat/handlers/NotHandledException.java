/*    */ package com.funcom.gameengine.conanchat.handlers;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.packets2.ChatMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotHandledException
/*    */   extends RuntimeException
/*    */ {
/*    */   private ChatMessage unhandledMessage;
/*    */   
/*    */   public NotHandledException(ChatMessage unhandledMessage) {
/* 15 */     this.unhandledMessage = unhandledMessage;
/*    */   }
/*    */   
/*    */   public ChatMessage getUnhandledMessage() {
/* 19 */     return this.unhandledMessage;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\handlers\NotHandledException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */