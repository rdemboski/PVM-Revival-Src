/*    */ package com.funcom.tcg.client.rpg;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGClientTownPortalFactory
/*    */   implements AbilityFactory
/*    */ {
/*    */   public Class<? extends Ability> getAbilityPermissionClass() {
/* 22 */     return (Class)ActiveAbility.class;
/*    */   }
/*    */ 
/*    */   
/*    */   public Ability create(AbilityContainer parentContainer, ShapeDataEvaluator evaluator, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 27 */     return (Ability)new ClientTownPortalAbility();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public AbilityParams createParams(List<String[]> fieldsList) {
/* 33 */     return new AbilityParams();
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\rpg\TCGClientTownPortalFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */