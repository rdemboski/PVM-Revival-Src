package com.funcom.tcg.client.ui.hud;

import com.funcom.tcg.client.model.rpg.ClientItem;
import com.funcom.tcg.client.model.rpg.ClientPet;
import com.funcom.tcg.client.ui.pets3.PetListItem;
import com.funcom.tcg.client.ui.skills.SkillListItemImpl;

public interface ActionContainerModel {
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
  
  ClientItem[] getActivePetSkills();
  
  PetListItem getActivePet();
  
  PetListItem getSelectedPet(int paramInt);
  
  void setActivePet(PetListItem paramPetListItem);
  
  int getXpValue();
  
  int getXpLevelMax();
  
  int getXpLevelMin();
  
  void selectSkill(ClientPet paramClientPet, int paramInt, SkillListItemImpl paramSkillListItemImpl);
  
  void moveSkill(ClientItem paramClientItem, int paramInt1, int paramInt2);
  
  int getCurrentMana();
  
  public static interface ChangeListener {
    void selectedPetsChanged(ActionContainerModel param1ActionContainerModel);
    
    void activePetChanged(ActionContainerModel param1ActionContainerModel);
    
    void petSkillsChanged(ActionContainerModel param1ActionContainerModel, ClientPet param1ClientPet);
    
    void xpChanged(ActionContainerModel param1ActionContainerModel, int param1Int1, int param1Int2, int param1Int3);
    
    void skillbarItemUsed(int param1Int);
    
    void manaChanged(ActionContainerModel param1ActionContainerModel, int param1Int);
    
    void levelUp(ActionContainerModel param1ActionContainerModel, int param1Int);
    
    void silenceChanged();
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\ActionContainerModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */