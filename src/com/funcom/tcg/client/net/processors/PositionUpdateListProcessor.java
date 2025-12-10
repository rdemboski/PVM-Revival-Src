/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.controllers.InterpolationController;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.GameOperationState;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.PositionUpdateListLMToken;
/*    */ import com.funcom.tcg.net.message.PositionUpdateListMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PositionUpdateListProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 25 */     PositionUpdateListMessage posListMsg = (PositionUpdateListMessage)message;
/*    */     
/* 27 */     if (LoadingManager.USE) {
/*    */ 
/*    */       
/* 30 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new PositionUpdateListLMToken(posListMsg, ioHandler, creatureDataMap, playerDataMap, unknownCreatureDataMap, unknownPlayerDataMap), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_POSITION_UPDATE);
/*    */     }
/*    */     else {
/*    */       
/* 34 */       PositionUpdateListMessage.PositionIterator posIterator = posListMsg.getPositionIterator();
/*    */       
/* 36 */       while (posIterator.hasMoreIDs()) {
/* 37 */         CreatureData data; PropNode propNode; int id = posIterator.nextID();
/*    */         
/* 39 */         int creatureType = posIterator.getCreatureType();
/*    */ 
/*    */         
/* 42 */         if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/* 43 */           PropNodeRegister propNodeRegister = TcgGame.getPropNodeRegister();
/* 44 */           propNode = propNodeRegister.getPropNode(Integer.valueOf(id));
/* 45 */           data = playerDataMap.get(Integer.valueOf(id));
/*    */         } else {
/* 47 */           PropNodeRegister propNodeRegister = TcgGame.getMonsterRegister();
/* 48 */           propNode = propNodeRegister.getPropNode(Integer.valueOf(id));
/* 49 */           data = creatureDataMap.get(Integer.valueOf(id));
/*    */         } 
/*    */         
/* 52 */         if (propNode != null && data != null) {
/*    */           
/* 54 */           WorldCoordinate prevCoord = data.getNetworkCoord();
/* 55 */           float prevAngle = data.getNetworkAngle();
/* 56 */           posIterator.evalPosition(prevCoord);
/* 57 */           float recentAngle = posIterator.getAngle();
/*    */           
/* 59 */           updateNetworkCoord(propNode, prevCoord, recentAngle);
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   private void updateNetworkCoord(PropNode prop, WorldCoordinate prevCoord, float angle) {
/* 68 */     InterpolationController cont = GameOperationState.findInterpolationController(prop);
/* 69 */     if (cont == null) {
/*    */       
/* 71 */       prop.getPosition().set(prevCoord);
/* 72 */       prop.setAngle(angle);
/*    */     } else {
/* 74 */       cont.moveToPosition(prevCoord, angle);
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 79 */     return 10;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\PositionUpdateListProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */