/*    */ package com.funcom.rpgengine2.items;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.ItemContainer;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemTransferContainer
/*    */ {
/*    */   public ItemContainer itemContainer;
/*    */   public int inventoryId;
/*    */   public int preferredSlotId;
/*    */   public Item item;
/*    */   public boolean addOrRemove;
/*    */   
/*    */   public ItemTransferContainer(ItemContainer itemContainer, int inventoryId, int preferredSlotId, Item item, boolean addOrRemove) {
/* 19 */     this.itemContainer = itemContainer;
/* 20 */     this.inventoryId = inventoryId;
/* 21 */     this.preferredSlotId = preferredSlotId;
/* 22 */     this.item = item;
/*    */     
/* 24 */     this.addOrRemove = addOrRemove;
/*    */   }
/*    */   
/*    */   public Item getItem() {
/* 28 */     return this.item;
/*    */   }
/*    */   
/*    */   public ItemContainer getItemContainer() {
/* 32 */     return this.itemContainer;
/*    */   }
/*    */   
/*    */   public int getInventoryId() {
/* 36 */     return this.inventoryId;
/*    */   }
/*    */   
/*    */   public int getPreferredSlotId() {
/* 40 */     return this.preferredSlotId;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAddOrRemove() {
/* 48 */     return this.addOrRemove;
/*    */   }
/*    */   
/*    */   public String toString() {
/* 52 */     StringBuffer sb = new StringBuffer();
/* 53 */     sb.append("[").append("itemContainer=").append(this.itemContainer).append(",inventoryId=").append(this.inventoryId).append(",preferredSlotId=").append(this.preferredSlotId).append(",item=").append(this.item).append("]");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 60 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\ItemTransferContainer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */