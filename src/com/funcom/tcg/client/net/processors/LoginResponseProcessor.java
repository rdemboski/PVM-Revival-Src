/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.view.ModularNode;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.LoadingWindow;
/*    */ import com.funcom.tcg.net.message.LoginResponseMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ public class LoginResponseProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 21 */     LoginResponseMessage lrsMessage = (LoginResponseMessage)message;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 30 */     if (TcgGame.getLoginResponse() == message) {
/*    */       return;
/*    */     }
/* 33 */     LoadingWindow loadingWindow = MainGameState.getLoadingWindow();
/* 34 */     loadingWindow.loadMap(lrsMessage.getMapId());
/*    */     
/* 36 */     TcgGame.setServerDomain(lrsMessage.getServerDomain());
/* 37 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 38 */     PropNode playerNode = MainGameState.getPlayerNode();
/* 39 */     localClientPlayer.syncPlayerProperties(lrsMessage);
/* 40 */     ((ModularNode)playerNode.getRepresentation()).reloadCharacter();
/* 41 */     localClientPlayer.setActivePetFromClassId(lrsMessage.getActivePetClassId());
/*    */     
/* 43 */     playerNode.getEffects().removeVisibleParticles();
/*    */     
/* 45 */     CreatureData creatureData = creatureDataMap.get(Integer.valueOf(localClientPlayer.getId()));
/* 46 */     creatureDataMap.clear();
/* 47 */     creatureDataMap.put(Integer.valueOf(localClientPlayer.getId()), creatureData);
/*    */     
/* 49 */     MainGameState.getWorld().loadMap(lrsMessage.getMapId(), ioHandler);
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 54 */     return 6;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\LoginResponseProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */