package com.funcom.rpgengine2.loader;

import com.funcom.rpgengine2.abilities.Ability;
import com.funcom.rpgengine2.abilities.AbilityContainer;
import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import java.util.List;

public interface AbilityFactory {
  Class<? extends Ability> getAbilityPermissionClass();
  
  Ability create(AbilityContainer paramAbilityContainer, ShapeDataEvaluator paramShapeDataEvaluator, AbilityParams paramAbilityParams, RpgLoader paramRpgLoader, ValueAccumulatorFactory paramValueAccumulatorFactory);
  
  AbilityParams createParams(List<String[]> paramList);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\AbilityFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */