/*    */ package com.funcom.gameengine.model.input;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FixedMouseOver
/*    */   extends MouseOver
/*    */ {
/*    */   private MouseCursorSetter cursorSetter;
/*    */   private String mouseOverCursorId;
/*    */   
/*    */   public FixedMouseOver(MouseCursorSetter cursorSetter, String mouseOverCursorId) {
/* 17 */     this.mouseOverCursorId = mouseOverCursorId;
/* 18 */     this.cursorSetter = cursorSetter;
/*    */   }
/*    */   
/*    */   public void mouseEntered() {
/* 22 */     this.cursorSetter.setCursor(this.mouseOverCursorId);
/*    */   }
/*    */   
/*    */   public void mouseExited() {
/* 26 */     this.cursorSetter.setDefaultCursor();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\input\FixedMouseOver.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */