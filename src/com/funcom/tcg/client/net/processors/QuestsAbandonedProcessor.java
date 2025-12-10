/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.QuestsAbandonedLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*    */ import com.funcom.tcg.net.message.QuestsAbandonedMessage;
/*    */ import java.util.Iterator;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class QuestsAbandonedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 23 */     if (LoadingManager.USE) {
/* 24 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new QuestsAbandonedLMToken((QuestsAbandonedMessage)message), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */     }
/*    */     else {
/*    */       
/* 28 */       QuestsAbandonedMessage questsAbandonedMessage = (QuestsAbandonedMessage)message;
/* 29 */       QuestModel questModel = MainGameState.getQuestModel();
/* 30 */       Iterator<ClientQuestData> questDescriptionIter = questModel.getQuestDescriptions().iterator();
/* 31 */       while (questDescriptionIter.hasNext()) {
/* 32 */         ClientQuestData clientQuestData = questDescriptionIter.next();
/* 33 */         if (questsAbandonedMessage.getQuestIds().contains(clientQuestData.getQuestId())) {
/* 34 */           questDescriptionIter.remove();
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 232;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\QuestsAbandonedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */