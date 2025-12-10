package com.funcom.rpgengine2.abilities;

import com.funcom.rpgengine2.creatures.RpgEntity;

public interface PassiveAbility extends Ability {
  void addAbility(RpgEntity paramRpgEntity);
  
  void removeAbility(RpgEntity paramRpgEntity);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\PassiveAbility.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */