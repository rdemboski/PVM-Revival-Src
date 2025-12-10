/*    */ package com.jmex.bui.dragndrop;
/*    */ 
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import com.jmex.bui.event.BEvent;
/*    */ 
/*    */ public class BDropEvent
/*    */   extends BEvent {
/*  8 */   public static final Object NOTHING = new Object();
/*    */   private BDragEvent BDragEvent;
/*    */   
/*    */   public BDropEvent(Object source, BDragEvent bDragEvent) {
/* 12 */     super(source, BuiSystem.getRootNode().getTickStamp());
/* 13 */     if (bDragEvent == null)
/* 14 */       throw new IllegalArgumentException("BDragEvent = null"); 
/* 15 */     this.BDragEvent = bDragEvent;
/*    */   }
/*    */   
/*    */   public Object getSource() {
/* 19 */     return super.getSource();
/*    */   }
/*    */   
/*    */   public BDragEvent getDragEvent() {
/* 23 */     return this.BDragEvent;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 27 */     return "BDropEvent@" + hashCode() + "{" + "source=" + getSource() + ", BDragEvent=" + this.BDragEvent + "}";
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\jmex\bui\dragndrop\BDropEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */