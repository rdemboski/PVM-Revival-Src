/*     */ package com.funcom.tcg.rpg.items;
/*     */ 
/*     */ import com.funcom.gameengine.WorldCoordinate;
/*     */ import com.funcom.rpgengine2.Stat;
/*     */ import com.funcom.rpgengine2.StatEffect;
/*     */ import com.funcom.rpgengine2.abilities.BuffType;
/*     */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*     */ import com.funcom.rpgengine2.abilities.SourceProviderImpl;
/*     */ import com.funcom.rpgengine2.abilities.TargetProviderImpl;
/*     */ import com.funcom.rpgengine2.abilities.projectile.Projectile;
/*     */ import com.funcom.rpgengine2.abilities.projectile.ProjectileQueue;
/*     */ import com.funcom.rpgengine2.buffs.Buff;
/*     */ import com.funcom.rpgengine2.combat.Element;
/*     */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*     */ import com.funcom.rpgengine2.combat.UsageParams;
/*     */ import com.funcom.rpgengine2.creatures.BuffQueue;
/*     */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*     */ import com.funcom.rpgengine2.creatures.RpgObject;
/*     */ import com.funcom.rpgengine2.creatures.StatEffectQueue;
/*     */ import com.funcom.rpgengine2.creatures.StatSupport;
/*     */ import com.funcom.rpgengine2.creatures.SupportEvent;
/*     */ import com.funcom.rpgengine2.items.Item;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class UsefulItemTester implements ItemTester {
/*     */   private static final int MAX_BUFF_CHECK_DURATION = 1500;
/*     */   private RpgObject owner;
/*     */   private ShapeDataEvaluator evaluator;
/*     */   
/*     */   public UsefulItemTester(ShapeDataEvaluator evaluator) {
/*  32 */     this.evaluator = evaluator;
/*     */   }
/*     */   
/*     */   public void setOwner(RpgObject owner) {
/*  36 */     this.owner = owner;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean testItem(Item item) {
/*  41 */     List<TargetProviderImpl> captureResultList = new ArrayList<TargetProviderImpl>(1);
/*  42 */     StatResultHandler recorder = new StatResultHandler((RpgEntity)this.owner);
/*  43 */     captureResultList.add(new TargetProviderImpl((RpgEntity)this.owner, recorder));
/*  44 */     SourceProviderImpl sourceProviderImpl = new SourceProviderImpl((RpgEntity)this.owner, recorder);
/*     */     
/*  46 */     if (item != null) {
/*  47 */       recorder.start();
/*  48 */       item.applyAbilities(this.evaluator, (SourceProvider)sourceProviderImpl, captureResultList, UsageParams.EMPTY_PARAMS);
/*  49 */       return recorder.stopAndEvaluate();
/*     */     } 
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   private class StatResultHandler implements RpgEntity {
/*     */     private boolean evaluateChangedWatchStat;
/*     */     
/*  57 */     private StatEffectQueue statEffectCostEval = new StatEffectQueue() {
/*     */         public void enqueueEffect(StatEffect statEffect) {
/*  59 */           int sum = statEffect.getSum();
/*     */           
/*  61 */           Stat stat = ((StatSupport)UsefulItemTester.StatResultHandler.this.owner.getSupport(StatSupport.class)).getStatById(statEffect.getStatId().shortValue());
/*  62 */           if (sum > 0 && (stat.getMax() == null || stat.getSum() < stat.getMax().getSum())) {
/*  63 */             UsefulItemTester.StatResultHandler.this.evaluateChangedWatchStat = true;
/*     */           }
/*     */         }
/*     */       };
/*     */     
/*  68 */     private BuffQueue buffCostEval = new BuffQueue() {
/*     */         public void enqueue(Buff buff) {
/*  70 */           int durationToUpdate = buff.getDuration();
/*  71 */           if (durationToUpdate > 1500) {
/*  72 */             durationToUpdate = 1500;
/*     */           }
/*  74 */           buff.update(durationToUpdate);
/*     */         }
/*     */ 
/*     */         
/*     */         public void enqueueFinish(BuffType type, Element cureElement, String cureStatus) {}
/*     */ 
/*     */         
/*     */         public void enqueueFinnishBuff(String targetId) {}
/*     */       };
/*     */ 
/*     */     
/*  85 */     private ProjectileQueue projectileEval = new ProjectileQueue()
/*     */       {
/*     */         public void enqueueProjectile(Projectile projectile, WorldCoordinate startPosition, float startAngle) {}
/*     */       };
/*     */     
/*     */     private RpgEntity owner;
/*     */     
/*     */     public StatResultHandler(RpgEntity owner) {
/*  93 */       this.owner = owner;
/*     */     }
/*     */     
/*     */     public <E extends com.funcom.rpgengine2.creatures.RpgQueryableSupport> E getSupport(Class<E> supportClass) {
/*  97 */       if (StatEffectQueue.class.isAssignableFrom(supportClass)) {
/*  98 */         return (E)this.statEffectCostEval;
/*     */       }
/* 100 */       if (BuffQueue.class.isAssignableFrom(supportClass)) {
/* 101 */         return (E)this.buffCostEval;
/*     */       }
/* 103 */       if (ProjectileQueue.class.isAssignableFrom(supportClass)) {
/* 104 */         return (E)this.projectileEval;
/*     */       }
/*     */       
/* 107 */       return (E)this.owner.getSupport(supportClass);
/*     */     }
/*     */ 
/*     */     
/*     */     public void fireEvent(SupportEvent event) {}
/*     */ 
/*     */     
/*     */     public int calcCritDamage(int value, RpgObject target) {
/* 115 */       return value;
/*     */     }
/*     */     
/*     */     public void start() {
/* 119 */       this.evaluateChangedWatchStat = false;
/*     */     }
/*     */     
/*     */     public boolean stopAndEvaluate() {
/* 123 */       return this.evaluateChangedWatchStat;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\items\UsefulItemTester.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */