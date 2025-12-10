package com.funcom.rpgengine2.items;

import com.funcom.rpgengine2.creatures.ItemContainer;
import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;

public interface ItemHolder extends ItemContainer {
  int getId();
  
  int getItemCount();
  
  int getSlotCount();
  
  boolean hasLimitedCapacity();
  
  int getCapacity();
  
  boolean isFull();
  
  void setOwner(RpgSourceProviderEntity paramRpgSourceProviderEntity);
  
  RpgSourceProviderEntity getOwner();
  
  void addChangeListener(ItemChangeListener paramItemChangeListener);
  
  void removeChangeListener(ItemChangeListener paramItemChangeListener);
  
  @Deprecated
  int getSlotIdByItemId(String paramString);
  
  int getSlotForItem(Item paramItem);
  
  boolean canPut(ItemDescription paramItemDescription);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\ItemHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */