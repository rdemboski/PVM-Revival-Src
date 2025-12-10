package com.funcom.gameengine.view;

import com.jme.scene.Spatial;

public interface OptimizedNode {
  void attachStaticChild(Spatial paramSpatial);
  
  void attachAnimatedChild(Spatial paramSpatial);
  
  void updateRenderState();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\OptimizedNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */