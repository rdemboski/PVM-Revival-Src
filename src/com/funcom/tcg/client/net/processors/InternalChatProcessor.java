/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.chat.ChatNetworkController;
/*    */ import com.funcom.tcg.net.message.InternalChatMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InternalChatProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 18 */     InternalChatMessage internalChatMessage = (InternalChatMessage)message;
/* 19 */     MainGameState.getChatNetworkController().newMessageRecieved(internalChatMessage.getPlayerId(), internalChatMessage.getMessage(), ChatNetworkController.MessageFrom.InternalCannedCHat);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 24 */     return 82;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\InternalChatProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */