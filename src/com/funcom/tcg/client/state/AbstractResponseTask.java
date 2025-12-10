/*    */ package com.funcom.tcg.client.state;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.net.AccountResult;
/*    */ import com.jme.util.GameTaskQueueManager;
/*    */ import java.util.concurrent.Callable;
/*    */ 
/*    */ public abstract class AbstractResponseTask
/*    */   implements Callable<Object>
/*    */ {
/*    */   private final AsyncMessageSender<? extends Message> sender;
/*    */   
/*    */   public AbstractResponseTask(AsyncMessageSender<? extends Message> sender) {
/* 14 */     this.sender = sender;
/*    */   }
/*    */ 
/*    */   
/*    */   public final Object call() throws Exception {
/* 19 */     if (this.sender.isDone()) {
/* 20 */       onDone();
/*    */       
/* 22 */       if (this.sender.isOk()) {
/* 23 */         onOk(this.sender.getMessage());
/*    */       } else {
/* 25 */         onError(this.sender.getError());
/*    */       } 
/*    */     } else {
/* 28 */       GameTaskQueueManager.getManager().getQueue("update").enqueue(this);
/*    */     } 
/*    */     
/* 31 */     return null;
/*    */   }
/*    */   
/*    */   protected abstract void onDone();
/*    */   
/*    */   protected abstract void onOk(Message paramMessage);
/*    */   
/*    */   protected abstract void onError(AccountResult paramAccountResult);
/*    */   
/*    */   public void start() {
/* 41 */     GameTaskQueueManager.getManager().getQueue("update").enqueue(this);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\AbstractResponseTask.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */