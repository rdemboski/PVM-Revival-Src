/*    */ package com.funcom.gameengine.pathfinding2;
/*    */ 
/*    */ import com.funcom.gameengine.WorldCoordinate;
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LinearInterpolator
/*    */   implements WaypointBuilder
/*    */ {
/*    */   public static final String TYPE = "linear";
/*    */   private WaypointBuilder waypointBuilder;
/*    */   private float spacing;
/*    */   
/*    */   public LinearInterpolator(WaypointBuilder waypointBuilder, float spacing) {
/* 19 */     if (waypointBuilder == null)
/* 20 */       throw new IllegalArgumentException("waypointBuilder = null"); 
/* 21 */     this.waypointBuilder = waypointBuilder;
/* 22 */     this.spacing = spacing;
/*    */   }
/*    */   
/*    */   public List<WorldCoordinate> makeWaypoints(AStarNode path) {
/* 26 */     List<WorldCoordinate> decoratorWaypoints = this.waypointBuilder.makeWaypoints(path);
/* 27 */     List<WorldCoordinate> returnableWaypoints = new LinkedList<WorldCoordinate>();
/* 28 */     returnableWaypoints.add(decoratorWaypoints.get(0));
/*    */ 
/*    */     
/* 31 */     float leftoverOffset = 0.0F;
/* 32 */     for (int i = 0; i < decoratorWaypoints.size() - 1; i++) {
/*    */       
/* 34 */       WorldCoordinate currentWaypoint = decoratorWaypoints.get(i);
/* 35 */       WorldCoordinate nextWaypoint = decoratorWaypoints.get(i + 1);
/*    */ 
/*    */       
/* 38 */       WorldCoordinate directionVector = (new WorldCoordinate(nextWaypoint)).subtract(currentWaypoint).normalize();
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 43 */       WorldCoordinate stepVector = (new WorldCoordinate(directionVector)).multLocal(this.spacing);
/*    */ 
/*    */ 
/*    */       
/* 47 */       WorldCoordinate currentPosition = (new WorldCoordinate(currentWaypoint)).add(stepVector);
/*    */ 
/*    */ 
/*    */       
/* 51 */       WorldCoordinate leftoverOffsetVector = (new WorldCoordinate(directionVector)).multLocal(leftoverOffset);
/*    */ 
/*    */       
/* 54 */       currentPosition.subtract(leftoverOffsetVector);
/*    */ 
/*    */ 
/*    */       
/* 58 */       while (currentPosition.distanceTo(nextWaypoint) > this.spacing) {
/* 59 */         returnableWaypoints.add(new WorldCoordinate(currentPosition));
/* 60 */         currentPosition.add(stepVector);
/*    */       } 
/*    */ 
/*    */       
/* 64 */       returnableWaypoints.add(new WorldCoordinate(currentPosition));
/*    */ 
/*    */       
/* 67 */       leftoverOffset = (float)currentPosition.distanceTo(nextWaypoint);
/*    */     } 
/*    */     
/* 70 */     return returnableWaypoints;
/*    */   }
/*    */ }


/* Location:              C:\Users\ryand\Desktop\PVM Revival\pvm-game-decomp\tcg-gameengine.jar!\com\funcom\gameengine\pathfinding2\LinearInterpolator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */