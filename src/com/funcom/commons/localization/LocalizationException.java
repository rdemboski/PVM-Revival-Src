/*    */ package com.funcom.commons.localization;
/*    */ 
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ 
/*    */ public class LocalizationException
/*    */   extends Exception
/*    */ {
/*    */   public LocalizationException(String message) {
/* 10 */     super(message);
/*    */   }
/*    */   
/*    */   public LocalizationException(String message, SQLException cause) {
/* 14 */     super(message, cause);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\localization\LocalizationException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */