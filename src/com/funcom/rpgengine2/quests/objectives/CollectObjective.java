/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CollectObjective
/*    */   extends DefaultQuestObjective
/*    */ {
/*    */   private String questId;
/* 15 */   private Set<String> collectId = new HashSet<String>();
/*    */   private int collectAmount;
/*    */   
/*    */   public CollectObjective(String objectiveId, String questId, String collectId, int collectAmount) {
/* 19 */     super(objectiveId);
/* 20 */     this.questId = questId;
/* 21 */     this.collectId.add(collectId);
/* 22 */     this.collectAmount = collectAmount;
/*    */   }
/*    */   
/*    */   public String getQuestId() {
/* 26 */     return this.questId;
/*    */   }
/*    */   
/*    */   public Set<String> getCollectId() {
/* 30 */     return this.collectId;
/*    */   }
/*    */   
/*    */   public void addCollectId(String id) {
/* 34 */     this.collectId.add(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 39 */     return ObjectiveType.COLLECT;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 44 */     return this.collectAmount;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return "CollectObjective{, questId='" + this.questId + '\'' + ", collectId='" + this.collectId + '\'' + ", collectAmount='" + this.collectAmount + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\CollectObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */