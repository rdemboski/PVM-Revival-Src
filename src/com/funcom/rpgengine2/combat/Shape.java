package com.funcom.rpgengine2.combat;

import com.funcom.commons.geom.RectangleWC;
import com.funcom.rpgengine2.abilities.SourceProvider;
import com.funcom.rpgengine2.abilities.TargetProvider;
import com.funcom.rpgengine2.creatures.RpgEntity;
import com.funcom.rpgengine2.items.Item;
import java.util.List;

public interface Shape {
  String getId();
  
  String getName();
  
  void extendBounds(RectangleWC paramRectangleWC);
  
  void process(Item paramItem, ShapeListener paramShapeListener, ShapeDataEvaluator paramShapeDataEvaluator, SourceProvider paramSourceProvider, List<? extends TargetProvider> paramList, UsageParams paramUsageParams);
  
  boolean canHit(Item paramItem, ShapeDataEvaluator paramShapeDataEvaluator, RpgEntity paramRpgEntity1, RpgEntity paramRpgEntity2, UsageParams paramUsageParams);
  
  double getRange();
  
  boolean isValidTarget(RpgEntity paramRpgEntity1, RpgEntity paramRpgEntity2);
  
  boolean isMelee();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\Shape.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */