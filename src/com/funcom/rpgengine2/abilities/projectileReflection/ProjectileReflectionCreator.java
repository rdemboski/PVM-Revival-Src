/*     */ package com.funcom.rpgengine2.abilities.projectileReflection;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.abilities.AbstractAbility;
/*     */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*     */ import com.funcom.rpgengine2.abilities.projectile.Projectile;
/*     */ import com.funcom.rpgengine2.combat.Shape;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.ShapeListener;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.MapSupport;
/*     */ import com.funcom.rpgengine2.creatures.ProjectileSupport;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import com.funcom.rpgengine2.items.ItemDescription;
/*     */ import com.funcom.rpgengine2.loader.AbilityParams;
/*     */ import com.funcom.rpgengine2.loader.RpgLoader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class ProjectileReflectionCreator
/*     */   extends AbstractAbility
/*     */   implements ActiveAbility, ShapeListener
/*     */ {
/*     */   private final RpgLoader loader;
/*     */   private final Shape shape;
/*     */   private final float reflectionValue;
/*     */   private final String dfxScript;
/*     */   
/*     */   public ProjectileReflectionCreator(AbilityParams params, RpgLoader loader) {
/*  33 */     this.loader = loader;
/*  34 */     if (params.next()) {
/*  35 */       init(params);
/*  36 */       this.reflectionValue = params.getFloat(AbilityParams.ParamName.REFLECTION_VALUE);
/*  37 */       String shapeRef = params.getStr(AbilityParams.ParamName.SHAPE_REF);
/*  38 */       this.dfxScript = params.getStr(AbilityParams.ParamName.DFX_REFERENCE);
/*  39 */       this.shape = loader.getShapeManager().getShape(shapeRef);
/*     */     } else {
/*  41 */       throw new RuntimeException("No params for " + getClass().getSimpleName());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/*  47 */     useAlways(parent, evaluator, sourceProvider, (TargetProvider)null, usageParams);
/*     */   }
/*     */ 
/*     */   
/*     */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/*  52 */     List<? extends TargetProvider> targetProviders = ((MapSupport)sourceProvider.getSourceObject().getSupport(MapSupport.class)).getAll();
/*     */     
/*  54 */     List<TargetProvider> projectiles = new ArrayList<TargetProvider>();
/*     */     
/*  56 */     for (TargetProvider provider : targetProviders) {
/*  57 */       if (!evaluator.getId(sourceProvider.getSourceObject()).equals(evaluator.getId(provider.getTargetObject()))) {
/*  58 */         List<Projectile> targetProjectiles = ((ProjectileSupport)provider.getTargetObject().getSupport(ProjectileSupport.class)).getProjectiles();
/*  59 */         filterReflective(projectiles, targetProjectiles);
/*     */       } 
/*     */     } 
/*     */     
/*  63 */     if (!projectiles.isEmpty()) {
/*  64 */       SourceProvider projectileReflector = makeNewProjectileReflector(sourceProvider, evaluator);
/*  65 */       this.shape.process(parent, this, evaluator, projectileReflector, projectiles, usageParams);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void filterReflective(List<TargetProvider> projectiles, List<Projectile> targetProjectiles) {
/*  70 */     for (Projectile targetProjectile : targetProjectiles) {
/*  71 */       if (targetProjectile.isReflective()) {
/*  72 */         projectiles.add(targetProjectile);
/*     */       }
/*     */     } 
/*     */   }
/*     */   
/*     */   private SourceProvider makeNewProjectileReflector(SourceProvider parent, ShapeDataEvaluator evaluator) {
/*  78 */     return (SourceProvider)this.loader.getProjectileReflectorFactory().newProjectileReflector(parent, evaluator, this.loader, this.dfxScript);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canHit(Item parentItem, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/*  83 */     return (this.shape.isValidTarget(source, target) && this.shape.canHit(parentItem, evaluator, source, target, usageParams));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getDamage(ItemDescription description, RpgEntity rpgEntity, short statToLookFor) {
/*  88 */     return 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public void notifyHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/*  93 */     RpgEntity targetObject = targetProvider.getTargetObject();
/*  94 */     if (targetObject instanceof Projectile && sourceProvider instanceof ProjectileReflector) {
/*  95 */       Projectile projectile = (Projectile)targetObject;
/*  96 */       ProjectileReflector projectileReflector = (ProjectileReflector)sourceProvider;
/*  97 */       projectileReflector.notifyImpact();
/*     */       
/*  99 */       SourceProvider reflectorOwner = projectileReflector.getReflectorSourceProvider();
/* 100 */       RpgEntity projectileOwner = projectile.getOwner();
/*     */       
/* 102 */       WorldCoordinate refOwnerStartPos = evaluator.getPos(reflectorOwner.getSourceObject());
/* 103 */       WorldCoordinate projOwnerPos = evaluator.getPos(projectileOwner);
/* 104 */       WorldCoordinate startPosition = evaluator.getPos((RpgEntity)projectile);
/*     */       
/* 106 */       float newAngle = (float)refOwnerStartPos.angleTo(projOwnerPos);
/*     */       
/* 108 */       ((ProjectileSupport)projectileOwner.getSupport(ProjectileSupport.class)).enqueueStopAndRedirectProjectile(projectile, reflectorOwner, newAngle, startPosition, this.reflectionValue);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\projectileReflection\ProjectileReflectionCreator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */