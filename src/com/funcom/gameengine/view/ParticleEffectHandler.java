/*     */ package com.funcom.gameengine.view;
/*     */ import com.funcom.commons.dfx.DireEffectResourceLoader;
/*     */ import com.funcom.commons.dfx.Effect;
/*     */ import com.funcom.commons.dfx.ParticleEffectDescription;
/*     */ import com.funcom.commons.dfx.PositionalEffectDescription;
/*     */ import com.jme.bounding.BoundingBox;
/*     */ import com.jme.bounding.BoundingVolume;
/*     */ import com.jme.math.Quaternion;
/*     */ import com.jme.math.Vector3f;
/*     */ import com.turborilla.jops.jme.JopsNode;
/*     */ import org.softmed.jops.ParticleSystem;
/*     */ 
/*     */ public class ParticleEffectHandler extends PositionalEffectHandler {
/*  14 */   private static final Quaternion rotOffset = (new Quaternion()).fromAngleAxis(3.1415927F, new Vector3f(0.0F, 0.0F, 1.0F));
/*     */   
/*     */   private JopsNode particleNode;
/*  17 */   private static boolean PARTICLES_ENABLED = !"false".equalsIgnoreCase(System.getProperty("Debug.PARTICLE_ENABLED"));
/*     */   
/*     */   public ParticleEffectHandler(RepresentationalNode target) {
/*  20 */     super(target);
/*     */   }
/*     */   
/*     */   public void startEffect(Effect sourceEffect) {
/*  24 */     if (PARTICLES_ENABLED) {
/*  25 */       ParticleEffectDescription particleEffectDescription = (ParticleEffectDescription)sourceEffect.getDescription();
/*  26 */       DireEffectResourceLoader effectResourceLoader = particleEffectDescription.getResourceFetcher();
/*  27 */       String resource = sourceEffect.getDescription().getResource();
/*  28 */       ParticleSystem particleSystem = (ParticleSystem)effectResourceLoader.getParticleSystem(resource, particleEffectDescription.isCache());
/*  29 */       particleSystem.setName(resource);
/*     */       
/*  31 */       ParticlesParams params = new ParticlesParams(particleEffectDescription, getTarget().getEffects());
/*     */ 
/*     */       
/*  34 */       if (particleEffectDescription.isDisconnectEmitter()) {
/*  35 */         addAsDisconnectedWorld(particleSystem, params);
/*  36 */       } else if (particleEffectDescription.isDisconnectParticles()) {
/*  37 */         addAsWorld(particleSystem, params);
/*  38 */       } else if (!particleEffectDescription.isDisconnectParticles()) {
/*  39 */         addAsLocal(particleSystem, params);
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public void endEffect(Effect sourceEffect) {
/*  45 */     getTarget().getEffects().removeParticle(this.particleNode);
/*     */   }
/*     */   
/*     */   private void addAsDisconnectedWorld(ParticleSystem particleSystem, ParticlesParams params) {
/*  49 */     if (params.hasBoneId()) {
/*  50 */       params.getOffsetAngleQuat().multLocal(rotOffset);
/*  51 */       this.particleNode = params.getTargetEffects().addDisconnectedBoneParticleWorld(particleSystem, params.getBoneId(), params.getOffset(), params.getScale(), params.getOffsetAngleQuat(), params.isKillInstantly());
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  58 */       this.particleNode = params.getTargetEffects().addDisconnectedGroundParticleWorld(particleSystem, params.getOffset(), params.getScale(), params.getOffsetAngleQuat(), params.isKillInstantly());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addAsWorld(ParticleSystem particleSystem, ParticlesParams params) {
/*  67 */     if (params.hasBoneId()) {
/*  68 */       params.getOffsetAngleQuat().multLocal(rotOffset);
/*  69 */       this.particleNode = params.getTargetEffects().addBoneParticleWorld(particleSystem, params.getBoneId(), params.getOffset(), params.getScale(), params.getOffsetAngleQuat(), params.isKillInstantly());
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  76 */       this.particleNode = params.getTargetEffects().addGroundParticleWorld(particleSystem, params.getOffset(), params.getScale(), params.getOffsetAngleQuat(), params.isKillInstantly());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void addAsLocal(ParticleSystem particleSystem, ParticlesParams params) {
/*  85 */     if (params.hasBoneId()) {
/*  86 */       params.getOffsetAngleQuat().multLocal(rotOffset);
/*  87 */       this.particleNode = params.getTargetEffects().addBoneParticleLocal(particleSystem, params.getBoneId(), params.getOffset(), params.getScale(), params.getOffsetAngleQuat(), params.isKillInstantly());
/*     */ 
/*     */     
/*     */     }
/*     */     else {
/*     */ 
/*     */       
/*  94 */       this.particleNode = params.getTargetEffects().addGroundParticleLocal(particleSystem, params.getOffset(), params.getScale(), params.getOffsetAngleQuat(), params.isKillInstantly());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private class ParticlesParams
/*     */   {
/*     */     private final ParticleEffectDescription particleEffectDescription;
/*     */     
/*     */     private final Effects targetEffects;
/*     */     
/*     */     private float scale;
/*     */     
/*     */     private Vector3f offset;
/*     */     private Quaternion offsetAngleQuat;
/*     */     
/*     */     public ParticlesParams(ParticleEffectDescription particleEffectDescription, Effects targetEffects) {
/* 111 */       this.particleEffectDescription = particleEffectDescription;
/* 112 */       this.targetEffects = targetEffects;
/*     */       
/* 114 */       init();
/*     */     }
/*     */     
/*     */     public float getScale() {
/* 118 */       return this.scale;
/*     */     }
/*     */     
/*     */     public Vector3f getOffset() {
/* 122 */       return this.offset;
/*     */     }
/*     */     
/*     */     public Quaternion getOffsetAngleQuat() {
/* 126 */       return this.offsetAngleQuat;
/*     */     }
/*     */     
/*     */     public Effects getTargetEffects() {
/* 130 */       return this.targetEffects;
/*     */     }
/*     */     
/*     */     private void init() {
/* 134 */       this.scale = (float)this.particleEffectDescription.getScale();
/* 135 */       this.offset = ParticleEffectHandler.this.getOffsetPos((PositionalEffectDescription)this.particleEffectDescription);
/* 136 */       if (ParticleEffectHandler.this.getTarget().getRepresentation() != null) {
/* 137 */         BoundingVolume volume = ParticleEffectHandler.this.getTarget().getRepresentation().getWorldBound();
/* 138 */         if (this.particleEffectDescription.getRelativeScale()) {
/* 139 */           double parentSize = Math.pow(volume.getVolume(), 0.3333333333333333D);
/* 140 */           this.scale = (float)(this.scale * parentSize);
/*     */         } 
/* 142 */         if (this.particleEffectDescription.isRelativeOffset()) {
/* 143 */           Vector3f extent = ((BoundingBox)volume).getExtent(new Vector3f());
/* 144 */           this.offset.multLocal(extent);
/*     */         } 
/*     */       } 
/* 147 */       float[] offsetAngle = this.particleEffectDescription.getOffsetAngle();
/* 148 */       this.offsetAngleQuat = ParticleEffectHandler.this.getOffsetAngle(offsetAngle);
/*     */     }
/*     */     
/*     */     public boolean hasBoneId() {
/* 152 */       String boneId = getBoneId();
/* 153 */       return !"None".equalsIgnoreCase(boneId);
/*     */     }
/*     */     
/*     */     public String getBoneId() {
/* 157 */       return this.particleEffectDescription.getBone();
/*     */     }
/*     */     
/*     */     public boolean isKillInstantly() {
/* 161 */       return this.particleEffectDescription.isKillInstantly();
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\view\ParticleEffectHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */