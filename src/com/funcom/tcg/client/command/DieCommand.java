/*     */ package com.funcom.tcg.client.command;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.commons.dfx.EffectDescription;
/*     */ import com.funcom.gameengine.model.props.Creature;
/*     */ import com.funcom.gameengine.view.PropNode;
/*     */ import com.funcom.tcg.client.TcgGame;
/*     */ import com.funcom.tcg.client.model.PropNodeRegister;
/*     */ import java.util.List;
/*     */ import org.apache.log4j.Logger;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DieCommand
/*     */   extends ExecuteDFXCommand
/*     */ {
/*     */   private static final double WARNING_DURATION_SIZE = 10.0D;
/*  18 */   private static final Logger LOGGER = Logger.getLogger(DieCommand.class);
/*     */   
/*     */   private double animationTime;
/*     */   private DireEffect elementalDeathEffect;
/*     */   private DireEffect impactEffect;
/*     */   private boolean addedElementalEffect = false;
/*     */   private boolean addedImpactEffect = false;
/*     */   private final PropNodeRegister propNodeRegister;
/*     */   
/*     */   public DieCommand(PropNode propNode, DireEffect deathEffect, DireEffect elementalDeathEffect, DireEffect impactEffect, float serverTime) {
/*  28 */     super(propNode, deathEffect, serverTime);
/*  29 */     this.elementalDeathEffect = elementalDeathEffect;
/*  30 */     this.impactEffect = impactEffect;
/*  31 */     this.animationTime = this.duration;
/*  32 */     this.duration = calculateDuration(deathEffect, elementalDeathEffect, impactEffect);
/*  33 */     this.propNodeRegister = TcgGame.getPropNodeRegister();
/*     */   }
/*     */   
/*     */   private double calculateDuration(DireEffect deathEffect, DireEffect elementalEffect, DireEffect impactEffect) {
/*  37 */     double duration = 0.0D;
/*  38 */     if (deathEffect != null) {
/*  39 */       List<EffectDescription> effectDescriptions = deathEffect.getDescription().getEffectDescriptions();
/*  40 */       for (EffectDescription description : effectDescriptions) {
/*  41 */         duration = getEffectDuration(duration, description);
/*     */       }
/*     */     } 
/*     */     
/*  45 */     if (elementalEffect != null) {
/*  46 */       List<EffectDescription> effectDescriptions = elementalEffect.getDescription().getEffectDescriptions();
/*  47 */       for (EffectDescription description : effectDescriptions) {
/*  48 */         duration = getEffectDuration(duration, description);
/*     */       }
/*     */     } 
/*     */     
/*  52 */     if (impactEffect != null) {
/*  53 */       List<EffectDescription> effectDescriptions = impactEffect.getDescription().getEffectDescriptions();
/*  54 */       for (EffectDescription description : effectDescriptions) {
/*  55 */         duration = getEffectDuration(duration, description);
/*     */       }
/*     */     } 
/*     */     
/*  59 */     return duration;
/*     */   }
/*     */ 
/*     */   
/*     */   private double getEffectDuration(double duration, EffectDescription description) {
/*  64 */     duration = Math.max(duration, description.getEndTime());
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
/*  75 */     return duration;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(float time) {
/*  81 */     if (!this.addedElementalEffect) {
/*  82 */       addDfx(this.elementalDeathEffect);
/*  83 */       this.addedElementalEffect = true;
/*     */     } 
/*     */     
/*  86 */     if (!this.addedImpactEffect) {
/*  87 */       addDfx(this.impactEffect);
/*  88 */       this.addedImpactEffect = true;
/*     */     } 
/*     */     
/*  91 */     super.update(time);
/*     */     
/*  93 */     if (this.timePassed >= this.animationTime && this.propNode.getRepresentation() != null) {
/*     */       
/*  95 */       this.propNode.getRepresentation().removeFromParent();
/*     */ 
/*     */       
/*  98 */       String mappedDfxId = this.propNode.getProp().getMappedDfx("idle");
/*  99 */       this.propNode.killDfx(mappedDfxId);
/*     */     } 
/*     */     
/* 102 */     if (isFinished() || ((this.effect == null || this.effect.isFinished()) && (this.impactEffect == null || this.impactEffect.isFinished()) && (this.elementalDeathEffect == null || this.elementalDeathEffect.isFinished()))) {
/*     */ 
/*     */ 
/*     */       
/* 106 */       this.propNode.removeFromParent();
/* 107 */       this.propNode.killDfxAll();
/* 108 */       this.propNode.getEffects().removeAllParticles();
/* 109 */       if (this.propNodeRegister.getPropNode(Integer.valueOf(((Creature)this.propNode.getProp()).getId())) == this.propNode)
/* 110 */         this.propNodeRegister.removePropNode(this.propNode); 
/* 111 */       this.forceFinish = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void addDfx(DireEffect effect) {
/* 118 */     if (effect != null) {
/* 119 */       effect.update(this.timePassed);
/* 120 */       this.propNode.addDfx(effect);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void forceFinish() {
/* 126 */     super.forceFinish();
/* 127 */     if (this.elementalDeathEffect != null)
/* 128 */       this.elementalDeathEffect.forceFinish(); 
/* 129 */     if (this.impactEffect != null)
/* 130 */       this.impactEffect.forceFinish(); 
/* 131 */     this.propNode.removeFromParent();
/* 132 */     this.propNode.getEffects().removeAllParticles();
/* 133 */     if (this.propNodeRegister.getPropNode(Integer.valueOf(((Creature)this.propNode.getProp()).getId())) == this.propNode) {
/* 134 */       this.propNodeRegister.removePropNode(this.propNode);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isFinished() {
/* 139 */     return (this.timePassed >= this.duration || this.forceFinish);
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\command\DieCommand.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */