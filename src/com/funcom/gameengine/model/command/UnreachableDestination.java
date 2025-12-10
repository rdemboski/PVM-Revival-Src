/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.commons.Vector2d;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import com.funcom.gameengine.model.props.Creature;
/*    */ import java.awt.Point;
/*    */ 
/*    */ public class UnreachableDestination
/*    */   implements Destination {
/*    */   private Creature mover;
/*    */   private double magnitude;
/*    */   private double angle;
/*    */   
/*    */   public UnreachableDestination(Creature mover) {
/* 15 */     this.mover = mover;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldCoordinate getTarget() {
/* 20 */     Vector2d vector = new Vector2d(this.magnitude * Math.cos(this.angle), this.magnitude * Math.sin(this.angle));
/* 21 */     WorldCoordinate moveVec = new WorldCoordinate(new Point(), vector, null, 0);
/* 22 */     moveVec.rotate(this.mover.getRotation());
/* 23 */     return (new WorldCoordinate(this.mover.getPosition())).add(moveVec);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAngleDependent() {
/* 28 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPreferredAngle() {
/* 33 */     return this.angle + this.mover.getRotation();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAtDestination(double destDistance) {
/* 38 */     return (this.magnitude < 1.0E-6D);
/*    */   }
/*    */   
/*    */   public void setVector(double magnitude, double angle) {
/* 42 */     this.magnitude = magnitude;
/* 43 */     this.angle = angle;
/*    */   }
/*    */   
/*    */   public double getManitude() {
/* 47 */     return this.magnitude;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\UnreachableDestination.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */