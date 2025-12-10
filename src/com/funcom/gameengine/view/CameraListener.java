package com.funcom.gameengine.view;

import com.funcom.gameengine.WorldCoordinate;

public interface CameraListener {
  void cameraMoved(WorldCoordinate paramWorldCoordinate);
  
  void cameraDistanceChanged(float paramFloat);
  
  void cameraZoomChanged(float paramFloat);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\CameraListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */