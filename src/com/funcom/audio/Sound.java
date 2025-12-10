package com.funcom.audio;

public interface Sound {
  void play();
  
  void stop();
  
  Vector3f getPos();
  
  boolean isFinished();
  
  void disposeWhenFinished();
  
  void preInit();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\audio\Sound.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */