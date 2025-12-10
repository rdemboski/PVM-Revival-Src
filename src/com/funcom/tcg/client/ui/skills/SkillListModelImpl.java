/*     */ package com.funcom.tcg.client.ui.skills;
/*     */ 
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.PetEventsListener;
/*     */ import com.funcom.tcg.client.model.rpg.PetSlot;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsAdapter;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsListener;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.net.message.RequestSkillLearnedMessage;
/*     */ import com.funcom.tcg.rpg.ItemHolderType;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class SkillListModelImpl
/*     */   extends PlayerEventsAdapter implements SkillListModel, PetEventsListener {
/*  22 */   private static final Logger LOGGER = Logger.getLogger(SkillListModelImpl.class.getName());
/*     */   private ClientPet selectedPet;
/*     */   private ClientPlayer clientPlayer;
/*     */   private List<SkillListModel.ChangeListener> listeners;
/*     */   
/*     */   public SkillListModelImpl(ClientPlayer clientPlayer) {
/*  28 */     this.clientPlayer = clientPlayer;
/*  29 */     clientPlayer.addPlayerEventsListener((PlayerEventsListener)this);
/*     */     
/*  31 */     for (ClientPet pet : clientPlayer.getCollectedPets()) {
/*  32 */       if (pet != null) {
/*  33 */         this.selectedPet = pet;
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void addChangeListener(SkillListModel.ChangeListener listener) {
/*  40 */     if (this.listeners == null)
/*  41 */       this.listeners = new LinkedList<SkillListModel.ChangeListener>(); 
/*  42 */     this.listeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeChangeListener(SkillListModel.ChangeListener listener) {
/*  46 */     if (this.listeners != null)
/*  47 */       this.listeners.remove(listener); 
/*     */   }
/*     */   
/*     */   public PetItem getSelectedPet() {
/*  51 */     return (PetItem)this.selectedPet;
/*     */   }
/*     */   
/*     */   public List<PetItem> getAllSelectedPets() {
/*  55 */     List<PetItem> allSelectedPets = new LinkedList<PetItem>();
/*  56 */     for (PetSlot petSlot : this.clientPlayer.getPetSlots()) {
/*  57 */       if (petSlot.getPet() != null)
/*  58 */         allSelectedPets.add(petSlot.getPet()); 
/*     */     } 
/*  60 */     return allSelectedPets;
/*     */   }
/*     */   
/*     */   public int getPlayerLevel() {
/*  64 */     return this.clientPlayer.getStatSum(Short.valueOf((short)20)).intValue();
/*     */   }
/*     */   
/*     */   public List<SkillListItem> getSkillItems() {
/*  68 */     List<ClientItem> petSkills = this.selectedPet.getAllSkills();
/*  69 */     List<SkillListItem> returnable = new LinkedList<SkillListItem>();
/*  70 */     for (ClientItem petSkill : petSkills)
/*  71 */       returnable.add(new SkillListItemImpl(this.selectedPet, petSkill)); 
/*  72 */     return returnable;
/*     */   }
/*     */   
/*     */   public void selectPet(String petClassId) {
/*  76 */     for (ClientPet pet : this.clientPlayer.getCollectedPets()) {
/*  77 */       if (pet.getClassId().equals(petClassId)) {
/*  78 */         if (this.selectedPet != null)
/*  79 */           this.selectedPet.removePetEventsListener(this); 
/*  80 */         this.selectedPet = pet;
/*  81 */         this.selectedPet.addPetEventsListener(this);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void dispose() {
/*  87 */     this.clientPlayer.removePlayerEventsListener((PlayerEventsListener)this);
/*  88 */     if (this.selectedPet != null)
/*  89 */       this.selectedPet.removePetEventsListener(this); 
/*  90 */     this.listeners.removeAll(this.listeners);
/*     */   }
/*     */   
/*     */   public void learnSkillByClassId(String scrollItemId, int inventoryId, int slotId) {
/*  94 */     for (SkillListItem skillItem : getSkillItems()) {
/*  95 */       String skillId = skillItem.getClassId();
/*  96 */       if (scrollItemId.startsWith(skillId) && skillItem.getLevel() <= getPlayerLevel()) {
/*  97 */         RequestSkillLearnedMessage requestSkillLearnedMessage = new RequestSkillLearnedMessage(this.selectedPet.getClassId(), skillId, ItemHolderType.INVENTORY, inventoryId, slotId);
/*     */         try {
/*  99 */           NetworkHandler.instance().getIOHandler().send((Message)requestSkillLearnedMessage);
/*     */           return;
/* 101 */         } catch (InterruptedException e) {
/* 102 */           LOGGER.log((Priority)Level.ERROR, "Failed to send RequestSkillLearnedMessage!", e);
/*     */         } 
/*     */       } 
/*     */     } 
/* 106 */     LOGGER.log((Priority)Level.INFO, "There is no matching skill for the scroll '" + scrollItemId + "' found!");
/*     */   }
/*     */   
/*     */   public void selectedSkillsChanged(ClientPet clientPet) {
/* 110 */     fireSkillsChanged();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void expChanged(ClientPet clientPet) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void levelChanged(ClientPet clientPet, int lastLevel) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void newSkillAcquired(ClientPet clientPet, ClientItem aSkill) {
/* 124 */     fireSkillsChanged();
/*     */   }
/*     */   
/*     */   public void skillLost(ClientPet clientPet, ClientItem aSkill) {
/* 128 */     fireSkillsChanged();
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectedPetsReconfigured(int petSlot, ClientPet newPet) {
/* 133 */     firePetsChanged();
/*     */   }
/*     */   
/*     */   private void fireSkillsChanged() {
/* 137 */     if (this.listeners != null)
/* 138 */       for (SkillListModel.ChangeListener listener : this.listeners)
/* 139 */         listener.skillsChanged(this);  
/*     */   }
/*     */   
/*     */   private void firePetsChanged() {
/* 143 */     if (this.listeners != null)
/* 144 */       for (SkillListModel.ChangeListener listener : this.listeners)
/* 145 */         listener.selectedPetsChanged(this);  
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\skills\SkillListModelImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */