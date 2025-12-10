/*     */ package com.funcom.tcg.client.model.rpg;
/*     */ 
/*     */ import com.funcom.commons.utils.GlobalTime;
/*     */ import com.funcom.gameengine.model.action.Action;
/*     */ import com.funcom.gameengine.model.action.DFXInvokerAction;
/*     */ import com.funcom.gameengine.model.props.InteractibleProp;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.combat.ManaCostCalc;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemType;
/*     */ import com.funcom.server.common.Message;
/*     */ import com.funcom.tcg.client.net.NetworkHandler;
/*     */ import com.funcom.tcg.client.ui.inventory.InventoryItem;
/*     */ import com.funcom.tcg.net.message.UseItemMessage;
/*     */ import com.funcom.tcg.rpg.ItemHolderType;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ClientItem
/*     */   implements InventoryItem
/*     */ {
/*  30 */   private static final Logger LOGGER = Logger.getLogger(ClientItem.class.getName());
/*     */   
/*     */   private ItemDescription itemDescription;
/*     */   
/*     */   private int amount;
/*     */   private long cooldownUntil;
/*     */   public static final String UNACQUIRED_STRING = "_unacquired";
/*     */   
/*     */   public ClientItem(ItemDescription itemDescription) {
/*  39 */     setItemDescription(itemDescription);
/*  40 */     this.amount = 1;
/*     */   }
/*     */   
/*     */   public String getClassId() {
/*  44 */     return this.itemDescription.getId();
/*     */   }
/*     */   
/*     */   public String getVisualId() {
/*  48 */     return this.itemDescription.getVisualId();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  52 */     return this.itemDescription.getName();
/*     */   }
/*     */   
/*     */   public String getIcon() {
/*  56 */     return this.itemDescription.getIcon();
/*     */   }
/*     */   
/*     */   public String getIconBlackAndWhite() {
/*  60 */     String originalIcon = getIcon();
/*  61 */     String name = originalIcon.substring(0, originalIcon.lastIndexOf("."));
/*  62 */     return name.concat("_unacquired").concat(".png");
/*     */   }
/*     */   
/*     */   public String getItemText() {
/*  66 */     return this.itemDescription.getItemText();
/*     */   }
/*     */   
/*     */   public ItemType getItemType() {
/*  70 */     return this.itemDescription.getItemType();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getStackSize() {
/*  75 */     return this.itemDescription.getStackSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSetId() {
/*  80 */     return this.itemDescription.getSetId();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSubscriberOnly() {
/*  85 */     return this.itemDescription.isSubscriberOnly();
/*     */   }
/*     */   
/*     */   public int getLevel() {
/*  89 */     return this.itemDescription.getLevel();
/*     */   }
/*     */   
/*     */   public long getCooldownMillis() {
/*  93 */     return this.itemDescription.getLocalCooldownMillis();
/*     */   }
/*     */   
/*     */   public List<Ability> getAbilities() {
/*  97 */     return this.itemDescription.getAbilities();
/*     */   }
/*     */   
/*     */   public int getAmount() {
/* 101 */     return this.amount;
/*     */   }
/*     */   
/*     */   public void setAmount(int newAmount) {
/* 105 */     this.amount = newAmount;
/*     */   }
/*     */   
/*     */   public boolean isReady() {
/* 109 */     return (this.cooldownUntil <= GlobalTime.getInstance().getCurrentTime());
/*     */   }
/*     */   
/*     */   public void use(ClientPlayer source, int containerId, int containerType, int slotId, float rotation, double distance) {
/* 113 */     if (isReady() && source.checkGlobalCooldown(getGlobalCooldown())) {
/* 114 */       LOGGER.log((Priority)Level.INFO, "Using containerID/slotID: " + containerId + "/" + slotId);
/*     */       try {
/* 116 */         requestUse(source, containerId, containerType, slotId, rotation, distance);
/* 117 */         updateCooldown(source);
/* 118 */         executeUseDFX(source);
/* 119 */       } catch (InterruptedException e) {
/* 120 */         LOGGER.log((Priority)Level.ERROR, "Use failed!", e);
/*     */       } 
/*     */     } else {
/* 123 */       LOGGER.log((Priority)Level.INFO, "ClientItem.use: In cooldown. ContainerId=" + containerId + ", slotId=" + slotId + ", cooldownUntil=" + this.cooldownUntil);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateCooldown(ClientPlayer source) {
/* 128 */     this.cooldownUntil = GlobalTime.getInstance().getCurrentTime() + getCooldownMillis();
/* 129 */     source.setGlobalCooldown(getGlobalCooldown());
/*     */   }
/*     */   
/*     */   private void requestUse(ClientPlayer source, int containerId, int containerType, int slotId, float rotation, double distance) throws InterruptedException {
/* 133 */     UseItemMessage useItemMessage = new UseItemMessage(source.getId(), ItemHolderType.valueById(containerType), containerId, slotId, rotation, distance);
/* 134 */     NetworkHandler.instance().getIOHandler().send((Message)useItemMessage);
/*     */   }
/*     */   
/*     */   public void executeUseDFX(ClientPlayer source) {
/* 138 */     String dFXScript = getDFXScript();
/* 139 */     if (dFXScript != null && dFXScript.length() > 0) {
/* 140 */       source.invokeAction((Action)new DFXInvokerAction(dFXScript), (InteractibleProp)source, dFXScript);
/*     */     }
/*     */   }
/*     */   
/*     */   public int getManaCost(int maxMana) {
/* 145 */     return ManaCostCalc.calculate(this.itemDescription.getManaCost(), maxMana);
/*     */   }
/*     */   
/*     */   public boolean isConsumable() {
/* 149 */     return this.itemDescription.isConsumable();
/*     */   }
/*     */   
/*     */   public String getValueClassId() {
/* 153 */     return this.itemDescription.getItemValue();
/*     */   }
/*     */   
/*     */   public int getValueAmount() {
/* 157 */     return this.itemDescription.getItemValueAmount();
/*     */   }
/*     */   
/*     */   public float getCooldownFraction() {
/* 161 */     if (isReady()) {
/* 162 */       return 1.0F;
/*     */     }
/* 164 */     long remainingTime = this.cooldownUntil - GlobalTime.getInstance().getCurrentTime();
/* 165 */     float fraction = 1.0F - (float)remainingTime / (float)getCooldownMillis();
/*     */     
/* 167 */     fraction = Math.min(fraction, 1.0F);
/* 168 */     fraction = Math.max(fraction, 0.0F);
/* 169 */     return fraction;
/*     */   }
/*     */   
/*     */   public String getDFXScript() {
/* 173 */     return (this.itemDescription.getDfxDescription() == null) ? null : this.itemDescription.getDfxDescription().getId();
/*     */   }
/*     */   
/*     */   public int getTier() {
/* 177 */     return this.itemDescription.getTier();
/*     */   }
/*     */   
/*     */   public int getDamageValue(RpgEntity rpgEntity) {
/* 181 */     return this.itemDescription.getDamage(rpgEntity, (short)12);
/*     */   }
/*     */   
/*     */   public void setItemDescription(ItemDescription itemDescription) {
/* 185 */     if (itemDescription.getDfxDescription() == null)
/* 186 */       LOGGER.error("Item missing DFX: " + itemDescription.getId() + "+ Tier: " + itemDescription.getTier()); 
/* 187 */     this.itemDescription = itemDescription;
/*     */   }
/*     */   
/*     */   public String getAbilityIcons() {
/* 191 */     String value = "";
/* 192 */     for (Ability ability : this.itemDescription.getAbilities()) {
/* 193 */       value = value + ability.getIcons();
/*     */     }
/* 195 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getGlobalCooldown() {
/* 200 */     return this.itemDescription.getGlobalCooldown();
/*     */   }
/*     */   
/*     */   public String getItemTypeText() {
/* 204 */     return this.itemDescription.getItemTypeText();
/*     */   }
/*     */   
/*     */   public String getSkillType() {
/* 208 */     return this.itemDescription.getSkillType();
/*     */   }
/*     */   
/*     */   public ItemDescription getItemDescription() {
/* 212 */     return this.itemDescription;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\rpg\ClientItem.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */