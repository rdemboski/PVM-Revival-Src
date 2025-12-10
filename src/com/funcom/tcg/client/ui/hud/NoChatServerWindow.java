/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.GridLayout;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class NoChatServerWindow extends BWindow {
/*     */   public static final int WINDOW_WIDTH = 400;
/*  19 */   public static final Rectangle LABEL_CONTRAINTS = new Rectangle(20, 150, 320, 30); public static final int WINDOW_HEIGHT = 250;
/*  20 */   public static final Rectangle BUTTON_CONTAINER_CONTRAINTS = new Rectangle(100, 30, 320, 80);
/*     */   
/*     */   public static final String STYLE_CLASS = "quitwindow";
/*     */   private int buttonWidth;
/*     */   private int buttonHeight;
/*     */   private BButton okButton;
/*     */   private BLabel messageLabel;
/*     */   
/*     */   public NoChatServerWindow(ResourceManager resourceManager, boolean isChatEnabled) {
/*  29 */     super(null, (BLayoutManager)new AbsoluteLayout()); PreQuitMode quitMode; this.buttonWidth = 160; this.buttonHeight = 40;
/*  30 */     this._style = BuiUtils.createMergedClassStyleSheets(QuitWindow.class, new BananaResourceProvider(resourceManager));
/*     */     
/*  32 */     setSize(400, 250);
/*  33 */     setLocation(DisplaySystem.getDisplaySystem().getWidth() / 2 - 200, DisplaySystem.getDisplaySystem().getHeight() / 2 - 125);
/*  34 */     setStyleClass("quitwindow");
/*     */     
/*  36 */     add((BComponent)layoutComponents(), new Rectangle(0, 0, 400, 250));
/*     */ 
/*     */ 
/*     */     
/*  40 */     if (isChatEnabled) {
/*  41 */       quitMode = PreQuitMode.CHAT_ENABLED;
/*     */     } else {
/*  43 */       quitMode = PreQuitMode.CHAT_DISABLED;
/*     */     } 
/*     */     
/*  46 */     setMode(quitMode);
/*     */   }
/*     */   
/*     */   private BContainer layoutComponents() {
/*  50 */     BContainer container = new BContainer((BLayoutManager)new AbsoluteLayout());
/*     */     
/*  52 */     this.messageLabel = new BLabel("");
/*  53 */     this.messageLabel.setStyleClass("quitwindow.label");
/*  54 */     container.add((BComponent)this.messageLabel, LABEL_CONTRAINTS);
/*     */     
/*  56 */     BContainer buttonContainer = makeButtonContainer();
/*  57 */     container.add((BComponent)buttonContainer, BUTTON_CONTAINER_CONTRAINTS);
/*     */     
/*  59 */     return container;
/*     */   }
/*     */ 
/*     */   
/*     */   private BContainer makeButtonContainer() {
/*  64 */     BContainer buttonContainer = new BContainer((BLayoutManager)new GridLayout(2, 1));
/*     */     
/*  66 */     this.okButton = new BButton("");
/*  67 */     this.okButton.setStyleClass("quitwindow.button");
/*  68 */     this.okButton.setPreferredSize(this.buttonWidth, this.buttonHeight);
/*     */     
/*  70 */     this.okButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/*  73 */             NoChatServerWindow.this.onOk();
/*     */           }
/*     */         });
/*  76 */     buttonContainer.add((BComponent)this.okButton);
/*     */     
/*  78 */     return buttonContainer;
/*     */   }
/*     */   
/*     */   private void onOk() {
/*  82 */     dismiss();
/*     */   }
/*     */   
/*     */   public void setMode(PreQuitMode mode) {
/*  86 */     this.okButton.setText(TcgGame.getLocalizedText(mode.getOkKey(), new String[0]));
/*  87 */     this.messageLabel.setText(TcgGame.getLocalizedText(mode.getMessageKey(), new String[0]));
/*     */   }
/*     */   
/*     */   public enum PreQuitMode {
/*  91 */     CHAT_DISABLED("common.ok.text", "dialog.chat.disabled"),
/*  92 */     CHAT_ENABLED("common.ok.text", "dialog.chat.enabled");
/*     */     
/*     */     private final String okKey;
/*     */     private final String messageKey;
/*     */     
/*     */     PreQuitMode(String okKey, String messageKey) {
/*  98 */       this.okKey = okKey;
/*  99 */       this.messageKey = messageKey;
/*     */     }
/*     */     
/*     */     public String getOkKey() {
/* 103 */       return this.okKey;
/*     */     }
/*     */     
/*     */     public String getMessageKey() {
/* 107 */       return this.messageKey;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\NoChatServerWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */