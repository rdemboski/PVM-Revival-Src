/*     */ package com.funcom.tcg.client.ui.hud;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.HighlightedButton;
/*     */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TutorialArrowWindow
/*     */   extends BWindow
/*     */   implements PartiallyNotInteractive
/*     */ {
/*     */   private HighlightedButton arrowUpButton;
/*     */   private HighlightedButton arrowDownButton;
/*     */   private HighlightedButton arrowLeftButton;
/*     */   private HighlightedButton arrowRightButton;
/*     */   private HighlightedButton arrowTopRightButton;
/*     */   private BLabel arrowInfoLabel;
/*  31 */   private final Rectangle INFO_BOTTOM = new Rectangle(0, 0, 200, 50);
/*  32 */   private final Rectangle INFO_TOP = new Rectangle(0, 200, 200, 50);
/*  33 */   private final Rectangle INFO_LEFT = new Rectangle(0, 0, 50, 250);
/*  34 */   private final Rectangle INFO_RIGHT = new Rectangle(200, 0, 50, 250);
/*     */   
/*     */   private ResourceManager resourceManager;
/*     */   private TutorialArrowDirection direction;
/*     */   public static final int ARROW_WIDTH = 200;
/*     */   public static final int ARROW_HEIGHT = 250;
/*     */   
/*     */   public TutorialArrowWindow(ResourceManager resourceManager, int x, int y) {
/*  42 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  43 */     this._style = BuiUtils.createMergedClassStyleSheets(TutorialArrowWindow.class, new BananaResourceProvider(resourceManager));
/*     */     
/*  45 */     this.resourceManager = resourceManager;
/*  46 */     this.direction = this.direction;
/*  47 */     initComponents();
/*  48 */     setLayer(101);
/*  49 */     setBounds(x, y, 200, 250);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initComponents() {
/*  54 */     this.arrowUpButton = new HighlightedButton();
/*  55 */     this.arrowUpButton.setStyleClass("button_arrow_up");
/*  56 */     this.arrowUpButton.setHighlighted(true);
/*  57 */     this.arrowUpButton.setClickthrough(true);
/*     */     
/*  59 */     this.arrowDownButton = new HighlightedButton();
/*  60 */     this.arrowDownButton.setStyleClass("button_arrow_down");
/*  61 */     this.arrowDownButton.setHighlighted(true);
/*  62 */     this.arrowDownButton.setClickthrough(true);
/*     */     
/*  64 */     this.arrowLeftButton = new HighlightedButton();
/*  65 */     this.arrowLeftButton.setStyleClass("button_arrow_left");
/*  66 */     this.arrowLeftButton.setHighlighted(true);
/*  67 */     this.arrowLeftButton.setClickthrough(true);
/*     */     
/*  69 */     this.arrowRightButton = new HighlightedButton();
/*  70 */     this.arrowRightButton.setStyleClass("button_arrow_right");
/*  71 */     this.arrowRightButton.setHighlighted(true);
/*  72 */     this.arrowRightButton.setClickthrough(true);
/*     */     
/*  74 */     this.arrowTopRightButton = new HighlightedButton();
/*  75 */     this.arrowTopRightButton.setStyleClass("button_arrow_top_right");
/*  76 */     this.arrowTopRightButton.setHighlighted(true);
/*  77 */     this.arrowTopRightButton.setClickthrough(true);
/*     */     
/*  79 */     this.arrowInfoLabel = new BLabel("");
/*     */     
/*  81 */     add((BComponent)this.arrowUpButton, new Rectangle(25, 50, 150, 200));
/*  82 */     add((BComponent)this.arrowDownButton, new Rectangle(25, 0, 150, 200));
/*  83 */     add((BComponent)this.arrowLeftButton, new Rectangle(0, 25, 150, 200));
/*  84 */     add((BComponent)this.arrowRightButton, new Rectangle(50, 25, 150, 200));
/*  85 */     add((BComponent)this.arrowTopRightButton, new Rectangle(25, 50, 150, 200));
/*  86 */     add((BComponent)this.arrowInfoLabel, this.INFO_TOP);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setVisible(boolean visible) {
/*  91 */     super.setVisible(visible);
/*  92 */     this.arrowUpButton.setVisible(false);
/*  93 */     this.arrowDownButton.setVisible(false);
/*  94 */     this.arrowLeftButton.setVisible(false);
/*  95 */     this.arrowRightButton.setVisible(false);
/*  96 */     this.arrowTopRightButton.setVisible(false);
/*  97 */     if (!visible) {
/*  98 */       this.arrowInfoLabel.setText("");
/*     */     }
/*     */   }
/*     */   
/*     */   public void updateInfoText(String text) {
/* 103 */     this.arrowInfoLabel.setText(text);
/*     */   }
/*     */   
/*     */   public String getInfoText() {
/* 107 */     return this.arrowInfoLabel.getText();
/*     */   }
/*     */   
/*     */   public void setDirection(TutorialArrowDirection direction) {
/* 111 */     this.arrowUpButton.setVisible(false);
/* 112 */     this.arrowDownButton.setVisible(false);
/* 113 */     this.arrowLeftButton.setVisible(false);
/* 114 */     this.arrowRightButton.setVisible(false);
/* 115 */     this.arrowTopRightButton.setVisible(false);
/*     */     
/* 117 */     switch (direction) {
/*     */       case UP:
/* 119 */         this.arrowUpButton.setVisible(true);
/* 120 */         this.arrowInfoLabel.setParent(null);
/* 121 */         add((BComponent)this.arrowInfoLabel, this.INFO_BOTTOM);
/*     */         break;
/*     */       case DOWN:
/* 124 */         this.arrowDownButton.setVisible(true);
/* 125 */         this.arrowInfoLabel.setParent(null);
/* 126 */         add((BComponent)this.arrowInfoLabel, this.INFO_TOP);
/*     */         break;
/*     */       case LEFT:
/* 129 */         this.arrowLeftButton.setVisible(true);
/* 130 */         this.arrowInfoLabel.setParent(null);
/* 131 */         add((BComponent)this.arrowInfoLabel, this.INFO_RIGHT);
/*     */         break;
/*     */       case RIGHT:
/* 134 */         this.arrowRightButton.setVisible(true);
/* 135 */         this.arrowInfoLabel.setParent(null);
/* 136 */         add((BComponent)this.arrowInfoLabel, this.INFO_LEFT);
/*     */         break;
/*     */       case TOP_RIGHT:
/* 139 */         this.arrowTopRightButton.setVisible(true);
/* 140 */         this.arrowInfoLabel.setParent(null);
/* 141 */         add((BComponent)this.arrowInfoLabel, this.INFO_BOTTOM);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 150 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud\TutorialArrowWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */