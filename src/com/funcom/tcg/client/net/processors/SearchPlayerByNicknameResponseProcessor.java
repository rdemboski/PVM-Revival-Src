/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.friend.FriendModel;
/*    */ import com.funcom.tcg.net.message.SearchPlayerByNicknameResponseMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SearchPlayerByNicknameResponseProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 19 */     if (TcgGame.isChatEnabled()) {
/* 20 */       SearchPlayerByNicknameResponseMessage searchPlayerByNicknameResponseMessage = (SearchPlayerByNicknameResponseMessage)message;
/* 21 */       Integer friendId = searchPlayerByNicknameResponseMessage.getFriendId();
/* 22 */       String nickname = searchPlayerByNicknameResponseMessage.getFriendNickname();
/* 23 */       FriendModel friendModel = MainGameState.getFriendModel();
/* 24 */       friendModel.searchPlayerResult(friendId.intValue(), nickname);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 30 */     return 76;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\SearchPlayerByNicknameResponseProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */