/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.giftbox.HudInfoModel;
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ 
/*    */ public class ItemInfoModelImpl implements HudInfoModel {
/*    */   private final int priority;
/*    */   private final InventoryItem item;
/*    */   
/*    */   public ItemInfoModelImpl(InventoryItem item, int priority) {
/* 11 */     this.item = item;
/* 12 */     this.priority = priority;
/*    */   }
/*    */   
/*    */   public InventoryItem getItem() {
/* 16 */     return this.item;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPriority() {
/* 21 */     return this.priority;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIconPath() {
/* 26 */     if (this.item.getClassId().equals("coin"))
/* 27 */       return "gui/icons/items/currency/coin_001.png"; 
/* 28 */     return this.item.getIcon();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 33 */     return Integer.toString(this.item.getAmount());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\ItemInfoModelImpl.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */