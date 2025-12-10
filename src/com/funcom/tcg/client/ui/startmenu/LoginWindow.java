/*     */ package com.funcom.tcg.client.ui.startmenu;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.account.RegisterWindow;
/*     */ import com.funcom.util.Browser;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
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
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LoginWindow
/*     */   extends BWindow
/*     */ {
/*     */   private static Rectangle SIZE_WINDOW;
/*     */   private static int CENTER_X;
/*     */   private static int CENTER_Y;
/*     */   private static Rectangle SIZE_BANNER_LABEL;
/*     */   private static Rectangle SIZE_CHARACTER_NAME_LABEL;
/*     */   private static Rectangle SIZE_CHARACTER_NAME_TEXT;
/*     */   private static Rectangle SIZE_PASSWORD_LABEL;
/*     */   private static Rectangle SIZE_PASSWORD_TEXT;
/*     */   private static Rectangle SIZE_PLAY_BUTTON;
/*     */   private static Rectangle SIZE_BACK_BUTTON;
/*     */   private static Rectangle SIZE_FORGOT_BUTTON;
/*     */   private static Rectangle SIZE_TITLE_LABEL;
/*     */   private StartMenuListener menuListener;
/*     */   private StartMenuStartGameListener startGameListener;
/*     */   private ResourceManager resourceManager;
/*     */   private StartMenuModel menuModel;
/*     */   private BLabel characterNameLabel;
/*     */   private MessagedBTextField characterNameText;
/*     */   private BLabel passwordLabel;
/*     */   private HoverPasswordField passwordText;
/*     */   private BButton playButton;
/*     */   private BButton backButton;
/*     */   private BLabel bannerLabel;
/*     */   private BLabel titleLabel;
/*     */   private BButton forgotPassButton;
/*     */   
/*     */   public LoginWindow(StartMenuModel menuModel, StartMenuListener menuListener, StartMenuStartGameListener startGameListener, ResourceManager resourceManager) {
/*  58 */     super(BuiUtils.createMergedClassStyleSheets(LoginWindow.class, new BananaResourceProvider(resourceManager)), (BLayoutManager)new AbsoluteLayout());
/*     */ 
/*     */     
/*  61 */     this.menuModel = menuModel;
/*  62 */     this.menuListener = menuListener;
/*  63 */     this.startGameListener = startGameListener;
/*  64 */     this.resourceManager = resourceManager;
/*     */     
/*  66 */     initConstants();
/*     */     
/*  68 */     setLocation(SIZE_WINDOW.x, SIZE_WINDOW.y);
/*  69 */     setSize(SIZE_WINDOW.width, SIZE_WINDOW.height);
/*     */     
/*  71 */     initComponents();
/*  72 */     initListeners();
/*     */   }
/*     */   
/*     */   private void initConstants() {
/*  76 */     int WIDTH = DisplaySystem.getDisplaySystem().getWidth();
/*  77 */     int HEIGHT = (DisplaySystem.getDisplaySystem().getHeight() < 768) ? 768 : DisplaySystem.getDisplaySystem().getHeight();
/*  78 */     int OFFSET_X = WIDTH / 2 - 512;
/*  79 */     int OFFSET_Y = HEIGHT / 2 - 384;
/*     */     
/*  81 */     int buttonSize = System.getProperty("tcg.locale").equals("en") ? 170 : 215;
/*     */     
/*  83 */     SIZE_WINDOW = new Rectangle(0, 0, WIDTH, HEIGHT);
/*  84 */     CENTER_X = WIDTH / 2;
/*  85 */     CENTER_Y = HEIGHT / 2;
/*     */     
/*  87 */     SIZE_BANNER_LABEL = new Rectangle(CENTER_X - 408, CENTER_Y - 163, 816, 326);
/*  88 */     SIZE_TITLE_LABEL = new Rectangle(CENTER_X - 235, CENTER_Y + 90, 450, 50);
/*     */     
/*  90 */     SIZE_CHARACTER_NAME_LABEL = new Rectangle(CENTER_X - 225, CENTER_Y + 25, 175, 41);
/*  91 */     SIZE_CHARACTER_NAME_TEXT = new Rectangle(CENTER_X - 50, CENTER_Y + 25, 250, 41);
/*  92 */     SIZE_PASSWORD_LABEL = new Rectangle(CENTER_X - 225, CENTER_Y - 25, 175, 41);
/*  93 */     SIZE_PASSWORD_TEXT = new Rectangle(CENTER_X - 50, CENTER_Y - 25, 250, 41);
/*  94 */     SIZE_FORGOT_BUTTON = new Rectangle(CENTER_X - 50, CENTER_Y - 60, 250, 41);
/*     */     
/*  96 */     SIZE_PLAY_BUTTON = new Rectangle(OFFSET_X + 1024 - buttonSize, OFFSET_Y, buttonSize, 69);
/*  97 */     SIZE_BACK_BUTTON = new Rectangle(OFFSET_X, OFFSET_Y, buttonSize, 69);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 105 */     this.bannerLabel = new BLabel("");
/* 106 */     this.bannerLabel.setStyleClass("label.bgd");
/* 107 */     this.titleLabel = new BLabel(TcgGame.getLocalizedText("startmenu.login.text.title", new String[0]).toUpperCase());
/* 108 */     this.titleLabel.setStyleClass("label.title");
/*     */     
/* 110 */     this.characterNameLabel = new BLabel(TcgGame.getLocalizedText("startmenu.login.label.charactername", new String[0]));
/* 111 */     this.characterNameLabel.setStyleClass("label");
/* 112 */     this.characterNameText = new MessagedBTextField((MessagedBTextField.CharValidator)new RegisterWindow.NickValidator());
/* 113 */     this.characterNameText.setMaxLength(14);
/* 114 */     this.characterNameText.setText(this.menuModel.getCharactersName());
/* 115 */     this.characterNameText.setEmptyDisplayText(TcgGame.getLocalizedText("startmenu.login.text.charactername", new String[0]).toUpperCase());
/* 116 */     this.characterNameText.setStyleClass("text.charactername");
/*     */     
/* 118 */     this.passwordLabel = new BLabel(TcgGame.getLocalizedText("startmenu.login.label.password", new String[0]));
/* 119 */     this.passwordLabel.setStyleClass("label");
/* 120 */     this.passwordText = new HoverPasswordField();
/* 121 */     this.passwordText.setStyleClass("text.password");
/* 122 */     this.passwordText.setMaxLength(20);
/* 123 */     this.forgotPassButton = new BButton(TcgGame.getLocalizedText("startmenu.login.button.forgotpassword", new String[0]).toUpperCase());
/* 124 */     this.forgotPassButton.setStyleClass("button.forgotpass");
/*     */ 
/*     */     
/* 127 */     this.playButton = new BButton(TcgGame.getLocalizedText("startmenu.login.button.play", new String[0]).toUpperCase());
/* 128 */     this.playButton.setStyleClass("button.play");
/* 129 */     this.backButton = new BButton(TcgGame.getLocalizedText("mainmenuwindow.back", new String[0]).toUpperCase());
/* 130 */     this.backButton.setStyleClass("button.back");
/*     */     
/* 132 */     add((BComponent)this.bannerLabel, SIZE_BANNER_LABEL);
/* 133 */     add((BComponent)this.titleLabel, SIZE_TITLE_LABEL);
/*     */     
/* 135 */     add((BComponent)this.characterNameLabel, SIZE_CHARACTER_NAME_LABEL);
/* 136 */     add((BComponent)this.characterNameText, SIZE_CHARACTER_NAME_TEXT);
/* 137 */     add((BComponent)this.passwordLabel, SIZE_PASSWORD_LABEL);
/* 138 */     add((BComponent)this.passwordText, SIZE_PASSWORD_TEXT);
/* 139 */     add((BComponent)this.forgotPassButton, SIZE_FORGOT_BUTTON);
/* 140 */     add((BComponent)this.playButton, SIZE_PLAY_BUTTON);
/* 141 */     add((BComponent)this.backButton, SIZE_BACK_BUTTON);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initListeners() {
/* 146 */     ActionListener playAction = new ActionListener()
/*     */       {
/*     */         public void actionPerformed(ActionEvent event) {
/* 149 */           if (LoginWindow.this.isAdded()) {
/* 150 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 151 */             LoginWindow.this.handlePlay();
/*     */           } 
/*     */         }
/*     */       };
/*     */     
/* 156 */     this.forgotPassButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*     */             try {
/* 160 */               Browser.openUrl(TcgGame.getLocalizedText("startmenu.login.forgotpassword.link", new String[0]));
/* 161 */             } catch (IOException e) {}
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */     
/* 167 */     this.playButton.addListener((ComponentListener)playAction);
/*     */     
/* 169 */     this.backButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 172 */             TcgUI.getUISoundPlayer().play("ClickBackward");
/* 173 */             LoginWindow.this.menuListener.loginBack();
/*     */           }
/*     */         });
/*     */     
/* 177 */     this.characterNameText.requestFocus();
/*     */     
/* 179 */     this.characterNameText.addListener((ComponentListener)playAction);
/* 180 */     this.passwordText.addListener((ComponentListener)playAction);
/*     */   }
/*     */   
/*     */   private void handlePlay() {
/* 184 */     this.menuModel.setCharactersName(getUsername());
/* 185 */     this.menuModel.setPassword(this.passwordText.getText());
/* 186 */     this.menuModel.setPlayerStartConfig(null);
/* 187 */     this.startGameListener.login();
/*     */   }
/*     */ 
/*     */   
/*     */   protected BComponent getNextFocus(BComponent current) {
/* 192 */     return BuiUtils.getNextFocus((BContainer)this, current);
/*     */   }
/*     */   
/*     */   public void reset() {
/* 196 */     this.characterNameText.setText("");
/* 197 */     this.passwordText.setText("");
/* 198 */     this.characterNameText.requestFocus();
/*     */   }
/*     */   
/*     */   private String getUsername() {
/* 202 */     return this.characterNameText.getText().trim();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\LoginWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */