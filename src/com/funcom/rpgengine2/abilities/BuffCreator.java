package com.funcom.rpgengine2.abilities;

import com.funcom.commons.dfx.DireEffectDescription;
import com.funcom.rpgengine2.combat.Element;
import java.util.List;
import java.util.Set;

public interface BuffCreator extends ActiveAbility {
  Set<String> getCureStatuses();
  
  int getDurationMillis();
  
  int getUpdateDelayMillis();
  
  List<Ability> getAbilities();
  
  DireEffectDescription getBuffDfx();
  
  BuffType getType();
  
  boolean matchCureCriterias(Element paramElement, String paramString);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\BuffCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */