package com.funcom.gameengine.model;

import com.funcom.gameengine.collisiondetection.Area;
import com.funcom.gameengine.view.AbstractProjectile;
import com.funcom.gameengine.view.PropNode;
import com.funcom.gameengine.view.RepresentationalNode;
import com.funcom.server.common.GameIOHandler;
import com.jme.scene.shape.Quad;

public interface World {
  void addObject(RepresentationalNode paramRepresentationalNode);
  
  void addCollisionObject(RepresentationalNode paramRepresentationalNode);
  
  void removeObject(RepresentationalNode paramRepresentationalNode);
  
  void addTile(Quad paramQuad);
  
  void addArea(Area paramArea);
  
  void addProjectile(AbstractProjectile paramAbstractProjectile);
  
  void removeProjectile(AbstractProjectile paramAbstractProjectile);
  
  void loadMap(String paramString, GameIOHandler paramGameIOHandler);
  
  void setPlayerNode(PropNode paramPropNode);
  
  WorldDebugFlags getDebugRenderFlags();
  
  PropNode getPlayerNode();
  
  String getMapName();
  
  ParticleSurface getParticleSurface();
  
  boolean isFullLoading();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\World.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */