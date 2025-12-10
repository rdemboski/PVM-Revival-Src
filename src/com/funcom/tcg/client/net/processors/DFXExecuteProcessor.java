/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.NoSuchDFXException;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.PrioritizedLoadingTokenQueue;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.command.ExecuteDFXCommand;
/*    */ import com.funcom.tcg.client.controllers.InterpolationController;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.GameOperationState;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.DFXExecuteProcessorLMToken;
/*    */ import com.funcom.tcg.net.message.DFXExecuteMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DFXExecuteProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 33 */     DFXExecuteMessage dfxExecuteMessage = (DFXExecuteMessage)message;
/* 34 */     processTarget(dfxExecuteMessage.getCreatureId(), dfxExecuteMessage.getDfxScript(), dfxExecuteMessage.getLocalTime(), RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void processTarget(int id, String dfxScript, float time, int creatureType) {
/* 40 */     if (LoadingManager.USE) {
/* 41 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new DFXExecuteProcessorLMToken(id, dfxScript, time, creatureType), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */     } else {
/*    */       PropNode propNode;
/*    */ 
/*    */ 
/*    */       
/* 47 */       if (creatureType == RpgCreatureConstants.Type.PLAYER_CREATURE_TYPE_ID.getTypeId()) {
/* 48 */         PropNodeRegister propNodeRegister = TcgGame.getPropNodeRegister();
/* 49 */         propNode = propNodeRegister.getPropNode(Integer.valueOf(id));
/*    */       } else {
/* 51 */         PropNodeRegister propNodeRegister = TcgGame.getMonsterRegister();
/* 52 */         propNode = propNodeRegister.getPropNode(Integer.valueOf(id));
/*    */       } 
/*    */ 
/*    */       
/* 56 */       if (propNode != null) {
/*    */         try {
/* 58 */           if (!dfxScript.isEmpty()) {
/* 59 */             DireEffectDescription effectDescription = propNode.getEffectDescriptionFactory().getDireEffectDescription(dfxScript, false);
/* 60 */             DireEffect effect = effectDescription.createInstance(propNode, UsageParams.EMPTY_PARAMS);
/*    */             
/* 62 */             ExecuteDFXCommand executeDFXCommand = new ExecuteDFXCommand(propNode, effect, time);
/* 63 */             if (executeDFXCommand.isFinished()) {
/* 64 */               executeDFXCommand.update(0.0F);
/*    */             
/*    */             }
/*    */             else {
/*    */               
/* 69 */               InterpolationController cont = GameOperationState.findInterpolationController(propNode);
/* 70 */               if (cont != null) {
/* 71 */                 cont.stop();
/*    */               }
/*    */ 
/*    */               
/* 75 */               ((Creature)propNode.getProp()).immediateCommand((Command)executeDFXCommand);
/*    */             } 
/*    */           } 
/* 78 */         } catch (NoSuchDFXException e) {
/* 79 */           e.printStackTrace();
/*    */         } 
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 86 */     return 225;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\DFXExecuteProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */