/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DummyOpenToken
/*    */   implements Token
/*    */ {
/*    */   public Token.TokenType getTokenType() {
/* 10 */     return Token.TokenType.OPEN;
/*    */   }
/*    */   
/*    */   public void process() {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\DummyOpenToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */