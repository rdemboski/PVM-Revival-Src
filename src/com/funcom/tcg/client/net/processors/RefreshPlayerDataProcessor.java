/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.RefreshPlayerDataLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.pets3.PetsWindow;
/*    */ import com.funcom.tcg.net.message.RefreshPlayerDataMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RefreshPlayerDataProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 23 */     RefreshPlayerDataMessage refreshMessage = (RefreshPlayerDataMessage)message;
/*    */     
/* 25 */     if (LoadingManager.USE) {
/* 26 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new RefreshPlayerDataLMToken(refreshMessage, ioHandler, creatureDataMap, playerDataMap));
/*    */     
/*    */     }
/*    */     else {
/*    */       
/* 31 */       PetsWindow window = MainGameState.getPetsWindow();
/* 32 */       if (window != null) {
/* 33 */         window.dismiss();
/* 34 */         MainGameState.setPetsWindow(null);
/*    */       } 
/*    */       
/* 37 */       LocalClientPlayer playerModel = MainGameState.getPlayerModel();
/*    */       
/* 39 */       playerModel.setName(refreshMessage.getNick());
/* 40 */       playerModel.setSelectedPetsFromClassIds(refreshMessage.getSelectedPetClassIds());
/* 41 */       playerModel.setActivePetFromClassId(refreshMessage.getActivePetClassId());
/* 42 */       MainGameState.setVisitedMaps(refreshMessage.getVisitedMaps());
/*    */       
/* 44 */       playerModel.refreshSelectedPets();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 50 */     return 71;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\RefreshPlayerDataProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */