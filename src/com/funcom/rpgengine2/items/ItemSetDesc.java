/*    */ package com.funcom.rpgengine2.items;
/*    */ 
/*    */ import com.funcom.commons.localization.JavaLocalization;
/*    */ 
/*    */ public class ItemSetDesc {
/*    */   private String itemSetId;
/*    */   private String name;
/*    */   private String icon;
/*    */   
/*    */   public ItemSetDesc(String itemSetId, String name, String icon) {
/* 11 */     this.itemSetId = itemSetId;
/* 12 */     this.name = name;
/* 13 */     this.icon = icon;
/*    */   }
/*    */   
/*    */   public String getItemSetId() {
/* 17 */     return this.itemSetId;
/*    */   }
/*    */   
/*    */   public String getName() {
/* 21 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*    */   }
/*    */   
/*    */   public String getIcon() {
/* 25 */     return this.icon;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\ItemSetDesc.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */