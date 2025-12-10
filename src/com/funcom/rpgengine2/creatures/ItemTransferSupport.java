/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import com.funcom.rpgengine2.items.ItemTransferContainer;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.ConcurrentLinkedQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemTransferSupport
/*    */   implements RpgQueryableSupport, RpgUpdateable
/*    */ {
/* 18 */   private final List<ItemTransferListener> listeners = new ArrayList<ItemTransferListener>();
/* 19 */   private final ConcurrentLinkedQueue<ItemTransferContainer> pendingItemTransfers = new ConcurrentLinkedQueue<ItemTransferContainer>();
/*    */ 
/*    */   
/*    */   public void addItemTransferListener(ItemTransferListener listener) {
/* 23 */     this.listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void removeItemTransferListener(ItemTransferListener listener) {
/* 27 */     this.listeners.remove(listener);
/*    */   }
/*    */   
/*    */   public void addItemTransfers(ItemTransferContainer itemTransferContainer) {
/* 31 */     this.pendingItemTransfers.add(itemTransferContainer);
/*    */   }
/*    */   
/*    */   public int[] getUpdatePriorities() {
/* 35 */     return new int[] { 500000 };
/*    */   }
/*    */   
/*    */   public void update(int priority, long updateMillis) {
/* 39 */     if (!this.pendingItemTransfers.isEmpty()) {
/*    */       ItemTransferContainer itemTransferContainer;
/* 41 */       while ((itemTransferContainer = this.pendingItemTransfers.poll()) != null) {
/* 42 */         ItemContainer itemContainer = itemTransferContainer.getItemContainer();
/*    */         
/* 44 */         if (itemContainer != null) {
/* 45 */           if (itemTransferContainer.isAddOrRemove()) {
/*    */             
/* 47 */             int slotId = itemContainer.putItem(itemTransferContainer.getPreferredSlotId(), itemTransferContainer.getItem());
/* 48 */             fireTransferredItems(slotId, itemContainer.getItem(slotId));
/*    */             continue;
/*    */           } 
/* 51 */           itemContainer.removeItem(itemTransferContainer.getPreferredSlotId());
/* 52 */           fireTransferredItems(itemTransferContainer.getPreferredSlotId(), null);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void fireTransferredItems(int slotId, Item item) {
/* 60 */     int size = this.listeners.size();
/* 61 */     for (int i = 0; i < size; i++)
/* 62 */       ((ItemTransferListener)this.listeners.get(i)).itemsTransferred(slotId, item); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\ItemTransferSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */