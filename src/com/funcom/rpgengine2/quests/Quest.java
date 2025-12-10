/*    */ package com.funcom.rpgengine2.quests;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Quest
/*    */ {
/*    */   private QuestDescription questDescription;
/*    */   
/*    */   public Quest(QuestDescription questDescription) {
/* 16 */     this.questDescription = questDescription;
/*    */   }
/*    */   
/*    */   public QuestDescription getQuestDescription() {
/* 20 */     return this.questDescription;
/*    */   }
/*    */   
/*    */   public boolean isRepeatable() {
/* 24 */     return this.questDescription.isRepeatable();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\Quest.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */