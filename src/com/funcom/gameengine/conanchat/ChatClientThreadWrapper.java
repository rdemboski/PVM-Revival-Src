/*    */ package com.funcom.gameengine.conanchat;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ChatClientThreadWrapper
/*    */   extends Shutdownable
/*    */   implements Runnable
/*    */ {
/*    */   private ChatClient chatClient;
/*    */   private static final int SLEEP_TIME_MILLIS = 100;
/*    */   private List<ExceptionListener> exceptionListeners;
/*    */   private boolean crashed;
/*    */   
/*    */   public ChatClientThreadWrapper(ChatClient chatClient) {
/* 16 */     this.chatClient = chatClient;
/* 17 */     this.exceptionListeners = new LinkedList<ExceptionListener>();
/*    */   }
/*    */   
/*    */   public void addExceptionListener(ExceptionListener exceptionListener) {
/* 21 */     this.exceptionListeners.add(exceptionListener);
/*    */   }
/*    */   
/*    */   public void removeExceptionListener(ExceptionListener exceptionListener) {
/* 25 */     this.exceptionListeners.remove(exceptionListener);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 30 */     while (!isShutdown()) {
/* 31 */       if (this.crashed) {
/*    */         try {
/* 33 */           Thread.sleep(20000L);
/* 34 */         } catch (InterruptedException e) {}
/*    */ 
/*    */         
/* 37 */         notifyException(null);
/*    */         continue;
/*    */       } 
/*    */       try {
/* 41 */         this.chatClient.update();
/* 42 */         Thread.sleep(100L);
/* 43 */       } catch (Exception e) {
/* 44 */         notifyException(e);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void setCrashed(boolean crashed) {
/* 51 */     this.crashed = crashed;
/*    */   }
/*    */   
/*    */   private void notifyException(Throwable e) {
/* 55 */     for (ExceptionListener exceptionListener : this.exceptionListeners) {
/* 56 */       exceptionListener.exceptionThrown(e, this.chatClient);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   protected void cleanup() {
/* 62 */     this.chatClient.shutdown();
/*    */   }
/*    */   
/*    */   public void setChatClient(ChatClient chatClient) {
/* 66 */     this.chatClient = chatClient;
/*    */   }
/*    */   
/*    */   public ChatClient getChatClient() {
/* 70 */     return this.chatClient;
/*    */   }
/*    */   
/*    */   public static interface ExceptionListener {
/*    */     void exceptionThrown(Throwable param1Throwable, ChatClient param1ChatClient);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\conanchat\ChatClientThreadWrapper.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */