/*    */ package com.funcom.rpgengine2.pickupitems;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.items.ItemManager;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ 
/*    */ public class ItemPickUpDescription extends AbstractPickUpDescription {
/*    */   private ItemDescription itemDescription;
/*    */   
/*    */   public ItemDescription getItemDescription() {
/* 11 */     return this.itemDescription;
/*    */   }
/*    */ 
/*    */   
/*    */   public void setAssociatedData(RpgLoader loader, String associatedItemId, int associatedItemTier) {
/* 16 */     ItemManager itemManager = loader.getItemManager();
/*    */     
/* 18 */     if (!itemManager.hasDescription(associatedItemId, Integer.valueOf(associatedItemTier))) {
/* 19 */       throw new RuntimeException("\n  Associated pickup item not assigned ItemType OR ItemTier not found.\n  Ensure that the ItemId exists with the specified ItemTier: pickupId=" + getId() + " associatedItemId=" + associatedItemId + " associatedItemTier=" + associatedItemTier);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 26 */     this.itemDescription = itemManager.getDescription(associatedItemId, associatedItemTier);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 31 */     return this.itemDescription.getTier();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\pickupitems\ItemPickUpDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */