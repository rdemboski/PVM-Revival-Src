/*     */ package com.funcom.rpgengine2.abilities.charged;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.rpgengine2.abilities.AbstractAbility;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.ChargedAbilitySupport;
/*     */ import com.funcom.rpgengine2.creatures.DFXSupport;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgSourceProviderEntity;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemManager;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ChargedAbilityCreator
/*     */   extends AbstractAbility
/*     */   implements ActiveAbility
/*     */ {
/*     */   private String itemId;
/*     */   private int tier;
/*     */   private ItemManager itemManager;
/*     */   private int chargedTier;
/*     */   private String chargedItemId;
/*     */   private int maxCharges;
/*     */   
/*     */   public ChargedAbilityCreator(AbilityParams params, RpgLoader loader) {
/*  36 */     this.itemManager = loader.getItemManager();
/*     */ 
/*     */     
/*  39 */     while (params.next()) {
/*  40 */       init(params);
/*     */       
/*  42 */       this.maxCharges = params.getInt(AbilityParams.ParamName.MAX_CHARGES);
/*     */ 
/*     */       
/*  45 */       this.tier = params.getInt(AbilityParams.ParamName.TIER);
/*  46 */       this.itemId = params.getStr(AbilityParams.ParamName.ABILITY_ID);
/*     */ 
/*     */       
/*  49 */       this.chargedTier = params.getInt(AbilityParams.ParamName.TIER2);
/*  50 */       this.chargedItemId = params.getStr(AbilityParams.ParamName.ABILITY_ID2);
/*     */       
/*  52 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getItemId() {
/*  57 */     return this.itemId;
/*     */   }
/*     */   
/*     */   public String getChargedItemId() {
/*  61 */     return this.chargedItemId;
/*     */   }
/*     */   
/*     */   public int getTier() {
/*  65 */     return this.tier;
/*     */   }
/*     */   
/*     */   public int getChargedTier() {
/*  69 */     return this.chargedTier;
/*     */   }
/*     */ 
/*     */   
/*     */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/*  74 */     TargetProvider targetProvider = null;
/*  75 */     if (!targetProviders.isEmpty()) {
/*  76 */       targetProvider = targetProviders.get(0);
/*     */     }
/*     */     
/*  79 */     useAlways(parent, evaluator, sourceProvider, targetProvider, usageParams);
/*     */   }
/*     */   
/*     */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/*     */     Item item;
/*  84 */     ChargedAbilitySupport support = (ChargedAbilitySupport)sourceProvider.getSourceHandler().getSupport(ChargedAbilitySupport.class);
/*  85 */     int charges = support.getCharges(this.id);
/*     */ 
/*     */ 
/*     */     
/*  89 */     if (charges != this.maxCharges) {
/*  90 */       item = this.itemManager.getDescription(this.itemId, this.tier).createInstance();
/*  91 */       support.incrementCharge(this.id);
/*     */     } else {
/*  93 */       item = this.itemManager.getDescription(this.chargedItemId, this.chargedTier).createInstance();
/*  94 */       support.resetCharges(this.id);
/*     */     } 
/*     */     
/*  97 */     item.setOwner((RpgSourceProviderEntity)sourceProvider.getSourceObject());
/*     */     
/*  99 */     DireEffect dfx = item.getDescription().getDfxDescription().createInstance(item, usageParams);
/* 100 */     ((DFXSupport)sourceProvider.getSourceObject().getSupport(DFXSupport.class)).add(dfx);
/* 101 */     dfx.update(0.0D);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHit(Item parentItem, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity rpgEntity, short statToLookFor) {
/* 111 */     return this.itemManager.getDescription(this.itemId, this.tier).getDamage(rpgEntity, statToLookFor);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\charged\ChargedAbilityCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */