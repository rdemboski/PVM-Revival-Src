/*    */ package com.funcom.gameengine.input;
/*    */ 
/*    */ import com.jme.input.MouseInput;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DefaultMouseInputPoller
/*    */   implements MouseInputPoller
/*    */ {
/*    */   public float getMouseXAbsolute() {
/* 16 */     return MouseInput.get().getXAbsolute();
/*    */   }
/*    */   
/*    */   public float getMouseYAbsolute() {
/* 20 */     return MouseInput.get().getYAbsolute();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\DefaultMouseInputPoller.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */