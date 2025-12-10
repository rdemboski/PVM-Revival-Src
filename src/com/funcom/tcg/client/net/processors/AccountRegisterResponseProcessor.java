/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.Localizer;
/*    */ import com.funcom.tcg.client.ui.TCGDialog;
/*    */ import com.funcom.tcg.net.AccountResult;
/*    */ import com.funcom.tcg.net.message.AccountRegisterResponseMessage;
/*    */ import com.jmex.bui.event.ActionEvent;
/*    */ import com.jmex.bui.event.ActionListener;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AccountRegisterResponseProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 23 */     AccountRegisterResponseMessage responseMessage = (AccountRegisterResponseMessage)message;
/*    */ 
/*    */     
/* 26 */     ReopenRegisterWindowHandler closeListener = null;
/* 27 */     if (responseMessage.getResult() == AccountResult.OK) {
/* 28 */       String textKey = "accountregisterdialog.ok";
/* 29 */       MainGameState.getPlayerModel().getSubscriptionState().setRegistered(true);
/* 30 */       TcgGame.setUserName(responseMessage.getName());
/*    */ 
/*    */ 
/*    */     
/*    */     }
/*    */     else {
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 40 */       String textKey = "accountregisterdialog.error";
/* 41 */       closeListener = new ReopenRegisterWindowHandler();
/* 42 */       TCGDialog.showMessage("accountregisterdialog.title", textKey, (Localizer)MainGameState.getInstance(), closeListener);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 49 */     return 15;
/*    */   }
/*    */   
/*    */   private static class ReopenRegisterWindowHandler
/*    */     implements ActionListener {
/*    */     public void actionPerformed(ActionEvent event) {
/* 55 */       MainGameState.getInstance().showRegisterWindow("REOPEN_BY_NETWORK_ERROR");
/*    */     }
/*    */     
/*    */     private ReopenRegisterWindowHandler() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\AccountRegisterResponseProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */