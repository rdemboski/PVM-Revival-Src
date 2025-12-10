/*     */ package com.funcom.tcg.client.ui.vendor;
/*     */ 
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsAdapter;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsListener;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.net.message.BuyItemFromVendorMessage;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class VendorModelImpl
/*     */   implements VendorModel
/*     */ {
/*     */   private Set<VendorModel.ChangeListener> changeListeners;
/*     */   private List<VendorModelItem> vendorItems;
/*     */   private List<VendorModelItem> buybackItems;
/*     */   private String vendorName;
/*     */   private int creatureId;
/*     */   private InventoryChangeListener inventoryChangeListener;
/*     */   private VendorEventListener eventsListener;
/*     */   
/*     */   public VendorModelImpl(List<VendorModelItem> vendorItems, List<VendorModelItem> buybackItems, String vendorName, int creatureId) {
/*  31 */     this.vendorItems = vendorItems;
/*  32 */     this.buybackItems = buybackItems;
/*  33 */     this.vendorName = vendorName;
/*  34 */     this.creatureId = creatureId;
/*  35 */     this.inventoryChangeListener = new InventoryChangeListener();
/*  36 */     MainGameState.getPlayerModel().getInventory().addChangeListener(this.inventoryChangeListener);
/*  37 */     this.eventsListener = new VendorEventListener();
/*  38 */     MainGameState.getPlayerModel().addPlayerEventsListener((PlayerEventsListener)this.eventsListener);
/*     */   }
/*     */   
/*     */   public void addChangeListener(VendorModel.ChangeListener listener) {
/*  42 */     if (this.changeListeners == null) {
/*  43 */       this.changeListeners = new HashSet<VendorModel.ChangeListener>();
/*     */     }
/*  45 */     this.changeListeners.add(listener);
/*     */   }
/*     */   
/*     */   public void removeChangeListener(VendorModel.ChangeListener listener) {
/*  49 */     if (this.changeListeners != null)
/*  50 */       this.changeListeners.remove(listener); 
/*     */   }
/*     */   
/*     */   public int getCreatureId() {
/*  54 */     return this.creatureId;
/*     */   }
/*     */   
/*     */   private void fireVendorItemsChanged() {
/*  58 */     if (this.changeListeners != null)
/*  59 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/*  60 */         changeListener.vendorItemsChanged();
/*     */       } 
/*     */   }
/*     */   
/*     */   private void firePlayerCurrencyChanged(String classId, int amount) {
/*  65 */     if (this.changeListeners != null)
/*  66 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/*  67 */         changeListener.playerCurrencyChanged(classId, amount);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireNewItem(String classId, int tier) {
/*  72 */     if (this.changeListeners != null)
/*  73 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/*  74 */         changeListener.newItemInInventory(classId, tier);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireBuyItemHoverEnter(VendorModelItem item) {
/*  79 */     if (this.changeListeners != null)
/*  80 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/*  81 */         changeListener.buyItemHoverEnter(item);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireSellItemHoverEnter(ClientItem item) {
/*  86 */     if (this.changeListeners != null) {
/*  87 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/*  88 */         changeListener.sellItemHoverEnter(item);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void fireSellItemHoverExit() {
/*  94 */     if (this.changeListeners != null)
/*  95 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/*  96 */         changeListener.ItemHoverExit();
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireSellItem(boolean sell, SellInventoryItem sellItem) {
/* 101 */     if (this.changeListeners != null)
/* 102 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/* 103 */         changeListener.sellItem(sell, sellItem);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireBuyItem(boolean buy, VendorItemButton buyItem) {
/* 108 */     if (this.changeListeners != null)
/* 109 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/* 110 */         changeListener.buyItem(buy, buyItem);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireItemSold(VendorModelItem vendorModelItem) {
/* 115 */     if (this.changeListeners != null)
/* 116 */       for (VendorModel.ChangeListener changeListener : this.changeListeners) {
/* 117 */         changeListener.itemSold(vendorModelItem);
/*     */       } 
/*     */   }
/*     */   
/*     */   public String getName() {
/* 122 */     return this.vendorName;
/*     */   }
/*     */   
/*     */   public List<VendorModelItem> getVendorItems() {
/* 126 */     return Collections.unmodifiableList(this.vendorItems);
/*     */   }
/*     */   
/*     */   public List<VendorModelItem> getBuyBackItems() {
/* 130 */     return Collections.unmodifiableList(this.buybackItems);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBuyBackItem(VendorModelItem vendorModelItem) {
/* 140 */     this.buybackItems.add(vendorModelItem);
/* 141 */     while (this.buybackItems.size() > 10) {
/* 142 */       this.buybackItems.remove(0);
/*     */     }
/*     */   }
/*     */   
/*     */   public void removeBuybackItem(VendorModelItem vendorItem) {
/* 147 */     this.buybackItems.remove(vendorItem);
/*     */   }
/*     */   
/*     */   public void sellItem(int containerId, int containerType, int slotId) {
/* 151 */     MainGameState.getPlayerModel().sellItem(containerId, containerType, slotId);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void buyItem(VendorModelItem item) {
/* 161 */     BuyItemFromVendorMessage buyItemFromVendorMessage = new BuyItemFromVendorMessage(MainGameState.getPlayerModel().getId(), getCreatureId(), item.getClassId(), item.getTier(), item.getItemAmount(), 0);
/*     */ 
/*     */     
/*     */     try {
/* 165 */       NetworkHandler.instance().getIOHandler().send((Message)buyItemFromVendorMessage);
/* 166 */     } catch (InterruptedException e) {
/* 167 */       throw new IllegalStateException(e);
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<PriceDesc> getPlayerCurrency() {
/* 172 */     Set<PriceDesc> currency = getPlayerInventoryItems();
/* 173 */     currency = filterRelevantItems(currency);
/* 174 */     return currency;
/*     */   }
/*     */   
/*     */   public Iterator<InventoryItem> getPlayerItems() {
/* 178 */     return MainGameState.getPlayerModel().getInventory().iterator();
/*     */   }
/*     */   
/*     */   public void disposeModel() {
/* 182 */     MainGameState.getPlayerModel().removePlayerEventsListener((PlayerEventsListener)this.eventsListener);
/* 183 */     MainGameState.getPlayerModel().getInventory().removeChangeListener(this.inventoryChangeListener);
/*     */   }
/*     */   
/*     */   private Set<PriceDesc> getPlayerInventoryItems() {
/* 187 */     Set<PriceDesc> currency = new HashSet<PriceDesc>();
/* 188 */     Iterator<InventoryItem> itemIterator = MainGameState.getPlayerModel().getInventory().iterator();
/* 189 */     while (itemIterator.hasNext()) {
/* 190 */       final InventoryItem inventoryItem = itemIterator.next();
/* 191 */       if (inventoryItem != null) {
/* 192 */         currency.add(new PriceDesc() {
/*     */               public String getClassId() {
/* 194 */                 return inventoryItem.getClassId();
/*     */               }
/*     */               
/*     */               public int getAmount() {
/* 198 */                 return inventoryItem.getAmount();
/*     */               }
/*     */             });
/*     */       }
/*     */     } 
/* 203 */     return currency;
/*     */   }
/*     */   
/*     */   private Set<PriceDesc> filterRelevantItems(Set<PriceDesc> priceDescSet) {
/* 207 */     Set<String> vendorPrices = new HashSet<String>();
/* 208 */     for (VendorModelItem vendorModelItem : getVendorItems()) {
/* 209 */       for (PriceDesc priceDesc : vendorModelItem.getPrice()) {
/* 210 */         vendorPrices.add(priceDesc.getClassId());
/*     */       }
/*     */     } 
/*     */     
/* 214 */     Set<PriceDesc> result = new HashSet<PriceDesc>();
/* 215 */     for (PriceDesc priceDesc : priceDescSet) {
/* 216 */       if (vendorPrices.contains(priceDesc.getClassId())) {
/* 217 */         result.add(priceDesc);
/*     */       }
/*     */     } 
/*     */     
/* 221 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public void buyHover(VendorModelItem item) {
/* 226 */     fireBuyItemHoverEnter(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public void sellHover(ClientItem item) {
/* 231 */     fireSellItemHoverEnter(item);
/*     */   }
/*     */ 
/*     */   
/*     */   public void hoverExit() {
/* 236 */     fireSellItemHoverExit();
/*     */   }
/*     */ 
/*     */   
/*     */   public void sellItemToggle(boolean sell, SellInventoryItem sellItem) {
/* 241 */     fireSellItem(sell, sellItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void buyItemToggle(boolean buy, VendorItemButton buyItem) {
/* 246 */     fireBuyItem(buy, buyItem);
/*     */   }
/*     */   
/*     */   private class InventoryChangeListener
/*     */     implements Inventory.ChangeListener {
/*     */     public void slotChanged(Inventory inventory, int slotId, InventoryItem oldItem, InventoryItem newItem) {
/* 252 */       if (newItem != null && newItem.getItemType() == ItemType.CRYSTAL) {
/* 253 */         VendorModelImpl.this.firePlayerCurrencyChanged(newItem.getClassId(), newItem.getAmount());
/* 254 */       } else if (newItem == null && oldItem != null && oldItem.getItemType() == ItemType.CRYSTAL) {
/* 255 */         VendorModelImpl.this.firePlayerCurrencyChanged(oldItem.getClassId(), 0);
/* 256 */       } else if (newItem != null) {
/* 257 */         VendorModelImpl.this.fireNewItem(newItem.getClassId(), newItem.getTier());
/*     */       } 
/*     */     }
/*     */     private InventoryChangeListener() {} }
/*     */   
/*     */   private class VendorEventListener extends PlayerEventsAdapter { private VendorEventListener() {}
/*     */     
/*     */     public void itemSold(int containerId, int containerType, int slotId) {
/* 265 */       InventoryItem inventoryItem = MainGameState.getPlayerModel().getInventory().getItemInSlot(slotId);
/* 266 */       for (int i = 0; i < inventoryItem.getAmount(); i++)
/* 267 */         VendorModelImpl.this.fireItemSold(new VendorModelItemInventoryAdapter(inventoryItem)); 
/*     */     } }
/*     */ 
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorModelImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */