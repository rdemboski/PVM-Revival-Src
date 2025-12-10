/*    */ package com.funcom.rpgengine2.regions;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ 
/*    */ public class RegionDescription {
/*    */   private String id;
/*    */   private ItemDescription onEnterDescription;
/*    */   private ItemDescription onExitDescription;
/*    */   
/*    */   public RegionDescription(String id, ItemDescription onEnterDescription, ItemDescription onExitDescription) {
/* 11 */     this.id = id;
/* 12 */     this.onEnterDescription = onEnterDescription;
/* 13 */     this.onExitDescription = onExitDescription;
/*    */   }
/*    */   
/*    */   public String getId() {
/* 17 */     return this.id;
/*    */   }
/*    */   
/*    */   public ItemDescription getOnEnterDescription() {
/* 21 */     return this.onEnterDescription;
/*    */   }
/*    */   
/*    */   public ItemDescription getOnExitDescription() {
/* 25 */     return this.onExitDescription;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\regions\RegionDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */