/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.ui.duel.DuelAcceptWindow;
/*    */ import com.funcom.tcg.net.message.DuelInvitationMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelInvitationProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 22 */     DuelInvitationMessage duelInvitationMessage = (DuelInvitationMessage)message;
/*    */     
/* 24 */     TcgGame.setStartDuelMode(false);
/* 25 */     DuelAcceptWindow window = new DuelAcceptWindow(duelInvitationMessage.getChallengerId(), duelInvitationMessage.getDuelId());
/* 26 */     window.setLayer(103);
/* 27 */     BuiSystem.getRootNode().addWindow((BWindow)window);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 33 */     return 247;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DuelInvitationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */