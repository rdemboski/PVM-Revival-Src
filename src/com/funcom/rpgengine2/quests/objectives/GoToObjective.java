/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GoToObjective
/*    */   extends DefaultQuestObjective
/*    */ {
/*    */   public GoToObjective(String missionId, String goToId, int endZoneHeight, int endZoneWidth) {
/* 13 */     super(missionId);
/* 14 */     setObjectiveLocationDefinition(goToId, endZoneHeight, endZoneWidth);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 19 */     return ObjectiveType.GO_TO;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 24 */     return 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 29 */     return "GoToObjective{goToId='" + getGoToId() + '\'' + ", endZoneHeight=" + getGotoZoneHeight() + ", endZoneWidth=" + getGotoZoneWidth() + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\GoToObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */