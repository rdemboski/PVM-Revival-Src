/*    */ package com.funcom.commons.dfx;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ 
/*    */ public class DFXCollection
/*    */ {
/* 10 */   protected final List<DireEffect> effects = new LinkedList<DireEffect>();
/*    */ 
/*    */   
/*    */   public boolean isEmpty() {
/* 14 */     return this.effects.isEmpty();
/*    */   }
/*    */   
/*    */   public void addDFX(DireEffect effect) {
/* 18 */     if (!effect.isFinished()) {
/* 19 */       this.effects.add(effect);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean hasNonStandardAnimationRunning() {
/* 24 */     for (DireEffect effect : this.effects) {
/* 25 */       List<String> animations = effect.getAnimationEffects();
/* 26 */       if (!animations.isEmpty()) {
/* 27 */         for (String animation : animations) {
/* 28 */           if (!animation.equals("idle") && !animation.equals("move")) {
/* 29 */             return true;
/*    */           }
/*    */         } 
/*    */       }
/*    */     } 
/* 34 */     return false;
/*    */   }
/*    */   
/*    */   public void killDfx(String id) {
/* 38 */     for (DireEffect effect : this.effects) {
/* 39 */       if (effect.getDescription().getId().equalsIgnoreCase(id)) {
/* 40 */         effect.forceFinish();
/*    */         break;
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean runningDfx(String id) {
/* 47 */     for (DireEffect effect : this.effects) {
/* 48 */       if (effect.getDescription().getId().equalsIgnoreCase(id)) {
/* 49 */         return true;
/*    */       }
/*    */     } 
/* 52 */     return false;
/*    */   }
/*    */   
/*    */   public void update(double dT) {
/* 56 */     if (this.effects == null || this.effects.isEmpty())
/* 57 */       return;  Set<DireEffect> finishedEffects = new HashSet<DireEffect>();
/* 58 */     for (DireEffect effect : this.effects) {
/* 59 */       boolean alive = effect.update(dT);
/* 60 */       if (!alive) {
/* 61 */         finishedEffects.add(effect);
/*    */       }
/*    */     } 
/* 64 */     this.effects.removeAll(finishedEffects);
/*    */   }
/*    */   
/*    */   public void killAll() {
/* 68 */     for (DireEffect effect : this.effects)
/* 69 */       effect.forceFinish(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-common.jar!\com\funcom\commons\dfx\DFXCollection.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */