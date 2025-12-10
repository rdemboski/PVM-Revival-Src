/*    */ package com.funcom.gameengine.model.token;
/*    */ 
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GameTokenProcessor
/*    */ {
/* 12 */   private static final Logger LOGGER = Logger.getLogger(GameTokenProcessor.class.getName());
/* 13 */   private static final Logger CSV_LOGGER = Logger.getLogger(GameTokenProcessor.class.getName() + "_csv");
/* 14 */   public static final Logger STEPPEDTOKEN_LOG = Logger.getLogger(GameTokenProcessor.class.getName() + "_steppedtoken");
/* 15 */   private static final GameTokenProcessor INSTANCE = new GameTokenProcessor();
/*    */   private static final int TOKENS_PR_SEC = 2000;
/*    */   
/*    */   static {
/* 19 */     boolean logTextureLoading = "true".equalsIgnoreCase(System.getProperty("tcg.logging.textureloading"));
/* 20 */     if (!logTextureLoading) {
/* 21 */       STEPPEDTOKEN_LOG.setLevel(Level.WARN);
/*    */     }
/*    */   }
/*    */   
/*    */   public static GameTokenProcessor instance() {
/* 26 */     return INSTANCE;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void process(float timePerFrame) {
/* 34 */     TokenRegister tokenRegister = TokenRegister.instance();
/* 35 */     int iters = Math.max(2, (int)(timePerFrame * 2000.0F));
/* 36 */     for (int i = 0; i < iters; ) {
/* 37 */       Token token = tokenRegister.peekGameToken();
/*    */       
/* 39 */       if (token != null) {
/* 40 */         boolean isSteppedToken = token instanceof SteppedToken;
/*    */         
/* 42 */         token.process();
/*    */         
/* 44 */         if (isSteppedToken) {
/* 45 */           if (((SteppedToken)token).isFinished()) {
/* 46 */             tokenRegister.removeGameToken(token);
/*    */           }
/*    */         } else {
/* 49 */           tokenRegister.removeGameToken(token);
/*    */         } 
/*    */         i++;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void processByTimeLimit(long nanosLeft) {
/* 59 */     TokenRegister tokenRegister = TokenRegister.instance();
/* 60 */     long start = System.nanoTime();
/*    */ 
/*    */     
/* 63 */     long usedTime = 0L;
/* 64 */     long tokenUsedTime = 0L;
/*    */     while (true) {
/* 66 */       long tokenStart = System.nanoTime();
/* 67 */       Token token = tokenRegister.peekGameToken();
/*    */       
/* 69 */       if (token != null) {
/* 70 */         boolean isSteppedToken = token instanceof SteppedToken;
/*    */         
/* 72 */         token.process();
/*    */         
/* 74 */         if (isSteppedToken) {
/* 75 */           if (((SteppedToken)token).isFinished()) {
/* 76 */             tokenRegister.removeGameToken(token);
/*    */           }
/*    */         } else {
/* 79 */           tokenRegister.removeGameToken(token);
/*    */         } 
/*    */ 
/*    */ 
/*    */         
/* 84 */         long now = System.nanoTime();
/* 85 */         usedTime = now - start;
/* 86 */         tokenUsedTime = now - tokenStart;
/* 87 */         if (tokenUsedTime > 50000000L) {
/* 88 */           System.out.println(token.getClass().getSimpleName() + " " + tokenUsedTime);
/*    */         }
/*    */         
/* 91 */         if (usedTime >= nanosLeft / 2L)
/*    */           break; 
/*    */         continue;
/*    */       } 
/*    */       break;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\token\GameTokenProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */