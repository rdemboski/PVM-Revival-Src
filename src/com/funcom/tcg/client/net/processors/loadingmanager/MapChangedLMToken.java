/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.commons.FileUtils;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MapChangedLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 28 */   MapChangedMessage mapChangedMessage = null;
/* 29 */   Map<Integer, CreatureData> creatureDataMap = null;
/* 30 */   Map<Integer, CreatureData> playerDataMap = null;
/* 31 */   GameIOHandler ioHandler = null;
/*    */ 
/*    */   
/*    */   public MapChangedLMToken(MapChangedMessage mapChangedMessage, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap) {
/* 35 */     this.mapChangedMessage = mapChangedMessage;
/* 36 */     this.ioHandler = ioHandler;
/* 37 */     this.creatureDataMap = creatureDataMap;
/* 38 */     this.playerDataMap = playerDataMap;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int update() throws Exception {
/* 45 */     LoadingManager.INSTANCE.clearLoadingTokens();
/*    */     
/* 47 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/*    */     
/* 49 */     if (MainGameState.getWorld().isFullLoading()) return 3;
/*    */     
/* 51 */     String mapId = this.mapChangedMessage.getMapId();
/* 52 */     mapId = FileUtils.trimTailingSlashes(mapId);
/*    */ 
/*    */     
/* 55 */     LoadingWindow loadingWindow = MainGameState.getLoadingWindow();
/*    */     
/* 57 */     if (MainGameState.getMainHud() != null) {
/* 58 */       MainGameState.getMainHud().getPortalButton().setEnabled((!mapId.contains("research_centre") && MainGameState.getHudModel().isTownPortalEnabled()));
/*    */     }
/*    */     
/* 61 */     if (mapId.equals(localClientPlayer.getPosition().getMapId()) && !loadingWindow.isHit()) {
/* 62 */       localClientPlayer.getPosition().set(this.mapChangedMessage.getWorldCoordinate());
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/*    */     }
/* 70 */     else if (TcgUI.isWindowOpen(LoadingWindow.class) || !mapId.equals(localClientPlayer.getPosition().getMapId()) || loadingWindow.isHit()) {
/*    */ 
/*    */       
/* 73 */       CreatureData creatureData = this.playerDataMap.get(Integer.valueOf(localClientPlayer.getId()));
/* 74 */       this.creatureDataMap.clear();
/* 75 */       this.playerDataMap.clear();
/* 76 */       this.playerDataMap.put(Integer.valueOf(localClientPlayer.getId()), creatureData);
/*    */       
/* 78 */       localClientPlayer.setPosition(this.mapChangedMessage.getWorldCoordinate());
/*    */       
/* 80 */       PropNode playerNode = MainGameState.getPlayerNode();
/* 81 */       playerNode.getEffects().removeVisibleParticles();
/* 82 */       MainGameState.getWorld().loadMap(mapId, this.ioHandler);
/* 83 */       List<String> visitedMaps = MainGameState.getVisitedMaps();
/* 84 */       if (!visitedMaps.contains(mapId))
/* 85 */         visitedMaps.add(mapId); 
/*    */     } else {
/* 87 */       localClientPlayer.getPosition().set(this.mapChangedMessage.getWorldCoordinate());
/*    */     } 
/*    */ 
/*    */     
/* 91 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\MapChangedLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */