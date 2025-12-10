/*    */ package com.funcom.rpgengine2.abilities;
/*    */ 
/*    */ import com.funcom.rpgengine2.Persistence;
/*    */ import com.funcom.rpgengine2.Stat;
/*    */ import com.funcom.rpgengine2.StatCollection;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulator;
/*    */ import com.funcom.rpgengine2.abilities.valueaccumulator.ValueAccumulatorFactory;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.StatModifierSupport;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ import com.funcom.rpgengine2.loader.RpgLoader;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneralStatModifier
/*    */   extends AbstractAbility
/*    */   implements StatModifier
/*    */ {
/*    */   private boolean equipRequired;
/*    */   private ValueAccumulator accumulator;
/*    */   private Short targetStatId;
/*    */   private AbilityContainer parentContainer;
/*    */   
/*    */   public GeneralStatModifier(AbilityContainer parentContainer, AbilityParams params, RpgLoader loader, ValueAccumulatorFactory factory) {
/* 29 */     if (params.getRecordCount() > 1) {
/* 30 */       throw new IllegalArgumentException("Multirow not supported for Stat Modifier");
/*    */     }
/* 32 */     params.next();
/*    */     
/* 34 */     init(params);
/*    */     
/* 36 */     this.parentContainer = parentContainer;
/*    */     
/* 38 */     this.equipRequired = params.getYesNo(AbilityParams.ParamName.EQUIP_REQUIRED);
/*    */ 
/*    */     
/* 41 */     Persistence checkPersistence = (Persistence)params.getEnumOptional(AbilityParams.ParamName.ADD_STAT_PERSISTENCE, (Enum[])Persistence.values(), null);
/* 42 */     if (checkPersistence != null && checkPersistence != Persistence.BASE) {
/* 43 */       throw new UnsupportedOperationException("Unsupported stat persistence type. Only BASE is supported. Using the othertypes requires handling of ordering of stat changes.");
/*    */     }
/*    */ 
/*    */     
/* 47 */     this.accumulator = (ValueAccumulator)factory.createValueAccumulator(loader, params);
/*    */     
/* 49 */     String targetStatIdStr = params.getStrNonEmpty(AbilityParams.ParamName.TARGET_STAT_ID);
/* 50 */     this.targetStatId = loader.getStatIdTranslator().translate(targetStatIdStr);
/*    */   }
/*    */   
/*    */   public void addAbility(RpgEntity rpgEntity) {
/* 54 */     ((StatModifierSupport)rpgEntity.getSupport(StatModifierSupport.class)).addUniqueStatModifier(this);
/*    */   }
/*    */   
/*    */   public void removeAbility(RpgEntity rpgEntity) {
/* 58 */     ((StatModifierSupport)rpgEntity.getSupport(StatModifierSupport.class)).removeUniqueStatModifier(this);
/*    */   }
/*    */   
/*    */   public void modifyStats(StatCollection statCollection, RpgEntity owner) {
/* 62 */     int value = this.accumulator.eval(this.parentContainer, owner);
/*    */     
/* 64 */     Stat targetStat = statCollection.get(this.targetStatId);
/* 65 */     targetStat.addModifier(value);
/*    */   }
/*    */   
/*    */   public short getTargetStatId() {
/* 69 */     return this.targetStatId.shortValue();
/*    */   }
/*    */   
/*    */   public boolean isEquipRequired() {
/* 73 */     return this.equipRequired;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getLanguageKey() {
/* 78 */     return super.getLanguageKey().replace("[X]", String.valueOf((int)this.accumulator.getAddFixed()));
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 83 */     return 0;
/*    */   }
/*    */   
/*    */   public ValueAccumulator getAccumulator() {
/* 87 */     return this.accumulator;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\GeneralStatModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */