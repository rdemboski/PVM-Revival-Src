/*     */ package com.funcom.tcg.client.net.processors;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.QuestType;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.rpgengine2.quests.objectives.TCGObjectiveTracker;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.QuestObjectiveUpdateLMToken;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud.QuestTextType;
/*     */ import com.funcom.tcg.net.message.QuestObjectiveUpdateMessage;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestObjectiveUpdateProcessor
/*     */   implements MessageProcessor
/*     */ {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  33 */     if (LoadingManager.USE) {
/*  34 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new QuestObjectiveUpdateLMToken((QuestObjectiveUpdateMessage)message), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/*  39 */       QuestObjectiveUpdateMessage questObjectiveUpdateMessage = (QuestObjectiveUpdateMessage)message;
/*     */       
/*  41 */       ClientQuestData questData = MainGameState.getQuestModel().getQuestDescriptionByIds(questObjectiveUpdateMessage.getQuestId());
/*     */       
/*  43 */       TCGObjectiveTracker tracker = questData.getObjectiveTrackerById(questObjectiveUpdateMessage.getQuestId() + "#" + questObjectiveUpdateMessage.getObjectiveId());
/*     */ 
/*     */       
/*  46 */       if (tracker != null) {
/*  47 */         tracker.setTrackAmount(Integer.valueOf(questObjectiveUpdateMessage.getNewValue()));
/*     */         
/*  49 */         if (questData.getQuestType() == QuestType.PROXIMITY.getType() || questData.getQuestType() == QuestType.MISSION.getType()) {
/*     */           
/*  51 */           showTextMessages("mission", questObjectiveUpdateMessage, tracker);
/*  52 */           if (tracker.isCompleted())
/*  53 */             checkCompletion("quest", questData); 
/*  54 */         } else if (questData.getQuestType() == QuestType.QUEST.getType()) {
/*  55 */           showTextMessages("quest", questObjectiveUpdateMessage, tracker);
/*  56 */           if (tracker.isCompleted())
/*  57 */             checkCompletion("quest", questData); 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkCompletion(String window, ClientQuestData questData) {
/*  64 */     boolean isCompleted = true;
/*  65 */     for (ClientObjectiveTracker clientObjectiveTracker : questData.getQuestObjectives()) {
/*  66 */       isCompleted = (isCompleted && clientObjectiveTracker.isCompleted());
/*     */     }
/*  68 */     if (isCompleted) {
/*  69 */       QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questData.getQuestId());
/*     */       
/*  71 */       if (TcgGame.isTutorialMode()) {
/*  72 */         if (MainGameState.getNoahTutorialWindow() != null) {
/*  73 */           MainGameState.getNoahTutorialWindow().setText(questDescription.getCompletionText(), QuestTextType.COMPLETION);
/*     */         }
/*     */       } else {
/*  76 */         DfxTextWindowManager.instance().getWindow(window).showText(questDescription.getCompletionText());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void showTextMessages(String window, QuestObjectiveUpdateMessage questObjectiveUpdateMessage, TCGObjectiveTracker tracker) {
/*     */     String message;
/*  83 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questObjectiveUpdateMessage.getQuestId());
/*     */     
/*  85 */     QuestObjective questObjective = questDescription.getQuestObjectiveById(questObjectiveUpdateMessage.getObjectiveId());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  90 */     if (!tracker.isCompleted()) {
/*  91 */       message = questObjective.getUnCompletedObjectiveText();
/*     */     } else {
/*  93 */       message = questObjective.getCompletedObjectiveText();
/*     */     } 
/*     */     
/*  96 */     Object[] values = { Integer.valueOf(tracker.getTrackAmount()), Integer.valueOf(tracker.getCompleteValue() - tracker.getTrackAmount()) };
/*     */ 
/*     */ 
/*     */     
/* 100 */     String formattedText = MessageFormat.format(message, values);
/*     */     
/* 102 */     if (TcgGame.isTutorialMode()) {
/* 103 */       if (MainGameState.getNoahTutorialWindow() != null && 
/* 104 */         tracker.isCompleted()) MainGameState.getNoahTutorialWindow().setText(formattedText, QuestTextType.UPDATE);
/*     */     
/*     */     } else {
/* 107 */       DfxTextWindowManager.instance().getWindow(window).showText(formattedText);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/* 113 */     return 43;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\QuestObjectiveUpdateProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */