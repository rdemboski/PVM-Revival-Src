/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*    */ import com.funcom.rpgengine2.abilities.GeneralEffectCreator;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EffectCreatorFactory
/*    */   implements AbilityFactory
/*    */ {
/*    */   public Class<? extends Ability> getAbilityPermissionClass() {
/* 18 */     return (Class)ActiveAbility.class;
/*    */   }
/*    */   
/*    */   public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 22 */     return (Ability)new GeneralEffectCreator(params, loader, factory);
/*    */   }
/*    */   
/*    */   public AbilityParams createParams(List<String[]> fieldsList) {
/* 26 */     AbilityParams params = new AbilityParams();
/*    */     
/* 28 */     params.setRecords(fieldsList, new AbilityParams.ParamName[] { AbilityParams.ParamName.ID, AbilityParams.ParamName.SHAPE_REF, AbilityParams.ParamName.ADD_STAT_ID, AbilityParams.ParamName.ADD_STAT_PERSISTENCE, AbilityParams.ParamName.ADD_STAT_MUL, AbilityParams.ParamName.ADD_FIXED_VALUE, AbilityParams.ParamName.ADD_PET_LEVEL_MULT, AbilityParams.ParamName.ADD_PET_LEVEL_SQUARE_MUL, AbilityParams.ParamName.ADD_ITEM_LEVEL_MUL, AbilityParams.ParamName.ADD_SOURCE_LEVEL_MUL, AbilityParams.ParamName.ADD_SOURCE_LEVEL_SQUARE_MUL, AbilityParams.ParamName.TARGET_STAT_ID, AbilityParams.ParamName.TARGET_STAT_PERSISTENCE, AbilityParams.ParamName.ELEMENT, AbilityParams.ParamName.VARIANCE, AbilityParams.ParamName.CRIT, AbilityParams.ParamName.SPELL_POWER_COEF, AbilityParams.ParamName.SPELL_POWER_COEF_A, AbilityParams.ParamName.SPELL_POWER_COEF_B, AbilityParams.ParamName.SPELL_POWER_COEF_C, AbilityParams.ParamName.SMALL_ICON, AbilityParams.ParamName.LANGUAGE_KEY });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 39 */     return params;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\EffectCreatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */