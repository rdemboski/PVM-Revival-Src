/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SubscriptionObjective
/*    */   extends DefaultQuestObjective
/*    */ {
/*    */   public SubscriptionObjective(String objectiveId) {
/*  9 */     super(objectiveId);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 14 */     return ObjectiveType.BECOME_MEMBER;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 19 */     return 1;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\SubscriptionObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */