/*   */ package com.funcom.gameengine.utils.parameterizer;
/*   */ 
/*   */ public class ParameterRangeException
/*   */   extends RuntimeException {
/*   */   public ParameterRangeException(int value, int minimum, int maximum) {
/* 6 */     super("Wrong value (" + value + "), allowed range: " + minimum + "/" + maximum);
/*   */   }
/*   */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\parameterizer\ParameterRangeException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */