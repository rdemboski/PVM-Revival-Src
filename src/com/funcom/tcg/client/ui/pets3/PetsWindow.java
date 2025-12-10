/*     */ package com.funcom.tcg.client.ui.pets3;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.commons.jme.bui.IrregularButton;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.items.ItemType;
import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
import com.funcom.tcg.client.ui.AbstractFauxWindow;
import com.funcom.tcg.client.ui.AbstractLargeWindow;
import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.hud.QuestTextType;
/*     */ import com.funcom.tcg.client.ui.hud.TutorialArrowDirection;
/*     */ import com.funcom.tcg.client.ui.hud2.AbstractHudModel;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
import com.funcom.tcg.net.message.CompleteTutorialQuestObjectiveMessage;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BScrollBar;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;

import java.util.ArrayList;
import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PetsWindow extends AbstractLargeWindow implements Inventory.ChangeListener {
/*  44 */   private final int NOAH_LAYER = 100;
/*     */ 
/*     */   
/*  47 */   private static final float[] VIEW_ANGLE = new float[] { 5.550147F, 0.0F, 0.7330383F };
/*     */ 
/*     */   
/*     */   private final PetsWindowModel windowModel;
/*     */   
/*     */   private PetInfoWindow petInfoWindow;
/*     */   
/*  54 */   private final Rectangle PET_VIEW_0_BOUNDS = new Rectangle(31, 285, 329, 415);
/*  55 */   private final Rectangle PET_VIEW_1_BOUNDS = new Rectangle(350, 285, 329, 415);
/*  56 */   private final Rectangle PET_VIEW_2_BOUNDS = new Rectangle(667, 285, 329, 415);
/*     */   
/*     */   private final DireEffectDescriptionFactory dfxDescriptionFactory;
/*     */   
/*     */   protected PetView[] petViews;
/*     */   
/*     */   private PetViewSelectionModel petViewSelectionModel;
/*     */   
/*     */   private PetButtonContainer petButtonContainer;
/*     */   
/*     */   private BLabel petTokenNumberLabel;
/*     */   
/*     */   private BLabel petTokenLabel;
/*     */   private BLabel tutorialHighlightLabel;
/*     */   private PetTutorialStep step;
/*     */   private AbstractHudModel.ChangeListenerAdapter listener;
/*     */   private IrregularButton infoButton;
/*     */   private long startTime;
/*     */   
/*     */   public PetsWindow(PetsWindowModel windowModel, ResourceManager resourceManager, TCGToolTipManager tooltipManager, PetRegistry petRegistry, DireEffectDescriptionFactory dfxDescriptionFactory, ClientPlayer player, boolean subscriber, Localizer localizer) {
/*  76 */     super(PetsWindow.class.getSimpleName(), null, (BLayoutManager)new AbsoluteLayout());
/*     */     
/*  78 */     this.startTime = System.currentTimeMillis();
/*  79 */     this.windowModel = windowModel;
/*  80 */     this.dfxDescriptionFactory = dfxDescriptionFactory;
/*  81 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  82 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*     */     
/*  84 */     initSortedPets(petRegistry, player);
/*     */     
/*  86 */     createComponents(resourceManager, tooltipManager, subscriber, localizer);
/*     */     
/*  88 */     player.getInventory().addChangeListener(this);
/*     */   }
/*     */   
/*     */   private void initSortedPets(PetRegistry petRegistry, ClientPlayer player) {
/*  92 */     List<PetWindowPet> sortedPets = new ArrayList<PetWindowPet>();
/*  93 */     Set<String> allPets = TcgGame.getRpgLoader().getPetManager().getUniquePetList();
/*  94 */     for (String petDesc : allPets) {
/*  95 */       String petId = TcgGame.getRpgLoader().getPetManager().petBackReference(petDesc);
/*  96 */       ClientPetDescription petForClassId = petRegistry.getPetForClassId(petId);
/*  97 */       PetWindowPet pet = new PetWindowPet(petForClassId);
/*  98 */       sortedPets.add(pet);
/*  99 */       if (player.hasCollectedPetForRefId(petDesc)) {
/* 100 */         pet.setPlayerPet(player.getCollectedPetForId(petDesc));
/*     */       }
/*     */     } 
/* 103 */     Collections.sort(sortedPets, new Comparator<PetWindowPet>()
/*     */         {
/*     */           public int compare(PetWindowPet o1, PetWindowPet o2)
/*     */           {
/* 107 */             int levelDiff = o1.getSortLevel() - o2.getSortLevel();
/* 108 */             if (levelDiff == 0) {
/* 109 */               return o1.getPetDescription().getElementId().compareTo(o2.getPetDescription().getElementId());
/*     */             }
/*     */             
/* 112 */             return levelDiff;
/*     */           }
/*     */         });
/* 115 */     this.windowModel.setSortedPets(sortedPets);
/*     */   }
/*     */ 
/*     */   
/*     */   public void slotChanged(Inventory inventory, int slotId, InventoryItem oldItem, InventoryItem newItem) {
/* 120 */     if (this.petTokenNumberLabel != null && ((
/* 121 */       newItem != null && newItem.getItemType().equals(ItemType.CRYSTAL)) || (oldItem != null && oldItem.getItemType().equals(ItemType.CRYSTAL)))) {
/*     */       int coinAmount;
/*     */       
/*     */       String type;
/* 125 */       if (newItem == null) {
/* 126 */         ClientItem clientItem = (ClientItem)oldItem;
/* 127 */         type = clientItem.getItemDescription().getId();
/* 128 */         coinAmount = 0;
/*     */       } else {
/* 130 */         ClientItem clientItem = (ClientItem)newItem;
/* 131 */         type = clientItem.getItemDescription().getId();
/* 132 */         coinAmount = clientItem.getAmount();
/*     */       } 
/* 134 */       updateItem(coinAmount, type);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void updateItem(int coinAmount, String type) {
/* 140 */     if (type.equals("pet-token")) {
/* 141 */       this.petTokenNumberLabel.setText(coinAmount + "");
/* 142 */       for (PetView view : this.petViews) {
/* 143 */         view.updateTotalTokens(coinAmount);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void createComponents(ResourceManager resourceManager, TCGToolTipManager tooltipManager, boolean subscriber, Localizer localizer) {
/* 150 */     int y = 44;
/* 151 */     int height = 257;
/*     */     
/* 153 */     this.petViewSelectionModel = new PetViewSelectionModel(this.windowModel);
/* 154 */     this.petButtonContainer = new PetButtonContainer(this.windowModel, this.petViewSelectionModel, resourceManager, tooltipManager);
/*     */     
/* 156 */     this.petButtonContainer.setPets(this.windowModel.getSortedPets(), subscriber);
/*     */     
/* 158 */     BScrollBar scrollBar = this.petButtonContainer.getScrollBar();
/*     */     
/* 160 */     BLabel containerLeft = new BLabel("", "pet-button-container-left");
/* 161 */     BLabel shadowLeft = new BLabel("", "pet-button-shadow-left");
/* 162 */     BLabel containerRight = new BLabel("", "pet-button-container-right");
/* 163 */     BLabel shadowRight = new BLabel("", "pet-button-shadow-right");
/*     */     
/* 165 */     int tokens = this.windowModel.getNumTokens();
/*     */     
/* 167 */     this.petViewSelectionModel.setViewChangeListener(this.petButtonContainer);
/* 168 */     this.petViews = new PetView[3];
/* 169 */     this.petViews[0] = new PetView(0, this.petViewSelectionModel, VIEW_ANGLE[0], resourceManager, this.dfxDescriptionFactory, localizer, tokens);
/*     */     
/* 171 */     this.petViews[0].setSwitchLocked(!subscriber);
/*     */     
/* 173 */     this.petViews[1] = new PetView(1, this.petViewSelectionModel, VIEW_ANGLE[1], resourceManager, this.dfxDescriptionFactory, localizer, tokens);
/*     */     
/* 175 */     this.petViews[2] = new PetView(2, this.petViewSelectionModel, VIEW_ANGLE[2], resourceManager, this.dfxDescriptionFactory, localizer, tokens);
/*     */     
/* 177 */     this.petViews[2].setSwitchLocked(!subscriber);
/*     */     
/* 179 */     this.tutorialHighlightLabel = new BLabel("");
/* 180 */     this.tutorialHighlightLabel.setStyleClass("highlight-label");
/*     */     
/* 182 */     this.fauxWindow.add((BComponent)containerLeft, new Rectangle(28, y, 161, height));
/* 183 */     this.fauxWindow.add((BComponent)shadowLeft, new Rectangle(28, y, 161, height));
/* 184 */     this.fauxWindow.add((BComponent)containerRight, new Rectangle(835, y, 161, height));
/* 185 */     this.fauxWindow.add((BComponent)shadowRight, new Rectangle(835, y, 161, height));
/* 186 */     this.fauxWindow.add((BComponent)this.petButtonContainer, new Rectangle(96, y, 835, height));
/* 187 */     this.fauxWindow.add((BComponent)scrollBar, new Rectangle(908, y + 23, 54, 214));
/* 188 */     this.fauxWindow.add((BComponent)this.petViews[0], this.PET_VIEW_0_BOUNDS);
/* 189 */     this.fauxWindow.add((BComponent)this.petViews[1], this.PET_VIEW_1_BOUNDS);
/* 190 */     this.fauxWindow.add((BComponent)this.petViews[2], this.PET_VIEW_2_BOUNDS);
/*     */     
/* 192 */     this.windowModel.setChangeListener(this.petViews[0].getViewId(), this.petViews[0]);
/* 193 */     this.windowModel.setChangeListener(this.petViews[1].getViewId(), this.petViews[1]);
/* 194 */     this.windowModel.setChangeListener(this.petViews[2].getViewId(), this.petViews[2]);
/*     */ 
/*     */     
/* 197 */     addTrainPetComponents(localizer, resourceManager);
/*     */     
/* 199 */     addInfoButton();
/*     */     
/* 201 */     addDefaultCloseButton(AbstractFauxWindow.CloseButtonPosition.TOP_RIGHT);
/*     */   }
/*     */   
/*     */   private void addInfoButton() {
/* 205 */     this.infoButton = new IrregularButton("");
/* 206 */     this.infoButton.setStyleClass("info_button");
/* 207 */     int closeButtonSize = 24;
/* 208 */     this.fauxWindow.add((BComponent)this.infoButton, new Rectangle(5, this.fauxWindow.getHeight() - closeButtonSize - 5, closeButtonSize, closeButtonSize));
/* 209 */     this.infoButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.help", new String[0]));
/*     */     
/* 211 */     this.infoButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           
/*     */           public void actionPerformed(ActionEvent event)
/*     */           {
/* 216 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 217 */             if (PetsWindow.this.petInfoWindow == null) {
/* 218 */               String infoWindowPath = "gui/peeler/info_petwindow_v1.xml";
/* 219 */               BananaPeel bananaPeel = (BananaPeel)TcgGame.getResourceManager().getResource(BananaPeel.class, infoWindowPath, CacheType.CACHE_PERMANENTLY);
/*     */ 
/*     */               
/* 222 */               PetsWindow.this.petInfoWindow = new PetInfoWindow(infoWindowPath, bananaPeel, TcgGame.getResourceManager());
/* 223 */               PetsWindow.this.petInfoWindow.setLayer(101);
/*     */             } 
/* 225 */             PetsWindow.this.petInfoWindow.setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - PetsWindow.this.petInfoWindow.getWidth() / 2, DisplaySystem.getDisplaySystem().getHeight() / 2 - PetsWindow.this.petInfoWindow.getHeight() / 2);
/*     */ 
/*     */             
/* 228 */             PetsWindow.this.petInfoWindow.setLayer(4);
/* 229 */             if (!BuiSystem.getRootNode().getAllWindows().contains(PetsWindow.this.petInfoWindow)) {
/* 230 */               BuiSystem.addWindow((BWindow)PetsWindow.this.petInfoWindow);
/*     */             } else {
/* 232 */               BuiSystem.removeWindow((BWindow)PetsWindow.this.petInfoWindow);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addTrainPetComponents(Localizer localizer, ResourceManager resourceManager) {
/* 241 */     this.petTokenLabel = new BLabel("");
/* 242 */     this.petTokenLabel.setStyleClass("train-token");
/* 243 */     this.fauxWindow.add((BComponent)this.petTokenLabel, new Rectangle(51, 140, 64, 64));
/*     */     
/* 245 */     this.petTokenNumberLabel = new BLabel("");
/* 246 */     this.petTokenNumberLabel.setStyleClass("token-number-label");
/* 247 */     this.fauxWindow.add((BComponent)this.petTokenNumberLabel, new Rectangle(51, 115, 64, 32));
/*     */   }
/*     */   
/*     */   public void reinitialize() {
/* 251 */     this.petViewSelectionModel.select(this.petViews[this.windowModel.getActiveViewId()]);
/* 252 */     this.windowModel.fireValuesAsChanges();
/* 253 */     this.petButtonContainer.reinitialize();
/*     */     
/* 255 */     for (PetView petView : this.petViews)
/* 256 */       petView.reinitialize(this.windowModel.getNumTokens()); 
/* 257 */     this.petTokenNumberLabel.setText(String.valueOf(this.windowModel.getNumTokens()));
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 262 */     this.windowModel.close();
/* 263 */     super.dismiss();
/*     */   }
/*     */   
/*     */   public void toggleTrainingMode(int viewId) {
/* 267 */     for (int i = 0; i < this.petViews.length; i++) {
/* 268 */       if (i != viewId) {
/* 269 */         this.petViews[i].resetView();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 276 */     super.setVisible(visible);
/* 277 */     if (!visible) {
/*     */       
/* 279 */       this.petViewSelectionModel.setSelectionMode();
/* 280 */       PetTutorialStep step = (TcgGame.getPetTutorialId() == 0) ? PetTutorialStep.STEP_9_PET_WINDOW_CLOSED : PetTutorialStep.STEP_2_7_PET_WINDOW_CLOSED;
/*     */ 
/*     */       
/* 283 */       updateTutorial(step);
/*     */       
/* 285 */       for (PetView view : this.petViews) {
/* 286 */         view.updateTotalTokens(this.windowModel.getNumTokens());
/* 287 */         view.resetView();
/*     */       } 
/* 289 */       if (BuiSystem.getRootNode().getAllWindows().contains(this.petInfoWindow)) {
/* 290 */         BuiSystem.removeWindow((BWindow)this.petInfoWindow);
/*     */       }
/*     */     } else {
/* 293 */       PetTutorialStep step = (TcgGame.getPetTutorialId() == 0) ? PetTutorialStep.STEP_1_PET_WINDOW_INFO : PetTutorialStep.STEP_2_1_PET_WINDOW_SELECT_TRAIN;
/*     */ 
/*     */       
/* 296 */       updateTutorial(step);
/*     */     } 
/* 298 */     this.tutorialHighlightLabel.setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 303 */     super.render(renderer);
/* 304 */     if (this.tutorialHighlightLabel.isVisible()) {
/* 305 */       double timeSeconds = ((float)(System.currentTimeMillis() - this.startTime) / 1000.0F);
/* 306 */       float v = (float)Math.abs(Math.sin(timeSeconds * 2.0D));
/* 307 */       this.tutorialHighlightLabel.setAlpha(v);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void nextTutorialStep() {
/* 312 */     for (int i = 0; i < (PetTutorialStep.values()).length; i++) {
/* 313 */       if (this.step == PetTutorialStep.values()[i] && i + 1 < (PetTutorialStep.values()).length) {
/* 314 */         this.step = PetTutorialStep.values()[i + 1];
/* 315 */         updateTutorial(this.step);
/*     */         return;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTutorial(PetTutorialStep step) {
/* 328 */     this.step = step;
/* 329 */     if (TcgGame.isPetTutorial() && MainGameState.getNoahTutorialWindow() != null) {
/* 330 */       int noahWidth; MainGameState.getNoahTutorialWindow().setLayer(100);
/* 331 */       int noahX = MainGameState.getNoahTutorialWindow().getX();
/* 332 */       int noahY = DisplaySystem.getDisplaySystem().getHeight() / 2 - 170;
/* 333 */       MainGameState.getNoahTutorialWindow().setLocation(noahX, noahY);
/* 334 */       int arrowWidth = MainGameState.getArrowWindow().getWidth();
/* 335 */       int arrowHeight = MainGameState.getArrowWindow().getHeight();
/*     */       
/* 337 */       switch (step) {
/*     */         case STEP_1_PET_WINDOW_INFO:
/* 339 */           MainGameState.getMainHud().getPetsButton().setHighlighted(false);
/*     */           
/* 341 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.pet.window.info", new String[0]), QuestTextType.INFO);
/*     */           
/* 343 */           MainGameState.getArrowWindow().setVisible(true);
/*     */           
/* 345 */           MainGameState.getArrowWindow().setLocation(noahX + 585 + 23 - arrowWidth / 2, noahY + 29 + 46);
/*     */ 
/*     */ 
/*     */           
/* 349 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/* 350 */           MainGameState.getArrowWindow().updateInfoText(TcgGame.getLocalizedText("tutorial.dryplains.clickcontinue", new String[0]));
/* 351 */           this.closeButton.setEnabled(false);
/* 352 */           this.infoButton.setEnabled(false);
/*     */           return;
/*     */         case STEP_2_PET_CARDS_INFO:
/* 355 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.pet.cards.info", new String[0]), QuestTextType.INFO);
/*     */ 
/*     */           
/* 358 */           MainGameState.getNoahTutorialWindow().setLocation(noahX, noahY + 300);
/*     */           
/* 360 */           this.fauxWindow.remove((BComponent)this.tutorialHighlightLabel);
/* 361 */           this.tutorialHighlightLabel.setVisible(true);
/* 362 */           this.fauxWindow.add((BComponent)this.tutorialHighlightLabel, new Rectangle(120, 60, 785, 225));
/*     */ 
/*     */           
/* 365 */           MainGameState.getArrowWindow().setVisible(true);
/* 366 */           MainGameState.getArrowWindow().setLocation(this.fauxWindow.getX() + 120 + 392 - arrowWidth / 2, this.fauxWindow.getY() + 285 + this.tutorialHighlightLabel.getHeight());
/*     */ 
/*     */ 
/*     */           
/* 370 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.DOWN);
/* 371 */           MainGameState.getArrowWindow().updateInfoText("");
/* 372 */           this.closeButton.setEnabled(false);
/* 373 */           this.infoButton.setEnabled(false);
/*     */           return;
/*     */         
/*     */         case STEP_3_YOUR_PET_INFO:
/* 377 */           MainGameState.getNoahTutorialWindow().setText(TcgGame.getLocalizedText("tutorial.gui.pet.info", new String[0]), QuestTextType.INFO);
/*     */           
/* 379 */           MainGameState.getNoahTutorialWindow().setLocation(noahX, noahY);
/*     */           
/* 381 */           this.fauxWindow.remove((BComponent)this.tutorialHighlightLabel);
/* 382 */           this.tutorialHighlightLabel.setVisible(true);
/* 383 */           this.fauxWindow.add((BComponent)this.tutorialHighlightLabel, this.PET_VIEW_1_BOUNDS);
/*     */           
/* 385 */           MainGameState.getArrowWindow().setVisible(true);
/* 386 */           MainGameState.getArrowWindow().setLocation(this.fauxWindow.getX() + this.PET_VIEW_1_BOUNDS.x - arrowWidth, this.fauxWindow.getY() + this.PET_VIEW_1_BOUNDS.y + this.PET_VIEW_1_BOUNDS.height / 2 - arrowHeight / 2);
/*     */ 
/*     */ 
/*     */           
/* 390 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.RIGHT);
/* 391 */           MainGameState.getArrowWindow().updateInfoText("");
/* 392 */           this.closeButton.setEnabled(false);
/* 393 */           this.infoButton.setEnabled(false);
/*     */           return;
/*     */         case STEP_4_PET_STATS_INFO:
/*     */         case STEP_2_4_PET_STATS_INCREASE:
/* 397 */           MainGameState.getNoahTutorialWindow().setText((step == PetTutorialStep.STEP_4_PET_STATS_INFO) ? TcgGame.getLocalizedText("tutorial.gui.pet.stats.info", new String[0]) : TcgGame.getLocalizedText("tutorial.gui.pet.stats.increase", new String[0]), QuestTextType.INFO);
/*     */ 
/*     */ 
/*     */           
/* 401 */           this.petViews[1].getConfirmTrainButton().setEnabled(!step.equals(PetTutorialStep.STEP_2_4_PET_STATS_INCREASE));
/*     */           
/* 403 */           this.fauxWindow.remove((BComponent)this.tutorialHighlightLabel);
/* 404 */           this.tutorialHighlightLabel.setVisible(true);
/* 405 */           this.fauxWindow.add((BComponent)this.tutorialHighlightLabel, new Rectangle(this.PET_VIEW_1_BOUNDS.x + 34, this.PET_VIEW_1_BOUNDS.y + 25, this.PET_VIEW_1_BOUNDS.width - 66, 104));
/*     */ 
/*     */           
/* 408 */           MainGameState.getArrowWindow().setVisible(true);
/* 409 */           MainGameState.getArrowWindow().setLocation(this.fauxWindow.getX() + this.PET_VIEW_1_BOUNDS.x - arrowWidth, this.fauxWindow.getY() + this.PET_VIEW_1_BOUNDS.y + 52 - arrowHeight / 2);
/*     */ 
/*     */ 
/*     */           
/* 413 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.RIGHT);
/* 414 */           MainGameState.getArrowWindow().updateInfoText("");
/* 415 */           this.closeButton.setEnabled(false);
/* 416 */           this.infoButton.setEnabled(false);
/*     */           return;
/*     */         
/*     */         case STEP_5_PET_WINDOW_SELECT_TRAIN:
/*     */         case STEP_2_1_PET_WINDOW_SELECT_TRAIN:
/* 421 */           this.tutorialHighlightLabel.setVisible(false);
/* 422 */           MainGameState.getMainHud().getPetsButton().setHighlighted(false);
/*     */           
/* 424 */           if (DfxTextWindowManager.instance() != null) {
/* 425 */             DfxTextWindowManager.instance().getTutorialWindow("tutorial").triggerKillText();
/*     */           }
/* 427 */           MainGameState.getNoahTutorialWindow().setText((step == PetTutorialStep.STEP_5_PET_WINDOW_SELECT_TRAIN) ? TcgGame.getLocalizedText("tutorial.gui.pet.start", new String[0]) : TcgGame.getLocalizedText("tutorial.gui.pet.start-1", new String[0]), QuestTextType.UPDATE);
/*     */ 
/*     */ 
/*     */           
/* 431 */           for (PetView view : this.petViews) {
/* 432 */             view.getPetTrainButton().setHighlighted(true);
/*     */           }
/* 434 */           getCloseButton().setEnabled(false);
/*     */           
/* 436 */           MainGameState.getArrowWindow().updateInfoText("");
/* 437 */           MainGameState.getArrowWindow().setLocation(this.fauxWindow.getX() + this.PET_VIEW_1_BOUNDS.x + 80 - arrowWidth, this.fauxWindow.getY() + this.PET_VIEW_1_BOUNDS.y + 135 - arrowHeight / 2);
/*     */ 
/*     */           
/* 440 */           MainGameState.getArrowWindow().setVisible(true);
/* 441 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.RIGHT);
/*     */           return;
/*     */         
/*     */         case STEP_6_TRAIN_PET_PRESS_UP:
/*     */         case STEP_2_2_TRAIN_PET_PRESS_UP:
/*     */         case STEP_2_3_TRAIN_PET_PRESS_UP_AGAIN:
/* 447 */           this.petViews[1].setViewButtonsEnabled(false);
/* 448 */           this.petViews[1].getPetTrainButton().setEnabled(false);
/*     */           
/* 450 */           MainGameState.getNoahTutorialWindow().setText((step == PetTutorialStep.STEP_6_TRAIN_PET_PRESS_UP) ? TcgGame.getLocalizedText("tutorial.gui.pet.trainwindowinit", new String[0]) : ((step == PetTutorialStep.STEP_2_2_TRAIN_PET_PRESS_UP) ? TcgGame.getLocalizedText("tutorial.gui.pet.trainwindowinit-1", new String[0]) : TcgGame.getLocalizedText("tutorial.gui.pet.trainwindowinit-second-click-1", new String[0])), QuestTextType.UPDATE);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 457 */           MainGameState.getArrowWindow().setLocation(this.fauxWindow.getX() + this.PET_VIEW_1_BOUNDS.x + 20 - arrowWidth, this.fauxWindow.getY() + this.PET_VIEW_1_BOUNDS.y + 230 - arrowHeight / 2);
/*     */ 
/*     */           
/* 460 */           MainGameState.getArrowWindow().setVisible(true);
/* 461 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.RIGHT);
/*     */           return;
/*     */         case STEP_7_TRAIN_PET_CONFIRM_CLOSE:
/*     */         case STEP_2_5_TRAIN_PET_CONFIRM_CLOSE:
/* 465 */           this.petViews[1].setViewButtonsEnabled(false);
/* 466 */           this.petViews[1].getConfirmTrainButton().setEnabled(true);
/*     */           
/* 468 */           this.tutorialHighlightLabel.setVisible(false);
/*     */           
/* 470 */           MainGameState.getNoahTutorialWindow().setText((step == PetTutorialStep.STEP_7_TRAIN_PET_CONFIRM_CLOSE) ? TcgGame.getLocalizedText("tutorial.gui.pet.trainwindowconfirm", new String[0]) : TcgGame.getLocalizedText("tutorial.gui.pet.trainwindowconfirm-1", new String[0]), QuestTextType.UPDATE);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 475 */           MainGameState.getArrowWindow().setLocation(this.petViews[1].getConfirmTrainButton().getAbsoluteX() + this.petViews[1].getConfirmTrainButton().getWidth() / 2 - 100, this.petViews[1].getConfirmTrainButton().getAbsoluteY() - 250);
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 480 */           MainGameState.getArrowWindow().setVisible(true);
/* 481 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.UP);
/*     */           return;
/*     */         case STEP_8_PET_WINDOW_CONFIRM_CLOSE:
/*     */         case STEP_2_6_PET_WINDOW_CONFIRM_CLOSE:
/* 485 */           this.petViews[1].setViewButtonsEnabled(true);
/* 486 */           MainGameState.getNoahTutorialWindow().setText((step == PetTutorialStep.STEP_8_PET_WINDOW_CONFIRM_CLOSE) ? TcgGame.getLocalizedText("tutorial.gui.pet.complete", new String[0]) : TcgGame.getLocalizedText("tutorial.gui.pet.complete-1", new String[0]), QuestTextType.UPDATE);
/*     */ 
/*     */ 
/*     */           
/* 490 */           getCloseButton().setEnabled(true);
/* 491 */           if (getCloseButton() instanceof HighlightedButton) {
/* 492 */             ((HighlightedButton)getCloseButton()).setHighlighted(true);
/*     */           }
/*     */           
/* 495 */           MainGameState.getArrowWindow().setLocation(this.closeButton.getAbsoluteX() - 200 + 10, this.closeButton.getAbsoluteY() - 250);
/*     */ 
/*     */           
/* 498 */           MainGameState.getArrowWindow().setVisible(true);
/* 499 */           MainGameState.getArrowWindow().setDirection(TutorialArrowDirection.TOP_RIGHT);
/*     */           
/* 501 */           this.petViews[1].getPetTrainButton().setEnabled(true);
/*     */           return;
/*     */         
/*     */         case STEP_9_PET_WINDOW_CLOSED:
/* 505 */           MainGameState.getMainHud().getPetsButton().setHighlighted(false);
/*     */           
/* 507 */           noahWidth = MainGameState.getNoahTutorialWindow().getWidth();
/* 508 */           MainGameState.getNoahTutorialWindow().revertLayer();
/* 509 */           MainGameState.getNoahTutorialWindow().setBounds(DisplaySystem.getDisplaySystem().getWidth() / 2 - noahWidth / 2 + 10, 115, noahWidth, 120);
/*     */           
/* 511 */           if (getCloseButton() instanceof HighlightedButton) {
/* 512 */             ((HighlightedButton)getCloseButton()).setHighlighted(false);
/*     */           }
/* 514 */           TcgGame.setPetTutorial(false);
/*     */           try {
/* 516 */             NetworkHandler.instance().getIOHandler().send((Message)new CompleteTutorialQuestObjectiveMessage("pet-tutorial"));
/*     */           }
/* 518 */           catch (InterruptedException e) {}
/*     */ 
/*     */ 
/*     */           
/* 522 */           for (PetView view : this.petViews) {
/* 523 */             view.updateTutorial(PetTutorialStep.STEP_9_PET_WINDOW_CLOSED);
/*     */           }
/*     */           
/* 526 */           MainGameState.getArrowWindow().setVisible(false);
/* 527 */           HttpMetrics.postEvent(HttpMetrics.Event.PET_TRAINING_LVL_2);
/*     */           return;
/*     */         case STEP_2_7_PET_WINDOW_CLOSED:
/* 530 */           MainGameState.getNoahTutorialWindow().dismiss();
/* 531 */           MainGameState.setNoahTutorialWindow(null);
/* 532 */           MainGameState.getMainHud().getPetsButton().setHighlighted(false);
/*     */           
/* 534 */           if (getCloseButton() instanceof HighlightedButton) {
/* 535 */             ((HighlightedButton)getCloseButton()).setHighlighted(false);
/*     */           }
/* 537 */           TcgGame.setPetTutorial(false);
/*     */           
/* 539 */           for (PetView view : this.petViews) {
/* 540 */             view.updateTutorial(PetTutorialStep.STEP_9_PET_WINDOW_CLOSED);
/*     */           }
/*     */           
/* 543 */           MainGameState.getArrowWindow().setVisible(false);
/* 544 */           HttpMetrics.postEvent(HttpMetrics.Event.PET_TRAINING_LVL_3);
/*     */           return;
/*     */       } 
/* 547 */       for (PetView view : this.petViews) {
/* 548 */         view.getPetTrainButton().setHighlighted(false);
/*     */       }
/* 550 */       getCloseButton().setEnabled(true);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetsWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */