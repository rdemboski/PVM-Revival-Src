/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.PickupType;
/*    */ 
/*    */ public abstract class TCGPickUpType {
/*  7 */   public static final PickupType GIFTBOX = new PickupType()
/*    */     {
/*    */       public String getName() {
/* 10 */         return "giftbox";
/*    */       }
/*    */ 
/*    */       
/*    */       public AbstractPickUpDescription createDescription() {
/* 15 */         return new GiftBoxPickUpDescription();
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGPickUpType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */