/*     */ package com.funcom.tcg.client.net.processors;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.net.CreatureData;
/*     */ import com.funcom.tcg.client.net.MessageProcessor;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.ConfirmDialogLMToken;
/*     */ import com.funcom.tcg.client.net.processors.loadingmanager.QuestCreationLMToken;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.hud.QuestPickUpWindow2;
/*     */ import com.funcom.tcg.client.ui.hud.QuestPickupModel;
/*     */ import com.funcom.tcg.client.ui.hud.QuestTextType;
/*     */ import com.funcom.tcg.client.ui.hud.SubscribeWindow;
/*     */ import com.funcom.tcg.client.ui.hud2.TCGQuestPickupModel;
/*     */ import com.funcom.tcg.net.message.QuestCreationMessage;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestCreationProcessor
/*     */   implements MessageProcessor
/*     */ {
/*     */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/*  42 */     if (LoadingManager.USE) {
/*  43 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new QuestCreationLMToken((QuestCreationMessage)message), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*     */     }
/*     */     else {
/*     */       
/*  47 */       QuestCreationMessage questCreationMessage = (QuestCreationMessage)message;
/*     */       
/*  49 */       ClientQuestData clientQuestData = MainGameState.getQuestModel().getQuestDescriptionByIds(questCreationMessage.getQuestId());
/*     */       
/*  51 */       String questGiverName = "";
/*  52 */       if (!questCreationMessage.getQuestGiverId().isEmpty()) {
/*  53 */         questGiverName = TcgGame.getRpgLoader().getMonsterManager().getDescription(questCreationMessage.getQuestGiverId()).getName();
/*     */       }
/*     */       
/*  56 */       QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questCreationMessage.getQuestId());
/*     */       
/*  58 */       if (clientQuestData == null) {
/*  59 */         List<ClientObjectiveTracker> trackers = new ArrayList<ClientObjectiveTracker>();
/*     */         
/*  61 */         for (int i = 0; i < questCreationMessage.getQuestObjectivesIds().size(); i++) {
/*  62 */           String objective = questCreationMessage.getQuestObjectivesIds().get(i);
/*  63 */           int amount = ((Integer)questCreationMessage.getCurrentAmounts().get(i)).intValue();
/*  64 */           QuestObjective questObjective = questDescription.getQuestObjectiveById(objective);
/*  65 */           questObjective.setGotoPositions(questCreationMessage.getGotoPositions(questObjective.getGoToId()));
/*  66 */           trackers.add(new ClientObjectiveTracker(questCreationMessage.getQuestId(), objective, amount, questObjective.getAmount(), questObjective));
/*     */         } 
/*     */ 
/*     */         
/*  70 */         clientQuestData = new ClientQuestData(questCreationMessage.getQuestId(), questDescription.getName(), questDescription.getQuestType().getType(), questDescription.getQuestCategory().getType(), questDescription.getQuestCategoryId(), trackers, questDescription.getQuestRewardDataContainerList(), false, false, questGiverName, questCreationMessage.getLocation(), questDescription.getIconPath(), questDescription.getQuestText(), (questDescription.getMissionCoordinate() != null) ? questDescription.getMissionCoordinate().getMapId() : null, questDescription.getNextQuest());
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*  85 */       else if (questDescription.isRepeatable() && !questCreationMessage.doConfirm()) {
/*     */         
/*  87 */         clientQuestData.setCompleted(false);
/*  88 */         for (ClientObjectiveTracker objective : clientQuestData.getQuestObjectives()) {
/*  89 */           objective.setCompleteValue(Integer.valueOf(0));
/*     */         }
/*  91 */         MainGameState.getQuestModel().getQuestDescriptions().remove(clientQuestData);
/*     */       } 
/*     */       
/*  94 */       if (questCreationMessage.getMessageCreationType() == QuestCreationMessage.MessageCreationType.NEW.getId()) {
/*  95 */         if (questCreationMessage.doConfirm()) {
/*  96 */           if (LoadingManager.USE) {
/*  97 */             LoadingManager.INSTANCE.submit((LoadingManagerToken)new ConfirmDialogLMToken(clientQuestData));
/*     */           } else {
/*  99 */             addConfirmDialog(clientQuestData);
/*     */           } 
/*     */         } else {
/* 102 */           MainGameState.getQuestModel().addClientQuestData(clientQuestData);
/* 103 */           addTextDataToScreen(clientQuestData);
/*     */         } 
/* 105 */       } else if (MainGameState.getQuestModel().getQuestDescriptionByIds(questCreationMessage.getQuestId()) == null) {
/* 106 */         MainGameState.getQuestModel().addClientQuestData(clientQuestData);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void addTextDataToScreen(ClientQuestData clientQuestData) {
/* 113 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(clientQuestData.getQuestId());
/*     */ 
/*     */     
/* 116 */     String window = "quest";
/*     */ 
/*     */     
/* 119 */     if (TcgGame.isTutorialMode()) {
/* 120 */       if (MainGameState.getNoahTutorialWindow() != null) {
/* 121 */         MainGameState.getNoahTutorialWindow().setText(questDescription.getStartText(), QuestTextType.CREATION);
/*     */       } else {
/* 123 */         System.out.println("Trying to access Noah window to add text: " + questDescription.getStartText());
/*     */       } 
/*     */     } else {
/* 126 */       DfxTextWindowManager.instance().getWindow(window).showText(questDescription.getStartText());
/* 127 */       if (!MainGameState.isPlayerSubscriber() && questDescription.getId().equals("028_rb_lavalair-quest0")) {
/* 128 */         SubscribeWindow subscribeWindow = new SubscribeWindow(TcgGame.getResourceManager(), MainGameState.isPlayerRegistered(), MainGameState.isPlayerRegistered() ? "subscribedialog.button.subscribe" : "quitwindow.askregister.ok", "subscribedialog.button.cancel", "popup.subscribe.finalquest");
/*     */ 
/*     */ 
/*     */         
/* 132 */         subscribeWindow.setLayer(101);
/* 133 */         BuiSystem.getRootNode().addWindow((BWindow)subscribeWindow);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addConfirmDialog(ClientQuestData clientQuestData) {
/* 139 */     BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_quest_accept.xml", CacheType.NOT_CACHED);
/*     */     
/* 141 */     QuestPickUpWindow2 pickUpWindow2 = new QuestPickUpWindow2("accept window", bananaPeel, TcgGame.getResourceManager(), (QuestPickupModel)new TCGQuestPickupModel(MainGameState.getQuestModel(), clientQuestData), MainGameState.getToolTipManager(), MainGameState.getPlayerNode().getPosition().clone());
/*     */ 
/*     */ 
/*     */     
/* 145 */     PanelManager.getInstance().addWindow((BWindow)pickUpWindow2);
/* 146 */     pickUpWindow2.refresh(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getMessageType() {
/* 151 */     return 39;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\QuestCreationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */