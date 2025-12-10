/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.QuestCompletedListLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*    */ import com.funcom.tcg.net.message.QuestCompletedListMessage;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuestCompletedListProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 25 */     if (LoadingManager.USE) {
/* 26 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new QuestCompletedListLMToken((QuestCompletedListMessage)message), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */     }
/*    */     else {
/*    */       
/* 30 */       QuestCompletedListMessage questCompletedMessage = (QuestCompletedListMessage)message;
/* 31 */       QuestModel questModel = MainGameState.getQuestModel();
/* 32 */       if (!questCompletedMessage.isShow()) {
/* 33 */         questModel.setAcceptAchievements(false);
/*    */       }
/* 35 */       List<String> questList = questCompletedMessage.getQuestIds();
/* 36 */       List<Boolean> claimedList = questCompletedMessage.getClaimedList();
/* 37 */       for (int i = 0; i < questList.size(); i++) {
/* 38 */         questModel.completeQuest(questList.get(i), ((Boolean)claimedList.get(i)).booleanValue());
/*    */       }
/*    */       
/* 41 */       questModel.setAcceptAchievements(true);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 47 */     return 72;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\QuestCompletedListProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */