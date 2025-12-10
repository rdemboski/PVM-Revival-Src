/*    */ package com.funcom.tcg.client.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.items.ItemManager;
/*    */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*    */ import com.funcom.rpgengine2.pickupitems.ItemPickUpDescription;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ 
/*    */ public class ScrollItemTextureLoader extends AbstractCardTextureLoader {
/*    */   private final ItemManager itemManager;
/*    */   
/*    */   public ScrollItemTextureLoader(ResourceManager resourceManager, ItemManager itemManager) {
/* 15 */     super(resourceManager);
/* 16 */     this.itemManager = itemManager;
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getIconPath(AbstractPickUpDescription pickUpDescription) {
/* 21 */     ItemDescription skillItem = getSkillItem(pickUpDescription);
/* 22 */     return skillItem.getIcon();
/*    */   }
/*    */ 
/*    */   
/*    */   protected Element getCardElement(AbstractPickUpDescription pickUpDescription) {
/* 27 */     ItemDescription skillItem = getSkillItem(pickUpDescription);
/* 28 */     return skillItem.getElement();
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getCardFront(AbstractPickUpDescription pickUpDescription) {
/* 33 */     return TcgGame.getRpgLoader().getElementManager().getElementDesc(getCardElement(pickUpDescription)).getPetCardImage();
/*    */   }
/*    */ 
/*    */   
/*    */   protected int getCardTier(AbstractPickUpDescription pickUpDescription) {
/* 38 */     ItemDescription skillItem = getSkillItem(pickUpDescription);
/* 39 */     return skillItem.getTier();
/*    */   }
/*    */   
/*    */   private ItemDescription getSkillItem(AbstractPickUpDescription pickUpDescription) {
/* 43 */     ItemDescription scrollItem = ((ItemPickUpDescription)pickUpDescription).getItemDescription();
/* 44 */     String scrollItemId = scrollItem.getId();
/* 45 */     String skillItemId = scrollItemId.substring(0, scrollItemId.length() - "-scroll".length());
/* 46 */     return this.itemManager.getDescription(skillItemId, scrollItem.getTier());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\ScrollItemTextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */