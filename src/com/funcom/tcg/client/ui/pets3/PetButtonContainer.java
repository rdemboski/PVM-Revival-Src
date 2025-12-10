/*     */ package com.funcom.tcg.client.ui.pets3;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.SelectableButtonContainer;
/*     */ import com.funcom.tcg.client.ui.SelectableButtonInter;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.BScrollBar;
/*     */ import com.jmex.bui.BoundedRangeModel;
/*     */ import com.jmex.bui.enumeratedConstants.Orientation;
/*     */ import com.jmex.bui.event.ActionEvent;
/*     */ import com.jmex.bui.event.ActionListener;
/*     */ import com.jmex.bui.event.ComponentListener;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import java.util.Collection;
/*     */ 
/*     */ class PetButtonContainer
/*     */   extends SelectableButtonContainer
/*     */ {
/*     */   private static final int BUTTON_WIDTH = 72;
/*     */   private static final int BUTTON_HEIGHT = 103;
/*     */   private final ResourceManager resourceManager;
/*     */   private final TCGToolTipManager tooltipManager;
/*     */   private final PetsWindowModel windowModel;
/*     */   private final PetViewSelectionModel viewSelectionModel;
/*     */   
/*     */   public PetButtonContainer(PetsWindowModel windowModel, PetViewSelectionModel viewSelectionModel, ResourceManager resourceManager, TCGToolTipManager tooltipManager) {
/*  32 */     super(72, 103, 0.075F);
/*     */     
/*  34 */     this.windowModel = windowModel;
/*  35 */     this.viewSelectionModel = viewSelectionModel;
/*  36 */     this.resourceManager = resourceManager;
/*  37 */     this.tooltipManager = tooltipManager;
/*  38 */     setLayoutManager(null);
/*  39 */     initializeScrollBar(this.buttonHeight, this.extraBorderPercent);
/*     */   }
/*     */   
/*     */   protected void initializeScrollBar(int buttonHeight, float extraBorderPercent) {
/*  43 */     this.rangeModel = (BoundedRangeModel)new SelectableButtonContainer.FixedScrollIncBoundedRangeModel((int)(buttonHeight + buttonHeight * extraBorderPercent));
/*  44 */     this.scrollBar = new ButtonScrollBar(Orientation.VERTICAL, this.rangeModel)
/*     */       {
/*     */         protected void roundToClosest() {
/*  47 */           float buttonHeight = PetButtonContainer.this.buttonHeight + PetButtonContainer.this.buttonHeight * PetButtonContainer.this.extraBorderPercent;
/*     */           
/*  49 */           int value = Math.round(PetButtonContainer.this.rangeModel.getValue() / buttonHeight);
/*  50 */           PetButtonContainer.this.rangeModel.setValue((int)(value * buttonHeight));
/*     */         }
/*     */       };
/*  53 */     addListener((ComponentListener)this.rangeModel.createWheelListener());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  59 */     return "pet-button-container";
/*     */   }
/*     */   
/*     */   public void setPets(Collection<PetWindowPet> pets, boolean subscriber) {
/*  63 */     for (PetWindowPet pet : pets) {
/*  64 */       PetWindowButton petButton = new PetWindowButton(new PetWindowButtonModel(this.resourceManager, this.viewSelectionModel, this.windowModel, pet), this.tooltipManager, subscriber);
/*     */       
/*  66 */       add((BComponent)petButton);
/*  67 */       this.layoutComponents.add(petButton);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void reloadPets(Collection<PetWindowPet> pets, boolean subscriber) {
/*  72 */     for (SelectableButtonInter button : this.layoutComponents)
/*  73 */       remove((BComponent)button); 
/*  74 */     this.layoutComponents.clear();
/*  75 */     setPets(pets, subscriber);
/*     */   }
/*     */   
/*     */   public void petChanged(ClientPet pet) {
/*  79 */     if (pet != null) {
/*  80 */       SelectableButtonInter buttonToCenterOn = null;
/*     */       
/*  82 */       for (SelectableButtonInter elementButton : this.layoutComponents) {
/*  83 */         elementButton.modelChanged();
/*  84 */         if (elementButton.isSelected()) {
/*  85 */           buttonToCenterOn = elementButton;
/*     */         }
/*     */       } 
/*     */       
/*  89 */       if (buttonToCenterOn != null) {
/*  90 */         centerView(buttonToCenterOn);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void layout() {
/*  97 */     super.layout();
/*     */     
/*  99 */     Insets insets = getInsets();
/* 100 */     int extraButtonWidth = (int)(this.buttonWidth * this.extraBorderPercent);
/* 101 */     int buttonIncWidth = this.buttonWidth + extraButtonWidth;
/* 102 */     int buttonIncHeight = (int)Math.ceil((this.buttonHeight + this.buttonHeight * this.extraBorderPercent));
/* 103 */     int contentWidth = getWidth() - insets.getHorizontal() - extraButtonWidth;
/* 104 */     int contentHeight = getHeight() - insets.getVertical();
/* 105 */     int buttonsInRow = contentWidth / buttonIncWidth;
/* 106 */     int buttonCount = 0;
/*     */     
/* 108 */     if (buttonsInRow <= 0) {
/* 109 */       buttonsInRow = 1;
/*     */     }
/*     */     
/* 112 */     int totalRows = (this.layoutComponents.size() + buttonsInRow - 1) / buttonsInRow;
/* 113 */     int y = (totalRows - 1) * buttonIncHeight;
/*     */     
/* 115 */     boolean hasLeftOverHeight = (y < contentHeight - buttonIncHeight);
/* 116 */     if (hasLeftOverHeight) {
/*     */ 
/*     */       
/* 119 */       int extraHeight = contentHeight - buttonIncHeight - y;
/* 120 */       y += extraHeight;
/*     */     } 
/*     */     
/* 123 */     for (SelectableButtonInter child : this.layoutComponents) {
/* 124 */       int flippedY = insets.bottom + y + (int)Math.ceil((this.buttonHeight * this.extraBorderPercent / 2.0F));
/* 125 */       child.setBounds(insets.left + extraButtonWidth / 2 + buttonCount * contentWidth / buttonsInRow, flippedY, this.buttonWidth, this.buttonHeight);
/*     */       
/* 127 */       if (!this.layoutInitialized) {
/* 128 */         child.setBoundsToTarget();
/*     */       }
/* 130 */       buttonCount++;
/* 131 */       if (buttonCount >= buttonsInRow) {
/* 132 */         buttonCount = 0;
/* 133 */         y -= buttonIncHeight;
/*     */       } 
/*     */     } 
/*     */     
/* 137 */     int scrollMax = totalRows * buttonIncHeight + insets.getVertical();
/* 138 */     if (scrollMax != this.rangeModel.getMaximum() || getHeight() != this.rangeModel.getExtent()) {
/*     */       
/* 140 */       this.rangeModel.setRange(0, 0, getHeight(), scrollMax);
/* 141 */       this.currentYOffset = 0.0F;
/*     */     } 
/*     */     
/* 144 */     this.layoutInitialized = true;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 149 */     super.renderComponent(renderer);
/* 150 */     renderOverlay(renderer, false);
/*     */   }
/*     */   
/*     */   public void reinitialize() {
/* 154 */     for (SelectableButtonInter elementButton : this.layoutComponents) {
/* 155 */       PetWindowButton petButton = (PetWindowButton)elementButton;
/* 156 */       petButton.wasCollected(this.windowModel.isCollectedPet(petButton.getPet()));
/*     */     } 
/*     */   }
/*     */   
/*     */   public void setOverlaysVisible(boolean val) {
/* 161 */     for (SelectableButtonInter elementButton : this.layoutComponents) {
/* 162 */       PetWindowButton petButton = (PetWindowButton)elementButton;
/* 163 */       if (!val || !petButton.getPet().hasPlayerPet() || petButton.getPet().getLevel() >= MainGameState.getPlayerModel().getStatSupport().getLevel())
/*     */       {
/* 165 */         petButton.setOverlayVisible(val);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private class ButtonScrollBar extends BScrollBar {
/*     */     private ButtonScrollBar(Orientation orientation, BoundedRangeModel model) {
/* 172 */       super(orientation, model);
/*     */     }
/*     */ 
/*     */     
/*     */     public void wasAdded() {
/* 177 */       super.wasAdded();
/*     */       
/* 179 */       ActionListener listener = new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent event) {
/* 182 */             TcgUI.getUISoundPlayer().play("ClickForward");
/*     */           }
/*     */         };
/* 185 */       this._more.addListener((ComponentListener)listener);
/* 186 */       this._less.addListener((ComponentListener)listener);
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\PetButtonContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */