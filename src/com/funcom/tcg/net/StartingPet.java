/*    */ package com.funcom.tcg.net;
/*    */ 
/*    */ public enum StartingPet {
/*  4 */   WOLF((byte)0), BEAR((byte)1), CAT((byte)2);
/*    */   
/*    */   private final byte id;
/*    */   
/*    */   StartingPet(byte id) {
/*  9 */     this.id = id;
/*    */   }
/*    */   
/*    */   public byte getId() {
/* 13 */     return this.id;
/*    */   }
/*    */   
/*    */   public boolean isValidPetClass(String petItemClassId) {
/* 17 */     return name().equalsIgnoreCase(petItemClassId);
/*    */   }
/*    */   
/*    */   public static StartingPet valueOfById(byte id) {
/* 21 */     for (StartingPet startingPet : values()) {
/* 22 */       if (startingPet.getId() == id) {
/* 23 */         return startingPet;
/*    */       }
/*    */     } 
/*    */     
/* 27 */     throw new IllegalArgumentException("unknown starting pet id: id=" + id);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\StartingPet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */