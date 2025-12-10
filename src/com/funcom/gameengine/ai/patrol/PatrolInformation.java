/*    */ package com.funcom.gameengine.ai.patrol;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PatrolInformation
/*    */ {
/* 10 */   private Map<String, Map<String, Patrol>> patrols = new HashMap<String, Map<String, Patrol>>();
/*    */   private static PatrolInformation INSTANCE;
/*    */   
/*    */   public static PatrolInformation instance() {
/* 14 */     if (INSTANCE == null) {
/* 15 */       INSTANCE = new PatrolInformation();
/*    */     }
/* 17 */     return INSTANCE;
/*    */   }
/*    */   
/*    */   public void addPatrol(String map, Patrol patrol) {
/* 21 */     Map<String, Patrol> patrolsForMap = this.patrols.get(map);
/* 22 */     if (patrolsForMap == null) {
/* 23 */       patrolsForMap = new HashMap<String, Patrol>();
/* 24 */       this.patrols.put(map, patrolsForMap);
/*    */     } 
/*    */     
/* 27 */     patrolsForMap.put(patrol.getName(), patrol);
/*    */   }
/*    */   
/*    */   public Patrol getPatrol(String map, String patrolName) {
/* 31 */     Map<String, Patrol> patrolsForMap = this.patrols.get(map);
/*    */     
/* 33 */     return (patrolsForMap == null) ? null : patrolsForMap.get(patrolName);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\patrol\PatrolInformation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */