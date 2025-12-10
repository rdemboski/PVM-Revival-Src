package com.funcom.rpgengine2.abilities;

import com.funcom.rpgengine2.StatEffect;

public interface EffectFilter extends EquipAwareAbility, PassiveAbility {
  int getPriority();
  
  void filter(StatEffect paramStatEffect);
  
  boolean isSourceFilter();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\EffectFilter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */