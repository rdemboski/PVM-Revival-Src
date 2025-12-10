/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.SearchPlayerToTellResponseMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchPlayerToTellResponseProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 18 */     if (TcgGame.isChatEnabled()) {
/* 19 */       SearchPlayerToTellResponseMessage searchPlayerToTellResponseMessage = (SearchPlayerToTellResponseMessage)message;
/* 20 */       Integer tellToId = searchPlayerToTellResponseMessage.getFriendId();
/* 21 */       String tellToNickname = searchPlayerToTellResponseMessage.getFriendNickname();
/* 22 */       String messageToTell = searchPlayerToTellResponseMessage.getMessageToTell();
/* 23 */       String name = searchPlayerToTellResponseMessage.getName();
/*    */       
/* 25 */       MainGameState.getChatNetworkController().sendPrivateMessage(messageToTell, tellToId.intValue(), name, tellToNickname);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 31 */     return 78;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\SearchPlayerToTellResponseProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */