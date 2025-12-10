/*    */ package com.funcom.commons.jme.md5importer.importer.resource.token;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Md5Token
/*    */ {
/*    */   private boolean number;
/*    */   
/*    */   protected Md5Token(boolean number) {
/* 10 */     this.number = number;
/*    */   }
/*    */   
/*    */   public boolean isNumber() {
/* 14 */     return this.number;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\token\Md5Token.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */