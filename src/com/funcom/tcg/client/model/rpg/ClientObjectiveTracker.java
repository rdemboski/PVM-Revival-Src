/*    */ package com.funcom.tcg.client.model.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.quests.objectives.QuestObjective;
/*    */ import com.funcom.rpgengine2.quests.objectives.TCGObjectiveTracker;
/*    */ import com.funcom.util.SizeCheckedArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientObjectiveTracker
/*    */   extends TCGObjectiveTracker
/*    */ {
/*    */   private QuestObjective objective;
/*    */   private List<TCGObjectiveTracker.ObjectiveListener> listeners;
/*    */   
/*    */   public ClientObjectiveTracker(String questId, String objectiveId, int trackAmount, int completeValue, QuestObjective objective) {
/* 17 */     super(questId, objectiveId, trackAmount, completeValue);
/* 18 */     this.objective = objective;
/*    */   }
/*    */   
/*    */   public QuestObjective getObjective() {
/* 22 */     return this.objective;
/*    */   }
/*    */   
/*    */   public void addObjectiveListener(TCGObjectiveTracker.ObjectiveListener objectiveListener) {
/* 26 */     if (this.listeners == null)
/* 27 */       this.listeners = (List<TCGObjectiveTracker.ObjectiveListener>)new SizeCheckedArrayList(1, "TCGObjectiveTracker.listeners", 5); 
/* 28 */     this.listeners.add(objectiveListener);
/*    */   }
/*    */   
/*    */   public void removeObjectiveListener(TCGObjectiveTracker.ObjectiveListener objectiveListener) {
/* 32 */     if (this.listeners != null)
/* 33 */       this.listeners.remove(objectiveListener); 
/*    */   }
/*    */   
/*    */   private void fireObjectiveCompleted() {
/* 37 */     if (this.listeners != null)
/* 38 */       for (TCGObjectiveTracker.ObjectiveListener listener : this.listeners) {
/* 39 */         listener.objectiveCompleted(this);
/*    */       } 
/*    */   }
/*    */   
/*    */   private void fireObjectiveUpdate() {
/* 44 */     if (this.listeners != null)
/* 45 */       for (TCGObjectiveTracker.ObjectiveListener listener : this.listeners) {
/* 46 */         listener.objectiveUpdate(this);
/*    */       } 
/*    */   }
/*    */   
/*    */   public void setTrackAmount(Integer trackAmount) {
/* 51 */     super.setTrackAmount(trackAmount);
/* 52 */     if (isCompleted()) {
/* 53 */       fireObjectiveCompleted();
/*    */     } else {
/* 55 */       fireObjectiveUpdate();
/*    */     } 
/*    */   }
/*    */   public void addTrackAmount(Integer trackAmount) {
/* 59 */     super.addTrackAmount(trackAmount);
/* 60 */     if (isCompleted()) {
/* 61 */       fireObjectiveCompleted();
/*    */     } else {
/* 63 */       fireObjectiveUpdate();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientObjectiveTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */