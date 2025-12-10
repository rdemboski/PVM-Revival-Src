package com.funcom.gameengine.input;

import com.jme.input.KeyInputListener;
import com.jme.input.MouseInputListener;

@Deprecated
public interface InputSourceListener extends MouseInputListener, KeyInputListener {
  void onUpdate(float paramFloat);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\input\InputSourceListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */