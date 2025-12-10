package com.funcom.rpgengine2.creatures;

import com.funcom.rpgengine2.items.EquipDoll;
import com.funcom.rpgengine2.items.EquipSlot;
import com.funcom.rpgengine2.items.GeneralItemHolder;
import com.funcom.rpgengine2.items.Item;

public interface EquipmentChangeListener {
  void equipmentChange(RpgObject paramRpgObject, EquipDoll paramEquipDoll, EquipSlot paramEquipSlot, Item paramItem);
  
  void inventoryChange(RpgObject paramRpgObject, GeneralItemHolder paramGeneralItemHolder, Item paramItem, boolean paramBoolean);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\EquipmentChangeListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */