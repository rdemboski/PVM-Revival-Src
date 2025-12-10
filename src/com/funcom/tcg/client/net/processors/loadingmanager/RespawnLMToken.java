/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.command.Command;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.command.ExecuteDFXCommand;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.RespawnMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RespawnLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 29 */   private Map<Integer, PropNode> debugMap = new HashMap<Integer, PropNode>();
/* 30 */   GameIOHandler ioHandler = null;
/* 31 */   Map<Integer, CreatureData> creatureDataMap = null;
/* 32 */   Map<Integer, CreatureData> playerDataMap = null;
/* 33 */   Message message = null;
/*    */ 
/*    */   
/*    */   public RespawnLMToken(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap) {
/* 37 */     this.message = message;
/* 38 */     this.ioHandler = ioHandler;
/* 39 */     this.creatureDataMap = creatureDataMap;
/* 40 */     this.playerDataMap = playerDataMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 46 */     BuiSystem.removeWindow((BWindow)MainGameState.getRespawnWindow());
/*    */ 
/*    */     
/* 49 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 50 */     killCorpse((ClientPlayer)localClientPlayer);
/*    */     
/* 52 */     localClientPlayer.setPosition(reconstructPosition((RespawnMessage)this.message));
/* 53 */     localClientPlayer.setRotation(((RespawnMessage)this.message).getAngle());
/* 54 */     localClientPlayer.updateStats(((RespawnMessage)this.message).getStats());
/*    */     
/* 56 */     PropNode playerNode = MainGameState.getPlayerNode();
/* 57 */     playerNode.initializeFloatingText2D(TcgGame.getResourceGetter());
/* 58 */     if (!playerNode.hasChild(playerNode.getRepresentation())) {
/* 59 */       playerNode.attachRepresentation(playerNode.getRepresentation());
/*    */     }
/*    */     
/* 62 */     MainGameState.getWorld().setPlayerNode(playerNode);
/* 63 */     playerNode.updateRenderState();
/*    */     
/* 65 */     TcgGame.getPropNodeRegister().addPropNode(playerNode);
/*    */     
/* 67 */     localClientPlayer.live();
/*    */     
/* 69 */     return true;
/*    */   }
/*    */   
/*    */   private WorldCoordinate reconstructPosition(RespawnMessage respawnMessage) {
/* 73 */     WorldCoordinate worldCoordinate = respawnMessage.getPosition();
/* 74 */     worldCoordinate.setMapId(MainGameState.getPlayerModel().getPosition().getMapId());
/* 75 */     worldCoordinate.setInstanceReference(MainGameState.getPlayerModel().getPosition().getInstanceReference());
/* 76 */     return worldCoordinate;
/*    */   }
/*    */   
/*    */   private void killCorpse(ClientPlayer playerModel) {
/* 80 */     PropNode corpse = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(playerModel.getId()));
/* 81 */     if (corpse != null) {
/* 82 */       Command currentCommand = ((Creature)corpse.getProp()).getCurrentCommand();
/* 83 */       if (currentCommand instanceof ExecuteDFXCommand) {
/* 84 */         ((ExecuteDFXCommand)currentCommand).forceFinish();
/*    */       } else {
/* 86 */         corpse.removeFromParent();
/* 87 */         corpse.getEffects().removeAllParticles();
/*    */       } 
/* 89 */       TcgGame.getPropNodeRegister().removePropNode(corpse);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\RespawnLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */