/*   */ package com.funcom.errorhandling;
/*   */ 
/*   */ public abstract class AbstractCrashDataProvider
/*   */   implements CrashDataProvider
/*   */ {
/*   */   public String getValueAsString() {
/* 7 */     return (getValue() != null) ? getValue().toString() : "null";
/*   */   }
/*   */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\errorhandling\AbstractCrashDataProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */