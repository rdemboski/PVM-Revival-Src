/*    */ package com.funcom.tcg.client.ui.character;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.ui.SelectableButtonContainer;
/*    */ import com.funcom.tcg.client.ui.SelectableButtonInter;
/*    */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*    */ import com.jmex.bui.util.Insets;
/*    */ 
/*    */ public abstract class AbstractWardrobeContainer
/*    */   extends SelectableButtonContainer
/*    */ {
/*    */   protected final ResourceManager resourceManager;
/*    */   protected final TCGToolTipManager tooltipManager;
/*    */   
/*    */   public AbstractWardrobeContainer(ResourceManager resourceManager, TCGToolTipManager tooltipManager, int buttonWidth, int buttonHeight, float sizeIncSelected) {
/* 16 */     super(buttonWidth, buttonHeight, sizeIncSelected);
/* 17 */     this.resourceManager = resourceManager;
/* 18 */     this.tooltipManager = tooltipManager;
/* 19 */     initializeScrollBar(buttonHeight, this.extraBorderPercent);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void layout() {
/* 24 */     super.layout();
/*    */     
/* 26 */     Insets insets = getInsets();
/* 27 */     int buttonIncWidth = (int)(this.buttonWidth * this.extraBorderPercent);
/* 28 */     int buttonIncHeight = (int)(this.buttonHeight * this.extraBorderPercent);
/* 29 */     int contentWidth = getWidth() - insets.getHorizontal() - buttonIncWidth;
/* 30 */     int contentHeight = getHeight() - insets.getVertical() - buttonIncHeight;
/* 31 */     int buttonsInRow = contentWidth / this.buttonWidth;
/* 32 */     int buttonCount = 0;
/*    */     
/* 34 */     if (buttonsInRow <= 0) {
/* 35 */       buttonsInRow = 1;
/*    */     }
/*    */     
/* 38 */     int totalRows = (this.layoutComponents.size() + buttonsInRow - 1) / buttonsInRow;
/* 39 */     int y = (totalRows - 1) * this.buttonHeight;
/*    */     
/* 41 */     boolean hasLeftOverHeight = (y < contentHeight - this.buttonHeight);
/* 42 */     if (hasLeftOverHeight) {
/*    */ 
/*    */       
/* 45 */       int extraHeight = contentHeight - this.buttonHeight - y;
/* 46 */       y += extraHeight;
/*    */     } 
/*    */     
/* 49 */     for (SelectableButtonInter child : this.layoutComponents) {
/* 50 */       int flippedY = insets.bottom + y + buttonIncHeight / 2;
/* 51 */       child.setBounds(insets.left + buttonIncWidth / 2 + buttonCount * contentWidth / buttonsInRow, flippedY, this.buttonWidth, this.buttonHeight);
/*    */       
/* 53 */       if (!this.layoutInitialized) {
/* 54 */         child.setBoundsToTarget();
/*    */       }
/* 56 */       buttonCount++;
/* 57 */       if (buttonCount >= buttonsInRow) {
/* 58 */         buttonCount = 0;
/* 59 */         y -= this.buttonHeight;
/*    */       } 
/*    */     } 
/*    */     
/* 63 */     int scrollMax = totalRows * this.buttonHeight + buttonIncHeight + insets.getVertical();
/* 64 */     if (scrollMax != this.rangeModel.getMaximum() || getHeight() != this.rangeModel.getExtent()) {
/*    */       
/* 66 */       this.rangeModel.setRange(0, 0, getHeight(), scrollMax);
/* 67 */       this.currentYOffset = 0.0F;
/*    */     } 
/*    */     
/* 70 */     this.layoutInitialized = true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\AbstractWardrobeContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */