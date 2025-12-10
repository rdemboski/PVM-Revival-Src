/*     */ package com.funcom.tcg.client.ui.vendor;
/*     */ 
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.givepet.GivePetAbility;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.rpgengine2.pets.PetManager;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ItemRegistry;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.jmex.bui.BContainer;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VendorItemList
/*     */ {
/*     */   protected BContainer itemContainer;
/*     */   protected VendorModel model;
/*  26 */   protected Map<String, Integer> currencyMap = new HashMap<String, Integer>();
/*     */   private static final int MAX_ITEM_TIER = 3;
/*     */   
/*     */   public VendorItemList(BContainer itemContainer, VendorModel model) {
/*  30 */     this.itemContainer = itemContainer;
/*  31 */     this.model = model;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateAvailableItems() {
/*  36 */     ItemRegistry itemRegistry = MainGameState.getItemRegistry();
/*  37 */     int level = MainGameState.getPlayerModel().getStatSupport().getLevel();
/*  38 */     int count = this.itemContainer.getComponentCount();
/*  39 */     for (int i = 0; i < count; i++) {
/*  40 */       VendorItemButton vendorItemButton = (VendorItemButton)this.itemContainer.getComponent(i);
/*     */ 
/*     */ 
/*     */       
/*  44 */       VendorModelItem vendorItem = vendorItemButton.getItem();
/*  45 */       if (!vendorItem.isWithinLevel(level)) {
/*  46 */         vendorItemButton.setVisible(false);
/*  47 */         vendorItemButton.setEnabled(false);
/*  48 */       } else if (vendorItem.isSubscriberOnly() && !MainGameState.getPlayerModel().getSubscriptionState().isSubscriber()) {
/*  49 */         vendorItemButton.setVisible(true);
/*  50 */         vendorItemButton.setEnabled(false);
/*     */       } else {
/*  52 */         vendorItemButton.setVisible(true);
/*  53 */         ClientItem clientItem = itemRegistry.getItemForClassID(vendorItem.getClassId(), vendorItem.getTier());
/*  54 */         ItemType itemType = clientItem.getItemType();
/*     */         
/*  56 */         if (itemType.isEquipable()) {
/*  57 */           vendorItemButton.setEnabled(isEquipmentBuyable(clientItem));
/*  58 */         } else if (clientItem.getItemType().equals(ItemType.USE_ON_BUY)) {
/*  59 */           vendorItemButton.setEnabled(isUsable(clientItem));
/*     */         } else {
/*  61 */           vendorItemButton.setEnabled(isSpaceInStack(clientItem));
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isUsable(ClientItem clientItem) {
/*  69 */     for (Ability ability : clientItem.getAbilities()) {
/*  70 */       if (ability instanceof GivePetAbility) {
/*  71 */         return canBuyPet(((GivePetAbility)ability).getPetId());
/*     */       }
/*     */     } 
/*  74 */     return true;
/*     */   }
/*     */   
/*     */   private boolean canBuyPet(String petId) {
/*  78 */     PetManager petManager = TcgGame.getRpgLoader().getPetManager();
/*  79 */     String ref = petManager.petRegistersAs(petId);
/*  80 */     Set<ClientPet> collectedPets = MainGameState.getPlayerModel().getCollectedPets();
/*  81 */     for (ClientPet collectedPet : collectedPets) {
/*  82 */       if (petManager.petRegistersAs(collectedPet.getBaseClassId()).equals(ref))
/*  83 */         return false; 
/*     */     } 
/*  85 */     return true;
/*     */   }
/*     */   
/*     */   private boolean isSpaceInStack(ClientItem clientItem) {
/*  89 */     for (int i = 3; i > clientItem.getTier(); i--) {
/*  90 */       if (MainGameState.getItemRegistry().containsClassID(clientItem.getClassId(), i) && MainGameState.getPlayerModel().getInventory().getSlotForItem((InventoryItem)MainGameState.getItemRegistry().getItemForClassID(clientItem.getClassId(), i)) > -1)
/*     */       {
/*  92 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  96 */     int slotForItem = MainGameState.getPlayerModel().getInventory().getSlotForItem((InventoryItem)clientItem);
/*  97 */     if (slotForItem > -1) {
/*  98 */       InventoryItem inventoryItem = MainGameState.getPlayerModel().getInventory().getItemInSlot(slotForItem);
/*  99 */       return (inventoryItem.getAmount() < inventoryItem.getStackSize());
/*     */     } 
/*     */ 
/*     */     
/* 103 */     String ref = MainGameState.getItemRegistry().itemReferencesAs(clientItem.getClassId());
/* 104 */     if (ref != null) {
/* 105 */       Set<ClientItem> backReferences = MainGameState.getItemRegistry().getBackReferences(ref, clientItem.getTier());
/* 106 */       for (ClientItem refItem : backReferences) {
/* 107 */         if (MainGameState.getPlayerModel().getInventory().getSlotForItem((InventoryItem)refItem) > -1) {
/* 108 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean isEquipmentBuyable(ClientItem clientItem) {
/* 117 */     if (MainGameState.getPlayerModel().getInventory().getSlotForItem((InventoryItem)clientItem) > -1) {
/* 118 */       return false;
/*     */     }
/*     */     
/* 121 */     String ref = MainGameState.getItemRegistry().itemReferencesAs(clientItem.getClassId());
/* 122 */     if (ref != null) {
/* 123 */       Set<ClientItem> backReferences = MainGameState.getItemRegistry().getBackReferences(ref, clientItem.getTier());
/* 124 */       for (ClientItem refItem : backReferences) {
/* 125 */         if (MainGameState.getPlayerModel().getInventory().getSlotForItem((InventoryItem)refItem) > -1) {
/* 126 */           return false;
/*     */         }
/*     */       } 
/*     */     } 
/* 130 */     if (clientItem.getTier() > 1 && MainGameState.getPlayerModel().getInventory().getSlotForItem((InventoryItem)MainGameState.getItemRegistry().getItemForClassID(clientItem.getClassId(), clientItem.getTier() - 1)) == -1)
/*     */     {
/* 132 */       return false;
/*     */     }
/*     */     
/* 135 */     for (int i = 3; i > clientItem.getTier(); i--) {
/* 136 */       if (MainGameState.getItemRegistry().containsClassID(clientItem.getClassId(), i) && MainGameState.getPlayerModel().getInventory().getSlotForItem((InventoryItem)MainGameState.getItemRegistry().getItemForClassID(clientItem.getClassId(), i)) > -1)
/*     */       {
/* 138 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 143 */     List<ClientItem> clientItems = MainGameState.getPlayerModel().getEquipDoll().getItems();
/* 144 */     for (ClientItem item : clientItems) {
/* 145 */       if (item != null && item.getClassId().equalsIgnoreCase(clientItem.getClassId()) && item.getTier() == clientItem.getTier()) {
/* 146 */         return false;
/*     */       }
/*     */     } 
/* 149 */     return true;
/*     */   }
/*     */   
/*     */   public void updateItemListAffordable() {
/* 153 */     for (int i = 0; i < this.itemContainer.getComponentCount(); i++) {
/* 154 */       VendorItemButton vendorItemButton = (VendorItemButton)this.itemContainer.getComponent(i);
/* 155 */       VendorModelItem vendorItem = vendorItemButton.getItem();
/* 156 */       vendorItemButton.setAffordable(isAffordable(vendorItem));
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isAffordable(VendorModelItem vendorItem) {
/* 161 */     Set<PriceDesc> priceDescSet = vendorItem.getPrice();
/* 162 */     for (PriceDesc priceDesc : priceDescSet) {
/* 163 */       Integer playerAmount = this.currencyMap.get(priceDesc.getClassId());
/*     */       
/* 165 */       if (playerAmount == null || playerAmount.intValue() < priceDesc.getAmount())
/* 166 */         return false; 
/* 167 */       if (MainGameState.getVendorWindow() != null && 
/* 168 */         playerAmount.intValue() - MainGameState.getVendorWindow().getBuyAmountCoins() < priceDesc.getAmount()) {
/* 169 */         return false;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 175 */     return true;
/*     */   }
/*     */   
/*     */   public abstract void containerSelected();
/*     */   
/*     */   public abstract void currencyChanged(String paramString, int paramInt);
/*     */   
/*     */   public abstract void vendorItemsChanged();
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorItemList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */