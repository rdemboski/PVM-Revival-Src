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
/*     */ public class SubscribeWindow extends BWindow {
/*     */   public static final int WINDOW_WIDTH = 400;
/*  27 */   private final int TOP_WINDOW_HEIGHT = 175; public static final int WINDOW_HEIGHT = 425; private static final int buttonWidth = 160;
/*     */   private static final int buttonHeight = 54;
/*     */   private static final int petWidth = 280;
/*     */   private static final int messageWidth = 360;
/*  31 */   public static final Rectangle MESSAGE_CONSTRAINTS = new Rectangle(20, 260, 360, 150);
/*     */   
/*  33 */   public static final Rectangle PET_LABEL_CONSTRAINTS = new Rectangle(60, 0, 280, 210);
/*     */   
/*  35 */   public static final Rectangle OK_BUTTON_CONSTRAINTS = new Rectangle(205, 200, 160, 54);
/*     */   
/*  37 */   public static final Rectangle CANCEL_BUTTON_CONSTRAINTS = new Rectangle(35, 200, 160, 54);
/*     */   
/*     */   private BButton cancelButton;
/*     */   
/*     */   private BButton okButton;
/*     */   
/*     */   private BLabel messageLabel;
/*     */   
/*     */   private boolean subscribe;
/*     */   private String okText;
/*     */   private String cancelText;
/*     */   private String messageText;
/*     */   
/*     */   public SubscribeWindow(ResourceManager resourceManager, boolean subscribe, String okText, String cancelText, String messageText) {
/*  51 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  52 */     if (TcgGame.isStartDuelMode()) {
/*  53 */       TcgGame.setStartDuelMode(false);
/*     */     }
/*  55 */     setLayer(101);
/*     */     
/*  57 */     BWindow existingWindow = TcgUI.getWindowFromClass(SubscribeWindow.class);
/*  58 */     if (existingWindow != null) {
/*  59 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */     
/*  62 */     existingWindow = TcgUI.getWindowFromClass(PreQuitWindow.class);
/*  63 */     if (existingWindow != null) {
/*  64 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */     
/*  67 */     existingWindow = TcgUI.getWindowFromClass(QuitWindow.class);
/*  68 */     if (existingWindow != null) {
/*  69 */       BuiSystem.getRootNode().removeWindow(existingWindow);
/*     */     }
/*     */     
/*  72 */     this.subscribe = subscribe;
/*  73 */     this.okText = okText;
/*  74 */     this.cancelText = cancelText;
/*  75 */     this.messageText = messageText;
/*     */     
/*  77 */     this._style = BuiUtils.createMergedClassStyleSheets(SubscribeWindow.class, new BananaResourceProvider(resourceManager));
/*     */ 
/*     */     
/*  80 */     initComponents();
/*  81 */     initListeners();
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*  85 */     setSize(400, 425);
/*  86 */     setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - 200, DisplaySystem.getDisplaySystem().getHeight() / 2 - 212);
/*     */     
/*  88 */     setStyleClass("subscribewindow");
/*  89 */     setLayer(101);
/*     */     
/*  91 */     BLabel petLabel = new BLabel("");
/*  92 */     petLabel.setStyleClass(this.subscribe ? "pet.label" : "pet.label.save");
/*     */     
/*  94 */     this.messageLabel = new BLabel("");
/*  95 */     this.messageLabel.setStyleClass("label");
/*  96 */     this.messageLabel.setText(TcgGame.getLocalizedText(this.messageText, new String[0]));
/*     */     
/*  98 */     this.okButton = new BButton("");
/*  99 */     this.okButton.setStyleClass("button");
/* 100 */     this.okButton.setText(TcgGame.getLocalizedText(this.okText, new String[0]));
/*     */     
/* 102 */     this.cancelButton = new BButton("");
/* 103 */     this.cancelButton.setStyleClass("button.cancel");
/* 104 */     this.cancelButton.setText(TcgGame.getLocalizedText(this.cancelText, new String[0]));
/*     */     
/* 106 */     add((BComponent)petLabel, PET_LABEL_CONSTRAINTS);
/* 107 */     add((BComponent)this.messageLabel, MESSAGE_CONSTRAINTS);
/* 108 */     add((BComponent)this.okButton, OK_BUTTON_CONSTRAINTS);
/* 109 */     add((BComponent)this.cancelButton, CANCEL_BUTTON_CONSTRAINTS);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initListeners() {
/* 114 */     this.okButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 117 */             if (SubscribeWindow.this.subscribe) {
/* 118 */               SubscribeWindow.this.dismiss();
/*     */               try {
/* 120 */                 NetworkHandler.instance().getIOHandler().send((Message)new AccountSubscribeTokenRequestMessage());
/* 121 */               } catch (InterruptedException e) {
/* 122 */                 e.printStackTrace();
/*     */               } 
/*     */             } else {
/* 125 */               SubscribeWindow.this.dismiss();
/* 126 */               MainGameState.getInstance().showRegisterWindow("OPEN_FROM_SUBSCRIBE_POPUP");
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 131 */     this.cancelButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 134 */             SubscribeWindow.this.dismiss();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTopLayout(boolean top) {
/* 141 */     setBounds(getX(), DisplaySystem.getDisplaySystem().getHeight() - 250, getWidth(), 175);
/*     */     
/* 143 */     removeAll();
/* 144 */     add((BComponent)this.messageLabel, new Rectangle(20, 100, 360, 60));
/* 145 */     add((BComponent)this.okButton, new Rectangle(205, 25, 160, 54));
/* 146 */     add((BComponent)this.cancelButton, new Rectangle(35, 25, 160, 54));
/* 147 */     setLayer(2);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\SubscribeWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */