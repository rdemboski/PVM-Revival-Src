/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.rpgengine2.quests.QuestDescription;
/*    */ import com.funcom.rpgengine2.quests.objectives.FinishMissionObjective;
/*    */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*    */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.QuestPickupModel;
/*    */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*    */ import com.funcom.tcg.net.message.AcceptQuestMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ import java.util.ArrayList;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class TCGQuestPickupModel
/*    */   extends TCGQuestWindowModel implements QuestPickupModel {
/*    */   private ClientQuestData clientQuestData;
/*    */   private boolean hadOtherQuest;
/*    */   
/*    */   public TCGQuestPickupModel(QuestModel questModel, ClientQuestData clientQuestData) {
/* 27 */     super(questModel);
/* 28 */     this.clientQuestData = clientQuestData;
/* 29 */     this.hadOtherQuest = hadOtherQuest();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasOtherQuest() {
/* 35 */     return this.hadOtherQuest;
/*    */   }
/*    */   
/*    */   private boolean hadOtherQuest() {
/* 39 */     Set<ClientQuestData> questData = MainGameState.getQuestModel().getQuestDescriptions();
/* 40 */     for (ClientQuestData data : questData) {
/* 41 */       if (!data.isCompleted()) {
/* 42 */         return true;
/*    */       }
/*    */     } 
/* 45 */     return false;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void questRejected(BWindow window) {}
/*    */ 
/*    */   
/*    */   public void questAccepted() {
/* 54 */     AcceptQuestMessage acceptMessage = new AcceptQuestMessage(this.clientQuestData.getQuestId());
/*    */     try {
/* 56 */       NetworkHandler.instance().getIOHandler().send((Message)acceptMessage);
/* 57 */     } catch (InterruptedException e) {
/* 58 */       e.printStackTrace();
/*    */     } 
/* 60 */     MainGameState.getQuestModel().addClientQuestData(this.clientQuestData);
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientQuestData getCurrentActiveSuperquest(QuestModel questModel) {
/* 65 */     return this.clientQuestData;
/*    */   }
/*    */ 
/*    */   
/*    */   protected ClientQuestData getCurrentMissionDescription(QuestModel questModel, FinishMissionObjective currentMissionObjective) {
/* 70 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(currentMissionObjective.getFinishMission());
/* 71 */     List<ClientObjectiveTracker> trackers = new LinkedList<ClientObjectiveTracker>();
/* 72 */     for (QuestObjective questObjective : questDescription.getQuestObjectives()) {
/* 73 */       trackers.add(new ClientObjectiveTracker(questDescription.getId(), questObjective.getObjectiveId(), 0, questObjective.getAmount(), questObjective));
/*    */     }
/* 75 */     return new ClientQuestData(questDescription.getId(), questDescription.getName(), questDescription.getQuestType().getType(), questDescription.getQuestCategory().getType(), questDescription.getQuestCategoryId(), trackers, new ArrayList(), false, false, "", "", "", "", (questDescription.getMissionCoordinate() != null) ? questDescription.getMissionCoordinate().getMapId() : null, questDescription.getNextQuest());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\TCGQuestPickupModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */