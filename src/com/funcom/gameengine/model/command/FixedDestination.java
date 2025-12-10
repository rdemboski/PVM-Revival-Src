/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FixedDestination
/*    */   extends AbstractDestination
/*    */ {
/*    */   private WorldCoordinate target;
/*    */   private Creature theOneWhoMoves;
/*    */   
/*    */   public FixedDestination(WorldCoordinate target, Creature theOneWhoMoves) {
/* 17 */     this.theOneWhoMoves = theOneWhoMoves;
/* 18 */     if (target == null)
/* 19 */       throw new IllegalArgumentException("target = null"); 
/* 20 */     this.target = target;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldCoordinate getTarget() {
/* 25 */     return this.target;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAngleDependent() {
/* 30 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPreferredAngle() {
/* 35 */     if (this.theOneWhoMoves.canMove()) {
/* 36 */       return this.theOneWhoMoves.getRotation();
/*    */     }
/* 38 */     return this.theOneWhoMoves.getPosition().angleTo(this.target);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\FixedDestination.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */