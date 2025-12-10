/*     */ package com.funcom.rpgengine2.abilities.targetedeffect;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*     */ import com.funcom.rpgengine2.abilities.AbstractAbility;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.OwnedRpgObject;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.TargetedEffectSupport;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.items.ItemManager;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TargetedEffectCreator
/*     */   extends AbstractAbility implements ActiveAbility, AbilityContainer {
/*     */   private TargetedEffectFactory targetedEffectFactory;
/*     */   private ItemManager itemManager;
/*     */   private double minDistance;
/*     */   private double maxDistance;
/*     */   private String itemId;
/*  29 */   private int tier = 0;
/*     */   
/*     */   public TargetedEffectCreator(AbilityParams params, RpgLoader loader) {
/*  32 */     this.targetedEffectFactory = loader.getTargetedEffectFactory();
/*  33 */     this.itemManager = loader.getItemManager();
/*     */ 
/*     */     
/*  36 */     while (params.next()) {
/*  37 */       init(params);
/*     */       
/*  39 */       this.minDistance = params.getFloat(AbilityParams.ParamName.MIN_DISTANCE);
/*  40 */       this.maxDistance = params.getFloat(AbilityParams.ParamName.MAX_DISTANCE);
/*  41 */       this.tier = params.getInt(AbilityParams.ParamName.TIER);
/*     */       
/*  43 */       this.itemId = params.getStr(AbilityParams.ParamName.ABILITY_ID);
/*     */       
/*  45 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public String getItemId() {
/*  50 */     return this.itemId;
/*     */   }
/*     */   
/*     */   private double reevaluateDistance(double distance) {
/*  54 */     distance = Math.max(this.minDistance, distance);
/*  55 */     return Math.min(this.maxDistance, distance);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void useIfHit(Item item, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/*  64 */     TargetProvider targetProvider = null;
/*  65 */     if (!targetProviders.isEmpty()) {
/*  66 */       targetProvider = targetProviders.get(0);
/*     */     }
/*     */     
/*  69 */     useAlways(item, evaluator, sourceProvider, targetProvider, usageParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public void useAlways(Item item, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/*  74 */     RpgEntity spatialParent = sourceProvider.getSourceObject();
/*  75 */     RpgEntity attacker = sourceProvider.getSourceHandler();
/*  76 */     while (attacker instanceof OwnedRpgObject) {
/*  77 */       attacker = ((OwnedRpgObject)attacker).getOwner();
/*     */     }
/*     */     
/*  80 */     float angle = (usageParams.getAngleType() == UsageParams.AngleType.SOURCE) ? evaluator.getAngle(sourceProvider.getSourceObject()) : usageParams.getDefinedAngle();
/*  81 */     ((TargetedEffectSupport)attacker.getSupport(TargetedEffectSupport.class)).enqueueTargetedEffect(newTargetedEffect(this.itemManager.getDescription(this.itemId, this.tier).createInstance(), evaluator, reevaluateDistance(usageParams.getDistance()), evaluator.getPos(spatialParent), angle));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private TargetedEffect newTargetedEffect(Item parent, ShapeDataEvaluator evaluator, double distance, WorldCoordinate wc, float angle) {
/*  88 */     return this.targetedEffectFactory.newTargetedEffect(parent, this, evaluator, distance, wc, angle);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHit(Item item, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/*  93 */     float angle = (usageParams.getAngleType() == UsageParams.AngleType.SOURCE) ? evaluator.getAngle(source) : usageParams.getDefinedAngle();
/*  94 */     double angleToTarget = evaluator.evalAngleLocal(angle, source, target, 0.0D, 0.0D, usageParams);
/*  95 */     double distance = evaluator.getPos(source).distanceTo(evaluator.getPos(target));
/*  96 */     return (Math.abs(angleToTarget) < 0.01D && distance >= this.minDistance && distance <= this.maxDistance);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 101 */     return this.itemManager.getDescription(this.itemId, this.tier).getDamage(statSupport, statToLookFor);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcons() {
/* 106 */     String value = getIcon();
/* 107 */     List<Ability> abilities = this.itemManager.getDescription(this.itemId, this.tier).getAbilities();
/* 108 */     for (Ability ability : abilities)
/* 109 */       value = value + ability.getIcons(); 
/* 110 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 115 */     return 0;
/*     */   }
/*     */   
/*     */   public int getTier() {
/* 119 */     return this.tier;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Ability> getAbilities() {
/* 124 */     return this.itemManager.getDescription(this.itemId, this.tier).getAbilities();
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\targetedeffect\TargetedEffectCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */