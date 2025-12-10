/*    */ package com.funcom.commons.persistence;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractProcessor
/*    */   implements Processor
/*    */ {
/* 10 */   private ProcessorState currentState = ProcessorState.STARTED;
/*    */   private ProcessorFactory processorFactory;
/*    */   
/*    */   public void setProcessorFactory(ProcessorFactory processorFactory) {
/* 14 */     this.processorFactory = processorFactory;
/*    */   }
/*    */   
/*    */   public ProcessorFactory getProcessorFactory() {
/* 18 */     return this.processorFactory;
/*    */   }
/*    */   
/*    */   public final void finish() throws ProcessorException {
/* 22 */     checkProcessorState();
/* 23 */     stateFinished();
/*    */     
/*    */     try {
/* 26 */       checkData();
/* 27 */       processData();
/* 28 */     } catch (Exception e) {
/* 29 */       stateBroken();
/* 30 */       throw new ProcessorException(e, this);
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract void checkData() throws IllegalStateException, IllegalArgumentException;
/*    */   
/*    */   protected abstract void processData() throws Exception;
/*    */   
/*    */   private void stateFinished() {
/* 39 */     this.currentState = ProcessorState.FINISHED;
/*    */   }
/*    */   
/*    */   private void stateBroken() {
/* 43 */     this.currentState = ProcessorState.BROKEN;
/*    */   }
/*    */   
/*    */   protected final void checkProcessorState() {
/* 47 */     if (this.currentState.equals(ProcessorState.FINISHED)) throw new ProcessorException("Processor already finished!", this); 
/* 48 */     if (this.currentState.equals(ProcessorState.BROKEN)) throw new ProcessorException("Processor broken!", this); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\persistence\AbstractProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */