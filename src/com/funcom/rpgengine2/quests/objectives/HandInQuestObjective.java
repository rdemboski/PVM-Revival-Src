/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ 
/*    */ public class HandInQuestObjective
/*    */   extends DefaultQuestObjective
/*    */ {
/*    */   private String handInNpcId;
/*    */   private String associatedQuest;
/*    */   private int objNum;
/*    */   
/*    */   public HandInQuestObjective(String objectiveId, String handInNpcId, String associatedQuest) {
/* 12 */     super(objectiveId);
/* 13 */     this.handInNpcId = handInNpcId;
/* 14 */     this.associatedQuest = associatedQuest;
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 19 */     return ObjectiveType.HAND_IN;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 24 */     return 1;
/*    */   }
/*    */   
/*    */   public String getHandInNpcId() {
/* 28 */     return this.handInNpcId;
/*    */   }
/*    */   
/*    */   public void setObjectiveNumber(int objNum) {
/* 32 */     this.objNum = objNum;
/*    */   }
/*    */   
/*    */   public int getObjNum() {
/* 36 */     return this.objNum;
/*    */   }
/*    */   
/*    */   public String getAssociatedQuest() {
/* 40 */     return this.associatedQuest;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\HandInQuestObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */