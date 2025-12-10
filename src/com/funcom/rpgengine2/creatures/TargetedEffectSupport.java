/*     */ package com.funcom.rpgengine2.creatures;
/*     */ 
/*     */ import com.funcom.commons.dfx.DireEffect;
/*     */ import com.funcom.rpgengine2.abilities.targetedeffect.TargetedEffect;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ 
/*     */ public class TargetedEffectSupport
/*     */   implements RpgQueryableSupport, RpgUpdateable {
/*  13 */   private static final int[] UPDATE_PRIORITY = new int[] { 500000 };
/*     */   
/*     */   private final List<TargetedEffect> pendingTargetedEffects;
/*     */   
/*     */   private final List<TargetedEffect> targetedEffects;
/*     */   
/*     */   private final List<PendingTargetedEffectDfx> pendingTargetedEffectDfxEffects;
/*     */   private int modCount;
/*     */   private RpgObject owner;
/*     */   
/*     */   public TargetedEffectSupport(RpgObject owner) {
/*  24 */     this.owner = owner;
/*  25 */     this.pendingTargetedEffects = Collections.synchronizedList(new ArrayList<TargetedEffect>());
/*  26 */     this.targetedEffects = new ArrayList<TargetedEffect>();
/*  27 */     this.pendingTargetedEffectDfxEffects = new CopyOnWriteArrayList<PendingTargetedEffectDfx>();
/*     */   }
/*     */   
/*     */   public void add(TargetedEffect targetedEffect) {
/*  31 */     this.pendingTargetedEffects.add(targetedEffect);
/*     */   }
/*     */   
/*     */   public int[] getUpdatePriorities() {
/*  35 */     return UPDATE_PRIORITY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(int priority, long updateMillis) {
/*  40 */     synchronized (this.pendingTargetedEffects) {
/*  41 */       if (!this.pendingTargetedEffects.isEmpty()) {
/*  42 */         int j = this.pendingTargetedEffects.size();
/*  43 */         for (int k = 0; k < j; k++) {
/*  44 */           this.targetedEffects.add(this.pendingTargetedEffects.get(k));
/*  45 */           this.modCount++;
/*     */         } 
/*  47 */         this.pendingTargetedEffects.clear();
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/*  52 */     int size = this.targetedEffects.size();
/*  53 */     for (int i = size - 1; i >= 0; i--) {
/*  54 */       TargetedEffect targetedEffect = this.targetedEffects.get(i);
/*  55 */       targetedEffect.update(updateMillis);
/*     */       
/*  57 */       if (targetedEffect.isDestroyed()) {
/*  58 */         this.targetedEffects.remove(i);
/*  59 */         this.modCount++;
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<TargetedEffect> getTargetedEffects() {
/*  65 */     return new ArrayList<TargetedEffect>(this.targetedEffects);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getModCount() {
/*  74 */     return this.modCount;
/*     */   }
/*     */   
/*     */   public void enqueueTargetedEffect(TargetedEffect targetedEffect) {
/*  78 */     add(targetedEffect);
/*  79 */     targetedEffect.setOwner(this.owner);
/*     */   }
/*     */   
/*     */   public void queuePendingDfx(TargetedEffect targetedEffect, DireEffect dfx) {
/*  83 */     this.pendingTargetedEffectDfxEffects.add(new PendingTargetedEffectDfx(targetedEffect, dfx));
/*     */   }
/*     */   
/*     */   public Iterator getPendingTargetedDfxEffects() {
/*  87 */     return this.pendingTargetedEffectDfxEffects.iterator();
/*     */   }
/*     */   
/*     */   public void removePendingDfx(TargetedEffect targetedEffect, DireEffect dfx) {
/*  91 */     synchronized (this.pendingTargetedEffectDfxEffects) {
/*  92 */       for (PendingTargetedEffectDfx pendingTargetedEffectDfxEffect : this.pendingTargetedEffectDfxEffects) {
/*  93 */         if (pendingTargetedEffectDfxEffect.getTargetedEffect().getId().equals(targetedEffect.getId()) && pendingTargetedEffectDfxEffect.getDfx().getDescription().getId().equalsIgnoreCase(dfx.getDescription().getId()))
/*     */         {
/*  95 */           this.pendingTargetedEffectDfxEffects.remove(pendingTargetedEffectDfxEffect);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */   
/*     */   public static class PendingTargetedEffectDfx
/*     */   {
/*     */     private TargetedEffect targetedEffect;
/*     */     private DireEffect dfx;
/*     */     private final List<Integer> triggeredList;
/*     */     
/*     */     public PendingTargetedEffectDfx(TargetedEffect targetedEffect, DireEffect dfx) {
/* 108 */       this.targetedEffect = targetedEffect;
/* 109 */       this.dfx = dfx;
/* 110 */       this.triggeredList = Collections.synchronizedList(new ArrayList<Integer>());
/*     */     }
/*     */     
/*     */     public TargetedEffect getTargetedEffect() {
/* 114 */       return this.targetedEffect;
/*     */     }
/*     */     
/*     */     public boolean isTriggered(Integer id) {
/* 118 */       synchronized (this.triggeredList) {
/* 119 */         if (!this.triggeredList.contains(id)) {
/* 120 */           this.triggeredList.add(id);
/* 121 */           return false;
/*     */         } 
/* 123 */         return true;
/*     */       } 
/*     */     }
/*     */     
/*     */     public DireEffect getDfx() {
/* 128 */       return this.dfx;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\TargetedEffectSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */