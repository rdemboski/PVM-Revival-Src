/*     */ package com.funcom.tcg.client.ui.startmenu;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.IrregularButton;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.metrics.HttpMetrics;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ 
/*     */ public class EulaWindow
/*     */   extends BWindow
/*     */ {
/*     */   private StartMenuListener menuListener;
/*     */   private ResourceManager resourceManager;
/*     */   private BButton tosButton;
/*     */   private BButton privacyButton;
/*     */   private BButton acceptButton;
/*     */   private BButton backButton;
/*     */   private BLabel noahBubble;
/*     */   private BLabel legalLabel;
/*     */   private BLabel parentsLabel;
/*     */   private BLabel separator;
/*     */   
/*     */   public EulaWindow(StartMenuListener menuListener, ResourceManager resourceManager) {
/*  38 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  39 */     this.menuListener = menuListener;
/*  40 */     this.resourceManager = resourceManager;
/*  41 */     this._style = BuiUtils.createMergedClassStyleSheets(EulaWindow.class, new BananaResourceProvider(resourceManager));
/*     */ 
/*     */     
/*  44 */     setBounds(0, 0, DisplaySystem.getDisplaySystem().getWidth(), DisplaySystem.getDisplaySystem().getHeight());
/*     */     
/*  46 */     initComponents();
/*  47 */     initListeners();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  52 */     this.tosButton = new BButton(TcgGame.getLocalizedText("text.window.legal.tos", new String[0]).toUpperCase());
/*  53 */     this.tosButton.setStyleClass("button.terms");
/*     */     
/*  55 */     this.privacyButton = new BButton(TcgGame.getLocalizedText("text.window.legal.pp", new String[0]).toUpperCase());
/*  56 */     this.privacyButton.setStyleClass("button.terms");
/*     */     
/*  58 */     this.acceptButton = (BButton)new IrregularButton(TcgGame.getLocalizedText("text.window.legal.accept", new String[0]).toUpperCase());
/*  59 */     this.acceptButton.setStyleClass("button_accept");
/*     */     
/*  61 */     this.backButton = new BButton(TcgGame.getLocalizedText("mainmenuwindow.back", new String[0]).toUpperCase());
/*  62 */     this.backButton.setStyleClass("button_back");
/*     */     
/*  64 */     this.noahBubble = new BLabel("");
/*  65 */     this.noahBubble.setStyleClass("label_wolf");
/*     */ 
/*     */     
/*  68 */     this.parentsLabel = new BLabel(TcgGame.getLocalizedText("text.window.legal.parents", new String[0]).toUpperCase());
/*  69 */     this.parentsLabel.setStyleClass("label_parents");
/*     */     
/*  71 */     this.separator = new BLabel("");
/*  72 */     this.separator.setStyleClass("separator");
/*     */     
/*  74 */     this.legalLabel = new BLabel(TcgGame.getLocalizedText("text.window.legal.message", new String[0]));
/*  75 */     this.legalLabel.setStyleClass((System.getProperty("tcg.locale") != null && System.getProperty("tcg.locale").equals("fr")) ? "label_textbubble_fr" : "label_textbubble");
/*     */     
/*  77 */     int tosButtonWidth = (System.getProperty("tcg.locale") != null && System.getProperty("tcg.locale").equals("en")) ? 200 : 300;
/*     */     
/*  79 */     int buttonHeight = 69;
/*     */     
/*  81 */     int backButtonSize = System.getProperty("tcg.locale").equals("en") ? 170 : 215;
/*     */     
/*  83 */     int noahWidth = 800;
/*  84 */     int noahHeight = 474;
/*     */     
/*  86 */     int WIDTH = DisplaySystem.getDisplaySystem().getWidth();
/*  87 */     int HEIGHT = (DisplaySystem.getDisplaySystem().getHeight() < 768) ? 768 : DisplaySystem.getDisplaySystem().getHeight();
/*  88 */     int OFFSET_X = WIDTH / 2 - 512;
/*  89 */     int OFFSET_Y = HEIGHT / 2 - 384;
/*     */     
/*  91 */     add((BComponent)this.noahBubble, new Rectangle(WIDTH / 2 - noahWidth / 2, HEIGHT / 2 - noahHeight / 2, noahWidth, noahHeight));
/*  92 */     add((BComponent)this.parentsLabel, new Rectangle(WIDTH / 2 - noahWidth / 2 + 440, HEIGHT / 2 - noahHeight / 2 + 300, 340, 45));
/*  93 */     add((BComponent)this.separator, new Rectangle(WIDTH / 2 - noahWidth / 2 + 440, HEIGHT / 2 - noahHeight / 2 + 290, 340, 10));
/*  94 */     add((BComponent)this.legalLabel, new Rectangle(WIDTH / 2 - noahWidth / 2 + 440, HEIGHT / 2 - noahHeight / 2 + 24, 340, 280));
/*     */     
/*  96 */     add((BComponent)this.backButton, new Rectangle(OFFSET_X, OFFSET_Y, backButtonSize, 69));
/*  97 */     add((BComponent)this.tosButton, new Rectangle(WIDTH / 2 - tosButtonWidth, OFFSET_Y, tosButtonWidth, buttonHeight));
/*  98 */     add((BComponent)this.privacyButton, new Rectangle(WIDTH / 2, OFFSET_Y, tosButtonWidth, buttonHeight));
/*  99 */     add((BComponent)this.acceptButton, new Rectangle(OFFSET_X + 1024 - backButtonSize, OFFSET_Y, backButtonSize, 69));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 106 */     super.setVisible(visible);
/* 107 */     if (visible) {
/* 108 */       HttpMetrics.postEvent(HttpMetrics.Event.TOS_LOADED);
/*     */     }
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 113 */     this.acceptButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 116 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 117 */             EulaWindow.this.menuListener.eulaAccepted();
/*     */           }
/*     */         });
/*     */     
/* 121 */     this.backButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 124 */             TcgUI.getUISoundPlayer().play("ClickBackward");
/* 125 */             EulaWindow.this.menuListener.eulaBack();
/*     */           }
/*     */         });
/*     */     
/* 129 */     this.tosButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 132 */             EulaWindow.this.menuListener.eulaTos();
/*     */           }
/*     */         });
/*     */     
/* 136 */     this.privacyButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 139 */             EulaWindow.this.menuListener.eulaPP();
/*     */           }
/*     */         });
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\startmenu\EulaWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */