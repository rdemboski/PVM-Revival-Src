/*    */ package com.funcom.rpgengine2.pickupitems;
/*    */ 
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import com.funcom.rpgengine2.pets.PetDescription;
/*    */ import com.funcom.rpgengine2.pets.PetManager;
/*    */ 
/*    */ public class PetPickUpDescription
/*    */   extends AbstractPickUpDescription {
/*    */   private PetDescription petDescription;
/*    */   
/*    */   public void setAssociatedData(RpgLoader loader, String associatedItemId, int associatedItemTier) {
/* 12 */     PetManager petManager = loader.getPetManager();
/* 13 */     this.petDescription = petManager.getPetDescription(associatedItemId);
/*    */   }
/*    */   
/*    */   public PetDescription getPetDescription() {
/* 17 */     return this.petDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 22 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pickupitems\PetPickUpDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */