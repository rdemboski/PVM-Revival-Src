/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.abilities.ActiveAbility;
/*    */ import com.funcom.rpgengine2.abilities.projectile.ProjectileCreator;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProjectileCreatorFactory
/*    */   implements AbilityFactory
/*    */ {
/*    */   public Class<? extends Ability> getAbilityPermissionClass() {
/* 19 */     return (Class)ActiveAbility.class;
/*    */   }
/*    */   
/*    */   public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 23 */     return (Ability)new ProjectileCreator(parentContainer, evaluator, params, loader, factory);
/*    */   }
/*    */   
/*    */   public AbilityParams createParams(List<String[]> fieldsList) {
/* 27 */     AbilityParams params = new AbilityParams();
/*    */     
/* 29 */     params.setRecords(fieldsList, new AbilityParams.ParamName[] { AbilityParams.ParamName.ID, AbilityParams.ParamName.TRIGGER_WIDTH, AbilityParams.ParamName.TRIGGER_HEIGHT, AbilityParams.ParamName.LOCKING_WIDTH, AbilityParams.ParamName.LOCKING_HEIGHT, AbilityParams.ParamName.MOVEMENT_DISTANCE, AbilityParams.ParamName.MOVEMENT_TIME, AbilityParams.ParamName.MOVEMENT_ANGLE_DEGREE, AbilityParams.ParamName.OFFSET_X, AbilityParams.ParamName.OFFSET_Y, AbilityParams.ParamName.OFFSET_DISTANCE, AbilityParams.ParamName.HOMING, AbilityParams.ParamName.TUNNELING, AbilityParams.ParamName.MULTIPLE_HITS_ALLOWED, AbilityParams.ParamName.REFLECTABLE, AbilityParams.ParamName.COLLISION_HEIGHT, AbilityParams.ParamName.ABILITY_ID, AbilityParams.ParamName.SHAPE_REF, AbilityParams.ParamName.X, AbilityParams.ParamName.Y, AbilityParams.ParamName.Z, AbilityParams.ParamName.K, AbilityParams.ParamName.SMALL_ICON, AbilityParams.ParamName.LANGUAGE_KEY });
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 37 */     return params;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\ProjectileCreatorFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */