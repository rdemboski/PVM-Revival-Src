/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class ClientPetDescription
/*     */ {
/*  11 */   private static final Logger LOG = Logger.getLogger(ClientPetDescription.class.getName());
/*     */   private String classId;
/*     */   private String model;
/*     */   private String name;
/*     */   private String description;
/*     */   private String family;
/*     */   private String elementId;
/*     */   private String type;
/*     */   private String rarity;
/*     */   private int tier;
/*     */   private boolean subscriberOnly;
/*     */   private List<ClientItem> allSkills;
/*     */   private ModularDescription petDescription;
/*     */   private CreatureVisualDescription petVisuals;
/*     */   
/*     */   public ClientPetDescription(String classId) {
/*  27 */     this.classId = classId;
/*  28 */     this.allSkills = new LinkedList<ClientItem>();
/*     */   }
/*     */   
/*     */   public String getClassId() {
/*  32 */     return this.classId;
/*     */   }
/*     */   
/*     */   public String getModel() {
/*  36 */     return this.model;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  40 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/*  44 */     return this.description;
/*     */   }
/*     */   
/*     */   public String getFamily() {
/*  48 */     return this.family;
/*     */   }
/*     */   
/*     */   public String getElementId() {
/*  52 */     return this.elementId;
/*     */   }
/*     */   
/*     */   public String getType() {
/*  56 */     return this.type;
/*     */   }
/*     */   
/*     */   public String getRarity() {
/*  60 */     return this.rarity;
/*     */   }
/*     */   
/*     */   public int getTier() {
/*  64 */     return this.tier;
/*     */   }
/*     */   
/*     */   public boolean isSubscriberOnly() {
/*  68 */     return this.subscriberOnly;
/*     */   }
/*     */   
/*     */   public List<ClientItem> getAllSkills() {
/*  72 */     return this.allSkills;
/*     */   }
/*     */   
/*     */   public ModularDescription getPetDescription() {
/*  76 */     return this.petDescription;
/*     */   }
/*     */   
/*     */   public CreatureVisualDescription getPetVisuals() {
/*  80 */     return this.petVisuals;
/*     */   }
/*     */   
/*     */   public void setType(String type) {
/*  84 */     this.type = type;
/*     */   }
/*     */   
/*     */   public void setRarity(String rarity) {
/*  88 */     this.rarity = rarity;
/*     */   }
/*     */   
/*     */   public void setClassId(String classId) {
/*  92 */     this.classId = classId;
/*     */   }
/*     */   
/*     */   public void setModel(String model) {
/*  96 */     this.model = model;
/*     */   }
/*     */   
/*     */   public void setName(String name) {
/* 100 */     this.name = name;
/*     */   }
/*     */   
/*     */   public void setDescription(String description) {
/* 104 */     this.description = description;
/*     */   }
/*     */   
/*     */   public void setFamily(String family) {
/* 108 */     this.family = family;
/*     */   }
/*     */   
/*     */   public void setElementId(String elementId) {
/* 112 */     this.elementId = elementId;
/*     */   }
/*     */   
/*     */   public void setTier(int tier) {
/* 116 */     this.tier = tier;
/*     */   }
/*     */   
/*     */   public void setSubscriberOnly(boolean subscriberOnly) {
/* 120 */     this.subscriberOnly = subscriberOnly;
/*     */   }
/*     */   
/*     */   public void setPetDescription(ModularDescription petDescription) {
/* 124 */     this.petDescription = petDescription;
/*     */   }
/*     */   
/*     */   public void setPetVisuals(CreatureVisualDescription petVisuals) {
/* 128 */     this.petVisuals = petVisuals;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 132 */     if (getPetVisuals() == null) {
/* 133 */       LOG.error("Pet visuals not defined for pet: " + getClassId() + " name: " + getName());
/*     */     }
/* 135 */     return getPetVisuals().getIcon();
/*     */   }
/*     */   
/*     */   public void addSkill(ClientItem clientItem) {
/* 139 */     this.allSkills.add(clientItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 144 */     return "ClientPetDescription{classId='" + this.classId + '\'' + ", model='" + this.model + '\'' + ", name='" + this.name + '\'' + ", description='" + this.description + '\'' + ", family='" + this.family + '\'' + ", elementId='" + this.elementId + '\'' + ", type='" + this.type + '\'' + ", rarity='" + this.rarity + '\'' + ", tier=" + this.tier + ", subscriberOnly=" + this.subscriberOnly + ", allSkills=" + this.allSkills + ", petDescription=" + this.petDescription + ", petVisuals=" + this.petVisuals + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientPetDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */