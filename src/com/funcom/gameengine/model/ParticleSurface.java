package com.funcom.gameengine.model;

import com.funcom.gameengine.view.PropNode;
import com.turborilla.jops.jme.JopsNode;

public interface ParticleSurface {
  void addWorldParticles(JopsNode paramJopsNode);
  
  void addWorldParticleEmitter(JopsNode paramJopsNode);
  
  void removeWorldParticles(JopsNode paramJopsNode);
  
  void addDisconnectedMeshEffect(PropNode paramPropNode);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\ParticleSurface.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */