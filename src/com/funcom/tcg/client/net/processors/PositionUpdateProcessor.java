/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.controllers.InterpolationController;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.GameOperationState;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.PositionUpdateLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.PositionUpdateMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PositionUpdateProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 29 */     PositionUpdateMessage posMessage = (PositionUpdateMessage)message;
/*    */     
/* 31 */     if (LoadingManager.USE) {
/*    */ 
/*    */ 
/*    */       
/* 35 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new PositionUpdateLMToken(posMessage, ioHandler, creatureDataMap, playerDataMap), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */     }
/*    */     else {
/*    */       
/* 39 */       int identity = posMessage.getIdentity();
/* 40 */       WorldCoordinate newPosition = reconstructWorldCoordinate(posMessage);
/* 41 */       float angle = posMessage.getAngle();
/* 42 */       PropNode prop = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(identity));
/*    */       
/* 44 */       if (prop != null) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 51 */         InterpolationController cont = GameOperationState.findInterpolationController(prop);
/* 52 */         if (cont == null) {
/* 53 */           prop.getProp().setPosition(newPosition);
/* 54 */           prop.getProp().setRotation(angle);
/*    */         } else {
/* 56 */           cont.moveToPosition(newPosition, angle);
/*    */         } 
/*    */         
/* 59 */         if (prop.getProp() instanceof Creature) {
/* 60 */           Creature c = (Creature)prop.getProp();
/* 61 */           Command cmd = c.getCurrentCommand();
/* 62 */           if (cmd instanceof com.funcom.gameengine.model.command.MoveCommand) {
/* 63 */             c.clearQueue();
/*    */           }
/*    */         } 
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   private WorldCoordinate reconstructWorldCoordinate(PositionUpdateMessage posMessage) {
/* 71 */     WorldCoordinate worldCoordinate = posMessage.getWorldCoordinate();
/* 72 */     worldCoordinate.setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/* 73 */     worldCoordinate.setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/* 74 */     return worldCoordinate;
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 78 */     return 9;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\PositionUpdateProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */