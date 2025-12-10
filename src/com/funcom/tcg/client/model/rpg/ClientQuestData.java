/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.rpgengine2.quests.objectives.FinishMissionObjective;
/*     */ import com.funcom.rpgengine2.quests.objectives.TCGObjectiveTracker;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.util.SizeCheckedArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientQuestData
/*     */ {
/*     */   private String questId;
/*     */   private String questName;
/*     */   private int questType;
/*     */   private int questCategory;
/*     */   private int questCategoryId;
/*     */   private List<ClientObjectiveTracker> questObjectives;
/*     */   private List<QuestRewardData> questRewardDatas;
/*     */   private boolean completed;
/*     */   private boolean toClaim;
/*     */   private boolean claimed = false;
/*     */   private String questGiverName;
/*     */   private String location;
/*     */   private String icon;
/*     */   private String questText;
/*     */   private String mapId;
/*     */   private String nextQuest;
/*     */   private List<QuestListener> listeners;
/*     */   
/*     */   public ClientQuestData(String questId, String questName, int type, int category, int categoryId, List<ClientObjectiveTracker> questObjectives, List<QuestRewardData> questRewardDatas, boolean completed, boolean toClaim, String questGiverName, String location, String icon, String questText, String mapId, String nextQuest) {
/*  36 */     this.questId = questId;
/*  37 */     this.questName = questName;
/*  38 */     this.questType = type;
/*  39 */     this.questCategory = category;
/*  40 */     this.questCategoryId = categoryId;
/*  41 */     this.questObjectives = questObjectives;
/*  42 */     this.questRewardDatas = questRewardDatas;
/*  43 */     this.completed = completed;
/*  44 */     this.toClaim = toClaim;
/*  45 */     this.questGiverName = questGiverName;
/*  46 */     this.location = location;
/*  47 */     this.icon = icon;
/*  48 */     this.questText = questText;
/*  49 */     this.mapId = mapId;
/*  50 */     this.nextQuest = nextQuest;
/*     */ 
/*     */     
/*  53 */     for (ClientObjectiveTracker questObjective : questObjectives)
/*  54 */       questObjective.addObjectiveListener(new QuestObjectiveListener(this)); 
/*     */   }
/*     */   
/*     */   public void addQuestListener(QuestListener questListener) {
/*  58 */     if (this.listeners == null)
/*  59 */       this.listeners = (List<QuestListener>)new SizeCheckedArrayList(1, "ClientQuestData.listener", 5); 
/*  60 */     this.listeners.add(questListener);
/*     */   }
/*     */   
/*     */   public void removeQuestListener(QuestListener questListener) {
/*  64 */     if (this.listeners != null)
/*  65 */       this.listeners.remove(questListener); 
/*     */   }
/*     */   
/*     */   public String getQuestId() {
/*  69 */     return this.questId;
/*     */   }
/*     */   
/*     */   public String getQuestName() {
/*  73 */     return this.questName;
/*     */   }
/*     */   
/*     */   public int getQuestType() {
/*  77 */     return this.questType;
/*     */   }
/*     */   
/*     */   public int getQuestCategory() {
/*  81 */     return this.questCategory;
/*     */   }
/*     */   
/*     */   public int getQuestCategoryId() {
/*  85 */     return this.questCategoryId;
/*     */   }
/*     */   
/*     */   public TCGObjectiveTracker getObjectiveTrackerById(String id) {
/*  89 */     for (TCGObjectiveTracker tracker : this.questObjectives) {
/*  90 */       if (tracker.getTrackerId().equals(id))
/*  91 */         return tracker; 
/*     */     } 
/*  93 */     return null;
/*     */   }
/*     */   
/*     */   public List<ClientObjectiveTracker> getQuestObjectives() {
/*  97 */     return this.questObjectives;
/*     */   }
/*     */   
/*     */   public List<QuestRewardData> getQuestRewards() {
/* 101 */     return this.questRewardDatas;
/*     */   }
/*     */   
/*     */   public boolean isCompleted() {
/* 105 */     return this.completed;
/*     */   }
/*     */   
/*     */   public void setCompleted(boolean completed) {
/* 109 */     this.completed = completed;
/*     */   }
/*     */   
/*     */   public boolean isToClaim() {
/* 113 */     return this.toClaim;
/*     */   }
/*     */   
/*     */   public void setToClaim(boolean toClaim) {
/* 117 */     this.toClaim = toClaim;
/*     */   }
/*     */   
/*     */   public boolean isClaimed() {
/* 121 */     return this.claimed;
/*     */   }
/*     */   
/*     */   public void setClaimed(boolean claimed) {
/* 125 */     if (isCompleted()) {
/* 126 */       this.claimed = claimed;
/*     */     } else {
/* 128 */       claimed = false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 135 */     return "ClientQuestData{, questId='" + this.questId + '\'' + ", questName='" + this.questName + '\'' + ", questObjectives=" + this.questObjectives + ", questRewardDatas=" + this.questRewardDatas + ", completed=" + this.completed + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getQuestGiverName() {
/* 145 */     return this.questGiverName;
/*     */   }
/*     */   
/*     */   public String getLocation() {
/* 149 */     return this.location;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 153 */     return this.icon;
/*     */   }
/*     */   
/*     */   public String getQuestText() {
/* 157 */     return this.questText;
/*     */   }
/*     */   
/*     */   public String getMapId() {
/* 161 */     return this.mapId;
/*     */   }
/*     */   
/*     */   public FinishMissionObjective findFinishMissionObjective() {
/* 165 */     int objNum = 0;
/* 166 */     for (ClientObjectiveTracker missionObjective : getQuestObjectives()) {
/* 167 */       if (!missionObjective.isCompleted() && missionObjective.getObjective() instanceof FinishMissionObjective) {
/* 168 */         FinishMissionObjective finishMissionObjective = (FinishMissionObjective)missionObjective.getObjective();
/* 169 */         finishMissionObjective.setObjectiveNumber(objNum);
/* 170 */         return finishMissionObjective;
/*     */       } 
/* 172 */       objNum++;
/*     */     } 
/* 174 */     return null;
/*     */   }
/*     */   
/*     */   private void fireQuestObjectiveCompleted(TCGObjectiveTracker objectiveTracker) {
/* 178 */     if (this.listeners != null)
/* 179 */       for (QuestListener listener : this.listeners) {
/* 180 */         listener.questObjectiveCompleted(this, objectiveTracker);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireQuestObjectiveUpdated(TCGObjectiveTracker objectiveTracker) {
/* 185 */     if (this.listeners != null)
/* 186 */       for (QuestListener listener : this.listeners) {
/* 187 */         listener.questObjectiveUpdated(this, objectiveTracker);
/*     */       } 
/*     */   }
/*     */   
/*     */   public Set<ClientObjectiveTracker> getIncompleteQuestObjectives() {
/* 192 */     Set<ClientObjectiveTracker> returnable = new HashSet<ClientObjectiveTracker>(this.questObjectives);
/*     */ 
/*     */     
/* 195 */     Iterator<ClientObjectiveTracker> it = returnable.iterator();
/* 196 */     while (it.hasNext()) {
/* 197 */       if (((ClientObjectiveTracker)it.next()).isCompleted()) {
/* 198 */         it.remove();
/*     */       }
/*     */     } 
/* 201 */     return returnable;
/*     */   }
/*     */   
/*     */   public String getNextQuest() {
/* 205 */     return this.nextQuest;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class QuestObjectiveListener
/*     */     implements TCGObjectiveTracker.ObjectiveListener
/*     */   {
/*     */     private ClientQuestData questData;
/*     */ 
/*     */ 
/*     */     
/*     */     private QuestObjectiveListener(ClientQuestData questData) {
/* 218 */       this.questData = questData;
/*     */     }
/*     */ 
/*     */     
/*     */     public void objectiveCompleted(TCGObjectiveTracker objectiveTracker) {
/* 223 */       this.questData.fireQuestObjectiveCompleted(objectiveTracker);
/*     */     }
/*     */ 
/*     */     
/*     */     public void objectiveUpdate(TCGObjectiveTracker objectiveTracker) {
/* 228 */       this.questData.fireQuestObjectiveUpdated(objectiveTracker);
/*     */     }
/*     */   }
/*     */   
/*     */   public static interface QuestListener {
/*     */     void questObjectiveCompleted(ClientQuestData param1ClientQuestData, TCGObjectiveTracker param1TCGObjectiveTracker);
/*     */     
/*     */     void questObjectiveUpdated(ClientQuestData param1ClientQuestData, TCGObjectiveTracker param1TCGObjectiveTracker);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientQuestData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */