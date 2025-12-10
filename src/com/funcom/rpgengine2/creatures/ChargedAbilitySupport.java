/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class ChargedAbilitySupport
/*    */   implements RpgQueryableSupport
/*    */ {
/* 10 */   private Map<String, Integer> chargesMap = new HashMap<String, Integer>();
/*    */   
/*    */   public void incrementCharge(String abilityId) {
/* 13 */     Integer charges = this.chargesMap.get(abilityId);
/* 14 */     if (charges == null) {
/* 15 */       this.chargesMap.put(abilityId, Integer.valueOf(1));
/*    */     } else {
/* 17 */       this.chargesMap.put(abilityId, Integer.valueOf(charges.intValue() + 1));
/*    */     } 
/*    */   }
/*    */   
/*    */   public void resetCharges(String abilityId) {
/* 22 */     this.chargesMap.put(abilityId, Integer.valueOf(0));
/*    */   }
/*    */   
/*    */   public int getCharges(String abilityId) {
/* 26 */     Integer charges = this.chargesMap.get(abilityId);
/* 27 */     return (charges == null) ? 0 : charges.intValue();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\ChargedAbilitySupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */