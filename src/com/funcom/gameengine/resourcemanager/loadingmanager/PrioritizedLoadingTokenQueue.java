/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ import java.util.AbstractQueue;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrioritizedLoadingTokenQueue<E>
/*    */ {
/*    */   public enum TokenPriority
/*    */   {
/* 21 */     PRIORITY_POSITION_UPDATE,
/* 22 */     PRIORITY_CREATURES,
/* 23 */     PRIORITY_MAP,
/* 24 */     PRIORITY_UNSET;
/*    */   }
/* 26 */   private AbstractQueue[] mQueues = new AbstractQueue[] { new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue() };
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public E getToken() {
/* 38 */     E token = null;
/* 39 */     for (int n = 0; n < this.mQueues.length; n++) {
/* 40 */       token = this.mQueues[n].peek();
/* 41 */       if (token != null) {
/* 42 */         this.mQueues[n].remove(token);
/*    */         break;
/*    */       } 
/*    */     } 
/* 46 */     return token;
/*    */   }
/*    */   
/*    */   public int size() {
/* 50 */     int size = 0;
/* 51 */     for (int n = 0; n < this.mQueues.length; n++) {
/* 52 */       size += this.mQueues[n].size();
/*    */     }
/* 54 */     return size;
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 58 */     for (int n = 0; n < this.mQueues.length; n++) {
/* 59 */       if (!this.mQueues[n].isEmpty())
/* 60 */         return false; 
/*    */     } 
/* 62 */     return true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void add(LoadingManagerToken token, TokenPriority Priority) {
/* 68 */     this.mQueues[Priority.ordinal()].add(token);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 72 */     for (int n = 0; n < this.mQueues.length; n++)
/* 73 */       this.mQueues[n].clear(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\PrioritizedLoadingTokenQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */