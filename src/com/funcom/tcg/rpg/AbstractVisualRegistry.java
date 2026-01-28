/*     */ package com.funcom.tcg.rpg;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CSVData;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.NoSuchElementException;
/*     */ 
/*     */ public abstract class AbstractVisualRegistry {
/*  14 */   private Map<String, CreatureVisualDescription> creatureVisuals = new HashMap<String, CreatureVisualDescription>();
/*  15 */   protected Map<ClientDescriptionType, Map<String, ClientDescriptionVisual>> descriptionVisuals = new HashMap<ClientDescriptionType, Map<String, ClientDescriptionVisual>>();
/*     */ 
/*     */   
/*     */   public void readAllData(ResourceManager resourceManager) {
/*  19 */     readCreatureVisualsData(resourceManager);
/*     */   }
/*     */   
/*     */   private void readCreatureVisualsData(ResourceManager resourceManager) {
/*  23 */     CSVData records = (CSVData)resourceManager.getResource(CSVData.class, "description/modular/*.creature-visuals.csv", CacheType.NOT_CACHED);
/*  24 */     for (String[] record : records) {
/*  25 */       createCreatureVisualDescription(record);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createCreatureVisualDescription(String[] record) {
/*  30 */     List<String> list = Arrays.asList(record);
/*  31 */     Iterator<String> iterator = list.iterator();
/*  32 */     CreatureVisualDescription creatureVisual = new CreatureVisualDescription();
/*  33 */     String classId = iterator.next();
/*     */     try {
/*  35 */       String icon = iterator.next();
/*  36 */       String xmlDocPath = iterator.next();
/*  37 */       String idleDfx = iterator.next();
/*  38 */       String walkDfx = iterator.next();
/*  39 */       String deathDfx = iterator.next();
/*  40 */       String interactDfx = iterator.next();
/*  41 */       String alwaysOnDfx = iterator.next();
/*  42 */       String shadow = iterator.next();
/*  43 */       creatureVisual.setId(classId);
/*  44 */       creatureVisual.setIcon(icon);
/*  45 */       creatureVisual.setXmlDocumentPath(xmlDocPath);
/*  46 */       creatureVisual.setIdleDfx(idleDfx);
/*  47 */       creatureVisual.setWalkDfx(walkDfx);
/*  48 */       creatureVisual.setDeathDfx(deathDfx);
/*  49 */       creatureVisual.setInteractDfx(interactDfx);
/*  50 */       creatureVisual.setAlwaysOnDfx(alwaysOnDfx);
/*     */       
/*  52 */       boolean hasShadow = true;
/*  53 */       if (!shadow.isEmpty())
/*  54 */         hasShadow = Boolean.parseBoolean(shadow); 
/*  55 */       creatureVisual.setShadow(hasShadow);
/*  56 */     } catch (NoSuchElementException e) {
/*  57 */       throw new RuntimeException("Missing data in creature visual definition: " + classId);
/*     */     } 
/*     */     
/*  60 */     this.creatureVisuals.put(classId, creatureVisual);
/*     */   }
/*     */   
/*     */   public CreatureVisualDescription getCreatureVisualForClassId(String classId) {
/*  64 */     return this.creatureVisuals.get(classId);
/*     */   }
/*     */   
/*     */   protected void readDescriptionMeshData(ResourceManager resourceManager) {
/*  68 */     CSVData records = (CSVData)resourceManager.getResource(CSVData.class, "description/modular/*.descriptionmesh.csv", CacheType.NOT_CACHED);
/*  69 */     for (String[] record : records) {
/*  70 */       createDescriptionMeshDescription(record);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createDescriptionMeshDescription(String[] record) {
/*  75 */     List<String> list = Arrays.asList(record);
/*  76 */     Iterator<String> iterator = list.iterator();
/*     */     
/*  78 */     String classId = iterator.next();
/*  79 */     String positionId = iterator.next();
/*  80 */     ClientDescriptionType type = ClientDescriptionType.valueOf(positionId.toUpperCase());
/*     */     
/*  82 */     ClientDescriptionVisual clientDescriptionVisual = new ClientDescriptionVisual();
/*  83 */     clientDescriptionVisual.setClassId(classId);
/*  84 */     clientDescriptionVisual.setType(type);
/*  85 */     clientDescriptionVisual.setName(iterator.next());
/*  86 */     clientDescriptionVisual.setMaleMeshPath(iterator.next());
/*  87 */     clientDescriptionVisual.setMaleTexturePath(iterator.next());
/*  88 */     clientDescriptionVisual.setFemaleMeshPath(iterator.next());
/*  89 */     clientDescriptionVisual.setFemaleTexturePath(iterator.next());
/*     */     
/*  91 */     Map<String, ClientDescriptionVisual> positionMap = this.descriptionVisuals.get(type);
/*  92 */     if (positionMap == null) {
/*  93 */       positionMap = new LinkedHashMap<String, ClientDescriptionVisual>();
/*  94 */       this.descriptionVisuals.put(type, positionMap);
/*     */     } 
/*  96 */     positionMap.put(classId, clientDescriptionVisual);
/*     */   }
/*     */ 
/*     */   
/*     */   public Map<String, ClientDescriptionVisual> getDescriptionVisualsForType(ClientDescriptionType type) {
/* 101 */     Map<String, ClientDescriptionVisual> ret = this.descriptionVisuals.get(type);
/* 102 */     if (ret != null) {
/* 103 */       return ret;
/*     */     }
/* 105 */     return Collections.EMPTY_MAP;
/*     */   }
/*     */   
/*     */   public Map<ClientDescriptionType, Map<String, ClientDescriptionVisual>> getDescriptionVisuals() {
/* 109 */     return this.descriptionVisuals;
/*     */   }
/*     */   
/*     */   public ClientDescriptionVisual getDescriptionVisualForClassID(ClientDescriptionType type, String classId) {
/* 113 */     Map<String, ClientDescriptionVisual> visualMap = getDescriptionVisualsForType(type);
/* 114 */     ClientDescriptionVisual result = visualMap.get(classId);
/* 115 */     if (result == null) {
/* 116 */       throw new RuntimeException("Cannot find visual description: " + classId);
/*     */     }
/* 118 */     return result;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\AbstractVisualRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */