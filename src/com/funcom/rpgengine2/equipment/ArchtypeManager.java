/*    */ package com.funcom.rpgengine2.equipment;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArchtypeManager
/*    */ {
/* 10 */   private Map<String, ArchType> archTypes = new HashMap<String, ArchType>();
/*    */   
/*    */   public void addArchType(String id, ArchType type) {
/* 13 */     this.archTypes.put(id, type);
/*    */   }
/*    */   
/*    */   public ArchType getArchtypeForId(String id) {
/* 17 */     return this.archTypes.get(id);
/*    */   }
/*    */   
/*    */   public Map<String, ArchType> getArchTypes() {
/* 21 */     return this.archTypes;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 25 */     this.archTypes.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\equipment\ArchtypeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */