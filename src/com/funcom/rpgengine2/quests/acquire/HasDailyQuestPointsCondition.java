/*    */ package com.funcom.rpgengine2.quests.acquire;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.DailyQuestSupport;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HasDailyQuestPointsCondition
/*    */   implements AcquireQuestCondition
/*    */ {
/*    */   public boolean canAquire(RpgEntity entity) {
/* 12 */     return ((DailyQuestSupport)entity.getSupport(DailyQuestSupport.class)).hasDailyQuestPointsLeft();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\quests\acquire\HasDailyQuestPointsCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */