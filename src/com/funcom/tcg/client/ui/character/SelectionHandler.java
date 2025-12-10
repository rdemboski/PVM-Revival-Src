/*     */ package com.funcom.tcg.client.ui.character;
/*     */ 
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.StatListener;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.model.rpg.ClientEquipDoll;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.EquipChangeListener;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.util.SizeCheckedArrayList;
/*     */ import com.jmex.bui.event.ChangeEvent;
/*     */ import com.jmex.bui.event.ChangeListener;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class SelectionHandler
/*     */   implements EquipChangeListener, StatListener
/*     */ {
/*     */   private final CharacterWindowModel windowModel;
/*     */   private final TCGItemAnalyzer analyzer;
/*     */   private final SizeCheckedArrayList<DiffChangeListener> diffChangeListeners;
/*     */   private final List<ChangeListener> wearingEquipmentChangeListeners;
/*     */   private int slotIdSelected;
/*     */   private boolean wearingSelectionEventEnabled;
/*     */   private InventoryItem compareItem;
/*     */   
/*     */   public SelectionHandler(CharacterWindowModel windowModel) {
/*  34 */     this.windowModel = windowModel;
/*     */     
/*  36 */     this.analyzer = new TCGItemAnalyzer();
/*  37 */     this.diffChangeListeners = new SizeCheckedArrayList(2, "SelectionHandler.diffChangeListeners", 2);
/*  38 */     this.wearingEquipmentChangeListeners = (List<ChangeListener>)new SizeCheckedArrayList(2, getClass().getSimpleName(), 2);
/*     */     
/*  40 */     windowModel.getEquipDoll().addChangeListener(this);
/*  41 */     Collection<Stat> statCollection = windowModel.getClientPlayer().getStatSupport().getStatCollection().getStats();
/*  42 */     for (Stat stat : statCollection) {
/*  43 */       stat.addStatListener(this);
/*     */     }
/*     */     
/*  46 */     this.slotIdSelected = -1;
/*     */   }
/*     */   
/*     */   private Map<Object, Integer> updateStats(InventoryItem targetItem) {
/*  50 */     ClientEquipDoll equipDoll = this.windowModel.getEquipDoll();
/*     */     
/*  52 */     List<ClientItem> list = new ArrayList<ClientItem>(equipDoll.getItems());
/*  53 */     list.remove(targetItem);
/*     */     
/*  55 */     Map<Object, Integer> statsFromTargetItem = this.analyzer.getItemStats(list, targetItem, this.windowModel.getClientPlayer());
/*     */ 
/*     */ 
/*     */     
/*  59 */     return statsFromTargetItem;
/*     */   }
/*     */   
/*     */   public void setSlotIdSelected(int selectSlotId) {
/*  63 */     if (this.slotIdSelected != selectSlotId) {
/*  64 */       this.slotIdSelected = selectSlotId;
/*  65 */       fireSelectionChanged();
/*  66 */       updateStatComponents(null);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void fireSelectionChanged() {
/*  71 */     if (this.wearingSelectionEventEnabled) {
/*  72 */       ChangeEvent changeEvent = new ChangeEvent(this);
/*  73 */       for (ChangeListener listener : this.wearingEquipmentChangeListeners) {
/*  74 */         listener.stateChanged(changeEvent);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getSlotIdSelected() {
/*  80 */     return this.slotIdSelected;
/*     */   }
/*     */   
/*     */   public void compareItemChanged(InventoryItem compareItem) {
/*  84 */     this.compareItem = compareItem;
/*  85 */     updateStatComponents(this.compareItem);
/*     */   }
/*     */   private void updateStatComponents(InventoryItem wardrobeItem) {
/*     */     ClientItem clientItem;
/*  89 */     InventoryItem wearingEquipmentSelection = null;
/*     */     
/*  91 */     if (this.slotIdSelected != -1) {
/*  92 */       clientItem = this.windowModel.getEquipDoll().getItem(this.slotIdSelected);
/*     */     }
/*     */     
/*  95 */     if (clientItem != null) {
/*  96 */       ClientItem clientItem1; if (wardrobeItem == null)
/*     */       {
/*  98 */         clientItem1 = clientItem;
/*     */       }
/* 100 */       Map<Object, Integer> wardrobeItemStats = updateStats((InventoryItem)clientItem1);
/* 101 */       Map<Object, Integer> equippedItemStats = updateStats((InventoryItem)clientItem);
/*     */ 
/*     */       
/* 104 */       int wardrobeArmour = this.analyzer.extractArmourSum(wardrobeItemStats);
/* 105 */       int equippedArmour = this.analyzer.extractArmourSum(equippedItemStats);
/*     */ 
/*     */ 
/*     */       
/* 109 */       Map<Object, Integer> diffStats = new HashMap<Object, Integer>();
/* 110 */       Set<Object> allIds = new HashSet();
/* 111 */       allIds.addAll(wardrobeItemStats.keySet());
/* 112 */       allIds.addAll(equippedItemStats.keySet());
/* 113 */       for (Object id : allIds) {
/* 114 */         int wardrobeStatValue = getNonNullValue(wardrobeItemStats, id);
/* 115 */         int equippedStatValue = getNonNullValue(equippedItemStats, id);
/*     */         
/* 117 */         wardrobeItemStats.put(id, Integer.valueOf(wardrobeStatValue));
/* 118 */         equippedItemStats.put(id, Integer.valueOf(equippedStatValue));
/* 119 */         diffStats.put(id, Integer.valueOf(wardrobeStatValue - equippedStatValue));
/*     */       } 
/*     */       
/* 122 */       fireDiffChanged((InventoryItem)clientItem, (InventoryItem)clientItem1, wardrobeItemStats, equippedItemStats, diffStats, wardrobeArmour, equippedArmour);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void fireDiffChanged(InventoryItem equippedItem, InventoryItem wardrobeItem, Map<Object, Integer> wardrobeItemStats, Map<Object, Integer> equippedItemStats, Map<Object, Integer> diffStats, int wardrobeArmour, int equippedArmour) {
/* 128 */     DiffChangeEvent event = new DiffChangeEvent(wardrobeItem, equippedItem, wardrobeItemStats, equippedItemStats, diffStats, wardrobeArmour, equippedArmour);
/*     */ 
/*     */     
/* 131 */     for (DiffChangeListener diffChangeListener : this.diffChangeListeners) {
/* 132 */       diffChangeListener.diffChanged(event);
/*     */     }
/*     */   }
/*     */   
/*     */   private int getNonNullValue(Map<Object, Integer> itemStats, Object id) {
/* 137 */     Integer value = itemStats.get(id);
/* 138 */     if (value != null) {
/* 139 */       return value.intValue();
/*     */     }
/* 141 */     return 0;
/*     */   }
/*     */   
/*     */   public void addDiffChangeListener(DiffChangeListener diffChangeListener) {
/* 145 */     this.diffChangeListeners.add(diffChangeListener);
/*     */   }
/*     */   
/*     */   public void addWearingEquipmentChangeListener(ChangeListener wearingEquipmentChangeListener) {
/* 149 */     this.wearingEquipmentChangeListeners.add(wearingEquipmentChangeListener);
/*     */   }
/*     */   
/*     */   public void reinitialize() {
/* 153 */     if (this.slotIdSelected == -1) {
/* 154 */       List<ClientItem> equippedItems = this.windowModel.getEquipDoll().getItems();
/* 155 */       for (int i = 0, equippedItemsSize = equippedItems.size(); i < equippedItemsSize; i++) {
/* 156 */         if (equippedItems.get(i) != null) {
/* 157 */           this.slotIdSelected = i;
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/* 162 */     fireSelectionChanged();
/*     */   }
/*     */   
/*     */   public boolean isEquipDollItemSelected(InventoryItem item) {
/* 166 */     if (this.slotIdSelected != -1) {
/* 167 */       ClientItem clientItem = this.windowModel.getEquipDoll().getItem(this.slotIdSelected);
/* 168 */       if (clientItem != null) {
/* 169 */         return (clientItem.getClassId().equals(item.getClassId()) && clientItem.getTier() == item.getTier());
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 174 */     return false;
/*     */   }
/*     */   
/*     */   public void setWearingSelectionEventEnabled(boolean wearingSelectionEventEnabled) {
/* 178 */     this.wearingSelectionEventEnabled = wearingSelectionEventEnabled;
/*     */   }
/*     */   
/*     */   public void wearBestItems() {
/* 182 */     ItemComparator itemComparator = new ItemComparator();
/*     */     
/* 184 */     Inventory inventory = this.windowModel.getInventory();
/*     */     
/* 186 */     Map<Integer, InventoryItem> bestItems = new HashMap<Integer, InventoryItem>();
/*     */     
/* 188 */     for (InventoryItem item : inventory) {
/* 189 */       if (item == null)
/*     */         continue; 
/* 191 */       ItemType itemType = item.getItemType();
/* 192 */       if (itemType.getCategory() == ItemType.ItemCategory.EQUIPMENT) {
/* 193 */         InventoryItem bestItem = bestItems.get(Integer.valueOf(itemType.getEquipValue()));
/* 194 */         if (bestItem == null || itemComparator.compare(item, bestItem) > 0)
/*     */         {
/* 196 */           bestItems.put(Integer.valueOf(itemType.getEquipValue()), item);
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 201 */     ClientEquipDoll equipDoll = this.windowModel.getEquipDoll();
/* 202 */     for (InventoryItem bestItem : bestItems.values()) {
/* 203 */       ClientItem equippedItem = equipDoll.getItem(bestItem.getItemType().getEquipValue());
/*     */       
/* 205 */       boolean alreadyEquippedBestItem = (equippedItem != null && equippedItem.getClassId().equals(bestItem.getClassId()) && equippedItem.getTier() == bestItem.getTier());
/*     */ 
/*     */ 
/*     */       
/* 209 */       if (!alreadyEquippedBestItem) {
/* 210 */         this.windowModel.equipItem(bestItem);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void itemEquipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem newItem, ClientItem oldItem) {
/* 217 */     updateStatComponents(this.compareItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void itemUnequipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem oldItem) {
/* 222 */     updateStatComponents(this.compareItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void statChanged(Stat stat, int oldBase, int oldModifier) {
/* 227 */     updateStatComponents(this.compareItem);
/*     */   }
/*     */ 
/*     */   
/*     */   static class DiffChangeEvent
/*     */   {
/*     */     final InventoryItem wardrobeItem;
/*     */     
/*     */     final InventoryItem equippedItem;
/*     */     
/*     */     final Map<Object, Integer> wardrobeItemStats;
/*     */     final Map<Object, Integer> equippedItemStats;
/*     */     final Map<Object, Integer> diffStats;
/*     */     final int wardrobeArmour;
/*     */     final int equippedArmour;
/*     */     
/*     */     private DiffChangeEvent(InventoryItem wardrobeItem, InventoryItem equippedItem, Map<Object, Integer> wardrobeItemStats, Map<Object, Integer> equippedItemStats, Map<Object, Integer> diffStats, int wardrobeArmour, int equippedArmour) {
/* 244 */       this.wardrobeItem = wardrobeItem;
/* 245 */       this.equippedItem = equippedItem;
/* 246 */       this.wardrobeItemStats = wardrobeItemStats;
/* 247 */       this.equippedItemStats = equippedItemStats;
/* 248 */       this.diffStats = diffStats;
/* 249 */       this.wardrobeArmour = wardrobeArmour;
/* 250 */       this.equippedArmour = equippedArmour;
/*     */     }
/*     */   }
/*     */   
/*     */   static interface DiffChangeListener {
/*     */     void diffChanged(SelectionHandler.DiffChangeEvent param1DiffChangeEvent);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\SelectionHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */