package com.funcom.rpgengine2.combat;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.rpgengine2.abilities.TargetProvider;
import com.funcom.rpgengine2.creatures.RpgEntity;
import com.funcom.rpgengine2.creatures.RpgObject;
import java.awt.geom.Rectangle2D;
import java.util.List;

public interface ShapeDataEvaluator {
  double evalAngleLocal(double paramDouble1, RpgEntity paramRpgEntity1, RpgEntity paramRpgEntity2, double paramDouble2, double paramDouble3, UsageParams paramUsageParams);
  
  double evalDistance(RpgEntity paramRpgEntity1, RpgEntity paramRpgEntity2, double paramDouble1, double paramDouble2, UsageParams paramUsageParams);
  
  float getAngle(RpgEntity paramRpgEntity);
  
  float getRadius(RpgEntity paramRpgEntity);
  
  WorldCoordinate getPos(RpgEntity paramRpgEntity);
  
  Object getId(RpgEntity paramRpgEntity);
  
  List<? extends TargetProvider> findTargets(RpgObject paramRpgObject, Rectangle2D paramRectangle2D);
  
  boolean contains(RpgEntity paramRpgEntity1, RpgEntity paramRpgEntity2, Rectangle2D paramRectangle2D, UsageParams paramUsageParams);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\ShapeDataEvaluator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */