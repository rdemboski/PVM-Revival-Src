/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ public class FinishMissionObjective extends DefaultQuestObjective {
/*    */   private String finishMission;
/*    */   private int objNum;
/*    */   
/*    */   public FinishMissionObjective(String objectiveId, String finishMission) {
/*  8 */     super(objectiveId);
/*  9 */     this.finishMission = finishMission;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 14 */     return ObjectiveType.FINISH_QUEST;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 19 */     return 1;
/*    */   }
/*    */   
/*    */   public String getFinishMission() {
/* 23 */     return this.finishMission;
/*    */   }
/*    */   
/*    */   public void setObjectiveNumber(int objNum) {
/* 27 */     this.objNum = objNum;
/*    */   }
/*    */   
/*    */   public int getObjNum() {
/* 31 */     return this.objNum;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\FinishMissionObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */