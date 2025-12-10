package com.funcom.gameengine.model.command;

import com.funcom.gameengine.WorldCoordinate;

public interface Destination {
  WorldCoordinate getTarget();
  
  boolean isAngleDependent();
  
  double getPreferredAngle();
  
  boolean isAtDestination(double paramDouble);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\Destination.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */