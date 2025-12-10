/*    */ package com.funcom.gameengine.model.command;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ 
/*    */ public class FixedDestinationWithAngle
/*    */   extends AbstractDestination
/*    */ {
/*    */   private WorldCoordinate target;
/*    */   private double angle;
/*    */   
/*    */   public FixedDestinationWithAngle(WorldCoordinate target, double angle) {
/* 13 */     this.target = target;
/* 14 */     this.angle = angle;
/*    */   }
/*    */ 
/*    */   
/*    */   public WorldCoordinate getTarget() {
/* 19 */     return this.target;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAngleDependent() {
/* 24 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getPreferredAngle() {
/* 29 */     return this.angle;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\model\command\FixedDestinationWithAngle.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */