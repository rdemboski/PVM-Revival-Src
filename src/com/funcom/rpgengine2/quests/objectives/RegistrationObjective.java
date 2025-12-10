/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RegistrationObjective
/*    */   extends DefaultQuestObjective
/*    */ {
/*    */   public RegistrationObjective(String objectiveId) {
/*  9 */     super(objectiveId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 14 */     return ObjectiveType.SAVE_CHAR;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 19 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\RegistrationObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */