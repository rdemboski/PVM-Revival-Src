/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*    */ import com.funcom.rpgengine2.abilities.givepet.GivePetAbility;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GivePetAbilityFactory
/*    */   implements AbilityFactory
/*    */ {
/*    */   public Class<? extends Ability> getAbilityPermissionClass() {
/* 22 */     return (Class)ActiveAbility.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 27 */     return (Ability)new GivePetAbility(params, loader);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbilityParams createParams(List<String[]> fieldsList) {
/* 32 */     AbilityParams params = new AbilityParams();
/*    */     
/* 34 */     params.setRecords(fieldsList, new AbilityParams.ParamName[] { AbilityParams.ParamName.ID, AbilityParams.ParamName.ABILITY_ID, AbilityParams.ParamName.SMALL_ICON, AbilityParams.ParamName.LANGUAGE_KEY });
/*    */ 
/*    */     
/* 37 */     return params;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\GivePetAbilityFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */