/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.DuelFinishMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DuelFinishProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 19 */     DuelFinishMessage duelFinishMessage = (DuelFinishMessage)message;
/*    */     
/* 21 */     if (MainGameState.getDuelHealthBarWindow() != null) {
/* 22 */       MainGameState.getDuelHealthBarWindow().updateOutcome(duelFinishMessage.isWon());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 29 */     return 251;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DuelFinishProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */