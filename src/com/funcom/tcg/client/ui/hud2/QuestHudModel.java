/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.ui.giftbox.HudInfoModel;
/*     */ import com.funcom.tcg.client.ui.giftbox.HudInfoSetModel;
/*     */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*     */ import com.funcom.tcg.client.ui.quest2.MissionObjective;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class QuestHudModel
/*     */   extends TCGQuestWindowModel
/*     */   implements HudInfoSetModel
/*     */ {
/*     */   private ArrayList<HudInfoModel> hudInfoSetModels;
/*     */   
/*     */   public QuestHudModel(QuestModel questModel) {
/*  20 */     super(questModel);
/*  21 */     if (this.hudInfoSetModels == null) this.hudInfoSetModels = new ArrayList<HudInfoModel>();
/*     */   
/*     */   }
/*     */   
/*     */   public void refresh() {
/*  26 */     if (this.hudInfoSetModels == null) this.hudInfoSetModels = new ArrayList<HudInfoModel>(); 
/*  27 */     this.hudInfoSetModels.clear();
/*     */     
/*  29 */     this.currentSuperquestDesc = getCurrentActiveSuperquest(this.questModel);
/*  30 */     this.currentMissionObjectives = new LinkedList<MissionObjective>();
/*     */ 
/*     */     
/*  33 */     if (this.currentSuperquestDesc == null) {
/*  34 */       this.hasQuest = false;
/*     */       
/*     */       return;
/*     */     } 
/*  38 */     this.currentMissionObjective = getCurrentMissionObjective(this.currentSuperquestDesc);
/*     */     
/*  40 */     if (this.currentMissionObjective == null) {
/*  41 */       this.hasQuest = false;
/*     */       return;
/*     */     } 
/*  44 */     ClientQuestData currentMissionDescription = getCurrentMissionDescription(this.questModel, this.currentMissionObjective);
/*     */     
/*  46 */     if (currentMissionDescription == null) {
/*  47 */       this.hasQuest = false;
/*     */       
/*     */       return;
/*     */     } 
/*  51 */     setObjectives(currentMissionDescription);
/*     */ 
/*     */     
/*  54 */     setMissionData(currentMissionDescription);
/*  55 */     this.hasQuest = true;
/*     */     
/*  57 */     if (hasCurrentMissionObjective() && hasQuest())
/*     */     {
/*     */       
/*  60 */       for (MissionObjective missionObjective : getCurrentMissionObjectives()) {
/*  61 */         this.hudInfoSetModels.add(missionObjective);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<HudInfoModel> getInfoModels() {
/*  68 */     return this.hudInfoSetModels;
/*     */   }
/*     */   
/*     */   public void questAdded(QuestModel questModel, ClientQuestData quest) {
/*  72 */     refresh();
/*  73 */     fireQuestWindowModelChanged();
/*     */   }
/*     */   
/*     */   public void missionAdded(QuestModel questModel, ClientQuestData mission) {
/*  77 */     refresh();
/*  78 */     fireQuestWindowModelChanged();
/*     */   }
/*     */   
/*     */   public void questCompleted(QuestModel questModel, ClientQuestData quest) {
/*  82 */     refresh();
/*  83 */     fireQuestWindowModelChanged();
/*     */   }
/*     */   
/*     */   public void missionCompleted(QuestModel questModel, ClientQuestData mission) {
/*  87 */     refresh();
/*  88 */     fireQuestWindowModelChanged();
/*     */   }
/*     */   
/*     */   public void questUpdated(QuestModel questModel, ClientQuestData quest) {
/*  92 */     refresh();
/*  93 */     fireQuestWindowModelChanged();
/*     */   }
/*     */   
/*     */   public void missionUpdated(QuestModel questModel, ClientQuestData mission) {
/*  97 */     refresh();
/*  98 */     fireQuestWindowModelChanged();
/*     */   }
/*     */   
/*     */   public String getQuestRegionID() {
/* 102 */     List<MissionObjective> missionObjectives = getCurrentMissionObjectives();
/* 103 */     for (MissionObjective missionObjective : missionObjectives) {
/* 104 */       if (!missionObjective.isCompleted()) {
/* 105 */         return missionObjective.getObjective().getRegionID();
/*     */       }
/*     */     } 
/* 108 */     return null;
/*     */   }
/*     */   
/*     */   public void dismiss() {
/* 112 */     super.dismiss();
/* 113 */     this.questModel.removeChangeListener(this);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\QuestHudModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */