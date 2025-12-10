package com.funcom.tcg.client.ui.startmenu;

import com.funcom.gameengine.jme.modular.ModularDescription;
import com.funcom.rpgengine2.items.ItemDescription;
import com.funcom.rpgengine2.items.PlayerDescription;
import com.funcom.tcg.net.PlayerStartConfig;
import com.funcom.tcg.net.StartingPet;
import com.funcom.tcg.rpg.ClientDescriptionVisual;
import com.funcom.tcg.rpg.CreatureVisualDescription;
import java.util.Collection;

public interface StartMenuModel {
  Collection<ClientDescriptionVisual> getHairList();
  
  Collection<ClientDescriptionVisual> getHairColorList();
  
  Collection<ClientDescriptionVisual> getFaceList();
  
  Collection<ItemDescription> getTorsoList();
  
  Collection<ItemDescription> getLegList();
  
  void setGender(PlayerDescription.Gender paramGender);
  
  PlayerDescription.Gender getGender();
  
  void setStartingPet(StartingPet paramStartingPet);
  
  StartingPet getStartingPet();
  
  void setHair(ClientDescriptionVisual paramClientDescriptionVisual);
  
  void setHairColor(ClientDescriptionVisual paramClientDescriptionVisual);
  
  void setFace(ClientDescriptionVisual paramClientDescriptionVisual);
  
  void setTorso(ItemDescription paramItemDescription);
  
  ItemDescription getTorso();
  
  void setLegs(ItemDescription paramItemDescription);
  
  ItemDescription getLegs();
  
  void setParentsEmail(String paramString);
  
  String getParentsEmail();
  
  void setPassword(String paramString);
  
  String getPassword();
  
  String getPasswordHash();
  
  void setCharactersName(String paramString);
  
  String getCharactersName();
  
  ModularDescription getModularDescription();
  
  CreatureVisualDescription getPetVisuals();
  
  PlayerDescription getPlayerDescription();
  
  PlayerStartConfig makeStartConfig();
  
  PlayerStartConfig getStartConfig();
  
  void setPlayerStartConfig(PlayerStartConfig paramPlayerStartConfig);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\StartMenuModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */