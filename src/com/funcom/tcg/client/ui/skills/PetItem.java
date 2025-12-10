package com.funcom.tcg.client.ui.skills;

import com.funcom.tcg.client.model.rpg.ClientItem;
import com.funcom.tcg.client.ui.Item;
import java.util.List;

@Deprecated
public interface PetItem extends Item {
  List<ClientItem> getAllSkills();
  
  int getSkillCount();
  
  SkillListItem getSelectedSkill(int paramInt);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\skills\PetItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */