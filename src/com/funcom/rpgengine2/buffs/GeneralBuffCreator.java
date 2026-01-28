/*     */ package com.funcom.rpgengine2.buffs;
/*     */ import com.funcom.commons.dfx.DireEffectDescription;
import com.funcom.commons.dfx.NoSuchDFXException;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.AbilityContainer;
import com.funcom.rpgengine2.abilities.AbstractAbility;
import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.BuffCreator;
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
import com.funcom.rpgengine2.abilities.EffectFilter;
/*     */ import com.funcom.rpgengine2.abilities.ImmunityModifier;
import com.funcom.rpgengine2.abilities.ShapedAbility;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
import com.funcom.rpgengine2.abilities.StatModifier;
import com.funcom.rpgengine2.abilities.StatusModifier;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.combat.Shape;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
import com.funcom.rpgengine2.combat.ShapeListener;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.BuffQueue;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
import com.funcom.rpgengine2.loader.LoaderUtils;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class GeneralBuffCreator extends AbstractAbility implements ShapedAbility, BuffCreator, ShapeListener, AbilityContainer {
/*  26 */   private static final Class[] SUPPORTED_ABILITIES = new Class[] { ActiveAbility.class, EffectFilter.class, StatusModifier.class, ImmunityModifier.class, StatModifier.class };
/*     */ 
/*     */   
/*     */   private boolean initialized;
/*     */ 
/*     */   
/*     */   private Shape shape;
/*     */   
/*     */   private int durationMillis;
/*     */   
/*     */   private int totalTicks;
/*     */   
/*     */   private boolean infinite = false;
/*     */   
/*     */   private DireEffectDescription buffDfx;
/*     */   
/*  42 */   private List<Ability> abilities = new ArrayList<Ability>();
/*     */   
/*     */   private ShapeDataEvaluator evaluator;
/*     */   private final BuffType buffType;
/*     */   private Set<Element> cureElements;
/*     */   
/*     */   public GeneralBuffCreator(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, BuffType buffType, ValueAccumulatorFactory factory) {
/*  49 */     this.evaluator = evaluator;
/*  50 */     this.buffType = buffType;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  55 */     while (params.next()) {
/*     */       
/*  57 */       init(params);
/*     */ 
/*     */       
/*  60 */       String _shapeRef = params.getStr(AbilityParams.ParamName.SHAPE_REF);
/*     */ 
/*     */       
/*  63 */       String buffDfxName = params.getStr(AbilityParams.ParamName.BUFF_DFX_REFERENCE);
/*  64 */       if (buffDfxName.isEmpty()) {
/*  65 */         this.buffDfx = null;
/*     */       } else {
/*     */         try {
/*  68 */           this.buffDfx = loader.getDfxDescriptionFactory().getDireEffectDescription(buffDfxName, true);
/*  69 */         } catch (NoSuchDFXException e) {
/*  70 */           this.buffDfx = null;
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/*  75 */       int _durationMillis = (int)(params.getFloat(AbilityParams.ParamName.DURATION) * 1000.0F);
/*  76 */       int _totalTicks = params.getIntOptional(AbilityParams.ParamName.TOTAL_TICKS, 1);
/*     */       
/*  78 */       this.infinite = params.getBoolean(AbilityParams.ParamName.INFINITE);
/*     */       
/*  80 */       if (this.initialized) {
/*  81 */         checkInstanceValues(_durationMillis, _totalTicks, _shapeRef);
/*     */       } else {
/*  83 */         this.durationMillis = _durationMillis;
/*  84 */         this.totalTicks = _totalTicks;
/*  85 */         if (this.totalTicks == 0) {
/*  86 */           throw new RuntimeException("Buff with 0 ticks. id: " + getId());
/*     */         }
/*  88 */         this.shape = loader.getShapeManager().getShape(_shapeRef);
/*  89 */         if (this.shape == null) {
/*  90 */           throw new IllegalArgumentException("cannot find shape: shapeId=" + _shapeRef);
/*     */         }
/*     */       } 
/*     */ 
/*     */       
/*  95 */       Element tmpCureElement = (Element)params.getEnumOptional(AbilityParams.ParamName.ELEMENT, (Enum[])Element.values(), null);
/*  96 */       String tmpCureStatus = params.getStrOptional(AbilityParams.ParamName.STATUS, null);
/*     */       
/*  98 */       if (tmpCureStatus != null) {
/*  99 */         tmpCureStatus = tmpCureStatus.toLowerCase();
/*     */       }
/*     */       
/* 102 */       this.cureElements = addCureData(tmpCureElement, this.cureElements);
/* 103 */       this.cureStatuses = addCureData(tmpCureStatus, this.cureStatuses);
/*     */ 
/*     */       
/* 106 */       String abilityId = params.getStr(AbilityParams.ParamName.ABILITY_ID);
/*     */       
/* 108 */       Ability ability = LoaderUtils.loadAbility(abilityId, parentContainer, params, evaluator, loader, factory, SUPPORTED_ABILITIES);
/*     */       
/* 110 */       this.abilities.add(ability);
/*     */       
/* 112 */       this.initialized = true;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkInstanceValues(int durationMillis, int totalTicks, String shapeRef) {
/* 117 */     if (this.durationMillis != durationMillis) {
/* 118 */       throw new IllegalArgumentException("buff duration mismatch (multiple value for same buff): buffid=" + this.id);
/*     */     }
/* 120 */     if (this.totalTicks != totalTicks) {
/* 121 */       throw new IllegalArgumentException("buff totalTicks mismatch (multiple value for same buff): buffid=" + this.id);
/*     */     }
/* 123 */     if (!this.shape.getId().equals(shapeRef)) {
/* 124 */       throw new IllegalArgumentException("buff shape mismatch (multiple value for same buff): buffid=" + this.id);
/*     */     }
/*     */   }
/*     */   
/*     */   public BuffType getType() {
/* 129 */     return this.buffType;
/*     */   }
/*     */   
/*     */   public boolean matchCureCriterias(Element cureElement, String cureStatus) {
/* 133 */     if (cureElement != null && 
/* 134 */       !this.cureElements.contains(cureElement)) {
/* 135 */       return false;
/*     */     }
/*     */     
/* 138 */     if (cureStatus != null && 
/* 139 */       !this.cureStatuses.contains(cureStatus)) {
/* 140 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 144 */     return true;
/*     */   }
/*     */   
/*     */   public int getLevel() {
/* 148 */     return 0;
/*     */   }
/*     */   
/*     */   public Shape getShape() {
/* 152 */     return this.shape;
/*     */   }
/*     */   
/*     */   public int getUpdateDelayMillis() {
/* 156 */     return this.durationMillis / this.totalTicks;
/*     */   }
/*     */   
/*     */   private Buff createBuff(Item parentItem, SourceProvider sourceProvider, TargetProvider targetProvider) {
/* 160 */     return new Buff(parentItem, this, this.evaluator, sourceProvider, targetProvider, this.infinite);
/*     */   }
/*     */   
/*     */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 164 */     this.shape.process(parent, this, evaluator, sourceProvider, targetProviders, usageParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 169 */     ((BuffQueue)targetProvider.getTargetHandler().getSupport(BuffQueue.class)).enqueue(createBuff(parent, sourceProvider, targetProvider));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHit(Item parent, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 174 */     return (this.shape.isValidTarget(source, target) && this.shape.canHit(parent, evaluator, source, target, usageParams));
/*     */   }
/*     */   
/*     */   public List<Ability> getAbilities() {
/* 178 */     return this.abilities;
/*     */   }
/*     */ 
/*     */   
/*     */   public DireEffectDescription getBuffDfx() {
/* 183 */     return this.buffDfx;
/*     */   }
/*     */ 
/*     */   
/*     */   public Set<String> getCureStatuses() {
/* 188 */     return this.cureStatuses;
/*     */   }
/*     */   
/*     */   public int getDurationMillis() {
/* 192 */     return this.durationMillis;
/*     */   }
/*     */   
/*     */   public void notifyHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 196 */     useAlways(parent, evaluator, sourceProvider, targetProvider, UsageParams.EMPTY_PARAMS);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 201 */     int value = 0;
/* 202 */     for (Ability ability : this.abilities)
/* 203 */       value += ability.getDamage(description, statSupport, statToLookFor); 
/* 204 */     value *= this.totalTicks;
/* 205 */     return value;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\buffs\GeneralBuffCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */