/*    */ package com.funcom.gameengine.model;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.HashSet;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TriggeredSpawnPointsInformation
/*    */ {
/* 12 */   private Map<String, Map<Integer, SpawnPoint>> spawnPoints = new HashMap<String, Map<Integer, SpawnPoint>>();
/* 13 */   private int maxTriggerId = 0;
/*    */   private static TriggeredSpawnPointsInformation INSTANCE;
/*    */   
/*    */   public static TriggeredSpawnPointsInformation instance() {
/* 17 */     if (INSTANCE == null) {
/* 18 */       INSTANCE = new TriggeredSpawnPointsInformation();
/*    */     }
/* 20 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 24 */     this.maxTriggerId = 0;
/* 25 */     this.spawnPoints.clear();
/*    */   }
/*    */   
/*    */   public void addSpawnPoint(String map, SpawnPoint spawnPoint) {
/* 29 */     addSpawnPoint(map, spawnPoint.getTriggerId(), spawnPoint);
/*    */   }
/*    */   
/*    */   public void addSpawnPoint(String map, int id, SpawnPoint spawnPoint) {
/* 33 */     Map<Integer, SpawnPoint> spawnpointsForMap = this.spawnPoints.get(map);
/* 34 */     if (spawnpointsForMap == null) {
/* 35 */       spawnpointsForMap = new HashMap<Integer, SpawnPoint>();
/* 36 */       this.spawnPoints.put(map, spawnpointsForMap);
/*    */     } 
/*    */     
/* 39 */     spawnpointsForMap.put(Integer.valueOf(id), spawnPoint);
/*    */   }
/*    */   
/*    */   public void removeSpawnPoint(String map, int id) {
/* 43 */     Map<Integer, SpawnPoint> spawnpointsForMap = this.spawnPoints.get(map);
/* 44 */     if (spawnpointsForMap != null) {
/* 45 */       spawnpointsForMap.remove(Integer.valueOf(id));
/*    */     }
/*    */   }
/*    */   
/*    */   public SpawnPoint getSpawnPoint(String map, int id) {
/* 50 */     Map<Integer, SpawnPoint> spawnpointsForMap = this.spawnPoints.get(map);
/*    */     
/* 52 */     return (spawnpointsForMap == null) ? null : spawnpointsForMap.get(Integer.valueOf(id));
/*    */   }
/*    */   
/*    */   public Set<Integer> getIdsList(String map) {
/* 56 */     if (this.spawnPoints.get(map) != null)
/* 57 */       return ((Map)this.spawnPoints.get(map)).keySet(); 
/* 58 */     return new HashSet<Integer>();
/*    */   }
/*    */   
/*    */   public void setMaxTriggerId(int maxTriggerId) {
/* 62 */     this.maxTriggerId = maxTriggerId;
/*    */   }
/*    */   
/*    */   public int getMaxTriggerId() {
/* 66 */     return this.maxTriggerId;
/*    */   }
/*    */   
/*    */   public int getNextTriggerId() {
/* 70 */     this.maxTriggerId++;
/* 71 */     return this.maxTriggerId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\TriggeredSpawnPointsInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */