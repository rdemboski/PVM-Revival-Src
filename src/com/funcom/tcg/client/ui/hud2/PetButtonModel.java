package com.funcom.tcg.client.ui.hud2;

import com.funcom.tcg.client.model.rpg.PetSlot;
import com.funcom.tcg.client.ui.Item;

public interface PetButtonModel extends Item {
  String getDescription();
  
  String getFamily();
  
  String getElementId();
  
  String getRarity();
  
  int getTier();
  
  String getType();
  
  String getHtml();
  
  PetSlot getPetSlot();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\PetButtonModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */