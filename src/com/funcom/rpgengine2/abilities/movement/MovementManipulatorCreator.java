/*     */ package com.funcom.rpgengine2.abilities.movement;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*     */ import com.funcom.rpgengine2.abilities.AbstractAbility;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.combat.RpgStatus;
/*     */ import com.funcom.rpgengine2.combat.Shape;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.ShapeListener;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.MovementManipulationSupport;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.StatusSupport;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class MovementManipulatorCreator extends AbstractAbility implements ActiveAbility, ShapeListener {
/*     */   private MovementManipulatorFactory movementManipulatorFactory;
/*     */   
/*     */   public MovementManipulatorCreator(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader) {
/*  28 */     this.movementManipulatorFactory = loader.getMovementManipulatorFactory();
/*     */ 
/*     */     
/*  31 */     while (params.next()) {
/*  32 */       init(params);
/*  33 */       this.movementTimeMillis = (int)(params.getFloat(AbilityParams.ParamName.MOVEMENT_TIME) * 1000.0F);
/*  34 */       this.minMovementDistance = params.getFloat(AbilityParams.ParamName.MIN_DISTANCE);
/*  35 */       this.maxMovementDistance = params.getFloat(AbilityParams.ParamName.MAX_DISTANCE);
/*  36 */       this.movementAngle = (float)(params.getFloat(AbilityParams.ParamName.MOVEMENT_ANGLE_DEGREE) * Math.PI / 180.0D);
/*  37 */       String _shapeRef = params.getStr(AbilityParams.ParamName.SHAPE_REF);
/*  38 */       this.shape = loader.getShapeManager().getShape(_shapeRef);
/*     */       
/*  40 */       String tmpCureStatus = params.getStrOptional(AbilityParams.ParamName.STATUS, null);
/*     */       
/*  42 */       if (tmpCureStatus != null) {
/*  43 */         tmpCureStatus = tmpCureStatus.toLowerCase();
/*     */       }
/*  45 */       this.cureStatuses = addCureData(tmpCureStatus, this.cureStatuses);
/*     */     } 
/*     */   }
/*     */   private int movementTimeMillis; private float movementAngle; private float minMovementDistance; private float maxMovementDistance;
/*     */   private Shape shape;
/*     */   
/*     */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/*  52 */     this.shape.process(parent, this, evaluator, sourceProvider, targetProviders, usageParams);
/*     */   }
/*     */   
/*     */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/*     */     float angle;
/*  57 */     RpgEntity thingThatMoves = targetProvider.getTargetObject();
/*     */     
/*  59 */     float movementDistance = 0.0F;
/*     */     
/*  61 */     StatusSupport statusSupport = (StatusSupport)thingThatMoves.getSupport(StatusSupport.class);
/*  62 */     if (statusSupport != null && statusSupport.getRpgStatuses().contains(RpgStatus.ROOTED)) {
/*     */       return;
/*     */     }
/*     */     
/*  66 */     double distance = usageParams.getDistance();
/*  67 */     if (this.maxMovementDistance > 0.0F) {
/*  68 */       movementDistance = (float)Math.min(this.maxMovementDistance, distance);
/*  69 */       movementDistance = Math.max(this.minMovementDistance, movementDistance);
/*     */     }
/*  71 */     else if (this.maxMovementDistance < 0.0F) {
/*  72 */       distance = -distance;
/*  73 */       movementDistance = (float)Math.max(this.maxMovementDistance, distance);
/*  74 */       movementDistance = Math.min(this.minMovementDistance, movementDistance);
/*     */     } 
/*     */     
/*  77 */     if (Math.abs(movementDistance) < 0.1D) {
/*     */       return;
/*     */     }
/*     */     
/*  81 */     WorldCoordinate sourcePos = evaluator.getPos(sourceProvider.getSourceObject());
/*  82 */     WorldCoordinate targetPos = evaluator.getPos(targetProvider.getTargetObject());
/*     */ 
/*     */     
/*  85 */     if (sourceProvider.equals(targetProvider)) {
/*  86 */       angle = evaluator.getAngle(sourceProvider.getSourceObject()) + this.movementAngle;
/*     */     } else {
/*  88 */       angle = (float)sourcePos.angleTo(targetPos) + this.movementAngle;
/*     */     } 
/*     */ 
/*     */     
/*  92 */     MovementManipulator movementManipulator = this.movementManipulatorFactory.createMovementManipulator(this, sourceProvider, evaluator, thingThatMoves, movementDistance, angle, this.movementTimeMillis);
/*     */ 
/*     */ 
/*     */     
/*  96 */     ((MovementManipulationSupport)thingThatMoves.getSupport(MovementManipulationSupport.class)).enqueueMovementManipulator(movementManipulator);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHit(Item parentItem, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 101 */     return (this.shape.isValidTarget(source, target) && this.shape.canHit(parentItem, evaluator, source, target, usageParams));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void notifyHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 107 */     RpgEntity sourceObject = sourceProvider.getSourceObject();
/* 108 */     RpgEntity targetObject = targetProvider.getTargetObject();
/*     */     
/* 110 */     if (!evaluator.getId(sourceObject).equals(evaluator.getId(targetObject))) {
/*     */       
/* 112 */       double distance = evaluator.getPos(sourceObject).distanceTo(evaluator.getPos(targetObject));
/* 113 */       distance -= evaluator.getRadius(sourceObject);
/* 114 */       distance -= evaluator.getRadius(targetObject);
/* 115 */       usageParams = new UsageParams(distance, null, UsageParams.AngleType.SOURCE, 0.0F);
/*     */     } 
/*     */     
/* 118 */     useAlways(parent, evaluator, sourceProvider, targetProvider, usageParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 123 */     return 0;
/*     */   }
/*     */   
/*     */   public Set<String> getCureStatuses() {
/* 127 */     return this.cureStatuses;
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\movement\MovementManipulatorCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */