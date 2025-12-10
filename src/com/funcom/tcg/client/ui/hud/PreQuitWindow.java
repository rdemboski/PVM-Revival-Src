/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.net.message.AccountSubscribeTokenRequestMessage;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class PreQuitWindow
/*     */   extends BWindow {
/*     */   public static final int WINDOW_WIDTH = 400;
/*     */   public static final int WINDOW_HEIGHT = 425;
/*     */   private static final int buttonWidth = 160;
/*  30 */   public static final Rectangle MESSAGE_CONSTRAINTS = new Rectangle(35, 260, 330, 150); private static final int buttonHeight = 54; private static final int petWidth = 280;
/*     */   private static final int messageWidth = 330;
/*  32 */   public static final Rectangle PET_LABEL_CONSTRAINTS = new Rectangle(60, 0, 280, 210);
/*     */   
/*  34 */   public static final Rectangle OK_BUTTON_CONSTRAINTS = new Rectangle(205, 200, 160, 54);
/*     */   
/*  36 */   public static final Rectangle CANCEL_BUTTON_CONSTRAINTS = new Rectangle(35, 200, 160, 54);
/*     */   
/*     */   public static final String STYLE_CLASS = "quitwindow";
/*     */   
/*     */   private BButton cancelButton;
/*     */   
/*     */   private BButton okButton;
/*     */   
/*     */   private BLabel messageLabel;
/*     */   
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   private PreQuitMode mode;
/*     */   private boolean quitToLogin;
/*     */   
/*     */   public PreQuitWindow(ResourceManager resourceManager, boolean quitToLogin) {
/*  52 */     super(null, (BLayoutManager)new AbsoluteLayout()); PreQuitMode quitMode;
/*  53 */     if (TcgGame.isStartDuelMode()) {
/*  54 */       TcgGame.setStartDuelMode(false);
/*     */     }
/*  56 */     setLayer(101);
/*     */     
/*  58 */     BWindow existingWindow = TcgUI.getWindowFromClass(SubscribeWindow.class);
/*  59 */     if (existingWindow != null) {
/*  60 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */     
/*  63 */     existingWindow = TcgUI.getWindowFromClass(PreQuitWindow.class);
/*  64 */     if (existingWindow != null) {
/*  65 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */     
/*  68 */     existingWindow = TcgUI.getWindowFromClass(QuitWindow.class);
/*  69 */     if (existingWindow != null) {
/*  70 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */ 
/*     */     
/*  74 */     this.resourceManager = resourceManager;
/*  75 */     this.quitToLogin = quitToLogin;
/*     */     
/*  77 */     this._style = BuiUtils.createMergedClassStyleSheets(PreQuitWindow.class, new BananaResourceProvider(resourceManager));
/*     */ 
/*     */     
/*  80 */     initComponents();
/*  81 */     initListeners();
/*     */ 
/*     */     
/*  84 */     if (!MainGameState.isPlayerRegistered()) {
/*  85 */       quitMode = PreQuitMode.ASK_REGISTER;
/*     */     } else {
/*  87 */       quitMode = PreQuitMode.ASK_SUBSCRIBE;
/*     */     } 
/*  89 */     setMode(quitMode);
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*  93 */     setSize(400, 425);
/*  94 */     setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - 200, DisplaySystem.getDisplaySystem().getHeight() / 2 - 212);
/*     */     
/*  96 */     setStyleClass("quitwindow");
/*     */     
/*  98 */     BLabel petLabel = new BLabel("");
/*  99 */     petLabel.setStyleClass("prequitwindow.pet.label");
/*     */     
/* 101 */     this.messageLabel = new BLabel("");
/* 102 */     this.messageLabel.setStyleClass("quitwindow.label");
/*     */     
/* 104 */     this.okButton = new BButton("");
/* 105 */     this.okButton.setStyleClass("quitwindow.button");
/*     */     
/* 107 */     this.cancelButton = new BButton("");
/* 108 */     this.cancelButton.setStyleClass("quitwindow.button.cancel");
/*     */     
/* 110 */     add((BComponent)petLabel, PET_LABEL_CONSTRAINTS);
/* 111 */     add((BComponent)this.messageLabel, MESSAGE_CONSTRAINTS);
/* 112 */     add((BComponent)this.okButton, OK_BUTTON_CONSTRAINTS);
/* 113 */     add((BComponent)this.cancelButton, CANCEL_BUTTON_CONSTRAINTS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initListeners() {
/* 119 */     this.okButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 122 */             if (PreQuitWindow.this.mode == PreQuitWindow.PreQuitMode.ASK_SUBSCRIBE) {
/* 123 */               PreQuitWindow.this.dismiss();
/*     */               try {
/* 125 */                 NetworkHandler.instance().getIOHandler().send((Message)new AccountSubscribeTokenRequestMessage());
/* 126 */               } catch (InterruptedException e) {
/* 127 */                 e.printStackTrace();
/*     */               } 
/* 129 */             } else if (PreQuitWindow.this.mode == PreQuitWindow.PreQuitMode.ASK_REGISTER) {
/* 130 */               PreQuitWindow.this.dismiss();
/* 131 */               MainGameState.getInstance().showRegisterWindow("OPEN_BEFORE_QUIT");
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 136 */     this.cancelButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 139 */             PreQuitWindow.this.dismiss();
/* 140 */             BWindow existingWindow = TcgUI.getWindowFromClass(QuitWindow.class);
/* 141 */             if (existingWindow == null) {
/* 142 */               QuitWindow window = new QuitWindow(PreQuitWindow.this.resourceManager, PreQuitWindow.this.quitToLogin);
/* 143 */               window.setLayer(103);
/* 144 */               BuiSystem.getRootNode().addWindow(window);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMode(PreQuitMode mode) {
/* 152 */     this.mode = mode;
/* 153 */     this.okButton.setText(TcgGame.getLocalizedText(mode.getOkKey(), new String[0]));
/* 154 */     this.cancelButton.setText(TcgGame.getLocalizedText(mode.getCancelKey(), new String[0]));
/* 155 */     this.messageLabel.setText(TcgGame.getLocalizedText(mode.getMessageKey(), new String[0]));
/*     */   }
/*     */   
/*     */   public enum PreQuitMode {
/* 159 */     ASK_REGISTER("quitwindow.askregister.ok", "quitwindow.askregister.cancel", "quitwindow.askregister.message"),
/* 160 */     ASK_SUBSCRIBE("quitwindow.asksubscribe.ok", "quitwindow.asksubscribe.cancel", "quitwindow.asksubscribe.message");
/*     */     
/*     */     private final String okKey;
/*     */     private final String cancelKey;
/*     */     private final String messageKey;
/*     */     
/*     */     PreQuitMode(String okKey, String cancelKey, String messageKey) {
/* 167 */       this.okKey = okKey;
/* 168 */       this.cancelKey = cancelKey;
/* 169 */       this.messageKey = messageKey;
/*     */     }
/*     */     
/*     */     public String getOkKey() {
/* 173 */       return this.okKey;
/*     */     }
/*     */     
/*     */     public String getCancelKey() {
/* 177 */       return this.cancelKey;
/*     */     }
/*     */     
/*     */     public String getMessageKey() {
/* 181 */       return this.messageKey;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\PreQuitWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */