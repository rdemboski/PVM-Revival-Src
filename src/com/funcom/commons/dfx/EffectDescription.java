package com.funcom.commons.dfx;

public interface EffectDescription {
  double getStartTime();
  
  double getEndTime();
  
  String getResource();
  
  Effect createInstance(Object paramObject1, Object paramObject2);
  
  void setResourceFetcher(DireEffectResourceLoader paramDireEffectResourceLoader);
  
  DireEffectResourceLoader getResourceFetcher();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\EffectDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */