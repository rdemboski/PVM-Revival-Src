/*    */ package com.funcom.tcg.client.ui.event;
/*    */ 
/*    */ public class DebugEvent
/*    */ {
/*    */   private int type;
/*    */   private double value;
/*    */   private boolean state;
/*    */   
/*    */   public DebugEvent(int type) {
/* 10 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 14 */     return this.type;
/*    */   }
/*    */   
/*    */   public boolean getState() {
/* 18 */     return this.state;
/*    */   }
/*    */   
/*    */   public double getValue() {
/* 22 */     return this.value;
/*    */   }
/*    */   
/*    */   public void setValue(double value) {
/* 26 */     this.value = value;
/*    */   }
/*    */   
/*    */   public void setState(boolean state) {
/* 30 */     this.state = state;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\event\DebugEvent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */