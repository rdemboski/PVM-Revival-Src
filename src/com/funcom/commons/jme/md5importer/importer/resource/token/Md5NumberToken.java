/*    */ package com.funcom.commons.jme.md5importer.importer.resource.token;
/*    */ 
/*    */ 
/*    */ public class Md5NumberToken
/*    */   extends Md5Token
/*    */ {
/*    */   private double value;
/*    */   
/*    */   public Md5NumberToken(double value) {
/* 10 */     super(true);
/* 11 */     this.value = value;
/*    */   }
/*    */   
/*    */   public double getValue() {
/* 15 */     return this.value;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\importer\resource\token\Md5NumberToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */