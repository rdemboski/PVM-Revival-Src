package com.funcom.rpgengine2.abilities.values;

import com.funcom.rpgengine2.StatEffect;

public interface Value {
  boolean isInteger();
  
  int getInt(StatEffect paramStatEffect);
  
  float getFloat(StatEffect paramStatEffect);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\values\Value.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */