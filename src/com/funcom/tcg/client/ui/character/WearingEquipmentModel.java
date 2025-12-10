/*    */ package com.funcom.tcg.client.ui.character;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ 
/*    */ public class WearingEquipmentModel extends EquipmentModel {
/*    */   private final SelectionHandler selectionHandler;
/*    */   private final int slotId;
/*    */   
/*    */   public WearingEquipmentModel(ResourceManager resourceManager, SelectionHandler selectionHandler, int slotId) {
/* 10 */     super(resourceManager);
/* 11 */     this.selectionHandler = selectionHandler;
/* 12 */     this.slotId = slotId;
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 16 */     return this.slotId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSelected() {
/* 21 */     return (hasItem() && this.selectionHandler.isEquipDollItemSelected(this.item));
/*    */   }
/*    */ 
/*    */   
/*    */   public void setSelected(boolean selected) {
/* 26 */     if (selected) {
/* 27 */       this.selectionHandler.setSlotIdSelected(this.slotId);
/*    */     } else {
/* 29 */       throw new UnsupportedOperationException("cannot unselect wearing item");
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\WearingEquipmentModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */