/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.tcg.client.model.rpg.ClientQuestData;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.quest.QuestModel;
/*    */ import com.funcom.tcg.net.message.QuestsAbandonedMessage;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QuestsAbandonedLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 20 */   QuestsAbandonedMessage questsAbandonedMessage = null;
/*    */   
/*    */   public QuestsAbandonedLMToken(QuestsAbandonedMessage questsAbandonedMessage) {
/* 23 */     this.questsAbandonedMessage = questsAbandonedMessage;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() {
/* 29 */     QuestModel questModel = MainGameState.getQuestModel();
/* 30 */     Iterator<ClientQuestData> questDescriptionIter = questModel.getQuestDescriptions().iterator();
/* 31 */     while (questDescriptionIter.hasNext()) {
/* 32 */       ClientQuestData clientQuestData = questDescriptionIter.next();
/* 33 */       if (this.questsAbandonedMessage.getQuestIds().contains(clientQuestData.getQuestId()))
/* 34 */         questDescriptionIter.remove(); 
/*    */     } 
/* 36 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\QuestsAbandonedLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */