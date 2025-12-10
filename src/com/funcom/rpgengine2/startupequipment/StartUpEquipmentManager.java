/*    */ package com.funcom.rpgengine2.startupequipment;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.items.ItemManager;
/*    */ import com.funcom.rpgengine2.pets.PetDescription;
/*    */ import com.funcom.rpgengine2.pets.PetManager;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.log4j.Level;
/*    */ import org.apache.log4j.Logger;
/*    */ import org.apache.log4j.Priority;
/*    */ 
/*    */ public class StartUpEquipmentManager
/*    */ {
/* 15 */   private static final Logger LOGGER = Logger.getLogger(StartUpEquipmentManager.class.getName());
/*    */   private ItemManager itemManager;
/*    */   private PetManager petManager;
/* 18 */   private Map<String, StartUpEquipmentDescription> startUpEquipmentByBuildId = new HashMap<String, StartUpEquipmentDescription>();
/*    */   
/*    */   public void clearData() {
/* 21 */     this.startUpEquipmentByBuildId.clear();
/*    */   }
/*    */   
/*    */   public StartUpEquipmentDescription getDescriptionById(String id) {
/* 25 */     return this.startUpEquipmentByBuildId.get(id);
/*    */   }
/*    */   
/*    */   public ItemManager getItemManager() {
/* 29 */     return this.itemManager;
/*    */   }
/*    */   
/*    */   public void setItemManager(ItemManager itemManager) {
/* 33 */     this.itemManager = itemManager;
/*    */   }
/*    */   
/*    */   public void setPetManager(PetManager petManager) {
/* 37 */     this.petManager = petManager;
/*    */   }
/*    */   
/*    */   public void addToDescription(String[] descriptionData) {
/* 41 */     String buildId = descriptionData[0];
/* 42 */     String itemId = descriptionData[1];
/* 43 */     int tier = Integer.parseInt(descriptionData[2]);
/* 44 */     String amount = descriptionData[3];
/* 45 */     String petId = descriptionData[4];
/* 46 */     String petSkillId = descriptionData[5];
/* 47 */     String equiped = descriptionData[6];
/* 48 */     String slot = descriptionData[7];
/* 49 */     String active = descriptionData[8];
/*    */     
/* 51 */     StartUpEquipmentDescription description = this.startUpEquipmentByBuildId.get(buildId);
/*    */     
/* 53 */     if (description == null) {
/* 54 */       description = new StartUpEquipmentDescription(buildId);
/* 55 */       this.startUpEquipmentByBuildId.put(buildId, description);
/*    */     } 
/*    */     
/*    */     try {
/* 59 */       if (!petId.isEmpty()) {
/* 60 */         PetDescription petDescription = this.petManager.getPetDescription(petId);
/* 61 */         if (petDescription != null) {
/* 62 */           boolean equipedValue = Boolean.parseBoolean(equiped);
/* 63 */           if (petSkillId.isEmpty() && 
/* 64 */             !description.hasPet(petId)) {
/* 65 */             if (equipedValue) {
/* 66 */               description.addPet(petDescription, equipedValue, Integer.parseInt(slot), Boolean.parseBoolean(active));
/*    */             } else {
/* 68 */               description.addPet(petDescription);
/*    */             } 
/*    */           }
/*    */           
/* 72 */           if (!petSkillId.isEmpty() && petDescription.getSkills().contains(petSkillId)) {
/* 73 */             ItemDescription item = this.itemManager.getDescription(petSkillId, tier);
/* 74 */             if (item != null) {
/* 75 */               if (equipedValue) {
/* 76 */                 description.addPetSkill(item, petId, equipedValue, Integer.parseInt(slot));
/*    */               } else {
/* 78 */                 description.addPetSkill(item, petId);
/*    */               } 
/*    */             }
/*    */           } 
/*    */         } 
/*    */       } 
/*    */       
/* 85 */       if (Boolean.parseBoolean(equiped)) {
/* 86 */         if (!itemId.isEmpty() && 
/* 87 */           this.itemManager.hasDescription(itemId)) {
/* 88 */           description.addEquipedItem(this.itemManager.getDescription(itemId, tier), Integer.parseInt(amount));
/*    */         }
/*    */       }
/* 91 */       else if (!itemId.isEmpty() && 
/* 92 */         this.itemManager.hasDescription(itemId)) {
/* 93 */         description.addInventoryItem(itemId, Integer.parseInt(amount), tier);
/*    */       }
/*    */     
/* 96 */     } catch (NumberFormatException e) {
/* 97 */       LOGGER.log((Priority)Level.FATAL, "Invaild number when parsing Start up equipment for build: " + buildId + " and item: " + itemId);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\startupequipment\StartUpEquipmentManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */