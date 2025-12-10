/*    */ package com.funcom.gameengine.input;
/*    */ 
/*    */ import com.jme.input.controls.GameControl;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameControlMouseInputPoller
/*    */   implements MouseInputPoller
/*    */ {
/*    */   private GameControl xAxisControl;
/*    */   private GameControl yAxisControl;
/*    */   
/*    */   public GameControlMouseInputPoller(GameControl xAxisControl, GameControl yAxisControl) {
/* 17 */     this.xAxisControl = xAxisControl;
/* 18 */     this.yAxisControl = yAxisControl;
/*    */   }
/*    */   
/*    */   public float getMouseXAbsolute() {
/* 22 */     return this.xAxisControl.getValue();
/*    */   }
/*    */   
/*    */   public float getMouseYAbsolute() {
/* 26 */     return this.yAxisControl.getValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\GameControlMouseInputPoller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */