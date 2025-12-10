/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.commons.MathUtils;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.ai.patrol.PatrolNode;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PatrolNodeDestination
/*    */   extends AbstractDestination
/*    */ {
/*    */   private PatrolNode patrolNode;
/*    */   private Creature theOneWhoMoves;
/*    */   
/*    */   public PatrolNodeDestination(PatrolNode patrolNode) {
/* 18 */     this.patrolNode = patrolNode;
/*    */   }
/*    */   
/*    */   public void setParent(Creature theOneWhoMoves) {
/* 22 */     this.theOneWhoMoves = theOneWhoMoves;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldCoordinate getTarget() {
/* 27 */     return this.patrolNode.getPosition();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAngleDependent() {
/* 32 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPreferredAngle() {
/* 37 */     return MathUtils.getPointAngle(this.theOneWhoMoves.getPosition(), getTarget());
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\PatrolNodeDestination.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */