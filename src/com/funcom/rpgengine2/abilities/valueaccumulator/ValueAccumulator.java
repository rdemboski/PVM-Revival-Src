package com.funcom.rpgengine2.abilities.valueaccumulator;

import com.funcom.rpgengine2.abilities.AbilityContainer;
import com.funcom.rpgengine2.creatures.RpgEntity;

public interface ValueAccumulator {
  int eval(AbilityContainer paramAbilityContainer, RpgEntity paramRpgEntity);
  
  float getAddFixed();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\valueaccumulator\ValueAccumulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */