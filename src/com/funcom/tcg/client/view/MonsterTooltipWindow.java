/*     */ package com.funcom.tcg.client.view;
/*     */ 
/*     */ import com.funcom.commons.jme.bui.PartiallyNotInteractive;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.utils.BananaResourceProvider;
/*     */ import com.funcom.tcg.client.model.ui.RpgDataProvider;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.BuiUtils;
/*     */ import com.jme.system.DisplaySystem;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.layout.AbsoluteLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Dimension;
/*     */ import com.jmex.bui.util.Point;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public class MonsterTooltipWindow
/*     */   extends BWindow implements RpgDataProvider.DataListener, PartiallyNotInteractive {
/*     */   private static final String STYLE_LEVEL_LABEL = "level-label";
/*  26 */   private static final Dimension WINDOW_SIZE = new Dimension(335, 54); private static final String STYLE_NAME_LABEL = "name-label"; private static final String STYLE_BACKGROUND_LABEL = "background-label";
/*  27 */   private static final Rectangle CON_BACKGROUND_LABEL = new Rectangle(0, 11, 277, 43);
/*  28 */   private static final Rectangle CON_HEALTH_PROGRESS = new Rectangle(13, 23, 250, 17);
/*  29 */   private static final Rectangle CON_NAME_LABEL = new Rectangle(13, 24, 250, 17);
/*  30 */   private static final Rectangle CON_LEVEL_LABEL = new Rectangle(290, 18, 32, 30);
/*  31 */   private static final Rectangle CON_TYPE_LABEL = new Rectangle(277, 4, 58, 56);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  40 */   private static final Comparable<Integer> LEVEL_COMPARATOR = new Comparable<Integer>() {
/*     */       public int compareTo(Integer o) {
/*  42 */         int levelDiff = o.intValue() - MainGameState.getPlayerModel().getStatSum(Short.valueOf((short)20)).intValue();
/*  43 */         if (Math.abs(levelDiff) < 5)
/*  44 */           return 0; 
/*  45 */         return (int)Math.signum(levelDiff);
/*     */       }
/*     */     };
/*     */   
/*     */   private RpgDataProvider rpgDataProvider;
/*     */   private BLabel nameLabel;
/*     */   private BProgressBar healthProgress;
/*     */   private BLabel levelLabel;
/*     */   private BLabel typeLabel;
/*  54 */   private int levelDiff = -2;
/*     */   
/*     */   private BImage playerProgressImage;
/*     */   
/*     */   private BImage monsterProgressImage;
/*     */   private BBackground bossTypeLevelBelow;
/*     */   private BBackground bossTypeLevelAbove;
/*     */   private BBackground bossTypeLevelSame;
/*     */   private BBackground minibossTypeLevelBelow;
/*     */   private BBackground minibossTypeLevelAbove;
/*     */   private BBackground minibossTypeLevelSame;
/*     */   private BBackground mobTypeLevelBelow;
/*     */   private BBackground mobTypeLevelAbove;
/*     */   private BBackground mobTypeLevelSame;
/*     */   private BBackground playerType;
/*     */   
/*     */   public MonsterTooltipWindow(RpgDataProvider rpgDataProvider, ResourceManager resourceManager) {
/*  71 */     super(null, (BLayoutManager)new AbsoluteLayout());
/*  72 */     BananaResourceProvider buiResourceProvider = new BananaResourceProvider(resourceManager);
/*  73 */     this._style = BuiUtils.createMergedClassStyleSheets(getClass(), buiResourceProvider);
/*  74 */     if (rpgDataProvider == null)
/*  75 */       throw new IllegalArgumentException("rpgDataProvider = null"); 
/*  76 */     this.rpgDataProvider = rpgDataProvider;
/*  77 */     this.rpgDataProvider.addListener(this);
/*  78 */     setStyleClass("monster-window");
/*     */     
/*  80 */     initWindow();
/*  81 */     initBackground();
/*  82 */     initHealthProgress();
/*  83 */     initNameLabel();
/*  84 */     initTypeLabel();
/*  85 */     initLevelLabel();
/*  86 */     setVisible(false);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void wasRemoved() {
/*  91 */     super.wasRemoved();
/*  92 */     this.rpgDataProvider.removeListener(this);
/*     */   }
/*     */   
/*     */   private void initBackground() {
/*  96 */     BLabel background = new BLabel("");
/*  97 */     background.setStyleClass("background-label");
/*  98 */     add((BComponent)background, CON_BACKGROUND_LABEL);
/*     */   }
/*     */   
/*     */   private void initNameLabel() {
/* 102 */     this.nameLabel = new BLabel("");
/* 103 */     this.nameLabel.setStyleClass("name-label");
/* 104 */     add((BComponent)this.nameLabel, CON_NAME_LABEL);
/*     */   }
/*     */   
/*     */   private void initHealthProgress() {
/* 108 */     this.healthProgress = new BProgressBar(BProgressBar.Direction.PROGRESSDIR_EAST);
/*     */     
/* 110 */     this.healthProgress.setProgress(0.0F);
/* 111 */     add((BComponent)this.healthProgress, CON_HEALTH_PROGRESS);
/*     */   }
/*     */   
/*     */   private void initLevelLabel() {
/* 115 */     this.levelLabel = new BLabel("");
/* 116 */     this.levelLabel.setStyleClass("level-label");
/* 117 */     add((BComponent)this.levelLabel, CON_LEVEL_LABEL);
/*     */   }
/*     */   
/*     */   private void initTypeLabel() {
/* 121 */     this.typeLabel = new BLabel("");
/* 122 */     add((BComponent)this.typeLabel, CON_TYPE_LABEL);
/*     */   }
/*     */   
/*     */   private void initWindow() {
/* 126 */     setSize(WINDOW_SIZE.width, WINDOW_SIZE.height);
/* 127 */     Point location = centerTop();
/* 128 */     setLocation(location.x, location.y);
/*     */   }
/*     */   
/*     */   private Point centerTop() {
/* 132 */     int displayWidth = DisplaySystem.getDisplaySystem().getWidth();
/* 133 */     int displayHeight = DisplaySystem.getDisplaySystem().getHeight();
/* 134 */     return new Point((displayWidth - getWidth()) / 2, displayHeight - getHeight() - 60);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void dismiss() {
/* 141 */     super.dismiss();
/* 142 */     this.rpgDataProvider.removeListener(this);
/*     */   }
/*     */   
/*     */   public void dataChanged(boolean containsObject, boolean isMonster, boolean isPlayer) {
/* 146 */     if (containsObject && (isMonster || isPlayer)) {
/* 147 */       updateLevel();
/* 148 */       updateProgress();
/* 149 */       updateName();
/* 150 */       updateHealth();
/*     */       
/* 152 */       setVisible(true);
/*     */     } else {
/* 154 */       setVisible(false);
/*     */     } 
/*     */   }
/*     */   private void updateName() {
/* 158 */     if (this.rpgDataProvider.nameChanged()) {
/* 159 */       String name = this.rpgDataProvider.getName();
/* 160 */       if (name.length() > 30)
/* 161 */         name = name.substring(0, 30); 
/* 162 */       this.nameLabel.setText(name);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateHealth() {
/* 167 */     if (this.rpgDataProvider.healthFractionChanged())
/* 168 */       this.healthProgress.setProgress(this.rpgDataProvider.getHealthFraction()); 
/*     */   }
/*     */   
/*     */   private void updateLevel() {
/* 172 */     if (this.rpgDataProvider.levelChanged())
/* 173 */       this.levelLabel.setText(String.valueOf(this.rpgDataProvider.getLevel())); 
/*     */   }
/*     */   
/*     */   private void updateProgress() {
/* 177 */     boolean typeChanged = this.rpgDataProvider.rpgTypeChanged();
/* 178 */     if (typeChanged) {
/* 179 */       this.healthProgress.removeAllProgressImages();
/* 180 */       if (this.rpgDataProvider.getRpgType().equals(RpgDataProvider.RpgType.PLAYER)) {
/* 181 */         this.healthProgress.addProgressImage(this.playerProgressImage);
/*     */       } else {
/* 183 */         this.healthProgress.addProgressImage(this.monsterProgressImage);
/*     */       } 
/*     */     } 
/*     */   }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void updateTypeBoss(int levelDiff) {
/* 206 */     if (levelDiff == -1) {
/* 207 */       this.typeLabel.setBackground(0, this.bossTypeLevelBelow);
/* 208 */     } else if (levelDiff == 1) {
/* 209 */       this.typeLabel.setBackground(0, this.bossTypeLevelAbove);
/*     */     } else {
/* 211 */       this.typeLabel.setBackground(0, this.bossTypeLevelSame);
/*     */     } 
/*     */   }
/*     */   private void updateTypeMiniboss(int levelDiff) {
/* 215 */     if (levelDiff == -1) {
/* 216 */       this.typeLabel.setBackground(0, this.minibossTypeLevelBelow);
/* 217 */     } else if (levelDiff == 1) {
/* 218 */       this.typeLabel.setBackground(0, this.minibossTypeLevelAbove);
/*     */     } else {
/* 220 */       this.typeLabel.setBackground(0, this.minibossTypeLevelSame);
/*     */     } 
/*     */   }
/*     */   private void updateTypeMob(int levelDiff) {
/* 224 */     if (levelDiff == -1) {
/* 225 */       this.typeLabel.setBackground(0, this.mobTypeLevelBelow);
/* 226 */     } else if (levelDiff == 1) {
/* 227 */       this.typeLabel.setBackground(0, this.mobTypeLevelAbove);
/*     */     } else {
/* 229 */       this.typeLabel.setBackground(0, this.mobTypeLevelSame);
/*     */     } 
/*     */   }
/*     */   private void updateTypePlayer() {
/* 233 */     this.typeLabel.setBackground(0, this.playerType);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void configureStyle(BStyleSheet style) {
/* 238 */     super.configureStyle(style);
/*     */     
/* 240 */     if (this.playerProgressImage == null) {
/* 241 */       this.playerProgressImage = (BImage)style.findProperty((BComponent)this, null, "health-progress-player", true);
/*     */     }
/*     */     
/* 244 */     if (this.monsterProgressImage == null) {
/* 245 */       this.monsterProgressImage = (BImage)style.findProperty((BComponent)this, null, "health-progress-monster", true);
/*     */     }
/*     */     
/* 248 */     if (this.bossTypeLevelBelow == null) {
/* 249 */       this.bossTypeLevelBelow = (BBackground)style.findProperty((BComponent)this, null, "boss-lvl-below", true);
/*     */     }
/*     */     
/* 252 */     if (this.bossTypeLevelAbove == null) {
/* 253 */       this.bossTypeLevelAbove = (BBackground)style.findProperty((BComponent)this, null, "boss-lvl-above", true);
/*     */     }
/*     */     
/* 256 */     if (this.bossTypeLevelSame == null) {
/* 257 */       this.bossTypeLevelSame = (BBackground)style.findProperty((BComponent)this, null, "boss-lvl-same", true);
/*     */     }
/*     */     
/* 260 */     if (this.minibossTypeLevelBelow == null) {
/* 261 */       this.minibossTypeLevelBelow = (BBackground)style.findProperty((BComponent)this, null, "miniboss-lvl-below", true);
/*     */     }
/*     */     
/* 264 */     if (this.minibossTypeLevelAbove == null) {
/* 265 */       this.minibossTypeLevelAbove = (BBackground)style.findProperty((BComponent)this, null, "miniboss-lvl-above", true);
/*     */     }
/*     */     
/* 268 */     if (this.minibossTypeLevelSame == null) {
/* 269 */       this.minibossTypeLevelSame = (BBackground)style.findProperty((BComponent)this, null, "miniboss-lvl-same", true);
/*     */     }
/*     */     
/* 272 */     if (this.mobTypeLevelBelow == null) {
/* 273 */       this.mobTypeLevelBelow = (BBackground)style.findProperty((BComponent)this, null, "mob-lvl-below", true);
/*     */     }
/*     */     
/* 276 */     if (this.mobTypeLevelAbove == null) {
/* 277 */       this.mobTypeLevelAbove = (BBackground)style.findProperty((BComponent)this, null, "mob-lvl-above", true);
/*     */     }
/*     */     
/* 280 */     if (this.mobTypeLevelSame == null) {
/* 281 */       this.mobTypeLevelSame = (BBackground)style.findProperty((BComponent)this, null, "mob-lvl-same", true);
/*     */     }
/*     */     
/* 284 */     if (this.playerType == null) {
/* 285 */       this.playerType = (BBackground)style.findProperty((BComponent)this, null, "player-image", true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isHit() {
/* 291 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\MonsterTooltipWindow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */