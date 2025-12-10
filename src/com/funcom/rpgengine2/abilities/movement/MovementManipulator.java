package com.funcom.rpgengine2.abilities.movement;

import com.funcom.rpgengine2.abilities.SourceProvider;

public interface MovementManipulator {
  MovementManipulatorCreator getParent();
  
  SourceProvider getSourceProvider();
  
  boolean update(long paramLong);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\movement\MovementManipulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */