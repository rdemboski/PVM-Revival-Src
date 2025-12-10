/*     */ package com.funcom.rpgengine2.waypoints;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class WaypointDescription
/*     */ {
/*     */   private String id;
/*     */   private String name;
/*     */   private String location;
/*     */   private String meshResource;
/*     */   private String dfxIdleResource;
/*     */   private WorldCoordinate worldCoordinate;
/*  25 */   private List<WaypointDestinationPortalDescription> destinationPortals = new ArrayList<WaypointDestinationPortalDescription>();
/*     */   private String dfxImpactResource;
/*     */   private double radius;
/*     */   private WorldCoordinate arrivalCoordinate;
/*     */   
/*     */   public WaypointDescription(String id, String name, String location, WorldCoordinate worldCoordinate, String meshResource, String dfxIdleResource, String dfxImpactResource, double radius, WorldCoordinate arrivalCoordinate) {
/*  31 */     this.id = id;
/*  32 */     this.name = name;
/*  33 */     this.location = location;
/*  34 */     this.worldCoordinate = worldCoordinate;
/*  35 */     this.meshResource = meshResource;
/*  36 */     this.dfxIdleResource = dfxIdleResource;
/*  37 */     this.dfxImpactResource = dfxImpactResource;
/*  38 */     this.radius = radius;
/*  39 */     this.arrivalCoordinate = arrivalCoordinate;
/*     */   }
/*     */   
/*     */   public void addWaypointDestinationPortalDescription(WaypointDestinationPortalDescription waypointDestinationPortalDescription) {
/*  43 */     this.destinationPortals.add(waypointDestinationPortalDescription);
/*     */   }
/*     */   
/*     */   public String getId() {
/*  47 */     return this.id;
/*     */   }
/*     */   
/*     */   public String getName() {
/*  51 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*     */   }
/*     */   
/*     */   public String getLocation() {
/*  55 */     return this.location;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getWorldCoordinate() {
/*  59 */     return this.worldCoordinate;
/*     */   }
/*     */   
/*     */   public List<WaypointDestinationPortalDescription> getDestinationPortals() {
/*  63 */     return this.destinationPortals;
/*     */   }
/*     */   
/*     */   public String getDfxImpactResource() {
/*  67 */     return this.dfxImpactResource;
/*     */   }
/*     */   
/*     */   public List<WaypointDestinationPortalDescription> getSortedDestinationPortalsByLevel() {
/*  71 */     List<WaypointDestinationPortalDescription> sortedList = new ArrayList<WaypointDestinationPortalDescription>(this.destinationPortals);
/*  72 */     Collections.sort(sortedList, new PortalByLevelComparator());
/*  73 */     return sortedList;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean doesWaypointDestinationPortalDescriptionExist(WaypointDestinationPortalDescription waypointDestinationPortalDescription) {
/*  78 */     for (WaypointDestinationPortalDescription destinationPortal : this.destinationPortals) {
/*  79 */       if (destinationPortal.getId().equalsIgnoreCase(waypointDestinationPortalDescription.getId())) {
/*  80 */         return true;
/*     */       }
/*     */     } 
/*  83 */     return false;
/*     */   }
/*     */   
/*     */   public WorldCoordinate getArrivalCoordinate() {
/*  87 */     return this.arrivalCoordinate;
/*     */   }
/*     */   
/*     */   public String getMeshResource() {
/*  91 */     return this.meshResource;
/*     */   }
/*     */   
/*     */   public String getDfxIdleResource() {
/*  95 */     return this.dfxIdleResource;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 100 */     return "WaypointDescription{id='" + this.id + '\'' + ", name='" + this.name + '\'' + ", location='" + this.location + '\'' + ", meshResource='" + this.meshResource + '\'' + ", dfxIdleResource='" + this.dfxIdleResource + '\'' + ", worldCoordinate=" + this.worldCoordinate + ", destinationPortals=" + this.destinationPortals + ", dfxImpactResource='" + this.dfxImpactResource + '\'' + ", radius=" + this.radius + '}';
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private class PortalByLevelComparator
/*     */     implements Comparator<WaypointDestinationPortalDescription>
/*     */   {
/*     */     private PortalByLevelComparator() {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public int compare(WaypointDestinationPortalDescription o1, WaypointDestinationPortalDescription o2) {
/* 116 */       int o1Level = o1.getRequiredLevel();
/* 117 */       int o2Level = o2.getRequiredLevel();
/* 118 */       if (o1Level < o2Level) {
/* 119 */         return -1;
/*     */       }
/*     */       
/* 122 */       if (o1Level == o2Level) {
/* 123 */         return o1.getName().compareTo(o2.getName());
/*     */       }
/*     */       
/* 126 */       return 1;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public double getRadius() {
/* 132 */     return this.radius;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\waypoints\WaypointDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */