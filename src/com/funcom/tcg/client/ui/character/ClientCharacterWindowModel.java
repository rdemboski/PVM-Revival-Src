/*    */ package com.funcom.tcg.client.ui.character;
/*    */ 
/*    */ import com.funcom.rpgengine2.StatCollection;
/*    */ import com.funcom.tcg.client.model.rpg.ClientEquipDoll;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.funcom.tcg.client.ui.event.SubscriberChangedListener;
/*    */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import com.funcom.tcg.net.DefaultSubscriptionState;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ClientCharacterWindowModel
/*    */   implements CharacterWindowModel {
/*    */   private final ClientPlayer clientPlayer;
/* 17 */   private List<SubscriberChangedListener> subscriberListeners = new ArrayList<SubscriberChangedListener>(1);
/*    */   
/*    */   public ClientCharacterWindowModel(ClientPlayer clientPlayer) {
/* 20 */     this.clientPlayer = clientPlayer;
/* 21 */     CharacterWindowListener eventsListener = new CharacterWindowListener();
/* 22 */     clientPlayer.getSubscriptionState().addListener(eventsListener);
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientPlayer getClientPlayer() {
/* 27 */     return this.clientPlayer;
/*    */   }
/*    */ 
/*    */   
/*    */   public StatCollection getStats() {
/* 32 */     return this.clientPlayer.getStatSupport().getStatCollection();
/*    */   }
/*    */ 
/*    */   
/*    */   public ClientEquipDoll getEquipDoll() {
/* 37 */     return this.clientPlayer.getEquipDoll();
/*    */   }
/*    */ 
/*    */   
/*    */   public Inventory getInventory() {
/* 42 */     return this.clientPlayer.getInventory();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSubscriber() {
/* 47 */     return this.clientPlayer.isSubscriber();
/*    */   }
/*    */ 
/*    */   
/*    */   public void removeSubscriberListener(SubscriberChangedListener listener) {
/* 52 */     this.subscriberListeners.remove(listener);
/*    */   }
/*    */ 
/*    */   
/*    */   public void addSubscriberListener(SubscriberChangedListener listener) {
/* 57 */     this.subscriberListeners.add(listener);
/*    */   }
/*    */ 
/*    */   
/*    */   public void equipItem(InventoryItem item) {
/* 62 */     Inventory inventory = getInventory();
/* 63 */     ClientEquipDoll equipDoll = getEquipDoll();
/* 64 */     int slotId = inventory.getSlotForItem(item);
/* 65 */     if (slotId != -1 && !equipDoll.hasItem(item) && (!item.isSubscriberOnly() || this.clientPlayer.isSubscriber())) {
/*    */ 
/*    */       
/* 68 */       TcgUI.getUISoundPlayer().play("WearItem");
/* 69 */       item.use(this.clientPlayer, inventory.getId(), Inventory.TYPE_INVENTORY, slotId, this.clientPlayer.getRotation(), 0.0D);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private class CharacterWindowListener
/*    */     implements DefaultSubscriptionState.ChangeListener
/*    */   {
/*    */     private CharacterWindowListener() {}
/*    */ 
/*    */     
/*    */     public void subscriptionStateChanged(DefaultSubscriptionState subscriptionState) {
/* 81 */       for (SubscriberChangedListener listener : ClientCharacterWindowModel.this.subscriberListeners)
/* 82 */         listener.subscriberStatusChanged(subscriptionState.isSubscriber()); 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\ClientCharacterWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */