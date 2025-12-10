/*    */ package com.funcom.tcg.client.net.processors.loadingmanager;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.loadingmanager.LoadingManagerToken;
/*    */ import com.funcom.gameengine.view.DfxTextWindowManager;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.DefaultPickUpType;
/*    */ import com.funcom.rpgengine2.pickupitems.ItemPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.PetPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.PickupType;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.net.message.PickUpLootConvertedMessage;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PickUpLootConvetedLMToken
/*    */   extends LoadingManagerToken
/*    */ {
/* 21 */   private PickUpLootConvertedMessage convertedMessage = null;
/*    */   
/*    */   public PickUpLootConvetedLMToken(PickUpLootConvertedMessage convertedMessage) {
/* 24 */     this.convertedMessage = convertedMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public int update() throws Exception {
/*    */     String name;
/* 30 */     AbstractPickUpDescription pickUpDesc = TcgGame.getRpgLoader().getPickUpManager().getDescription(this.convertedMessage.getPickUpId());
/*    */     
/* 32 */     if (pickUpDesc != null) {
/* 33 */       PickupType pickUpType = pickUpDesc.getAssociatedType();
/* 34 */       if (pickUpType == DefaultPickUpType.ITEM) {
/* 35 */         name = ((ItemPickUpDescription)pickUpDesc).getItemDescription().getName();
/* 36 */       } else if (pickUpType == DefaultPickUpType.PET) {
/* 37 */         name = ((PetPickUpDescription)pickUpDesc).getPetDescription().getId();
/* 38 */         name = MainGameState.getPetRegistry().getPetForClassId(name).getName();
/*    */       } else {
/* 40 */         return 3;
/*    */       } 
/* 42 */     } else if (TcgGame.getRpgLoader().getItemManager().hasDescription(this.convertedMessage.getPickUpId())) {
/* 43 */       ItemDescription itemDescription = TcgGame.getRpgLoader().getItemManager().getDescription(this.convertedMessage.getPickUpId(), -1);
/* 44 */       name = itemDescription.getName();
/*    */     } else {
/* 46 */       ClientPetDescription clientPet = MainGameState.getPetRegistry().getPetForClassId(this.convertedMessage.getPickUpId());
/* 47 */       name = clientPet.getName();
/*    */     } 
/*    */     
/* 50 */     String text = TcgGame.getLocalizedText("conversion.text", new String[0]);
/* 51 */     text = text.replace("[name]", name);
/* 52 */     DfxTextWindowManager.instance().getWindow("main").showText(text);
/*    */     
/* 54 */     return 3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\net\processors\loadingmanager\PickUpLootConvetedLMToken.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */