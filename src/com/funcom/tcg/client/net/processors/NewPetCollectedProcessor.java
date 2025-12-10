/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*    */ import com.funcom.tcg.client.model.rpg.LocalClientPlayer;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.NewPetCollectedMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NewPetCollectedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 20 */     NewPetCollectedMessage newPetMessage = (NewPetCollectedMessage)message;
/*    */     
/* 22 */     LocalClientPlayer localClientPlayer = MainGameState.getPlayerModel();
/* 23 */     if (localClientPlayer.getId() == newPetMessage.getCharacterId()) {
/* 24 */       ClientPetDescription petDesc = MainGameState.getPetRegistry().getPetForClassId(newPetMessage.getPetId());
/* 25 */       ClientPetDescription updatedClientPetDescription = null;
/* 26 */       if (MainGameState.getPetRegistry().hasPetForClassId(newPetMessage.getPetId() + "-upgrade"))
/* 27 */         updatedClientPetDescription = MainGameState.getPetRegistry().getPetForClassId(newPetMessage.getPetId() + "-upgrade"); 
/* 28 */       ClientPet pet = new ClientPet(petDesc, (updatedClientPetDescription == null) ? petDesc : updatedClientPetDescription);
/* 29 */       pet.setExp(0);
/* 30 */       pet.setLevel(newPetMessage.getLevel());
/* 31 */       localClientPlayer.addPet(pet);
/* 32 */       MainGameState.getRewardWindowController().addReward(pet);
/* 33 */       MainGameState.getRenderPassManager().getGuiParticlesRenderPass().triggerParticleEffect("petCollected");
/* 34 */       MainGameState.getHudModel().triggerPetPickup();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 40 */     return 236;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\NewPetCollectedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */