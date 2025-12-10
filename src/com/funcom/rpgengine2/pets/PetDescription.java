/*    */ package com.funcom.rpgengine2.pets;
/*    */ 
/*    */ import com.funcom.rpgengine2.SkillId;
/*    */ import com.funcom.rpgengine2.equipment.PetArchType;
/*    */ import java.util.HashMap;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PetDescription
/*    */ {
/*    */   private String id;
/*    */   private String petItemClassId;
/*    */   private int petLevel;
/*    */   private boolean subscriberOnly;
/*    */   private List<String> skills;
/*    */   private Map<Integer, SkillId> defaultSkills;
/*    */   private PetArchType archType;
/*    */   
/*    */   public PetDescription(String id, String petItemClassId, int petLevel, boolean subscriberOnly, PetArchType archType) {
/* 21 */     this.id = id;
/* 22 */     this.petItemClassId = petItemClassId;
/* 23 */     this.petLevel = petLevel;
/* 24 */     this.subscriberOnly = subscriberOnly;
/* 25 */     this.skills = new LinkedList<String>();
/* 26 */     this.defaultSkills = new HashMap<Integer, SkillId>();
/* 27 */     this.archType = archType;
/*    */   }
/*    */   
/*    */   public void addSkill(String skill, int tier, boolean defaultSkill, int slot) {
/* 31 */     this.skills.add(skill);
/*    */     
/* 33 */     if (defaultSkill) {
/* 34 */       SkillId skillId = new SkillId(skill, tier);
/* 35 */       if (!this.defaultSkills.containsValue(skillId)) {
/* 36 */         this.defaultSkills.put(Integer.valueOf(slot), skillId);
/*    */       } else {
/* 38 */         throw new RuntimeException("cannot assign same skill as default multiple times: petId=" + getId() + " problemSkill=" + skillId);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public String getId() {
/* 45 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getPetItemClassId() {
/* 49 */     return this.petItemClassId;
/*    */   }
/*    */   
/*    */   public List<String> getSkills() {
/* 53 */     return this.skills;
/*    */   }
/*    */   
/*    */   public Map<Integer, SkillId> getDefaultSkills() {
/* 57 */     return this.defaultSkills;
/*    */   }
/*    */   
/*    */   public void setId(String id) {
/* 61 */     this.id = id;
/*    */   }
/*    */   
/*    */   public void setPetItemClassId(String petItemClassId) {
/* 65 */     this.petItemClassId = petItemClassId;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPetLevel() {
/* 70 */     return this.petLevel;
/*    */   }
/*    */   
/*    */   public boolean isSubscriberOnly() {
/* 74 */     return this.subscriberOnly;
/*    */   }
/*    */   
/*    */   public PetArchType getArchType() {
/* 78 */     return this.archType;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pets\PetDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */