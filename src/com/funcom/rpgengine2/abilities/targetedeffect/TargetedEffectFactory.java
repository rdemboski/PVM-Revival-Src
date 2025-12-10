package com.funcom.rpgengine2.abilities.targetedeffect;

import com.funcom.gameengine.WorldCoordinate;
import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import com.funcom.rpgengine2.items.Item;

public interface TargetedEffectFactory {
  TargetedEffect newTargetedEffect(Item paramItem, TargetedEffectCreator paramTargetedEffectCreator, ShapeDataEvaluator paramShapeDataEvaluator, double paramDouble, WorldCoordinate paramWorldCoordinate, float paramFloat);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\targetedeffect\TargetedEffectFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */