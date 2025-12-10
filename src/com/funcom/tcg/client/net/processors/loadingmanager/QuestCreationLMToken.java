/*     */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.hud.QuestPickUpWindow2;
/*     */ import com.funcom.tcg.client.ui.hud.QuestPickupModel;
/*     */ import com.funcom.tcg.client.ui.hud.QuestTextType;
/*     */ import com.funcom.tcg.client.ui.hud2.TCGQuestPickupModel;
/*     */ import com.funcom.tcg.net.message.QuestCreationMessage;
/*     */ import com.jmex.bui.BWindow;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestCreationLMToken
/*     */   extends LoadingManagerToken
/*     */ {
/*  32 */   QuestCreationMessage questCreationMessage = null;
/*     */   
/*     */   public QuestCreationLMToken(QuestCreationMessage message) {
/*  35 */     this.questCreationMessage = message;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int update() {
/*  41 */     ClientQuestData clientQuestData = MainGameState.getQuestModel().getQuestDescriptionByIds(this.questCreationMessage.getQuestId());
/*     */     
/*  43 */     String questGiverName = "";
/*  44 */     if (!this.questCreationMessage.getQuestGiverId().isEmpty()) {
/*  45 */       MonsterDescription monsterDescription = TcgGame.getRpgLoader().getMonsterManager().getDescription(this.questCreationMessage.getQuestGiverId());
/*  46 */       if (monsterDescription == null)
/*  47 */         throw new NullPointerException("No monster exists for id: " + this.questCreationMessage.getQuestGiverId() + " referenced in quest: " + this.questCreationMessage.getQuestId()); 
/*  48 */       questGiverName = monsterDescription.getName();
/*     */     } 
/*     */     
/*  51 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(this.questCreationMessage.getQuestId());
/*     */     
/*  53 */     if (questDescription == null)
/*  54 */       throw new NullPointerException("no quest exists for quest id: " + this.questCreationMessage.getQuestId()); 
/*  55 */     if (clientQuestData == null) {
/*  56 */       List<ClientObjectiveTracker> trackers = new ArrayList<ClientObjectiveTracker>();
/*     */       
/*  58 */       for (int i = 0; i < this.questCreationMessage.getQuestObjectivesIds().size(); i++) {
/*  59 */         String objective = this.questCreationMessage.getQuestObjectivesIds().get(i);
/*  60 */         int amount = ((Integer)this.questCreationMessage.getCurrentAmounts().get(i)).intValue();
/*  61 */         QuestObjective questObjective = questDescription.getQuestObjectiveById(objective);
/*  62 */         questObjective.setGotoPositions(this.questCreationMessage.getGotoPositions(questObjective.getGoToId()));
/*  63 */         trackers.add(new ClientObjectiveTracker(this.questCreationMessage.getQuestId(), objective, amount, questObjective.getAmount(), questObjective));
/*     */       } 
/*     */ 
/*     */       
/*  67 */       clientQuestData = new ClientQuestData(this.questCreationMessage.getQuestId(), questDescription.getName(), questDescription.getQuestType().getType(), questDescription.getQuestCategory().getType(), questDescription.getQuestCategoryId(), trackers, questDescription.getQuestRewardDataContainerList(), false, false, questGiverName, this.questCreationMessage.getLocation(), questDescription.getIconPath(), questDescription.getQuestText(), (questDescription.getMissionCoordinate() != null) ? questDescription.getMissionCoordinate().getMapId() : null, questDescription.getNextQuest());
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
/*     */     }
/*  82 */     else if (questDescription.isRepeatable() && !this.questCreationMessage.doConfirm()) {
/*     */       
/*  84 */       clientQuestData.setCompleted(false);
/*  85 */       for (ClientObjectiveTracker objective : clientQuestData.getQuestObjectives()) {
/*  86 */         objective.setCompleteValue(Integer.valueOf(0));
/*     */       }
/*  88 */       MainGameState.getQuestModel().getQuestDescriptions().remove(clientQuestData);
/*     */     } 
/*     */     
/*  91 */     if (this.questCreationMessage.getMessageCreationType() == QuestCreationMessage.MessageCreationType.NEW.getId()) {
/*  92 */       if (this.questCreationMessage.doConfirm()) {
/*     */         
/*  94 */         addConfirmDialog(clientQuestData);
/*     */       } else {
/*  96 */         MainGameState.getQuestModel().addClientQuestData(clientQuestData);
/*  97 */         addTextDataToScreen(clientQuestData);
/*     */       } 
/*  99 */     } else if (MainGameState.getQuestModel().getQuestDescriptionByIds(this.questCreationMessage.getQuestId()) == null) {
/* 100 */       MainGameState.getQuestModel().addClientQuestData(clientQuestData);
/*     */     } 
/*     */ 
/*     */     
/* 104 */     return 3;
/*     */   }
/*     */   
/*     */   private void addConfirmDialog(ClientQuestData clientQuestData) {
/* 108 */     BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_quest_accept.xml", CacheType.NOT_CACHED);
/*     */     
/* 110 */     QuestPickUpWindow2 pickUpWindow2 = new QuestPickUpWindow2("accept window", bananaPeel, TcgGame.getResourceManager(), (QuestPickupModel)new TCGQuestPickupModel(MainGameState.getQuestModel(), clientQuestData), MainGameState.getToolTipManager(), MainGameState.getPlayerNode().getPosition().clone());
/*     */ 
/*     */ 
/*     */     
/* 114 */     PanelManager.getInstance().addWindow((BWindow)pickUpWindow2);
/* 115 */     pickUpWindow2.refresh(true);
/*     */   }
/*     */   
/*     */   private void addTextDataToScreen(ClientQuestData clientQuestData) {
/* 119 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(clientQuestData.getQuestId());
/*     */ 
/*     */     
/* 122 */     String window = "quest";
/*     */ 
/*     */     
/* 125 */     if (TcgGame.isTutorialMode()) {
/* 126 */       if (MainGameState.getNoahTutorialWindow() != null) {
/* 127 */         MainGameState.getNoahTutorialWindow().setText(questDescription.getStartText(), QuestTextType.CREATION);
/*     */       } else {
/* 129 */         System.out.println("Trying to access Noah window to add text: " + questDescription.getStartText());
/*     */       } 
/*     */     } else {
/* 132 */       DfxTextWindowManager.instance().getWindow(window).showText(questDescription.getStartText());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\QuestCreationLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */