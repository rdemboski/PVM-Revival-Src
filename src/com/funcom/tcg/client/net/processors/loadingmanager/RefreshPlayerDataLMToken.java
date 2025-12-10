/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.pets3.PetsWindow;
/*    */ import com.funcom.tcg.net.message.RefreshPlayerDataMessage;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefreshPlayerDataLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 23 */   private Map<Integer, PropNode> debugMap = new HashMap<Integer, PropNode>();
/* 24 */   GameIOHandler ioHandler = null;
/* 25 */   Map<Integer, CreatureData> creatureDataMap = null;
/* 26 */   Map<Integer, CreatureData> playerDataMap = null;
/* 27 */   RefreshPlayerDataMessage refreshMessage = null;
/*    */ 
/*    */   
/*    */   public RefreshPlayerDataLMToken(RefreshPlayerDataMessage refreshMessage, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap) {
/* 31 */     this.refreshMessage = refreshMessage;
/* 32 */     this.ioHandler = ioHandler;
/* 33 */     this.creatureDataMap = creatureDataMap;
/* 34 */     this.playerDataMap = playerDataMap;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean processGame() throws Exception {
/* 41 */     PetsWindow window = MainGameState.getPetsWindow();
/* 42 */     if (window != null) {
/* 43 */       window.dismiss();
/* 44 */       MainGameState.setPetsWindow(null);
/*    */     } 
/*    */     
/* 47 */     LocalClientPlayer playerModel = MainGameState.getPlayerModel();
/*    */     
/* 49 */     playerModel.setName(this.refreshMessage.getNick());
/* 50 */     playerModel.setSelectedPetsFromClassIds(this.refreshMessage.getSelectedPetClassIds());
/* 51 */     playerModel.setActivePetFromClassId(this.refreshMessage.getActivePetClassId());
/* 52 */     MainGameState.setVisitedMaps(this.refreshMessage.getVisitedMaps());
/*    */     
/* 54 */     playerModel.refreshSelectedPets();
/*    */     
/* 56 */     return true;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\RefreshPlayerDataLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */