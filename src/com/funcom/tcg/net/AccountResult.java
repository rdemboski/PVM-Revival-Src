/*    */ package com.funcom.tcg.net;
/*    */ 
/*    */ public enum AccountResult {
/*  4 */   OK((byte)0),
/*  5 */   ERROR_INVALID_NICK((byte)-1),
/*  6 */   ERROR_INVALID_NICK_OR_PWD((byte)-2),
/*  7 */   ERROR_INVALID_INPUT_DATA((byte)-3),
/*  8 */   ERROR_SERVER_BUSY((byte)-4),
/*  9 */   ERROR_SERVER_NOT_FOUND((byte)-5),
/* 10 */   ERROR_CLIENT_SIDE((byte)-6),
/* 11 */   ERROR_SERVER_SIDE((byte)-7),
/* 12 */   ERROR_NEED_CREATE_CHARACTER((byte)-8),
/* 13 */   ERROR_CREATION_DISABLED((byte)-9),
/* 14 */   ERROR_IP_BLACKLISTED((byte)-10),
/* 15 */   ERROR_BANNED((byte)-11);
/*    */   
/*    */   private final byte id;
/*    */   
/*    */   AccountResult(byte id) {
/* 20 */     this.id = id;
/*    */   }
/*    */   
/*    */   public byte getId() {
/* 24 */     return this.id;
/*    */   }
/*    */   
/*    */   public static AccountResult valueOfById(byte id) {
/* 28 */     for (AccountResult result : values()) {
/* 29 */       if (result.getId() == id) {
/* 30 */         return result;
/*    */       }
/*    */     } 
/*    */     
/* 34 */     throw new IllegalArgumentException("unknown id: id=" + id);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\net\AccountResult.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */