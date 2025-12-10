/*     */ package com.funcom.tcg.client.ui.startmenu;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class LoginMethodWindow
/*     */   extends BWindow
/*     */ {
/*     */   private static final String STYLE_WINDOW = "window.loginmethod";
/*     */   private static final String STYLE_CLOSE_BUTTON = "close-button";
/*     */   private static Rectangle SIZE_WINDOW;
/*     */   private static Rectangle SIZE_LOGO_LABEL;
/*     */   private static Rectangle SIZE_NEWPLAYER_BUTTON;
/*     */   private static Rectangle SIZE_LOGIN_BUTTON;
/*     */   private static Rectangle SIZE_OPTIONS_BUTTON;
/*     */   private static Rectangle SIZE_CLOSE_BUTTON;
/*     */   private static Rectangle SIZE_NEW_LABEL;
/*     */   private static Rectangle SIZE_RETURN_LABEL;
/*     */   private StartMenuListener menuListener;
/*     */   private StartMenuStartGameListener startGameListener;
/*     */   private StartMenuModel menuModel;
/*     */   private BLabel logoLabel;
/*     */   private BButton loginButton;
/*     */   private BButton newPlayerButton;
/*     */   private BButton optionsButton;
/*     */   private BButton closeButton;
/*     */   private Renderer renderer;
/*     */   
/*     */   public LoginMethodWindow(StartMenuModel menuModel, StartMenuListener menuListener, StartMenuStartGameListener startGameListener, ResourceManager resourceManager, Localizer localizer) {
/*  48 */     super(BuiUtils.createMergedClassStyleSheets(LoginMethodWindow.class, new BananaResourceProvider(resourceManager)), (BLayoutManager)new AbsoluteLayout());
/*     */ 
/*     */     
/*  51 */     this.menuModel = menuModel;
/*  52 */     this.menuListener = menuListener;
/*  53 */     this.startGameListener = startGameListener;
/*     */     
/*  55 */     initConstants();
/*     */ 
/*     */     
/*  58 */     setLocation(SIZE_WINDOW.x, SIZE_WINDOW.y);
/*  59 */     setSize(SIZE_WINDOW.width, SIZE_WINDOW.height);
/*     */ 
/*     */     
/*  62 */     initComponents();
/*  63 */     initListeners();
/*     */   }
/*     */   
/*     */   private void initConstants() {
/*  67 */     SIZE_WINDOW = new Rectangle(0, 0, DisplaySystem.getDisplaySystem().getWidth(), (DisplaySystem.getDisplaySystem().getHeight() < 768) ? 768 : DisplaySystem.getDisplaySystem().getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  72 */     SIZE_LOGO_LABEL = new Rectangle(SIZE_WINDOW.width / 2 - 194, SIZE_WINDOW.height / 2 + 150, 389, 200);
/*     */ 
/*     */     
/*  75 */     int buttonWidth = 300;
/*  76 */     int buttonHeight = 211;
/*  77 */     int labelHeight = 30;
/*     */     
/*  79 */     int LABEL_OFFSET = !System.getProperty("tcg.locale").equals("fr") ? 0 : 5;
/*     */     
/*  81 */     SIZE_NEWPLAYER_BUTTON = new Rectangle(SIZE_WINDOW.width / 2 + 50, SIZE_WINDOW.height / 2 - buttonHeight / 2, buttonWidth, buttonHeight);
/*     */ 
/*     */     
/*  84 */     SIZE_NEW_LABEL = new Rectangle(SIZE_WINDOW.width / 2 + 50, SIZE_WINDOW.height / 2 - labelHeight / 2, buttonWidth, labelHeight);
/*     */ 
/*     */     
/*  87 */     SIZE_LOGIN_BUTTON = new Rectangle(SIZE_WINDOW.width / 2 - buttonWidth - 50, SIZE_WINDOW.height / 2 - buttonHeight / 2, buttonWidth, buttonHeight);
/*     */ 
/*     */     
/*  90 */     SIZE_RETURN_LABEL = new Rectangle(SIZE_WINDOW.width / 2 - buttonWidth - 50, SIZE_WINDOW.height / 2 - labelHeight / 2 - LABEL_OFFSET, buttonWidth, labelHeight);
/*     */ 
/*     */ 
/*     */     
/*  94 */     SIZE_OPTIONS_BUTTON = !System.getProperty("tcg.locale").equals("no") ? new Rectangle(SIZE_WINDOW.width / 2 - 85, SIZE_WINDOW.height / 2 - 384, 170, 69) : new Rectangle(SIZE_WINDOW.width / 2 - 125, SIZE_WINDOW.height / 2 - 384, 250, 69);
/*     */ 
/*     */ 
/*     */     
/*  98 */     SIZE_CLOSE_BUTTON = new Rectangle(SIZE_WINDOW.width - 60, SIZE_WINDOW.height - 60, 54, 54);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 103 */     this.loginButton = new BButton(TcgGame.getLocalizedText("startmenu.loginmethod.button.continue", new String[0]).toUpperCase());
/* 104 */     this.loginButton.setStyleClass("button");
/*     */     
/* 106 */     this.newPlayerButton = new BButton(TcgGame.getLocalizedText("startmenu.loginmethod.button.newgame", new String[0]).toUpperCase());
/* 107 */     this.newPlayerButton.setStyleClass("button");
/*     */ 
/*     */     
/* 110 */     BClickthroughLabel newPlayerLabel = new BClickthroughLabel("");
/* 111 */     newPlayerLabel.setStyleClass("label.new." + ((System.getProperty("tcg.locale") != null) ? System.getProperty("tcg.locale") : "en"));
/*     */ 
/*     */     
/* 114 */     BClickthroughLabel returnPlayerLabel = new BClickthroughLabel("");
/* 115 */     returnPlayerLabel.setStyleClass("label.returning." + ((System.getProperty("tcg.locale") != null) ? System.getProperty("tcg.locale") : "en"));
/*     */     
/* 117 */     this.optionsButton = new BButton(TcgGame.getLocalizedText("startmenu.loginmethod.button.options", new String[0]).toUpperCase());
/* 118 */     this.optionsButton.setStyleClass("button.options");
/*     */     
/* 120 */     this.logoLabel = new BLabel("");
/* 121 */     this.logoLabel.setStyleClass("label.logo");
/*     */     
/* 123 */     this.closeButton = new BButton("");
/* 124 */     this.closeButton.setStyleClass("close-button");
/*     */     
/* 126 */     add((BComponent)this.logoLabel, SIZE_LOGO_LABEL);
/*     */     
/* 128 */     add((BComponent)this.newPlayerButton, SIZE_NEWPLAYER_BUTTON);
/* 129 */     add((BComponent)newPlayerLabel, SIZE_NEWPLAYER_BUTTON);
/* 130 */     add((BComponent)this.loginButton, SIZE_LOGIN_BUTTON);
/* 131 */     add((BComponent)returnPlayerLabel, SIZE_LOGIN_BUTTON);
/* 132 */     add((BComponent)this.optionsButton, SIZE_OPTIONS_BUTTON);
/* 133 */     if (DisplaySystem.getDisplaySystem().isFullScreen()) {
/* 134 */       add((BComponent)this.closeButton, SIZE_CLOSE_BUTTON);
/*     */     }
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 139 */     this.closeButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 142 */             TcgGame.getInstance().addQuitDialog();
/*     */           }
/*     */         });
/*     */     
/* 146 */     this.loginButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 149 */             if (LoginMethodWindow.this.isAdded()) {
/* 150 */               TcgUI.getUISoundPlayer().play("ClickForward");
/* 151 */               LoginMethodWindow.this.startGameListener.showLoginWizard();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 156 */     this.newPlayerButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 159 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 160 */             LoginMethodWindow.this.startGameListener.showNewPlayerWizard();
/*     */           }
/*     */         });
/*     */     
/* 164 */     this.optionsButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 167 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 168 */             LoginMethodWindow.this.menuListener.showOptions();
/*     */           }
/*     */         });
/*     */     
/* 172 */     render(this.renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 177 */     super.setVisible(visible);
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(Renderer renderer) {
/* 182 */     this.renderer = renderer;
/* 183 */     super.render(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   protected BComponent getNextFocus(BComponent current) {
/* 188 */     return BuiUtils.getNextFocus((BContainer)this, current);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\LoginMethodWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */