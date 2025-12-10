/*    */ package com.funcom.tcg.client.ui.vendor;
/*    */ 
/*    */ import com.funcom.rpgengine2.vendor.VendorItem;
/*    */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class VendorModelItemVendorAdapter
/*    */   implements VendorModelItem
/*    */ {
/*    */   private VendorItem vendorItem;
/*    */   private ClientItem clientItem;
/*    */   
/*    */   public VendorModelItemVendorAdapter(VendorItem vendorItem) {
/* 16 */     this.vendorItem = vendorItem;
/* 17 */     this.clientItem = MainGameState.getItemRegistry().getItemForClassID(vendorItem.getItemId(), vendorItem.getTier());
/*    */   }
/*    */   
/*    */   public String getClassId() {
/* 21 */     return this.clientItem.getClassId();
/*    */   }
/*    */   
/*    */   public String getName() {
/* 25 */     return (this.clientItem.getName() == null || this.clientItem.getName().trim().isEmpty()) ? "Item name needed" : this.clientItem.getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIcon() {
/* 30 */     return this.clientItem.getIcon();
/*    */   }
/*    */   
/*    */   public Set<PriceDesc> getPrice() {
/* 34 */     Set<PriceDesc> result = new HashSet<PriceDesc>();
/*    */     
/* 36 */     result.add(new PriceDesc() {
/*    */           public String getClassId() {
/* 38 */             return VendorModelItemVendorAdapter.this.vendorItem.getItemCostId();
/*    */           }
/*    */           
/*    */           public int getAmount() {
/* 42 */             return VendorModelItemVendorAdapter.this.vendorItem.getItemCostAmount();
/*    */           }
/*    */         });
/*    */     
/* 46 */     return result;
/*    */   }
/*    */   
/*    */   public boolean isWithinLevel(int level) {
/* 50 */     return (level >= this.vendorItem.getFromLevel() && level <= this.vendorItem.getToLevel());
/*    */   }
/*    */   
/*    */   public int getAvailableAmount() {
/* 54 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getItemAmount() {
/* 59 */     return this.vendorItem.getItemAmount();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getItemDescriptionText() {
/* 64 */     return this.vendorItem.getItemDescriptionText();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 69 */     return this.vendorItem.getTier();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getLevel() {
/* 74 */     return this.clientItem.getLevel();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSubscriberOnly() {
/* 79 */     return this.clientItem.isSubscriberOnly();
/*    */   }
/*    */   
/*    */   public ClientItem getClientItem() {
/* 83 */     return this.clientItem;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\vendor\VendorModelItemVendorAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */