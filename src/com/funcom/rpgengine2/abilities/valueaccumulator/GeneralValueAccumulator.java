/*    */ package com.funcom.rpgengine2.abilities.valueaccumulator;
/*    */ 
/*    */ import com.funcom.rpgengine2.Persistence;
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.StatCollection;
/*    */ import com.funcom.rpgengine2.abilities.AbilityContainer;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.StatSupport;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ 
/*    */ public class GeneralValueAccumulator
/*    */   implements ValueAccumulator {
/*    */   private Short addStatId;
/*    */   private Persistence addStatPersistence;
/*    */   
/*    */   public float getAddFixed() {
/* 18 */     return this.addFixed;
/*    */   }
/*    */ 
/*    */   
/*    */   private float addStatMul;
/*    */   private float addFixed;
/*    */   private float variance;
/*    */   private float addItemLevelMul;
/*    */   private float addSourceLevelSquareMul;
/*    */   private float addSourceLevelMul;
/*    */   private Short levelStatId;
/*    */   
/*    */   public GeneralValueAccumulator(RpgLoader loader, AbilityParams params) {
/* 31 */     init(loader, params);
/*    */     
/* 33 */     this.levelStatId = loader.getLevelStatId();
/*    */   }
/*    */   
/*    */   public int eval(AbilityContainer abilityContainer, RpgEntity rpgObject) {
/* 37 */     int damage = getBaseDamage(abilityContainer, rpgObject);
/* 38 */     if (this.variance != 0.0F) {
/* 39 */       double currentVariance = (Math.random() * 2.0D - 1.0D) * this.variance;
/* 40 */       damage = (int)(damage + Math.round(damage * currentVariance));
/*    */     } 
/* 42 */     return damage;
/*    */   }
/*    */   
/*    */   protected int getBaseDamage(AbilityContainer abilityContainer, RpgEntity rpgObject) {
/* 46 */     float value = 0.0F;
/* 47 */     StatCollection statSupport = ((StatSupport)rpgObject.getSupport(StatSupport.class)).getStatCollection();
/*    */     
/* 49 */     if (this.addStatId != null) {
/* 50 */       Stat stat = statSupport.get(this.addStatId);
/* 51 */       value += this.addStatMul * stat.getByPersistence(this.addStatPersistence);
/*    */     } 
/*    */     
/* 54 */     value += this.addFixed;
/*    */     
/* 56 */     if (this.addItemLevelMul != 0.0F) {
/* 57 */       value += this.addItemLevelMul * abilityContainer.getLevel();
/*    */     }
/* 59 */     if ((this.addSourceLevelMul != 0.0F || this.addSourceLevelSquareMul != 0.0F) && this.levelStatId != null) {
/* 60 */       value = (float)(value + (this.addSourceLevelMul * statSupport.get(this.levelStatId).getSum()) + this.addSourceLevelSquareMul * Math.pow(statSupport.get(this.levelStatId).getSum(), 2.0D));
/*    */     }
/*    */ 
/*    */     
/* 64 */     return (int)value;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void init(RpgLoader loader, AbilityParams params) {
/* 69 */     String addStatIdStr = params.getStrOptional(AbilityParams.ParamName.ADD_STAT_ID, null);
/* 70 */     this.addStatId = loader.getStatIdTranslator().translate(addStatIdStr);
/*    */     
/* 72 */     this.addStatPersistence = (Persistence)params.getEnumOptional(AbilityParams.ParamName.ADD_STAT_PERSISTENCE, (Enum[])Persistence.values(), null);
/* 73 */     this.addStatMul = params.getFloatOptional(AbilityParams.ParamName.ADD_STAT_MUL, 0.0F);
/*    */     
/* 75 */     this.addFixed = params.getFloatOptional(AbilityParams.ParamName.ADD_FIXED_VALUE, 0.0F);
/* 76 */     this.variance = params.getFloatOptional(AbilityParams.ParamName.VARIANCE, 0.0F);
/*    */     
/* 78 */     this.addItemLevelMul = params.getFloatOptional(AbilityParams.ParamName.ADD_ITEM_LEVEL_MUL, 0.0F);
/* 79 */     this.addSourceLevelMul = params.getFloatOptional(AbilityParams.ParamName.ADD_SOURCE_LEVEL_MUL, 0.0F);
/* 80 */     this.addSourceLevelSquareMul = params.getFloatOptional(AbilityParams.ParamName.ADD_SOURCE_LEVEL_SQUARE_MUL, 0.0F);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\valueaccumulator\GeneralValueAccumulator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */