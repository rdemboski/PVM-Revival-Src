/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CenterPointWaypointBuilder
/*    */   implements WaypointBuilder
/*    */ {
/*    */   public static final String TYPE = "centerpoint";
/*    */   
/*    */   public List<WorldCoordinate> makeWaypoints(AStarNode path) {
/* 17 */     List<WorldCoordinate> worldCoordinates = new LinkedList<WorldCoordinate>();
/*    */ 
/*    */     
/* 20 */     AStarNode loopNode = path;
/*    */     do {
/* 22 */       WorldCoordinate waypoint = ((WorldBoundsGraphNode)loopNode.getGraphNode()).getBounds().getCenter();
/* 23 */       worldCoordinates.add(waypoint);
/* 24 */       loopNode = loopNode.getParent();
/* 25 */     } while (loopNode != null && loopNode.hasParent());
/*    */     
/* 27 */     Collections.reverse(worldCoordinates);
/* 28 */     return worldCoordinates;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\CenterPointWaypointBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */