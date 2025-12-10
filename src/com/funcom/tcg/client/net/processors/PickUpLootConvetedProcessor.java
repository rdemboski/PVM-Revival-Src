/*    */ package com.funcom.tcg.client.net.processors;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManager;
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.DefaultPickUpType;
/*    */ import com.funcom.rpgengine2.pickupitems.ItemPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.PetPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.PickupType;
/*    */ import com.funcom.server.common.GameIOHandler;
/*    */ import com.funcom.server.common.Message;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*    */ import com.funcom.tcg.client.net.CreatureData;
/*    */ import com.funcom.tcg.client.net.MessageProcessor;
/*    */ import com.funcom.tcg.client.net.processors.loadingmanager.PickUpLootConvetedLMToken;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.PickUpLootConvertedMessage;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class PickUpLootConvetedProcessor
/*    */   implements MessageProcessor {
/*    */   public void process(Message message, GameIOHandler ioHandler, Map<Integer, CreatureData> creatureDataMap, Map<Integer, CreatureData> playerDataMap, Map<Integer, CreatureData> unknownCreatureDataMap, Map<Integer, CreatureData> unknownPlayerDataMap) {
/* 26 */     PickUpLootConvertedMessage convertedMessage = (PickUpLootConvertedMessage)message;
/*    */     
/* 28 */     if (LoadingManager.USE) {
/* 29 */       LoadingManager.INSTANCE.submit((LoadingManagerToken)new PickUpLootConvetedLMToken(convertedMessage));
/*    */     } else {
/*    */       String name;
/* 32 */       AbstractPickUpDescription pickUpDesc = TcgGame.getRpgLoader().getPickUpManager().getDescription(convertedMessage.getPickUpId());
/*    */       
/* 34 */       if (pickUpDesc != null) {
/* 35 */         PickupType pickUpType = pickUpDesc.getAssociatedType();
/* 36 */         if (pickUpType == DefaultPickUpType.ITEM) {
/* 37 */           name = ((ItemPickUpDescription)pickUpDesc).getItemDescription().getName();
/* 38 */         } else if (pickUpType == DefaultPickUpType.PET) {
/* 39 */           name = ((PetPickUpDescription)pickUpDesc).getPetDescription().getId();
/* 40 */           name = MainGameState.getPetRegistry().getPetForClassId(name).getName();
/*    */         } else {
/*    */           return;
/*    */         } 
/* 44 */       } else if (TcgGame.getRpgLoader().getItemManager().hasDescription(convertedMessage.getPickUpId())) {
/* 45 */         ItemDescription itemDescription = TcgGame.getRpgLoader().getItemManager().getDescription(convertedMessage.getPickUpId(), -1);
/* 46 */         name = itemDescription.getName();
/*    */       } else {
/* 48 */         ClientPetDescription clientPet = MainGameState.getPetRegistry().getPetForClassId(convertedMessage.getPickUpId());
/* 49 */         name = clientPet.getName();
/*    */       } 
/*    */       
/* 52 */       String text = TcgGame.getLocalizedText("conversion.text", new String[0]);
/* 53 */       text = text.replace("[name]", name);
/* 54 */       DfxTextWindowManager.instance().getWindow("main").showText(text);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public short getMessageType() {
/* 60 */     return 54;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\PickUpLootConvetedProcessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */