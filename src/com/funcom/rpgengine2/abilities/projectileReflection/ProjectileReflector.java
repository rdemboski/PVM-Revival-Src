package com.funcom.rpgengine2.abilities.projectileReflection;

import com.funcom.rpgengine2.abilities.SourceProvider;

public interface ProjectileReflector {
  SourceProvider getReflectorSourceProvider();
  
  void notifyImpact();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\projectileReflection\ProjectileReflector.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */