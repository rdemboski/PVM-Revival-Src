/*      */ package com.funcom.rpgengine2.loader;
import com.funcom.commons.configuration.ExtProperties;
/*      */ import com.funcom.commons.dfx.DireEffectDescription;
/*      */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
import com.funcom.commons.dfx.EffectHandlerFactory;
/*      */ import com.funcom.commons.dfx.GameplayEffectDescription;
/*      */ import com.funcom.commons.dfx.NoSuchDFXException;
import com.funcom.commons.localization.LocalizationException;
import com.funcom.rpgengine2.DefaultFactionEnum;
/*      */ import com.funcom.rpgengine2.RPGFactionFilterType;
import com.funcom.rpgengine2.Stat;
import com.funcom.rpgengine2.StatsManager;
/*      */ import com.funcom.rpgengine2.abilities.Ability;
import com.funcom.rpgengine2.abilities.AbilityContainer;
import com.funcom.rpgengine2.abilities.BuffType;
/*      */ import com.funcom.rpgengine2.abilities.movement.MovementManipulatorFactory;
/*      */ import com.funcom.rpgengine2.abilities.projectile.ProjectileFactory;
import com.funcom.rpgengine2.abilities.projectileReflection.ProjectileReflectionFactory;
/*      */ import com.funcom.rpgengine2.abilities.projectileReflection.ProjectileReflectorFactory;
/*      */ import com.funcom.rpgengine2.abilities.targetedeffect.TargetedEffectFactory;
/*      */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*      */ import com.funcom.rpgengine2.abilities.values.GeneralValueParser;
import com.funcom.rpgengine2.buffs.RemoveInfiniteBuffFactory;
/*      */ import com.funcom.rpgengine2.checkpoints.CheckpointDescription;
/*      */ import com.funcom.rpgengine2.checkpoints.CheckpointManager;
/*      */ import com.funcom.rpgengine2.combat.AbstractRectShape;
/*      */ import com.funcom.rpgengine2.combat.AbstractShape;
import com.funcom.rpgengine2.combat.AllRectShape;
import com.funcom.rpgengine2.combat.AllShape;
/*      */ import com.funcom.rpgengine2.combat.Element;
import com.funcom.rpgengine2.combat.ElementDescription;
/*      */ import com.funcom.rpgengine2.combat.ElementManager;
import com.funcom.rpgengine2.combat.SelfShape;
import com.funcom.rpgengine2.combat.Shape;
/*      */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*      */ import com.funcom.rpgengine2.combat.ShapeManager;
import com.funcom.rpgengine2.combat.SingleRectShape;
import com.funcom.rpgengine2.combat.SingleShape;
/*      */ import com.funcom.rpgengine2.dfx.DFXValidity;
/*      */ import com.funcom.rpgengine2.dfx.ItemDFXHandlerFactory;
/*      */ import com.funcom.rpgengine2.equipment.ArchType;
import com.funcom.rpgengine2.equipment.ArchtypeManager;
/*      */ import com.funcom.rpgengine2.equipment.EquipmentBuilder;
/*      */ import com.funcom.rpgengine2.equipment.PetArchType;
import com.funcom.rpgengine2.equipment.PetArchtypeManager;
/*      */ import com.funcom.rpgengine2.equipment.PetEquipmentBuilder;
import com.funcom.rpgengine2.items.DefaultItemFactory;
/*      */ import com.funcom.rpgengine2.items.ItemDescription;
/*      */ import com.funcom.rpgengine2.items.ItemFactory;
/*      */ import com.funcom.rpgengine2.items.ItemManager;
import com.funcom.rpgengine2.items.ItemSetDesc;
/*      */ import com.funcom.rpgengine2.items.ItemSetManager;
/*      */ import com.funcom.rpgengine2.items.ItemType;
/*      */ import com.funcom.rpgengine2.loot.LevelLootDescription;
/*      */ import com.funcom.rpgengine2.loot.LootDescription;
/*      */ import com.funcom.rpgengine2.loot.LootManager;
/*      */ import com.funcom.rpgengine2.loot.LootType;
import com.funcom.rpgengine2.loot.MonsterGroupLoot;
/*      */ import com.funcom.rpgengine2.monsters.MonsterDescription;
/*      */ import com.funcom.rpgengine2.monsters.MonsterManager;
/*      */ import com.funcom.rpgengine2.pets.PetDescription;
/*      */ import com.funcom.rpgengine2.pets.PetManager;
/*      */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
import com.funcom.rpgengine2.pickupitems.DefaultPickUpType;
import com.funcom.rpgengine2.pickupitems.PickUpManager;
/*      */ import com.funcom.rpgengine2.pickupitems.PickupType;
/*      */ import com.funcom.rpgengine2.portals.CustomPortalManager;
/*      */ import com.funcom.rpgengine2.portkey.PortkeyManager;
import com.funcom.rpgengine2.quests.DefaultQuestFactory;
/*      */ import com.funcom.rpgengine2.quests.QuestDescription;
/*      */ import com.funcom.rpgengine2.quests.QuestFactory;
import com.funcom.rpgengine2.quests.QuestManager;
/*      */ import com.funcom.rpgengine2.quests.objectives.CollectObjective;
import com.funcom.rpgengine2.quests.objectives.CollectPetObjective;
import com.funcom.rpgengine2.quests.objectives.FinishMissionObjective;
import com.funcom.rpgengine2.quests.objectives.GoToObjective;
import com.funcom.rpgengine2.quests.objectives.HandInQuestObjective;
import com.funcom.rpgengine2.quests.objectives.KillObjective;
/*      */ import com.funcom.rpgengine2.quests.objectives.ObjectiveSpecialType;
import com.funcom.rpgengine2.quests.objectives.ObjectiveType;
import com.funcom.rpgengine2.quests.objectives.PetLevelObjective;
/*      */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*      */ import com.funcom.rpgengine2.quests.objectives.RegistrationObjective;
import com.funcom.rpgengine2.quests.objectives.SpecialAmountObjective;
import com.funcom.rpgengine2.quests.objectives.SpecialTutorialObjective;
/*      */ import com.funcom.rpgengine2.quests.objectives.SubscriptionObjective;
/*      */ import com.funcom.rpgengine2.quests.objectives.UseItemObjective;
/*      */ import com.funcom.rpgengine2.regions.RegionManager;
/*      */ import com.funcom.rpgengine2.speach.SpeachContext;
import com.funcom.rpgengine2.speach.SpeachManager;
/*      */ import com.funcom.rpgengine2.startupequipment.StartUpEquipmentManager;
/*      */ import com.funcom.rpgengine2.vendor.VendorManager;
/*      */ import com.funcom.rpgengine2.waypoints.WaypointManager;
/*      */ import java.io.IOException;
/*      */ import java.util.HashMap;
import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;

import org.apache.log4j.Logger;
/*      */ 
/*      */ public class RpgLoader {
/*   64 */   protected static final Logger LOG = Logger.getLogger(RpgLoader.class.getName());
/*      */   
/*      */   private List<RpgDataLoadHook> loadHooks;
/*      */   
/*      */   protected DataRecords dataRecords;
/*      */   private ItemManager itemManager;
/*      */   protected PetManager petManager;
/*      */   protected QuestManager questManager;
/*   72 */   private final ShapeManager shapeManager = new ShapeManager();
/*   73 */   private final ElementManager elementManager = new ElementManager();
/*   74 */   private final StatsManager statsManager = new StatsManager();
/*   75 */   private final LootManager lootManager = new LootManager();
/*   76 */   private final VendorManager vendorManager = new VendorManager();
/*   77 */   private final WaypointManager waypointManager = new WaypointManager();
/*   78 */   private final StartUpEquipmentManager startUpManager = new StartUpEquipmentManager();
/*   79 */   private final PickUpManager pickUpManager = new PickUpManager();
/*   80 */   private final CheckpointManager checkpointManager = new CheckpointManager();
/*   81 */   private final PortkeyManager portkeyManager = new PortkeyManager();
/*   82 */   private final CustomPortalManager customPortalManager = new CustomPortalManager();
/*   83 */   private final RegionManager regionManager = new RegionManager();
/*   84 */   private final SpeachManager speachManager = new SpeachManager();
/*   85 */   private final ItemSetManager itemSetManager = new ItemSetManager();
/*   86 */   private final ArchtypeManager archtypeManager = new ArchtypeManager();
/*   87 */   private final PetArchtypeManager petArchtypeManager = new PetArchtypeManager();
/*      */   
/*   89 */   private GeneralValueParser valueParser = new GeneralValueParser();
/*      */   private Map<String, RawData> rawDataMap;
/*      */   private ShapeDataEvaluator evaluator;
/*      */   protected StatIdTranslator statIdTranslator;
/*      */   protected ExtProperties configs;
/*      */   private MonsterManager monsterManager;
/*      */   private ProjectileFactory projectileFactory;
/*      */   private TargetedEffectFactory targetedEffectFactory;
/*   97 */   private Map<String, String> projectilePathMap = new HashMap<String, String>();
/*      */   private Short levelStatId;
/*   99 */   private Map<String, Class<? extends AbstractShape>> shapeClasses = new HashMap<String, Class<? extends AbstractShape>>();
/*  100 */   private Map<String, Class<? extends AbstractRectShape>> rectShapeClasses = new HashMap<String, Class<? extends AbstractRectShape>>();
/*      */   
/*  102 */   private ItemFactory itemFactory = DefaultItemFactory.INSTANCE;
/*  103 */   private QuestFactory questFactory = DefaultQuestFactory.INSTANCE;
/*      */   
/*      */   protected DireEffectDescriptionFactory dfxDescriptionFactory;
/*      */   protected final ConfigErrors configErrors;
/*  107 */   private Class<? extends Enum> factionEnumClass = (Class)DefaultFactionEnum.class;
/*      */   
/*      */   private MovementManipulatorFactory movementManipulatorFactory;
/*      */   private ValueAccumulatorFactory valueAccumulatorFactory;
/*      */   private ProjectileReflectorFactory projectileReflectorFactory;
/*      */   
/*      */   public RpgLoader(ConfigErrors configErrors) {
/*  114 */     this.configErrors = configErrors;
/*      */     
/*  116 */     this.itemManager = createItemManager(configErrors);
/*  117 */     this.petManager = new PetManager(configErrors);
/*  118 */     this.loadHooks = new LinkedList<RpgDataLoadHook>();
/*  119 */     this.valueParser.addGroup("abilityValues", new DynamicValueParser());
/*  120 */     setQuestManager();
/*      */     
/*  122 */     initPickUpTypes();
/*      */   }
/*      */   
/*      */   protected void setQuestManager() {
/*  126 */     this.questManager = new QuestManager();
/*      */   }
/*      */   
/*      */   public void addDataLoadHook(RpgDataLoadHook rpgDataLoadHook) {
/*  130 */     this.loadHooks.add(rpgDataLoadHook);
/*      */   }
/*      */   
/*      */   public void removeDataLoadHook(RpgDataLoadHook rpgDataLoadHook) {
/*  134 */     this.loadHooks.remove(rpgDataLoadHook);
/*      */   }
/*      */   
/*      */   protected void fireLoadPerformed(String id) {
/*  138 */     for (RpgDataLoadHook loadHook : this.loadHooks) {
/*  139 */       loadHook.genericDataLoaded(id, this);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void fireCheckpointLoadPerformed(CheckpointDescription description) {
/*  144 */     for (RpgDataLoadHook loadHook : this.loadHooks) {
/*  145 */       loadHook.checkpointLoaded(description, this);
/*      */     }
/*      */   }
/*      */   
/*      */   protected void initPickUpTypes() {
/*  150 */     this.pickUpManager.putType(DefaultPickUpType.ITEM);
/*  151 */     this.pickUpManager.putType(DefaultPickUpType.PET);
/*      */   }
/*      */   
/*      */   protected ItemManager createItemManager(ConfigErrors configErrors) {
/*  155 */     return new ItemManager(configErrors);
/*      */   }
/*      */   
/*      */   public void setDataRecords(DataRecords dataRecords) {
/*  159 */     this.dataRecords = dataRecords;
/*      */   }
/*      */ 
/*      */   
/*      */   public void load() throws IOException {
/*  164 */     if (this.evaluator == null) {
/*  165 */       throw new NullPointerException("no " + ShapeDataEvaluator.class);
/*      */     }
/*      */     
/*  168 */     if (this.statIdTranslator == null) {
/*  169 */       throw new NullPointerException("no " + StatIdTranslator.class);
/*      */     }
/*      */     
/*  172 */     long start = System.currentTimeMillis();
/*      */     
/*  174 */     this.monsterManager = new MonsterManager(this.configErrors, this.factionEnumClass);
/*      */     
/*  176 */     this.configErrors.clear();
/*      */     
/*  178 */     setupDFXFactory();
/*      */     
/*  180 */     dependencyInjection();
/*      */     
/*  182 */     initShapeClasses();
/*      */     
/*  184 */     loadConfig();
/*      */     
/*  186 */     this.rawDataMap = loadAbilitiesData();
/*      */     
/*  188 */     loadArcShapes();
/*      */     
/*  190 */     loadRectShapes();
/*      */     
/*  192 */     loadElements();
/*      */     
/*  194 */     loadStats();
/*      */     
/*  196 */     loadArchtypes();
/*      */     
/*  198 */     loadPetArchtypes();
/*      */     
/*  200 */     buildEquipment(this.rawDataMap);
/*      */     
/*  202 */     buildPetEquipment(this.rawDataMap);
/*      */     
/*  204 */     loadItemDescriptions(this.rawDataMap);
/*      */     
/*  206 */     loadCastTimes();
/*      */     
/*  208 */     loadItemSetDescriptions();
/*      */     
/*  210 */     loadItemSetModifiers();
/*      */     
/*  212 */     loadMonsterDescriptions();
/*      */     
/*  214 */     loadPetFile();
/*      */     
/*  216 */     loadPickUpItemsFile();
/*      */     
/*  218 */     loadLootDescriptions(this.rawDataMap);
/*      */     
/*  220 */     loadGroupLootDescriptions(this.rawDataMap);
/*      */     
/*  222 */     loadLootMobs(this.rawDataMap);
/*      */     
/*  224 */     loadMonsterLoots(this.rawDataMap);
/*      */     
/*  226 */     loadVendors(this.rawDataMap);
/*      */     
/*  228 */     loadVendorItems(this.rawDataMap);
/*      */     
/*  230 */     loadQuests();
/*      */     
/*  232 */     loadStartUpEquipment();
/*      */     
/*  234 */     loadProjectilePaths();
/*      */     
/*  236 */     loadWaypoints();
/*      */     
/*  238 */     loadWaypointDestinationPortals();
/*      */     
/*  240 */     loadCheckpoints();
/*      */     
/*  242 */     loadPortkeys();
/*      */     
/*  244 */     loadCustomPortals();
/*      */     
/*  246 */     loadRegionDescriptions();
/*      */     
/*  248 */     loadSpeach();
/*      */     
/*  250 */     loadSpeachMapping();
/*      */     
/*  252 */     this.itemManager.init();
/*  253 */     LOG.info("Rpg Data Loaded in " + (System.currentTimeMillis() - start) + " ms");
/*      */   }
/*      */   
/*      */   private FieldMap createItemSetModifiersMap(String[] fields) {
/*  257 */     return new FieldMap((Object[])ItemSetModifierFields.values(), fields);
/*      */   }
/*      */   
/*      */   private void loadItemSetModifiers() {
/*  261 */     FieldMap fieldMap = null;
/*  262 */     for (String[] fields : this.dataRecords.getItemSetModifierFiles()) {
/*  263 */       fieldMap = createItemSetModifiersMap(fields);
/*  264 */       String setId = fieldMap.get(ItemSetModifierFields.ITEM_SET_ID);
/*  265 */       int numItems = fieldMap.getInt(ItemSetModifierFields.NUM_ITEMS, 0);
/*  266 */       String itemId = fieldMap.get(ItemSetModifierFields.ITEM_ID);
/*  267 */       int tier = fieldMap.getInt(ItemSetModifierFields.TIER, 0);
/*      */       
/*  269 */       ItemDescription itemDesc = this.itemManager.getDescription(itemId, tier);
/*  270 */       this.itemSetManager.addItemSetModifier(setId, numItems, itemDesc);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadCastTimes() {
/*  275 */     FieldMap fieldMap = null;
/*  276 */     String itemId = "Unknown.";
/*  277 */     for (String[] fields : this.dataRecords.getCastTimeFiles()) {
/*  278 */       fieldMap = createCastTimeFieldMap(fields);
/*  279 */       itemId = fieldMap.get(CastTimeDescriptionFields.ID);
/*  280 */       if (itemId.isEmpty()) {
/*  281 */         throw new RuntimeException("Cast Time without id! Empty line?");
/*      */       }
/*  283 */       String tierStr = fieldMap.get(CastTimeDescriptionFields.TIER);
/*  284 */       int tier = 0;
/*  285 */       if (!tierStr.isEmpty()) {
/*  286 */         tier = Integer.parseInt(tierStr);
/*      */       }
/*  288 */       ItemDescription description = this.itemManager.getDescription(itemId, tier);
/*  289 */       if (description == null) {
/*  290 */         throw new RuntimeException("Cast time for non-existing item: " + itemId + " tier: " + tier);
/*      */       }
/*      */       
/*  293 */       String castTimeStr = fieldMap.get(CastTimeDescriptionFields.CAST_TIME);
/*  294 */       float castTime = Float.parseFloat(castTimeStr);
/*  295 */       description.setCastTime(castTime);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private FieldMap createCastTimeFieldMap(String[] fields) {
/*  301 */     return new FieldMap((Object[])CastTimeDescriptionFields.values(), fields);
/*      */   }
/*      */   
/*      */   private void setupDFXFactory() {
/*  305 */     if (this.dfxDescriptionFactory == null) {
/*  306 */       ItemDFXHandlerFactory dfxHandlerFactory = new ItemDFXHandlerFactory(this.evaluator);
/*      */ 
/*      */ 
/*      */       
/*  310 */       GameplayEffectDescription defaultGameEffectDesc = new GameplayEffectDescription();
/*  311 */       defaultGameEffectDesc.setHandlerFactory((EffectHandlerFactory)dfxHandlerFactory);
/*  312 */       defaultGameEffectDesc.setResource("");
/*      */       
/*  314 */       defaultGameEffectDesc.setEndTime(defaultGameEffectDesc.getStartTime());
/*      */ 
/*      */       
/*  317 */       RpgDireEffectDescriptionFactory factory = new RpgDireEffectDescriptionFactory(this.dataRecords.getDFXResourceLoader(), defaultGameEffectDesc);
/*  318 */       factory.setDescriptionFactory(new RpgEffectDescriptionFactory((EffectHandlerFactory)dfxHandlerFactory));
/*      */ 
/*      */       
/*  321 */       DireEffectDescription defaultDFXDescription = factory.newDFXDescription("", false);
/*  322 */       factory.checkDFXDescription(defaultDFXDescription, false);
/*  323 */       factory.putDefaultDireEffectDescription(defaultDFXDescription);
/*      */       
/*  325 */       this.dfxDescriptionFactory = factory;
/*      */     } 
/*      */   }
/*      */   
/*      */   private void initShapeClasses() {
/*  330 */     addShapeClass((Class)SingleShape.class, new String[] { "single" });
/*  331 */     addShapeClass((Class)AllShape.class, new String[] { "all" });
/*  332 */     addShapeClass((Class)SelfShape.class, new String[] { "self" });
/*  333 */     addRectShapeClass((Class)SingleRectShape.class, new String[] { "single" });
/*  334 */     addRectShapeClass((Class)AllRectShape.class, new String[] { "all" });
/*      */   }
/*      */ 
/*      */   
/*      */   public void reload() throws IOException {
/*  339 */     if (this.evaluator == null) {
/*  340 */       throw new NullPointerException("no " + ShapeDataEvaluator.class);
/*      */     }
/*      */     
/*  343 */     if (this.statIdTranslator == null) {
/*  344 */       throw new NullPointerException("no " + StatIdTranslator.class);
/*      */     }
/*      */     
/*  347 */     long start = System.currentTimeMillis();
/*      */     
/*  349 */     dependencyInjection();
/*      */     
/*  351 */     loadConfig();
/*      */     
/*  353 */     this.rawDataMap = loadAbilitiesData();
/*      */     
/*  355 */     synchronized (this.shapeManager) {
/*  356 */       this.shapeManager.clearData();
/*  357 */       loadArcShapes();
/*  358 */       loadRectShapes();
/*      */     } 
/*      */     
/*  361 */     synchronized (this.elementManager) {
/*  362 */       this.elementManager.clearData();
/*  363 */       loadElements();
/*      */     } 
/*      */     
/*  366 */     synchronized (this.statsManager) {
/*  367 */       this.statsManager.clearData();
/*  368 */       loadStats();
/*      */     } 
/*      */     
/*  371 */     synchronized (this.archtypeManager) {
/*  372 */       this.archtypeManager.clear();
/*  373 */       loadArchtypes();
/*      */     } 
/*      */     
/*  376 */     synchronized (this.petArchtypeManager) {
/*  377 */       this.petArchtypeManager.clear();
/*  378 */       loadPetArchtypes();
/*      */     } 
/*      */ 
/*      */     
/*  382 */     synchronized (this.itemManager) {
/*  383 */       this.itemManager.clearData();
/*  384 */       loadItemDescriptions(this.rawDataMap);
/*  385 */       loadItemSetDescriptions();
/*  386 */       buildEquipment(this.rawDataMap);
/*  387 */       buildPetEquipment(this.rawDataMap);
/*      */     } 
/*      */     
/*  390 */     synchronized (this.itemSetManager) {
/*  391 */       this.itemSetManager.clear();
/*  392 */       loadItemSetModifiers();
/*      */     } 
/*      */     
/*  395 */     synchronized (this.monsterManager) {
/*  396 */       this.monsterManager.clearData();
/*  397 */       loadMonsterDescriptions();
/*      */     } 
/*      */     
/*  400 */     synchronized (this.petManager) {
/*  401 */       this.petManager.clearData();
/*  402 */       loadPetFile();
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  407 */     synchronized (this.pickUpManager) {
/*  408 */       this.pickUpManager.clearData();
/*  409 */       loadPickUpItemsFile();
/*      */     } 
/*      */     
/*  412 */     synchronized (this.lootManager) {
/*  413 */       this.lootManager.clearData();
/*  414 */       loadLootDescriptions(this.rawDataMap);
/*  415 */       loadLootMobs(this.rawDataMap);
/*  416 */       loadGroupLootDescriptions(this.rawDataMap);
/*      */     } 
/*      */     
/*  419 */     synchronized (this.vendorManager) {
/*  420 */       this.vendorManager.clearData();
/*  421 */       loadVendors(this.rawDataMap);
/*  422 */       loadVendorItems(this.rawDataMap);
/*      */     } 
/*      */     
/*  425 */     synchronized (this.questManager) {
/*  426 */       this.questManager.clearData();
/*  427 */       loadQuests();
/*      */     } 
/*      */     
/*  430 */     synchronized (this.startUpManager) {
/*  431 */       this.startUpManager.clearData();
/*  432 */       loadStartUpEquipment();
/*      */     } 
/*      */     
/*  435 */     synchronized (this.projectilePathMap) {
/*  436 */       this.projectilePathMap.clear();
/*  437 */       loadProjectilePaths();
/*      */     } 
/*      */     
/*  440 */     synchronized (this.checkpointManager) {
/*  441 */       this.checkpointManager.clearData();
/*  442 */       loadCheckpoints();
/*      */     } 
/*      */     
/*  445 */     synchronized (this.waypointManager) {
/*  446 */       this.waypointManager.clear();
/*  447 */       loadWaypoints();
/*  448 */       loadWaypointDestinationPortals();
/*      */     } 
/*      */     
/*  451 */     synchronized (this.portkeyManager) {
/*  452 */       this.portkeyManager.clear();
/*  453 */       loadPortkeys();
/*      */     } 
/*      */     
/*  456 */     synchronized (this.customPortalManager) {
/*  457 */       this.customPortalManager.clear();
/*  458 */       loadCustomPortals();
/*      */     } 
/*      */     
/*  461 */     synchronized (this.regionManager) {
/*  462 */       this.regionManager.clear();
/*  463 */       loadRegionDescriptions();
/*      */     } 
/*      */     
/*  466 */     synchronized (this.speachManager) {
/*  467 */       this.speachManager.clear();
/*  468 */       loadSpeach();
/*  469 */       loadSpeachMapping();
/*      */     } 
/*      */     
/*  472 */     this.itemManager.init();
/*  473 */     LOG.info("Rpg Data Reloaded in " + (System.currentTimeMillis() - start) + " ms");
/*      */   }
/*      */   
/*      */   private void loadItemSetDescriptions() {
/*  477 */     FieldMap fieldMap = null;
/*      */     
/*  479 */     String itemSetId = "Unknown.";
/*      */     try {
/*  481 */       for (String[] fields : this.dataRecords.getItemSetsFiles()) {
/*  482 */         fieldMap = createItemSetFieldMap(fields);
/*  483 */         itemSetId = fieldMap.get(ItemSetDescriptionFields.ID);
/*  484 */         if (itemSetId.isEmpty()) {
/*  485 */           throw new RuntimeException("Item set without id! Empty line?");
/*      */         }
/*  487 */         String name = fieldMap.get(ItemSetDescriptionFields.NAME);
/*  488 */         String icon = fieldMap.get(ItemSetDescriptionFields.ICON);
/*  489 */         this.itemManager.addItemSet(new ItemSetDesc(itemSetId, name, icon));
/*      */       } 
/*  491 */     } catch (Exception e) {
/*  492 */       throw new RuntimeException("Error parsing item set id: " + itemSetId + "\nerror after field=" + ((fieldMap != null) ? fieldMap.getLastGottenKeyString() : "(no field map)"), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private FieldMap createItemSetFieldMap(String[] fields) {
/*  499 */     return new FieldMap((Object[])ItemSetDescriptionFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected void dependencyInjection() {
/*  503 */     this.petManager.setItemManager(this.itemManager);
/*  504 */     this.itemManager.setPetManager(this.petManager);
/*  505 */     this.questManager.setMonsterManager(this.monsterManager);
/*  506 */     this.startUpManager.setItemManager(this.itemManager);
/*  507 */     this.startUpManager.setPetManager(this.petManager);
/*      */   }
/*      */   
/*      */   private void loadPortkeys() {
/*  511 */     this.portkeyManager.createPortkeyDescriptions(this.dataRecords);
/*      */   }
/*      */   
/*      */   private void loadCustomPortals() {
/*  515 */     this.customPortalManager.createCustomPortals(this.dataRecords);
/*      */   }
/*      */   
/*      */   private void loadCheckpoints() {
/*  519 */     this.checkpointManager.createCheckpointDescription(this.dataRecords);
/*  520 */     for (CheckpointDescription checkpointDescription : this.checkpointManager.getAllCheckpointDescriptions()) {
/*  521 */       fireCheckpointLoadPerformed(checkpointDescription);
/*      */     }
/*      */   }
/*      */   
/*      */   private void loadWaypointDestinationPortals() {
/*  526 */     this.waypointManager.createWaypointDestinationPortal(this.dataRecords);
/*      */   }
/*      */   
/*      */   private void loadWaypoints() {
/*  530 */     this.waypointManager.createWaypoint(this.dataRecords);
/*      */   }
/*      */   
/*      */   private void loadMonsterDescriptions() {
/*  534 */     this.monsterManager.read(this.dataRecords, this.itemManager);
/*      */   }
/*      */   
/*      */   private void loadProjectilePaths() {
/*  538 */     for (String[] fields : this.dataRecords.getProjectilePaths()) {
/*  539 */       int index = 0;
/*  540 */       String key = fields[index++];
/*  541 */       String value = fields[index];
/*      */       
/*  543 */       this.projectilePathMap.put(key, value);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadConfig() {
/*  549 */     this.configs = new ExtProperties();
/*  550 */     for (String[] fields : this.dataRecords.getConfigFiles()) {
/*  551 */       int index = 0;
/*  552 */       String key = fields[index++];
/*  553 */       String value = fields[index];
/*  554 */       this.configs.setProperty(RpgConfigConstants.valueOf(key.toUpperCase()), value);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadElements() {
/*  565 */     for (String[] fields : this.dataRecords.getElementsFiles()) {
/*  566 */       int index = 0;
/*  567 */       String elementId = fields[index++];
/*  568 */       index++;
/*  569 */       String elementName = fields[index++];
/*  570 */       String fontColor = fields[index++];
/*  571 */       String deathDfx = fields[index++];
/*  572 */       String petCardImage = fields[index];
/*      */       
/*  574 */       Element element = Element.valueOf(elementId.toUpperCase());
/*  575 */       this.elementManager.putElement(element, new ElementDescription(elementName, fontColor, deathDfx, petCardImage));
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadArcShapes() {
/*  583 */     for (String[] fields : this.dataRecords.getShapesFiles()) {
/*  584 */       int index = 0;
/*  585 */       String shapeId = fields[index++];
/*  586 */       String shapeName = fields[index++];
/*  587 */       float offsetX = Float.parseFloat(fields[index++]);
/*  588 */       float offsetY = Float.parseFloat(fields[index++]);
/*  589 */       float angleStart = Float.parseFloat(fields[index++]);
/*  590 */       float angleEnd = Float.parseFloat(fields[index++]);
/*  591 */       float distance = Float.parseFloat(fields[index++]);
/*  592 */       String type = fields[index++];
/*  593 */       boolean isMelee = Boolean.parseBoolean(fields[index++]);
/*  594 */       RPGFactionFilterType rpgFactionFilterType = RPGFactionFilterType.getFactionEnum(fields[index++]);
/*      */       
/*      */       try {
/*  597 */         Class<? extends AbstractShape> shapeClass = getShapeClass(type);
/*  598 */         AbstractShape shape = shapeClass.newInstance();
/*  599 */         shape.init(shapeId, shapeName, offsetX, offsetY, angleStart, angleEnd, distance, isMelee, rpgFactionFilterType);
/*  600 */         this.shapeManager.putShape((Shape)shape);
/*  601 */       } catch (ClassNotFoundException e) {
/*  602 */         e.printStackTrace();
/*  603 */       } catch (IllegalAccessException e) {
/*  604 */         e.printStackTrace();
/*  605 */       } catch (InstantiationException e) {
/*  606 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadRectShapes() {
/*  615 */     for (String[] fields : this.dataRecords.getRectShapesFiles()) {
/*  616 */       int index = 0;
/*  617 */       String shapeId = fields[index++];
/*  618 */       String shapeName = fields[index++];
/*  619 */       float offsetX = Float.parseFloat(fields[index++]);
/*  620 */       float offsetY = Float.parseFloat(fields[index++]);
/*  621 */       float width = Float.parseFloat(fields[index++]);
/*  622 */       float height = Float.parseFloat(fields[index++]);
/*  623 */       String type = fields[index++];
/*  624 */       RPGFactionFilterType rpgFactionFilterType = RPGFactionFilterType.getFactionEnum(fields[index++]);
/*      */       
/*      */       try {
/*  627 */         Class<? extends AbstractRectShape> shapeClass = getRectShapeClass(type);
/*  628 */         AbstractRectShape shape = shapeClass.newInstance();
/*  629 */         shape.init(shapeId, shapeName, width, height, offsetX, offsetY, rpgFactionFilterType);
/*  630 */         this.shapeManager.putShape((Shape)shape);
/*  631 */       } catch (ClassNotFoundException e) {
/*  632 */         e.printStackTrace();
/*  633 */       } catch (IllegalAccessException e) {
/*  634 */         e.printStackTrace();
/*  635 */       } catch (InstantiationException e) {
/*  636 */         e.printStackTrace();
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void addShapeClass(Class<? extends AbstractShape> singleShapeClass, String... mappedTypeNames) {
/*  642 */     for (String mappedTypeName : mappedTypeNames) {
/*  643 */       this.shapeClasses.put(mappedTypeName.toLowerCase(), singleShapeClass);
/*      */     }
/*      */   }
/*      */   
/*      */   public void addRectShapeClass(Class<? extends AbstractRectShape> singleShapeClass, String... mappedTypeNames) {
/*  648 */     for (String mappedTypeName : mappedTypeNames) {
/*  649 */       this.rectShapeClasses.put(mappedTypeName.toLowerCase(), singleShapeClass);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private Class<? extends AbstractShape> getShapeClass(String type) throws ClassNotFoundException {
/*  655 */     Class<? extends AbstractShape> ret = this.shapeClasses.get(type.toLowerCase());
/*      */     
/*  657 */     if (ret != null) {
/*  658 */       return ret;
/*      */     }
/*      */     
/*  661 */     String availableTypes = "";
/*      */     
/*  663 */     for (String key : this.shapeClasses.keySet()) {
/*  664 */       availableTypes = availableTypes + "    " + key + "\n";
/*      */     }
/*      */     
/*  667 */     throw new ClassNotFoundException("Cannot find shape class for shape type: type " + type + "\n" + "\n    -----------------------\n" + "    --- AVAILABLE TYPES ---\n" + "    -----------------------\n" + availableTypes + "    -----------------------\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class<? extends AbstractRectShape> getRectShapeClass(String type) throws ClassNotFoundException {
/*  677 */     Class<? extends AbstractRectShape> ret = this.rectShapeClasses.get(type.toLowerCase());
/*      */     
/*  679 */     if (ret != null) {
/*  680 */       return ret;
/*      */     }
/*      */     
/*  683 */     String availableTypes = "";
/*      */     
/*  685 */     for (String key : this.rectShapeClasses.keySet()) {
/*  686 */       availableTypes = availableTypes + "    " + key + "\n";
/*      */     }
/*      */     
/*  689 */     throw new ClassNotFoundException("Cannot find shape class for shape type: type " + type + "\n" + "\n    -----------------------\n" + "    --- AVAILABLE TYPES ---\n" + "    -----------------------\n" + availableTypes + "    -----------------------\n");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Map<String, RawData> loadAbilitiesData() throws IOException {
/*  698 */     HashMap<String, RawData> ret = new HashMap<String, RawData>();
/*      */     
/*  700 */     loadAbilitiesData(ret);
/*      */     
/*  702 */     return ret;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadAbilitiesData(Map<String, RawData> returnData) throws IOException {
/*  708 */     List<String[]> files = this.dataRecords.getEffectCreatorsFiles();
/*  709 */     loadFileList(files, new EffectCreatorFactory(), returnData);
/*      */ 
/*      */     
/*  712 */     files = this.dataRecords.getSourceEffectFiltersFiles();
/*  713 */     loadFileList(files, new EffectFilterFactory(true), returnData);
/*      */ 
/*      */     
/*  716 */     files = this.dataRecords.getTargetEffectFiltersFiles();
/*  717 */     loadFileList(files, new EffectFilterFactory(false), returnData);
/*      */ 
/*      */     
/*  720 */     files = this.dataRecords.getStatModifierFiles();
/*  721 */     loadFileList(files, new StatModifierFactory(), returnData);
/*      */     
/*  723 */     loadBuffs(returnData);
/*      */ 
/*      */     
/*  726 */     files = this.dataRecords.getProjectileCreatorsFiles();
/*  727 */     loadFileList(files, new ProjectileCreatorFactory(), returnData);
/*      */ 
/*      */     
/*  730 */     files = this.dataRecords.getMovementManipulatorCreatorsFiles();
/*  731 */     loadFileList(files, new MovementManipulatorCreatorFactory(), returnData);
/*      */ 
/*      */     
/*  734 */     files = this.dataRecords.getTargetedEffectCreatorsFiles();
/*  735 */     loadFileList(files, new TargetedEffectCreatorFactory(), returnData);
/*      */ 
/*      */     
/*  738 */     files = this.dataRecords.getChargedAbilityFiles();
/*  739 */     loadFileList(files, new ChargedAbilityCreatorFactory(), returnData);
/*      */     
/*  741 */     files = this.dataRecords.getProjectileReflectionFiles();
/*  742 */     loadFileList(files, (AbilityFactory)new ProjectileReflectionFactory(), returnData);
/*      */     
/*  744 */     files = this.dataRecords.getRemoveInfiniteBuffAbilitiesFiles();
/*  745 */     loadFileList(files, (AbilityFactory)new RemoveInfiniteBuffFactory(), returnData);
/*      */     
/*  747 */     files = this.dataRecords.getGivePetAbilitiesFiles();
/*  748 */     loadFileList(files, new GivePetAbilityFactory(), returnData);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadBuffs(Map<String, RawData> returnData) throws IOException {
/*  754 */     List<String[]> files = this.dataRecords.getBuffFiles();
/*  755 */     loadFileList(files, new BuffCreatorFactory(BuffType.BUFF), returnData);
/*      */ 
/*      */     
/*  758 */     files = this.dataRecords.getDebuffFiles();
/*  759 */     loadFileList(files, new BuffCreatorFactory(BuffType.DEBUFF), returnData);
/*      */   }
/*      */   
/*      */   protected void loadFileList(List<String[]> files, AbilityFactory abilityFactory, Map<String, RawData> ret) throws IOException {
/*  763 */     for (String[] fields : files) {
/*  764 */       RawData data = ret.get(fields[0]);
/*  765 */       if (data == null) {
/*  766 */         data = new RawData(abilityFactory);
/*  767 */         ret.put(fields[0], data);
/*      */       } 
/*  769 */       data.addFields(fields);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadStats() {
/*  774 */     this.statsManager.setStatIdTranslator(this.statIdTranslator);
/*      */     
/*  776 */     for (String[] fields : this.dataRecords.getLevelStatsFiles()) {
/*  777 */       String statIdStr = fields[0];
/*  778 */       int level = Integer.parseInt(fields[1]);
/*  779 */       int permanentValue = Integer.parseInt(fields[2]);
/*      */       
/*  781 */       Short statId = this.statIdTranslator.translate(statIdStr);
/*  782 */       Stat stat = new Stat(statId, permanentValue);
/*  783 */       this.statsManager.put(level, stat);
/*      */     } 
/*      */     
/*  786 */     this.statsManager.ready();
/*      */   }
/*      */   
/*      */   private void loadRegionDescriptions() {
/*  790 */     this.regionManager.createRegionDescriptions(this.dataRecords, this);
/*      */   }
/*      */   
/*      */   private void loadItemDescriptions(Map<String, RawData> rawDataMap) {
/*  794 */     FieldMap fieldMap = null;
/*      */     
/*  796 */     String itemId = "Unknown.";
/*      */     try {
/*  798 */       for (String[] fields : this.dataRecords.getItemsFiles()) {
/*  799 */         Integer itemLevel; ItemDescription itemDescription; Integer itemValueAmount; fieldMap = createItemFieldMap(fields);
/*  800 */         itemId = fieldMap.get(ItemDescriptionFields.ID);
/*  801 */         if (itemId.isEmpty()) {
/*  802 */           throw new RuntimeException("Item without id! Empty line?");
/*      */         }
/*      */         
/*  805 */         String tierStr = fieldMap.get(ItemDescriptionFields.TIER);
/*  806 */         int tier = 0;
/*  807 */         if (!tierStr.isEmpty())
/*  808 */           tier = Integer.parseInt(tierStr); 
/*  809 */         String ref = fieldMap.get(ItemDescriptionFields.REFERENCE_AS);
/*  810 */         String name = fieldMap.get(ItemDescriptionFields.NAME);
/*  811 */         String icon = fieldMap.get(ItemDescriptionFields.ICON);
/*  812 */         String rarity = fieldMap.get(ItemDescriptionFields.RARITY);
/*  813 */         String setid = fieldMap.get(ItemDescriptionFields.SETID);
/*  814 */         String dFXScript = fieldMap.get(ItemDescriptionFields.DFX_SCRIPT);
/*  815 */         String impactDFX = fieldMap.get(ItemDescriptionFields.IMPACT_DFX);
/*  816 */         String itemText = fieldMap.get(ItemDescriptionFields.ITEM_TEXT);
/*  817 */         String sType = fieldMap.get(ItemDescriptionFields.ITEM_TYPE);
/*  818 */         boolean subscriberOnly = Boolean.parseBoolean(fieldMap.get(ItemDescriptionFields.SUBSCIBER_ONLY));
/*  819 */         String visualId = fieldMap.get(ItemDescriptionFields.VISUAL_ID);
/*  820 */         String sTypeText = fieldMap.get(ItemDescriptionFields.ITEM_TYPE_TEXT);
/*  821 */         String sSkillType = fieldMap.get(ItemDescriptionFields.SKILL_TYPE);
/*  822 */         String consumableString = fieldMap.get(ItemDescriptionFields.CONSUMABLE);
/*  823 */         ItemType itemType = ItemType.getItemType(sType);
/*  824 */         if (itemType == null) {
/*  825 */           throw new RuntimeException("Could not parse item type: \"" + sType + "\"");
/*      */         }
/*      */         
/*      */         try {
/*  829 */           itemLevel = Integer.valueOf(fieldMap.get(ItemDescriptionFields.ITEM_LEVEL));
/*  830 */         } catch (NumberFormatException e) {
/*  831 */           throw new RuntimeException("Cannot parse item level: levelText =\"" + fieldMap.get(ItemDescriptionFields.ITEM_LEVEL) + "\"\n" + "ItemID=\"" + itemId + "\"");
/*      */         } 
/*      */ 
/*      */ 
/*      */         
/*  836 */         if (!this.itemManager.hasDescription(itemId, Integer.valueOf(tier), false)) {
/*  837 */           itemDescription = createItemDescription(itemId, Integer.valueOf(tier));
/*  838 */           this.itemManager.putOnce(itemId, ref, itemDescription);
/*      */         } else {
/*  840 */           itemDescription = this.itemManager.getDescription(itemId, Integer.valueOf(tier), false);
/*      */         } 
/*  842 */         String itemValue = fieldMap.get(ItemDescriptionFields.ITEM_VALUE);
/*  843 */         itemDescription.setItemValue(itemValue);
/*      */         
/*  845 */         itemDescription.setConsumable(Boolean.parseBoolean(consumableString));
/*      */         
/*  847 */         if (itemType != null) {
/*  848 */           itemDescription.setItemType(itemType);
/*      */         } else {
/*  850 */           throw new RuntimeException("No item type set for item: " + itemId + " tier: " + tier);
/*      */         } 
/*  852 */         itemDescription.setName(name);
/*  853 */         itemDescription.setItemText(itemText);
/*  854 */         itemDescription.setItemTypeText(sTypeText);
/*  855 */         itemDescription.setSubscriberOnly(subscriberOnly);
/*  856 */         itemDescription.setVisualId(visualId);
/*  857 */         if (setid != null) {
/*  858 */           itemDescription.setSetId(setid);
/*      */         }
/*  860 */         itemDescription.setLevel(itemLevel.intValue());
/*  861 */         if (!icon.trim().isEmpty())
/*  862 */           itemDescription.setIcon(icon); 
/*  863 */         itemDescription.setRarity(rarity);
/*  864 */         itemDescription.setSkillType(sSkillType);
/*      */         
/*  866 */         setItemDfxDescriptions(dFXScript, impactDFX, itemDescription);
/*      */ 
/*      */         
/*      */         try {
/*  870 */           itemValueAmount = Integer.valueOf(fieldMap.get(ItemDescriptionFields.ITEM_VALUE_AMOUNT));
/*  871 */         } catch (NumberFormatException e) {
/*  872 */           throw new RuntimeException("Cannot parse item level: itemValueAmount=" + fieldMap.get(ItemDescriptionFields.ITEM_LEVEL));
/*      */         } 
/*      */         
/*  875 */         itemDescription.setItemValueAmount(itemValueAmount.intValue());
/*      */         
/*  877 */         loadAbility(fieldMap, rawDataMap, itemDescription);
/*      */         
/*  879 */         fireLoadPerformed(itemId);
/*      */       } 
/*  881 */     } catch (Exception e) {
/*  882 */       throw new RuntimeException("Error parsing item id: " + itemId + "\nerror after field=" + ((fieldMap != null) ? fieldMap.getLastGottenKeyString() : "(no field map)\n"), e);
/*      */     } 
/*      */     
/*  885 */     validateItems(this.itemManager);
/*      */   }
/*      */   
/*      */   protected void setItemDfxDescriptions(String dFXScript, String impactDFX, ItemDescription itemDescription) throws NoSuchDFXException {
/*      */     DireEffectDescription direEffectDescription1, direEffectDescription2;
/*      */     try {
/*  891 */       direEffectDescription1 = this.dfxDescriptionFactory.getDireEffectDescription(dFXScript, false);
/*  892 */     } catch (NoSuchDFXException e) {
/*  893 */       this.configErrors.addError("Missing DFX", "itemId=" + itemDescription.getId() + " dfxScript=" + e.getDFXScript());
/*      */       
/*  895 */       direEffectDescription1 = this.dfxDescriptionFactory.getDireEffectDescription("", false);
/*      */     } 
/*  897 */     itemDescription.setDfxDescription(direEffectDescription1);
/*      */ 
/*      */     
/*      */     try {
/*  901 */       if (impactDFX.isEmpty()) {
/*  902 */         direEffectDescription2 = DireEffectDescriptionFactory.EMPTY_DFX;
/*      */       } else {
/*  904 */         direEffectDescription2 = this.dfxDescriptionFactory.getDireEffectDescription(impactDFX, true);
/*      */       } 
/*  906 */     } catch (NoSuchDFXException e) {
/*  907 */       this.configErrors.addError("Missing Impact DFX", "itemId=" + itemDescription.getId() + " dfxScript=" + e.getDFXScript());
/*      */       
/*  909 */       direEffectDescription2 = DireEffectDescriptionFactory.EMPTY_DFX;
/*      */     } 
/*  911 */     itemDescription.setImpactDfxDescription(direEffectDescription2);
/*      */   }
/*      */   
/*      */   protected void validateItems(ItemManager itemManager) {
/*  915 */     List<ItemDescription> itemDescriptions = itemManager.getAll();
/*      */     
/*  917 */     for (ItemDescription itemDescription : itemDescriptions) {
/*  918 */       DFXValidity dfxValidity = itemDescription.validateFireEffect();
/*      */       
/*  920 */       if (!dfxValidity.isValid()) {
/*  921 */         this.configErrors.addError("DFX Problems", dfxValidity.getMessage());
/*      */       }
/*      */     } 
/*      */     
/*  925 */     for (ItemDescription itemDescription : itemDescriptions) {
/*  926 */       switch (itemDescription.getItemType().getCategory()) {
/*      */         case SPECIAL:
/*      */         case UNKNOWN:
/*  931 */           if (itemDescription.getAbilities().isEmpty()) {
/*  932 */             this.configErrors.addError("Empty Item", "No abilities assigned: itemId='" + itemDescription.getId() + "' tier=" + itemDescription.getTier());
/*      */           }
/*      */           break;
                   default:
                     break;       
                } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadLootDescriptions(Map<String, RawData> rawDataMap) {
/*  941 */     loadLootCard(this.lootManager);
/*      */   }
/*      */   
/*      */   private void loadGroupLootDescriptions(Map<String, RawData> rawDataMap) {
/*  945 */     loadGroupLootCard(this.lootManager);
/*      */   }
/*      */   
/*      */   private void loadLootMobs(Map<String, RawData> rawDataMap) {
/*  949 */     loadLootMob(this.lootManager);
/*      */   }
/*      */   
/*      */   private void loadMonsterLoots(Map<String, RawData> rawDataMap) {
/*  953 */     loadMonsterLoot(this.monsterManager);
/*      */   }
/*      */   
/*      */   private void loadVendors(Map<String, RawData> rawDataMap) {
/*  957 */     RawFields rawFields = null;
/*      */     try {
/*  959 */       for (String[] fields : this.dataRecords.getVendors()) {
/*  960 */         rawFields = new RawFields(fields);
/*  961 */         this.vendorManager.addVendorDescription(fields);
/*      */       } 
/*  963 */     } catch (Exception e) {
/*  964 */       throw new RuntimeException("Error parsing vendor:\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadVendorItems(Map<String, RawData> rawDataMap) {
/*  970 */     RawFields rawFields = null;
/*      */     try {
/*  972 */       for (String[] fields : this.dataRecords.getVendorItems()) {
/*  973 */         rawFields = new RawFields(fields);
/*  974 */         this.vendorManager.addVendorItemDescription(fields);
/*      */       }
/*      */     
/*  977 */     } catch (Exception e) {
/*  978 */       throw new RuntimeException("Error parsing vendor item:\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadStartUpEquipment() {
/*  984 */     RawFields rawFields = null;
/*      */     try {
/*  986 */       for (String[] fields : this.dataRecords.getStartUpEquipment()) {
/*  987 */         rawFields = new RawFields(fields);
/*  988 */         this.startUpManager.addToDescription(fields);
/*      */       } 
/*  990 */     } catch (Exception e) {
/*  991 */       throw new RuntimeException("Error parsing startup equipment:\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadQuests() {
/*  997 */     RawFields rawFields = null;
/*      */     try {
/*  999 */       for (String[] fields : this.dataRecords.getQuests()) {
/* 1000 */         rawFields = new RawFields(fields);
/* 1001 */         QuestDescription questDescription = this.questManager.addQuestDescription(fields, this.questFactory);
/* 1002 */         fireLoadPerformed(questDescription.getId());
/*      */       } 
/* 1004 */     } catch (Exception e) {
/* 1005 */       throw new RuntimeException("Error parsing quest:\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadLootCard(LootManager lootManager) {
/* 1011 */     RawFields rawFields = null;
/* 1012 */     String itemName = "Unknown";
/*      */     try {
/* 1014 */       for (String[] fields : this.dataRecords.getLootDescriptionFiles()) {
/* 1015 */         rawFields = new RawFields(fields);
/*      */         
/* 1017 */         itemName = fields[0];
/* 1018 */         if (itemName.isEmpty()) {
/* 1019 */           throw new RuntimeException("LootCard without name! Empty line?");
/*      */         }
/* 1021 */         int tier = Integer.valueOf(fields[1]).intValue();
/* 1022 */         int amount = Integer.valueOf(fields[2]).intValue();
/* 1023 */         int fromLevel = Integer.valueOf(fields[3]).intValue();
/* 1024 */         int toLevel = Integer.valueOf(fields[4]).intValue();
/* 1025 */         int numberOfPicks = Integer.valueOf(fields[5]).intValue();
/* 1026 */         String type = fields[6];
/* 1027 */         LootType lootType = LootType.valueOf(type.toUpperCase());
/*      */         
/* 1029 */         LevelLootDescription lootDescription = new LevelLootDescription(itemName, tier, amount, fromLevel, toLevel, numberOfPicks, lootType);
/* 1030 */         if (lootDescription.isPickUp() && !itemName.equals("no-loot-drop") && !this.pickUpManager.hasDescription(itemName)) {
/* 1031 */           throw new RuntimeException("No pickup description for id: " + itemName + " caused by level loot.");
/*      */         }
/*      */         
/* 1034 */         lootManager.addLootCard(lootDescription);
/*      */       } 
/* 1036 */     } catch (Exception e) {
/* 1037 */       throw new RuntimeException("Error parsing loot card itemName:" + itemName + "\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadGroupLootCard(LootManager lootManager) {
/* 1043 */     RawFields rawFields = null;
/*      */     
/* 1045 */     String groupId = "unknown";
/*      */     try {
/* 1047 */       for (String[] fields : this.dataRecords.getGroupLootDescriptionFiles()) {
/* 1048 */         rawFields = new RawFields(fields);
/*      */         
/* 1050 */         groupId = fields[0];
/* 1051 */         if (groupId.isEmpty()) {
/* 1052 */           throw new RuntimeException("Group Loot without id! Empty line?");
/*      */         }
/*      */         
/* 1055 */         String itemName = fields[1];
/* 1056 */         int tier = Integer.valueOf(fields[2]).intValue();
/* 1057 */         int amount = Integer.valueOf(fields[3]).intValue();
/* 1058 */         int numberOfPicks = Integer.valueOf(fields[4]).intValue();
/* 1059 */         String type = fields[5];
/* 1060 */         LootType lootType = LootType.valueOf(type.toUpperCase());
/*      */         
/* 1062 */         LootDescription lootDescription = new LootDescription(itemName, tier, amount, numberOfPicks, lootType);
/* 1063 */         if (lootDescription.isPickUp() && !itemName.equals("no-loot-drop") && !this.pickUpManager.hasDescription(itemName)) {
/* 1064 */           throw new RuntimeException("No pickup description for id: " + itemName + " caused by group loot: " + groupId);
/*      */         }
/*      */         
/* 1067 */         lootManager.addGroupLootCard(groupId, lootDescription);
/*      */       } 
/* 1069 */     } catch (Exception e) {
/* 1070 */       throw new RuntimeException("Error parsing group loot card groupId: " + groupId + "\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadQuestObjectives() {
/* 1077 */     RawFields rawFields = null;
/*      */     try {
/* 1079 */       for (String[] fields : this.dataRecords.getQuestObjectives()) {
/* 1080 */         rawFields = new RawFields(fields);
/* 1081 */         QuestObjective obj = createObjective(fields);
/* 1082 */         if (obj != null)
/* 1083 */           fireLoadPerformed(obj.getObjectiveId()); 
/*      */       } 
/* 1085 */     } catch (Exception e) {
/* 1086 */       throw new RuntimeException("Error parsing QuestObjective:\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private QuestObjective createObjective(String[] fields) {
/* 1092 */     FieldMap fieldMap = createQuestObjectivesFieldMap(fields);
/* 1093 */     String objectiveId = fieldMap.get(QuestObjectiveFields.OBJECTIVE_ID);
/* 1094 */     String questId = fieldMap.get(QuestObjectiveFields.QUEST_ID);
/* 1095 */     String mapId = fieldMap.get(QuestObjectiveFields.MAP_ID);
/* 1096 */     String killId = fieldMap.get(QuestObjectiveFields.KILL_ID);
/* 1097 */     String killAmount = fieldMap.get(QuestObjectiveFields.KILL_AMOUNT);
/* 1098 */     String collectId = fieldMap.get(QuestObjectiveFields.COLLECT_ID);
/* 1099 */     String collectAmount = fieldMap.get(QuestObjectiveFields.COLLECT_AMOUNT);
/* 1100 */     String isGotoMissionObjective = fieldMap.get(QuestObjectiveFields.IS_GOTO_MISSION_OBJECTIVE);
/* 1101 */     String regionID = fieldMap.get(QuestObjectiveFields.REGION_ID);
/* 1102 */     String goToId = fieldMap.get(QuestObjectiveFields.GOTO_ID);
/* 1103 */     String goToRangeHeight = fieldMap.get(QuestObjectiveFields.GOTO_RANGE_HEIGHT);
/* 1104 */     String goToRangeWidth = fieldMap.get(QuestObjectiveFields.GOTO_RANGE_WIDTH);
/* 1105 */     String handInNpc = fieldMap.get(QuestObjectiveFields.HANDIN_NPC);
/* 1106 */     String associatedQuest = fieldMap.get(QuestObjectiveFields.ASSOCIATED_QUEST);
/* 1107 */     String finishMission = fieldMap.get(QuestObjectiveFields.FINISH_MISSION);
/* 1108 */     String tutorial = fieldMap.get(QuestObjectiveFields.TUTORIAL);
/* 1109 */     String useItem = fieldMap.get(QuestObjectiveFields.USE_ITEM);
/* 1110 */     String collectPet = fieldMap.get(QuestObjectiveFields.COLLECT_PET);
/* 1111 */     String petLevel = fieldMap.get(QuestObjectiveFields.PET_LEVEL);
/* 1112 */     String petLevelAmount = fieldMap.get(QuestObjectiveFields.PET_LEVEL_AMOUNT);
/* 1113 */     String killMonsterType = fieldMap.get(QuestObjectiveFields.KILL_MONSTER_TYPE);
/* 1114 */     String killMonsterTypeAmount = fieldMap.get(QuestObjectiveFields.KILL_MONSTER_TYPE_AMOUNT);
/* 1115 */     String useItemAmount = fieldMap.get(QuestObjectiveFields.USE_ITEM_AMOUNT);
/*      */     
/* 1117 */     String special = fieldMap.get(QuestObjectiveFields.SPECIAL);
/* 1118 */     String specialAmount = fieldMap.get(QuestObjectiveFields.SPECIAL_AMOUNT);
/* 1119 */     String startObjectiveText = fieldMap.get(QuestObjectiveFields.START_OBJECTIVE_TEXT);
/* 1120 */     String uncompletedObjectiveText = fieldMap.get(QuestObjectiveFields.UNCOMPLETED_OBJECTIVE_TEXT);
/* 1121 */     String completedObjectiveText = fieldMap.get(QuestObjectiveFields.COMPLETED_OBJECTIVE_TEXT);
/* 1122 */     String objectiveIconPath = fieldMap.get(QuestObjectiveFields.OBJECTIVE_ICON_PATH);
/* 1123 */     String shortObjectiveText = fieldMap.get(QuestObjectiveFields.SHORT_OBJECTIVE_TEXT);
/*      */     
/* 1125 */     QuestDescription questDescription = getQuestManager().getQuestDescription(questId);
/*      */     
/* 1127 */     if (questDescription != null) {
/* 1128 */       QuestObjective questObjective = questDescription.getQuestObjectiveById(objectiveId);
/* 1129 */       if (questObjective != null) {
/* 1130 */         if (questObjective instanceof KillObjective && killId.length() > 0) {
/* 1131 */           ((KillObjective)questObjective).addKillId(killId);
/* 1132 */         } else if (questObjective instanceof UseItemObjective && useItem.length() > 0) {
/* 1133 */           ((UseItemObjective)questObjective).addItemId(useItem);
/* 1134 */         } else if (questObjective instanceof CollectObjective && collectId.length() > 0) {
/* 1135 */           ((CollectObjective)questObjective).addCollectId(collectId);
/*      */         } 
/*      */       } else {
/* 1138 */         SubscriptionObjective subscriptionObjective = null; if (!killId.isEmpty()) {
/* 1139 */           KillObjective killObjective = new KillObjective(objectiveId, questId, killId, Integer.parseInt(killAmount));
/* 1140 */         } else if (!collectId.isEmpty()) {
/* 1141 */           CollectObjective collectObjective = new CollectObjective(objectiveId, questId, collectId, Integer.parseInt(collectAmount));
/* 1142 */         } else if (isGotoMissionObjective.equalsIgnoreCase("true")) {
/* 1143 */           GoToObjective goToObjective = new GoToObjective(objectiveId, goToId, Integer.parseInt(goToRangeHeight), Integer.parseInt(goToRangeWidth));
/* 1144 */         } else if (!finishMission.isEmpty()) {
/* 1145 */           if (finishMission.equals(questId)) {
/* 1146 */             throw new RuntimeException("Quest objective to complete quest itelf. Can not possibly work questid: " + questId);
/*      */           }
/* 1148 */           if (finishMission.equals(objectiveId)) {
/* 1149 */             throw new RuntimeException("Quest objective id identical finish mission ID. Questid: " + objectiveId);
/*      */           }
/* 1151 */           FinishMissionObjective finishMissionObjective = new FinishMissionObjective(objectiveId, finishMission);
/* 1152 */         } else if (!handInNpc.isEmpty()) {
/* 1153 */           MonsterDescription monsterDesc = this.monsterManager.getDescription(handInNpc);
/* 1154 */           if (monsterDesc == null) {
/* 1155 */             throw new RuntimeException("Quest objective handin creature does not exist. Questid: " + objectiveId + " MonsterId: " + handInNpc);
/*      */           }
/* 1157 */           if (getQuestManager().getQuestDescription(associatedQuest) == null) {
/* 1158 */             throw new RuntimeException("Quest objective associated quest does not exist. Questid: " + objectiveId + " associated Questid: " + associatedQuest);
/*      */           }
/* 1160 */           monsterDesc.setAssociatedWithQuests(true);
/* 1161 */           monsterDesc.addAssociatedHandInQuest(questId);
/* 1162 */           HandInQuestObjective handInQuestObjective = new HandInQuestObjective(objectiveId, handInNpc, associatedQuest);
/* 1163 */         } else if (!tutorial.isEmpty()) {
/* 1164 */           SpecialTutorialObjective specialTutorialObjective = new SpecialTutorialObjective(objectiveId, tutorial);
/* 1165 */         } else if (!useItem.isEmpty()) {
/* 1166 */           UseItemObjective useItemObjective = new UseItemObjective(objectiveId, useItem, Integer.parseInt(useItemAmount));
/* 1167 */         } else if (!collectPet.isEmpty()) {
/* 1168 */           CollectPetObjective collectPetObjective = new CollectPetObjective(objectiveId, collectPet);
/* 1169 */         } else if (!petLevel.isEmpty()) {
/* 1170 */           PetLevelObjective petLevelObjective = new PetLevelObjective(objectiveId, petLevel, Integer.parseInt(petLevelAmount));
/* 1171 */         } else if (killMonsterType.isEmpty()) {
/* 1172 */           if (!special.isEmpty()) {
/* 1173 */             SpecialAmountObjective specialAmountObjective; RegistrationObjective registrationObjective; ObjectiveSpecialType specialType = ObjectiveSpecialType.valueOf(special.toUpperCase());
/* 1174 */             switch (specialType) {
/*      */               case TOTAL_QUESTS:
/*      */               case TOTAL_DAMAGE_TAKEN:
/*      */               case TOTAL_DAMAGE_DEALT:
/*      */               case TOTAL_DEATHS:
/*      */               case TOTAL_EQUIPMENT:
/*      */               case TOTAL_KILLS:
/*      */               case TOTAL_DUEL_WINS:
/*      */               case LEVEL_UP:
/*      */               case SPECIAL_USED:
/*      */               case TOTAL_PETS:
/* 1185 */                 specialAmountObjective = new SpecialAmountObjective(objectiveId, Integer.parseInt(specialAmount), ObjectiveType.valueOf(specialType.name()));
/*      */                 break;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */               
/*      */               case SAVE_CHAR:
/* 1209 */                 registrationObjective = new RegistrationObjective(objectiveId);
/*      */                 break;
/*      */               case BECOME_MEMBER:
/* 1212 */                 subscriptionObjective = new SubscriptionObjective(objectiveId);
/*      */                 break;
/*      */               default:
/* 1215 */                 throw new RuntimeException("Missing objective data objetiveId: " + objectiveId + " invalid special type");
/*      */             } 
/*      */           } else {
/* 1218 */             throw new RuntimeException("Missing objective data objetiveId: " + objectiveId);
/*      */           } 
/*      */         } 
/* 1221 */         if (goToId.length() > 0 && subscriptionObjective.getGoToId() == null) {
/* 1222 */           subscriptionObjective.setObjectiveLocationDefinition(goToId, Integer.parseInt(goToRangeHeight), Integer.parseInt(goToRangeWidth));
/*      */         }
/* 1224 */         subscriptionObjective.addUnCompletedObjectiveText(uncompletedObjectiveText);
/* 1225 */         subscriptionObjective.addStartObjectiveText(startObjectiveText);
/* 1226 */         subscriptionObjective.addCompletedObjectiveText(completedObjectiveText);
/* 1227 */         subscriptionObjective.addIconPath(objectiveIconPath);
/* 1228 */         subscriptionObjective.setShortObjetiveText(shortObjectiveText);
/* 1229 */         subscriptionObjective.setRegionID(regionID);
/* 1230 */         subscriptionObjective.setMapId(mapId);
/* 1231 */         questDescription.addQuestObjective((QuestObjective)subscriptionObjective);
/* 1232 */         getQuestManager().addQuestObjective(questDescription.getId(), (QuestObjective)subscriptionObjective);
/*      */         
/* 1234 */         return (QuestObjective)subscriptionObjective;
/*      */       } 
/*      */     } 
/* 1237 */     return null;
/*      */   }
/*      */   
/*      */   private void loadMonsterLoot(MonsterManager monsterManager) {
/* 1241 */     RawFields rawFields = null;
/*      */     
/*      */     try {
/* 1244 */       for (String[] fields : this.dataRecords.getMonsterLootGroups()) {
/* 1245 */         rawFields = new RawFields(fields);
/*      */         
/* 1247 */         String groupId = fields[0];
/* 1248 */         String monsterId = fields[1];
/* 1249 */         int minDrops = Integer.valueOf(fields[2]).intValue();
/* 1250 */         int maxDrops = Integer.valueOf(fields[3]).intValue();
/* 1251 */         int range = Math.max(0, maxDrops - minDrops);
/* 1252 */         String relatedQuestId = fields[4];
/* 1253 */         MonsterDescription monster = monsterManager.getDescription(monsterId);
/* 1254 */         if (monster != null) {
/* 1255 */           MonsterGroupLoot lootGroup = new MonsterGroupLoot(groupId, minDrops, range, relatedQuestId);
/* 1256 */           monster.addLootGroup(lootGroup);
/*      */         } 
/*      */       } 
/* 1259 */     } catch (Exception e) {
/* 1260 */       throw new RuntimeException("Error parsing loot:\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadLootMob(LootManager lootManager) {
/* 1266 */     RawFields rawFields = null;
/*      */     try {
/* 1268 */       for (String[] fields : this.dataRecords.getLootMobTypes()) {
/* 1269 */         rawFields = new RawFields(fields);
/* 1270 */         lootManager.addMobType(fields[0].toUpperCase(), Double.valueOf(fields[1]).doubleValue());
/* 1271 */         lootManager.addMobTypeLootModel(fields[0].toUpperCase(), fields[2]);
/*      */       } 
/* 1273 */     } catch (Exception e) {
/* 1274 */       throw new RuntimeException("Error parsing loot:\nerror after field=" + rawFields, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void loadPickUpItemsFile() {
/* 1280 */     FieldMap fieldMap = null;
/* 1281 */     String id = "Unknown!";
/*      */     try {
/* 1283 */       for (String[] fields : this.dataRecords.getPickUpItemsFiles()) {
/* 1284 */         Float triggerDistance; fieldMap = createPickUpItemFieldMap(fields);
/* 1285 */         id = fieldMap.get(PickUpItemDescriptionFields.ID);
/* 1286 */         if (id.isEmpty()) {
/* 1287 */           throw new RuntimeException("Pickup item without id! Empty line?");
/*      */         }
/*      */         
/* 1290 */         if (this.pickUpManager.hasDescription(id)) {
/*      */           
/* 1292 */           this.configErrors.addError("Duplicate Pickup", "id=" + id);
/*      */           
/*      */           continue;
/*      */         } 
/* 1296 */         String associatedItemTypeStr = fieldMap.get(PickUpItemDescriptionFields.ASSOCIATED_TYPE);
/* 1297 */         PickupType associatedItemType = this.pickUpManager.getType(associatedItemTypeStr.toLowerCase());
/* 1298 */         String associatedItemId = fieldMap.get(PickUpItemDescriptionFields.ASSOCIATED_ITEM);
/* 1299 */         String associatedItemTierStr = fieldMap.get(PickUpItemDescriptionFields.ASSOCIATED_ITEM_TIER);
/* 1300 */         int associatedItemTier = LoaderUtils.parseLevel(associatedItemTierStr);
/*      */         
/* 1302 */         boolean use = Boolean.parseBoolean(fieldMap.get(PickUpItemDescriptionFields.USE));
/*      */         
/* 1304 */         String ammountStr = fieldMap.get(PickUpItemDescriptionFields.AMMOUNT);
/* 1305 */         int ammount = 0;
/* 1306 */         if (!ammountStr.isEmpty()) {
/* 1307 */           ammount = Integer.parseInt(ammountStr);
/*      */         }
/*      */ 
/*      */         
/*      */         try {
/* 1312 */           triggerDistance = Float.valueOf(fieldMap.get(PickUpItemDescriptionFields.TRIGGER_DISTANCE));
/* 1313 */         } catch (NumberFormatException e) {
/* 1314 */           throw new RuntimeException("Cannot parse trigger distance: triggerDistance=" + fieldMap.get(PickUpItemDescriptionFields.TRIGGER_DISTANCE) + " for item: " + fieldMap.get(PickUpItemDescriptionFields.ID));
/*      */         } 
/* 1316 */         String mesh = fieldMap.get(PickUpItemDescriptionFields.MESH);
/* 1317 */         String spawnDFX = fieldMap.get(PickUpItemDescriptionFields.SPAWN_DFX);
/* 1318 */         String useDFX = fieldMap.get(PickUpItemDescriptionFields.PICKUP_DFX);
/* 1319 */         String idleDFX = fieldMap.get(PickUpItemDescriptionFields.IDLE_DFX);
/*      */         
/* 1321 */         Boolean splittable = Boolean.valueOf(fieldMap.get(PickUpItemDescriptionFields.SPLITTABLE));
/* 1322 */         String splitGroupId = fieldMap.get(PickUpItemDescriptionFields.SPLIT_GROUP_ID);
/*      */         
/* 1324 */         AbstractPickUpDescription description = associatedItemType.createDescription();
/*      */         
/* 1326 */         description.setId(id);
/* 1327 */         description.setUse(use);
/* 1328 */         description.setAmount(ammount);
/* 1329 */         description.setTriggerDistance(triggerDistance.floatValue());
/* 1330 */         description.setMesh(mesh);
/* 1331 */         description.setSpawnDFX(spawnDFX);
/* 1332 */         description.setPickUpDFX(useDFX);
/* 1333 */         description.setIdleDFX(idleDFX);
/* 1334 */         description.setSplittable(splittable);
/* 1335 */         description.setSplitGroupID(splitGroupId);
/* 1336 */         description.setAssociatedType(associatedItemType);
/*      */         
/* 1338 */         description.setAssociatedData(this, associatedItemId, associatedItemTier);
/*      */         
/* 1340 */         this.pickUpManager.addDescription(description);
/*      */       } 
/* 1342 */     } catch (Exception e) {
/* 1343 */       throw new RuntimeException("Error parsing item: id: \"" + id + "\"\nerror after field=" + ((fieldMap != null) ? fieldMap.getLastGottenKeyString() : "(no field map)"), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected ItemDescription createItemDescription(String itemId, Integer tier) {
/* 1349 */     return new ItemDescription(itemId, tier, this.itemFactory);
/*      */   }
/*      */   
/*      */   protected FieldMap createPickUpItemFieldMap(String[] fields) {
/* 1353 */     return new FieldMap((Object[])PickUpItemDescriptionFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected FieldMap createItemFieldMap(String[] fields) {
/* 1357 */     return new FieldMap((Object[])ItemDescriptionFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected FieldMap createEquipmentFieldMap(String[] fields) {
/* 1361 */     return new FieldMap((Object[])EquipmentBuilderFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected FieldMap createQuestObjectivesFieldMap(String[] fields) {
/* 1365 */     return new FieldMap((Object[])QuestObjectiveFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected FieldMap createArchtypeFieldMap(String[] fields) {
/* 1369 */     return new FieldMap((Object[])ArchtypeFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected FieldMap createPetArchtypeFieldMap(String[] fields) {
/* 1373 */     return new FieldMap((Object[])PetArchtypeFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected FieldMap createPetFieldMap(String[] fields) {
/* 1377 */     return new FieldMap((Object[])PetDescriptionFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected FieldMap createSpeachFieldMap(String[] fields) {
/* 1381 */     return new FieldMap((Object[])SpeachDescriptionFields.values(), fields);
/*      */   }
/*      */   
/*      */   protected FieldMap createSpeachMappingFieldMap(String[] fields) {
/* 1385 */     return new FieldMap((Object[])SpeachMappingFields.values(), fields);
/*      */   }
/*      */   
/*      */   private void loadSpeach() {
/* 1389 */     FieldMap fieldMap = null;
/* 1390 */     for (String[] fields : this.dataRecords.getSpeachFiles()) {
/* 1391 */       fieldMap = createSpeachFieldMap(fields);
/* 1392 */       String speachId = fieldMap.get(SpeachDescriptionFields.SPEACH_ID);
/* 1393 */       SpeachContext context = SpeachContext.valueOf(fieldMap.get(SpeachDescriptionFields.CONTEXT).toUpperCase());
/* 1394 */       String text = fieldMap.get(SpeachDescriptionFields.LOCALIZATION_STRING);
/* 1395 */       this.speachManager.addSpeachDescription(speachId, context, text);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadSpeachMapping() {
/* 1400 */     FieldMap fieldMap = null;
/* 1401 */     for (String[] fields : this.dataRecords.getSpeachMappingFiles()) {
/* 1402 */       fieldMap = createSpeachMappingFieldMap(fields);
/* 1403 */       String npcId = fieldMap.get(SpeachMappingFields.NPC_ID);
/* 1404 */       String speachId = fieldMap.get(SpeachMappingFields.SPEACH_ID);
/* 1405 */       boolean barks = Boolean.valueOf(fieldMap.get(SpeachMappingFields.BARKS)).booleanValue();
/* 1406 */       int barkMin = fieldMap.getInt(SpeachMappingFields.BARK_MIN, 0);
/* 1407 */       int barkMax = fieldMap.getInt(SpeachMappingFields.BARK_MAX, 0);
/* 1408 */       this.speachManager.addSpeachMapping(npcId, speachId, barks, barkMin, barkMax);
/*      */     } 
/*      */   }
/*      */   
/*      */   private void loadPetFile() {
/* 1413 */     FieldMap fieldMap = null;
/* 1414 */     String classId = "unknown";
/*      */     try {
/* 1416 */       for (String[] fields : this.dataRecords.getPetFiles()) {
/* 1417 */         PetDescription petDescription; fieldMap = createPetFieldMap(fields);
/* 1418 */         classId = fieldMap.get(PetDescriptionFields.CLASSID);
/* 1419 */         if (classId.isEmpty()) {
/* 1420 */           throw new RuntimeException("Pet without id! Empty line?");
/*      */         }
/* 1422 */         int petLevel = Integer.valueOf(fieldMap.get(PetDescriptionFields.PETLEVEL)).intValue();
/* 1423 */         String archTypeId = fieldMap.get(PetDescriptionFields.ARCHTYPE);
/* 1424 */         boolean subscriber = Boolean.valueOf(fieldMap.get(PetDescriptionFields.SUBSCRIBER)).booleanValue();
/* 1425 */         String petItemClassId = fieldMap.get(PetDescriptionFields.ITEMCLASSID);
/* 1426 */         String skill = fieldMap.get(PetDescriptionFields.SKILL);
/* 1427 */         boolean defaultSkill = Boolean.valueOf(fieldMap.get(PetDescriptionFields.DEFAULT_PET_SKILL)).booleanValue();
/* 1428 */         String slot = fieldMap.get(PetDescriptionFields.SLOT);
/* 1429 */         int slotValue = Integer.valueOf(slot).intValue();
/* 1430 */         String tierStr = fieldMap.get(PetDescriptionFields.TIER);
/* 1431 */         int tier = Integer.valueOf(tierStr).intValue();
/*      */ 
/*      */         
/* 1434 */         if (this.petManager.hasPetDescription(classId)) {
/* 1435 */           petDescription = this.petManager.getPetDescription(classId);
/*      */         } else {
/* 1437 */           PetArchType archtype = this.petArchtypeManager.getArchtypeForId(archTypeId);
/* 1438 */           petDescription = new PetDescription(classId, petItemClassId, petLevel, subscriber, archtype);
/* 1439 */           this.petManager.putPet(petDescription);
/*      */         } 
/* 1441 */         petDescription.addSkill(skill, tier, defaultSkill, slotValue);
/* 1442 */         fireLoadPerformed(classId);
/*      */         
/* 1444 */         String resolvesAs = fieldMap.get(PetDescriptionFields.RESOLVE_AS);
/* 1445 */         this.petManager.addPetReference(classId, resolvesAs);
/*      */       } 
/* 1447 */     } catch (Exception e) {
/* 1448 */       throw new RuntimeException("Error parsing item: " + classId + "\nerror after field=" + ((fieldMap != null) ? fieldMap.getLastGottenKeyString() : "(no field map)"), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setItemManager(ItemManager itemManager) {
/* 1455 */     this.itemManager = itemManager;
/*      */   }
/*      */   
/*      */   public ItemManager getItemManager() {
/* 1459 */     return this.itemManager;
/*      */   }
/*      */   
/*      */   public PickUpManager getPickUpManager() {
/* 1463 */     return this.pickUpManager;
/*      */   }
/*      */   
/*      */   public PetManager getPetManager() {
/* 1467 */     return this.petManager;
/*      */   }
/*      */   
/*      */   public SpeachManager getSpeachManager() {
/* 1471 */     return this.speachManager;
/*      */   }
/*      */   
/*      */   public GeneralValueParser getValueParser() {
/* 1475 */     return this.valueParser;
/*      */   }
/*      */   
/*      */   public StatsManager getStatsManager() {
/* 1479 */     return this.statsManager;
/*      */   }
/*      */   
/*      */   public Map<String, RawData> getRawDataMap() {
/* 1483 */     return this.rawDataMap;
/*      */   }
/*      */   
/*      */   public ShapeManager getShapeManager() {
/* 1487 */     return this.shapeManager;
/*      */   }
/*      */   
/*      */   public ElementManager getElementManager() {
/* 1491 */     return this.elementManager;
/*      */   }
/*      */   
/*      */   public void setEvaluator(ShapeDataEvaluator evaluator) {
/* 1495 */     this.evaluator = evaluator;
/*      */   }
/*      */   
/*      */   public ShapeDataEvaluator getEvaluator() {
/* 1499 */     return this.evaluator;
/*      */   }
/*      */   
/*      */   public ItemFactory getItemFactory() {
/* 1503 */     return this.itemFactory;
/*      */   }
/*      */   
/*      */   public void setItemFactory(ItemFactory itemFactory) {
/* 1507 */     this.itemFactory = itemFactory;
/*      */   }
/*      */   
/*      */   public QuestFactory getQuestFactory() {
/* 1511 */     return this.questFactory;
/*      */   }
/*      */   
/*      */   public void setQuestFactory(QuestFactory questFactory) {
/* 1515 */     this.questFactory = questFactory;
/*      */   }
/*      */   
/*      */   public StatIdTranslator getStatIdTranslator() {
/* 1519 */     return this.statIdTranslator;
/*      */   }
/*      */   
/*      */   public void setStatIdTranslator(StatIdTranslator statIdTranslator) {
/* 1523 */     this.statIdTranslator = statIdTranslator;
/*      */   }
/*      */   
/*      */   public MonsterManager getMonsterManager() {
/* 1527 */     return this.monsterManager;
/*      */   }
/*      */   
/*      */   public LootManager getLootManager() {
/* 1531 */     return this.lootManager;
/*      */   }
/*      */   
/*      */   public RegionManager getRegionManager() {
/* 1535 */     return this.regionManager;
/*      */   }
/*      */   
/*      */   protected void loadAbility(FieldMap fieldMap, Map<String, RawData> rawDataMap, ItemDescription itemDescription) {
/* 1539 */     if (!itemDescription.getId().equals(fieldMap.get(ItemDescriptionFields.ID))) {
/* 1540 */       throw new IllegalArgumentException("item data row not matching the item name");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1547 */     int _tier = Integer.parseInt(fieldMap.get(ItemDescriptionFields.TIER));
/* 1548 */     int _stacksize = 1;
/* 1549 */     String stackSizeStr = fieldMap.get(ItemDescriptionFields.STACKSIZE);
/* 1550 */     if (!stackSizeStr.isEmpty())
/* 1551 */       _stacksize = Integer.parseInt(stackSizeStr); 
/* 1552 */     int _localCooldownMillis = (int)(Double.parseDouble(fieldMap.get(ItemDescriptionFields.LOCAL_COOLDOWN)) * 1000.0D);
/* 1553 */     double _globalCooldown = Double.parseDouble(fieldMap.get(ItemDescriptionFields.GLOBAL_COOLDOWN));
/*      */     
/* 1555 */     if (itemDescription.isInitialized()) {
/*      */       
/* 1557 */       itemDescription.checkInstanceValues(_tier, _localCooldownMillis, _stacksize);
/*      */     } else {
/* 1559 */       itemDescription.init(_tier, _localCooldownMillis, _stacksize, _globalCooldown);
/*      */     } 
/*      */     
/* 1562 */     int requireLevelFrom = Integer.parseInt(fieldMap.get(ItemDescriptionFields.REQIREMENT_LEVEL_FROM));
/* 1563 */     int requireLevelTo = LoaderUtils.parseLevel(fieldMap.get(ItemDescriptionFields.REQUIREMENT_LEVEL_TO));
/*      */     
/* 1565 */     String abilityId = fieldMap.get(ItemDescriptionFields.ABILITY_FILTER);
/* 1566 */     RawData data = rawDataMap.get(abilityId);
/*      */     
/* 1568 */     if (data == null || abilityId == null || abilityId.compareTo("") == 0) {
/* 1569 */       if (!abilityId.isEmpty()) {
/* 1570 */         notifyMissingAbility(itemDescription, abilityId);
/*      */       }
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/* 1576 */     data.assertAbilityType("This ability is not supported by Item", ItemDescription.SUPPORTED_ABILITIES);
/*      */     
/* 1578 */     AbilityFactory abilityFactory = data.getAbilityFactory();
/* 1579 */     AbilityParams params = abilityFactory.createParams(data.getFieldsList());
/* 1580 */     params.setCommon(AbilityParams.ParamName.REQ_LEVEL_FROM, Integer.valueOf(requireLevelFrom));
/* 1581 */     params.setCommon(AbilityParams.ParamName.REQ_LEVEL_TO, Integer.valueOf(requireLevelTo));
/* 1582 */     String x = fieldMap.get(ItemDescriptionFields.PARAM_X);
/* 1583 */     String y = fieldMap.get(ItemDescriptionFields.PARAM_Y);
/* 1584 */     String z = fieldMap.get(ItemDescriptionFields.PARAM_Z);
/* 1585 */     String k = fieldMap.get(ItemDescriptionFields.PARAM_K);
/* 1586 */     String ka = fieldMap.get(ItemDescriptionFields.PARAM_KA);
/* 1587 */     String kb = fieldMap.get(ItemDescriptionFields.PARAM_KB);
/* 1588 */     String kc = fieldMap.get(ItemDescriptionFields.PARAM_KC);
/* 1589 */     String dfxReference = fieldMap.get(ItemDescriptionFields.DFX_REFERENCE);
/* 1590 */     params.setCommon(AbilityParams.ParamName.X, x);
/* 1591 */     params.setCommon(AbilityParams.ParamName.Y, y);
/* 1592 */     params.setCommon(AbilityParams.ParamName.Z, z);
/* 1593 */     params.setCommon(AbilityParams.ParamName.K, k);
/* 1594 */     params.setCommon(AbilityParams.ParamName.KA, ka);
/* 1595 */     params.setCommon(AbilityParams.ParamName.KB, kb);
/* 1596 */     params.setCommon(AbilityParams.ParamName.KC, kc);
/* 1597 */     params.setCommon(AbilityParams.ParamName.DFX_REFERENCE, dfxReference);
/*      */     
/* 1599 */     AbilityParams realVariables = new AbilityParams();
/* 1600 */     realVariables.setCommon(AbilityParams.ParamName.X, x);
/* 1601 */     realVariables.setCommon(AbilityParams.ParamName.Y, y);
/* 1602 */     realVariables.setCommon(AbilityParams.ParamName.Z, z);
/* 1603 */     realVariables.setCommon(AbilityParams.ParamName.K, k);
/* 1604 */     realVariables.setCommon(AbilityParams.ParamName.KA, ka);
/* 1605 */     realVariables.setCommon(AbilityParams.ParamName.KB, kb);
/* 1606 */     realVariables.setCommon(AbilityParams.ParamName.KC, kc);
/*      */     try {
/* 1608 */       params.resolveVariables(realVariables);
/* 1609 */     } catch (LocalizationException e) {
/* 1610 */       throw new RuntimeException(e);
/*      */     } 
/*      */     
/* 1613 */     Ability ability = abilityFactory.create((AbilityContainer)itemDescription, getEvaluator(), params, this, this.valueAccumulatorFactory);
/*      */     
/* 1615 */     if (ability == null) {
/* 1616 */       throw new NullPointerException("Cannot create ability: factoryClass=" + abilityFactory.getClass());
/*      */     }
/*      */     
/* 1619 */     itemDescription.getAbilities().add(ability);
/*      */   }
/*      */   
/*      */   public void notifyMissingAbility(ItemDescription itemDescription, String missingAbilityId) {
/* 1623 */     this.configErrors.addError("Missing Ability", "Ability not found for: itemId='" + itemDescription.getId() + "' tier=" + itemDescription.getTier() + " missingAbilityId='" + missingAbilityId + "'");
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   protected void loadArchtypes() {
/* 1629 */     FieldMap fieldMap = null;
/* 1630 */     String classId = "unknown";
/*      */     try {
/* 1632 */       for (String[] fields : this.dataRecords.getArchtypeFiles()) {
/* 1633 */         fieldMap = createArchtypeFieldMap(fields);
/* 1634 */         classId = fieldMap.get(ArchtypeFields.ID);
/* 1635 */         double health = Double.parseDouble(fieldMap.get(ArchtypeFields.HEALTH));
/* 1636 */         double mana = Double.parseDouble(fieldMap.get(ArchtypeFields.MANA));
/* 1637 */         double attack = Double.parseDouble(fieldMap.get(ArchtypeFields.ATTACK));
/* 1638 */         double defense = Double.parseDouble(fieldMap.get(ArchtypeFields.DEFENSE));
/* 1639 */         double crit = Double.parseDouble(fieldMap.get(ArchtypeFields.CRIT));
/* 1640 */         ArchType archType = new ArchType(classId, health, mana, attack, defense, crit);
/* 1641 */         this.archtypeManager.addArchType(classId, archType);
/*      */       } 
/* 1643 */     } catch (Exception e) {
/* 1644 */       throw new RuntimeException("Error parsing archtype id: " + classId + "\nerror after field=" + ((fieldMap != null) ? fieldMap.getLastGottenKeyString() : "(no field map)\n"), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void loadPetArchtypes() {
/* 1650 */     FieldMap fieldMap = null;
/* 1651 */     String classId = "unknown";
/*      */     try {
/* 1653 */       for (String[] fields : this.dataRecords.getPetArchtypeFiles()) {
/* 1654 */         fieldMap = createPetArchtypeFieldMap(fields);
/* 1655 */         classId = fieldMap.get(PetArchtypeFields.ID);
/* 1656 */         double health = Double.parseDouble(fieldMap.get(PetArchtypeFields.HEALTH));
/* 1657 */         double mana = Double.parseDouble(fieldMap.get(PetArchtypeFields.MANA));
/* 1658 */         double attack = Double.parseDouble(fieldMap.get(PetArchtypeFields.ATTACK));
/* 1659 */         double defense = Double.parseDouble(fieldMap.get(PetArchtypeFields.DEFENSE));
/* 1660 */         double crit = Double.parseDouble(fieldMap.get(PetArchtypeFields.CRIT));
/* 1661 */         PetArchType archType = new PetArchType(classId, health, mana, attack, defense, crit);
/* 1662 */         this.petArchtypeManager.addArchType(classId, archType);
/*      */       } 
/* 1664 */     } catch (Exception e) {
/* 1665 */       throw new RuntimeException("Error parsing archtype id: " + classId + "\nerror after field=" + ((fieldMap != null) ? fieldMap.getLastGottenKeyString() : "(no field map)\n"), e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected void buildEquipment(Map<String, RawData> rawDataMap) {
/* 1671 */     EquipmentBuilder builder = new EquipmentBuilder();
/* 1672 */     builder.setArchtypeManager(this.archtypeManager);
/* 1673 */     builder.setEvaluator(this.evaluator);
/* 1674 */     builder.setItemFactory(this.itemFactory);
/* 1675 */     builder.setItemManager(this.itemManager);
/* 1676 */     builder.setLoader(this);
/* 1677 */     builder.setValueAccumulatorFactory(this.valueAccumulatorFactory);
/*      */     
/* 1679 */     FieldMap fieldMap = null;
/*      */     
/* 1681 */     String itemId = "Unknown.";
/*      */     try {
/* 1683 */       for (String[] fields : this.dataRecords.getEquipmentFiles()) {
/* 1684 */         fieldMap = createEquipmentFieldMap(fields);
/* 1685 */         itemId = fieldMap.get(EquipmentBuilderFields.ID);
/* 1686 */         builder.createEquipment(rawDataMap, fieldMap);
/*      */       } 
/* 1688 */     } catch (Exception e) {
/* 1689 */       throw new RuntimeException("Error parsing equipment id: " + itemId, e);
/*      */     } 
/*      */   }
/*      */   
/*      */   protected void buildPetEquipment(Map<String, RawData> rawDataMap) {
/* 1694 */     PetEquipmentBuilder builder = new PetEquipmentBuilder();
/* 1695 */     builder.setEvaluator(this.evaluator);
/* 1696 */     builder.setItemFactory(this.itemFactory);
/* 1697 */     builder.setItemManager(this.itemManager);
/* 1698 */     builder.setLoader(this);
/* 1699 */     builder.setValueAccumulatorFactory(this.valueAccumulatorFactory);
/*      */     
/* 1701 */     String itemId = "Unknown.";
/*      */     try {
/* 1703 */       for (PetArchType archtype : this.petArchtypeManager.getArchTypes().values()) {
/* 1704 */         for (int level = 1; level <= 45L; level++) {
/* 1705 */           itemId = archtype.toString() + "-" + level;
/* 1706 */           builder.createEquipment(rawDataMap, archtype, level);
/*      */         }
/*      */       
/*      */       } 
/* 1710 */     } catch (Exception e) {
/* 1711 */       throw new RuntimeException("Error parsing equipment id: " + itemId, e);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setValueAccumulatorFactory(ValueAccumulatorFactory valueAccumulatorFactory) {
/* 1717 */     this.valueAccumulatorFactory = valueAccumulatorFactory;
/*      */   }
/*      */   
/*      */   public VendorManager getVendorManager() {
/* 1721 */     return this.vendorManager;
/*      */   }
/*      */   
/*      */   public QuestManager getQuestManager() {
/* 1725 */     return this.questManager;
/*      */   }
/*      */   
/*      */   public StartUpEquipmentManager getStartUpManager() {
/* 1729 */     return this.startUpManager;
/*      */   }
/*      */   
/*      */   public PortkeyManager getPortkeyManager() {
/* 1733 */     return this.portkeyManager;
/*      */   }
/*      */   
/*      */   public ProjectileFactory getProjectileFactory() {
/* 1737 */     return this.projectileFactory;
/*      */   }
/*      */   
/*      */   public void setProjectileFactory(ProjectileFactory projectileFactory) {
/* 1741 */     this.projectileFactory = projectileFactory;
/*      */   }
/*      */   
/*      */   public ProjectileReflectorFactory getProjectileReflectorFactory() {
/* 1745 */     return this.projectileReflectorFactory;
/*      */   }
/*      */   
/*      */   public void setProjectileReflectorFactory(ProjectileReflectorFactory projectileReflectorFactory) {
/* 1749 */     this.projectileReflectorFactory = projectileReflectorFactory;
/*      */   }
/*      */   
/*      */   public TargetedEffectFactory getTargetedEffectFactory() {
/* 1753 */     return this.targetedEffectFactory;
/*      */   }
/*      */   
/*      */   public void setTargetedEffectFactory(TargetedEffectFactory targetedEffectFactory) {
/* 1757 */     this.targetedEffectFactory = targetedEffectFactory;
/*      */   }
/*      */   
/*      */   public Map<String, String> getProjectilePathMap() {
/* 1761 */     return this.projectilePathMap;
/*      */   }
/*      */   
/*      */   public Short getLevelStatId() {
/* 1765 */     return this.levelStatId;
/*      */   }
/*      */   
/*      */   public void setLevelStatId(Short levelStatId) {
/* 1769 */     this.levelStatId = levelStatId;
/*      */   }
/*      */   
/*      */   public DireEffectDescriptionFactory getDfxDescriptionFactory() {
/* 1773 */     return this.dfxDescriptionFactory;
/*      */   }
/*      */   
/*      */   public void setDfxDescriptionFactory(DireEffectDescriptionFactory dfxDescriptionFactory) {
/* 1777 */     this.dfxDescriptionFactory = dfxDescriptionFactory;
/*      */   }
/*      */   
/*      */   public Class<? extends Enum> getFactionEnumClass() {
/* 1781 */     return this.factionEnumClass;
/*      */   }
/*      */   
/*      */   public void setFactionEnumClass(Class<? extends Enum> factionEnumClass) {
/* 1785 */     this.factionEnumClass = factionEnumClass;
/*      */   }
/*      */   
/*      */   public MovementManipulatorFactory getMovementManipulatorFactory() {
/* 1789 */     return this.movementManipulatorFactory;
/*      */   }
/*      */   
/*      */   public void setMovementManipulatorFactory(MovementManipulatorFactory movementManipulatorFactory) {
/* 1793 */     this.movementManipulatorFactory = movementManipulatorFactory;
/*      */   }
/*      */   
/*      */   public WaypointManager getWaypointManager() {
/* 1797 */     return this.waypointManager;
/*      */   }
/*      */   
/*      */   public CheckpointManager getCheckpointManager() {
/* 1801 */     return this.checkpointManager;
/*      */   }
/*      */   
/*      */   public CustomPortalManager getCustomPortalManager() {
/* 1805 */     return this.customPortalManager;
/*      */   }
/*      */   
/*      */   public ItemSetManager getItemSetManager() {
/* 1809 */     return this.itemSetManager;
/*      */   }
/*      */   
/*      */   public ArchtypeManager getArchtypeManager() {
/* 1813 */     return this.archtypeManager;
/*      */   }
/*      */   
/*      */   public PetArchtypeManager getPetArchtypeManager() {
/* 1817 */     return this.petArchtypeManager;
/*      */   }
/*      */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\RpgLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */