/*     */ package com.funcom.tcg.client.ui;
/*     */ 
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.gameengine.resourcemanager.CacheType;
/*     */ import com.funcom.gameengine.resourcemanager.ResourceManager;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*     */ import com.funcom.rpgengine2.abilities.ElementAbility;
/*     */ import com.funcom.rpgengine2.abilities.ShapedAbility;
/*     */ import com.funcom.rpgengine2.combat.ElementDescription;
/*     */ import com.funcom.rpgengine2.combat.ElementManager;
/*     */ import com.funcom.rpgengine2.combat.ManaCostCalc;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemManager;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPetDescription;
/*     */ import com.funcom.tcg.client.model.rpg.ClientPlayer;
/*     */ import com.funcom.tcg.client.model.rpg.ItemRegistry;
/*     */ import com.funcom.tcg.client.model.rpg.PetRegistry;
/*     */ import com.funcom.tcg.client.state.MainGameState;
/*     */ import com.funcom.tcg.client.ui.hud2.VendorWindow;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class TCGToolTipManager
/*     */ {
/*     */   private static final String WHITE = "white";
/*     */   private static final String GRAY = "gray";
/*     */   private ItemManager itemManager;
/*     */   private ElementManager elementManager;
/*     */   private PetRegistry petRegistry;
/*     */   private ItemRegistry itemRegistry;
/*     */   private ClientPlayer clientPlayer;
/*     */   private final ResourceManager resourceManager;
/*     */   private final Localizer localizer;
/*     */   
/*     */   public TCGToolTipManager(ResourceManager resourceManager, Localizer localizer) {
/*  43 */     this.resourceManager = resourceManager;
/*  44 */     this.localizer = localizer;
/*     */   }
/*     */   
/*     */   public void setItemManager(ItemManager itemManager) {
/*  48 */     this.itemManager = itemManager;
/*     */   }
/*     */   
/*     */   public void setElementManager(ElementManager elementManager) {
/*  52 */     this.elementManager = elementManager;
/*     */   }
/*     */   
/*     */   public void setPetRegistry(PetRegistry petRegistry) {
/*  56 */     this.petRegistry = petRegistry;
/*     */   }
/*     */   
/*     */   public void setItemRegistry(ItemRegistry itemRegistry) {
/*  60 */     this.itemRegistry = itemRegistry;
/*     */   }
/*     */   
/*     */   public void setClientPlayer(ClientPlayer clientPlayer) {
/*  64 */     this.clientPlayer = clientPlayer;
/*     */   }
/*     */   
/*     */   public String getPetHtml(String petId) {
/*  68 */     String html = null;
/*  69 */     ClientPetDescription clientPet = this.petRegistry.getPetForClassId(petId);
/*  70 */     if (clientPet != null) {
/*  71 */       html = (String)this.resourceManager.getManagedResource(String.class, "html/pet.html").getResource();
/*  72 */       return replaceVariablesInPetHtml(html, clientPet);
/*     */     } 
/*  74 */     return html;
/*     */   }
/*     */   
/*     */   public String getHudHtml(String replacement) {
/*  78 */     String html = (String)this.resourceManager.getManagedResource(String.class, "html/tooltip_main_window.html").getResource();
/*     */     
/*  80 */     return html.replace("$name", getLocalizedText(replacement, new String[0]));
/*     */   }
/*     */   
/*     */   private String getLocalizedText(String key, String... parameters) {
/*  84 */     return this.localizer.getLocalizedText(getClass(), key, parameters);
/*     */   }
/*     */   
/*     */   public String getCharacterEmptyEquipSlotHtml(String slotName) {
/*  88 */     String html = (String)this.resourceManager.getManagedResource(String.class, "html/empty_equipslot.html").getResource();
/*  89 */     if (html != null) {
/*  90 */       html = html.replace("$slot_name", slotName);
/*  91 */       html = html.replace("$text", getLocalizedText("tooltip.characterslotempty", new String[0]));
/*     */     } 
/*  93 */     return html;
/*     */   }
/*     */   
/*     */   public String getVendorItemHtml(String vendorItemId, int tier, int ammount) {
/*  97 */     ItemDescription itemDesc = this.itemManager.getDescription(vendorItemId, tier);
/*  98 */     return getItemHtml(itemDesc, getLocalizedText("tooltip.price", new String[0]), getLocalizedText("tooltip.free", new String[0]), getLocalizedText("members.only", new String[0]), tier, ammount);
/*     */   }
/*     */   
/*     */   public String getItemHtml(String itemId, int tier) {
/* 102 */     ItemDescription itemDesc = this.itemManager.getDescription(itemId, tier);
/* 103 */     return getItemHtml(itemDesc, getLocalizedText("tooltip.sellsfor", new String[0]), getLocalizedText("tooltip.cannotbesold", new String[0]), getLocalizedText("members.only", new String[0]), tier, itemDesc.getItemValueAmount());
/*     */   }
/*     */   
/*     */   private String getItemHtml(ItemDescription itemDesc, String itemSaleString, String noPriceString, String membersOnlyText, int tier, int ammount) {
/* 107 */     String html = null;
/*     */ 
/*     */     
/* 110 */     if (itemDesc != null) {
/* 111 */       ItemType itemType = itemDesc.getItemType();
/*     */       
/* 113 */       if (itemType == ItemType.SKILL) {
/* 114 */         html = (String)this.resourceManager.getManagedResource(String.class, "html/skill.html").getResource();
/* 115 */         return replaceVariablesInSkillHtml(html, itemDesc);
/* 116 */       }  if (itemType.isEquipable()) {
/* 117 */         html = getItemTypeHtmlTemplate("equipment", itemDesc, ammount);
/* 118 */         return replaceVariablesInEquipmentHtml(html, itemDesc, itemSaleString, noPriceString, membersOnlyText, ammount);
/* 119 */       }  if (itemType == ItemType.POTION) {
/* 120 */         html = getItemTypeHtmlTemplate("potion", itemDesc, ammount);
/* 121 */         return replaceVariablesInPotionHtml(html, itemDesc, itemSaleString, noPriceString, ammount);
/* 122 */       }  if (itemType == ItemType.CRYSTAL) {
/* 123 */         html = getItemTypeHtmlTemplate("crystal", itemDesc, ammount);
/* 124 */         return replaceVariablesInCrystalHtml(html, itemDesc, itemSaleString, noPriceString, ammount);
/*     */       } 
/*     */     } 
/* 127 */     return html;
/*     */   }
/*     */   
/*     */   public String getHealthBubbleHtml() {
/* 131 */     short statId = 12;
/* 132 */     short maxStatId = 11;
/* 133 */     String name = getLocalizedText("tooltip.healthname", new String[0]);
/* 134 */     String effect = getLocalizedText("tooltip.healtheffect", new String[0]);
/* 135 */     String clickStartDrinkString = getLocalizedText("tooltip.healthstartdrinking", new String[0]);
/* 136 */     String clickEndDrinkString = getLocalizedText("tooltip.healthenddrinking", new String[0]);
/*     */     
/* 138 */     return getStatBubbleHtml(statId, maxStatId, name, effect, clickStartDrinkString, clickEndDrinkString);
/*     */   }
/*     */   
/*     */   public String getManaBubbleHtml() {
/* 142 */     short statId = 14;
/* 143 */     short maxStatId = 13;
/* 144 */     String name = getLocalizedText("tooltip.mananame", new String[0]);
/* 145 */     String effect = getLocalizedText("tooltip.manaeffect", new String[0]);
/* 146 */     String clickStartDrinkString = getLocalizedText("tooltip.manastartdrinking", new String[0]);
/* 147 */     String clickEndDrinkString = getLocalizedText("tooltip.manaenddrinking", new String[0]);
/*     */     
/* 149 */     return getStatBubbleHtml(statId, maxStatId, name, effect, clickStartDrinkString, clickEndDrinkString);
/*     */   }
/*     */   
/*     */   public String getStatBubbleHtml(short statId, short maxStatId, String name, String effect, String clickStartDrinkString, String clickEndDrinkString) {
/* 153 */     String html = (String)this.resourceManager.getManagedResource(String.class, "html/stat_bubble.html").getResource();
/*     */     
/* 155 */     if (html != null) {
/* 156 */       String currentValue = MainGameState.getPlayerModel().getStatSum(Short.valueOf(statId)).toString();
/* 157 */       String maxValue = MainGameState.getPlayerModel().getStatSum(Short.valueOf(maxStatId)).toString();
/* 158 */       html = html.replace("$name", name);
/* 159 */       html = html.replace("$current_stat", currentValue);
/* 160 */       html = html.replace("$max_stat", maxValue);
/* 161 */       html = html.replace("$click_start_drink_string", clickStartDrinkString);
/* 162 */       html = html.replace("$stat", effect);
/* 163 */       html = html.replace("$click_end_drink_string", clickEndDrinkString);
/*     */     } 
/*     */     
/* 166 */     return html;
/*     */   }
/*     */   
/*     */   private String getItemTypeHtmlTemplate(String itemType, ItemDescription itemDesc, int amount) {
/* 170 */     if (TcgUI.isWindowOpen((Class)VendorWindow.class)) {
/* 171 */       if (amount == 0)
/* 172 */         return (String)this.resourceManager.getManagedResource(String.class, "html/" + itemType + "_noprice.html", CacheType.CACHE_TEMPORARILY).getResource(); 
/* 173 */       if (itemDesc.getItemValue() != null && !itemDesc.getItemValue().isEmpty()) {
/* 174 */         return (String)this.resourceManager.getManagedResource(String.class, "html/" + itemType + "_vendor.html", CacheType.CACHE_TEMPORARILY).getResource();
/*     */       }
/*     */     } else {
/* 177 */       return (String)this.resourceManager.getManagedResource(String.class, "html/" + itemType + ".html", CacheType.CACHE_TEMPORARILY).getResource();
/*     */     } 
/* 179 */     return null;
/*     */   }
/*     */   
/*     */   private String replaceVariablesInPetHtml(String html, ClientPetDescription clientPet) {
/* 183 */     if (html != null) {
/* 184 */       String image = "<img src=\"" + TcgUI.getIconProvider().getPathForElement(clientPet.getElementId().toLowerCase(), 24) + "\" height=\"24\" width=\"24\"/>";
/* 185 */       html = html.replace("$name", JavaLocalization.getInstance().getLocalizedRPGText(clientPet.getName()));
/* 186 */       html = html.replace("$element", image);
/* 187 */       html = html.replace("$description", clientPet.getDescription());
/* 188 */       html = html.replace("$members_only_tooltip", clientPet.isSubscriberOnly() ? getLocalizedText("members.only", new String[0]) : "");
/*     */     } 
/* 190 */     return html;
/*     */   }
/*     */   
/*     */   private String extractImagesFromString(String itemText) {
/* 194 */     while (itemText.contains("{") && itemText.contains("}")) {
/* 195 */       itemText = itemText.substring(0, itemText.indexOf("{")) + "<img src=\"" + itemText.substring(itemText.indexOf("{") + 1, itemText.indexOf("}")) + "\" height=\"20\" width=\"20\"/>" + itemText.substring(itemText.indexOf("}") + 1);
/*     */     }
/*     */ 
/*     */     
/* 199 */     return itemText;
/*     */   }
/*     */   
/*     */   private String replaceVariablesInCrystalHtml(String html, ItemDescription itemDesc, String itemSaleString, String noPriceString, int ammount) {
/* 203 */     if (html != null) {
/* 204 */       html = html.replace("$name", itemDesc.getName());
/* 205 */       html = html.replace("$item_text", extractImagesFromString(itemDesc.getItemText()));
/* 206 */       html = html.replace("$item_type_text", itemDesc.getItemTypeText());
/* 207 */       html = html.replace("$item_sale_string", itemSaleString);
/* 208 */       html = html.replace("$item_value_amount", String.valueOf(ammount));
/*     */       
/* 210 */       html = html.replace("$no_price_string", noPriceString);
/*     */     } 
/* 212 */     return html;
/*     */   }
/*     */   
/*     */   private String replaceVariablesInPotionHtml(String html, ItemDescription itemDesc, String itemSaleString, String noPriceString, int ammount) {
/* 216 */     if (html != null) {
/* 217 */       String abilitiesListHtml = extractImagesFromString(getAbilitiesInfoHtml("", itemDesc.getAbilities()));
/* 218 */       html = html.replace("$name", itemDesc.getName());
/* 219 */       html = html.replace("$item_text", extractImagesFromString(itemDesc.getItemText()));
/* 220 */       html = html.replace("$item_sale_string", itemSaleString);
/* 221 */       html = html.replace("$item_value_amount", String.valueOf(ammount));
/* 222 */       html = html.replace("$item_value", this.itemRegistry.getItemForClassID(itemDesc.getItemValue(), 0).getIcon());
/* 223 */       html = html.replace("$no_price_string", noPriceString);
/* 224 */       html = html.replace("$abilities_list", abilitiesListHtml);
/*     */     } 
/* 226 */     return html;
/*     */   }
/*     */   
/*     */   private String replaceVariablesInEquipmentHtml(String html, ItemDescription itemDesc, String itemSaleString, String noPriceString, String membersOnlyText, int ammount) {
/* 230 */     if (html != null) {
/* 231 */       String abilitiesListHtml = extractImagesFromString(getAbilitiesInfoHtml("", itemDesc.getAbilities()));
/* 232 */       String name = itemDesc.getName();
/*     */ 
/*     */       
/* 235 */       html = html.replace("$name", name);
/* 236 */       html = html.replace("$item_type_text", itemDesc.getItemTypeText());
/* 237 */       html = html.replace("$item_text", extractImagesFromString(itemDesc.getItemText()));
/* 238 */       html = html.replace("$item_sale_string", itemSaleString);
/* 239 */       html = html.replace("$item_value_amount", String.valueOf(ammount));
/* 240 */       html = html.replace("$item_value", this.itemRegistry.getItemForClassID(itemDesc.getItemValue(), 0).getIcon());
/* 241 */       html = html.replace("$no_price_string", noPriceString);
/* 242 */       html = html.replace("$abilities_list", abilitiesListHtml);
/* 243 */       if (!itemDesc.isSubscriberOnly())
/* 244 */         membersOnlyText = ""; 
/* 245 */       html = html.replace("$members_only_tooltip", membersOnlyText);
/*     */     } 
/* 247 */     return html;
/*     */   }
/*     */   
/*     */   private String replaceVariablesInSkillHtml(String html, ItemDescription itemDesc) {
/* 251 */     if (html != null) {
/* 252 */       ShapedAbility shapedAbility = (ShapedAbility)getShapedAbility(itemDesc.getAbilities());
/* 253 */       String shapeName = (shapedAbility != null) ? shapedAbility.getShape().getName() : "";
/* 254 */       ElementAbility elementAbility = (shapedAbility instanceof ElementAbility) ? (ElementAbility)shapedAbility : null;
/* 255 */       ElementDescription elementDesc = (elementAbility != null) ? this.elementManager.getElementDesc(elementAbility.getElement()) : null;
/* 256 */       String elementName = (elementDesc != null) ? elementDesc.getElementName() : "";
/* 257 */       String fontColor = (elementDesc != null) ? elementDesc.getFontColor() : "white";
/* 258 */       String cooldownSeconds = (itemDesc.getLocalCooldownMillis() == 0) ? "no" : ((itemDesc.getLocalCooldownMillis() / 1000) + "s");
/* 259 */       String rank = getLocalizedText("tooltip.rank", new String[0]);
/* 260 */       rank = rank.replace("$rank", "" + itemDesc.getTier());
/*     */       
/* 262 */       html = html.replace("$name", itemDesc.getName() + " " + itemDesc.getTier());
/* 263 */       int playerLevel = MainGameState.getPlayerModel().getStatSupport().getStatById((short)13).getBase();
/* 264 */       html = html.replace("$mana_cost", String.valueOf(ManaCostCalc.calculate(itemDesc.getManaCost(), playerLevel)));
/* 265 */       html = html.replace("$shape_name", shapeName);
/* 266 */       html = html.replace("$rank", rank);
/* 267 */       html = html.replace("$local_cooldown", cooldownSeconds);
/* 268 */       html = html.replace("$element", getTextInColor(elementName, fontColor));
/* 269 */       html = html.replace("$item_text", extractImagesFromString(itemDesc.getItemText()));
/*     */     } 
/* 271 */     return html;
/*     */   }
/*     */   
/*     */   private Ability getShapedAbility(List<Ability> abilities) {
/* 275 */     for (Ability ability : abilities) {
/* 276 */       if (ability instanceof ShapedAbility) {
/* 277 */         return ability;
/*     */       }
/*     */     } 
/* 280 */     return null;
/*     */   }
/*     */   
/*     */   private String getAbilitiesInfoHtml(String abilitiesText, List<Ability> abilities) {
/* 284 */     for (Ability ability : abilities) {
/* 285 */       if (ability.getLanguageKey().isEmpty())
/*     */         continue; 
/* 287 */       abilitiesText = abilitiesText + "<br/>" + ability.getLanguageKey();
/* 288 */       if (ability instanceof AbilityContainer)
/* 289 */         abilitiesText = getAbilitiesInfoHtml(abilitiesText, ((AbilityContainer)ability).getAbilities()); 
/*     */     } 
/* 291 */     return abilitiesText;
/*     */   }
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
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSkillCardHtml(String itemId, int tier) {
/* 317 */     ItemDescription itemDesc = this.itemManager.getDescription(itemId, tier);
/* 318 */     ItemDescription nextTier = null;
/* 319 */     if (this.itemManager.hasDescription(itemId, Integer.valueOf(tier + 1)))
/* 320 */       nextTier = this.itemManager.getDescription(itemId, tier + 1); 
/* 321 */     String html = null;
/* 322 */     if (itemDesc != null) {
/* 323 */       html = (String)this.resourceManager.getManagedResource(String.class, "html/skill2.html").getResource();
/* 324 */       html = replaceVariablesInSkillCardHtml(html, itemDesc, nextTier);
/*     */     } 
/*     */     
/* 327 */     return html;
/*     */   }
/*     */   
/*     */   private String replaceVariablesInSkillCardHtml(String html, ItemDescription itemDesc, ItemDescription nextTier) {
/* 331 */     if (html != null) {
/* 332 */       String breakStr = "<br/>";
/*     */ 
/*     */ 
/*     */       
/* 336 */       String rarity = getLocalizedText("tooltip.rarity", new String[0]);
/* 337 */       rarity = rarity.replace("$rarity", itemDesc.getRarity());
/* 338 */       String manaCost = "";
/* 339 */       if (itemDesc.getManaCost() > 0.0F) {
/* 340 */         manaCost = getLocalizedText("tooltip.manacost", new String[0]);
/* 341 */         int playerLevel = MainGameState.getPlayerModel().getStatSupport().getStatById((short)13).getBase();
/* 342 */         html = html.replace("$mana_cost", String.valueOf(ManaCostCalc.calculate(itemDesc.getManaCost(), playerLevel)));
/* 343 */         manaCost = manaCost + breakStr;
/*     */       } 
/* 345 */       String coolDown = "";
/* 346 */       if (itemDesc.getLocalCooldownMillis() > 0) {
/* 347 */         coolDown = getLocalizedText("tooltip.cooldown", new String[0]);
/* 348 */         String cooldownSeconds = (itemDesc.getLocalCooldownMillis() / 1000.0D) + "s";
/* 349 */         coolDown = coolDown.replace("$cooldown", cooldownSeconds);
/* 350 */         coolDown = coolDown + breakStr;
/*     */       } 
/*     */       
/* 353 */       String nextRankText = "";
/* 354 */       String nextRankAbilities = "";
/* 355 */       if (nextTier != null) {
/* 356 */         nextRankText = getLocalizedText("tooltip.nextrank", new String[0]);
/* 357 */         nextRankText = nextRankText + breakStr;
/* 358 */         nextRankAbilities = extractImagesFromString(getAbilitiesInfoHtml(nextRankAbilities, nextTier.getAbilities()));
/* 359 */         nextRankAbilities = nextRankAbilities + breakStr + breakStr;
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 365 */       html = html.replace("$name", itemDesc.getName() + " " + itemDesc.getTier());
/* 366 */       html = html.replace("$attack_type", itemDesc.getItemText());
/* 367 */       html = html.replace("$mana_cost", manaCost);
/* 368 */       html = html.replace("$cooldown", coolDown);
/* 369 */       html = html.replace("$ability_text", extractImagesFromString(getAbilitiesInfoHtml("", itemDesc.getAbilities())));
/* 370 */       html = html.replace("$next_rank_text", nextRankText);
/* 371 */       html = html.replace("$next_rank_ability_text", nextRankAbilities);
/* 372 */       html = html.replace("$rarity", rarity);
/*     */     } 
/* 374 */     return html;
/*     */   }
/*     */   
/*     */   private String getTextInColor(String text, String color) {
/* 378 */     return "<font color=\"" + color + "\">" + text + "</font>";
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\clien\\ui\TCGToolTipManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */