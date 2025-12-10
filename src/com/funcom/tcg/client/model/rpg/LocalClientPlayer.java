/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.gameengine.ai.Brain;
/*     */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.client.ui.inventory.ItemUser;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorFullWindow;
/*     */ import com.funcom.tcg.net.message.ActivePetUpdateMessage;
/*     */ import com.funcom.tcg.net.message.ArrangeItemsInInventoryMessage;
/*     */ import com.funcom.tcg.net.message.AutoUseItemMessage;
/*     */ import com.funcom.tcg.net.message.PetSelectionUpdateMessage;
/*     */ import com.funcom.tcg.net.message.RemoveItemMessage;
/*     */ import com.funcom.tcg.net.message.RequestInventorySyncMessage;
/*     */ import com.funcom.tcg.net.message.SellItemToVendorMessage;
/*     */ import com.funcom.tcg.net.message.TryToUseItemMessage;
/*     */ import com.funcom.tcg.net.message.VerifyTownPortalActivationMessage;
/*     */ import com.funcom.tcg.rpg.BaseFaction;
/*     */ import com.funcom.tcg.rpg.Faction;
/*     */ import com.funcom.tcg.rpg.ItemHolderType;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ public class LocalClientPlayer
/*     */   extends ClientPlayer
/*     */   implements ItemUser {
/*  30 */   private static final Logger LOGGER = Logger.getLogger(LocalClientPlayer.class.getName());
/*  31 */   private Faction faction = BaseFaction.PLAYER;
/*     */   private String email;
/*     */   
/*     */   public LocalClientPlayer(String name, String email, WorldCoordinate position, Brain aliveBrain, double radius) {
/*  35 */     super(name, position, aliveBrain, radius);
/*  36 */     this.email = email;
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeItem(int containerId, int containerType, int slotId) {
/*  41 */     RemoveItemMessage removeItemMessage = new RemoveItemMessage(getId(), containerType, containerId, slotId);
/*     */     
/*     */     try {
/*  44 */       NetworkHandler.instance().getIOHandler().send((Message)removeItemMessage);
/*  45 */     } catch (InterruptedException e) {
/*  46 */       LOG.error("Message failed!", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Faction getFaction() {
/*  51 */     return this.faction;
/*     */   }
/*     */   
/*     */   public void setFaction(Faction faction) {
/*  55 */     this.faction = faction;
/*     */   }
/*     */ 
/*     */   
/*     */   public void sellItem(int containerId, int containerType, int slotId) {
/*  60 */     if (!TcgUI.isWindowOpen(VendorFullWindow.class)) {
/*     */       return;
/*     */     }
/*     */     
/*  64 */     InventoryItem inventoryItem = getInventory().getItemInSlot(slotId);
/*  65 */     if (inventoryItem == null) {
/*     */       return;
/*     */     }
/*  68 */     if (inventoryItem.getValueAmount() == 0)
/*     */       return; 
/*  70 */     int vendorId = ((VendorFullWindow)TcgUI.getWindowFromClass(VendorFullWindow.class)).getModel().getCreatureId();
/*     */     
/*  72 */     SellItemToVendorMessage sellItemToVendorMessage = new SellItemToVendorMessage(getId(), containerId, containerType, slotId, inventoryItem.getAmount(), vendorId);
/*     */ 
/*     */     
/*     */     try {
/*  76 */       NetworkHandler.instance().getIOHandler().send((Message)sellItemToVendorMessage);
/*  77 */     } catch (InterruptedException e) {
/*  78 */       LOG.error("Message failed!", e);
/*     */     } 
/*     */     
/*  81 */     fireItemSold(containerId, containerType, slotId);
/*     */   }
/*     */   
/*     */   private void fireItemSold(int containerId, int containerType, int slotId) {
/*  85 */     if (this.playerEventsListeners != null) {
/*  86 */       for (PlayerEventsListener playerEventsListener : this.playerEventsListeners) {
/*  87 */         playerEventsListener.itemSold(containerId, containerType, slotId);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void arrangeItem(int fromSlotId, int toSlotId) {
/*  93 */     ArrangeItemsInInventoryMessage arrangeItemsInInventoryMessage = new ArrangeItemsInInventoryMessage(fromSlotId, toSlotId);
/*     */     try {
/*  95 */       NetworkHandler.instance().getIOHandler().send((Message)arrangeItemsInInventoryMessage);
/*  96 */     } catch (InterruptedException e) {
/*  97 */       LOG.error("Message failed!", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void refreshPets() {
/* 105 */     super.refreshPets();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void setActivePet(ClientPet activePet) {
/* 111 */     super.setActivePet(activePet);
/*     */     
/* 113 */     sendActivePetUpdateMessage();
/* 114 */     MainGameState.updatePetSize(activePet);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean setActivePetFromClassId(String activePetClassId) {
/* 120 */     boolean assignedActivePet = false;
/* 121 */     if (this.activePet == null || (!activePetClassId.equals("") && !this.activePet.getClassId().equals(activePetClassId))) {
/* 122 */       if (this.activePet != null)
/* 123 */         this.activePet.removePetEventsListener(this.petListener); 
/* 124 */       this.activePet = getCollectedPetForId(activePetClassId);
/* 125 */       setUpMappedDfxsForActivePet();
/* 126 */       this.activePet.addPetEventsListener(this.petListener);
/* 127 */       fireActivePetChanged();
/*     */       
/* 129 */       assignedActivePet = true;
/*     */     } 
/*     */     
/* 132 */     return assignedActivePet;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void firePetSelectionChanged(int index, ClientPet clientPet) {
/* 137 */     super.firePetSelectionChanged(index, clientPet);
/* 138 */     sendPetSelectionMessage();
/*     */   }
/*     */   
/*     */   private void sendPetSelectionMessage() {
/* 142 */     PetSlot[] selectedPets = getPetSlots();
/*     */     
/* 144 */     String[] petClassIds = new String[3];
/* 145 */     for (int i = 0; i < 3; i++) {
/* 146 */       ClientPet selectedPet = selectedPets[i].getPet();
/* 147 */       if (selectedPet == null) {
/* 148 */         petClassIds[i] = "";
/*     */       } else {
/* 150 */         petClassIds[i] = selectedPet.getClassId();
/*     */       } 
/*     */     } 
/* 153 */     PetSelectionUpdateMessage petSelectionUpdateMessage = new PetSelectionUpdateMessage(petClassIds);
/*     */     try {
/* 155 */       NetworkHandler.instance().getIOHandler().send((Message)petSelectionUpdateMessage);
/* 156 */     } catch (InterruptedException e) {
/* 157 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   private void sendActivePetUpdateMessage() {
/*     */     String classId;
/* 162 */     ClientPet activePet = getActivePet();
/*     */ 
/*     */     
/* 165 */     if (activePet == null) {
/* 166 */       classId = "";
/*     */     } else {
/* 168 */       classId = activePet.getClassId();
/*     */     } 
/* 170 */     ActivePetUpdateMessage activePetUpdateMessage = new ActivePetUpdateMessage(getId(), classId, 0);
/*     */     try {
/* 172 */       NetworkHandler.instance().getIOHandler().send((Message)activePetUpdateMessage);
/* 173 */     } catch (InterruptedException e) {
/* 174 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void autoUseItem(AutoUseItemMessage.Type type) {
/*     */     try {
/* 182 */       NetworkHandler.instance().getIOHandler().send((Message)new AutoUseItemMessage(type));
/* 183 */     } catch (InterruptedException e) {
/* 184 */       LOGGER.error("Failed to send AutoUseItemMessage", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void tryToUseItem(AutoUseItemMessage.Type type, int slotId) {
/*     */     try {
/* 192 */       NetworkHandler.instance().getIOHandler().send((Message)new TryToUseItemMessage(type, slotId));
/* 193 */     } catch (InterruptedException e) {
/* 194 */       LOGGER.error("Failed to send AutoUseItemMessage", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void openTownportal() {
/*     */     try {
/* 200 */       NetworkHandler.instance().getIOHandler().send((Message)new VerifyTownPortalActivationMessage(getId(), getPosition()));
/*     */     }
/* 202 */     catch (InterruptedException e) {
/* 203 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void requestInventorySync() {
/* 209 */     RequestInventorySyncMessage requestInventorySyncMessage = new RequestInventorySyncMessage(ItemHolderType.INVENTORY.getId(), getInventory().getId(), getId(), RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId());
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 214 */       NetworkHandler.instance().getIOHandler().send((Message)requestInventorySyncMessage);
/* 215 */     } catch (InterruptedException e) {
/* 216 */       LOGGER.error("Failed to send InventoryRequestMessage!", e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getEmail() {
/* 221 */     return this.email;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\LocalClientPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */