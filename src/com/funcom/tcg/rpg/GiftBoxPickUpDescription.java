/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*    */ 
/*    */ public class GiftBoxPickUpDescription
/*    */   extends AbstractPickUpDescription
/*    */ {
/*    */   private TCGGiftBoxManager giftBoxManager;
/*    */   private GiftBoxDescription giftBoxDescription;
/*    */   private String giftBoxDescriptionId;
/*    */   
/*    */   public void setAssociatedData(RpgLoader loader, String associatedItemId, int associatedItemTier) {
/* 14 */     this.giftBoxDescriptionId = associatedItemId;
/* 15 */     this.giftBoxManager = ((AbstractTCGRpgLoader)loader).getGiftBoxManager();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public GiftBoxDescription getGiftBoxDescription() {
/* 21 */     return this.giftBoxDescription;
/*    */   }
/*    */   
/*    */   public void init() {
/* 25 */     this.giftBoxDescription = this.giftBoxManager.getDescription(this.giftBoxDescriptionId);
/* 26 */     if (this.giftBoxDescription == null) {
/* 27 */       throw new IllegalArgumentException("cannot find gift box for pickup: pickupId=" + getId() + " missingGiftBoxId=" + this.giftBoxDescriptionId);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public int getTier() {
/* 33 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\GiftBoxPickUpDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */