/*    */ package com.funcom.rpgengine2.pets;
import com.funcom.rpgengine2.equipment.PetArchType;
/*    */ import com.funcom.rpgengine2.items.ItemManager;
/*    */ import com.funcom.rpgengine2.loader.ConfigErrors;
/*    */ import java.util.HashMap;
import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ import org.apache.log4j.Logger;
/*    */ 
/*    */ public class PetManager {
/* 11 */   private static final Logger LOG = Logger.getLogger(PetManager.class.getName());
/*    */   
/*    */   protected ItemManager itemManager;
/*    */   private Map<String, PetDescription> petMap;
/*    */   private Map<String, String> petReferenceMap;
/*    */   private final ConfigErrors configErrors;
/*    */   
/*    */   public PetManager(ConfigErrors configErrors) {
/* 19 */     this.configErrors = configErrors;
/* 20 */     this.petMap = new HashMap<String, PetDescription>();
/* 21 */     this.petReferenceMap = new HashMap<String, String>();
/*    */   }
/*    */   
/*    */   public void clearData() {
/* 25 */     this.petMap.clear();
/* 26 */     this.petReferenceMap.clear();
/*    */   }
/*    */   
/*    */   public void setItemManager(ItemManager itemManager) {
/* 30 */     this.itemManager = itemManager;
/*    */   }
/*    */   
/*    */   public void putPet(PetDescription petDescription) {
/* 34 */     this.petMap.put(petDescription.getId(), petDescription);
/*    */   }
/*    */   
/*    */   public boolean hasPetDescription(String classId) {
/* 38 */     return this.petMap.containsKey(classId);
/*    */   }
/*    */   
/*    */   public PetDescription getPetDescription(String classId) {
/* 42 */     if (!this.petMap.containsKey(classId)) {
/*    */       
/* 44 */       this.configErrors.addError("Missing Pet", "No pet exists for Id: " + classId);
/* 45 */       PetDescription petDescription = new PetDescription(classId, classId, 1, false, new PetArchType("", 0.0D, 0.0D, 0.0D, 0.0D, 0.0D));
/* 46 */       putPet(petDescription);
/* 47 */       return petDescription;
/*    */     } 
/*    */     
/* 50 */     return this.petMap.get(classId);
/*    */   }
/*    */   
/*    */   public Set<String> getPetList() {
/* 54 */     return this.petMap.keySet();
/*    */   }
/*    */   
/*    */   public void addPetReference(String petId, String mapsAs) {
/* 58 */     this.petReferenceMap.put(petId, mapsAs);
/*    */   }
/*    */   
/*    */   public String petRegistersAs(String petId) {
/* 62 */     return this.petReferenceMap.get(petId);
/*    */   }
/*    */   
/*    */   public String petBackReference(String petId) {
/* 66 */     String ref = this.petReferenceMap.get(petId);
/* 67 */     if (ref != null)
/* 68 */       return ref; 
/* 69 */     Set<Map.Entry<String, String>> entries = this.petReferenceMap.entrySet();
/* 70 */     for (Map.Entry<String, String> entry : entries) {
/* 71 */       if (((String)entry.getValue()).equals(petId)) {
/* 72 */         return entry.getKey();
/*    */       }
/*    */     } 
/* 75 */     return null;
/*    */   }
/*    */   
/*    */   public Set<String> getUniquePetList() {
/* 79 */     Set<String> pets = new HashSet<String>();
/* 80 */     pets.addAll(this.petReferenceMap.values());
/* 81 */     return pets;
/*    */   }
/*    */   
/*    */   public void collectPetsWithSkill(String skillId, List<PetDescription> petList) {
/* 85 */     for (Map.Entry<String, PetDescription> pet : this.petMap.entrySet()) {
/* 86 */       PetDescription petDescription = pet.getValue();
/* 87 */       if (petDescription.getSkills().contains(skillId))
/* 88 */         petList.add(petDescription); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pets\PetManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */