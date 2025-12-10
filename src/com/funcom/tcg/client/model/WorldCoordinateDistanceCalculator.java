/*    */ package com.funcom.tcg.client.model;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ 
/*    */ public class WorldCoordinateDistanceCalculator
/*    */   implements DistanceCalculator
/*    */ {
/*    */   private WorldCoordinate wc1;
/*    */   private WorldCoordinate wc2;
/*    */   
/*    */   public WorldCoordinateDistanceCalculator(WorldCoordinate wc1, WorldCoordinate wc2) {
/* 13 */     this.wc1 = wc1;
/* 14 */     this.wc2 = wc2;
/*    */   }
/*    */ 
/*    */   
/*    */   public double getDistance() {
/* 19 */     return this.wc1.distanceTo(this.wc2);
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-client.jar!\com\funcom\tcg\client\model\WorldCoordinateDistanceCalculator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */