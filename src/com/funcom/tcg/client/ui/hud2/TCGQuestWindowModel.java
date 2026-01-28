/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ import com.funcom.rpgengine2.combat.ElementManager;
/*     */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*     */ import com.funcom.rpgengine2.pickupitems.DefaultPickUpType;
/*     */ import com.funcom.rpgengine2.pickupitems.ItemPickUpDescription;
/*     */ import com.funcom.rpgengine2.pickupitems.PetPickUpDescription;
/*     */ import com.funcom.rpgengine2.pickupitems.PickUpManager;
/*     */ import com.funcom.rpgengine2.quests.QuestType;
/*     */ import com.funcom.rpgengine2.quests.objectives.FinishMissionObjective;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardType;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*     */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*     */ import com.funcom.tcg.client.model.rpg.ItemRegistry;
/*     */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*     */ import com.funcom.tcg.client.ui.quest2.MissionObjective;
/*     */ import com.funcom.tcg.client.ui.quest2.MissionReward;
/*     */ import com.funcom.tcg.client.ui.quest2.QuestWindowModel;
import com.funcom.tcg.client.ui.quest2.TCGClientMission;
/*     */ import com.funcom.tcg.rpg.GiftBoxPickUpDescription;
/*     */ import com.funcom.tcg.rpg.TCGPickUpType;
/*     */ import java.util.ArrayList;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class TCGQuestWindowModel implements QuestWindowModel, QuestModel.QuestChangeListener {
/*     */   protected QuestModel questModel;
/*  32 */   private static final Logger LOGGER = Logger.getLogger(TCGQuestWindowModel.class.getName()); protected ClientQuestData currentSuperquestDesc; protected FinishMissionObjective currentMissionObjective;
/*     */   protected LinkedList<MissionObjective> currentMissionObjectives;
/*     */   private LinkedList<MissionReward> missionRewards;
/*     */   protected boolean hasQuest = false;
/*     */   private List<QuestWindowModel.QuestWindowModelListener> listeners;
/*     */   
/*     */   public TCGQuestWindowModel(QuestModel questModel) {
/*  39 */     this.questModel = questModel;
/*  40 */     this.listeners = new ArrayList<QuestWindowModel.QuestWindowModelListener>();
/*  41 */     questModel.addChangeListener(this);
/*  42 */     refresh();
/*     */   }
/*     */   
/*     */   public void refresh() {
/*  46 */     this.currentSuperquestDesc = getCurrentActiveSuperquest(this.questModel);
/*  47 */     this.currentMissionObjectives = new LinkedList<MissionObjective>();
/*     */ 
/*     */     
/*  50 */     if (this.currentSuperquestDesc == null) {
/*  51 */       this.hasQuest = false;
/*     */       
/*     */       return;
/*     */     } 
/*  55 */     this.currentMissionObjective = getCurrentMissionObjective(this.currentSuperquestDesc);
/*     */     
/*  57 */     if (this.currentMissionObjective == null) {
/*  58 */       this.hasQuest = false;
/*     */       
/*     */       return;
/*     */     } 
/*  62 */     setObjectives(this.currentSuperquestDesc);
/*     */ 
/*     */     
/*  65 */     setMissionData(this.currentSuperquestDesc);
/*  66 */     this.hasQuest = true;
/*     */   }
/*     */   
/*     */   protected void setObjectives(ClientQuestData currentMissionDescription) {
/*  70 */     List<ClientObjectiveTracker> subMissionObjectives = currentMissionDescription.getQuestObjectives();
/*  71 */     for (ClientObjectiveTracker subMissionObjective : subMissionObjectives) {
/*  72 */       this.currentMissionObjectives.add(new TCGClientMission(subMissionObjective));
/*     */     }
/*     */   }
/*     */   
/*     */   protected void setMissionData(ClientQuestData currentMissionDescription) {
/*  77 */     this.missionRewards = new LinkedList<MissionReward>();
/*     */     
/*  79 */     for (QuestRewardData questRewardData : currentMissionDescription.getQuestRewards()) {
/*  80 */       this.missionRewards.add(new MissionReward()
/*     */           {
/*     */             public String getIconPath() {
/*  83 */               if (questRewardData.getType() == QuestRewardType.STAT_REWARD.getId()) {
/*  84 */                 return TcgUI.getIconProvider().getPathForStat(questRewardData.getRewardId(), 48);
/*     */               }
/*  86 */               if (questRewardData.getType() == QuestRewardType.ITEM_REWARD.getId()) {
/*  87 */                 AbstractPickUpDescription pickUp = TcgGame.getRpgLoader().getPickUpManager().getDescription(questRewardData.getRewardId());
/*  88 */                 if (pickUp != null) {
/*  89 */                   if (pickUp.getAssociatedType() == DefaultPickUpType.ITEM)
/*  90 */                     return ((ItemPickUpDescription)pickUp).getItemDescription().getIcon(); 
/*  91 */                   if (pickUp.getAssociatedType() == DefaultPickUpType.PET)
/*  92 */                     return MainGameState.getPetRegistry().getPetForClassId(((PetPickUpDescription)pickUp).getPetDescription().getId()).getIcon(); 
/*  93 */                   if (pickUp.getAssociatedType() == TCGPickUpType.GIFTBOX) {
/*  94 */                     return ((GiftBoxPickUpDescription)pickUp).getGiftBoxDescription().getIconPathUnlocked();
/*     */                   }
/*     */                 } 
/*  97 */                 return MainGameState.getItemRegistry().getItemForClassID(questRewardData.getRewardId(), questRewardData.getTier()).getIcon();
/*     */               } 
/*  99 */               TCGQuestWindowModel.LOGGER.log((Priority)Level.WARN, "No defined icon for mission reward " + questRewardData.getRewardId() + ", type: " + questRewardData.getType());
/* 100 */               return TcgUI.getIconProvider().getPathForDefaultIcon();
/*     */             }
/*     */ 
/*     */             
/*     */             public int getNumber() {
/* 105 */               return questRewardData.getAmount();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQuestIcon() {
/* 113 */     return this.currentSuperquestDesc.getIcon();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQuestName() {
/* 118 */     return this.currentSuperquestDesc.getQuestName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQuestGiver() {
/* 123 */     return this.currentSuperquestDesc.getQuestGiverName();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQuestLocation() {
/* 128 */     return this.currentSuperquestDesc.getLocation();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getQuestText() {
/* 133 */     return this.currentSuperquestDesc.getQuestText();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasQuest() {
/* 138 */     return this.hasQuest;
/*     */   }
/*     */ 
/*     */   
/*     */   public PickUpManager getPickUpManager() {
/* 143 */     return TcgGame.getRpgLoader().getPickUpManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public VisualRegistry getVisualRegistry() {
/* 148 */     return TcgGame.getVisualRegistry();
/*     */   }
/*     */ 
/*     */   
/*     */   public PetRegistry getPetRegistry() {
/* 153 */     return MainGameState.getPetRegistry();
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemRegistry getItemRegistry() {
/* 158 */     return MainGameState.getItemRegistry();
/*     */   }
/*     */ 
/*     */   
/*     */   public ElementManager getElementManager() {
/* 163 */     return TcgGame.getRpgLoader().getElementManager();
/*     */   }
/*     */ 
/*     */   
/*     */   public void addQuestWindowModelListener(QuestWindowModel.QuestWindowModelListener listener) {
/* 168 */     this.listeners.add(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeQuestWindowModelListener(QuestWindowModel.QuestWindowModelListener listener) {
/* 173 */     return this.listeners.remove(listener);
/*     */   }
/*     */   
/*     */   public void dismiss() {
/* 177 */     this.listeners.clear();
/* 178 */     this.questModel.removeChangeListener(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<MissionObjective> getCurrentMissionObjectives() {
/* 183 */     return this.currentMissionObjectives;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentMissionNum() {
/* 188 */     return this.currentMissionObjective.getObjNum();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCurrentMissionIcon() {
/* 193 */     return this.currentMissionObjective.getIconPath();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMissionTotalNum() {
/* 198 */     return this.currentSuperquestDesc.getQuestObjectives().size();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCurrentMissionName() {
/* 203 */     return this.currentMissionObjective.getStartObjectiveText();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<MissionReward> getCurrentMissionRewards() {
/* 208 */     return this.missionRewards;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<QuestRewardData> getQuestRewards() {
/* 213 */     return this.currentSuperquestDesc.getQuestRewards();
/*     */   }
/*     */   
/*     */   protected ClientQuestData getCurrentMissionDescription(QuestModel questModel, FinishMissionObjective currentMissionObjective) {
/* 217 */     for (ClientQuestData missionDescription : questModel.getQuestDescriptions()) {
/* 218 */       if (missionDescription.getQuestId().equals(currentMissionObjective.getFinishMission())) {
/* 219 */         return missionDescription;
/*     */       }
/*     */     } 
/* 222 */     return null;
/*     */   }
/*     */   
/*     */   protected FinishMissionObjective getCurrentMissionObjective(ClientQuestData currentSuperquestDesc) {
/* 226 */     int objNum = 0;
/* 227 */     for (ClientObjectiveTracker missionObjective : currentSuperquestDesc.getQuestObjectives()) {
/* 228 */       if (!missionObjective.isCompleted() && missionObjective.getObjective() instanceof FinishMissionObjective) {
/* 229 */         FinishMissionObjective finishMissionObjective = (FinishMissionObjective)missionObjective.getObjective();
/* 230 */         finishMissionObjective.setObjectiveNumber(objNum);
/* 231 */         return finishMissionObjective;
/*     */       } 
/* 233 */       objNum++;
/*     */     } 
/* 235 */     return null;
/*     */   }
/*     */   
/*     */   protected ClientQuestData getCurrentActiveSuperquest(QuestModel questModel) {
/* 239 */     for (ClientQuestData questDescription : questModel.getQuestDescriptions()) {
/* 240 */       if (questDescription.getQuestType() == QuestType.QUEST.getType() && !questDescription.isCompleted()) {
/* 241 */         return questDescription;
/*     */       }
/*     */     } 
/* 244 */     return null;
/*     */   }
/*     */   
/*     */   public QuestModel getQuestModel() {
/* 248 */     return this.questModel;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDailyQuestLimit() {
/* 253 */     return this.questModel.getDailyQuestLimit();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDailyQuestMax() {
/* 258 */     return TcgGame.getRpgLoader().getDailyQuestLimit();
/*     */   }
/*     */ 
/*     */   
/*     */   public void questAdded(QuestModel questModel, ClientQuestData quest) {
/* 263 */     fireQuestWindowModelChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void missionAdded(QuestModel questModel, ClientQuestData mission) {
/* 268 */     fireQuestWindowModelChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void questCompleted(QuestModel questModel, ClientQuestData quest) {
/* 273 */     fireQuestWindowModelChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void missionCompleted(QuestModel questModel, ClientQuestData mission) {
/* 278 */     fireQuestWindowModelChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void questUpdated(QuestModel questModel, ClientQuestData quest) {
/* 283 */     fireQuestWindowModelChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void missionUpdated(QuestModel questModel, ClientQuestData mission) {
/* 288 */     fireQuestWindowModelChanged();
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
/*     */   public void rewardClaimed(QuestModel questModel, ClientQuestData quest) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void achievementUpdated(QuestModel questModel, ClientQuestData mission) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void fireQuestWindowModelChanged() {
/* 312 */     for (QuestWindowModel.QuestWindowModelListener listener : this.listeners) {
/* 313 */       listener.questWindowModelChanged(this);
/*     */     }
/*     */   }
/*     */   
/*     */   protected boolean hasCurrentMissionObjective() {
/* 318 */     return (this.currentMissionObjective != null);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\TCGQuestWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */