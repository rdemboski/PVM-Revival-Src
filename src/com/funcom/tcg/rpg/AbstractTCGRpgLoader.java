/*     */ package com.funcom.tcg.rpg;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CSVData;
/*     */ import com.funcom.rpgengine2.StatCollection;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*     */ import com.funcom.rpgengine2.abilities.GeneralStatusModifier;
/*     */ import com.funcom.rpgengine2.abilities.ImmunityModifier;
/*     */ import com.funcom.rpgengine2.abilities.StatusModifier;
/*     */ import com.funcom.rpgengine2.abilities.valueaccumulator.GeneralValueAccumulatorFactory;
/*     */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemManager;
/*     */ import com.funcom.rpgengine2.loader.AbilityFactory;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.ConfigErrors;
/*     */ import com.funcom.rpgengine2.loader.DataRecords;
/*     */ import com.funcom.rpgengine2.loader.FieldMap;
/*     */ import com.funcom.rpgengine2.loader.ItemDescriptionFields;
/*     */ import com.funcom.rpgengine2.loader.LoaderUtils;
/*     */ import com.funcom.rpgengine2.loader.RawData;
/*     */ import com.funcom.rpgengine2.loader.RpgConfigConstants;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import com.funcom.rpgengine2.pets.PetManager;
/*     */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*     */ import com.funcom.rpgengine2.pickupitems.ItemPickUpDescription;
/*     */ import com.funcom.rpgengine2.quests.QuestDescription;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardDescription;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardType;
/*     */ import com.funcom.tcg.rpg.abilities.DebuffCureFactory;
/*     */ import com.funcom.tcg.rpg.pvp.PvpMapDescription;
/*     */ import com.funcom.tcg.rpg.pvp.PvpMapFields;
/*     */ import com.funcom.tcg.rpg.pvp.PvpMapManager;
/*     */ import com.funcom.tcg.rpg.pvp.PvpMapType;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractTCGRpgLoader
/*     */   extends RpgLoader
/*     */ {
/*     */   private static final Object[] TCG_ITEM_DESC_FIELDS;
/*     */   private TCGLevelUpRewardManager levelUpRewardManager;
/*  54 */   private PvpMapManager pvpMapManager = new PvpMapManager();
/*  55 */   private LevelRangeManager levelRangeManager = new LevelRangeManager();
/*     */   private TCGAggroRangeEvaluator aggroRangeEvaluator;
/*     */   
/*     */   static {
/*  59 */     ItemDescriptionFields[] itemDescFields = ItemDescriptionFields.values();
/*  60 */     List<Object> tmpFields = new ArrayList(Arrays.asList((Object[])itemDescFields));
/*  61 */     int levelFromIndex = tmpFields.indexOf(ItemDescriptionFields.REQIREMENT_LEVEL_FROM);
/*  62 */     tmpFields.add(levelFromIndex, TCGItemDescriptionFields.MANA_COST);
/*     */     
/*  64 */     TCG_ITEM_DESC_FIELDS = tmpFields.toArray();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TCGGiftBoxManager giftBoxManager;
/*     */ 
/*     */   
/*     */   protected final ResourceManager resourceManager;
/*     */ 
/*     */ 
/*     */   
/*     */   public AbstractTCGRpgLoader(ConfigErrors configErrors, ResourceManager resourceManager) {
/*  78 */     super(configErrors);
/*  79 */     this.resourceManager = resourceManager;
/*     */     
/*  81 */     setValueAccumulatorFactory((ValueAccumulatorFactory)new GeneralValueAccumulatorFactory());
/*     */     
/*  83 */     getStatsManager().setStatsClass(StatCollection.class);
/*     */     
/*  85 */     StatId.registerIds();
/*     */     
/*  87 */     setEvaluator(new TCGShapeDataEvaluator());
/*     */     
/*  89 */     DataRecords dataRecords = new DataRecordsImpl(resourceManager);
/*  90 */     setDataRecords(dataRecords);
/*  91 */     setStatIdTranslator(new TCGStatIdTranslator());
/*     */     
/*  93 */     setLevelStatId(Short.valueOf((short)20));
/*     */     
/*  95 */     setFactionEnumClass(TCGFaction.class);
/*     */     
/*  97 */     this.giftBoxManager = new TCGGiftBoxManager();
/*  98 */     this.levelUpRewardManager = new TCGLevelUpRewardManager();
/*     */   }
/*     */   
/*     */   public TCGLevelUpRewardManager getLevelUpRewardManager() {
/* 102 */     return this.levelUpRewardManager;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void initPickUpTypes() {
/* 107 */     super.initPickUpTypes();
/*     */     
/* 109 */     getPickUpManager().putType(TCGPickUpType.GIFTBOX);
/*     */   }
/*     */ 
/*     */   
/*     */   protected ItemManager createItemManager(ConfigErrors configErrors) {
/* 114 */     return new ItemManager(configErrors);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemManager getItemManager() {
/* 119 */     return super.getItemManager();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadAbilitiesData(Map<String, RawData> returnData) throws IOException {
/* 124 */     super.loadAbilitiesData(returnData);
/*     */ 
/*     */ 
/*     */     
/* 128 */     List<String[]> files = this.dataRecords.getDebuffCureFiles();
/* 129 */     loadFileList(files, (AbilityFactory)new DebuffCureFactory(), returnData);
/*     */ 
/*     */     
/* 132 */     addGeneralStatusModifierAbility(returnData);
/* 133 */     addImmunityAbility(returnData);
/*     */   }
/*     */   
/*     */   private void addGeneralStatusModifierAbility(Map<String, RawData> returnData) {
/* 137 */     String id = "builtin-status";
/* 138 */     returnData.put("builtin-status", new RawData(new BuiltinAbilityFactory() {
/*     */             public Class<? extends Ability> getAbilityPermissionClass() {
/* 140 */               return (Class)StatusModifier.class;
/*     */             }
/*     */             
/*     */             public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 144 */               return (Ability)new GeneralStatusModifier("builtin-status", params);
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   private void addImmunityAbility(Map<String, RawData> returnData) {
/* 150 */     String id = "builtin-immune";
/* 151 */     returnData.put("builtin-immune", new RawData(new BuiltinAbilityFactory() {
/*     */             public Class<? extends Ability> getAbilityPermissionClass() {
/* 153 */               return (Class)ImmunityModifier.class;
/*     */             }
/*     */             
/*     */             public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 157 */               return (Ability)new ImmunityModifier("builtin-immune", params);
/*     */             }
/*     */           }));
/*     */   }
/*     */   
/*     */   protected FieldMap createItemFieldMap(String[] fields) {
/* 163 */     return new FieldMap(TCG_ITEM_DESC_FIELDS, fields);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void loadAbility(FieldMap fieldMap, Map<String, RawData> rawDataMap, ItemDescription itemDescription) {
/* 168 */     String manaCostStr = fieldMap.get(TCGItemDescriptionFields.MANA_COST);
/* 169 */     float _manaCost = 0.0F;
/* 170 */     if (manaCostStr.length() > 0) {
/* 171 */       _manaCost = Float.parseFloat(manaCostStr);
/*     */     }
/*     */     
/* 174 */     if (itemDescription.isInitialized()) {
/* 175 */       if (itemDescription.getManaCost() != _manaCost) {
/* 176 */         throw new IllegalArgumentException("mana cost mismatch: itemId=" + itemDescription.getId() + " assignedMana=" + itemDescription.getManaCost() + " mismatchingMana=" + _manaCost);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 181 */       itemDescription.setManaCost(_manaCost);
/*     */     } 
/*     */     
/* 184 */     super.loadAbility(fieldMap, rawDataMap, itemDescription);
/*     */   }
/*     */   
/*     */   public double getMaxInteractionDistance() {
/* 188 */     return this.configs.getDouble((Enum)RpgConfigConstants.MAX_INTERACTION_RANGE);
/*     */   }
/*     */   
/*     */   public double getShareKillRange() {
/* 192 */     return this.configs.getDouble((Enum)RpgConfigConstants.SHARE_KILL_RANGE);
/*     */   }
/*     */   
/*     */   public int getDailyQuestLimit() {
/* 196 */     return this.configs.getInt((Enum)RpgConfigConstants.DAILY_QUEST_LIMIT);
/*     */   }
/*     */   
/*     */   public int getPvPLevelRange() {
/* 200 */     return this.configs.getInt((Enum)RpgConfigConstants.PVP_LEVEL_RANGE);
/*     */   }
/*     */   
/*     */   public int getDailyQuestLimitUpdateTime() {
/* 204 */     return this.configs.getInt((Enum)RpgConfigConstants.DAILY_QUEST_LIMIT_UPDATE_TIME);
/*     */   }
/*     */   
/*     */   public long getConfigPauseTimer() {
/* 208 */     return this.configs.getLong((Enum)RpgConfigConstants.PAUSE_TIMER);
/*     */   }
/*     */   
/*     */   public long getPetTrialTime() {
/* 212 */     return this.configs.getLong((Enum)RpgConfigConstants.PET_TRIAL_TIME);
/*     */   }
/*     */   
/*     */   public void load() throws IOException {
/* 216 */     super.load();
/*     */     
/* 218 */     updateData();
/*     */   }
/*     */   
/*     */   public void reload() throws IOException {
/* 222 */     super.reload();
/*     */     
/* 224 */     this.pvpMapManager.clear();
/*     */     
/* 226 */     updateData();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void updateData() {
/* 231 */     loadAggroRangeEvaluator();
/*     */     
/* 233 */     loadQuestObjectives();
/*     */     
/* 235 */     loadGiftBoxes();
/*     */     
/* 237 */     loadQuestRewards();
/* 238 */     updateLevelUpRewardManager();
/*     */     
/* 240 */     loadPvpMapDescriptions();
/*     */     
/* 242 */     loadLevelRanges();
/*     */   }
/*     */   
/*     */   private void loadGiftBoxes() {
/* 246 */     Map<String, List<FieldMap>> rewards = initRewardData();
/* 247 */     this.giftBoxManager.clear();
/*     */     
/* 249 */     CSVData data = loadCSVData("tcg_gift_box");
/* 250 */     for (String[] fields : data) {
/* 251 */       FieldMap fieldMap = new FieldMap((Object[])GiftBoxFields.values(), fields);
/* 252 */       String id = fieldMap.get(GiftBoxFields.ID);
/* 253 */       GiftBoxDescription description = GiftBoxDescription.create(fieldMap, createBoxRewards(id, rewards));
/*     */       
/* 255 */       this.giftBoxManager.addDescription(description);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void loadPvpMapDescriptions() {
/* 260 */     CSVData data = loadCSVData("pvp_map_desc");
/* 261 */     for (String[] fields : data) {
/* 262 */       FieldMap fieldMap = new FieldMap((Object[])PvpMapFields.values(), fields);
/* 263 */       String map = fieldMap.get(PvpMapFields.MAP_NAME);
/* 264 */       int startLevelRange = fieldMap.getInt(PvpMapFields.START_LEVEL_RANGE, 0);
/* 265 */       int minPlayers = fieldMap.getInt(PvpMapFields.MIN_PLAYERS, 0);
/* 266 */       int maxPlayers = fieldMap.getInt(PvpMapFields.MAX_PLAYERS, 0);
/* 267 */       PvpMapType type = PvpMapType.valueOf(fieldMap.get(PvpMapFields.TYPE).toUpperCase());
/*     */       
/* 269 */       PvpMapDescription mapDesc = new PvpMapDescription(map, startLevelRange, minPlayers, maxPlayers, type);
/* 270 */       this.pvpMapManager.addDescription(mapDesc);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadLevelRanges() {
/* 276 */     CSVData data = loadCSVData("level_ranges");
/* 277 */     for (String[] fields : data) {
/* 278 */       FieldMap fieldMap = new FieldMap((Object[])LevelRangeFields.values(), fields);
/* 279 */       int min = fieldMap.getInt(LevelRangeFields.MIN, 0);
/* 280 */       int max = fieldMap.getInt(LevelRangeFields.MAX, 0);
/* 281 */       LevelRange levelRange = new LevelRange(min, max);
/* 282 */       this.levelRangeManager.addLevelRange(levelRange);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private List<QuestRewardDescription> createBoxRewards(String giftBoxId, Map<String, List<FieldMap>> rewards) {
/* 288 */     List<FieldMap> rewardDataList = rewards.get(giftBoxId);
/* 289 */     List<QuestRewardDescription> ret = new ArrayList<QuestRewardDescription>();
/* 290 */     if (rewardDataList != null) {
/* 291 */       for (FieldMap rewardData : rewardDataList) {
/* 292 */         List<QuestRewardDescription> rewardList = createRewards(rewardData, 1, "giftbox:" + giftBoxId);
/* 293 */         ret.addAll(rewardList);
/*     */       } 
/*     */     }
/*     */     
/* 297 */     if (ret.isEmpty()) {
/* 298 */       this.configErrors.addError("giftbox", "no reward for giftbox: giftBoxId=" + giftBoxId);
/*     */     }
/*     */     
/* 301 */     return ret;
/*     */   }
/*     */   
/*     */   private Map<String, List<FieldMap>> initRewardData() {
/* 305 */     Map<String, List<FieldMap>> rewardDataLists = new HashMap<String, List<FieldMap>>();
/* 306 */     CSVData data = loadCSVData("tcg_gift_box_rewards");
/* 307 */     for (String[] fields : data) {
/* 308 */       FieldMap fieldMap = new FieldMap((Object[])QuestRewardFields.values(), fields);
/* 309 */       String giftBoxId = fieldMap.get(QuestRewardFields.ID);
/*     */       
/* 311 */       List<FieldMap> rewardDataList = rewardDataLists.get(giftBoxId);
/* 312 */       if (rewardDataList == null) {
/* 313 */         rewardDataList = new ArrayList<FieldMap>();
/* 314 */         rewardDataLists.put(giftBoxId, rewardDataList);
/*     */       } 
/*     */       
/* 317 */       rewardDataList.add(fieldMap);
/*     */     } 
/* 319 */     return rewardDataLists;
/*     */   }
/*     */   
/*     */   private void updateLevelUpRewardManager() {
/* 323 */     this.levelUpRewardManager.clear();
/*     */     
/* 325 */     CSVData data = loadCSVData("tcg_level_up_rewards");
/* 326 */     for (String[] record : data) {
/*     */       
/* 328 */       FieldMap fieldMap = new FieldMap((Object[])QuestRewardFields.values(), record);
/* 329 */       String levelStr = fieldMap.get(QuestRewardFields.ID);
/* 330 */       int level = LoaderUtils.parseLevel(levelStr);
/*     */       
/* 332 */       if (level <= 1) {
/* 333 */         throw new RuntimeException("unsupported level for level-up rewards, valid level range [2-'inf']: level=" + level);
/*     */       }
/*     */       
/* 336 */       List<QuestRewardDescription> rewards = createRewards(fieldMap, level, "levelup:" + levelStr);
/*     */       
/* 338 */       this.levelUpRewardManager.add(level, rewards);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadAggroRangeEvaluator() {
/* 344 */     double aggroMaxRangeFactor = this.configs.getDouble((Enum)RpgConfigConstants.AGGRO_MAX_RANGE_FACTOR);
/* 345 */     double aggroExponent = this.configs.getDouble((Enum)RpgConfigConstants.AGGRO_EXPONENT);
/* 346 */     this.aggroRangeEvaluator = new TCGAggroRangeEvaluator(aggroMaxRangeFactor, aggroExponent);
/*     */   }
/*     */   
/*     */   public TCGAggroRangeEvaluator getAggroRangeEvaluator() {
/* 350 */     return this.aggroRangeEvaluator;
/*     */   }
/*     */   
/*     */   public TCGGiftBoxManager getGiftBoxManager() {
/* 354 */     return this.giftBoxManager;
/*     */   }
/*     */   
/*     */   public PvpMapManager getPvpMapManager() {
/* 358 */     return this.pvpMapManager;
/*     */   }
/*     */   
/*     */   public LevelRangeManager getLevelRangeManager() {
/* 362 */     return this.levelRangeManager;
/*     */   }
/*     */   
/*     */   public void setPetManager(PetManager petManager) {
/* 366 */     this.petManager = petManager;
/* 367 */     dependencyInjection();
/*     */   }
/*     */   
/*     */   protected CSVData loadCSVData(String fileId) {
/* 371 */     return (CSVData)this.resourceManager.getResource(CSVData.class, "rpg/*." + fileId + ".csv", CacheType.NOT_CACHED);
/*     */   }
/*     */   
/*     */   private void loadQuestRewards() {
/*     */     try {
/* 376 */       for (String[] fields : this.dataRecords.getQuestRewards()) {
/* 377 */         FieldMap fieldMap = new FieldMap((Object[])QuestRewardFields.values(), fields);
/* 378 */         String questId = fieldMap.get(QuestRewardFields.ID);
/* 379 */         QuestDescription questDescription = getQuestManager().getQuestDescription(questId);
/* 380 */         if (questDescription != null) {
/* 381 */           List<QuestRewardDescription> rewards = createRewards(fieldMap, questDescription.getLevelDifficulty(), "quest:" + fieldMap.get(QuestRewardFields.ID));
/*     */ 
/*     */           
/* 384 */           for (QuestRewardDescription reward : rewards) {
/* 385 */             questDescription.addQuestReward(reward);
/*     */           }
/*     */         }
/*     */       
/*     */       } 
/* 390 */     } catch (Exception e) {
/* 391 */       throw new RuntimeException("Error parsing QuestReward:", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected List<QuestRewardDescription> createRewards(FieldMap fields, int calcXpLevel, String parentLogId) {
/* 397 */     String objectId = fields.get(QuestRewardFields.OBJECT_ID);
/* 398 */     int tier = fields.getInt(QuestRewardFields.TIER, 0);
/* 399 */     int amount = fields.getInt(QuestRewardFields.AMOUNT, 0);
/* 400 */     String type = fields.get(QuestRewardFields.TYPE);
/*     */     
/* 402 */     List<QuestRewardDescription> rewards = new ArrayList<QuestRewardDescription>();
/*     */     
/* 404 */     if (type.equalsIgnoreCase("item")) {
/*     */       try {
/*     */         ItemDescription itemDescription;
/* 407 */         AbstractPickUpDescription abstractPickUpDescription = getPickUpManager().getDescription(objectId);
/* 408 */         if (abstractPickUpDescription != null) {
/* 409 */           itemDescription = ((ItemPickUpDescription)abstractPickUpDescription).getItemDescription();
/*     */         } else {
/* 411 */           itemDescription = getItemManager().getDescription(objectId, tier);
/*     */         } 
/*     */         
/* 414 */         if (itemDescription == null) {
/* 415 */           throw new RuntimeException("Item reward ID [" + fields.get(QuestRewardFields.ID) + "] uses a missing (Pickup)Item ID [" + objectId + "]");
/*     */         }
/* 417 */         if (objectId.isEmpty()) {
/* 418 */           LOG.error("Item reward ID [" + fields.get(QuestRewardFields.ID) + "] uses an empty (Pickup)Item ID");
/*     */         }
/*     */         
/* 421 */         QuestRewardDescription reward = new QuestRewardDescription(objectId, tier, amount, QuestRewardType.ITEM_REWARD, calcXpLevel);
/* 422 */         rewards.add(reward);
/* 423 */       } catch (NumberFormatException e) {
/* 424 */         LOG.warn("can't convert amount field to integer value for reward: '" + parentLogId + "' giving item reward: '" + objectId + "' and amount: '" + amount + "'");
/*     */       }
/*     */     
/* 427 */     } else if (type.equalsIgnoreCase("pet")) {
/* 428 */       QuestRewardDescription reward = new QuestRewardDescription(objectId, tier, amount, QuestRewardType.PET_REWARD, calcXpLevel);
/* 429 */       rewards.add(reward);
/* 430 */     } else if (type.equalsIgnoreCase("stat")) {
/* 431 */       if (objectId.equalsIgnoreCase("xp")) {
/* 432 */         QuestRewardDescription reward = new QuestRewardDescription(objectId, tier, amount, QuestRewardType.STAT_REWARD, calcXpLevel);
/* 433 */         rewards.add(reward);
/*     */       } else {
/* 435 */         throw new RuntimeException("Illegal reward stat: parentLogId=" + parentLogId + " stat=" + objectId);
/*     */       } 
/* 437 */     } else if (type.equalsIgnoreCase("portal")) {
/* 438 */       QuestRewardDescription reward = new QuestRewardDescription(objectId, tier, amount, QuestRewardType.PORTAL_REWARD, calcXpLevel);
/* 439 */       rewards.add(reward);
/* 440 */     } else if (type.equalsIgnoreCase("giftbox")) {
/* 441 */       QuestRewardDescription reward = new QuestRewardDescription(objectId, tier, amount, QuestRewardType.GIFT_BOX_REWARD, calcXpLevel);
/* 442 */       rewards.add(reward);
/* 443 */     } else if (type.equalsIgnoreCase("lootgroup")) {
/* 444 */       QuestRewardDescription reward = new QuestRewardDescription(objectId, tier, amount, QuestRewardType.LOOT_GROUP_REWARD, calcXpLevel);
/* 445 */       rewards.add(reward);
/*     */     } 
/*     */     
/* 448 */     return rewards;
/*     */   }
/*     */   
/*     */   protected static abstract class BuiltinAbilityFactory implements AbilityFactory {
/*     */     public AbilityParams createParams(List<String[]> fieldsList) {
/* 453 */       AbilityParams params = new AbilityParams();
/* 454 */       String[] singleRow = { "[X]", "[Y]", "[Z]", "[K]" };
/*     */       
/* 456 */       List<String[]> builtinFields = (List)new ArrayList<String>(3);
/* 457 */       builtinFields.add(singleRow);
/* 458 */       params.setRecords(builtinFields, new AbilityParams.ParamName[] { AbilityParams.ParamName.X, AbilityParams.ParamName.Y, AbilityParams.ParamName.Z, AbilityParams.ParamName.K });
/*     */ 
/*     */       
/* 461 */       return params;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\AbstractTCGRpgLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */