package com.funcom.commons.persistence;

public interface Processor {
  void setProcessorFactory(ProcessorFactory paramProcessorFactory);
  
  void finish() throws ProcessorException;
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\persistence\Processor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */