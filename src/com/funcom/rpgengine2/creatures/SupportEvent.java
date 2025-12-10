/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ public class SupportEvent {
/*    */   private final Type type;
/*    */   
/*    */   public SupportEvent(Type type) {
/*  7 */     this.type = type;
/*    */   }
/*    */   
/*    */   public Type getType() {
/* 11 */     return this.type;
/*    */   }
/*    */   
/*    */   public static interface Type {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\SupportEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */