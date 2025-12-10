/*   */ package com.funcom.gameengine.model.command;
/*   */ 
/*   */ 
/*   */ 
/*   */ public abstract class AbstractDestination
/*   */   implements Destination
/*   */ {
/*   */   public boolean isAtDestination(double destDistance) {
/* 9 */     return (destDistance - 0.15D <= 0.0D);
/*   */   }
/*   */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\AbstractDestination.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */