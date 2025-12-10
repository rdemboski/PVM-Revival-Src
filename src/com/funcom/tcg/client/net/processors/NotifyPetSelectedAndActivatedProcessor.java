/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.view.PropNode;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPet;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*    */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.NotifyPetSelectedAndActivatedMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NotifyPetSelectedAndActivatedProcessor
/*    */   implements MessageProcessor
/*    */ {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 23 */     NotifyPetSelectedAndActivatedMessage petMessage = (NotifyPetSelectedAndActivatedMessage)message;
/*    */     
/* 25 */     PropNode prop = TcgGame.getPropNodeRegister().getPropNode(Integer.valueOf(petMessage.getCharacterId()));
/*    */     
/* 27 */     if (prop != null) {
/* 28 */       ClientPlayer otherPlayer = (ClientPlayer)prop.getProp();
/* 29 */       PetRegistry petRegistry = MainGameState.getPetRegistry();
/* 30 */       ClientPetDescription petDesc = petRegistry.getPetForClassId(petMessage.getPetId());
/* 31 */       ClientPetDescription updatedClientPetDescription = petRegistry.getPetForClassId(petMessage.getPetId() + "-upgrade");
/* 32 */       ClientPet selectedPet = new ClientPet(petDesc, (updatedClientPetDescription == null) ? petDesc : updatedClientPetDescription);
/* 33 */       otherPlayer.addPet(selectedPet);
/* 34 */       otherPlayer.setSelectedPet(petMessage.getSlot(), selectedPet);
/* 35 */       otherPlayer.setActivePetFromClassId(petMessage.getPetId());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 41 */     return 36;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\NotifyPetSelectedAndActivatedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */