/*    */ package com.funcom.commons.utils.scriptlinecomponents;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ChainedScriptComponent<T>
/*    */ {
/* 15 */   private ChainedScriptComponent _next = null; private ChainedScriptComponent _last = null;
/*    */   
/*    */   public void setNext(ChainedScriptComponent next) {
/* 18 */     this._next = next;
/* 19 */     if (this._next != null && this._next.getLastScriptComponent() != this) this._next.setLast(this); 
/*    */   }
/*    */   public void setLast(ChainedScriptComponent last) {
/* 22 */     this._last = last;
/* 23 */     if (this._last != null && this._last.getNextScriptComponent() != this) this._last.setNext(this); 
/*    */   }
/*    */   
/*    */   public ChainedScriptComponent getNextScriptComponent() {
/* 27 */     return this._next;
/*    */   }
/*    */   public ChainedScriptComponent getLastScriptComponent() {
/* 30 */     return this._last;
/*    */   }
/*    */   
/*    */   public boolean validateChain() {
/* 34 */     if (isValid()) {
/* 35 */       if (this._next != null) return this._next.validateChain(); 
/* 36 */       return true;
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public abstract boolean choose(T paramT);
/*    */ 
/*    */   
/*    */   public abstract boolean chooseAsString(String paramString);
/*    */   
/*    */   public abstract T getValidItem();
/*    */   
/*    */   public abstract boolean isValid();
/*    */   
/*    */   public String toString() {
/* 52 */     String retur = getClass().getName();
/* 53 */     if (getNextScriptComponent() != null) retur = retur + " : " + getNextScriptComponent().toString(); 
/* 54 */     return retur;
/*    */   }
/*    */   
/* 57 */   private ArrayList<SelectionChangedListener> listeners = new ArrayList<SelectionChangedListener>();
/*    */   
/*    */   public void addSelectionChangedListener(SelectionChangedListener selectionChangedListener) {
/* 60 */     this.listeners.add(selectionChangedListener);
/*    */   }
/*    */   public boolean removeSelectionChangedListener(SelectionChangedListener selectionChangedListener) {
/* 63 */     return this.listeners.remove(selectionChangedListener);
/*    */   }
/*    */   protected void fireSelectionChanged(ChainedScriptComponent source) {
/* 66 */     Iterator<SelectionChangedListener> selectionChangedListenerIterator = this.listeners.iterator();
/* 67 */     while (selectionChangedListenerIterator.hasNext())
/* 68 */       ((SelectionChangedListener)selectionChangedListenerIterator.next()).selectionChanged(source); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\common\\utils\scriptlinecomponents\ChainedScriptComponent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */