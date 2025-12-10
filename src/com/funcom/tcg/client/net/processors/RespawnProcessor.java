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
/*    */ import com.funcom.tcg.client.command.ExecuteDFXCommand;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.RespawnLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.RespawnMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class RespawnProcessor
/*    */   implements MessageProcessor {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 28 */     if (LoadingManager.USE) {
/* 29 */       LoadingManager.INSTANCE.submitByPriority((LoadingManagerToken)new RespawnLMToken(message, ioHandler, creatureDataMap, playerDataMap), PrioritizedLoadingTokenQueue.TokenPriority.PRIORITY_CREATURES);
/*    */     }
/*    */     else {
/*    */       
/* 33 */       BuiSystem.removeWindow((BWindow)MainGameState.getRespawnWindow());
/*    */ 
/*    */       
/* 36 */       LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 37 */       killCorpse((ClientPlayer)localClientPlayer);
/*    */       
/* 39 */       localClientPlayer.setPosition(reconstructPosition((RespawnMessage)message));
/* 40 */       localClientPlayer.setRotation(((RespawnMessage)message).getAngle());
/* 41 */       localClientPlayer.updateStats(((RespawnMessage)message).getStats());
/*    */       
/* 43 */       PropNode playerNode = MainGameState.getPlayerNode();
/* 44 */       playerNode.initializeFloatingText2D(TcgGame.getResourceGetter());
/* 45 */       if (!playerNode.hasChild(playerNode.getRepresentation())) {
/* 46 */         playerNode.attachRepresentation(playerNode.getRepresentation());
/*    */       }
/*    */       
/* 49 */       MainGameState.getWorld().setPlayerNode(playerNode);
/* 50 */       playerNode.updateRenderState();
/*    */       
/* 52 */       TcgGame.getPropNodeRegister().addPropNode(playerNode);
/*    */       
/* 54 */       localClientPlayer.live();
/*    */     } 
/*    */   }
/*    */   
/*    */   private WorldCoordinate reconstructPosition(RespawnMessage respawnMessage) {
/* 59 */     WorldCoordinate worldCoordinate = respawnMessage.getPosition();
/* 60 */     worldCoordinate.setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/* 61 */     worldCoordinate.setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/* 62 */     return worldCoordinate;
/*    */   }
/*    */   
/*    */   private void killCorpse(ClientPlayer playerModel) {
/* 66 */     PropNode corpse = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(playerModel.getId()));
/* 67 */     if (corpse != null) {
/* 68 */       Command currentCommand = ((Creature)corpse.getProp()).getCurrentCommand();
/* 69 */       if (currentCommand instanceof ExecuteDFXCommand) {
/* 70 */         ((ExecuteDFXCommand)currentCommand).forceFinish();
/*    */       } else {
/* 72 */         corpse.removeFromParent();
/* 73 */         corpse.getEffects().removeAllParticles();
/*    */       } 
/* 75 */       TcgGame.getPropNodeRegister().removePropNode(corpse);
/*    */     } 
/*    */   }
/*    */   
/*    */   public short getMessageType() {
/* 80 */     return 206;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\RespawnProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */