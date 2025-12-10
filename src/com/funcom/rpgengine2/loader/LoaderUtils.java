/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.commons.localization.LocalizationException;
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.combat.ShapeDataEvaluator;
/*    */ 
/*    */ 
/*    */ public class LoaderUtils
/*    */ {
/*    */   public static boolean parseYesNo(String str) {
/* 13 */     if ("yes".equalsIgnoreCase(str) || "y".equalsIgnoreCase(str))
/*    */     {
/* 15 */       return true; } 
/* 16 */     if ("no".equalsIgnoreCase(str) || "n".equalsIgnoreCase(str))
/*    */     {
/* 18 */       return false;
/*    */     }
/*    */     
/* 21 */     throw new IllegalArgumentException("Cannot parse yes/no, unknown text: text=" + str);
/*    */   }
/*    */   
/*    */   public static boolean parseYesNo(String str, boolean defaultValue) {
/* 25 */     if ("yes".equalsIgnoreCase(str) || "y".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str))
/*    */     {
/*    */       
/* 28 */       return true; } 
/* 29 */     if ("no".equalsIgnoreCase(str) || "n".equalsIgnoreCase(str) || "false".equalsIgnoreCase(str))
/*    */     {
/*    */       
/* 32 */       return false; } 
/* 33 */     if (str == null || str.length() == 0) {
/* 34 */       return defaultValue;
/*    */     }
/*    */     
/* 37 */     throw new IllegalArgumentException("Cannot parse yes/no, unknown text: text=" + str);
/*    */   }
/*    */   
/*    */   public static int parseLevel(String str) {
/* 41 */     if (str.length() == 0 || "inf".equalsIgnoreCase(str))
/*    */     {
/* 43 */       return Integer.MAX_VALUE;
/*    */     }
/*    */     try {
/* 46 */       return Integer.parseInt(str);
/* 47 */     } catch (NumberFormatException e) {
/* 48 */       throw new RuntimeException("cannot parse level: levelStr=" + str, e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static Ability loadAbility(String abilityId, AbilityContainer parentContainer, AbilityParams params, ShapeDataEvaluator evaluator, RpgLoader loader, ValueAccumulatorFactory factory, Class[] supportedAbilities) {
/* 55 */     RawData data = loader.getRawDataMap().get(abilityId);
/*    */     
/* 57 */     if (data == null) {
/* 58 */       throw new IllegalArgumentException("Ability not found: name=" + abilityId);
/*    */     }
/*    */     
/* 61 */     data.assertAbilityType("This ability is not supported by Item", supportedAbilities);
/*    */     
/* 63 */     AbilityFactory abilityFactory = data.getAbilityFactory();
/* 64 */     AbilityParams abilityParams = abilityFactory.createParams(data.getFieldsList());
/*    */     
/* 66 */     abilityParams.setCommon(AbilityParams.ParamName.REQ_LEVEL_FROM, Integer.valueOf(params.getInt(AbilityParams.ParamName.REQ_LEVEL_FROM)));
/* 67 */     abilityParams.setCommon(AbilityParams.ParamName.REQ_LEVEL_TO, Integer.valueOf(params.getLevel(AbilityParams.ParamName.REQ_LEVEL_TO)));
/*    */     try {
/* 69 */       abilityParams.resolveVariables(params);
/* 70 */     } catch (LocalizationException e) {
/* 71 */       throw new RuntimeException(e);
/*    */     } 
/*    */     
/* 74 */     Ability ability = abilityFactory.create(parentContainer, evaluator, abilityParams, loader, factory);
/*    */     
/* 76 */     if (ability == null) {
/* 77 */       throw new NullPointerException("Cannot create ability: factoryClass=" + abilityFactory.getClass());
/*    */     }
/* 79 */     return ability;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\LoaderUtils.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */