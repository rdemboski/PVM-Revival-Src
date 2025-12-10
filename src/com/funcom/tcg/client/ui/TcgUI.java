/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
/*     */ import com.funcom.tcg.client.model.RpgIconRegistry;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.client.ui.pets3.PetListItem;
/*     */ import com.funcom.tcg.client.ui.skills.PetItem;
/*     */ import com.funcom.tcg.client.ui.skills.SkillListItem;
/*     */ import com.funcom.tcg.client.ui.vendor.PriceDesc;
/*     */ import com.funcom.tcg.client.ui.vendor.VendorModelItem;
/*     */ import com.funcom.tcg.net.message.StateUpdateMessage;
/*     */ import com.jmex.bui.BImage;
/*     */ import com.jmex.bui.BStyleSheet;
/*     */ import com.jmex.bui.BWindow;
/*     */ import com.jmex.bui.BuiSystem;
/*     */ import com.jmex.bui.icon.BIcon;
/*     */ import com.jmex.bui.icon.ImageIcon;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.URL;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class TcgUI
/*     */ {
/*     */   private static TcgUI INSTANCE;
/*     */   
/*     */   public static void useTestImplementation() {
/*  37 */     INSTANCE = new TestImplementation();
/*     */   }
/*     */   
/*     */   public static void useProductionImplementation(ResourceManager resourceManager) {
/*  41 */     INSTANCE = new ProductionImplementation(resourceManager);
/*     */   }
/*     */   
/*     */   public static IconProvider getIconProvider() {
/*  45 */     return INSTANCE._getIconProvider();
/*     */   }
/*     */   
/*     */   public static UISoundPlayer getUISoundPlayer() {
/*  49 */     return INSTANCE.getSoundPlayer();
/*     */   }
/*     */   
/*     */   public static BStyleSheet getGameStylesheet() {
/*  53 */     return INSTANCE._getGameStylesheet();
/*     */   }
/*     */   
/*     */   public static BWindow getWindowFromClass(Class<? extends BWindow> windowClass) {
/*  57 */     for (int i = 0; i < BuiSystem.getRootNode().getWindowCount(); i++) {
/*  58 */       BWindow window = BuiSystem.getRootNode().getWindow(i);
/*  59 */       if (window.getClass().equals(windowClass)) {
/*  60 */         return window;
/*     */       }
/*     */     } 
/*  63 */     return null;
/*     */   }
/*     */   
/*     */   public static boolean isWindowOpen(Class<? extends BWindow> windowClass) {
/*  67 */     BWindow windowFromClass = getWindowFromClass(windowClass);
/*  68 */     return (windowFromClass != null && windowFromClass.isVisible());
/*     */   }
/*     */ 
/*     */   
/*     */   public abstract UISoundPlayer getSoundPlayer();
/*     */   
/*     */   protected abstract IconProvider _getIconProvider();
/*     */   
/*     */   protected abstract BStyleSheet _getGameStylesheet();
/*     */   
/*     */   private static class TestImplementation
/*     */     extends TcgUI
/*     */   {
/*  81 */     private IconProvider iconProvider = new IconProvider() {
/*     */         public BImage getImageForBuff(StateUpdateMessage.BuffData buff) {
/*  83 */           if (buff.getType() == BuffType.BUFF)
/*  84 */             return _getImageForName(buff.getId(), System.getProperty("tcg.resourcepath") + "/ui/buffs"); 
/*  85 */           if (buff.getType() == BuffType.DEBUFF) {
/*  86 */             return _getImageForName(buff.getId(), System.getProperty("tcg.resourcepath") + "/ui/debuffs");
/*     */           }
/*     */           
/*  89 */           throw new IllegalArgumentException("unknown buff type: buff=" + buff);
/*     */         }
/*     */         
/*     */         public BImage getImageForItem(InventoryItem item) {
/*  93 */           return _getImageForName(item.getIcon(), System.getProperty("tcg.resourcepath") + "/ui/items");
/*     */         }
/*     */         
/*     */         public BIcon getIconForItem(InventoryItem item) {
/*  97 */           return _getIconForName(item.getIcon(), System.getProperty("tcg.resourcepath") + "/ui/items");
/*     */         }
/*     */         
/*     */         public BIcon getIconForItem(VendorModelItem item) {
/* 101 */           return _getIconForName(item.getIcon(), System.getProperty("tcg.resourcepath") + "/ui/items");
/*     */         }
/*     */         
/*     */         public BImage getImageForCooldownBackground(InventoryItem item) {
/* 105 */           return _getImageForName(item.getIcon() + "_unacquired", System.getProperty("tcg.resourcepath") + "/ui/items");
/*     */         }
/*     */         
/*     */         public BImage getImageForCooldownProgress(InventoryItem item) {
/* 109 */           return _getImageForName(item.getIcon(), System.getProperty("tcg.resourcepath") + "/ui/items");
/*     */         }
/*     */         
/*     */         public BIcon getIconForPetFace(PetListItem item) {
/* 113 */           return _getIconForName(item.getIcon(), System.getProperty("tcg.resourcepath") + "/ui/pets");
/*     */         }
/*     */         
/*     */         public BIcon getIconForPetFace(PetItem petItem) {
/* 117 */           return _getIconForName(petItem.getIcon(), System.getProperty("tcg.resourcepath") + "/ui/pets");
/*     */         }
/*     */         
/*     */         public BIcon getIconForPetType(PetListItem item) {
/* 121 */           return _getIconForName(item.getFamily(), System.getProperty("tcg.resourcepath") + "/ui/pets");
/*     */         }
/*     */         
/*     */         public BIcon getIconForElement(PetListItem item) {
/* 125 */           return _getIconForName(item.getElementId() + "_24", System.getProperty("tcg.resourcepath") + "/ui/elements");
/*     */         }
/*     */         
/*     */         public BIcon getIconForSkill(SkillListItem item) {
/* 129 */           return _getIconForName(item.getIcon(), System.getProperty("tcg.resourcepath") + "/ui/items");
/*     */         }
/*     */         
/*     */         public BIcon getIconForPriceDesc(PriceDesc priceDesc) {
/* 133 */           return _getIconForName(priceDesc.getClassId(), System.getProperty("tcg.resourcepath") + "/ui/currency");
/*     */         }
/*     */         
/*     */         public BIcon getIconShine() {
/* 137 */           return _getIconForName("icon_shine", System.getProperty("tcg.resourcepath") + "/ui/general");
/*     */         }
/*     */         
/*     */         public BIcon getIconDisabledSkill() {
/* 141 */           return _getIconForName("icon_disabled", System.getProperty("tcg.resourcepath") + "/ui/general");
/*     */         }
/*     */         
/*     */         public BIcon getIconSilenceSkill(InventoryItem item) {
/* 145 */           return _getIconForName(item.getIcon() + "_unacquired", System.getProperty("tcg.resourcepath") + "/ui/items");
/*     */         }
/*     */         
/*     */         public BImage getSpellHoverImage() {
/* 149 */           return _getImageForName("spell_hover", System.getProperty("tcg.resourcepath") + "/ui/general");
/*     */         }
/*     */         
/*     */         public BImage getSpellPressImage() {
/* 153 */           return _getImageForName("spell_press", System.getProperty("tcg.resourcepath") + "/ui/general");
/*     */         }
/*     */         
/*     */         public BImage getImageForElement(String elementId) {
/* 157 */           if (elementId != null && !"".equalsIgnoreCase(elementId)) {
/* 158 */             return _getImageForName(elementId, System.getProperty("tcg.resourcepath") + "/ui/elements");
/*     */           }
/* 160 */           return _getImageForName("default", System.getProperty("tcg.resourcepath") + "/ui/elements");
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public BIcon getIconForElement(String elementID, int imageSize) {
/* 166 */           return _getIconForName(elementID.toLowerCase() + "_" + imageSize, "icons/interface/elements/");
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPathForElement(String elementID, int imageSize) {
/* 171 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public BIcon getIconForFamily(String familyID, int imageSize) {
/* 176 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPathForFamily(String familyID, int imgSize) {
/* 181 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPathForStat(String stat, int imgSize) {
/* 186 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public BIcon getIconForRarity(String rarity) {
/* 191 */           return _getIconForName("card_" + rarity.toLowerCase(), "icons/items/cards/");
/*     */         }
/*     */ 
/*     */         
/*     */         public BIcon getIconForTier(int tier, int imageSize) {
/* 196 */           return _getIconForName("tier_" + tier + "_" + imageSize, "/icons/interface/tier/");
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPathForTier(int tier, int imageSize) {
/* 201 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPathForTier(String tier, int imageSize) {
/* 206 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public BIcon getIconForType(String type, int imageSize) {
/* 211 */           return _getIconForName(type.replace(' ', '_') + "_" + imageSize, "/icons/interface/pet_type/");
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPathForType(String type, int imageSize) {
/* 216 */           return null;
/*     */         }
/*     */         
/*     */         private BIcon _getIconForName(String name, String parentPath) {
/* 220 */           return (BIcon)new ImageIcon(_getImageForName(name, parentPath));
/*     */         }
/*     */         
/*     */         private BImage _getImageForName(String name, String parentPath) {
/*     */           try {
/* 225 */             URL url = (new File(parentPath, name + ".png")).toURI().toURL();
/* 226 */             return new BImage(url);
/* 227 */           } catch (IOException e) {
/* 228 */             throw new IllegalStateException("Failed for: " + name, e);
/*     */           } 
/*     */         }
/*     */ 
/*     */         
/*     */         public BIcon getDefaultIcon() {
/* 234 */           return null;
/*     */         }
/*     */ 
/*     */         
/*     */         public String getPathForDefaultIcon() {
/* 239 */           return null;
/*     */         }
/*     */       };
/*     */ 
/*     */ 
/*     */     
/*     */     public UISoundPlayer getSoundPlayer() {
/* 246 */       return null;
/*     */     }
/*     */     
/*     */     protected IconProvider _getIconProvider() {
/* 250 */       return this.iconProvider;
/*     */     }
/*     */     
/*     */     protected BStyleSheet _getGameStylesheet() {
/* 254 */       return BuiSystem.getStyle();
/*     */     }
/*     */     
/*     */     private TestImplementation() {} }
/*     */   
/*     */   private static class ProductionImplementation extends TcgUI {
/*     */     private RpgIconRegistry iconRegistry;
/*     */     
/*     */     private ProductionImplementation(ResourceManager resourceManager) {
/* 263 */       this.iconRegistry = new RpgIconRegistry(resourceManager);
/* 264 */       this.soundPlayer = new UISoundPlayer(resourceManager);
/*     */     }
/*     */ 
/*     */     
/*     */     public UISoundPlayer getSoundPlayer() {
/* 269 */       return this.soundPlayer;
/*     */     }
/*     */     private UISoundPlayer soundPlayer;
/*     */     protected IconProvider _getIconProvider() {
/* 273 */       return (IconProvider)this.iconRegistry;
/*     */     }
/*     */     
/*     */     protected BStyleSheet _getGameStylesheet() {
/* 277 */       return BuiSystem.getStyle();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\TcgUI.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */