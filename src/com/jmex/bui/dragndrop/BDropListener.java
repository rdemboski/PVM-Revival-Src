/*    */ package com.jmex.bui.dragndrop;
/*    */ 
/*    */ import com.jmex.bui.event.BEvent;
/*    */ import com.jmex.bui.event.EventListener;
/*    */ 
/*    */ public abstract class BDropListener
/*    */   implements EventListener {
/*    */   public void eventDispatched(BEvent event) {
/*  9 */     if (event instanceof BDropEvent) {
/* 10 */       BDropEvent e = (BDropEvent)event;
/* 11 */       drop(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void drop(BDropEvent paramBDropEvent);
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\dragndrop\BDropListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */