/*    */ package com.funcom.tcg.client.ui.pets3;
/*    */ 
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DragPetContent
/*    */ {
/*    */   private PetWindowPet pet;
/*    */   
/*    */   public DragPetContent(PetWindowPet pet) {
/* 12 */     this.pet = pet;
/*    */   }
/*    */   
/*    */   public PetWindowPet getPet() {
/* 16 */     return this.pet;
/*    */   }
/*    */   public void setPet(ClientPet pet) {
/*    */     PetWindowPet dragPet;
/* 20 */     if (this.pet != null) {
/* 21 */       this.pet.killListener();
/*    */     }
/*    */     
/* 24 */     if (pet == null) {
/* 25 */       dragPet = new PetWindowPet(null);
/*    */     } else {
/* 27 */       dragPet = new PetWindowPet(pet.getClientPetDescription());
/* 28 */       dragPet.setPlayerPet(pet);
/*    */     } 
/* 30 */     this.pet = dragPet;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\pets3\DragPetContent.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */