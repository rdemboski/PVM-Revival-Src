/*     */ package com.funcom.rpgengine2.waypoints;
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.loader.DataRecords;

import java.util.Arrays;
import java.util.Collections;
/*     */ import java.util.HashMap;
import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class WaypointManager {
/*  11 */   private Map<String, WaypointDescription> waypointMap = new HashMap<String, WaypointDescription>();
/*  12 */   private Map<String, Set> destinationPortalsPerMap = new HashMap<String, Set>();
/*  13 */   private Map<String, WaypointDestinationPortalDescription> destinationPortalsMap = new HashMap<String, WaypointDestinationPortalDescription>();
/*     */   
/*     */   private void addWaypoint(WaypointDescription waypointDescription) {
/*  16 */     if (!this.waypointMap.containsKey(waypointDescription.getId())) {
/*  17 */       this.waypointMap.put(waypointDescription.getId(), waypointDescription);
/*     */     }
/*     */   }
/*     */   
/*     */   private void addWaypointDestinationPortalDescription(String id, WaypointDestinationPortalDescription waypointDestinationPortalDescription) {
/*  22 */     if (this.waypointMap.containsKey(id)) {
/*  23 */       WaypointDescription waypointDescription = this.waypointMap.get(id);
/*  24 */       if (!waypointDescription.doesWaypointDestinationPortalDescriptionExist(waypointDestinationPortalDescription)) {
/*  25 */         waypointDescription.addWaypointDestinationPortalDescription(waypointDestinationPortalDescription);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public Set<WaypointDestinationPortalDescription> getDestinationPortalsPerMap(String mapId) {
/*  31 */     return this.destinationPortalsPerMap.get(FileUtils.fixTailingSlashes(mapId));
/*     */   }
/*     */   
/*     */   public WaypointDescription getWaypoint(String id) {
/*  35 */     return this.waypointMap.get(id);
/*     */   }
/*     */   
/*     */   public Map<String, WaypointDescription> getWaypointMap() {
/*  39 */     return this.waypointMap;
/*     */   }
/*     */   
/*     */   private void addDestinationPortalPerMap(String mapId, WaypointDestinationPortalDescription waypointDestinationPortalDescription) {
/*  43 */     if (this.destinationPortalsPerMap.containsKey(FileUtils.fixTailingSlashes(mapId))) {
/*  44 */       Set<WaypointDestinationPortalDescription> portals = this.destinationPortalsPerMap.get(FileUtils.fixTailingSlashes(mapId));
/*  45 */       if (!portals.contains(waypointDestinationPortalDescription)) {
/*  46 */         portals.add(waypointDestinationPortalDescription);
/*     */       }
/*     */     } else {
/*     */       
/*  50 */       Set<WaypointDestinationPortalDescription> portals = new HashSet<WaypointDestinationPortalDescription>();
/*  51 */       portals.add(waypointDestinationPortalDescription);
/*  52 */       this.destinationPortalsPerMap.put(FileUtils.fixTailingSlashes(mapId), portals);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void addDestinationPortal(WaypointDestinationPortalDescription waypointDestinationPortalDescription) {
/*  57 */     this.destinationPortalsMap.put(waypointDestinationPortalDescription.getId(), waypointDestinationPortalDescription);
/*     */   }
/*     */   
/*     */   public WaypointDestinationPortalDescription getWaypointDestinationPortalDescription(String id) {
/*  61 */     return this.destinationPortalsMap.get(id);
/*     */   }
/*     */   
/*     */   public void createWaypoint(DataRecords dataRecords) {
/*  65 */     for (String[] fields : dataRecords.getWaypointFiles()) {
/*  66 */       int index = 0;
/*  67 */       String id = fields[index++];
/*  68 */       String name = fields[index++];
/*  69 */       String location = fields[index++];
/*  70 */       double xCoord = Double.parseDouble(fields[index++]);
/*  71 */       double yCoord = Double.parseDouble(fields[index++]);
/*  72 */       WorldCoordinate wc = new WorldCoordinate();
/*  73 */       wc.addOffset(xCoord, yCoord);
/*  74 */       wc.setMapId(location);
/*  75 */       double arrivalOffsetX = Double.parseDouble(fields[index++]);
/*  76 */       double arrivalOffsetY = Double.parseDouble(fields[index++]);
/*  77 */       WorldCoordinate arrivalCoordinate = wc.clone();
/*  78 */       arrivalCoordinate.addOffset(arrivalOffsetX, arrivalOffsetY);
/*  79 */       String meshResource = fields[index++];
/*  80 */       String dfxIdleResource = fields[index++];
/*  81 */       String dfxImpactResource = fields[index++];
/*     */ 
/*     */       
/*  84 */       WaypointDescription waypointDescription = new WaypointDescription(id, name, location, wc, meshResource, dfxIdleResource, dfxImpactResource, 0.6000000238418579D, arrivalCoordinate);
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
/*  96 */       addWaypoint(waypointDescription);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void createWaypointDestinationPortal(DataRecords dataRecords) {
/* 101 */     for (String[] fields : dataRecords.getWaypointDestinationPortals()) {
/* 102 */       int index = 0;
/* 103 */       String id = fields[index++];
/* 104 */       String name = fields[index++];
/* 105 */       String map = fields[index++];
/* 106 */       int x = Integer.parseInt(fields[index++]);
/* 107 */       int y = Integer.parseInt(fields[index++]);
/* 108 */       WorldCoordinate worldCoordinate = new WorldCoordinate();
/* 109 */       worldCoordinate.addOffset(x, y);
/* 110 */       worldCoordinate.setMapId(map);
/* 111 */       double arrivalOffsetX = Double.parseDouble(fields[index++]);
/* 112 */       double arrivalOffsetY = Double.parseDouble(fields[index++]);
/* 113 */       WorldCoordinate arrivalCoordinate = worldCoordinate.clone();
/* 114 */       arrivalCoordinate.addOffset(arrivalOffsetX, arrivalOffsetY);
/* 115 */       int proximityHeight = Integer.parseInt(fields[index++]);
/* 116 */       int proximityWidth = Integer.parseInt(fields[index++]);
/* 117 */       int requiredLevel = Integer.parseInt(fields[index++]);
/* 118 */       String keys = fields[index++];
/* 119 */       boolean subscriptionRequired = Boolean.parseBoolean(fields[index++]);
/* 120 */       String waypointId = fields[index++];
/* 121 */       String meshResource = fields[index++];
/* 122 */       String idleDFX = fields[index++];
/* 123 */       String activationDfx = fields[index++];
/* 124 */       String impactDFX = fields[index++];
/* 125 */       String mapImagePath = fields[index++];
/* 126 */       boolean autoUnlock = Boolean.valueOf(fields[index++]).booleanValue();
/*     */       
/* 128 */       List<String> accessKeys = Collections.unmodifiableList(Arrays.asList(keys.split(".")));
/*     */       
/* 130 */       WaypointDestinationPortalDescription waypointDestinationPortalDescription = new WaypointDestinationPortalDescription(id, name, worldCoordinate, proximityHeight, proximityWidth, requiredLevel, waypointId, meshResource, idleDFX, activationDfx, impactDFX, mapImagePath, autoUnlock, 0.6000000238418579D, arrivalCoordinate, subscriptionRequired, accessKeys);
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
/* 149 */       addWaypointDestinationPortalDescription(waypointId, waypointDestinationPortalDescription);
/* 150 */       addDestinationPortalPerMap(FileUtils.fixTailingSlashes(map), waypointDestinationPortalDescription);
/* 151 */       addDestinationPortal(waypointDestinationPortalDescription);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void clear() {
/* 157 */     this.waypointMap.clear();
/* 158 */     this.destinationPortalsMap.clear();
/* 159 */     this.destinationPortalsPerMap.clear();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\waypoints\WaypointManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */