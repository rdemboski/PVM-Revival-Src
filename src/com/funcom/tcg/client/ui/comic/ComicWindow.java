/*     */ package com.funcom.tcg.client.ui.comic;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.startmenu.StartMenuListener;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BToggleButton;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.layout.CenterLayout;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ComicWindow
/*     */   extends BWindow
/*     */ {
/*     */   private static final int SIZE = 54;
/*     */   private static Rectangle SIZE_WINDOW;
/*     */   private static Rectangle SIZE_WINDOW_TOP;
/*     */   private static Rectangle SIZE_SKIP_BUTTON;
/*     */   private static Rectangle SIZE_TEXT_LABEL;
/*     */   private static Rectangle SIZE_PREV_BUTTON;
/*  38 */   private BToggleButton[] pageButtons = new BToggleButton[7]; private static Rectangle SIZE_NEXT_BUTTON; private static Rectangle SIZE_BACK_BUTTON; private static Rectangle SIZE_CONTINUE_BUTTON; private static int OFFSET_X;
/*     */   private static int OFFSET_Y;
/*     */   private ComicWindowModel model;
/*     */   private BButton backButton;
/*     */   private BButton skipButton;
/*     */   private HighlightedButton nextButton;
/*     */   private StartMenuListener menuListener;
/*     */   
/*     */   public ComicWindow(ComicWindowModel model, StartMenuListener menuListener, ResourceManager resourceManager, Localizer localizer) {
/*  47 */     super(BuiUtils.createMergedClassStyleSheets(ComicWindow.class, new BananaResourceProvider(resourceManager)), (BLayoutManager)new AbsoluteLayout());
/*     */     
/*  49 */     this.model = model;
/*  50 */     this.menuListener = menuListener;
/*     */     
/*  52 */     initConstants();
/*     */     
/*  54 */     setLayer(100);
/*  55 */     setLocation(SIZE_WINDOW.x, SIZE_WINDOW.y);
/*  56 */     setSize(SIZE_WINDOW.width, SIZE_WINDOW.height);
/*     */     
/*  58 */     initComponents();
/*  59 */     initListeners();
/*     */   }
/*     */   
/*     */   private void initConstants() {
/*  63 */     SIZE_WINDOW = new Rectangle(0, 0, DisplaySystem.getDisplaySystem().getWidth(), DisplaySystem.getDisplaySystem().getHeight());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  68 */     OFFSET_X = DisplaySystem.getDisplaySystem().getWidth() / 2 - 512;
/*  69 */     OFFSET_Y = DisplaySystem.getDisplaySystem().getHeight() / 2 - 384;
/*     */     
/*  71 */     SIZE_WINDOW_TOP = new Rectangle(OFFSET_X, OFFSET_Y + 68, 1024, 700);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  76 */     int backButtonSize = System.getProperty("tcg.locale").equals("en") ? 170 : 215;
/*     */     
/*  78 */     SIZE_PREV_BUTTON = new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - 81 - 5, OFFSET_Y + 9, 54, 54);
/*     */     
/*  80 */     SIZE_NEXT_BUTTON = new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 + 27 + 5, OFFSET_Y + 9, 54, 54);
/*     */     
/*  82 */     SIZE_BACK_BUTTON = new Rectangle(OFFSET_X, OFFSET_Y, backButtonSize, 69);
/*  83 */     SIZE_SKIP_BUTTON = new Rectangle(OFFSET_X + backButtonSize, OFFSET_Y, backButtonSize, 69);
/*  84 */     SIZE_CONTINUE_BUTTON = new Rectangle(OFFSET_X + 1024 - backButtonSize, OFFSET_Y, backButtonSize, 69);
/*     */   }
/*     */   
/*     */   private void initComponents() {
/*  88 */     this.backButton = new BButton(TcgGame.getLocalizedText("mainmenuwindow.back", new String[0]).toUpperCase());
/*  89 */     this.backButton.setStyleClass("button_back");
/*     */     
/*  91 */     this.skipButton = new BButton(TcgGame.getLocalizedText("startmenu.comic.button.skip", new String[0]).toUpperCase());
/*  92 */     this.skipButton.setStyleClass("button_skip" + (System.getProperty("tcg.locale").equals("no") ? "_no" : ""));
/*     */     
/*  94 */     this.nextButton = new HighlightedButton(TcgGame.getLocalizedText("startmenu.comic.button.next", new String[0]).toUpperCase());
/*  95 */     this.nextButton.setStyleClass("button_continue");
/*     */     
/*  97 */     for (int i = 0; i < this.pageButtons.length; i++) {
/*  98 */       this.pageButtons[i] = new BToggleButton("");
/*  99 */       this.pageButtons[i].setStyleClass("label_toggle");
/* 100 */       int pageLabelSize = 20;
/* 101 */       add((BComponent)this.pageButtons[i], new Rectangle(DisplaySystem.getDisplaySystem().getWidth() / 2 - 7 * pageLabelSize / 2 + i * pageLabelSize, OFFSET_Y + 25, pageLabelSize, pageLabelSize));
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 106 */     add((BComponent)this.backButton, SIZE_BACK_BUTTON);
/* 107 */     add((BComponent)this.skipButton, SIZE_SKIP_BUTTON);
/*     */     
/* 109 */     add((BComponent)this.nextButton, SIZE_CONTINUE_BUTTON);
/*     */     
/* 111 */     int x = 1024;
/* 112 */     int y = 700;
/*     */     
/* 114 */     BContainer top = new BContainer((BLayoutManager)new CenterLayout());
/* 115 */     top.setStyleClass("top_container");
/*     */     
/* 117 */     BLabel comicLabel = new BLabel("");
/* 118 */     BLabel comicTextLabel = new BLabel("");
/*     */     
/* 120 */     top.add((BComponent)comicLabel);
/* 121 */     top.add((BComponent)comicTextLabel);
/*     */     
/* 123 */     add((BComponent)top, SIZE_WINDOW_TOP);
/*     */     
/* 125 */     this.model.setComicTextLabel(comicTextLabel);
/* 126 */     this.model.setComicLabel(comicLabel);
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 130 */     for (BToggleButton pageButton : this.pageButtons) {
/* 131 */       pageButton.addListener((ComponentListener)new ActionListener()
/*     */           {
/*     */             public void actionPerformed(ActionEvent event) {
/* 134 */               for (int i = 0; i < ComicWindow.this.pageButtons.length; i++) {
/* 135 */                 ComicWindow.this.pageButtons[i].setSelected(false);
/* 136 */                 if (pageButton.equals(ComicWindow.this.pageButtons[i])) {
/* 137 */                   pageButton.setSelected(true);
/* 138 */                   ComicWindow.this.model.goToPage(i);
/*     */                 } 
/*     */               } 
/*     */             }
/*     */           });
/*     */     } 
/*     */ 
/*     */     
/* 146 */     this.backButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 149 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 150 */             if (ComicWindow.this.model.getCurrentPage() > 0) {
/* 151 */               ComicWindow.this.showPage(false);
/*     */             } else {
/* 153 */               ComicWindow.this.menuListener.comicBack();
/*     */             } 
/*     */           }
/*     */         });
/*     */     
/* 158 */     this.nextButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 161 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 162 */             ComicWindow.this.showPage(true);
/*     */           }
/*     */         });
/*     */     
/* 166 */     this.skipButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 169 */             TcgUI.getUISoundPlayer().play("ClickForward");
/* 170 */             ComicWindow.this.model.skip();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/* 177 */     super.setVisible(visible);
/* 178 */     if (visible) {
/* 179 */       this.model.reset();
/* 180 */       for (int i = 0; i < this.pageButtons.length; i++) {
/* 181 */         this.pageButtons[i].setSelected(false);
/*     */       }
/*     */       
/* 184 */       this.pageButtons[this.model.getCurrentPage() - 1].setSelected(true);
/* 185 */       this.nextButton.setText(TcgGame.getLocalizedText("startmenu.comic.button.next", new String[0]).toUpperCase());
/*     */     } 
/*     */   }
/*     */   
/*     */   private void showPage(boolean next) {
/* 190 */     if (next) {
/* 191 */       this.model.next();
/*     */     } else {
/* 193 */       this.model.prev();
/*     */     } 
/* 195 */     boolean last = (this.model.getCurrentPage() == this.model.getTotalPages());
/*     */     
/* 197 */     this.nextButton.setHighlighted(last);
/* 198 */     for (int i = 0; i < this.pageButtons.length; i++) {
/* 199 */       this.pageButtons[i].setSelected(false);
/*     */     }
/* 201 */     this.pageButtons[this.model.getCurrentPage() - 1].setSelected(true);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\comic\ComicWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */