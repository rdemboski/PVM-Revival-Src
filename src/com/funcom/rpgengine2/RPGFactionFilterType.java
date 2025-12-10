/*    */ package com.funcom.rpgengine2;
/*    */ 
/*    */ public enum RPGFactionFilterType {
/*  4 */   OWN, OTHER, ALL, OWN_EXCEPT_SELF, ALL_EXCEPT_SELF;
/*    */ 
/*    */   
/*    */   public static RPGFactionFilterType getFactionEnum(String enumName) {
/*  8 */     RPGFactionFilterType[] rpgFactionFilterTypes = values();
/*  9 */     for (RPGFactionFilterType rpgFactionFilterType : rpgFactionFilterTypes) {
/* 10 */       if (rpgFactionFilterType.name().equalsIgnoreCase(enumName)) {
/* 11 */         return rpgFactionFilterType;
/*    */       }
/*    */     } 
/* 14 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\RPGFactionFilterType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */