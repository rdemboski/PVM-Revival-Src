/*    */ package com.funcom.gameengine.ai.patrol;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PatrolNode
/*    */ {
/*    */   private PatrolNode next;
/*    */   private PatrolNode previous;
/*    */   private WorldCoordinate position;
/*    */   private int order;
/*    */   private float delay;
/*    */   
/*    */   public PatrolNode(WorldCoordinate position, int order, float delay) {
/* 16 */     this.position = position;
/* 17 */     this.order = order;
/* 18 */     this.delay = delay;
/*    */   }
/*    */   
/*    */   public PatrolNode getNext() {
/* 22 */     return this.next;
/*    */   }
/*    */   
/*    */   public PatrolNode getPrevious() {
/* 26 */     return this.previous;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getPosition() {
/* 30 */     return this.position;
/*    */   }
/*    */   
/*    */   public int getOrderNumber() {
/* 34 */     return this.order;
/*    */   }
/*    */   
/*    */   public float getDelay() {
/* 38 */     return this.delay;
/*    */   }
/*    */   
/*    */   public void setNext(PatrolNode next) {
/* 42 */     this.next = next;
/*    */   }
/*    */   
/*    */   public void setPrevious(PatrolNode previous) {
/* 46 */     this.previous = previous;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\patrol\PatrolNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */