/*    */ package com.funcom.tcg.client.view;
/*    */ 
/*    */ import com.funcom.gameengine.resourcemanager.CacheType;
/*    */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*    */ import com.funcom.gameengine.view.TextureLoader;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*    */ import com.jme.image.Texture;
/*    */ import java.util.Map;
/*    */ 
/*    */ public abstract class AbstractCardTextureLoader
/*    */   implements TextureLoader {
/*    */   private final ResourceManager resourceManager;
/*    */   public static final String CARD_BACK = "gui/v2/interface/cards/card_back.png";
/*    */   public static final String STAR_FILLED = "effects/world/pickup_loot/star_filled.png";
/*    */   public static final String STAR_OUTLINE = "effects/world/pickup_loot/star_outline.png";
/*    */   
/*    */   public AbstractCardTextureLoader(ResourceManager resourceManager) {
/* 19 */     this.resourceManager = resourceManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public Texture load(String shaderId, Map<String, String> descriptionParams, Map<Object, Object> runtimeParams) {
/* 24 */     String typeStr = descriptionParams.get("datatype");
/* 25 */     DataType dataType = DataType.valueOf(typeStr.toUpperCase());
/*    */     
/* 27 */     AbstractPickUpDescription pickUpDescription = (AbstractPickUpDescription)runtimeParams.get(AbstractPickUpDescription.class);
/* 28 */     if (pickUpDescription == null) {
/* 29 */       throw new IllegalArgumentException("missing " + AbstractPickUpDescription.class.getSimpleName() + " object");
/*    */     }
/*    */     
/* 32 */     String texturePath = getTexturePath(dataType, pickUpDescription);
/*    */     
/* 34 */     return (Texture)this.resourceManager.getResource(Texture.class, texturePath, CacheType.CACHE_TEMPORARILY);
/*    */   }
/*    */   
/*    */   private String getTexturePath(DataType dataType, AbstractPickUpDescription pickUpDescription) {
/* 38 */     switch (dataType) {
/*    */       case ICON:
/* 40 */         return getIconPath(pickUpDescription);
/*    */       
/*    */       case CARDBACK:
/* 43 */         return "gui/v2/interface/cards/card_back.png";
/*    */ 
/*    */       
/*    */       case CARDFRONT:
/* 47 */         return getCardFront(pickUpDescription);
/*    */       
/*    */       case TIER1:
/* 50 */         return getTierTexturePath(getCardTier(pickUpDescription), 1);
/*    */       case TIER2:
/* 52 */         return getTierTexturePath(getCardTier(pickUpDescription), 2);
/*    */       case TIER3:
/* 54 */         return getTierTexturePath(getCardTier(pickUpDescription), 3);
/*    */     } 
/*    */ 
/*    */     
/* 58 */     throw new RuntimeException("unknown dataType: " + dataType);
/*    */   }
/*    */   
/*    */   protected abstract String getCardFront(AbstractPickUpDescription paramAbstractPickUpDescription);
/*    */   
/*    */   private String getTierTexturePath(int tier, int requiredTier) {
/* 64 */     if (tier >= requiredTier) {
/* 65 */       return "effects/world/pickup_loot/star_filled.png";
/*    */     }
/* 67 */     return "effects/world/pickup_loot/star_outline.png";
/*    */   }
/*    */   
/*    */   protected abstract int getCardTier(AbstractPickUpDescription paramAbstractPickUpDescription);
/*    */   
/*    */   protected abstract Element getCardElement(AbstractPickUpDescription paramAbstractPickUpDescription);
/*    */   
/*    */   protected abstract String getIconPath(AbstractPickUpDescription paramAbstractPickUpDescription);
/*    */   
/*    */   protected enum DataType
/*    */   {
/* 78 */     ICON, CARDBACK, CARDFRONT, TIER1, TIER2, TIER3;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\view\AbstractCardTextureLoader.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */