package com.funcom.commons.persistence;

public interface ProcessorFactory {
  <E extends Processor> E createProcessor(Class<E> paramClass);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\persistence\ProcessorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */