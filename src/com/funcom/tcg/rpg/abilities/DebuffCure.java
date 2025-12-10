/*    */ package com.funcom.tcg.rpg.abilities;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.AbstractAbility;
/*    */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*    */ import com.funcom.rpgengine2.abilities.BuffType;
/*    */ import com.funcom.rpgengine2.abilities.ElementAbility;
/*    */ import com.funcom.rpgengine2.abilities.SourceProvider;
/*    */ import com.funcom.rpgengine2.abilities.TargetProvider;
/*    */ import com.funcom.rpgengine2.combat.Element;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import com.funcom.rpgengine2.combat.UsageParams;
/*    */ import com.funcom.rpgengine2.creatures.BuffQueue;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.items.Item;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import java.util.List;
/*    */ 
/*    */ public class DebuffCure
/*    */   extends AbstractAbility
/*    */   implements ElementAbility, ActiveAbility {
/*    */   private Element cureElement;
/*    */   private String cureStatus;
/*    */   
/*    */   public DebuffCure(AbilityParams params, RpgLoader loader) {
/* 27 */     if (params.getRecordCount() > 1) {
/* 28 */       throw new IllegalArgumentException("Multirow not supported for Debuff Cure");
/*    */     }
/* 30 */     params.next();
/*    */     
/* 32 */     init(params);
/*    */     
/* 34 */     this.cureElement = (Element)params.getEnumOptional(AbilityParams.ParamName.ELEMENT, (Enum[])Element.values(), null);
/* 35 */     this.cureStatus = params.getStrOptional(AbilityParams.ParamName.STATUS, null);
/*    */     
/* 37 */     if (this.cureStatus != null) {
/* 38 */       this.cureStatus = this.cureStatus.toLowerCase();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void useIfHit(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, List<? extends TargetProvider> targetProviders, UsageParams usageParams) {
/* 44 */     for (TargetProvider targetProvider : targetProviders) {
/* 45 */       useAlways(parent, evaluator, sourceProvider, targetProvider, usageParams);
/*    */     }
/*    */   }
/*    */   
/*    */   public void useAlways(Item parent, ShapeDataEvaluator evaluator, SourceProvider sourceProvider, TargetProvider targetProvider, UsageParams usageParams) {
/* 50 */     ((BuffQueue)targetProvider.getTargetHandler().getSupport(BuffQueue.class)).enqueueFinish(BuffType.DEBUFF, this.cureElement, this.cureStatus);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean canHit(Item parentItem, ShapeDataEvaluator evaluator, RpgEntity source, RpgEntity target, UsageParams usageParams) {
/* 55 */     return true;
/*    */   }
/*    */   
/*    */   public Element getElement() {
/* 59 */     return this.cureElement;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 64 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\abilities\DebuffCure.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */