package com.funcom.tcg.client.model.rpg;

import com.funcom.rpgengine2.combat.RpgStatus;
import com.funcom.tcg.net.message.StateUpdateMessage;
import java.util.Collection;
import java.util.Set;

public interface PlayerEventsListener {
  void updateBuff(StateUpdateMessage.BuffData paramBuffData);
  
  void updateHealth(int paramInt1, int paramInt2);
  
  void updateLives(int paramInt);
  
  void updateMana(int paramInt1, int paramInt2);
  
  void updateXp(int paramInt1, int paramInt2, int paramInt3);
  
  void updateLevel(int paramInt);
  
  void acquiredPetsChanged(Set<ClientPet> paramSet);
  
  void newPetAcquired(ClientPet paramClientPet);
  
  void selectedPetsReconfigured(int paramInt, ClientPet paramClientPet);
  
  void activePetChanged();
  
  void itemSold(int paramInt1, int paramInt2, int paramInt3);
  
  void skillbarItemUsed(int paramInt1, int paramInt2, int paramInt3);
  
  void updateStatus(Collection<RpgStatus> paramCollection);
  
  void updateTownPortal(boolean paramBoolean);
  
  void respawned();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\PlayerEventsListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */