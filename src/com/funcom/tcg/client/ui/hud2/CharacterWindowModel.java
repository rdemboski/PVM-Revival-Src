package com.funcom.tcg.client.ui.hud2;

public interface CharacterWindowModel {
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
  
  String getTitle();
  
  String getPetId(int paramInt);
  
  String getEquippedItemId(int paramInt);
  
  int getEquippedItemTier(int paramInt);
  
  int getLevel();
  
  int getMaxHealth();
  
  int getMaxMana();
  
  float getCritFraction();
  
  int getPower();
  
  float getFireArmorProgress();
  
  float getEarthArmorProgress();
  
  float getWaterArmorProgress();
  
  String getActivePetDfx();
  
  void openEquipmentView(String paramString, ItemTierData paramItemTierData);
  
  void openPetView(String paramString);
  
  void dispose();
  
  public static interface ChangeListener {
    void equipmentChange(int param1Int1, String param1String, int param1Int2);
    
    void equipmentRemoved(int param1Int);
    
    void activePetChanged();
    
    void petChange(int param1Int, String param1String);
    
    void resistancesChange();
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\CharacterWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */