/*    */ package com.funcom.tcg.client.state;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.server.common.LocalGameClient;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.MainGameStateActivationLMToken;
/*    */ import com.jmex.game.state.GameState;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class TcgGameState
/*    */   extends GameState
/*    */ {
/*    */   private boolean initialized = false;
/* 16 */   private TcgBasicPassManager passManager = new TcgBasicPassManager();
/*    */ 
/*    */ 
/*    */   
/*    */   public void setActive(boolean b) {
/* 21 */     if (LoadingManager.USE) {
/* 22 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new MainGameStateActivationLMToken(this, b), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_MAP);
/*    */     } else {
/*    */       
/* 25 */       activate(b);
/*    */     } 
/*    */   }
/*    */   
/*    */   public void activate(boolean b) {
/* 30 */     super.setActive(b);
/*    */     
/* 32 */     if (b && !this.initialized) {
/* 33 */       initialize();
/* 34 */       this.initialized = true;
/*    */     } 
/*    */     
/* 37 */     if (b) {
/* 38 */       activated();
/*    */     } else {
/* 40 */       deactivated();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected abstract void initialize();
/*    */   
/*    */   protected abstract void activated();
/*    */   
/*    */   protected abstract void deactivated();
/*    */   
/*    */   public abstract void notifyNetworkDisconnect(LocalGameClient.DisconnectReason paramDisconnectReason);
/*    */   
/*    */   public void setName(String s) {
/* 54 */     throw new IllegalStateException("Client game states have constant names.");
/*    */   }
/*    */   
/*    */   public TcgBasicPassManager getPassManager() {
/* 58 */     return this.passManager;
/*    */   }
/*    */   
/*    */   public boolean isInitialized() {
/* 62 */     return this.initialized;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\TcgGameState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */