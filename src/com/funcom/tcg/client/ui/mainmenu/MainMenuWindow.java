/*     */ package com.funcom.tcg.client.ui.mainmenu;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.AbstractTcgWindow;
/*     */ import com.jmex.bui.BButton2;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.BorderLayout;
/*     */ import com.jmex.bui.layout.TableLayout;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ 
/*     */ public class MainMenuWindow
/*     */   extends AbstractTcgWindow
/*     */ {
/*  20 */   private static int WINDOW_WIDTH = 300;
/*  21 */   private static int WINDOW_HEIGHT = 400;
/*     */   
/*     */   private BContainer menuContainer;
/*     */   private MainMenuModel model;
/*     */   
/*     */   public MainMenuWindow(ResourceManager resourceManager, MainMenuModel model) {
/*  27 */     super(resourceManager);
/*  28 */     this.model = model;
/*     */     
/*  30 */     setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  35 */     setLayoutManager(null);
/*     */     
/*  37 */     this.menuContainer = new BContainer((BLayoutManager)new TableLayout(1, 10, 5));
/*  38 */     addButtons(this.menuContainer);
/*  39 */     add((BComponent)this.menuContainer, BorderLayout.CENTER);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void layout() {
/*  44 */     super.layout();
/*  45 */     Dimension preferredSize = this.menuContainer.getPreferredSize(-1, -1);
/*     */     
/*  47 */     int width = getWidth();
/*  48 */     int height = getHeight();
/*     */     
/*  50 */     int x = (width - preferredSize.width) / 2;
/*  51 */     int y = (height - preferredSize.height) / 2;
/*     */     
/*  53 */     this.menuContainer.setBounds(x, y, preferredSize.width, preferredSize.height);
/*     */   }
/*     */   
/*     */   private void addButtons(BContainer menuContainer) {
/*  57 */     BButton2 cancel = new BButton2(TcgGame.getLocalizedText("mainmenuwindow.cancel", new String[0]));
/*  58 */     cancel.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/*  61 */             MainMenuWindow.this.model.cancel();
/*     */           }
/*     */         });
/*  64 */     menuContainer.add((BComponent)cancel);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  75 */     BButton2 help = new BButton2(TcgGame.getLocalizedText("helpwindow.webbutton", new String[0]));
/*  76 */     help.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/*  79 */             MainMenuWindow.this.model.help();
/*     */           }
/*     */         });
/*  82 */     menuContainer.add((BComponent)help);
/*     */     
/*  84 */     BButton2 options = new BButton2(TcgGame.getLocalizedText("mainmenuwindow.options", new String[0]));
/*  85 */     options.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/*  88 */             MainMenuWindow.this.model.options();
/*     */           }
/*     */         });
/*  91 */     menuContainer.add((BComponent)options);
/*     */     
/*  93 */     BButton2 quitToLogin = new BButton2(TcgGame.getLocalizedText("mainmenuwindow.logout", new String[0]));
/*  94 */     quitToLogin.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/*  97 */             MainMenuWindow.this.model.quitToLogin();
/*     */           }
/*     */         });
/*     */     
/* 101 */     menuContainer.add((BComponent)quitToLogin);
/*     */     
/* 103 */     BButton2 quit = new BButton2(TcgGame.getLocalizedText("mainmenuwindow.quit", new String[0]));
/* 104 */     quit.setStyleClass("quit-button");
/* 105 */     quit.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 108 */             MainMenuWindow.this.model.quit();
/*     */           }
/*     */         });
/* 111 */     menuContainer.add((BComponent)quit);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\mainmenu\MainMenuWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */