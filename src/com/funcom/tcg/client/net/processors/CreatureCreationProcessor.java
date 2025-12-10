/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.gameengine.view.RepresentationalNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.controllers.InterpolationController;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.MCMonsterLMToken;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.MCQuestGiverLMToken;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.MCQuestInteractibleLoadingToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.CreatureCreationMessage;
/*    */ import com.funcom.tcg.rpg.Faction;
/*    */ import com.jme.scene.Controller;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class CreatureCreationProcessor
/*    */   implements MessageProcessor {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 27 */     CreatureCreationMessage creationMessage = (CreatureCreationMessage)message;
/*    */     
/* 29 */     WorldCoordinate wc = creationMessage.getCoord();
/* 30 */     float angle = creationMessage.getAngle();
/* 31 */     if (LoadingManager.USE) {
/* 32 */       creationMessage.setExtraParam(creatureDataMap);
/*    */       
/*    */       try {
/*    */         MCQuestInteractibleLoadingToken mCQuestInteractibleLoadingToken;
/* 36 */         Faction faction = creationMessage.getFaction();
/* 37 */         LoadingManagerToken token = null;
/* 38 */         if (MCMonsterLMToken.canBuild(faction)) {
/* 39 */           MCMonsterLMToken mCMonsterLMToken = new MCMonsterLMToken(creationMessage);
/* 40 */         } else if (MCQuestGiverLMToken.canBuild(faction)) {
/* 41 */           MCQuestGiverLMToken mCQuestGiverLMToken = new MCQuestGiverLMToken(creationMessage);
/* 42 */         } else if (MCQuestInteractibleLoadingToken.canBuild(faction)) {
/* 43 */           mCQuestInteractibleLoadingToken = new MCQuestInteractibleLoadingToken(creationMessage);
/*    */         } else {
/* 45 */           throw new IllegalStateException("Don't know how to build: " + creationMessage.getType());
/*    */         } 
/* 47 */         if (mCQuestInteractibleLoadingToken != null)
/*    */         {
/* 49 */           LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)mCQuestInteractibleLoadingToken, PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */         }
/*    */       }
/* 52 */       catch (Exception e) {
/* 53 */         throw new IllegalStateException("Error while trying to build: " + creationMessage.getType());
/*    */       }
/*    */     
/*    */     } else {
/*    */       
/* 58 */       PropNode creatureNode = MainGameState.getNodeFactory().createForType(creationMessage);
/*    */       
/* 60 */       MainGameState.getWorld().addObject((RepresentationalNode)creatureNode);
/* 61 */       TcgGame.getMonsterRegister().addPropNode(creatureNode);
/* 62 */       creatureNode.updateRenderState();
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 67 */       CreatureData creatureData = new CreatureData(wc, angle);
/* 68 */       creatureData.setType(creationMessage.getType());
/* 69 */       creatureDataMap.put(Integer.valueOf(creationMessage.getCreatureId()), creatureData);
/* 70 */       creatureNode.getBasicEffectsNode().setStats(creatureData.getStats());
/*    */ 
/*    */       
/* 73 */       InterpolationController cont = new InterpolationController(creatureNode);
/* 74 */       cont.setPosition(wc);
/* 75 */       cont.setAngle(creationMessage.getAngle());
/* 76 */       creatureNode.addController((Controller)cont);
/*    */       
/* 78 */       creatureNode.updateWorldData(0.0F);
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 83 */     return 11;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\CreatureCreationProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */