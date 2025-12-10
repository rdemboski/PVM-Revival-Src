/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.combat.RpgStatus;
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.StateUpdateLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.StateUpdateMessage;
/*    */ import java.util.Collection;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StateUpdateProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 31 */     StateUpdateMessage updateMessage = (StateUpdateMessage)message;
/* 32 */     Collection<Stat> stats = updateMessage.getStats();
/* 33 */     List<StateUpdateMessage.BuffData> buffDatas = updateMessage.getBuffDatas();
/* 34 */     Set<RpgStatus> rpgStatusList = updateMessage.getRpgStatus();
/*    */     
/* 36 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 37 */     if (updateMessage.getId() == localClientPlayer.getId() && updateMessage.getCreatureType() == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/*    */       
/* 39 */       if (!stats.isEmpty()) {
/* 40 */         localClientPlayer.updateStats(stats);
/*    */       }
/* 42 */       if (!buffDatas.isEmpty()) {
/* 43 */         localClientPlayer.updateBuffs(buffDatas);
/*    */       }
/* 45 */       if (rpgStatusList.size() > 0) {
/* 46 */         localClientPlayer.updateStatusList(rpgStatusList);
/*    */       }
/* 48 */     } else if (MainGameState.getDuelHealthBarWindow() != null && updateMessage.getId() == MainGameState.getDuelHealthBarWindow().getOpponentId()) {
/* 49 */       float currentHealth = 0.0F, maxHealth = 0.0F, currentMana = 0.0F, maxMana = 0.0F;
/* 50 */       for (Stat stat : stats) {
/* 51 */         if (stat.getId().shortValue() == 12) {
/* 52 */           currentHealth = stat.getSumAsFloat(); continue;
/* 53 */         }  if (stat.getId().shortValue() == 11) {
/* 54 */           maxHealth = stat.getSumAsFloat(); continue;
/* 55 */         }  if (stat.getId().shortValue() == 14) {
/* 56 */           currentMana = stat.getSumAsFloat(); continue;
/* 57 */         }  if (stat.getId().shortValue() == 13) {
/* 58 */           maxMana = stat.getSumAsFloat();
/*    */         }
/*    */       } 
/* 61 */       MainGameState.getDuelHealthBarWindow().setOpponentProgress(true, Math.min(currentHealth / maxHealth, 1.0F));
/* 62 */       MainGameState.getDuelHealthBarWindow().setOpponentProgress(false, Math.min(currentMana / maxMana, 1.0F));
/*    */     } else {
/*    */       CreatureData data;
/* 65 */       if (updateMessage.getCreatureType() == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/* 66 */         data = playerDataMap.get(Integer.valueOf(updateMessage.getId()));
/*    */       } else {
/* 68 */         data = creatureDataMap.get(Integer.valueOf(updateMessage.getId()));
/*    */       } 
/*    */       
/* 71 */       if (data != null) {
/* 72 */         if (!stats.isEmpty()) {
/* 73 */           data.updateStats(stats);
/*    */         }
/*    */         
/* 76 */         if (!buffDatas.isEmpty()) {
/* 77 */           data.updateBuffs(buffDatas);
/*    */ 
/*    */         
/*    */         }
/*    */       
/*    */       }
/* 83 */       else if (LoadingManager.USE) {
/* 84 */         LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new StateUpdateLMToken(updateMessage, creatureDataMap), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 93 */     return 13;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\StateUpdateProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */