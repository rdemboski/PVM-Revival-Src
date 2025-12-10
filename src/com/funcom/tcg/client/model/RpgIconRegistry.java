/*     */ package com.funcom.tcg.client.model;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManagerException;
/*     */ import com.funcom.tcg.client.model.rpg.ClientBuff;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.IconProvider;
/*     */ import com.funcom.tcg.client.ui.errorreporting.ErrorWindowCreator;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.client.ui.pets3.PetListItem;
/*     */ import com.funcom.tcg.client.ui.skills.PetItem;
/*     */ import com.funcom.tcg.client.ui.skills.SkillListItem;
/*     */ import com.funcom.tcg.client.ui.vendor.PriceDesc;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorModelItem;
/*     */ import com.funcom.tcg.net.message.StateUpdateMessage;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.icon.ImageIcon;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class RpgIconRegistry
/*     */   implements IconProvider
/*     */ {
/*     */   public static final String QM_ICON = "gui/icons/icon_qm.png";
/*     */   public static final String NO_HAS_PET = "gui/icons/pets/empty_slot.png";
/*     */   private final ResourceManager resourceManager;
/*     */   
/*     */   public RpgIconRegistry(ResourceManager resourceManager) {
/*  35 */     this.resourceManager = resourceManager;
/*     */   }
/*     */   
/*     */   public BImage getImageForBuff(StateUpdateMessage.BuffData buff) {
/*  39 */     ClientBuff clientBuff = MainGameState.getBuffRegistry().getBuffForClassID(buff.getId());
/*  40 */     String icon = "";
/*  41 */     if (clientBuff != null) {
/*  42 */       icon = clientBuff.getIcon();
/*     */     }
/*  44 */     return _image(icon, "");
/*     */   }
/*     */   
/*     */   public BImage getImageForItem(InventoryItem item) {
/*  48 */     return _image(item.getIcon(), "");
/*     */   }
/*     */   
/*     */   public BIcon getIconForItem(InventoryItem item) {
/*  52 */     return _icon(item.getIcon(), "");
/*     */   }
/*     */   
/*     */   public BImage getImageForCooldownBackground(InventoryItem item) {
/*  56 */     return _image(item.getIcon() + "_unacquired", "");
/*     */   }
/*     */   
/*     */   public BImage getImageForCooldownProgress(InventoryItem item) {
/*  60 */     return _image(item.getIcon(), "");
/*     */   }
/*     */   
/*     */   public BIcon getIconForItem(VendorModelItem item) {
/*  64 */     return _icon(item.getIcon(), "");
/*     */   }
/*     */   
/*     */   public BIcon getIconForPetFace(PetListItem item) {
/*  68 */     return _icon(item.getIcon(), "");
/*     */   }
/*     */   
/*     */   public BIcon getIconForPetFace(PetItem petItem) {
/*  72 */     return _icon(petItem.getIcon(), "");
/*     */   }
/*     */   
/*     */   public BIcon getIconForPetType(PetListItem item) {
/*  76 */     return _icon(item.getFamily(), "gui/icons/pets/");
/*     */   }
/*     */   
/*     */   public BIcon getIconForElement(PetListItem item) {
/*  80 */     return _icon(item.getElementId() + "_24", "gui/icons/interface/elements/");
/*     */   }
/*     */   
/*     */   public BIcon getIconForSkill(SkillListItem item) {
/*  84 */     return _icon(item.getIcon(), "");
/*     */   }
/*     */   
/*     */   public String getPathForPriceDesc(String priceDesc) {
/*  88 */     return "gui/icons/items/currency/" + priceDesc + "_item.png";
/*     */   }
/*     */   
/*     */   public BIcon getIconForPriceDesc(PriceDesc priceDesc) {
/*  92 */     return _icon(priceDesc.getClassId(), "gui/icons/items/currency/");
/*     */   }
/*     */   
/*     */   public BIcon getIconShine() {
/*  96 */     return _icon("icon_shine", "gui/icons/interface/interaction/");
/*     */   }
/*     */   
/*     */   public BIcon getIconDisabledSkill() {
/* 100 */     return _icon("icon_disabled", "gui/icons/interface/interaction/");
/*     */   }
/*     */   
/*     */   public BIcon getIconSilenceSkill(InventoryItem item) {
/* 104 */     return _icon(item.getIcon() + "_unacquired", "");
/*     */   }
/*     */   
/*     */   public BImage getSpellHoverImage() {
/* 108 */     return _image("spell_hover", "gui/icons/interface/interaction/");
/*     */   }
/*     */   
/*     */   public BImage getSpellPressImage() {
/* 112 */     return _image("spell_press", "gui/icons/interface/interaction/");
/*     */   }
/*     */   
/*     */   public BImage getImageForElement(String elementId) {
/* 116 */     if (elementId != null && !"".equalsIgnoreCase(elementId)) {
/* 117 */       return _image(elementId, "interface/skin/");
/*     */     }
/* 119 */     return _image("default", "interface/skin/");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public BIcon getIconForElement(String elementID, int imageSize) {
/* 125 */     return _icon(elementID.toLowerCase() + "_" + imageSize, "gui/v2/icons/interface/elements/");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathForElement(String elementID, int imageSize) {
/* 130 */     return "gui/v2/icons/interface/elements/" + elementID.toLowerCase() + "_" + imageSize + ".png";
/*     */   }
/*     */ 
/*     */   
/*     */   public BIcon getIconForFamily(String familyID, int imageSize) {
/* 135 */     return _icon("family_" + familyID.toLowerCase() + "_" + imageSize, "gui/v2/icons/pets/");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathForFamily(String familyID, int imageSize) {
/* 140 */     return "gui/v2/icons/pets/family_" + familyID.toLowerCase() + "_" + imageSize + ".png";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathForStat(String stat, int imgSize) {
/* 145 */     return "gui/v2/icons/interface/stats/" + stat.toLowerCase() + "_" + imgSize + ".png";
/*     */   }
/*     */ 
/*     */   
/*     */   public BIcon getIconForRarity(String rarity) {
/* 150 */     return _icon("card_" + rarity.toLowerCase(), "gui/icons/items/cards/");
/*     */   }
/*     */ 
/*     */   
/*     */   public BIcon getIconForTier(int tier, int imageSize) {
/* 155 */     return _icon("tier_" + tier + "_" + imageSize, "gui/v2/icons/interface/tier/");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathForTier(int tier, int imageSize) {
/* 160 */     return "gui/v2/icons/interface/tier/tier_" + tier + "_" + imageSize + ".png";
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathForTier(String tier, int imageSize) {
/* 165 */     if (tier.equalsIgnoreCase("all")) {
/* 166 */       return "gui/v2/icons/interface/tier/tier_all_" + imageSize + ".png";
/*     */     }
/* 168 */     return getPathForTier(Integer.parseInt(tier), imageSize);
/*     */   }
/*     */ 
/*     */   
/*     */   public BIcon getIconForType(String type, int imageSize) {
/* 173 */     return _icon(type.toLowerCase().replace(' ', '_') + "_" + imageSize, "gui/v2/icons/interface/pet_type/");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathForType(String type, int imageSize) {
/* 178 */     return "gui/v2/icons/interface/pet_type/" + type.toLowerCase().replace(' ', '_') + "_" + imageSize + ".png";
/*     */   }
/*     */   
/*     */   private BIcon _icon(String name, String iconPath) {
/* 182 */     return (BIcon)new ImageIcon(_image(name, iconPath));
/*     */   }
/*     */   
/*     */   public BIcon getDefaultIcon() {
/* 186 */     return _icon("icon_qm", "gui/icons/interface/interaction/");
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPathForDefaultIcon() {
/* 191 */     return "gui/icons/interface/interaction/icon_qm.png";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private BImage _image(String name, String iconPath) {
/*     */     BImage bImage;
/* 199 */     if (name == null) {
/* 200 */       bImage = (BImage)this.resourceManager.getResource(BImage.class, "gui/icons/icon_qm.png");
/* 201 */       ErrorWindowCreator.instance().createIconErrorMessage("No Icon Error", "Icon name null!");
/* 202 */       return bImage;
/*     */     } 
/*     */     try {
/* 205 */       StringBuilder sb = new StringBuilder(iconPath);
/* 206 */       sb.append(name);
/* 207 */       if (!name.endsWith(".png")) sb.append(".png"); 
/* 208 */       bImage = (BImage)this.resourceManager.getResource(BImage.class, sb.toString(), CacheType.CACHE_TEMPORARILY);
/* 209 */     } catch (ResourceManagerException e) {
/* 210 */       bImage = (BImage)this.resourceManager.getResource(BImage.class, "gui/icons/icon_qm.png");
/* 211 */       ErrorWindowCreator.instance().createIconErrorMessage("No Icon Error", "No icon exists for item: " + name + "\nwhich should be located at " + iconPath + name + ".png");
/*     */     } 
/*     */     
/* 214 */     if (bImage == null) {
/* 215 */       throw new IllegalStateException("No item: " + name);
/*     */     }
/* 217 */     return bImage;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\RpgIconRegistry.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */