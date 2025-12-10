/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.commons.geom.LineWC;
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.util.Collections;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.apache.log4j.Logger;
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
/*    */ public class LineOfSightWaypointBuilder
/*    */   implements WaypointBuilder
/*    */ {
/*    */   public static final String TYPE = "los";
/* 24 */   private static final Logger LOGGER = Logger.getLogger(LineOfSightWaypointBuilder.class);
/*    */   
/*    */   public List<WorldCoordinate> makeWaypoints(AStarNode path) {
/* 27 */     List<WorldCoordinate> waypoints = new LinkedList<WorldCoordinate>();
/* 28 */     LineWC ray = new LineWC();
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 33 */     List<WorldBoundsGraphNode> allNodesInBetween = new LinkedList<WorldBoundsGraphNode>();
/* 34 */     WorldBoundsGraphNode lastWaypointNode = (WorldBoundsGraphNode)path.getGraphNode();
/* 35 */     waypoints.add(lastWaypointNode.getBounds().getCenter());
/*    */     
/* 37 */     AStarNode currentPathNode = path;
/* 38 */     while (currentPathNode.hasParent()) {
/* 39 */       currentPathNode = currentPathNode.getParent();
/*    */       
/* 41 */       WorldBoundsGraphNode currentGraphNode = (WorldBoundsGraphNode)currentPathNode.getGraphNode();
/* 42 */       ray.setLine(lastWaypointNode.getBounds().getCenter(), currentGraphNode.getBounds().getCenter());
/*    */       
/* 44 */       boolean missed = false;
/* 45 */       for (WorldBoundsGraphNode boundsGraphNode : allNodesInBetween) {
/* 46 */         if (!boundsGraphNode.getBounds().intersectsLine(ray)) {
/* 47 */           missed = true;
/*    */           
/*    */           break;
/*    */         } 
/*    */       } 
/* 52 */       if (missed) {
/* 53 */         WorldBoundsGraphNode lastVisibleGraphNode = allNodesInBetween.get(allNodesInBetween.size() - 1);
/* 54 */         waypoints.add(lastVisibleGraphNode.getBounds().getCenter());
/* 55 */         allNodesInBetween.clear();
/* 56 */         lastWaypointNode = lastVisibleGraphNode; continue;
/*    */       } 
/* 58 */       allNodesInBetween.add(currentGraphNode);
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 63 */     WorldCoordinate firstWaypoint = ((WorldBoundsGraphNode)currentPathNode.getGraphNode()).getBounds().getCenter();
/* 64 */     waypoints.add(firstWaypoint);
/*    */ 
/*    */     
/* 67 */     Collections.reverse(waypoints);
/* 68 */     return waypoints;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\LineOfSightWaypointBuilder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */