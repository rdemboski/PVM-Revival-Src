/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ 
/*    */ public class NoSuchDFXException
/*    */   extends Exception
/*    */ {
/*    */   private final String dfxScript;
/*    */   
/*    */   public NoSuchDFXException(String dfxScript) {
/* 10 */     super("Cannot find dfx: dfxScript=" + dfxScript);
/* 11 */     this.dfxScript = dfxScript;
/*    */   }
/*    */   
/*    */   public String getDFXScript() {
/* 15 */     return this.dfxScript;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\NoSuchDFXException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */