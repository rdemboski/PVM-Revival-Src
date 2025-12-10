/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.chat.ChatNetworkController;
/*    */ import com.funcom.tcg.net.message.TcgChatMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class TcgChatProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 17 */     TcgChatMessage tcgChatMessage = (TcgChatMessage)message;
/* 18 */     MainGameState.getChatNetworkController().newMessageRecieved((int)tcgChatMessage.getClientChatId(), tcgChatMessage.getMessage(), ChatNetworkController.MessageFrom.siteCasting);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 23 */     return 56;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\TcgChatProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */