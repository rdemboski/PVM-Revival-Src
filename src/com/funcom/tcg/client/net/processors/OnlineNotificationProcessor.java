/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.friend.FriendModel;
/*    */ import com.funcom.tcg.net.Friend;
/*    */ import com.funcom.tcg.net.message.OnlineNotificationMessage;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OnlineNotificationProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 22 */     OnlineNotificationMessage onlineNotificationMessage = (OnlineNotificationMessage)message;
/* 23 */     int friendId = onlineNotificationMessage.getPlayerId();
/* 24 */     Boolean online = onlineNotificationMessage.isOnline();
/*    */     
/* 26 */     FriendModel friendModel = MainGameState.getFriendModel();
/* 27 */     Map<Integer, Friend> friendsList = friendModel.getFriendsList();
/*    */     
/* 29 */     Set<Integer> playerIds = friendsList.keySet();
/*    */     
/* 31 */     if (playerIds.contains(Integer.valueOf(friendId))) {
/*    */       
/* 33 */       Friend friend = friendsList.get(Integer.valueOf(friendId));
/*    */       
/* 35 */       if (friend != null) {
/* 36 */         if (friend.isOnline() == online) {
/*    */           return;
/*    */         }
/* 39 */         friend.setOnline(online);
/* 40 */         friendsList.put(Integer.valueOf(friendId), friend);
/* 41 */         if (friend.isOnline().booleanValue()) {
/* 42 */           DfxTextWindowManager.instance().getWindow("friends").showText(friend.getNickname() + " is now online");
/*    */         }
/*    */         
/* 45 */         friendModel.fireModelChanged();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 53 */     return 83;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\OnlineNotificationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */