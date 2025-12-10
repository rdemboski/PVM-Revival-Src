/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.gameengine.jme.text.HTMLView2;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BContainer;
/*     */ import com.jmex.bui.event.BEvent;
/*     */ import com.jmex.bui.event.MouseEvent;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ 
/*     */ public abstract class SelectableButton
/*     */   extends ScalingSupportLabel
/*     */   implements SelectableButtonInter
/*     */ {
/*  15 */   private Rectangle targetBounds = new Rectangle();
/*     */   
/*     */   private float currX;
/*     */   private float currY;
/*     */   private float currWidth;
/*     */   private float currHeight;
/*     */   protected float sizeIncreasePercent;
/*  22 */   private float sizeIncSelected = 0.0F;
/*  23 */   private float sizeIncHover = 0.0F;
/*     */   protected final SelectableButtonModel model;
/*     */   private boolean pressed;
/*     */   private boolean highlighted = false;
/*     */   private long elapsed;
/*     */   private boolean state;
/*     */   
/*     */   public SelectableButton(ResourceManager resourceManager, SelectableButtonModel model) {
/*  31 */     super(resourceManager);
/*  32 */     this.model = model;
/*  33 */     modelChanged();
/*     */   }
/*     */   
/*     */   public void setSizeIncHover(float sizeIncHover) {
/*  37 */     this.sizeIncHover = sizeIncHover;
/*     */   }
/*     */   
/*     */   public void setSizeIncSelected(float sizeIncSelected) {
/*  41 */     this.sizeIncSelected = sizeIncSelected;
/*     */   }
/*     */   
/*     */   public void update(long updateMillis) {
/*  45 */     this.elapsed += updateMillis;
/*  46 */     if (this.highlighted) {
/*  47 */       if (this.elapsed >= 1000L) {
/*  48 */         this.elapsed = 0L;
/*  49 */         this.state = !this.state;
/*     */       } 
/*     */       
/*  52 */       if (this.state) {
/*  53 */         onMouseEntered();
/*     */       } else {
/*  55 */         onMouseExited();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  60 */     int widthIncrease = (int)(this.targetBounds.width * this.sizeIncreasePercent);
/*  61 */     int heightIncrease = (int)(this.targetBounds.height * this.sizeIncreasePercent);
/*  62 */     int widthHalf = widthIncrease / 2;
/*  63 */     int heightHalf = heightIncrease / 2;
/*  64 */     float INTERPOLATE_SPEED = 25.0F;
/*  65 */     this.currX = interpolate(this.currX, (this.targetBounds.x - widthHalf), updateMillis, 25.0F);
/*  66 */     this.currY = interpolate(this.currY, (this.targetBounds.y - heightHalf), updateMillis, 25.0F);
/*  67 */     this.currWidth = interpolate(this.currWidth, (this.targetBounds.width + widthIncrease), updateMillis, 25.0F);
/*  68 */     this.currHeight = interpolate(this.currHeight, (this.targetBounds.height + heightIncrease), updateMillis, 25.0F);
/*     */     
/*  70 */     this._x = (int)this.currX;
/*  71 */     this._y = (int)this.currY;
/*  72 */     this._width = (int)this.currWidth;
/*  73 */     this._height = (int)this.currHeight;
/*     */   }
/*     */   
/*     */   public void setBoundsToTarget() {
/*  77 */     this._x = (int)(this.currX = this.targetBounds.x);
/*  78 */     this._y = (int)(this.currY = this.targetBounds.y);
/*  79 */     this._width = (int)(this.currWidth = this.targetBounds.width);
/*  80 */     this._height = (int)(this.currHeight = this.targetBounds.height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean dispatchEvent(BEvent event) {
/*  86 */     if (isEnabled() && event instanceof MouseEvent) {
/*     */       
/*  88 */       MouseEvent mouseEvent = (MouseEvent)event;
/*  89 */       int type = mouseEvent.getType();
/*  90 */       switch (type) {
/*     */         case 2:
/*  92 */           onMouseEntered();
/*     */           break;
/*     */         
/*     */         case 3:
/*  96 */           onMouseExited();
/*     */           break;
/*     */         
/*     */         case 0:
/* 100 */           onMousePressed();
/* 101 */           return true;
/*     */         case 1:
/* 103 */           if (mouseEvent.getX() > getAbsoluteX() && mouseEvent.getX() < getAbsoluteX() + getWidth() && mouseEvent.getY() > getAbsoluteY() && mouseEvent.getY() < getAbsoluteY() + getHeight())
/*     */           {
/* 105 */             onMouseReleased();
/*     */           }
/* 107 */           this.pressed = false;
/* 108 */           return true;
/*     */       } 
/*     */     
/*     */     } 
/* 112 */     return super.dispatchEvent(event);
/*     */   }
/*     */   
/*     */   protected void onMouseReleased() {
/* 116 */     if (this.model != null && this.pressed) {
/* 117 */       processReleased();
/*     */     }
/*     */   }
/*     */   
/*     */   public void processReleased() {
/* 122 */     this.model.setSelected(true);
/*     */   }
/*     */   
/*     */   protected void onMouseEntered() {
/* 126 */     if (!this.model.isSelected()) {
/* 127 */       this.sizeIncreasePercent = this.sizeIncHover;
/*     */     }
/* 129 */     moveToFront();
/*     */   }
/*     */   
/*     */   protected void onMouseExited() {
/* 133 */     if (this.model.isSelected()) {
/* 134 */       this.sizeIncreasePercent = this.sizeIncSelected;
/*     */     } else {
/* 136 */       this.sizeIncreasePercent = 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   protected void onMousePressed() {
/* 141 */     if (this.model != null) {
/* 142 */       moveToFront();
/* 143 */       this.pressed = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public float getAlpha() {
/* 150 */     float alpha = super.getAlpha();
/* 151 */     if (!isEnabled()) {
/* 152 */       alpha *= 0.5F;
/*     */     }
/* 154 */     return alpha;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void moveToFront() {
/* 164 */     BContainer parent = getParent();
/* 165 */     if (parent instanceof SelectableButtonContainer) {
/* 166 */       ((SelectableButtonContainer)parent).moveToFront(this);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBounds(int x, int y, int width, int height) {
/* 172 */     this.targetBounds.set(x, y, width, height);
/*     */     
/* 174 */     if (!isAdded()) {
/* 175 */       super.setBounds(x, y, width, height);
/* 176 */       this.currX = this._x;
/* 177 */       this.currY = this._y;
/* 178 */       this.currWidth = this._width;
/* 179 */       this.currHeight = this._height;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected BComponent createTooltipComponent(String tiptext) {
/* 185 */     return (BComponent)new HTMLView2(tiptext, this.resourceManager);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private float interpolate(float from, float to, long millisDelta, float speed) {
/* 191 */     float directSetLimit = 0.5F;
/* 192 */     float millisPerSecond = 1000.0F;
/*     */     
/* 194 */     float diff = to - from;
/*     */     
/* 196 */     if (Math.abs(diff) < 0.5F) {
/* 197 */       return to;
/*     */     }
/* 199 */     float toAdd = diff * speed * (float)millisDelta / 1000.0F;
/*     */     
/* 201 */     if (Math.abs(toAdd) > Math.abs(diff)) {
/* 202 */       toAdd = diff;
/*     */     }
/*     */     
/* 205 */     return from + toAdd;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSelected(boolean selected) {
/* 210 */     if (this.model.isSelected() != selected) {
/* 211 */       this.model.setSelected(selected);
/*     */       
/* 213 */       updateSelectionAppearance(selected);
/*     */     } 
/*     */     
/* 216 */     this.highlighted = false;
/*     */   }
/*     */   
/*     */   protected void updateSelectionAppearance(boolean selected) {
/* 220 */     if (selected) {
/* 221 */       this.sizeIncreasePercent = this.sizeIncSelected;
/* 222 */       moveToFront();
/*     */     } else {
/* 224 */       this.sizeIncreasePercent = 0.0F;
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isSelected() {
/* 229 */     return this.model.isSelected();
/*     */   }
/*     */   
/*     */   public Rectangle getTargetBounds() {
/* 233 */     return this.targetBounds;
/*     */   }
/*     */   
/*     */   public void modelChanged() {
/* 237 */     updateSelectionAppearance(this.model.isSelected());
/*     */   }
/*     */   
/*     */   public boolean isHighlighted() {
/* 241 */     return this.highlighted;
/*     */   }
/*     */   
/*     */   public void setHighlighted(boolean highlighted) {
/* 245 */     this.highlighted = highlighted;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\SelectableButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */