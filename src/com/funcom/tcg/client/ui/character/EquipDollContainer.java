/*     */ package com.funcom.tcg.client.ui.character;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.rpg.ClientEquipDoll;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.EquipChangeListener;
/*     */ import com.funcom.tcg.client.ui.SelectableButtonInter;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.TcgUI;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.jmex.bui.BComponent;
/*     */ import com.jmex.bui.event.ChangeEvent;
/*     */ import com.jmex.bui.event.ChangeListener;
/*     */ 
/*     */ class EquipDollContainer extends AbstractWardrobeContainer implements EquipChangeListener, ChangeListener {
/*     */   private static final float SIZE_INC_SELECTED = 0.15F;
/*     */   private static final int BUTTON_WIDTH = 90;
/*     */   private static final int BUTTON_HEIGHT = 73;
/*  21 */   private CharacterEquipmentWindow equipmentWindow = null;
/*     */ 
/*     */ 
/*     */   
/*     */   public EquipDollContainer(SelectionHandler selectionHandler, ResourceManager resourceManager, TCGToolTipManager tooltipManager, CharacterEquipmentWindow equipWindow) {
/*  26 */     super(resourceManager, tooltipManager, 90, 73, 0.15F);
/*     */     
/*  28 */     this.equipmentWindow = equipWindow;
/*  29 */     int maxSlotId = getMaxSlotId();
/*  30 */     for (int i = 0; i <= maxSlotId; i++) {
/*  31 */       EquipmentModel model = new WearingEquipmentModel(resourceManager, selectionHandler, i);
/*  32 */       EquipmentButton equipmentButton = new EquipmentButton(resourceManager, tooltipManager, model, null, false)
/*     */         {
/*     */           
/*     */           public void processReleased()
/*     */           {
/*  37 */             super.processReleased();
/*  38 */             TcgUI.getUISoundPlayer().play("ClickForward");
/*     */           }
/*     */         };
/*  41 */       equipmentButton.setSizeIncSelected(0.15F);
/*  42 */       equipmentButton.setStyleClass("equipment-tab");
/*  43 */       add((BComponent)equipmentButton);
/*  44 */       this.layoutComponents.add(equipmentButton);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addDummyForSetEquipmentButton(ResourceManager resourceManager, TCGToolTipManager tooltipManager) {
/*  51 */     EquipmentButton equipmentButton = new EquipmentButton(resourceManager, tooltipManager, new DummyWearingEquipmentModel(), null, false);
/*     */ 
/*     */ 
/*     */     
/*  55 */     equipmentButton.setStyleClass("equipment-tab");
/*  56 */     equipmentButton.setEnabled(false);
/*  57 */     add((BComponent)equipmentButton);
/*  58 */     this.layoutComponents.add(equipmentButton);
/*     */   }
/*     */   
/*     */   private int getMaxSlotId() {
/*  62 */     int maxSlotId = 0;
/*  63 */     for (ItemType itemType : ItemType.values()) {
/*  64 */       if (itemType.getCategory() == ItemType.ItemCategory.EQUIPMENT) {
/*  65 */         maxSlotId = Math.max(maxSlotId, itemType.getEquipValue());
/*     */       }
/*     */     } 
/*  68 */     return maxSlotId;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  73 */     return "equipdoll-button-container";
/*     */   }
/*     */ 
/*     */   
/*     */   public void stateChanged(ChangeEvent event) {
/*  78 */     for (SelectableButtonInter button : this.layoutComponents) {
/*  79 */       button.modelChanged();
/*     */     }
/*     */   }
/*     */   
/*     */   public void reinitialize(ClientEquipDoll equipDoll) {
/*  84 */     boolean hadSelectedButton = hasSelectedButton();
/*  85 */     assignItems(equipDoll);
/*  86 */     if (!hadSelectedButton) {
/*  87 */       setFirstButtonWithItem();
/*     */     }
/*     */   }
/*     */   
/*     */   private void setFirstButtonWithItem() {
/*  92 */     for (SelectableButtonInter layoutComponent : this.layoutComponents) {
/*  93 */       EquipmentButton button = (EquipmentButton)layoutComponent;
/*  94 */       WearingEquipmentModel model = (WearingEquipmentModel)button.getModel();
/*  95 */       if (model.hasItem()) {
/*  96 */         model.setSelected(true);
/*     */         break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private boolean hasSelectedButton() {
/* 103 */     for (SelectableButtonInter button : this.layoutComponents) {
/* 104 */       if (button.isSelected()) {
/* 105 */         return true;
/*     */       }
/*     */     } 
/* 108 */     return false;
/*     */   }
/*     */   
/*     */   private void assignItems(ClientEquipDoll equipDoll) {
/* 112 */     for (SelectableButtonInter elementButton : this.layoutComponents) {
/* 113 */       EquipmentButton button = (EquipmentButton)elementButton;
/* 114 */       WearingEquipmentModel model = (WearingEquipmentModel)button.getModel();
/* 115 */       if (model.getSlotId() >= 0) {
/* 116 */         ClientItem clientItem = equipDoll.getItem(model.getSlotId());
/* 117 */         if (clientItem != null) {
/* 118 */           model.setItem((InventoryItem)clientItem);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void itemEquipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem newItem, ClientItem oldItem) {
/* 126 */     WearingEquipmentModel model = findModel(placementId);
/* 127 */     if (model != null) {
/* 128 */       model.setItem((InventoryItem)newItem);
/*     */ 
/*     */       
/* 131 */       if (TcgGame.isEquipmentTutorial()) {
/* 132 */         if (this.equipmentWindow.getNewestItem() != null) {
/*     */           
/* 134 */           if (newItem.getName().equals(this.equipmentWindow.getNewestItem().getName())) {
/* 135 */             this.equipmentWindow.updateTutorial(EquipmentTutorialStep.STEP_6_CLOSE_WINDOW);
/*     */           } else {
/* 137 */             this.equipmentWindow.updateTutorial(EquipmentTutorialStep.STEP_5_SELECT_ITEM);
/*     */           } 
/*     */         } else {
/* 140 */           this.equipmentWindow.updateTutorial(EquipmentTutorialStep.STEP_4_ITEM_STAT_INFO);
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private WearingEquipmentModel findModel(int placementId) {
/* 147 */     for (SelectableButtonInter elementButton : this.layoutComponents) {
/* 148 */       EquipmentButton button = (EquipmentButton)elementButton;
/* 149 */       WearingEquipmentModel model = (WearingEquipmentModel)button.getModel();
/* 150 */       if (model.getSlotId() == placementId) {
/* 151 */         return model;
/*     */       }
/*     */     } 
/* 154 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void itemUnequipped(ClientEquipDoll clientEquipDoll, int placementId, ClientItem oldItem) {
/* 160 */     for (SelectableButtonInter button : this.layoutComponents) {
/* 161 */       EquipmentModel model = ((EquipmentButton)button).getModel();
/* 162 */       if (model.getItem().getClassId().equals(oldItem.getClassId()))
/* 163 */         model.setItem(null); 
/*     */     } 
/*     */   }
/*     */   
/*     */   private static class DummyWearingEquipmentModel
/*     */     extends WearingEquipmentModel {
/*     */     public DummyWearingEquipmentModel() {
/* 170 */       super((ResourceManager)null, (SelectionHandler)null, -1);
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean isSelected() {
/* 175 */       return false;
/*     */     }
/*     */     
/*     */     public void setSelected(boolean selected) {}
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\EquipDollContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */