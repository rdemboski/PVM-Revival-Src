/*     */ package com.funcom.tcg.client.ui.character;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.model.rpg.ClientEquipDoll;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.EquipChangeListener;
/*     */ import com.funcom.tcg.client.ui.SelectableButtonContainer;
/*     */ import com.funcom.tcg.client.ui.SelectableButtonInter;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.inventory.Inventory;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.event.ChangeEvent;
/*     */ import com.jmex.bui.event.ChangeListener;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ 
/*     */ class InventoryEquipmentsContainer
/*     */   extends SelectableButtonContainer
/*     */   implements ChangeListener, EquipChangeListener {
/*     */   private static final int BUTTON_WIDTH = 62;
/*     */   private static final int BUTTON_HEIGHT = 62;
/*     */   private final CharacterWindowModel windowModel;
/*     */   private final SelectionHandler selectionHandler;
/*     */   private ResourceManager resourceManager;
/*     */   private TCGToolTipManager tooltipManager;
/*     */   
/*     */   public InventoryEquipmentsContainer(CharacterWindowModel windowModel, SelectionHandler selectionHandler, ResourceManager resourceManager, TCGToolTipManager tooltipManager) {
/*  33 */     super(62, 62, 0.075F);
/*  34 */     this.windowModel = windowModel;
/*  35 */     this.selectionHandler = selectionHandler;
/*  36 */     this.resourceManager = resourceManager;
/*  37 */     this.tooltipManager = tooltipManager;
/*  38 */     initializeScrollBar(this.buttonHeight, this.extraBorderPercent);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  43 */     return "inventory-equipments-container";
/*     */   }
/*     */ 
/*     */   
/*     */   public void stateChanged(ChangeEvent event) {
/*  48 */     int slotIdSelected = ((SelectionHandler)event.getSource()).getSlotIdSelected();
/*  49 */     if (slotIdSelected != -1) {
/*  50 */       equippedSelectionChanged(slotIdSelected);
/*     */     }
/*     */   }
/*     */   
/*     */   private void equippedSelectionChanged(int slotIdSelected) {
/*  55 */     List<InventoryItem> itemsToShow = getEquipmentsBySlotId(slotIdSelected);
/*  56 */     reinitialize(itemsToShow);
/*     */   }
/*     */   
/*     */   private List<InventoryItem> getEquipmentsBySlotId(int targetSlotId) {
/*  60 */     Inventory inventory = this.windowModel.getInventory();
/*  61 */     List<InventoryItem> itemsToShow = new ArrayList<InventoryItem>();
/*  62 */     for (InventoryItem item : inventory) {
/*  63 */       if (item == null)
/*     */         continue; 
/*  65 */       ItemType itemType = item.getItemType();
/*  66 */       if (itemType.getCategory() == ItemType.ItemCategory.EQUIPMENT && itemType.getEquipValue() == targetSlotId)
/*     */       {
/*  68 */         itemsToShow.add(item);
/*     */       }
/*     */     } 
/*  71 */     Collections.sort(itemsToShow, new ItemComparator());
/*  72 */     return itemsToShow;
/*     */   }
/*     */   
/*     */   private void reinitialize(List<InventoryItem> itemsToShow) {
/*  76 */     removeAll();
/*  77 */     this.layoutComponents.clear();
/*  78 */     getScrollBar().getModel().setValue(0);
/*     */     
/*  80 */     for (InventoryItem item : itemsToShow) {
/*  81 */       if (item != null) {
/*  82 */         EquipmentModel model = new InventoryEquipmentModel(this.resourceManager, this.selectionHandler, this.windowModel);
/*  83 */         model.setItem(item);
/*  84 */         EquipmentButton equipmentButton = new EquipmentButton(this.resourceManager, this.tooltipManager, model, this.selectionHandler, (this.windowModel.isSubscriber() || !item.isSubscriberOnly()));
/*     */ 
/*     */         
/*  87 */         if (!this.windowModel.isSubscriber() && item.isSubscriberOnly()) {
/*  88 */           equipmentButton.setStyleClass("equipment-button-member");
/*  89 */         } else if (item.getLevel() == 0) {
/*  90 */           equipmentButton.setStyleClass("equipment-button");
/*  91 */         }  model.addChangeListener(equipmentButton);
/*  92 */         add((BComponent)equipmentButton);
/*  93 */         this.layoutComponents.add(equipmentButton);
/*  94 */         equipmentButton.modelChanged();
/*     */       } 
/*     */     } 
/*     */     
/*  98 */     invalidateLayout();
/*  99 */     layout();
/*     */     
/* 101 */     for (SelectableButtonInter button : this.layoutComponents) {
/* 102 */       if (button.isSelected()) {
/* 103 */         centerView(button);
/* 104 */         moveToFront(button);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void layout() {
/* 111 */     super.layout();
/*     */     
/* 113 */     Insets insets = getInsets();
/* 114 */     int extraButtonWidth = (int)(this.buttonWidth * this.extraBorderPercent);
/* 115 */     int buttonIncWidth = this.buttonWidth + extraButtonWidth;
/* 116 */     int buttonIncHeight = (int)Math.ceil((this.buttonHeight + this.buttonHeight * this.extraBorderPercent));
/* 117 */     int contentWidth = getWidth() - insets.getHorizontal() - extraButtonWidth;
/* 118 */     int contentHeight = getHeight() - insets.getVertical();
/* 119 */     int buttonsInRow = contentWidth / buttonIncWidth;
/* 120 */     int buttonCount = 0;
/*     */     
/* 122 */     if (buttonsInRow <= 0) {
/* 123 */       buttonsInRow = 1;
/*     */     }
/*     */     
/* 126 */     int totalRows = (this.layoutComponents.size() + buttonsInRow - 1) / buttonsInRow;
/* 127 */     int y = (totalRows - 1) * buttonIncHeight;
/*     */     
/* 129 */     boolean hasLeftOverHeight = (y < contentHeight - buttonIncHeight);
/* 130 */     if (hasLeftOverHeight) {
/*     */ 
/*     */       
/* 133 */       int extraHeight = contentHeight - buttonIncHeight - y;
/* 134 */       y += extraHeight;
/*     */     } 
/*     */     
/* 137 */     for (SelectableButtonInter child : this.layoutComponents) {
/* 138 */       int flippedY = insets.bottom + y + (int)Math.ceil((this.buttonHeight * this.extraBorderPercent / 2.0F));
/* 139 */       child.setBounds(insets.left + extraButtonWidth / 2 + buttonCount * contentWidth / buttonsInRow, flippedY, this.buttonWidth, this.buttonHeight);
/*     */       
/* 141 */       if (!this.layoutInitialized) {
/* 142 */         child.setBoundsToTarget();
/*     */       }
/* 144 */       buttonCount++;
/* 145 */       if (buttonCount >= buttonsInRow) {
/* 146 */         buttonCount = 0;
/* 147 */         y -= buttonIncHeight;
/*     */       } 
/*     */     } 
/*     */     
/* 151 */     int scrollMax = totalRows * buttonIncHeight + insets.getVertical();
/* 152 */     if (scrollMax != this.rangeModel.getMaximum() || getHeight() != this.rangeModel.getExtent()) {
/*     */       
/* 154 */       this.rangeModel.setRange(0, 0, getHeight(), scrollMax);
/* 155 */       this.currentYOffset = 0.0F;
/*     */     } 
/*     */     
/* 158 */     this.layoutInitialized = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/* 164 */     super.renderComponent(renderer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void itemEquipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem newItem, ClientItem oldItem) {
/* 169 */     for (SelectableButtonInter button : this.layoutComponents) {
/* 170 */       button.modelChanged();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void itemUnequipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem oldItem) {
/* 176 */     for (SelectableButtonInter button : this.layoutComponents)
/* 177 */       button.modelChanged(); 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\InventoryEquipmentsContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */