/*    */ package com.funcom.rpgengine2.loader;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class RawData
/*    */ {
/* 10 */   private List<String[]> fieldsList = (List)new ArrayList<String>();
/*    */   
/*    */   private AbilityFactory abilityFactory;
/*    */   
/*    */   public RawData(AbilityFactory abilityFactory) {
/* 15 */     this.abilityFactory = abilityFactory;
/*    */   }
/*    */   
/*    */   public void addFields(String[] fields) {
/* 19 */     this.fieldsList.add(fields);
/*    */   }
/*    */ 
/*    */   
/*    */   public void assertAbilityType(String errorMsg, Class[] supportedAbilityClasses) {
/* 24 */     for (Class<? extends Ability> abilityClass : supportedAbilityClasses) {
/* 25 */       if (this.abilityFactory.getAbilityPermissionClass() == abilityClass) {
/*    */         return;
/*    */       }
/*    */     } 
/*    */ 
/*    */     
/* 31 */     throw new UnsupportedAbilityException(errorMsg);
/*    */   }
/*    */   
/*    */   public AbilityFactory getAbilityFactory() {
/* 35 */     return this.abilityFactory;
/*    */   }
/*    */   
/*    */   public List<String[]> getFieldsList() {
/* 39 */     return this.fieldsList;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\loader\RawData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */