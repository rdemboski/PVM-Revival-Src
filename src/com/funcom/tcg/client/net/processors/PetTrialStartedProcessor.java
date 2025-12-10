/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.PetTrialStartMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PetTrialStartedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 21 */     PetTrialStartMessage startMessage = (PetTrialStartMessage)message;
/*    */     
/* 23 */     ClientPet pet = MainGameState.getPlayerModel().getCollectedPetForId(startMessage.getPetId());
/* 24 */     pet.setOnTrial(true);
/* 25 */     pet.setPetTrialExpireTime(System.currentTimeMillis() + startMessage.getTimeLeft());
/*    */     
/* 27 */     if (startMessage.isSetActive()) {
/* 28 */       LocalClientPlayer player = MainGameState.getPlayerModel();
/* 29 */       player.setSelectedPet(1, pet);
/* 30 */       player.setActivePet(pet);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 37 */     return 238;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\PetTrialStartedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */