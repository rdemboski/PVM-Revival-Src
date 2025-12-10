/*    */ package com.funcom.tcg.client.ui.quest2;
/*    */ 
/*    */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*    */ import com.funcom.tcg.client.model.rpg.ClientObjectiveTracker;
/*    */ import com.funcom.tcg.client.ui.hud2.HasAlphaValues;
/*    */ import java.text.MessageFormat;
/*    */ 
/*    */ public class TCGClientMission
/*    */   implements MissionObjective, HasAlphaValues {
/*    */   private ClientObjectiveTracker questObjective;
/*    */   private int priority;
/*    */   
/*    */   public TCGClientMission(ClientObjectiveTracker questObjective) {
/* 14 */     this.questObjective = questObjective;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getObjectiveText() {
/* 19 */     return this.questObjective.getObjective().getStartObjectiveText();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getObjectiveIconPath() {
/* 24 */     return this.questObjective.getObjective().getIconPath();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getPriority() {
/* 29 */     return this.priority;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getIconPath() {
/* 34 */     return getObjectiveIconPath();
/*    */   }
/*    */ 
/*    */   
/*    */   public String getText() {
/* 39 */     String objectiveText = this.questObjective.getObjective().getShortObjectiveText();
/* 40 */     objectiveText = MessageFormat.format(objectiveText, new Object[] { Integer.valueOf(this.questObjective.getTrackAmount()), Integer.valueOf(this.questObjective.getCompleteValue()) });
/* 41 */     return objectiveText;
/*    */   }
/*    */ 
/*    */   
/*    */   public float getAlpha() {
/* 46 */     if (this.questObjective.isCompleted()) return 0.5F; 
/* 47 */     return 1.0F;
/*    */   }
/*    */   
/*    */   public void setPriority(int priority) {
/* 51 */     this.priority = priority;
/*    */   }
/*    */ 
/*    */   
/*    */   public QuestObjective getObjective() {
/* 56 */     return this.questObjective.getObjective();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCompleted() {
/* 61 */     return this.questObjective.isCompleted();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\quest2\TCGClientMission.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */