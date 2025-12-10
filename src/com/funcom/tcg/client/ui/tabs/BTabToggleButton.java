/*    */ package com.funcom.tcg.client.ui.tabs;
/*    */ import com.funcom.gameengine.jme.text.HTMLView2;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.jme.renderer.Renderer;
/*    */ import com.jmex.bui.BComponent;
/*    */ import com.jmex.bui.BContainer;
/*    */ import com.jmex.bui.BCustomToggleButton;
/*    */ import com.jmex.bui.BLabel;
/*    */ import com.jmex.bui.BLabel2;
/*    */ import com.jmex.bui.background.BBackground;
/*    */ import com.jmex.bui.icon.BIcon;
/*    */ import com.jmex.bui.layout.BLayoutManager;
/*    */ import com.jmex.bui.layout.GroupLayout;
/*    */ import com.jmex.bui.layout.Justification;
/*    */ import com.jmex.bui.layout.StackedLayout;
/*    */ import com.jmex.bui.util.Rectangle;
/*    */ 
/*    */ public class BTabToggleButton extends BCustomToggleButton {
/*    */   private BLabel label;
/*    */   
/*    */   public BTabToggleButton(BIcon icon, String action) {
/* 22 */     this(icon, (BIcon)null, action);
/*    */   }
/*    */   private Rectangle scissorRect;
/*    */   public BTabToggleButton(BIcon icon, BIcon overlay, String action) {
/* 26 */     BContainer labelContainer = new BContainer((BLayoutManager)new StackedLayout());
/* 27 */     labelContainer.setStyleClass(getDefaultStyleClass() + "_label");
/*    */     
/* 29 */     this.label = (BLabel)new BLabel2(icon, action);
/* 30 */     setAction(action);
/* 31 */     setLayoutManager((BLayoutManager)GroupLayout.makeHoriz(Justification.CENTER));
/* 32 */     add((BComponent)labelContainer);
/* 33 */     labelContainer.add((BComponent)this.label);
/*    */     
/* 35 */     if (overlay != null) {
/* 36 */       BLabel overlayLabel = new BLabel(overlay);
/* 37 */       labelContainer.add((BComponent)overlayLabel);
/*    */     } 
/*    */     
/* 40 */     this.scissorRect = new Rectangle();
/*    */   }
/*    */   
/*    */   public BLabel getLabel() {
/* 44 */     return this.label;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setTooltipText(String text) {
/* 49 */     super.setTooltipText(text);
/* 50 */     this.label.setTooltipText(text);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected String getDefaultStyleClass() {
/* 56 */     return "bwindowtcg.tab";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void renderBackground(Renderer renderer) {
/* 64 */     BBackground background = getBackground();
/* 65 */     if (background != null) {
/* 66 */       int drawWidth = this._width + (getInsets()).right;
/* 67 */       int scissorWidth = this._parent.getWidth() - this._x;
/* 68 */       if (!isSelected()) {
/* 69 */         boolean scissored = BComponent.intersectScissorBox(this.scissorRect, getAbsoluteX(), getAbsoluteY(), scissorWidth, this._height);
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 74 */         background.render(renderer, 0, 0, drawWidth, this._height, this._alpha);
/*    */         
/* 76 */         BComponent.restoreScissorState(scissored, this.scissorRect);
/*    */       } else {
/* 78 */         background.render(renderer, 0, 0, drawWidth, this._height, this._alpha);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected BComponent createTooltipComponent(String tiptext) {
/* 85 */     return (BComponent)new HTMLView2(tiptext, TcgGame.getResourceManager());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\tabs\BTabToggleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */