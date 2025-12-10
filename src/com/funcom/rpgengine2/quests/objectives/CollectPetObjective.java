/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ public class CollectPetObjective
/*    */   extends DefaultQuestObjective {
/*    */   private String petId;
/*    */   
/*    */   public CollectPetObjective(String objectiveId, String petId) {
/*  8 */     super(objectiveId);
/*  9 */     this.petId = petId;
/*    */   }
/*    */   
/*    */   public String getPetId() {
/* 13 */     return this.petId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 18 */     return ObjectiveType.COLLECT_PET;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 23 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 28 */     return "CollectPetObjective{, petId='" + this.petId + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\CollectPetObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */