/*    */ package com.funcom.tcg.client.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.ItemPickUpDescription;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ArmourItemTextureLoader
/*    */   extends AbstractCardTextureLoader
/*    */ {
/*    */   public ArmourItemTextureLoader(ResourceManager resourceManager) {
/* 16 */     super(resourceManager);
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getIconPath(AbstractPickUpDescription pickUpDescription) {
/* 21 */     ItemDescription skillItem = getItem(pickUpDescription);
/* 22 */     return skillItem.getIcon();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Element getCardElement(AbstractPickUpDescription pickUpDescription) {
/* 27 */     ItemDescription skillItem = getItem(pickUpDescription);
/* 28 */     return skillItem.getElement();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getCardFront(AbstractPickUpDescription pickUpDescription) {
/* 33 */     return TcgGame.getVisualRegistry().getImageStringForRarity(String.valueOf(pickUpDescription.getTier()));
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getCardTier(AbstractPickUpDescription pickUpDescription) {
/* 38 */     ItemDescription skillItem = getItem(pickUpDescription);
/* 39 */     return skillItem.getTier();
/*    */   }
/*    */   
/*    */   private ItemDescription getItem(AbstractPickUpDescription pickUpDescription) {
/* 43 */     return ((ItemPickUpDescription)pickUpDescription).getItemDescription();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\ArmourItemTextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */