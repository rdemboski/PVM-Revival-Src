package com.funcom.tcg.client.model.rpg;

import com.funcom.rpgengine2.combat.RpgStatus;
import com.funcom.tcg.net.message.StateUpdateMessage;
import java.util.Collection;
import java.util.Set;

public abstract class PlayerEventsAdapter implements PlayerEventsListener {
  public void updateHealth(int currentHealth, int maximumHealth) {}
  
  public void updateLives(int currentLives) {}
  
  public void updateMana(int currentMana, int maximumMana) {}
  
  public void updateXp(int startXp, int endXp, int xp) {}
  
  public void updateLevel(int currentLevel) {}
  
  public void acquiredPetsChanged(Set<ClientPet> petList) {}
  
  public void newPetAcquired(ClientPet pet) {}
  
  public void selectedPetsReconfigured(int petSlot, ClientPet newPet) {}
  
  public void activePetChanged() {}
  
  public void itemSold(int containerId, int containerType, int slotId) {}
  
  public void skillbarItemUsed(int containerId, int containerType, int slotId) {}
  
  public void updateStatus(Collection<RpgStatus> changedStatus) {}
  
  public void updateBuff(StateUpdateMessage.BuffData buffData) {}
  
  public void updateTownPortal(boolean visibility) {}
  
  public void respawned() {}
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\PlayerEventsAdapter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */