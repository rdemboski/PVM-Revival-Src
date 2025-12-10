/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.commons.StringUtils;
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.PairedWCandBoolean;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.model.props.MultipleTargetsModel;
/*     */ import com.funcom.gameengine.utils.LoadingScreenListener;
/*     */ import com.funcom.rpgengine2.quests.objectives.FinishMissionObjective;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*     */ import com.funcom.tcg.client.ui.quest2.MissionObjective;
/*     */ import com.funcom.tcg.client.ui.quest2.TCGClientMission;
/*     */ import com.funcom.tcg.token.TCGWorld;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TCGGoToObjectiveModel
/*     */   implements MultipleTargetsModel, LoadingScreenListener, QuestModel.QuestChangeListener
/*     */ {
/*     */   private QuestModel questModel;
/*     */   private LinkedList<MissionObjective> currentMissionObjectives;
/*     */   private List<MultipleTargetsModel.SwitchTargetListener> eventListeners;
/*     */   private List<MultipleTargetsModel.TargetData> targetDatas;
/*     */   private MultipleTargetsModel.TargetData currentTarget;
/*     */   
/*     */   public TCGGoToObjectiveModel(QuestModel questModel) {
/*  31 */     this.questModel = questModel;
/*  32 */     questModel.addChangeListener(this);
/*  33 */     this.eventListeners = new LinkedList<MultipleTargetsModel.SwitchTargetListener>();
/*  34 */     refresh();
/*     */   }
/*     */   
/*     */   public void refreshOld() {
/*  38 */     ClientQuestData currentSuperquestDesc = this.questModel.findCurrentActiveQuest();
/*  39 */     this.currentMissionObjectives = new LinkedList<MissionObjective>();
/*     */ 
/*     */     
/*  42 */     if (currentSuperquestDesc == null) {
/*     */       return;
/*     */     }
/*     */     
/*  46 */     FinishMissionObjective currentMissionObjective = currentSuperquestDesc.findFinishMissionObjective();
/*     */     
/*  48 */     if (currentMissionObjective == null) {
/*     */       return;
/*     */     }
/*     */     
/*  52 */     ClientQuestData currentMissionDescription = this.questModel.getCurrentMissionDescription(currentMissionObjective.getFinishMission());
/*     */     
/*  54 */     if (currentMissionDescription == null) {
/*     */       return;
/*     */     }
/*  57 */     List<ClientObjectiveTracker> subMissionObjectives = currentMissionDescription.getQuestObjectives();
/*  58 */     for (ClientObjectiveTracker subMissionObjective : subMissionObjectives) {
/*  59 */       this.currentMissionObjectives.add(new TCGClientMission(subMissionObjective));
/*     */     }
/*     */   }
/*     */   
/*     */   public List<MissionObjective> getCurrentMissionObjectives() {
/*  64 */     return this.currentMissionObjectives;
/*     */   }
/*     */   
/*     */   public void addSwitchTargetListener(MultipleTargetsModel.SwitchTargetListener switchTargetListener) {
/*  68 */     this.eventListeners.add(switchTargetListener);
/*     */   }
/*     */   
/*     */   public void removeSwitchTargetListener(MultipleTargetsModel.SwitchTargetListener switchTargetListener) {
/*  72 */     this.eventListeners.remove(switchTargetListener);
/*     */   }
/*     */   
/*     */   private void fireSwitchTargetEvent(MultipleTargetsModel.TargetData currentTarget) {
/*  76 */     for (MultipleTargetsModel.SwitchTargetListener eventListener : this.eventListeners) {
/*  77 */       eventListener.targetSwitched(this, currentTarget);
/*     */     }
/*     */   }
/*     */   
/*     */   public void refresh() {
/*  82 */     refreshOld();
/*  83 */     updateTargetPointerData();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void rewardClaimed(QuestModel questModel, ClientQuestData quest) {}
/*     */ 
/*     */   
/*     */   public void questAdded(QuestModel questModel, ClientQuestData quest) {
/*  92 */     refresh();
/*     */   }
/*     */   
/*     */   public void missionAdded(QuestModel questModel, ClientQuestData mission) {
/*  96 */     refresh();
/*     */   }
/*     */   
/*     */   public void questCompleted(QuestModel questModel, ClientQuestData quest) {
/* 100 */     refresh();
/*     */   }
/*     */   
/*     */   public void missionCompleted(QuestModel questModel, ClientQuestData mission) {
/* 104 */     refresh();
/*     */   }
/*     */   
/*     */   public void questUpdated(QuestModel questModel, ClientQuestData quest) {
/* 108 */     refresh();
/*     */   }
/*     */   
/*     */   public void missionUpdated(QuestModel questModel, ClientQuestData mission) {
/* 112 */     refresh();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementAdded(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementCompleted(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementUpdated(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public MultipleTargetsModel.TargetData getClosestToPlayer() {
/* 133 */     if (!isWorldInitialized()) {
/* 134 */       return null;
/*     */     }
/* 136 */     WorldCoordinate sourcePosition = MainGameState.getPlayerModel().getPosition();
/* 137 */     String currentMap = MainGameState.getWorld().getMapName();
/*     */     
/* 139 */     MultipleTargetsModel.TargetData retur = null;
/* 140 */     double dist = Double.MAX_VALUE;
/*     */ 
/*     */     
/* 143 */     for (MultipleTargetsModel.TargetData targetData : this.targetDatas) {
/* 144 */       if (targetData.isValid() && StringUtils.equals(targetData.getPosition().getMapId(), currentMap)) {
/* 145 */         double tempDist = sourcePosition.distanceTo(targetData.getPosition());
/* 146 */         if (tempDist < dist) {
/* 147 */           dist = tempDist;
/* 148 */           retur = targetData;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 153 */     if (retur != this.currentTarget) {
/* 154 */       setCurrentTarget(retur);
/*     */     }
/* 156 */     return retur;
/*     */   }
/*     */   
/*     */   private boolean isWorldInitialized() {
/* 160 */     return (((TCGWorld)MainGameState.getWorld()).getChunkWorldNode() != null);
/*     */   }
/*     */   
/*     */   private void setCurrentTarget(MultipleTargetsModel.TargetData targetData) {
/* 164 */     this.currentTarget = targetData;
/* 165 */     fireSwitchTargetEvent(targetData);
/*     */   }
/*     */   
/*     */   private void updateTargetPointerData() {
/* 169 */     if (this.targetDatas == null)
/* 170 */       this.targetDatas = new ArrayList<MultipleTargetsModel.TargetData>(); 
/* 171 */     this.targetDatas.clear();
/*     */     
/* 173 */     for (MissionObjective missionObjective : getCurrentMissionObjectives()) {
/* 174 */       List<PairedWCandBoolean> positions = missionObjective.getObjective().getGotoPositions();
/* 175 */       if (positions != null) {
/* 176 */         for (PairedWCandBoolean pairedWCandBoolean : positions) {
/* 177 */           this.targetDatas.add(new TargetDataImpl(missionObjective, pairedWCandBoolean));
/*     */         }
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyLoadingScreenStarted(String toLoadMapName) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyLoadingScreenFinished(String loadedMapName) {
/* 190 */     setCurrentTarget(this.currentTarget);
/*     */   }
/*     */   
/*     */   public void dismiss() {
/* 194 */     this.questModel.removeChangeListener(this);
/*     */   }
/*     */   
/*     */   private class TargetDataImpl implements MultipleTargetsModel.TargetData {
/*     */     private MissionObjective missionObjective;
/*     */     private PairedWCandBoolean pair;
/*     */     
/*     */     private TargetDataImpl(MissionObjective missionObjective, PairedWCandBoolean pair) {
/* 202 */       this.missionObjective = missionObjective;
/* 203 */       this.pair = pair;
/*     */     }
/*     */ 
/*     */     
/*     */     public WorldCoordinate getPosition() {
/* 208 */       return this.pair.getWc();
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isWithinMinimumRange(WorldCoordinate source) {
/* 213 */       RectangleWC wc = getRectangleWC(this.pair.getWc(), this.missionObjective.getObjective().getGotoZoneWidth(), this.missionObjective.getObjective().getGotoZoneHeight());
/*     */ 
/*     */ 
/*     */       
/* 217 */       return wc.contains(source);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isValid() {
/* 222 */       return (!this.missionObjective.isCompleted() && this.pair != null);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean show() {
/* 227 */       return this.pair.isShown();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     private RectangleWC getRectangleWC(WorldCoordinate position, int width, int height) {
/* 233 */       RectangleWC rectangleWC = new RectangleWC(position, position);
/* 234 */       rectangleWC.getOrigin().getTileCoord().translate(-width / 2, -height / 2);
/* 235 */       rectangleWC.getExtent().getTileCoord().translate(width / 2, height / 2);
/* 236 */       return rectangleWC;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\TCGGoToObjectiveModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */