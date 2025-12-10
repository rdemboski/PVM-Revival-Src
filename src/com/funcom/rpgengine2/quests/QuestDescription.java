/*     */ package com.funcom.rpgengine2.quests;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.quests.acquire.AcquireQuestCondition;
/*     */ import com.funcom.rpgengine2.quests.acquire.HasDailyQuestPointsCondition;
/*     */ import com.funcom.rpgengine2.quests.acquire.HasNotDoneQuestCondition;
/*     */ import com.funcom.rpgengine2.quests.acquire.HasNotDoneQuestTodayCondition;
/*     */ import com.funcom.rpgengine2.quests.acquire.NotOnQuestCondition;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestCategory;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardDescription;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class QuestDescription
/*     */ {
/*     */   private String id;
/*     */   private String name;
/*     */   private String giverId;
/*     */   private String startText;
/*     */   private String completionText;
/*     */   private WorldCoordinate missionCoordinate;
/*     */   private int acquireProximityRangeHeight;
/*     */   private int acquireProximityRangeWidth;
/*  37 */   private List<AcquireQuestCondition> acquireConditions = new LinkedList<AcquireQuestCondition>(); private int deliverProximityRangeHeight; private int deliverProximityRangeWidth; private boolean confirmationDialog; private boolean trackable; private boolean repeatable = false; private boolean daily = false; private QuestType questType; private QuestCategory questCategory; private int questCategoryId;
/*  38 */   private List<QuestObjective> questObjectives = new LinkedList<QuestObjective>();
/*     */   
/*     */   private List<QuestRewardData> questRewardDataList;
/*  41 */   private Map<String, QuestRewardDescription> questRewards = new HashMap<String, QuestRewardDescription>();
/*     */   
/*     */   private QuestFactory questFactory;
/*     */   private int levelDifficulty;
/*     */   private String iconPath;
/*     */   private String questText;
/*     */   private String nextQuest;
/*     */   
/*     */   public QuestDescription(QuestFactory questFactory) {
/*  50 */     this.questFactory = questFactory;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  54 */     this.id = id;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/*  58 */     this.name = name;
/*     */   }
/*     */   
/*     */   public void setGiverId(String giverId) {
/*  62 */     this.giverId = giverId;
/*     */   }
/*     */   
/*     */   public void setCompletionText(String completionText) {
/*  66 */     this.completionText = completionText;
/*     */   }
/*     */   
/*     */   public void setRepeatable(boolean repeatable) {
/*  70 */     this.repeatable = repeatable;
/*     */   }
/*     */   
/*     */   public void addAcquireQuestCondition(AcquireQuestCondition condition) {
/*  74 */     this.acquireConditions.add(condition);
/*     */   }
/*     */   
/*     */   public void addBasicAcquireConditions() {
/*  78 */     addAcquireQuestCondition((AcquireQuestCondition)new NotOnQuestCondition(this.id));
/*  79 */     if (!this.repeatable)
/*  80 */       addAcquireQuestCondition((AcquireQuestCondition)new HasNotDoneQuestCondition(this.id)); 
/*  81 */     if (this.daily) {
/*  82 */       addAcquireQuestCondition((AcquireQuestCondition)new HasNotDoneQuestTodayCondition(this.id));
/*  83 */       addAcquireQuestCondition((AcquireQuestCondition)new HasDailyQuestPointsCondition());
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getId() {
/*  88 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  92 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*     */   }
/*     */   
/*     */   public String getGiverId() {
/*  96 */     return this.giverId;
/*     */   }
/*     */   
/*     */   public int getAcquireProximityRangeHeight() {
/* 100 */     return this.acquireProximityRangeHeight;
/*     */   }
/*     */   
/*     */   public int getAcquireProximityRangeWidth() {
/* 104 */     return this.acquireProximityRangeWidth;
/*     */   }
/*     */   
/*     */   public void setAcquireProximityRangeHeight(int acquireProximityRangeHeight) {
/* 108 */     this.acquireProximityRangeHeight = acquireProximityRangeHeight;
/*     */   }
/*     */   
/*     */   public void setAcquireProximityRangeWidth(int acquireProximityRangeWidth) {
/* 112 */     this.acquireProximityRangeWidth = acquireProximityRangeWidth;
/*     */   }
/*     */   
/*     */   public int getDeliverProximityRangeHeight() {
/* 116 */     return this.deliverProximityRangeHeight;
/*     */   }
/*     */   
/*     */   public void setDeliverProximityRangeHeight(int deliverProximityRangeHeight) {
/* 120 */     this.deliverProximityRangeHeight = deliverProximityRangeHeight;
/*     */   }
/*     */   
/*     */   public int getDeliverProximityRangeWidth() {
/* 124 */     return this.deliverProximityRangeWidth;
/*     */   }
/*     */   
/*     */   public void setDeliverProximityRangeWidth(int deliverProximityRangeWidth) {
/* 128 */     this.deliverProximityRangeWidth = deliverProximityRangeWidth;
/*     */   }
/*     */   
/*     */   public boolean isConfirmationDialog() {
/* 132 */     return this.confirmationDialog;
/*     */   }
/*     */   
/*     */   public void setConfirmationDialog(boolean confirmationDialog) {
/* 136 */     this.confirmationDialog = confirmationDialog;
/*     */   }
/*     */   
/*     */   public boolean isTrackable() {
/* 140 */     return this.trackable;
/*     */   }
/*     */   
/*     */   public void setTrackable(boolean trackable) {
/* 144 */     this.trackable = trackable;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCompletionText() {
/* 149 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.completionText);
/*     */   }
/*     */   
/*     */   public String getStartText() {
/* 153 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.startText);
/*     */   }
/*     */   
/*     */   public String getCompletionTextUnLocalized() {
/* 157 */     return this.completionText;
/*     */   }
/*     */   
/*     */   public String getStartTextUnLocalized() {
/* 161 */     return this.startText;
/*     */   }
/*     */   
/*     */   public boolean isRepeatable() {
/* 165 */     return this.repeatable;
/*     */   }
/*     */   
/*     */   public QuestType getQuestType() {
/* 169 */     return this.questType;
/*     */   }
/*     */   
/*     */   public void setQuestType(QuestType questType) {
/* 173 */     this.questType = questType;
/*     */   }
/*     */   
/*     */   public QuestCategory getQuestCategory() {
/* 177 */     return this.questCategory;
/*     */   }
/*     */   
/*     */   public void setQuestCategory(QuestCategory questCategory) {
/* 181 */     this.questCategory = questCategory;
/*     */   }
/*     */   
/*     */   public int getQuestCategoryId() {
/* 185 */     return this.questCategoryId;
/*     */   }
/*     */   
/*     */   public void setQuestCategoryId(int questCategoryId) {
/* 189 */     this.questCategoryId = questCategoryId;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getMissionCoordinate() {
/* 193 */     return this.missionCoordinate;
/*     */   }
/*     */   
/*     */   public void setMissionCoordinate(WorldCoordinate missionCoordinate) {
/* 197 */     this.missionCoordinate = missionCoordinate;
/*     */   }
/*     */   
/*     */   public void addQuestReward(QuestRewardDescription reward) {
/* 201 */     this.questRewards.put(reward.getId(), reward);
/*     */   }
/*     */   
/*     */   public List<QuestObjective> getQuestObjectives() {
/* 205 */     return this.questObjectives;
/*     */   }
/*     */   
/*     */   public QuestObjective getQuestObjectiveById(String id) {
/* 209 */     for (QuestObjective questObjective : this.questObjectives) {
/* 210 */       if (questObjective.getObjectiveId().equalsIgnoreCase(id)) {
/* 211 */         return questObjective;
/*     */       }
/*     */     } 
/* 214 */     return null;
/*     */   }
/*     */   
/*     */   public void addQuestObjective(QuestObjective questObjective) {
/* 218 */     this.questObjectives.add(questObjective);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, QuestRewardDescription> getQuestRewards() {
/* 223 */     return this.questRewards;
/*     */   }
/*     */   
/*     */   public boolean canAcquire(RpgEntity character) {
/* 227 */     for (AcquireQuestCondition condtion : this.acquireConditions) {
/* 228 */       if (!condtion.canAquire(character))
/* 229 */         return false; 
/*     */     } 
/* 231 */     return true;
/*     */   }
/*     */   
/*     */   public Quest createQuest(RpgEntity questOwner) {
/* 235 */     return this.questFactory.getInstance(this, questOwner);
/*     */   }
/*     */   
/*     */   public List<QuestRewardData> getQuestRewardDataContainerList() {
/* 239 */     if (this.questRewardDataList == null || this.questRewardDataList.size() != this.questRewards.size()) {
/* 240 */       createQuestRewardDataContainer();
/*     */     }
/* 242 */     return this.questRewardDataList;
/*     */   }
/*     */   
/*     */   private void createQuestRewardDataContainer() {
/* 246 */     if (this.questRewardDataList == null) {
/* 247 */       this.questRewardDataList = new ArrayList<QuestRewardData>();
/*     */       
/* 249 */       for (QuestRewardDescription questReward : this.questRewards.values())
/*     */       {
/* 251 */         this.questRewardDataList.add(new QuestRewardData(questReward.getId(), questReward.getTier(), questReward.getAmount(), questReward.type().getId(), true));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLevelDifficulty(int levelDifficulty) {
/* 264 */     this.levelDifficulty = levelDifficulty;
/*     */   }
/*     */   
/*     */   public int getLevelDifficulty() {
/* 268 */     return this.levelDifficulty;
/*     */   }
/*     */   
/*     */   public void setIconPath(String iconPath) {
/* 272 */     this.iconPath = iconPath;
/*     */   }
/*     */   
/*     */   public String getIconPath() {
/* 276 */     return this.iconPath;
/*     */   }
/*     */   
/*     */   public String getQuestText() {
/* 280 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.questText);
/*     */   }
/*     */   
/*     */   public String getQuestTextUnLocalized() {
/* 284 */     return this.questText;
/*     */   }
/*     */   
/*     */   public void setQuestText(String questText) {
/* 288 */     this.questText = questText;
/*     */   }
/*     */   
/*     */   public boolean isDaily() {
/* 292 */     return this.daily;
/*     */   }
/*     */   
/*     */   public void setDaily(boolean daily) {
/* 296 */     this.daily = daily;
/*     */   }
/*     */   
/*     */   public void setStartText(String startText) {
/* 300 */     this.startText = startText;
/*     */   }
/*     */   
/*     */   public String getNextQuest() {
/* 304 */     return this.nextQuest;
/*     */   }
/*     */   
/*     */   public void setNextQuest(String nextQuest) {
/* 308 */     this.nextQuest = nextQuest;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\QuestDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */