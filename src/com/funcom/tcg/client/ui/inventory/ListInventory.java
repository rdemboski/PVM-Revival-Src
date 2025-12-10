/*    */ package com.funcom.tcg.client.ui.inventory;
/*    */ 
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ListInventory extends AbstractInventory {
/*    */   private List<InventoryItem> inventory;
/*    */   
/*    */   public ListInventory(int id) {
/* 13 */     super(id);
/* 14 */     this.inventory = new ArrayList<InventoryItem>();
/*    */   }
/*    */   
/*    */   public InventoryItem getItemInSlot(int slotId) {
/* 18 */     if (slotId < this.inventory.size() && slotId >= 0) {
/* 19 */       return this.inventory.get(slotId);
/*    */     }
/* 21 */     return null;
/*    */   }
/*    */   
/*    */   public int getSlotForItem(InventoryItem item) {
/* 25 */     return this.inventory.indexOf(item);
/*    */   }
/*    */   
/*    */   public InventoryItem setItemToSlot(InventoryItem item, int slotId) {
/* 29 */     while (this.inventory.size() < slotId + 1) {
/* 30 */       this.inventory.add(null);
/*    */     }
/*    */     
/* 33 */     InventoryItem oldItem = this.inventory.set(slotId, item);
/*    */     
/* 35 */     if (oldItem != null || item != null) {
/* 36 */       fireChanged(slotId, oldItem, item);
/*    */     }
/*    */     
/* 39 */     return oldItem;
/*    */   }
/*    */ 
/*    */   
/*    */   public InventoryItem setItemToSlot(String itemId, int tier, int ammount, int slotId) {
/* 44 */     while (this.inventory.size() < slotId + 1) {
/* 45 */       this.inventory.add(null);
/*    */     }
/*    */     
/* 48 */     InventoryItem oldItem = this.inventory.get(slotId);
/*    */     
/* 50 */     if (oldItem == null || !oldItem.getClassId().equals(itemId) || oldItem.getAmount() != ammount || oldItem.getTier() != tier) {
/*    */ 
/*    */ 
/*    */       
/* 54 */       ClientItem clientItem = MainGameState.getItemRegistry().getItemForClassID(itemId, tier);
/* 55 */       clientItem.setAmount(ammount);
/* 56 */       setItemToSlot((InventoryItem)clientItem, slotId);
/*    */     } 
/*    */     
/* 59 */     return oldItem;
/*    */   }
/*    */   
/*    */   public InventoryItem removeItem(int slotId) {
/* 63 */     return setItemToSlot((InventoryItem)null, slotId);
/*    */   }
/*    */   
/*    */   public int getFirstFreeSlotId() {
/* 67 */     int freeSlotId = this.inventory.indexOf(null);
/* 68 */     if (freeSlotId >= 0) {
/* 69 */       return freeSlotId;
/*    */     }
/* 71 */     return this.inventory.size();
/*    */   }
/*    */   
/*    */   public boolean isEmpty() {
/* 75 */     for (InventoryItem inventoryItem : this.inventory) {
/* 76 */       if (inventoryItem != null) {
/* 77 */         return false;
/*    */       }
/*    */     } 
/* 80 */     return true;
/*    */   }
/*    */   
/*    */   public Iterator<InventoryItem> iterator() {
/* 84 */     return this.inventory.iterator();
/*    */   }
/*    */   
/*    */   public int getSlotCount() {
/* 88 */     return getCapacity();
/*    */   }
/*    */   
/*    */   public int getLastOccupiedSlot() {
/* 92 */     return this.inventory.size();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\inventory\ListInventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */