/*    */ package com.funcom.tcg.client.ui.character;
/*    */ 
/*    */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*    */ import java.util.Comparator;
/*    */ 
/*    */ class ItemComparator
/*    */   implements Comparator<InventoryItem>
/*    */ {
/*    */   public int compare(InventoryItem o1, InventoryItem o2) {
/* 10 */     int levelDiff = o1.getLevel() - o2.getLevel();
/*    */     
/* 12 */     if (levelDiff == 0) {
/* 13 */       return o1.getName().compareTo(o2.getName());
/*    */     }
/* 15 */     return levelDiff;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\character\ItemComparator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */