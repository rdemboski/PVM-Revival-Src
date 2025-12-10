/*    */ package com.funcom.rpgengine2.items;
/*    */ 
/*    */ 
/*    */ public class DefaultItemFactory
/*    */   implements ItemFactory
/*    */ {
/*  7 */   public static final ItemFactory INSTANCE = new DefaultItemFactory();
/*    */   
/*    */   public Item newItem(ItemDescription description) {
/* 10 */     return new Item(description);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\DefaultItemFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */