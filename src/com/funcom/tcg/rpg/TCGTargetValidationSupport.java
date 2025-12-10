/*    */ package com.funcom.tcg.rpg;
/*    */ 
/*    */ import com.funcom.rpgengine2.RPGFactionFilterType;
/*    */ import com.funcom.rpgengine2.creatures.RpgEntity;
/*    */ import com.funcom.rpgengine2.creatures.TargetValidationSupport;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TCGTargetValidationSupport
/*    */   extends TargetValidationSupport
/*    */ {
/*    */   public TCGTargetValidationSupport(RpgEntity owner) {
/* 14 */     super(owner);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isValidTarget(RpgEntity target, RPGFactionFilterType rpgFactionFilterType) {
/* 19 */     TCGRpgObject targetObject = (TCGRpgObject)target;
/*    */ 
/*    */     
/* 22 */     if (targetObject.getFaction().equivalentTo(BaseFaction.FREE_FOR_ALL)) {
/* 23 */       return (super.isValidTarget(target, rpgFactionFilterType) && targetObject.isHittable());
/*    */     }
/*    */     
/* 26 */     if (isTargetAcceptableFaction(targetObject)) {
/* 27 */       switch (rpgFactionFilterType) {
/*    */         case OWN:
/* 29 */           if (((TCGRpgObject)this.owner).getFaction().equivalentTo(targetObject.getFaction())) {
/* 30 */             return targetObject.isHittable();
/*    */           }
/*    */           break;
/*    */         case ALL:
/* 34 */           return targetObject.isHittable();
/*    */         case OWN_EXCEPT_SELF:
/* 36 */           if (((TCGRpgObject)this.owner).getFaction() == targetObject.getFaction()) {
/* 37 */             return (targetObject.isHittable() && !isValidSelf(target));
/*    */           }
/*    */           break;
/*    */         case ALL_EXCEPT_SELF:
/* 41 */           return (targetObject.isHittable() && !isValidSelf(target));
/*    */         case OTHER:
/* 43 */           if (!((TCGRpgObject)this.owner).getFaction().equivalentTo(targetObject.getFaction())) {
/* 44 */             return targetObject.isHittable();
/*    */           }
/*    */           break;
/*    */       } 
/*    */     }
/* 49 */     return false;
/*    */   }
/*    */   
/*    */   public boolean isTargetAcceptableFaction(TCGRpgObject tcgRpgObject) {
/* 53 */     return (!tcgRpgObject.getFaction().equivalentTo(BaseFaction.PROJECTILE) && !tcgRpgObject.getFaction().equivalentTo(BaseFaction.REFLECTED_PROJECTILE) && !tcgRpgObject.getFaction().equivalentTo(BaseFaction.INTERACT_KILL) && !tcgRpgObject.getFaction().equivalentTo(BaseFaction.NO_FIGHT));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-tcgcommon.jar!\com\funcom\tcg\rpg\TCGTargetValidationSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */