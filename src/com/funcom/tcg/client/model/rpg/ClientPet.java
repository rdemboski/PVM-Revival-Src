/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.tcg.client.ui.pets3.PetListItem;
/*     */ import com.funcom.tcg.client.ui.skills.PetItem;
/*     */ import com.funcom.tcg.client.ui.skills.SkillListItem;
/*     */ import com.funcom.tcg.client.ui.skills.SkillListItemImpl;
/*     */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientPet
/*     */   implements PetListItem, PetItem, Comparable<ClientPet>
/*     */ {
/*  23 */   private static final Logger LOG = Logger.getLogger(ClientPet.class.getName());
/*     */   
/*     */   private Set<PetEventsListener> listeners;
/*     */   private boolean onTrial;
/*     */   private long petTrialExpireTime;
/*     */   private ClientPetDescription clientPetDescription;
/*     */   private ClientPetDescription updatedClientPetDescription;
/*     */   private int level;
/*     */   private int exp;
/*     */   
/*     */   public ClientPet(ClientPetDescription clientPetDescription, ClientPetDescription updatedClientPetDescription) {
/*  34 */     this.clientPetDescription = clientPetDescription;
/*  35 */     this.updatedClientPetDescription = updatedClientPetDescription;
/*     */   }
/*     */   
/*     */   public void addPetEventsListener(PetEventsListener petEventsListener) {
/*  39 */     if (this.listeners == null)
/*  40 */       this.listeners = new HashSet<PetEventsListener>(); 
/*  41 */     this.listeners.add(petEventsListener);
/*     */   }
/*     */   
/*     */   public void removePetEventsListener(PetEventsListener petEventsListener) {
/*  45 */     if (this.listeners != null) {
/*  46 */       this.listeners.remove(petEventsListener);
/*     */     }
/*     */   }
/*     */   
/*     */   public List<ClientItem> getAllSkills() {
/*  51 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getAllSkills() : this.clientPetDescription.getAllSkills();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientItem getSkillAt(int slotId) {
/*  57 */     return getAllSkills().get(slotId);
/*     */   }
/*     */   
/*     */   private void fireLevelChanged(int lastLevel) {
/*  61 */     if (this.listeners != null)
/*  62 */       for (PetEventsListener listener : this.listeners)
/*  63 */         listener.levelChanged(this, lastLevel);  
/*     */   }
/*     */   
/*     */   private void fireExpChanged() {
/*  67 */     if (this.listeners != null)
/*  68 */       for (PetEventsListener listener : this.listeners)
/*  69 */         listener.expChanged(this);  
/*     */   }
/*     */   
/*     */   public String getClassId() {
/*  73 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getClassId() : this.clientPetDescription.getClassId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getBaseClassId() {
/*  79 */     return this.clientPetDescription.getClassId();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  83 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.clientPetDescription.getName());
/*     */   }
/*     */   
/*     */   public String getIcon() {
/*  87 */     if (this.clientPetDescription.getPetVisuals() == null) {
/*  88 */       LOG.error("Pet visuals not defined for pet: " + getClassId() + " name: " + getName());
/*     */     }
/*  90 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getPetVisuals().getIcon() : this.clientPetDescription.getPetVisuals().getIcon();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getEpicIcon() {
/*  96 */     if (this.clientPetDescription.getPetVisuals() == null) {
/*  97 */       LOG.error("Pet visuals not defined for pet: " + getClassId() + " name: " + getName());
/*     */     }
/*  99 */     return (this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getPetVisuals().getIcon() : this.clientPetDescription.getPetVisuals().getIcon();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getDescription() {
/* 105 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getDescription() : this.clientPetDescription.getDescription();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFamily() {
/* 111 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getFamily() : this.clientPetDescription.getFamily();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getElementId() {
/* 117 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getElementId() : this.clientPetDescription.getElementId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTier() {
/* 123 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getTier() : this.clientPetDescription.getTier();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSubscriberOnly() {
/* 129 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.isSubscriberOnly() : this.clientPetDescription.isSubscriberOnly();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ModularDescription getPetDescription() {
/* 135 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getPetDescription() : this.clientPetDescription.getPetDescription();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public CreatureVisualDescription getPetVisuals() {
/* 141 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getPetVisuals() : this.clientPetDescription.getPetVisuals();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getModel() {
/* 147 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getModel() : this.clientPetDescription.getModel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSkillCount() {
/* 153 */     return 5;
/*     */   }
/*     */   
/*     */   public SkillListItem getSelectedSkill(int index) {
/* 157 */     ClientItem skill = getSkillAt(index);
/* 158 */     if (skill == null)
/* 159 */       return null; 
/* 160 */     return (SkillListItem)new SkillListItemImpl(this, skill);
/*     */   }
/*     */   
/*     */   public String getType() {
/* 164 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getType() : this.clientPetDescription.getType();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getRarity() {
/* 170 */     return (this.level >= 40 && this.updatedClientPetDescription != null) ? this.updatedClientPetDescription.getRarity() : this.clientPetDescription.getRarity();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ClientPetDescription getClientPetDescription() {
/* 176 */     return this.clientPetDescription;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int compareTo(ClientPet o) {
/* 183 */     if (o.getName().compareTo(getName()) != 0) return o.getName().compareTo(getName()); 
/* 184 */     if (o.getTier() != getTier()) return o.getTier() - getTier(); 
/* 185 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 190 */     return "ClientPet{listeners=" + this.listeners + ", onTrial=" + this.onTrial + ", petTrialExpireTime=" + this.petTrialExpireTime + ", clientPetDescription=" + this.clientPetDescription + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isOnTrial() {
/* 199 */     return this.onTrial;
/*     */   }
/*     */   
/*     */   public void setOnTrial(boolean onTrial) {
/* 203 */     this.onTrial = onTrial;
/*     */   }
/*     */   
/*     */   public long getPetTrialExpireTime() {
/* 207 */     return this.petTrialExpireTime;
/*     */   }
/*     */   
/*     */   public void setPetTrialExpireTime(long petTrialExpireTime) {
/* 211 */     this.petTrialExpireTime = petTrialExpireTime;
/*     */   }
/*     */   
/*     */   public int getLevel() {
/* 215 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(int level) {
/* 219 */     int lastLevel = this.level;
/* 220 */     this.level = level;
/* 221 */     fireLevelChanged(lastLevel);
/*     */   }
/*     */   
/*     */   public int getExp() {
/* 225 */     return this.exp;
/*     */   }
/*     */   
/*     */   public void setExp(int exp) {
/* 229 */     this.exp = exp;
/* 230 */     fireExpChanged();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientPet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */