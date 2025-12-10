/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.abilities.EffectFilter;
/*    */ import com.funcom.rpgengine2.abilities.GeneralEffectFilter;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EffectFilterFactory
/*    */   implements AbilityFactory
/*    */ {
/*    */   private boolean sourceFilter;
/*    */   
/*    */   public EffectFilterFactory(boolean isSourceFilter) {
/* 20 */     this.sourceFilter = isSourceFilter;
/*    */   }
/*    */   
/*    */   public Class<? extends Ability> getAbilityPermissionClass() {
/* 24 */     return (Class)EffectFilter.class;
/*    */   }
/*    */   
/*    */   public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 28 */     GeneralEffectFilter ret = new GeneralEffectFilter();
/*    */     
/* 30 */     ret.init(params, loader);
/*    */     
/* 32 */     return (Ability)ret;
/*    */   }
/*    */   
/*    */   public AbilityParams createParams(List<String[]> fieldsList) {
/* 36 */     AbilityParams params = new AbilityParams();
/*    */     
/* 38 */     params.setCommon(AbilityParams.ParamName.__SOURCE_FILTER, Boolean.valueOf(this.sourceFilter).toString());
/*    */     
/* 40 */     params.setRecords(fieldsList, new AbilityParams.ParamName[] { AbilityParams.ParamName.ID, AbilityParams.ParamName.PRIORITY, AbilityParams.ParamName.EQUIP_REQUIRED, AbilityParams.ParamName.COND_PERSISTENCE, AbilityParams.ParamName.COND_ORIGINAL, AbilityParams.ParamName.COND_BONUS, AbilityParams.ParamName.COND_STAT, AbilityParams.ParamName.COND_ELEMENT, AbilityParams.ParamName.EFFECT_FIELD, AbilityParams.ParamName.EFFECT_ASSIGN_OP, AbilityParams.ParamName.EFFECT_1, AbilityParams.ParamName.EFFECT_OPERATOR, AbilityParams.ParamName.EFFECT_2 });
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 45 */     return params;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\EffectFilterFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */