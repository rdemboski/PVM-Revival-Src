/*    */ package com.funcom.gameengine.model.token.logging;
/*    */ 
/*    */ import com.funcom.gameengine.model.token.Token;
/*    */ 
/*    */ public class TokenLoggerParameter
/*    */ {
/*    */   private Class clazz;
/*    */   private Token.TokenType tokenType;
/*    */   private long usedTime;
/*    */   private long timeNanos;
/*    */   
/*    */   public TokenLoggerParameter(Class clazz, Token.TokenType tokenType, long usedTime, long timeNanos) {
/* 13 */     this.clazz = clazz;
/* 14 */     this.tokenType = tokenType;
/* 15 */     this.usedTime = usedTime;
/* 16 */     this.timeNanos = timeNanos;
/*    */   }
/*    */   
/*    */   public Class getClazz() {
/* 20 */     return this.clazz;
/*    */   }
/*    */   
/*    */   public Token.TokenType getTokenType() {
/* 24 */     return this.tokenType;
/*    */   }
/*    */   
/*    */   public long getUsedTime() {
/* 28 */     return this.usedTime;
/*    */   }
/*    */   
/*    */   public long getTimeNanos() {
/* 32 */     return this.timeNanos;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 37 */     return "TokenLoggerParameter{clazz=" + this.clazz + ", tokenType=" + this.tokenType + ", usedTime=" + this.usedTime + ", timeNanos=" + this.timeNanos + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\logging\TokenLoggerParameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */