/*    */ package com.funcom.tcg.client.ui.inventory;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DragItemContent
/*    */ {
/*    */   private ItemButton button;
/*    */   private Inventory inventory;
/*    */   private int slotId;
/*    */   
/*    */   public DragItemContent(ItemButton button, Inventory inventory, int slotId) {
/* 12 */     this.button = button;
/* 13 */     this.inventory = inventory;
/* 14 */     this.slotId = slotId;
/*    */   }
/*    */   
/*    */   public InventoryItem getItem() {
/* 18 */     return this.button.getItem();
/*    */   }
/*    */   
/*    */   public int getInventoryId() {
/* 22 */     return this.inventory.getId();
/*    */   }
/*    */   
/*    */   public int getSlotId() {
/* 26 */     return this.slotId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\inventory\DragItemContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */