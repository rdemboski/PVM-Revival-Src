package com.funcom.gameengine.model;

import com.jme.intersection.PickResults;
import com.jme.math.Ray;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;

public interface SpatializedWorld extends World {
  void updateGeometricState(float paramFloat, boolean paramBoolean);
  
  void updateRenderState();
  
  void render(Renderer paramRenderer);
  
  Node getWorldNode();
  
  void findPick(Ray paramRay, PickResults paramPickResults);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\SpatializedWorld.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */