/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BScrollBar;
/*     */ import com.jmex.bui.BoundedRangeModel;
/*     */ import com.jmex.bui.enumeratedConstants.Orientation;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import com.jmex.bui.util.Rectangle;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public abstract class SelectableButtonContainer extends OverlayedContainer {
/*  18 */   protected final List<SelectableButtonInter> layoutComponents = new ArrayList<SelectableButtonInter>();
/*  19 */   private final Rectangle _srect = new Rectangle();
/*     */   
/*     */   protected BoundedRangeModel rangeModel;
/*     */   
/*     */   protected BScrollBar scrollBar;
/*     */   
/*     */   protected boolean layoutInitialized;
/*     */   
/*     */   protected final int buttonWidth;
/*     */   protected final int buttonHeight;
/*     */   protected final float extraBorderPercent;
/*     */   protected float currentYOffset;
/*     */   private long lastRenderAt;
/*     */   
/*     */   public SelectableButtonContainer(int buttonWidth, int buttonHeight, float extraBorderPercent) {
/*  34 */     this.buttonWidth = buttonWidth;
/*  35 */     this.buttonHeight = buttonHeight;
/*  36 */     this.extraBorderPercent = extraBorderPercent;
/*  37 */     setLayoutManager(null);
/*     */   }
/*     */   
/*     */   protected void initializeScrollBar(int buttonHeight, float extraBorderPercent) {
/*  41 */     this.rangeModel = new FixedScrollIncBoundedRangeModel((int)(buttonHeight + buttonHeight * extraBorderPercent));
/*  42 */     this.scrollBar = new BScrollBar(Orientation.VERTICAL, this.rangeModel)
/*     */       {
/*     */         protected void roundToClosest() {
/*  45 */           float buttonHeight = SelectableButtonContainer.this.buttonHeight + SelectableButtonContainer.this.buttonHeight * SelectableButtonContainer.this.extraBorderPercent;
/*     */           
/*  47 */           int value = Math.round(SelectableButtonContainer.this.rangeModel.getValue() / buttonHeight);
/*  48 */           SelectableButtonContainer.this.rangeModel.setValue((int)(value * buttonHeight));
/*     */         }
/*     */ 
/*     */         
/*     */         public void wasAdded() {
/*  53 */           super.wasAdded();
/*     */           
/*  55 */           ActionListener listener = new ActionListener()
/*     */             {
/*     */               public void actionPerformed(ActionEvent event) {
/*  58 */                 TcgUI.getUISoundPlayer().play("ClickForward");
/*     */               }
/*     */             };
/*  61 */           this._more.addListener((ComponentListener)listener);
/*  62 */           this._less.addListener((ComponentListener)listener);
/*     */         }
/*     */       };
/*  65 */     addListener((ComponentListener)this.rangeModel.createWheelListener());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BComponent getHitComponent(int mx, int my) {
/*  71 */     if (isInContainer(mx, my)) {
/*  72 */       mx -= this._x;
/*  73 */       my -= this._y + getYOffset();
/*     */ 
/*     */       
/*  76 */       for (int ii = getComponentCount() - 1; ii >= 0; ii--) {
/*  77 */         BComponent child = getComponent(ii); BComponent hit;
/*  78 */         if ((hit = child.getHitComponent(mx, my)) != null) {
/*  79 */           return hit;
/*     */         }
/*     */       } 
/*  82 */       return (BComponent)this;
/*     */     } 
/*     */     
/*  85 */     return null;
/*     */   }
/*     */   
/*     */   public int getAbsoluteY() {
/*  89 */     return super.getAbsoluteY() + getYOffset();
/*     */   }
/*     */   
/*     */   private boolean isInContainer(int mx, int my) {
/*  93 */     return (isVisible() && mx >= this._x && my >= this._y && mx < this._x + this._width && my < this._y + this._height);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void invalidateLayout() {
/*  99 */     this.layoutInitialized = false;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 104 */     long now = System.currentTimeMillis();
/* 105 */     if (this.lastRenderAt != 0L) {
/* 106 */       long updateMillis = now - this.lastRenderAt;
/* 107 */       updateButtons(updateMillis);
/* 108 */       updateOffset(updateMillis);
/*     */     } 
/* 110 */     this.lastRenderAt = now;
/*     */     
/* 112 */     Insets insets = getInsets();
/* 113 */     int yoffset = getYOffset();
/* 114 */     int xoffset = 0;
/* 115 */     GL11.glTranslatef(xoffset, yoffset, 0.0F);
/* 116 */     boolean scissored = intersectScissorBox(this._srect, getAbsoluteX() + insets.left, super.getAbsoluteY() + insets.bottom, this._width - insets.getHorizontal(), this._height - insets.getVertical());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 122 */       for (int i = 0, count = getComponentCount(); i < count; i++) {
/* 123 */         getComponent(i).render(renderer);
/*     */       }
/*     */     } finally {
/* 126 */       restoreScissorState(scissored, this._srect);
/* 127 */       GL11.glTranslatef(-xoffset, -yoffset, 0.0F);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateOffset(long updateMillis) {
/* 132 */     this.currentYOffset = interpolate(this.currentYOffset, this.rangeModel.getValue(), updateMillis, 10.0F);
/*     */   }
/*     */   
/*     */   private float interpolate(float from, float to, long millisDelta, float speed) {
/* 136 */     float directSetLimit = 0.5F;
/* 137 */     float millisPerSecond = 1000.0F;
/*     */     
/* 139 */     float diff = to - from;
/*     */     
/* 141 */     if (Math.abs(diff) < 0.5F) {
/* 142 */       return to;
/*     */     }
/* 144 */     float toAdd = diff * speed * (float)millisDelta / 1000.0F;
/*     */     
/* 146 */     if (Math.abs(toAdd) > Math.abs(diff)) {
/* 147 */       toAdd = diff;
/*     */     }
/*     */     
/* 150 */     return from + toAdd;
/*     */   }
/*     */ 
/*     */   
/*     */   private int getYOffset() {
/* 155 */     return (int)this.currentYOffset - this.rangeModel.getMaximum() - this.rangeModel.getExtent();
/*     */   }
/*     */   
/*     */   private void updateButtons(long updateMillis) {
/* 159 */     for (int ii = 0, ll = getComponentCount(); ii < ll; ii++) {
/* 160 */       BComponent comp = getComponent(ii);
/* 161 */       if (comp instanceof SelectableButtonInter) {
/* 162 */         ((SelectableButtonInter)comp).update(updateMillis);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   public BScrollBar getScrollBar() {
/* 168 */     return this.scrollBar;
/*     */   }
/*     */   
/*     */   protected void centerView(SelectableButtonInter button) {
/* 172 */     if (button != null) {
/* 173 */       int y = (button.getTargetBounds()).y;
/* 174 */       int value = this.rangeModel.getMaximum() - this.rangeModel.getExtent() / 2 - y;
/* 175 */       if (this.rangeModel.getValue() > value || this.rangeModel.getValue() + this.rangeModel.getExtent() / 2 <= value)
/* 176 */         this.rangeModel.setValue(value); 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveSelectionInFront() {
/* 181 */     for (SelectableButtonInter button : this.layoutComponents) {
/* 182 */       if (button.isSelected()) {
/* 183 */         this._children.remove(button);
/* 184 */         this._children.add((BComponent)button);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void moveToFront(SelectableButtonInter selectableButton) {
/* 190 */     if (this._children.remove(selectableButton)) {
/* 191 */       this._children.add((BComponent)selectableButton);
/* 192 */       moveSelectionInFront();
/*     */     } 
/*     */   }
/*     */   
/*     */   protected static class FixedScrollIncBoundedRangeModel extends BoundedRangeModel {
/*     */     private final int scrollIncrement;
/*     */     
/*     */     public FixedScrollIncBoundedRangeModel(int scrollIncrement) {
/* 200 */       super(0, 0, 0, 0);
/* 201 */       this.scrollIncrement = scrollIncrement;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getScrollIncrement() {
/* 206 */       return this.scrollIncrement;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\SelectableButtonContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */