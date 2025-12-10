/*    */ package com.funcom.rpgengine2.buffs;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import com.funcom.rpgengine2.loader.AbilityFactory;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RemoveInfiniteBuffFactory
/*    */   implements AbilityFactory
/*    */ {
/*    */   public Class<? extends Ability> getAbilityPermissionClass() {
/* 19 */     return (Class)ActiveAbility.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 24 */     return (Ability)new RemoveInfiniteBuffCreator(parentContainer, evaluator, params, loader, factory);
/*    */   }
/*    */ 
/*    */   
/*    */   public AbilityParams createParams(List<String[]> fieldsList) {
/* 29 */     AbilityParams params = new AbilityParams();
/* 30 */     params.setRecords(fieldsList, new AbilityParams.ParamName[] { AbilityParams.ParamName.ID, AbilityParams.ParamName.TARGET_BUFF, AbilityParams.ParamName.ELEMENT, AbilityParams.ParamName.STATUS, AbilityParams.ParamName.SHAPE_REF });
/* 31 */     return params;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\buffs\RemoveInfiniteBuffFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */