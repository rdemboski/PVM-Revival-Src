/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ 
/*    */ public class SpecialAmountObjective
/*    */   extends DefaultQuestObjective
/*    */ {
/*    */   private int amount;
/*    */   private ObjectiveType objectiveType;
/*    */   
/*    */   public SpecialAmountObjective(String objectiveId, int amount, ObjectiveType objectiveType) {
/* 11 */     super(objectiveId);
/* 12 */     this.amount = amount;
/* 13 */     this.objectiveType = objectiveType;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 18 */     return this.objectiveType;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 23 */     return this.amount;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\SpecialAmountObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */