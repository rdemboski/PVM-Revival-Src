/*    */ package com.funcom.commons.persistence;
/*    */ 
/*    */ public class ProcessorException
/*    */   extends RuntimeException
/*    */ {
/*    */   private Processor processor;
/*    */   
/*    */   public ProcessorException(String message, Processor processor) {
/*  9 */     super(message);
/* 10 */     this.processor = processor;
/*    */   }
/*    */   
/*    */   public ProcessorException(String message, Throwable cause, Processor processor) {
/* 14 */     super(message, cause);
/* 15 */     this.processor = processor;
/*    */   }
/*    */   
/*    */   public ProcessorException(Throwable cause, Processor processor) {
/* 19 */     super(cause);
/* 20 */     this.processor = processor;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\persistence\ProcessorException.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */