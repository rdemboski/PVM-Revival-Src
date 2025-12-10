/*    */ package com.funcom.commons.brain;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum EventAIContainerIDs
/*    */ {
/* 12 */   HealthEvent("Percentage Health"),
/* 13 */   DamageTypeEvent("Damage Type"),
/* 14 */   DiedEvent("Died");
/*    */ 
/*    */   
/*    */   private String eventTag;
/*    */   
/*    */   private static HashMap<String, EventAIContainerIDs> thisMap;
/*    */ 
/*    */   
/*    */   EventAIContainerIDs(String eventTag) {
/* 23 */     this.eventTag = eventTag;
/*    */   }
/*    */   
/*    */   public String getEventTag() {
/* 27 */     return this.eventTag;
/*    */   }
/*    */   
/*    */   static {
/* 31 */     thisMap = new HashMap<String, EventAIContainerIDs>();
/* 32 */     for (EventAIContainerIDs eventAIContainerIDs : values()) {
/* 33 */       thisMap.put(eventAIContainerIDs.getEventTag(), eventAIContainerIDs);
/*    */     }
/*    */   }
/*    */   
/*    */   public static EventAIContainerIDs getByStringTag(String tag) {
/* 38 */     return thisMap.get(tag);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\brain\EventAIContainerIDs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */