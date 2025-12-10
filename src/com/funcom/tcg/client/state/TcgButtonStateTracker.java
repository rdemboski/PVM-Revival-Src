/*     */ package com.funcom.tcg.client.state;
/*     */ 
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.input.ButtonStateTracker;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.tcg.client.Controls;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.PetSlot;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.achievements.AchievementsWindow;
/*     */ import com.funcom.tcg.client.ui.character.CharacterEquipmentWindow;
/*     */ import com.funcom.tcg.client.ui.friend.FriendModeType;
/*     */ import com.funcom.tcg.client.ui.hud.GameWindows;
/*     */ import com.funcom.tcg.client.ui.hud.GuiWindowsController;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.mainmenu.MainMenuWindow;
/*     */ import com.funcom.tcg.client.ui.mainmenu.OptionsWindow;
/*     */ import com.funcom.tcg.client.ui.maps.MapWindow2;
/*     */ import com.funcom.tcg.client.ui.pets3.PetsWindow;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorFullWindow;
/*     */ import com.funcom.tcg.net.message.AutoUseItemMessage;
/*     */ import com.jme.input.controls.GameControl;
/*     */ import com.jme.input.controls.GameControlManager;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class TcgButtonStateTracker
/*     */   extends ButtonStateTracker
/*     */ {
/*  30 */   private static final Logger LOGGER = Logger.getLogger(TcgButtonStateTracker.class.getName());
/*     */   private long petSwitchSpamPreventionTime;
/*     */   private static final long PET_SWITHC_SPAM_PREVENT_CD = 450L;
/*     */   
/*     */   public TcgButtonStateTracker(GameControlManager gcm) {
/*  35 */     super(Controls.controlConstantsToGameControls(gcm, Controls.getGuiControlConstants()));
/*     */   }
/*     */ 
/*     */   
/*     */   protected void pressed(GameControl gameControl) {
/*  40 */     GuiWindowsController windowsController = MainGameState.getGuiWindowsController();
/*     */     
/*  42 */     if (PanelManager.getInstance().isKeyInputWindowOpen()) {
/*     */       return;
/*     */     }
/*     */ 
/*     */     
/*  47 */     if (Controls.CID_GUI_CLOSE_ALL_OR_MAINMENU.is(gameControl) && !TcgGame.isPetTutorial() && !TcgGame.isEquipmentTutorial()) {
/*  48 */       if (windowsController.windowsOpen()) {
/*  49 */         windowsController.closeAll();
/*     */       } else {
/*  51 */         windowsController.toggleWindow(GameWindows.MAIN_MENU);
/*     */       } 
/*     */     }
/*  54 */     if (!MainGameState.getPlayerModel().isAlive()) {
/*     */       return;
/*     */     }
/*  57 */     if (Controls.CID_PET_SLOT1.is(gameControl)) {
/*  58 */       switchPet(0);
/*  59 */     } else if (Controls.CID_PET_SLOT2.is(gameControl)) {
/*  60 */       switchPet(1);
/*  61 */     } else if (Controls.CID_PET_SLOT3.is(gameControl)) {
/*  62 */       switchPet(2);
/*  63 */     } else if (Controls.CID_GUI_OPEN_CHARACTER.is(gameControl)) {
/*  64 */       MainGameState.getHudModel().characterButtonAction();
/*     */     
/*     */     }
/*  67 */     else if (Controls.CID_GUI_OPEN_PETS.is(gameControl)) {
/*  68 */       MainGameState.getHudModel().petsButtonAction();
/*     */     
/*     */     }
/*  71 */     else if (Controls.CID_GUI_OPEN_MAP.is(gameControl)) {
/*  72 */       if (!TcgGame.isTutorialMode()) windowsController.toggleWindow(GameWindows.MAP); 
/*  73 */     } else if (Controls.CID_GUI_OPEN_ACHIEVEMENTS.is(gameControl)) {
/*  74 */       windowsController.toggleWindow(GameWindows.ACHIEVEMENTS);
/*  75 */     } else if (Controls.CID_GUI_OPEN_FRIENDS.is(gameControl)) {
/*  76 */       if (TcgGame.isChatEnabled() && MainGameState.getPlayerModel().getChatController().isChatControllerEnabled()) {
/*     */         
/*  78 */         TcgGame.setAddFriendMode(FriendModeType.OFF);
/*  79 */         windowsController.toggleWindow(GameWindows.FRIENDS);
/*     */       } 
/*  81 */     } else if (Controls.CID_GUI_OPEN_DUEL.is(gameControl)) {
/*  82 */       if (TcgGame.isPetTutorial() || TcgGame.isEquipmentTutorial() || TcgGame.isTutorialMode()) {
/*  83 */         TcgGame.setStartDuelMode(false);
/*     */       } else {
/*  85 */         TcgGame.setStartDuelMode(!TcgGame.isStartDuelMode());
/*     */       } 
/*  87 */     } else if (Controls.CID_GUI_OPEN_QUESTS.is(gameControl)) {
/*  88 */       windowsController.toggleWindow(GameWindows.QUEST_WINDOW);
/*  89 */     } else if (Controls.CID_USE_MANA_POTION.is(gameControl)) {
/*  90 */       if (!TcgGame.isDueling()) {
/*  91 */         MainGameState.getPlayerModel().autoUseItem(AutoUseItemMessage.Type.CURE_MANA);
/*     */       } else {
/*  93 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.potions", new String[0]));
/*     */       } 
/*  95 */     } else if (Controls.CID_PAUSE.is(gameControl)) {
/*  96 */       if (!TcgUI.isWindowOpen(PetsWindow.class) && !TcgUI.isWindowOpen(MapWindow2.class) && !TcgUI.isWindowOpen(CharacterEquipmentWindow.class) && !TcgUI.isWindowOpen(VendorFullWindow.class) && !TcgUI.isWindowOpen(MainMenuWindow.class) && !TcgUI.isWindowOpen(OptionsWindow.class) && !TcgUI.isWindowOpen(AchievementsWindow.class))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 103 */         windowsController.toggleWindow(GameWindows.PAUSE);
/*     */       }
/* 105 */     } else if (Controls.CID_USE_HEALTH_POTION.is(gameControl)) {
/* 106 */       if (!TcgGame.isDueling()) {
/* 107 */         MainGameState.getPlayerModel().autoUseItem(AutoUseItemMessage.Type.CURE_HEALTH);
/*     */       } else {
/* 109 */         DfxTextWindowManager.instance().getWindow("main").showText(TcgGame.getLocalizedText("duel.potions", new String[0]));
/*     */       } 
/* 111 */     } else if (Controls.CID_GUI_FOCUS_TO_CHAT.is(gameControl) && 
/* 112 */       TcgGame.isChatEnabled() && MainGameState.getPlayerModel().getChatController().isChatControllerEnabled() && TcgGame.isLoginFinished()) {
/* 113 */       windowsController.toggleWindow(GameWindows.CHAT);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void switchPet(int slot) {
/* 119 */     if (this.petSwitchSpamPreventionTime > GlobalTime.getInstance().getCurrentTime() || MainGameState.getPlayerModel().isDoingAnythingExceptMoving()) {
/*     */       return;
/*     */     }
/* 122 */     PetSlot petSlot = MainGameState.getPlayerModel().getPetSlot(slot);
/* 123 */     if (petSlot.isSelectable() && petSlot.getPet() != null && MainGameState.getPlayerModel().getActivePet() != petSlot.getPet()) {
/*     */       
/* 125 */       MainGameState.getPlayerModel().setActivePet(petSlot.getPet());
/* 126 */       this.petSwitchSpamPreventionTime = GlobalTime.getInstance().getCurrentTime() + 450L;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\state\TcgButtonStateTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */