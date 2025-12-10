/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class DireEffect
/*    */ {
/*    */   private final DireEffectDescription description;
/*    */   private List<Effect> effects;
/*    */   private double localTime;
/*    */   private boolean forceFinish = false;
/*    */   
/*    */   public DireEffect(DireEffectDescription description, List<Effect> effects) {
/* 17 */     this.description = description;
/* 18 */     this.effects = effects;
/* 19 */     this.localTime = 0.0D;
/*    */   }
/*    */   
/*    */   public void addEffect(Effect effect) {
/* 23 */     if (this.localTime < 0.0D)
/* 24 */       throw new IllegalStateException("No more effects should be added after the DFX is started."); 
/* 25 */     this.effects.add(effect);
/*    */   }
/*    */   
/*    */   public boolean hasEffect(Effect effect) {
/* 29 */     return this.effects.contains(effect);
/*    */   }
/*    */   
/*    */   public List<String> getAnimationEffects() {
/* 33 */     List<String> animationList = new LinkedList<String>();
/* 34 */     for (Effect effect : this.effects) {
/* 35 */       if (effect.getDescription() instanceof AnimationEffectDescription && effect.isAlive() && effect.getDescription().getEndTime() - this.localTime > 0.08D) {
/* 36 */         animationList.add(effect.getDescription().getResource());
/*    */       }
/*    */     } 
/* 39 */     return animationList;
/*    */   }
/*    */   
/*    */   public boolean update(double dT) {
/* 43 */     this.localTime += dT;
/* 44 */     int size = this.effects.size();
/*    */     int i;
/* 46 */     for (i = 0; i < size; i++) {
/* 47 */       Effect effect = this.effects.get(i);
/* 48 */       effect.update(this.localTime);
/*    */     } 
/*    */     
/* 51 */     for (i = size - 1; i >= 0; i--) {
/* 52 */       Effect effect = this.effects.get(i);
/* 53 */       if (!effect.isAlive()) {
/* 54 */         effect.kill();
/* 55 */         this.effects.remove(i);
/*    */       } 
/*    */     } 
/* 58 */     return !isFinished();
/*    */   }
/*    */   
/*    */   public boolean isFinished() {
/* 62 */     return (this.effects.isEmpty() || this.forceFinish);
/*    */   }
/*    */   
/*    */   public double getLocalTime() {
/* 66 */     return this.localTime;
/*    */   }
/*    */   
/*    */   public void forceFinish() {
/* 70 */     this.forceFinish = true;
/*    */     
/* 72 */     int size = this.effects.size();
/* 73 */     for (int i = size - 1; i >= 0; i--) {
/* 74 */       Effect effect = this.effects.get(i);
/* 75 */       if (effect.isAlive()) {
/* 76 */         effect.kill();
/* 77 */         this.effects.remove(i);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean isForceFinish() {
/* 83 */     return this.forceFinish;
/*    */   }
/*    */   
/*    */   public DireEffectDescription getDescription() {
/* 87 */     return this.description;
/*    */   }
/*    */   
/*    */   public double getDuration() {
/* 91 */     return getDescription().getDuration();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\DireEffect.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */