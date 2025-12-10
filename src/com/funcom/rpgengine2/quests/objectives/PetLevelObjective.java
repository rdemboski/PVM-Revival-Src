/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ public class PetLevelObjective
/*    */   extends DefaultQuestObjective {
/*    */   private String petId;
/*    */   private int petLevel;
/*    */   
/*    */   public PetLevelObjective(String objectiveId, String petId, int petLevel) {
/*  9 */     super(objectiveId);
/* 10 */     this.petId = petId;
/* 11 */     this.petLevel = petLevel;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPetId() {
/* 16 */     return this.petId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 21 */     return ObjectiveType.PET_LEVEL;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 26 */     return this.petLevel;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 31 */     return "PetLevelObjective{, petId='" + this.petId + '\'' + ", petLevel='" + this.petLevel + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\PetLevelObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */