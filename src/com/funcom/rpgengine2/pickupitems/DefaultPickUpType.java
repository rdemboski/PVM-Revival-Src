/*    */ package com.funcom.rpgengine2.pickupitems;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class DefaultPickUpType
/*    */ {
/*  7 */   public static final PickupType ITEM = new PickupType()
/*    */     {
/*    */       public String getName() {
/* 10 */         return "item";
/*    */       }
/*    */ 
/*    */       
/*    */       public AbstractPickUpDescription createDescription() {
/* 15 */         return new ItemPickUpDescription();
/*    */       }
/*    */     };
/*    */   
/* 19 */   public static final PickupType PET = new PickupType()
/*    */     {
/*    */       public String getName() {
/* 22 */         return "pet";
/*    */       }
/*    */ 
/*    */       
/*    */       public AbstractPickUpDescription createDescription() {
/* 27 */         return new PetPickUpDescription();
/*    */       }
/*    */     };
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pickupitems\DefaultPickUpType.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */