package com.funcom.rpgengine2.abilities.projectile;

import com.funcom.rpgengine2.abilities.TargetProvider;
import com.funcom.rpgengine2.abilities.TriggerShape;
import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import com.funcom.rpgengine2.items.Item;

public interface ProjectileFactory {
  Projectile newProjectile(Item paramItem, ProjectileCreator paramProjectileCreator, TriggerShape paramTriggerShape, ShapeDataEvaluator paramShapeDataEvaluator, TargetProvider paramTargetProvider, boolean paramBoolean, int paramInt);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\projectile\ProjectileFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */