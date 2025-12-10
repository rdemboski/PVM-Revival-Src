/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.quests.QuestDescription;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.creaturebuilders.QuestGiverBuilder;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.RefreshQuestGiversLMToken;
/*    */ import com.funcom.tcg.net.message.RefreshQuestGiversMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefreshQuestGiversProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 30 */     RefreshQuestGiversMessage refreshMessage = (RefreshQuestGiversMessage)message;
/*    */     
/* 32 */     if (LoadingManager.USE) {
/* 33 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new RefreshQuestGiversLMToken(refreshMessage, ioHandler, creatureDataMap, playerDataMap), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 38 */       String[] quests = refreshMessage.getQuestIds();
/* 39 */       Integer[] questGivers = refreshMessage.getQuestGiverIds();
/* 40 */       Short[] progress = refreshMessage.getProgress();
/* 41 */       String[] handInQuests = refreshMessage.getHandInQuestIds();
/*    */       
/* 43 */       for (int i = 0; i < questGivers.length; i++) {
/* 44 */         PropNode creature = TcgGame.getMonsterRegister().getPropNode(questGivers[i]);
/* 45 */         if (creature != null) {
/* 46 */           updateQuestGiverAction(creature, quests[i], progress[i].shortValue(), handInQuests[i]);
/*    */         }
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private void updateQuestGiverAction(PropNode propNode, String questId, short progress, String handInQuest) {
/* 53 */     InteractibleProp monster = (InteractibleProp)propNode.getProp();
/* 54 */     monster.clearActions();
/* 55 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questId);
/* 56 */     propNode.setActionHandler((UserActionHandler)QuestGiverBuilder.updateQuestGiverIconsAndGetAction(monster, propNode, questDescription, progress, handInQuest));
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 60 */     return 222;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\RefreshQuestGiversProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */