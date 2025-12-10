package com.funcom.rpgengine2.abilities.projectile;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.rpgengine2.creatures.RpgQueryableSupport;

public interface ProjectileQueue extends RpgQueryableSupport {
  void enqueueProjectile(Projectile paramProjectile, WorldCoordinate paramWorldCoordinate, float paramFloat);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\projectile\ProjectileQueue.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */