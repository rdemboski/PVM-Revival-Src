/*    */ package com.funcom.tcg.client.ui.vendor;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.ItemType;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VendorItemListBuy
/*    */   extends VendorItemList
/*    */ {
/*    */   public VendorItemListBuy(BContainer itemContainer, VendorModel model) {
/* 21 */     super(itemContainer, model);
/*    */   }
/*    */   
/*    */   private void updateContainer() {
/* 25 */     this.itemContainer.removeAll();
/* 26 */     for (VendorModelItem vendorItem : this.model.getVendorItems()) {
/* 27 */       VendorItemButton vendorItemButton = new VendorItemButton(vendorItem, this.model);
/* 28 */       this.itemContainer.add((BComponent)vendorItemButton);
/*    */     } 
/* 30 */     this.itemContainer.invalidate();
/*    */   }
/*    */   
/*    */   public void containerSelected() {
/* 34 */     updateCurrencyMap();
/* 35 */     updateContainer();
/* 36 */     updateItemListAffordable();
/* 37 */     updateAvailableItems();
/*    */   }
/*    */   
/*    */   private void updateCurrencyMap() {
/* 41 */     this.currencyMap.clear();
/* 42 */     Iterator<InventoryItem> items = this.model.getPlayerItems();
/* 43 */     while (items.hasNext()) {
/* 44 */       InventoryItem item = items.next();
/* 45 */       if (item != null && item.getItemType() == ItemType.CRYSTAL) {
/* 46 */         this.currencyMap.put(item.getClassId(), Integer.valueOf(item.getAmount()));
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void currencyChanged(String classId, int amount) {
/* 52 */     this.currencyMap.put(classId, Integer.valueOf(amount));
/* 53 */     updateItemListAffordable();
/* 54 */     updateAvailableItems();
/*    */   }
/*    */   
/*    */   public void vendorItemsChanged() {
/* 58 */     updateContainer();
/* 59 */     updateItemListAffordable();
/* 60 */     updateAvailableItems();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorItemListBuy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */