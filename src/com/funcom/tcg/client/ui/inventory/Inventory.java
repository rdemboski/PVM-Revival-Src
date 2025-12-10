/*    */ package com.funcom.tcg.client.ui.inventory;
/*    */ 
/*    */ import com.funcom.tcg.rpg.ItemHolderType;
/*    */ 
/*    */ 
/*    */ public interface Inventory
/*    */   extends Iterable<InventoryItem>
/*    */ {
/*  9 */   public static final int TYPE_INVENTORY = ItemHolderType.INVENTORY.getId();
/* 10 */   public static final int TYPE_SKILLBAR = ItemHolderType.SKILLBAR.getId();
/* 11 */   public static final int TYPE_LOOT = ItemHolderType.LOOT_BAG.getId();
/* 12 */   public static final int TYPE_EQUIPDOLL = ItemHolderType.EQUIPMENT_DOLL.getId();
/*    */   
/*    */   int getId();
/*    */   
/*    */   void addChangeListener(ChangeListener paramChangeListener);
/*    */   
/*    */   void removeChangeListener(ChangeListener paramChangeListener);
/*    */   
/*    */   InventoryItem getItemInSlot(int paramInt);
/*    */   
/*    */   int getSlotForItem(InventoryItem paramInventoryItem);
/*    */   
/*    */   InventoryItem setItemToSlot(InventoryItem paramInventoryItem, int paramInt);
/*    */   
/*    */   InventoryItem setItemToSlot(String paramString, int paramInt1, int paramInt2, int paramInt3);
/*    */   
/*    */   InventoryItem removeItem(int paramInt);
/*    */   
/*    */   int getFirstFreeSlotId();
/*    */   
/*    */   boolean isEmpty();
/*    */   
/*    */   int getCapacity();
/*    */   
/*    */   void setCapacity(int paramInt);
/*    */   
/*    */   int getLastOccupiedSlot();
/*    */   
/*    */   public static interface ChangeListener {
/*    */     void slotChanged(Inventory param1Inventory, int param1Int, InventoryItem param1InventoryItem1, InventoryItem param1InventoryItem2);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\inventory\Inventory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */