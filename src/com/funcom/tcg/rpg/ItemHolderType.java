/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ 
/*    */ public enum ItemHolderType
/*    */ {
/*  6 */   INVENTORY(0),
/*  7 */   EQUIPMENT_DOLL(1),
/*  8 */   LOOT_BAG(2),
/*  9 */   SKILLBAR(3); private static ItemHolderType[] TYPES;
/*    */   static {
/* 11 */     TYPES = values();
/*    */   }
/*    */   private final int id;
/*    */   
/*    */   ItemHolderType(int id) {
/* 16 */     this.id = id;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 20 */     return this.id;
/*    */   }
/*    */   
/*    */   public static ItemHolderType valueById(int containerTypeId) {
/* 24 */     for (int i = TYPES.length - 1; i >= 0; i--) {
/* 25 */       ItemHolderType type = TYPES[i];
/* 26 */       if (type.getId() == containerTypeId) {
/* 27 */         return type;
/*    */       }
/*    */     } 
/* 30 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\ItemHolderType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */