/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.StatEffect;
/*    */ import com.funcom.rpgengine2.abilities.EffectFilter;
/*    */ import java.util.Comparator;
/*    */ import java.util.PriorityQueue;
/*    */ import java.util.concurrent.PriorityBlockingQueue;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StatEffectSupport
/*    */   implements RpgQueryableSupport, RpgUpdateable, StatEffectQueue, SupportEventListener
/*    */ {
/* 14 */   private static final int[] UPDATE_PRIORITY = new int[] { 500000 };
/* 15 */   private static final StatEffectComparator STAT_EFFECT_COMPARATOR = new StatEffectComparator();
/*    */   
/*    */   protected final StatSupport statSupport;
/*    */   
/*    */   protected final PriorityBlockingQueue<StatEffect> pendingStatEffects;
/*    */   protected final PriorityQueue<EffectFilter> targetEffectFilters;
/*    */   
/*    */   public StatEffectSupport(StatSupport statSupport) {
/* 23 */     this.statSupport = statSupport;
/*    */     
/* 25 */     this.pendingStatEffects = new PriorityBlockingQueue<StatEffect>(11, STAT_EFFECT_COMPARATOR);
/* 26 */     this.targetEffectFilters = new PriorityQueue<EffectFilter>();
/*    */   }
/*    */   
/*    */   public void addUniqueTargetEffectFilters(EffectFilter filter) {
/* 30 */     if (!this.targetEffectFilters.contains(filter)) {
/* 31 */       this.targetEffectFilters.add(filter);
/*    */     }
/*    */   }
/*    */   
/*    */   public void removeUniqueTargetEffectFilters(EffectFilter filter) {
/* 36 */     this.targetEffectFilters.remove(filter);
/*    */   }
/*    */   
/*    */   public int[] getUpdatePriorities() {
/* 40 */     return UPDATE_PRIORITY;
/*    */   }
/*    */   
/*    */   public void update(int priority, long updateMillis) {
/* 44 */     if (!this.pendingStatEffects.isEmpty()) {
/*    */       StatEffect statEffect;
/* 46 */       while ((statEffect = this.pendingStatEffects.poll()) != null) {
/* 47 */         if (!this.targetEffectFilters.isEmpty()) {
/* 48 */           for (EffectFilter targetEffectFilter : this.targetEffectFilters) {
/* 49 */             targetEffectFilter.filter(statEffect);
/*    */           }
/*    */         }
/* 52 */         applyEffect(statEffect);
/*    */       } 
/* 54 */       sendDamageMessage();
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   protected void sendDamageMessage() {}
/*    */ 
/*    */   
/*    */   protected void applyEffect(StatEffect statEffect) {
/* 63 */     statEffect.process(this.statSupport.getStatCollection());
/*    */   }
/*    */   
/*    */   public void enqueueEffect(StatEffect modificationRequest) {
/* 67 */     modificationRequest.sourceFilter();
/* 68 */     this.pendingStatEffects.add(modificationRequest);
/*    */   }
/*    */   
/*    */   public void processEvent(SupportEvent event) {
/* 72 */     if (event.getType() == PassiveAbilityHandlingSupport.EventType.CLEAR_PASSIVE_ABILITIES)
/* 73 */       this.targetEffectFilters.clear(); 
/*    */   }
/*    */   
/*    */   private static class StatEffectComparator
/*    */     implements Comparator<StatEffect> {
/*    */     private StatEffectComparator() {}
/*    */     
/*    */     public int compare(StatEffect o1, StatEffect o2) {
/* 81 */       return o2.getSum() - o1.getSum();
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\StatEffectSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */