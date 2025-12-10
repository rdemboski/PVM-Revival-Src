/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import com.funcom.gameengine.model.ResourceGetter;
/*    */ 
/*    */ public abstract class CreateWaterToken
/*    */   implements Token {
/*    */   protected ResourceGetter resourceGetter;
/*    */   
/*    */   protected CreateWaterToken(ResourceGetter resourceGetter) {
/* 10 */     this.resourceGetter = resourceGetter;
/*    */   }
/*    */   
/*    */   public Token.TokenType getTokenType() {
/* 14 */     return Token.TokenType.GAME_THREAD;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\CreateWaterToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */