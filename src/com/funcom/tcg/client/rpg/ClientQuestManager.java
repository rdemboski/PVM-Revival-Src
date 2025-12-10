/*    */ package com.funcom.tcg.client.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.quests.QuestDescription;
/*    */ import com.funcom.rpgengine2.quests.QuestManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ClientQuestManager
/*    */   extends QuestManager
/*    */ {
/*    */   protected void addHasDoneQuestCondition(String previousQuestInChain, QuestDescription questDescription) {
/* 13 */     questDescription.addAcquireQuestCondition(new ClientHasDoneQuestCondition(previousQuestInChain));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\rpg\ClientQuestManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */