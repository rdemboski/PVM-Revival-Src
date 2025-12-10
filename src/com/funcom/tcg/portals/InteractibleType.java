/*    */ package com.funcom.tcg.portals;
/*    */ 
/*    */ 
/*    */ public enum InteractibleType
/*    */ {
/*  6 */   TELEPORT((byte)1),
/*  7 */   TOWN_PORTAL((byte)2),
/*  8 */   RETURN_POINT((byte)3),
/*  9 */   DESTINATION_PORTAL((byte)4),
/* 10 */   WAYPOINT((byte)5),
/* 11 */   CUSTOM_PORTAL((byte)6),
/* 12 */   QUEST_ITEM((byte)7); private static final InteractibleType[] TYPEs;
/*    */   static {
/* 14 */     TYPEs = values();
/*    */   }
/*    */   public final byte id;
/*    */   
/*    */   InteractibleType(byte id) {
/* 19 */     this.id = id;
/*    */   }
/*    */   
/*    */   public static InteractibleType valueById(byte id) {
/* 23 */     for (int i = 0; i <= TYPEs.length; i++) {
/* 24 */       InteractibleType type = TYPEs[i];
/* 25 */       if (type.id == id) {
/* 26 */         return type;
/*    */       }
/*    */     } 
/* 29 */     throw new IllegalArgumentException("unknown " + InteractibleType.class.getSimpleName() + ": id=" + id);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\portals\InteractibleType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */