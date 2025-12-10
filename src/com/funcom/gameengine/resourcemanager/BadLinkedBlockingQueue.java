/*    */ package com.funcom.gameengine.resourcemanager;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ 
/*    */ 
/*    */ public class BadLinkedBlockingQueue<E>
/*    */   extends LinkedBlockingQueue<E>
/*    */ {
/*    */   public void addToFront(E object) {
/* 11 */     LinkedList<E> tempList = new LinkedList<E>();
/* 12 */     drainTo(tempList);
/* 13 */     add(object);
/* 14 */     addAll(tempList);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\resourcemanager\BadLinkedBlockingQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */