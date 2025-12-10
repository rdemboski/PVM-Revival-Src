package com.funcom.tcg.client.ui.pets3;

import com.funcom.tcg.client.model.rpg.ClientPet;
import com.funcom.tcg.client.ui.event.SubscriberChangedListener;
import java.util.List;

public interface PetsWindowModel {
  void setPet(int paramInt, ClientPet paramClientPet);
  
  ClientPet getPet(int paramInt);
  
  void setChangeListener(int paramInt, PetView paramPetView);
  
  void fireValuesAsChanges();
  
  void viewChanged(int paramInt);
  
  int getActiveViewId();
  
  boolean isCollectedPet(PetWindowPet paramPetWindowPet);
  
  void setPetSwitchLock(int paramInt, boolean paramBoolean);
  
  boolean isSubscriber();
  
  List<PetWindowPet> getSortedPets();
  
  void setSortedPets(List<PetWindowPet> paramList);
  
  int getNumTokens();
  
  void addSubscriberListener(SubscriberChangedListener paramSubscriberChangedListener);
  
  void removeSubscriberListener(SubscriberChangedListener paramSubscriberChangedListener);
  
  void close();
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetsWindowModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */