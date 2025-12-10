/*    */ package com.funcom.tcg.rpg.abilities;
/*    */ 
/*    */ import com.funcom.rpgengine2.RPGFactionFilterType;
/*    */ import com.funcom.rpgengine2.creatures.OwnedRpgObject;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.TargetValidationSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGOwnedRpgObjectTargetValidationSupport
/*    */   extends TargetValidationSupport
/*    */ {
/*    */   public TCGOwnedRpgObjectTargetValidationSupport(OwnedRpgObject owner) {
/* 14 */     super((RpgEntity)owner);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidTarget(RpgEntity target, RPGFactionFilterType rpgFactionFilterType) {
/* 19 */     return ((TargetValidationSupport)((OwnedRpgObject)this.owner).getOwner().getSupport(TargetValidationSupport.class)).isValidTarget(target, rpgFactionFilterType);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidSelf(RpgEntity target) {
/* 24 */     return (this.owner == target);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\abilities\TCGOwnedRpgObjectTargetValidationSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */