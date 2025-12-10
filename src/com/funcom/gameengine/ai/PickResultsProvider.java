package com.funcom.gameengine.ai;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.view.PropNode;
import java.util.Set;

public interface PickResultsProvider {
  Set<PropNode> getPointingProps();
  
  PropNode getTopPointingProp();
  
  void clearPickedData();
  
  WorldCoordinate getPointingCoordinate();
  
  boolean isGuiHit();
  
  PropNode getTopClickableProp();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\ai\PickResultsProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */