/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.StatEffect;
/*    */ import com.funcom.rpgengine2.abilities.EffectFilter;
/*    */ import java.util.Comparator;
/*    */ import java.util.PriorityQueue;
/*    */ 
/*    */ public class SourceEffectFilterSupport
/*    */   implements RpgQueryableSupport, SupportEventListener
/*    */ {
/* 11 */   protected static final Comparator<EffectFilter> FILTER_COMP = new Comparator<EffectFilter>() {
/*    */       public int compare(EffectFilter f1, EffectFilter f2) {
/* 13 */         return f1.getPriority() - f2.getPriority();
/*    */       }
/*    */     };
/*    */   
/* 17 */   private PriorityQueue<EffectFilter> sourceEffectFilters = new PriorityQueue<EffectFilter>(3, FILTER_COMP);
/*    */   
/*    */   public void filterSourceEffect(StatEffect statEffect) {
/* 20 */     for (EffectFilter sourceEffectFilter : this.sourceEffectFilters) {
/* 21 */       sourceEffectFilter.filter(statEffect);
/*    */     }
/*    */   }
/*    */   
/*    */   public void addUniqueSourceEffectFilters(EffectFilter filter) {
/* 26 */     if (!this.sourceEffectFilters.contains(filter)) {
/* 27 */       this.sourceEffectFilters.add(filter);
/*    */     }
/*    */   }
/*    */   
/*    */   public void removeUniqueSourceEffectFilters(EffectFilter filter) {
/* 32 */     this.sourceEffectFilters.remove(filter);
/*    */   }
/*    */   
/*    */   public PriorityQueue<EffectFilter> getSourceEffectFilters() {
/* 36 */     return this.sourceEffectFilters;
/*    */   }
/*    */   
/*    */   public void processEvent(SupportEvent event) {
/* 40 */     if (event.getType() == PassiveAbilityHandlingSupport.EventType.CLEAR_PASSIVE_ABILITIES)
/* 41 */       this.sourceEffectFilters.clear(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\SourceEffectFilterSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */