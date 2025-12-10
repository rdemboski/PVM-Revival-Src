package com.funcom.rpgengine2.creatures;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.rpgengine2.TargetProviderMap;

public interface MapSupportable extends RpgQueryableSupport, TargetProviderMap {
  WorldCoordinate getPosition();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\MapSupportable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */