/*     */ package com.funcom.tcg.client.ui.hud2;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.IrregularWindow;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BActiveProgressBar;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Point;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ExperienceBarWindow
/*     */   extends IrregularWindow
/*     */ {
/*     */   private static final String STYLE_BACKGROUND = "xp-background";
/*     */   private static final String STYLE_WINDOW = "experience-bar";
/*     */   private static final String STYLE_STAR_LABEL = "level-label";
/*     */   private static final String STYLE_PROGRESS = "xp-progress";
/*     */   private HudModel hudModel;
/*     */   private BActiveProgressBar xpProgress;
/*     */   private BLabel starLabel;
/*     */   private Rectangle sizeXpProgress;
/*     */   private Rectangle sizeXpOverlay;
/*     */   private Rectangle sizeStarLabel;
/*     */   private Point windowLocation;
/*     */   private BLabel overlay;
/*     */   private AbstractHudModel.ChangeListenerAdapter listener;
/*     */   
/*     */   public ExperienceBarWindow(HudModel hudModel, ResourceManager resourceManager) {
/*  42 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  43 */     this._style = BuiUtils.createMergedClassStyleSheets(ExperienceBarWindow.class, new BananaResourceProvider(resourceManager));
/*     */     
/*  45 */     this.hudModel = hudModel;
/*  46 */     Dimension windowSize = new Dimension(DisplaySystem.getDisplaySystem().getWidth() - 5, 93);
/*     */ 
/*     */     
/*  49 */     this.windowLocation = new Point(0, DisplaySystem.getDisplaySystem().getHeight() - windowSize.height);
/*     */ 
/*     */ 
/*     */     
/*  53 */     int progressX = 85;
/*  54 */     int progressY = 49;
/*  55 */     int progressWidth = windowSize.width - progressX - 12;
/*  56 */     this.sizeXpProgress = new Rectangle(progressX, progressY, progressWidth, 26);
/*  57 */     this.sizeXpOverlay = new Rectangle(progressX - 12, progressY - 12, progressWidth + 24, 50);
/*  58 */     this.sizeStarLabel = new Rectangle(0, 0, 87, 92);
/*     */     
/*  60 */     setStyleClass("experience-bar");
/*  61 */     setSize(windowSize.width, windowSize.height);
/*  62 */     setLocation(this.windowLocation.x, this.windowLocation.y);
/*     */     
/*  64 */     this.listener = new AbstractHudModel.ChangeListenerAdapter()
/*     */       {
/*     */         public void xpChanged(float fraction) {
/*  67 */           ExperienceBarWindow.this.starLabel.setTooltipText(TcgGame.getLocalizedText("tooltip.maingui.playerlevel", new String[0]) + " - " + (int)(fraction * 100.0D) + "%");
/*     */ 
/*     */           
/*  70 */           if (fraction < ExperienceBarWindow.this.xpProgress.getProgressInstant()) {
/*  71 */             ExperienceBarWindow.this.xpProgress.setProgressInstant(fraction);
/*     */           } else {
/*  73 */             ExperienceBarWindow.this.xpProgress.setProgress(fraction);
/*     */           } 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*  79 */           ExperienceBarWindow.this.hudModel.triggerXPParticles();
/*     */         }
/*     */ 
/*     */         
/*     */         public void levelChanged(int level) {
/*  84 */           ExperienceBarWindow.this.starLabel.setText(String.valueOf(level));
/*     */         }
/*     */       };
/*  87 */     hudModel.addChangeListener(this.listener);
/*     */     
/*  89 */     xpBackground();
/*  90 */     xpProgress();
/*  91 */     xpLabel();
/*     */   }
/*     */   
/*     */   private void xpLabel() {
/*  95 */     this.starLabel = new BLabel(String.valueOf(this.hudModel.getCurrentLevel()));
/*  96 */     this.starLabel.setStyleClass("level-label");
/*  97 */     this.starLabel.setTooltipText(TcgGame.getLocalizedText("tooltip.maingui.playerlevel", new String[0]) + " - " + (int)(this.hudModel.getCurrentXpFraction() * 100.0F) + "%");
/*  98 */     add((BComponent)this.starLabel, this.sizeStarLabel);
/*  99 */     Point point = new Point(this.windowLocation.x + 40, this.windowLocation.y + 40);
/* 100 */     this.hudModel.addXPParticle(point);
/*     */   }
/*     */ 
/*     */   
/*     */   private void xpBackground() {
/* 105 */     this.overlay = new BLabel("");
/* 106 */     this.overlay.setStyleClass("xp-background");
/* 107 */     add((BComponent)this.overlay, this.sizeXpOverlay);
/*     */   }
/*     */   
/*     */   private void xpProgress() {
/* 111 */     this.xpProgress = new BActiveProgressBar(BProgressBar.Direction.PROGRESSDIR_EAST, 300L, 0.03F);
/* 112 */     this.xpProgress.setStyleClass("xp-progress");
/* 113 */     this.xpProgress.setProgressInstant(this.hudModel.getCurrentXpFraction());
/* 114 */     add((BComponent)this.xpProgress, this.sizeXpProgress);
/*     */   }
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 119 */     this.hudModel.removeChangeListener(this.listener);
/* 120 */     super.dismiss();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHit(int mx, int my) {
/* 125 */     return (super.isHit(mx, my) && (contains(mx, my, this.overlay) || contains(mx, my, this.starLabel)));
/*     */   }
/*     */   
/*     */   private boolean contains(int mx, int my, BLabel label) {
/* 129 */     return (label.getAbsoluteX() <= mx && label.getAbsoluteY() <= my && label.getAbsoluteX() + label.getWidth() >= mx && label.getAbsoluteY() + label.getHeight() >= my);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\ExperienceBarWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */