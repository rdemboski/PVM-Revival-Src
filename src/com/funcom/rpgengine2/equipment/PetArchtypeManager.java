/*    */ package com.funcom.rpgengine2.equipment;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PetArchtypeManager
/*    */ {
/* 10 */   private Map<String, PetArchType> archTypes = new HashMap<String, PetArchType>();
/*    */   
/*    */   public void addArchType(String id, PetArchType type) {
/* 13 */     this.archTypes.put(id, type);
/*    */   }
/*    */   
/*    */   public PetArchType getArchtypeForId(String id) {
/* 17 */     return this.archTypes.get(id);
/*    */   }
/*    */   
/*    */   public Map<String, PetArchType> getArchTypes() {
/* 21 */     return this.archTypes;
/*    */   }
/*    */   
/*    */   public void clear() {
/* 25 */     this.archTypes.clear();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\equipment\PetArchtypeManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */