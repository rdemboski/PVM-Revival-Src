/*     */ package com.funcom.rpgengine2.quests.objectives;
/*     */ 
/*     */ import com.funcom.util.SizeCheckedArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCGObjectiveTracker
/*     */ {
/*     */   private boolean persisted = false;
/*     */   private String questId;
/*     */   private String objectiveId;
/*     */   private int trackAmount;
/*     */   private int completeValue;
/*     */   private List<ObjectiveListener> listeners;
/*     */   
/*     */   public TCGObjectiveTracker() {
/*  22 */     this("UNSET_ID", "UNSET_OBJECTIVE", -1, -1);
/*     */   }
/*     */   
/*     */   public TCGObjectiveTracker(String questId, String objectiveId, int trackAmount, int completeValue) {
/*  26 */     this.questId = questId;
/*  27 */     this.objectiveId = objectiveId;
/*  28 */     this.trackAmount = trackAmount;
/*  29 */     this.completeValue = completeValue;
/*     */   }
/*     */   
/*     */   public void addObjectiveListener(ObjectiveListener objectiveListener) {
/*  33 */     if (this.listeners == null)
/*  34 */       this.listeners = (List<ObjectiveListener>)new SizeCheckedArrayList(1, "TCGObjectiveTracker.listeners", 5); 
/*  35 */     this.listeners.add(objectiveListener);
/*     */   }
/*     */   
/*     */   public void removeObjectiveListener(ObjectiveListener objectiveListener) {
/*  39 */     if (this.listeners != null)
/*  40 */       this.listeners.remove(objectiveListener); 
/*     */   }
/*     */   
/*     */   public String getTrackerId() {
/*  44 */     return this.questId + "#" + this.objectiveId;
/*     */   }
/*     */   
/*     */   public int getTrackAmount() {
/*  48 */     return this.trackAmount;
/*     */   }
/*     */   
/*     */   public void setTrackAmount(Integer trackAmount) {
/*  52 */     if (trackAmount.intValue() < 0)
/*     */       return; 
/*  54 */     this.trackAmount = trackAmount.intValue();
/*  55 */     if (isCompleted()) {
/*  56 */       fireObjectiveCompleted();
/*     */     } else {
/*  58 */       fireObjectiveUpdate();
/*     */     } 
/*     */   }
/*     */   public void addTrackAmount(Integer trackAmount) {
/*  62 */     if (trackAmount.intValue() < 0)
/*     */       return; 
/*  64 */     this.trackAmount += trackAmount.intValue();
/*  65 */     if (isCompleted()) {
/*  66 */       fireObjectiveCompleted();
/*     */     } else {
/*  68 */       fireObjectiveUpdate();
/*     */     } 
/*     */   }
/*     */   public boolean isCompleted() {
/*  72 */     return (this.trackAmount >= this.completeValue);
/*     */   }
/*     */   
/*     */   public String getQuestId() {
/*  76 */     return this.questId;
/*     */   }
/*     */   
/*     */   public String getObjectiveId() {
/*  80 */     return this.objectiveId;
/*     */   }
/*     */   
/*     */   public int getCompleteValue() {
/*  84 */     return this.completeValue;
/*     */   }
/*     */   
/*     */   public void setQuestId(String questId) {
/*  88 */     this.questId = questId;
/*     */   }
/*     */   
/*     */   public void setObjectiveId(String objectiveId) {
/*  92 */     this.objectiveId = objectiveId;
/*     */   }
/*     */   
/*     */   public void setCompleteValue(Integer completeValue) {
/*  96 */     this.completeValue = completeValue.intValue();
/*     */   }
/*     */   
/*     */   private void fireObjectiveCompleted() {
/* 100 */     if (this.listeners != null)
/* 101 */       for (ObjectiveListener listener : this.listeners) {
/* 102 */         listener.objectiveCompleted(this);
/*     */       } 
/*     */   }
/*     */   
/*     */   private void fireObjectiveUpdate() {
/* 107 */     if (this.listeners != null)
/* 108 */       for (ObjectiveListener listener : this.listeners) {
/* 109 */         listener.objectiveUpdate(this);
/*     */       } 
/*     */   }
/*     */   
/*     */   public void setPersisted() {
/* 114 */     this.persisted = true;
/*     */   }
/*     */   
/*     */   public boolean isPersisted() {
/* 118 */     return this.persisted;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 123 */     return "TCGObjectiveTracker{questId='" + this.questId + '\'' + ", objectiveId='" + this.objectiveId + '\'' + ", trackAmount=" + this.trackAmount + ", completeValue=" + this.completeValue + '}';
/*     */   }
/*     */   
/*     */   public static interface ObjectiveListener {
/*     */     void objectiveCompleted(TCGObjectiveTracker param1TCGObjectiveTracker);
/*     */     
/*     */     void objectiveUpdate(TCGObjectiveTracker param1TCGObjectiveTracker);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\TCGObjectiveTracker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */