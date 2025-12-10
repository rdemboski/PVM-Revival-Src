/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.tcg.client.state.TcgGameState;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MainGameStateActivationLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 15 */   TcgGameState MGS = null;
/*    */   boolean Activate = true;
/*    */   
/*    */   public MainGameStateActivationLMToken(TcgGameState MGS, boolean Activate) {
/* 19 */     this.MGS = MGS;
/* 20 */     this.Activate = Activate;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processRequestAssets() {
/* 25 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processWaitingAssets() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 35 */     this.MGS.activate(this.Activate);
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\MainGameStateActivationLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */