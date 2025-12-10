package com.funcom.rpgengine2.abilities;

import com.funcom.commons.geom.RectangleWC;
import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import com.funcom.rpgengine2.combat.UsageParams;
import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;
import java.util.List;

public interface AbilityHolder {
  RpgSourceProviderEntity getOwner();
  
  RectangleWC getBoundsByOwner();
  
  void applyAbilities(String paramString, ShapeDataEvaluator paramShapeDataEvaluator, SourceProvider paramSourceProvider, List<? extends TargetProvider> paramList, UsageParams paramUsageParams);
  
  String getId();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\AbilityHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */