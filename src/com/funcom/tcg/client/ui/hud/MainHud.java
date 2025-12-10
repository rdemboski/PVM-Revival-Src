/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.commons.jme.bui.HighlightedRegularButton;
/*     */ import com.funcom.commons.jme.bui.HighlightedToggleButton;
/*     */ import com.funcom.commons.jme.bui.IrregularLabel;
import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.funcom.commons.utils.ClientUtils;
/*     */ import com.funcom.gameengine.jme.text.HTMLView2;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
import com.funcom.tcg.client.ui.BPeelWindow;
import com.funcom.tcg.client.ui.BuiUtils;
import com.funcom.tcg.client.ui.Localizer;
import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.achievements.AchievementsWindow;
/*     */ import com.funcom.tcg.client.ui.character.CharacterEquipmentWindow;
/*     */ import com.funcom.tcg.client.ui.hud2.AbstractHudModel;
/*     */ import com.funcom.tcg.client.ui.hud2.HudModel;
/*     */ import com.funcom.tcg.client.ui.hud2.PetButtonModel;
/*     */ import com.funcom.tcg.client.ui.hud2.PetCardButton;
/*     */ import com.funcom.tcg.client.ui.hud2.SkillBarPetButtonModel;
/*     */ import com.funcom.tcg.client.ui.mainmenu.MainMenuWindow;
/*     */ import com.funcom.tcg.client.ui.mainmenu.OptionsWindow;
/*     */ import com.funcom.tcg.client.ui.maps.MapWindow2;
/*     */ import com.funcom.tcg.client.ui.pets3.PetWindowButtonModel;
/*     */ import com.funcom.tcg.client.ui.pets3.PetsWindow;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorFullWindow;
/*     */ import com.funcom.tcg.net.message.DuelRequestCancelMessage;
/*     */ import com.jme.input.MouseInput;
/*     */ import com.jme.renderer.Renderer;
import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BActiveProgressBar;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.BToggleButton;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.event.MouseListener;
/*     */ import com.jmex.bui.util.Point;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class MainHud extends BPeelWindow implements PartiallyNotInteractive {
/*     */   private static final double WARNING_LEVEL = 0.3D;
/*  51 */   private final int HEALTH = 0; 
            private static final double SAFE_LEVEL = 0.65D;
/*  52 */   private final int MANA = 1;
/*     */   
/*  54 */   private final int LEFT = 1;
/*  55 */   private final int CENTER = 0;
/*  56 */   private final int RIGHT = 2;
/*     */   
/*  58 */   private final int CENTER_PET = 1;
/*     */   
/*     */   private boolean manaWarning = true;
/*     */   
/*     */   private boolean healthWarning = true;
/*     */   private boolean isManaFlashing = false;
/*     */   private boolean isHealthFlashing = false;
/*  65 */   private int WINDOW_X = 0;
/*     */   
/*     */   private static final double FLASH_SPEED = 3.0D;
/*     */   
/*     */   private long petTrialTime;
/*     */   
/*     */   private IrregularLabel[] backgrounds;
/*     */   
/*     */   private BLabel goldBorder;
/*     */   
/*     */   private BLabel[] glossOverlays;
/*     */   
/*     */   private BClickthroughLabel[] hotkeys;
/*     */   
/*     */   private BClickthroughLabel[] progressGlows;
/*     */   
/*     */   private BLabel timerLabel;
/*     */   
/*     */   private PetCardButton[] petCardButtons;
/*     */   
/*     */   private BActiveProgressBar[] playerProgress;
/*     */   private HighlightedRegularButton[] addPotionBarButtons;
/*     */   private HighlightedButton[] addPotionButtons;
/*     */   private HighlightedButton chatButton;
/*     */   private HighlightedButton friendsButton;
/*     */   private HighlightedButton petsButton;
/*     */   private HighlightedButton achievementsButton;
/*     */   private HighlightedButton characterButton;
/*     */   private HighlightedButton questsButton;
/*     */   private HighlightedButton mapButton;
/*     */   private HighlightedButton optionsButton;
/*     */   private HighlightedButton pvpButton;
/*     */   private BToggleButton duelButton;
/*     */   private HighlightedButton portalButton;
/*     */   private HighlightedToggleButton pauseButton;
/*     */   private HudModel hudModel;
/*     */   private ResourceManager resourceManager;
/*     */   private long startTime;
/*     */   private AbstractHudModel.ChangeListenerAdapter listener;
/*     */   private boolean arrowVisible = false;
/*     */   private BButton duelCancelButton;
/*     */   
/*     */   public MainHud(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, HudModel hudModel, TCGToolTipManager toolTipManager) {
/* 108 */     super(windowName, bananaPeel);
/* 109 */     this.resourceManager = resourceManager;
/* 110 */     this.hudModel = hudModel;
/* 111 */     this.manaWarning = TcgGame.isTutorialMode();
/* 112 */     this.healthWarning = TcgGame.isTutorialMode();
/* 113 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/* 114 */     this._style = BuiUtils.createStyleSheet("/peeler/main_hud.bss", buiResourceProvider);
/*     */     
/* 116 */     this.startTime = System.currentTimeMillis();
/*     */     
/* 118 */     this.WINDOW_X = DisplaySystem.getDisplaySystem().getWidth() / 2 - getWidth() / 2;
/* 119 */     setLocation(this.WINDOW_X, 0);
/*     */     
/* 121 */     initComponents();
/* 122 */     initListeners();
/* 123 */     setLayer(2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 129 */     this.chatButton = new HighlightedButton();
/* 130 */     BComponent placeholder = findComponent((BContainer)this, "button_chat");
/* 131 */     overridePeelerComponent((BComponent)this.chatButton, placeholder);
/* 132 */     this.chatButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.chat", new String[0]));
/*     */     
/* 134 */     this.friendsButton = new HighlightedButton();
/* 135 */     placeholder = findComponent((BContainer)this, "button_friends");
/* 136 */     overridePeelerComponent((BComponent)this.friendsButton, placeholder);
/* 137 */     this.friendsButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.friends", new String[0]));
/*     */     
/* 139 */     this.characterButton = new HighlightedButton();
/* 140 */     placeholder = findComponent((BContainer)this, "button_character");
/* 141 */     overridePeelerComponent((BComponent)this.characterButton, placeholder);
/* 142 */     this.characterButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.character", new String[0]));
/*     */     
/* 144 */     this.questsButton = new HighlightedButton();
/* 145 */     placeholder = findComponent((BContainer)this, "button_quest");
/* 146 */     overridePeelerComponent((BComponent)this.questsButton, placeholder);
/* 147 */     this.questsButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.quests", new String[0]));
/*     */     
/* 149 */     this.mapButton = new HighlightedButton();
/* 150 */     placeholder = findComponent((BContainer)this, "button_map");
/* 151 */     overridePeelerComponent((BComponent)this.mapButton, placeholder);
/* 152 */     this.mapButton.setEnabled(!TcgGame.isTutorialMode());
/* 153 */     this.mapButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.maps", new String[0]));
/*     */     
/* 155 */     this.duelButton = new BToggleButton("");
/* 156 */     placeholder = findComponent((BContainer)this, "button_duel");
/* 157 */     overridePeelerComponent((BComponent)this.duelButton, placeholder);
/* 158 */     this.duelButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.duel", new String[0]));
/*     */     
/* 160 */     this.optionsButton = new HighlightedButton();
/* 161 */     placeholder = findComponent((BContainer)this, "button_options");
/* 162 */     overridePeelerComponent((BComponent)this.optionsButton, placeholder);
/* 163 */     this.optionsButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.options", new String[0]));
/*     */     
/* 165 */     this.pauseButton = new HighlightedToggleButton();
/* 166 */     placeholder = findComponent((BContainer)this, "button_pause");
/* 167 */     overridePeelerComponent((BComponent)this.pauseButton, placeholder);
/* 168 */     this.pauseButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.pause", new String[0]));
/*     */     
/* 170 */     this.portalButton = new HighlightedButton();
/* 171 */     placeholder = findComponent((BContainer)this, "button_portal");
/* 172 */     overridePeelerComponent((BComponent)this.portalButton, placeholder);
/* 173 */     this.portalButton.setEnabled((this.hudModel.isTownPortalEnabled() && !MainGameState.getWorld().getMapName().contains("research_centre")));
/* 174 */     this.portalButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.portal", new String[0]));
/*     */     
/* 176 */     this.achievementsButton = new HighlightedButton();
/* 177 */     placeholder = findComponent((BContainer)this, "button_achieve");
/* 178 */     overridePeelerComponent((BComponent)this.achievementsButton, placeholder);
/* 179 */     this.achievementsButton.setEnabled(!TcgGame.isTutorialMode());
/* 180 */     this.achievementsButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.achievements", new String[0]));
/*     */     
/* 182 */     this.petsButton = new HighlightedButton();
/* 183 */     placeholder = findComponent((BContainer)this, "button_petgui");
/* 184 */     overridePeelerComponent((BComponent)this.petsButton, placeholder);
/* 185 */     this.petsButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.pets", new String[0]));
/*     */     
/* 187 */     this.pvpButton = new HighlightedButton();
/* 188 */     placeholder = findComponent((BContainer)this, "button_pvp");
/* 189 */     overridePeelerComponent((BComponent)this.pvpButton, placeholder);
/* 190 */     this.pvpButton.setTooltipText(TcgGame.getLocalizedText("tooltips.hud.pvp", new String[0]));
/* 191 */     this.pvpButton.setEnabled(false);
/*     */ 
/*     */     
/* 194 */     this.petCardButtons = new PetCardButton[3];
/*     */     
/* 196 */     for (int i = 0; i < this.petCardButtons.length; i++) {
/* 197 */       SkillBarPetButtonModel model = new SkillBarPetButtonModel(this.resourceManager, null, null, null, this.hudModel, i);
/* 198 */       this.petCardButtons[i] = new PetCardButton((PetWindowButtonModel)model, MainGameState.getToolTipManager(), true, this.hudModel.getPet(i));
/* 199 */       placeholder = findComponent((BContainer)this, "button_petslot" + (i + 1));
/* 200 */       overridePeelerComponent((BComponent)this.petCardButtons[i], placeholder);
/* 201 */       this.petCardButtons[i].setTooltipText(TcgGame.getLocalizedText("tooltips.hud.petslot." + (i + 1), new String[0]));
/*     */     } 
/* 203 */     this.timerLabel = (BLabel)new BClickthroughLabel("");
/* 204 */     this.timerLabel.setStyleClass("timer-label");
/* 205 */     add((BComponent)this.timerLabel, this.petCardButtons[1].getBounds());
/*     */     
/* 207 */     int hud_areas = 3;
/* 208 */     this.backgrounds = new IrregularLabel[hud_areas];
/* 209 */     for (int j = 0; j < hud_areas; j++) {
/*     */       String loc;
/* 211 */       switch (j) {
/*     */         case 1:
/* 213 */           loc = "left";
/*     */           break;
/*     */         case 0:
/* 216 */           loc = "centerhud";
/*     */           break;
/*     */         case 2:
/* 219 */           loc = "right";
/*     */           break;
/*     */         default:
/* 222 */           loc = "";
/*     */           break;
/*     */       } 
/* 225 */       this.backgrounds[j] = new IrregularLabel("");
/* 226 */       placeholder = findComponent((BContainer)this, "graphic_bkg_" + loc);
/* 227 */       overridePeelerComponent((BComponent)this.backgrounds[j], placeholder);
/*     */     } 
/*     */ 
/*     */     
/* 231 */     int progress_areas = 2;
/* 232 */     this.addPotionBarButtons = new HighlightedRegularButton[progress_areas];
/* 233 */     this.addPotionButtons = new HighlightedButton[progress_areas];
/* 234 */     this.progressGlows = new BClickthroughLabel[progress_areas];
/* 235 */     this.glossOverlays = new BLabel[progress_areas];
/* 236 */     this.hotkeys = new BClickthroughLabel[progress_areas];
/* 237 */     this.playerProgress = new BActiveProgressBar[progress_areas];
/*     */     
/* 239 */     for (int k = 0; k < progress_areas; k++) {
/* 240 */       String type = (k == 0) ? "health" : "mana";
/*     */ 
/*     */       
/* 243 */       switch (k) {
/*     */         case 0:
/* 245 */           placeholder = findComponent((BContainer)this, "graphic_bar_" + type);
/* 246 */           this.playerProgress[k] = new BActiveProgressBar(BProgressBar.Direction.PROGRESSDIR_EAST, 100L, 0.2F)
/*     */             {
/*     */               public String getTooltipText() {
/* 249 */                 return MainHud.this.hudModel.getHealthBubbleHtml();
/*     */               }
/*     */ 
/*     */               
/*     */               protected BComponent createTooltipComponent(String tiptext) {
/* 254 */                 return (BComponent)new HTMLView2(tiptext, MainHud.this.resourceManager);
/*     */               }
/*     */             };
/* 257 */           Point p = new Point(this.WINDOW_X + placeholder.getX() + placeholder.getWidth() / 2, placeholder.getY() + placeholder.getHeight() / 2);
/*     */           
/* 259 */           this.hudModel.addHealthParticle(p);
/*     */           break;
/*     */         case 1:
/* 262 */           placeholder = findComponent((BContainer)this, "graphic_bar_" + type);
/* 263 */           this.playerProgress[k] = new BActiveProgressBar(BProgressBar.Direction.WEST, 100L, 0.2F)
/*     */             {
/*     */               public String getTooltipText() {
/* 266 */                 return MainHud.this.hudModel.getManaBubbleHtml();
/*     */               }
/*     */ 
/*     */               
/*     */               protected BComponent createTooltipComponent(String tiptext) {
/* 271 */                 return (BComponent)new HTMLView2(tiptext, MainHud.this.resourceManager);
/*     */               }
/*     */             };
/* 274 */           p = new Point(this.WINDOW_X + placeholder.getX() + placeholder.getWidth() / 2, placeholder.getY() + placeholder.getHeight() / 2);
/*     */           
/* 276 */           this.hudModel.addManaParticle(p);
/*     */           break;
/*     */         default:
/* 279 */           type = "";
/*     */           break;
/*     */       } 
/*     */       
/* 283 */       placeholder = findComponent((BContainer)this, "graphic_bar_" + type);
/* 284 */       overridePeelerComponent((BComponent)this.playerProgress[k], placeholder);
/* 285 */       this.playerProgress[k].setStyleClass((k == 0) ? "progress.health" : "progress.mana");
/* 286 */       this.playerProgress[k].setProgress((k == 0) ? this.hudModel.getCurrentHealthFraction() : this.hudModel.getCurrentManaFraction());
/*     */ 
/*     */       
/* 289 */       this.progressGlows[k] = new BClickthroughLabel("");
/* 290 */       placeholder = findComponent((BContainer)this, "graphic_glow_bar_" + type);
/* 291 */       overridePeelerComponent((BComponent)this.progressGlows[k], placeholder);
/* 292 */       this.progressGlows[k].setAlpha(0.0F);
/*     */       
/* 294 */       this.addPotionBarButtons[k] = new HighlightedRegularButton();
/* 295 */       placeholder = findComponent((BContainer)this, "button_" + type + "bar");
/* 296 */       overridePeelerComponent((BComponent)this.addPotionBarButtons[k], placeholder);
/* 297 */       this.addPotionBarButtons[k].setTooltipText(TcgGame.getLocalizedText("tooltips.hud." + type, new String[] { String.valueOf((k == 0) ? this.hudModel.getCurrentHealth() : this.hudModel.getCurrentMana()), String.valueOf((k == 0) ? this.hudModel.getMaxHealth() : this.hudModel.getMaxMana()) }));
/*     */ 
/*     */ 
/*     */       
/* 301 */       this.addPotionButtons[k] = new HighlightedButton();
/* 302 */       placeholder = findComponent((BContainer)this, "button_potion_" + type);
/* 303 */       overridePeelerComponent((BComponent)this.addPotionButtons[k], placeholder);
/* 304 */       this.addPotionButtons[k].setText("" + ((k == 0) ? this.hudModel.getHealthPotionsAmount() : this.hudModel.getManaPotionsAmount()));
/*     */       
/* 306 */       this.addPotionButtons[k].setTooltipText(TcgGame.getLocalizedText("tooltips.hud." + type + ".potions", new String[0]));
/* 307 */       Point p = new Point(this.WINDOW_X + placeholder.getX() + placeholder.getWidth() / 2, placeholder.getY() + placeholder.getHeight() / 2);
/*     */       
/* 309 */       if (k == 0) {
/* 310 */         this.hudModel.addHealthPotionParticle(p);
/*     */       } else {
/* 312 */         this.hudModel.addManaPotionParticle(p);
/*     */       } 
/*     */ 
/*     */       
/* 316 */       this.glossOverlays[k] = (BLabel)new BClickthroughLabel("");
/* 317 */       placeholder = findComponent((BContainer)this, "graphic_overlay_" + type + "gloss");
/* 318 */       overridePeelerComponent((BComponent)this.glossOverlays[k], placeholder);
/* 319 */       this.glossOverlays[k].setAlpha(0.0F);
/*     */       
/* 321 */       this.hotkeys[k] = new BClickthroughLabel("");
/* 322 */       placeholder = findComponent((BContainer)this, "text_hotkey_" + type);
/* 323 */       overridePeelerComponent((BComponent)this.hotkeys[k], placeholder);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 330 */     this.petCardButtons[this.hudModel.getActivePetSlot()].setSelected(true);
/*     */     
/* 332 */     this.duelCancelButton = new BButton("");
/* 333 */     this.duelCancelButton.setStyleClass("close-button");
/* 334 */     this.duelCancelButton.setTooltipText(TcgGame.getLocalizedText("duel.request.cancel", new String[0]));
/*     */     
/* 336 */     this.duelCancelButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*     */             try {
/* 340 */               NetworkHandler.instance().getIOHandler().send((Message)new DuelRequestCancelMessage(MainGameState.getPlayerModel().getId()));
/* 341 */             } catch (InterruptedException e) {
/* 342 */               throw new RuntimeException(e);
/*     */             } 
/* 344 */             MainHud.this.duelCancelButton.setVisible(false);
/*     */           }
/*     */         });
/* 347 */     add((BComponent)this.duelCancelButton, new Rectangle(this.duelButton.getX() + 30, this.duelButton.getY() + 30, 24, 24));
/* 348 */     this.duelCancelButton.setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void layout() {
/* 353 */     super.layout();
/* 354 */     for (int i = 0; i < this.petCardButtons.length; i++) {
/* 355 */       this.petCardButtons[i].update(10000L);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 361 */     this.hudModel.removeChangeListener((HudModel.ChangeListener)this.listener);
/* 362 */     for (int i = 0; i < this.petCardButtons.length; i++) {
/* 363 */       this.petCardButtons[i].dismiss();
/*     */     }
/* 365 */     super.dismiss();
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 369 */     for (int i = 0; i < this.petCardButtons.length; i++) {
/* 370 */       final int slot = i;
/* 371 */       this.petCardButtons[i].setPetModel(this.hudModel.getPet(i));
/* 372 */       this.petCardButtons[i].setTooltipText(TcgGame.getLocalizedText("tooltips.hud.petslot." + (slot + 1), new String[0]) + (!this.hudModel.getPet(i).getDescription().isEmpty() ? (": " + this.hudModel.getPet(i).getName()) : (MainGameState.isPlayerSubscriber() ? "" : (": " + TcgGame.getLocalizedText("members.only", new String[0])))));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 378 */       this.petCardButtons[i].addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent actionEvent) {
/* 381 */               MainHud.this.hudModel.petButtonAction(slot);
/*     */             }
/*     */           });
/*     */       
/* 385 */       final int finalI = i;
/* 386 */       this.petCardButtons[i].addListener((ComponentListener)new MouseListener()
/*     */           {
/*     */             public void mouseEntered(MouseEvent event) {
/* 389 */               if (MainHud.this.hudModel.getPet(finalI) != null) {
/* 390 */                 MainHud.this.hudModel.petSlotHover(finalI);
/*     */               }
/*     */             }
/*     */ 
/*     */             
/*     */             public void mouseExited(MouseEvent event) {
/* 396 */               MainHud.this.hudModel.petSlotHover(-1);
/*     */             }
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             public void mousePressed(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */             
/*     */             public void mouseReleased(MouseEvent event) {}
/*     */           });
/*     */     } 
/* 409 */     this.listener = new AbstractHudModel.ChangeListenerAdapter()
/*     */       {
/*     */         public void chatPending(int pendingMessages)
/*     */         {
/* 413 */           MainHud.this.chatButton.setHighlighted((pendingMessages > 0));
/* 414 */           MainHud.this.chatButton.setText((pendingMessages > 0) ? ("" + pendingMessages) : "");
/*     */         }
/*     */ 
/*     */         
/*     */         public void friendPending(int pendingMessages) {
/* 419 */           MainHud.this.friendsButton.setHighlighted((pendingMessages > 0));
/* 420 */           MainHud.this.friendsButton.setText((pendingMessages > 0) ? ("" + pendingMessages) : "");
/*     */         }
/*     */ 
/*     */         
/*     */         public void achievementPending(int pendingMessages) {
/* 425 */           MainHud.this.achievementsButton.setHighlighted((pendingMessages > 0));
/* 426 */           MainHud.this.achievementsButton.setText((pendingMessages > 0) ? ("" + pendingMessages) : "");
/*     */         }
/*     */ 
/*     */         
/*     */         public void petChanged(int slot, PetButtonModel model) {
/* 431 */           MainHud.this.petCardButtons[slot].setPetModel(model);
/* 432 */           MainHud.this.petCardButtons[slot].setTooltipText(TcgGame.getLocalizedText("tooltips.hud.petslot." + (slot + 1), new String[0]) + (!model.getName().isEmpty() ? (": " + model.getName()) : ""));
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void activePetSelected(int slot) {
/* 438 */           MainHud.this.setActivePetSlot(slot);
/*     */         }
/*     */ 
/*     */         
/*     */         public void manaChanged(float fraction) {
/* 443 */           MainHud.this.playerProgress[1].setProgress(fraction);
/* 444 */           MainHud.this.addPotionBarButtons[1].setTooltipText(TcgGame.getLocalizedText("tooltips.hud.mana", new String[] { String.valueOf(MainHud.this.hudModel.getCurrentMana()), String.valueOf(MainHud.this.hudModel.getMaxMana()) }));
/*     */           
/* 446 */           if (fraction < 0.3D) {
/* 447 */             MainHud.this.triggerOrContinueFlash(1);
/* 448 */             if (MainHud.this.manaWarning && !TcgGame.isPetTutorial() && !TcgGame.isEquipmentTutorial()) {
/* 449 */               MainGameState.getArrowWindow().setLocation(MainHud.this.playerProgress[1].getAbsoluteX() + MainHud.this.playerProgress[1].getWidth() / 2 - 100, MainHud.this.playerProgress[1].getAbsoluteY() + MainHud.this.playerProgress[1].getHeight());
/*     */ 
/*     */ 
/*     */               
/* 453 */               MainGameState.getArrowWindow().updateInfoText(TcgGame.getLocalizedText("arrowwindow.usemana", new String[0]));
/* 454 */               MainGameState.getArrowWindow().setVisible(true);
/* 455 */               MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/* 456 */               MainHud.this.arrowVisible = true;
/*     */             } 
/*     */           } else {
/* 459 */             MainHud.this.endFlash(1);
/* 460 */             if (fraction > 0.65D && MainGameState.getArrowWindow().getInfoText().equals(TcgGame.getLocalizedText("arrowwindow.usemana", new String[0])))
/*     */             {
/* 462 */               MainGameState.getArrowWindow().setVisible(false); } 
/* 463 */             MainHud.this.arrowVisible = false;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void manaPotionsAmountChanged(int newAmount) {
/* 469 */           MainHud.this.addPotionButtons[1].setText("" + newAmount);
/*     */         }
/*     */ 
/*     */         
/*     */         public void healthChanged(float fraction) {
/* 474 */           MainHud.this.playerProgress[0].setProgress(fraction);
/* 475 */           MainHud.this.addPotionBarButtons[0].setTooltipText(TcgGame.getLocalizedText("tooltips.hud.health", new String[] { String.valueOf(MainHud.this.hudModel.getCurrentHealth()), String.valueOf(MainHud.this.hudModel.getMaxHealth()) }));
/*     */           
/* 477 */           if (fraction < 0.3D) {
/* 478 */             MainHud.this.triggerOrContinueFlash(0);
/* 479 */             if (MainHud.this.healthWarning && !TcgGame.isPetTutorial() && !TcgGame.isEquipmentTutorial()) {
/* 480 */               MainGameState.getArrowWindow().setLocation(MainHud.this.playerProgress[0].getAbsoluteX() + MainHud.this.playerProgress[0].getWidth() / 2 - 100, MainHud.this.playerProgress[0].getAbsoluteY() + MainHud.this.playerProgress[0].getHeight());
/*     */ 
/*     */ 
/*     */               
/* 484 */               MainGameState.getArrowWindow().updateInfoText(TcgGame.getLocalizedText("arrowwindow.usehealth", new String[0]));
/* 485 */               MainGameState.getArrowWindow().setVisible(true);
/* 486 */               MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/* 487 */               MainHud.this.arrowVisible = true;
/*     */             } 
/*     */           } else {
/* 490 */             MainHud.this.endFlash(0);
/* 491 */             if (fraction > 0.65D && MainGameState.getArrowWindow().getInfoText().equals(TcgGame.getLocalizedText("arrowwindow.usehealth", new String[0])))
/*     */             {
/* 493 */               MainGameState.getArrowWindow().setVisible(false); } 
/* 494 */             MainHud.this.arrowVisible = false;
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public void healthPotionsAmountChanged(int newAmount) {
/* 500 */           MainHud.this.addPotionButtons[0].setText("" + newAmount);
/*     */         }
/*     */ 
/*     */         
/*     */         public void itemPickedUp(ItemDescription itemDesc) {
/* 505 */           MainHud.this.characterButton.setHighlighted(true);
/*     */         }
/*     */ 
/*     */         
/*     */         public void petPickedUp() {
/* 510 */           MainHud.this.petsButton.setHighlighted(true);
/*     */         }
/*     */ 
/*     */         
/*     */         public void petWindowToggled() {
/* 515 */           MainHud.this.petsButton.setHighlighted(false);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void characterWindowToggled() {
/* 523 */           MainHud.this.characterButton.setHighlighted(false);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void mapWindowToggled() {
/* 531 */           MainHud.this.mapButton.setHighlighted(false);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void optionsWindowToggled() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void chatWindowToggled() {
/* 547 */           MainHud.this.chatButton.setHighlighted(false);
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         public void townPortalVisibilityChanged(boolean visible) {
/* 555 */           MainHud.this.portalButton.setEnabled((visible && !MainGameState.getWorld().getMapName().contains("research_centre")));
/*     */         }
/*     */       };
/* 558 */     this.hudModel.addChangeListener((HudModel.ChangeListener)this.listener);
/*     */     
/* 560 */     this.optionsButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 563 */             MainHud.this.hudModel.optionsButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 567 */     this.duelButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 570 */             MainHud.this.hudModel.duelButtonAction(MainHud.this.duelButton.isSelected());
/*     */           }
/*     */         });
/*     */     
/* 574 */     this.mapButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 577 */             MainHud.this.hudModel.mapButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 581 */     this.characterButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 584 */             MainHud.this.hudModel.characterButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 588 */     this.achievementsButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 591 */             MainHud.this.hudModel.achievementsButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 595 */     this.pauseButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 598 */             if (MainGameState.getPauseModel().isPaused()) {
/* 599 */               if (!TcgUI.isWindowOpen(PetsWindow.class) && !TcgUI.isWindowOpen(MapWindow2.class) && !TcgUI.isWindowOpen(CharacterEquipmentWindow.class) && !TcgUI.isWindowOpen(VendorFullWindow.class) && !TcgUI.isWindowOpen(MainMenuWindow.class) && !TcgUI.isWindowOpen(OptionsWindow.class) && !TcgUI.isWindowOpen(AchievementsWindow.class))
/*     */               {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */                 
/* 606 */                 MainGameState.getPauseModel().reset();
/*     */               }
/*     */             } else {
/*     */               
/* 610 */               MainGameState.getPauseModel().activatePause();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 615 */     this.portalButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 618 */             MainHud.this.hudModel.townportalAction();
/*     */           }
/*     */         });
/*     */     
/* 622 */     this.petsButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 625 */             MainHud.this.hudModel.petsButtonAction();
/*     */           }
/*     */         });
/* 628 */     this.questsButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 631 */             MainHud.this.hudModel.questsButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 635 */     this.chatButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 638 */             MainHud.this.hudModel.chatButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 642 */     this.friendsButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 645 */             MainHud.this.hudModel.friendsButtonAction();
/*     */           }
/*     */         });
/*     */     
/* 649 */     this.addPotionButtons[0].addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 652 */             MainHud.this.hudModel.healingAction();
/* 653 */             if (MainHud.this.healthWarning && MainHud.this.arrowVisible) {
/* 654 */               MainHud.this.healthWarning = false;
/* 655 */               MainGameState.getArrowWindow().setVisible(false);
/* 656 */               MainHud.this.arrowVisible = false;
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 661 */     this.addPotionButtons[1].addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 664 */             if (MainHud.this.manaWarning && MainHud.this.arrowVisible) {
/* 665 */               MainHud.this.manaWarning = false;
/* 666 */               MainGameState.getArrowWindow().setVisible(false);
/* 667 */               MainHud.this.arrowVisible = false;
/*     */             } 
/* 669 */             MainHud.this.hudModel.manaAction();
/*     */           }
/*     */         });
/*     */     
/* 673 */     this.addPotionBarButtons[0].addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 676 */             if (MainHud.this.healthWarning && MainHud.this.arrowVisible) {
/* 677 */               MainHud.this.healthWarning = false;
/* 678 */               MainGameState.getArrowWindow().setVisible(false);
/* 679 */               MainHud.this.arrowVisible = false;
/*     */             } 
/* 681 */             MainHud.this.hudModel.healingAction();
/*     */           }
/*     */         });
/*     */     
/* 685 */     this.addPotionBarButtons[1].addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 688 */             if (MainHud.this.manaWarning && MainHud.this.arrowVisible) {
/* 689 */               MainHud.this.manaWarning = false;
/* 690 */               MainGameState.getArrowWindow().setVisible(false);
/* 691 */               MainHud.this.arrowVisible = false;
/*     */             } 
/* 693 */             MainHud.this.hudModel.manaAction();
/*     */           }
/*     */         });
/*     */     
/* 697 */     this.addPotionBarButtons[0].addListener((ComponentListener)new MouseListener()
/*     */         {
/*     */           public void mouseEntered(MouseEvent event) {
/* 700 */             MainHud.this.addPotionButtons[0].setFakeHover(true);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseExited(MouseEvent event) {
/* 705 */             MainHud.this.addPotionButtons[0].setFakeHover(false);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void mousePressed(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent event) {}
/*     */         });
/* 717 */     this.addPotionBarButtons[1].addListener((ComponentListener)new MouseListener()
/*     */         {
/*     */           public void mouseEntered(MouseEvent event) {
/* 720 */             MainHud.this.addPotionButtons[1].setFakeHover(true);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseExited(MouseEvent event) {
/* 725 */             MainHud.this.addPotionButtons[1].setFakeHover(false);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void mousePressed(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent event) {}
/*     */         });
/* 737 */     this.addPotionButtons[0].addListener((ComponentListener)new MouseListener()
/*     */         {
/*     */           public void mouseEntered(MouseEvent event) {
/* 740 */             MainHud.this.addPotionBarButtons[0].setFakeHover(true);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseExited(MouseEvent event) {
/* 745 */             MainHud.this.addPotionBarButtons[0].setFakeHover(false);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void mousePressed(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent event) {}
/*     */         });
/* 757 */     this.addPotionButtons[1].addListener((ComponentListener)new MouseListener()
/*     */         {
/*     */           public void mouseEntered(MouseEvent event) {
/* 760 */             MainHud.this.addPotionBarButtons[1].setFakeHover(true);
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseExited(MouseEvent event) {
/* 765 */             MainHud.this.addPotionBarButtons[1].setFakeHover(false);
/*     */           }
/*     */ 
/*     */ 
/*     */           
/*     */           public void mousePressed(MouseEvent event) {}
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent event) {}
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void endFlash(int type) {
/* 779 */     switch (type) {
/*     */       case 0:
/* 781 */         if (!this.isHealthFlashing) {
/*     */           return;
/*     */         }
/* 784 */         this.isHealthFlashing = false;
/*     */         break;
/*     */       case 1:
/* 787 */         if (!this.isManaFlashing) {
/*     */           return;
/*     */         }
/* 790 */         this.isManaFlashing = false;
/*     */         break;
/*     */       default:
/*     */         return;
/*     */     } 
/* 795 */     this.progressGlows[type].setAlpha(0.0F);
/*     */   }
/*     */   
/*     */   private void triggerOrContinueFlash(int type) {
/* 799 */     if (TcgGame.isDueling()) {
/* 800 */       endFlash(0);
/* 801 */       endFlash(1);
/*     */       return;
/*     */     } 
/* 804 */     switch (type) {
/*     */       case 0:
/* 806 */         if (this.isHealthFlashing) {
/*     */           return;
/*     */         }
/* 809 */         this.isHealthFlashing = true;
/*     */         break;
/*     */       case 1:
/* 812 */         if (this.isManaFlashing) {
/*     */           return;
/*     */         }
/* 815 */         this.isManaFlashing = true;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 824 */     int mx = MouseInput.get().getXAbsolute();
/* 825 */     int my = MouseInput.get().getYAbsolute();
/* 826 */     return isHit(mx, my);
/*     */   }
/*     */   
/*     */   public boolean isHit(int mx, int my) {
/* 830 */     boolean hit = subWindowsHit(mx, my);
/* 831 */     return hit;
/*     */   }
/*     */   
/*     */   private boolean subWindowsHit(int mx, int my) {
/* 835 */     mx -= this.WINDOW_X;
/* 836 */     for (IrregularLabel bgd : this.backgrounds) {
/* 837 */       if (bgd.getHitComponent(mx, my) != null) {
/* 838 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 842 */     for (BActiveProgressBar bar : this.playerProgress) {
/* 843 */       if (bar.getHitComponent(mx, my) != null) {
/* 844 */         return true;
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 853 */     for (HighlightedButton addPotionButton : this.addPotionButtons) {
/* 854 */       if (addPotionButton.getHitComponent(mx, my) != null) {
/* 855 */         return true;
/*     */       }
/*     */     } 
/*     */     
/* 859 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 864 */     double timeSeconds = ((float)(System.currentTimeMillis() - this.startTime) / 1000.0F);
/*     */     
/* 866 */     if (this.isHealthFlashing || this.isManaFlashing) {
/* 867 */       float v = (float)Math.abs(Math.sin(timeSeconds * 3.0D));
/* 868 */       if (this.isHealthFlashing) {
/* 869 */         this.progressGlows[0].setAlpha(v);
/*     */       }
/* 871 */       if (this.isManaFlashing) {
/* 872 */         this.progressGlows[1].setAlpha(v);
/*     */       }
/*     */     } 
/* 875 */     updateTimerLabel(this.hudModel.getPet(1).getPetSlot().getPet());
/* 876 */     if (this.timerLabel.isVisible()) {
/* 877 */       String timeText = ClientUtils.toTimeString(ClientUtils.calcPassedSeconds(this.petTrialTime, 0L), (Localizer)MainGameState.getInstance(), MainHud.class);
/*     */ 
/*     */       
/* 880 */       if (!this.timerLabel.getText().equals(timeText)) {
/* 881 */         this.timerLabel.setText(timeText);
/* 882 */         this.timerLabel.render(renderer);
/*     */       } 
/*     */     } 
/* 885 */     super.renderComponent(renderer);
/*     */   }
/*     */   
/*     */   private void updateTimerLabel(ClientPet pet) {
/* 889 */     if (pet != null && pet.isOnTrial() && !MainGameState.isPlayerSubscriber()) {
/* 890 */       this.petTrialTime = pet.getPetTrialExpireTime();
/* 891 */       this.timerLabel.setVisible(true);
/*     */     } else {
/*     */       
/* 894 */       this.timerLabel.setVisible(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setActivePetSlot(int slot) {
/* 899 */     for (int i = 0; i < this.petCardButtons.length; i++) {
/* 900 */       this.petCardButtons[i].setSelected((slot == i));
/* 901 */       this.petCardButtons[i].modelChanged();
/* 902 */       this.petCardButtons[i].update(10000L);
/*     */     } 
/*     */   }
/*     */   
/*     */   public HighlightedButton getPetsButton() {
/* 907 */     return this.petsButton;
/*     */   }
/*     */   
/*     */   public HighlightedButton getCharacterButton() {
/* 911 */     return this.characterButton;
/*     */   }
/*     */   
/*     */   public HighlightedButton getAchievementsButton() {
/* 915 */     return this.achievementsButton;
/*     */   }
/*     */   
/*     */   public void setAchievementsButton(HighlightedButton achievementsButton) {
/* 919 */     this.achievementsButton = achievementsButton;
/*     */   }
/*     */   
/*     */   public HighlightedButton getMapButton() {
/* 923 */     return this.mapButton;
/*     */   }
/*     */   
/*     */   public HighlightedButton getQuestsButton() {
/* 927 */     return this.questsButton;
/*     */   }
/*     */   
/*     */   public HighlightedToggleButton getPauseButton() {
/* 931 */     return this.pauseButton;
/*     */   }
/*     */   
/*     */   public BToggleButton getDuelButton() {
/* 935 */     return this.duelButton;
/*     */   }
/*     */   
/*     */   public BButton getDuelCancelButton() {
/* 939 */     return this.duelCancelButton;
/*     */   }
/*     */   
/*     */   public HighlightedButton getPortalButton() {
/* 943 */     return this.portalButton;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\MainHud.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */