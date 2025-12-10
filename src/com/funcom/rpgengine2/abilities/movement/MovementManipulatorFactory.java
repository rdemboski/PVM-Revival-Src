package com.funcom.rpgengine2.abilities.movement;

import com.funcom.rpgengine2.abilities.SourceProvider;
import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import com.funcom.rpgengine2.creatures.RpgEntity;

public interface MovementManipulatorFactory {
  MovementManipulator createMovementManipulator(MovementManipulatorCreator paramMovementManipulatorCreator, SourceProvider paramSourceProvider, ShapeDataEvaluator paramShapeDataEvaluator, RpgEntity paramRpgEntity, double paramDouble, float paramFloat, int paramInt);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\movement\MovementManipulatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */