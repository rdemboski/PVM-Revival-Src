/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.GameWindows;
/*    */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*    */ import com.funcom.tcg.net.message.SyncInventoryListMessage;
/*    */ import com.funcom.tcg.rpg.InventoryItem;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class SyncInventoryListProcessor
/*    */   extends SyncInventoryProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 21 */     SyncInventoryListMessage syncInventoryListMessage = (SyncInventoryListMessage)message;
/* 22 */     List<InventoryItem> items = syncInventoryListMessage.getInventoryItems();
/* 23 */     int inventoryType = syncInventoryListMessage.getInventoryType();
/* 24 */     if (inventoryType == Inventory.TYPE_INVENTORY) {
/* 25 */       int capacity = syncInventoryListMessage.getCapacity();
/* 26 */       updateInventory(items, capacity);
/* 27 */     } else if (inventoryType == Inventory.TYPE_LOOT) {
/* 28 */       for (InventoryItem item : items)
/* 29 */         updateLoot(syncInventoryListMessage.getId(), item.getSlotId(), item.getItemId(), item.getAmount(), item.getTier()); 
/* 30 */     } else if (inventoryType == Inventory.TYPE_EQUIPDOLL) {
/* 31 */       if (MainGameState.getPlayerNode() != null && !MainGameState.getWorld().isFullLoading() && TcgGame.isLoginFinished())
/*    */       {
/*    */         
/* 34 */         MainGameState.getGuiWindowsController().openWindow(GameWindows.CHARACTER);
/*    */       }
/*    */       
/* 37 */       for (InventoryItem item : items)
/* 38 */         updateEquipDoll(item.getSlotId(), item.getItemId(), item.getTier()); 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void updateInventory(List<InventoryItem> items, int capacity) {
/* 43 */     Inventory inventory = MainGameState.getPlayerModel().getInventory();
/* 44 */     Map<Integer, InventoryItem> slotIdToItemMap = makeSlotIdToItemMap(items, inventory.getLastOccupiedSlot());
/*    */     
/* 46 */     inventory.setCapacity(capacity);
/*    */     
/* 48 */     syncItemSlots(inventory, slotIdToItemMap);
/*    */   }
/*    */   
/*    */   private void syncItemSlots(Inventory inventory, Map<Integer, InventoryItem> slotIdToItemMap) {
/* 52 */     for (Map.Entry<Integer, InventoryItem> itemEntry : slotIdToItemMap.entrySet()) {
/* 53 */       InventoryItem item = slotIdToItemMap.get(itemEntry.getKey());
/* 54 */       if (item != null) {
/* 55 */         updateInventory(((Integer)itemEntry.getKey()).intValue(), item.getItemId(), item.getAmount(), item.getTier()); continue;
/* 56 */       }  if (inventory.getItemInSlot(((Integer)itemEntry.getKey()).intValue()) != null) {
/* 57 */         inventory.removeItem(((Integer)itemEntry.getKey()).intValue());
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   private Map<Integer, InventoryItem> makeSlotIdToItemMap(List<InventoryItem> items, int capacity) {
/* 63 */     Map<Integer, InventoryItem> itemMap = new HashMap<Integer, InventoryItem>();
/* 64 */     for (int i = 0; i < capacity; i++) {
/* 65 */       itemMap.put(Integer.valueOf(i), null);
/*    */     }
/* 67 */     for (InventoryItem item : items) {
/* 68 */       itemMap.put(Integer.valueOf(item.getSlotId()), item);
/*    */     }
/* 70 */     return itemMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 75 */     return 205;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\SyncInventoryListProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */