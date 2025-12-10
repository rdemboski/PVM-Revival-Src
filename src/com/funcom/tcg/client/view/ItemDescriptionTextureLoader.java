/*    */ package com.funcom.tcg.client.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.view.TextureLoader;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.tcg.client.TcgGame;
/*    */ import com.jme.image.Texture;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class ItemDescriptionTextureLoader
/*    */   implements TextureLoader {
/*    */   private final ResourceManager resourceManager;
/*    */   
/*    */   public ItemDescriptionTextureLoader(ResourceManager resourceManager) {
/* 17 */     this.resourceManager = resourceManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public Texture load(String shaderId, Map<String, String> descriptionParams, Map<Object, Object> runtimeParams) {
/* 22 */     String typeStr = descriptionParams.get("datatype");
/* 23 */     DataType dataType = DataType.valueOf(typeStr.toUpperCase());
/*    */     
/* 25 */     ItemDescription itemDescription = (ItemDescription)runtimeParams.get(ItemDescription.class);
/* 26 */     if (itemDescription == null) {
/* 27 */       throw new IllegalArgumentException("missing " + ItemDescription.class.getSimpleName() + " object");
/*    */     }
/*    */     
/* 30 */     String texturePath = getTexturePath(dataType, itemDescription);
/*    */     
/* 32 */     return (Texture)this.resourceManager.getResource(Texture.class, texturePath, CacheType.CACHE_TEMPORARILY);
/*    */   }
/*    */   
/*    */   private String getTexturePath(DataType dataType, ItemDescription itemDescription) {
/* 36 */     switch (dataType) {
/*    */       case ICON:
/* 38 */         return getIconPath(itemDescription);
/*    */       
/*    */       case CARDBACK:
/* 41 */         return "gui/v2/interface/cards/card_back.png";
/*    */       
/*    */       case CARDFRONT:
/* 44 */         return TcgGame.getVisualRegistry().getImageStringForRarity(String.valueOf(itemDescription.getTier()));
/*    */       
/*    */       case TIER1:
/* 47 */         return getTierTexturePath(getCardTier(itemDescription), 1);
/*    */       case TIER2:
/* 49 */         return getTierTexturePath(getCardTier(itemDescription), 2);
/*    */       case TIER3:
/* 51 */         return getTierTexturePath(getCardTier(itemDescription), 3);
/*    */     } 
/*    */ 
/*    */     
/* 55 */     throw new RuntimeException("unknown dataType: " + dataType);
/*    */   }
/*    */   
/*    */   private String getTierTexturePath(int tier, int requiredTier) {
/* 59 */     if (tier >= requiredTier) {
/* 60 */       return "effects/world/pickup_loot/star_filled.png";
/*    */     }
/* 62 */     return "effects/world/pickup_loot/star_outline.png";
/*    */   }
/*    */ 
/*    */   
/*    */   protected String getIconPath(ItemDescription itemDescription) {
/* 67 */     return itemDescription.getIcon();
/*    */   }
/*    */   
/*    */   protected Element getCardElement(ItemDescription itemDescription) {
/* 71 */     if (itemDescription.getItemType().isEquipable())
/* 72 */       return Element.PHYSICAL; 
/* 73 */     Element element = itemDescription.getElement();
/* 74 */     return (element == null) ? Element.PHYSICAL : element;
/*    */   }
/*    */   
/*    */   protected int getCardTier(ItemDescription itemDescription) {
/* 78 */     return itemDescription.getTier();
/*    */   }
/*    */   
/*    */   protected enum DataType {
/* 82 */     ICON, CARDBACK, CARDFRONT, TIER1, TIER2, TIER3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\ItemDescriptionTextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */