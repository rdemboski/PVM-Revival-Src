/*     */ package com.funcom.rpgengine2.pickupitems;
/*     */ 
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ 
/*     */ public abstract class AbstractPickUpDescription {
/*     */   private String id;
/*     */   private boolean use;
/*     */   private float triggerDistance;
/*     */   private int amount;
/*     */   private String mesh;
/*     */   private String spawnDFX;
/*     */   private String pickUpDFX;
/*     */   private String idleDFX;
/*     */   private boolean splittable;
/*     */   private String splitGroupID;
/*     */   private PickupType associatedType;
/*     */   
/*     */   public String getId() {
/*  19 */     return this.id;
/*     */   }
/*     */   
/*     */   public boolean isUse() {
/*  23 */     return this.use;
/*     */   }
/*     */   
/*     */   public float getTriggerDistance() {
/*  27 */     return this.triggerDistance;
/*     */   }
/*     */   
/*     */   public int getAmount() {
/*  31 */     return this.amount;
/*     */   }
/*     */   
/*     */   public String getMesh() {
/*  35 */     return this.mesh;
/*     */   }
/*     */   
/*     */   public String getSpawnDFX() {
/*  39 */     return this.spawnDFX;
/*     */   }
/*     */   
/*     */   public String getPickUpDFX() {
/*  43 */     return this.pickUpDFX;
/*     */   }
/*     */   
/*     */   public String getIdleDFX() {
/*  47 */     return this.idleDFX;
/*     */   }
/*     */   
/*     */   public void setId(String id) {
/*  51 */     this.id = id;
/*     */   }
/*     */   
/*     */   public void setUse(boolean use) {
/*  55 */     this.use = use;
/*     */   }
/*     */   
/*     */   public void setTriggerDistance(float triggerDistance) {
/*  59 */     this.triggerDistance = triggerDistance;
/*     */   }
/*     */   
/*     */   public boolean isTriggerByDistance() {
/*  63 */     return (this.triggerDistance > 0.0F);
/*     */   }
/*     */   
/*     */   public void setAmount(int amount) {
/*  67 */     this.amount = amount;
/*     */   }
/*     */   
/*     */   public void setMesh(String mesh) {
/*  71 */     this.mesh = mesh;
/*     */   }
/*     */   
/*     */   public void setSpawnDFX(String spawnDFX) {
/*  75 */     this.spawnDFX = spawnDFX;
/*     */   }
/*     */   
/*     */   public void setPickUpDFX(String pickUpDFX) {
/*  79 */     this.pickUpDFX = pickUpDFX;
/*     */   }
/*     */   
/*     */   public void setIdleDFX(String idleDFX) {
/*  83 */     this.idleDFX = idleDFX;
/*     */   }
/*     */   
/*     */   public boolean isSplittable() {
/*  87 */     return this.splittable;
/*     */   }
/*     */   
/*     */   public void setSplittable(Boolean splittable) {
/*  91 */     this.splittable = splittable.booleanValue();
/*     */   }
/*     */   
/*     */   public void setSplitGroupID(String splitGroupID) {
/*  95 */     this.splitGroupID = splitGroupID;
/*     */   }
/*     */   
/*     */   public String getSplitGroupID() {
/*  99 */     return this.splitGroupID;
/*     */   }
/*     */   
/*     */   public void setAssociatedType(PickupType associatedType) {
/* 103 */     this.associatedType = associatedType;
/*     */   }
/*     */   
/*     */   public PickupType getAssociatedType() {
/* 107 */     return this.associatedType;
/*     */   }
/*     */   
/*     */   public abstract int getTier();
/*     */   
/*     */   public abstract void setAssociatedData(RpgLoader paramRpgLoader, String paramString, int paramInt);
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pickupitems\AbstractPickUpDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */