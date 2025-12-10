/*     */ package com.funcom.tcg.client.ui.pets3;
/*     */ 
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.PetSlot;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsAdapter;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsListener;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.event.SubscriberChangedListener;
/*     */ import com.funcom.tcg.client.ui.hud2.HudModel;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.net.DefaultSubscriptionState;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ClientPetsWindowModel implements PetsWindowModel {
/*     */   private final LocalClientPlayer playerModel;
/*  19 */   private List<SubscriberChangedListener> subscriberListeners = new ArrayList<SubscriberChangedListener>(4); private final HudModel hudModel; private final PetView[] changeListeners;
/*     */   private List<PetWindowPet> sortedPets;
/*     */   private InventoryItem tokens;
/*     */   private PetEventsListener petEventsListener;
/*     */   
/*     */   public ClientPetsWindowModel(LocalClientPlayer playerModel, HudModel hudModel) {
/*  25 */     this.playerModel = playerModel;
/*  26 */     this.hudModel = hudModel;
/*  27 */     int petSlotCount = (playerModel.getPetSlots()).length;
/*  28 */     this.changeListeners = new PetView[petSlotCount];
/*  29 */     this.petEventsListener = new PetEventsListener(this);
/*  30 */     playerModel.addPlayerEventsListener((PlayerEventsListener)this.petEventsListener);
/*  31 */     playerModel.getSubscriptionState().addListener(this.petEventsListener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fireValuesAsChanges() {
/*  36 */     for (int viewId = 0; viewId < this.changeListeners.length; viewId++) {
/*  37 */       this.changeListeners[viewId].petChanged(this.playerModel.getSelectedPet(viewId));
/*  38 */       this.changeListeners[viewId].refresh();
/*     */     } 
/*     */     
/*  41 */     Iterator<InventoryItem> items = MainGameState.getPlayerModel().getInventory().iterator();
/*     */     
/*  43 */     while (items.hasNext()) {
/*  44 */       InventoryItem item = items.next();
/*  45 */       if (item != null && item.getClassId().equals("pet-token")) {
/*  46 */         this.tokens = item;
/*     */         return;
/*     */       } 
/*     */     } 
/*  50 */     this.tokens = null;
/*     */   }
/*     */   
/*     */   public int getNumTokens() {
/*  54 */     if (this.tokens == null)
/*  55 */       return 0; 
/*  56 */     return this.tokens.getAmount();
/*     */   }
/*     */ 
/*     */   
/*     */   public void viewChanged(int viewId) {
/*  61 */     this.hudModel.petButtonAction(viewId);
/*     */   }
/*     */   
/*     */   public int getActiveViewId() {
/*  65 */     ClientPet activePet = this.playerModel.getActivePet();
/*  66 */     PetSlot[] selectedPets = this.playerModel.getPetSlots();
/*     */     
/*  68 */     for (int i = 0; i < selectedPets.length; i++) {
/*  69 */       if (selectedPets[i].getPet() != null && selectedPets[i].getPet().getClassId().equals(activePet.getClassId()))
/*     */       {
/*  71 */         return i;
/*     */       }
/*     */     } 
/*     */     
/*  75 */     return -1;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCollectedPet(PetWindowPet pet) {
/*  80 */     return pet.hasPlayerPet();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPetSwitchLock(int viewId, boolean locked) {
/*  85 */     this.changeListeners[viewId].setSwitchLocked(locked);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSubscriber() {
/*  90 */     return this.playerModel.isSubscriber();
/*     */   }
/*     */   
/*     */   private void firePetChanged(int viewId, ClientPet newPet) {
/*  94 */     this.changeListeners[viewId].petChanged(this.playerModel.getSelectedPet(viewId));
/*     */   }
/*     */   
/*     */   private void firePetAcquired(ClientPet pet) {
/*  98 */     for (PetWindowPet windowPet : this.sortedPets) {
/*  99 */       if (windowPet.getPetDescription().equals(pet.getClientPetDescription())) {
/* 100 */         windowPet.setPlayerPet(pet);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPet(int viewId, ClientPet newPet) {
/* 108 */     if (newPet == null) {
/* 109 */       this.playerModel.setSelectedPet(viewId, newPet);
/*     */       return;
/*     */     } 
/* 112 */     if (this.playerModel.getCollectedPets().contains(newPet)) {
/*     */       
/* 114 */       if (!this.playerModel.isSubscriber() && newPet.isSubscriberOnly() && !newPet.isOnTrial()) {
/*     */         return;
/*     */       }
/* 117 */       if (!this.playerModel.getPetSlot(viewId).isSelectable()) {
/*     */         return;
/*     */       }
/* 120 */       PetSlot[] selectedPets = this.playerModel.getPetSlots();
/* 121 */       for (int i = 0; i < selectedPets.length; i++) {
/* 122 */         if (newPet.equals(selectedPets[i].getPet())) {
/* 123 */           ClientPet petInCurrentSlot = selectedPets[viewId].getPet();
/* 124 */           if (petInCurrentSlot == null)
/*     */             return; 
/* 126 */           this.playerModel.setSelectedPet(i, petInCurrentSlot);
/*     */           
/*     */           break;
/*     */         } 
/*     */       } 
/* 131 */       this.playerModel.setSelectedPet(viewId, newPet);
/* 132 */       this.playerModel.setActivePet(newPet);
/* 133 */       this.changeListeners[viewId].select();
/*     */     } else {
/* 135 */       System.out.println("Doesn't have: " + newPet.getClassId());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ClientPet getPet(int viewId) {
/* 141 */     return this.playerModel.getSelectedPet(viewId);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setChangeListener(int viewId, PetView petView) {
/* 146 */     this.changeListeners[viewId] = petView;
/*     */   }
/*     */   
/*     */   public void addSubscriberListener(SubscriberChangedListener listener) {
/* 150 */     this.subscriberListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeSubscriberListener(SubscriberChangedListener listener) {
/* 154 */     this.subscriberListeners.remove(listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 159 */     this.playerModel.removePlayerEventsListener((PlayerEventsListener)this.petEventsListener);
/* 160 */     this.playerModel.getSubscriptionState().removeListener(this.petEventsListener);
/* 161 */     this.subscriberListeners.clear();
/*     */     
/* 163 */     for (PetView changeListener : this.changeListeners)
/* 164 */       changeListener.dispose(); 
/*     */   }
/*     */   
/*     */   public List<PetWindowPet> getSortedPets() {
/* 168 */     return this.sortedPets;
/*     */   }
/*     */   
/*     */   public void setSortedPets(List<PetWindowPet> sortedPets) {
/* 172 */     this.sortedPets = sortedPets;
/*     */   }
/*     */   
/*     */   private static class PetEventsListener
/*     */     extends PlayerEventsAdapter implements DefaultSubscriptionState.ChangeListener {
/*     */     private final ClientPetsWindowModel parent;
/*     */     
/*     */     public PetEventsListener(ClientPetsWindowModel parent) {
/* 180 */       this.parent = parent;
/*     */     }
/*     */ 
/*     */     
/*     */     public void selectedPetsReconfigured(int petSlot, ClientPet newPet) {
/* 185 */       this.parent.firePetChanged(petSlot, newPet);
/*     */     }
/*     */ 
/*     */     
/*     */     public void newPetAcquired(ClientPet pet) {
/* 190 */       this.parent.firePetAcquired(pet);
/*     */     }
/*     */     
/*     */     public void subscriptionStateChanged(DefaultSubscriptionState subscriptionState) {
/* 194 */       this.parent.setPetSwitchLock(0, !subscriptionState.isSubscriber());
/* 195 */       this.parent.setPetSwitchLock(2, !subscriptionState.isSubscriber());
/*     */       
/* 197 */       if (subscriptionState.isSubscriber()) {
/* 198 */         for (PetWindowPet pet : this.parent.getSortedPets()) {
/* 199 */           ClientPet playerPet = pet.getPlayerPet();
/* 200 */           if (playerPet != null) {
/* 201 */             playerPet.setOnTrial(false);
/* 202 */             playerPet.setPetTrialExpireTime(0L);
/*     */           } 
/*     */         } 
/*     */       }
/*     */       
/* 207 */       PetView[] petViews = this.parent.changeListeners;
/* 208 */       for (PetView petView : petViews) {
/* 209 */         petView.resetView();
/*     */       }
/*     */       
/* 212 */       for (SubscriberChangedListener listener : this.parent.subscriberListeners)
/* 213 */         listener.subscriberStatusChanged(subscriptionState.isSubscriber()); 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\ClientPetsWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */