/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface RpgCreatureConstants
/*    */ {
/*    */   public enum Type
/*    */   {
/* 10 */     LOOT_CREATURE_TYPE_ID(0),
/* 11 */     PLAYER_CREATURE_TYPE_ID(1),
/* 12 */     MONSTER_CREATURE_TYPE_ID(-1),
/* 13 */     VENDOR_TYPE_ID(-2),
/* 14 */     WALK_OVER_LOOT_CREATURE_TYPE(-3),
/* 15 */     TOWN_PORTAL(-4),
/* 16 */     WAYPOINT_DESTINATION_PORTAL(-5),
/* 17 */     WAYPOINT(-6),
/* 18 */     RETURN_POINT(-7);
/*    */     
/*    */     private final int typeId;
/*    */     
/*    */     Type(int typeId) {
/* 23 */       this.typeId = typeId;
/*    */     }
/*    */     
/*    */     public int getTypeId() {
/* 27 */       return this.typeId;
/*    */     }
/*    */     
/*    */     public static Type valueById(int id) {
/* 31 */       for (int i = 0; i <= (values()).length; i++) {
/* 32 */         Type type = values()[i];
/* 33 */         if (type.typeId == id) {
/* 34 */           return type;
/*    */         }
/*    */       } 
/* 37 */       throw new IllegalArgumentException("unknown " + RpgCreatureConstants.class.getSimpleName() + ": id=" + id);
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\RpgCreatureConstants.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */