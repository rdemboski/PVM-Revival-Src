/*    */ package com.funcom.gameengine;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class RespawnPoints
/*    */ {
/* 16 */   private static RespawnPoints INSTANCE = new RespawnPoints();
/*    */   Map<String, WorldCoordinate> coordinateMap;
/*    */   
/*    */   public RespawnPoints() {
/* 20 */     this.coordinateMap = new HashMap<String, WorldCoordinate>();
/* 21 */     this.coordinateMap.put("vsgreen/", new WorldCoordinate(25, 131, 0.5D, 0.0D, "vsgreen/", 0));
/* 22 */     this.coordinateMap.put("vsred/", new WorldCoordinate(60, 422, 0.0D, 0.0D, "vsred/", 0));
/* 23 */     this.coordinateMap.put("vsqueenlair/", new WorldCoordinate(95, 58, 0.0D, 0.0D, "vsgreen/", 0));
/* 24 */     this.coordinateMap.put("testdungeon/", new WorldCoordinate(5, 5, 0.0D, 0.0D, "starttown/", 0));
/*    */   }
/*    */   
/*    */   public static RespawnPoints getINSTANCE() {
/* 28 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public WorldCoordinate getRespawnPoint(WorldCoordinate deathSpot) {
/* 32 */     WorldCoordinate worldCoordinate = this.coordinateMap.get(deathSpot.getMapId());
/* 33 */     if (worldCoordinate == null) {
/* 34 */       worldCoordinate = deathSpot;
/*    */     }
/* 36 */     return worldCoordinate;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\gameengine\RespawnPoints.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */