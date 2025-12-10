package com.funcom.tcg.client.ui.pets3;

import com.funcom.gameengine.jme.modular.ModularDescription;
import com.funcom.tcg.client.model.rpg.ClientItem;
import com.funcom.tcg.client.ui.Item;
import com.funcom.tcg.rpg.CreatureVisualDescription;
import java.util.List;

public interface PetListItem extends Item {
  String getDescription();
  
  String getFamily();
  
  String getElementId();
  
  String getRarity();
  
  int getTier();
  
  String getType();
  
  ModularDescription getPetDescription();
  
  CreatureVisualDescription getPetVisuals();
  
  List<ClientItem> getAllSkills();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetListItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */