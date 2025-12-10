/*    */ package com.funcom.tcg.client.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.PetPickUpDescription;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*    */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*    */ 
/*    */ public class PetTextureLoader
/*    */   extends AbstractCardTextureLoader {
/*    */   private final PetRegistry petRegistry;
/*    */   
/*    */   public PetTextureLoader(ResourceManager resourceManager, PetRegistry petRegistry) {
/* 16 */     super(resourceManager);
/* 17 */     this.petRegistry = petRegistry;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getIconPath(AbstractPickUpDescription pickUpDescription) {
/* 22 */     ClientPetDescription clientPet = getClientPet(pickUpDescription);
/* 23 */     return clientPet.getIcon();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Element getCardElement(AbstractPickUpDescription pickUpDescription) {
/* 28 */     ClientPetDescription clientPet = getClientPet(pickUpDescription);
/* 29 */     return Element.valueOf(clientPet.getElementId().toUpperCase());
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getCardFront(AbstractPickUpDescription pickUpDescription) {
/* 34 */     return TcgGame.getRpgLoader().getElementManager().getElementDesc(getCardElement(pickUpDescription)).getPetCardImage();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getCardTier(AbstractPickUpDescription pickUpDescription) {
/* 39 */     ClientPetDescription clientPet = getClientPet(pickUpDescription);
/* 40 */     return clientPet.getTier();
/*    */   }
/*    */   
/*    */   private ClientPetDescription getClientPet(AbstractPickUpDescription pickUpDescription) {
/* 44 */     PetPickUpDescription petPickUpDescription = (PetPickUpDescription)pickUpDescription;
/* 45 */     return this.petRegistry.getPetForClassId(petPickUpDescription.getPetDescription().getId());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\PetTextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */