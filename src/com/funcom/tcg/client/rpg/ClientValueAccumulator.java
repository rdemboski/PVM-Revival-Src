/*    */ package com.funcom.tcg.client.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.GeneralValueAccumulator;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ import com.funcom.rpgengine2.pets.PetSupport;
/*    */ 
/*    */ 
/*    */ public class ClientValueAccumulator
/*    */   extends GeneralValueAccumulator
/*    */ {
/*    */   private float addPetLevelMul;
/*    */   private float addPetLevelSqrMul;
/*    */   
/*    */   public ClientValueAccumulator(RpgLoader loader, AbilityParams params) {
/* 18 */     super(loader, params);
/*    */   }
/*    */ 
/*    */   
/*    */   public int eval(AbilityContainer abilityContainer, RpgEntity rpgObject) {
/* 23 */     return getBaseDamage(abilityContainer, rpgObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public int getBaseDamage(AbilityContainer abilityContainer, RpgEntity rpgObject) {
/* 28 */     int value = super.getBaseDamage(abilityContainer, rpgObject);
/* 29 */     if (this.addPetLevelMul != 0.0F) {
/* 30 */       PetSupport petSupport = (PetSupport)rpgObject.getSupport(PetSupport.class);
/* 31 */       if (petSupport != null) {
/* 32 */         value += (int)(this.addPetLevelMul * petSupport.getActivePetLevel());
/*    */       }
/*    */     } 
/* 35 */     if (this.addPetLevelSqrMul != 0.0F) {
/* 36 */       PetSupport petSupport = (PetSupport)rpgObject.getSupport(PetSupport.class);
/* 37 */       if (petSupport != null) {
/* 38 */         value += (int)(this.addPetLevelSqrMul * petSupport.getActivePetLevel() * petSupport.getActivePetLevel());
/*    */       }
/*    */     } 
/* 41 */     return value;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init(RpgLoader loader, AbilityParams params) {
/* 46 */     super.init(loader, params);
/*    */     
/* 48 */     this.addPetLevelMul = params.getFloatOptional(AbilityParams.ParamName.ADD_PET_LEVEL_MULT, 0.0F);
/* 49 */     this.addPetLevelSqrMul = params.getFloatOptional(AbilityParams.ParamName.ADD_PET_LEVEL_SQUARE_MUL, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\rpg\ClientValueAccumulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */