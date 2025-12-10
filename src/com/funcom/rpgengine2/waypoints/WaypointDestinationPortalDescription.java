/*     */ package com.funcom.rpgengine2.waypoints;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaypointDestinationPortalDescription
/*     */ {
/*     */   private String id;
/*     */   private String name;
/*     */   private WorldCoordinate worldCoordinate;
/*     */   private int proximityHeight;
/*     */   private int proximityWidth;
/*     */   private String waypointId;
/*     */   private String meshResource;
/*     */   private String idleDFX;
/*     */   private String activationDfx;
/*     */   private String impactDfx;
/*     */   private String mapImagePath;
/*     */   private boolean autoUnlock;
/*     */   private double radius;
/*     */   private WorldCoordinate arrivalCoordinate;
/*     */   private int requiredLevel;
/*     */   private boolean subscriptionRequired;
/*     */   private List<String> accessKeys;
/*     */   
/*     */   public WaypointDestinationPortalDescription(String id, String name, WorldCoordinate worldCoordinate, int proximityHeight, int proximityWidth, int requiredLevel, String waypointId, String meshResource, String idleDFX, String activationDfx, String impactDfx, String mapImagePath, boolean autoUnlock, double radius, WorldCoordinate arrivalCoordinate, boolean subscriptionRequired, List<String> accessKeys) {
/*  35 */     this.id = id;
/*  36 */     this.name = name;
/*  37 */     this.worldCoordinate = worldCoordinate;
/*  38 */     this.proximityHeight = proximityHeight;
/*  39 */     this.proximityWidth = proximityWidth;
/*  40 */     this.requiredLevel = requiredLevel;
/*  41 */     this.waypointId = waypointId;
/*  42 */     this.meshResource = meshResource;
/*  43 */     this.idleDFX = idleDFX;
/*  44 */     this.activationDfx = activationDfx;
/*  45 */     this.impactDfx = impactDfx;
/*  46 */     this.mapImagePath = mapImagePath;
/*  47 */     this.autoUnlock = autoUnlock;
/*  48 */     this.radius = radius;
/*  49 */     this.arrivalCoordinate = arrivalCoordinate;
/*  50 */     this.subscriptionRequired = subscriptionRequired;
/*  51 */     this.accessKeys = accessKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getId() {
/*  56 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  60 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*     */   }
/*     */   
/*     */   public WorldCoordinate getWorldCoordinate() {
/*  64 */     return this.worldCoordinate;
/*     */   }
/*     */   
/*     */   public int getProximityHeight() {
/*  68 */     return this.proximityHeight;
/*     */   }
/*     */   
/*     */   public int getProximityWidth() {
/*  72 */     return this.proximityWidth;
/*     */   }
/*     */   
/*     */   public int getRequiredLevel() {
/*  76 */     return this.requiredLevel;
/*     */   }
/*     */   
/*     */   public String getWaypointId() {
/*  80 */     return this.waypointId;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIdleDFX() {
/*  85 */     return this.idleDFX;
/*     */   }
/*     */   
/*     */   public String getMeshResource() {
/*  89 */     return this.meshResource;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMapImagePath() {
/*  94 */     return this.mapImagePath;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAutoUnlock() {
/*  99 */     return this.autoUnlock;
/*     */   }
/*     */   
/*     */   public void setAutoUnlock(boolean autoUnlock) {
/* 103 */     this.autoUnlock = autoUnlock;
/*     */   }
/*     */   
/*     */   public String getActivationDfx() {
/* 107 */     return this.activationDfx;
/*     */   }
/*     */   
/*     */   public String getImpactDfx() {
/* 111 */     return this.impactDfx;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getArrivalCoordinate() {
/* 115 */     return this.arrivalCoordinate;
/*     */   }
/*     */   
/*     */   public List<String> getAccessKeys() {
/* 119 */     return this.accessKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 124 */     return "WaypointDestinationPortalDescription{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", worldCoordinate=" + this.worldCoordinate + ", proximityHeight=" + this.proximityHeight + ", proximityWidth=" + this.proximityWidth + ", waypointId='" + this.waypointId + '\'' + ", meshResource='" + this.meshResource + '\'' + ", idleDFX='" + this.idleDFX + '\'' + ", activationDfx='" + this.activationDfx + '\'' + ", impactDfx='" + this.impactDfx + '\'' + ", mapImagePath='" + this.mapImagePath + '\'' + ", autoUnlock=" + this.autoUnlock + ", radius=" + this.radius + ", arrivalCoordinate=" + this.arrivalCoordinate + ", requiredLevel=" + this.requiredLevel + ", subscriptionRequired=" + this.subscriptionRequired + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public double getRadius() {
/* 145 */     return this.radius;
/*     */   }
/*     */   
/*     */   public boolean isSubscriptionRequired() {
/* 149 */     return this.subscriptionRequired;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\waypoints\WaypointDestinationPortalDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */