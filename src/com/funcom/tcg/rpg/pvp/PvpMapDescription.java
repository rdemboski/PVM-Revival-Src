/*    */ package com.funcom.tcg.rpg.pvp;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PvpMapDescription
/*    */ {
/*    */   private String mapName;
/*    */   private int startLevelRange;
/*    */   private int minPlayers;
/*    */   private int maxPlayers;
/*    */   private PvpMapType type;
/*    */   
/*    */   public PvpMapDescription(String mapName, int startLevelRange, int minPlayers, int maxPlayers, PvpMapType type) {
/* 14 */     this.mapName = mapName;
/* 15 */     this.startLevelRange = startLevelRange;
/* 16 */     this.minPlayers = minPlayers;
/* 17 */     this.maxPlayers = maxPlayers;
/* 18 */     this.type = type;
/*    */   }
/*    */   
/*    */   public int getMinPlayers() {
/* 22 */     return this.minPlayers;
/*    */   }
/*    */   
/*    */   public int getMaxPlayers() {
/* 26 */     return this.maxPlayers;
/*    */   }
/*    */   
/*    */   public String getMapName() {
/* 30 */     return this.mapName;
/*    */   }
/*    */   
/*    */   public int getStartLevelRange() {
/* 34 */     return this.startLevelRange;
/*    */   }
/*    */   
/*    */   public PvpMapType getType() {
/* 38 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\pvp\PvpMapDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */