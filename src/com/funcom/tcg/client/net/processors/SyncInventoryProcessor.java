/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.ClientEquipDoll;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.GameOperationState;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import com.funcom.tcg.net.message.SyncInventoryMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SyncInventoryProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 22 */     SyncInventoryMessage inventoryMessage = (SyncInventoryMessage)message;
/* 23 */     int inventoryType = inventoryMessage.getInventoryType();
/* 24 */     if (inventoryType == Inventory.TYPE_INVENTORY) {
/* 25 */       updateInventoryAndTips(inventoryMessage.getSlotId(), inventoryMessage.getItemId(), inventoryMessage.getAmount(), inventoryMessage.getTier());
/* 26 */     } else if (inventoryType == Inventory.TYPE_LOOT) {
/* 27 */       updateLoot(inventoryMessage.getInventoryId(), inventoryMessage.getSlotId(), inventoryMessage.getItemId(), inventoryMessage.getAmount(), inventoryMessage.getTier());
/* 28 */     } else if (inventoryType == Inventory.TYPE_EQUIPDOLL) {
/* 29 */       updateEquipDoll(inventoryMessage.getSlotId(), inventoryMessage.getItemId(), inventoryMessage.getTier());
/*    */     } 
/*    */   }
/*    */   protected void updateLoot(int inventoryId, int slotId, String itemId, int amount, int tier) {
/* 33 */     Inventory inv = GameOperationState.findForId(inventoryId);
/* 34 */     if (amount > 0) {
/* 35 */       ClientItem clientItem = MainGameState.getItemRegistry().getItemForClassID(itemId, tier);
/* 36 */       clientItem.setAmount(amount);
/* 37 */       inv.setItemToSlot((InventoryItem)clientItem, slotId);
/*    */     } else {
/* 39 */       inv.removeItem(slotId);
/*    */     } 
/*    */   }
/*    */   protected void updateInventoryAndTips(int slotId, String itemId, int ammount, int tier) {
/* 43 */     Inventory inventory = MainGameState.getPlayerModel().getInventory();
/*    */     
/* 45 */     if (itemId != null && ammount > 0) {
/* 46 */       InventoryItem oldItem = inventory.getItemInSlot(slotId);
/*    */       
/* 48 */       if ((oldItem == null || oldItem.getAmount() != ammount) && (
/* 49 */         oldItem == null || oldItem.getAmount() < ammount)) {
/* 50 */         if (itemId.equals("coin")) {
/* 51 */           MainGameState.getTips().put("tutorial.description.foundcoin");
/* 52 */         } else if (itemId.equals("pet-token")) {
/* 53 */           MainGameState.getTips().put("tutorial.description.foundpettoken");
/* 54 */         } else if (itemId.equals("potion-health") || itemId.equals("potion-mana")) {
/* 55 */           MainGameState.getTips().put("tutorial.description.foundpotion");
/*    */         } 
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 61 */     updateInventory(slotId, itemId, ammount, tier);
/*    */   }
/*    */   
/*    */   protected void updateInventory(int slotId, String itemId, int amount, int tier) {
/* 65 */     Inventory inv = MainGameState.getPlayerModel().getInventory();
/*    */     
/* 67 */     if (itemId != null && amount > 0) {
/* 68 */       inv.setItemToSlot(itemId, tier, amount, slotId);
/*    */     } else {
/* 70 */       inv.removeItem(slotId);
/*    */     } 
/*    */   }
/*    */   protected void updateEquipDoll(int slotId, String itemId, int tier) {
/* 74 */     ClientEquipDoll equipDoll = MainGameState.getPlayerModel().getEquipDoll();
/* 75 */     ClientItem item = MainGameState.getItemRegistry().getItemForClassID(itemId, tier);
/* 76 */     equipDoll.setItem(slotId, item);
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 80 */     return 203;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\SyncInventoryProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */