package com.funcom.tcg.client.ui.character;

import com.funcom.rpgengine2.StatCollection;
import com.funcom.tcg.client.model.rpg.ClientEquipDoll;
import com.funcom.tcg.client.model.rpg.ClientPlayer;
import com.funcom.tcg.client.ui.event.SubscriberChangedListener;
import com.funcom.tcg.client.ui.inventory.Inventory;
import com.funcom.tcg.client.ui.inventory.InventoryItem;

public interface CharacterWindowModel {
  ClientPlayer getClientPlayer();
  
  StatCollection getStats();
  
  ClientEquipDoll getEquipDoll();
  
  Inventory getInventory();
  
  void equipItem(InventoryItem paramInventoryItem);
  
  boolean isSubscriber();
  
  void addSubscriberListener(SubscriberChangedListener paramSubscriberChangedListener);
  
  void removeSubscriberListener(SubscriberChangedListener paramSubscriberChangedListener);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\CharacterWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */