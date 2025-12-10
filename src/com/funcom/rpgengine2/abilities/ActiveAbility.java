package com.funcom.rpgengine2.abilities;

import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import com.funcom.rpgengine2.combat.UsageParams;
import com.funcom.rpgengine2.creatures.RpgEntity;
import com.funcom.rpgengine2.items.Item;
import java.util.List;

public interface ActiveAbility extends Ability {
  void useIfHit(Item paramItem, ShapeDataEvaluator paramShapeDataEvaluator, SourceProvider paramSourceProvider, List<? extends TargetProvider> paramList, UsageParams paramUsageParams);
  
  void useAlways(Item paramItem, ShapeDataEvaluator paramShapeDataEvaluator, SourceProvider paramSourceProvider, TargetProvider paramTargetProvider, UsageParams paramUsageParams);
  
  boolean canHit(Item paramItem, ShapeDataEvaluator paramShapeDataEvaluator, RpgEntity paramRpgEntity1, RpgEntity paramRpgEntity2, UsageParams paramUsageParams);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\ActiveAbility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */