/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface Token
/*    */ {
/*    */   TokenType getTokenType();
/*    */   
/*    */   void process();
/*    */   
/*    */   public enum TokenType
/*    */   {
/* 15 */     OPEN,
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 20 */     GAME_THREAD;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\Token.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */