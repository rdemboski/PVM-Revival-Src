/*     */ package com.funcom.tcg.client.ui.character;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.ui.SelectableButton;
/*     */ import com.funcom.tcg.client.ui.TCGToolTipManager;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.jme.renderer.Renderer;
/*     */ import com.jmex.bui.event.ChangeEvent;
/*     */ import com.jmex.bui.event.ChangeListener;
/*     */ import com.jmex.bui.util.Insets;
/*     */ import java.awt.Point;
/*     */ 
/*     */ public class EquipmentButton
/*     */   extends SelectableButton
/*     */   implements ChangeListener
/*     */ {
/*     */   private static final int SELECTED = 3;
/*     */   private static final int SELECTED_HOVER = 4;
/*     */   private static final int SELECTED_DISABLED = 5;
/*     */   private static final float SIZE_INC_HOVER = 0.075F;
/*     */   static final float SIZE_INC_SELECTED = 0.075F;
/*     */   private final TCGToolTipManager tooltipManager;
/*     */   private final EquipmentModel model;
/*     */   private final SelectionHandler onHoverSelectionHandler;
/*     */   private final boolean showLevel;
/*     */   protected static final int STATE_COUNT = 6;
/*     */   
/*     */   public EquipmentButton(ResourceManager resourceManager, TCGToolTipManager tooltipManager, EquipmentModel model, SelectionHandler onHoverSelectionHandler, boolean showLevel) {
/*  30 */     super(resourceManager, model);
/*  31 */     this.tooltipManager = tooltipManager;
/*  32 */     this.model = model;
/*  33 */     this.onHoverSelectionHandler = onHoverSelectionHandler;
/*  34 */     this.showLevel = (showLevel && !model.getItem().getItemType().equals(ItemType.EQUIP_HEAD));
/*     */     
/*  36 */     model.addChangeListener(this);
/*  37 */     updateIconAndText();
/*     */   }
/*     */ 
/*     */   
/*     */   public void stateChanged(ChangeEvent event) {
/*  42 */     updateIconAndText();
/*     */   }
/*     */   
/*     */   private void updateIconAndText() {
/*  46 */     setIcon(this.model.getIcon());
/*  47 */     if (this.showLevel) {
/*  48 */       setText((this.model.getLevel() == 0) ? "" : Integer.toString(this.model.getLevel()));
/*     */     }
/*     */   }
/*     */   
/*     */   protected String getDefaultStyleClass() {
/*  53 */     return "equipment-button-level";
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMouseEntered() {
/*  58 */     super.onMouseEntered();
/*  59 */     if (this.onHoverSelectionHandler != null) {
/*  60 */       this.onHoverSelectionHandler.compareItemChanged(this.model.getItem());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onMouseExited() {
/*  66 */     super.onMouseExited();
/*  67 */     if (this.onHoverSelectionHandler != null) {
/*  68 */       this.onHoverSelectionHandler.compareItemChanged(null);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void renderComponent(Renderer renderer) {
/*  74 */     super.renderComponent(renderer);
/*     */     
/*  76 */     if (this.model.hasItem()) {
/*  77 */       Insets insets = (this._parent instanceof EquipDollContainer) ? getInsets() : new Insets(7, 7, 7, 7);
/*  78 */       int belowIconHeight = getHeight() - insets.getVertical();
/*     */       
/*  80 */       Point pos = newRenderPos(insets);
/*     */ 
/*     */ 
/*     */       
/*  84 */       if (renderIcon(renderer, false, pos, getIconSize(insets))) {
/*  85 */         pos.y += 40;
/*  86 */         pos.x += 40;
/*     */       } 
/*     */ 
/*     */       
/*  90 */       renderText(renderer, pos, 1.0F, true, false);
/*     */     } 
/*     */   }
/*     */   
/*     */   protected Point newRenderPos(Insets insets) {
/*  95 */     Point pos = new Point();
/*  96 */     pos.x = insets.left;
/*  97 */     pos.y = insets.top;
/*  98 */     return pos;
/*     */   }
/*     */   
/*     */   protected int getIconSize(Insets insets) {
/* 102 */     int w = getWidth() - insets.getHorizontal();
/* 103 */     int h = getHeight() - insets.getVertical();
/* 104 */     int size = Math.min(w, h);
/* 105 */     return size;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTooltipText() {
/* 110 */     if (getParent() instanceof InventoryEquipmentsContainer) {
/* 111 */       return null;
/*     */     }
/*     */     
/* 114 */     if (this.model.hasItem()) {
/* 115 */       InventoryItem item = this.model.getItem();
/* 116 */       return this.tooltipManager.getItemHtml(item.getClassId(), item.getTier());
/*     */     } 
/* 118 */     return super.getTooltipText();
/*     */   }
/*     */   
/*     */   public EquipmentModel getModel() {
/* 122 */     return this.model;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getStateCount() {
/* 128 */     return 6;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected String getStatePseudoClass(int state) {
/* 134 */     if (state >= 3) {
/* 135 */       return STATE_PCLASSES[state - 3];
/*     */     }
/* 137 */     return super.getStatePseudoClass(state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/* 144 */     if (this.model.isSelected()) {
/* 145 */       return this._enabled ? (this._hover ? 4 : 3) : 5;
/*     */     }
/* 147 */     return super.getState();
/*     */   }
/*     */ 
/*     */   
/* 151 */   protected static final String[] STATE_PCLASSES = new String[] { "selected", "selected_hover", "selected_disabled" };
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\EquipmentButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */