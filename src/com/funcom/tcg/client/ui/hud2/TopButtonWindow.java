/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.IrregularWindow;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BToggleButton;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TopButtonWindow
/*     */   extends IrregularWindow
/*     */ {
/*     */   private static final int BUTTON_X = 50;
/*     */   private static final int BUTTON_Y = 50;
/*     */   private static final String STYLE_WINDOW = "window.topbutton";
/*     */   private static final String STYLE_HOME_BUTTON = "button.home";
/*     */   private static final String STYLE_PAUSE_BUTTON = "button.pause";
/*  38 */   private static final Rectangle SIZE_HOME_BUTTON = new Rectangle(0, 0, 50, 50);
/*     */   
/*  40 */   private static final Rectangle SIZE_PAUSE_BUTTON = new Rectangle(60, 0, 50, 50);
/*     */   
/*     */   private ResourceManager resourceManager;
/*     */   
/*     */   private HudModel hudModel;
/*     */   private BButton homeButton;
/*     */   private BToggleButton pauseButton;
/*     */   private AbstractHudModel.ChangeListenerAdapter listener;
/*     */   
/*     */   public TopButtonWindow(HudModel hudModel, ResourceManager resourceManager, TCGToolTipManager toolTipManager) {
/*  50 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  51 */     this._style = BuiUtils.createMergedClassStyleSheets(TopButtonWindow.class, new BananaResourceProvider(resourceManager));
/*     */     
/*  53 */     this.resourceManager = resourceManager;
/*  54 */     this.hudModel = hudModel;
/*     */ 
/*     */ 
/*     */     
/*  58 */     setStyleClass("window.topbutton");
/*     */     
/*  60 */     this.listener = new AbstractHudModel.ChangeListenerAdapter()
/*     */       {
/*     */         public void townPortalVisibilityChanged(boolean visible) {
/*  63 */           TopButtonWindow.this.homeButton.setEnabled(visible);
/*     */         }
/*     */       };
/*  66 */     hudModel.addChangeListener(this.listener);
/*     */     
/*  68 */     initComponents(toolTipManager);
/*  69 */     initListeners();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/*  74 */     this.hudModel.removeChangeListener(this.listener);
/*  75 */     super.dismiss();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initComponents(TCGToolTipManager toolTipManager) {
/*  84 */     this.homeButton = new BButton("");
/*  85 */     this.homeButton.setStyleClass("button.home");
/*  86 */     add((BComponent)this.homeButton, SIZE_HOME_BUTTON);
/*  87 */     this.homeButton.setEnabled(this.hudModel.isTownPortalEnabled());
/*     */     
/*  89 */     this.pauseButton = new BToggleButton("");
/*  90 */     this.pauseButton.setStyleClass("button.pause");
/*  91 */     add((BComponent)this.pauseButton, SIZE_PAUSE_BUTTON);
/*  92 */     this.pauseButton.setEnabled(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void initListeners() {
/*  99 */     this.homeButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 102 */             TopButtonWindow.this.hudModel.townportalAction();
/*     */           }
/*     */         });
/* 105 */     this.pauseButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent actionEvent) {
/* 108 */             if (MainGameState.getPauseModel().isPaused()) {
/* 109 */               MainGameState.getPauseModel().reset();
/*     */             } else {
/* 111 */               MainGameState.getPauseModel().activatePause();
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 120 */     return super.isHit();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHit(int mx, int my) {
/* 125 */     return super.isHit(mx, my);
/*     */   }
/*     */   
/*     */   public BToggleButton getPauseButton() {
/* 129 */     return this.pauseButton;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\TopButtonWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */