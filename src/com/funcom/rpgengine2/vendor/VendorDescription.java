/*    */ package com.funcom.rpgengine2.vendor;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VendorDescription
/*    */ {
/*    */   private VendorTypeDescription vendorType;
/*    */   private String id;
/*    */   private String name;
/*    */   private String zone;
/*    */   private String faction;
/*    */   private String model;
/*    */   private String vendorId;
/* 21 */   private double radius = -1.0D;
/*    */   
/*    */   public void setId(String id) {
/* 24 */     this.id = id;
/*    */   }
/*    */   
/*    */   public void setName(String name) {
/* 28 */     this.name = name;
/*    */   }
/*    */   
/*    */   public void setZone(String zone) {
/* 32 */     this.zone = zone;
/*    */   }
/*    */   
/*    */   public void setFaction(String faction) {
/* 36 */     this.faction = faction;
/*    */   }
/*    */   
/*    */   public void setModel(String model) {
/* 40 */     this.model = model;
/*    */   }
/*    */   
/*    */   public void setVendorId(String vendorId) {
/* 44 */     this.vendorId = vendorId;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 48 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 52 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*    */   }
/*    */   
/*    */   public String getZone() {
/* 56 */     return this.zone;
/*    */   }
/*    */   
/*    */   public String getFaction() {
/* 60 */     return this.faction;
/*    */   }
/*    */   
/*    */   public String getModel() {
/* 64 */     return this.model;
/*    */   }
/*    */   
/*    */   public String getVendorId() {
/* 68 */     return this.vendorId;
/*    */   }
/*    */   
/*    */   public VendorTypeDescription getVendorType() {
/* 72 */     return this.vendorType;
/*    */   }
/*    */   
/*    */   public void setVendorType(VendorTypeDescription vendorType) {
/* 76 */     this.vendorType = vendorType;
/*    */   }
/*    */   
/*    */   public double getRadius() {
/* 80 */     if (this.radius < 0.0D)
/* 81 */       throw new IllegalStateException("Radius not set!"); 
/* 82 */     return this.radius;
/*    */   }
/*    */   
/*    */   public void setRadius(double radius) {
/* 86 */     this.radius = radius;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\vendor\VendorDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */