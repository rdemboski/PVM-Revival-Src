/*    */ package com.funcom.rpgengine2.vendor;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VendorTypeDescription
/*    */ {
/* 10 */   private List<VendorItem> vendorItems = new ArrayList<VendorItem>();
/*    */   
/*    */   public void addVendorItem(VendorItem vendorItem) {
/* 13 */     this.vendorItems.add(vendorItem);
/*    */   }
/*    */   
/*    */   public List<VendorItem> getVendorItems() {
/* 17 */     return this.vendorItems;
/*    */   }
/*    */   
/*    */   public List<VendorItem> getClonedList() {
/* 21 */     List<VendorItem> tmp = new ArrayList<VendorItem>();
/* 22 */     for (VendorItem vendorItem : this.vendorItems) {
/* 23 */       if (vendorItem != null) {
/* 24 */         tmp.add(new VendorItem(vendorItem.getItemId(), vendorItem.getItemAmount(), vendorItem.getItemDescriptionText(), vendorItem.getTier(), vendorItem.getFromLevel(), vendorItem.getToLevel(), vendorItem.getItemCostId(), vendorItem.getItemCostAmount()));
/*    */       }
/*    */     } 
/* 27 */     return tmp;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 32 */     StringBuffer sb = new StringBuffer();
/* 33 */     sb.append("[").append("vendorItems=").append(this.vendorItems).append("]");
/*    */ 
/*    */     
/* 36 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\vendor\VendorTypeDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */