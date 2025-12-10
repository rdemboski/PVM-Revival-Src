/*    */ package com.funcom.rpgengine2.quests.acquire;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.BasicQuestSupportable;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ 
/*    */ 
/*    */ public class HasNotDoneQuestCondition
/*    */   implements AcquireQuestCondition
/*    */ {
/*    */   private String questId;
/*    */   
/*    */   public HasNotDoneQuestCondition(String questId) {
/* 13 */     this.questId = questId;
/*    */   }
/*    */   
/*    */   public boolean canAquire(RpgEntity entity) {
/* 17 */     BasicQuestSupportable questSupport = (BasicQuestSupportable)entity.getSupport(BasicQuestSupportable.class);
/* 18 */     return !questSupport.hasCompletedQuest(this.questId);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\acquire\HasNotDoneQuestCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */