/*    */ package com.funcom.rpgengine2.items;
/*    */ 
/*    */ public class EquipDoll
/*    */   extends GeneralItemHolder
/*    */ {
/*    */   public static final int HEAD_SLOT = 0;
/*    */   public static final int TORSO_SLOT = 1;
/*    */   public static final int LEGS_SLOT = 2;
/*    */   public static final int SUBSCRIBER_SLOT = 3;
/*    */   public static final int BACK_SLOT = 4;
/*    */   public static final int TRINKET_SLOT = 5;
/*    */   public static final int SLOT_CAPACITY = 5;
/*    */   
/*    */   public EquipDoll(int id) {
/* 15 */     super(id, 5);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getHeadItem() throws EquipException {
/* 26 */     return getItem(0);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getTorsoItem() throws EquipException {
/* 38 */     return getItem(1);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getLegsItem() throws EquipException {
/* 49 */     return getItem(2);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getBackItem() throws EquipException {
/* 61 */     return getItem(4);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Item getTrinketItem() throws EquipException {
/* 72 */     return getItem(5);
/*    */   }
/*    */   
/*    */   public Item getSubscriberItem() throws EquipException {
/* 76 */     return getItem(3);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\EquipDoll.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */