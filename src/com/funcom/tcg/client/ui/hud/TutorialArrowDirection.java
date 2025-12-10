/*    */ package com.funcom.tcg.client.ui.hud;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum TutorialArrowDirection
/*    */ {
/* 11 */   UP(0, "up"),
/* 12 */   RIGHT(1, "right"),
/* 13 */   DOWN(2, "down"),
/* 14 */   LEFT(3, "left"),
/* 15 */   TOP_RIGHT(4, "top_right");
/*    */   
/*    */   private int direction;
/*    */   
/*    */   private String name;
/*    */   
/*    */   TutorialArrowDirection(int direction, String name) {
/* 22 */     this.direction = direction;
/* 23 */     this.name = name;
/*    */   }
/*    */   
/*    */   public int getDirection() {
/* 27 */     return this.direction;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 31 */     return this.name;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\TutorialArrowDirection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */