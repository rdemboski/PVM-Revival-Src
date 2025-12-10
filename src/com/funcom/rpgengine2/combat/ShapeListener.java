package com.funcom.rpgengine2.combat;

import com.funcom.rpgengine2.abilities.SourceProvider;
import com.funcom.rpgengine2.abilities.TargetProvider;
import com.funcom.rpgengine2.items.Item;

public interface ShapeListener {
  void notifyHit(Item paramItem, ShapeDataEvaluator paramShapeDataEvaluator, SourceProvider paramSourceProvider, TargetProvider paramTargetProvider, UsageParams paramUsageParams);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\combat\ShapeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */