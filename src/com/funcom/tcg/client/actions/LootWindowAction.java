/*    */ package com.funcom.tcg.client.actions;
/*    */ 
/*    */ import com.funcom.gameengine.model.action.AbstractAction;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.ui.BWindowTcg;
/*    */ import com.funcom.tcg.client.ui.WindowClosedListener;
/*    */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*    */ import com.funcom.tcg.client.ui.inventory.ListInventory;
/*    */ import com.funcom.tcg.net.message.RequestInventorySyncMessage;
/*    */ import com.funcom.tcg.rpg.ItemHolderType;
/*    */ 
/*    */ public class LootWindowAction
/*    */   extends AbstractAction
/*    */ {
/*    */   public static final String ACTION_NAME = "loot";
/*    */   private int containerId;
/*    */   private InteractibleProp lootPoint;
/*    */   
/*    */   public LootWindowAction(int containerId, InteractibleProp lootPoint) {
/* 24 */     this.containerId = containerId;
/* 25 */     this.lootPoint = lootPoint;
/*    */   }
/*    */   
/*    */   public void perform(InteractibleProp invoker) {
/* 29 */     ListInventory listInventory = new ListInventory(this.containerId);
/*    */     
/* 31 */     registerLootInventoryUpdate((Inventory)listInventory);
/*    */   }
/*    */   
/*    */   private void updateGui(InteractibleProp invoker, Inventory loot) {
/* 35 */     ClientPlayer player = (ClientPlayer)invoker;
/*    */   }
/*    */ 
/*    */   
/*    */   private void registerLootInventoryUpdate(Inventory loot) {
/* 40 */     NetworkHandler.instance().registerForInventoryUpdate(loot);
/* 41 */     RequestInventorySyncMessage requestInventorySyncMessage = new RequestInventorySyncMessage(ItemHolderType.LOOT_BAG.getId(), this.containerId, getParent().getId(), RpgCreatureConstants.Type.LOOT_CREATURE_TYPE_ID.getTypeId());
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 47 */       NetworkHandler.instance().getIOHandler().send((Message)requestInventorySyncMessage);
/* 48 */     } catch (InterruptedException e) {
/* 49 */       throw new IllegalStateException(e);
/*    */     } 
/*    */   }
/*    */   
/*    */   public String getName() {
/* 54 */     return "loot";
/*    */   }
/*    */   
/*    */   private static class UnregisterLootUpdateListener implements WindowClosedListener {
/*    */     private final Inventory loot;
/*    */     
/*    */     public UnregisterLootUpdateListener(Inventory loot) {
/* 61 */       this.loot = loot;
/*    */     }
/*    */ 
/*    */     
/*    */     public void windowClosed(BWindowTcg window) {
/* 66 */       NetworkHandler.instance().unregisterInventoryUpdates(this.loot);
/* 67 */       window.removeWindowClosedListener(this);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\actions\LootWindowAction.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */