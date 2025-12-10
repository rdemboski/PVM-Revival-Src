/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ 
/*    */ public class ManhattanDistance
/*    */   implements HeuristicsAlgorithm {
/*    */   private static final int COST_FACTOR = 14;
/*    */   
/*    */   public float calculateCost(AStarNode startPoint, AStarNode endPoint) {
/* 10 */     WorldCoordinate myPos = ((WorldBoundsGraphNode)startPoint.getGraphNode()).getBounds().getCenter();
/* 11 */     WorldCoordinate endPos = ((WorldBoundsGraphNode)endPoint.getGraphNode()).getBounds().getCenter();
/* 12 */     return (14 * (Math.abs(myPos.getTileX() - endPos.getTileX()) + Math.abs(myPos.getTileY() - endPos.getTileY())));
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\ManhattanDistance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */