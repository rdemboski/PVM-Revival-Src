/*     */ package com.funcom.rpgengine2.abilities.projectile;
/*     */ 
/*     */ import com.funcom.commons.Vector2d;
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.abilities.Ability;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.abilities.TriggerShape;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.OwnedRpgObject;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgObject;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import java.awt.geom.Rectangle2D;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ 
/*     */ public abstract class Projectile extends OwnedRpgObject {
/*  21 */   private static final AtomicInteger nextProjectileId = new AtomicInteger();
/*     */   
/*     */   private final Integer id;
/*     */   
/*     */   protected final Item parent;
/*     */   
/*     */   protected final TriggerShape triggerShape;
/*     */   
/*     */   protected final ShapeDataEvaluator evaluator;
/*     */   
/*     */   private int aliveTimerMillis;
/*     */   
/*     */   private boolean triggered;
/*     */   
/*     */   protected String abilityId;
/*     */   protected List<Ability> abilities;
/*     */   protected int ownerLevel;
/*     */   private boolean started;
/*     */   
/*     */   public Projectile(Item parentItem, String abilityId, List<Ability> abilities, TriggerShape triggerShape, ShapeDataEvaluator evaluator, int ownerLevel) {
/*  41 */     this.parent = parentItem;
/*  42 */     this.abilityId = abilityId;
/*  43 */     this.triggerShape = triggerShape;
/*  44 */     this.evaluator = evaluator;
/*  45 */     this.abilities = abilities;
/*  46 */     this.ownerLevel = ownerLevel;
/*     */     
/*  48 */     this.id = Integer.valueOf(nextProjectileId.getAndIncrement());
/*     */     
/*  50 */     this.aliveTimerMillis = triggerShape.getMovementTimeMillis();
/*     */   }
/*     */   
/*     */   public Vector2d[] getLocalCorners(long updateMillis) {
/*  54 */     float endMillis = getShapeAtMillis();
/*  55 */     float startMillis = endMillis - (float)updateMillis;
/*     */     
/*  57 */     return this.triggerShape.getPositionedCorners(getProjectileAngle(), startMillis, endMillis);
/*     */   }
/*     */   
/*     */   public void update(long updateMillis) {
/*  61 */     if (getOwner() == null) {
/*  62 */       throw new RuntimeException("cannot update projectile without an owner");
/*     */     }
/*  64 */     if (!this.started) {
/*  65 */       throw new IllegalStateException("projectile is not yet started");
/*     */     }
/*     */     
/*  68 */     super.update(updateMillis);
/*     */ 
/*     */     
/*  71 */     this.aliveTimerMillis = (int)(this.aliveTimerMillis - updateMillis);
/*  72 */     if (this.aliveTimerMillis <= 0) {
/*  73 */       this.aliveTimerMillis = 0;
/*     */     }
/*     */     
/*  76 */     float endMillis = getShapeAtMillis();
/*  77 */     float startMillis = endMillis - (float)updateMillis;
/*     */ 
/*     */     
/*  80 */     List<? extends TargetProvider> potentialTriggerTargets = findTargetsInBoundingRect(updateMillis);
/*  81 */     keepTriggeredTargets(startMillis, endMillis, potentialTriggerTargets);
/*     */     
/*  83 */     boolean hasCollided = hasCollided(startMillis, endMillis);
/*  84 */     boolean triggered = (!potentialTriggerTargets.isEmpty() || hasCollided);
/*  85 */     if (triggered) {
/*  86 */       WorldCoordinate closestTriggerTargetPos = getClosestPos(potentialTriggerTargets);
/*  87 */       onTriggered(hasCollided, closestTriggerTargetPos);
/*     */     } 
/*     */   }
/*     */   
/*     */   private WorldCoordinate getClosestPos(List<? extends TargetProvider> triggerTargets) {
/*  92 */     if (!triggerTargets.isEmpty()) {
/*  93 */       double closestDist = Double.POSITIVE_INFINITY;
/*  94 */       WorldCoordinate closestPos = null;
/*  95 */       for (TargetProvider triggerTarget : triggerTargets) {
/*  96 */         double dist = this.evaluator.evalDistance((RpgEntity)this, triggerTarget.getTargetObject(), 0.0D, 0.0D, UsageParams.EMPTY_PARAMS);
/*  97 */         if (dist < closestDist) {
/*  98 */           closestPos = this.evaluator.getPos(triggerTarget.getTargetObject());
/*  99 */           closestDist = dist;
/*     */         } 
/*     */       } 
/* 102 */       return closestPos;
/*     */     } 
/* 104 */     return null;
/*     */   }
/*     */   
/*     */   private void onTriggered(boolean hasCollided, WorldCoordinate closestTriggerTargetPos) {
/* 108 */     List<? extends TargetProvider> abilityTargets = getAbilityTargets();
/* 109 */     UsageParams usageParams = UsageParams.EMPTY_PARAMS;
/*     */     
/* 111 */     if (closestTriggerTargetPos != null) {
/* 112 */       usageParams = new UsageParams(0.0D, closestTriggerTargetPos, UsageParams.AngleType.DEFINED, getProjectileAngle());
/*     */     }
/*     */     
/* 115 */     List<Ability> abilities = getAbilities();
/* 116 */     int abilityCount = abilities.size();
/* 117 */     for (int abilityIdx = 0; abilityIdx < abilityCount; abilityIdx++) {
/* 118 */       Ability ability = abilities.get(abilityIdx);
/*     */       
/* 120 */       if (this.ownerLevel >= ability.getLevelFrom() && this.ownerLevel <= ability.getLevelTo() && ability instanceof ActiveAbility)
/*     */       {
/*     */         
/* 123 */         ((ActiveAbility)ability).useIfHit(this.parent, this.evaluator, (SourceProvider)this, abilityTargets, usageParams);
/*     */       }
/*     */     } 
/*     */     
/* 127 */     if (!isTunneling() || hasCollided) {
/* 128 */       stop(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private List<? extends TargetProvider> findTargetsInBoundingRect(long updateMillis) {
/* 134 */     Rectangle2D triggerBounds = this.triggerShape.getBoundingRect(getProjectileAngle(), (float)updateMillis);
/* 135 */     triggerBounds.add(triggerBounds.getMinX() - 4.0D, triggerBounds.getMinY() - 4.0D);
/*     */     
/* 137 */     triggerBounds.add(triggerBounds.getMaxX() + 4.0D, triggerBounds.getMaxY() + 4.0D);
/*     */     
/* 139 */     List<? extends TargetProvider> potentialTargets = this.evaluator.findTargets((RpgObject)this, triggerBounds);
/*     */     
/* 141 */     return potentialTargets;
/*     */   }
/*     */   
/*     */   private void keepTriggeredTargets(float startMillis, float endMillis, List<? extends TargetProvider> potentialTargets) {
/* 145 */     if (!potentialTargets.isEmpty()) {
/* 146 */       int size = potentialTargets.size();
/* 147 */       WorldCoordinate startPos = getStartPosition();
/* 148 */       for (int i = size - 1; i >= 0; i--) {
/* 149 */         boolean removeTarget; TargetProvider targetProvider = potentialTargets.get(i);
/*     */ 
/*     */         
/* 152 */         if (!canTriggerProjectile(targetProvider)) {
/* 153 */           removeTarget = true;
/* 154 */         } else if (isTunneling() && !isMultipleHitsAllowed() && checkIfAlreadyHit(targetProvider)) {
/* 155 */           removeTarget = true;
/*     */         } else {
/* 157 */           WorldCoordinate targetPos = this.evaluator.getPos(targetProvider.getTargetObject());
/* 158 */           float radius = this.evaluator.getRadius(targetProvider.getTargetObject());
/* 159 */           WorldCoordinate localPos = new WorldCoordinate();
/* 160 */           localPos.set(targetPos).subtract(startPos);
/* 161 */           removeTarget = !this.triggerShape.contains(localPos.getX().floatValue(), localPos.getY().floatValue(), radius, getProjectileAngle(), startMillis, endMillis);
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 166 */         if (removeTarget) {
/* 167 */           potentialTargets.remove(i);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean isTriggered() {
/* 174 */     return this.triggered;
/*     */   }
/*     */ 
/*     */   
/*     */   protected abstract boolean hasCollided(float paramFloat1, float paramFloat2);
/*     */   
/*     */   protected abstract List<? extends TargetProvider> getAbilityTargets();
/*     */   
/*     */   protected abstract WorldCoordinate getStartPosition();
/*     */   
/*     */   protected abstract void setStartPosition(WorldCoordinate paramWorldCoordinate);
/*     */   
/*     */   protected abstract float getProjectileAngle();
/*     */   
/*     */   protected abstract void setProjectileAngle(float paramFloat);
/*     */   
/*     */   protected abstract boolean isTunneling();
/*     */   
/*     */   protected abstract boolean checkIfAlreadyHit(TargetProvider paramTargetProvider);
/*     */   
/*     */   protected abstract boolean isMultipleHitsAllowed();
/*     */   
/*     */   public abstract WorldCoordinate getLockedTargetPos();
/*     */   
/*     */   public abstract boolean hasBeenUpdated();
/*     */   
/*     */   public abstract void setHasBeenUpdated(boolean paramBoolean);
/*     */   
/*     */   public abstract boolean isReflective();
/*     */   
/*     */   public abstract Projectile copy();
/*     */   
/*     */   public abstract void initRedirect(float paramFloat);
/*     */   
/*     */   public abstract boolean hasTargetBeenHit();
/*     */   
/*     */   public RpgEntity getTargetHandler() {
/* 211 */     return getOwner();
/*     */   }
/*     */   
/*     */   private int getShapeAtMillis() {
/* 215 */     return this.triggerShape.getMovementTimeMillis() - this.aliveTimerMillis;
/*     */   }
/*     */   
/*     */   protected boolean canTriggerProjectile(TargetProvider targetProvider) {
/* 219 */     return (targetProvider.getTargetObject() != getOwner());
/*     */   }
/*     */   
/*     */   public boolean isDestroyed() {
/* 223 */     return (this.triggered || this.aliveTimerMillis <= 0);
/*     */   }
/*     */   
/*     */   public int getAliveTimerMillis() {
/* 227 */     return this.aliveTimerMillis;
/*     */   }
/*     */   
/*     */   public Integer getId() {
/* 231 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void start(WorldCoordinate startPosition, float startAngle) {
/* 237 */     setStartPosition(new WorldCoordinate(startPosition));
/* 238 */     setProjectileAngle(startAngle);
/*     */     
/* 240 */     this.started = true;
/*     */   }
/*     */   
/*     */   public void stop(boolean triggered) {
/* 244 */     this.triggered = triggered;
/*     */   }
/*     */ 
/*     */   
/*     */   public WorldCoordinate getPos() {
/* 249 */     if (getOwner() == null) {
/* 250 */       throw new IllegalStateException("no position due to no owner for projectile");
/*     */     }
/*     */     
/* 253 */     WorldCoordinate ret = getStartPosition().clone();
/*     */     
/* 255 */     Vector2d currentPos = this.triggerShape.getPos(getProjectileAngle(), getShapeAtMillis());
/* 256 */     ret.addOffset(currentPos.getX(), currentPos.getY());
/*     */     
/* 258 */     return ret;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Vector2d getVelocity() {
/* 268 */     int endMillis = this.triggerShape.getMovementTimeMillis();
/*     */     
/* 270 */     Vector2d startPos = this.triggerShape.getPos(getProjectileAngle(), 0.0F);
/* 271 */     Vector2d endPos = this.triggerShape.getPos(getProjectileAngle(), endMillis);
/*     */     
/* 273 */     Vector2d ret = new Vector2d(endPos.getX() - startPos.getX(), endPos.getY() - startPos.getY());
/*     */ 
/*     */     
/* 276 */     if (endMillis != 0) {
/* 277 */       ret.mult(1000.0D / endMillis);
/*     */     }
/*     */     
/* 280 */     return ret;
/*     */   }
/*     */   
/*     */   public String getAbilityId() {
/* 284 */     return this.abilityId;
/*     */   }
/*     */   
/*     */   public List<Ability> getAbilities() {
/* 288 */     return this.abilities;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\projectile\Projectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */