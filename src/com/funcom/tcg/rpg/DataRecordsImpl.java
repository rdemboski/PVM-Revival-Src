/*     */ package com.funcom.tcg.rpg;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffectResourceLoader;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ManagedResource;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceListener;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CSVData;
/*     */ import com.funcom.rpgengine2.loader.DataRecords;
/*     */ import com.funcom.server.common.ServerConstants;
/*     */ import java.io.IOException;
/*     */ import java.io.StringReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.EnumMap;
/*     */ import java.util.EnumSet;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ import org.jdom.Document;
/*     */ import org.jdom.Element;
/*     */ 
/*     */ public class DataRecordsImpl
/*     */   implements DataRecords
/*     */ {
/*  31 */   private static final Logger LOGGER = Logger.getLogger(DataRecordsImpl.class);
/*     */ 
/*     */ 
/*     */   
/*     */   private EnumMap<ServerConstants.RpgConfigFile, List<String[]>> rpgFiles;
/*     */ 
/*     */   
/*     */   private Set<ServerConstants.RpgConfigFile> loadedStatus;
/*     */ 
/*     */   
/*  41 */   private Map<String, List<String[]>> aiEventFiles = new HashMap<String, List<String[]>>();
/*     */   
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   public DataRecordsImpl(ResourceManager resourceManager) {
/*  46 */     if (resourceManager == null)
/*  47 */       throw new IllegalArgumentException("resourceManager = null"); 
/*  48 */     this.resourceManager = resourceManager;
/*     */     
/*  50 */     this.loadedStatus = Collections.synchronizedSet(EnumSet.noneOf(ServerConstants.RpgConfigFile.class));
/*  51 */     this.rpgFiles = new EnumMap<ServerConstants.RpgConfigFile, List<String[]>>(ServerConstants.RpgConfigFile.class);
/*  52 */     for (ServerConstants.RpgConfigFile enumKey : ServerConstants.RpgConfigFile.values()) {
/*  53 */       this.rpgFiles.put(enumKey, (List)new ArrayList<String>());
/*     */     }
/*     */     
/*  56 */     loadFiles();
/*     */   }
/*     */   
/*     */   public final void loadFiles() {
/*  60 */     for (ServerConstants.RpgConfigFile rpgConfigFileEnumConst : ServerConstants.RpgConfigFile.values()) {
/*  61 */       this.resourceManager.getManagedResourceAsync(CSVData.class, "rpg/*." + rpgConfigFileEnumConst.fileType + ".csv", new ResourceListener<CSVData>() {
/*     */             public void resourceLoaded(ManagedResource<CSVData> managedResource) {
/*  63 */               CSVData itemRecords = (CSVData)managedResource.getResource();
/*  64 */               for (String[] record : itemRecords) {
/*  65 */                 ((List<String[]>)DataRecordsImpl.this.rpgFiles.get(rpgConfigFileEnumConst)).add(record);
/*     */               }
/*     */               
/*  68 */               DataRecordsImpl.this.loadedStatus.add(rpgConfigFileEnumConst);
/*  69 */               if (DataRecordsImpl.LOGGER.isEnabledFor((Priority)Level.INFO)) {
/*  70 */                 DataRecordsImpl.LOGGER.info(rpgConfigFileEnumConst + " loaded!");
/*     */               }
/*     */             }
/*     */           },  CacheType.NOT_CACHED);
/*     */     } 
/*  75 */     String resource = (String)this.resourceManager.getResource(String.class, "rpg/eventai/eventais.properties", CacheType.NOT_CACHED);
/*  76 */     Properties aiPropfile = new Properties();
/*     */     try {
/*  78 */       aiPropfile.load(new StringReader(resource));
/*  79 */     } catch (IOException e) {
/*  80 */       throw new IllegalStateException(e);
/*     */     } 
/*     */     
/*  83 */     String[] events = aiPropfile.getProperty("eventais").split(",");
/*  84 */     for (String event : events) {
/*  85 */       List<String[]> records = (List)new ArrayList<String>();
/*  86 */       CSVData aiRecords = (CSVData)this.resourceManager.getResource(CSVData.class, "rpg/eventai/" + event, CacheType.NOT_CACHED);
/*  87 */       for (String[] record : aiRecords) {
/*  88 */         records.add(record);
/*     */       }
/*  90 */       this.aiEventFiles.put(event, records);
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
/*     */   private void waitUntilLoaded(ServerConstants.RpgConfigFile enumConst) {
/* 133 */     if (LOGGER.isEnabledFor((Priority)Level.DEBUG)) {
/* 134 */       LOGGER.debug("Requested: " + enumConst);
/*     */     }
/* 136 */     while (!this.loadedStatus.contains(enumConst)) {
/*     */       try {
/* 138 */         Thread.sleep(150L);
/* 139 */       } catch (InterruptedException e) {
/* 140 */         throw new IllegalStateException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String[]> getItemsFiles() {
/* 146 */     waitUntilLoaded(ServerConstants.RpgConfigFile.ITEMS);
/* 147 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.ITEMS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getPetFiles() {
/* 152 */     waitUntilLoaded(ServerConstants.RpgConfigFile.PETS);
/* 153 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.PETS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getMonstersFiles() {
/* 158 */     waitUntilLoaded(ServerConstants.RpgConfigFile.MONSTERS);
/* 159 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.MONSTERS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getShapesFiles() {
/* 164 */     waitUntilLoaded(ServerConstants.RpgConfigFile.SHAPES);
/* 165 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.SHAPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getRectShapesFiles() {
/* 170 */     waitUntilLoaded(ServerConstants.RpgConfigFile.RECT_SHAPES);
/* 171 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.RECT_SHAPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getElementsFiles() {
/* 176 */     waitUntilLoaded(ServerConstants.RpgConfigFile.ELEMENTS);
/* 177 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.ELEMENTS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getEffectCreatorsFiles() {
/* 182 */     waitUntilLoaded(ServerConstants.RpgConfigFile.EFFECT_CREATOR);
/* 183 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.EFFECT_CREATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getSourceEffectFiltersFiles() {
/* 188 */     waitUntilLoaded(ServerConstants.RpgConfigFile.SOURCE_EFFECT_CREATOR);
/* 189 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.SOURCE_EFFECT_CREATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getTargetEffectFiltersFiles() {
/* 194 */     waitUntilLoaded(ServerConstants.RpgConfigFile.TARGET_EFFECT_CREATOR);
/* 195 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.TARGET_EFFECT_CREATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getLevelStatsFiles() {
/* 200 */     waitUntilLoaded(ServerConstants.RpgConfigFile.LEVEL_STATS);
/* 201 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.LEVEL_STATS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getStatModifierFiles() {
/* 206 */     waitUntilLoaded(ServerConstants.RpgConfigFile.STAT_MODIFIERS);
/* 207 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.STAT_MODIFIERS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getBuffFiles() {
/* 212 */     waitUntilLoaded(ServerConstants.RpgConfigFile.BUFF);
/* 213 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.BUFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getDebuffFiles() {
/* 218 */     waitUntilLoaded(ServerConstants.RpgConfigFile.DEBUFF);
/* 219 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.DEBUFF);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getProjectileCreatorsFiles() {
/* 224 */     waitUntilLoaded(ServerConstants.RpgConfigFile.PROJECTILE_CREATOR);
/* 225 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.PROJECTILE_CREATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getTargetedEffectCreatorsFiles() {
/* 230 */     waitUntilLoaded(ServerConstants.RpgConfigFile.TARGETED_EFFECTS);
/* 231 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.TARGETED_EFFECTS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getConfigFiles() {
/* 236 */     waitUntilLoaded(ServerConstants.RpgConfigFile.CONFIG);
/* 237 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.CONFIG);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getLootDescriptionFiles() {
/* 242 */     waitUntilLoaded(ServerConstants.RpgConfigFile.LOOT_DESCRIPTION);
/* 243 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.LOOT_DESCRIPTION);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getGroupLootDescriptionFiles() {
/* 248 */     waitUntilLoaded(ServerConstants.RpgConfigFile.GROUP_LOOT_DESCRIPTION);
/* 249 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.GROUP_LOOT_DESCRIPTION);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getLootMobTypes() {
/* 254 */     waitUntilLoaded(ServerConstants.RpgConfigFile.LOOT_MOB_TYPES);
/* 255 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.LOOT_MOB_TYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getMonsterLootGroups() {
/* 260 */     waitUntilLoaded(ServerConstants.RpgConfigFile.MONSTER_GROUP_LOOT);
/* 261 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.MONSTER_GROUP_LOOT);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getVendors() {
/* 266 */     waitUntilLoaded(ServerConstants.RpgConfigFile.VENDORS);
/* 267 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.VENDORS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getVendorItems() {
/* 272 */     waitUntilLoaded(ServerConstants.RpgConfigFile.VENDOR_ITEMS);
/* 273 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.VENDOR_ITEMS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getQuests() {
/* 278 */     waitUntilLoaded(ServerConstants.RpgConfigFile.QUESTS);
/* 279 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.QUESTS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getQuestRewards() {
/* 284 */     waitUntilLoaded(ServerConstants.RpgConfigFile.QUEST_REWARDS);
/* 285 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.QUEST_REWARDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getQuestObjectives() {
/* 290 */     waitUntilLoaded(ServerConstants.RpgConfigFile.QUEST_OBJECTIVES);
/* 291 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.QUEST_OBJECTIVES);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getDebuffCureFiles() {
/* 296 */     waitUntilLoaded(ServerConstants.RpgConfigFile.DEBUFF_CURE);
/* 297 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.DEBUFF_CURE);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getStartUpEquipment() {
/* 302 */     waitUntilLoaded(ServerConstants.RpgConfigFile.START_EQUIPMENT);
/* 303 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.START_EQUIPMENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getProjectilePaths() {
/* 308 */     waitUntilLoaded(ServerConstants.RpgConfigFile.PROJECTILE_PATHS);
/* 309 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.PROJECTILE_PATHS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getPickUpItemsFiles() {
/* 314 */     waitUntilLoaded(ServerConstants.RpgConfigFile.PICKUP_ITEMS);
/* 315 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.PICKUP_ITEMS);
/*     */   }
/*     */ 
/*     */   
/*     */   public DireEffectResourceLoader getDFXResourceLoader() {
/* 320 */     return new MyDireEffectResourceLoader(this.resourceManager);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getMovementManipulatorCreatorsFiles() {
/* 325 */     waitUntilLoaded(ServerConstants.RpgConfigFile.MOVEMENT_MANIPULATOR_CREATOR);
/* 326 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.MOVEMENT_MANIPULATOR_CREATOR);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getWaypointFiles() {
/* 331 */     waitUntilLoaded(ServerConstants.RpgConfigFile.WAYPOINT);
/* 332 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.WAYPOINT);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getWaypointDestinationPortals() {
/* 337 */     waitUntilLoaded(ServerConstants.RpgConfigFile.WAYPOINT_DESTINATION_PORTALS);
/* 338 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.WAYPOINT_DESTINATION_PORTALS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getCheckpointDescriptions() {
/* 343 */     waitUntilLoaded(ServerConstants.RpgConfigFile.CHECKPOINT_DESCRIPTION);
/* 344 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.CHECKPOINT_DESCRIPTION);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, List<String[]>> getAiEventFiles() {
/* 349 */     return this.aiEventFiles;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getSpawnEffectsCreatorFiles() {
/* 354 */     waitUntilLoaded(ServerConstants.RpgConfigFile.SPAWN_EFFECTS);
/* 355 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.SPAWN_EFFECTS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getPortkeys() {
/* 360 */     waitUntilLoaded(ServerConstants.RpgConfigFile.PORTKEYS);
/* 361 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.PORTKEYS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getCustomPortals() {
/* 366 */     waitUntilLoaded(ServerConstants.RpgConfigFile.CUSTOM_PORTALS);
/* 367 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.CUSTOM_PORTALS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getItemSetsFiles() {
/* 372 */     waitUntilLoaded(ServerConstants.RpgConfigFile.ITEM_SETS);
/* 373 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.ITEM_SETS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getChargedAbilityFiles() {
/* 378 */     waitUntilLoaded(ServerConstants.RpgConfigFile.CHARGED_ABILITY);
/* 379 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.CHARGED_ABILITY);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getProjectileReflectionFiles() {
/* 384 */     waitUntilLoaded(ServerConstants.RpgConfigFile.PROJECTILE_REFLECTION);
/* 385 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.PROJECTILE_REFLECTION);
/*     */   }
/*     */   
/*     */   public List<String[]> getRemoveInfiniteBuffAbilitiesFiles() {
/* 389 */     waitUntilLoaded(ServerConstants.RpgConfigFile.REMOVE_INFINITE_BUFF_ABILITIES);
/* 390 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.REMOVE_INFINITE_BUFF_ABILITIES);
/*     */   }
/*     */   
/*     */   public List<String[]> getRegionDescriptionFiles() {
/* 394 */     waitUntilLoaded(ServerConstants.RpgConfigFile.REGION_DESCRIPTIONS);
/* 395 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.REGION_DESCRIPTIONS);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getCastTimeFiles() {
/* 400 */     waitUntilLoaded(ServerConstants.RpgConfigFile.CASTTIME);
/* 401 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.CASTTIME);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getSpeachFiles() {
/* 406 */     waitUntilLoaded(ServerConstants.RpgConfigFile.SPEACH);
/* 407 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.SPEACH);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getSpeachMappingFiles() {
/* 412 */     waitUntilLoaded(ServerConstants.RpgConfigFile.SPEACH_MAPPING);
/* 413 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.SPEACH_MAPPING);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getItemSetModifierFiles() {
/* 418 */     waitUntilLoaded(ServerConstants.RpgConfigFile.ITEM_SET_MODIFIER);
/* 419 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.ITEM_SET_MODIFIER);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getGivePetAbilitiesFiles() {
/* 424 */     waitUntilLoaded(ServerConstants.RpgConfigFile.GIVE_PET_ABILITIES);
/* 425 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.GIVE_PET_ABILITIES);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getArchtypeFiles() {
/* 430 */     waitUntilLoaded(ServerConstants.RpgConfigFile.ARCHTYPES);
/* 431 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.ARCHTYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getPetArchtypeFiles() {
/* 436 */     waitUntilLoaded(ServerConstants.RpgConfigFile.PET_ARCHETYPES);
/* 437 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.PET_ARCHETYPES);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getEquipmentFiles() {
/* 442 */     waitUntilLoaded(ServerConstants.RpgConfigFile.EQUIPMENT);
/* 443 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.EQUIPMENT);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String[]> getPetLevelStatsFiles() {
/* 448 */     waitUntilLoaded(ServerConstants.RpgConfigFile.PET_LEVEL_STATS);
/* 449 */     return this.rpgFiles.get(ServerConstants.RpgConfigFile.PET_LEVEL_STATS);
/*     */   }
/*     */   
/*     */   private class MyDireEffectResourceLoader implements DireEffectResourceLoader {
/*     */     private ResourceManager resourceManager;
/*     */     
/*     */     public MyDireEffectResourceLoader(ResourceManager resourceManager) {
/* 456 */       this.resourceManager = resourceManager;
/*     */     }
/*     */     
/*     */     public Element getDireEffectData(String dfxScriptId, boolean cache) {
/* 460 */       return ((Document)this.resourceManager.getResource(Document.class, dfxScriptId, cache ? CacheType.CACHE_TEMPORARILY : CacheType.NOT_CACHED)).getRootElement();
/*     */     }
/*     */     
/*     */     public Object getParticleSystem(String particleId, boolean cacheDFX) {
/* 464 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getModelNode(String resource) {
/* 469 */       return null;
/*     */     }
/*     */ 
/*     */     
/*     */     public Object getJointAnimation(String resource) {
/* 474 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\DataRecordsImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */