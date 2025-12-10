/*    */ package com.funcom.tcg.client.ui;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PeelWindowEvent
/*    */ {
/*    */   private String eventName;
/*    */   private PeelEventType type;
/*    */   
/*    */   public PeelWindowEvent(String eventName, PeelEventType type) {
/* 11 */     this.eventName = eventName;
/* 12 */     this.type = type;
/*    */   }
/*    */   
/*    */   public String getEventName() {
/* 16 */     return this.eventName;
/*    */   }
/*    */   
/*    */   public PeelEventType getType() {
/* 20 */     return this.type;
/*    */   }
/*    */   
/*    */   public enum PeelEventType {
/* 24 */     ACTION, MOUSE_DOWN, MOUSE_UP;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\PeelWindowEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */