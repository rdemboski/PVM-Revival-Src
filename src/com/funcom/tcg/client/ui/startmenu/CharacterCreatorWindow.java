/*     */ package com.funcom.tcg.client.ui.startmenu;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*     */ import com.funcom.commons.dfx.DireEffectResourceLoader;
/*     */ import com.funcom.commons.dfx.EffectDescriptionFactory;
/*     */ import com.funcom.commons.dfx.EffectHandlerFactory;
/*     */ import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.commons.jme.bui.HighlightedToggleButton;
/*     */ import com.funcom.commons.jme.bui.IrregularButton;
/*     */ import com.funcom.gameengine.jme.modular.ModularDescription;
/*     */ import com.funcom.gameengine.model.ParticleSurface;
/*     */ import com.funcom.gameengine.model.props.Prop;
/*     */ import com.funcom.gameengine.model.token.GameTokenProcessor;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.gameengine.utils.SpatialUtils;
/*     */ import com.funcom.gameengine.view.AnimationMapper;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.gameengine.view.XmlEffectDescriptionFactory;
/*     */ import com.funcom.peeler.BananaPeel;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.PlayerDescription;
/*     */ import com.funcom.tcg.TcgConstants;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.dfx.ClientDFXResourceLoader;
/*     */ import com.funcom.tcg.client.dfx.GuiParticleHandlerFactory;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.ui.BPeelWindow;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.DefaultSelectorModel;
/*     */ import com.funcom.tcg.client.ui.SelectorModel;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.UpdatedGeomView;
/*     */ import com.funcom.tcg.client.view.modular.ClientDescribedModularNode;
/*     */ import com.funcom.tcg.net.StartingPet;
/*     */ import com.funcom.tcg.rpg.ClientDescriptionVisual;
/*     */ import com.funcom.tcg.rpg.CreatureVisualDescription;
/*     */ import com.jme.renderer.ColorRGBA;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.scene.Spatial;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BToggleButton;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.event.MouseListener;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import com.turborilla.jops.jme.ParticleProcessor;
/*     */ import com.turborilla.jops.jme.ParticleTextureLoader;
/*     */ import com.turborilla.jops.jme.ParticleTextureLoaderInstance;
/*     */ import com.turborilla.jops.jme.ResourceManagerParticleTextureLoader;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CharacterCreatorWindow
/*     */   extends BPeelWindow
/*     */ {
/*     */   protected static final float DEFAULT_ANGLE = 0.3926991F;
/*     */   private long last;
/*     */   private static int CENTER_OFFSET_X;
/*     */   private static int CENTER_OFFSET_Y;
/*     */   private static Rectangle SIZE_PLAY_BUTTON;
/*     */   private static Rectangle SIZE_BACK_BUTTON;
/*     */   private BToggleButton boyToggle;
/*     */   private BToggleButton girlToggle;
/*     */   private BButton[] leftButtons;
/*     */   private BButton[] rightButtons;
/*     */   private BLabel[] boyIconLabels;
/*     */   private BLabel[] girlIconLabels;
/*     */   private SelectorModel[] partModels;
/*     */   private int[] partIndices;
/*     */   private BLabel lookHeaderLabel;
/*     */   private BLabel petHeaderLabel;
/*     */   private BLabel petSeperatorLabel;
/*     */   private BButton randomButton;
/*     */   private BButton playButton;
/*     */   private HighlightedToggleButton[] petButtons;
/*     */   private BClickthroughLabel[] petNameLabels;
/*     */   private BContainer leftSidebar;
/*     */   private BLabel topSidebar;
/*     */   private BButton backButton;
/*     */   private UpdatedGeomView playerGeometryView;
/*     */   private ClientDescribedModularNode playerNode;
/*     */   private ParticleProcessor particleProcessor;
/*     */   private VisualRegistry visualRegistry;
/*     */   private StartMenuModel menuModel;
/*     */   private StartMenuListener menuListener;
/*     */   private StartMenuStartGameListener startGameListener;
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   public CharacterCreatorWindow(String windowName, BananaPeel bananaPeel, ResourceManager resourceManager, StartMenuModel menuModel, VisualRegistry visualRegistry, StartMenuListener menuListener, StartMenuStartGameListener startGameListener) {
/* 103 */     super(windowName, bananaPeel);
/* 104 */     this.menuModel = menuModel;
/* 105 */     this.menuListener = menuListener;
/* 106 */     this.startGameListener = startGameListener;
/* 107 */     this.resourceManager = resourceManager;
/* 108 */     this.visualRegistry = visualRegistry;
/*     */     
/* 110 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*     */     
/* 112 */     if (menuListener != null) {
/* 113 */       setSize(DisplaySystem.getDisplaySystem().getWidth(), DisplaySystem.getDisplaySystem().getHeight());
/* 114 */       setLocation(0, 0);
/*     */     } 
/*     */     
/* 117 */     int WIDTH = DisplaySystem.getDisplaySystem().getWidth();
/* 118 */     int HEIGHT = DisplaySystem.getDisplaySystem().getHeight();
/* 119 */     CENTER_OFFSET_X = (WIDTH - 1024) / 2;
/* 120 */     CENTER_OFFSET_Y = (HEIGHT - 768) / 2;
/* 121 */     CENTER_OFFSET_Y = (CENTER_OFFSET_Y < 0) ? 0 : CENTER_OFFSET_Y;
/*     */     
/* 123 */     int buttonSize = System.getProperty("tcg.locale").equals("en") ? 170 : 215;
/* 124 */     int OFFSET_X = WIDTH / 2 - 512;
/* 125 */     int OFFSET_Y = HEIGHT / 2 - 384;
/* 126 */     SIZE_PLAY_BUTTON = new Rectangle(OFFSET_X + 1024 - buttonSize, OFFSET_Y, buttonSize, 69);
/* 127 */     SIZE_BACK_BUTTON = new Rectangle(OFFSET_X, OFFSET_Y, buttonSize, 69);
/*     */     
/* 129 */     initComponents();
/* 130 */     initListeners();
/* 131 */     this.petNameLabels[PetType.WOLF.getIndex()].setColor(0, new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 137 */     super.dismiss();
/* 138 */     this.playerNode.dispose();
/* 139 */     this.playerNode = null;
/* 140 */     this.playerGeometryView.setGeometry(null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 146 */     BContainer container = new BContainer();
/* 147 */     BComponent placeholder = findComponent((BContainer)this, "main_container");
/* 148 */     placeholder.setSize(getWidth(), getHeight());
/*     */     
/* 150 */     overridePeelerComponent((BComponent)container, placeholder);
/*     */     
/* 152 */     this.leftButtons = new BButton[5];
/* 153 */     this.rightButtons = new BButton[5];
/* 154 */     this.boyIconLabels = new BLabel[5];
/* 155 */     this.girlIconLabels = new BLabel[5];
/*     */     
/* 157 */     this.petButtons = new HighlightedToggleButton[3];
/* 158 */     this.petNameLabels = new BClickthroughLabel[3];
/*     */     
/* 160 */     this.leftSidebar = new BContainer();
/* 161 */     placeholder = findComponent((BContainer)this, "container_player");
/* 162 */     offsetPlaceholder(placeholder);
/* 163 */     overridePeelerComponent((BComponent)this.leftSidebar, placeholder);
/*     */     
/* 165 */     this.topSidebar = new BLabel("");
/* 166 */     placeholder = findComponent((BContainer)this, "pet_bgd");
/* 167 */     offsetPlaceholder(placeholder);
/* 168 */     overridePeelerComponent((BComponent)this.topSidebar, placeholder);
/*     */ 
/*     */     
/* 171 */     this.boyToggle = new BToggleButton("");
/* 172 */     placeholder = findComponent((BContainer)this, "button_toggle_boy");
/* 173 */     overridePeelerComponent((BComponent)this.boyToggle, placeholder);
/*     */     
/* 175 */     this.girlToggle = new BToggleButton("");
/* 176 */     placeholder = findComponent((BContainer)this, "button_toggle_girl");
/* 177 */     overridePeelerComponent((BComponent)this.girlToggle, placeholder);
/*     */ 
/*     */     
/* 180 */     this.lookHeaderLabel = new BLabel(TcgGame.getLocalizedText("charactercreatewindow.step2", new String[0]));
/* 181 */     placeholder = findComponent((BContainer)this, "text_header_chooselook");
/* 182 */     overridePeelerComponent((BComponent)this.lookHeaderLabel, placeholder);
/*     */     
/* 184 */     this.partModels = (SelectorModel[])new DefaultSelectorModel[(CharacterPart.values()).length];
/* 185 */     this.partModels[CharacterPart.FACE.getIndex()] = (SelectorModel)new DefaultSelectorModel(this.menuModel.getFaceList());
/* 186 */     this.partModels[CharacterPart.HAIR_STYLE.getIndex()] = (SelectorModel)new DefaultSelectorModel(this.menuModel.getHairList());
/* 187 */     this.partModels[CharacterPart.HAIR_COLOR.getIndex()] = (SelectorModel)new DefaultSelectorModel(this.menuModel.getHairColorList());
/* 188 */     this.partModels[CharacterPart.CHEST.getIndex()] = (SelectorModel)new DefaultSelectorModel(this.menuModel.getTorsoList());
/* 189 */     this.partModels[CharacterPart.PANTS.getIndex()] = (SelectorModel)new DefaultSelectorModel(this.menuModel.getLegList());
/* 190 */     this.partIndices = new int[(CharacterPart.values()).length];
/*     */     int i;
/* 192 */     for (i = 0; i < (CharacterPart.values()).length; i++) {
/* 193 */       this.leftButtons[i] = new BButton("");
/* 194 */       placeholder = findComponent((BContainer)this, "button_left_" + CharacterPart.values()[i].getName());
/* 195 */       overridePeelerComponent((BComponent)this.leftButtons[i], placeholder);
/*     */       
/* 197 */       this.rightButtons[i] = new BButton("");
/* 198 */       placeholder = findComponent((BContainer)this, "button_right_" + CharacterPart.values()[i].getName());
/* 199 */       overridePeelerComponent((BComponent)this.rightButtons[i], placeholder);
/*     */       
/* 201 */       this.boyIconLabels[i] = new BLabel("");
/* 202 */       placeholder = findComponent((BContainer)this, "label_boy_" + CharacterPart.values()[i].getName());
/* 203 */       overridePeelerComponent((BComponent)this.boyIconLabels[i], placeholder);
/*     */       
/* 205 */       this.girlIconLabels[i] = new BLabel("");
/* 206 */       placeholder = findComponent((BContainer)this, "label_girl_" + CharacterPart.values()[i].getName());
/* 207 */       overridePeelerComponent((BComponent)this.girlIconLabels[i], placeholder);
/* 208 */       this.girlIconLabels[i].setVisible(false);
/*     */     } 
/*     */     
/* 211 */     this.randomButton = new BButton(TcgGame.getLocalizedText("startmenu.charactercreation.button.random", new String[0]).toUpperCase());
/* 212 */     placeholder = findComponent((BContainer)this, "button_random");
/* 213 */     overridePeelerComponent((BComponent)this.randomButton, placeholder);
/*     */ 
/*     */     
/* 216 */     this.petHeaderLabel = new BLabel(TcgGame.getLocalizedText("charactercreatewindow.step3", new String[0]));
/* 217 */     placeholder = findComponent((BContainer)this, "text_header_choosepet");
/* 218 */     offsetPlaceholder(placeholder);
/* 219 */     overridePeelerComponent((BComponent)this.petHeaderLabel, placeholder);
/*     */     
/* 221 */     this.petSeperatorLabel = new BLabel("");
/* 222 */     placeholder = findComponent((BContainer)this, "text_header_choosepet_separator");
/* 223 */     offsetPlaceholder(placeholder);
/* 224 */     overridePeelerComponent((BComponent)this.petSeperatorLabel, placeholder);
/*     */     
/* 226 */     for (i = 0; i < (PetType.values()).length; i++) {
/* 227 */       this.petButtons[i] = new HighlightedToggleButton();
/* 228 */       placeholder = findComponent((BContainer)this, "button_" + PetType.values()[i].getName());
/* 229 */       offsetPlaceholder(placeholder);
/* 230 */       overridePeelerComponent((BComponent)this.petButtons[i], placeholder);
/*     */       
/* 232 */       this.petNameLabels[i] = new BClickthroughLabel(TcgGame.getLocalizedText("charactercreatewindow.petname." + PetType.values()[i].getName(), new String[0]));
/*     */       
/* 234 */       placeholder = findComponent((BContainer)this, "text_name_" + PetType.values()[i].getName());
/* 235 */       offsetPlaceholder(placeholder);
/* 236 */       overridePeelerComponent((BComponent)this.petNameLabels[i], placeholder);
/*     */     } 
/*     */     
/* 239 */     String startingPetString = this.menuModel.getStartingPet().toString().toUpperCase();
/* 240 */     this.petButtons[PetType.valueOf(startingPetString).getIndex()].setSelected(true);
/*     */ 
/*     */     
/* 243 */     this.playerGeometryView = new UpdatedGeomView();
/* 244 */     this.playerGeometryView.setLookAtAngle(15.0F);
/* 245 */     this.playerGeometryView.setGeometry(createPlayerNode());
/* 246 */     this.playerGeometryView.update(1.0F);
/* 247 */     float zoom = 1.0F;
/* 248 */     this.playerGeometryView.setZoom(zoom);
/* 249 */     this.playerGeometryView.resetCameraPosition();
/* 250 */     this.playerGeometryView.addListener((ComponentListener)new UpdatedGeomView.CharacterRotationListener(this.playerGeometryView));
/* 251 */     this.playerGeometryView.addListener((ComponentListener)new UpdatedGeomView.CharacterZoomListener(this.playerGeometryView));
/*     */     
/* 253 */     placeholder = findComponent((BContainer)this, "placeholder_3dview");
/* 254 */     offsetPlaceholder(placeholder);
/* 255 */     overridePeelerComponent((BComponent)this.playerGeometryView, placeholder);
/*     */     
/* 257 */     BuiUtils.workaroundForParticleFacing();
/*     */ 
/*     */     
/* 260 */     this.playButton = (BButton)new IrregularButton(TcgGame.getLocalizedText("charactercreatewindow.play", new String[0]).toUpperCase());
/* 261 */     placeholder = findComponent((BContainer)this, "button_play");
/* 262 */     offsetPlaceholder(placeholder);
/* 263 */     overridePeelerComponent((BComponent)this.playButton, placeholder);
/* 264 */     this.playButton.getParent().remove((BComponent)this.playButton);
/* 265 */     add((BComponent)this.playButton, SIZE_PLAY_BUTTON);
/*     */     
/* 267 */     this.backButton = new BButton(TcgGame.getLocalizedText("mainmenuwindow.back", new String[0]).toUpperCase());
/* 268 */     placeholder = findComponent((BContainer)this, "button_back");
/* 269 */     offsetPlaceholder(placeholder);
/* 270 */     overridePeelerComponent((BComponent)this.backButton, placeholder);
/* 271 */     this.backButton.getParent().remove((BComponent)this.backButton);
/* 272 */     add((BComponent)this.backButton, SIZE_BACK_BUTTON);
/*     */   }
/*     */   private void initListeners() {
/*     */     int i;
/* 276 */     for (i = 0; i < (CharacterPart.values()).length; i++) {
/* 277 */       this.leftButtons[i].addListener((ComponentListener)new ChangePartListener(CharacterPart.values()[i], true));
/* 278 */       this.rightButtons[i].addListener((ComponentListener)new ChangePartListener(CharacterPart.values()[i], false));
/*     */     } 
/*     */     
/* 281 */     for (i = 0; i < (PetType.values()).length; i++) {
/* 282 */       this.petButtons[i].addListener((ComponentListener)new PetHoverListener(i, false));
/* 283 */       this.petButtons[i].addListener((ComponentListener)new PetActionListener(i, false));
/*     */     } 
/*     */     
/* 286 */     this.randomButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 289 */             TcgUI.getUISoundPlayer().play("ClickForward");
/*     */             
/* 291 */             CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.FACE.getIndex()].setSelectionIndex((int)(CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.FACE.getIndex()].getSize() * Math.random()));
/*     */             
/* 293 */             CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.HAIR_STYLE.getIndex()].setSelectionIndex((int)(CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.HAIR_STYLE.getIndex()].getSize() * Math.random()));
/*     */             
/* 295 */             CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.HAIR_COLOR.getIndex()].setSelectionIndex((int)(CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.HAIR_COLOR.getIndex()].getSize() * Math.random()));
/*     */             
/* 297 */             CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.CHEST.getIndex()].setSelectionIndex((int)(CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.CHEST.getIndex()].getSize() * Math.random()));
/*     */             
/* 299 */             CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.PANTS.getIndex()].setSelectionIndex((int)(CharacterCreatorWindow.this.partModels[CharacterCreatorWindow.CharacterPart.PANTS.getIndex()].getSize() * Math.random()));
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 304 */     this.playButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 307 */             TcgUI.getUISoundPlayer().play("ClickForward");
/*     */             
/* 309 */             if (CharacterCreatorWindow.this.girlToggle.isSelected()) {
/* 310 */               HttpMetrics.postEvent(HttpMetrics.Event.CHARACTER_GIRL);
/* 311 */             } else if (CharacterCreatorWindow.this.boyToggle.isSelected()) {
/* 312 */               HttpMetrics.postEvent(HttpMetrics.Event.CHARACTER_BOY);
/*     */             } 
/* 314 */             CharacterCreatorWindow.this.startGameListener.loginByCreation();
/*     */           }
/*     */         });
/*     */     
/* 318 */     this.backButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 321 */             CharacterCreatorWindow.this.menuListener.characterCreationBack();
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 326 */     ActionListener genderListener = new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent actionEvent) {
/* 329 */           TcgUI.getUISoundPlayer().play("ClickForward");
/* 330 */           BToggleButton source = (BToggleButton)actionEvent.getSource();
/* 331 */           CharacterCreatorWindow.this.boyToggle.setSelected(CharacterCreatorWindow.this.boyToggle.equals(source));
/* 332 */           CharacterCreatorWindow.this.girlToggle.setSelected(CharacterCreatorWindow.this.girlToggle.equals(source));
/*     */           
/* 334 */           for (int i = 0; i < (CharacterCreatorWindow.CharacterPart.values()).length; i++) {
/* 335 */             CharacterCreatorWindow.this.boyIconLabels[i].setVisible(CharacterCreatorWindow.this.boyToggle.isSelected());
/* 336 */             CharacterCreatorWindow.this.girlIconLabels[i].setVisible(CharacterCreatorWindow.this.girlToggle.isSelected());
/*     */           } 
/*     */           
/* 339 */           CharacterCreatorWindow.this.menuModel.setGender(CharacterCreatorWindow.this.boyToggle.isSelected() ? PlayerDescription.Gender.MALE : PlayerDescription.Gender.FEMALE);
/*     */ 
/*     */           
/* 342 */           CharacterCreatorWindow.this.refreshModel();
/*     */         }
/*     */       };
/* 345 */     this.boyToggle.addListener((ComponentListener)genderListener);
/* 346 */     this.girlToggle.addListener((ComponentListener)genderListener);
/* 347 */     this.boyToggle.setSelected(this.menuModel.getGender().equals(PlayerDescription.Gender.MALE));
/* 348 */     this.girlToggle.setSelected(this.menuModel.getGender().equals(PlayerDescription.Gender.FEMALE));
/*     */ 
/*     */     
/* 351 */     this.partModels[CharacterPart.FACE.getIndex()].addChangeListener(new SelectorModel.ChangeListener()
/*     */         {
/*     */           public void changeEvent(SelectorModel model) {
/* 354 */             CharacterCreatorWindow.this.modelChange(CharacterCreatorWindow.CharacterPart.FACE);
/*     */           }
/*     */         });
/* 357 */     this.partModels[CharacterPart.HAIR_STYLE.getIndex()].addChangeListener(new SelectorModel.ChangeListener()
/*     */         {
/*     */           public void changeEvent(SelectorModel model) {
/* 360 */             CharacterCreatorWindow.this.modelChange(CharacterCreatorWindow.CharacterPart.HAIR_STYLE);
/*     */           }
/*     */         });
/* 363 */     this.partModels[CharacterPart.HAIR_COLOR.getIndex()].addChangeListener(new SelectorModel.ChangeListener()
/*     */         {
/*     */           public void changeEvent(SelectorModel model) {
/* 366 */             CharacterCreatorWindow.this.modelChange(CharacterCreatorWindow.CharacterPart.HAIR_COLOR);
/*     */           }
/*     */         });
/*     */     
/* 370 */     this.partModels[CharacterPart.CHEST.getIndex()].addChangeListener(new SelectorModel.ChangeListener()
/*     */         {
/*     */           public void changeEvent(SelectorModel model) {
/* 373 */             CharacterCreatorWindow.this.modelChange(CharacterCreatorWindow.CharacterPart.CHEST);
/*     */           }
/*     */         });
/* 376 */     this.partModels[CharacterPart.PANTS.getIndex()].addChangeListener(new SelectorModel.ChangeListener()
/*     */         {
/*     */           public void changeEvent(SelectorModel model) {
/* 379 */             CharacterCreatorWindow.this.modelChange(CharacterCreatorWindow.CharacterPart.PANTS);
/*     */           }
/*     */         });
/* 382 */     refreshPetWithDfx();
/*     */   }
/*     */   
/*     */   private void modelChange(CharacterPart part) {
/* 386 */     TcgUI.getUISoundPlayer().play("ClickForward");
/* 387 */     switch (part) {
/*     */       case FACE:
/* 389 */         this.menuModel.setFace((ClientDescriptionVisual)this.partModels[CharacterPart.FACE.getIndex()].getCurrent());
/*     */         break;
/*     */       case HAIR_STYLE:
/* 392 */         this.menuModel.setHair((ClientDescriptionVisual)this.partModels[CharacterPart.HAIR_STYLE.getIndex()].getCurrent());
/*     */         break;
/*     */       case HAIR_COLOR:
/* 395 */         this.menuModel.setHairColor((ClientDescriptionVisual)this.partModels[CharacterPart.HAIR_COLOR.getIndex()].getCurrent());
/*     */         break;
/*     */       case CHEST:
/* 398 */         this.menuModel.setTorso((ItemDescription)this.partModels[CharacterPart.CHEST.getIndex()].getCurrent());
/*     */         break;
/*     */       case PANTS:
/* 401 */         this.menuModel.setLegs((ItemDescription)this.partModels[CharacterPart.PANTS.getIndex()].getCurrent());
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 406 */     refreshModel();
/*     */   }
/*     */   
/*     */   private void refreshPetWithDfx() {
/* 410 */     this.playerNode.activePetChanged();
/* 411 */     startDFX(this.playerGeometryView.getGeometry());
/*     */   }
/*     */   
/*     */   private void refreshModel() {
/* 415 */     this.playerNode.reloadAll();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 421 */     long now = System.nanoTime();
/* 422 */     if (this.last != 0L) {
/* 423 */       float secDelta = (float)((now - this.last) / TimeUnit.SECONDS.toNanos(1L));
/* 424 */       GameTokenProcessor.instance().process(secDelta);
/* 425 */       this.particleProcessor.process(secDelta);
/*     */     } 
/* 427 */     this.last = now;
/*     */     
/* 429 */     super.render(renderer);
/*     */   }
/*     */   
/*     */   private PropNode createPlayerNode() {
/* 433 */     this.particleProcessor = new ParticleProcessor();
/* 434 */     this.particleProcessor.setCalculationsPerSecond(31);
/*     */     
/* 436 */     ParticleTextureLoaderInstance.setLoader((ParticleTextureLoader)new ResourceManagerParticleTextureLoader(this.resourceManager));
/*     */     
/* 438 */     DireEffectDescriptionFactory direEffectDescriptionFactory = new DireEffectDescriptionFactory((DireEffectResourceLoader)new ClientDFXResourceLoader(this.resourceManager, this.particleProcessor));
/*     */ 
/*     */     
/* 441 */     direEffectDescriptionFactory.setDescriptionFactory((EffectDescriptionFactory)new XmlEffectDescriptionFactory(direEffectDescriptionFactory, (EffectHandlerFactory)new GuiParticleHandlerFactory()));
/*     */     
/* 443 */     Prop prop = new Prop(this.menuModel.getCharactersName());
/* 444 */     PropNode propNode = new PropNode(prop, 3, "", direEffectDescriptionFactory);
/*     */ 
/*     */     
/* 447 */     ModularDescription modularDescription = this.menuModel.getModularDescription();
/*     */     
/* 449 */     this.playerNode = new ClientDescribedModularNode(modularDescription, (AnimationMapper)propNode, TcgConstants.MODEL_ROTATION, 0.0025F, this.visualRegistry, this.resourceManager);
/*     */ 
/*     */     
/* 452 */     this.playerNode.reloadAll();
/* 453 */     propNode.setScale(1.0F);
/* 454 */     propNode.setLocalTranslation(13.0F, 0.0F, 0.0F);
/* 455 */     propNode.setAngle(0.3926991F);
/* 456 */     propNode.attachRepresentation((Spatial)this.playerNode);
/* 457 */     System.out.println(this.playerNode.getWorldBound());
/* 458 */     SpatialUtils.addShadow(propNode, this.resourceManager);
/*     */     
/* 460 */     propNode.setWorldOriginAligned(false);
/*     */     
/* 462 */     this.playerNode.updateRenderState();
/* 463 */     propNode.initializeEffects((ParticleSurface)this.playerGeometryView);
/*     */     
/* 465 */     propNode.playAnimation("character_creation_idle", true);
/*     */     
/* 467 */     return propNode;
/*     */   }
/*     */   
/*     */   private void startDFX(PropNode propNode) {
/* 471 */     propNode.killDfxAll();
/*     */     
/* 473 */     CreatureVisualDescription visuals = this.menuModel.getPetVisuals();
/* 474 */     if (!visuals.getAlwaysOnDfx().isEmpty()) {
/*     */       try {
/* 476 */         DireEffectDescription alwaysOnDfxDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(visuals.getAlwaysOnDfx(), false);
/*     */         
/* 478 */         if (!alwaysOnDfxDescription.isEmpty()) {
/* 479 */           DireEffect dfx = alwaysOnDfxDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/* 480 */           propNode.addDfx(dfx);
/*     */         } 
/* 482 */       } catch (NoSuchDFXException e) {}
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void toggleCrossHover(int petSlot, boolean isBgd, boolean enableHover) {
/* 489 */     if (isBgd) {
/* 490 */       this.petButtons[petSlot].setFakeHover(enableHover);
/*     */     }
/*     */   }
/*     */   
/*     */   private void offsetPlaceholder(BComponent placeholder) {
/* 495 */     if (placeholder != null) {
/* 496 */       placeholder.setLocation(placeholder.getX() + CENTER_OFFSET_X, placeholder.getY() + CENTER_OFFSET_Y);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 501 */     super.setVisible(visible);
/* 502 */     for (int i = 0; i < (CharacterPart.values()).length; i++) {
/* 503 */       this.boyIconLabels[i].setVisible(this.boyToggle.isSelected());
/* 504 */       this.girlIconLabels[i].setVisible(this.girlToggle.isSelected());
/*     */     } 
/* 506 */     this.petButtons[0].setSelected(false);
/* 507 */     this.petButtons[1].setSelected(false);
/* 508 */     this.petButtons[2].setSelected(false);
/*     */     
/* 510 */     String startingPetString = this.menuModel.getStartingPet().toString().toUpperCase();
/* 511 */     this.petButtons[PetType.valueOf(startingPetString).getIndex()].setSelected(true);
/*     */     
/* 513 */     this.petNameLabels[PetType.valueOf(startingPetString).getIndex()].setColor(0, new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F));
/* 514 */     refreshPetWithDfx();
/*     */   }
/*     */   
/*     */   enum PetType {
/* 518 */     WOLF("wolf", 0),
/* 519 */     CAT("cat", 1),
/* 520 */     BEAR("bear", 2);
/*     */     
/*     */     String name;
/*     */     int index;
/*     */     
/*     */     PetType(String name, int index) {
/* 526 */       this.name = name;
/* 527 */       this.index = index;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 531 */       return this.name;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 535 */       return this.index;
/*     */     }
/*     */   }
/*     */   
/*     */   enum CharacterPart {
/* 540 */     FACE("face", 0),
/* 541 */     HAIR_STYLE("hairstyle", 1),
/* 542 */     HAIR_COLOR("haircolor", 2),
/* 543 */     CHEST("chest", 3),
/* 544 */     PANTS("pants", 4);
/*     */     
/*     */     String name;
/*     */     int index;
/*     */     
/*     */     CharacterPart(String name, int index) {
/* 550 */       this.name = name;
/* 551 */       this.index = index;
/*     */     }
/*     */     
/*     */     public String getName() {
/* 555 */       return this.name;
/*     */     }
/*     */     
/*     */     public int getIndex() {
/* 559 */       return this.index;
/*     */     }
/*     */   }
/*     */   
/*     */   class ChangePartListener implements ActionListener {
/*     */     CharacterCreatorWindow.CharacterPart part;
/*     */     boolean isLeft;
/*     */     
/*     */     ChangePartListener(CharacterCreatorWindow.CharacterPart part, boolean isLeft) {
/* 568 */       this.part = part;
/* 569 */       this.isLeft = isLeft;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent event) {
/* 575 */       if (this.isLeft) {
/* 576 */         if (CharacterCreatorWindow.this.partModels[this.part.getIndex()].hasPrevious()) {
/* 577 */           CharacterCreatorWindow.this.partModels[this.part.getIndex()].previous();
/*     */         } else {
/* 579 */           CharacterCreatorWindow.this.partModels[this.part.getIndex()].setSelectionIndex(CharacterCreatorWindow.this.partModels[this.part.getIndex()].getSize() - 1);
/*     */         }
/*     */       
/* 582 */       } else if (CharacterCreatorWindow.this.partModels[this.part.getIndex()].hasNext()) {
/* 583 */         CharacterCreatorWindow.this.partModels[this.part.getIndex()].next();
/*     */       } else {
/* 585 */         CharacterCreatorWindow.this.partModels[this.part.getIndex()].setSelectionIndex(0);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class PetActionListener
/*     */     implements ActionListener {
/*     */     int petSlot;
/*     */     boolean isBgd;
/*     */     
/*     */     PetActionListener(int petSlot, boolean isBgd) {
/* 596 */       this.petSlot = petSlot;
/* 597 */       this.isBgd = isBgd;
/*     */     }
/*     */ 
/*     */     
/*     */     public void actionPerformed(ActionEvent event) {
/* 602 */       TcgUI.getUISoundPlayer().play("ClickForward");
/* 603 */       StartingPet oldPet = CharacterCreatorWindow.this.menuModel.getStartingPet();
/* 604 */       StartingPet newPet = null;
/*     */       
/* 606 */       HighlightedToggleButton source = (HighlightedToggleButton)event.getSource();
/* 607 */       CharacterCreatorWindow.this.petButtons[0].setSelected(CharacterCreatorWindow.this.petButtons[0].equals(source));
/* 608 */       CharacterCreatorWindow.this.petButtons[1].setSelected(CharacterCreatorWindow.this.petButtons[1].equals(source));
/* 609 */       CharacterCreatorWindow.this.petButtons[2].setSelected(CharacterCreatorWindow.this.petButtons[2].equals(source));
/*     */       
/* 611 */       ColorRGBA selectedColor = new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F);
/* 612 */       ColorRGBA defaultColor = new ColorRGBA(0.0F, 0.0F, 0.0F, 1.0F);
/* 613 */       CharacterCreatorWindow.this.petNameLabels[0].setColor(0, CharacterCreatorWindow.this.petButtons[0].isSelected() ? selectedColor : defaultColor);
/* 614 */       CharacterCreatorWindow.this.petNameLabels[1].setColor(0, CharacterCreatorWindow.this.petButtons[1].isSelected() ? selectedColor : defaultColor);
/* 615 */       CharacterCreatorWindow.this.petNameLabels[2].setColor(0, CharacterCreatorWindow.this.petButtons[2].isSelected() ? selectedColor : defaultColor);
/*     */       
/* 617 */       if (CharacterCreatorWindow.this.petButtons[CharacterCreatorWindow.PetType.WOLF.getIndex()].isSelected()) {
/* 618 */         newPet = StartingPet.WOLF;
/* 619 */       } else if (CharacterCreatorWindow.this.petButtons[CharacterCreatorWindow.PetType.BEAR.getIndex()].isSelected()) {
/* 620 */         newPet = StartingPet.BEAR;
/* 621 */       } else if (CharacterCreatorWindow.this.petButtons[CharacterCreatorWindow.PetType.CAT.getIndex()].isSelected()) {
/* 622 */         newPet = StartingPet.CAT;
/*     */       } 
/*     */       
/* 625 */       if (newPet != oldPet) {
/* 626 */         CharacterCreatorWindow.this.menuModel.setStartingPet(newPet);
/* 627 */         CharacterCreatorWindow.this.refreshPetWithDfx();
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   class PetHoverListener implements MouseListener {
/*     */     int petSlot;
/*     */     boolean isBgd;
/*     */     
/*     */     PetHoverListener(int petSlot, boolean isBgd) {
/* 637 */       this.petSlot = petSlot;
/* 638 */       this.isBgd = isBgd;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void mousePressed(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(MouseEvent event) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseEntered(MouseEvent event) {
/* 653 */       ColorRGBA hoverColor = new ColorRGBA(0.8F, 0.8F, 0.8F, 1.0F);
/* 654 */       ColorRGBA selectedColor = new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F);
/* 655 */       CharacterCreatorWindow.this.petNameLabels[this.petSlot].setColor(0, CharacterCreatorWindow.this.petButtons[this.petSlot].isSelected() ? selectedColor : hoverColor);
/* 656 */       CharacterCreatorWindow.this.toggleCrossHover(this.petSlot, this.isBgd, true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void mouseExited(MouseEvent event) {
/* 661 */       ColorRGBA defaultColor = new ColorRGBA(0.0F, 0.0F, 0.0F, 1.0F);
/* 662 */       ColorRGBA selectedColor = new ColorRGBA(1.0F, 1.0F, 1.0F, 1.0F);
/* 663 */       CharacterCreatorWindow.this.petNameLabels[this.petSlot].setColor(0, CharacterCreatorWindow.this.petButtons[this.petSlot].isSelected() ? selectedColor : defaultColor);
/* 664 */       CharacterCreatorWindow.this.toggleCrossHover(this.petSlot, this.isBgd, false);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\CharacterCreatorWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */