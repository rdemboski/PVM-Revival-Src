package com.funcom.gameengine.utils.parameterizer;

public interface Parameter {
  void setAsIntegerValue(int paramInt) throws ParameterRangeException;
  
  int getAsIntegerValue();
  
  int getMinimum();
  
  int getMaximum();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengin\\utils\parameterizer\Parameter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */