/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.QuestType;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.rpgengine2.quests.objectives.TCGObjectiveTracker;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud.QuestTextType;
/*     */ import com.funcom.tcg.net.message.QuestObjectiveUpdateMessage;
/*     */ import java.text.MessageFormat;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestObjectiveUpdateLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  22 */   QuestObjectiveUpdateMessage questObjectiveUpdateMessage = null;
/*     */   
/*     */   public QuestObjectiveUpdateLMToken(QuestObjectiveUpdateMessage questObjectiveUpdateMessage) {
/*  25 */     this.questObjectiveUpdateMessage = questObjectiveUpdateMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int update() {
/*  31 */     ClientQuestData questData = MainGameState.getQuestModel().getQuestDescriptionByIds(this.questObjectiveUpdateMessage.getQuestId());
/*     */     
/*  33 */     if (questData == null)
/*  34 */       throw new NullPointerException("No quest data exists for quest: " + this.questObjectiveUpdateMessage.getQuestId() + " when trying to update objective: " + this.questObjectiveUpdateMessage.getObjectiveId() + " has completed quest: " + MainGameState.getQuestModel().getCompletedQuests().contains(this.questObjectiveUpdateMessage.getQuestId())); 
/*  35 */     TCGObjectiveTracker tracker = questData.getObjectiveTrackerById(this.questObjectiveUpdateMessage.getQuestId() + "#" + this.questObjectiveUpdateMessage.getObjectiveId());
/*     */ 
/*     */     
/*  38 */     if (tracker != null) {
/*  39 */       tracker.setTrackAmount(Integer.valueOf(this.questObjectiveUpdateMessage.getNewValue()));
/*     */ 
/*     */       
/*  42 */       MainGameState.getQuestModel().updateQuest(questData.getQuestId());
/*     */       
/*  44 */       if (questData.getQuestType() == QuestType.PROXIMITY.getType() || questData.getQuestType() == QuestType.MISSION.getType()) {
/*     */         
/*  46 */         showTextMessages("mission", this.questObjectiveUpdateMessage, tracker);
/*  47 */         if (tracker.isCompleted())
/*  48 */           checkCompletion("quest", questData); 
/*  49 */       } else if (questData.getQuestType() == QuestType.QUEST.getType()) {
/*  50 */         showTextMessages("quest", this.questObjectiveUpdateMessage, tracker);
/*  51 */         if (tracker.isCompleted()) {
/*  52 */           checkCompletion("quest", questData);
/*     */         }
/*     */       } 
/*     */     } 
/*  56 */     return 3;
/*     */   }
/*     */   
/*     */   private void checkCompletion(String window, ClientQuestData questData) {
/*  60 */     boolean isCompleted = true;
/*  61 */     for (ClientObjectiveTracker clientObjectiveTracker : questData.getQuestObjectives()) {
/*  62 */       isCompleted = (isCompleted && clientObjectiveTracker.isCompleted());
/*     */     }
/*  64 */     if (isCompleted) {
/*  65 */       QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questData.getQuestId());
/*     */       
/*  67 */       if (TcgGame.isTutorialMode()) {
/*  68 */         if (MainGameState.getNoahTutorialWindow() != null) {
/*  69 */           MainGameState.getNoahTutorialWindow().setText(questDescription.getCompletionText(), QuestTextType.COMPLETION);
/*     */         }
/*     */       } else {
/*  72 */         DfxTextWindowManager.instance().getWindow(window).showText(questDescription.getCompletionText());
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void showTextMessages(String window, QuestObjectiveUpdateMessage questObjectiveUpdateMessage, TCGObjectiveTracker tracker) {
/*     */     String message;
/*  79 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questObjectiveUpdateMessage.getQuestId());
/*     */     
/*  81 */     QuestObjective questObjective = questDescription.getQuestObjectiveById(questObjectiveUpdateMessage.getObjectiveId());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  86 */     if (!tracker.isCompleted()) {
/*  87 */       message = questObjective.getUnCompletedObjectiveText();
/*     */     } else {
/*  89 */       message = questObjective.getCompletedObjectiveText();
/*     */     } 
/*     */     
/*  92 */     Object[] values = { Integer.valueOf(tracker.getTrackAmount()), Integer.valueOf(tracker.getCompleteValue() - tracker.getTrackAmount()) };
/*     */ 
/*     */ 
/*     */     
/*  96 */     String formattedText = MessageFormat.format(message, values);
/*     */     
/*  98 */     if (TcgGame.isTutorialMode()) {
/*  99 */       if (MainGameState.getNoahTutorialWindow() != null && 
/* 100 */         tracker.isCompleted()) MainGameState.getNoahTutorialWindow().setText(formattedText, QuestTextType.UPDATE);
/*     */     
/*     */     } else {
/* 103 */       DfxTextWindowManager.instance().getWindow(window).showText(formattedText);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\QuestObjectiveUpdateLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */