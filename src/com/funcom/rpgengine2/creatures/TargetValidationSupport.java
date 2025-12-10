/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ import com.funcom.rpgengine2.RPGFactionFilterType;
/*    */ 
/*    */ 
/*    */ public class TargetValidationSupport
/*    */   implements RpgQueryableSupport
/*    */ {
/*    */   protected RpgEntity owner;
/*    */   
/*    */   public TargetValidationSupport(RpgEntity owner) {
/* 12 */     this.owner = owner;
/*    */   }
/*    */   
/*    */   public boolean isValidTarget(RpgEntity target, RPGFactionFilterType rpgFactionFilterType) {
/* 16 */     return (this.owner != target);
/*    */   }
/*    */   
/*    */   public boolean isValidSelf(RpgEntity target) {
/* 20 */     return (this.owner == target);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\TargetValidationSupport.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */