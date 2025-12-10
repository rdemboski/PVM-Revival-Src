/*     */ package com.funcom.rpgengine2.abilities;
/*     */ import com.funcom.rpgengine2.Persistence;
/*     */ import com.funcom.rpgengine2.StatCollection;
/*     */ import com.funcom.rpgengine2.StatEffect;
/*     */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulator;
/*     */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.combat.Shape;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.ShapeListener;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.CriticalDamageSupportable;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.StatEffectQueue;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Level;
/*     */ import org.apache.log4j.Logger;
/*     */ import org.apache.log4j.Priority;
/*     */ 
/*     */ public class GeneralEffectCreator extends AbstractAbility implements ShapedAbility, ElementAbility, ActiveAbility, ShapeListener {
/*  26 */   private static final Logger LOG = Logger.getLogger(GeneralEffectCreator.class.getName());
/*     */   
/*     */   private Short targetStatId;
/*     */   
/*     */   private Persistence targetStatPersistence;
/*     */   private Element element;
/*     */   private ValueAccumulator accumulator;
/*     */   private Shape shape;
/*     */   private boolean critPossible;
/*     */   private float spellPowerCoeff;
/*     */   private float spellPowerCoeffA;
/*     */   private float spellPowerCoeffB;
/*     */   private float spellPowerCoeffC;
/*     */   private Short levelStatId;
/*     */   
/*     */   public GeneralEffectCreator() {}
/*     */   
/*     */   protected GeneralEffectCreator(Element element) {
/*  44 */     this.element = element;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GeneralEffectCreator(AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/*  51 */     if (params.getRecordCount() > 1) {
/*  52 */       throw new IllegalArgumentException("Multirow not supported for Effect Creator");
/*     */     }
/*  54 */     params.next();
/*     */     
/*  56 */     init(params);
/*     */     
/*  58 */     String shapeRef = params.getStr(AbilityParams.ParamName.SHAPE_REF);
/*     */     
/*  60 */     this.shape = loader.getShapeManager().getShape(shapeRef);
/*  61 */     if (this.shape == null) {
/*  62 */       throw new IllegalArgumentException("cannot find shape: shapeId=" + shapeRef);
/*     */     }
/*     */     
/*     */     try {
/*  66 */       this.accumulator = (ValueAccumulator)factory.createValueAccumulator(loader, params);
/*  67 */     } catch (IllegalArgumentException e) {
/*  68 */       throw new RuntimeException("Error parsing Effect Creator: id=" + this.id, e);
/*     */     } 
/*     */     
/*  71 */     String targetStatIdStr = params.getStrNonEmpty(AbilityParams.ParamName.TARGET_STAT_ID);
/*  72 */     this.targetStatId = loader.getStatIdTranslator().translate(targetStatIdStr);
/*  73 */     this.targetStatPersistence = (Persistence)params.getEnum(AbilityParams.ParamName.TARGET_STAT_PERSISTENCE, (Enum[])Persistence.values());
/*  74 */     this.spellPowerCoeff = params.getFloat(AbilityParams.ParamName.SPELL_POWER_COEF, 0.0F);
/*  75 */     this.spellPowerCoeffA = params.getFloat(AbilityParams.ParamName.SPELL_POWER_COEF_A, 0.0F);
/*  76 */     this.spellPowerCoeffB = params.getFloat(AbilityParams.ParamName.SPELL_POWER_COEF_B, 0.0F);
/*  77 */     this.spellPowerCoeffC = params.getFloat(AbilityParams.ParamName.SPELL_POWER_COEF_C, 0.0F);
/*  78 */     this.element = (Element)params.getEnum(AbilityParams.ParamName.ELEMENT, (Enum[])Element.values());
/*     */     
/*  80 */     this.critPossible = params.getYesNo(AbilityParams.ParamName.CRIT);
/*     */     
/*  82 */     this.levelStatId = loader.getLevelStatId();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/*  88 */     this.shape.process(parent, this, evaluator, sourceProvider, targetProviders, usageParams);
/*     */   }
/*     */   
/*     */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/*  92 */     notifyHit(parent, evaluator, sourceProvider, targetProvider, usageParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHit(Item parent, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/*  97 */     return (this.shape.isValidTarget(source, target) && this.shape.canHit(parent, evaluator, source, target, usageParams));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 104 */     RpgEntity source = sourceProvider.getSourceHandler();
/* 105 */     RpgEntity target = targetProvider.getTargetObject();
/*     */     
/* 107 */     int value = this.accumulator.eval((AbilityContainer)parent.getDescription(), source);
/*     */     
/* 109 */     boolean isCrit = false;
/* 110 */     if (this.critPossible) {
/* 111 */       RpgEntity sourceHandler = sourceProvider.getSourceHandler();
/* 112 */       CriticalDamageSupportable damageSupportable = (CriticalDamageSupportable)sourceHandler.getSupport(CriticalDamageSupportable.class);
/*     */ 
/*     */       
/* 115 */       if (damageSupportable != null) {
/* 116 */         int newValue = damageSupportable.processCriticalDamage(value, target);
/* 117 */         isCrit = (newValue != value);
/* 118 */         value = newValue;
/*     */       } 
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 126 */     StatEffect statEffect = new StatEffect(parent, sourceProvider, targetProvider, this.targetStatId, this.targetStatPersistence, value, this.element);
/* 127 */     if (this.spellPowerCoeffA != 0.0F || this.spellPowerCoeffB != 0.0F || this.spellPowerCoeffC != 0.0F) {
/* 128 */       StatCollection statSupport = ((StatSupport)source.getSupport(StatSupport.class)).getStatCollection();
/* 129 */       int level = statSupport.get(this.levelStatId).getSum();
/* 130 */       float spellPowerCoeffTemp = this.spellPowerCoeffA * level + this.spellPowerCoeffB + this.spellPowerCoeffC / level;
/* 131 */       statEffect.setAbilityValue("powerFactor", spellPowerCoeffTemp);
/*     */     } else {
/* 133 */       statEffect.setAbilityValue("powerFactor", this.spellPowerCoeff);
/*     */     } 
/* 135 */     statEffect.setAbilityValue("level", parent.getDescription().getLevel());
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 140 */     statEffect.setAbilityValue("baseValue", statEffect.getOriginalValue());
/* 141 */     if (isCrit) {
/* 142 */       statEffect.setAbilityValue("crit", Float.NaN);
/*     */     }
/* 144 */     if (LOG.isEnabledFor((Priority)Level.INFO)) {
/* 145 */       LOG.info("CREATE EFFECT:" + getId() + ":" + "\n '" + sourceProvider.getSourceObject() + "' hit '" + ((target != null) ? target : targetProvider.getTargetHandler()) + "' with" + "\n " + statEffect + "\n (before source filter value=" + value + ")");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 152 */     ((StatEffectQueue)targetProvider.getTargetHandler().getSupport(StatEffectQueue.class)).enqueueEffect(statEffect);
/*     */     
/* 154 */     sourceProvider.notifyHit(targetProvider);
/*     */   }
/*     */   
/*     */   public Shape getShape() {
/* 158 */     return this.shape;
/*     */   }
/*     */   
/*     */   public Element getElement() {
/* 162 */     return this.element;
/*     */   }
/*     */   
/*     */   public float getSpellPowerCoeff() {
/* 166 */     return this.spellPowerCoeff;
/*     */   }
/*     */   
/*     */   public void setSpellPowerCoeff(float spellPowerCoeff) {
/* 170 */     this.spellPowerCoeff = spellPowerCoeff;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity rpgEntity, short statToLookFor) {
/* 175 */     if (statToLookFor == this.targetStatId.shortValue())
/* 176 */       return this.accumulator.eval((AbilityContainer)description, rpgEntity); 
/* 177 */     return 0;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\GeneralEffectCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */