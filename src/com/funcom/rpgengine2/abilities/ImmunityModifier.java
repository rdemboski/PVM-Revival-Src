/*    */ package com.funcom.rpgengine2.abilities;
/*    */ 
/*    */ import com.funcom.rpgengine2.creatures.ImmunitySupport;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ 
/*    */ 
/*    */ public class ImmunityModifier
/*    */   extends AbstractAbility
/*    */   implements EquipAwareAbility, PassiveAbility
/*    */ {
/*    */   private final String immunity;
/*    */   private final boolean equipRequired;
/*    */   
/*    */   public ImmunityModifier(String id, AbilityParams params) {
/* 17 */     if (params.getRecordCount() > 1) {
/* 18 */       throw new IllegalArgumentException("Multirow not supported for RpgStatus Modifier");
/*    */     }
/* 20 */     params.next();
/*    */     
/* 22 */     init(params);
/*    */     
/* 24 */     this.immunity = params.getStr(AbilityParams.ParamName.X);
/* 25 */     this.equipRequired = params.getBoolean(AbilityParams.ParamName.Y);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 31 */     return 0;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isEquipRequired() {
/* 36 */     return this.equipRequired;
/*    */   }
/*    */ 
/*    */   
/*    */   public void addAbility(RpgEntity rpgEntity) {
/* 41 */     ImmunitySupport immunitySupport = (ImmunitySupport)rpgEntity.getSupport(ImmunitySupport.class);
/* 42 */     immunitySupport.addImmunity(this.immunity);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void removeAbility(RpgEntity rpgEntity) {
/* 48 */     ImmunitySupport immunitySupport = (ImmunitySupport)rpgEntity.getSupport(ImmunitySupport.class);
/* 49 */     immunitySupport.removeImmunity(this.immunity);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\ImmunityModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */