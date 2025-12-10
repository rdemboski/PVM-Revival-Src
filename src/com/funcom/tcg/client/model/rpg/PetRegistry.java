/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.jme.modular.XmlModularDescription;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CSVData;
/*     */ import com.funcom.rpgengine2.equipment.PetArchType;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.errorreporting.ErrorWindowCreator;
/*     */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.jdom.Document;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PetRegistry
/*     */ {
/*     */   private Map<String, ClientPetDescription> pets;
/*     */   private static final String NEW_PET_DESCRIPTION = "description/modular/*.petdescription.csv";
/*     */   private ResourceManager resourceManager;
/*     */   private VisualRegistry visualRegistry;
/*     */   private ItemRegistry itemRegistry;
/*     */   
/*     */   public PetRegistry(ResourceManager resourceManager, VisualRegistry visualRegistry, ItemRegistry itemRegistry) {
/*  31 */     this.pets = new HashMap<String, ClientPetDescription>();
/*  32 */     this.resourceManager = resourceManager;
/*  33 */     this.visualRegistry = visualRegistry;
/*  34 */     this.itemRegistry = itemRegistry;
/*     */   }
/*     */   
/*     */   public void reload() {
/*  38 */     this.pets.clear();
/*  39 */     this.resourceManager.getManagedResource(CSVData.class, "description/modular/*.petdescription.csv", CacheType.NOT_CACHED).setDirty();
/*  40 */     this.resourceManager.getManagedResource(CSVData.class, "rpg/*.pets.csv", CacheType.NOT_CACHED).setDirty();
/*  41 */     this.resourceManager.update();
/*     */     
/*  43 */     readPetDescriptionData();
/*  44 */     readPetSkillsData();
/*     */   }
/*     */   
/*     */   public void readPetDescriptionData() {
/*  48 */     CSVData records = (CSVData)this.resourceManager.getResource(CSVData.class, "description/modular/*.petdescription.csv", CacheType.NOT_CACHED);
/*  49 */     for (String[] record : records) {
/*  50 */       createPet(record);
/*     */     }
/*     */   }
/*     */   
/*     */   public void readPetSkillsData() {
/*  55 */     CSVData records = (CSVData)this.resourceManager.getResource(CSVData.class, "rpg/*.pets.csv", CacheType.NOT_CACHED);
/*  56 */     for (String[] record : records) {
/*  57 */       fetchPetAddSkill(record);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createPet(String[] record) {
/*  62 */     List<String> list = Arrays.asList(record);
/*  63 */     Iterator<String> iterator = list.iterator();
/*  64 */     String classId = iterator.next();
/*     */     
/*  66 */     ClientPetDescription clientPet = this.pets.get(classId);
/*  67 */     if (clientPet == null) {
/*  68 */       clientPet = new ClientPetDescription(classId);
/*  69 */       this.pets.put(clientPet.getClassId(), clientPet);
/*     */     } 
/*     */     
/*  72 */     clientPet.setName(iterator.next());
/*  73 */     clientPet.setDescription(iterator.next());
/*  74 */     clientPet.setFamily(iterator.next());
/*  75 */     clientPet.setElementId(iterator.next());
/*     */     
/*  77 */     String modelId = iterator.next();
/*  78 */     clientPet.setModel(modelId);
/*  79 */     CreatureVisualDescription petVisuals = this.visualRegistry.getCreatureVisualForClassId(modelId);
/*  80 */     clientPet.setPetVisuals(petVisuals);
/*  81 */     Document modelDoc = (Document)this.resourceManager.getResource(Document.class, petVisuals.getXmlDocumentPath(), CacheType.NOT_CACHED);
/*  82 */     clientPet.setPetDescription((ModularDescription)new XmlModularDescription(modelDoc));
/*  83 */     clientPet.setRarity(iterator.next());
/*  84 */     clientPet.setType(iterator.next());
/*     */   }
/*     */   
/*     */   private void fetchPetAddSkill(String[] record) {
/*  88 */     List<String> list = Arrays.asList(record);
/*  89 */     Iterator<String> iterator = list.iterator();
/*  90 */     String classId = iterator.next();
/*     */     
/*  92 */     ClientPetDescription clientPet = getPetForClassId(classId);
/*  93 */     if (clientPet == null) {
/*  94 */       throw new IllegalStateException("No pet with classId " + classId + " exists!");
/*     */     }
/*  96 */     String regAs = iterator.next();
/*  97 */     PetArchType type = TcgGame.getRpgLoader().getPetArchtypeManager().getArchtypeForId(iterator.next());
/*     */     
/*  99 */     clientPet.setTier(Integer.parseInt(iterator.next()));
/* 100 */     boolean subscriberOnly = Boolean.parseBoolean(iterator.next());
/* 101 */     clientPet.setSubscriberOnly(subscriberOnly);
/* 102 */     iterator.next();
/* 103 */     String skillItemClassId = iterator.next();
/* 104 */     int tier = Integer.parseInt(iterator.next());
/* 105 */     ClientItem skillItem = this.itemRegistry.getItemForClassID(skillItemClassId, tier);
/* 106 */     clientPet.addSkill(skillItem);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientPetDescription getPetForClassId(String classId) {
/* 112 */     if (classId == null) {
/* 113 */       throw new IllegalArgumentException("classId = null");
/*     */     }
/* 115 */     if (!this.pets.containsKey(classId)) {
/* 116 */       ErrorWindowCreator.instance().createErrorMessage("RPG Data Error", "No rpg data exists for pet: " + classId);
/* 117 */       ClientPetDescription pet = new ClientPetDescription(classId);
/* 118 */       this.pets.put(classId, pet);
/* 119 */       return pet;
/*     */     } 
/*     */     
/* 122 */     return this.pets.get(classId);
/*     */   }
/*     */   
/*     */   public boolean hasPetForClassId(String classId) {
/* 126 */     return this.pets.containsKey(classId);
/*     */   }
/*     */   
/*     */   public Collection<ClientPetDescription> getAllPets() {
/* 130 */     return this.pets.values();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\PetRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */