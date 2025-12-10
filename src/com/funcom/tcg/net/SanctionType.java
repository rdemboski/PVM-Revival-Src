/*    */ package com.funcom.tcg.net;
/*    */ 
/*    */ public enum SanctionType {
/*  4 */   NOTHING((byte)0),
/*  5 */   SILENCE((byte)1),
/*  6 */   KICK((byte)2),
/*  7 */   SUSPEND((byte)3),
/*  8 */   BAN((byte)4),
/*  9 */   WARN((byte)5);
/*    */   
/*    */   private final byte id;
/*    */   
/*    */   SanctionType(byte id) {
/* 14 */     this.id = id;
/*    */   }
/*    */   
/*    */   public byte getId() {
/* 18 */     return this.id;
/*    */   }
/*    */   
/*    */   public static SanctionType valueOfById(byte id) {
/* 22 */     for (SanctionType result : values()) {
/* 23 */       if (result.getId() == id) {
/* 24 */         return result;
/*    */       }
/*    */     } 
/*    */     
/* 28 */     throw new IllegalArgumentException("unknown id: id=" + id);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\SanctionType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */