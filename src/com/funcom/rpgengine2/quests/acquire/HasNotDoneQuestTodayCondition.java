/*    */ package com.funcom.rpgengine2.quests.acquire;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.DailyQuestSupport;
/*    */ import com.funcom.rpgengine2.creatures.QuestSupportable;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HasNotDoneQuestTodayCondition
/*    */   implements AcquireQuestCondition
/*    */ {
/*    */   private String questId;
/*    */   
/*    */   public HasNotDoneQuestTodayCondition(String questId) {
/* 16 */     this.questId = questId;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canAquire(RpgEntity entity) {
/* 21 */     QuestSupportable questSupport = (QuestSupportable)entity.getSupport(QuestSupportable.class);
/* 22 */     return (questSupport.getQuestCompletionTime(this.questId) < ((DailyQuestSupport)entity.getSupport(DailyQuestSupport.class)).getNextUpdateTime() - TimeUnit.MILLISECONDS.convert(1L, TimeUnit.DAYS));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\acquire\HasNotDoneQuestTodayCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */