/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.commons.geom.RectangleWC;
/*    */ import java.awt.Point;
/*    */ 
/*    */ public class WorldBoundsGraphNode
/*    */   extends AbstractGraphNode
/*    */ {
/*    */   private static final float ORTHOGONAL_COST = 10.0F;
/*    */   private static final float EDGE_COST = 10.0F;
/*    */   private static final float DIAGONAL_COST = 14.0F;
/*    */   private RectangleWC bounds;
/*    */   
/*    */   public WorldBoundsGraphNode(RectangleWC bounds) {
/* 15 */     if (bounds == null)
/* 16 */       throw new IllegalArgumentException("bounds = null"); 
/* 17 */     this.bounds = bounds;
/*    */   }
/*    */   
/*    */   public RectangleWC getBounds() {
/* 21 */     return this.bounds;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public float recalculateCost(AStarNode parentAStarNode, AStarNode aStarEndPoint) {
/* 39 */     if (parentAStarNode == null) {
/* 40 */       return 10.0F;
/*    */     }
/* 42 */     WorldBoundsGraphNode parentGraphNode = (WorldBoundsGraphNode)parentAStarNode.getGraphNode();
/* 43 */     float edgeCost = (getNeighbors().size() < 8) ? 10.0F : 0.0F;
/* 44 */     float parentsCost = parentGraphNode.recalculateCost(parentAStarNode.getParent(), aStarEndPoint);
/* 45 */     if (isParentDiagonal(parentGraphNode)) {
/* 46 */       return parentsCost + 14.0F + edgeCost;
/*    */     }
/* 48 */     return parentsCost + 10.0F + edgeCost;
/*    */   }
/*    */   
/*    */   private boolean isParentDiagonal(WorldBoundsGraphNode parentGraphNode) {
/* 52 */     Point parentPos = parentGraphNode.getBounds().getCenter().getTileCoord();
/* 53 */     Point myPos = getBounds().getCenter().getTileCoord();
/* 54 */     return (parentPos.x != myPos.x && parentPos.y != myPos.y);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 60 */     return "DefaultGraphNode{bounds=" + this.bounds + '}';
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\WorldBoundsGraphNode.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */