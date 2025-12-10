/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ 
/*    */ public class SpecialTutorialObjective
/*    */   extends DefaultQuestObjective
/*    */ {
/*    */   private String specialId;
/*    */   
/*    */   public SpecialTutorialObjective(String objectiveId, String specialId) {
/* 10 */     super(objectiveId);
/* 11 */     this.specialId = specialId;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 16 */     return ObjectiveType.TUTORIAL;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 21 */     return 1;
/*    */   }
/*    */   
/*    */   public String getSpecialId() {
/* 25 */     return this.specialId;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\SpecialTutorialObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */