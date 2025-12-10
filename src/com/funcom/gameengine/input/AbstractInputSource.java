/*    */ package com.funcom.gameengine.input;
/*    */ 
/*    */ import com.jme.input.KeyInputListener;
/*    */ import com.jme.input.MouseInputListener;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public abstract class AbstractInputSource
/*    */   implements InputSource, MouseInputListener, KeyInputListener
/*    */ {
/*    */   private Set<InputSourceListener> listeners;
/*    */   
/*    */   public void addListener(InputSourceListener listener) {
/* 20 */     if (this.listeners == null) {
/* 21 */       this.listeners = new HashSet<InputSourceListener>();
/*    */     }
/* 23 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void removeListener(InputSourceListener listener) {
/* 27 */     if (this.listeners == null) {
/*    */       return;
/*    */     }
/* 30 */     this.listeners.remove(listener);
/*    */   }
/*    */   
/*    */   public void update(float time) {
/* 34 */     if (this.listeners != null)
/* 35 */       for (InputSourceListener listener : this.listeners) {
/* 36 */         listener.onUpdate(time);
/*    */       } 
/*    */   }
/*    */   
/*    */   public void onButton(int button, boolean pressed, int x, int y) {
/* 41 */     if (this.listeners != null)
/* 42 */       for (InputSourceListener listener : this.listeners) {
/* 43 */         listener.onButton(button, pressed, x, y);
/*    */       } 
/*    */   }
/*    */   
/*    */   public void onWheel(int wheelDelta, int x, int y) {
/* 48 */     if (this.listeners != null)
/* 49 */       for (InputSourceListener listener : this.listeners) {
/* 50 */         listener.onWheel(wheelDelta, x, y);
/*    */       } 
/*    */   }
/*    */   
/*    */   public void onMove(int xDelta, int yDelta, int newX, int newY) {
/* 55 */     if (this.listeners != null)
/* 56 */       for (InputSourceListener listener : this.listeners) {
/* 57 */         listener.onMove(xDelta, yDelta, newX, newY);
/*    */       } 
/*    */   }
/*    */   
/*    */   public void onKey(char character, int keyCode, boolean pressed) {
/* 62 */     if (this.listeners != null)
/* 63 */       for (InputSourceListener listener : this.listeners)
/* 64 */         listener.onKey(character, keyCode, pressed);  
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\AbstractInputSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */