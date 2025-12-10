package com.funcom.gameengine.input;

@Deprecated
public interface InputSource {
  void addListener(InputSourceListener paramInputSourceListener);
  
  void removeListener(InputSourceListener paramInputSourceListener);
  
  void update(float paramFloat);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\InputSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */