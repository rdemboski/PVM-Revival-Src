/*    */ package com.funcom.tcg.client.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.BasicQuestSupportable;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.quests.acquire.AcquireQuestCondition;
/*    */ 
/*    */ 
/*    */ public class ClientHasDoneQuestCondition
/*    */   implements AcquireQuestCondition
/*    */ {
/*    */   private String questId;
/*    */   
/*    */   public ClientHasDoneQuestCondition(String questId) {
/* 14 */     this.questId = questId;
/*    */   }
/*    */   
/*    */   public boolean canAquire(RpgEntity entity) {
/* 18 */     BasicQuestSupportable questSupport = (BasicQuestSupportable)entity.getSupport(BasicQuestSupportable.class);
/* 19 */     return (questSupport.hasCompletedQuest(this.questId) || questSupport.hasQuest(this.questId));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\rpg\ClientHasDoneQuestCondition.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */