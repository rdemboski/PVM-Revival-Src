/*    */ package com.funcom.gameengine.ai;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BrainEvent
/*    */   extends EventObject
/*    */ {
/*    */   private String notification;
/*    */   
/*    */   public BrainEvent(Object source, String notification) {
/* 19 */     super(source);
/* 20 */     this.notification = notification;
/*    */   }
/*    */   
/*    */   public String getNotification() {
/* 24 */     return this.notification;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\BrainEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */