package com.funcom.rpgengine2;

import com.funcom.commons.geom.RectangleWC;
import com.funcom.rpgengine2.abilities.TargetProvider;
import com.funcom.rpgengine2.creatures.RpgTargetProviderEntity;
import java.util.List;

public interface TargetProviderMap {
  List<? extends TargetProvider> findObjects(RectangleWC paramRectangleWC);
  
  List<? extends TargetProvider> getAll();
  
  void add(RpgTargetProviderEntity paramRpgTargetProviderEntity);
  
  void remove(RpgTargetProviderEntity paramRpgTargetProviderEntity);
  
  String getMapId();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\TargetProviderMap.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */