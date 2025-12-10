/*    */ package com.funcom.tcg.client.ui.vendor;
/*    */ 
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.net.NetworkHandler;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.BuyItemFromVendorMessage;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.event.ActionEvent;
/*    */ import com.jmex.bui.event.ActionListener;
/*    */ import com.jmex.bui.event.ComponentListener;
/*    */ 
/*    */ public class VendorItemListBuyback extends VendorItemList {
/*    */   public VendorItemListBuyback(BContainer itemContainer, VendorModel model) {
/* 15 */     super(itemContainer, model);
/*    */   }
/*    */   
/*    */   private void updateContainer() {
/* 19 */     this.itemContainer.removeAll();
/* 20 */     for (VendorModelItem vendorItem : this.model.getBuyBackItems()) {
/* 21 */       VendorItemButton vendorItemButton = new VendorItemButton(vendorItem, this.model);
/* 22 */       this.itemContainer.add((BComponent)vendorItemButton);
/* 23 */       vendorItemButton.addListener((ComponentListener)new ActionListener() {
/*    */             public void actionPerformed(ActionEvent event) {
/* 25 */               VendorItemListBuyback.this.buybackItem(vendorItem);
/*    */             }
/*    */           });
/*    */     } 
/*    */   }
/*    */   
/*    */   public void containerSelected() {
/* 32 */     updateContainer();
/* 33 */     updateItemListAffordable();
/*    */   }
/*    */   
/*    */   public void currencyChanged(String classId, int amount) {
/* 37 */     this.currencyMap.put(classId, Integer.valueOf(amount));
/* 38 */     updateItemListAffordable();
/*    */   }
/*    */   
/*    */   public void vendorItemsChanged() {
/* 42 */     updateContainer();
/* 43 */     updateItemListAffordable();
/*    */   }
/*    */   
/*    */   private void buybackItem(VendorModelItem vendorItem) {
/* 47 */     BuyItemFromVendorMessage buyItemFromVendorMessage = new BuyItemFromVendorMessage(MainGameState.getPlayerModel().getId(), this.model.getCreatureId(), vendorItem.getClassId(), vendorItem.getTier(), 1, 1);
/*    */ 
/*    */ 
/*    */     
/*    */     try {
/* 52 */       NetworkHandler.instance().getIOHandler().send((Message)buyItemFromVendorMessage);
/* 53 */     } catch (InterruptedException e) {
/* 54 */       throw new IllegalStateException(e);
/*    */     } 
/*    */     
/* 57 */     this.model.removeBuybackItem(vendorItem);
/* 58 */     containerSelected();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorItemListBuyback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */