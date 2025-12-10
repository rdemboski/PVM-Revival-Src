package com.funcom.rpgengine2.abilities;

import com.funcom.rpgengine2.creatures.RpgEntity;

public interface SourceProvider {
  RpgEntity getSourceObject();
  
  RpgEntity getSourceHandler();
  
  void notifyHit(TargetProvider paramTargetProvider);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\SourceProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */