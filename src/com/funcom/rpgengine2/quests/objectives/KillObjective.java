/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class KillObjective
/*    */   extends DefaultQuestObjective
/*    */ {
/*    */   private String questId;
/* 14 */   private Set<String> killId = new HashSet<String>();
/*    */   private int killAmount;
/*    */   
/*    */   public KillObjective(String objectiveId, String questId, String killId, int killAmount) {
/* 18 */     super(objectiveId);
/* 19 */     this.questId = questId;
/* 20 */     this.killId.add(killId);
/* 21 */     this.killAmount = killAmount;
/*    */   }
/*    */   
/*    */   public String getQuestId() {
/* 25 */     return this.questId;
/*    */   }
/*    */   
/*    */   public void setQuestId(String questId) {
/* 29 */     this.questId = questId;
/*    */   }
/*    */   
/*    */   public Set<String> getKillId() {
/* 33 */     return this.killId;
/*    */   }
/*    */   
/*    */   public void addKillId(String id) {
/* 37 */     this.killId.add(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 42 */     return ObjectiveType.KILL;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 47 */     return this.killAmount;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 52 */     return "KillObjective{objectiveId='" + this.objectiveId + '\'' + ", questId='" + this.questId + '\'' + ", killId='" + this.killId + '\'' + ", killAmount='" + this.killAmount + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\KillObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */