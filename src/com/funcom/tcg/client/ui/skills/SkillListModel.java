package com.funcom.tcg.client.ui.skills;

import java.util.List;

public interface SkillListModel {
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
  
  PetItem getSelectedPet();
  
  List<PetItem> getAllSelectedPets();
  
  int getPlayerLevel();
  
  List<SkillListItem> getSkillItems();
  
  void selectPet(String paramString);
  
  void dispose();
  
  void learnSkillByClassId(String paramString, int paramInt1, int paramInt2);
  
  public static interface ChangeListener {
    void selectedPetsChanged(SkillListModel param1SkillListModel);
    
    void skillsChanged(SkillListModel param1SkillListModel);
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\skills\SkillListModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */