/*    */ package com.funcom.tcg.client.ui.character;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.SubscribeWindow;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ 
/*    */ public class InventoryEquipmentModel extends EquipmentModel {
/*    */   private final SelectionHandler selectionHandler;
/*    */   private final CharacterWindowModel windowModel;
/*    */   
/*    */   public InventoryEquipmentModel(ResourceManager resourceManager, SelectionHandler selectionHandler, CharacterWindowModel windowModel) {
/* 15 */     super(resourceManager);
/* 16 */     this.selectionHandler = selectionHandler;
/* 17 */     this.windowModel = windowModel;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSelected() {
/* 22 */     if (hasItem()) {
/* 23 */       return this.selectionHandler.isEquipDollItemSelected(this.item);
/*    */     }
/* 25 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 30 */     if (selected) {
/* 31 */       if (hasItem()) {
/* 32 */         if (!this.windowModel.isSubscriber() && this.item.isSubscriberOnly()) {
/*    */           
/* 34 */           SubscribeWindow window = new SubscribeWindow(TcgGame.getResourceManager(), MainGameState.isPlayerRegistered(), MainGameState.isPlayerRegistered() ? "subscribedialog.button.subscribe" : "quitwindow.askregister.ok", "subscribedialog.button.cancel", MainGameState.isPlayerRegistered() ? "popup.select.members.item" : "popup.select.members.item.notsaved");
/*    */ 
/*    */ 
/*    */           
/* 38 */           window.setLayer(101);
/* 39 */           BuiSystem.getRootNode().addWindow((BWindow)window);
/*    */         } else {
/*    */           
/* 42 */           this.windowModel.equipItem(this.item);
/*    */         } 
/*    */       }
/*    */     } else {
/* 46 */       throw new UnsupportedOperationException("cannot unequip items");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\InventoryEquipmentModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */