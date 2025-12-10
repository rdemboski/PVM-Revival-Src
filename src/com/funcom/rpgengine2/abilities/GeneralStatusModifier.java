/*    */ package com.funcom.rpgengine2.abilities;
/*    */ 
/*    */ import com.funcom.rpgengine2.combat.RpgStatus;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.StatusSupport;
/*    */ import com.funcom.rpgengine2.items.ItemDescription;
/*    */ import com.funcom.rpgengine2.loader.AbilityParams;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class GeneralStatusModifier
/*    */   extends AbstractAbility
/*    */   implements StatusModifier, EquipAwareAbility
/*    */ {
/*    */   private RpgStatus rpgStatus;
/*    */   
/*    */   public GeneralStatusModifier(String id, AbilityParams params) {
/* 19 */     if (params.getRecordCount() > 1) {
/* 20 */       throw new IllegalArgumentException("Multirow not supported for RpgStatus Modifier");
/*    */     }
/* 22 */     params.next();
/*    */     
/* 24 */     init(params);
/*    */     
/* 26 */     this.rpgStatus = (RpgStatus)params.getEnum(AbilityParams.ParamName.X, (Enum[])RpgStatus.values());
/*    */   }
/*    */   
/*    */   public void addAbility(RpgEntity rpgEntity) {
/* 30 */     StatusSupport statusSupport = (StatusSupport)rpgEntity.getSupport(StatusSupport.class);
/* 31 */     if (statusSupport != null) {
/* 32 */       statusSupport.addRpgStatus(this.rpgStatus);
/*    */     }
/*    */   }
/*    */   
/*    */   public void removeAbility(RpgEntity rpgEntity) {
/* 37 */     StatusSupport statusSupport = (StatusSupport)rpgEntity.getSupport(StatusSupport.class);
/* 38 */     if (statusSupport != null) {
/* 39 */       statusSupport.removeRpgStatus(this.rpgStatus);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean isEquipRequired() {
/* 44 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getDamage(ItemDescription description, RpgEntity statSupport, short statToLookFor) {
/* 49 */     return 0;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\abilities\GeneralStatusModifier.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */