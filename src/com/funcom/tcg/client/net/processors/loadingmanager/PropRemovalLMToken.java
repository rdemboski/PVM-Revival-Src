/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.rpgengine2.creatures.RpgCreatureConstants;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.PropNodeRegister;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.net.message.PropRemovalMessage;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PropRemovalLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 23 */   private Map<Integer, PropNode> debugMap = new HashMap<Integer, PropNode>();
/* 24 */   PropRemovalMessage propRemovalMessage = null;
/* 25 */   GameIOHandler ioHandler = null;
/* 26 */   Map<Integer, CreatureData> creatureDataMap = null;
/* 27 */   Map<Integer, CreatureData> playerDataMap = null;
/*    */ 
/*    */   
/*    */   public PropRemovalLMToken(PropRemovalMessage propRemovalMessage, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap) {
/* 31 */     this.propRemovalMessage = propRemovalMessage;
/* 32 */     this.ioHandler = ioHandler;
/* 33 */     this.creatureDataMap = creatureDataMap;
/* 34 */     this.playerDataMap = playerDataMap;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() throws Exception {
/* 40 */     if (this.propRemovalMessage.getType().equals(RpgCreatureConstants.Type.LOOT_CREATURE_TYPE_ID)) {
/* 41 */       PropNodeRegister propNodeRegister = TcgGame.getMonsterRegister();
/* 42 */       removeFromRegister(this.propRemovalMessage, propNodeRegister);
/* 43 */     } else if (this.propRemovalMessage.getType().equals(RpgCreatureConstants.Type.TOWN_PORTAL)) {
/* 44 */       PropNodeRegister propNodeRegister = TcgGame.getTownPortalRegister();
/* 45 */       removeFromRegister(this.propRemovalMessage, propNodeRegister);
/* 46 */     } else if (this.propRemovalMessage.getType().equals(RpgCreatureConstants.Type.WAYPOINT_DESTINATION_PORTAL)) {
/* 47 */       PropNodeRegister propNodeRegister = TcgGame.getWaypointDestinationRegister();
/* 48 */       removeFromRegister(this.propRemovalMessage, propNodeRegister);
/* 49 */     } else if (this.propRemovalMessage.getType().equals(RpgCreatureConstants.Type.WAYPOINT)) {
/* 50 */       PropNodeRegister propNodeRegister = TcgGame.getWaypointRegister();
/* 51 */       removeFromRegister(this.propRemovalMessage, propNodeRegister);
/* 52 */     } else if (this.propRemovalMessage.getType().equals(RpgCreatureConstants.Type.RETURN_POINT)) {
/* 53 */       PropNodeRegister propNodeRegister = TcgGame.getReturnPointRegister();
/* 54 */       removeFromRegister(this.propRemovalMessage, propNodeRegister);
/*    */     } 
/*    */     
/* 57 */     return 3;
/*    */   }
/*    */   
/*    */   private void removeFromRegister(PropRemovalMessage propRemovalMessage, PropNodeRegister nodeRegister) {
/* 61 */     PropNode propNode = nodeRegister.getPropNode(Integer.valueOf(propRemovalMessage.getId()));
/* 62 */     if (propNode != null) {
/* 63 */       nodeRegister.removePropNode(propNode);
/* 64 */       propNode.removeFromParent();
/* 65 */       propNode.getEffects().removeAllParticles();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\PropRemovalLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */