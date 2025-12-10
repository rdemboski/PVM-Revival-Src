/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.pets.PetDescription;
/*    */ import com.funcom.rpgengine2.pets.PetSupport;
/*    */ 
/*    */ 
/*    */ public class FixedPetSupport
/*    */   extends PetSupport
/*    */ {
/*    */   private int petLevel;
/*    */   
/*    */   public FixedPetSupport(int petLevel) {
/* 13 */     this.petLevel = petLevel;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getActivePetLevel() {
/* 18 */     return this.petLevel;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean collectNewPetAndNotifyClient(PetDescription petDescription) {
/* 23 */     return false;
/*    */   }
/*    */   
/*    */   public void setPetLevel(int petLevel) {
/* 27 */     this.petLevel = petLevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\FixedPetSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */