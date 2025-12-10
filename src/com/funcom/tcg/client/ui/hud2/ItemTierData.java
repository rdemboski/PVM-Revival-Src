/*    */ package com.funcom.tcg.client.ui.hud2;
/*    */ 
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.items.ItemType;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.funcom.tcg.client.state.MainGameState;
/*    */ import com.funcom.tcg.client.ui.TieredData;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ItemTierData
/*    */   implements TieredData
/*    */ {
/*    */   private String itemId;
/*    */   private String iconPath;
/*    */   private int tier;
/*    */   private String elementalBackgroundPath;
/*    */   private ItemType type;
/*    */   
/*    */   public ItemTierData() {}
/*    */   
/*    */   public ItemTierData(String itemId, int tier) {
/* 23 */     this.itemId = itemId;
/* 24 */     this.tier = tier;
/*    */     
/* 26 */     if (itemId != null) {
/* 27 */       ItemDescription item = TcgGame.getRpgLoader().getItemManager().getDescription(itemId, tier);
/* 28 */       this.iconPath = item.getIcon();
/* 29 */       this.type = item.getItemType();
/*    */     } else {
/* 31 */       this.iconPath = "";
/*    */     } 
/*    */     
/* 34 */     this.elementalBackgroundPath = TcgGame.getVisualRegistry().getImageStringForRarity(String.valueOf(tier));
/*    */   }
/*    */   
/*    */   public String getItem() {
/* 38 */     return this.itemId;
/*    */   }
/*    */   
/*    */   public int getTier() {
/* 42 */     return this.tier;
/*    */   }
/*    */   
/*    */   public String getBImagePath() {
/* 46 */     return this.iconPath;
/*    */   }
/*    */   
/*    */   public boolean isUsingCustomBackground() {
/* 50 */     return (this.elementalBackgroundPath != null);
/*    */   }
/*    */   
/*    */   public String getCustomImageBackgroundPath() {
/* 54 */     return this.elementalBackgroundPath;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getPathForIconReplacingTierStars() {
/* 59 */     return null;
/*    */   }
/*    */   
/*    */   public void setTier(int tier) {
/* 63 */     this.tier = tier;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getHtml() {
/* 68 */     if (this.itemId == null) return null; 
/* 69 */     return MainGameState.getToolTipManager().getItemHtml(this.itemId, this.tier);
/*    */   }
/*    */   
/*    */   public ItemType getType() {
/* 73 */     return this.type;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\hud2\ItemTierData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */