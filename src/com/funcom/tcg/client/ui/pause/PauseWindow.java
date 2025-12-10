/*     */ package com.funcom.tcg.client.ui.pause;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.IrregularWindow;
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BButton;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
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
/*     */ public class PauseWindow
/*     */   extends IrregularWindow
/*     */ {
/*     */   public static final int PROGRESS_WIDTH = 20;
/*     */   public static final int PROGRESS_HEIGHT = 100;
/*     */   public static final int PROGRESS_FRAME_WIDTH = 30;
/*     */   public static final int PROGRESS_FRAME_HEIGHT = 112;
/*     */   public static final int LAYER_INDEX = -1;
/*     */   public static final String STYLE_CLASS = "pause-window";
/*     */   private static final String PROGRESS_BAR_LABEL_STYLE = "progressbar-label";
/*     */   private static final String EVENT_PROGRESS_LABEL_STYLE = "eventProgress-label";
/*     */   private static final String PROGRESS_FRAME_STYLE = "pause-frame";
/*     */   private static final String PROGRESS_BACKGROUND_STYLE = "pause-background";
/*     */   private boolean instant = false;
/*     */   public static int WINDOW_WIDTH;
/*     */   public static int WINDOW_HEIGHT;
/*     */   public static int PROGRESS_X;
/*     */   public static int PROGRESS_Y;
/*     */   public static int PROGRESS_FRAME_X;
/*     */   public static int PROGRESS_FRAME_Y;
/*     */   private static Rectangle[] PROGRESSBAR_CONSTRAINTS;
/*     */   private static Rectangle[] PROGRESSBAR_BG_CONSTRAINTS;
/*     */   private static Rectangle EVENTLABEL_CONSTRAINTS;
/*     */   private static Rectangle TOGGLE_CONSTRAINTS;
/*     */   private PauseModel pauseModel;
/*     */   private ChangeListenerImpl changeListener;
/*     */   private BProgressBar[] progressBars;
/*     */   private BLabel[] progressBarFrames;
/*     */   private BLabel[] progressBarBackgrounds;
/*     */   private BLabel overlayShadow;
/*     */   private BLabel eventProgress;
/*     */   private BButton toggleButton;
/*     */   
/*     */   public PauseWindow(ResourceManager resourceManager, PauseModel pauseModel, boolean instant) {
/*  65 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  66 */     initConstants();
/*  67 */     ResourceManager resourceManager1 = resourceManager;
/*  68 */     this.pauseModel = pauseModel;
/*  69 */     this.instant = instant;
/*     */     
/*  71 */     setLayer(-1);
/*     */     
/*  73 */     this.changeListener = new ChangeListenerImpl();
/*  74 */     this.pauseModel.addChangeListener(this.changeListener);
/*     */     
/*  76 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  77 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*     */     
/*  79 */     setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
/*     */ 
/*     */     
/*  82 */     DisplaySystem displaySystem = DisplaySystem.getDisplaySystem();
/*     */     
/*  84 */     setLocation(0, 0);
/*     */     
/*  86 */     initComponents();
/*  87 */     initListeners();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHit(int mx, int my) {
/*  94 */     if (this.progressBars[0].getAbsoluteX() < mx && this.progressBars[1].getAbsoluteX() + this.progressBars[0].getWidth() > mx && this.progressBars[0].getAbsoluteY() < my && this.progressBars[0].getAbsoluteY() + this.progressBars[0].getHeight() > my)
/*     */     {
/*  96 */       return true;
/*     */     }
/*     */ 
/*     */     
/* 100 */     return false;
/*     */   }
/*     */   
/*     */   private void initConstants() {
/* 104 */     WINDOW_WIDTH = DisplaySystem.getDisplaySystem().getWindowWidth();
/* 105 */     WINDOW_HEIGHT = DisplaySystem.getDisplaySystem().getWindowHeight();
/*     */     
/* 107 */     PROGRESS_X = WINDOW_WIDTH / 2 - 10 - 20;
/* 108 */     PROGRESS_Y = WINDOW_HEIGHT / 2 - 50;
/* 109 */     PROGRESS_FRAME_X = PROGRESS_X - 5;
/* 110 */     PROGRESS_FRAME_Y = PROGRESS_Y - 5;
/*     */     
/* 112 */     PROGRESSBAR_CONSTRAINTS = new Rectangle[2];
/* 113 */     PROGRESSBAR_CONSTRAINTS[0] = new Rectangle(PROGRESS_X, PROGRESS_Y, 20, 100);
/* 114 */     PROGRESSBAR_CONSTRAINTS[1] = new Rectangle(PROGRESS_X + 40, PROGRESS_Y, 20, 100);
/*     */ 
/*     */     
/* 117 */     PROGRESSBAR_BG_CONSTRAINTS = new Rectangle[2];
/* 118 */     PROGRESSBAR_BG_CONSTRAINTS[0] = new Rectangle(PROGRESS_FRAME_X, PROGRESS_FRAME_Y, 30, 112);
/*     */     
/* 120 */     PROGRESSBAR_BG_CONSTRAINTS[1] = new Rectangle(PROGRESS_FRAME_X + 30 + 10, PROGRESS_FRAME_Y, 30, 112);
/*     */ 
/*     */     
/* 123 */     TOGGLE_CONSTRAINTS = new Rectangle(PROGRESS_FRAME_X, PROGRESS_FRAME_Y, 70, 112);
/*     */ 
/*     */     
/* 126 */     EVENTLABEL_CONSTRAINTS = new Rectangle(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2 + 100, 300, 50);
/*     */   }
/*     */ 
/*     */   
/*     */   private void initComponents() {
/* 131 */     this.overlayShadow = new BLabel("");
/* 132 */     this.overlayShadow.setStyleClass("overlay-shadow");
/* 133 */     add((BComponent)this.overlayShadow, new Rectangle(0, 0, WINDOW_WIDTH, WINDOW_HEIGHT));
/* 134 */     this.overlayShadow.setVisible(false);
/*     */     
/* 136 */     this.progressBarBackgrounds = new BLabel[2];
/* 137 */     this.progressBarFrames = new BLabel[2];
/* 138 */     this.progressBars = new BProgressBar[2];
/* 139 */     for (int i = 0; i < this.progressBars.length; i++) {
/* 140 */       this.progressBarBackgrounds[i] = new BLabel("");
/* 141 */       this.progressBarBackgrounds[i].setStyleClass("pause-background");
/* 142 */       add((BComponent)this.progressBarBackgrounds[i], PROGRESSBAR_CONSTRAINTS[i]);
/*     */       
/* 144 */       this.progressBars[i] = new BProgressBar(BProgressBar.Direction.PROGRESSDIR_NORTH);
/* 145 */       this.progressBars[i].setProgress(0.0F);
/* 146 */       this.progressBars[i].setStyleClass("progressbar-label");
/* 147 */       add((BComponent)this.progressBars[i], PROGRESSBAR_CONSTRAINTS[i]);
/*     */       
/* 149 */       this.progressBarFrames[i] = new BLabel("");
/* 150 */       this.progressBarFrames[i].setStyleClass("pause-frame");
/* 151 */       add((BComponent)this.progressBarFrames[i], PROGRESSBAR_BG_CONSTRAINTS[i]);
/*     */     } 
/*     */     
/* 154 */     this.toggleButton = new BButton("");
/* 155 */     this.toggleButton.setStyleClass("");
/*     */     
/* 157 */     add((BComponent)this.toggleButton, TOGGLE_CONSTRAINTS);
/*     */   }
/*     */   
/*     */   private void initListeners() {
/* 161 */     this.toggleButton.addListener((ComponentListener)new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 164 */             MainGameState.getPauseModel().reset();
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private class ChangeListenerImpl
/*     */     implements PauseModel.ChangeListener
/*     */   {
/*     */     private static final float PROGRESS_UPDATE_RATE = 100.0F;
/* 174 */     long lastUpdate = 0L;
/*     */ 
/*     */     
/*     */     public void progressBarUpdate() {
/* 178 */       if (PauseWindow.this.progressBars[0].getProgress() < 1.0F && (this.lastUpdate == 0L || (float)(GlobalTime.getInstance().getCurrentTime() - this.lastUpdate) >= 100.0F)) {
/*     */         
/* 180 */         this.lastUpdate = GlobalTime.getInstance().getCurrentTime();
/* 181 */         float progress = PauseWindow.this.progressBars[0].getProgress() + 100.0F / (float)TcgGame.getRpgLoader().getConfigPauseTimer();
/*     */         
/* 183 */         if (progress <= 1.0F) {
/* 184 */           for (BProgressBar progressBar : PauseWindow.this.progressBars) {
/* 185 */             progressBar.setProgress(progress);
/*     */           }
/*     */         }
/* 188 */         PauseWindow.this.overlayShadow.setVisible(false);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public void completeProgressBar() {
/* 194 */       for (int i = 0; i < PauseWindow.this.progressBars.length; i++) {
/* 195 */         PauseWindow.this.progressBars[i].setProgress(1.0F);
/* 196 */         PauseWindow.this.progressBars[i].setVisible(!PauseWindow.this.instant);
/* 197 */         PauseWindow.this.progressBarBackgrounds[i].setVisible(!PauseWindow.this.instant);
/* 198 */         PauseWindow.this.progressBarFrames[i].setVisible(!PauseWindow.this.instant);
/*     */       } 
/* 200 */       PauseWindow.this.overlayShadow.setVisible(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public void close() {
/* 205 */       PauseWindow.this.dismiss();
/*     */     }
/*     */     
/*     */     private ChangeListenerImpl() {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pause\PauseWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */