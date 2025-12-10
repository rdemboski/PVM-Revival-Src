/*    */ package com.funcom.commons.jme.md5importer.importer.resource.token;
/*    */ 
/*    */ 
/*    */ public class Md5StringToken
/*    */   extends Md5Token
/*    */ {
/*    */   private String value;
/*    */   
/*    */   public Md5StringToken(String value) {
/* 10 */     super(false);
/* 11 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getValue() {
/* 15 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\token\Md5StringToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */