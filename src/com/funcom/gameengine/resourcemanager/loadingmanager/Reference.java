/*    */ package com.funcom.gameengine.resourcemanager.loadingmanager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Reference<T>
/*    */ {
/* 11 */   private volatile T data = null;
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public synchronized void set(T d) {
/* 17 */     this.data = d;
/*    */   }
/*    */   
/*    */   public T get() {
/* 21 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\loadingmanager\Reference.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */