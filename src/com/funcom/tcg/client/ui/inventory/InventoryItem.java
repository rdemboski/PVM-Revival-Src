package com.funcom.tcg.client.ui.inventory;

import com.funcom.rpgengine2.abilities.Ability;
import com.funcom.rpgengine2.items.ItemType;
import com.funcom.tcg.client.model.rpg.ClientPlayer;
import com.funcom.tcg.client.ui.Item;
import java.util.List;

public interface InventoryItem extends Item {
  int getLevel();
  
  long getCooldownMillis();
  
  double getGlobalCooldown();
  
  List<Ability> getAbilities();
  
  int getAmount();
  
  void setAmount(int paramInt);
  
  boolean isReady();
  
  void use(ClientPlayer paramClientPlayer, int paramInt1, int paramInt2, int paramInt3, float paramFloat, double paramDouble);
  
  int getManaCost(int paramInt);
  
  String getValueClassId();
  
  int getValueAmount();
  
  int getTier();
  
  ItemType getItemType();
  
  int getStackSize();
  
  String getSetId();
  
  boolean isSubscriberOnly();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\inventory\InventoryItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */