/*     */ package com.funcom.tcg.client.ui.quest;
/*     */ 
/*     */ import com.funcom.gameengine.PairedWCandBoolean;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.creatures.BasicQuestSupportable;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.QuestType;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.achievements.AchievementsWindow;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestModel
/*     */   implements BasicQuestSupportable
/*     */ {
/*     */   private Set<QuestChangeListener> listeners;
/*     */   private Set<ClientQuestData> questDescriptions;
/*     */   private Set<ClientQuestData> achievements;
/*     */   private Set<String> completedQuests;
/*     */   private Set<String> unclaimedQuests;
/*  31 */   private int dailyQuestLimit = 0;
/*     */   private int achievementsPending;
/*     */   private boolean acceptAchievements = true;
/*     */   
/*     */   public QuestModel() {
/*  36 */     this.questDescriptions = new HashSet<ClientQuestData>();
/*  37 */     this.achievements = new HashSet<ClientQuestData>();
/*  38 */     this.completedQuests = new HashSet<String>();
/*  39 */     this.unclaimedQuests = new HashSet<String>();
/*     */   }
/*     */   
/*     */   public Set<ClientQuestData> getQuestDescriptions() {
/*  43 */     return this.questDescriptions;
/*     */   }
/*     */   
/*     */   public Set<ClientQuestData> getAchievements() {
/*  47 */     return this.achievements;
/*     */   }
/*     */   
/*     */   public Set<String> getCompletedQuests() {
/*  51 */     return this.completedQuests;
/*     */   }
/*     */   
/*     */   public void addChangeListener(QuestChangeListener listener) {
/*  55 */     if (this.listeners == null) {
/*  56 */       this.listeners = new HashSet<QuestChangeListener>();
/*     */     }
/*  58 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeChangeListener(QuestChangeListener listener) {
/*  62 */     this.listeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void addClientQuestData(ClientQuestData clientQuestData) {
/*  66 */     removeTrailingSlashFromPosition(clientQuestData);
/*     */     
/*  68 */     if (clientQuestData.getQuestType() == QuestType.QUEST.getType()) {
/*  69 */       fireQuestAdded(clientQuestData);
/*  70 */     } else if (clientQuestData.getQuestType() == QuestType.MISSION.getType()) {
/*  71 */       fireMissionAdded(clientQuestData);
/*  72 */     } else if (clientQuestData.getQuestType() == QuestType.ACHIEVEMENT.getType()) {
/*  73 */       fireAchievementAdded(clientQuestData);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void removeTrailingSlashFromPosition(ClientQuestData clientQuestData) {
/*  79 */     for (ClientObjectiveTracker clientObjectiveTracker : clientQuestData.getQuestObjectives()) {
/*  80 */       List<PairedWCandBoolean> positions = clientObjectiveTracker.getObjective().getGotoPositions();
/*  81 */       if (positions != null)
/*  82 */         for (PairedWCandBoolean pair : positions) {
/*  83 */           WorldCoordinate position = pair.getWc();
/*  84 */           if (position != null) {
/*  85 */             String mapID = position.getMapId();
/*  86 */             if (mapID.endsWith("/")) {
/*  87 */               position.setMapId(mapID.substring(0, mapID.length() - 1));
/*     */             }
/*     */           } 
/*     */         }  
/*     */     } 
/*     */   }
/*     */   
/*     */   public void clearQuests() {
/*  95 */     this.questDescriptions.clear();
/*  96 */     this.completedQuests.clear();
/*  97 */     this.achievements.clear();
/*  98 */     this.achievementsPending = 0;
/*     */   }
/*     */   
/*     */   public ClientQuestData getQuestDescriptionByIds(String questId) {
/* 102 */     for (ClientQuestData questDescription : this.questDescriptions) {
/* 103 */       if (questDescription.getQuestId().equalsIgnoreCase(questId)) {
/* 104 */         return questDescription;
/*     */       }
/*     */     } 
/* 107 */     for (ClientQuestData questDescription : this.achievements) {
/* 108 */       if (questDescription.getQuestId().equalsIgnoreCase(questId)) {
/* 109 */         return questDescription;
/*     */       }
/*     */     } 
/* 112 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasQuest(String questId) {
/* 117 */     return (getQuestDescriptionByIds(questId) != null);
/*     */   }
/*     */   
/*     */   public ClientQuestData findCurrentActiveQuest() {
/* 121 */     for (ClientQuestData questDescription : getQuestDescriptions()) {
/* 122 */       if (questDescription.getQuestType() == QuestType.QUEST.getType() && !questDescription.isCompleted()) {
/* 123 */         return questDescription;
/*     */       }
/*     */     } 
/* 126 */     return null;
/*     */   }
/*     */   
/*     */   public ClientQuestData getCurrentMissionDescription(String finishMissionId) {
/* 130 */     for (ClientQuestData missionDescription : getQuestDescriptions()) {
/* 131 */       if (missionDescription.getQuestId().equals(finishMissionId)) {
/* 132 */         return missionDescription;
/*     */       }
/*     */     } 
/* 135 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClientObjectiveTracker findNearestObjectiveLocal(WorldCoordinate position, ClientQuestData quest, boolean ignoreCompleted) {
/* 143 */     ClientObjectiveTracker returnable = null;
/* 144 */     double dist = Double.MAX_VALUE;
/*     */ 
/*     */     
/* 147 */     for (ClientObjectiveTracker objectiveTracker : quest.getQuestObjectives()) {
/* 148 */       if (objectiveTracker.isCompleted() && ignoreCompleted) {
/*     */         continue;
/*     */       }
/* 151 */       List<PairedWCandBoolean> gotoPositions = objectiveTracker.getObjective().getGotoPositions();
/*     */       
/* 153 */       if (gotoPositions == null) {
/*     */         continue;
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 159 */       for (PairedWCandBoolean gotoPosition : gotoPositions) {
/* 160 */         WorldCoordinate gotoPositionWc = gotoPosition.getWc();
/*     */ 
/*     */         
/* 163 */         if (position.getMapId().equals(gotoPositionWc.getMapId())) {
/*     */ 
/*     */           
/* 166 */           double tempDist = position.distanceTo(gotoPositionWc);
/* 167 */           if (tempDist < dist) {
/* 168 */             dist = tempDist;
/* 169 */             returnable = objectiveTracker;
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/* 175 */     return returnable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static ClientObjectiveTracker findFirstOutOfTheMapObjective(WorldCoordinate position, ClientQuestData quest, boolean ignoreCompleted) {
/* 184 */     for (ClientObjectiveTracker objectiveTracker : quest.getQuestObjectives()) {
/* 185 */       if (objectiveTracker.isCompleted() && ignoreCompleted)
/*     */         continue; 
/* 187 */       return objectiveTracker;
/*     */     } 
/* 189 */     return null;
/*     */   }
/*     */   
/*     */   public void claimReward(ClientQuestData questData) {
/* 193 */     fireRewardClaimed(questData);
/*     */   }
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
/*     */ 
/*     */   
/*     */   private ClientQuestData getQuestData(String questId, boolean claimed) {
/* 212 */     ClientQuestData clientQuestData = getQuestDescriptionByIds(questId);
/*     */     
/* 214 */     if (clientQuestData != null) {
/* 215 */       clientQuestData.setClaimed(claimed);
/* 216 */       return clientQuestData;
/*     */     } 
/*     */     
/* 219 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questId);
/* 220 */     if (questDescription == null) {
/* 221 */       return null;
/*     */     }
/*     */     
/* 224 */     List<ClientObjectiveTracker> questObjStrings = new ArrayList<ClientObjectiveTracker>();
/* 225 */     for (int i = 0; i < questDescription.getQuestObjectives().size(); i++) {
/* 226 */       QuestObjective questObjective = questDescription.getQuestObjectives().get(i);
/* 227 */       int amount = questObjective.getAmount();
/* 228 */       int current = hasCompletedQuest(questId) ? amount : ((ClientObjectiveTracker)getQuestDescriptionByIds(questId).getQuestObjectives().get(i)).getTrackAmount();
/* 229 */       ClientObjectiveTracker tracker = new ClientObjectiveTracker(questDescription.getId(), questObjective.getObjectiveId(), current, amount, questObjective);
/*     */ 
/*     */       
/* 232 */       questObjStrings.add(tracker);
/*     */     } 
/*     */     
/* 235 */     boolean toClaim = (questDescription.getQuestRewards().size() > 0 && !claimed);
/*     */     
/* 237 */     clientQuestData = new ClientQuestData(questId, questDescription.getName(), questDescription.getQuestType().getType(), questDescription.getQuestCategory().getType(), questDescription.getQuestCategoryId(), questObjStrings, questDescription.getQuestRewardDataContainerList(), hasCompletedQuest(questId), toClaim, "fixme!", "fixme!", questDescription.getIconPath(), questDescription.getQuestText(), (questDescription.getMissionCoordinate() != null) ? questDescription.getMissionCoordinate().getMapId() : null, questDescription.getNextQuest());
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
/* 248 */     clientQuestData.setClaimed(claimed);
/* 249 */     return clientQuestData;
/*     */   }
/*     */   
/*     */   public void completeQuest(String questId, boolean claimed) {
/* 253 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questId);
/* 254 */     this.completedQuests.add(questId);
/* 255 */     if (questDescription != null && 
/* 256 */       questDescription.getQuestRewards().size() > 0 && !claimed) {
/* 257 */       this.unclaimedQuests.add(questId);
/*     */     }
/*     */ 
/*     */     
/* 261 */     ClientQuestData clientQuestData = getQuestData(questId, claimed);
/* 262 */     if (clientQuestData != null) {
/* 263 */       clientQuestData.setCompleted(true);
/* 264 */       if (clientQuestData.getQuestType() == QuestType.QUEST.getType()) {
/* 265 */         fireQuestCompleted(clientQuestData);
/* 266 */       } else if (clientQuestData.getQuestType() == QuestType.MISSION.getType()) {
/* 267 */         fireMissionCompleted(clientQuestData);
/* 268 */       } else if (clientQuestData.getQuestType() == QuestType.ACHIEVEMENT.getType()) {
/* 269 */         fireAchievementCompleted(clientQuestData);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   public boolean hasCompletedQuest(String questId) {
/* 274 */     return this.completedQuests.contains(questId);
/*     */   }
/*     */   
/*     */   public boolean hasUnclaimedReward(String questId) {
/* 278 */     return this.unclaimedQuests.contains(questId);
/*     */   }
/*     */   
/*     */   public void updateQuest(String questId) {
/* 282 */     ClientQuestData clientQuestData = getQuestDescriptionByIds(questId);
/*     */     
/* 284 */     if (clientQuestData.getQuestType() == QuestType.QUEST.getType()) {
/* 285 */       fireQuestUpdate(clientQuestData);
/* 286 */     } else if (clientQuestData.getQuestType() == QuestType.MISSION.getType()) {
/* 287 */       fireMissionUpdate(clientQuestData);
/* 288 */     } else if (clientQuestData.getQuestType() == QuestType.ACHIEVEMENT.getType()) {
/* 289 */       fireAchievementUpdate(clientQuestData);
/*     */     } 
/*     */   }
/*     */   private void fireQuestUpdate(ClientQuestData quest) {
/* 293 */     if (this.listeners != null)
/* 294 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 295 */         questChangeListener.questUpdated(this, quest);
/*     */       } 
/*     */   }
/*     */   
/*     */   private boolean finishTutorial() {
/* 300 */     TcgGame.setTutorialMode(false);
/* 301 */     if (MainGameState.getNoahTutorialWindow() != null) {
/*     */       try {
/* 303 */         MainGameState.getNoahTutorialWindow().dismiss();
/* 304 */         MainGameState.setNoahTutorialWindow(null);
/* 305 */         return true;
/* 306 */       } catch (Exception e) {}
/*     */     }
/*     */ 
/*     */     
/* 310 */     return false;
/*     */   }
/*     */   
/*     */   private void fireMissionUpdate(ClientQuestData mission) {
/* 314 */     if (this.listeners != null) {
/* 315 */       boolean checked = false;
/* 316 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 317 */         questChangeListener.missionUpdated(this, mission);
/* 318 */         if (TcgGame.isTutorialMode() && 
/* 319 */           mission.getQuestId().contains("000_rbg_tutorial-quest5") && !checked) {
/* 320 */           for (ClientObjectiveTracker tracker : mission.getQuestObjectives()) {
/* 321 */             if (tracker.getObjective().getObjectiveId().equals("000_rbg_tutorial-mission5-part1-objective1") && 
/* 322 */               tracker.isCompleted()) {
/* 323 */               checked = finishTutorial();
/*     */             }
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void fireAchievementUpdate(ClientQuestData achievement) {
/* 334 */     if (this.listeners != null)
/* 335 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 336 */         questChangeListener.achievementUpdated(this, achievement);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireQuestCompleted(ClientQuestData quest) {
/* 341 */     this.questDescriptions.remove(quest);
/* 342 */     if (this.listeners != null) {
/* 343 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 344 */         questChangeListener.questCompleted(this, quest);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireMissionCompleted(ClientQuestData mission) {
/* 350 */     this.questDescriptions.remove(mission);
/* 351 */     if (this.listeners != null)
/* 352 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 353 */         questChangeListener.missionCompleted(this, mission);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireAchievementCompleted(ClientQuestData achievement) {
/* 358 */     this.achievements.remove(achievement);
/* 359 */     if (this.acceptAchievements) {
/* 360 */       BWindow window = BuiSystem.getWindow(AchievementsWindow.class.getSimpleName());
/* 361 */       if (window != null && window.isVisible()) {
/* 362 */         this.achievementsPending = 0;
/*     */       } else {
/* 364 */         this.achievementsPending++;
/*     */       } 
/* 366 */       MainGameState.getHudModel().achievementPendingAction(this.achievementsPending);
/*     */     } 
/* 368 */     if (this.listeners != null)
/* 369 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 370 */         questChangeListener.achievementCompleted(this, achievement);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireRewardClaimed(ClientQuestData questData) {
/* 375 */     this.unclaimedQuests.remove(questData.getQuestId());
/* 376 */     questData.setClaimed(true);
/* 377 */     questData.setToClaim(false);
/* 378 */     for (QuestChangeListener questChangeListener : this.listeners) {
/* 379 */       questChangeListener.rewardClaimed(this, questData);
/*     */     }
/*     */   }
/*     */   
/*     */   public void fireQuestAdded(ClientQuestData quest) {
/* 384 */     this.questDescriptions.add(quest);
/* 385 */     if (this.listeners != null) {
/* 386 */       boolean checked = false;
/* 387 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 388 */         questChangeListener.questAdded(this, quest);
/* 389 */         if (quest.getQuestId().equals("000_rbg_tutorial-quest5") && !checked) {
/* 390 */           TcgGame.setAllowSave(true);
/* 391 */           if (MainGameState.getAccountButtonWindow() != null) {
/* 392 */             MainGameState.getAccountButtonWindow().setVisible(true);
/* 393 */             MainGameState.getAccountButtonWindow().showSaveCharacterWindow("SAVE_CHARACTER_BUTTON");
/*     */           } 
/* 395 */           checked = true;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireMissionAdded(ClientQuestData mission) {
/* 402 */     this.questDescriptions.add(mission);
/* 403 */     if (this.listeners != null)
/* 404 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 405 */         questChangeListener.missionAdded(this, mission);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireAchievementAdded(ClientQuestData achievement) {
/* 410 */     this.achievements.add(achievement);
/* 411 */     if (this.listeners != null)
/* 412 */       for (QuestChangeListener questChangeListener : this.listeners) {
/* 413 */         questChangeListener.achievementAdded(this, achievement);
/*     */       } 
/*     */   }
/*     */   
/*     */   public int getDailyQuestLimit() {
/* 418 */     return this.dailyQuestLimit;
/*     */   }
/*     */   
/*     */   public void setDailyQuestLimit(int dailyQuestLimit) {
/* 422 */     this.dailyQuestLimit = dailyQuestLimit;
/*     */   }
/*     */   
/*     */   public int getAchievementsPending() {
/* 426 */     return this.achievementsPending;
/*     */   }
/*     */   
/*     */   public void setAchievementsPending(int achievementsPending) {
/* 430 */     this.achievementsPending = achievementsPending;
/* 431 */     MainGameState.getHudModel().achievementPendingAction(achievementsPending);
/*     */   }
/*     */   
/*     */   public boolean isAcceptAchievements() {
/* 435 */     return this.acceptAchievements;
/*     */   }
/*     */   
/*     */   public void setAcceptAchievements(boolean acceptAchievements) {
/* 439 */     this.acceptAchievements = acceptAchievements;
/*     */   }
/*     */   
/*     */   public static abstract class QuestChangeAdapter implements QuestChangeListener {
/*     */     public void questAdded(QuestModel questModel, ClientQuestData quest) {}
/*     */     
/*     */     public void rewardClaimed(QuestModel questModel, ClientQuestData quest) {}
/*     */     
/*     */     public void missionAdded(QuestModel questModel, ClientQuestData mission) {}
/*     */     
/*     */     public void questCompleted(QuestModel questModel, ClientQuestData quest) {}
/*     */     
/*     */     public void missionCompleted(QuestModel questModel, ClientQuestData mission) {}
/*     */     
/*     */     public void questUpdated(QuestModel questModel, ClientQuestData quest) {}
/*     */     
/*     */     public void missionUpdated(QuestModel questModel, ClientQuestData mission) {}
/*     */     
/*     */     public void achievementAdded(QuestModel questModel, ClientQuestData mission) {}
/*     */     
/*     */     public void achievementCompleted(QuestModel questModel, ClientQuestData mission) {}
/*     */     
/*     */     public void achievementUpdated(QuestModel questModel, ClientQuestData mission) {}
/*     */   }
/*     */   
/*     */   public static interface QuestChangeListener {
/*     */     void questAdded(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void rewardClaimed(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void missionAdded(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void achievementAdded(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void questCompleted(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void missionCompleted(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void achievementCompleted(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void questUpdated(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void missionUpdated(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */     
/*     */     void achievementUpdated(QuestModel param1QuestModel, ClientQuestData param1ClientQuestData);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest\QuestModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */