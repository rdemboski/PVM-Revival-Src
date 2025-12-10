/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.input.DefaultActionInteractActionHandler;
/*     */ import com.funcom.gameengine.model.input.MouseOver;
/*     */ import com.funcom.gameengine.model.input.UserActionHandler;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.view.OverheadIcons;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.actions.QuestGiverMouseOver;
/*     */ import com.funcom.tcg.client.actions.TalkAction;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.net.creaturebuilders.QuestGiverBuilder;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud2.TCGQuestPickupModel;
/*     */ import com.funcom.tcg.client.ui.hud2.TCGQuestWindowModel;
/*     */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*     */ import com.funcom.tcg.net.message.QuestCompleteMessage;
/*     */ import com.jmex.bui.BWindow;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class QuestFinishModel extends TCGQuestWindowModel {
/*     */   private int questGiverId;
/*     */   
/*     */   public QuestFinishModel(QuestModel questModel, int questGiverId, String questHandIn) {
/*  38 */     super(questModel);
/*  39 */     this.questGiverId = questGiverId;
/*  40 */     this.questHandIn = questHandIn;
/*     */   }
/*     */   private String questHandIn;
/*     */   public void finish() {
/*  44 */     QuestCompleteMessage questCompleteMessage = new QuestCompleteMessage(this.questHandIn, this.questGiverId);
/*     */     try {
/*  46 */       NetworkHandler.instance().getIOHandler().send((Message)questCompleteMessage);
/*  47 */     } catch (InterruptedException e) {
/*  48 */       e.printStackTrace();
/*     */     } 
/*  50 */     PropNode questGiver = TcgGame.getMonsterRegister().getPropNode(Integer.valueOf(this.questGiverId));
/*  51 */     if (questGiver != null) {
/*  52 */       questGiver.getBasicEffectsNode().setState(OverheadIcons.State.NONE);
/*  53 */       InteractibleProp prop = (InteractibleProp)questGiver.getProp();
/*  54 */       prop.addAction((Action)new TalkAction());
/*  55 */       QuestGiverMouseOver questGiverMouseOver = new QuestGiverMouseOver(MainGameState.getMouseCursorSetter(), prop, QuestGiverBuilder.QUESTGIVER_TINT_COLOR);
/*     */       
/*  57 */       questGiverMouseOver.setOwnerPropNode(questGiver);
/*  58 */       DefaultActionInteractActionHandler actionHanlder = new DefaultActionInteractActionHandler(prop, (Creature)MainGameState.getPlayerNode().getProp(), "talk", MainGameState.getCollisionDataProvider().getCollisionRoot(), (MouseOver)questGiverMouseOver);
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  63 */       questGiver.setActionHandler((UserActionHandler)actionHanlder);
/*     */     } 
/*     */     
/*  66 */     if (this.currentSuperquestDesc.getNextQuest() != null && !this.questModel.hasCompletedQuest(this.currentSuperquestDesc.getNextQuest()))
/*     */     {
/*  68 */       startNextQuest(this.currentSuperquestDesc.getNextQuest(), questGiver);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void startNextQuest(String nextQuest, PropNode questGiver) {
/*  74 */     ClientQuestData clientQuestData = this.questModel.getQuestDescriptionByIds(nextQuest);
/*  75 */     String questGiverName = "";
/*  76 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(nextQuest);
/*     */ 
/*     */     
/*  79 */     if (!questDescription.canAcquire((RpgEntity)MainGameState.getPlayerModel())) {
/*     */       return;
/*     */     }
/*  82 */     if (clientQuestData == null) {
/*  83 */       List<ClientObjectiveTracker> trackers = new ArrayList<ClientObjectiveTracker>();
/*     */ 
/*     */       
/*  86 */       for (QuestObjective questObjective : questDescription.getQuestObjectives()) {
/*  87 */         int amount = 0;
/*  88 */         trackers.add(new ClientObjectiveTracker(nextQuest, questObjective.getObjectiveId(), amount, questObjective.getAmount(), questObjective));
/*     */       } 
/*     */ 
/*     */       
/*  92 */       clientQuestData = new ClientQuestData(nextQuest, questDescription.getName(), questDescription.getQuestType().getType(), questDescription.getQuestCategory().getType(), questDescription.getQuestCategoryId(), trackers, questDescription.getQuestRewardDataContainerList(), false, false, questGiverName, getQuestLocation(), questDescription.getIconPath(), questDescription.getQuestText(), (questDescription.getMissionCoordinate() != null) ? questDescription.getMissionCoordinate().getMapId() : null, questDescription.getNextQuest());
/*     */     } 
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
/*     */     
/* 108 */     addConfirmDialog(clientQuestData, questGiver);
/*     */   }
/*     */   
/*     */   private void addConfirmDialog(ClientQuestData clientQuestData, PropNode questGiver) {
/* 112 */     BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, "gui/peeler/window_quest_accept.xml", CacheType.NOT_CACHED);
/*     */     
/* 114 */     WorldCoordinate questGiverCoord = (questGiver == null) ? MainGameState.getPlayerNode().getPosition().clone() : questGiver.getPosition().clone();
/* 115 */     QuestPickUpWindow2 pickUpWindow2 = new QuestPickUpWindow2("accept window", bananaPeel, TcgGame.getResourceManager(), (QuestPickupModel)new TCGQuestPickupModel(MainGameState.getQuestModel(), clientQuestData), MainGameState.getToolTipManager(), questGiverCoord);
/*     */ 
/*     */ 
/*     */     
/* 119 */     PanelManager.getInstance().addWindow((BWindow)pickUpWindow2);
/* 120 */     pickUpWindow2.refresh(false);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\QuestFinishModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */