/*     */ package com.funcom.rpgengine2.quests;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
import com.funcom.rpgengine2.creatures.RpgObject;
/*     */ import com.funcom.rpgengine2.loader.FieldMap;
/*     */ import com.funcom.rpgengine2.loader.QuestFields;
/*     */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*     */ import com.funcom.rpgengine2.monsters.MonsterManager;
/*     */ import com.funcom.rpgengine2.quests.acquire.AcquireQuestCondition;
import com.funcom.rpgengine2.quests.acquire.HasDoneQuestCondition;
/*     */ import com.funcom.rpgengine2.quests.acquire.LevelCondition;
/*     */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestCategory;
/*     */ import java.util.ArrayList;
import java.util.Collections;
/*     */ import java.util.HashMap;
import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
import org.apache.log4j.Priority;
/*     */ 
/*     */ public class QuestManager {
/*  21 */   public static final Logger LOG = Logger.getLogger(QuestManager.class.getName());
/*     */   
/*     */   public static final short STATUS_NOT_STARTED = 0;
/*     */   public static final short STATUS_INCOMPLETE = 1;
/*     */   public static final short STATUS_COMPLETE = 2;
/*  26 */   private Map<String, QuestDescription> questDescriptions = new HashMap<String, QuestDescription>();
/*  27 */   private Set<String> achievements = new HashSet<String>();
/*     */   
/*     */   private MonsterManager monsterManager;
/*  30 */   private List<QuestDescription> questMissionList = new ArrayList<QuestDescription>();
/*  31 */   private Map<String, QuestObjective> objectiveMap = new HashMap<String, QuestObjective>();
/*     */   
/*     */   public void clearData() {
/*  34 */     this.questDescriptions.clear();
/*  35 */     this.questMissionList.clear();
/*  36 */     this.objectiveMap.clear();
/*     */   }
/*     */   
/*     */   public QuestDescription addQuestDescription(String[] questDescriptionFields, QuestFactory questFactory) {
/*  40 */     FieldMap fieldMap = new FieldMap((Object[])QuestFields.values(), questDescriptionFields);
/*  41 */     String id = fieldMap.get(QuestFields.ID);
/*  42 */     String name = fieldMap.get(QuestFields.NAME);
/*  43 */     String reqLvl = fieldMap.get(QuestFields.REQUIRED_LEVEL);
/*  44 */     String levelDiff = fieldMap.get(QuestFields.LEVEL_DIFFICULTY);
/*  45 */     String acquireProximityRangeHeight = fieldMap.get(QuestFields.ACQUIRE_PROXIMITY_RANGE_HEIGHT);
/*  46 */     String acquireProximityRangeWidth = fieldMap.get(QuestFields.ACQUIRE_PROXIMITY_RANGE_WIDTH);
/*  47 */     String deliverProximityRangeHeight = fieldMap.get(QuestFields.DELIVER_PROXIMITY_RANGE_HEIGHT);
/*  48 */     String deliverProximityRangeWidth = fieldMap.get(QuestFields.DELIVER_PROXIMITY_RANGE_WIDTH);
/*  49 */     String questType = fieldMap.get(QuestFields.QUEST_TYPE);
/*  50 */     String questCategory = fieldMap.get(QuestFields.QUEST_CATEGORY);
/*  51 */     String questCategoryId = fieldMap.get(QuestFields.QUEST_CATEGORY_ID);
/*  52 */     String missionCoordX = fieldMap.get(QuestFields.MISSION_COORD_X);
/*  53 */     String missionCoordY = fieldMap.get(QuestFields.MISSION_COORD_Y);
/*  54 */     String missionMap = fieldMap.get(QuestFields.MISSION_MAP);
/*  55 */     String confirmationDialog = fieldMap.get(QuestFields.CONFIRMATION_DIALOG);
/*  56 */     String trackable = fieldMap.get(QuestFields.TRACKABLE);
/*  57 */     String dailyStr = fieldMap.get(QuestFields.DAILY_STRING);
/*  58 */     String giverId = fieldMap.get(QuestFields.QUEST_GIVER_ID);
/*  59 */     String previousQuestInChain = fieldMap.get(QuestFields.PREVIOUS_QUEST_IN_CHAIN);
/*  60 */     String startText = fieldMap.get(QuestFields.START_TEXT);
/*  61 */     String completionText = fieldMap.get(QuestFields.COMPLETION_TEXT);
/*  62 */     String questIconPath = fieldMap.get(QuestFields.QUEST_ICON_PATH);
/*  63 */     String questText = fieldMap.get(QuestFields.QUEST_TEXT);
/*  64 */     String nextQuest = fieldMap.get(QuestFields.NEXT_QUEST);
/*     */     
/*  66 */     if (nextQuest.trim().isEmpty()) {
/*  67 */       nextQuest = null;
/*     */     }
/*  69 */     QuestDescription questDescription = new QuestDescription(questFactory);
/*  70 */     questDescription.setId(id);
/*  71 */     questDescription.setName(name);
/*  72 */     questDescription.setGiverId(giverId);
/*     */     
/*  74 */     questDescription.setAcquireProximityRangeHeight(Integer.parseInt(acquireProximityRangeHeight));
/*  75 */     questDescription.setAcquireProximityRangeWidth(Integer.parseInt(acquireProximityRangeWidth));
/*  76 */     questDescription.setDeliverProximityRangeHeight(Integer.parseInt(deliverProximityRangeHeight));
/*  77 */     questDescription.setDeliverProximityRangeWidth(Integer.parseInt(deliverProximityRangeWidth));
/*  78 */     questDescription.setConfirmationDialog(Boolean.parseBoolean(confirmationDialog));
/*  79 */     questDescription.setTrackable(Boolean.parseBoolean(trackable));
/*  80 */     boolean daily = Boolean.parseBoolean(dailyStr);
/*  81 */     questDescription.setRepeatable(daily);
/*  82 */     questDescription.setDaily(daily);
/*  83 */     questDescription.setStartText(startText);
/*  84 */     questDescription.setCompletionText(completionText);
/*  85 */     questDescription.setIconPath(questIconPath);
/*  86 */     questDescription.setQuestText(questText);
/*  87 */     questDescription.setLevelDifficulty(Integer.parseInt(levelDiff));
/*  88 */     questDescription.setNextQuest(nextQuest);
/*     */     
/*  90 */     questDescription.setQuestCategory(QuestCategory.NONE);
/*  91 */     questDescription.setQuestCategoryId(0);
/*     */     
/*  93 */     if (questType.equalsIgnoreCase(QuestType.QUEST.toString())) {
/*  94 */       questDescription.setQuestType(QuestType.QUEST);
/*     */ 
/*     */       
/*  97 */       WorldCoordinate missionCoordinate = new WorldCoordinate();
/*  98 */       missionCoordinate.setMapId(missionMap);
/*  99 */       questDescription.setMissionCoordinate(missionCoordinate);
/* 100 */     } else if (questType.equalsIgnoreCase(QuestType.PROXIMITY.toString())) {
/* 101 */       questDescription.setQuestType(QuestType.PROXIMITY);
/* 102 */       WorldCoordinate missionCoordinate = new WorldCoordinate();
/* 103 */       missionCoordinate.addOffset(Double.parseDouble(missionCoordX), Double.parseDouble(missionCoordY));
/* 104 */       missionCoordinate.setMapId(missionMap);
/* 105 */       questDescription.setMissionCoordinate(missionCoordinate);
/* 106 */     } else if (questType.equalsIgnoreCase(QuestType.MISSION.toString())) {
/* 107 */       questDescription.setQuestType(QuestType.MISSION);
/*     */ 
/*     */       
/* 110 */       WorldCoordinate missionCoordinate = new WorldCoordinate();
/* 111 */       missionCoordinate.setMapId(missionMap);
/* 112 */       questDescription.setMissionCoordinate(missionCoordinate);
/* 113 */     } else if (questType.equalsIgnoreCase(QuestType.ACHIEVEMENT.toString())) {
/* 114 */       questDescription.setQuestType(QuestType.ACHIEVEMENT);
/* 115 */       questDescription.setQuestCategory(QuestCategory.valueOf(questCategory.toUpperCase()));
/* 116 */       questDescription.setQuestCategoryId(Integer.parseInt(questCategoryId));
/*     */       
/* 118 */       this.achievements.add(id);
/*     */     } 
/*     */     
/* 121 */     MonsterDescription monsterDesc = this.monsterManager.getDescription(giverId);
/* 122 */     if (monsterDesc != null && questDescription.getQuestType() == QuestType.QUEST) {
/* 123 */       monsterDesc.setAssociatedWithQuests(true);
/* 124 */       monsterDesc.addAssociatedStartQuest(id);
/*     */     } 
/*     */     
/* 127 */     questDescription.addBasicAcquireConditions();
/* 128 */     createLevelQuestCondition(Integer.parseInt(reqLvl), questDescription);
/*     */     
/* 130 */     if (!previousQuestInChain.isEmpty()) {
/* 131 */       addHasDoneQuestCondition(previousQuestInChain, questDescription);
/*     */     }
/*     */     
/* 134 */     this.questDescriptions.put(id, questDescription);
/*     */     
/* 136 */     if (questDescription.getQuestType() == QuestType.PROXIMITY) {
/* 137 */       this.questMissionList.add(questDescription);
/*     */     }
/*     */     
/* 140 */     return questDescription;
/*     */   }
/*     */   
/*     */   public void addQuestObjective(String questId, QuestObjective objective) {
/* 144 */     this.objectiveMap.put(questId + "#" + objective.getObjectiveId(), objective);
/*     */   }
/*     */   
/*     */   public QuestObjective getQuestObjective(String id) {
/* 148 */     return this.objectiveMap.get(id);
/*     */   }
/*     */   
/*     */   protected void addHasDoneQuestCondition(String previousQuestInChain, QuestDescription questDescription) {
/* 152 */     questDescription.addAcquireQuestCondition((AcquireQuestCondition)new HasDoneQuestCondition(previousQuestInChain));
/*     */   }
/*     */   
/*     */   private void createLevelQuestCondition(int level, QuestDescription questDescription) {
/* 156 */     LevelCondition levelCondition = new LevelCondition(level);
/* 157 */     questDescription.addAcquireQuestCondition((AcquireQuestCondition)levelCondition);
/*     */   }
/*     */   
/*     */   public QuestDescription getQuestDescription(String id) {
/* 161 */     return this.questDescriptions.get(id);
/*     */   }
/*     */   
/*     */   public boolean canAcquireQuest(String id, RpgEntity character) {
/* 165 */     QuestDescription quest = this.questDescriptions.get(id);
/* 166 */     if (quest == null) {
/* 167 */       LOG.log((Priority)Level.ERROR, "HACK ATTEMPT, SHOULD NOT OCCUR");
/* 168 */       return false;
/*     */     } 
/* 170 */     return quest.canAcquire(character);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void acquireQuest(String id, RpgObject character) {}
/*     */ 
/*     */   
/*     */   public Set<String> getAchievements() {
/* 179 */     return Collections.unmodifiableSet(this.achievements);
/*     */   }
/*     */   
/*     */   public Set<String> getQuestIds() {
/* 183 */     return this.questDescriptions.keySet();
/*     */   }
/*     */   
/*     */   public void setMonsterManager(MonsterManager monsterManager) {
/* 187 */     this.monsterManager = monsterManager;
/*     */   }
/*     */   
/*     */   public List<QuestDescription> getQuestMissionList() {
/* 191 */     return this.questMissionList;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\QuestManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */