/*    */ package com.funcom.tcg.rpg.pvp;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PvpMapManager
/*    */ {
/* 10 */   private HashMap<String, PvpMapDescription> pvpMaps = new HashMap<String, PvpMapDescription>();
/*    */   
/*    */   public boolean contains(String map) {
/* 13 */     return this.pvpMaps.containsKey(map);
/*    */   }
/*    */   
/*    */   public PvpMapDescription getDescription(String map) {
/* 17 */     return this.pvpMaps.get(map);
/*    */   }
/*    */   
/*    */   public void addDescription(PvpMapDescription desc) {
/* 21 */     this.pvpMaps.put(desc.getMapName(), desc);
/*    */   }
/*    */   
/*    */   public void clear() {
/* 25 */     this.pvpMaps.clear();
/*    */   }
/*    */   
/*    */   public Set<String> getMaps() {
/* 29 */     return this.pvpMaps.keySet();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\pvp\PvpMapManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */