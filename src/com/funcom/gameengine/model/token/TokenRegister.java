/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.concurrent.BlockingQueue;
/*    */ import java.util.concurrent.LinkedBlockingQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TokenRegister
/*    */ {
/* 11 */   private static TokenRegister INSTANCE = new TokenRegister();
/*    */   private BlockingQueue<Token> openQueue;
/*    */   private BlockingQueue<Token> gameQueue;
/*    */   
/*    */   public static TokenRegister instance() {
/* 16 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   private TokenRegister() {
/* 20 */     this.openQueue = new LinkedBlockingQueue<Token>();
/* 21 */     this.gameQueue = new LinkedBlockingQueue<Token>();
/*    */   }
/*    */   
/*    */   public void addToken(Token token) {
/* 25 */     if (Token.TokenType.OPEN.equals(token.getTokenType())) {
/* 26 */       addOpenToken(token);
/* 27 */     } else if (Token.TokenType.GAME_THREAD.equals(token.getTokenType())) {
/* 28 */       addGameToken(token);
/*    */     } else {
/* 30 */       throw new IllegalArgumentException("Unknown token type");
/*    */     } 
/*    */   }
/*    */   public void clear() {
/* 34 */     this.openQueue.clear();
/* 35 */     this.gameQueue.clear();
/*    */   }
/*    */   
/*    */   public void processAllSteppedTokens() {
/* 39 */     BlockingQueue<Token> tokens = this.gameQueue;
/* 40 */     for (Iterator<Token> iterator = tokens.iterator(); iterator.hasNext(); ) {
/* 41 */       Token token = iterator.next();
/* 42 */       if (token instanceof SteppedToken) {
/* 43 */         SteppedToken steppedToken = (SteppedToken)token;
/* 44 */         while (!steppedToken.isFinished()) {
/* 45 */           steppedToken.process();
/*    */         }
/* 47 */         iterator.remove();
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void addOpenToken(Token token) {
/* 53 */     this.openQueue.add(token);
/*    */   }
/*    */   
/*    */   private void addGameToken(Token token) {
/* 57 */     this.gameQueue.add(token);
/*    */   }
/*    */   
/*    */   public Token takeOpenToken() throws InterruptedException {
/* 61 */     return this.openQueue.take();
/*    */   }
/*    */   
/*    */   public Token peekOpenToken() {
/* 65 */     return this.openQueue.peek();
/*    */   }
/*    */   
/*    */   public Token peekGameToken() {
/* 69 */     return this.gameQueue.peek();
/*    */   }
/*    */   
/*    */   public int getGameQueueSize() {
/* 73 */     return this.gameQueue.size();
/*    */   }
/*    */   
/*    */   public int getOpenQueueSize() {
/* 77 */     return this.openQueue.size();
/*    */   }
/*    */   
/*    */   public void removeGameToken(Token token) {
/* 81 */     this.gameQueue.remove(token);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\TokenRegister.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */