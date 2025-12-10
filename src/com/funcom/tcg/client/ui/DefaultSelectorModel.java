/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public class DefaultSelectorModel
/*    */   implements SelectorModel {
/*    */   private List<SelectorModel.ChangeListener> listeners;
/*    */   private List<Object> data;
/*    */   private int index;
/*    */   
/*    */   public DefaultSelectorModel(Collection<?> data) {
/* 16 */     this.data = new ArrayList();
/* 17 */     this.data.addAll(data);
/* 18 */     this.listeners = new LinkedList<SelectorModel.ChangeListener>();
/*    */   }
/*    */   
/*    */   public void addChangeListener(SelectorModel.ChangeListener changeListener) {
/* 22 */     this.listeners.add(changeListener);
/*    */   }
/*    */   
/*    */   public void removeChangeListener(SelectorModel.ChangeListener changeListener) {
/* 26 */     this.listeners.remove(changeListener);
/*    */   }
/*    */   
/*    */   public boolean hasPrevious() {
/* 30 */     return (this.index > 0);
/*    */   }
/*    */   
/*    */   public boolean hasNext() {
/* 34 */     return (this.index < this.data.size() - 1);
/*    */   }
/*    */   
/*    */   public Object getCurrent() {
/* 38 */     return this.data.get(this.index);
/*    */   }
/*    */   
/*    */   public void next() throws NoSuchElementException {
/* 42 */     if (!hasNext())
/* 43 */       throw new NoSuchElementException("Index out of bounds, list size: " + this.data.size()); 
/* 44 */     this.index++;
/* 45 */     fireChange();
/*    */   }
/*    */   
/*    */   public void previous() throws NoSuchElementException {
/* 49 */     if (!hasPrevious())
/* 50 */       throw new NoSuchElementException("Index out of bounds, list size: " + this.data.size()); 
/* 51 */     this.index--;
/* 52 */     fireChange();
/*    */   }
/*    */   
/*    */   public void first() {
/* 56 */     this.index = 0;
/* 57 */     fireChange();
/*    */   }
/*    */   
/*    */   public void last() {
/* 61 */     this.index = Math.max(0, this.data.size() - 1);
/* 62 */     fireChange();
/*    */   }
/*    */   
/*    */   protected void fireChange() {
/* 66 */     for (SelectorModel.ChangeListener listener : this.listeners) {
/* 67 */       listener.changeEvent(this);
/*    */     }
/*    */   }
/*    */   
/*    */   public int getSize() {
/* 72 */     return this.data.size();
/*    */   }
/*    */   
/*    */   public void setSelectionIndex(int index) {
/* 76 */     if (index < 0 || index > this.data.size() - 1)
/* 77 */       throw new IndexOutOfBoundsException(String.format("Index: %s, data size: %s", new Object[] { Integer.valueOf(index), Integer.valueOf(getSize()) })); 
/* 78 */     this.index = index;
/* 79 */     fireChange();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\DefaultSelectorModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */