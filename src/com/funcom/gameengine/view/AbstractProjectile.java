/*    */ package com.funcom.gameengine.view;
/*    */ 
/*    */ import com.funcom.commons.dfx.DireEffect;
/*    */ import com.funcom.commons.dfx.DireEffectDescription;
/*    */ import com.funcom.commons.dfx.DireEffectDescriptionFactory;
/*    */ import com.funcom.gameengine.model.World;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.jme.animation.SpatialTransformer;
/*    */ import com.jme.math.Vector3f;
/*    */ import com.jme.scene.Controller;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractProjectile
/*    */   extends RepresentationalNode
/*    */ {
/*    */   private SpatialTransformer projectileController;
/*    */   protected float aliveTime;
/*    */   protected DireEffectDescription direEffect;
/*    */   
/*    */   public AbstractProjectile(String name, DireEffectDescription direEffect, World world, DireEffectDescriptionFactory effectDescriptionFactory) {
/* 22 */     super(name, effectDescriptionFactory, 17);
/* 23 */     this.direEffect = direEffect;
/*    */     
/* 25 */     addController((Controller)initProjectileController(world));
/* 26 */     world.addProjectile(this);
/* 27 */     initializeEffects(world.getParticleSurface());
/*    */   }
/*    */   
/*    */   protected SpatialTransformer initProjectileController(final World world) {
/* 31 */     this.projectileController = new SpatialTransformer(1) {
/*    */         public void update(float v) {
/* 33 */           super.update(v);
/* 34 */           if (!AbstractProjectile.this.hasDfxRunning())
/*    */           {
/* 36 */             AbstractProjectile.this.addDfx(AbstractProjectile.this.createDireEffectInstance());
/*    */           }
/* 38 */           if (getCurTime() >= AbstractProjectile.this.aliveTime) {
/* 39 */             AbstractProjectile.this.clearFromWorld(world);
/*    */           }
/*    */         }
/*    */       };
/* 43 */     return this.projectileController;
/*    */   }
/*    */   
/*    */   private DireEffect createDireEffectInstance() {
/* 47 */     return this.direEffect.createInstance(this, UsageParams.EMPTY_PARAMS);
/*    */   }
/*    */   
/*    */   protected void clearFromWorld(World world) {
/* 51 */     cleanup();
/* 52 */     world.removeProjectile(this);
/* 53 */     detachAllChildren();
/*    */   }
/*    */   
/*    */   public SpatialTransformer getProjectileController() {
/* 57 */     return this.projectileController;
/*    */   }
/*    */   
/*    */   protected abstract void setAliveTime(float paramFloat);
/*    */   
/*    */   protected abstract void setProjectileControllerProperties(Vector3f paramVector3f1, Vector3f paramVector3f2, float paramFloat);
/*    */   
/*    */   protected abstract void cleanup();
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\AbstractProjectile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */