package com.funcom.rpgengine2.creatures;

public interface BuffSupportable extends RpgUpdateable, BuffQueue {
  boolean isEmpty();
  
  void addBuffListener(BuffListener paramBuffListener);
  
  void removeBuffListener(BuffListener paramBuffListener);
  
  void reset();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\BuffSupportable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */