/*    */ package com.funcom.commons.jme.md5importer.exception;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidVersionException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 7440063515649141651L;
/*    */   private static final String message = "Invalid MD5 format version: ";
/*    */   
/*    */   public InvalidVersionException(int version) {
/* 21 */     super("Invalid MD5 format version: " + version);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-monkeycommon.jar!\com\funcom\commons\jme\md5importer\exception\InvalidVersionException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */