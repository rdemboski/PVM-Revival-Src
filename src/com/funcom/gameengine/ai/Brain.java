package com.funcom.gameengine.ai;

import com.funcom.gameengine.Updated;

public interface Brain extends Updated {
  void addPerceptor(Perceptor paramPerceptor);
  
  void removePerceptor(Perceptor paramPerceptor);
  
  void setControlled(BrainControlled paramBrainControlled);
  
  BrainControlled getControlled();
  
  String getName();
  
  void notifyEvent(BrainEvent paramBrainEvent);
  
  void init();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\Brain.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */