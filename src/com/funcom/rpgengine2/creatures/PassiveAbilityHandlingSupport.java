/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.abilities.Ability;
/*    */ import com.funcom.rpgengine2.abilities.EquipAwareAbility;
/*    */ import com.funcom.rpgengine2.abilities.PassiveAbility;
/*    */ import java.util.List;
/*    */ 
/*    */ public class PassiveAbilityHandlingSupport
/*    */   implements RpgQueryableSupport {
/*    */   private final StatSupport statSupport;
/*    */   private final RpgEntity entity;
/*    */   
/*    */   public PassiveAbilityHandlingSupport(RpgEntity entity, StatSupport statSupport) {
/* 14 */     this.statSupport = statSupport;
/* 15 */     this.entity = entity;
/*    */   }
/*    */   
/*    */   public void removePassiveAbilities(List<Ability> abilities) {
/* 19 */     removePassiveAbilities(abilities, true);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void removePassiveAbilities(List<Ability> abilities, boolean isEquiped) {
/* 29 */     for (Ability ability : abilities) {
/* 30 */       if (!(ability instanceof PassiveAbility) || (
/* 31 */         !isEquiped && ability instanceof EquipAwareAbility && ((EquipAwareAbility)ability).isEquipRequired())) {
/*    */         continue;
/*    */       }
/*    */       
/* 35 */       ((PassiveAbility)ability).removeAbility(this.entity);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void resetPassiveAbilities() {
/* 42 */     this.entity.fireEvent(new SupportEvent(EventType.CLEAR_PASSIVE_ABILITIES));
/*    */     
/* 44 */     this.entity.fireEvent(new SupportEvent(EventType.ADD_PASSIVE_ABILITIES));
/*    */   }
/*    */   
/*    */   public enum EventType implements SupportEvent.Type {
/* 48 */     ADD_PASSIVE_ABILITIES, CLEAR_PASSIVE_ABILITIES;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void addPassiveAbilities(List<Ability> abilities, boolean isEquipped) {
/* 64 */     int ownerLevel = this.statSupport.getLevel();
/* 65 */     int size = abilities.size();
/* 66 */     for (int i = 0; i < size; i++) {
/* 67 */       Ability ability = abilities.get(i);
/*    */       
/* 69 */       if (ability instanceof PassiveAbility && ownerLevel >= ability.getLevelFrom() && ownerLevel <= ability.getLevelTo()) {
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 74 */         if (ability instanceof EquipAwareAbility) {
/* 75 */           boolean requiresToBeEquipped = ((EquipAwareAbility)ability).isEquipRequired();
/* 76 */           if (requiresToBeEquipped && !isEquipped) {
/*    */             continue;
/*    */           }
/*    */         } 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 84 */         ((PassiveAbility)ability).addAbility(this.entity);
/*    */       } 
/*    */       continue;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\PassiveAbilityHandlingSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */