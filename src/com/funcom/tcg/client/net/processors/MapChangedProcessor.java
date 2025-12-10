/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.commons.FileUtils;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.MapChangedLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.TcgUI;
/*    */ import com.funcom.tcg.client.ui.hud.LoadingWindow;
/*    */ import com.funcom.tcg.net.message.MapChangedMessage;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapChangedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 28 */     MapChangedMessage mapChangedMessage = (MapChangedMessage)message;
/*    */     
/* 30 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/*    */     
/* 32 */     if (MainGameState.getWorld().isFullLoading())
/*    */       return; 
/* 34 */     String mapId = mapChangedMessage.getMapId();
/* 35 */     mapId = FileUtils.trimTailingSlashes(mapId);
/*    */     
/* 37 */     if (LoadingManager.USE) {
/* 38 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new MapChangedLMToken(mapChangedMessage, ioHandler, creatureDataMap, playerDataMap));
/*    */     }
/*    */     else {
/*    */       
/* 42 */       LoadingWindow loadingWindow = (LoadingWindow)TcgUI.getWindowFromClass(LoadingWindow.class);
/*    */       
/* 44 */       if (loadingWindow == null) {
/*    */         return;
/*    */       }
/* 47 */       if (mapId.equals(localClientPlayer.getPosition().getMapId()) && !loadingWindow.isHit()) {
/* 48 */         localClientPlayer.getPosition().set(mapChangedMessage.getWorldCoordinate());
/*    */       
/*    */       }
/* 51 */       else if (TcgUI.isWindowOpen(LoadingWindow.class) || !mapId.equals(localClientPlayer.getPosition().getMapId()) || loadingWindow.isHit()) {
/*    */ 
/*    */         
/* 54 */         CreatureData creatureData = playerDataMap.get(Integer.valueOf(localClientPlayer.getId()));
/* 55 */         creatureDataMap.clear();
/* 56 */         playerDataMap.clear();
/* 57 */         playerDataMap.put(Integer.valueOf(localClientPlayer.getId()), creatureData);
/*    */         
/* 59 */         localClientPlayer.setPosition(mapChangedMessage.getWorldCoordinate());
/*    */         
/* 61 */         PropNode playerNode = MainGameState.getPlayerNode();
/* 62 */         playerNode.getEffects().removeVisibleParticles();
/* 63 */         MainGameState.getWorld().loadMap(mapId, ioHandler);
/* 64 */         List<String> visitedMaps = MainGameState.getVisitedMaps();
/* 65 */         if (!visitedMaps.contains(mapId))
/* 66 */           visitedMaps.add(mapId); 
/*    */       } else {
/* 68 */         localClientPlayer.getPosition().set(mapChangedMessage.getWorldCoordinate());
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 76 */     return 27;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\MapChangedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */