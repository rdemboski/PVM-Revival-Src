/*    */ package com.funcom.tcg.client.ui.vendor;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class VendorModelItemInventoryAdapter
/*    */   implements VendorModelItem
/*    */ {
/*    */   private InventoryItem inventoryItem;
/*    */   
/*    */   public VendorModelItemInventoryAdapter(InventoryItem inventoryItem) {
/* 13 */     this.inventoryItem = inventoryItem;
/*    */   }
/*    */   
/*    */   public String getClassId() {
/* 17 */     return this.inventoryItem.getClassId();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 21 */     return (this.inventoryItem.getName() == null || this.inventoryItem.getName().trim().isEmpty()) ? "Item name needed" : this.inventoryItem.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 26 */     return this.inventoryItem.getIcon();
/*    */   }
/*    */   
/*    */   public Set<PriceDesc> getPrice() {
/* 30 */     Set<PriceDesc> result = new HashSet<PriceDesc>();
/* 31 */     if (this.inventoryItem.getValueClassId() == null) {
/* 32 */       System.err.println("!!!!  Inventory Item without value class: " + this.inventoryItem.getName());
/* 33 */       return result;
/*    */     } 
/*    */     
/* 36 */     if (!this.inventoryItem.getValueClassId().isEmpty()) {
/* 37 */       result.add(new PriceDesc() {
/*    */             public String getClassId() {
/* 39 */               return VendorModelItemInventoryAdapter.this.inventoryItem.getValueClassId();
/*    */             }
/*    */             
/*    */             public int getAmount() {
/* 43 */               return VendorModelItemInventoryAdapter.this.inventoryItem.getValueAmount();
/*    */             }
/*    */           });
/*    */     }
/* 47 */     return result;
/*    */   }
/*    */   
/*    */   public int getAvailableAmount() {
/* 51 */     return this.inventoryItem.getAmount();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isWithinLevel(int level) {
/* 56 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 61 */     return this.inventoryItem.getTier();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemAmount() {
/* 66 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getItemDescriptionText() {
/* 71 */     return "";
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLevel() {
/* 76 */     return this.inventoryItem.getLevel();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSubscriberOnly() {
/* 81 */     return this.inventoryItem.isSubscriberOnly();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorModelItemInventoryAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */