/*      */ package com.funcom.tcg.client.ui.pets3;
/*      */ import com.funcom.commons.dfx.DireEffect;
import com.funcom.commons.dfx.DireEffectDescription;
/*      */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
import com.funcom.commons.dfx.NoSuchDFXException;
/*      */ import com.funcom.commons.jme.bui.HighlightedButton;
/*      */ import com.funcom.commons.jme.bui.HighlightedToggleButton;
import com.funcom.commons.localization.JavaLocalization;
import com.funcom.commons.utils.ClientUtils;
import com.funcom.gameengine.WorldCoordinate;
import com.funcom.gameengine.jme.modular.ModularDescription;
import com.funcom.gameengine.model.ParticleSurface;
/*      */ import com.funcom.gameengine.model.props.Prop;
/*      */ import com.funcom.gameengine.resourcemanager.ResourceManager;
import com.funcom.gameengine.view.AnimationMapper;
/*      */ import com.funcom.gameengine.view.ModularNode;
/*      */ import com.funcom.gameengine.view.PropNode;
import com.funcom.rpgengine2.combat.UsageParams;
import com.funcom.rpgengine2.equipment.ArchType;
/*      */ import com.funcom.rpgengine2.equipment.PetArchType;
import com.funcom.rpgengine2.pets.PetDescription;
import com.funcom.server.common.Message;
import com.funcom.tcg.TcgConstants;
/*      */ import com.funcom.tcg.client.TcgGame;
/*      */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*      */ import com.funcom.tcg.client.model.rpg.ClientPet;
import com.funcom.tcg.client.model.rpg.PetEventsListener;
/*      */ import com.funcom.tcg.client.net.NetworkHandler;
/*      */ import com.funcom.tcg.client.state.MainGameState;
import com.funcom.tcg.client.ui.BPeelWindow;
import com.funcom.tcg.client.ui.BuiUtils;
/*      */ import com.funcom.tcg.client.ui.CenteredGeomView;
import com.funcom.tcg.client.ui.ClientStatCalc;
/*      */ import com.funcom.tcg.client.ui.GuiStatType;
/*      */ import com.funcom.tcg.client.ui.Localizer;
import com.funcom.tcg.client.ui.OverlayedContainer;
/*      */ import com.funcom.tcg.client.ui.TcgUI;
/*      */ import com.funcom.tcg.client.view.modular.PreviewPetModularNode;
/*      */ import com.funcom.tcg.net.message.TrainPetMessage;
/*      */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*      */ import com.jme.renderer.Renderer;
import com.jme.scene.Spatial;
import com.jmex.bui.BActiveProgressBar;
/*      */ import com.jmex.bui.BClickthroughLabel;
/*      */ import com.jmex.bui.BComponent;
/*      */ import com.jmex.bui.BContainer;
/*      */ import com.jmex.bui.BImage;
/*      */ import com.jmex.bui.BLabel;
/*      */ import com.jmex.bui.BProgressBar;
/*      */ import com.jmex.bui.BStyleSheet;
/*      */ import com.jmex.bui.background.BBackground;
/*      */ import com.jmex.bui.background.ImageBackground;
/*      */ import com.jmex.bui.dragndrop.BDragListener;
/*      */ import com.jmex.bui.dragndrop.BDragNDrop;
/*      */ import com.jmex.bui.dragndrop.BDropEvent;
/*      */ import com.jmex.bui.dragndrop.BDropListener;
/*      */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
    import java.awt.event.ActionEvent;
/*      */ import java.awt.event.ActionListener;
/*      */ import com.jmex.bui.event.BEvent;
/*      */ import com.jmex.bui.event.ComponentListener;
/*      */ import com.jmex.bui.event.MouseAdapter;
/*      */ import com.jmex.bui.event.MouseEvent;
/*      */ import com.jmex.bui.icon.BIcon;
/*      */ import com.jmex.bui.icon.ImageIcon;
/*      */ import com.jmex.bui.layout.AbsoluteLayout;
/*      */ import com.jmex.bui.layout.BLayoutManager;
/*      */ import com.jmex.bui.util.Insets;
/*      */ import com.jmex.bui.util.Rectangle;
/*      */ import java.util.List;
/*      */ import javax.swing.Timer;
/*      */ 
/*      */ public class PetView extends OverlayedContainer {
/*   54 */   private ClientPet clientPet = null; private static final int scrollSpeedThrottle = 5;
/*   55 */   private int totalTokens = 0;
/*   56 */   private int currentNumTokens = 0;
/*   57 */   private int currentExp = 0;
/*   58 */   private int currentLevel = 1;
/*      */   
/*   60 */   private final int BUTTON_OFFSET_X = 16;
/*   61 */   private final int BUTTON_OFFSET_Y = 113;
/*   62 */   private final int BUTTON_SIZE = 32;
/*      */   
/*      */   private static final String NAME_LABEL_STYLE = "pet-title-name-label";
/*      */   
/*      */   private static final String LEVEL_LABEL_STYLE = "pet-title-level-label";
/*      */   private static final String HOVER_LABEL_STYLE = "pet-title-hover-label";
/*      */   private static final String TRIAL_LABEL_STYLE = "pet-trial-label";
/*      */   private static final String BIO_BUTTON_STYLE = "bio-button";
/*      */   private static final String SKILL_BUTTON_STYLE = "skill-button";
/*      */   private static final String VIEW_BUTTON_STYLE = "view-button";
/*      */   private static final String SUBVIEW_INFO_STYLE = "subview-info-label";
/*      */   private static final String TRAIN_BUTTON_STYLE = "train-button";
/*      */   private static final String epicPetCardStyle = "lvl40_pet_card";
/*      */   private static final String petCardStyle = "staticgraphic";
/*   76 */   private final Rectangle HIGHLIGHTING_SIZE = new Rectangle(16, 272, 50, 50);
/*   77 */   private final Rectangle ICON_SIZE = new Rectangle(17, 273, 48, 48);
/*   78 */   private final Rectangle SKILL_TEXT_SIZE = new Rectangle(80, 163, 195, 140);
/*   79 */   private final Rectangle BIO_TEXT_SIZE = new Rectangle(80, 182, 195, 140);
/*   80 */   private final Rectangle SUBVIEW_INFO_SIZE = new Rectangle(80, 287, 195, 40);
/*      */   
/*   82 */   private final Rectangle TRAIN_BUTTON_SIZE = new Rectangle(74, 113, 32, 32);
/*      */   
/*   84 */   private final Rectangle BIO_BUTTON_SIZE = new Rectangle(111, 113, 32, 32);
/*      */   
/*   86 */   private final Rectangle VIEW_BUTTON_SIZE = new Rectangle(148, 113, 32, 32);
/*      */   
/*   88 */   private final Rectangle SKILL_BUTTON_SIZE = new Rectangle(185, 113, 32, 32);
/*      */   
/*      */   private final ResourceManager resourceManager;
/*      */   
/*      */   private final int viewId;
/*      */   private final PetViewSelectionModel petViewSelectionModel;
/*      */   private final float defaultAngle;
/*      */   private CenteredGeomView pet3DView;
/*      */   private PropNode petNode;
/*      */   private DireEffectDescriptionFactory dfxDescriptionFactory;
/*      */   private Rectangle petViewBounds;
/*      */   private boolean selected;
/*      */   private long petTrialTime;
/*  101 */   private String timeText = "";
/*      */   
/*      */   private final DumbLabel nameLabel;
/*      */   
/*      */   private final BLabel trialLabel;
/*      */   
/*      */   private final BLabel levelLabel;
/*      */   
/*      */   private final BLabel hoverLabel;
/*      */   
/*      */   private BContainer bioContainer;
/*      */   
/*      */   private BContainer skillContainer;
/*      */   
/*      */   private BContainer petTrainingContainer;
/*      */   
/*      */   private DumbLabel nameLabelSelected;
/*      */   
/*      */   private BLabel petIconLabel;
/*      */   private BLabel petTrainIconLabel;
/*      */   private DumbLabel petCard;
/*      */   private BLabel skillIconLabel;
/*      */   private BLabel skillTextLabel;
/*      */   private BLabel petTextLabel;
/*      */   private BLabel skillNameLabel;
/*      */   private BLabel tokenTotalLabel;
/*      */   private BLabel starLevelLabel;
/*      */   private BLabel warningLabel;
/*      */   private BClickthroughLabel membersOnly;
/*      */   private BProgressBar xpBar;
/*      */   private BStyleSheet style;
/*      */   private HighlightedToggleButton petTrainButton;
/*      */   private HighlightedToggleButton bioButton;
/*      */   private HighlightedToggleButton viewButton;
/*      */   private HighlightedToggleButton skillButton;
/*      */   private HighlightedButton confirmTrainButton;
/*      */   private HighlightedButton cancelTrainButton;
/*      */   private HighlightedButton addTokenButton;
/*      */   private boolean switchLocked = false;
/*      */   public DragPetContent dragObject;
/*      */   private Timer buttonTimer;
/*      */   private TrainPetListener buttonListener;
/*      */   private PetEventsListener petEventsListener;
/*  144 */   private DireEffectDescription alwaysOnDfxDescription = null;
/*      */   
/*      */   private BLabel[] statIcons;
/*      */   
/*      */   private BLabel[] progressBgds;
/*      */   private BProgressBar[] statIncreaseBars;
/*      */   private BActiveProgressBar[] statBars;
/*      */   private BLabel[] statBarGloss;
/*  152 */   private PetSubView info = PetSubView.VIEW;
/*      */   
/*      */   private boolean tokenAmmountChanged = false;
/*      */   private boolean backgroundChanged = false;
/*      */   private boolean petCardChanged = false;
/*      */   private boolean statsChanged = false;
/*      */   private ImageBackground background;
/*      */   private DumbLabel backdropLabel;
/*      */   private long lastRenderAt;
/*      */   
/*      */   public PetView(int viewId, PetViewSelectionModel petViewSelectionModel, float defaultAngle, ResourceManager resourceManager, DireEffectDescriptionFactory dfxDescriptionFactory, Localizer localizer, int tokens) {
/*  163 */     this.viewId = viewId;
/*  164 */     this.petViewSelectionModel = petViewSelectionModel;
/*  165 */     this.defaultAngle = 0.0F;
/*  166 */     setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*  167 */     this.resourceManager = resourceManager;
/*  168 */     this.dfxDescriptionFactory = dfxDescriptionFactory;
/*      */ 
/*      */     
/*  171 */     this.totalTokens = tokens;
/*  172 */     this.currentNumTokens = tokens;
/*      */     
/*  174 */     this.backdropLabel = new DumbLabel("");
/*  175 */     this.backdropLabel.setStyleClass("backdrop-label");
/*      */     
/*  177 */     this.membersOnly = new BClickthroughLabel("");
/*  178 */     this.membersOnly.setStyleClass("members-label");
/*      */     
/*  180 */     this.pet3DView = new CenteredGeomView(true)
/*      */       {
/*      */         protected void stateDidChange() {
/*  183 */           super.stateDidChange();
/*  184 */           if (PetView.this.pet3DView.getState() == 1 && BDragNDrop.instance().isDragging()) {
/*      */             
/*  186 */             PetView.this.hoverLabel.setVisible(true);
/*      */           } else {
/*  188 */             PetView.this.hoverLabel.setVisible(false);
/*      */           } 
/*      */         }
/*      */       };
/*  192 */     this.pet3DView.setStyleClass("pet-view");
/*  193 */     this.pet3DView.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.drag" + (this.switchLocked ? ".no" : ""), new String[0]));
/*  194 */     this.pet3DView.update(1.0F);
/*  195 */     this.pet3DView.resetCameraPosition();
/*  196 */     BuiUtils.workaroundForParticleFacing();
/*      */     
/*  198 */     initializeBioContainer();
/*  199 */     initializeSkillContainer();
/*  200 */     initializePetTrainingContainer();
/*      */     
/*  202 */     String tp = "gui/peeler/window_pet_training.xml";
/*      */     
/*  204 */     this.nameLabel = new DumbLabel("", true);
/*  205 */     this.nameLabel.setStyleClass("pet-title-name-label");
/*  206 */     this.levelLabel = new BLabel("");
/*  207 */     this.levelLabel.setStyleClass("pet-title-level-label");
/*  208 */     this.levelLabel.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.petlevel", new String[0]));
/*  209 */     this.hoverLabel = (BLabel)new BClickthroughLabel("");
/*  210 */     this.hoverLabel.setStyleClass("pet-title-hover-label");
/*  211 */     this.hoverLabel.setVisible(false);
/*  212 */     this.trialLabel = (BLabel)new BClickthroughLabel("");
/*  213 */     this.trialLabel.setStyleClass("pet-trial-label");
/*  214 */     this.trialLabel.setVisible(false);
/*      */ 
/*      */     
/*  217 */     this.petTrainButton = new HighlightedToggleButton();
/*  218 */     this.petTrainButton.setStyleClass("train-button");
/*  219 */     this.petTrainButton.setEnabled(false);
/*  220 */     this.petTrainButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.train", new String[0]));
/*      */     
/*  222 */     this.bioButton = new HighlightedToggleButton();
/*  223 */     this.bioButton.setStyleClass("bio-button");
/*  224 */     this.bioButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.bio", new String[0]));
/*      */     
/*  226 */     this.viewButton = new HighlightedToggleButton();
/*  227 */     this.viewButton.setStyleClass("view-button");
/*  228 */     this.viewButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.view", new String[0]));
/*      */     
/*  230 */     this.skillButton = new HighlightedToggleButton();
/*  231 */     this.skillButton.setStyleClass("skill-button");
/*  232 */     this.skillButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.skill", new String[0]));
/*      */     
/*  234 */     this.nameLabelSelected = new DumbLabel("", true);
/*  235 */     this.nameLabelSelected.setStyleClass("pet-title-name-label-selected");
/*      */     
/*  237 */     add((BComponent)this.backdropLabel, new Rectangle(0, 0, 290, 375));
/*      */     
/*  239 */     this.petViewBounds = new Rectangle(0, 170, 280, 230);
/*      */     
/*  241 */     add((BComponent)this.pet3DView, this.petViewBounds);
/*  242 */     add((BComponent)this.petTrainingContainer, this.petViewBounds);
/*  243 */     add((BComponent)this.bioContainer, this.petViewBounds);
/*  244 */     add((BComponent)this.skillContainer, this.petViewBounds);
/*      */ 
/*      */     
/*  247 */     add((BComponent)this.levelLabel, new Rectangle(7, 336, 36, 36));
/*  248 */     add((BComponent)this.hoverLabel, new Rectangle(239, 361, 53, 53));
/*  249 */     add((BComponent)this.trialLabel, new Rectangle(0, 155, 280, 51));
/*      */     
/*  251 */     add((BComponent)this.nameLabel, new Rectangle(0, 331, 285, 51));
/*  252 */     add((BComponent)this.nameLabelSelected, new Rectangle(0, 331, 285, 51));
/*      */     
/*  254 */     add((BComponent)this.petTrainButton, this.TRAIN_BUTTON_SIZE);
/*  255 */     add((BComponent)this.bioButton, this.BIO_BUTTON_SIZE);
/*  256 */     add((BComponent)this.viewButton, this.VIEW_BUTTON_SIZE);
/*  257 */     add((BComponent)this.skillButton, this.SKILL_BUTTON_SIZE);
/*      */     
/*  259 */     int statTotal = (GuiStatType.values()).length;
/*  260 */     this.statIcons = new BLabel[statTotal];
/*  261 */     this.progressBgds = new BLabel[statTotal];
/*  262 */     this.statIncreaseBars = new BProgressBar[statTotal];
/*  263 */     this.statBars = new BActiveProgressBar[statTotal];
/*  264 */     this.statBarGloss = new BLabel[statTotal];
/*  265 */     for (int i = 0; i < statTotal; i++) {
/*  266 */       String guiStatString = GuiStatType.values()[i].toString().toLowerCase();
/*      */       
/*  268 */       this.statIcons[i] = new BLabel("");
/*  269 */       this.statIcons[i].setStyleClass("icon-stat-" + guiStatString);
/*  270 */       this.statIcons[i].setTooltipText(TcgGame.getLocalizedText("charwindow.info." + guiStatString, new String[0]));
/*      */       
/*  272 */       this.progressBgds[i] = new BLabel("");
/*  273 */       this.progressBgds[i].setStyleClass("progress-bgd");
/*      */       
/*  275 */       this.statIncreaseBars[i] = new BProgressBar();
/*  276 */       this.statIncreaseBars[i].setStyleClass("progress-stat-increase-" + guiStatString);
/*      */       
/*  278 */       this.statBars[i] = new BActiveProgressBar(BProgressBar.Direction.PROGRESSDIR_EAST, 10L, 0.4F);
/*  279 */       this.statBars[i].setStyleClass("progress-stat-" + guiStatString);
/*      */       
/*  281 */       this.statBarGloss[i] = new BLabel("0");
/*  282 */       this.statBarGloss[i].setStyleClass("progress-stat-gloss");
/*      */       
/*  284 */       int inverse = statTotal - 1 - i;
/*  285 */       int height = 20;
/*      */       
/*  287 */       Rectangle STAT_ICON_SIZE = new Rectangle(16, 8 + inverse * height, height, height);
/*  288 */       Rectangle STAT_PROGRESS_SIZE = new Rectangle(24 + height + 6, 8 + inverse * height, 226, height);
/*      */       
/*  290 */       add((BComponent)this.statIcons[i], STAT_ICON_SIZE);
/*  291 */       add((BComponent)this.progressBgds[i], STAT_PROGRESS_SIZE);
/*  292 */       add((BComponent)this.statIncreaseBars[i], STAT_PROGRESS_SIZE);
/*  293 */       add((BComponent)this.statBars[i], STAT_PROGRESS_SIZE);
/*  294 */       add((BComponent)this.statBarGloss[i], STAT_PROGRESS_SIZE);
/*      */     } 
/*      */     
/*  297 */     add((BComponent)this.membersOnly, new Rectangle(0, 0, 290, 375));
/*      */     
/*  299 */     initListeners();
/*  300 */     resetView();
/*      */   }
/*      */   
/*      */   private void initializePetTrainingContainer() {
/*  304 */     if (this.clientPet != null) {
/*  305 */       this.currentLevel = this.clientPet.getLevel();
/*  306 */       this.currentExp = this.clientPet.getExp();
/*      */     } 
/*      */     
/*  309 */     this.petTrainingContainer = new BContainer();
/*  310 */     this.petTrainingContainer.setStyleClass("pet-view");
/*  311 */     this.petTrainingContainer.setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*      */     
/*  313 */     this.confirmTrainButton = new HighlightedButton();
/*  314 */     this.confirmTrainButton.setStyleClass("button_accept");
/*  315 */     this.confirmTrainButton.setEnabled(false);
/*  316 */     this.confirmTrainButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.confirm", new String[0]));
/*      */     
/*  318 */     this.cancelTrainButton = new HighlightedButton();
/*  319 */     this.cancelTrainButton.setStyleClass("button_cancel");
/*  320 */     this.cancelTrainButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.cancel", new String[0]));
/*      */     
/*  322 */     this.addTokenButton = new HighlightedButton();
/*  323 */     this.addTokenButton.setStyleClass("add_token_button");
/*  324 */     updateAddToken();
/*  325 */     this.addTokenButton.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.addtoken", new String[0]));
/*      */     
/*  327 */     this.xpBar = new BProgressBar();
/*  328 */     this.xpBar.setStyleClass("pet_xpbar");
/*  329 */     updateXp();
/*      */     
/*  331 */     this.tokenTotalLabel = (BLabel)new BClickthroughLabel("" + this.totalTokens);
/*  332 */     this.tokenTotalLabel.setStyleClass("train-token-label");
/*      */     
/*  334 */     this.starLevelLabel = new BLabel("");
/*  335 */     this.starLevelLabel.setStyleClass("level-label-icon");
/*      */     
/*  337 */     this.petTrainIconLabel = new BLabel("");
/*  338 */     this.petCard = new DumbLabel("");
/*  339 */     this.petCard.setStyleClass("staticgraphic");
/*      */     
/*  341 */     this.warningLabel = new BLabel("");
/*  342 */     this.warningLabel.setStyleClass("warning-label");
/*      */     
/*  344 */     int button_x = 124;
/*  345 */     int button_y = 4;
/*      */     
/*  347 */     int cardSize = 76;
/*  348 */     int tokenSize = 100;
/*  349 */     int cardBorder = (cardSize - 48) / 2;
/*  350 */     int UPGRADE_OFFSET = 137;
/*      */     
/*  352 */     Rectangle ADD_TOKEN_BUTTON_SIZE = new Rectangle(16, UPGRADE_OFFSET + 16 + 32, tokenSize, cardSize);
/*  353 */     Rectangle TOKEN_TOTAL_BUTTON_SIZE = new Rectangle(16, UPGRADE_OFFSET + 24 + 32 + cardSize - 6, tokenSize, 30);
/*  354 */     Rectangle PET_CARD_BUTTON_SIZE = new Rectangle(32 + tokenSize, UPGRADE_OFFSET + 16 + 32, cardSize, tokenSize);
/*  355 */     Rectangle PET_CARD_ICON_SIZE = new Rectangle(PET_CARD_BUTTON_SIZE.x + cardBorder, PET_CARD_BUTTON_SIZE.y + tokenSize - 48 - cardBorder, 48, 48);
/*  356 */     Rectangle PET_CARD_XP_SIZE = new Rectangle(PET_CARD_BUTTON_SIZE.x + 9, PET_CARD_BUTTON_SIZE.y + 8, 58, 5);
/*  357 */     Rectangle PET_CARD_STAR_SIZE = new Rectangle(PET_CARD_BUTTON_SIZE.x + cardBorder, PET_CARD_XP_SIZE.y + 10, cardSize - 2 * cardBorder, 16);
/*      */     
/*  359 */     Rectangle CONFIRM_TRAIN_SIZE = new Rectangle(PET_CARD_BUTTON_SIZE.x + PET_CARD_BUTTON_SIZE.width + 16, UPGRADE_OFFSET + 16 + 32 + 48 + 4, 48, 48);
/*      */ 
/*      */ 
/*      */     
/*  363 */     Rectangle CANCEL_TRAIN_SIZE = new Rectangle(CONFIRM_TRAIN_SIZE.x, UPGRADE_OFFSET + 16 + 32, 48, 48);
/*      */ 
/*      */ 
/*      */     
/*  367 */     this.petTrainingContainer.add((BComponent)this.addTokenButton, ADD_TOKEN_BUTTON_SIZE);
/*  368 */     this.petTrainingContainer.add((BComponent)this.petCard, PET_CARD_BUTTON_SIZE);
/*  369 */     this.petTrainingContainer.add((BComponent)this.petTrainIconLabel, PET_CARD_ICON_SIZE);
/*  370 */     this.petTrainingContainer.add((BComponent)this.xpBar, PET_CARD_XP_SIZE);
/*      */     
/*  372 */     this.petTrainingContainer.add((BComponent)this.starLevelLabel, PET_CARD_STAR_SIZE);
/*      */     
/*  374 */     this.petTrainingContainer.add((BComponent)this.tokenTotalLabel, TOKEN_TOTAL_BUTTON_SIZE);
/*      */ 
/*      */ 
/*      */     
/*  378 */     this.petTrainingContainer.add((BComponent)this.confirmTrainButton, CONFIRM_TRAIN_SIZE);
/*      */     
/*  380 */     this.petTrainingContainer.add((BComponent)this.cancelTrainButton, CANCEL_TRAIN_SIZE);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeSkillContainer() {
/*  385 */     this.skillContainer = new BContainer();
/*  386 */     this.skillContainer.setStyleClass("pet-view");
/*  387 */     this.skillContainer.setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*  388 */     this.skillContainer.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.drag" + (this.switchLocked ? ".no" : ""), new String[0]));
/*      */     
/*  390 */     this.skillNameLabel = (BLabel)new BClickthroughLabel("");
/*  391 */     this.skillNameLabel.setStyleClass("subview-info-label");
/*      */     
/*  393 */     BLabel skillHighlighting = new BLabel("");
/*  394 */     skillHighlighting.setStyleClass("icon-overlay-label");
/*  395 */     this.skillIconLabel = (BLabel)new BClickthroughLabel("");
/*  396 */     this.skillTextLabel = new BLabel("");
/*  397 */     this.skillTextLabel.setStyleClass("info-text-label");
/*      */     
/*  399 */     this.skillContainer.add((BComponent)skillHighlighting, this.HIGHLIGHTING_SIZE);
/*  400 */     this.skillContainer.add((BComponent)this.skillNameLabel, this.SUBVIEW_INFO_SIZE);
/*  401 */     this.skillContainer.add((BComponent)this.skillIconLabel, this.ICON_SIZE);
/*  402 */     this.skillContainer.add((BComponent)this.skillTextLabel, this.SKILL_TEXT_SIZE);
/*      */   }
/*      */ 
/*      */   
/*      */   private void initializeBioContainer() {
/*  407 */     this.bioContainer = new BContainer();
/*  408 */     this.bioContainer.setStyleClass("pet-view");
/*  409 */     this.bioContainer.setLayoutManager((BLayoutManager)new AbsoluteLayout());
/*  410 */     this.bioContainer.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.drag" + (this.switchLocked ? ".no" : ""), new String[0]));
/*      */     
/*  412 */     BLabel bioHighlighting = new BLabel("");
/*  413 */     bioHighlighting.setStyleClass("icon-overlay-label");
/*      */     
/*  415 */     this.petIconLabel = (BLabel)new BClickthroughLabel("");
/*  416 */     this.petTextLabel = (BLabel)new BClickthroughLabel("");
/*  417 */     this.petTextLabel.setStyleClass("info-text-label");
/*      */     
/*  419 */     this.bioContainer.add((BComponent)bioHighlighting, this.HIGHLIGHTING_SIZE);
/*  420 */     this.bioContainer.add((BComponent)this.petIconLabel, this.ICON_SIZE);
/*  421 */     this.bioContainer.add((BComponent)this.petTextLabel, this.BIO_TEXT_SIZE);
/*      */   }
/*      */   
/*      */   public int getViewId() {
/*  425 */     return this.viewId;
/*      */   }
/*      */   
/*      */   public void select() {
/*  429 */     this.petViewSelectionModel.select(this);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean dispatchEvent(BEvent event) {
/*  434 */     if (event instanceof MouseEvent) {
/*  435 */       MouseEvent mouseEvent = (MouseEvent)event;
/*      */       
/*  437 */       if (mouseEvent.getType() == 0) {
/*  438 */         this.petViewSelectionModel.select(this);
/*  439 */         return true;
/*      */       } 
/*      */     } 
/*      */     
/*  443 */     return super.dispatchEvent(event);
/*      */   }
/*      */   
/*      */   protected void initListeners() {
/*  447 */     PetWindowPet dragPet = new PetWindowPet(null);
/*  448 */     this.dragObject = new DragPetContent(dragPet);
/*  449 */     BDragListener dragListener = new BDragListener((BComponent)this, this.dragObject, new BDragListener.IconRequest() {
/*      */           public BIcon getIcon() {
/*  451 */             if (PetView.this.clientPet == null || PetView.this.switchLocked)
/*  452 */               return null; 
/*  453 */             return (BIcon)new ImageIcon((BImage)PetView.this.resourceManager.getResource(BImage.class, PetView.this.clientPet.getPetVisuals().getIcon()));
/*      */           }
/*      */         });
/*  456 */     this.pet3DView.addListener((ComponentListener)dragListener);
/*  457 */     this.bioContainer.addListener((ComponentListener)dragListener);
/*  458 */     this.skillContainer.addListener((ComponentListener)dragListener);
/*  459 */     this.nameLabel.addListener((ComponentListener)dragListener);
/*  460 */     this.nameLabelSelected.addListener((ComponentListener)dragListener);
/*  461 */     this.levelLabel.addListener((ComponentListener)dragListener);
/*  462 */     this.trialLabel.addListener((ComponentListener)dragListener);
/*      */     
/*  464 */     this.buttonListener = new TrainPetListener();
/*  465 */     this.buttonTimer = new Timer(5, this.buttonListener);
/*  466 */     this.buttonTimer.setInitialDelay(300);
/*      */     
/*  468 */     addListener((ComponentListener)new BDropListener()
/*      */         {
/*      */           protected void drop(BDropEvent e) {
/*  471 */             if (!PetView.this.isSwitchLocked() && e.getDragEvent().getDraggedObject() instanceof DragPetContent) {
/*  472 */               ClientPet pet = ((DragPetContent)e.getDragEvent().getDraggedObject()).getPet().getPlayerPet();
/*  473 */               PetView.this.petViewSelectionModel.setPetForView(PetView.this.viewId, pet);
/*      */             } 
/*      */           }
/*      */         });
/*      */     
/*  478 */     this.petTrainButton.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*  481 */             PetView.this.info = PetSubView.TRAIN;
/*  482 */             PetView.this.updateSubViews();
/*      */           }
/*      */         });
/*      */     
/*  486 */     this.bioButton.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*  489 */             PetView.this.info = PetSubView.BIO;
/*  490 */             PetView.this.updateSubViews();
/*      */           }
/*      */         });
/*      */     
/*  494 */     this.viewButton.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*  497 */             PetView.this.info = PetSubView.VIEW;
/*  498 */             PetView.this.updateSubViews();
/*      */           }
/*      */         });
/*      */     
/*  502 */     this.skillButton.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*  505 */             PetView.this.info = PetSubView.SKILL;
/*  506 */             PetView.this.updateSubViews();
/*      */           }
/*      */         });
/*      */ 
/*      */ 
/*      */     
/*  512 */     this.addTokenButton.addListener((ComponentListener)new MouseAdapter()
/*      */         {
/*      */           public void mousePressed(MouseEvent event) {
/*  515 */             PetView.this.addToken();
/*  516 */             if (PetView.this.currentLevel < MainGameState.getPlayerModel().getStatSupport().getLevel()) {
/*  517 */               PetView.this.buttonTimer.stop();
/*  518 */               PetView.this.buttonTimer.start();
/*      */             } 
/*      */           }
/*      */ 
/*      */           
/*      */           public void mouseReleased(MouseEvent event) {
/*  524 */             PetView.this.buttonTimer.stop();
/*      */           }
/*      */         });
/*      */     
/*  528 */     this.cancelTrainButton.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*  531 */             PetView.this.resetView();
/*      */           }
/*      */         });
/*      */     
/*  535 */     this.confirmTrainButton.addListener((ComponentListener)new ActionListener()
/*      */         {
/*      */           public void actionPerformed(ActionEvent event) {
/*  538 */             int tokensUsed = PetView.this.totalTokens - PetView.this.currentNumTokens;
/*  539 */             int tempNumTokens = PetView.this.currentNumTokens;
/*  540 */             if (tokensUsed > 0) {
/*  541 */               if (PetView.this.clientPet.getLevel() != PetView.this.currentLevel)
/*  542 */                 TcgUI.getUISoundPlayer().play("PetLevel"); 
/*  543 */               PetView.this.clientPet.setLevel(PetView.this.currentLevel);
/*  544 */               PetView.this.clientPet.setExp(PetView.this.currentExp);
/*  545 */               MainGameState.updatePetSize(PetView.this.clientPet);
/*      */               
/*  547 */               PetView.this.updatePetStats(false, PetView.this.clientPet.getLevel());
/*      */ 
/*      */               
/*      */               try {
/*  551 */                 NetworkHandler.instance().getIOHandler().send((Message)new TrainPetMessage(tokensUsed, PetView.this.clientPet.getBaseClassId()));
/*      */               }
/*  553 */               catch (InterruptedException e) {
/*  554 */                 TcgGame.LOGGER.error("Failed to send Train pet message", e);
/*      */               } 
/*      */             } 
/*  557 */             PetView.this.totalTokens = tempNumTokens;
/*  558 */             if (TcgGame.getPetTutorialId() == 0) {
/*  559 */               PetView.this.updateTutorial(PetTutorialStep.STEP_8_PET_WINDOW_CONFIRM_CLOSE);
/*      */             } else {
/*  561 */               PetView.this.updateTutorial(PetTutorialStep.STEP_2_6_PET_WINDOW_CONFIRM_CLOSE);
/*      */             } 
/*  563 */             PetView.this.resetView();
/*      */           }
/*      */         });
/*      */   }
/*      */ 
/*      */   
/*      */   private void addToken() {
/*  570 */     if (!this.addTokenButton.isEnabled())
/*      */       return; 
/*  572 */     this.tokenAmmountChanged = true;
/*  573 */     this.currentNumTokens--;
/*  574 */     this.currentExp++;
/*  575 */     this.confirmTrainButton.setEnabled(true);
/*  576 */     if (this.currentExp == this.currentLevel) {
/*  577 */       this.currentLevel++;
/*      */       
/*  579 */       this.statsChanged = true;
/*      */       
/*  581 */       this.currentExp = 0;
/*  582 */       if (this.currentLevel >= 40) {
/*  583 */         String iconPath = this.clientPet.getEpicIcon();
/*  584 */         if (this.resourceManager.exists(iconPath)) {
/*  585 */           BImage petImage = (BImage)this.resourceManager.getResource(BImage.class, iconPath);
/*  586 */           this.background = new ImageBackground(ImageBackgroundMode.SCALE_XY, petImage);
/*  587 */           this.backgroundChanged = true;
/*      */         } 
/*  589 */         this.petCard.setStyleClass("lvl40_pet_card");
/*  590 */         this.petCardChanged = true;
/*      */       } 
/*  592 */       if (this.currentLevel >= MainGameState.getPlayerModel().getStatSupport().getLevel()) {
/*  593 */         updateAddToken();
/*  594 */         this.buttonTimer.stop();
/*      */       } 
/*  596 */       this.xpBar.setProgress(0.0F);
/*      */     } else {
/*  598 */       updateXp();
/*      */     } 
/*      */     
/*  601 */     updateAddToken();
/*  602 */     if (this.currentNumTokens == 0) this.buttonTimer.stop();
/*      */ 
/*      */ 
/*      */     
/*  606 */     if (TcgGame.getPetTutorialId() == 0) {
/*  607 */       updateTutorial(PetTutorialStep.STEP_7_TRAIN_PET_CONFIRM_CLOSE);
/*      */     }
/*  609 */     else if (this.currentExp > 0) {
/*  610 */       updateTutorial(PetTutorialStep.STEP_2_3_TRAIN_PET_PRESS_UP_AGAIN);
/*      */     } else {
/*  612 */       updateTutorial(PetTutorialStep.STEP_2_4_PET_STATS_INCREASE);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateSubViews() {
/*  619 */     this.bioButton.setSelected(this.info.equals(PetSubView.BIO));
/*  620 */     this.viewButton.setSelected(this.info.equals(PetSubView.VIEW));
/*  621 */     this.skillButton.setSelected(this.info.equals(PetSubView.SKILL));
/*  622 */     this.petTrainButton.setSelected(this.info.equals(PetSubView.TRAIN));
/*      */     
/*  624 */     switch (this.info) {
/*      */       case VIEW:
/*  626 */         this.bioContainer.setVisible(false);
/*  627 */         this.skillContainer.setVisible(false);
/*  628 */         this.petTrainingContainer.setVisible(false);
/*  629 */         TcgUI.getUISoundPlayer().play("ClickForward");
/*      */         return;
/*      */       case BIO:
/*  632 */         this.bioContainer.setVisible(true);
/*  633 */         this.skillContainer.setVisible(false);
/*  634 */         this.petTrainingContainer.setVisible(false);
/*  635 */         TcgUI.getUISoundPlayer().play("ClickForward");
/*      */         return;
/*      */       case SKILL:
/*  638 */         this.bioContainer.setVisible(false);
/*  639 */         this.skillContainer.setVisible(true);
/*  640 */         this.petTrainingContainer.setVisible(false);
/*  641 */         TcgUI.getUISoundPlayer().play("ClickForward");
/*      */         return;
/*      */       case TRAIN:
/*  644 */         this.bioContainer.setVisible(false);
/*  645 */         this.skillContainer.setVisible(false);
/*  646 */         this.petTrainingContainer.setVisible(this.petTrainButton.isSelected());
/*      */         
/*  648 */         this.petCard.setStyleClass((this.clientPet.getLevel() >= 40) ? "lvl40_pet_card" : "staticgraphic");
/*  649 */         this.petCardChanged = true;
/*  650 */         if (!this.petTrainButton.isSelected()) {
/*  651 */           resetView();
/*      */         } else {
/*  653 */           TcgUI.getUISoundPlayer().play("PetTraining");
/*  654 */           this.confirmTrainButton.setEnabled((this.totalTokens > Integer.parseInt(this.tokenTotalLabel.getText())));
/*  655 */           updateXp();
/*  656 */           MainGameState.getPetsWindow().toggleTrainingMode(this.viewId);
/*  657 */           if (TcgGame.getPetTutorialId() == 0) {
/*  658 */             updateTutorial(PetTutorialStep.STEP_6_TRAIN_PET_PRESS_UP);
/*      */           } else {
/*  660 */             updateTutorial(PetTutorialStep.STEP_2_2_TRAIN_PET_PRESS_UP);
/*      */           } 
/*      */         } 
/*      */         return;
/*      */     } 
/*  665 */     this.bioContainer.setVisible(false);
/*  666 */     this.skillContainer.setVisible(false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updateAddToken() {
/*  672 */     if (this.clientPet != null) {
/*  673 */       this.addTokenButton.setEnabled((this.currentNumTokens > 0 && this.currentLevel < MainGameState.getPlayerModel().getStatSupport().getLevel()));
/*      */ 
/*      */       
/*  676 */       if (this.currentLevel >= MainGameState.getPlayerModel().getStatSupport().getLevel()) {
/*  677 */         this.warningLabel.setText(TcgGame.getLocalizedText("petswindow.upperwindow.text.petmaxlevel", new String[0]));
/*  678 */       } else if (this.currentNumTokens <= 0) {
/*  679 */         this.warningLabel.setText(TcgGame.getLocalizedText("petswindow.upperwindow.text.notokens", new String[0]));
/*      */       } else {
/*  681 */         this.warningLabel.setText("");
/*      */       }
/*      */     
/*      */     } else {
/*      */       
/*  686 */       this.addTokenButton.setEnabled((this.currentNumTokens > 0));
/*      */     } 
/*      */   }
/*      */   
/*      */   private void updateXp() {
/*      */     try {
/*  692 */       if (this.currentLevel > 0) {
/*  693 */         float xpProgress = this.currentExp / this.currentLevel;
/*  694 */         this.xpBar.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.xp", new String[] { String.valueOf(this.currentExp), String.valueOf(this.currentLevel) }));
/*  695 */         if (xpProgress >= 0.0F && xpProgress <= 1.0F) {
/*  696 */           this.xpBar.setProgress(xpProgress);
/*      */         }
/*      */       } 
/*  699 */     } catch (ArithmeticException e) {
/*  700 */       TcgGame.LOGGER.info("Oh bother, you divided by 0 didn't you?");
/*  701 */       this.xpBar.setProgress(0.0F);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   protected String getDefaultStyleClass() {
/*  707 */     return "pet-view-container";
/*      */   }
/*      */ 
/*      */   
/*      */   protected void configureStyle(BStyleSheet style) {
/*  712 */     this.style = style;
/*  713 */     super.configureStyle(style);
/*      */   }
/*      */ 
/*      */   
/*      */   protected void layout() {
/*  718 */     super.layout();
/*  719 */     Insets insets = getInsets();
/*  720 */     this.petViewBounds.set(insets.left, insets.top, getWidth() - insets.getHorizontal(), getHeight() - insets.getVertical());
/*      */     
/*  722 */     this.pet3DView.setBounds(this.petViewBounds.x, 132, this.petViewBounds.width, this.petViewBounds.width);
/*  723 */     this.bioContainer.setBounds(this.petViewBounds.x, this.petViewBounds.y, this.petViewBounds.width, this.petViewBounds.height);
/*  724 */     this.skillContainer.setBounds(this.petViewBounds.x, this.petViewBounds.y, this.petViewBounds.width, this.petViewBounds.height);
/*  725 */     this.petTrainingContainer.setBounds(this.petViewBounds.x, this.petViewBounds.y, this.petViewBounds.width, this.petViewBounds.height);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderComponent(Renderer renderer) {
/*  732 */     long now = System.currentTimeMillis();
/*      */     
/*  734 */     if (this.lastRenderAt != 0L) {
/*  735 */       long updateMillis = now - this.lastRenderAt;
/*  736 */       if (this.selected && this.petNode != null) {
/*  737 */         float angle = this.petNode.getAngle();
/*  738 */         angle = (float)(angle + 0.7853981633974483D * updateMillis / 1000.0D);
/*  739 */         this.petNode.setAngle((float)(angle % 6.283185307179586D));
/*      */       } 
/*      */     } 
/*  742 */     this.lastRenderAt = now;
/*      */     
/*  744 */     if (this.switchLocked) {
/*  745 */       this.membersOnly.render(renderer);
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  750 */     this.backdropLabel.render(renderer);
/*  751 */     switch (this.info) {
/*      */       case VIEW:
/*  753 */         this.pet3DView.render(renderer);
/*      */         break;
/*      */       case BIO:
/*  756 */         this.bioContainer.render(renderer);
/*      */         break;
/*      */       case SKILL:
/*  759 */         this.skillContainer.render(renderer);
/*      */         break;
/*      */       case TRAIN:
/*  762 */         this.petTrainingContainer.render(renderer);
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  768 */     renderOverlay(renderer, this.selected);
/*      */     
/*  770 */     if (this.tokenAmmountChanged) {
/*      */       
/*  772 */       this.tokenAmmountChanged = false;
/*  773 */       this.tokenTotalLabel.setText(String.valueOf(this.currentNumTokens));
/*  774 */       this.starLevelLabel.setText(String.valueOf(this.currentLevel));
/*      */     } 
/*  776 */     if (this.backgroundChanged) {
/*  777 */       this.backgroundChanged = false;
/*  778 */       this.petTrainIconLabel.setBackground(0, (BBackground)this.background);
/*  779 */       this.petTrainIconLabel.setBackground(1, (BBackground)this.background);
/*      */     } 
/*      */     
/*  782 */     if (this.petCardChanged) {
/*  783 */       this.petCardChanged = false;
/*  784 */       this.petCard.updateStyle();
/*      */     } 
/*      */     
/*  787 */     if (!this.switchLocked) {
/*  788 */       this.petTrainButton.render(renderer);
/*  789 */       this.bioButton.render(renderer);
/*  790 */       this.viewButton.render(renderer);
/*  791 */       this.skillButton.render(renderer);
/*      */       
/*  793 */       if (this.selected) {
/*  794 */         this.nameLabelSelected.render(renderer);
/*      */       } else {
/*  796 */         this.nameLabel.render(renderer);
/*      */       } 
/*  798 */       this.levelLabel.render(renderer);
/*      */     } 
/*      */     
/*  801 */     for (int i = 0; i < (GuiStatType.values()).length; i++) {
/*  802 */       this.progressBgds[i].render(renderer);
/*  803 */       this.statIcons[i].render(renderer);
/*  804 */       this.statIncreaseBars[i].render(renderer);
/*  805 */       this.statBars[i].render(renderer);
/*  806 */       this.statBarGloss[i].render(renderer);
/*      */     } 
/*      */     
/*  809 */     if (this.statsChanged) {
/*  810 */       this.statsChanged = false;
/*  811 */       updatePetStats(true, this.currentLevel);
/*      */     } 
/*      */     
/*  814 */     if (this.trialLabel.isVisible() && !MainGameState.isPlayerSubscriber()) {
/*  815 */       this.trialLabel.render(renderer);
/*  816 */       String s = ClientUtils.toTimeString(ClientUtils.calcPassedSeconds(this.petTrialTime, 0L), (Localizer)TcgGame.getInstance(), PetView.class);
/*      */       
/*  818 */       if (!s.equals(this.timeText)) {
/*  819 */         this.timeText = s;
/*  820 */         this.trialLabel.setText(this.timeText);
/*      */       } 
/*      */     } 
/*  823 */     this.hoverLabel.render(renderer);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected void renderOverlay(Renderer renderer, boolean selected) {
/*  833 */     if (selected) {
/*  834 */       renderSelected(renderer);
/*      */     } else {
/*  836 */       renderOverlay(renderer);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void updatePetViewContainer(ClientPet pet) {
/*  841 */     if (this.clientPet != null && pet != this.clientPet) {
/*  842 */       this.clientPet.removePetEventsListener(this.petEventsListener);
/*      */     }
/*  844 */     boolean updateXp = !pet.equals(this.clientPet);
/*  845 */     this.clientPet = pet;
/*  846 */     if (this.clientPet != null) {
/*  847 */       this.currentLevel = this.clientPet.getLevel();
/*  848 */       this.currentExp = updateXp ? this.clientPet.getExp() : this.currentExp;
/*      */       
/*  850 */       updatePetStats(false, this.clientPet.getLevel());
/*      */     } 
/*      */ 
/*      */     
/*  854 */     cleanupPetNode();
/*  855 */     resetView();
/*      */     
/*  857 */     this.petNode = createPetNode(pet);
/*      */     
/*  859 */     this.petNode.setLocalTranslation(0.0F, 0.0F, 0.0F);
/*  860 */     this.petNode.updateWorldVectors();
/*      */     
/*  862 */     BImage petImage = null;
/*  863 */     if (pet.getLevel() >= 40) {
/*  864 */       String iconPath = this.clientPet.getIcon();
/*  865 */       if (this.resourceManager.exists(iconPath)) {
/*  866 */         petImage = (BImage)this.resourceManager.getResource(BImage.class, iconPath);
/*      */       } else {
/*  868 */         petImage = (BImage)this.resourceManager.getResource(BImage.class, pet.getIcon());
/*      */       } 
/*  870 */       this.petCard.setStyleClass("lvl40_pet_card");
/*  871 */       this.petCardChanged = true;
/*      */     } else {
/*  873 */       petImage = (BImage)this.resourceManager.getResource(BImage.class, pet.getIcon());
/*      */     } 
/*      */     
/*  876 */     this.petIconLabel.setIcon((BIcon)new ImageIcon(petImage));
/*  877 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, petImage);
/*  878 */     this.petTrainIconLabel.setBackground(0, (BBackground)imageBackground);
/*  879 */     this.petTrainIconLabel.setBackground(1, (BBackground)imageBackground);
/*  880 */     this.petTextLabel.setText(JavaLocalization.getInstance().getLocalizedRPGText(pet.getDescription()));
/*      */ 
/*      */     
/*  883 */     List<ClientItem> skills = pet.getAllSkills();
/*  884 */     ClientItem lastSkill = skills.get(skills.size() - 1);
/*  885 */     BImage iconImage = (BImage)this.resourceManager.getResource(BImage.class, lastSkill.getIcon());
/*  886 */     this.skillIconLabel.setIcon((BIcon)new ImageIcon(iconImage));
/*  887 */     this.skillNameLabel.setText(lastSkill.getName());
/*  888 */     this.skillTextLabel.setText(lastSkill.getItemText());
/*      */     
/*  890 */     this.nameLabel.setText(pet.getName().toUpperCase());
/*  891 */     this.levelLabel.setText(Integer.toString(pet.getLevel()));
/*  892 */     this.starLevelLabel.setText(Integer.toString(pet.getLevel()));
/*      */     
/*  894 */     this.nameLabelSelected.setText(pet.getName().toUpperCase());
/*      */     
/*  896 */     if (!this.switchLocked) {
/*  897 */       update3DView(this.petNode);
/*  898 */       this.petNode.setAngle(this.defaultAngle);
/*  899 */       if (pet.getLevel() >= 40) {
/*  900 */         this.backdropLabel.setStyleClass("backdrop-label-upgraded");
/*  901 */         this.nameLabel.setStyleClass("pet-title-name-label-upgraded");
/*  902 */         this.nameLabelSelected.setStyleClass("pet-title-name-label-selected-upgraded");
/*      */       } else {
/*  904 */         this.backdropLabel.setStyleClass("backdrop-label");
/*  905 */         this.nameLabel.setStyleClass("pet-title-name-label");
/*  906 */         this.nameLabelSelected.setStyleClass("pet-title-name-label-selected");
/*      */       } 
/*      */       
/*  909 */       if (this.style != null) {
/*  910 */         this.backdropLabel.configureStyle(this.style);
/*  911 */         this.nameLabel.configureStyle(this.style);
/*  912 */         this.nameLabelSelected.configureStyle(this.style);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void resetView() {
/*  918 */     PetSubView resetView = PetSubView.VIEW;
/*  919 */     this.info = this.info.equals(PetSubView.TRAIN) ? resetView : this.info;
/*      */     
/*  921 */     if (this.currentLevel >= 40 && this.clientPet != null) {
/*  922 */       String iconPath = this.clientPet.getIcon();
/*  923 */       BImage petImage = (BImage)this.resourceManager.getResource(BImage.class, iconPath);
/*  924 */       this.background = new ImageBackground(ImageBackgroundMode.SCALE_XY, petImage);
/*  925 */       this.backgroundChanged = true;
/*      */     } 
/*      */     
/*  928 */     this.currentExp = (this.clientPet != null) ? this.clientPet.getExp() : 0;
/*  929 */     this.currentLevel = (this.clientPet != null) ? this.clientPet.getLevel() : 1;
/*      */     
/*  931 */     this.currentNumTokens = this.totalTokens;
/*  932 */     this.tokenAmmountChanged = true;
/*      */     
/*  934 */     this.petTrainButton.setSelected(false);
/*  935 */     this.petTrainButton.setEnabled((this.clientPet != null && this.totalTokens > 0 && (MainGameState.getPlayerModel().isSubscriber() || !this.clientPet.isSubscriberOnly()) && this.clientPet.getLevel() < MainGameState.getPlayerModel().getStatSupport().getLevel()));
/*      */ 
/*      */     
/*  938 */     this.bioButton.setEnabled((this.clientPet != null));
/*  939 */     this.viewButton.setEnabled((this.clientPet != null));
/*  940 */     this.skillButton.setEnabled((this.clientPet != null));
/*  941 */     this.bioButton.setVisible((this.clientPet != null));
/*  942 */     this.viewButton.setVisible((this.clientPet != null));
/*  943 */     this.skillButton.setVisible((this.clientPet != null));
/*      */ 
/*      */     
/*  946 */     this.starLevelLabel.setText("" + this.currentLevel);
/*  947 */     this.warningLabel.setText("");
/*  948 */     updateXp();
/*  949 */     updateSubViews();
/*  950 */     updateAddToken();
/*  951 */     if (this.clientPet != null) updatePetStats(false, this.clientPet.getLevel());
/*      */     
/*  953 */     this.petTrainButton.setVisible((this.clientPet != null));
/*      */ 
/*      */     
/*  956 */     this.viewButton.setVisible((this.clientPet != null && this.viewButton.isVisible()));
/*  957 */     this.bioButton.setVisible((this.clientPet != null && this.bioButton.isVisible()));
/*  958 */     this.skillButton.setVisible((this.clientPet != null && this.skillButton.isVisible()));
/*      */     
/*  960 */     if (this.clientPet != null) {
/*  961 */       this.trialLabel.setVisible(this.clientPet.isOnTrial());
/*  962 */       if (this.clientPet.isOnTrial()) {
/*  963 */         this.petTrialTime = this.clientPet.getPetTrialExpireTime();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void dispose() {
/*  969 */     petChanged((ClientPet)null);
/*  970 */     cleanupPetNode();
/*      */   }
/*      */   
/*      */   private void update3DView(PropNode petNode) {
/*  974 */     this.pet3DView.setGeometry(petNode);
/*  975 */     this.pet3DView.update(1.0F);
/*  976 */     this.pet3DView.resetCameraPosition();
/*      */   }
/*      */   
/*      */   private void cleanupPetNode() {
/*  980 */     if (this.petNode != null) {
/*  981 */       ModularNode representation = (ModularNode)this.petNode.getRepresentation();
/*  982 */       representation.dispose();
/*      */     } 
/*  984 */     this.petNode = null;
/*  985 */     this.pet3DView.setGeometry(null);
/*      */   }
/*      */   
/*      */   private PropNode createPetNode(ClientPet petListItem) {
/*  989 */     ModularDescription modularDescription = petListItem.getPetDescription();
/*  990 */     Prop prop = new Prop(petListItem.getClassId(), new WorldCoordinate());
/*  991 */     PropNode propNode = new PropNode(prop, 3, "", this.dfxDescriptionFactory);
/*      */     
/*  993 */     PreviewPetModularNode previewPetModularNode = new PreviewPetModularNode(modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, this.resourceManager);
/*      */ 
/*      */     
/*  996 */     previewPetModularNode.reloadCharacter();
/*  997 */     propNode.attachRepresentation((Spatial)previewPetModularNode);
/*      */     
/*  999 */     propNode.setWorldOriginAligned(false);
/* 1000 */     propNode.updateRenderState();
/*      */     
/* 1002 */     propNode.initializeEffects((ParticleSurface)this.pet3DView);
/* 1003 */     CreatureVisualDescription visuals = petListItem.getPetVisuals();
/* 1004 */     this.alwaysOnDfxDescription = null;
/* 1005 */     if (!visuals.getAlwaysOnDfx().isEmpty()) {
/*      */       try {
/* 1007 */         this.alwaysOnDfxDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(visuals.getAlwaysOnDfx(), false);
/*      */         
/* 1009 */         if (!this.alwaysOnDfxDescription.isEmpty()) {
/* 1010 */           DireEffect dfx = this.alwaysOnDfxDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 1011 */           propNode.addDfx(dfx);
/*      */         } 
/* 1013 */       } catch (NoSuchDFXException e) {}
/*      */     }
/*      */ 
/*      */     
/* 1017 */     return propNode;
/*      */   }
/*      */   
/*      */   public void setSelected(boolean selected) {
/* 1021 */     if (!this.switchLocked) {
/* 1022 */       this.selected = selected;
/*      */     } else {
/* 1024 */       this.selected = false;
/*      */     } 
/*      */     
/* 1027 */     if (!selected && 
/* 1028 */       this.petNode != null) {
/* 1029 */       this.petNode.setAngle(this.defaultAngle);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public ClientPet getClientPet() {
/* 1036 */     return this.clientPet;
/*      */   }
/*      */   
/*      */   public boolean isSwitchLocked() {
/* 1040 */     return this.switchLocked;
/*      */   }
/*      */   
/*      */   public void setSwitchLocked(boolean switchLocked) {
/* 1044 */     this.switchLocked = switchLocked;
/* 1045 */     this.skillContainer.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.drag" + (switchLocked ? ".no" : ""), new String[0]));
/* 1046 */     this.pet3DView.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.drag" + (switchLocked ? ".no" : ""), new String[0]));
/* 1047 */     this.bioContainer.setTooltipText(TcgGame.getLocalizedText("tooltips.pets.drag" + (switchLocked ? ".no" : ""), new String[0]));
/*      */     
/* 1049 */     this.bioButton.setVisible(!switchLocked);
/* 1050 */     this.viewButton.setVisible(!switchLocked);
/* 1051 */     this.skillButton.setVisible(!switchLocked);
/*      */     
/* 1053 */     if (switchLocked) {
/*      */       
/* 1055 */       this.info = PetSubView.VIEW;
/* 1056 */       updateSubViews();
/*      */       
/* 1058 */       this.backdropLabel.setStyleClass("backdrop-label");
/* 1059 */       if (this.style != null) {
/* 1060 */         this.backdropLabel.configureStyle(this.style);
/*      */       }
/* 1062 */       if (this.petNode != null) {
/* 1063 */         update3DView((PropNode)null);
/*      */       }
/*      */     } else {
/*      */       
/* 1067 */       this.backdropLabel.setStyleClass("backdrop-label");
/* 1068 */       this.nameLabel.setStyleClass("pet-title-name-label");
/* 1069 */       this.nameLabelSelected.setStyleClass("pet-title-name-label-selected");
/* 1070 */       if (this.petNode != null) {
/* 1071 */         update3DView(this.petNode);
/* 1072 */         if (Integer.valueOf(this.levelLabel.getText()).intValue() >= 40) {
/* 1073 */           this.backdropLabel.setStyleClass("backdrop-label-upgraded");
/* 1074 */           this.nameLabel.setStyleClass("pet-title-name-label-upgraded");
/* 1075 */           this.nameLabelSelected.setStyleClass("pet-title-name-label-selected-upgraded");
/*      */         } 
/*      */       } 
/* 1078 */       if (this.style != null) {
/* 1079 */         this.backdropLabel.configureStyle(this.style);
/* 1080 */         this.nameLabel.configureStyle(this.style);
/* 1081 */         this.nameLabelSelected.configureStyle(this.style);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void petChanged(ClientPet pet) {
/* 1087 */     if (pet != null) {
/*      */       
/* 1089 */       String currentPetClassId = "";
/* 1090 */       if (this.petNode != null) {
/* 1091 */         currentPetClassId = this.petNode.getProp().getName();
/*      */       }
/* 1093 */       if (!currentPetClassId.equals(pet.getClassId())) {
/* 1094 */         updatePetViewContainer(pet);
/* 1095 */         this.petViewSelectionModel.petChanged(this.viewId, pet);
/*      */       } 
/*      */     } else {
/*      */       
/* 1099 */       this.trialLabel.setVisible(false);
/*      */     } 
/* 1101 */     if (this.clientPet != null) {
/* 1102 */       this.clientPet.removePetEventsListener(this.petEventsListener);
/*      */     }
/* 1104 */     this.clientPet = pet;
/* 1105 */     this.petEventsListener = new PetViewListener();
/* 1106 */     if (this.clientPet != null)
/* 1107 */       this.clientPet.addPetEventsListener(this.petEventsListener); 
/* 1108 */     this.dragObject.setPet(pet);
/* 1109 */     resetView();
/*      */   }
/*      */   
/*      */   public void reinitialize(int totalTokens) {
/* 1113 */     this.hoverLabel.setVisible(false);
/* 1114 */     updateTotalTokens(totalTokens);
/* 1115 */     resetView();
/*      */   }
/*      */   
/*      */   public void refresh() {
/* 1119 */     if (this.petNode != null && 
/* 1120 */       this.alwaysOnDfxDescription != null && !this.alwaysOnDfxDescription.isEmpty()) {
/* 1121 */       this.petNode.killDfxAll();
/* 1122 */       DireEffect dfx = this.alwaysOnDfxDescription.createInstance(this.petNode, UsageParams.EMPTY_PARAMS);
/* 1123 */       this.petNode.addDfx(dfx);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void setVisible(boolean visible) {
/* 1130 */     if (visible && 
/* 1131 */       this.clientPet != null) {
/* 1132 */       updatePetStats(false, this.clientPet.getLevel());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private class PetViewListener
/*      */     implements PetEventsListener
/*      */   {
/*      */     private PetViewListener() {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void newSkillAcquired(ClientPet clientPet, ClientItem aSkill) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void skillLost(ClientPet clientPet, ClientItem aSkill) {}
/*      */ 
/*      */ 
/*      */     
/*      */     public void selectedSkillsChanged(ClientPet clientPet) {}
/*      */ 
/*      */     
/*      */     public void levelChanged(ClientPet clientPet, int lastLevel) {
/* 1157 */       PetView.this.levelLabel.setText(String.valueOf(clientPet.getLevel()));
/* 1158 */       if (clientPet.getLevel() >= 40 && PetView.this.petNode != null && PetView.this.petNode.getProp().getName().equals(clientPet.getBaseClassId())) {
/* 1159 */         PetView.this.updatePetViewContainer(clientPet);
/*      */       }
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*      */     public void expChanged(ClientPet clientPet) {}
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private void updatePetStats(boolean increase, int level) {
/* 1171 */     PetDescription desc = TcgGame.getRpgLoader().getPetManager().getPetDescription(this.clientPet.getClassId());
/* 1172 */     int playerLevel = MainGameState.getPlayerModel().getStatSupport().getLevel();
/*      */     
/* 1174 */     PetArchType maxArchType = new PetArchType("max-arch", 2.0D, 2.0D, 2.0D, 2.0D, 1.0D);
/* 1175 */     double[] maxStats = ClientStatCalc.getAbilityAmounts((ArchType)maxArchType, playerLevel, 1);
/*      */     
/* 1177 */     double[] petStats = ClientStatCalc.getAbilityAmounts((ArchType)desc.getArchType(), level, 1);
/* 1178 */     for (int i = 0; i < (GuiStatType.values()).length; i++) {
/* 1179 */       float ratio = (float)petStats[i] / (float)maxStats[i];
/* 1180 */       ratio = (ratio < 0.0F) ? 0.0F : ((ratio > 1.0F) ? 1.0F : ratio);
/* 1181 */       if (!increase) {
/* 1182 */         this.statBars[i].setProgress(ratio);
/*      */       }
/* 1184 */       this.statIncreaseBars[i].setProgress(ratio);
/* 1185 */       this.statBarGloss[i].setText(String.valueOf((int)petStats[i]));
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setViewButtonsEnabled(boolean enabled) {
/* 1191 */     this.bioButton.setEnabled(enabled);
/* 1192 */     this.skillButton.setEnabled(enabled);
/* 1193 */     this.viewButton.setEnabled(enabled);
/*      */   }
/*      */   
/*      */   public void updateTutorial(PetTutorialStep step) {
/* 1197 */     if (TcgGame.isPetTutorial()) {
/* 1198 */       this.cancelTrainButton.setEnabled(false);
/*      */       
/* 1200 */       if (TcgGame.getPetTutorialId() == 0) {
/* 1201 */         this.petTrainButton.setEnabled(step.equals(PetTutorialStep.STEP_6_TRAIN_PET_PRESS_UP));
/* 1202 */         this.addTokenButton.setHighlighted(step.equals(PetTutorialStep.STEP_6_TRAIN_PET_PRESS_UP));
/* 1203 */         this.confirmTrainButton.setHighlighted(step.equals(PetTutorialStep.STEP_7_TRAIN_PET_CONFIRM_CLOSE));
/*      */       } else {
/* 1205 */         this.petTrainButton.setEnabled((step.equals(PetTutorialStep.STEP_2_2_TRAIN_PET_PRESS_UP) || step.equals(PetTutorialStep.STEP_2_3_TRAIN_PET_PRESS_UP_AGAIN)));
/*      */         
/* 1207 */         this.addTokenButton.setHighlighted((step.equals(PetTutorialStep.STEP_2_2_TRAIN_PET_PRESS_UP) || step.equals(PetTutorialStep.STEP_2_3_TRAIN_PET_PRESS_UP_AGAIN)));
/*      */         
/* 1209 */         this.confirmTrainButton.setEnabled((!step.equals(PetTutorialStep.STEP_2_2_TRAIN_PET_PRESS_UP) && !step.equals(PetTutorialStep.STEP_2_3_TRAIN_PET_PRESS_UP_AGAIN)));
/* 1210 */         this.confirmTrainButton.setHighlighted(step.equals(PetTutorialStep.STEP_2_5_TRAIN_PET_CONFIRM_CLOSE));
/*      */       } 
/* 1212 */       MainGameState.getPetsWindow().updateTutorial(step);
/*      */     } else {
/* 1214 */       this.cancelTrainButton.setEnabled(true);
/* 1215 */       this.addTokenButton.setHighlighted(false);
/* 1216 */       this.confirmTrainButton.setHighlighted(false);
/* 1217 */       this.petTrainButton.setEnabled(true);
/* 1218 */       setViewButtonsEnabled(true);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void buttonCancelPressed(BPeelWindow window, BComponent component) {
/* 1224 */     TcgUI.getUISoundPlayer().play("ClickForward");
/* 1225 */     this.petTrainButton.setSelected(false);
/*      */   }
/*      */   
/*      */   public int getTotalTokens() {
/* 1229 */     return this.totalTokens;
/*      */   }
/*      */   
/*      */   public HighlightedToggleButton getPetTrainButton() {
/* 1233 */     return this.petTrainButton;
/*      */   }
/*      */   
/*      */   public HighlightedButton getAddTokenButton() {
/* 1237 */     return this.addTokenButton;
/*      */   }
/*      */   
/*      */   public HighlightedButton getConfirmTrainButton() {
/* 1241 */     return this.confirmTrainButton;
/*      */   }
/*      */   
/*      */   public void updateTotalTokens(int totalTokens) {
/* 1245 */     this.totalTokens = totalTokens;
/* 1246 */     this.currentNumTokens = totalTokens;
/* 1247 */     this.tokenTotalLabel.setText("" + totalTokens);
/* 1248 */     this.addTokenButton.setEnabled((this.currentLevel < MainGameState.getPlayerModel().getStatSupport().getLevel() && totalTokens > 0));
/*      */   }
/*      */ 
/*      */   
/*      */   private class DumbLabel
/*      */     extends BLabel
/*      */   {
/*      */     private BStyleSheet style;
/*      */     private boolean clickthrough = false;
/*      */     
/*      */     private DumbLabel(String text) {
/* 1259 */       super(text);
/*      */     }
/*      */     
/*      */     private DumbLabel(String text, boolean clickthrough) {
/* 1263 */       this(text);
/* 1264 */       this.clickthrough = clickthrough;
/*      */     }
/*      */ 
/*      */     
/*      */     public void setStyleClass(String styleClass) {
/* 1269 */       this._styleClass = styleClass;
/*      */     }
/*      */     
/*      */     public void updateStyle() {
/* 1273 */       configureStyle(this.style);
/*      */     }
/*      */ 
/*      */     
/*      */     public void configureStyle(BStyleSheet style) {
/* 1278 */       for (int i = 0; i < this._backgrounds.length; i++) {
/* 1279 */         if (this._backgrounds[i] != null) {
/* 1280 */           this._backgrounds[i].wasRemoved();
/*      */         }
/* 1282 */         this._backgrounds[i] = null;
/*      */       } 
/* 1284 */       this.style = style;
/* 1285 */       super.configureStyle(style);
/* 1286 */       for (BBackground _background : this._backgrounds) {
/* 1287 */         if (_background != null) {
/* 1288 */           _background.wasAdded();
/*      */         }
/*      */       } 
/*      */     }
/*      */     
/*      */     public BComponent getHitComponent(int mx, int my) {
/* 1294 */       return this.clickthrough ? null : super.getHitComponent(mx, my);
/*      */     }
/*      */   }
/*      */   
/*      */   private class TrainPetListener
/*      */     implements ActionListener {
/*      */     private TrainPetListener() {}
/*      */     
/*      */     public void actionPerformed(ActionEvent e) {
/* 1303 */       PetView.this.addToken();
/*      */     }
/*      */   }
/*      */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetView.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */