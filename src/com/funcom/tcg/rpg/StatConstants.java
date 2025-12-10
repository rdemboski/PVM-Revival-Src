/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public enum StatConstants
/*    */ {
/*  8 */   MOVEMENTSPEED("movement-speed"),
/*  9 */   HEALTH("health"),
/* 10 */   MANA("mana"),
/* 11 */   XP("xp"),
/* 12 */   READONLY_LEVEL("ro_level");
/*    */   
/*    */   private final String id;
/*    */   
/*    */   StatConstants(String id) {
/* 17 */     this.id = id;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 21 */     return this.id;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\StatConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */