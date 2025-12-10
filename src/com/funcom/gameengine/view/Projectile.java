package com.funcom.gameengine.view;

import com.funcom.gameengine.model.World;
import com.jme.math.Vector3f;

public interface Projectile {
  void launchProjectile(World paramWorld, Vector3f paramVector3f1, Vector3f paramVector3f2, float paramFloat);
  
  void killProjectile(World paramWorld);
  
  void updateProjectileTarget(Vector3f paramVector3f, double paramDouble);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\Projectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */