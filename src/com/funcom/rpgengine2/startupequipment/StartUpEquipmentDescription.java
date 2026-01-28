/*     */ package com.funcom.rpgengine2.startupequipment;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.rpgengine2.pets.PetDescription;

import java.util.ArrayList;
/*     */ import java.util.HashMap;
import java.util.HashSet;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class StartUpEquipmentDescription {
/*  12 */   private Map<String, StartUpPet> pets = new HashMap<String, StartUpPet>(); private String id;
/*  13 */   private Map<String, Integer> itemAmounts = new HashMap<String, Integer>();
/*  14 */   private Map<String, Integer> itemTiers = new HashMap<String, Integer>();
/*  15 */   private Map<String, List<StartUpPetSkill>> petSkills = new HashMap<String, List<StartUpPetSkill>>();
/*  16 */   private List<String> inventoryItems = new LinkedList<String>();
/*  17 */   private List<ItemDescription> equipedItems = new LinkedList<ItemDescription>();
/*     */   
/*     */   public StartUpEquipmentDescription(String id) {
/*  20 */     this.id = id;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  24 */     return this.id;
/*     */   }
/*     */   
/*     */   public void addPetSkill(ItemDescription skill, String petId, boolean equiped, int slot) {
/*  28 */     if (!this.petSkills.containsKey(petId))
/*  29 */       this.petSkills.put(petId, new LinkedList<StartUpPetSkill>()); 
/*  30 */     List<StartUpPetSkill> skills = this.petSkills.get(petId);
/*  31 */     StartUpPetSkill petSkill = new StartUpPetSkill(skill, equiped, slot);
/*  32 */     skills.add(petSkill);
/*     */   }
/*     */   
/*     */   public void addPetSkill(ItemDescription skill, String petId) {
/*  36 */     if (!this.petSkills.containsKey(petId))
/*  37 */       this.petSkills.put(petId, new LinkedList<StartUpPetSkill>()); 
/*  38 */     List<StartUpPetSkill> skills = this.petSkills.get(petId);
/*  39 */     StartUpPetSkill petSkill = new StartUpPetSkill(skill, false, 0);
/*  40 */     skills.add(petSkill);
/*     */   }
/*     */   
/*     */   public boolean hasPet(String petId) {
/*  44 */     return (this.pets.get(petId) != null);
/*     */   }
/*     */   
/*     */   public void addPet(PetDescription pet, boolean equiped, int slot, boolean active) {
/*  48 */     this.pets.put(pet.getId(), new StartUpPet(pet, equiped, slot, active));
/*     */   }
/*     */   
/*     */   public void addPet(PetDescription pet) {
/*  52 */     this.pets.put(pet.getId(), new StartUpPet(pet, false, 0, false));
/*     */   }
/*     */   
/*     */   public void addInventoryItem(String item, int amount, int tier) {
/*  56 */     if (checkIfItemAlreadyExists(item, amount))
/*  57 */       return;  if (amount < 1)
/*     */       return; 
/*  59 */     this.inventoryItems.add(item);
/*  60 */     this.itemAmounts.put(item, Integer.valueOf(amount));
/*  61 */     this.itemTiers.put(item, Integer.valueOf(tier));
/*     */   }
/*     */   
/*     */   public void addEquipedItem(ItemDescription item, int amount) {
/*  65 */     if (checkIfItemAlreadyExists(item.getId(), amount))
/*  66 */       return;  if (amount < 1)
/*     */       return; 
/*  68 */     this.equipedItems.add(item);
/*  69 */     this.itemAmounts.put(item.getId(), Integer.valueOf(amount));
/*     */   }
/*     */   
/*     */   private boolean checkIfItemAlreadyExists(String item, int amount) {
/*  73 */     if (this.itemAmounts.containsKey(item)) {
/*  74 */       this.itemAmounts.put(item, Integer.valueOf(((Integer)this.itemAmounts.get(item)).intValue() + amount));
/*  75 */       return true;
/*     */     } 
/*  77 */     return false;
/*     */   }
/*     */   
/*     */   public Map<String, StartUpPet> getPets() {
/*  81 */     return this.pets;
/*     */   }
/*     */   
/*     */   public Map<String, Integer> getItemAmounts() {
/*  85 */     return this.itemAmounts;
/*     */   }
/*     */   
/*     */   public Map<String, List<StartUpPetSkill>> getPetSkills() {
/*  89 */     return this.petSkills;
/*     */   }
/*     */   
/*     */   public List<String> getInventoryItems() {
/*  93 */     return this.inventoryItems;
/*     */   }
/*     */   
/*     */   public List<ItemDescription> getAllEquipedItems() {
/*  97 */     return this.equipedItems;
/*     */   }
/*     */   
/*     */   public List<ItemDescription> getFirstEquipedItems() {
/* 101 */     List<ItemDescription> ret = new ArrayList<ItemDescription>();
/*     */     
/* 103 */     Set<ItemType> types = new HashSet<ItemType>();
/* 104 */     for (ItemDescription equipedItem : this.equipedItems) {
/* 105 */       if (!types.contains(equipedItem.getItemType())) {
/* 106 */         ret.add(equipedItem);
/* 107 */         types.add(equipedItem.getItemType());
/*     */       } 
/*     */     } 
/*     */     
/* 111 */     return ret;
/*     */   }
/*     */   
/*     */   public Map<String, Integer> getItemTiers() {
/* 115 */     return this.itemTiers;
/*     */   }
/*     */   
/*     */   public class StartUpPetSkill {
/*     */     ItemDescription skill;
/*     */     boolean equiped;
/*     */     int slot;
/*     */     
/*     */     public StartUpPetSkill(ItemDescription skill, boolean equiped, int slot) {
/* 124 */       this.skill = skill;
/* 125 */       this.equiped = equiped;
/* 126 */       this.slot = slot;
/*     */     }
/*     */     
/*     */     public ItemDescription getSkill() {
/* 130 */       return this.skill;
/*     */     }
/*     */     
/*     */     public boolean isEquiped() {
/* 134 */       return this.equiped;
/*     */     }
/*     */     
/*     */     public int getSlot() {
/* 138 */       return this.slot;
/*     */     }
/*     */   }
/*     */   
/*     */   public static class StartUpPet {
/*     */     PetDescription petDescription;
/*     */     boolean equiped;
/*     */     int slot;
/*     */     boolean active;
/*     */     
/*     */     public StartUpPet(PetDescription petDescription, boolean equiped, int slot, boolean active) {
/* 149 */       this.petDescription = petDescription;
/* 150 */       this.equiped = equiped;
/* 151 */       this.slot = slot;
/* 152 */       this.active = active;
/*     */     }
/*     */     
/*     */     public PetDescription getPetDescription() {
/* 156 */       return this.petDescription;
/*     */     }
/*     */     
/*     */     public boolean isEquiped() {
/* 160 */       return this.equiped;
/*     */     }
/*     */     
/*     */     public int getSlot() {
/* 164 */       return this.slot;
/*     */     }
/*     */     
/*     */     public boolean isActive() {
/* 168 */       return this.active;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\startupequipment\StartUpEquipmentDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */