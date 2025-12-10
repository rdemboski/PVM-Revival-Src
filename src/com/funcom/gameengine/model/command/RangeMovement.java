/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import com.funcom.gameengine.model.props.InteractibleProp;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RangeMovement
/*    */   extends AbstractDestination
/*    */ {
/*    */   private Creature theOneWhoMoves;
/*    */   private double targetRange;
/*    */   private InteractibleProp target;
/*    */   private double spotAroundTarget;
/*    */   
/*    */   public RangeMovement(InteractibleProp target, Creature theOneWhoMoves, double targetRange, double spreadAngle) {
/* 19 */     this.target = target;
/* 20 */     this.theOneWhoMoves = theOneWhoMoves;
/* 21 */     this.targetRange = targetRange;
/* 22 */     this.spotAroundTarget = spreadAngle * (Math.random() - 0.5D);
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldCoordinate getTarget() {
/* 27 */     WorldCoordinate moverPos = this.theOneWhoMoves.getPosition();
/* 28 */     WorldCoordinate targetPos = this.target.getPosition();
/*    */     
/* 30 */     double distance = moverPos.distanceTo(targetPos) - this.targetRange;
/* 31 */     if (distance <= 0.0D) {
/* 32 */       return moverPos;
/*    */     }
/* 34 */     WorldCoordinate unitDirectionVectorFromTargetToMover = new WorldCoordinate(moverPos);
/* 35 */     unitDirectionVectorFromTargetToMover.subtract(targetPos).normalize();
/* 36 */     WorldCoordinate fromTargetToMover = (new WorldCoordinate(unitDirectionVectorFromTargetToMover)).multLocal(this.targetRange);
/* 37 */     fromTargetToMover.rotate(this.spotAroundTarget);
/* 38 */     return fromTargetToMover.add(targetPos);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAngleDependent() {
/* 43 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPreferredAngle() {
/* 48 */     WorldCoordinate moverPos = this.theOneWhoMoves.getPosition();
/* 49 */     WorldCoordinate targetPos = this.target.getPosition();
/* 50 */     return moverPos.angleTo(targetPos);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\RangeMovement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */