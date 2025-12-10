/*     */ package com.funcom.tcg.client.ui.pets3;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.commons.utils.ClientUtils;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.ui.Localizer;
/*     */ import com.funcom.tcg.client.ui.SelectableButtonContainer;
/*     */ import com.funcom.tcg.client.ui.SelectableButtonInter;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.jmex.bui.BClickthroughLabel;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.BLabel;
/*     */ import com.jmex.bui.BProgressBar;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.background.BBackground;
/*     */ import com.jmex.bui.background.ImageBackground;
/*     */ import com.jmex.bui.enumeratedConstants.ImageBackgroundMode;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.layout.AbsoluteButChangeableLayout;
/*     */ import com.jmex.bui.layout.BLayoutManager;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public abstract class AbstractPetButton
/*     */   extends BContainer
/*     */   implements SelectableButtonInter {
/*     */   private static final int SELECTED = 3;
/*     */   private static final int SELECTED_HOVER = 4;
/*     */   private static final int SELECTED_DISABLED = 5;
/*     */   private static final float SIZE_INC_HOVER = 0.075F;
/*     */   public static final float SIZE_INC_SELECTED = 0.075F;
/*  34 */   private Rectangle targetBounds = new Rectangle(); private final TCGToolTipManager tooltipManager; protected SpecialPetIconLabel petIconLabel; protected BLabel levelIconLabel; protected BLabel trialLabel;
/*     */   protected BProgressBar xpBar;
/*     */   private float currX;
/*     */   private float currY;
/*     */   private float currWidth;
/*     */   private float currHeight;
/*     */   protected float sizeIncreasePercent;
/*     */   protected final PetWindowButtonModel model;
/*     */   protected long petTrialTime;
/*     */   protected boolean collected = false;
/*  44 */   private float sizeIncSelected = 0.0F;
/*  45 */   private float sizeIncHover = 0.0F; private boolean pressed;
/*     */   protected AbsoluteButChangeableLayout absoluteButChangeableLayout;
/*     */   protected BStyleSheet style;
/*     */   protected static final int STATE_COUNT = 6;
/*     */   
/*     */   public AbstractPetButton(PetWindowButtonModel model, TCGToolTipManager tooltipManager, boolean subscriber) {
/*  51 */     this.tooltipManager = tooltipManager;
/*  52 */     this.absoluteButChangeableLayout = new AbsoluteButChangeableLayout();
/*  53 */     setLayoutManager((BLayoutManager)this.absoluteButChangeableLayout);
/*  54 */     this.model = model;
/*     */     
/*  56 */     this.petIconLabel = new SpecialPetIconLabel();
/*     */     
/*  58 */     this.levelIconLabel = (BLabel)new BClickthroughLabel();
/*     */     
/*  60 */     this.trialLabel = (BLabel)new BClickthroughLabel("");
/*  61 */     this.trialLabel.setStyleClass("trial-label");
/*     */     
/*  63 */     if (model.getPet() != null) {
/*  64 */       setTooltipText(JavaLocalization.getInstance().getLocalizedRPGText(model.getPet().getName()));
/*     */     }
/*     */ 
/*     */     
/*  68 */     this.xpBar = new BProgressBar()
/*     */       {
/*     */         public BComponent getHitComponent(int mx, int my) {
/*  71 */           return null;
/*     */         }
/*     */       };
/*  74 */     this.xpBar.setStyleClass("xp-progress");
/*     */     
/*  76 */     add((BComponent)this.petIconLabel, new Rectangle(0, 0, 0, 0));
/*  77 */     add((BComponent)this.levelIconLabel, new Rectangle(0, 0, 27, 0));
/*  78 */     add((BComponent)this.trialLabel, new Rectangle(0, 0, 27, 0));
/*  79 */     add((BComponent)this.xpBar, new Rectangle(0, 0, 0, 0));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  84 */     initialize(subscriber);
/*     */   }
/*     */   
/*     */   protected void initialize(boolean subscriber) {
/*  88 */     this.petIconLabel.setStyleClass(this.model.getPet().getPetDescription().getFamily() + "-pet-label");
/*  89 */     this.levelIconLabel.setText(String.valueOf(this.model.getLevel()));
/*     */     
/*  91 */     if (this.model.isSubscriberOnly() && !subscriber) {
/*  92 */       this.levelIconLabel.setStyleClass("member-level-label-icon");
/*  93 */       setStyleClass(getDefaultStyleClass() + "-member");
/*     */     } else {
/*  95 */       this.levelIconLabel.setStyleClass("level-label-icon");
/*     */     } 
/*     */     
/*  98 */     if (this.model.getLevel() >= 40) {
/*  99 */       this.xpBar.setVisible((this.model.getLevel() < 45));
/* 100 */       setStyleClass("pet-button-epic");
/*     */     } 
/*     */     
/* 103 */     this.xpBar.setProgress(this.model.getCurrentXp());
/*     */     
/* 105 */     addListeners();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStyleClass(String styleClass) {
/* 110 */     this._styleClass = styleClass;
/*     */   }
/*     */   
/*     */   public void wasCollected(boolean value) {
/* 114 */     if (value) {
/* 115 */       this.collected = true;
/* 116 */       ImageBackground imageBackground = new ImageBackground(ImageBackgroundMode.SCALE_XY, this.model.getIcon());
/* 117 */       this.petIconLabel.setBackground(0, (BBackground)imageBackground);
/* 118 */       this.petTrialTime = this.model.getPet().getPlayerPet().getPetTrialExpireTime();
/* 119 */       if (this.model.getLevel() >= 40) {
/* 120 */         this.xpBar.setVisible((this.model.getLevel() < 45));
/* 121 */         setStyleClass("pet-button-epic");
/* 122 */         if (this.style != null) {
/* 123 */           configureStyle(this.style, false);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void configureStyle(BStyleSheet style, boolean override) {
/* 130 */     configureStyle(style);
/* 131 */     this.petIconLabel.configureStyle(style, override);
/*     */   }
/*     */ 
/*     */   
/*     */   public void configureStyle(BStyleSheet style) {
/* 136 */     for (int i = 0; i < this._backgrounds.length; i++) {
/* 137 */       if (this._backgrounds[i] != null) {
/* 138 */         this._backgrounds[i].wasRemoved();
/*     */       }
/* 140 */       this._backgrounds[i] = null;
/*     */     } 
/* 142 */     if (style != null) {
/* 143 */       this.style = style;
/*     */     } else {
/* 145 */       this.style = new BStyleSheet(null, null);
/*     */     } 
/* 147 */     super.configureStyle(this.style);
/* 148 */     for (BBackground _background : this._backgrounds) {
/* 149 */       if (_background != null)
/* 150 */         _background.wasAdded(); 
/*     */     } 
/*     */   }
/*     */   
/*     */   protected abstract void addListeners();
/*     */   
/*     */   public void setSizeIncHover(float sizeIncHover) {
/* 157 */     this.sizeIncHover = sizeIncHover;
/*     */   }
/*     */   
/*     */   public void setSizeIncSelected(float sizeIncSelected) {
/* 161 */     this.sizeIncSelected = sizeIncSelected;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/* 167 */     if (isEnabled() && event instanceof MouseEvent) {
/*     */       
/* 169 */       MouseEvent mouseEvent = (MouseEvent)event;
/* 170 */       int type = mouseEvent.getType();
/* 171 */       switch (type) {
/*     */         case 2:
/* 173 */           onMouseEntered();
/*     */           break;
/*     */         
/*     */         case 3:
/* 177 */           onMouseExited();
/*     */           break;
/*     */         
/*     */         case 0:
/* 181 */           onMousePressed();
/* 182 */           return true;
/*     */         case 1:
/* 184 */           if (mouseEvent.getX() > getAbsoluteX() && mouseEvent.getX() < getAbsoluteX() + getWidth() && mouseEvent.getY() > getAbsoluteY() && mouseEvent.getY() < getAbsoluteY() + getHeight())
/*     */           {
/* 186 */             onMouseReleased();
/*     */           }
/* 188 */           this.pressed = false;
/* 189 */           return true;
/*     */       } 
/*     */     
/*     */     } 
/* 193 */     return super.dispatchEvent(event);
/*     */   }
/*     */   
/*     */   protected void onMouseReleased() {
/* 197 */     if (this.model != null && this.pressed) {
/* 198 */       this.model.setSelected(true);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMouseEntered() {
/* 207 */     moveToFront();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMouseExited() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onMousePressed() {
/* 219 */     if (this.model != null) {
/* 220 */       moveToFront();
/* 221 */       this.pressed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(long updateMillis) {
/* 228 */     int widthIncrease = (int)(this.targetBounds.width * this.sizeIncreasePercent);
/* 229 */     int heightIncrease = (int)(this.targetBounds.height * this.sizeIncreasePercent);
/* 230 */     int widthHalf = widthIncrease / 2;
/* 231 */     int heightHalf = heightIncrease / 2;
/* 232 */     float INTERPOLATE_SPEED = 25.0F;
/* 233 */     this.currX = interpolate(this.currX, (this.targetBounds.x - widthHalf), updateMillis, 25.0F);
/* 234 */     this.currY = interpolate(this.currY, (this.targetBounds.y - heightHalf), updateMillis, 25.0F);
/* 235 */     this.currWidth = interpolate(this.currWidth, (this.targetBounds.width + widthIncrease), updateMillis, 25.0F);
/* 236 */     this.currHeight = interpolate(this.currHeight, (this.targetBounds.height + heightIncrease), updateMillis, 25.0F);
/*     */     
/* 238 */     this._x = (int)this.currX;
/* 239 */     this._y = (int)this.currY;
/* 240 */     this._width = (int)this.currWidth;
/* 241 */     this._height = (int)this.currHeight;
/*     */     
/* 243 */     updateSizes();
/*     */     
/* 245 */     layout();
/* 246 */     invalidate();
/*     */   }
/*     */   
/*     */   protected void updateSizes() {
/* 250 */     int iconSize = getIconSize();
/* 251 */     int cardYOffset = (this instanceof com.funcom.tcg.client.ui.hud2.PetCardButton) ? 7 : 0;
/* 252 */     int cardWidthExtra = (this instanceof com.funcom.tcg.client.ui.hud2.PetCardButton) ? 1 : 0;
/*     */     
/* 254 */     int petIconX = (getWidth() - iconSize - (getInsets()).left - (getInsets()).right) / 2;
/* 255 */     int petIconY = getHeight() - (getInsets()).top - iconSize - (getInsets()).bottom - cardYOffset;
/*     */     
/* 257 */     this.absoluteButChangeableLayout.setConstraints((BComponent)this.petIconLabel, new Rectangle(petIconX, petIconY, iconSize, iconSize));
/* 258 */     int levelWidth = (getWidth() - 46 - (getInsets()).left - (getInsets()).right) / 2 - 2;
/* 259 */     int levelHeight = petIconY - 25;
/* 260 */     int xpWidth = getWidth() - (getInsets()).left - (getInsets()).right + 9;
/* 261 */     this.absoluteButChangeableLayout.setConstraints((BComponent)this.xpBar, new Rectangle(-4, -3, xpWidth, 5));
/*     */     
/* 263 */     if (this.collected) {
/* 264 */       this.petTrialTime = this.model.getPet().getPlayerPet().getPetTrialExpireTime();
/* 265 */       if (this.petTrialTime > System.currentTimeMillis()) {
/* 266 */         showTrialLabels(levelWidth, levelHeight);
/*     */       } else {
/* 268 */         showNonTrialLables(levelWidth, levelHeight);
/*     */       } 
/*     */     } else {
/* 271 */       showNonTrialLables(levelWidth, levelHeight);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void showTrialLabels(int levelWidth, int levelHeight) {
/* 276 */     this.trialLabel.setVisible(true);
/* 277 */     this.levelIconLabel.setVisible(false);
/* 278 */     String s = ClientUtils.toTimeString(ClientUtils.calcPassedSeconds(this.petTrialTime, 0L), (Localizer)TcgGame.getInstance(), PetWindowButton.class);
/* 279 */     this.trialLabel.setText(s);
/* 280 */     this.absoluteButChangeableLayout.setConstraints((BComponent)this.trialLabel, new Rectangle(levelWidth, levelHeight, 50, 25));
/*     */   }
/*     */   
/*     */   private void showNonTrialLables(int levelWidth, int levelHeight) {
/* 284 */     this.trialLabel.setVisible(false);
/* 285 */     this.levelIconLabel.setVisible(true);
/* 286 */     this.absoluteButChangeableLayout.setConstraints((BComponent)this.levelIconLabel, new Rectangle(levelWidth, levelHeight, 50, 25));
/*     */   }
/*     */   
/*     */   protected int getIconSize() {
/* 290 */     Insets insets = getInsets();
/*     */     
/* 292 */     int w = getWidth() - insets.getHorizontal();
/* 293 */     int h = getHeight() - insets.getVertical();
/* 294 */     return Math.min(w, h);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float interpolate(float from, float to, long millisDelta, float speed) {
/* 300 */     float directSetLimit = 0.5F;
/* 301 */     float millisPerSecond = 1000.0F;
/*     */     
/* 303 */     float diff = to - from;
/*     */     
/* 305 */     if (Math.abs(diff) < 0.5F) {
/* 306 */       return to;
/*     */     }
/* 308 */     float toAdd = diff * speed * (float)millisDelta / 1000.0F;
/*     */     
/* 310 */     if (Math.abs(toAdd) > Math.abs(diff)) {
/* 311 */       toAdd = diff;
/*     */     }
/*     */     
/* 314 */     return from + toAdd;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isSelected() {
/* 320 */     return this.model.isSelected();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/* 325 */     return "pet-button";
/*     */   }
/*     */ 
/*     */   
/*     */   public Rectangle getTargetBounds() {
/* 330 */     return this.targetBounds;
/*     */   }
/*     */ 
/*     */   
/*     */   public void modelChanged() {
/* 335 */     updateSelectionAppearance(this.model.isSelected());
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBoundsToTarget() {
/* 340 */     this._x = (int)(this.currX = this.targetBounds.x);
/* 341 */     this._y = (int)(this.currY = this.targetBounds.y);
/* 342 */     this._width = (int)(this.currWidth = this.targetBounds.width);
/* 343 */     this._height = (int)(this.currHeight = this.targetBounds.height);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBounds(int x, int y, int width, int height) {
/* 348 */     this.targetBounds.set(x, y, width, height);
/*     */     
/* 350 */     if (!isAdded()) {
/* 351 */       super.setBounds(x, y, width, height);
/* 352 */       this.currX = this._x;
/* 353 */       this.currY = this._y;
/* 354 */       this.currWidth = this._width;
/* 355 */       this.currHeight = this._height;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void updateSelectionAppearance(boolean selected) {
/* 360 */     if (selected) {
/* 361 */       this.sizeIncreasePercent = this.sizeIncSelected;
/* 362 */       moveToFront();
/*     */     } else {
/* 364 */       this.sizeIncreasePercent = 0.0F;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveToFront() {
/* 375 */     BContainer parent = getParent();
/* 376 */     if (parent instanceof SelectableButtonContainer) {
/* 377 */       ((SelectableButtonContainer)parent).moveToFront(this);
/*     */     }
/*     */   }
/*     */   
/*     */   public PetWindowPet getPet() {
/* 382 */     return this.model.getPet();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getStateCount() {
/* 388 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStatePseudoClass(int state) {
/* 394 */     if (state >= 3) {
/* 395 */       return STATE_PCLASSES[state - 3];
/*     */     }
/* 397 */     return super.getStatePseudoClass(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/* 404 */     if (this.model.isSelected()) {
/* 405 */       return this._enabled ? (this._hover ? 4 : 3) : 5;
/*     */     }
/* 407 */     return super.getState();
/*     */   }
/*     */ 
/*     */   
/* 411 */   protected static final String[] STATE_PCLASSES = new String[] { "selected", "selected_hover", "selected_disabled" };
/*     */   
/*     */   protected class SpecialPetIconLabel
/*     */     extends BClickthroughLabel
/*     */   {
/*     */     public void setStyleClass(String styleClass) {
/* 417 */       this._styleClass = styleClass;
/*     */     }
/*     */     
/*     */     public void configureStyle(BStyleSheet style, boolean override) {
/* 421 */       if (AbstractPetButton.this.collected && !override)
/*     */         return; 
/* 423 */       for (int i = 0; i < this._backgrounds.length; i++) {
/* 424 */         if (this._backgrounds[i] != null) {
/* 425 */           this._backgrounds[i].wasRemoved();
/*     */         }
/* 427 */         this._backgrounds[i] = null;
/*     */       } 
/* 429 */       configureStyle(style);
/* 430 */       for (BBackground _background : this._backgrounds) {
/* 431 */         if (_background != null)
/* 432 */           _background.wasAdded(); 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\AbstractPetButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */