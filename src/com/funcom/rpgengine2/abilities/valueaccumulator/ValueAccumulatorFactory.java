package com.funcom.rpgengine2.abilities.valueaccumulator;

import com.funcom.rpgengine2.loader.AbilityParams;
import com.funcom.rpgengine2.loader.RpgLoader;

public interface ValueAccumulatorFactory {
  GeneralValueAccumulator createValueAccumulator(RpgLoader paramRpgLoader, AbilityParams paramAbilityParams);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\valueaccumulator\ValueAccumulatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */