/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.abilities.BuffCreator;
/*    */ import com.funcom.rpgengine2.abilities.BuffType;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.buffs.GeneralBuffCreator;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BuffCreatorFactory
/*    */   implements AbilityFactory
/*    */ {
/*    */   protected final BuffType buffType;
/*    */   
/*    */   public BuffCreatorFactory(BuffType buffType) {
/* 24 */     this.buffType = buffType;
/*    */   }
/*    */   
/*    */   public Class<? extends Ability> getAbilityPermissionClass() {
/* 28 */     return (Class)BuffCreator.class;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 34 */     return (Ability)new GeneralBuffCreator(parentContainer, evaluator, params, loader, this.buffType, factory);
/*    */   }
/*    */   
/*    */   public AbilityParams createParams(List<String[]> fieldsList) {
/* 38 */     AbilityParams params = new AbilityParams();
/*    */     
/* 40 */     params.setRecords(fieldsList, new AbilityParams.ParamName[] { AbilityParams.ParamName.ID, AbilityParams.ParamName.ICON, AbilityParams.ParamName.BUFF_DFX_REFERENCE, AbilityParams.ParamName.SHAPE_REF, AbilityParams.ParamName.DURATION, AbilityParams.ParamName.TOTAL_TICKS, AbilityParams.ParamName.INFINITE, AbilityParams.ParamName.ELEMENT, AbilityParams.ParamName.STATUS, AbilityParams.ParamName.REQ_LEVEL_FROM, AbilityParams.ParamName.REQ_LEVEL_TO, AbilityParams.ParamName.ABILITY_ID, AbilityParams.ParamName.X, AbilityParams.ParamName.Y, AbilityParams.ParamName.Z, AbilityParams.ParamName.K, AbilityParams.ParamName.SMALL_ICON, AbilityParams.ParamName.LANGUAGE_KEY });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 48 */     return params;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\BuffCreatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */