/*    */ package com.funcom.tcg.client.ui.quest;
/*    */ 
/*    */ import com.funcom.rpgengine2.quests.objectives.ObjectiveType;
/*    */ import com.funcom.rpgengine2.quests.objectives.SpecialTutorialObjective;
/*    */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*    */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TutorialQuestTriggers
/*    */   extends QuestModel.QuestChangeAdapter
/*    */ {
/*    */   public void missionAdded(QuestModel questModel, ClientQuestData mission) {
/* 16 */     List<ClientObjectiveTracker> objectives = mission.getQuestObjectives();
/* 17 */     for (ClientObjectiveTracker objectiveTracker : objectives) {
/* 18 */       if (objectiveTracker.getObjective().getObjectiveType() == ObjectiveType.TUTORIAL)
/* 19 */         parseSpecial(((SpecialTutorialObjective)objectiveTracker.getObjective()).getSpecialId()); 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void parseSpecial(String specialId) {}
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest\TutorialQuestTriggers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */