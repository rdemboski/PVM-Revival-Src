/*     */ package com.funcom.rpgengine2.abilities.projectile;
/*     */ 
/*     */ import com.funcom.commons.geom.RectangleWC;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.RPGFactionFilterType;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*     */ import com.funcom.rpgengine2.abilities.AbstractAbility;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.BuffCreator;
/*     */ import com.funcom.rpgengine2.abilities.EffectFilter;
/*     */ import com.funcom.rpgengine2.abilities.ShapedAbility;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.StatModifier;
/*     */ import com.funcom.rpgengine2.abilities.StatusModifier;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.abilities.TriggerShape;
/*     */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*     */ import com.funcom.rpgengine2.combat.Shape;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.OwnedRpgObject;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import com.funcom.rpgengine2.creatures.TargetValidationSupport;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.LoaderUtils;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
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
/*     */ public class ProjectileCreator
/*     */   extends AbstractAbility
/*     */   implements ActiveAbility, ShapedAbility, AbilityContainer
/*     */ {
/*  49 */   private static final Logger LOGGER = Logger.getLogger(ProjectileCreator.class.getName());
/*  50 */   private static final Class[] SUPPORTED_ABILITIES = new Class[] { ActiveAbility.class, EffectFilter.class, StatModifier.class, StatusModifier.class, BuffCreator.class };
/*     */ 
/*     */ 
/*     */   
/*     */   private TriggerShape triggerShape;
/*     */ 
/*     */   
/*  57 */   private List<Ability> abilities = new ArrayList<Ability>();
/*     */   
/*     */   private boolean initialized;
/*     */   
/*     */   private ProjectileFactory projectileFactory;
/*     */   
/*     */   private Shape shape;
/*     */   private boolean homing;
/*     */   private boolean tunneling;
/*     */   private boolean multipleHitsAllowed;
/*     */   private boolean reflectable;
/*     */   private int lockingWidth;
/*     */   private int lockingHeight;
/*     */   
/*     */   protected ProjectileCreator() {}
/*     */   
/*     */   public ProjectileCreator(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/*  74 */     this.projectileFactory = loader.getProjectileFactory();
/*     */ 
/*     */     
/*  77 */     while (params.next()) {
/*  78 */       init(params);
/*     */       try {
/*  80 */         float triggerWidth = params.getFloat(AbilityParams.ParamName.TRIGGER_WIDTH);
/*  81 */         float triggerHeight = params.getFloat(AbilityParams.ParamName.TRIGGER_HEIGHT);
/*  82 */         this.lockingWidth = params.getInt(AbilityParams.ParamName.LOCKING_WIDTH);
/*  83 */         this.lockingHeight = params.getInt(AbilityParams.ParamName.LOCKING_HEIGHT);
/*  84 */         float movementDistance = params.getFloat(AbilityParams.ParamName.MOVEMENT_DISTANCE);
/*  85 */         int movementTimeMillis = (int)(params.getFloat(AbilityParams.ParamName.MOVEMENT_TIME) * 1000.0F);
/*  86 */         float movementAngle = (float)(params.getFloat(AbilityParams.ParamName.MOVEMENT_ANGLE_DEGREE) * Math.PI / 180.0D);
/*  87 */         float offsetX = params.getFloat(AbilityParams.ParamName.OFFSET_X);
/*  88 */         float offsetY = params.getFloat(AbilityParams.ParamName.OFFSET_Y);
/*  89 */         float offsetDistance = params.getFloat(AbilityParams.ParamName.OFFSET_DISTANCE);
/*     */         
/*  91 */         this.homing = params.getBoolean(AbilityParams.ParamName.HOMING);
/*  92 */         this.tunneling = params.getBoolean(AbilityParams.ParamName.TUNNELING);
/*  93 */         this.multipleHitsAllowed = params.getBoolean(AbilityParams.ParamName.MULTIPLE_HITS_ALLOWED);
/*  94 */         this.reflectable = params.getBoolean(AbilityParams.ParamName.REFLECTABLE);
/*  95 */         float height = -1.0F;
/*  96 */         if (!params.getStr(AbilityParams.ParamName.COLLISION_HEIGHT).isEmpty()) {
/*  97 */           height = params.getFloat(AbilityParams.ParamName.COLLISION_HEIGHT);
/*     */         }
/*  99 */         TriggerShape _triggerShape = new TriggerShape(triggerWidth, triggerHeight, movementDistance, movementTimeMillis, movementAngle, offsetX, offsetY, offsetDistance, height);
/*     */ 
/*     */         
/* 102 */         if (this.initialized) {
/* 103 */           checkInstanceValues(_triggerShape);
/*     */         } else {
/* 105 */           this.triggerShape = _triggerShape;
/*     */         } 
/*     */ 
/*     */         
/* 109 */         String abilityId = params.getStr(AbilityParams.ParamName.ABILITY_ID);
/*     */         
/* 111 */         Ability ability = LoaderUtils.loadAbility(abilityId, parentContainer, params, evaluator, loader, factory, SUPPORTED_ABILITIES);
/*     */         
/* 113 */         String targetShape = params.getStr(AbilityParams.ParamName.SHAPE_REF);
/* 114 */         this.shape = loader.getShapeManager().getShape(targetShape);
/*     */         
/* 116 */         this.abilities.add(ability);
/* 117 */         this.initialized = true;
/* 118 */       } catch (Exception e) {
/* 119 */         throw new RuntimeException("Error parsing projectile id: " + getId() + "\n", e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   private void checkInstanceValues(TriggerShape triggerShape) {
/* 125 */     if (!this.triggerShape.equals(triggerShape)) {
/* 126 */       throw new IllegalArgumentException("trigger shape mismatch (multiple value for same projectile): projectileid=" + this.id + "\nnew=" + triggerShape + "\nold=" + this.triggerShape);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 133 */     TargetProvider chosenTargetProvider = null;
/* 134 */     if (this.homing) {
/* 135 */       chosenTargetProvider = findHomingTarget(parent, evaluator, sourceProvider, targetProviders, usageParams);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 140 */     else if (!targetProviders.isEmpty()) {
/* 141 */       chosenTargetProvider = targetProviders.get(0);
/*     */     } 
/*     */ 
/*     */     
/* 145 */     useAlways(parent, evaluator, sourceProvider, chosenTargetProvider, usageParams);
/*     */   }
/*     */   
/*     */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 149 */     RpgEntity spatialParent = sourceProvider.getSourceObject();
/* 150 */     RpgEntity attacker = sourceProvider.getSourceHandler();
/* 151 */     while (attacker instanceof OwnedRpgObject) {
/* 152 */       attacker = ((OwnedRpgObject)attacker).getOwner();
/*     */     }
/* 154 */     int ownerLevel = ((StatSupport)attacker.getSupport(StatSupport.class)).getLevel();
/* 155 */     float angle = (usageParams.getAngleType() == UsageParams.AngleType.SOURCE) ? evaluator.getAngle(sourceProvider.getSourceObject()) : usageParams.getDefinedAngle();
/*     */     
/* 157 */     ((ProjectileQueue)attacker.getSupport(ProjectileQueue.class)).enqueueProjectile(newProjectile(parent, evaluator, targetProvider, ownerLevel), evaluator.getPos(spatialParent), angle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Projectile newProjectile(Item parent, ShapeDataEvaluator evaluator, TargetProvider lockedTarget, int ownerLevel) {
/* 164 */     return this.projectileFactory.newProjectile(parent, this, this.triggerShape, evaluator, lockedTarget, this.homing, ownerLevel);
/*     */   }
/*     */   
/*     */   public boolean canHit(Item parentItem, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 168 */     return (this.shape.isValidTarget(source, target) && this.shape.canHit(parentItem, evaluator, source, target, usageParams));
/*     */   }
/*     */   
/*     */   public List<Ability> getAbilities() {
/* 172 */     return this.abilities;
/*     */   }
/*     */ 
/*     */   
/*     */   public Shape getShape() {
/* 177 */     return this.shape;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 182 */     int value = 0;
/* 183 */     for (Ability ability : this.abilities)
/* 184 */       value += ability.getDamage(description, statSupport, statToLookFor); 
/* 185 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getIcons() {
/* 190 */     String value = getIcon();
/* 191 */     for (Ability ability : this.abilities)
/* 192 */       value = value + ability.getIcons(); 
/* 193 */     return value;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getLevel() {
/* 198 */     return 0;
/*     */   }
/*     */   
/*     */   public boolean isTunneling() {
/* 202 */     return this.tunneling;
/*     */   }
/*     */   
/*     */   public boolean isMultipleHitsAllowed() {
/* 206 */     return this.multipleHitsAllowed;
/*     */   }
/*     */   
/*     */   public boolean isReflectable() {
/* 210 */     return this.reflectable;
/*     */   }
/*     */   
/*     */   private TargetProvider findHomingTarget(Item parentItem, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 214 */     TargetValidationSupport targetValidationSupport = (TargetValidationSupport)sourceProvider.getSourceObject().getSupport(TargetValidationSupport.class);
/* 215 */     WorldCoordinate ownerPos = evaluator.getPos(sourceProvider.getSourceObject());
/*     */     
/* 217 */     WorldCoordinate targetedPos = new WorldCoordinate(0, 0, usageParams.getDistance(), 0.0D, ownerPos.getMapId(), ownerPos.getInstanceReference());
/* 218 */     targetedPos.rotate(evaluator.getAngle(sourceProvider.getSourceObject()));
/* 219 */     targetedPos.add(ownerPos);
/*     */     
/* 221 */     RectangleWC lockingRectangleWC = new RectangleWC(targetedPos, targetedPos);
/* 222 */     lockingRectangleWC.getOrigin().getTileCoord().translate(-this.lockingWidth / 2, -this.lockingHeight / 2);
/* 223 */     lockingRectangleWC.getExtent().getTileCoord().translate(this.lockingWidth / 2, this.lockingHeight / 2);
/*     */     
/* 225 */     double minDistance = Double.MAX_VALUE;
/* 226 */     TargetProvider chosenTargetProvider = null;
/*     */     
/* 228 */     for (TargetProvider targetProvider : targetProviders) {
/* 229 */       WorldCoordinate targetLockPos = evaluator.getPos(targetProvider.getTargetObject());
/* 230 */       if (targetValidationSupport != null && targetValidationSupport.isValidTarget(targetProvider.getTargetObject(), RPGFactionFilterType.OTHER) && lockingRectangleWC.contains(targetLockPos)) {
/* 231 */         double newD = targetLockPos.distanceTo(targetedPos);
/* 232 */         if (newD < minDistance) {
/* 233 */           chosenTargetProvider = targetProvider;
/* 234 */           minDistance = newD;
/*     */         } 
/*     */       } 
/*     */     } 
/* 238 */     return chosenTargetProvider;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\projectile\ProjectileCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */