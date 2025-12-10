/*    */ package com.funcom.gameengine.model.input;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class UserActionHandler
/*    */ {
/* 20 */   protected static final Logger LOG = Logger.getLogger(UserActionHandler.class.getName());
/*    */ 
/*    */   
/*    */   public abstract void handleLeftMousePress(WorldCoordinate paramWorldCoordinate);
/*    */ 
/*    */   
/*    */   public void handleRightMousePress() {}
/*    */ 
/*    */   
/*    */   public void handleMouseEnter() {}
/*    */ 
/*    */   
/*    */   public void handleMouseExit() {}
/*    */   
/*    */   public boolean isClickable() {
/* 35 */     return true;
/*    */   }
/*    */   
/*    */   public void handleKeyPress(int keycode) {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\input\UserActionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */