package com.funcom.tcg.client.ui.hud2;

import com.funcom.gameengine.model.input.Cursor;
import com.funcom.gameengine.model.input.MouseCursorSetter;
import com.funcom.gameengine.utils.LoadingScreenListener;
import com.funcom.rpgengine2.items.ItemDescription;
import com.funcom.tcg.client.ui.skills.SkillListModel;
import com.jmex.bui.util.Point;

public interface HudModel extends LoadingScreenListener {
  void addChangeListener(ChangeListener paramChangeListener);
  
  void removeChangeListener(ChangeListener paramChangeListener);
  
  void healingAction();
  
  void manaAction();
  
  void townportalAction();
  
  void petButtonAction(int paramInt);
  
  void skillButtonAction(int paramInt);
  
  String getHealthBubbleHtml();
  
  String getManaBubbleHtml();
  
  float getCurrentHealthFraction();
  
  float getCurrentManaFraction();
  
  int getCurrentHealth();
  
  int getCurrentMana();
  
  int getMaxHealth();
  
  int getMaxMana();
  
  float getCurrentXpFraction();
  
  int getCurrentLevel();
  
  PetButtonModel getPet(int paramInt);
  
  MouseCursorSetter getCursorSetter();
  
  Cursor getUseCursor();
  
  int getActivePetSlot();
  
  int getHealthPotionsAmount();
  
  int getManaPotionsAmount();
  
  void chatButtonAction();
  
  void cannedChatButtonAction();
  
  void friendsButtonAction();
  
  void characterButtonAction();
  
  void duelButtonAction(boolean paramBoolean);
  
  void mapButtonAction();
  
  void optionsButtonAction();
  
  void petsButtonAction();
  
  void achievementsButtonAction();
  
  void questsButtonAction();
  
  void achievementPendingAction(int paramInt);
  
  void chatPendingAction(int paramInt);
  
  void friendPendingAction(int paramInt);
  
  void addManaParticle(Point paramPoint);
  
  void addHealthParticle(Point paramPoint);
  
  void notifyLoadingScreenStarted(String paramString);
  
  void notifyLoadingScreenFinished(String paramString);
  
  void triggerXPParticles();
  
  void addXPParticle(Point paramPoint);
  
  void addHealthPotionParticle(Point paramPoint);
  
  void addManaPotionParticle(Point paramPoint);
  
  SkillListModel getSkillListModel();
  
  void triggerItemPickup(ItemDescription paramItemDescription);
  
  void triggerPetPickup();
  
  boolean isTownPortalEnabled();
  
  void petSlotHover(int paramInt);
  
  public static interface ChangeListener {
    void healthChanged(float param1Float);
    
    void healthPotionsAmountChanged(int param1Int);
    
    void manaChanged(float param1Float);
    
    void manaPotionsAmountChanged(int param1Int);
    
    void xpChanged(float param1Float);
    
    void levelChanged(int param1Int);
    
    void petChanged(int param1Int, PetButtonModel param1PetButtonModel);
    
    void activePetSelected(int param1Int);
    
    void skillbarItemUsed(int param1Int);
    
    void townPortalVisibilityChanged(boolean param1Boolean);
    
    void petPickedUp();
    
    void itemPickedUp(ItemDescription param1ItemDescription);
    
    void characterWindowToggled();
    
    void petWindowToggled();
    
    void mapWindowToggled();
    
    void chatWindowToggled();
    
    void optionsWindowToggled();
    
    void chatPending(int param1Int);
    
    void friendPending(int param1Int);
    
    void achievementPending(int param1Int);
    
    void petSlotHovered(int param1Int);
  }
}


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\HudModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */