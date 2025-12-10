package com.funcom.tcg.client.model.rpg;

public interface PetEventsListener {
  void newSkillAcquired(ClientPet paramClientPet, ClientItem paramClientItem);
  
  void skillLost(ClientPet paramClientPet, ClientItem paramClientItem);
  
  void selectedSkillsChanged(ClientPet paramClientPet);
  
  void levelChanged(ClientPet paramClientPet, int paramInt);
  
  void expChanged(ClientPet paramClientPet);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\PetEventsListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */