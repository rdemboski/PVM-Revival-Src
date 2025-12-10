/*     */ package com.funcom.tcg.client.ui.duel;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.funcom.gameengine.Updated;
/*     */ import com.funcom.gameengine.resourcemanager.NoLocatorException;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.hud2.AbstractHudModel;
/*     */ import com.funcom.tcg.client.ui.hud2.HudModel;
/*     */ import com.funcom.tcg.net.message.DuelCancelMessage;
/*     */ import com.jme.input.MouseInput;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BActiveProgressBar;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.BToggleButton;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class DuelHealthBarWindow
/*     */   extends BWindow
/*     */   implements PartiallyNotInteractive, Updated {
/*  43 */   private final String EMPTY_PET = "gui/icons/pets/empty_slot.png";
/*     */   private BActiveProgressBar[] playerHealthProgressBars;
/*     */   private BActiveProgressBar[] playerManaProgressBars;
/*     */   private BClickthroughLabel[] petImages;
/*     */   private BClickthroughLabel[] playerNames;
/*     */   private BContainer bottomWindow;
/*     */   private BContainer topWindow;
/*     */   private BClickthroughLabel outcomeLabel;
/*     */   private BClickthroughLabel holderLabel;
/*     */   private BButton cancelDuelButton;
/*     */   private BToggleButton[] petSelections;
/*     */   private BClickthroughLabel[] petCards;
/*     */   private int HEIGHT;
/*     */   private int WIDTH;
/*  57 */   private int opponentId = 0;
/*     */   
/*  59 */   private float timeComplete = 5.0F;
/*     */   
/*     */   private boolean fightComplete = false;
/*  62 */   private String[] petIcons = new String[] { "", "" };
/*     */   private BClickthroughLabel[] petBgds;
/*     */   private BClickthroughLabel petHolder;
/*     */   
/*     */   public DuelHealthBarWindow() {
/*  67 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*     */     
/*  69 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(TcgGame.getResourceManager());
/*  70 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*  71 */     setSize(DisplaySystem.getDisplaySystem().getWidth(), DisplaySystem.getDisplaySystem().getHeight());
/*  72 */     setLayer(MainGameState.getMainHud().getLayer() + 1);
/*     */ 
/*     */     
/*  75 */     this.WIDTH = DisplaySystem.getDisplaySystem().getWidth();
/*  76 */     this.HEIGHT = DisplaySystem.getDisplaySystem().getHeight();
/*     */     
/*  78 */     initComponents();
/*  79 */     initListeners();
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*  83 */     this.topWindow = new BContainer();
/*  84 */     this.topWindow.setStyleClass("top_window");
/*     */     
/*  86 */     this.bottomWindow = new BContainer();
/*  87 */     this.bottomWindow.setStyleClass("bottom_window");
/*     */ 
/*     */     
/*  90 */     this.cancelDuelButton = new BButton(TcgGame.getLocalizedText("duel.cancel.giveup", new String[0]));
/*  91 */     this.cancelDuelButton.setStyleClass("giveup_button");
/*  92 */     this.cancelDuelButton.setTooltipText(TcgGame.getLocalizedText("duel.cancel.tooltip", new String[0]));
/*     */     
/*  94 */     int blackBarHeight = 120;
/*  95 */     int topY = this.HEIGHT - blackBarHeight;
/*  96 */     int holderY = topY + blackBarHeight / 2 - 36 + 10;
/*     */     
/*  98 */     this.playerHealthProgressBars = new BActiveProgressBar[2];
/*  99 */     this.playerManaProgressBars = new BActiveProgressBar[2];
/* 100 */     BClickthroughLabel[] playerHealthProgressBarBgds = new BClickthroughLabel[2];
/* 101 */     BClickthroughLabel[] playerManaProgressBarBgds = new BClickthroughLabel[2];
/* 102 */     this.petImages = new BClickthroughLabel[2];
/* 103 */     this.playerNames = new BClickthroughLabel[2];
/* 104 */     add((BComponent)this.topWindow, new Rectangle(0, topY, this.WIDTH, blackBarHeight));
/* 105 */     add((BComponent)this.bottomWindow, new Rectangle(0, 0, this.WIDTH, blackBarHeight));
/*     */ 
/*     */     
/* 108 */     this.petBgds = new BClickthroughLabel[2]; int i;
/* 109 */     for (i = 0; i < this.playerHealthProgressBars.length; i++) {
/* 110 */       playerHealthProgressBarBgds[i] = new BClickthroughLabel("");
/* 111 */       playerHealthProgressBarBgds[i].setStyleClass("progress.health.bgd");
/*     */       
/* 113 */       this.playerHealthProgressBars[i] = new BActiveProgressBar((i == 0) ? BProgressBar.Direction.WEST : BProgressBar.Direction.PROGRESSDIR_EAST, 100L, 0.2F);
/* 114 */       this.playerHealthProgressBars[i].setStyleClass("progress.health.player" + (i + 1));
/* 115 */       this.playerHealthProgressBars[i].setProgress(MainGameState.getHudModel().getCurrentHealthFraction());
/*     */       
/* 117 */       playerManaProgressBarBgds[i] = new BClickthroughLabel("");
/* 118 */       playerManaProgressBarBgds[i].setStyleClass("progress.mana.bgd.player" + (i + 1));
/*     */       
/* 120 */       this.playerManaProgressBars[i] = new BActiveProgressBar((i == 0) ? BProgressBar.Direction.WEST : BProgressBar.Direction.PROGRESSDIR_EAST, 100L, 0.2F);
/* 121 */       this.playerManaProgressBars[i].setStyleClass("progress.mana.player" + (i + 1));
/* 122 */       this.playerManaProgressBars[i].setProgress(MainGameState.getHudModel().getCurrentManaFraction());
/*     */       
/* 124 */       this.petImages[i] = new BClickthroughLabel("");
/* 125 */       this.petBgds[i] = new BClickthroughLabel("");
/* 126 */       this.petBgds[i].setStyleClass("pet_bgd_player" + (i + 1));
/*     */       
/* 128 */       this.playerNames[i] = new BClickthroughLabel("");
/* 129 */       this.playerNames[i].setStyleClass("player" + (i + 1) + ".name");
/*     */       
/* 131 */       int imageHeight = 48;
/* 132 */       int progressWidth = 350;
/*     */       
/* 134 */       add((BComponent)playerHealthProgressBarBgds[i], new Rectangle((i == 0) ? (this.WIDTH / 2 - 43 - progressWidth) : (this.WIDTH / 2 + 44), holderY + 36, progressWidth, 18));
/* 135 */       add((BComponent)this.playerHealthProgressBars[i], new Rectangle((i == 0) ? (this.WIDTH / 2 - 44 - progressWidth) : (this.WIDTH / 2 + 44), holderY + 36, progressWidth, 18));
/* 136 */       add((BComponent)playerManaProgressBarBgds[i], new Rectangle((i == 0) ? (this.WIDTH / 2 - 75 - 314) : (this.WIDTH / 2 + 75), holderY + 9, 314, 18));
/* 137 */       add((BComponent)this.playerManaProgressBars[i], new Rectangle((i == 0) ? (this.WIDTH / 2 - 75 - 314) : (this.WIDTH / 2 + 74), holderY + 9, 314, 18));
/*     */       
/* 139 */       add((BComponent)this.playerNames[i], new Rectangle((i == 0) ? (this.WIDTH / 2 - 44 - progressWidth - 72) : (this.WIDTH / 2 + 44 + 72), holderY - 20, progressWidth, 20));
/* 140 */       add((BComponent)this.petImages[i], new Rectangle((i == 0) ? (this.WIDTH / 2 - 44 - progressWidth - 65) : (this.WIDTH / 2 + 44 + progressWidth + 15), holderY + 9, 48, 48));
/* 141 */       add((BComponent)this.petBgds[i], new Rectangle((i == 0) ? (this.WIDTH / 2 - 44 - progressWidth - 72) : (this.WIDTH / 2 + 44 + progressWidth - 12), holderY, 82, 64));
/*     */     } 
/*     */     
/* 144 */     this.petSelections = new BToggleButton[3];
/* 145 */     this.petCards = new BClickthroughLabel[3];
/* 146 */     for (i = 0; i < this.petSelections.length; i++) {
/* 147 */       this.petSelections[i] = new BToggleButton("");
/* 148 */       this.petSelections[i].setStyleClass("button_pet_select");
/* 149 */       this.petCards[i] = new BClickthroughLabel("");
/* 150 */       BImage iconImage = null;
/*     */       
/* 152 */       String iconPath = "gui/icons/pets/empty_slot.png";
/* 153 */       if (MainGameState.getPlayerModel().getPetSlot(i) != null && MainGameState.getPlayerModel().getPetSlot(i).getPet() != null) {
/* 154 */         iconPath = MainGameState.getPlayerModel().getPetSlot(i).getPet().getIcon();
/* 155 */         this.petSelections[i].setSelected(MainGameState.getPlayerModel().getPetSlot(i).getPet().getName().equals(MainGameState.getPlayerModel().getActivePet().getName()));
/*     */       } 
/*     */ 
/*     */       
/*     */       try {
/* 160 */         iconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, iconPath);
/* 161 */       } catch (NoLocatorException e) {
/* 162 */         e.printStackTrace();
/* 163 */         throw new RuntimeException("Missing image for destination portal: " + this.petSelections[i]);
/*     */       } 
/*     */       
/* 166 */       ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, iconImage);
/*     */ 
/*     */       
/* 169 */       this.petSelections[i].setEnabled(!iconPath.equals("gui/icons/pets/empty_slot.png"));
/*     */       
/* 171 */       this.petCards[i].setBackground(0, (BBackground)imageBackground);
/* 172 */       this.petCards[i].setBackground(1, (BBackground)imageBackground);
/* 173 */       add((BComponent)this.petCards[i], new Rectangle(this.WIDTH / 2 - 80 + i * 57, 7, 48, 48));
/* 174 */       add((BComponent)this.petSelections[i], new Rectangle(this.WIDTH / 2 - 80 + i * 57, 7, 48, 48));
/* 175 */       final int finalIndex = i;
/* 176 */       this.petSelections[i].addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event) {
/* 179 */               for (BToggleButton button : DuelHealthBarWindow.this.petSelections) {
/* 180 */                 button.setSelected(false);
/*     */               }
/*     */               
/* 183 */               DuelHealthBarWindow.this.petSelections[finalIndex].setSelected(true);
/* 184 */               MainGameState.getHudModel().petButtonAction(finalIndex);
/*     */             }
/*     */           });
/*     */     } 
/*     */     
/* 189 */     this.petHolder = new BClickthroughLabel("");
/* 190 */     this.petHolder.setStyleClass("pet_holder");
/* 191 */     add((BComponent)this.petHolder, new Rectangle(this.WIDTH / 2 - 142, 0, 285, 66));
/*     */     
/* 193 */     this.outcomeLabel = new BClickthroughLabel("");
/* 194 */     this.outcomeLabel.setStyleClass("label_countdown");
/* 195 */     add((BComponent)this.outcomeLabel, new Rectangle(0, this.HEIGHT / 2 - 75, this.WIDTH, 150));
/*     */     
/* 197 */     int backButtonSize = System.getProperty("tcg.locale").equals("fr") ? 160 : 120;
/* 198 */     add((BComponent)this.cancelDuelButton, new Rectangle(5, 5, backButtonSize, 35));
/*     */     
/* 200 */     this.holderLabel = new BClickthroughLabel("");
/* 201 */     this.holderLabel.setStyleClass("progress.holder");
/* 202 */     add((BComponent)this.holderLabel, new Rectangle(this.WIDTH / 2 - 404, holderY, 808, 73));
/*     */ 
/*     */ 
/*     */     
/* 206 */     this.playerNames[0].setText(MainGameState.getPlayerModel().getName());
/* 207 */     this.playerNames[1].setText("");
/*     */     
/* 209 */     float currentHealth = MainGameState.getPlayerModel().getStatAsFloat(Short.valueOf((short)12));
/* 210 */     float maxHealth = MainGameState.getPlayerModel().getStatAsFloat(Short.valueOf((short)11));
/* 211 */     float currentMana = MainGameState.getPlayerModel().getStatAsFloat(Short.valueOf((short)12));
/* 212 */     float maxMana = MainGameState.getPlayerModel().getStatAsFloat(Short.valueOf((short)11));
/*     */     
/* 214 */     float progress = currentHealth / maxHealth;
/* 215 */     progress = (progress < 0.0F) ? 0.0F : ((progress > 1.0F) ? 1.0F : progress);
/* 216 */     this.playerHealthProgressBars[0].setProgress(progress);
/* 217 */     this.playerHealthProgressBars[1].setProgress(1.0F);
/*     */     
/* 219 */     progress = currentMana / maxMana;
/* 220 */     progress = (progress < 0.0F) ? 0.0F : ((progress > 1.0F) ? 1.0F : progress);
/* 221 */     this.playerManaProgressBars[0].setProgress(progress);
/* 222 */     this.playerManaProgressBars[1].setProgress(1.0F);
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 226 */     this.cancelDuelButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*     */             try {
/* 230 */               NetworkHandler.instance().getIOHandler().send((Message)new DuelCancelMessage(MainGameState.getPlayerModel().getId()));
/* 231 */             } catch (InterruptedException e) {
/* 232 */               e.printStackTrace();
/*     */             } 
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 238 */     AbstractHudModel.ChangeListenerAdapter changeListenerAdapter = new AbstractHudModel.ChangeListenerAdapter()
/*     */       {
/*     */         public void healthChanged(float fraction) {
/* 241 */           if (!DuelHealthBarWindow.this.fightComplete) {
/* 242 */             DuelHealthBarWindow.this.playerHealthProgressBars[0].setProgress(fraction);
/*     */           }
/*     */         }
/*     */         
/*     */         public void manaChanged(float fraction) {
/* 247 */           if (!DuelHealthBarWindow.this.fightComplete) {
/* 248 */             DuelHealthBarWindow.this.playerManaProgressBars[0].setProgress(fraction);
/*     */           }
/*     */         }
/*     */         
/*     */         public void activePetSelected(int slot) {
/* 253 */           DuelHealthBarWindow.this.updatePetImage(0, MainGameState.getPlayerModel().getActivePet());
/* 254 */           for (BToggleButton button : DuelHealthBarWindow.this.petSelections) {
/* 255 */             button.setSelected(false);
/*     */           }
/* 257 */           DuelHealthBarWindow.this.petSelections[slot].setSelected(true);
/*     */         }
/*     */       };
/*     */     
/* 261 */     MainGameState.getHudModel().addChangeListener((HudModel.ChangeListener)changeListenerAdapter);
/*     */   }
/*     */   
/*     */   public void updateCountdown(int countdownNumber) {
/* 265 */     if (!this.fightComplete) {
/* 266 */       if (countdownNumber <= 5 && countdownNumber > 0) {
/* 267 */         this.outcomeLabel.setText(String.valueOf(countdownNumber));
/* 268 */       } else if (countdownNumber == 0) {
/* 269 */         this.outcomeLabel.setText(TcgGame.getLocalizedText("duelwindow.fight", new String[0]));
/*     */       } else {
/* 271 */         this.outcomeLabel.setText("");
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 278 */     super.render(renderer); int i;
/* 279 */     for (i = 0; i < 2; i++) {
/* 280 */       this.petImages[i].render(renderer);
/* 281 */       this.petBgds[i].render(renderer);
/*     */     } 
/* 283 */     for (i = 0; i < this.petCards.length; i++) {
/* 284 */       this.petCards[i].render(renderer);
/* 285 */       this.petSelections[i].render(renderer);
/*     */     } 
/* 287 */     this.petHolder.render(renderer);
/*     */     
/* 289 */     for (i = 0; i < this.petSelections.length; i++) {
/* 290 */       this.petSelections[i].render(renderer);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(float time) {
/* 296 */     if (this.fightComplete) {
/* 297 */       this.timeComplete -= time;
/* 298 */       if (this.timeComplete <= 0.0F) {
/* 299 */         TcgGame.setDueling(false, 0);
/*     */       }
/*     */     } else {
/* 302 */       setOpponentId(this.opponentId);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void updateOutcome(boolean won) {
/* 307 */     this.fightComplete = true;
/*     */     
/* 309 */     this.playerHealthProgressBars[won ? 1 : 0].setProgressInstant(0.0F);
/* 310 */     this.playerManaProgressBars[0].setProgressInstant(this.playerManaProgressBars[0].getProgress());
/* 311 */     this.playerManaProgressBars[1].setProgressInstant(this.playerManaProgressBars[1].getProgress());
/*     */     
/* 313 */     this.outcomeLabel.setText(won ? TcgGame.getLocalizedText("duelwindow.win", new String[0]) : TcgGame.getLocalizedText("duelwindow.lose", new String[0]));
/*     */   }
/*     */   
/*     */   public void setOpponentProgress(boolean health, float progress) {
/* 317 */     if (!this.fightComplete) {
/* 318 */       if (health) {
/* 319 */         this.playerHealthProgressBars[1].setProgress(progress);
/*     */       } else {
/* 321 */         this.playerManaProgressBars[1].setProgress(progress);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private void updatePetImage(int playerNumber, ClientPet pet) {
/* 328 */     if (pet == null) {
/* 329 */       if (this.petIcons[playerNumber].equals("gui/icons/pets/empty_slot.png")) {
/*     */         return;
/*     */       }
/* 332 */       this.petIcons[playerNumber] = "gui/icons/pets/empty_slot.png";
/*     */     } else {
/* 334 */       if (this.petIcons[playerNumber].equals(pet.getIcon())) {
/*     */         return;
/*     */       }
/* 337 */       this.petIcons[playerNumber] = pet.getIcon();
/*     */     } 
/*     */     
/* 340 */     BImage iconImage = null;
/*     */     
/*     */     try {
/* 343 */       iconImage = (BImage)TcgGame.getResourceManager().getResource(BImage.class, this.petIcons[playerNumber]);
/* 344 */     } catch (NoLocatorException e) {
/* 345 */       e.printStackTrace();
/* 346 */       throw new RuntimeException("Missing image for destination portal: " + this.petIcons[playerNumber]);
/*     */     } 
/*     */     
/* 349 */     ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, iconImage);
/* 350 */     remove((BComponent)this.petImages[playerNumber]);
/* 351 */     this.petImages[playerNumber].setBackground(0, (BBackground)imageBackground);
/* 352 */     this.petImages[playerNumber].setBackground(1, (BBackground)imageBackground);
/* 353 */     add((BComponent)this.petImages[playerNumber], new Rectangle(this.petImages[playerNumber].getX(), this.petImages[playerNumber].getY(), this.petImages[playerNumber].getWidth(), this.petImages[playerNumber].getHeight()));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getOpponentId() {
/* 358 */     return this.opponentId;
/*     */   }
/*     */   
/*     */   public void setOpponentId(int opponentId) {
/* 362 */     this.opponentId = opponentId;
/* 363 */     PropNode propNode = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(opponentId));
/* 364 */     if (propNode != null && propNode.getProp() instanceof ClientPlayer) {
/* 365 */       ClientPlayer player = (ClientPlayer)propNode.getProp();
/* 366 */       this.playerNames[1].setText(player.getName());
/* 367 */       updatePetImage(1, player.getActivePet());
/*     */     } else {
/*     */       
/* 370 */       this.playerNames[1].setText(TcgGame.getLocalizedText("duelwindow.opponent", new String[0]));
/* 371 */       updatePetImage(1, (ClientPet)null);
/*     */     } 
/* 373 */     updatePetImage(0, MainGameState.getPlayerModel().getActivePet());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 379 */     int mx = MouseInput.get().getXAbsolute();
/* 380 */     int my = MouseInput.get().getYAbsolute();
/* 381 */     return isHit(mx, my);
/*     */   }
/*     */   
/*     */   public boolean isHit(int mx, int my) {
/* 385 */     boolean hit = subWindowsHit(mx, my);
/* 386 */     return hit;
/*     */   }
/*     */   
/*     */   private boolean subWindowsHit(int mx, int my) {
/* 390 */     if (this.topWindow.getHitComponent(mx, my) != null) {
/* 391 */       return true;
/*     */     }
/* 393 */     if (this.bottomWindow.getHitComponent(mx, my) != null) {
/* 394 */       return true;
/*     */     }
/*     */     
/* 397 */     if (this.cancelDuelButton.getHitComponent(mx, my) != null) {
/* 398 */       return true;
/*     */     }
/* 400 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\duel\DuelHealthBarWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */