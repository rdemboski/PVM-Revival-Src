/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.conanchat.DefaultChatUser;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.ClientChatController;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.friend.FriendModel;
/*    */ import com.funcom.tcg.net.Friend;
/*    */ import com.funcom.tcg.net.message.FriendsMapMessage;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ public class FriendsMapProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 22 */     FriendsMapMessage friendsMapMessage = (FriendsMapMessage)message;
/*    */     
/* 24 */     if (TcgGame.isChatEnabled()) {
/* 25 */       DefaultChatUser chatUser = MainGameState.getChatNetworkController().getChatOutput().getChatUser();
/* 26 */       FriendModel friendModel = MainGameState.getFriendModel();
/*    */       
/* 28 */       Map<Integer, Friend> map = friendsMapMessage.getFriendsMap();
/* 29 */       Set<Integer> set = map.keySet();
/*    */       
/* 31 */       friendModel.getFriendsList().clear();
/*    */       
/* 33 */       for (Integer clientId : set) {
/* 34 */         Friend friend = map.get(clientId);
/* 35 */         chatUser.addFriendWithNickname(clientId.intValue(), friend.getNickname());
/* 36 */         friendModel.getFriendsList().put(clientId, friend);
/*    */       } 
/*    */       
/* 39 */       friendModel.fireModelChanged();
/*    */     } 
/* 41 */     ClientChatController chatController = MainGameState.getPlayerModel().getChatController();
/* 42 */     if (chatController != null) {
/* 43 */       chatController.activate();
/*    */     }
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 48 */     return 61;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\FriendsMapProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */