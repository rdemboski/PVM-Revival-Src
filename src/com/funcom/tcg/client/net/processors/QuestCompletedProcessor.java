/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.rpgengine2.quests.QuestDescription;
/*    */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*    */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.QuestCompletedLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*    */ import com.funcom.tcg.net.message.QuestCompletedMessage;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuestCompletedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 31 */     if (LoadingManager.USE) {
/* 32 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new QuestCompletedLMToken((QuestCompletedMessage)message), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */     } else {
/*    */       
/* 35 */       QuestCompletedMessage questCompletedMessage = (QuestCompletedMessage)message;
/* 36 */       QuestModel questModel = MainGameState.getQuestModel();
/*    */ 
/*    */       
/* 39 */       if (questCompletedMessage.getMessageInformationType() == QuestCompletedMessage.MessageInformationType.COMPLETED.getId()) {
/* 40 */         questModel.completeQuest(questCompletedMessage.getQuestId(), questCompletedMessage.isClaimed());
/*    */       } else {
/* 42 */         questModel.completeQuest(questCompletedMessage.getQuestId(), questCompletedMessage.isClaimed());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private ClientQuestData addClientQuestData(QuestCompletedMessage questCompletedMessage, QuestModel questModel) {
/* 59 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questCompletedMessage.getQuestId());
/*    */     
/* 61 */     List<ClientObjectiveTracker> questObjStrings = new ArrayList<ClientObjectiveTracker>();
/* 62 */     for (int i = 0; i < questDescription.getQuestObjectives().size(); i++) {
/* 63 */       QuestObjective questObjective = questDescription.getQuestObjectives().get(i);
/* 64 */       int ammount = questObjective.getAmount();
/* 65 */       ClientObjectiveTracker tracker = new ClientObjectiveTracker(questDescription.getId(), questObjective.getObjectiveId(), ammount, ammount, questObjective);
/*    */ 
/*    */       
/* 68 */       questObjStrings.add(tracker);
/*    */     } 
/*    */     
/* 71 */     ClientQuestData client = new ClientQuestData(questCompletedMessage.getQuestId(), questDescription.getName(), questDescription.getQuestType().getType(), questDescription.getQuestCategory().getType(), questDescription.getQuestCategoryId(), questObjStrings, new ArrayList(), true, true, "fixme!", "fixme!", "fixme!", "fixme!", (questDescription.getMissionCoordinate() != null) ? questDescription.getMissionCoordinate().getMapId() : null, questDescription.getNextQuest());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 78 */     if (questDescription.isTrackable()) {
/* 79 */       questModel.addClientQuestData(client);
/*    */     }
/*    */     
/* 82 */     return client;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 88 */     return 40;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\QuestCompletedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */