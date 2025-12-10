/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.commons.FileUtils;
/*     */ import com.funcom.gameengine.model.command.Command;
/*     */ import com.funcom.gameengine.model.input.Cursor;
/*     */ import com.funcom.gameengine.model.input.MouseCursorSetter;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.gameengine.view.particles.GuiParticleJoint;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.DistanceCalculator;
/*     */ import com.funcom.tcg.client.model.UseSkillCommand;
/*     */ import com.funcom.tcg.client.model.WorldCoordinateDistanceCalculator;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.PetEventsListener;
/*     */ import com.funcom.tcg.client.model.rpg.PetSlot;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsAdapter;
/*     */ import com.funcom.tcg.client.model.rpg.PlayerEventsListener;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.state.TCGCursorSetter;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.friend.FriendModeType;
/*     */ import com.funcom.tcg.client.ui.hud.GameWindows;
/*     */ import com.funcom.tcg.client.ui.hud.PanelManager;
/*     */ import com.funcom.tcg.client.ui.hud.SubscribeWindow;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.client.ui.skills.SkillListModel;
/*     */ import com.funcom.tcg.net.message.AutoUseItemMessage;
/*     */ import com.jme.math.Vector2f;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.util.Point;
/*     */ import java.util.Arrays;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.softmed.jops.ParticleSystem;
/*     */ 
/*     */ public class TCGClientHudModel
/*     */   extends AbstractHudModel {
/*  45 */   private static final Logger LOGGER = Logger.getLogger(TCGClientHudModel.class.getName());
/*     */   
/*     */   public static final String GUIEFFECT_MANADRINK = "guieffect_manadrink_blue.ops";
/*     */   
/*     */   public static final String GUIEFFECT_HEALTHDRINK = "guieffect_healthdrink_red.ops";
/*     */   
/*     */   public static final String GUIEFFECT_XP = "guieffect_xpgain.ops";
/*     */   public static final String GUIEFFECT_HEALTH_POT = "guieffect_health_pot_gain.ops";
/*     */   public static final String GUIEFFECT_MANA_POT = "guieffect_mana_pot_gain.ops";
/*     */   private static final String POTION_HEALTH = "potion-health";
/*     */   private static final String POTION_MANA = "potion-mana";
/*     */   private LocalClientPlayer clientPlayer;
/*  57 */   private List<GuiParticleJoint> guiParticles = new LinkedList<GuiParticleJoint>();
/*     */   private int oldHealthPotAmount;
/*     */   private int oldManaPotAmount;
/*     */   private boolean townPortalEnabled = false;
/*     */   private boolean firstMapLoaded = false;
/*     */   
/*     */   public TCGClientHudModel(LocalClientPlayer clientPlayer) {
/*  64 */     if (clientPlayer == null)
/*  65 */       throw new IllegalArgumentException("clientPlayer = null"); 
/*  66 */     setClientPlayer(clientPlayer);
/*     */   }
/*     */   
/*     */   private void petsReconfigured(int petSlot, ClientPet newPet) {
/*  70 */     firePetChanged(petSlot, new ClientPetModel((ClientPlayer)this.clientPlayer, petSlot));
/*  71 */     if (newPet == this.clientPlayer.getActivePet()) {
/*  72 */       fireActivePetChanged(getActivePetSlot());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void triggerItemPickup(ItemDescription itemDesc) {
/*  78 */     fireItemPickedUp(itemDesc);
/*     */   }
/*     */ 
/*     */   
/*     */   public void triggerPetPickup() {
/*  83 */     firePetPickedUp();
/*     */   }
/*     */ 
/*     */   
/*     */   public void healingAction() {
/*  88 */     this.clientPlayer.autoUseItem(AutoUseItemMessage.Type.CURE_HEALTH);
/*     */   }
/*     */ 
/*     */   
/*     */   public void manaAction() {
/*  93 */     this.clientPlayer.autoUseItem(AutoUseItemMessage.Type.CURE_MANA);
/*     */   }
/*     */   
/*     */   public void setClientPlayer(LocalClientPlayer clientPlayer) {
/*  97 */     this.clientPlayer = clientPlayer;
/*     */     
/*  99 */     this.oldHealthPotAmount = getHealthPotionsAmount();
/* 100 */     this.oldManaPotAmount = getManaPotionsAmount();
/* 101 */     clientPlayer.addPlayerEventsListener((PlayerEventsListener)new PlayerEventsAdapter()
/*     */         {
/*     */           public void updateHealth(int currentHealth, int maximumHealth) {
/* 104 */             TCGClientHudModel.this.fireHealthChanged(TCGClientHudModel.this.getCurrentHealthFraction());
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateMana(int currentMana, int maximumMana) {
/* 109 */             TCGClientHudModel.this.fireManaChanged(TCGClientHudModel.this.getCurrentManaFraction());
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateXp(int startXp, int endXp, int xp) {
/* 114 */             TCGClientHudModel.this.fireXpChanged(TCGClientHudModel.this.getCurrentXpFraction());
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateLevel(int currentLevel) {
/* 119 */             TCGClientHudModel.this.fireLevelChanged(currentLevel);
/*     */           }
/*     */ 
/*     */           
/*     */           public void selectedPetsReconfigured(int petSlot, ClientPet newPet) {
/* 124 */             TCGClientHudModel.this.petsReconfigured(petSlot, newPet);
/*     */           }
/*     */ 
/*     */           
/*     */           public void activePetChanged() {
/* 129 */             TCGClientHudModel.this.fireActivePetChanged(TCGClientHudModel.this.getActivePetSlot());
/*     */           }
/*     */ 
/*     */           
/*     */           public void skillbarItemUsed(int containerId, int containerType, int slotId) {
/* 134 */             TCGClientHudModel.this.fireSkillbarItemUsed(slotId);
/*     */           }
/*     */ 
/*     */           
/*     */           public void updateTownPortal(boolean visibility) {
/* 139 */             TCGClientHudModel.this.townPortalEnabled = visibility;
/* 140 */             TCGClientHudModel.this.fireTownportalChanged(visibility);
/*     */           }
/*     */         });
/*     */     
/* 144 */     clientPlayer.getInventory().addChangeListener(new Inventory.ChangeListener()
/*     */         {
/*     */           public void slotChanged(Inventory inventory, int slotId, InventoryItem oldItem, InventoryItem newItem) {
/* 147 */             if (newItem != null && newItem.getClassId().equals("potion-health")) {
/* 148 */               if (newItem.getAmount() < TCGClientHudModel.this.oldHealthPotAmount) {
/* 149 */                 MainGameState.getGuiParticlesRenderPass().triggerParticleEffect("guieffect_healthdrink_red.ops");
/* 150 */               } else if (newItem.getAmount() > TCGClientHudModel.this.oldHealthPotAmount) {
/* 151 */                 MainGameState.getGuiParticlesRenderPass().triggerParticleEffect("guieffect_health_pot_gain.ops");
/*     */               } else {
/*     */                 return;
/*     */               } 
/* 155 */               TCGClientHudModel.this.oldHealthPotAmount = newItem.getAmount();
/* 156 */               TCGClientHudModel.this.fireHealthPotionsChanged(newItem.getAmount());
/* 157 */             } else if (oldItem != null && oldItem.getClassId().equals("potion-health")) {
/* 158 */               TCGClientHudModel.this.fireHealthPotionsChanged(0);
/* 159 */               MainGameState.getGuiParticlesRenderPass().triggerParticleEffect("guieffect_healthdrink_red.ops");
/* 160 */               TCGClientHudModel.this.oldHealthPotAmount = 0;
/* 161 */             } else if (newItem != null && newItem.getClassId().equals("potion-mana")) {
/* 162 */               if (newItem.getAmount() < TCGClientHudModel.this.oldManaPotAmount) {
/* 163 */                 MainGameState.getGuiParticlesRenderPass().triggerParticleEffect("guieffect_manadrink_blue.ops");
/* 164 */               } else if (newItem.getAmount() > TCGClientHudModel.this.oldManaPotAmount) {
/* 165 */                 MainGameState.getGuiParticlesRenderPass().triggerParticleEffect("guieffect_mana_pot_gain.ops");
/*     */               } else {
/*     */                 return;
/*     */               } 
/* 169 */               TCGClientHudModel.this.oldManaPotAmount = newItem.getAmount();
/* 170 */               TCGClientHudModel.this.fireManaPotionsChanged(newItem.getAmount());
/* 171 */             } else if (oldItem != null && oldItem.getClassId().equals("potion-mana")) {
/* 172 */               TCGClientHudModel.this.fireManaPotionsChanged(0);
/* 173 */               MainGameState.getGuiParticlesRenderPass().triggerParticleEffect("guieffect_manadrink_blue.ops");
/* 174 */               TCGClientHudModel.this.oldManaPotAmount = 0;
/*     */             } 
/*     */           }
/*     */         });
/* 178 */     clientPlayer.getActivePet().addPetEventsListener((PetEventsListener)MainGameState.getSkillListModel());
/*     */   }
/*     */ 
/*     */   
/*     */   public void townportalAction() {
/* 183 */     String townPortalHomeMapId = MainGameState.getTownPortalPropertyReader().loadHomeTownProperties().getMapId();
/* 184 */     String currentMapId = FileUtils.fixTailingSlashes(MainGameState.getWorld().getMapName());
/* 185 */     if (!townPortalHomeMapId.equalsIgnoreCase(currentMapId)) {
/* 186 */       this.clientPlayer.openTownportal();
/*     */     } else {
/* 188 */       DfxTextWindowManager.instance().getWindow("townportal").showText(TcgGame.getLocalizedText("townportal.action.rejected", new String[0]));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void petButtonAction(int slot) {
/* 194 */     PetSlot petSlot = this.clientPlayer.getPetSlot(slot);
/* 195 */     if (petSlot.isSelectable() && petSlot.getPet() != null) {
/* 196 */       this.clientPlayer.setActivePet(petSlot.getPet());
/*     */     }
/*     */   }
/*     */   
/*     */   public void skillButtonAction(int slot) {
/* 201 */     this.clientPlayer.immediateCommand((Command)new UseSkillCommand(slot, (ClientPlayer)this.clientPlayer, TcgGame.getDireEffectDescriptionFactory(), (DistanceCalculator)new WorldCoordinateDistanceCalculator(this.clientPlayer.getPosition(), this.clientPlayer.getPosition())));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String getHealthBubbleHtml() {
/* 207 */     return MainGameState.getToolTipManager().getHealthBubbleHtml();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getManaBubbleHtml() {
/* 212 */     return MainGameState.getToolTipManager().getManaBubbleHtml();
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCurrentHealthFraction() {
/* 217 */     float health = this.clientPlayer.getStatSum(Short.valueOf((short)12)).floatValue();
/* 218 */     float maxHealth = this.clientPlayer.getStatSum(Short.valueOf((short)11)).floatValue();
/*     */     
/* 220 */     float fraction = health / maxHealth;
/* 221 */     if (fraction > 1.0F) { fraction = 1.0F; }
/* 222 */     else if (fraction < 0.0F) { fraction = 0.0F; }
/*     */ 
/*     */     
/* 225 */     return fraction;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCurrentManaFraction() {
/* 230 */     float mana = this.clientPlayer.getStatSum(Short.valueOf((short)14)).floatValue();
/* 231 */     float maxMana = this.clientPlayer.getStatSum(Short.valueOf((short)13)).floatValue();
/*     */     
/* 233 */     float fraction = mana / maxMana;
/* 234 */     if (fraction > 1.0F) { fraction = 1.0F; }
/* 235 */     else if (fraction < 0.0F) { fraction = 0.0F; }
/*     */     
/* 237 */     return fraction;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxMana() {
/* 242 */     if (this.clientPlayer != null) {
/* 243 */       return this.clientPlayer.getStatSum(Short.valueOf((short)13)).intValue();
/*     */     }
/* 245 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMaxHealth() {
/* 250 */     if (this.clientPlayer != null) {
/* 251 */       return this.clientPlayer.getStatSum(Short.valueOf((short)11)).intValue();
/*     */     }
/* 253 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentMana() {
/* 258 */     if (this.clientPlayer != null) {
/* 259 */       return this.clientPlayer.getStatSum(Short.valueOf((short)14)).intValue();
/*     */     }
/* 261 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentHealth() {
/* 266 */     if (this.clientPlayer != null) {
/* 267 */       return this.clientPlayer.getStatSum(Short.valueOf((short)12)).intValue();
/*     */     }
/* 269 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getCurrentXpFraction() {
/* 274 */     float startXp = this.clientPlayer.getStatSum(Short.valueOf((short)21)).floatValue();
/* 275 */     float endXp = this.clientPlayer.getStatSum(Short.valueOf((short)22)).floatValue();
/* 276 */     float xp = this.clientPlayer.getStatSum(Short.valueOf((short)0)).floatValue();
/* 277 */     if (xp > endXp)
/* 278 */       return 1.0F; 
/* 279 */     if (xp < startXp)
/* 280 */       return 0.0F; 
/* 281 */     float range = endXp - startXp;
/* 282 */     return (xp - startXp) / range;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getCurrentLevel() {
/* 287 */     return this.clientPlayer.getStatSum(Short.valueOf((short)20)).intValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public PetButtonModel getPet(int slot) {
/* 292 */     return new ClientPetModel((ClientPlayer)this.clientPlayer, slot);
/*     */   }
/*     */ 
/*     */   
/*     */   public MouseCursorSetter getCursorSetter() {
/* 297 */     return MainGameState.getMouseCursorSetter();
/*     */   }
/*     */ 
/*     */   
/*     */   public Cursor getUseCursor() {
/* 302 */     return (Cursor)TCGCursorSetter.BuiltinCursors.CURSOR_USE;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getActivePetSlot() {
/* 307 */     ClientPet activePet = this.clientPlayer.getActivePet();
/* 308 */     PetSlot[] pets = this.clientPlayer.getPetSlots();
/*     */     
/* 310 */     for (int i = 0; i < pets.length; i++) {
/* 311 */       if (pets[i].getPet() == activePet) {
/* 312 */         return i;
/*     */       }
/*     */     } 
/* 315 */     throw new IllegalStateException(String.format("Unselected pet is active???? activePet: %s, selectedPets: %s ", new Object[] { activePet, Arrays.toString(pets) }));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getHealthPotionsAmount() {
/* 322 */     InventoryItem item = findItemById("potion-health", this.clientPlayer.getInventory());
/* 323 */     if (item != null) {
/* 324 */       return item.getAmount();
/*     */     }
/* 326 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getManaPotionsAmount() {
/* 331 */     InventoryItem item = findItemById("potion-mana", this.clientPlayer.getInventory());
/* 332 */     if (item != null) {
/* 333 */       return item.getAmount();
/*     */     }
/* 335 */     return 0;
/*     */   }
/*     */   
/*     */   private InventoryItem findItemById(String itemId, Inventory inventory) {
/* 339 */     for (InventoryItem inventoryItem : inventory) {
/*     */       
/* 341 */       if (inventoryItem != null && inventoryItem.getClassId().equals(itemId))
/* 342 */         return inventoryItem; 
/*     */     } 
/* 344 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void chatButtonAction() {
/* 350 */     if (TcgGame.isChatEnabled() && MainGameState.getPlayerModel().getChatController().isChatControllerEnabled()) {
/* 351 */       MainGameState.getGuiWindowsController().toggleWindow(GameWindows.CHAT);
/*     */     } else {
/* 353 */       BWindow window = TcgUI.getWindowFromClass(GameWindows.CHAT.getWindowClass());
/* 354 */       if (window != null) {
/* 355 */         PanelManager.getInstance().closeWindow(window);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cannedChatButtonAction() {
/* 362 */     if (TcgGame.isChatEnabled() && MainGameState.getPlayerModel().getChatController().isChatControllerEnabled()) {
/* 363 */       MainGameState.getGuiWindowsController().toggleWindow(GameWindows.CANNED_CHAT);
/*     */     } else {
/* 365 */       BWindow window = TcgUI.getWindowFromClass(GameWindows.CANNED_CHAT.getWindowClass());
/* 366 */       if (window != null) {
/* 367 */         PanelManager.getInstance().closeWindow(window);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void friendsButtonAction() {
/* 374 */     if (TcgGame.isChatEnabled() && MainGameState.getPlayerModel().getChatController().isChatControllerEnabled()) {
/*     */       
/* 376 */       TcgGame.setAddFriendMode(FriendModeType.OFF);
/* 377 */       MainGameState.getGuiWindowsController().toggleWindow(GameWindows.FRIENDS);
/*     */     } else {
/* 379 */       BWindow window = TcgUI.getWindowFromClass(GameWindows.FRIENDS.getWindowClass());
/* 380 */       if (window != null) {
/* 381 */         PanelManager.getInstance().closeWindow(window);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void achievementPendingAction(int pendingMessages) {
/* 388 */     fireAchievementPending(pendingMessages);
/*     */   }
/*     */ 
/*     */   
/*     */   public void chatPendingAction(int pendingMessages) {
/* 393 */     fireChatPending(pendingMessages);
/*     */   }
/*     */ 
/*     */   
/*     */   public void friendPendingAction(int pendingMessages) {
/* 398 */     fireFriendPending(pendingMessages);
/*     */   }
/*     */ 
/*     */   
/*     */   public void characterButtonAction() {
/* 403 */     MainGameState.getGuiWindowsController().toggleWindow(GameWindows.CHARACTER);
/*     */   }
/*     */ 
/*     */   
/*     */   public void duelButtonAction(boolean selected) {
/* 408 */     if (TcgGame.isPetTutorial() || TcgGame.isEquipmentTutorial() || TcgGame.isTutorialMode()) {
/* 409 */       TcgGame.setStartDuelMode(false);
/*     */     } else {
/* 411 */       TcgGame.setStartDuelMode(selected);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void mapButtonAction() {
/* 417 */     MainGameState.getGuiWindowsController().toggleWindow(GameWindows.MAP);
/*     */   }
/*     */ 
/*     */   
/*     */   public void optionsButtonAction() {
/* 422 */     MainGameState.getGuiWindowsController().toggleWindow(GameWindows.MAIN_MENU);
/*     */   }
/*     */ 
/*     */   
/*     */   public void achievementsButtonAction() {
/* 427 */     MainGameState.getGuiWindowsController().toggleWindow(GameWindows.ACHIEVEMENTS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void petsButtonAction() {
/* 432 */     MainGameState.getGuiWindowsController().toggleWindow(GameWindows.PETS_AND_SKILLS);
/*     */   }
/*     */ 
/*     */   
/*     */   public void questsButtonAction() {
/* 437 */     MainGameState.getGuiWindowsController().toggleWindow(GameWindows.QUEST_WINDOW);
/*     */   }
/*     */ 
/*     */   
/*     */   private void addParticle(Point point, String effect) {
/* 442 */     ParticleSystem particles = (ParticleSystem)TcgGame.getResourceManager().getResource(ParticleSystem.class, effect);
/* 443 */     GuiParticleJoint guiParticle = new GuiParticleJoint(effect, new Vector2f(point.x, point.y), particles, DisplaySystem.getDisplaySystem().getRenderer().getCamera());
/*     */     
/* 445 */     MainGameState.getGuiParticlesRenderPass().addGuiParticleJoint(guiParticle);
/* 446 */     this.guiParticles.add(guiParticle);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addManaParticle(Point point) {
/* 451 */     addParticle(point, "guieffect_manadrink_blue.ops");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addHealthParticle(Point point) {
/* 456 */     addParticle(point, "guieffect_healthdrink_red.ops");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addXPParticle(Point point) {
/* 461 */     addParticle(point, "guieffect_xpgain.ops");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addHealthPotionParticle(Point point) {
/* 466 */     addParticle(point, "guieffect_health_pot_gain.ops");
/*     */   }
/*     */ 
/*     */   
/*     */   public void addManaPotionParticle(Point point) {
/* 471 */     addParticle(point, "guieffect_mana_pot_gain.ops");
/*     */   }
/*     */ 
/*     */   
/*     */   public SkillListModel getSkillListModel() {
/* 476 */     return (SkillListModel)MainGameState.getSkillListModel();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyLoadingScreenStarted(String toLoadMapName) {}
/*     */ 
/*     */   
/*     */   public void notifyLoadingScreenFinished(String loadedMapName) {
/* 485 */     readAllParticles();
/*     */ 
/*     */     
/* 488 */     if (!this.firstMapLoaded && MainGameState.isPlayerRegistered() && !this.clientPlayer.isSubscriber()) {
/* 489 */       SubscribeWindow window = new SubscribeWindow(TcgGame.getResourceManager(), MainGameState.isPlayerRegistered(), "subscribedialog.button.subscribe", "subscribedialog.button.cancel", "popup.login.nonmember");
/*     */ 
/*     */       
/* 492 */       window.setLayer(101);
/* 493 */       BuiSystem.getRootNode().addWindow((BWindow)window);
/*     */     } 
/* 495 */     this.firstMapLoaded = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void triggerXPParticles() {
/* 500 */     MainGameState.getGuiParticlesRenderPass().triggerParticleEffect("guieffect_xpgain.ops");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void readAllParticles() {
/* 508 */     for (GuiParticleJoint guiParticle : this.guiParticles) {
/* 509 */       ParticleSystem particles = (ParticleSystem)TcgGame.getResourceManager().getResource(ParticleSystem.class, guiParticle.getName());
/* 510 */       guiParticle.setParticleSystem(particles);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isTownPortalEnabled() {
/* 516 */     return this.townPortalEnabled;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void petSlotHover(int slot) {
/* 522 */     firePetSlotHover(slot);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\TCGClientHudModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */