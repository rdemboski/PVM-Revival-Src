package com.funcom.tcg.client.ui;

import com.funcom.tcg.client.ui.inventory.InventoryItem;
import com.funcom.tcg.client.ui.pets3.PetListItem;
import com.funcom.tcg.client.ui.skills.PetItem;
import com.funcom.tcg.client.ui.skills.SkillListItem;
import com.funcom.tcg.client.ui.vendor.PriceDesc;
import com.funcom.tcg.client.ui.vendor.VendorModelItem;
import com.funcom.tcg.net.message.StateUpdateMessage;
import com.jmex.bui.BImage;
import com.jmex.bui.icon.BIcon;

public interface IconProvider {
  BImage getImageForBuff(StateUpdateMessage.BuffData paramBuffData);
  
  BImage getImageForItem(InventoryItem paramInventoryItem);
  
  BIcon getIconForItem(InventoryItem paramInventoryItem);
  
  BIcon getIconForItem(VendorModelItem paramVendorModelItem);
  
  BIcon getIconForPetFace(PetListItem paramPetListItem);
  
  BIcon getIconForPetFace(PetItem paramPetItem);
  
  BIcon getIconForPetType(PetListItem paramPetListItem);
  
  BIcon getIconForElement(PetListItem paramPetListItem);
  
  BIcon getIconForSkill(SkillListItem paramSkillListItem);
  
  BIcon getIconForPriceDesc(PriceDesc paramPriceDesc);
  
  BIcon getIconShine();
  
  BIcon getIconDisabledSkill();
  
  BIcon getIconSilenceSkill(InventoryItem paramInventoryItem);
  
  BImage getImageForCooldownBackground(InventoryItem paramInventoryItem);
  
  BImage getImageForCooldownProgress(InventoryItem paramInventoryItem);
  
  BImage getSpellHoverImage();
  
  BImage getSpellPressImage();
  
  BImage getImageForElement(String paramString);
  
  BIcon getIconForElement(String paramString, int paramInt);
  
  String getPathForElement(String paramString, int paramInt);
  
  BIcon getIconForFamily(String paramString, int paramInt);
  
  String getPathForFamily(String paramString, int paramInt);
  
  String getPathForStat(String paramString, int paramInt);
  
  BIcon getDefaultIcon();
  
  String getPathForDefaultIcon();
  
  BIcon getIconForRarity(String paramString);
  
  BIcon getIconForTier(int paramInt1, int paramInt2);
  
  String getPathForTier(int paramInt1, int paramInt2);
  
  String getPathForTier(String paramString, int paramInt);
  
  BIcon getIconForType(String paramString, int paramInt);
  
  String getPathForType(String paramString, int paramInt);
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\IconProvider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */