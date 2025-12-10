/*    */ package com.funcom.rpgengine2.vendor;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VendorManager
/*    */ {
/* 13 */   private Map<String, VendorDescription> vendorDescriptions = new HashMap<String, VendorDescription>();
/* 14 */   private Map<String, VendorTypeDescription> vendorTypeDescriptions = new HashMap<String, VendorTypeDescription>();
/*    */   
/*    */   public void clearData() {
/* 17 */     this.vendorDescriptions.clear();
/* 18 */     this.vendorTypeDescriptions.clear();
/*    */   }
/*    */   
/*    */   public void addVendorDescription(String[] vendorDescriptions) {
/* 22 */     String id = vendorDescriptions[0];
/* 23 */     String name = vendorDescriptions[1];
/* 24 */     String zone = vendorDescriptions[2];
/* 25 */     String faction = vendorDescriptions[3];
/* 26 */     String model = vendorDescriptions[4];
/* 27 */     String vendorId = vendorDescriptions[5];
/* 28 */     double vendorRadius = Double.parseDouble(vendorDescriptions[6]);
/* 29 */     if (!this.vendorTypeDescriptions.containsKey(vendorId)) {
/* 30 */       VendorTypeDescription vendorTypeDescription = new VendorTypeDescription();
/* 31 */       this.vendorTypeDescriptions.put(vendorId, vendorTypeDescription);
/*    */     } 
/*    */     
/* 34 */     VendorDescription vendorDescription = new VendorDescription();
/* 35 */     vendorDescription.setId(id);
/* 36 */     vendorDescription.setName(name);
/* 37 */     vendorDescription.setZone(zone);
/* 38 */     vendorDescription.setFaction(faction);
/* 39 */     vendorDescription.setModel(model);
/* 40 */     vendorDescription.setVendorId(vendorId);
/* 41 */     vendorDescription.setVendorType(this.vendorTypeDescriptions.get(vendorId));
/* 42 */     vendorDescription.setRadius(vendorRadius);
/*    */     
/* 44 */     this.vendorDescriptions.put(id, vendorDescription);
/*    */   }
/*    */   
/*    */   public void addVendorItemDescription(String[] vendorItemDescriptions) {
/* 48 */     String vendorId = vendorItemDescriptions[0];
/* 49 */     String itemId = vendorItemDescriptions[1];
/* 50 */     int itemAmount = Integer.parseInt(vendorItemDescriptions[2].isEmpty() ? "1" : vendorItemDescriptions[2]);
/* 51 */     String itemDescription = vendorItemDescriptions[3];
/* 52 */     int tier = Integer.parseInt(vendorItemDescriptions[4]);
/* 53 */     int fromLevel = Integer.parseInt(vendorItemDescriptions[5]);
/* 54 */     int toLevel = Integer.parseInt(vendorItemDescriptions[6]);
/* 55 */     String itemCostId = vendorItemDescriptions[7];
/* 56 */     int itemCostAmount = Integer.parseInt(vendorItemDescriptions[8]);
/*    */     
/* 58 */     if (this.vendorTypeDescriptions.containsKey(vendorId)) {
/* 59 */       ((VendorTypeDescription)this.vendorTypeDescriptions.get(vendorId)).addVendorItem(new VendorItem(itemId, itemAmount, itemDescription, tier, fromLevel, toLevel, itemCostId, itemCostAmount));
/*    */     } else {
/*    */       
/* 62 */       VendorTypeDescription vendorTypeDescription = new VendorTypeDescription();
/* 63 */       vendorTypeDescription.addVendorItem(new VendorItem(itemId, itemAmount, itemDescription, tier, fromLevel, toLevel, itemCostId, itemCostAmount));
/* 64 */       this.vendorTypeDescriptions.put(vendorId, vendorTypeDescription);
/*    */     } 
/*    */   }
/*    */   
/*    */   public VendorDescription getVendorDescription(String id) {
/* 69 */     return this.vendorDescriptions.get(id);
/*    */   }
/*    */   
/*    */   public Set<String> getVendorIds() {
/* 73 */     return this.vendorDescriptions.keySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\vendor\VendorManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */