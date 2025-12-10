/*    */ package com.funcom.rpgengine2.quests.objectives;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class UseItemObjective extends DefaultQuestObjective {
/*  7 */   private Set<String> itemId = new HashSet<String>();
/*    */   private int useAmount;
/*    */   
/*    */   public UseItemObjective(String objectiveId, String itemId, int useAmount) {
/* 11 */     super(objectiveId);
/* 12 */     this.itemId.add(itemId);
/* 13 */     this.useAmount = useAmount;
/*    */   }
/*    */   
/*    */   public Set<String> getItemId() {
/* 17 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public void addItemId(String id) {
/* 21 */     this.itemId.add(id);
/*    */   }
/*    */ 
/*    */   
/*    */   public ObjectiveType getObjectiveType() {
/* 26 */     return ObjectiveType.USE_ITEM;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getAmount() {
/* 31 */     return this.useAmount;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 36 */     return "UseItemObjective{, itemId='" + this.itemId + '\'' + ", useAmount='" + this.useAmount + '\'' + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\objectives\UseItemObjective.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */