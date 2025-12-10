/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.model.input.UserActionHandler;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.quests.QuestDescription;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.creaturebuilders.QuestGiverBuilder;
/*    */ import com.funcom.tcg.net.message.RefreshQuestGiversMessage;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefreshQuestGiversLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 24 */   private Map<Integer, PropNode> debugMap = new HashMap<Integer, PropNode>();
/* 25 */   GameIOHandler ioHandler = null;
/* 26 */   Map<Integer, CreatureData> creatureDataMap = null;
/* 27 */   Map<Integer, CreatureData> playerDataMap = null;
/* 28 */   RefreshQuestGiversMessage refreshMessage = null;
/*    */   
/* 30 */   int debugId = -1000;
/*    */ 
/*    */   
/*    */   public RefreshQuestGiversLMToken(RefreshQuestGiversMessage refreshMessage, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap) {
/* 34 */     this.refreshMessage = refreshMessage;
/* 35 */     this.ioHandler = ioHandler;
/* 36 */     this.creatureDataMap = creatureDataMap;
/* 37 */     this.playerDataMap = playerDataMap;
/*    */   }
/*    */ 
/*    */   
/*    */   public int update() throws Exception {
/* 42 */     String[] quests = this.refreshMessage.getQuestIds();
/* 43 */     Integer[] questGivers = this.refreshMessage.getQuestGiverIds();
/* 44 */     Short[] progress = this.refreshMessage.getProgress();
/* 45 */     String[] handInQuests = this.refreshMessage.getHandInQuestIds();
/*    */     
/* 47 */     for (int i = 0; i < questGivers.length; i++) {
/* 48 */       PropNode creature = TcgGame.getMonsterRegister().getPropNode(questGivers[i]);
/* 49 */       if (creature != null) {
/* 50 */         updateQuestGiverAction(creature, quests[i], progress[i].shortValue(), handInQuests[i]);
/*    */       }
/*    */     } 
/*    */     
/* 54 */     return 3;
/*    */   }
/*    */   
/*    */   private void updateQuestGiverAction(PropNode propNode, String questId, short progress, String handInQuest) {
/* 58 */     InteractibleProp monster = (InteractibleProp)propNode.getProp();
/* 59 */     monster.clearActions();
/* 60 */     QuestDescription questDescription = TcgGame.getRpgLoader().getQuestManager().getQuestDescription(questId);
/* 61 */     propNode.setActionHandler((UserActionHandler)QuestGiverBuilder.updateQuestGiverIconsAndGetAction(monster, propNode, questDescription, progress, handInQuest));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\RefreshQuestGiversLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */