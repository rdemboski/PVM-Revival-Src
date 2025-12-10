package com.funcom.rpgengine2.abilities.projectileReflection;

import com.funcom.rpgengine2.abilities.SourceProvider;
import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import com.funcom.rpgengine2.loader.RpgLoader;

public interface ProjectileReflectorFactory {
  ProjectileReflector newProjectileReflector(SourceProvider paramSourceProvider, ShapeDataEvaluator paramShapeDataEvaluator, RpgLoader paramRpgLoader, String paramString);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\projectileReflection\ProjectileReflectorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */