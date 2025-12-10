/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.hud.SubscribeWindow;
/*    */ import com.funcom.tcg.net.message.PetTrialFinishedMessage;
/*    */ import com.jmex.bui.BWindow;
/*    */ import com.jmex.bui.BuiSystem;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PetTrialFinishedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 26 */     PetTrialFinishedMessage finishedMessage = (PetTrialFinishedMessage)message;
/*    */     
/* 28 */     MainGameState.getPlayerModel().getCollectedPetForId(finishedMessage.getPetId());
/* 29 */     ClientPet pet = MainGameState.getPlayerModel().getCollectedPetForId(finishedMessage.getPetId());
/* 30 */     pet.setOnTrial(false);
/* 31 */     pet.setPetTrialExpireTime(0L);
/*    */     
/* 33 */     if (!MainGameState.getPlayerModel().getActivePet().getClassId().equals(finishedMessage.getActivePetId())) {
/* 34 */       ClientPet activePet = MainGameState.getPlayerModel().getCollectedPetForId(finishedMessage.getActivePetId());
/* 35 */       LocalClientPlayer player = MainGameState.getPlayerModel();
/* 36 */       player.setSelectedPet(1, activePet);
/* 37 */       player.setActivePet(activePet);
/*    */ 
/*    */       
/* 40 */       SubscribeWindow window = new SubscribeWindow(TcgGame.getResourceManager(), MainGameState.isPlayerRegistered(), MainGameState.isPlayerRegistered() ? "subscribedialog.button.subscribe" : "quitwindow.askregister.ok", "subscribedialog.button.cancel", MainGameState.isPlayerRegistered() ? "popup.pettrial.end" : "popup.pettrial.end.notsaved");
/*    */ 
/*    */ 
/*    */       
/* 44 */       window.setTopLayout(true);
/* 45 */       window.setLayer(101);
/* 46 */       BuiSystem.getRootNode().addWindow((BWindow)window);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 53 */     return 239;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\PetTrialFinishedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */