/*     */ package com.funcom.rpgengine2.items;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
/*     */ import com.funcom.commons.dfx.GameplayEffectDescription;
/*     */ import com.funcom.commons.localization.JavaLocalization;
/*     */ import com.funcom.rpgengine2.SkillId;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.BuffCreator;
import com.funcom.rpgengine2.abilities.EffectFilter;
import com.funcom.rpgengine2.abilities.ElementAbility;
import com.funcom.rpgengine2.abilities.ImmunityModifier;
import com.funcom.rpgengine2.abilities.StatModifier;
import com.funcom.rpgengine2.abilities.StatusModifier;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.dfx.DFXValidity;
/*     */ import com.funcom.rpgengine2.equipment.ArchType;
/*     */ import com.funcom.util.DebugManager;

import java.util.ArrayDeque;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Deque;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ItemDescription implements AbilityContainer {
/*  20 */   public static final Class[] SUPPORTED_ABILITIES = new Class[] { ActiveAbility.class, EffectFilter.class, StatModifier.class, StatusModifier.class, ImmunityModifier.class, BuffCreator.class };
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean initialized;
/*     */ 
/*     */   
/*     */   private double globalCooldown;
/*     */ 
/*     */   
/*  30 */   private String id = "";
/*  31 */   private String visualId = "";
/*  32 */   private int tier = 0;
/*  33 */   private int localCooldownMillis = 0;
/*     */   
/*     */   private float manaCost;
/*     */   
/*     */   private String icon;
/*     */   private String rarity;
/*  39 */   private List<Ability> abilities = new ArrayList<Ability>();
/*  40 */   private String itemValue = "";
/*  41 */   private int itemValueAmount = 0;
/*     */   
/*     */   private ItemType itemType;
/*     */   
/*     */   private boolean consumable;
/*     */   
/*     */   private String name;
/*     */   
/*     */   private String itemText;
/*     */   private String itemTypeText;
/*     */   private ArchType archType;
/*     */   private ItemFactory itemFactory;
/*     */   private DireEffectDescription dfxDescription;
/*     */   private DireEffectDescription impactDfxDescription;
/*     */   private int level;
/*     */   private int requiredLevel;
/*     */   private SkillId skillId;
/*     */   private String skillType;
/*     */   private int stackSize;
/*     */   private String setId;
/*     */   private boolean subscriberOnly;
/*  62 */   private float castTime = 0.0F;
/*     */   
/*     */   public ItemDescription(String id, Integer tier, ItemFactory itemFactory) {
/*  65 */     this.id = id;
/*  66 */     this.tier = tier.intValue();
/*  67 */     this.itemFactory = itemFactory;
/*     */   }
/*     */   
/*     */   public String getId() {
/*  71 */     return this.id;
/*     */   }
/*     */   
/*     */   public Item createInstance() {
/*  75 */     return this.itemFactory.newItem(this);
/*     */   }
/*     */   
/*     */   public List<Ability> getAbilities() {
/*  79 */     return this.abilities;
/*     */   }
/*     */   
/*     */   public int getTier() {
/*  83 */     return this.tier;
/*     */   }
/*     */   
/*     */   public int getLocalCooldownMillis() {
/*  87 */     return this.localCooldownMillis;
/*     */   }
/*     */   
/*     */   public String toString() {
/*  91 */     StringBuffer sb = new StringBuffer();
/*  92 */     sb.append("itemDecription=[").append("id=").append(this.id).append(",tier=").append(this.tier).append(",itemType=").append(this.itemType).append(",initialized=").append(this.initialized).append(",localCooldownMillis=").append(this.localCooldownMillis).append(",stackSize=").append(this.stackSize).append(",consumable=").append(this.consumable).append(",itemValue=").append(this.itemValue).append(",itemValueAmount=").append(this.itemValueAmount).append(",archType=").append(this.archType).append("]");
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
/* 104 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public boolean isInitialized() {
/* 108 */     return this.initialized;
/*     */   }
/*     */   
/*     */   public void init(int _tier, int _localCooldownMillis, int _stackSize, double globalCooldown) {
/* 112 */     this.tier = _tier;
/* 113 */     this.localCooldownMillis = _localCooldownMillis;
/* 114 */     this.globalCooldown = globalCooldown;
/* 115 */     this.stackSize = _stackSize;
/* 116 */     this.skillId = new SkillId(getId(), getTier());
/*     */     
/* 118 */     this.initialized = true;
/*     */   }
/*     */   
/*     */   public void checkInstanceValues(int _tier, int _localCooldownMillis, int _stackSize) {
/* 122 */     if (getTier() != _tier) {
/* 123 */       throw new IllegalArgumentException("item tier mismatch (multiple value for same item)");
/*     */     }
/* 125 */     if (getLocalCooldownMillis() != _localCooldownMillis) {
/* 126 */       throw new IllegalArgumentException("item cooldown mismatch (also check global cooldown value) (multiple value for same item)");
/*     */     }
/* 128 */     if (getStackSize() != _stackSize) {
/* 129 */       throw new IllegalArgumentException("stackable value does not match previous value set for this item");
/*     */     }
/*     */   }
/*     */   
/*     */   public void setManaCost(float manaCost) {
/* 134 */     this.manaCost = manaCost;
/*     */   }
/*     */   
/*     */   public float getManaCost() {
/* 138 */     return this.manaCost;
/*     */   }
/*     */   
/*     */   public String getItemValue() {
/* 142 */     return this.itemValue;
/*     */   }
/*     */   
/*     */   public void setItemValue(String itemValue) {
/* 146 */     this.itemValue = itemValue;
/*     */   }
/*     */   
/*     */   public int getItemValueAmount() {
/* 150 */     return this.itemValueAmount;
/*     */   }
/*     */   
/*     */   public void setItemValueAmount(int itemValueAmount) {
/* 154 */     this.itemValueAmount = itemValueAmount;
/*     */   }
/*     */   
/*     */   public ItemType getItemType() {
/* 158 */     return this.itemType;
/*     */   }
/*     */   
/*     */   public void setItemType(ItemType itemType) {
/* 162 */     this.itemType = itemType;
/*     */   }
/*     */   
/*     */   public boolean isConsumable() {
/* 166 */     return this.consumable;
/*     */   }
/*     */   
/*     */   public void setConsumable(boolean consumable) {
/* 170 */     this.consumable = consumable;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 174 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.name);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setName(String name) {
/* 179 */     this.name = name;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public String getItemText() {
/* 184 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.itemText);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setItemText(String itemText) {
/* 189 */     this.itemText = itemText;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public String getItemTypeText() {
/* 194 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.itemTypeText);
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public void setItemTypeText(String itemTypeText) {
/* 199 */     this.itemTypeText = itemTypeText;
/*     */   }
/*     */   
/*     */   public void setSkillType(String skillType) {
/* 203 */     this.skillType = skillType;
/*     */   }
/*     */   
/*     */   public String getSkillType() {
/* 207 */     return this.skillType;
/*     */   }
/*     */   
/*     */   public void setDfxDescription(DireEffectDescription dfxDescription) {
/* 211 */     this.dfxDescription = dfxDescription;
/*     */   }
/*     */   
/*     */   public DireEffectDescription getDfxDescription() {
/* 215 */     return this.dfxDescription;
/*     */   }
/*     */   
/*     */   public void setImpactDfxDescription(DireEffectDescription impactDfxDescription) {
/* 219 */     this.impactDfxDescription = impactDfxDescription;
/*     */   }
/*     */   
/*     */   public DireEffectDescription getImpactDfxDescription() {
/* 223 */     return this.impactDfxDescription;
/*     */   }
/*     */   
/*     */   public DFXValidity validateFireEffect() {
/* 227 */     DFXValidity ret = new DFXValidity(getId());
/*     */     
/* 229 */     List<GameplayEffectDescription> gameDescriptions = this.dfxDescription.getEffectDescriptions(GameplayEffectDescription.class);
/*     */ 
/*     */     
/* 232 */     for (GameplayEffectDescription description : gameDescriptions) {
/* 233 */       String reference = description.getResource();
/* 234 */       if (!reference.isEmpty() && !hasDFXReference(reference)) {
/* 235 */         ret.addMissingItemReference(reference);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 240 */     int size = this.abilities.size();
/* 241 */     for (int i = 0; i < size; i++) {
/* 242 */       String reference = ((Ability)this.abilities.get(i)).getDFXReference();
/* 243 */       if (!foundInDFX(reference, gameDescriptions)) {
/* 244 */         ret.addUnusedItemReference(reference);
/*     */       }
/*     */     } 
/*     */     
/* 248 */     return ret;
/*     */   }
/*     */   
/*     */   private boolean hasDFXReference(String reference) {
/* 252 */     int size = this.abilities.size();
/* 253 */     for (int i = 0; i < size; i++) {
/* 254 */       Ability ability = this.abilities.get(i);
/* 255 */       if (ability.getDFXReference().equals(reference)) {
/* 256 */         return true;
/*     */       }
/*     */     } 
/* 259 */     return false;
/*     */   }
/*     */   
/*     */   private static boolean foundInDFX(String reference, List<GameplayEffectDescription> gameDescriptions) {
/* 263 */     int size = gameDescriptions.size();
/* 264 */     for (int i = 0; i < size; i++) {
/* 265 */       if (reference.equals(((GameplayEffectDescription)gameDescriptions.get(i)).getResource())) {
/* 266 */         return true;
/*     */       }
/*     */     } 
/* 269 */     return false;
/*     */   }
/*     */   
/*     */   public double getGlobalCooldown() {
/* 273 */     return this.globalCooldown;
/*     */   }
/*     */   
/*     */   public int getLevel() {
/* 277 */     return this.level;
/*     */   }
/*     */   
/*     */   public void setLevel(int level) {
/* 281 */     this.level = level;
/*     */   }
/*     */   
/*     */   public int getRequiredLevelFrom() {
/* 285 */     if (this.abilities.isEmpty()) {
/* 286 */       return Integer.MIN_VALUE;
/*     */     }
/* 288 */     int minLevel = Integer.MAX_VALUE;
/* 289 */     for (Ability ability : this.abilities) {
/* 290 */       if (minLevel > ability.getLevelFrom()) {
/* 291 */         minLevel = ability.getLevelFrom();
/*     */       }
/*     */     } 
/* 294 */     return minLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getRequiredLevelTo() {
/* 299 */     if (this.abilities.isEmpty()) {
/* 300 */       return Integer.MAX_VALUE;
/*     */     }
/* 302 */     int maxLevel = Integer.MIN_VALUE;
/* 303 */     for (Ability ability : this.abilities) {
/* 304 */       if (maxLevel < ability.getLevelTo()) {
/* 305 */         maxLevel = ability.getLevelTo();
/*     */       }
/*     */     } 
/* 308 */     return maxLevel;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValidLevel(int userLevel) {
/* 313 */     return (userLevel >= getRequiredLevelFrom() && userLevel <= getRequiredLevelTo());
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(RpgEntity rpgEntity, short statToLookFor) {
/* 318 */     int value = 0;
/* 319 */     for (Ability ability : this.abilities)
/* 320 */       value += ability.getDamage(this, rpgEntity, statToLookFor); 
/* 321 */     return value;
/*     */   }
/*     */   
/*     */   public String getIcon() {
/* 325 */     return this.icon;
/*     */   }
/*     */   
/*     */   public void setIcon(String icon) {
/* 329 */     this.icon = icon;
/*     */   }
/*     */   
/*     */   public String getRarity() {
/* 333 */     return JavaLocalization.getInstance().getLocalizedRPGText(this.rarity);
/*     */   }
/*     */   
/*     */   public void setRarity(String rarity) {
/* 337 */     this.rarity = rarity;
/*     */   }
/*     */   
/*     */   public SkillId getSkillId() {
/* 341 */     return this.skillId;
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
/*     */   public Element getElement() {
/* 354 */     Element element = getElementNoReplacement();
/*     */     
/* 356 */     if (element == null) {
/* 357 */       DebugManager.getInstance().warn("no element found for item: itemId=" + getId() + "\n");
/* 358 */       element = Element.ROCK1;
/*     */     } 
/*     */     
/* 361 */     return element;
/*     */   }
/*     */   
/*     */   Element getElementNoReplacement() {
/* 365 */     return recursiveElementSearch(this, null);
/*     */   }
/*     */   
/*     */   private Element recursiveElementSearch(AbilityContainer abilityContainer, Deque<AbilityContainer> queue) {
/* 369 */     List<Ability> abilities = abilityContainer.getAbilities();
/*     */     
/* 371 */     Element element = findElement(abilities);
/* 372 */     if (element != null) {
/* 373 */       return element;
/*     */     }
/*     */     
/* 376 */     queue = enqueueAbilityContainers(abilities, queue);
/*     */     
/* 378 */     if (queue.isEmpty()) {
/* 379 */       return null;
/*     */     }
/* 381 */     return recursiveElementSearch(queue.removeFirst(), queue);
/*     */   }
/*     */ 
/*     */   
/*     */   private Deque<AbilityContainer> enqueueAbilityContainers(List<Ability> abilities, Deque<AbilityContainer> queue) {
/* 386 */     if (queue == null) {
/* 387 */       queue = new ArrayDeque<AbilityContainer>();
/*     */     }
/* 389 */     for (Ability ability : abilities) {
/* 390 */       if (ability instanceof AbilityContainer) {
/* 391 */         queue.addLast((AbilityContainer)ability);
/*     */       }
/*     */     } 
/* 394 */     return queue;
/*     */   }
/*     */   
/*     */   private Element findElement(List<Ability> abilities) {
/* 398 */     Element element = null;
/* 399 */     for (Ability ability : abilities) {
/* 400 */       if (ability instanceof ElementAbility) {
/* 401 */         element = ((ElementAbility)ability).getElement();
/* 402 */         if (element == null) {
/* 403 */           DebugManager.getInstance().warn("missing element from elementability: abilityId=" + ability.getId() + " class=" + ability.getClass());
/*     */           
/*     */           continue;
/*     */         } 
/*     */         
/*     */         break;
/*     */       } 
/*     */     } 
/* 411 */     return element;
/*     */   }
/*     */   
/*     */   public boolean isLowerTierOf(ItemDescription other) {
/* 415 */     boolean isSameItemClass = getId().equals(other.getId());
/* 416 */     if (isSameItemClass) {
/* 417 */       boolean thisIsLowerTiered = (getTier() < other.getTier());
/* 418 */       return thisIsLowerTiered;
/*     */     } 
/* 420 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/* 425 */     if (this == o) {
/* 426 */       return true;
/*     */     }
/* 428 */     if (!(o instanceof ItemDescription)) {
/* 429 */       return false;
/*     */     }
/*     */     
/* 432 */     ItemDescription that = (ItemDescription)o;
/*     */     
/* 434 */     if (this.tier != that.tier) {
/* 435 */       return false;
/*     */     }
/* 437 */     if (!this.id.equals(that.id)) {
/* 438 */       return false;
/*     */     }
/*     */     
/* 441 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 446 */     int result = this.id.hashCode();
/* 447 */     result = 31 * result + this.tier;
/* 448 */     return result;
/*     */   }
/*     */   
/*     */   public int getStackSize() {
/* 452 */     return this.stackSize;
/*     */   }
/*     */   
/*     */   public void setSetId(String setid) {
/* 456 */     this.setId = setid;
/*     */   }
/*     */   
/*     */   public String getSetId() {
/* 460 */     return this.setId;
/*     */   }
/*     */   
/*     */   public boolean isSubscriberOnly() {
/* 464 */     return this.subscriberOnly;
/*     */   }
/*     */   
/*     */   public void setSubscriberOnly(boolean subscriberOnly) {
/* 468 */     this.subscriberOnly = subscriberOnly;
/*     */   }
/*     */   
/*     */   public String getVisualId() {
/* 472 */     return this.visualId;
/*     */   }
/*     */   
/*     */   public void setVisualId(String visualId) {
/* 476 */     this.visualId = visualId;
/*     */   }
/*     */   
/*     */   public void setCastTime(float castTime) {
/* 480 */     this.castTime = castTime;
/*     */   }
/*     */   
/*     */   public float getCastTime() {
/* 484 */     return this.castTime;
/*     */   }
/*     */   
/*     */   public ArchType getArchType() {
/* 488 */     return this.archType;
/*     */   }
/*     */   
/*     */   public void setArchType(ArchType archType) {
/* 492 */     this.archType = archType;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\items\ItemDescription.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */