/*    */ package com.funcom.tcg.client.ui.character;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.tcg.client.ui.SelectableButtonModel;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import com.jmex.bui.BImage;
/*    */ 
/*    */ public abstract class EquipmentModel
/*    */   extends SelectableButtonModel {
/*    */   private final ResourceManager resourceManager;
/*    */   protected InventoryItem item;
/*    */   
/*    */   public EquipmentModel(ResourceManager resourceManager) {
/* 14 */     this.resourceManager = resourceManager;
/*    */   }
/*    */   
/*    */   public void setItem(InventoryItem item) {
/* 18 */     this.item = item;
/* 19 */     fireStateChanged();
/*    */   }
/*    */   
/*    */   public InventoryItem getItem() {
/* 23 */     return this.item;
/*    */   }
/*    */   
/*    */   public BImage getIcon() {
/* 27 */     if (this.item != null) {
/* 28 */       String itemIconPath = this.item.getIcon();
/* 29 */       return (BImage)this.resourceManager.getResource(BImage.class, itemIconPath);
/*    */     } 
/*    */     
/* 32 */     return null;
/*    */   }
/*    */   
/*    */   public int getLevel() {
/* 36 */     if (this.item != null) {
/* 37 */       return this.item.getLevel();
/*    */     }
/* 39 */     return -1;
/*    */   }
/*    */   
/*    */   public String getClassId() {
/* 43 */     return null;
/*    */   }
/*    */   
/*    */   public boolean hasItem() {
/* 47 */     return (this.item != null);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\EquipmentModel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */