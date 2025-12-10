/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DummyToken
/*    */   implements Token
/*    */ {
/*    */   public Token.TokenType getTokenType() {
/* 11 */     return Token.TokenType.GAME_THREAD;
/*    */   }
/*    */   
/*    */   public void process() {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\DummyToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */