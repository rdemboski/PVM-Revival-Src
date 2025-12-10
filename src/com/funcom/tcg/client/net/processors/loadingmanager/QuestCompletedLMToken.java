/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*    */ import com.funcom.tcg.net.message.QuestCompletedMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuestCompletedLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 17 */   QuestCompletedMessage questCompletedMessage = null;
/*    */   public QuestCompletedLMToken(QuestCompletedMessage questCompletedMessage) {
/* 19 */     this.questCompletedMessage = questCompletedMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() {
/* 25 */     QuestModel questModel = MainGameState.getQuestModel();
/*    */ 
/*    */     
/* 28 */     if (this.questCompletedMessage.getMessageInformationType() == QuestCompletedMessage.MessageInformationType.COMPLETED.getId()) {
/* 29 */       questModel.completeQuest(this.questCompletedMessage.getQuestId(), this.questCompletedMessage.isClaimed());
/*    */     } else {
/* 31 */       questModel.completeQuest(this.questCompletedMessage.getQuestId(), this.questCompletedMessage.isClaimed());
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 44 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\QuestCompletedLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */