package com.funcom.tcg.client.ui.skills;

import com.funcom.tcg.client.model.rpg.ClientItem;
import com.funcom.tcg.client.ui.Item;

@Deprecated
public interface SkillListItem extends Item {
  int getLevel();
  
  @Deprecated
  ClientItem getPetSkill();
  
  int getTier();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\skills\SkillListItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */