/*     */ package com.funcom.tcg.client.ui;
/*     */ import com.funcom.rpgengine2.combat.ElementDescription;
/*     */ import com.funcom.rpgengine2.combat.ElementManager;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.pickupitems.AbstractPickUpDescription;
/*     */ import com.funcom.rpgengine2.pickupitems.DefaultPickUpType;
/*     */ import com.funcom.rpgengine2.pickupitems.ItemPickUpDescription;
/*     */ import com.funcom.rpgengine2.pickupitems.PickUpManager;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardData;
/*     */ import com.funcom.rpgengine2.quests.reward.QuestRewardType;
/*     */ import com.funcom.tcg.client.model.rpg.ClientItem;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.model.rpg.ItemRegistry;
/*     */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*     */ import com.funcom.tcg.client.model.rpg.VisualRegistry;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.rpg.GiftBoxDescription;
/*     */ import com.funcom.tcg.rpg.TCGPickUpType;
/*     */ import java.util.List;
/*     */ 
/*     */ public class RewardTierData implements TieredData {
/*     */   private int tier;
/*     */   private String bImagePath;
/*  24 */   private String pathForIconReplacingTierStars = null; private boolean usingCustomBackground; private String customBackgroundPath;
/*  25 */   private String html = null;
/*     */   
/*     */   public void setTier(int tier) {
/*  28 */     this.tier = tier;
/*     */   }
/*     */   
/*     */   public void setBImagePath(String bImagePath) {
/*  32 */     this.bImagePath = bImagePath;
/*     */   }
/*     */   
/*     */   public void setUsingCustomBackground(boolean usingCustomBackground) {
/*  36 */     this.usingCustomBackground = usingCustomBackground;
/*     */   }
/*     */   
/*     */   public void setCustomBackgroundPath(String customBackgroundPath) {
/*  40 */     this.customBackgroundPath = customBackgroundPath;
/*     */   }
/*     */   
/*     */   public void setPathForIconReplacingTierStars(String pathForIconReplacingTierStars) {
/*  44 */     this.pathForIconReplacingTierStars = pathForIconReplacingTierStars;
/*     */   }
/*     */   
/*     */   public void setHtml(String html) {
/*  48 */     this.html = html;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getTier() {
/*  53 */     return this.tier;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getBImagePath() {
/*  58 */     return this.bImagePath;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsingCustomBackground() {
/*  64 */     return this.usingCustomBackground;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCustomImageBackgroundPath() {
/*  69 */     return this.customBackgroundPath;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathForIconReplacingTierStars() {
/*  74 */     return this.pathForIconReplacingTierStars;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getHtml() {
/*  79 */     return this.html;
/*     */   }
/*     */   
/*     */   public static List<TieredData> createTieredData(List<QuestRewardData> rewardLabels, PickUpManager pickUpManager, VisualRegistry visualRegistry, PetRegistry petRegistry, ItemRegistry itemRegistry, ElementManager elementManager) {
/*  83 */     List<TieredData> dataList = new ArrayList<TieredData>();
/*     */     
/*  85 */     for (QuestRewardData rewardLabel : rewardLabels) {
/*  86 */       toButtonData(rewardLabel, dataList, pickUpManager, visualRegistry, petRegistry, itemRegistry, elementManager);
/*     */     }
/*     */     
/*  89 */     return dataList;
/*     */   }
/*     */ 
/*     */   
/*     */   private static void toButtonData(QuestRewardData questRewardData, List<TieredData> dataList, PickUpManager pickUpManager, VisualRegistry visualRegistry, PetRegistry petRegistry, ItemRegistry itemRegistry, ElementManager elementManager) {
/*  94 */     RewardTierData rewardTierData = new RewardTierData();
/*     */     
/*  96 */     if (questRewardData.getType() == QuestRewardType.ITEM_REWARD.getId()) {
/*  97 */       AbstractPickUpDescription pickUp = pickUpManager.getDescription(questRewardData.getRewardId());
/*  98 */       if (pickUp != null) {
/*  99 */         if (pickUp.getAssociatedType() == DefaultPickUpType.ITEM) {
/* 100 */           ItemDescription itemDescription = ((ItemPickUpDescription)pickUp).getItemDescription();
/* 101 */           rewardTierData.setBImagePath(itemDescription.getIcon());
/* 102 */           rewardTierData.setTier(questRewardData.getTier());
/* 103 */           rewardTierData.setUsingCustomBackground(true);
/* 104 */           rewardTierData.setCustomBackgroundPath(visualRegistry.getImageStringForRarity(String.valueOf(questRewardData.getTier())));
/* 105 */           rewardTierData.setHtml(MainGameState.getToolTipManager().getItemHtml(itemDescription.getId(), itemDescription.getTier()));
/* 106 */           dataList.add(rewardTierData);
/* 107 */         } else if (pickUp.getAssociatedType() == DefaultPickUpType.PET) {
/* 108 */           ClientPetDescription pet = petRegistry.getPetForClassId(((PetPickUpDescription)pickUp).getPetDescription().getId());
/* 109 */           addButtonForPet(dataList, rewardTierData, pet, elementManager);
/* 110 */         } else if (pickUp.getAssociatedType() == TCGPickUpType.GIFTBOX) {
/* 111 */           GiftBoxDescription boxDescription = ((GiftBoxPickUpDescription)pickUp).getGiftBoxDescription();
/* 112 */           rewardTierData.setBImagePath(boxDescription.getIconPathUnlocked());
/* 113 */           rewardTierData.setTier(0);
/* 114 */           rewardTierData.setUsingCustomBackground(true);
/* 115 */           rewardTierData.setCustomBackgroundPath(visualRegistry.getImageStringForRarity(String.valueOf(0)));
/* 116 */           dataList.add(rewardTierData);
/*     */         } 
/*     */       } else {
/* 119 */         ClientItem item = itemRegistry.getItemForClassID(questRewardData.getRewardId(), questRewardData.getTier());
/* 120 */         rewardTierData.setBImagePath(item.getIcon());
/* 121 */         rewardTierData.setTier(questRewardData.getTier());
/* 122 */         rewardTierData.setUsingCustomBackground(true);
/* 123 */         rewardTierData.setCustomBackgroundPath(visualRegistry.getImageStringForRarity(String.valueOf(questRewardData.getTier())));
/* 124 */         rewardTierData.setHtml(MainGameState.getToolTipManager().getItemHtml(item.getClassId(), item.getTier()));
/* 125 */         dataList.add(rewardTierData);
/*     */       } 
/* 127 */     } else if (questRewardData.getType() == QuestRewardType.PET_REWARD.getId()) {
/* 128 */       ClientPetDescription pet = petRegistry.getPetForClassId(questRewardData.getRewardId());
/* 129 */       addButtonForPet(dataList, rewardTierData, pet, elementManager);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void addButtonForPet(List<TieredData> dataList, RewardTierData rewardTierData, ClientPetDescription pet, ElementManager elementManager) {
/* 134 */     rewardTierData.setBImagePath(pet.getIcon());
/* 135 */     rewardTierData.setTier(0);
/* 136 */     rewardTierData.setUsingCustomBackground(true);
/*     */     
/* 138 */     ElementDescription elementDescription = elementManager.getElementDesc(Element.valueOf(pet.getElementId().toUpperCase()));
/* 139 */     rewardTierData.setCustomBackgroundPath(elementDescription.getPetCardImage());
/*     */     
/* 141 */     String typePath = TcgUI.getIconProvider().getPathForType(pet.getType(), 16);
/* 142 */     rewardTierData.setPathForIconReplacingTierStars(typePath);
/* 143 */     rewardTierData.setHtml(MainGameState.getToolTipManager().getPetHtml(pet.getClassId()));
/* 144 */     dataList.add(rewardTierData);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\RewardTierData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */