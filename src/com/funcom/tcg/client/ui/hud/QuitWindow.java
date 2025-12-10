/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.server.common.GameIOHandler;
/*     */ import com.funcom.server.common.LogoutMessage;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
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
/*     */ public class QuitWindow extends BWindow {
/*     */   public static final int WINDOW_WIDTH = 400;
/*  28 */   public static final Rectangle LABEL_CONSTRAINTS = new Rectangle(35, 260, 330, 150); public static final int WINDOW_HEIGHT = 425; private static final int buttonWidth = 160; private static final int buttonHeight = 54; private static final int petWidth = 280;
/*     */   private static final int labelWidth = 330;
/*  30 */   public static final Rectangle OK_BUTTON_CONSTRAINTS = new Rectangle(35, 200, 160, 54);
/*     */   
/*  32 */   public static final Rectangle CANCEL_BUTTON_CONSTRAINTS = new Rectangle(205, 200, 160, 54);
/*     */   
/*  34 */   public static final Rectangle PET_LABEL_CONSTRAINTS = new Rectangle(60, 0, 280, 210);
/*     */   
/*     */   public static final String STYLE_CLASS = "quitwindow";
/*     */   
/*     */   private BButton cancelButton;
/*     */   
/*     */   private BButton okButton;
/*     */   
/*     */   private BLabel messageLabel;
/*     */   private boolean quitToLogin;
/*     */   
/*     */   public QuitWindow(ResourceManager resourceManager, boolean quitToLogin) {
/*  46 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  47 */     if (TcgGame.isStartDuelMode()) {
/*  48 */       TcgGame.setStartDuelMode(false);
/*     */     }
/*  50 */     setLayer(103);
/*     */     
/*  52 */     BWindow existingWindow = TcgUI.getWindowFromClass(SubscribeWindow.class);
/*  53 */     if (existingWindow != null) {
/*  54 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */     
/*  57 */     existingWindow = TcgUI.getWindowFromClass(PreQuitWindow.class);
/*  58 */     if (existingWindow != null) {
/*  59 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */     
/*  62 */     existingWindow = TcgUI.getWindowFromClass(QuitWindow.class);
/*  63 */     if (existingWindow != null) {
/*  64 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */     
/*  67 */     this.quitToLogin = quitToLogin;
/*  68 */     this._style = BuiUtils.createMergedClassStyleSheets(QuitWindow.class, new BananaResourceProvider(resourceManager));
/*     */ 
/*     */     
/*  71 */     setSize(400, 425);
/*  72 */     setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - 200, DisplaySystem.getDisplaySystem().getHeight() / 2 - 212);
/*     */ 
/*     */     
/*  75 */     setStyleClass("quitwindow");
/*     */     
/*  77 */     initComponents();
/*  78 */     initListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  84 */     boolean save = MainGameState.isPlayerRegistered();
/*     */     
/*  86 */     BLabel petLabel = new BLabel("");
/*  87 */     petLabel.setStyleClass("prequitwindow.pet.label");
/*     */     
/*  89 */     String okText = "";
/*  90 */     String messageText = "";
/*     */     
/*  92 */     if (save) {
/*  93 */       okText = "quitwindow.askquit.save" + (this.quitToLogin ? ".logout" : "");
/*  94 */       messageText = "quitwindow.askquit.message.save" + (this.quitToLogin ? ".logout" : "");
/*     */     } else {
/*  96 */       okText = "quitwindow.askquit.ok" + (this.quitToLogin ? ".logout" : "");
/*  97 */       messageText = "quitwindow.askquit.message" + (this.quitToLogin ? ".logout" : "");
/*     */     } 
/*     */     
/* 100 */     this.messageLabel = new BLabel(TcgGame.getLocalizedText(messageText, new String[0]));
/* 101 */     this.messageLabel.setStyleClass("quitwindow.label");
/*     */     
/* 103 */     this.okButton = new BButton(TcgGame.getLocalizedText(okText, new String[0]));
/* 104 */     this.okButton.setStyleClass("quitwindow.button");
/*     */     
/* 106 */     this.cancelButton = new BButton(TcgGame.getLocalizedText("quitwindow.askquit.cancel", new String[0]));
/* 107 */     this.cancelButton.setStyleClass("quitwindow.button");
/*     */     
/* 109 */     add((BComponent)petLabel, PET_LABEL_CONSTRAINTS);
/* 110 */     add((BComponent)this.messageLabel, LABEL_CONSTRAINTS);
/* 111 */     add((BComponent)this.okButton, OK_BUTTON_CONSTRAINTS);
/* 112 */     add((BComponent)this.cancelButton, CANCEL_BUTTON_CONSTRAINTS);
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 116 */     this.okButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*     */             try {
/* 120 */               if (!QuitWindow.this.quitToLogin) {
/* 121 */                 TcgGame.setLoggedOut(false);
/* 122 */                 GameIOHandler gameIOHandler = NetworkHandler.instance().getIOHandler();
/* 123 */                 if (gameIOHandler != null && gameIOHandler.isConnected()) {
/* 124 */                   gameIOHandler.send((Message)new LogoutMessage(false, false));
/*     */                 }
/*     */                 
/* 127 */                 TcgGame.finishGame();
/*     */               } else {
/* 129 */                 MainGameState.setSilentExit(true);
/* 130 */                 TcgGame.setLoggedOut(true);
/* 131 */                 NetworkHandler.instance().getIOHandler().stop();
/*     */               } 
/* 133 */             } catch (InterruptedException e) {
/* 134 */               e.printStackTrace();
/*     */             } 
/*     */           }
/*     */         });
/* 138 */     this.cancelButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 141 */             QuitWindow.this.dismiss();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\QuitWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */