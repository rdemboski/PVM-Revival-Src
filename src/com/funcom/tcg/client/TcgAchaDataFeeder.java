/*    */ package com.funcom.tcg.client;
/*    */ 
/*    */ import com.funcom.errorhandling.AchaDoomsdayErrorHandler;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ 
/*    */ public class TcgAchaDataFeeder
/*    */   implements AchaDoomsdayErrorHandler.AchaBugreportDataFeeder
/*    */ {
/*    */   public String getUsername() {
/* 12 */     if (!MainGameState.isStateInitialized()) {
/* 13 */       return "not player logged on yet";
/*    */     }
/* 15 */     PropNode playerNode = MainGameState.getPlayerNode();
/* 16 */     if (playerNode == null) {
/* 17 */       return "not player logged on yet";
/*    */     }
/*    */     
/* 20 */     LocalClientPlayer playerModel = (LocalClientPlayer)playerNode.getProp();
/* 21 */     if (playerModel == null) {
/* 22 */       return "not player logged on yet";
/*    */     }
/*    */     
/* 25 */     return playerModel.getName();
/*    */   }
/*    */   
/*    */   public String getEmail() {
/* 29 */     if (!MainGameState.isStateInitialized()) {
/* 30 */       return "not player logged on yet";
/*    */     }
/* 32 */     PropNode playerNode = MainGameState.getPlayerNode();
/* 33 */     if (playerNode == null) {
/* 34 */       return "not player logged on yet";
/*    */     }
/*    */     
/* 37 */     LocalClientPlayer playerModel = (LocalClientPlayer)playerNode.getProp();
/* 38 */     if (playerModel == null) {
/* 39 */       return "not player logged on yet";
/*    */     }
/*    */     
/* 42 */     return playerModel.getEmail();
/*    */   }
/*    */   
/*    */   public String getUniverse() {
/* 46 */     return TcgGame.getServerDomain();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\TcgAchaDataFeeder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */