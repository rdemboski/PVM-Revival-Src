package com.funcom.rpgengine2.creatures;

import com.funcom.rpgengine2.items.EquipException;
import com.funcom.rpgengine2.items.Item;

public interface ItemContainer extends Iterable<Item> {
  int putItem(int paramInt, Item paramItem) throws EquipException;
  
  Item getItem(int paramInt) throws EquipException;
  
  void removeItem(int paramInt) throws EquipException;
  
  void removeItem(Item paramItem) throws EquipException;
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\ItemContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */