package com.funcom.rpgengine2.abilities;

import com.funcom.rpgengine2.creatures.RpgEntity;
import com.funcom.rpgengine2.items.ItemDescription;

public interface Ability {
  String getId();
  
  int getLevelFrom();
  
  int getLevelTo();
  
  String getDFXReference();
  
  String getLanguageKey();
  
  String getIcon();
  
  String getIcons();
  
  int getDamage(ItemDescription paramItemDescription, RpgEntity paramRpgEntity, short paramShort);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\Ability.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */