/*     */ package com.funcom.tcg.client.ui.character;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.commons.jme.bui.IrregularButton;
import com.jmex.bui.layout.BLayoutManager;
import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.NoLocatorException;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.peeler.BananaPeel;
import com.funcom.rpgengine2.Stat;
import com.funcom.rpgengine2.StatListener;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.state.MainGameState;
import com.funcom.tcg.client.ui.AbstractFauxWindow;
import com.funcom.tcg.client.ui.AbstractLargeWindow;
import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.event.SubscriberChangedListener;
/*     */ import com.funcom.tcg.client.ui.hud.QuestTextType;
/*     */ import com.funcom.tcg.client.ui.hud.TutorialArrowDirection;
/*     */ import com.funcom.tcg.client.ui.hud2.AbstractHudModel;
/*     */ import com.funcom.tcg.client.ui.hud2.HudModel;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.system.DisplaySystem;
import com.jme.util.Timer;
import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BScrollBar;
/*     */ import com.jmex.bui.BToggleButton;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
import com.jmex.bui.event.ChangeEvent;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseEvent;
import com.jmex.bui.event.MouseListener;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class CharacterEquipmentWindow extends AbstractLargeWindow {
/*  43 */   private CharacterInfoWindow characterInfoWindow = null; private final CharacterWindowModel windowModel;
/*     */   private EquipDollContainer equipDollContainer;
/*     */   private final SelectionHandler selectionHandler;
/*     */   private AbstractHudModel.ChangeListenerAdapter listener;
/*     */   private HudModel hudModel;
/*  48 */   private final int NOAH_LAYER = 100;
/*     */   
/*     */   private long startTime;
/*     */   
/*  52 */   private ItemDescription newestItem = null;
/*     */   
/*     */   private EquipmentTutorialStep step;
/*     */   
/*     */   private CharacterContainer characterContainer;
/*     */   
/*     */   private InventoryEquipmentsContainer inventoryEquipmentsContainer;
/*     */   
/*     */   private BLabel tutorialHighlightLabel;
/*     */   
/*     */   private float elapsed;
/*     */   
/*     */   private IrregularButton infoButton;
/*     */   private BToggleButton iconBgd;
/*     */   private BClickthroughLabel itemIconLabel;
/*     */   private String petIcon;
/*     */   private boolean mouseInPet = false;
/*     */   private Rectangle ITEM_ICON_SIZE;
/*     */   private EquipmentStatsContainer equipmentStatsContainer;
/*     */   
/*     */   public CharacterEquipmentWindow(CharacterWindowModel windowModel, ResourceManager resourceManager, TCGToolTipManager tooltipManager, DireEffectDescriptionFactory direEffectDescriptionFactory, VisualRegistry visualRegistry, Localizer localizer, HudModel hudModel) {
/*  73 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  74 */     this.startTime = System.currentTimeMillis();
/*  75 */     this.windowModel = windowModel;
/*  76 */     this.hudModel = hudModel;
/*  77 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  78 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*  79 */     this.selectionHandler = new SelectionHandler(windowModel);
/*     */     
/*  81 */     initInventoryEquipmentsContainer(windowModel, resourceManager, tooltipManager);
/*     */     
/*  83 */     initEquipmentStatsContainer(windowModel, resourceManager, localizer);
/*     */     
/*  85 */     initTabs(windowModel, resourceManager, tooltipManager);
/*     */     
/*  87 */     initCharacterContainer(windowModel, resourceManager, direEffectDescriptionFactory, visualRegistry, localizer);
/*     */     
/*  89 */     addInfoButton();
/*     */     
/*  91 */     addDefaultCloseButton(AbstractFauxWindow.CloseButtonPosition.TOP_RIGHT);
/*     */ 
/*     */     
/*  94 */     this.tutorialHighlightLabel = new BLabel("");
/*  95 */     this.tutorialHighlightLabel.setStyleClass("highlight-label");
/*     */     
/*  97 */     this.listener = new AbstractHudModel.ChangeListenerAdapter()
/*     */       {
/*     */         public void itemPickedUp(ItemDescription itemDesc)
/*     */         {
/* 101 */           if (itemDesc.getItemType().isEquipable()) {
/* 102 */             CharacterEquipmentWindow.this.selectionHandler.setSlotIdSelected(itemDesc.getItemType().getEquipValue());
/* 103 */             CharacterEquipmentWindow.this.newestItem = itemDesc;
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void activePetSelected(int slot) {
/* 110 */           ClientPet pet = MainGameState.getPlayerModel().getActivePet();
/* 111 */           CharacterEquipmentWindow.this.updatePetImage(pet);
/* 112 */           CharacterEquipmentWindow.this.equipmentStatsContainer.petHover(CharacterEquipmentWindow.this.mouseInPet, pet);
/*     */         }
/*     */ 
/*     */         
/*     */         public void petSlotHovered(int slot) {
/* 117 */           if ((slot == 0 || slot == 1 || slot == 2) && MainGameState.getPlayerModel().getPetSlot(slot).getPet() != null) {
/* 118 */             CharacterEquipmentWindow.this.equipmentStatsContainer.petHover(true, MainGameState.getPlayerModel().getPetSlot(slot).getPet());
/*     */           }
/* 120 */           else if (CharacterEquipmentWindow.this.mouseInPet) {
/* 121 */             CharacterEquipmentWindow.this.equipmentStatsContainer.petHover(true, MainGameState.getPlayerModel().getActivePet());
/*     */           } else {
/* 123 */             CharacterEquipmentWindow.this.equipmentStatsContainer.petHover(false, (ClientPet)null);
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 128 */     this.hudModel.addChangeListener((HudModel.ChangeListener)this.listener);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 133 */     this.hudModel.removeChangeListener((HudModel.ChangeListener)this.listener);
/* 134 */     super.dismiss();
/*     */   }
/*     */   
/*     */   private void addInfoButton() {
/* 138 */     this.infoButton = new IrregularButton("");
/* 139 */     this.infoButton.setStyleClass("info_button");
/* 140 */     int closeButtonSize = 24;
/* 141 */     this.fauxWindow.add((BComponent)this.infoButton, new Rectangle(5, this.fauxWindow.getHeight() - closeButtonSize - 5, closeButtonSize, closeButtonSize));
/* 142 */     this.infoButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.help", new String[0]));
/*     */     
/* 144 */     this.infoButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 147 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 148 */             if (CharacterEquipmentWindow.this.characterInfoWindow == null) {
/* 149 */               String infoWindowPath = "gui/peeler/info_characterwindow_v1.xml";
/* 150 */               BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, infoWindowPath, CacheType.CACHE_PERMANENTLY);
/*     */ 
/*     */               
/* 153 */               CharacterEquipmentWindow.this.characterInfoWindow = new CharacterInfoWindow(infoWindowPath, bananaPeel, TcgGame.getResourceManager());
/*     */             } 
/* 155 */             CharacterEquipmentWindow.this.characterInfoWindow.setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - CharacterEquipmentWindow.this.characterInfoWindow.getWidth() / 2, DisplaySystem.getDisplaySystem().getHeight() / 2 - CharacterEquipmentWindow.this.characterInfoWindow.getHeight() / 2);
/*     */ 
/*     */             
/* 158 */             CharacterEquipmentWindow.this.characterInfoWindow.setLayer(4);
/* 159 */             if (!BuiSystem.getRootNode().getAllWindows().contains(CharacterEquipmentWindow.this.characterInfoWindow)) {
/* 160 */               BuiSystem.addWindow((BWindow)CharacterEquipmentWindow.this.characterInfoWindow);
/*     */             } else {
/* 162 */               BuiSystem.removeWindow((BWindow)CharacterEquipmentWindow.this.characterInfoWindow);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initCharacterContainer(CharacterWindowModel windowModel, ResourceManager resourceManager, DireEffectDescriptionFactory direEffectDescriptionFactory, VisualRegistry visualRegistry, Localizer localizer) {
/* 172 */     int width = 400, height = 625;
/*     */     
/* 174 */     ClientPlayer clientPlayer = windowModel.getClientPlayer();
/* 175 */     this.characterContainer = new CharacterContainer(clientPlayer, resourceManager, direEffectDescriptionFactory, visualRegistry, localizer, width, height);
/*     */     
/* 177 */     this.fauxWindow.add((BComponent)this.characterContainer, new Rectangle(44, 60, width, height));
/*     */     
/* 179 */     this.selectionHandler.addDiffChangeListener(this.characterContainer);
/* 180 */     BLabel levelLbl = new BLabel("" + clientPlayer.getStatSupport().getLevel(), "character-level");
/* 181 */     this.fauxWindow.add((BComponent)levelLbl, new Rectangle(60, 635, 36, 36));
/* 182 */     clientPlayer.getStatSupport().getStatById((short)20).addStatListener(new LevelStatHandler(levelLbl));
/*     */   }
/*     */ 
/*     */   
/*     */   private void initInventoryEquipmentsContainer(CharacterWindowModel windowModel, ResourceManager resourceManager, TCGToolTipManager tooltipManager) {
/* 187 */     this.inventoryEquipmentsContainer = new InventoryEquipmentsContainer(windowModel, this.selectionHandler, resourceManager, tooltipManager);
/*     */     
/* 189 */     windowModel.getEquipDoll().addChangeListener(this.inventoryEquipmentsContainer);
/* 190 */     int y = 220;
/* 191 */     int height = 450;
/* 192 */     BLabel bgdLabel = new BLabel("", "bgd-label");
/* 193 */     this.fauxWindow.add((BComponent)bgdLabel, new Rectangle(525, y, 455, height));
/*     */     
/* 195 */     this.fauxWindow.add((BComponent)this.inventoryEquipmentsContainer, new Rectangle(543, y + 15, 367, height - 32));
/* 196 */     this.selectionHandler.addWearingEquipmentChangeListener(this.inventoryEquipmentsContainer);
/*     */     
/* 198 */     windowModel.addSubscriberListener(new SubscriberChangedListener()
/*     */         {
/*     */           public void subscriberStatusChanged(boolean newValue) {
/* 201 */             CharacterEquipmentWindow.this.inventoryEquipmentsContainer.stateChanged(new ChangeEvent(CharacterEquipmentWindow.this.selectionHandler));
/*     */           }
/*     */         });
/*     */     
/* 205 */     BScrollBar scrollBar = this.inventoryEquipmentsContainer.getScrollBar();
/* 206 */     this.fauxWindow.add((BComponent)scrollBar, new Rectangle(907, y + 15, 54, height - 32));
/*     */   }
/*     */ 
/*     */   
/*     */   private void initTabs(CharacterWindowModel windowModel, ResourceManager resourceManager, TCGToolTipManager tooltipManager) {
/* 211 */     this.equipDollContainer = new EquipDollContainer(this.selectionHandler, resourceManager, tooltipManager, this);
/* 212 */     windowModel.getEquipDoll().addChangeListener(this.equipDollContainer);
/* 213 */     this.fauxWindow.add((BComponent)this.equipDollContainer, new Rectangle(415, 140, 114, 531));
/* 214 */     this.selectionHandler.addWearingEquipmentChangeListener(this.equipDollContainer);
/*     */     
/* 216 */     this.iconBgd = new BToggleButton("");
/* 217 */     this.iconBgd.setStyleClass("equipment-tab");
/* 218 */     this.itemIconLabel = new BClickthroughLabel("");
/* 219 */     this.ITEM_ICON_SIZE = new Rectangle(445, 82, 48, 48);
/* 220 */     this.fauxWindow.add((BComponent)this.iconBgd, new Rectangle(430, 70, 79, 72));
/* 221 */     this.fauxWindow.add((BComponent)this.itemIconLabel, this.ITEM_ICON_SIZE);
/*     */     
/* 223 */     updatePetImage(MainGameState.getPlayerModel().getActivePet());
/*     */     
/* 225 */     this.iconBgd.addListener((ComponentListener)new MouseListener()
/*     */         {
/*     */           public void mousePressed(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void mouseReleased(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           public void mouseEntered(MouseEvent event) {
/* 238 */             CharacterEquipmentWindow.this.mouseInPet = true;
/* 239 */             CharacterEquipmentWindow.this.equipmentStatsContainer.petHover(true, MainGameState.getPlayerModel().getActivePet());
/*     */           }
/*     */ 
/*     */           
/*     */           public void mouseExited(MouseEvent event) {
/* 244 */             if (!CharacterEquipmentWindow.this.iconBgd.isSelected()) {
/* 245 */               CharacterEquipmentWindow.this.mouseInPet = false;
/* 246 */               CharacterEquipmentWindow.this.equipmentStatsContainer.petHover(false, MainGameState.getPlayerModel().getActivePet());
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 251 */     this.iconBgd.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 254 */             if (CharacterEquipmentWindow.this.iconBgd.isSelected()) {
/* 255 */               CharacterEquipmentWindow.this.mouseInPet = true;
/* 256 */               CharacterEquipmentWindow.this.equipmentStatsContainer.petHover(true, MainGameState.getPlayerModel().getActivePet());
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void initEquipmentStatsContainer(CharacterWindowModel windowModel, ResourceManager resourceManager, Localizer localizer) {
/* 264 */     this.equipmentStatsContainer = new EquipmentStatsContainer(windowModel, resourceManager, localizer);
/*     */     
/* 266 */     this.fauxWindow.add((BComponent)this.equipmentStatsContainer, new Rectangle(525, 60, 455, 150));
/* 267 */     this.selectionHandler.addDiffChangeListener(this.equipmentStatsContainer);
/*     */   }
/*     */   
/*     */   public void reinitialize() {
/* 271 */     this.selectionHandler.setWearingSelectionEventEnabled(false);
/* 272 */     this.equipDollContainer.reinitialize(this.windowModel.getEquipDoll());
/* 273 */     this.selectionHandler.setWearingSelectionEventEnabled(true);
/* 274 */     this.selectionHandler.reinitialize();
/*     */   }
/*     */   
/*     */   public void resetView() {
/* 278 */     if (this.characterContainer.getCharacter3DView().getGeometry() != null) {
/* 279 */       this.characterContainer.getCharacter3DView().getGeometry().setAngle(-0.7853982F);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 285 */     super.setVisible(visible);
/* 286 */     if (!visible) {
/* 287 */       if (BuiSystem.getRootNode().getAllWindows().contains(this.characterInfoWindow)) {
/* 288 */         BuiSystem.removeWindow((BWindow)this.characterInfoWindow);
/*     */       }
/* 290 */       this.step = EquipmentTutorialStep.STEP_7_WINDOW_CLOSED;
/* 291 */       updateTutorial(this.step);
/*     */     } else {
/* 293 */       this.step = EquipmentTutorialStep.STEP_1_WELCOME;
/* 294 */       updateTutorial(this.step);
/*     */     } 
/*     */     
/* 297 */     this.tutorialHighlightLabel.setVisible(false);
/*     */   }
/*     */   
/*     */   private static class LevelStatHandler
/*     */     implements StatListener {
/*     */     private BLabel levelLbl;
/*     */     
/*     */     public LevelStatHandler(BLabel levelLbl) {
/* 305 */       this.levelLbl = levelLbl;
/*     */     }
/*     */ 
/*     */     
/*     */     public void statChanged(Stat stat, int oldBase, int oldModifier) {
/* 310 */       this.levelLbl.setText("" + stat.getSum());
/*     */     }
/*     */   }
/*     */   
/*     */   public void nextTutorialStep() {
/* 315 */     for (int i = 0; i < (EquipmentTutorialStep.values()).length; i++) {
/* 316 */       if (this.step == EquipmentTutorialStep.values()[i] && i + 1 < (EquipmentTutorialStep.values()).length) {
/* 317 */         this.step = EquipmentTutorialStep.values()[i + 1];
/* 318 */         updateTutorial(this.step);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 326 */     super.render(renderer);
/* 327 */     if (this.tutorialHighlightLabel.isVisible()) {
/* 328 */       double timeSeconds = ((float)(System.currentTimeMillis() - this.startTime) / 1000.0F);
/* 329 */       float v = (float)Math.abs(Math.sin(timeSeconds * 2.0D));
/* 330 */       this.tutorialHighlightLabel.setAlpha(v);
/*     */       
/* 332 */       this.elapsed += Timer.getTimer().getTimePerFrame();
/*     */       
/* 334 */       if (this.step.equals(EquipmentTutorialStep.STEP_3_CATEGORY_INFO) && this.elapsed >= 1.0F) {
/* 335 */         setItemsEnabled(true);
/* 336 */         this.selectionHandler.setSlotIdSelected((this.selectionHandler.getSlotIdSelected() + 1) % 6);
/* 337 */         setItemsEnabled(false);
/* 338 */         this.elapsed = 0.0F;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void setItemsEnabled(boolean enable) {
/* 344 */     for (int i = 0; i < this.inventoryEquipmentsContainer.getComponentCount(); i++) {
/* 345 */       BComponent component = this.inventoryEquipmentsContainer.getComponent(i);
/*     */       
/* 347 */       component.setEnabled(enable);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTutorial(EquipmentTutorialStep step) {
/* 353 */     if (TcgGame.isEquipmentTutorial() && MainGameState.getNoahTutorialWindow() != null) {
/*     */       int i; BComponent button; int j;
/* 355 */       MainGameState.getNoahTutorialWindow().setLayer(100);
/* 356 */       int noahX = MainGameState.getNoahTutorialWindow().getX();
/* 357 */       int noahY = (DisplaySystem.getDisplaySystem().getHeight() < 1000) ? 20 : 115;
/* 358 */       MainGameState.getNoahTutorialWindow().setLocation(noahX, noahY);
/*     */       
/* 360 */       int arrowWidth = MainGameState.getArrowWindow().getWidth();
/* 361 */       int arrowHeight = MainGameState.getArrowWindow().getHeight();
/*     */       
/* 363 */       switch (step) {
/*     */         case STEP_1_WELCOME:
/* 365 */           this.selectionHandler.setSlotIdSelected(ItemType.EQUIP_HEAD.getEquipValue());
/*     */           
/* 367 */           MainGameState.getMainHud().getCharacterButton().setHighlighted(false);
/* 368 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.equip.welcome", new String[0]), QuestTextType.INFO);
/*     */           
/* 370 */           MainGameState.getNoahTutorialWindow().getConfirmButton().setHighlighted(true);
/*     */           
/* 372 */           MainGameState.getArrowWindow().setVisible(true);
/*     */ 
/*     */           
/* 375 */           MainGameState.getArrowWindow().setLocation(noahX + 585 + 23 - arrowWidth / 2, noahY + 29 + 46);
/*     */ 
/*     */ 
/*     */           
/* 379 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/* 380 */           MainGameState.getArrowWindow().updateInfoText(TcgGame.getLocalizedText("tutorial.dryplains.clickcontinue", new String[0]));
/*     */           
/* 382 */           this.closeButton.setEnabled(false);
/* 383 */           this.infoButton.setEnabled(false);
/*     */           
/* 385 */           for (i = 0; i < this.equipDollContainer.getComponentCount(); i++) {
/* 386 */             this.equipDollContainer.getComponent(i).setEnabled(false);
/*     */           }
/*     */           break;
/*     */         
/*     */         case STEP_2_STAT_INFO:
/* 391 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.equip.stats", new String[0]), QuestTextType.INFO);
/*     */           
/* 393 */           MainGameState.getNoahTutorialWindow().getConfirmButton().setHighlighted(false);
/*     */           
/* 395 */           this.tutorialHighlightLabel.setVisible(true);
/* 396 */           add((BComponent)this.tutorialHighlightLabel, new Rectangle(this.characterContainer.getAbsoluteX() + 10, this.characterContainer.getAbsoluteY() + 10, 370, 100));
/*     */ 
/*     */           
/* 399 */           MainGameState.getArrowWindow().setLocation(this.characterContainer.getAbsoluteX() + 185 - 100, this.characterContainer.getAbsoluteY() + 100);
/*     */           
/* 401 */           MainGameState.getArrowWindow().setVisible(true);
/* 402 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/* 403 */           MainGameState.getArrowWindow().updateInfoText("");
/*     */           break;
/*     */         
/*     */         case STEP_3_CATEGORY_INFO:
/* 407 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.equip.category", new String[0]), QuestTextType.INFO);
/*     */ 
/*     */           
/* 410 */           remove((BComponent)this.tutorialHighlightLabel);
/* 411 */           add((BComponent)this.tutorialHighlightLabel, new Rectangle(this.equipDollContainer.getAbsoluteX() + 29, this.fauxWindow.getAbsoluteY() + 70, 65, 600));
/*     */ 
/*     */           
/* 414 */           MainGameState.getArrowWindow().setLocation(this.equipDollContainer.getAbsoluteX() + this.equipDollContainer.getWidth() + 3, this.fauxWindow.getAbsoluteY() + 70 + 300 - arrowHeight / 2);
/*     */           
/* 416 */           MainGameState.getArrowWindow().setVisible(true);
/* 417 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.LEFT);
/*     */           break;
/*     */         
/*     */         case STEP_4_ITEM_STAT_INFO:
/* 421 */           setItemsEnabled(true);
/* 422 */           this.selectionHandler.setSlotIdSelected(ItemType.EQUIP_TORSO.getEquipValue());
/* 423 */           setItemsEnabled(false);
/*     */           
/* 425 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.equip.stats.item", new String[0]), QuestTextType.INFO);
/*     */ 
/*     */ 
/*     */           
/* 429 */           remove((BComponent)this.tutorialHighlightLabel);
/* 430 */           add((BComponent)this.tutorialHighlightLabel, new Rectangle(this.equipmentStatsContainer.getAbsoluteX(), this.equipmentStatsContainer.getAbsoluteY(), 455, 150));
/*     */ 
/*     */           
/* 433 */           MainGameState.getArrowWindow().setLocation(this.equipmentStatsContainer.getAbsoluteX() + 227 - 100, this.equipmentStatsContainer.getAbsoluteY() + 150);
/*     */           
/* 435 */           MainGameState.getArrowWindow().setVisible(true);
/* 436 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/*     */           break;
/*     */         
/*     */         case STEP_5_SELECT_ITEM:
/* 440 */           setItemsEnabled(true);
/* 441 */           remove((BComponent)this.tutorialHighlightLabel);
/*     */           
/* 443 */           this.selectionHandler.setSlotIdSelected(ItemType.EQUIP_TORSO.getEquipValue());
/*     */           
/* 445 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.equip.item", new String[0]), QuestTextType.UPDATE);
/*     */ 
/*     */           
/* 448 */           button = this.inventoryEquipmentsContainer.getComponent(0);
/* 449 */           for (j = 0; j < this.inventoryEquipmentsContainer.getComponentCount(); j++) {
/* 450 */             BComponent component = this.inventoryEquipmentsContainer.getComponent(j);
/*     */             
/* 452 */             String id = "";
/* 453 */             if (component instanceof EquipmentButton) {
/* 454 */               InventoryItem item = ((EquipmentButton)component).getModel().getItem();
/* 455 */               if (item instanceof ClientItem) {
/* 456 */                 id = ((ClientItem)item).getClassId();
/* 457 */                 this.newestItem = ((ClientItem)item).getItemDescription();
/* 458 */                 ((EquipmentButton)component).setHighlighted(true);
/*     */               } 
/*     */             } 
/*     */             
/* 462 */             if (id.equals("eq-scout-blue-torso")) {
/* 463 */               button = component;
/*     */               
/*     */               break;
/*     */             } 
/*     */           } 
/* 468 */           MainGameState.getArrowWindow().setLocation(button.getAbsoluteX() + button.getWidth() / 2 - arrowWidth / 2, button.getAbsoluteY() - arrowHeight);
/*     */           
/* 470 */           MainGameState.getArrowWindow().setVisible(true);
/* 471 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.UP);
/*     */           break;
/*     */         
/*     */         case STEP_6_CLOSE_WINDOW:
/* 475 */           this.closeButton.setEnabled(true);
/* 476 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.equip.close", new String[0]), QuestTextType.UPDATE);
/*     */ 
/*     */           
/* 479 */           MainGameState.getArrowWindow().setLocation(this.closeButton.getAbsoluteX() - 200 + 10, this.closeButton.getAbsoluteY() - 250);
/*     */ 
/*     */           
/* 482 */           MainGameState.getArrowWindow().setVisible(true);
/* 483 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.TOP_RIGHT);
/*     */           break;
/*     */         
/*     */         case STEP_7_WINDOW_CLOSED:
/* 487 */           MainGameState.getNoahTutorialWindow().dismiss();
/* 488 */           MainGameState.setNoahTutorialWindow(null);
/* 489 */           TcgGame.setEquipmentTutorial(false);
/* 490 */           MainGameState.getArrowWindow().setVisible(false);
/*     */           
/* 492 */           for (j = 0; j < this.equipDollContainer.getComponentCount(); j++) {
/* 493 */             this.equipDollContainer.getComponent(j).setEnabled(true);
/*     */           }
/*     */           
/* 496 */           if (getCloseButton() instanceof HighlightedButton) {
/* 497 */             ((HighlightedButton)getCloseButton()).setHighlighted(false);
/*     */           }
/*     */           
/* 500 */           this.infoButton.setEnabled(true);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePetImage(ClientPet pet) {
/* 508 */     if (pet == null) {
/*     */       return;
/*     */     }
/* 511 */     this.petIcon = pet.getIcon();
/*     */ 
/*     */     
/* 514 */     BImage iconImage = null;
/*     */     
/*     */     try {
/* 517 */       iconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, this.petIcon);
/* 518 */     } catch (NoLocatorException e) {
/* 519 */       e.printStackTrace();
/* 520 */       throw new RuntimeException("Missing image for destination portal: " + this.petIcon);
/*     */     } 
/*     */     
/* 523 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, iconImage);
/* 524 */     this.fauxWindow.remove((BComponent)this.itemIconLabel);
/* 525 */     this.itemIconLabel.setBackground(0, (BBackground)imageBackground);
/* 526 */     this.itemIconLabel.setBackground(1, (BBackground)imageBackground);
/* 527 */     this.fauxWindow.add((BComponent)this.itemIconLabel, this.ITEM_ICON_SIZE);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemDescription getNewestItem() {
/* 532 */     return this.newestItem;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\CharacterEquipmentWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */