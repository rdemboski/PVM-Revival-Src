/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*    */ import com.funcom.tcg.net.message.QuestCompletedListMessage;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuestCompletedListLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 15 */   QuestCompletedListMessage questCompletedMessage = null;
/*    */   
/*    */   public QuestCompletedListLMToken(QuestCompletedListMessage questCompletedMessage) {
/* 18 */     this.questCompletedMessage = questCompletedMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() {
/* 24 */     QuestModel questModel = MainGameState.getQuestModel();
/* 25 */     if (!this.questCompletedMessage.isShow()) {
/* 26 */       questModel.setAcceptAchievements(false);
/*    */     }
/* 28 */     List<String> questList = this.questCompletedMessage.getQuestIds();
/* 29 */     List<Boolean> claimedList = this.questCompletedMessage.getClaimedList();
/* 30 */     for (int i = 0; i < questList.size(); i++) {
/* 31 */       questModel.completeQuest(questList.get(i), ((Boolean)claimedList.get(i)).booleanValue());
/*    */     }
/*    */     
/* 34 */     questModel.setAcceptAchievements(true);
/* 35 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\QuestCompletedListLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */