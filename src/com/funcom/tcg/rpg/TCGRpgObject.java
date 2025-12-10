package com.funcom.tcg.rpg;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.rpgengine2.TargetProviderMap;

public interface TCGRpgObject {
  WorldCoordinate getPosition();
  
  float getAngle();
  
  Object getObjectId();
  
  TargetProviderMap getCurrentMap();
  
  Faction getFaction();
  
  float getRadius();
  
  boolean isHittable();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGRpgObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */