/*    */ package com.funcom.rpgengine2.creatures;
/*    */ 
/*    */ 
/*    */ public abstract class OwnedRpgObject
/*    */   extends RpgObject
/*    */ {
/*    */   private RpgEntity owner;
/*    */   
/*    */   public void setOwner(RpgEntity owner) {
/* 10 */     this.owner = owner;
/*    */   }
/*    */   
/*    */   public RpgEntity getOwner() {
/* 14 */     return this.owner;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-rpgengine.jar!\com\funcom\rpgengine2\creatures\OwnedRpgObject.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */