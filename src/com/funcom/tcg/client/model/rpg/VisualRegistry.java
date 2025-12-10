/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.loaders.CSVData;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.tcg.rpg.AbstractVisualRegistry;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VisualRegistry
/*     */   extends AbstractVisualRegistry
/*     */ {
/*  21 */   private Map<String, MeshData> defaultMeshes = new HashMap<String, MeshData>();
/*  22 */   private Map<String, AnimationData> defaultAnimations = new HashMap<String, AnimationData>();
/*  23 */   private Map<Integer, List<String>> elements = new HashMap<Integer, List<String>>();
/*  24 */   private HashMap<String, String> elementConversion = new HashMap<String, String>();
/*  25 */   private Map<String, ClientItemVisual> itemVisuals = new HashMap<String, ClientItemVisual>();
/*     */   
/*  27 */   private Map<String, String> rarityImages = new HashMap<String, String>();
/*     */ 
/*     */   
/*     */   public void readAllData(ResourceManager resourceManager) {
/*  31 */     super.readAllData(resourceManager);
/*  32 */     readDefaultMeshData(resourceManager);
/*  33 */     readDefaultAnimationData(resourceManager);
/*  34 */     readDescriptionMeshData(resourceManager);
/*  35 */     readElementData(resourceManager);
/*  36 */     readItemVisualsData(resourceManager);
/*  37 */     readRarityData(resourceManager);
/*     */   }
/*     */   
/*     */   private void readRarityData(ResourceManager resourceManager) {
/*  41 */     CSVData records = (CSVData)resourceManager.getResource(CSVData.class, "description/modular/*.rarity.csv", CacheType.NOT_CACHED);
/*  42 */     for (String[] record : records) {
/*  43 */       createRarityDescription(record);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createRarityDescription(String[] record) {
/*  48 */     List<String> list = Arrays.asList(record);
/*  49 */     Iterator<String> iterator = list.iterator();
/*  50 */     String rarity = iterator.next();
/*  51 */     String image = iterator.next();
/*  52 */     this.rarityImages.put(rarity, image);
/*     */   }
/*     */   
/*     */   public void readDefaultMeshData(ResourceManager resourceManager) {
/*  56 */     CSVData records = (CSVData)resourceManager.getResource(CSVData.class, "description/modular/*.defaultmesh.csv", CacheType.NOT_CACHED);
/*  57 */     for (String[] record : records) {
/*  58 */       createDefaultMeshDescription(record);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createDefaultMeshDescription(String[] record) {
/*  63 */     List<String> list = Arrays.asList(record);
/*  64 */     Iterator<String> iterator = list.iterator();
/*  65 */     String classId = iterator.next();
/*  66 */     String meshPath = iterator.next();
/*  67 */     String texturePath = iterator.next();
/*     */     
/*  69 */     this.defaultMeshes.put(classId, new MeshData(meshPath, texturePath));
/*     */   }
/*     */   
/*     */   private void readDefaultAnimationData(ResourceManager resourceManager) {
/*  73 */     CSVData records = (CSVData)resourceManager.getResource(CSVData.class, "description/modular/*.defaultanimations.csv", CacheType.NOT_CACHED);
/*  74 */     for (String[] record : records) {
/*  75 */       createDefaultAnimationDescription(record);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createDefaultAnimationDescription(String[] record) {
/*  80 */     List<String> list = Arrays.asList(record);
/*  81 */     Iterator<String> iterator = list.iterator();
/*  82 */     String animationName = iterator.next();
/*  83 */     String animationMale = iterator.next();
/*  84 */     String animationFemale = iterator.next();
/*     */     
/*  86 */     this.defaultAnimations.put(animationName, new AnimationData(animationMale, animationFemale));
/*     */   }
/*     */   
/*     */   public void readElementData(ResourceManager resourceManager) {
/*  90 */     CSVData records = (CSVData)resourceManager.getResource(CSVData.class, "rpg/*.elements.csv", CacheType.NOT_CACHED);
/*  91 */     for (String[] record : records) {
/*  92 */       createElementDescription(record);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createElementDescription(String[] record) {
/*  97 */     List<String> list = Arrays.asList(record);
/*  98 */     Iterator<String> iterator = list.iterator();
/*  99 */     String elementId = iterator.next();
/* 100 */     int setId = Integer.parseInt(iterator.next());
/* 101 */     String elementName = iterator.next();
/*     */     
/* 103 */     List<String> elementList = this.elements.get(Integer.valueOf(setId));
/* 104 */     if (elementList == null) {
/* 105 */       elementList = new LinkedList<String>();
/* 106 */       this.elements.put(Integer.valueOf(setId), elementList);
/*     */     } 
/*     */     
/* 109 */     elementList.add(elementId);
/* 110 */     this.elementConversion.put(elementId, elementName);
/*     */   }
/*     */   
/*     */   public void readItemVisualsData(ResourceManager resourceManager) {
/* 114 */     CSVData records = (CSVData)resourceManager.getResource(CSVData.class, "description/modular/*.visuals.csv", CacheType.NOT_CACHED);
/* 115 */     for (String[] record : records) {
/* 116 */       createItemVisualsDescription(record);
/*     */     }
/*     */   }
/*     */   
/*     */   private void createItemVisualsDescription(String[] record) {
/* 121 */     List<String> list = Arrays.asList(record);
/* 122 */     Iterator<String> iterator = list.iterator();
/*     */     
/* 124 */     String itemId = iterator.next();
/* 125 */     ClientItemVisual itemVisual = new ClientItemVisual();
/* 126 */     itemVisual.setId(itemId);
/* 127 */     itemVisual.setAlwaysOnDfxPath(iterator.next());
/* 128 */     itemVisual.setMaleMeshPath(iterator.next());
/* 129 */     itemVisual.setMaleTexturePath(iterator.next());
/* 130 */     itemVisual.setFemaleMeshPath(iterator.next());
/* 131 */     itemVisual.setFemaleTexturePath(iterator.next());
/* 132 */     itemVisual.setHairType(iterator.next());
/* 133 */     itemVisual.setShowHair(Integer.parseInt(iterator.next()));
/* 134 */     itemVisual.setShowHead(Integer.parseInt(iterator.next()));
/* 135 */     itemVisual.setTransparent((Integer.parseInt(iterator.next()) == 1));
/*     */     
/* 137 */     this.itemVisuals.put(itemId, itemVisual);
/*     */   }
/*     */   
/*     */   public MeshData getDefaultMeshPathForClassID(String classId) {
/* 141 */     return this.defaultMeshes.get(classId);
/*     */   }
/*     */   
/*     */   public Map<String, AnimationData> getDefaultAnimations() {
/* 145 */     return Collections.unmodifiableMap(this.defaultAnimations);
/*     */   }
/*     */   
/*     */   public List<String> getElementsForSetID(int setId) {
/* 149 */     return this.elements.get(Integer.valueOf(setId));
/*     */   }
/*     */   
/*     */   public ClientItemVisual getItemVisualForClassID(String classId) {
/* 153 */     return this.itemVisuals.get(classId);
/*     */   }
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public String getElementNameForId(String elementID) {
/* 159 */     return this.elementConversion.get(elementID);
/*     */   }
/*     */   
/*     */   public String getImageStringForRarity(String rarity) {
/* 163 */     return this.rarityImages.get(rarity);
/*     */   }
/*     */   
/*     */   public Map<String, String> getRarityImages() {
/* 167 */     return this.rarityImages;
/*     */   }
/*     */   
/*     */   public static class MeshData {
/*     */     private String meshPath;
/*     */     private String texturePath;
/*     */     
/*     */     private MeshData(String meshPath, String texturePath) {
/* 175 */       this.meshPath = meshPath;
/* 176 */       this.texturePath = texturePath;
/*     */     }
/*     */     
/*     */     public String getMeshPath() {
/* 180 */       return this.meshPath;
/*     */     }
/*     */     
/*     */     public String getTexturePath() {
/* 184 */       return this.texturePath;
/*     */     }
/*     */   }
/*     */   
/*     */   public class AnimationData {
/*     */     private String animationMale;
/*     */     private String animationFemale;
/*     */     
/*     */     public AnimationData(String animationMale, String animationFemale) {
/* 193 */       this.animationMale = animationMale;
/* 194 */       this.animationFemale = animationFemale;
/*     */     }
/*     */     
/*     */     public String getAnimationMale() {
/* 198 */       return this.animationMale;
/*     */     }
/*     */     
/*     */     public String getAnimationFemale() {
/* 202 */       return this.animationFemale;
/*     */     }
/*     */     
/*     */     public String getAnimation(PlayerDescription.Gender gender) {
/* 206 */       if (gender == PlayerDescription.Gender.MALE)
/* 207 */         return this.animationMale; 
/* 208 */       if (gender == PlayerDescription.Gender.FEMALE) {
/* 209 */         return this.animationFemale;
/*     */       }
/* 211 */       throw new IllegalArgumentException("Unknown gender");
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\VisualRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */