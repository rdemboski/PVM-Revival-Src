/*    */ package com.funcom.rpgengine2.items;
/*    */ 
/*    */ import com.funcom.rpgengine2.Dice;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class DumbDropList
/*    */   implements DropList
/*    */ {
/*    */   private List<Item> itemList;
/*    */   
/*    */   public DumbDropList(List<Item> itemList) {
/* 14 */     this.itemList = itemList;
/*    */   }
/*    */   
/*    */   public Item getItemToDrop() {
/* 18 */     return this.itemList.get(Dice.RANDOM.nextInt(this.itemList.size()));
/*    */   }
/*    */   
/*    */   public static DumbDropList createFromItems(Item... items) {
/* 22 */     return new DumbDropList(Arrays.asList(items));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\DumbDropList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */