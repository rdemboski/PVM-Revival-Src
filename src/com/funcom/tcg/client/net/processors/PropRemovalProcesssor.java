/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.PropRemovalLMToken;
/*    */ import com.funcom.tcg.net.message.PropRemovalMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PropRemovalProcesssor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 22 */     PropRemovalMessage propRemovalMessage = (PropRemovalMessage)message;
/*    */     
/* 24 */     if (LoadingManager.USE) {
/*    */ 
/*    */       
/* 27 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new PropRemovalLMToken(propRemovalMessage, ioHandler, creatureDataMap, playerDataMap), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */ 
/*    */     
/*    */     }
/* 31 */     else if (propRemovalMessage.getType().equals(RpgCreatureConstants.Type.LOOT_CREATURE_TYPE_ID)) {
/* 32 */       PropNodeRegister propNodeRegister = TcgGame.getMonsterRegister();
/* 33 */       removeFromRegister(propRemovalMessage, propNodeRegister);
/* 34 */     } else if (propRemovalMessage.getType().equals(RpgCreatureConstants.Type.TOWN_PORTAL)) {
/* 35 */       PropNodeRegister propNodeRegister = TcgGame.getTownPortalRegister();
/* 36 */       removeFromRegister(propRemovalMessage, propNodeRegister);
/* 37 */     } else if (propRemovalMessage.getType().equals(RpgCreatureConstants.Type.WAYPOINT_DESTINATION_PORTAL)) {
/* 38 */       PropNodeRegister propNodeRegister = TcgGame.getWaypointDestinationRegister();
/* 39 */       removeFromRegister(propRemovalMessage, propNodeRegister);
/* 40 */     } else if (propRemovalMessage.getType().equals(RpgCreatureConstants.Type.WAYPOINT)) {
/* 41 */       PropNodeRegister propNodeRegister = TcgGame.getWaypointRegister();
/* 42 */       removeFromRegister(propRemovalMessage, propNodeRegister);
/* 43 */     } else if (propRemovalMessage.getType().equals(RpgCreatureConstants.Type.RETURN_POINT)) {
/* 44 */       PropNodeRegister propNodeRegister = TcgGame.getReturnPointRegister();
/* 45 */       removeFromRegister(propRemovalMessage, propNodeRegister);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private void removeFromRegister(PropRemovalMessage propRemovalMessage, PropNodeRegister nodeRegister) {
/* 51 */     PropNode propNode = nodeRegister.getPropNode(Integer.valueOf(propRemovalMessage.getId()));
/* 52 */     if (propNode != null) {
/* 53 */       nodeRegister.removePropNode(propNode);
/* 54 */       propNode.removeFromParent();
/* 55 */       propNode.getEffects().removeAllParticles();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 61 */     return 51;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\PropRemovalProcesssor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */