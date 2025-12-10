/*    */ package com.funcom.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class SizeCheckedArrayList<E> extends ArrayList<E> {
/*    */   private String warningName;
/*    */   private int warningLimit;
/*    */   
/*    */   public SizeCheckedArrayList(int initialCapacity, String warningName, int warningLimit) {
/* 10 */     super(initialCapacity);
/* 11 */     this.warningName = warningName;
/* 12 */     this.warningLimit = warningLimit;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean add(E e) {
/* 17 */     checkSize();
/* 18 */     return super.add(e);
/*    */   }
/*    */ 
/*    */   
/*    */   public void add(int index, E element) {
/* 23 */     checkSize();
/* 24 */     super.add(index, element);
/*    */   }
/*    */   
/*    */   private void checkSize() {
/* 28 */     if (size() >= this.warningLimit) {
/* 29 */       outputWarning();
/*    */     }
/*    */   }
/*    */   
/*    */   private void outputWarning() {
/* 34 */     String message = "------------\nWARNING, A LIST IS TOO LONG, POSSIBLE MEM LEAK\nname@id=" + this.warningName + "@" + Integer.toHexString(System.identityHashCode(this)) + "\n" + "size=" + size() + " warningLimit=" + this.warningLimit + "\n" + "Content Objects:\n";
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 39 */     for (E e : this) {
/* 40 */       message = message + "  " + e.getClass() + "@" + Integer.toHexString(System.identityHashCode(e)) + "\n";
/*    */     }
/* 42 */     message = message + "------------";
/* 43 */     (new RuntimeException(message)).printStackTrace();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funco\\util\SizeCheckedArrayList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */